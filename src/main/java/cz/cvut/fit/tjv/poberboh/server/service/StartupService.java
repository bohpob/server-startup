package cz.cvut.fit.tjv.poberboh.server.service;

import cz.cvut.fit.tjv.poberboh.server.converter.StartupConverter;
import cz.cvut.fit.tjv.poberboh.server.dto.StartupDTO;
import cz.cvut.fit.tjv.poberboh.server.entity.Startup;
import cz.cvut.fit.tjv.poberboh.server.exception.UserAlreadyExistException;
import cz.cvut.fit.tjv.poberboh.server.exception.NotFoundException;
import cz.cvut.fit.tjv.poberboh.server.repository.StartupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class StartupService {

    @Autowired
    private StartupRepository startupRepository;

    public StartupDTO create(StartupDTO startupDTO) throws UserAlreadyExistException {
        if(startupRepository.findById(StartupConverter.toModel(startupDTO).getId()).orElse(null) != null) {
            throw new UserAlreadyExistException(StartupConverter.toModel(startupDTO).toString() + " already exist");
        }
        startupRepository.save(StartupConverter.toModel(startupDTO));
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
        if(id != null) {
            Startup newStartup = startupRepository.findById(id).get();
            newStartup.setName(startupDTO.getName());
            newStartup.setInvestment(startupDTO.getInvestment());
            return StartupConverter.fromModel(startupRepository.save(newStartup));
        } else {
            throw new NotFoundException("User not found");
        }
    }

    public void delete(Integer id) throws NotFoundException {
        if(id != null) {
            if(startupRepository.findById(id).isPresent()) {
                startupRepository.deleteById(id);
            } else {
                throw new NotFoundException("User not found");
            }
        } else {
            throw new NotFoundException("User not found");
        }
    }
    
}
