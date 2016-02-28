package com.weibei.client.payments;

import com.weibei.core.coretypes.Amount;
import com.weibei.core.coretypes.PathSet;
import com.weibei.core.coretypes.hash.HalfSha512;
import com.weibei.core.coretypes.hash.Hash256;
import org.json.JSONObject;

public class Alternative implements Comparable<Alternative> {

    public Amount sourceAmount;
    public PathSet paths;
    public Hash256 hash;

    public Alternative(PathSet paths, Amount sourceAmount) {
        this.paths = paths;
        this.sourceAmount = sourceAmount;
        this.hash = calculateHash(paths, sourceAmount);
    }

    private Hash256 calculateHash(PathSet paths, Amount sourceAmount) {
        HalfSha512 half = new HalfSha512();
        half.update(paths.toBytes());
        half.update(sourceAmount.toBytes());
        return half.finish();
    }

    @Override
    public int compareTo(Alternative another) {
        return hash.compareTo(another.hash);
    }

    public boolean directXRP() {
        return !hasPaths() && sourceAmount.isNative();
    }

    boolean hasPaths() {
        return paths.size() > 0;
    }

    @Override
    public String toString() {
        JSONObject o = toJSON();
        return o.toString(2);
    }

    public JSONObject toJSON() {
        JSONObject o = new JSONObject();
        o.put("source_amount", sourceAmount.toJSON());
        o.put("paths", paths.toJSON());
        return o;
    }
}
