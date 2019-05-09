package habr.demo.app;

import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.containers.GenericContainer;

public class CassandraIntegrationTestLocalDependency implements IntegrationTestLocalDependency {

    @Override
    public String name() {
        return "cassandra";
    }

    @Override
    public GenericContainer containerDefinition() {
        return new CassandraContainer("cassandra:3.11.4")
                .withJmxReporting(false)
                .withInitScript("cassandra-schema.cql")
                .withEnv("MAX_HEAP_SIZE", "512M")
                .withEnv("HEAP_NEWSIZE", "512M");
    }

    @Override
    public void initializeSystemProperties(GenericContainer it) {
        System.setProperty("cassandra.host", it.getContainerIpAddress());
        System.setProperty("cassandra.port", Integer.toString(it.getMappedPort(9042)));
        System.setProperty("cassandra.keyspace", "test");
    }
}
