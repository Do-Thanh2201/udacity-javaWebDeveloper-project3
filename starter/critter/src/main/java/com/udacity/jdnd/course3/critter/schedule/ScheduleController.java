package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {

        ScheduleDTO scheduleDTOResponse = scheduleService.createSchedule(scheduleDTO);
        if(scheduleDTOResponse != null) {

            return scheduleDTOResponse;
        }
        throw new UnsupportedOperationException();
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> scheduleDTOList = scheduleService.getAllSchedules();
        if(scheduleDTOList.isEmpty()) {

            throw new UnsupportedOperationException();
        }

        return scheduleDTOList;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {

        List<ScheduleDTO> scheduleDTOList = scheduleService.getScheduleForPet(petId);
        if(scheduleDTOList.isEmpty()) {

            throw new UnsupportedOperationException();
        }
        return scheduleDTOList;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {

        List<ScheduleDTO> scheduleDTOList = scheduleService.getScheduleForEmployee(employeeId);
        if(scheduleDTOList.isEmpty()) {

            throw new UnsupportedOperationException();
        }
        return scheduleDTOList;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {

        List<ScheduleDTO> scheduleDTOList = scheduleService.getScheduleForCustomer(customerId);
        if(scheduleDTOList.isEmpty()) {

            throw new UnsupportedOperationException();
        }
        return scheduleDTOList;
    }
}
