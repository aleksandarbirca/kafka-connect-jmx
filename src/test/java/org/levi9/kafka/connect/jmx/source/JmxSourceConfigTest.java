package org.levi9.kafka.connect.jmx.source;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class JmxSourceConfigTest {

    private Map<String, String> properties;

    @Before
    public void setUp() throws Exception {
        this.properties = new HashMap<>();
        this.properties.put(JmxSourceConfig.JMX_OBJECTS, "kafka.server:type=ReplicaManager," +
                "name=UnderReplicatedPartitions;kafka.controller:type=KafkaController,name=*");
    }


    @Test
    public void testDefaultConfigs() {
        JmxSourceConfig config = new JmxSourceConfig(this.properties);
        Assert.assertEquals(config.getTopic(), JmxSourceConfig.KAFKA_TOPIC_DEFAULT);
        Assert.assertEquals(config.getHost(), JmxSourceConfig.JMX_HOST_DEFAULT);
        Assert.assertEquals(config.getPort(), JmxSourceConfig.JMX_PORT_DEFAULT);
        Assert.assertEquals(config.getPollingFrequencyMs(), JmxSourceConfig.JMX_POLLING_FREQUENCY_MS_DEFAULT);
        Assert.assertEquals(config.getUsername(), null);
    }

    @Test
    public void testObjectNames() {
        JmxSourceConfig config = new JmxSourceConfig(this.properties);
        Assert.assertEquals(config.getObjectNames().get(0), "kafka.server:type=ReplicaManager," +
                "name=UnderReplicatedPartitions");
        Assert.assertEquals(config.getObjectNames().get(1), "kafka.controller:type=KafkaController,name=*");
    }


}