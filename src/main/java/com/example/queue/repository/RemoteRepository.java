package com.example.queue.repository;

import com.example.queue.model.Remote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RemoteRepository extends MongoRepository<Remote, UUID> {
    Optional<Remote> findByName(String name);

    List<Remote> findBySubsContaining(String topic);
}
