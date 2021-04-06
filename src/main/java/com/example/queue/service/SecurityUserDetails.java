package com.example.queue.service;

import com.example.queue.model.Remote;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
public class SecurityUserDetails implements UserDetails {
	private static final long serialVersionUID = 1L;

	private UUID id;

	private String remoteName;

	@JsonIgnore
	private String remotePass;

	private Collection<? extends GrantedAuthority> authorities;

	public SecurityUserDetails(UUID id, String remoteName, String remotePass,
							   Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.remoteName = remoteName;
		this.remotePass = remotePass;
		this.authorities = authorities;
	}

	public static SecurityUserDetails build(Remote remote) {
		List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(remote.getRole().name()));

		return new SecurityUserDetails(
				remote.getId(),
				remote.getName(),
				remote.getPass(),
				authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return remotePass;
	}

	@Override
	public String getUsername() {
		return remoteName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
