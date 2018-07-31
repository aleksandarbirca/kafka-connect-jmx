package org.levi9.kafka.connect.jmx.source;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.levi9.kafka.connect.jmx.util.JmxSchemas;
import org.levi9.kafka.connect.jmx.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JmxSourceTask extends SourceTask {
	public static final Logger LOG = LoggerFactory.getLogger(JmxSourceTask.class);

	public static final String RMI_PREFIX = "service:jmx:rmi:///jndi/rmi://";
	public static final String RMI_SUFFIX = "/jmxrmi";

	private JMXConnector jmxConnector;
	private JMXServiceURL jmxServiceURL;
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
			this.jmxServiceURL = new JMXServiceURL(
					RMI_PREFIX + this.config.getHost() + ":" + this.config.getPort() + RMI_SUFFIX);
		} catch (MalformedURLException e) {
			LOG.error("Invalid JMX URL", e);
		}

		populateCredentials();
		try {
			this.jmxConnector = JMXConnectorFactory.connect(jmxServiceURL, credentials);
		} catch (IOException e) {
			LOG.error("Connecting to JMX host failed.");
		}
	}

	public List<SourceRecord> poll() throws InterruptedException {
		LOG.info("Polling for JMX metrics.");
		ArrayList<SourceRecord> sourceRecords = new ArrayList<>();

		// TODO: generate source records from JMX

		return sourceRecords;
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
	private SourceRecord generateSourceRecord(String jmx) {
		// TODO: add value schema, partition, offset...
		return new SourceRecord(
				null,
				null,
				this.config.getTopic(),
				null, // inferred by the framework
				JmxSchemas.KEY_SCHEMA,
				0,
				Schema.STRING_SCHEMA,
				jmx,
				Instant.now().toEpochMilli());
	}
	//  @formatter:on

	private void populateCredentials() {
		if (this.config.getUsername() != null || this.config.getPassword() != null) {
			this.credentials = new HashMap<>();
			this.credentials.put(JMXConnector.CREDENTIALS,
					new String[] { this.config.getUsername(), this.config.getPassword() });
		}
	}

}
