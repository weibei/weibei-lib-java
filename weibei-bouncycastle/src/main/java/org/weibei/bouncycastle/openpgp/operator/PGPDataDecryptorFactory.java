package org.weibei.bouncycastle.openpgp.operator;

import org.weibei.bouncycastle.openpgp.PGPException;

public interface PGPDataDecryptorFactory
{
    public PGPDataDecryptor createDataDecryptor(boolean withIntegrityPacket, int encAlgorithm, byte[] key)
        throws PGPException;
}
