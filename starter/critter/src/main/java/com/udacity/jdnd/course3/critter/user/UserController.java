package com.udacity.jdnd.course3.critter.user;

import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final EmployeeService employeeService;
    private final CustomerService customerService;

    public UserController(EmployeeService employeeService, CustomerService customerService) {
        this.employeeService = employeeService;
        this.customerService = customerService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        CustomerDTO customerDTOResponse = customerService.saveCustomer(customerDTO);
        if (customerDTOResponse != null) {

            return customerDTOResponse;
        }
//        throw new UnsupportedOperationException();
        return null;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){

        List<CustomerDTO> customerDTOList = customerService.getAllCustomers();
        if(customerDTOList == null) {

//        throw new UnsupportedOperationException();
            return null;
        }
        return customerDTOList;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        try {

            CustomerDTO customerDTO = customerService.getOwnerByPet(petId);
            return  customerDTO;
        } catch (EntityNotFoundException e) {

//        throw new UnsupportedOperationException();
            return null;
        }

    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {

        EmployeeDTO employeeDTOResponse = employeeService.saveEmployee(employeeDTO);
        if (employeeDTOResponse != null) {

            return employeeDTOResponse;
        }
//        throw new UnsupportedOperationException();
        return null;

    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        try {

            EmployeeDTO employeeDTO = employeeService.getEmployee(employeeId);
            return employeeDTO;
        } catch (EntityNotFoundException e) {

//        throw new UnsupportedOperationException();
            return null;
        }
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        try {

            employeeService.setAvailability(daysAvailable, employeeId);
        } catch (EntityNotFoundException e) {

//        throw new UnsupportedOperationException();
        }
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {

        try {
            List<EmployeeDTO> employeeDTOList = employeeService.findEmployeesForService(employeeDTO);
            return employeeDTOList;
        } catch (EntityNotFoundException e) {

//        throw new UnsupportedOperationException();
            return null;
        }
    }

}
