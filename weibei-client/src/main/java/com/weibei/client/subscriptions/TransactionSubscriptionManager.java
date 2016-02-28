package com.weibei.client.subscriptions;
import com.weibei.core.types.known.tx.result.TransactionResult;

public interface TransactionSubscriptionManager {
    public void notifyTransactionResult(TransactionResult tr);
}
