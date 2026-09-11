package com.digital_wallet_ledger.digital_wallet_ledger.service;

import com.digital_wallet_ledger.digital_wallet_ledger.domain.Money;
import com.digital_wallet_ledger.digital_wallet_ledger.domain.Wallet;
import com.digital_wallet_ledger.digital_wallet_ledger.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TransactionExecutionEngine {

    private static final Logger log = LoggerFactory.getLogger(TransactionExecutionEngine.class);
    private final WalletRepository walletRepository;

    public TransactionExecutionEngine(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    /**
     * Executes transfer step within an atomic Spring-managed transaction.
     * Invoked from an external collaborator bean, ensuring Spring's AOP proxy intercepts it!
     */
    @Transactional
    public void executeTransferStep(UUID senderWalletId, UUID receiverWalletId, Money amount) {
        // Step 1: Debit sender
        Wallet sender = walletRepository.findById(senderWalletId)
                .orElseThrow(() -> new IllegalArgumentException("Sender wallet not found"));
        sender.debit(amount);
        walletRepository.save(sender);
        log.info("Debited {} from sender wallet {}", amount.amount(), senderWalletId);

        // Step 2: Simulate crash
        if (amount.isPositive()) {
            throw new RuntimeException("Simulated unexpected network/system crash during transfer!");
        }

        // Step 3: Credit receiver
        Wallet receiver = walletRepository.findById(receiverWalletId)
                .orElseThrow(() -> new IllegalArgumentException("Receiver wallet not found"));
        receiver.credit(amount);
        walletRepository.save(receiver);
    }
}