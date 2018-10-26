package com.mtl.snapshot.bench.snapshot;

import com.mtl.snapshot.Snapshot;
import com.mtl.snapshot.bench.AbstractBenchmark;
import com.mtl.snapshot.SnapshotHeader;
import com.mtl.snapshot.SnapshotRule;
import com.mtl.snapshot.bench.model.OrderEntry;
import com.mtl.snapshot.rule.IncrementShardingRule;
import com.mtl.snapshot.rule.Quota;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@Fork(2)
@State(Scope.Benchmark)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@OutputTimeUnit(TimeUnit.SECONDS)
public class SnapshotBenchmark extends AbstractBenchmark {

    @Benchmark
    @BenchmarkMode(Mode.All)
    public void writeSnapshot() {
        SnapshotHeader header = new SnapshotHeader("/data/hulk", "snapshot.test.log");
        SnapshotRule rule = new IncrementShardingRule(new Quota(10 * 1024, 1000));
        Snapshot snapshot = new Snapshot(header, rule);
        snapshot.write(new OrderEntry("12345"));
    }

}
