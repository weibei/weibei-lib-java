package com.weibei.core.coretypes.hash.prefixes;

import com.weibei.core.coretypes.AccountID;
import com.weibei.core.coretypes.Currency;
import com.weibei.core.coretypes.STObject;
import com.weibei.core.coretypes.hash.Hash256;
import com.weibei.core.coretypes.hash.Index;
import com.weibei.core.types.known.sle.entries.WeibeiState;
import junit.framework.TestCase;

public class LedgerSpaceTest extends TestCase {
    public void testWeibeiStateIndexCreation() throws Exception {
        String testy = "{" +
                "    \"Balance\": {" +
                "        \"currency\": \"CNY\"," +
                "        \"issuer\": \"rrrrrrrrrrrrrrrrrrrrBZbvji\"," +
                "        \"value\": \"-31.2365570758485\"" +
                "    }," +
                "    \"Flags\": 2228224," +
                "    \"HighLimit\": {" +
                "        \"currency\": \"CNY\"," +
                "        \"issuer\": \"rHpoggSkNY7puahMUGVafWPZQ5JH8piZVQ\"," +
                "        \"value\": \"200000\"" +
                "    }," +
                "    \"HighNode\": \"0000000000000000\"," +
                "    \"LedgerEntryType\": \"WeibeiState\"," +
                "    \"LowLimit\": {" +
                "        \"currency\": \"CNY\"," +
                "        \"issuer\": \"razqQKzJRdB4UxFPWf5NEpEG3WMkmwgcXA\"," +
                "        \"value\": \"0\"" +
                "    }," +
                "    \"LowNode\": \"00000000000000AA\"," +
                "    \"PreviousTxnID\": \"7B4EE05D265ABECAAF9D7EA65BEE6943571F03A77D1CD50AE01192F944C341ED\"," +
                "    \"PreviousTxnLgrSeq\": 6226713," +
                "    \"index\": \"D043B6B526F5B9FBC7C2DE1BC2D59291A0C59CB7906153CF0E7DC2F6C80D00C8\"" +
                "},";

        WeibeiState rs = (WeibeiState) STObject.fromJSON(testy);
        Hash256 expected = rs.index();
        AccountID a1 = rs.highAccount();
        AccountID a2 = rs.lowAccount();
        Currency currency = rs.currency();

        Hash256 rebuilt12 = Index.weibeiState(a1, a2, currency);
        Hash256 rebuilt21 = Index.weibeiState(a2, a1, currency);

        assertEquals(expected, rebuilt12);
        assertEquals(expected, rebuilt21);

    }

}