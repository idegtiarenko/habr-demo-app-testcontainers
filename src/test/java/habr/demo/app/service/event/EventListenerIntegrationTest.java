package habr.demo.app.service.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import habr.demo.app.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@IntegrationTest
class EventListenerIntegrationTest {

    @Autowired
    private EventRepository eventRepository;

    @Value("${kafka.topics.events}")
    private String eventsTopic;
    @Autowired
    private KafkaTemplate<String, String> template;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void shouldSavePublishedEvent() throws JsonProcessingException {
        //given
        Event event = new Event(
                UUID.randomUUID(),
                UUID.randomUUID(),
                Instant.now(),
                "test"
        );

        //when
        template.send(eventsTopic, mapper.writeValueAsString(event));

        //then
        await()
                .atMost(10, TimeUnit.SECONDS)
                .untilAsserted(() -> assertThat(eventRepository.findByUserId(event.getUserId())).isNotEmpty());
    }

}