package com.example.zingym;


import javax.persistence.*;

@Entity
@Table(name = "booking")
public class Bookings{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "booking_id")
    private Integer id;

    @Column( name = "class_name",nullable = false, length = 20)
    private String className;

    @Column( name = "class_activity", length = 50)
    private String classActivity;

    @Column(name = "trainer_name", length = 45)
    private String trainerName;

    @Column(name = "trainee_name", length = 45)
    private String traineeName;

    @Column(name = "trainee_id" )
    private String traineeId;

    @Column(name = "trainer_id")
    private String trainerId;

    @Column(name = "date")
    private String date;

    @Column(name = "time")
    private String time;


    @Column(name = "class_id")
    private String classId;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(String traineeId) {
        this.traineeId = traineeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassActivity() {
        return classActivity;
    }

    public void setClassActivity(String classActivity) {
        this.classActivity = classActivity;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getTraineeName() {
        return traineeName;
    }

    public void setTraineeName(String traineeName) {
        this.traineeName = traineeName;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
