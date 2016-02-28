package com.weibei.config;

import com.weibei.encodings.B58IdentiferCodecs;
import com.weibei.encodings.base58.B58;
import org.weibei.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

// Somewhat of a global registry, dependency injection ala guice would be nicer, but trying to KISS
public class Config {
    public  static final String DEFAULT_ALPHABET = "rpshnaf39wBUDNEGHJKLM4PQRST7VWXYZ2bcdeCg65jkm8oFqi1tuvAxyz";

    private static B58IdentiferCodecs b58IdentiferCodecs;
    private static double feeCushion;

    public static void setAlphabet(String alphabet) {
        B58 b58 = new B58(alphabet);
        b58IdentiferCodecs = new B58IdentiferCodecs(b58);
    }

    /**
     * @return the configured B58IdentiferCodecs object
     */
    public static B58IdentiferCodecs getB58IdentiferCodecs() {
        return b58IdentiferCodecs;
    }

    /**
     * TODO, this is gross
     */
    static public boolean bouncyInitiated = false;
    static public void initBouncy() {
        if (!bouncyInitiated) {
            Security.addProvider(new BouncyCastleProvider());
            bouncyInitiated = true;
        }
    }
    /***
     * We set up all the defaults here
     */
    static {
        setAlphabet(DEFAULT_ALPHABET);
        setFeeCushion(1.1);
        initBouncy();
    }

    public static double getFeeCushion() {
        return feeCushion;
    }

    public static void setFeeCushion(double fee_cushion) {
        feeCushion = fee_cushion;
    }
}
