package org.weibei.bouncycastle.crypto;

import java.io.IOException;
import java.io.InputStream;

import org.weibei.bouncycastle.crypto.params.AsymmetricKeyParameter;

public interface KeyParser
{
    AsymmetricKeyParameter readKey(InputStream stream)
        throws IOException;
}
