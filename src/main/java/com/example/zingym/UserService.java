package com.example.zingym;

import com.example.zingym.trainee.Trainee;
import com.example.zingym.trainee.TraineeRepository;
import com.example.zingym.trainer.Trainer;
import com.example.zingym.trainer.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

	@Autowired PasswordEncoder passwordEncoder;

	@Autowired
	private TraineeRepository traineeRepo;

	@Autowired
	private ClassesRepository classesRepository;

	@Autowired
	private TrainerRepository trainerRepository;

	@Autowired
	private BookingsRepository bookingsRepository;

	@Autowired
	private ActivityRepository activityRepository;

	@Autowired
	private TraineeRepository traineeRepository;
	
	public void registerTrainee(Trainee trainee) {

		trainee.setAccountStatus("Active");
		traineeRepo.save(trainee);
	}

	public void registerTrainer(Trainer trainer) {

		trainer.setAccountStatus("InActive");
//		trainer.s("N/A");
		trainerRepository.save(trainer);

	}

	public void registerTrainerTest(Trainer trainer) {

		trainerRepository.save(trainer);

	}

	public void registerTrainerEdit(Trainer trainer) {

		trainer.setAccountStatus("Active");
		trainerRepository.save(trainer);

	}

	public void registerTraineeEdit(Trainee trainee) {

		trainee.setAccountStatus("Active");
		traineeRepository.save(trainee);

	}
//
//	public void registerAdmin(User user) {
//		encodePassword(user);
//
//		Role roleUser = roleRepo.findByName("Admin");
//		user.addRole(roleUser);
//
//
//		userRepository.save(user);
//
//	}
//
//	public void update(User user) {
//		userRepository.save(user);
//	}
//
//	public void registerTrainer(User user, Trainer trainer) {
//		encodePassword(user);
//
//		Role roleUser = roleRepo.findByName("Trainer");
//		user.addRole(roleUser);
//
//
//		String rawUserPassword = trainer.getPassword();
//		user.setRawPassword(rawUserPassword);
//		user.setAccountStatus("InActive");
//		userRepository.save(user);
//		trainer.setUser(user);
//		String rawPassword = trainer.getPassword();
//		trainer.setPassword(rawPassword);
//
//		trainerRepository.save(trainer);
//	}
//
////	public void classes2(Classes classes, User user) {
////
////		classes.setUser(user);
////		classesRepository.save(classes);
////	}
//
//	public void registerBooking(Bookings bookings) {
//
//		bookingsRepository.save(bookings);
//	}
//
	public void classes(Classes classes) {

//		classes.setUser(user);
		classesRepository.save(classes);
	}
//
	public void activities(Activity activity) {

		activityRepository.save(activity);
	}
//
//	public List<User> listAll() {
//		return userRepository.findAll();
//	}
//
//	public List<User> listAl() {
//		return userRepository.findBySpecificRoles("ADMIN");
//	}
//
	public List<Trainer> listTrainer() {
		return trainerRepository.findAll();
	}
	public List<Trainee> listTrainee() {
		return traineeRepo.findAll();
	}
//
//	public List<Classes> listClasses() {
//		return classesRepository.findAll();
//	}
//
//	public List<User> listTrainee() {
//		return userRepository.findByTrainee("TRAINEE");
//	}
//
//	public User get(Long id) {
//		return userRepository.findById(String.valueOf(id)).get();
//	}
//
//	public List<Role> listRoles() {
//		return roleRepo.findAll();
//	}
//
//	public void save(User user) {
//		userRepository.save(user);
//	}
//
//	private void encodePassword(User user) {
//		String encodedPassword = passwordEncoder.encode(user.getPassword());
//		user.setPassword(encodedPassword);
//	}
//
//	private void rawwPassword(User user) {
//		String rawPassword = user.getPassword();
//		user.setPassword(rawPassword);
//	}
//
//
//	public User findByEmail(String email) {
//		return userRepository.findByEmail(email);
//	}
//
//    public User getStudent(int id) {
//		return userRepository.findById(String.valueOf(id)).get();
//    }
}
