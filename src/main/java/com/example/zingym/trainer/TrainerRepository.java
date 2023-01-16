package com.example.zingym.trainer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TrainerRepository extends JpaRepository<com.example.zingym.trainer.Trainer, String> {
	@Query("SELECT u FROM Trainer u WHERE u.email = ?1")
    com.example.zingym.trainer.Trainer findByEmail(String email);

	@Query("SELECT u FROM Trainer u WHERE u.id = ?1")
	Trainer findById(Long id);

	@Query("SELECT u FROM Trainer u WHERE u.id = ?1")
    com.example.zingym.trainer.Trainer findByName (Long name);

	//    Delete by id
	@Modifying
	@Transactional
	@Query("DELETE FROM Trainer u WHERE u.id = :id")
	void deleteByUserId(@Param("id") Long id);

	@Query( "select u from Trainer u where u.AccountStatus = ?1" )
	List<Trainer> findByAccountStatus(@Param("accountStatus") String accountStatus);

	@Query( "select u from Trainer u where u.deleteProfileRequests.email = ?1")
	Trainer findByDeleteProfileRequests(String email);

	@Query( "select u from Trainer u where u.deleteProfileRequests.reason = ?1")
	Trainer findByDeleteProfileRequestsd(String reason);

}
