package com.example.zingym.trainee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, String> {
	@Query("SELECT u FROM Trainee u WHERE u.email = ?1")
    Trainee findByEmail(String email);

    @Query("SELECT u FROM Trainee u WHERE u.id = ?1")
    Trainee findByName (Long name);

    @Query("SELECT u FROM Trainee u WHERE u.id = ?1")
    Trainee findById(Long id);

    //    Delete by id
    @Modifying
    @Transactional
    @Query("DELETE FROM Trainee u WHERE u.id = :id")
    void deleteByUserId(@Param("id") Long id);

    @Query("SELECT u FROM Trainee u WHERE u.email = ?1 AND u.password = ?2")
    List<Trainee> findByEmailAndPassword(String email, String password);



}
