package com.example.queue.service;

import com.example.queue.exceptions.EntityNotFoundException;
import com.example.queue.model.Remote;
import com.example.queue.model.enums.Role;
import com.example.queue.repository.RemoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RemoteService implements UserDetailsService {

    @Autowired
    RemoteRepository remoteRepository;

    public Remote save(Remote remote) {
        remote.setId(UUID.randomUUID());
        return remoteRepository.save(remote);
    }

    public Remote findByName(String name) {
        return remoteRepository.findByName(name).orElseThrow(EntityNotFoundException::new);
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
