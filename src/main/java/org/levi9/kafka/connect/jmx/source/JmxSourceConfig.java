package org.levi9.kafka.connect.jmx.source;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class JmxSourceConfig extends AbstractConfig {

	public static final String KAFKA_TOPIC = "kafka.topic";
	public static final String KAFKA_TOPIC_DOC = "Kafka topic where JMX metrics will be ingested";
	public static final String KAFKA_TOPIC_DEFAULT = "jmx-metrics";

	public static final String JMX_HOST = "jmx.host";
	public static final String JMX_HOST_DOC = "JMX listening host/ip";
	public static final String JMX_HOST_DEFAULT = "localhost";

	public static final String JMX_PORT = "jmx.port";
	public static final String JMX_PORT_DOC = "JMX listening port";
	public static final String JMX_PORT_DEFAULT = "9999";

	public static final String USERNAME = "jmx.username";
	public static final String USERNAME_DOC = "The username to connect to JMX";

	public static final String PASSWORD = "jmx.password";
	public static final String PASSWORD_DOC = "The password to connect to JMX";

	public static final String JMX_OBJECTS = "jmx.objects";
	public static final String JMX_OBJECTS_DOC = "list of JMX metrics to retrieve, for example java.lang:type=Memory";
	public static final String JMX_OBJECTS_DEFAULT = "*";

	public static final String JMX_POLLING_FREQUENCY_MS = "jmx.polling.frequency.ms";
	public static final String JMX_POLL_INTERVAL_MS_DOC = "JMX polling interval.";
	public static final Long JMX_POLL_INTERVAL_MS_DEFAULT = 1000L;

	private static final String DELIMITER = ";";

	private String topic;
	private String host;
	private String port;
	private String username;
	private String password;
	private List<String> objectNames;
	private Long pollingFrequencyMs;

	public JmxSourceConfig(ConfigDef definition, Map<?, ?> originals, boolean doLog) {
		super(definition, originals, doLog);
	}

	public static final ConfigDef CONFIG_DEF = new ConfigDef()
			.define(KAFKA_TOPIC,
					ConfigDef.Type.STRING,
					KAFKA_TOPIC_DEFAULT,
					ConfigDef.Importance.HIGH,
					KAFKA_TOPIC_DOC)
			.define(JMX_HOST,
					ConfigDef.Type.STRING,
					JMX_HOST_DEFAULT,
					ConfigDef.Importance.HIGH,
					JMX_HOST_DOC)
			.define(JMX_PORT,
					ConfigDef.Type.STRING,
					JMX_PORT_DEFAULT,
					ConfigDef.Importance.HIGH,
					JMX_PORT_DOC)
			.define(USERNAME,
					ConfigDef.Type.STRING,
					null,
					ConfigDef.Importance.LOW,
					USERNAME_DOC)
			.define(PASSWORD,
					ConfigDef.Type.STRING,
					null,
					ConfigDef.Importance.LOW,
					PASSWORD_DOC)
			.define(JMX_OBJECTS,
					ConfigDef.Type.STRING,
					JMX_OBJECTS_DEFAULT,
					ConfigDef.Importance.HIGH,
					JMX_OBJECTS_DOC)
			.define(JMX_POLLING_FREQUENCY_MS,
					ConfigDef.Type.LONG,
					JMX_POLL_INTERVAL_MS_DEFAULT,
					ConfigDef.Importance.MEDIUM,
					JMX_POLL_INTERVAL_MS_DOC);


	public JmxSourceConfig(Map<?, ?> properties) {
		super(CONFIG_DEF, properties);
		this.topic = getString(KAFKA_TOPIC);
		this.host = getString(JMX_HOST);
		this.port = getString(JMX_PORT);
		this.username = getString(USERNAME);
		this.password = getString(PASSWORD);
		this.objectNames = Arrays.asList(getString(JMX_OBJECTS).split(DELIMITER));
		this.pollingFrequencyMs = getLong(JMX_POLLING_FREQUENCY_MS);
	}

	public String getTopic() {
		return topic;
	}

	public String getHost() {
		return host;
	}

	public String getPort() {
		return port;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public List<String> getObjectNames() {
		return objectNames;
	}

	public Long getPollingFrequencyMs() {
		return pollingFrequencyMs;
	}
}
