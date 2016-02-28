
package com.weibei.client;

import com.weibei.core.coretypes.AccountID;
import com.weibei.core.coretypes.Amount;
import com.weibei.core.coretypes.uint.UInt32;
import com.weibei.core.types.known.tx.txns.Payment;
import org.junit.Test;

public class TransactionTest {

    @Test
    public void testCreatePaymentTransaction() throws Exception {
        String secret = "ssStiMFzkGefDoTqgk9w9WpYkTepQ";
        Payment payment = new Payment();

        // Put `as` AccountID field Account, `Object` o
        payment.as(AccountID.Account,     "rGZG674DSZJfoY8abMPSgChxZTJZEhyMRm");
        payment.as(AccountID.Destination, "rPMh7Pi9ct699iZUTWaytJUoHcJ7cgyziK");
        payment.as(Amount.Amount,         "1000000000");
        payment.as(Amount.Fee,            "10000");
        payment.as(UInt32.Sequence,       10);

        payment.sign(secret);
    }
}
