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
public interface ClassesRepository extends JpaRepository<Classes, String> {
	@Query("SELECT u FROM Classes u WHERE u.id = ?1")
    Optional<Classes> findById (String id);

    @Query("SELECT u FROM Classes u WHERE u.className = ?1")
    Classes findByName (String name);

    @Query("SELECT u FROM Classes u WHERE u.className = ?1")
    Classes findByNameAndId (String name);

    @Query("SELECT u FROM Classes u WHERE u.trainerName = ?1 AND u.trainerId = ?2")
    List <Classes> findByTrainerNameAndTrainerId (String name, Long id);

    @Query( "select u from Classes u WHERE u.id = ?1")
    List<Classes> findByClass(String id);

    //    Delete by id
    @Modifying
    @Transactional
    @Query("DELETE FROM Classes u WHERE u.id = :id")
    void deleteById(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Classes u WHERE u.trainerName = ?1 and u.trainerId = ?2")
    void deleteByTrainerNameANdTrainerId(@Param("trainerName") String trainerName, @Param("trainerId") Long trainerId);

    @Query( "select u from Classes u where u.trainerStyle = ?1 and u.classActivity = ?2 and u.classType = ?3" )
    List<Classes> findByStyleAndActivityAndClassType(@Param("style") String style, @Param("activity") String activity, @Param("classtype") String classtype);

    @Query( "select u from Classes u where u.className = ?1")
    Classes findByClassName(String className);

    @Query( "select u from Classes u where u.trainerId = ?1 and u.trainerName = ?2")
    Classes findByTrainerIDAndTrainerName(Integer trainerId, String trainerName);
}
