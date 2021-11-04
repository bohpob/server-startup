package cz.cvut.fit.tjv.poberboh.server.service;

import cz.cvut.fit.tjv.poberboh.server.converter.OwnerConverter;
import cz.cvut.fit.tjv.poberboh.server.dto.OwnerDTO;
import cz.cvut.fit.tjv.poberboh.server.entity.Owner;
import cz.cvut.fit.tjv.poberboh.server.exception.UserAlreadyExistException;
import cz.cvut.fit.tjv.poberboh.server.exception.UserNotFoundException;
import cz.cvut.fit.tjv.poberboh.server.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    public OwnerDTO create(OwnerDTO ownerDTO) throws UserAlreadyExistException {
        if(ownerRepository.findById(OwnerConverter.toModel(ownerDTO).getId()).orElse(null) != null) {
            throw new UserAlreadyExistException(OwnerConverter.toModel(ownerDTO).toString() + " already exist");
        }
        ownerRepository.save(OwnerConverter.toModel(ownerDTO));
        return ownerDTO;
    }

    public OwnerDTO read(Integer id) throws UserNotFoundException {
        OwnerDTO ownerDTO = OwnerConverter.fromModel(ownerRepository.findById(id).get());
        if(ownerRepository.existsById(OwnerConverter.toModel(ownerDTO).getId())) {
            throw new UserNotFoundException("User not found");
        }
        return ownerDTO;
    }

    public OwnerDTO update(Integer id, OwnerDTO ownerDTO) throws UserNotFoundException {
        if(id != null) {
            Owner newOwner = ownerRepository.findById(id).get();
            newOwner.setFirstname(ownerDTO.getFirstname());
            newOwner.setLastname(ownerDTO.getLastname());
            return OwnerConverter.fromModel(ownerRepository.save(newOwner));
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    public void delete(Integer id) throws UserNotFoundException {
        if(id != null) {
            if(ownerRepository.findById(id).isPresent()) {
                ownerRepository.deleteById(id);
            } else {
                throw new UserNotFoundException("User not found");
            }
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

}
