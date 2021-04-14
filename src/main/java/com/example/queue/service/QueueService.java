package com.example.queue.service;

import com.example.queue.model.Queue;
import com.example.queue.model.enums.Status;
import com.example.queue.repository.QueueRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class QueueService {

    @Autowired
    private QueueRepository queueRepository;

    public void save(Queue queue) {
        if (queue.getId() == null)
            queue.setId(UUID.randomUUID());
        queueRepository.save(queue);
    }

    public void storage(Queue queue) {
        queue.setStatus(Status.STORED);
        queueRepository.save(queue);
    }

    public List<Queue> findRelevant() {
        return queueRepository.findAllByStatus(Status.WAITING);
    }

    public List<Queue> findHandled() {
        return queueRepository.findAllByStatus(Status.HANDLED);
    }

    public List<Queue> findStored() {
        return queueRepository.findAllByStatus(Status.STORED);
    }
}
