package org.weibei.bouncycastle.openpgp.operator;

import org.weibei.bouncycastle.openpgp.PGPException;
import org.weibei.bouncycastle.openpgp.PGPPrivateKey;

public interface PGPContentSignerBuilder
{
    public PGPContentSigner build(final int signatureType, final PGPPrivateKey privateKey)
        throws PGPException;
}
