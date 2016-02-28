package org.weibei.bouncycastle.crypto.tls;

import org.weibei.bouncycastle.crypto.DSA;
import org.weibei.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.weibei.bouncycastle.crypto.params.DSAPublicKeyParameters;
import org.weibei.bouncycastle.crypto.signers.DSASigner;

public class TlsDSSSigner
    extends TlsDSASigner
{

    public boolean isValidPublicKey(AsymmetricKeyParameter publicKey)
    {
        return publicKey instanceof DSAPublicKeyParameters;
    }

    protected DSA createDSAImpl()
    {
        return new DSASigner();
    }
}
