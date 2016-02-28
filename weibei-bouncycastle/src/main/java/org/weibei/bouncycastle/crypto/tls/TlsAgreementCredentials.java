package org.weibei.bouncycastle.crypto.tls;

import java.io.IOException;

import org.weibei.bouncycastle.crypto.params.AsymmetricKeyParameter;

public interface TlsAgreementCredentials
    extends TlsCredentials
{

    byte[] generateAgreement(AsymmetricKeyParameter peerPublicKey)
        throws IOException;
}
