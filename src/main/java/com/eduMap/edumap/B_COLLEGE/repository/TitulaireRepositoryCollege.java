package com.eduMap.edumap.B_COLLEGE.repository;

import com.eduMap.edumap.B_COLLEGE.Entity.TitulaireCollege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitulaireRepositoryCollege extends JpaRepository<TitulaireCollege, Long> {
}
