package com.mtl.snapshot.bench.snapshot;

import com.mtl.snapshot.bench.AbstractBenchmark;
import com.mtl.snapshot.bench.model.OrderEntry;
import com.mtl.hulk.serializer.kryo.KryoSerializer;
import com.mtl.snapshot.SnapshotHeader;
import com.mtl.snapshot.SnapshotRule;
import com.mtl.snapshot.io.FastFile;
import com.mtl.snapshot.rule.IncrementShardingRule;
import com.mtl.snapshot.rule.Quota;
import org.openjdk.jmh.annotations.*;

import java.io.File;
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
        File file = rule.run(header);
        FastFile ff = new FastFile(file, "rw", rule.getQuota().getBufferSize());
        KryoSerializer serializer = new KryoSerializer();
        byte[] data = serializer.serialize(new OrderEntry("123456"));
        ff.write(data);
        ff.close();
    }

}
