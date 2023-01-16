package com.example.zingym;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "class")
public class Classes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "class_id")
    private Integer id;

    @Column( name = "class_name",nullable = false, length = 20)
    private String className;

    @Column( name = "class_description", nullable = false, length = 100)
    private String description;

    @Column( name ="class_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Column(name = "trainer_name", nullable = false, length = 45)
    private String trainerName;

    @Column(name = "class_start_time", nullable = false, length = 45)
    private String startTime;

    @Column(name = "class_end_time", nullable = false, length = 45)
    private String endTime;

    @Column( name = "trainer_style", nullable = false, length = 100)
    private String trainerStyle;

    @Column( name = "class_activity", nullable = false, length = 100)
    private String classActivity;

    @Column( name = "class_type", nullable = false, length = 100)
    private String classType;

    @Column( name = "class_seats", nullable = false, length = 100)
    private int numberOfSpots;

    @Column( name = "trainer_id", nullable = false, length = 100)
    private Long trainerId;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTrainerStyle() {
        return trainerStyle;
    }

    public void setTrainerStyle(String trainerStyle) {
        this.trainerStyle = trainerStyle;
    }

    public String getClassActivity() {
        return classActivity;
    }

    public void setClassActivity(String classActivity) {
        this.classActivity = classActivity;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public int getNumberOfSpots() {
        return numberOfSpots;
    }

    public void setNumberOfSpots(int numberOfSpots) {
        this.numberOfSpots = numberOfSpots;
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }
}
