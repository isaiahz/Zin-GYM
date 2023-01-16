package com.example.zingym;

import com.example.zingym.admin.Admin;
import com.example.zingym.admin.AdminRepository;
import com.example.zingym.trainee.Trainee;
import com.example.zingym.trainee.TraineeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private TraineeRepository traineeRepository;
	@Autowired
	private AdminRepository adminRepository;

	@Test
	public void addAdmin() {
		Admin admin = new Admin();
		admin.setEmail("admin@gmail.com");
		admin.setPassword("adminpassword");


		adminRepository.save(admin);

	}

}

