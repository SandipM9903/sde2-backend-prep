package com.digital_wallet_ledger.digital_wallet_ledger.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WalletDomainTest {

    @Test
    @DisplayName("Should initialize wallet with zero balance scaled to 4 decimal places")
    void shouldInitializeWithZeroBalance() {
        UUID userId = UUID.randomUUID();
        Wallet wallet = new Wallet(userId, Currency.USD);

        assertEquals(new BigDecimal("0.0000"), wallet.getBalance());
        assertEquals(Currency.USD, wallet.getCurrency());
    }

    @Test
    @DisplayName("Should successfully credit money and maintain Banker's rounding")
    void shouldCreditMoneyCorrectly() {
        Wallet wallet = new Wallet(UUID.randomUUID(), Currency.INR);
        wallet.credit(Money.of("150.2550", Currency.INR));

        assertEquals(new BigDecimal("150.2550"), wallet.getBalance());
    }

    @Test
    @DisplayName("Should fail when debiting more than current balance (Insufficient funds)")
    void shouldThrowExceptionWhenDebitingMoreThanBalance() {
        Wallet wallet = new Wallet(UUID.randomUUID(), Currency.EUR);
        wallet.credit(Money.of("50.0000", Currency.EUR));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            wallet.debit(Money.of("100.0000", Currency.EUR));
        });

        assertTrue(exception.getMessage().contains("Insufficient balance"));
    }

    @Test
    @DisplayName("Should prevent currency mismatch operations")
    void shouldPreventCurrencyMismatch() {
        Wallet wallet = new Wallet(UUID.randomUUID(), Currency.USD);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            wallet.credit(Money.of("10.0000", Currency.EUR));
        });

        assertTrue(exception.getMessage().contains("Currency mismatch"));
    }
}
