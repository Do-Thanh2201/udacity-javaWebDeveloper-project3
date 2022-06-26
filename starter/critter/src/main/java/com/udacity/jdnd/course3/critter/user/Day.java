package com.udacity.jdnd.course3.critter.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long dayId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private DayOfWeek dayOfWeek;

    public Day(DayOfWeek day) {
        this.dayOfWeek = day;
    }

    public Day() {
    }
}
