package com.digital_wallet_ledger.digital_wallet_ledger.service;

import com.digital_wallet_ledger.digital_wallet_ledger.domain.Currency;
import com.digital_wallet_ledger.digital_wallet_ledger.domain.Money;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * PRODUCTION-GRADE IMPLEMENTATION:
 * Fully stateless service. All state is confined to the method call stack.
 * Every concurrent thread executes within its own isolated stack frame.
 */
@Service
public class TransferFeeCalculator {

    private static final BigDecimal TIER_THRESHOLD = new BigDecimal("1000.0000");
    private static final BigDecimal HIGH_TIER_FEE_PERCENTAGE = new BigDecimal("0.0100"); // 1%
    private static final BigDecimal LOW_TIER_FEE_PERCENTAGE = new BigDecimal("0.0010");  // 0.1%

    /**
     * Calculates transfer fee in a pure, thread-safe manner.
     * No instance variables are read or written.
     */
    public Money calculateFee(Money transferAmount) {
        Objects.requireNonNull(transferAmount, "Transfer amount must not be null");

        BigDecimal percentage = transferAmount.amount().compareTo(TIER_THRESHOLD) > 0
                ? HIGH_TIER_FEE_PERCENTAGE
                : LOW_TIER_FEE_PERCENTAGE;

        BigDecimal feeValue = transferAmount.amount().multiply(percentage);
        return new Money(feeValue, transferAmount.currency());
    }
}