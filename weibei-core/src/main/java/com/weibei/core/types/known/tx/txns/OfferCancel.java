package com.weibei.core.types.known.tx.txns;

import com.weibei.core.serialized.enums.TransactionType;
import com.weibei.core.types.known.tx.Transaction;

public class OfferCancel extends Transaction {
    public OfferCancel() {
        super(TransactionType.OfferCancel);
    }
}
