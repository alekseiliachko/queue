package com.example.queue.service;

import com.example.queue.config.PathConfig;
import com.example.queue.model.Message;
import com.example.queue.model.Queue;
import com.example.queue.model.Remote;
import com.example.queue.repository.QueueRepository;
import com.example.queue.repository.RemoteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class QueueService {

    private static final Logger logger = LoggerFactory.getLogger(QueueService.class);

    private final RemoteRepository remoteRepository;

    private final QueueRepository queueRepository;

    private RestTemplate restTemplate = new RestTemplate();

    public void handle(Message message) {
        List<Remote> subbed = remoteRepository.findBySubsContaining(message.getTopic());
        for (Remote remote : subbed) {
            sendMessage(remote, message);
        }
    }

    private void sendMessage(Remote from, Message message) {
        String topic = message.getTopic();
        String url = "https://" + from.getName() + "/" + PathConfig.MESSAGE_RECEIVER + "/" + topic + "/";
        try {
            restTemplate.postForObject(url, message, Message.class);
        }catch (Exception exception) {
            logger.error("failed to send message. archived");
            Queue queue = message.toQueue(from.getId());
            queueRepository.save(queue);
        }
    }
}
