package com.example.zingym.trainee;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class
CustomTraineeDetails implements UserDetails {

	private Trainee trainee;

	public CustomTraineeDetails(Trainee trainee) {
		this.trainee = trainee;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return trainee.getPassword();
	}

	@Override
	public String getUsername() {
		return trainee.getEmail();
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

		return trainee.getAccountStatus().equals("Active");
	}
	
	public String getFullName() {
		return trainee.getFirstName() + " " + trainee.getLastName();
	}

	public String getAccountstatuss() {
		return trainee.getAccountStatus();
	}


	public Long getID() {
		return trainee.getId();
	}

	public String getName() {

		return trainee.getFirstName();
	}
}
