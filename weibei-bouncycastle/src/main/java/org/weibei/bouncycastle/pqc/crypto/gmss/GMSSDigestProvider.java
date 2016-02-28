package org.weibei.bouncycastle.pqc.crypto.gmss;

import org.weibei.bouncycastle.crypto.Digest;

public interface GMSSDigestProvider
{
    Digest get();
}
