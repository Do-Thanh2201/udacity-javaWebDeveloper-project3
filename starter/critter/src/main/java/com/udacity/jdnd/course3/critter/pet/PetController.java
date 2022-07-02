package com.udacity.jdnd.course3.critter.pet;

import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private  final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        PetDTO petDTOResponse = petService.savePet(petDTO);
        if (petDTOResponse != null) {

            return petDTOResponse;
        }
//        throw new UnsupportedOperationException();
        return null;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {

        try {

            PetDTO petDTOResponse = petService.getPet(petId);
            return petDTOResponse;
        } catch (EntityNotFoundException e) {

            //        throw new UnsupportedOperationException();
            return null;
        }
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<PetDTO> petDTOList = petService.getPets();

        if (petDTOList.isEmpty())
        {

//        throw new UnsupportedOperationException();
            return null;        }
        return petDTOList;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {

        List<PetDTO> petDTOList = petService.getPetsByOwner(ownerId);

        if (petDTOList.isEmpty())
        {

//        throw new UnsupportedOperationException();
            return null;
        }
        return petDTOList;
    }

}
