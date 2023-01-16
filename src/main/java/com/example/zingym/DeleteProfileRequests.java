package com.example.zingym;

import javax.persistence.*;

@Entity
@Table(name = "Delete_Profile_Requests")
public class DeleteProfileRequests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DeleteProfileRequestID")
        private Long id;

    @Column(name = "TrainerEmail", nullable = false, unique = true, length = 45)
        private String email;

    @Column(name = "TrainerName", nullable = false, length = 64)
        private String trainerName;

    @Column(name = "TrainerID", length = 150)
    private String trainerID;

    @Column(name = "TrainerReason", nullable = false, length = 150)
        private String reason;

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

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getTrainerID() {
        return trainerID;
    }

    public void setTrainerID(String trainerID) {
        this.trainerID = trainerID;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
