package com.example.zingym.trainer;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class
CustomTrainerDetails implements UserDetails {

	private Trainer trainer;

	public CustomTrainerDetails(Trainer trainer) {
		this.trainer = trainer;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return trainer.getPassword();
	}

	@Override
	public String getUsername() {
		return trainer.getEmail();
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

		return trainer.getAccountStatus().equals("Active") || trainer.getAccountStatus().equals("Active (Account Deletion Requested)");
	}
	
	public String getFullName() {
		return trainer.getFirstName() + " " + trainer.getLastName();
	}

	public String getDeleteRequest() {
		return trainer.getDeleteProfileRequests().getEmail();
	}

	public String getEmail() {
		return trainer.getEmail();
	}

	public String getAccountstatuss() {
		return trainer.getAccountStatus();
	}


	public Long getID() {
		return trainer.getId();
	}

	public String getName() {

		return trainer.getFirstName();
	}
}
