package com.example.queue.service;

import com.example.queue.exceptions.EntityNotFoundException;
import com.example.queue.model.Remote;
import com.example.queue.repository.RemoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RemoteService {

    @Autowired
    RemoteRepository remoteRepository;

    public Remote save(Remote remote) {
        remote.setId(UUID.randomUUID());
        return remoteRepository.save(remote);
    }

    public Remote findByName(String name) {
        return remoteRepository.findByName(name).orElseThrow(EntityNotFoundException::new);
    }
}
