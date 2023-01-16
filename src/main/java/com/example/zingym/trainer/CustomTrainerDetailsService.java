package com.example.zingym.trainer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomTrainerDetailsService implements UserDetailsService {

	@Autowired
	private TrainerRepository trainerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Trainer trainer = trainerRepository.findByEmail(username);
		if (trainer == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new CustomTrainerDetails(trainer);
	}

}
