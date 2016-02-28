package org.weibei.bouncycastle.openpgp.operator.bc;

import java.io.InputStream;

import org.weibei.bouncycastle.crypto.BlockCipher;
import org.weibei.bouncycastle.crypto.BufferedBlockCipher;
import org.weibei.bouncycastle.crypto.io.CipherInputStream;
import org.weibei.bouncycastle.crypto.modes.CFBBlockCipher;
import org.weibei.bouncycastle.crypto.modes.OpenPGPCFBBlockCipher;
import org.weibei.bouncycastle.crypto.params.KeyParameter;
import org.weibei.bouncycastle.crypto.params.ParametersWithIV;
import org.weibei.bouncycastle.openpgp.operator.PGPDataDecryptor;
import org.weibei.bouncycastle.openpgp.operator.PGPDigestCalculator;

class BcUtil
{
    static BufferedBlockCipher createStreamCipher(boolean forEncryption, BlockCipher engine, boolean withIntegrityPacket, byte[] key)
    {
        BufferedBlockCipher c;

        if (withIntegrityPacket)
        {
            c = new BufferedBlockCipher(new CFBBlockCipher(engine, engine.getBlockSize() * 8));
        }
        else
        {
            c = new BufferedBlockCipher(new OpenPGPCFBBlockCipher(engine));
        }

        KeyParameter keyParameter = new KeyParameter(key);

        if (withIntegrityPacket)
        {
            c.init(forEncryption, new ParametersWithIV(keyParameter, new byte[engine.getBlockSize()]));
        }
        else
        {
            c.init(forEncryption, keyParameter);
        }

        return c;
    }

    public static PGPDataDecryptor createDataDecryptor(boolean withIntegrityPacket, BlockCipher engine, byte[] key)
    {
        final BufferedBlockCipher c = createStreamCipher(false, engine, withIntegrityPacket, key);

        return new PGPDataDecryptor()
        {
            public InputStream getInputStream(InputStream in)
            {
                return new CipherInputStream(in, c);
            }

            public int getBlockSize()
            {
                return c.getBlockSize();
            }

            public PGPDigestCalculator getIntegrityCalculator()
            {
                return new SHA1PGPDigestCalculator();
            }
        };
    }

    public static BufferedBlockCipher createSymmetricKeyWrapper(boolean forEncryption, BlockCipher engine, byte[] key, byte[] iv)
    {
        BufferedBlockCipher c = new BufferedBlockCipher(new CFBBlockCipher(engine, engine.getBlockSize() * 8));

        c.init(forEncryption, new ParametersWithIV(new KeyParameter(key), iv));

        return c;
    }
}
