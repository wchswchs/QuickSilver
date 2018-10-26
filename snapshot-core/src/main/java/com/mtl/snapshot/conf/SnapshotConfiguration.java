package com.mtl.snapshot.conf;

import com.mtl.snapshot.Snapshot;
import com.mtl.snapshot.SnapshotFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SnapshotProperties.class)
public class SnapshotConfiguration {

    private final SnapshotFactory factory;

    public SnapshotConfiguration(SnapshotProperties properties) {
        this.factory = new SnapshotFactory(properties);
    }

    @Bean
    public Snapshot snapshot() {
        SnapshotProperties properties = factory.getProperties();
        return factory.createSnapshot(properties.getBufferSize(), properties.getPerFileSize());
    }

}
