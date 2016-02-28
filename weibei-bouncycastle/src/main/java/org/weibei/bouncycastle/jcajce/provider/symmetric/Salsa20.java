package org.weibei.bouncycastle.jcajce.provider.symmetric;

import org.weibei.bouncycastle.crypto.CipherKeyGenerator;
import org.weibei.bouncycastle.crypto.engines.Salsa20Engine;
import org.weibei.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.weibei.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.weibei.bouncycastle.jcajce.provider.symmetric.util.BaseStreamCipher;
import org.weibei.bouncycastle.jcajce.provider.util.AlgorithmProvider;

public final class Salsa20
{
    private Salsa20()
    {
    }
    
    public static class Base
        extends BaseStreamCipher
    {
        public Base()
        {
            super(new Salsa20Engine(), 8);
        }
    }

    public static class KeyGen
        extends BaseKeyGenerator
    {
        public KeyGen()
        {
            super("Salsa20", 128, new CipherKeyGenerator());
        }
    }

    public static class Mappings
        extends AlgorithmProvider
    {
        private static final String PREFIX = Salsa20.class.getName();

        public Mappings()
        {
        }

        public void configure(ConfigurableProvider provider)
        {

            provider.addAlgorithm("Cipher.SALSA20", PREFIX + "$Base");
            provider.addAlgorithm("KeyGenerator.SALSA20", PREFIX + "$KeyGen");

        }
    }
}
