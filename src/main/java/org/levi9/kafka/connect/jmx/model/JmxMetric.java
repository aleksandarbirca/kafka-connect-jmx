package org.levi9.kafka.connect.jmx.model;

import java.time.Instant;

public class JmxMetric {

    private String metricName;
    private String metricValue;
    private Instant updatedAt;

    public JmxMetric(String metricName, String metricValue, Instant createdAt) {
        this.metricName = metricName;
        this.metricValue = metricValue;
        this.updatedAt = createdAt;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public String getMetricValue() {
        return metricValue;
    }

    public void setMetricValue(String metricValue) {
        this.metricValue = metricValue;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
