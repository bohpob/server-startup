package cz.cvut.fit.tjv.poberboh.server.service;

import cz.cvut.fit.tjv.poberboh.server.converter.OwnerConverter;
import cz.cvut.fit.tjv.poberboh.server.dto.OwnerDTO;
import cz.cvut.fit.tjv.poberboh.server.entity.Owner;
import cz.cvut.fit.tjv.poberboh.server.exception.AlreadyExistException;
import cz.cvut.fit.tjv.poberboh.server.exception.NotFoundException;
import cz.cvut.fit.tjv.poberboh.server.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    public OwnerDTO create(OwnerDTO ownerDTO) throws AlreadyExistException {
        if (ownerRepository.findByUsername(ownerDTO.getUsername()).isPresent()) {
            throw new AlreadyExistException("Owner already exist");
        }
        ownerRepository.save(OwnerConverter.toModel(ownerDTO));
        return ownerDTO;
    }

    public OwnerDTO read(Integer id) throws NotFoundException {
        Optional<Owner> owner = ownerRepository.findById(id);
        if(owner.isEmpty()) {
            throw new NotFoundException("Owner not found");
        }
        Owner ownerToDTO = owner.get();
        return OwnerConverter.fromModel(ownerToDTO);
    }

    public OwnerDTO update(Integer id, OwnerDTO ownerDTO) throws NotFoundException {
        Optional<Owner> owner = ownerRepository.findById(id);
        if (owner.isEmpty()) {
            throw new NotFoundException("Owner not found");
        }
        owner.get().setFirstname(ownerDTO.getFirstname());
        owner.get().setLastname(ownerDTO.getLastname());
        return OwnerConverter.fromModel(ownerRepository.save(owner.get()));
    }

    public void delete(Integer id) throws NotFoundException {
        if (ownerRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Owner not found");
        } else {
            ownerRepository.deleteById(id);
        }
    }
}
