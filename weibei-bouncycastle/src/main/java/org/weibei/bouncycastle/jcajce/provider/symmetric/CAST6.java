package org.weibei.bouncycastle.jcajce.provider.symmetric;

import org.weibei.bouncycastle.crypto.CipherKeyGenerator;
import org.weibei.bouncycastle.crypto.engines.CAST6Engine;
import org.weibei.bouncycastle.crypto.macs.GMac;
import org.weibei.bouncycastle.crypto.modes.GCMBlockCipher;
import org.weibei.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.weibei.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.weibei.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.weibei.bouncycastle.jcajce.provider.symmetric.util.BaseMac;

public final class CAST6
{
    private CAST6()
    {
    }
    
    public static class ECB
        extends BaseBlockCipher
    {
        public ECB()
        {
            super(new CAST6Engine());
        }
    }

    public static class KeyGen
        extends BaseKeyGenerator
    {
        public KeyGen()
        {
            super("CAST6", 256, new CipherKeyGenerator());
        }
    }

    public static class GMAC
        extends BaseMac
    {
        public GMAC()
        {
            super(new GMac(new GCMBlockCipher(new CAST6Engine())));
        }
    }

    public static class Mappings
        extends SymmetricAlgorithmProvider
    {
        private static final String PREFIX = CAST6.class.getName();

        public Mappings()
        {
        }

        public void configure(ConfigurableProvider provider)
        {
            provider.addAlgorithm("Cipher.CAST6", PREFIX + "$ECB");
            provider.addAlgorithm("KeyGenerator.CAST6", PREFIX + "$KeyGen");

            addGMacAlgorithm(provider, "CAST6", PREFIX + "$GMAC", PREFIX + "$KeyGen");
        }
    }
}
