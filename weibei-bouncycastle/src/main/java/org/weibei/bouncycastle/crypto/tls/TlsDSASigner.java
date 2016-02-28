package org.weibei.bouncycastle.crypto.tls;

import org.weibei.bouncycastle.crypto.CipherParameters;
import org.weibei.bouncycastle.crypto.CryptoException;
import org.weibei.bouncycastle.crypto.DSA;
import org.weibei.bouncycastle.crypto.Digest;
import org.weibei.bouncycastle.crypto.Signer;
import org.weibei.bouncycastle.crypto.digests.NullDigest;
import org.weibei.bouncycastle.crypto.digests.SHA1Digest;
import org.weibei.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.weibei.bouncycastle.crypto.params.ParametersWithRandom;
import org.weibei.bouncycastle.crypto.signers.DSADigestSigner;

public abstract class TlsDSASigner
    extends AbstractTlsSigner
{

    public byte[] generateRawSignature(AsymmetricKeyParameter privateKey, byte[] md5AndSha1)
        throws CryptoException
    {

        // Note: Only use the SHA1 part of the hash
        Signer signer = makeSigner(new NullDigest(), true,
            new ParametersWithRandom(privateKey, this.context.getSecureRandom()));
        signer.update(md5AndSha1, 16, 20);
        return signer.generateSignature();
    }

    public boolean verifyRawSignature(byte[] sigBytes, AsymmetricKeyParameter publicKey, byte[] md5AndSha1)
        throws CryptoException
    {

        // Note: Only use the SHA1 part of the hash
        Signer signer = makeSigner(new NullDigest(), false, publicKey);
        signer.update(md5AndSha1, 16, 20);
        return signer.verifySignature(sigBytes);
    }

    public Signer createSigner(AsymmetricKeyParameter privateKey)
    {
        return makeSigner(new SHA1Digest(), true, new ParametersWithRandom(privateKey, this.context.getSecureRandom()));
    }

    public Signer createVerifyer(AsymmetricKeyParameter publicKey)
    {
        return makeSigner(new SHA1Digest(), false, publicKey);
    }

    protected Signer makeSigner(Digest d, boolean forSigning, CipherParameters cp)
    {
        Signer s = new DSADigestSigner(createDSAImpl(), d);
        s.init(forSigning, cp);
        return s;
    }

    protected abstract DSA createDSAImpl();
}
