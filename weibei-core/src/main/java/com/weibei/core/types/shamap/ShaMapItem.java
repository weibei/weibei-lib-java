package com.weibei.core.types.shamap;

import com.weibei.core.coretypes.hash.prefixes.Prefix;
import com.weibei.core.serialized.BytesSink;

abstract public class ShaMapItem<T> {
    abstract void toBytesSink(BytesSink sink);
    public abstract ShaMapItem<T> copy();
    public abstract T value();
    public abstract Prefix hashPrefix();
}
