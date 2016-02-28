package com.weibei.core.coretypes;

import com.weibei.core.coretypes.uint.UInt32;
import com.weibei.core.serialized.BinaryParser;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

//public class WeibeiDate extends Date implements SerializedType {
public class WeibeiDate extends Date {
    public static long RIPPLE_EPOCH_SECONDS_OFFSET = 0x386D4380;
    static {
        /**
         * Magic constant tested and documented.
         *
         * Seconds since the unix epoch from unix time (accounting leap years etc)
         * at 1/January/2000 GMT
         */
        GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        cal.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
        long computed = cal.getTimeInMillis() / 1000;
        assertEquals("1 Jan 2000 00:00:00 GMT", cal.getTime().toGMTString()); // TODO
        assertEquals(WeibeiDate.RIPPLE_EPOCH_SECONDS_OFFSET, computed);
    }

    private static void assertEquals(String s, String s1) {
        if (!s.equals(s1)) throw new AssertionError(String.format("%s != %s", s, s1));
    }
    private static void assertEquals(long a, long b) {
        if (a != b) throw new AssertionError(String.format("%s != %s", a, b));
    }

    private WeibeiDate() {
        super();
    }
    private WeibeiDate(long milliseconds) {
        super(milliseconds);
    }

    public long secondsSinceWeibeiEpoch() {
        return ((this.getTime() / 1000) - RIPPLE_EPOCH_SECONDS_OFFSET);
    }
    public static WeibeiDate fromSecondsSinceWeibeiEpoch(Number seconds) {
        return new WeibeiDate((seconds.longValue() + RIPPLE_EPOCH_SECONDS_OFFSET) * 1000);
    }
    public static WeibeiDate fromParser(BinaryParser parser) {
        UInt32 uInt32 = UInt32.translate.fromParser(parser);
        return fromSecondsSinceWeibeiEpoch(uInt32);
    }
    public static WeibeiDate now() {
        return new WeibeiDate();
    }

/*    @Override
    public Object toJSON() {
        return secondsSinceWeibeiEpoch();
    }

    @Override
    public byte[] toBytes() {
        return new UInt32(secondsSinceWeibeiEpoch()).toBytes();
    }

    @Override
    public String toHex() {
        return new UInt32(secondsSinceWeibeiEpoch()).toHex();
    }

    @Override
    public void toBytesSink(BytesSink to) {
        new UInt32(secondsSinceWeibeiEpoch()).toBytesSink(to);
    }*/
}
