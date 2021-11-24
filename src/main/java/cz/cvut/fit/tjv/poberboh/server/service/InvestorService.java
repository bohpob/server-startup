package cz.cvut.fit.tjv.poberboh.server.service;

import cz.cvut.fit.tjv.poberboh.server.converter.InvestorConverter;
import cz.cvut.fit.tjv.poberboh.server.dto.InvestorDTO;
import cz.cvut.fit.tjv.poberboh.server.entity.Investor;
import cz.cvut.fit.tjv.poberboh.server.exception.AlreadyExistException;
import cz.cvut.fit.tjv.poberboh.server.exception.NotFoundException;
import cz.cvut.fit.tjv.poberboh.server.repository.InvestorRepository;
import cz.cvut.fit.tjv.poberboh.server.repository.StartupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class InvestorService{

    @Autowired
    private InvestorRepository investorRepository;
    
    @Autowired
    private StartupRepository startupRepository;

    public InvestorDTO create(InvestorDTO investorDTO, ArrayList<Integer> ids) throws AlreadyExistException {
        for (Integer id : ids) {
            if(startupRepository.findById(id).isPresent()) {
                startupRepository.findById(id).get().setInvestor(InvestorConverter.toModel(investorDTO));
            }
        }
        if(investorRepository.findById(InvestorConverter.toModel(investorDTO).getId()).orElse(null) != null) {
            throw new AlreadyExistException(InvestorConverter.toModel(investorDTO).toString() + " already exist");
        }
        investorRepository.save(InvestorConverter.toModel(investorDTO));
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
        investor.get().setFirstname(investorDTO.getFirstname());
        investor.get().setLastname(investorDTO.getLastname());
        return InvestorConverter.fromModel(investorRepository.save(investor.get()));
    }

    public void delete(Integer id) throws NotFoundException {
        if (investorRepository.findById(id).isEmpty()) {
            throw new NotFoundException("User not found");
        } else {
            investorRepository.deleteById(id);
        }
    }

}
