package com.example.queue.service;

import com.example.queue.exceptions.EntityNotFoundException;
import com.example.queue.exceptions.TopicException;
import com.example.queue.model.Remote;
import com.example.queue.model.enums.Role;
import com.example.queue.repository.RemoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RemoteService implements UserDetailsService {

    @Autowired
    RemoteRepository remoteRepository;

    public Remote save(Remote remote) {
        if (remote.getId() == null)
            remote.setId(UUID.randomUUID());
        return remoteRepository.save(remote);
    }

    public Remote findByName(String name) {
        return remoteRepository.findByName(name).orElseThrow(EntityNotFoundException::new);
    }

    public Boolean existsByName(String name) {
        return remoteRepository.existsByName(name);
    }

    public List<Remote> getSubbedToTopic(String topic) {
        return remoteRepository.findBySubsContaining(topic);
    }

    public void subscribe(String name, String topic) {

        Remote remote = remoteRepository.findByName(name).orElseThrow(EntityNotFoundException::new);
        List<String> topics = remote.getSubs();
        if (topics.contains(topic)) {
            throw new TopicException(topic);
        }
        topics.add(topic);
        remote.setSubs(topics);
        remoteRepository.save(remote);
    }

    public void unsubscribe(String name, String topic) {
        Remote remote = remoteRepository.findByName(name).orElseThrow(EntityNotFoundException::new);
        List<String> topics = remote.getSubs();
        if (!topics.contains(topic)) {
            throw new TopicException(topic);
        }
        topics.remove(topic);
        remote.setSubs(topics);
        remoteRepository.save(remote);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Remote remote = remoteRepository.findByName(username).orElse(null);

        if (remote == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return remote;
    }
}
