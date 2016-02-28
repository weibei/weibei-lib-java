package org.weibei.bouncycastle.openpgp.operator;

import org.weibei.bouncycastle.bcpg.ContainedPacket;
import org.weibei.bouncycastle.openpgp.PGPException;

public abstract class PGPKeyEncryptionMethodGenerator
{
    public abstract ContainedPacket generate(int encAlgorithm, byte[] sessionInfo)
        throws PGPException;
}
