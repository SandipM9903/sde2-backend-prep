package com.digital_wallet_ledger.digital_wallet_ledger.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(
    name = "wallets",
    indexes = {
        @Index(name = "idx_wallets_user_id", columnList = "user_id"),
        @Index(name = "idx_wallets_user_currency", columnList = "user_id, currency", unique = true)
    }
)
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false, length = 3, updatable = false)
    private Currency currency;

    @NotNull
    @Column(name = "balance", nullable = false, precision = 19, scale = 4)
    private BigDecimal balance;

    /**
     * Optimistic locking mechanism.
     * Prevents lost updates under concurrent debits/credits.
     */
    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    // Protected default constructor required by JPA
    protected Wallet() {
    }

    public Wallet(UUID userId, Currency currency) {
        this.userId = Objects.requireNonNull(userId, "UserId cannot be null");
        this.currency = Objects.requireNonNull(currency, "Currency cannot be null");
        this.balance = BigDecimal.ZERO.setScale(Money.INTERNAL_SCALE, Money.DEFAULT_ROUNDING);
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }

    // Getters
    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public Currency getCurrency() { return currency; }
    public BigDecimal getBalance() { return balance; }
    public Long getVersion() { return version; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    /**
     * Domain method: Credit funds to the wallet.
     * Keeps business logic encapsulated within the entity.
     */
    public void credit(Money money) {
        Objects.requireNonNull(money, "Money cannot be null");
        validateCurrency(money.currency());

        if (!money.isPositive()) {
            throw new IllegalArgumentException("Credit amount must be strictly positive");
        }

        this.balance = this.balance.add(money.amount());
    }

    /**
     * Domain method: Debit funds from the wallet.
     * Prevents balance from dropping below zero.
     */
    public void debit(Money money) {
        Objects.requireNonNull(money, "Money cannot be null");
        validateCurrency(money.currency());

        if (!money.isPositive()) {
            throw new IllegalArgumentException("Debit amount must be strictly positive");
        }

        if (this.balance.compareTo(money.amount()) < 0) {
            throw new IllegalStateException("Insufficient balance for wallet ID: " + this.id);
        }

        this.balance = this.balance.subtract(money.amount());
    }

    private void validateCurrency(Currency targetCurrency) {
        if (this.currency != targetCurrency) {
            throw new IllegalArgumentException(
                "Currency mismatch: Wallet currency is " + this.currency + " but operation was in " + targetCurrency
            );
        }
    }
}