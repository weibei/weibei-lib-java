package com.weibei.core.types.known.tx.signed;

import com.weibei.core.coretypes.Amount;
import com.weibei.core.coretypes.Blob;
import com.weibei.core.coretypes.PathSet;
import com.weibei.core.coretypes.hash.HalfSha512;
import com.weibei.core.coretypes.hash.Hash256;
import com.weibei.core.coretypes.hash.prefixes.HashPrefix;
import com.weibei.core.coretypes.uint.UInt32;
import com.weibei.core.fields.Field;
import com.weibei.core.serialized.BytesList;
import com.weibei.core.serialized.MultiSink;
import com.weibei.core.serialized.SerializedType;
import com.weibei.core.serialized.enums.TransactionType;
import com.weibei.core.types.known.tx.Transaction;
import com.weibei.crypto.ecdsa.IKeyPair;
import com.weibei.crypto.ecdsa.Seed;

public class SignedTransaction {
    private SignedTransaction(Transaction of) {
        // TODO: is this just over kill ?
        // TODO: do we want to -> binary -> Transaction ?
        // Shallow copy the transaction. Child fields
        // are practically immutable, except perhaps PathSet.
        txn = new Transaction(of.transactionType());
        for (Field field : of) {
            SerializedType st = of.get(field);
            // Deep copy Paths
            if (field == Field.Paths) {
                st = PathSet.translate.fromBytes(st.toBytes());
            }
            txn.put(field, st);
        }
    }

    // This will eventually be private
    @Deprecated
    public SignedTransaction() {}

    public Transaction txn;
    public Hash256 hash;
    public Hash256 signingHash;
    public Hash256 previousSigningHash;
    public String tx_blob;

    public void sign(String base58Secret) {
        sign(Seed.fromBase58(base58Secret).keyPair());
    }

    public static SignedTransaction fromTx(Transaction tx) {
        return new SignedTransaction(tx);
    }

    public void sign(IKeyPair keyPair) {
        prepare(keyPair, null, null, null);
    }

    public void prepare(IKeyPair keyPair,
                        Amount fee,
                        UInt32 Sequence,
                        UInt32 lastLedgerSequence) {

        Blob pubKey = new Blob(keyPair.pubBytes());

        // This won't always be specified
        if (lastLedgerSequence != null) {
            txn.put(UInt32.LastLedgerSequence, lastLedgerSequence);
        }
        if (Sequence != null) {
            txn.put(UInt32.Sequence, Sequence);
        }
        if (fee != null) {
            txn.put(Amount.Fee, fee);
        }

        txn.signingPubKey(pubKey);

        if (Transaction.CANONICAL_FLAG_DEPLOYED) {
            txn.setCanonicalSignatureFlag();
        }

        txn.checkFormat();
        signingHash = txn.signingHash();
        if (previousSigningHash != null && signingHash.equals(previousSigningHash)) {
            return;
        }
        try {
            txn.txnSignature(new Blob(keyPair.sign(signingHash.bytes())));

            BytesList blob = new BytesList();
            HalfSha512 id = HalfSha512.prefixed256(HashPrefix.transactionID);

            txn.toBytesSink(new MultiSink(blob, id));
            tx_blob = blob.bytesHex();
            hash = id.finish();
        } catch (Exception e) {
            // electric paranoia
            previousSigningHash = null;
            throw new RuntimeException(e);
        } /*else {*/
            previousSigningHash = signingHash;
        // }
    }

    public TransactionType transactionType() {
        return txn.transactionType();
    }
}
