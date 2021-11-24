package cz.cvut.fit.tjv.poberboh.server.service;

import cz.cvut.fit.tjv.poberboh.server.converter.StartupConverter;
import cz.cvut.fit.tjv.poberboh.server.dto.StartupDTO;
import cz.cvut.fit.tjv.poberboh.server.entity.Owner;
import cz.cvut.fit.tjv.poberboh.server.entity.Startup;
import cz.cvut.fit.tjv.poberboh.server.exception.AlreadyExistException;
import cz.cvut.fit.tjv.poberboh.server.exception.NotFoundException;
import cz.cvut.fit.tjv.poberboh.server.repository.OwnerRepository;
import cz.cvut.fit.tjv.poberboh.server.repository.StartupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class StartupService {

    @Autowired
    private StartupRepository startupRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    public StartupDTO create(StartupDTO startupDTO, Integer id) throws AlreadyExistException, NotFoundException {
        Optional<Owner> owner = ownerRepository.findById(id);
        if (owner.isEmpty()) {
            throw new NotFoundException("Owner not found");
        }
        //startupDTO.setOwner(owner.get());
        Startup startup = new Startup(startupDTO.getName(), startupDTO.getInvestment(), owner.get());
//        if(startupRepository.findById(StartupConverter.toModel(startupDTO).getId()).orElse(null) != null) {
//            throw new AlreadyExistException(StartupConverter.toModel(startupDTO).toString() + " already exist");
//        }
        startupRepository.save(startup);
        return startupDTO;
    }

    public StartupDTO read(Integer id) throws NotFoundException {
        Optional<Startup> startup = startupRepository.findById(id);
        if (startup.isEmpty()) {
            throw new NotFoundException("Startup not found");
        }
        Startup startupToDTO = startup.get();
        return StartupConverter.fromModel(startupToDTO);
    }

    public StartupDTO update(Integer id, StartupDTO startupDTO) throws NotFoundException {
        Optional<Startup> startup = startupRepository.findById(id);
        if (startup.isEmpty()) {
            throw new NotFoundException("Startup not found");
        }
        startup.get().setName(startupDTO.getName());
        startup.get().setInvestment(startupDTO.getInvestment());
        return StartupConverter.fromModel(startupRepository.save(startup.get()));
    }

    public void delete(Integer id) throws NotFoundException {
        if (startupRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Startup not found");
        } else {
            startupRepository.deleteById(id);
        }
    }
    
}
