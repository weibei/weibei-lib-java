package org.weibei.bouncycastle.crypto.params;

import org.weibei.bouncycastle.crypto.CipherParameters;

public class MQVPublicParameters
    implements CipherParameters
{
    private ECPublicKeyParameters staticPublicKey;
    private ECPublicKeyParameters ephemeralPublicKey;

    public MQVPublicParameters(
        ECPublicKeyParameters   staticPublicKey,
        ECPublicKeyParameters   ephemeralPublicKey)
    {
        this.staticPublicKey = staticPublicKey;
        this.ephemeralPublicKey = ephemeralPublicKey;
    }

    public ECPublicKeyParameters getStaticPublicKey()
    {
        return staticPublicKey;
    }

    public ECPublicKeyParameters getEphemeralPublicKey()
    {
        return ephemeralPublicKey;
    }
}
