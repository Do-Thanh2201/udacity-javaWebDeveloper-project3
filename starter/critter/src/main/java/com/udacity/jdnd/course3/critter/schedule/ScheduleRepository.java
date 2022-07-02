package com.udacity.jdnd.course3.critter.schedule;


import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
    @Query(value = "select s.* from Schedule s, Pet p where s.pet_id = p.pet_id and p.pet_id = ?1", nativeQuery = true)
    List<Schedule> findScheduleForPet(Long petId);
    List<Schedule> findAllByPets(Pet pet);

    @Query(value = "select s.* from Schedule s, Employee e where s.employee_id = e.employee_id and e.employee_id = ?1", nativeQuery = true)
    List<Schedule> findScheduleForEmployee(Long petId);

    List<Schedule> findAllByEmployees(Employee employee);

    @Query(value = "select s.* from Schedule s, Customer c where s.customer_id = c.customer_id and c.customer_id = ?1", nativeQuery = true)
    List<Schedule> findScheduleForCustomer(Long petId);


}
