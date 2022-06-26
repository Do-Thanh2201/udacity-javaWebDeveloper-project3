package com.udacity.jdnd.course3.critter.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeSkillRepository extends CrudRepository<EmployeeSkill, Long> {
    Optional<EmployeeSkill> findBySkill(EEmployeeSkill skill);
}
