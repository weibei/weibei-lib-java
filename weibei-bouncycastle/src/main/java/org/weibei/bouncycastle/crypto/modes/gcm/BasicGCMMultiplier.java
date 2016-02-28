package org.weibei.bouncycastle.crypto.modes.gcm;

import org.weibei.bouncycastle.util.Arrays;

public class BasicGCMMultiplier implements GCMMultiplier
{
    private byte[] H;

    public void init(byte[] H)
    {
        this.H = Arrays.clone(H);
    }

    public void multiplyH(byte[] x)
    {
        GCMUtil.multiply(x, H);
    }
}
