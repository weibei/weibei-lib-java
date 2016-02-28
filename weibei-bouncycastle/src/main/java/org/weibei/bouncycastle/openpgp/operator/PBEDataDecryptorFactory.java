package org.weibei.bouncycastle.openpgp.operator;

import org.weibei.bouncycastle.bcpg.S2K;
import org.weibei.bouncycastle.openpgp.PGPException;

public abstract class PBEDataDecryptorFactory
    implements PGPDataDecryptorFactory
{
    private char[] passPhrase;
    private PGPDigestCalculatorProvider calculatorProvider;

    protected PBEDataDecryptorFactory(char[] passPhrase, PGPDigestCalculatorProvider calculatorProvider)
    {
        this.passPhrase = passPhrase;
        this.calculatorProvider = calculatorProvider;
    }

    public byte[] makeKeyFromPassPhrase(int keyAlgorithm, S2K s2k)
        throws PGPException
    {
        return PGPUtil.makeKeyFromPassPhrase(calculatorProvider, keyAlgorithm, s2k, passPhrase);
    }

    public abstract byte[] recoverSessionData(int keyAlgorithm, byte[] key, byte[] seckKeyData)
        throws PGPException;
}
