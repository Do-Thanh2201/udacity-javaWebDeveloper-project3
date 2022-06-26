package com.udacity.jdnd.course3.critter.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.Optional;

@Repository
public interface DayRepository extends CrudRepository<Day, Long> {
    Optional<Day> findByDay(DayOfWeek day);
}
