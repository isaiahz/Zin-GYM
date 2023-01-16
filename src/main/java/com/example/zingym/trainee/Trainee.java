package com.example.zingym.trainee;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Trainee")
public class Trainee implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MembershipNo")
	private Long id;

	@Column(name = "TraineeEmail", nullable = false, unique = true, length = 45)
	private String email;

	@Column(name = "TraineePassword", nullable = false, length = 64)
	private String password;

	@Column(name = "TraineeFName", nullable = false, length = 20)
	private String firstName;

	@Column(name = "TraineeLName", nullable = false, length = 20)
	private String lastName;

	@Column(name = "TraineePhone",  nullable = false, length = 20)
	private String phoneNumber;

	@Column(name = "TraineeGender",  nullable = false, length = 20)
	private String gender;

	@Column(name = "TraineeExpLevel",  length = 100)
	private String experienceLevel;

	@Column(name = "TraineeStyle",  length = 100)
	private String TrainingStyle;

	@Column(name = "TraineeActivities",  length = 100)
	private String TrainingActivity;

	@Column(name = "AccountStatus",  length = 100)
	private String AccountStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getExperienceLevel() {
		return experienceLevel;
	}

	public void setExperienceLevel(String experienceLevel) {
		this.experienceLevel = experienceLevel;
	}

	public String getTrainingStyle() {
		return TrainingStyle;
	}

	public void setTrainingStyle(String trainingStyle) {
		TrainingStyle = trainingStyle;
	}

	public String getTrainingActivity() {
		return TrainingActivity;
	}

	public void setTrainingActivity(String trainingActivity) {
		TrainingActivity = trainingActivity;
	}

	public String getAccountStatus() {
		return AccountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		AccountStatus = accountStatus;
	}
}