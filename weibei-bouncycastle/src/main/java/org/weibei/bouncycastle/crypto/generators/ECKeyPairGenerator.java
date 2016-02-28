package org.weibei.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.weibei.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.weibei.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.weibei.bouncycastle.crypto.KeyGenerationParameters;
import org.weibei.bouncycastle.crypto.params.ECDomainParameters;
import org.weibei.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.weibei.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.weibei.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.weibei.bouncycastle.math.ec.ECConstants;
import org.weibei.bouncycastle.math.ec.ECPoint;

public class ECKeyPairGenerator
    implements AsymmetricCipherKeyPairGenerator, ECConstants
{
    ECDomainParameters  params;
    SecureRandom        random;

    public void init(
        KeyGenerationParameters param)
    {
        ECKeyGenerationParameters  ecP = (ECKeyGenerationParameters)param;

        this.random = ecP.getRandom();
        this.params = ecP.getDomainParameters();
    }

    /**
     * Given the domain parameters this routine generates an EC key
     * pair in accordance with X9.62 section 5.2.1 pages 26, 27.
     */
    public AsymmetricCipherKeyPair generateKeyPair()
    {
        BigInteger n = params.getN();
        int        nBitLength = n.bitLength();
        BigInteger d;

        do
        {
            d = new BigInteger(nBitLength, random);
        }
        while (d.equals(ZERO)  || (d.compareTo(n) >= 0));

        ECPoint Q = params.getG().multiply(d);

        return new AsymmetricCipherKeyPair(
            new ECPublicKeyParameters(Q, params),
            new ECPrivateKeyParameters(d, params));
    }
}
