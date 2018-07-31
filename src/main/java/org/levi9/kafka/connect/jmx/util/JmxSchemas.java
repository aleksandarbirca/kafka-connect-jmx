package org.levi9.kafka.connect.jmx.util;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;

public class JmxSchemas {

	public static final String SCHEMA_KEY = "org.levi9.kafka.connect.jmx.JmxKey";
	public static final String URL_FIELD = "url";
	public static final String TIMESTAMP_FIELD = "timestamp";

	public static final Schema KEY_SCHEMA = SchemaBuilder.struct().name(SCHEMA_KEY)
			.version(1)
			.field(URL_FIELD, Schema.STRING_SCHEMA)
			.field(TIMESTAMP_FIELD, Schema.STRING_SCHEMA)
			.build();



}
