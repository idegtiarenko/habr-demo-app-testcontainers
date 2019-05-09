package habr.demo.app.service.event;

import com.datastax.driver.core.Row;
import habr.demo.app.service.CassandraConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public class EventRepository {

    @Autowired
    private CassandraConnection cassandraConnection;

    public void save(Event event) {
        cassandraConnection.getSession().execute("INSERT INTO events(user_id, id, time, type) VALUES(?, ?, ?, ?)",
                event.getUserId(), event.getId(), event.getTime().toEpochMilli(), event.getType());
    }

    public Stream<Event> findByUserId(UUID userId) {
        return cassandraConnection.getSession()
                .execute("SELECT * FROM events WHERE user_id = ?", userId).all().stream()
                .map(this::fromResultRow);
    }

    private Event fromResultRow(Row row) {
        return new Event(
                row.getUUID("user_id"),
                row.getUUID("id"),
                Instant.ofEpochMilli(row.getLong("time")),
                row.getString("type")
        );
    }
}
