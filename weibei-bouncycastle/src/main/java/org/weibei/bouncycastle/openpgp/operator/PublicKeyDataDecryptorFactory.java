package org.weibei.bouncycastle.openpgp.operator;

import java.math.BigInteger;

import org.weibei.bouncycastle.openpgp.PGPException;

public interface PublicKeyDataDecryptorFactory
    extends PGPDataDecryptorFactory
{
    public byte[] recoverSessionData(int keyAlgorithm, BigInteger[] secKeyData)
            throws PGPException;
}
