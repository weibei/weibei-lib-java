package org.weibei.bouncycastle.jcajce.provider.symmetric.util;

import org.weibei.bouncycastle.crypto.BlockCipher;

public interface BlockCipherProvider
{
    BlockCipher get();
}
