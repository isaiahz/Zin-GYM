package com.example.zingym;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DeleteProfileRequestsRepository extends JpaRepository<DeleteProfileRequests, String> {

    @Query("SELECT u FROM DeleteProfileRequests u WHERE u.email = ?1 AND u.trainerID = ?2")
    DeleteProfileRequests findByTrainerNameAndTrainerID (String email, String trainerID);


    @Query("SELECT u FROM DeleteProfileRequests u WHERE u.email = ?1 AND u.trainerName = ?2")
    DeleteProfileRequests findByEmailAndTrainerName (String email, String trainerName);

    @Modifying
    @Transactional
    @Query("DELETE FROM DeleteProfileRequests u WHERE u.id = :id")
    void deleteById(@Param("id") Long id);

}
