package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.schedule.Schedule;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long employeeId;
    private String name;

    // Map to EmployeeSkill Entity
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "employee_employee_skill",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_skill_id"))
    private Set<EmployeeSkill> skills = new HashSet<>();

    // Map to Day Entity
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "employee_day",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "day_id"))
    private Set<Day> daysAvailable = new HashSet<>();

    // Map to Schedule
    @ManyToMany(mappedBy = "employees")
    private List<Schedule> schedules;
}
