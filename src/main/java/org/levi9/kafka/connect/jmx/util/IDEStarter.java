package org.levi9.kafka.connect.jmx.util;

import org.apache.kafka.connect.cli.ConnectStandalone;

public class IDEStarter {

	public static void main(String[] args) throws Exception {
		// Use connector and worker property files from resources
		String[] arguments = {
				IDEStarter.class.getResource("/worker.properties").getPath(),
				IDEStarter.class.getResource("/connector.properties").getPath()};
		ConnectStandalone.main(arguments);
	}

}
