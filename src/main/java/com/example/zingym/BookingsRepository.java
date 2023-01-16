package com.example.zingym;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingsRepository  extends JpaRepository<Bookings, String> {

    @Query("SELECT u FROM Bookings u WHERE u.id = ?1")
    Optional<Bookings> findById (String id);

    @Query( "select u from Bookings u where u.traineeId = :name" )
    List<Bookings> findByTraineeID(@Param("name") String name);


    @Query( "select u from Bookings u where u.trainerId = ?1" )
    List<Bookings> findByTrainerID(@Param("trainerId") String trainerId);


//    Existsby traineeId and className
    @Query( "select u from Bookings u where u.className = :className" )
    boolean findByClassName(@Param("className") String className);

    //    Existsby traineeId and className
    @Query( "select u from Bookings u where u.traineeId = :traineeId")
    boolean findByTraineeID2(@Param("traineeId") String traineeId);

    @Query("SELECT u FROM Bookings u WHERE u.className = :className")
    Optional<Bookings> findByTraineeID3 (boolean className);

//    Delete by id
    @Modifying
    @Transactional
    @Query("DELETE FROM Bookings u WHERE u.id = :id")
    void deleteById(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Bookings u WHERE u.trainerName = ?1 and u.trainerId = ?2")
    void deleteByTrainerNameANdTrainerId(@Param("trainerName") String trainerName, @Param("trainerId") String trainerId);

    //    Delete by id
    @Modifying
    @Transactional
    @Query("DELETE FROM Bookings u WHERE u.className = :className")
    void deleteByClassName(@Param("className") String className);

    @Modifying
    @Transactional
    @Query("DELETE FROM Bookings u WHERE u.className = ?1 and  u.trainerId = ?2 ")
    void deleteByClassNameAndClassID(@Param("className") String className, @Param("trainerId") String trainerId);

    //exists by traineeId and className and trainerName
    @Query( "select u from Bookings u where u.traineeId = ?1 and u.className = ?2 and u.trainerName = ?3 and u.traineeName = ?4 and u.classActivity = ?5 " +
            "and u.trainerId = ?6 and u.date = ?7 and u.time = ?8 and u.classId = ?9" )
    Bookings findByTraineeIDAndClassNameAndTrainerName(String traineeId, String className, String trainerName, String traineeName, String classActivity, String trainerId, String date, String time, String classId);

    @Query( "select u from Bookings u where u.trainerId = ?1 and u.className = ?2")
    List <Bookings> findByTrainerIDAndClassName(String traineeId, String className);

    @Query( "select u from Bookings u where u.trainerId = ?1 and u.trainerName = ?2")
    Bookings findByTrainerIDAndTrainerName(String trainerId, String trainerName);

    Bookings findByTraineeIdAndClassNameAndTrainerName(String traineeId, String className, String trainerName);
}
