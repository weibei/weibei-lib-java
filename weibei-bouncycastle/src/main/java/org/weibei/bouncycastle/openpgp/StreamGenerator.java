package org.weibei.bouncycastle.openpgp;

import java.io.IOException;

interface StreamGenerator
{
    void close()
        throws IOException;
}
