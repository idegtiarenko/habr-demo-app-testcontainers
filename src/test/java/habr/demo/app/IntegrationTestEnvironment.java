package habr.demo.app;

import org.junit.jupiter.api.extension.Extension;
import org.testcontainers.containers.GenericContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class IntegrationTestEnvironment implements Extension {

    private static final Map<String, GenericContainer> INITIALIZED_CONTAINERS = new HashMap<>();

    /**
     * loaded once before any spring lifecycle events
     */
    static {
        Stream.of(
                new CassandraIntegrationTestLocalDependency(),
                new KafkaIntegrationTestLocalDependency()
        ).forEach(IntegrationTestEnvironment::initializeContainer);
    }

    private static void initializeContainer(IntegrationTestLocalDependency dependency) {
        GenericContainer container = dependency.containerDefinition();
        container.start();
        dependency.initializeSystemProperties(container);
        Runtime.getRuntime().addShutdownHook(new Thread(container::stop));
        INITIALIZED_CONTAINERS.put(dependency.name(), container);
    }

    public static Optional<GenericContainer> findTestContainer(String name) {
        return Optional.ofNullable(INITIALIZED_CONTAINERS.get(name));
    }
}
