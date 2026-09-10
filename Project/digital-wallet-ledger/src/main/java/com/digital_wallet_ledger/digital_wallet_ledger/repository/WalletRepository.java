package com.digital_wallet_ledger.digital_wallet_ledger.repository;

import com.digital_wallet_ledger.digital_wallet_ledger.domain.Currency;
import com.digital_wallet_ledger.digital_wallet_ledger.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {

    /**
     * Find a user's wallet for a specific currency.
     * Guaranteed at most one result due to our unique composite index.
     */
    Optional<Wallet> findByUserIdAndCurrency(UUID userId, Currency currency);

    /**
     * Retrieve all currency wallets belonging to a user.
     */
    List<Wallet> findAllByUserId(UUID userId);

    /**
     * Fast existence check without loading the full entity into memory.
     */
    boolean existsByUserIdAndCurrency(UUID userId, Currency currency);
}