package com.mtl.snapshot;

import com.mtl.snapshot.rule.Quota;

import java.io.File;

public abstract class SnapshotRule {

    protected Quota quota;

    public SnapshotRule() {
    }

    public SnapshotRule(Quota quota) {
        this.quota = quota;
    }

    public void setQuota(Quota quota) {
        this.quota = quota;
    }

    public Quota getQuota() {
        return quota;
    }

    public abstract File run(SnapshotHeader header);

}
