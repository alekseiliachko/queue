package com.example.queue.service;

import com.example.queue.exceptions.EntityNotFoundException;
import com.example.queue.model.Remote;
import com.example.queue.repository.RemoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

	@Autowired
	RemoteRepository remoteRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		Remote remote = remoteRepository.findByName(name).orElseThrow(EntityNotFoundException::new);

		return SecurityUserDetails.build(remote);
	}

}
