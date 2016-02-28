package org.weibei.bouncycastle.openpgp.operator;

import org.weibei.bouncycastle.bcpg.PublicKeyPacket;
import org.weibei.bouncycastle.openpgp.PGPException;

public interface KeyFingerPrintCalculator
{
    byte[] calculateFingerprint(PublicKeyPacket publicPk)
        throws PGPException;
}
