package org.levi9.kafka.connect.jmx.util;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;
import org.apache.kafka.connect.data.Timestamp;

public class JmxSchemas {

	public static final String SCHEMA_KEY = "org.levi9.kafka.connect.jmx.JmxKey";
	public static final String SCHEMA_VALUE = "org.levi9.kafka.connect.jmx.JmxValue";
	public static final String HOST = "host";
	public static final String PORT = "port";
	public static final String METRIC_NAME = "metric_name";
	public static final String METRIC_VALUE = "metric_value";
	public static final String UPDATED_AT = "updated_at";

	// TODO: change key schema
	public static final Schema KEY_SCHEMA = SchemaBuilder.struct().name(SCHEMA_KEY)
			.version(1)
			.field(HOST, Schema.STRING_SCHEMA)
			.field(PORT, Schema.STRING_SCHEMA)
			.build();

	public static final Schema VALUE_SCHEMA = SchemaBuilder.struct().name(SCHEMA_VALUE)
			.version(1)
			.field(HOST, Schema.STRING_SCHEMA)
			.field(PORT, Schema.STRING_SCHEMA)
			.field(METRIC_NAME, Schema.STRING_SCHEMA)
			.field(METRIC_VALUE, Schema.STRING_SCHEMA)
			.field(UPDATED_AT, Timestamp.SCHEMA)
			.build();

}
