package com.example.zingym.trainee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomTraineeDetailsService implements UserDetailsService {

	@Autowired
	private TraineeRepository traineeRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Trainee trainee = traineeRepository.findByEmail(username);
		if (trainee == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new CustomTraineeDetails(trainee);
	}

}
