package habr.demo.app.service;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class CassandraConnection {

    @Value("${cassandra.host}")
    private String host;
    @Value("${cassandra.port}")
    private int port;
    @Value("${cassandra.keyspace}")
    private String keyspace;

    @Getter
    private Cluster cluster;
    @Getter
    private Session session;

    @PostConstruct
    public void init() {
        cluster = Cluster.builder().withoutMetrics().withoutJMXReporting().addContactPoint(host).withPort(port).build();
        session = cluster.connect(keyspace);
    }

    @PreDestroy
    public void destroy() {
        session.close();
        cluster.close();
    }
}
