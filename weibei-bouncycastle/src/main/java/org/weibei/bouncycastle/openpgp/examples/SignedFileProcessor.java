package org.weibei.bouncycastle.openpgp.examples;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.SignatureException;
import java.util.Iterator;

import org.weibei.bouncycastle.bcpg.ArmoredOutputStream;
import org.weibei.bouncycastle.bcpg.BCPGOutputStream;
import org.weibei.bouncycastle.jce.provider.BouncyCastleProvider;
import org.weibei.bouncycastle.openpgp.PGPCompressedData;
import org.weibei.bouncycastle.openpgp.PGPCompressedDataGenerator;
import org.weibei.bouncycastle.openpgp.PGPException;
import org.weibei.bouncycastle.openpgp.PGPLiteralData;
import org.weibei.bouncycastle.openpgp.PGPLiteralDataGenerator;
import org.weibei.bouncycastle.openpgp.PGPObjectFactory;
import org.weibei.bouncycastle.openpgp.PGPOnePassSignature;
import org.weibei.bouncycastle.openpgp.PGPOnePassSignatureList;
import org.weibei.bouncycastle.openpgp.PGPPrivateKey;
import org.weibei.bouncycastle.openpgp.PGPPublicKey;
import org.weibei.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.weibei.bouncycastle.openpgp.PGPSecretKey;
import org.weibei.bouncycastle.openpgp.PGPSignature;
import org.weibei.bouncycastle.openpgp.PGPSignatureGenerator;
import org.weibei.bouncycastle.openpgp.PGPSignatureList;
import org.weibei.bouncycastle.openpgp.PGPSignatureSubpacketGenerator;
import org.weibei.bouncycastle.openpgp.PGPUtil;
import org.weibei.bouncycastle.openpgp.operator.jcajce.JcaPGPContentSignerBuilder;
import org.weibei.bouncycastle.openpgp.operator.jcajce.JcaPGPContentVerifierBuilderProvider;
import org.weibei.bouncycastle.openpgp.operator.jcajce.JcePBESecretKeyDecryptorBuilder;

/**
 * A simple utility class that signs and verifies files.
 * <p>
 * To sign a file: SignedFileProcessor -s [-a] fileName secretKey passPhrase.<br>
 * If -a is specified the output file will be "ascii-armored".
 * <p>
 * To decrypt: SignedFileProcessor -v fileName publicKeyFile.
 * <p>
 * <b>Note</b>: this example will silently overwrite files, nor does it pay any attention to
 * the specification of "_CONSOLE" in the filename. It also expects that a single pass phrase
 * will have been used.
 * <p>
 * <b>Note</b>: the example also makes use of PGP compression. If you are having difficulty getting it
 * to interoperate with other PGP programs try removing the use of compression first.
 */
public class SignedFileProcessor
{
    /*
     * verify the passed in file as being correctly signed.
     */
    private static void verifyFile(
        InputStream        in,
        InputStream        keyIn)
        throws Exception
    {
        in = PGPUtil.getDecoderStream(in);
        
        PGPObjectFactory            pgpFact = new PGPObjectFactory(in);

        PGPCompressedData           c1 = (PGPCompressedData)pgpFact.nextObject();

        pgpFact = new PGPObjectFactory(c1.getDataStream());
            
        PGPOnePassSignatureList     p1 = (PGPOnePassSignatureList)pgpFact.nextObject();
            
        PGPOnePassSignature         ops = p1.get(0);
            
        PGPLiteralData              p2 = (PGPLiteralData)pgpFact.nextObject();

        InputStream                 dIn = p2.getInputStream();
        int                         ch;
        PGPPublicKeyRingCollection  pgpRing = new PGPPublicKeyRingCollection(PGPUtil.getDecoderStream(keyIn));

        PGPPublicKey                key = pgpRing.getPublicKey(ops.getKeyID());
        FileOutputStream            out = new FileOutputStream(p2.getFileName());

        ops.init(new JcaPGPContentVerifierBuilderProvider().setProvider("BC"), key);
            
        while ((ch = dIn.read()) >= 0)
        {
            ops.update((byte)ch);
            out.write(ch);
        }

        out.close();
        
        PGPSignatureList            p3 = (PGPSignatureList)pgpFact.nextObject();

        if (ops.verify(p3.get(0)))
        {
            System.out.println("signature verified.");
        }
        else
        {
            System.out.println("signature verification failed.");
        }
    }

    /**
     * Generate an encapsulated signed file.
     * 
     * @param fileName
     * @param keyIn
     * @param out
     * @param pass
     * @param armor
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws PGPException
     * @throws SignatureException
     */
    private static void signFile(
        String          fileName,
        InputStream     keyIn,
        OutputStream    out,
        char[]          pass,
        boolean         armor)
        throws IOException, NoSuchAlgorithmException, NoSuchProviderException, PGPException, SignatureException
    {
        if (armor)
        {
            out = new ArmoredOutputStream(out);
        }

        PGPSecretKey                pgpSec = PGPExampleUtil.readSecretKey(keyIn);
        PGPPrivateKey               pgpPrivKey = pgpSec.extractPrivateKey(new JcePBESecretKeyDecryptorBuilder().setProvider("BC").build(pass));
        PGPSignatureGenerator       sGen = new PGPSignatureGenerator(new JcaPGPContentSignerBuilder(pgpSec.getPublicKey().getAlgorithm(), PGPUtil.SHA1).setProvider("BC"));
        
        sGen.init(PGPSignature.BINARY_DOCUMENT, pgpPrivKey);
        
        Iterator    it = pgpSec.getPublicKey().getUserIDs();
        if (it.hasNext())
        {
            PGPSignatureSubpacketGenerator  spGen = new PGPSignatureSubpacketGenerator();
            
            spGen.setSignerUserID(false, (String)it.next());
            sGen.setHashedSubpackets(spGen.generate());
        }
        
        PGPCompressedDataGenerator  cGen = new PGPCompressedDataGenerator(
                                                                PGPCompressedData.ZLIB);
        
        BCPGOutputStream            bOut = new BCPGOutputStream(cGen.open(out));
        
        sGen.generateOnePassVersion(false).encode(bOut);
        
        File                        file = new File(fileName);
        PGPLiteralDataGenerator     lGen = new PGPLiteralDataGenerator();
        OutputStream                lOut = lGen.open(bOut, PGPLiteralData.BINARY, file);
        FileInputStream             fIn = new FileInputStream(file);
        int                         ch;
        
        while ((ch = fIn.read()) >= 0)
        {
            lOut.write(ch);
            sGen.update((byte)ch);
        }

        lGen.close();

        sGen.generate().encode(bOut);

        cGen.close();

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
                FileInputStream     keyIn = new FileInputStream(args[3]);
                FileOutputStream    out = new FileOutputStream(args[2] + ".asc");
                
                signFile(args[2], keyIn, out, args[4].toCharArray(), true);
            }
            else
            {
                FileInputStream     keyIn = new FileInputStream(args[2]);
                FileOutputStream    out = new FileOutputStream(args[1] + ".bpg");
                
                signFile(args[1], keyIn, out, args[3].toCharArray(), false);
            }
        }
        else if (args[0].equals("-v"))
        {
            FileInputStream    in = new FileInputStream(args[1]);
            FileInputStream    keyIn = new FileInputStream(args[2]);
            
            verifyFile(in, keyIn);
        }
        else
        {
            System.err.println("usage: SignedFileProcessor -v|-s [-a] file keyfile [passPhrase]");
        }
    }
}