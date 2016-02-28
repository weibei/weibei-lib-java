package org.weibei.bouncycastle.crypto.tls;

import org.weibei.bouncycastle.crypto.Digest;

interface TlsHandshakeHash
    extends Digest
{

    void init(TlsContext context);

    TlsHandshakeHash commit();

    TlsHandshakeHash fork();
}
