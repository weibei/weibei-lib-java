package org.weibei.bouncycastle.crypto.ec;

import org.weibei.bouncycastle.crypto.CipherParameters;
import org.weibei.bouncycastle.math.ec.ECPoint;

public interface ECDecryptor
{
    void init(CipherParameters params);

    ECPoint decrypt(ECPair cipherText);
}
