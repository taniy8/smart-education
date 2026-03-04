package com.smartedu.smart_education.repository;

import com.smartedu.smart_education.entity.PersonalizedTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalizedTestRepository extends JpaRepository<PersonalizedTest,Long> {
}
