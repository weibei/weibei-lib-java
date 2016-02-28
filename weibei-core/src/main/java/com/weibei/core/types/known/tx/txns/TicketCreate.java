package com.weibei.core.types.known.tx.txns;

import com.weibei.core.serialized.enums.TransactionType;
import com.weibei.core.types.known.tx.Transaction;

public class TicketCreate extends Transaction {
    public TicketCreate() {
        super(TransactionType.TicketCreate);
    }
}
