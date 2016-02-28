package org.weibei.bouncycastle.crypto.tls;

import org.weibei.bouncycastle.crypto.CryptoException;
import org.weibei.bouncycastle.crypto.Signer;
import org.weibei.bouncycastle.crypto.params.AsymmetricKeyParameter;

public interface TlsSigner
{

    void init(TlsContext context);

    byte[] generateRawSignature(AsymmetricKeyParameter privateKey, byte[] md5AndSha1)
        throws CryptoException;

    boolean verifyRawSignature(byte[] sigBytes, AsymmetricKeyParameter publicKey, byte[] md5AndSha1)
        throws CryptoException;

    Signer createSigner(AsymmetricKeyParameter privateKey);

    Signer createVerifyer(AsymmetricKeyParameter publicKey);

    boolean isValidPublicKey(AsymmetricKeyParameter publicKey);
}
