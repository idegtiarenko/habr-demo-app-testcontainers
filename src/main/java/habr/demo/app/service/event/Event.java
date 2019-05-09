package habr.demo.app.service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private UUID userId;
    private UUID id;
    private Instant time;
    private String type;
}
