package com.digital_wallet_ledger.digital_wallet_ledger.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Immutable Value Object representing an amount in a specific Currency.
 * Maintains an internal scale of 4 decimal places for precision.
 */
public record Money(BigDecimal amount, Currency currency) {

    public static final int INTERNAL_SCALE = 4;
    public static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_EVEN; // Banker's Rounding

    // Compact Canonical Constructor with Domain Invariants
    public Money {
        Objects.requireNonNull(amount, "Amount must not be null");
        Objects.requireNonNull(currency, "Currency must not be null");

        // Normalize amount to 4 decimal places with Banker's Rounding
        amount = amount.setScale(INTERNAL_SCALE, DEFAULT_ROUNDING);
    }

    // Static Factory Methods
    public static Money of(BigDecimal amount, Currency currency) {
        return new Money(amount, currency);
    }

    public static Money of(String amount, Currency currency) {
        return new Money(new BigDecimal(amount), currency);
    }

    public static Money zero(Currency currency) {
        return new Money(BigDecimal.ZERO, currency);
    }

    // Domain Arithmetic Operations
    public Money add(Money other) {
        validateSameCurrency(other);
        return new Money(this.amount.add(other.amount), this.currency);
    }

    public Money subtract(Money other) {
        validateSameCurrency(other);
        return new Money(this.amount.subtract(other.amount), this.currency);
    }

    public boolean isGreaterThanOrEqual(Money other) {
        validateSameCurrency(other);
        return this.amount.compareTo(other.amount) >= 0;
    }

    public boolean isPositive() {
        return this.amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isNegative() {
        return this.amount.compareTo(BigDecimal.ZERO) < 0;
    }

    public boolean isZero() {
        return this.amount.compareTo(BigDecimal.ZERO) == 0;
    }

    private void validateSameCurrency(Money other) {
        Objects.requireNonNull(other, "Comparison Money must not be null");
        if (this.currency != other.currency) {
            throw new IllegalArgumentException(
                "Currency mismatch: cannot operate between " + this.currency + " and " + other.currency
            );
        }
    }
}
