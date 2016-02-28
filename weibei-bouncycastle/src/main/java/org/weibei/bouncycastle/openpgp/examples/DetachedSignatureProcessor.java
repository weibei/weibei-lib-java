package org.weibei.bouncycastle.openpgp.examples;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.Security;

import org.weibei.bouncycastle.bcpg.ArmoredOutputStream;
import org.weibei.bouncycastle.bcpg.BCPGOutputStream;
import org.weibei.bouncycastle.jce.provider.BouncyCastleProvider;
import org.weibei.bouncycastle.openpgp.PGPCompressedData;
import org.weibei.bouncycastle.openpgp.PGPException;
import org.weibei.bouncycastle.openpgp.PGPObjectFactory;
import org.weibei.bouncycastle.openpgp.PGPPrivateKey;
import org.weibei.bouncycastle.openpgp.PGPPublicKey;
import org.weibei.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.weibei.bouncycastle.openpgp.PGPSecretKey;
import org.weibei.bouncycastle.openpgp.PGPSignature;
import org.weibei.bouncycastle.openpgp.PGPSignatureGenerator;
import org.weibei.bouncycastle.openpgp.PGPSignatureList;
import org.weibei.bouncycastle.openpgp.PGPUtil;
import org.weibei.bouncycastle.openpgp.operator.jcajce.JcaPGPContentSignerBuilder;
import org.weibei.bouncycastle.openpgp.operator.jcajce.JcaPGPContentVerifierBuilderProvider;
import org.weibei.bouncycastle.openpgp.operator.jcajce.JcePBESecretKeyDecryptorBuilder;

/**
 * A simple utility class that creates seperate signatures for files and verifies them.
 * <p>
 * To sign a file: DetachedSignatureProcessor -s [-a] fileName secretKey passPhrase.<br>
 * If -a is specified the output file will be "ascii-armored".
 * <p>
 * To decrypt: DetachedSignatureProcessor -v  fileName signatureFile publicKeyFile.
 * <p>
 * Note: this example will silently overwrite files.
 * It also expects that a single pass phrase
 * will have been used.
 */
public class DetachedSignatureProcessor
{
    private static void verifySignature(
        String fileName,
        String inputFileName,
        String keyFileName)
        throws GeneralSecurityException, IOException, PGPException
    {
        InputStream in = new BufferedInputStream(new FileInputStream(inputFileName));
        InputStream keyIn = new BufferedInputStream(new FileInputStream(keyFileName));

        verifySignature(fileName, in, keyIn);

        keyIn.close();
        in.close();
    }

    /*
     * verify the signature in in against the file fileName.
     */
    private static void verifySignature(
        String          fileName,
        InputStream     in,
        InputStream     keyIn)
        throws GeneralSecurityException, IOException, PGPException
    {
        in = PGPUtil.getDecoderStream(in);
        
        PGPObjectFactory    pgpFact = new PGPObjectFactory(in);
        PGPSignatureList    p3;

        Object    o = pgpFact.nextObject();
        if (o instanceof PGPCompressedData)
        {
            PGPCompressedData             c1 = (PGPCompressedData)o;

            pgpFact = new PGPObjectFactory(c1.getDataStream());
            
            p3 = (PGPSignatureList)pgpFact.nextObject();
        }
        else
        {
            p3 = (PGPSignatureList)o;
        }
            
        PGPPublicKeyRingCollection  pgpPubRingCollection = new PGPPublicKeyRingCollection(PGPUtil.getDecoderStream(keyIn));


        InputStream                 dIn = new BufferedInputStream(new FileInputStream(fileName));

        PGPSignature                sig = p3.get(0);
        PGPPublicKey                key = pgpPubRingCollection.getPublicKey(sig.getKeyID());

        sig.init(new JcaPGPContentVerifierBuilderProvider().setProvider("BC"), key);

        int ch;
        while ((ch = dIn.read()) >= 0)
        {
            sig.update((byte)ch);
        }

        dIn.close();

        if (sig.verify())
        {
            System.out.println("signature verified.");
        }
        else
        {
            System.out.println("signature verification failed.");
        }
    }

    private static void createSignature(
        String  inputFileName,
        String  keyFileName,
        String  outputFileName,
        char[]  pass,
        boolean armor)
        throws GeneralSecurityException, IOException, PGPException
    {
        InputStream keyIn = new BufferedInputStream(new FileInputStream(keyFileName));
        OutputStream out = new BufferedOutputStream(new FileOutputStream(outputFileName));

        createSignature(inputFileName, keyIn, out, pass, armor);

        out.close();
        keyIn.close();
    }

    private static void createSignature(
        String          fileName,
        InputStream     keyIn,
        OutputStream    out,
        char[]          pass,
        boolean         armor)
        throws GeneralSecurityException, IOException, PGPException
    {    
        if (armor)
        {
            out = new ArmoredOutputStream(out);
        }

        PGPSecretKey             pgpSec = PGPExampleUtil.readSecretKey(keyIn);
        PGPPrivateKey            pgpPrivKey = pgpSec.extractPrivateKey(new JcePBESecretKeyDecryptorBuilder().setProvider("BC").build(pass));
        PGPSignatureGenerator    sGen = new PGPSignatureGenerator(new JcaPGPContentSignerBuilder(pgpSec.getPublicKey().getAlgorithm(), PGPUtil.SHA1).setProvider("BC"));
        
        sGen.init(PGPSignature.BINARY_DOCUMENT, pgpPrivKey);
        
        BCPGOutputStream         bOut = new BCPGOutputStream(out);
        
        InputStream              fIn = new BufferedInputStream(new FileInputStream(fileName));

        int ch;
        while ((ch = fIn.read()) >= 0)
        {
            sGen.update((byte)ch);
        }

        fIn.close();

        sGen.generate().encode(bOut);

        if (armor)
        {
            out.close();
        }
    }

    public static void main(
        String[] args)
        throws Exception
    {
        Security.addProvider(new BouncyCastleProvider());

        if (args[0].equals("-s"))
        {
            if (args[1].equals("-a"))
            {
                createSignature(args[2], args[3], args[2] + ".asc", args[4].toCharArray(), true);
            }
            else
            {
                createSignature(args[1], args[2], args[1] + ".bpg", args[3].toCharArray(), false);
            }
        }
        else if (args[0].equals("-v"))
        {
            verifySignature(args[1], args[2], args[3]);
        }
        else
        {
            System.err.println("usage: DetachedSignatureProcessor [-s [-a] file keyfile passPhrase]|[-v file sigFile keyFile]");
        }
    }
}
