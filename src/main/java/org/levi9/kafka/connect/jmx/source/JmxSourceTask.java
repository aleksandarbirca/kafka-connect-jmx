package org.levi9.kafka.connect.jmx.source;

import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.levi9.kafka.connect.jmx.model.JmxMetric;
import org.levi9.kafka.connect.jmx.util.JmxSchemas;
import org.levi9.kafka.connect.jmx.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Instant;
import java.util.*;

import static org.levi9.kafka.connect.jmx.util.JmxSchemas.*;

public class JmxSourceTask extends SourceTask {
    public static final Logger LOG = LoggerFactory.getLogger(JmxSourceTask.class);

    public static final String RMI_PREFIX = "service:jmx:rmi:///jndi/rmi://";
    public static final String RMI_SUFFIX = "/jmxrmi";

    private JMXConnector jmxConnector;
    private JMXServiceURL jmxServiceURL;
    private MBeanServerConnection mbeanServerConnection;
    private Map<String, Object> credentials;
    private JmxSourceConfig config;

    public java.lang.String version() {
        return Version.getVersion();
    }

    @Override
    public void start(Map<java.lang.String, java.lang.String> props) {
        LOG.info("Starting task");
        this.config = new JmxSourceConfig(props);

        try {
            this.jmxServiceURL =
                    new JMXServiceURL(
                            RMI_PREFIX + this.config.getHost() + ":" + this.config.getPort() + RMI_SUFFIX);
        } catch (MalformedURLException e) {
            LOG.error("Invalid JMX URL", e);
        }

        populateCredentials();
        try {
            this.jmxConnector = JMXConnectorFactory.connect(jmxServiceURL, credentials);
            mbeanServerConnection = jmxConnector.getMBeanServerConnection();
        } catch (IOException e) {
            LOG.error("Connecting to JMX host failed.");
        }

    }

    public List<SourceRecord> poll() throws InterruptedException {
        LOG.info("Polling for JMX metrics.");
        ArrayList<SourceRecord> sourceRecords = new ArrayList<>();

        // TODO: error handling and recovery, reconnecting to host, retry backoff...
        try {
            for (String object : this.config.getObjectNames()) {
                ObjectName queryFilter = new ObjectName(object);
                Set<ObjectInstance> objectInstances =
                        mbeanServerConnection.queryMBeans(queryFilter, null);

                for (ObjectInstance objectInstance : objectInstances) {
                    ObjectName objectName = objectInstance.getObjectName();
                    MBeanInfo mBeanInfo = mbeanServerConnection.getMBeanInfo(objectName);
                    MBeanAttributeInfo[] mBeanAttributeInfos = mBeanInfo.getAttributes();
                    for (MBeanAttributeInfo info : mBeanAttributeInfos) {
                        Object value = mbeanServerConnection.getAttribute(objectName, info.getName());
                        sourceRecords.add(generateSourceRecord(new JmxMetric(objectName.getCanonicalName(),
                                value.toString(), Instant.now())));
                    }
                }
            }
            Thread.sleep(this.config.getPollingFrequencyMs());
            return sourceRecords;
        } catch (Exception e) {
            LOG.error("Error while polling for JMX metrics", e);
        }

        return null;
    }

    public void stop() {
        LOG.info("Stopping task");
        try {
            this.jmxConnector.close();
        } catch (Exception e) {
            LOG.error("Task shutdown failed", e);
        }
    }

    //	@formatter:off
    // TODO: offset, key
    private SourceRecord generateSourceRecord(JmxMetric jmxMetric) {
        return new SourceRecord(
                sourcePartition(),
                null,
                this.config.getTopic(),
                null, // inferred by the framework
                JmxSchemas.KEY_SCHEMA,
                buildRecordKey(jmxMetric),
                JmxSchemas.VALUE_SCHEMA,
                buildRecordValue(jmxMetric),
                Instant.now().toEpochMilli());
    }
    //  @formatter:on

    private void populateCredentials() {
        if (this.config.getUsername() != null || this.config.getPassword() != null) {
            this.credentials = new HashMap<>();
            this.credentials.put(
                    JMXConnector.CREDENTIALS,
                    new String[]{this.config.getUsername(), this.config.getPassword()});
        }
    }

    private Map<String, String> sourcePartition() {
        Map<String, String> map = new HashMap<>();
        map.put(HOST, config.getHost());
        map.put(PORT, config.getPort());
        return map;
    }

    private Struct buildRecordKey(JmxMetric metric) {
        Struct key = new Struct(KEY_SCHEMA)
                .put(HOST, config.getHost())
                .put(PORT, config.getPort());
        return key;
    }

    private Struct buildRecordValue(JmxMetric metric) {
        Struct value = new Struct(VALUE_SCHEMA)
                .put(HOST, config.getHost())
                .put(PORT, config.getPort())
                .put(METRIC_NAME, metric.getMetricName())
                .put(METRIC_VALUE, metric.getMetricValue())
                .put(UPDATED_AT, Date.from(metric.getUpdatedAt()));
        return value;
    }

}
