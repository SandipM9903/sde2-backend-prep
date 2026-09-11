package com.digital_wallet_ledger.digital_wallet_ledger.service;

import com.digital_wallet_ledger.digital_wallet_ledger.domain.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WalletTransferService {

    private static final Logger log = LoggerFactory.getLogger(WalletTransferService.class);
    private final TransactionExecutionEngine executionEngine;

    // Inject the collaborator service
    public WalletTransferService(TransactionExecutionEngine executionEngine) {
        this.executionEngine = executionEngine;
    }

    /**
     * Entry method delegating to the collaborator engine.
     * The call goes through TransactionExecutionEngine's proxy, activating @Transactional!
     */
    public void processTransferWithProxy(UUID senderWalletId, UUID receiverWalletId, Money amount) {
        log.info("Processing transfer from {} to {} through execution engine proxy...", senderWalletId, receiverWalletId);
        executionEngine.executeTransferStep(senderWalletId, receiverWalletId, amount);
    }
}