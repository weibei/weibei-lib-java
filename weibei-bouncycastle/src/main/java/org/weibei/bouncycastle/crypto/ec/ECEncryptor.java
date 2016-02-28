package org.weibei.bouncycastle.crypto.ec;

import org.weibei.bouncycastle.crypto.CipherParameters;
import org.weibei.bouncycastle.math.ec.ECPoint;

public interface ECEncryptor
{
    void init(CipherParameters params);

    ECPair encrypt(ECPoint point);
}
