package org.weibei.bouncycastle.openpgp.operator;

import java.security.SecureRandom;

import org.weibei.bouncycastle.openpgp.PGPException;

public interface PGPDataEncryptorBuilder
{
    int getAlgorithm();

    PGPDataEncryptor build(byte[] keyBytes)
        throws PGPException;

    SecureRandom getSecureRandom();
}
