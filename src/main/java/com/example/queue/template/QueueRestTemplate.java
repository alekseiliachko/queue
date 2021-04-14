package com.example.queue.template;

import com.example.queue.config.PathConfig;
import com.example.queue.exceptions.NonSubbedTopicException;
import com.example.queue.model.Message;
import com.example.queue.model.Queue;
import com.example.queue.model.Remote;
import com.example.queue.model.enums.Status;
import com.example.queue.service.QueueService;
import com.example.queue.service.RemoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class QueueRestTemplate {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    QueueService queueService;

    @Autowired
    RemoteService remoteService;

    public void sendMessageToRecipients(String topic, Message messageDto) {
        // send message if failed save it as query

        List<Remote> remotes = remoteService.getSubbedToTopic(topic);
        if (remotes.isEmpty()) {
            throw new NonSubbedTopicException(topic);
        }

        for (Remote remote : remotes) {
            Queue queue = messageDto.toQueue(remote.getName());
            try {
                String url = remote.getName() + PathConfig.MESSAGE_RECEIVER;
                ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, messageDto, String.class);
                if (!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
                    throw new IllegalArgumentException();
                }
                queue.setStatus(Status.HANDLED);
                queue.setHandled(new Date());
                log.info("send message to: " + remote.getName());
            } catch (Exception e) {
                log.error("failed to send message to recipient: " + "<" + remote.getName() + ">" + " saved to retry later.");
            }
            queueService.save(queue);
        }
    }

    public void tryResolveQueue(Queue queue) {
        try {
             String url = queue.getDestination() + PathConfig.MESSAGE_RECEIVER;
             ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, queue.toMessage(), String.class);
             if (!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
                 throw new IllegalArgumentException();
             }
             queue.setStatus(Status.HANDLED);
             queue.setHandled(new Date());
             queueService.save(queue);
             log.info("send message to: " + queue.getDestination());
        } catch (Exception e) {
            e.printStackTrace();
            queue.setRetriesLeft(queue.getRetriesLeft() - 1);
            if (queue.getRetriesLeft() == 0) {
                log.error("failed to send message to recipient: " + "<" + queue.getDestination() + ">" + " stored.");
                queueService.storage(queue);
            } else {
                log.error("failed to send message to recipient: " + "<" + queue.getDestination() + ">" + " saved to retry later.");
                queueService.save(queue);
            }
        }
    }
}
