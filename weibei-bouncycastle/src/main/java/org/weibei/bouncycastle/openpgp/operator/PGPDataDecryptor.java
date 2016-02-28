package org.weibei.bouncycastle.openpgp.operator;

import java.io.InputStream;

public interface PGPDataDecryptor
{
    InputStream getInputStream(InputStream in);

    int getBlockSize();

    PGPDigestCalculator getIntegrityCalculator();
}
