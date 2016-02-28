package org.weibei.bouncycastle.crypto.generators;

import org.weibei.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.weibei.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.weibei.bouncycastle.crypto.EphemeralKeyPair;
import org.weibei.bouncycastle.crypto.KeyEncoder;

public class EphemeralKeyPairGenerator
{
    private AsymmetricCipherKeyPairGenerator gen;
    private KeyEncoder keyEncoder;

    public EphemeralKeyPairGenerator(AsymmetricCipherKeyPairGenerator gen, KeyEncoder keyEncoder)
    {
        this.gen = gen;
        this.keyEncoder = keyEncoder;
    }

    public EphemeralKeyPair generate()
    {
        AsymmetricCipherKeyPair eph = gen.generateKeyPair();

        // Encode the ephemeral public key
         return new EphemeralKeyPair(eph, keyEncoder);
    }
}
