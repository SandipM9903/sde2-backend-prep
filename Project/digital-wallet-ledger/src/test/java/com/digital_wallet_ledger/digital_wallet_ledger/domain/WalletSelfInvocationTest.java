package com.digital_wallet_ledger.digital_wallet_ledger.domain;

import com.digital_wallet_ledger.digital_wallet_ledger.repository.WalletRepository;
import com.digital_wallet_ledger.digital_wallet_ledger.service.WalletTransferService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class WalletSelfInvocationTest {

    @Autowired
    private WalletTransferService transferService;

    @Autowired
    private WalletRepository walletRepository;

    @Test
    @DisplayName("Verify Architectural Fix: Proxy intercepts call, @Transactional rolls back on crash")
    void shouldRollbackSuccessfullyThroughCollaboratorProxy() {
        // 1. Create Sender with $100.00
        Wallet sender = new Wallet(UUID.randomUUID(), Currency.USD);
        sender.credit(Money.of("100.0000", Currency.USD));
        sender = walletRepository.save(sender);

        // 2. Create Receiver with $0.00
        Wallet receiver = new Wallet(UUID.randomUUID(), Currency.USD);
        receiver = walletRepository.save(receiver);

        UUID senderId = sender.getId();
        UUID receiverId = receiver.getId();

        // 3. Attempt transfer of $40.00 via proxy delegation
        assertThrows(RuntimeException.class, () -> {
            transferService.processTransferWithProxy(senderId, receiverId, Money.of("40.0000", Currency.USD));
        });

        // 4. Reload sender from DB
        Wallet reloadedSender = walletRepository.findById(senderId).orElseThrow();

        System.out.println("==================================================");
        System.out.println("Sender Balance After Fix: " + reloadedSender.getBalance());
        System.out.println("Expected (Transaction Rolled Back): 100.0000");
        System.out.println("==================================================");

        // With the collaborator proxy handling the call, rollback succeeded!
        assertEquals(new BigDecimal("100.0000"), reloadedSender.getBalance(), 
                "SUCCESS: Transaction rolled back completely. Zero money lost!");
    }
}