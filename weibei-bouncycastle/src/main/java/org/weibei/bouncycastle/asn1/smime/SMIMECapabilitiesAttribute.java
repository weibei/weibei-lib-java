package org.weibei.bouncycastle.asn1.smime;

import org.weibei.bouncycastle.asn1.DERSequence;
import org.weibei.bouncycastle.asn1.DERSet;
import org.weibei.bouncycastle.asn1.cms.Attribute;

public class SMIMECapabilitiesAttribute
    extends Attribute
{
    public SMIMECapabilitiesAttribute(
        SMIMECapabilityVector capabilities)
    {
        super(SMIMEAttributes.smimeCapabilities,
                new DERSet(new DERSequence(capabilities.toASN1EncodableVector())));
    }
}
