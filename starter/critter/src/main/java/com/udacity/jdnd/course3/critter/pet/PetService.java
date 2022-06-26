package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetService {
    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    /** Save pet
     * @param petDTO
     *
     * return petDto
     * */
    public PetDTO savePet(PetDTO petDTO) {
        Pet pet = new Pet();
        try {

            convertToPet(pet, petDTO);
        } catch (EntityNotFoundException e) {

            return null;
        }
        petRepository.save(pet);
        // Save Pet

        return petDTO;
    }


    /** Get pet
     * @param petId
     *
     * @return petDto
     * */
    public PetDTO getPet(long petId) {

        Pet pet = petRepository.findById(petId)
                        .orElseThrow(() -> new EntityNotFoundException("Can't find the pet with ID = " + petId));

        return convertToPetDTO(pet);
    }

    /** Get a list Pet
     *
     * @return A list petDto
     * */
    public List<PetDTO> getPets() {

        List<Pet> petList = (List<Pet>) petRepository.findAll();

        return petList.stream().map(this::convertToPetDTO).collect(Collectors.toList());
    }

    /** Get Pet by ownerId
     * @param ownerId
     *
     * @return A list petDto
     * */
    public List<PetDTO> getPetsByOwner(long ownerId) {

        List<Pet> petList = petRepository.findByCustomer_CustomerId(ownerId);

        return petList.stream().map(this::convertToPetDTO).collect(Collectors.toList());

    }

    /** Convert from PetDTO to Pet
     * @param pet
     * @param petDTO
     *
     * */
    private void convertToPet(Pet pet, PetDTO petDTO) {

        Customer customer = customerRepository.findById(petDTO.getOwnerId())
                .orElseThrow(() -> new EntityNotFoundException(("Can't find Customer")));

        pet.setType(petDTO.getType());
        pet.setName(petDTO.getName());
        pet.setCustomer(customer);
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());

        // Set Schedule
//        pet.setSchedule(petDTO.);

    }

    /** Convert from Pet to PetDTO
     *
     * @param pet
     *
     * */
    private PetDTO convertToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();

        petDTO.setId(pet.getPetId());
        petDTO.setType(pet.getType());
        petDTO.setName(pet.getName());
        petDTO.setOwnerId(pet.getCustomer().getCustomerId());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setNotes(pet.getNotes());

        // Set Schedule

        return petDTO;
    }

}
