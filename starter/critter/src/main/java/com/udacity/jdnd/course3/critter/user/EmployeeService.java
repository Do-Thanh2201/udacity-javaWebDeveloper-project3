package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeSkillRepository employeeSkillRepository;
    private final DayRepository dayRepository;

    private ScheduleRepository scheduleRepository;
    @Autowired
    public void getScheduleRepository(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }
    public EmployeeService(EmployeeRepository employeeRepository
            , EmployeeSkillRepository employeeSkillRepository, DayRepository dayRepository
//            , ScheduleRepository scheduleRepository
    ) {

        this.employeeRepository = employeeRepository;
        this.employeeSkillRepository = employeeSkillRepository;
        this.dayRepository = dayRepository;
//        this.scheduleRepository = scheduleRepository;
    }

    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {

        Employee employee = new Employee();
        String message = this.convertToEmployee(employee, employeeDTO);

        if (message == null) {
            return employeeDTO;
        }
        return null;
    }



    public EmployeeDTO getEmployee(long employeeId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EntityNotFoundException("Can't find the employeeId: " + employeeId));
        return this.convertToEmployeeDTO(employee);
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EntityNotFoundException("Can't find the employeeId: " + employeeId));

        // Set Day
        Set<Day> dayAvailableSet = new HashSet<>();
        for (DayOfWeek day:
                daysAvailable) {

            Day dayResponse = dayRepository.findDayByDayOfWeek(day)
                    .orElseThrow( () -> new EntityNotFoundException("Can't find the Day: "+ day.toString()));

            dayAvailableSet.add(dayResponse);
        }
        employee.setDaysAvailable(dayAvailableSet);
    }

    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeDTO) {

        Set<EmployeeSkill> employeeSkillSet = new HashSet<>();
        for (EEmployeeSkill skill:
                employeeDTO.getSkills()) {

            EmployeeSkill employeeSkill = employeeSkillRepository.findBySkill(skill)
                    .orElseThrow(() -> new EntityNotFoundException("can't find the Skill"));
            employeeSkillSet.add(employeeSkill);
        }

        Schedule schedule = scheduleRepository.findScheduleByDate(employeeDTO.getDate())
                .orElseThrow(() -> new EntityNotFoundException("Can't find the day available"));

        List<Employee> employeeList = employeeRepository.findBySkillsInAndScheduleIn(employeeSkillSet, Collections.singletonList(schedule));
        if(employeeList.isEmpty()) {
            return null;
        }
        return employeeList.stream().map(this::convertToEmployeeDTO).collect(Collectors.toList());
    }

    /** Convert from EmployeeDTO to Employee
     * @param employee
     * @param employeeDTO
     *
     * */
    private String convertToEmployee(Employee employee, EmployeeDTO employeeDTO) {

        employee.setName(employeeDTO.getName());

        // Set Skills
        Set<EmployeeSkill> employeeSkillList = new HashSet<>();
        for (EEmployeeSkill skill:
                employeeDTO.getSkills()) {

            EmployeeSkill employeeSkill = employeeSkillRepository.findBySkill(skill)
                    .orElseThrow( () -> new EntityNotFoundException("Can't find the Activity: "+ skill.toString()));

            employeeSkillList.add(employeeSkill);
        }
        if(employeeSkillList.isEmpty()) {
            return "Can't find the Activity";
        }
        employee.setSkills(employeeSkillList);

        // Set Day
        Set<Day> dayAvailableSet = new HashSet<>();
        for (DayOfWeek day:
                employeeDTO.getDaysAvailable()) {

            Day dayAvailable = dayRepository.findDayByDayOfWeek(day)
                    .orElseThrow( () -> new EntityNotFoundException("Can't find the Day: "+ day.toString()));

            dayAvailableSet.add(dayAvailable);
        }
        if(dayAvailableSet.isEmpty()) {

            return "Can't find the Day";
        }
        employee.setDaysAvailable(dayAvailableSet);

        return null;
    }

    /** Convert from Employee to EmployeeDTO
     *
     * @param employee
     *
     * @return employeeDTO
     * */
    private EmployeeDTO convertToEmployeeDTO(Employee employee) {

        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.setId(employee.getEmployeeId());
        employeeDTO.setName(employee.getName());

        Set<EmployeeSkill> employeeSkillSet = employee.getSkills();
        employeeDTO.setSkills(employeeSkillSet.stream().map(EmployeeSkill::getSkill).collect(Collectors.toSet()));

        Set<Day> daySet = employee.getDaysAvailable();
        employeeDTO.setDaysAvailable(daySet.stream().map(Day::getDayOfWeek).collect(Collectors.toSet()));

        return employeeDTO;
    }
}
