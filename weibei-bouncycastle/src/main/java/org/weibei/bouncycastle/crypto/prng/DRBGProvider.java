package org.weibei.bouncycastle.crypto.prng;

import org.weibei.bouncycastle.crypto.prng.drbg.SP80090DRBG;

interface DRBGProvider
{
    SP80090DRBG get(EntropySource entropySource);
}
