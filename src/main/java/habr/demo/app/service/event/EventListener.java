package habr.demo.app.service.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EventListener {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ObjectMapper mapper;

    @KafkaListener(topics = "${kafka.topics.events}", groupId = "${kafka.consumer.events_safer:events_safer}")
    public void listen(String event) throws IOException {
        eventRepository.save(mapper.readValue(event, Event.class));
    }
}
