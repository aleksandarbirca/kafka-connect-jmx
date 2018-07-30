package org.levi9.kafka.connect.jmx.source;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

public class JmxSourceConfig extends AbstractConfig {

	// TODO: Add other parameters
	public static final String JMX_HOST = "jmx.host";
	public static final String JMX_HOST_DOC = "";
	public static final String JMX_HOST_DEFAULT = "localhost";

	public static final String JMX_PORT = "jmx.port";
	public static final String JMX_PORT_DOC = "";
	public static final String JMX_PORT_DEFAULT = "9999";

	public static final String JMX_POLL_INTERVAL_MS = "jmx.poll.interval.ms";
	public static final String JMX_POLL_INTERVAL_MS_DOC = "";
	public static final Long JMX_POLL_INTERVAL_MS_DEFAULT = 1000L;


	private String host;
	private String port;
	private Long pollInterval;

	public JmxSourceConfig(ConfigDef definition, Map<?, ?> originals, boolean doLog) {
		super(definition, originals, doLog);
	}

	public static final ConfigDef CONFIG_DEF = new ConfigDef()
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
			.define(JMX_POLL_INTERVAL_MS,
					ConfigDef.Type.LONG,
					JMX_POLL_INTERVAL_MS_DEFAULT,
					ConfigDef.Importance.HIGH,
					JMX_POLL_INTERVAL_MS_DOC);


	public JmxSourceConfig(Map<?, ?> properties) {
		super(CONFIG_DEF, properties);
		this.host = getString(JMX_HOST);
		this.port = getString(JMX_PORT);
		this.pollInterval = getLong(JMX_POLL_INTERVAL_MS);
	}

}
