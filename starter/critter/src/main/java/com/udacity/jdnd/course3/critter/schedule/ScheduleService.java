package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final PetRepository petRepository;

    private EmployeeRepository employeeRepository;

    @Autowired
    public  void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    private final EmployeeSkillRepository employeeSkillRepository;

    public ScheduleService(ScheduleRepository scheduleRepository
                            ,PetRepository petRepository
//                            ,EmployeeRepository employeeRepository
                            ,EmployeeSkillRepository employeeSkillRepository) {

        this.scheduleRepository = scheduleRepository;
        this.petRepository = petRepository;
//        this.employeeRepository = employeeRepository;
        this.employeeSkillRepository = employeeSkillRepository;
    }


    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        String message = this.convertToSchedule(schedule, scheduleDTO);

        if(message == null) {

            return null;
        }
        return scheduleDTO;
    }

    public List<ScheduleDTO> getAllSchedules() {

        List<Schedule> scheduleList = (List<Schedule>) scheduleRepository.findAll();
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        if(scheduleList.isEmpty()) {
            return scheduleDTOList;
        }

        scheduleDTOList = scheduleList.stream().map(this::convertToScheduleDTO).collect(Collectors.toList());
        return scheduleDTOList;
    }

    public List<ScheduleDTO> getScheduleForPet(long petId) {

        List<Schedule> scheduleList = scheduleRepository.findScheduleForPet(petId);

        if(scheduleList.isEmpty()) {

            return null;
        }
        List<ScheduleDTO> scheduleDTOList = scheduleList.stream().map(this::convertToScheduleDTO).collect(Collectors.toList());

        return scheduleDTOList;
    }

    public List<ScheduleDTO> getScheduleForEmployee(long employeeId) {

        List<Schedule> scheduleList = scheduleRepository.findScheduleForEmployee(employeeId);

        if(scheduleList.isEmpty()) {

            return null;
        }
        List<ScheduleDTO> scheduleDTOList = scheduleList.stream().map(this::convertToScheduleDTO).collect(Collectors.toList());

        return scheduleDTOList;
    }

    public List<ScheduleDTO> getScheduleForCustomer(long customerId) {

        List<Schedule> scheduleList = scheduleRepository.findScheduleForCustomer(customerId);

        if(scheduleList.isEmpty()) {

            return null;
        }
        List<ScheduleDTO> scheduleDTOList = scheduleList.stream().map(this::convertToScheduleDTO).collect(Collectors.toList());

        return scheduleDTOList;
    }

    /** Convert from ScheduleDTO to Schedule
     * @param schedule
     * @param scheduleDTO
     *
     * */
    private String convertToSchedule(Schedule schedule, ScheduleDTO scheduleDTO) {

        // Set Employees
        List<Employee> employees = (List<Employee>) employeeRepository.findAllById(scheduleDTO.getEmployeeIds());
        schedule.setEmployees(employees);

        // Set Date
        List<Pet> petList = (List<Pet>) petRepository.findAllById(scheduleDTO.getPetIds());
        if(petList.isEmpty()) {

            return "Can't find the PetIDs";
        }
        schedule.setPets(petList);

        schedule.setDate(scheduleDTO.getDate());

        // Set Activities
        Set<EmployeeSkill> employeeSkillList = new HashSet<>();
        for (EEmployeeSkill skill:
                scheduleDTO.getActivities()) {

            EmployeeSkill employeeSkill = employeeSkillRepository.findBySkill(skill)
                    .orElseThrow( () -> new EntityNotFoundException("Can't find the Activity: "+ skill.toString()));

            employeeSkillList.add(employeeSkill);
        }
        if(employeeSkillList.isEmpty()) {

            return "Can't find the Activity";
        }
        schedule.setActivities(employeeSkillList);

        return null;
    }

    /** Convert from Schedule to ScheduleDTO
     *
     * @param schedule
     *
     * @return scheduleDTO
     * */
    private ScheduleDTO convertToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();

        scheduleDTO.setId(schedule.getScheduleId());

        List<Employee> employeeList = schedule.getEmployees();
        scheduleDTO.setEmployeeIds(employeeList.stream().map(Employee::getEmployeeId).collect(Collectors.toList()));

        List<Pet> petList = schedule.getPets();
        scheduleDTO.setPetIds(petList.stream().map(Pet::getPetId).collect(Collectors.toList()));

        scheduleDTO.setDate(scheduleDTO.getDate());

        Set<EmployeeSkill> activitieList = schedule.getActivities();
        scheduleDTO.setActivities(activitieList.stream().map(EmployeeSkill::getSkill).collect(Collectors.toSet()));

        return scheduleDTO;
    }
}
