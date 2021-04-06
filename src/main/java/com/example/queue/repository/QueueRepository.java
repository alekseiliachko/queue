package com.example.queue.repository;

import com.example.queue.model.Queue;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QueueRepository extends MongoRepository<Queue, UUID> {
}
