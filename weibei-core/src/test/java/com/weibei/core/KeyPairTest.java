package com.weibei.core;

import com.weibei.crypto.ecdsa.IKeyPair;
import com.weibei.crypto.ecdsa.KeyPair;
import com.weibei.crypto.ecdsa.Seed;
import com.weibei.encodings.common.B16;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;
import org.junit.Test;
import org.weibei.bouncycastle.util.encoders.Hex;

import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class KeyPairTest {
    IKeyPair keyPair = Seed.createKeyPair(TestFixtures.master_seed_bytes, 0);

    @Test
    public void testVerify() {
        assertTrue(keyPair.verify(TestFixtures.master_seed_bytes,
                Hex.decode(TestFixtures.singed_master_seed_bytes)));
    }

    @Test
    public void sanityTestSignAndVerify() {
        assertTrue(keyPair.verify(TestFixtures.master_seed_bytes,
                keyPair.sign(TestFixtures.master_seed_bytes)));
    }

    @Test
    public void testDerivationFromSeedBytes() {
        assertEquals("0330E7FC9D56BB25D6893BA3F317AE5BCF33B3291BD63DB32654A313222F7FD020", keyPair.pubHex());
        assertEquals("1ACAAEDECE405B2A958212629E16F2EB46B153EEE94CDD350FDEFF52795525B7", keyPair.privHex());
    }

    @Test
    public void testDerivationFromString() {
        IKeyPair keyPairFromSeed = Seed.getKeyPair(TestFixtures.master_seed);
        assertEquals("0330E7FC9D56BB25D6893BA3F317AE5BCF33B3291BD63DB32654A313222F7FD020", keyPairFromSeed.pubHex());
        assertEquals("1ACAAEDECE405B2A958212629E16F2EB46B153EEE94CDD350FDEFF52795525B7", keyPairFromSeed.privHex());
    }

    static public JSONArray getJSONArray(String filename) throws IOException, JSONException {
        // should close file ...
        FileReader f = new FileReader(filename);
        return new JSONArray(new JSONTokener(f));
    }

//    @Test
    public void testWeibeiLibGarbage() throws Exception {
        JSONArray array = getJSONArray("/home/nick/weibei-lib/dumps.json");

        IKeyPair kp = Seed.getKeyPair(Seed.passPhraseToSeedBytes("root"));
        byte[] zeros = new byte[32];

        for (int i = 0; i < array.length(); i++) {
            String sig = array.getString(i);
            byte[] sigBytes = B16.decode(sig);
            assertTrue(KeyPair.isStrictlyCanonical(sigBytes));
            assertTrue(kp.verify(zeros, sigBytes));
        }
    }
}
