package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.user.Customer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PetType type;

    private String name;

    // Map to Customer entity
    @ManyToOne
    @JoinColumn(name="customer_id", nullable=false)
    private Customer customer;

    private LocalDate birthDate;

    private String notes;

    // Map to Schedule
    @ManyToOne
    @JoinColumn(name="schedule_id")
    private Schedule schedule;

}
