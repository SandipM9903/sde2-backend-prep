// package com.digital_wallet_ledger.digital_wallet_ledger.domain;

// import com.digital_wallet_ledger.digital_wallet_ledger.domain.Currency;
// import com.digital_wallet_ledger.digital_wallet_ledger.domain.Money;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;

// import java.math.BigDecimal;
// import java.util.concurrent.*;
// import java.util.concurrent.atomic.AtomicInteger;
// import com.digital_wallet_ledger.digital_wallet_ledger.service.TransferFeeCalculator;

// class TransferFeeCalculatorConcurrencyTest {

//     @Test
//     @DisplayName("Expose Race Condition: Concurrent requests corrupt singleton internal state")
//     void shouldExposeRaceConditionUnderConcurrentExecution() throws InterruptedException, ExecutionException {
//         TransferFeeCalculator calculator = new TransferFeeCalculator();

//         // We will simulate 2 concurrent users:
//         // User 1: Transfer $5,000 -> Expected Fee = $50.0000 (1%)
//         // User 2: Transfer $100   -> Expected Fee = $0.1000  (0.1%)
//         Money largeTransfer = Money.of("5000.0000", Currency.USD);
//         Money smallTransfer = Money.of("100.0000", Currency.USD);

//         ExecutorService executor = Executors.newFixedThreadPool(2);
//         CountDownLatch startSignal = new CountDownLatch(1);

//         // Submit User 1 task
//         Future<Money> user1Future = executor.submit(() -> {
//             startSignal.await(); // wait for simultaneous start
//             return calculator.calculateFee(largeTransfer);
//         });

//         // Submit User 2 task
//         Future<Money> user2Future = executor.submit(() -> {
//             startSignal.await(); // wait for simultaneous start
//             return calculator.calculateFee(smallTransfer);
//         });

//         // Fire both threads at the exact same moment
//         startSignal.countDown();

//         Money feeUser1 = user1Future.get();
//         Money feeUser2 = user2Future.get();
//         Money finalStateInBean = calculator.getLastCalculatedFee();

//         System.out.println("User 1 Fee Returned: " + feeUser1.amount());
//         System.out.println("User 2 Fee Returned: " + feeUser2.amount());
//         System.out.println("Final Singleton Field State: " + finalStateInBean.amount());

//         executor.shutdown();
//     }
// }