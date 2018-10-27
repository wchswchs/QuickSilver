# QuickSilver
本地高速快照系统

该系统可用于本地快速持久化结构化数据。单个线程每毫秒50K对象可写入8500次。在高并发时，写入性能更高。
适用于对写入性能要求较高的应用场景。

## 特性

1. 高速持久化
2. 自动实现快照分割

### Getting Started

#### 不使用Spring Boot
```java
public void writeSnapshot() {
    SnapshotHeader header = new SnapshotHeader("/data/hulk", "snapshot.test.log");
    SnapshotRule rule = new IncrementShardingRule(new Quota(10 * 1024, 1000));
    Snapshot snapshot = new Snapshot(header, rule);
    snapshot.write(new OrderEntry("12345"));
}
```

#### 使用Spring Boot
```java
@Component
public class SnapshotForSpringBoot {
    
    @Autowired
    private Snapshot snapshot;

    public void writeSnapshotForSpringBoot() {
        snapshot.getHeader().setFileName("snapshot.test.log");
        snapshot.write(new OrderEntry("12345"));
    }
    
}
```

### 工程属性配置

配置属性说明：

* mtl.snapshot.dir：该属性指定本地快照存储目录，默认：/data/hulk

* mtl.snapshot.rule：该属性设置本地快照分割策略，默认：increment，自然数自增分割

* mtl.snapshot.bufferSize：该属性指定读取/写入缓冲区大小，单位Byte，默认：20K

* mtl.snapshot.perFileSize：该属性设置单个快照存储记录数，默认：1000
