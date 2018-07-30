package org.levi9.kafka.connect.jmx.source;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;
import org.levi9.kafka.connect.jmx.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class JmxSourceConnector extends SourceConnector {

	public static final Logger LOG = LoggerFactory.getLogger(JmxSourceConnector.class);

	private JmxSourceConfig config;

	public JmxSourceConnector(JmxSourceConfig config) {
		this.config = config;
	}

	public String version() {
		return Version.getVersion();
	}

	public void start(Map<String, String> map) {

	}

	public Class<? extends Task> taskClass() {
		return JmxSourceTask.class;
	}

	public List<Map<String, String>> taskConfigs(int i) {
		return null;
	}

	public void stop() {

	}

	public ConfigDef config() {
		return null;
	}
}