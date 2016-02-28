package org.weibei.bouncycastle.openpgp.operator;

import org.weibei.bouncycastle.openpgp.PGPException;
import org.weibei.bouncycastle.openpgp.PGPPublicKey;

public interface PGPContentVerifierBuilder
{
    public PGPContentVerifier build(final PGPPublicKey publicKey)
        throws PGPException;
}
