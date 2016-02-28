package com.weibei.java8.example;

import com.weibei.core.coretypes.AccountID;
import com.weibei.core.coretypes.Amount;
import com.weibei.core.coretypes.STObject;
import com.weibei.core.coretypes.uint.UInt32;
import com.weibei.core.types.known.tx.signed.SignedTransaction;
import com.weibei.core.types.known.tx.txns.Payment;

import static com.weibei.java8.utils.Print.print;

/**
 * This example shows how to sign a transaction built using
 * the weibei-lib-java API and one already built in json.
 */
public class SignTransaction {
    public static void main(String[] args) {
        String secret = "ssStiMFzkGefDoTqgk9w9WpYkTepQ";
        Payment payment = new Payment();

        // Put `as` AccountID field Account, `Object` o
        payment.as(AccountID.Account,     "rGZG674DSZJfoY8abMPSgChxZTJZEhyMRm");
        payment.as(AccountID.Destination, "rPMh7Pi9ct699iZUTWaytJUoHcJ7cgyziK");
        payment.as(Amount.Amount,         "1000000000");
        payment.as(UInt32.Sequence,       10);
        payment.as(Amount.Fee,            "10000");

        // Try commenting out the Fee, you'll get STObject.FormatException
        SignedTransaction signed = payment.sign(secret);
        // Sign doesn't mutate the original transaction
        // `txn` is a shallow copy
        if (signed.txn == payment)
            throw new AssertionError();

        // MessageFormat which does the heavy lifting for print gets confused
        // by the `{` and `}` in the json.
        print("The original transaction:");
        print("{0}", payment.prettyJSON());
        print("The signed transaction, with SigningPubKey and TxnSignature:");
        print("{0}", signed.txn.prettyJSON());
        print("The signing hash:   {0}", signed.signingHash);
        print("The transaction id: {0}", signed.hash);
        print("The blob to submit to weibeid:");
        print(signed.tx_blob);

        // What if we just have some JSON as a string we want to sign?
        // That's pretty easy to do as well!
        String tx_json = payment.prettyJSON();
        signAgain(tx_json, secret, signed);
    }

    private static void signAgain(String tx_json,
                                  String secret,
                                  SignedTransaction signedAlready) {
        // fromJSON will give us a payment object but we must cast it
        Payment txn = (Payment) STObject.fromJSON(tx_json);
        SignedTransaction signedAgain = txn.sign(secret);

        // The signing hash will be the same
        if (!signedAgain.signingHash.equals(signedAlready.signingHash))
            throw new AssertionError();

        // There's a random component to TxnSignature which
        // is a component of the `transaction id` but not the
        // signing hash.
        if (signedAgain.hash.equals(signedAlready.hash))
            throw new AssertionError();
    }
}
