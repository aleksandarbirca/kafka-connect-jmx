package org.levi9.kafka.connect.jmx.source;

import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class JmxSourceTask extends SourceTask {

	public static final Logger LOG = LoggerFactory.getLogger(JmxSourceTask.class);

	private JmxSourceConfig config;
	private JmxSourceConnector connector;

	public String version() {
		return getClass().getPackage().getImplementationVersion();
	}

	public void start(Map<String, String> map) {
		LOG.info("Starting task");
		this.config = new JmxSourceConfig(map);
		this.connector =  new JmxSourceConnector(this.config);
	}

	public List<SourceRecord> poll() throws InterruptedException {
		return null;
	}

	public void stop() {
		LOG.info("Stopping task");
		try {
			this.connector.stop();
		} catch (Exception e) {
			LOG.error("Task shutdown failed", e);
		}
	}
}
