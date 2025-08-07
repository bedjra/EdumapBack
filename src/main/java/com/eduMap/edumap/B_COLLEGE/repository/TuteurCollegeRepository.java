package com.eduMap.edumap.B_COLLEGE.repository;

import com.eduMap.edumap.B_COLLEGE.Entity.TuteurCollege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TuteurCollegeRepository extends JpaRepository<TuteurCollege, Long> {

    Optional<TuteurCollege> findByNomIgnoreCaseAndPrenomIgnoreCase(String nom, String prenom);


}
