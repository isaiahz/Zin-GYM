package com.example.zingym.trainer;

import com.example.zingym.DeleteProfileRequests;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Trainer")
public class Trainer implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "TrainerEmail", nullable = false, unique = true, length = 45)
	private String email;
	
	@Column(name = "TrainerPassword", nullable = false, length = 64)
	private String password;
	
	@Column(name = "TrainerFName", nullable = false, length = 20)
	private String firstName;
	
	@Column(name = "TrainerLName", nullable = false, length = 20)
	private String lastName;

	@Column(name = "TrainerPhone",  nullable = false, length = 20)
	private String phoneNumber;

	@Column(name = "TrainerGender",  nullable = false, length = 20)
	private String gender;

	@Column( name = "TrainerStyle",  length = 100)
	private String TrainingStyle;

	@Column(name = "TrainerActivities",  length = 100)
	private String TrainingActivity;

	@Column(name = "AccountStatus",  length = 100)
	private String AccountStatus;

	@OneToOne
	@JoinColumn(name = "delete_request_ID" , referencedColumnName = "DeleteProfileRequestID", columnDefinition = "bigint(20) default 0")
	private DeleteProfileRequests deleteProfileRequests;


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

	public DeleteProfileRequests getDeleteProfileRequests() {
		return deleteProfileRequests;
	}

	public void setDeleteProfileRequests(DeleteProfileRequests deleteProfileRequests) {
		this.deleteProfileRequests = deleteProfileRequests;
	}
}
