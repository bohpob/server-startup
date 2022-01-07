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
    InvestorRepository investorRepository;

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

    public StartupDTO addInvestor(Integer startupID, Integer investorID) throws NotFoundException, AlreadyExistException {
        Optional<Startup> startup = startupRepository.findById(startupID);
        if (startup.isEmpty()) {
            throw new NotFoundException("Startup not found");
        }
        Optional<Investor> investor = investorRepository.findById(investorID);
        if (investor.isEmpty()) {
            throw new NotFoundException("Investor not found");
        }
        for (Investor i : startup.get().getInvestors()) {
            if (i.getUsername().equals(investor.get().getUsername())) {
                throw new AlreadyExistException("Investor is already a startup investor");
            }
        }
        startup.get().addInvestor(investor.get());
        investor.get().setInvestments(startup.get());
        return StartupConverter.fromModel(startup.get());
    }

    public void deleteInvestor(Integer startupID, Integer investorID) throws NotFoundException {
        Optional<Startup> startup = startupRepository.findById(startupID);
        boolean invested = false;
        if (startup.isEmpty()) {
            throw new NotFoundException("Startup not found");
        }
        Optional<Investor> investor = investorRepository.findById(investorID);
        if (investor.isEmpty()) {
            throw new NotFoundException("Investor not found");
        }
        for (Investor i : startup.get().getInvestors()) {
            if (i.getUsername().equals(investor.get().getUsername())) {
                startup.get().deleteInvestor(investor.get());
                invested = true;
            }
        }
        investor.get().removeInvested(startup.get());
        if (!invested) {
            throw new NotFoundException("Еhe investor was not a startup investor");
        }
    }
}
