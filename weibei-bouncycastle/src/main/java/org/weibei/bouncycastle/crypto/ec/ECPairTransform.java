package org.weibei.bouncycastle.crypto.ec;

import org.weibei.bouncycastle.crypto.CipherParameters;

public interface ECPairTransform
{
    void init(CipherParameters params);

    ECPair transform(ECPair cipherText);
}
