package com.weibei.core.types.ledger;

import com.weibei.core.binary.STReader;
import com.weibei.core.coretypes.hash.prefixes.HashPrefix;
import com.weibei.core.serialized.BinaryParser;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class LedgerTest {
    @Test
    /**
     * ➜  weibeid git:(develop) ✗ ~/rocksdb/ldb --hex --db=/mnt/weibeid/db/rocksdb get 0x498ef6300a8693c98be8345037843a95216fb254b672af679c8ef1c85d213972
     .... {some long hex string} prefixed with 0x...

     */
    public void testInnerNodeParsing() throws Exception {
        String hex;
        // This is the format as returned by ldb, with an `0x` prefix
        hex = "0x0043324C0043324C034D494E007F41BDF4734EFA5E5C9093DCB5BC39E7ABB9F9A9C8B8010A35439BFCF54E6904056B160D859FB688D917C5D61DF10A9E95F39FAE11BB7D1E97EE99BD337890F93850B8D2BF3AE0C87A754ACFCE94D73762AC89141B60F331184C2E994079AC1042C032F079CF51B16ACD5CC353DDDFEEA96ABCB19D69CC52AA21A3B62D5EBA144418A512DDAFA24B3683878B84609B998EBBE0D325F45D15646C27B3F0EF625DB569F872A802D77D644130364B7E91D95297EBC093DC2FFFB888FBF39677184EF73DA334C05972CA5CCC78C6355C809060888113EBD71DECA18D5A99608531A5F8887D8E7D5BE3B7930F2D7E516E0BA9BC58E94DC4C5B1674B94C07A0BFDED0DF8CA11FC61D7E3B0F77B82A9F4E5FB4AF5D08B011195F83DB82FA79234B6BD598728AFB1899B4FDD6223716111EA1C1E26992B39ADD2F3076280795A90505BCE15B9C381E9F21789EE0BFC47A835C31014B2D9538D3CDF76F711736B6296B908D57E010521F28C0443920C83457FACA9E1C748A1B2191D8F4530129ABEA5CDC42285EB7556306B668CD05E3B50AC6AA9783C8CFBBDBF16C7C31EDBB135AC9572CF0FB4501DF94B240D917D4C2435A195297E0EE6E6DCF4A38B0C25C714A2DC04C374C28628CCF95DF02078B4DED6C2B1509D5B542D9A36A99E042D108F42989FB17506B199241513FF2D374B02E6B8C95DD5B533D1C5ED0E526010D6EA523CA6";
        hex = hex.substring(2);

        BinaryParser parser = new BinaryParser(hex);
        STReader reader = new STReader(parser);

        assertEquals(4403788, reader.uInt32().longValue()); //
        assertEquals(4403788, reader.uInt32().longValue()); //
        assertEquals(3, parser.readOne()); //

        assertEquals(HashPrefix.innerNode.uInt32, reader.uInt32());
        assertEquals(16, reader.vector256().size());
        assertTrue(parser.end());
    }
}
