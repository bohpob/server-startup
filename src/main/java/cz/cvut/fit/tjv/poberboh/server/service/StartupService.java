package cz.cvut.fit.tjv.poberboh.server.service;

import cz.cvut.fit.tjv.poberboh.server.converter.StartupConverter;
import cz.cvut.fit.tjv.poberboh.server.dto.StartupDTO;
import cz.cvut.fit.tjv.poberboh.server.entity.Investor;
import cz.cvut.fit.tjv.poberboh.server.entity.Owner;
import cz.cvut.fit.tjv.poberboh.server.entity.Startup;
import cz.cvut.fit.tjv.poberboh.server.exception.AlreadyExistException;
import cz.cvut.fit.tjv.poberboh.server.exception.NotFoundException;
import cz.cvut.fit.tjv.poberboh.server.repository.InvestorRepository;
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

    @Autowired
    private InvestorRepository investorRepository;

    public StartupDTO create(StartupDTO startupDTO, Integer id) throws AlreadyExistException, NotFoundException {
        Optional<Owner> owner = ownerRepository.findById(id);
        if (owner.isEmpty()) {
            throw new NotFoundException("Owner not found");
        }
        if (startupRepository.findByName(startupDTO.getName()).isPresent()) {
            throw new AlreadyExistException("Startup already exist");
        }
        Startup startup = new Startup(startupDTO.getName(), startupDTO.getInvestment(), owner.get());
        startupRepository.save(startup);
        return startupDTO;
    }

    public StartupDTO read(Integer id) throws NotFoundException {
        if (startupRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Startup not found");
        }
        return StartupConverter.fromModel(startupRepository.findById(id).get());
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
        Optional <Startup> startup = startupRepository.findById(id);
        if (startup.isEmpty()) {
            throw new NotFoundException("Startup not found");
        }
        for (Investor i : startup.get().getInvestors()) {
            if (investorRepository.findByUsername(i.getUsername()).isPresent()) {
                investorRepository.findByUsername(i.getUsername()).get().removeInvested(startup.get());
            }
        }
        startupRepository.deleteById(id);
    }

    public Optional<Startup> readById(Integer id) throws NotFoundException {
        return startupRepository.findById(id);
    }

    public StartupDTO addInvestor(Integer startupID, Integer investorID) throws NotFoundException, AlreadyExistException {
        if (startupRepository.findById(startupID).isEmpty()) {
            throw new NotFoundException("Startup not found");
        }
        if (investorRepository.findById(investorID).isEmpty()) {
            throw new NotFoundException("Investor not found");
        }
        for (Investor i : startupRepository.findById(startupID).get().getInvestors()) {
            if (i.getUsername().equals(investorRepository.findById(investorID).get().getUsername())) {
                throw new AlreadyExistException("Investor is already a startup investor");
            }
        }
        startupRepository.findById(startupID).get().addInvestor(investorRepository.findById(investorID).get());
        investorRepository.findById(investorID).get().setInvestments(startupRepository.findById(startupID).get());
        startupRepository.save(startupRepository.findById(startupID).get());
        return StartupConverter.fromModel(startupRepository.findById(startupID).get());
    }

    public void deleteInvestor(Integer startupID, Integer investorID) throws NotFoundException {
        if (startupRepository.findById(startupID).isEmpty()) {
            throw new NotFoundException("Startup not found");
        }
        if (investorRepository.findById(investorID).isEmpty()) {
            throw new NotFoundException("Investor not found");
        }
        boolean invested = false;
        for (Investor i : startupRepository.findById(startupID).get().getInvestors()) {
            if (i.getUsername().equals(investorRepository.findById(investorID).get().getUsername())) {
                investorRepository.findById(investorID).get().removeInvested(startupRepository.findById(startupID).get());
                startupRepository.findById(startupID).get().deleteInvestor(investorRepository.findById(investorID).get());
                startupRepository.save(startupRepository.findById(startupID).get());
                invested = true;
            }
        }
        if (!invested) {
            throw new NotFoundException("Investor was not a startup investor");
        }
    }
}
