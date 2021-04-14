package com.example.queue.scheduler;

import com.example.queue.model.Queue;
import com.example.queue.service.QueueService;
import com.example.queue.template.QueueRestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class QueueManager {

    @Autowired
    QueueService queueService;

    @Autowired
    QueueRestTemplate queueRestTemplate;

//    @Scheduled(fixedRate = 300000)
        @Scheduled(fixedRate = 3000)
    private void doMyJob() {
        List<Queue> queues = queueService.findRelevant();

        if (queues.isEmpty()) {
            log.info("query is empty; nothing to send");
            return;
        }
        for (Queue queue: queues) {
            queueRestTemplate.tryResolveQueue(queue);
        }
    }

    @Scheduled(fixedRate = 3600000 * 24 * 1)
    private void clear() {

    }
}
