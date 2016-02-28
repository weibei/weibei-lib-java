package org.weibei.bouncycastle.crypto.tls;

import org.weibei.bouncycastle.crypto.DSA;
import org.weibei.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.weibei.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.weibei.bouncycastle.crypto.signers.ECDSASigner;

public class TlsECDSASigner
    extends TlsDSASigner
{

    public boolean isValidPublicKey(AsymmetricKeyParameter publicKey)
    {
        return publicKey instanceof ECPublicKeyParameters;
    }

    protected DSA createDSAImpl()
    {
        return new ECDSASigner();
    }
}
