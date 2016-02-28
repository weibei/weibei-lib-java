package org.weibei.bouncycastle.jcajce.provider.config;

import javax.crypto.spec.DHParameterSpec;

import org.weibei.bouncycastle.jce.spec.ECParameterSpec;

public interface ProviderConfiguration
{
    ECParameterSpec getEcImplicitlyCa();

    DHParameterSpec getDHDefaultParameters(int keySize);
}
