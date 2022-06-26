package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;

    public CustomerService(CustomerRepository customerRepository, PetRepository petRepository) {
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }

    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {

        Customer customer = new Customer();
        String message = this.convertToCustomer(customer, customerDTO);

        if(message != null) {

            return customerDTO;
        }
         return null;
    }



    public List<CustomerDTO> getAllCustomers() {

        List<Customer> customerList = (List<Customer>) customerRepository.findAll();
        if(customerList.isEmpty()) {

            return null;
        }
        return customerList.stream().map(this::convertToCustomerDTO).collect(Collectors.toList());
    }

    public CustomerDTO getOwnerByPet(long petId) {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new EntityNotFoundException("Can't find the petId: " + petId));
        Customer customer = pet.getCustomer();
        return this.convertToCustomerDTO(customer);
    }

    /** Convert from CustomerDTO to Customer
     * @param customer
     * @param customerDTO
     *
     * */
    private String convertToCustomer(Customer customer, CustomerDTO customerDTO) {

        customer.setName(customerDTO.getName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setNotes(customerDTO.getNotes());
        List<Pet> petList = petRepository.findByCustomer_CustomerId(customerDTO.getId());
        if(petList.isEmpty()) {

            return "Can't pet with customerId: " + customerDTO.getId();
        }
        customer.setPets(petList);
        return null;
    }

    /** Convert from Customer to CustomerDTO
     *
     * @param customer
     *
     * @return customerDTO
     * */
    private CustomerDTO convertToCustomerDTO(Customer customer) {

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getCustomerId());
        customerDTO.setName(customer.getName());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setNotes(customer.getNotes());

        List<Pet> petList = customer.getPets();
        customerDTO.setPetIds(petList.stream().map(Pet::getPetId).collect(Collectors.toList()));
        return customerDTO;
    }
}
