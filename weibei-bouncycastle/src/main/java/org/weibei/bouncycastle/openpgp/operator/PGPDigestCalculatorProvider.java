package org.weibei.bouncycastle.openpgp.operator;

import org.weibei.bouncycastle.openpgp.PGPException;

public interface PGPDigestCalculatorProvider
{
    PGPDigestCalculator get(int algorithm)
        throws PGPException;
}
