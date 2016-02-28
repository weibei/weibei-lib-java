package org.weibei.bouncycastle.openpgp.operator;

import org.weibei.bouncycastle.openpgp.PGPException;

public interface PGPContentVerifierBuilderProvider
{
    public PGPContentVerifierBuilder get(int keyAlgorithm, int hashAlgorithm)
        throws PGPException;
}
