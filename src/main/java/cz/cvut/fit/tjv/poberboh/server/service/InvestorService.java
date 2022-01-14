package cz.cvut.fit.tjv.poberboh.server.service;

import cz.cvut.fit.tjv.poberboh.server.converter.InvestorConverter;
import cz.cvut.fit.tjv.poberboh.server.dto.InvestorDTO;
import cz.cvut.fit.tjv.poberboh.server.entity.Investor;
import cz.cvut.fit.tjv.poberboh.server.entity.Startup;
import cz.cvut.fit.tjv.poberboh.server.exception.AlreadyExistException;
import cz.cvut.fit.tjv.poberboh.server.exception.NotFoundException;
import cz.cvut.fit.tjv.poberboh.server.repository.InvestorRepository;
import cz.cvut.fit.tjv.poberboh.server.repository.StartupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InvestorService {

    @Autowired
    private InvestorRepository investorRepository;
    
    @Autowired
    private StartupRepository startupRepository;

    public InvestorDTO create(InvestorDTO investorDTO, Integer startupID) throws AlreadyExistException, NotFoundException {
        Optional<Startup> startup = startupRepository.findById(startupID);
        if (startup.isEmpty()) {
            throw new NotFoundException("Startup not found");
        }
        if (investorRepository.findByUsername(investorDTO.getUsername()).isPresent()) {
            throw new AlreadyExistException("Investor already exist");
        }
        Investor investor = new Investor(investorDTO.getUsername(), investorDTO.getFirstname(), investorDTO.getLastname());
        investor.setInvestments(startup.get());
        investorDTO.addStartup(startupID);
        investorRepository.save(investor);
        return investorDTO;
    }

    public InvestorDTO read(Integer id) throws NotFoundException {
        Optional<Investor> investor = investorRepository.findById(id);
        if (investor.isEmpty()) {
            throw new NotFoundException("Investor not found");
        }
        Investor investorToDTO = investor.get();
        return InvestorConverter.fromModel(investorToDTO);
    }

    public InvestorDTO update(Integer id, InvestorDTO investorDTO) throws NotFoundException {
        Optional<Investor> investor = investorRepository.findById(id);
        if (investor.isEmpty()) {
            throw new NotFoundException("Investor not found");
        }
        investor.get().setUsername(investorDTO.getUsername());
        investor.get().setFirstname(investorDTO.getFirstname());
        investor.get().setLastname(investorDTO.getLastname());
        return InvestorConverter.fromModel(investorRepository.save(investor.get()));
    }

    public void delete(Integer id) throws NotFoundException {
        Optional<Investor> investor = investorRepository.findById(id);
        if (investor.isEmpty()) {
            throw new NotFoundException("Investor not found");
        }
        for (Startup startup : investor.get().getInvestments()) {
            if (startupRepository.findByName(startup.getName()).isPresent()) {
                startupRepository.findByName(startup.getName()).get().deleteInvestor(investor.get());
            }
        }
        investorRepository.deleteById(id);
    }

    public Optional<Investor> readById(Integer id) {
        return investorRepository.findById(id);
    }
}
