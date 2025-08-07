package com.eduMap.edumap.B_COLLEGE.repository;


import com.eduMap.edumap.B_COLLEGE.Entity.ScolariteCollege;
import com.eduMap.edumap.B_COLLEGE.enums.ClasseCOLLEGE;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScolariteCollegeRepository extends JpaRepository<ScolariteCollege, Long> {
    Optional<ScolariteCollege> findByClasse(ClasseCOLLEGE classeCOLLEGE);
}
