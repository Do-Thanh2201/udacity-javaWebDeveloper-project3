package com.udacity.jdnd.course3.critter.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class EmployeeSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long employeeSkillId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EEmployeeSkill skill;

    public EmployeeSkill() {
    }

    public EmployeeSkill(EEmployeeSkill skill) {
        this.skill = skill;
    }
}
