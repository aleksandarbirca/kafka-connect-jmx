package org.levi9.kafka.connect.jmx.source;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

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

	public static final String JMX_POLL_INTERVAL_MS = "jmx.poll.interval.ms";
	public static final String JMX_POLL_INTERVAL_MS_DOC = "";
	public static final Long JMX_POLL_INTERVAL_MS_DEFAULT = 1000L;

	private String topic;
	private String host;
	private String port;
	private String username;
	private String password;
	private String objectNames;
	private Long pollInterval;

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
					ConfigDef.Importance.LOW,
					USERNAME_DOC)
			.define(PASSWORD,
					ConfigDef.Type.STRING,
					ConfigDef.Importance.LOW,
					PASSWORD_DOC)
			.define(JMX_OBJECTS,
					ConfigDef.Type.STRING,
					JMX_OBJECTS_DEFAULT,
					ConfigDef.Importance.HIGH,
					JMX_OBJECTS_DOC)
			.define(JMX_POLL_INTERVAL_MS,
					ConfigDef.Type.LONG,
					JMX_POLL_INTERVAL_MS_DEFAULT,
					ConfigDef.Importance.HIGH,
					JMX_POLL_INTERVAL_MS_DOC);


	public JmxSourceConfig(Map<?, ?> properties) {
		super(CONFIG_DEF, properties);
		this.topic = getString(KAFKA_TOPIC);
		this.host = getString(JMX_HOST);
		this.port = getString(JMX_PORT);
		this.username = getString(USERNAME);
		this.password = getString(PASSWORD);
		this.objectNames = getString(JMX_OBJECTS);
		this.pollInterval = getLong(JMX_POLL_INTERVAL_MS);
	}

	public String getTopic() {
		return topic;
	}

	public static String getKafkaTopicDefault() {
		return KAFKA_TOPIC_DEFAULT;
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

	public String getObjectNames() {
		return objectNames;
	}

	public Long getPollInterval() {
		return pollInterval;
	}
}
