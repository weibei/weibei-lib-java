package org.weibei.bouncycastle.pqc.crypto.gmss;

import java.security.SecureRandom;

import org.weibei.bouncycastle.crypto.KeyGenerationParameters;

public class GMSSKeyGenerationParameters
    extends KeyGenerationParameters
{

    private GMSSParameters params;

    public GMSSKeyGenerationParameters(
        SecureRandom random,
        GMSSParameters params)
    {
        // XXX key size?
        super(random, 1);
        this.params = params;
    }

    public GMSSParameters getParameters()
    {
        return params;
    }
}
