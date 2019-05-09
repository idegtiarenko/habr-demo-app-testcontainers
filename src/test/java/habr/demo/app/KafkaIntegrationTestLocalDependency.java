package habr.demo.app;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;

public class KafkaIntegrationTestLocalDependency implements IntegrationTestLocalDependency {

    @Override
    public String name() {
        return "kafka";
    }

    @Override
    public GenericContainer containerDefinition() {
        return new KafkaContainer("4.1.2");
    }

    @Override
    public void initializeSystemProperties(GenericContainer it) {
        System.setProperty("kafka.topics.events", "events");
        System.setProperty("spring.kafka.bootstrapServers", ((KafkaContainer) it).getBootstrapServers());
    }
}
