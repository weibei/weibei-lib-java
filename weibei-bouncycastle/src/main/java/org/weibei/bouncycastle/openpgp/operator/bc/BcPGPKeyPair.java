package org.weibei.bouncycastle.openpgp.operator.bc;

import java.util.Date;

import org.weibei.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.weibei.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.weibei.bouncycastle.openpgp.PGPException;
import org.weibei.bouncycastle.openpgp.PGPKeyPair;
import org.weibei.bouncycastle.openpgp.PGPPrivateKey;
import org.weibei.bouncycastle.openpgp.PGPPublicKey;

public class BcPGPKeyPair
    extends PGPKeyPair
{
    private static PGPPublicKey getPublicKey(int algorithm, AsymmetricKeyParameter pubKey, Date date)
        throws PGPException
    {
        return new BcPGPKeyConverter().getPGPPublicKey(algorithm, pubKey, date);
    }

    private static PGPPrivateKey getPrivateKey(PGPPublicKey pub, AsymmetricKeyParameter privKey)
        throws PGPException
    {
        return new BcPGPKeyConverter().getPGPPrivateKey(pub, privKey);
    }

    public BcPGPKeyPair(int algorithm, AsymmetricCipherKeyPair keyPair, Date date)
        throws PGPException
    {
        this.pub = getPublicKey(algorithm, (AsymmetricKeyParameter)keyPair.getPublic(), date);
        this.priv = getPrivateKey(this.pub, (AsymmetricKeyParameter)keyPair.getPrivate());
    }
}
