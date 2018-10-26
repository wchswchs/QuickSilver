package com.mtl.snapshot;

import com.mtl.hulk.serializer.kryo.KryoSerializer;
import com.mtl.snapshot.io.FastFile;

import java.io.File;

public class Snapshot {

    private SnapshotHeader header;
    private SnapshotRule rule;

    public Snapshot(SnapshotHeader header, SnapshotRule rule) {
        this.header = header;
        this.rule = rule;
    }

    public SnapshotHeader getHeader() {
        return header;
    }

    public void setHeader(SnapshotHeader header) {
        this.header = header;
    }

    public SnapshotRule getRule() {
        return rule;
    }

    public void write(Object data) {
        File file = rule.run(header);
        FastFile ff = new FastFile(file, "rw", rule.getQuota().getBufferSize());
        KryoSerializer serializer = new KryoSerializer();
        ff.write(serializer.serialize(data));
        ff.close();
    }

}
