package cz.cvut.fit.tjv.poberboh.server.service;

import cz.cvut.fit.tjv.poberboh.server.converter.InvestorConverter;
import cz.cvut.fit.tjv.poberboh.server.dto.InvestorDTO;
import cz.cvut.fit.tjv.poberboh.server.entity.Investor;
import cz.cvut.fit.tjv.poberboh.server.exception.UserAlreadyExistException;
import cz.cvut.fit.tjv.poberboh.server.exception.UserNotFoundException;
import cz.cvut.fit.tjv.poberboh.server.repository.InvestorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvestorService{

    @Autowired
    private InvestorRepository investorRepository;

    public InvestorDTO create(InvestorDTO investorDTO) throws UserAlreadyExistException {
        if(investorRepository.findById(InvestorConverter.toModel(investorDTO).getId()).orElse(null) != null) {
            throw new UserAlreadyExistException(InvestorConverter.toModel(investorDTO).toString() + " already exist");
        }
        investorRepository.save(InvestorConverter.toModel(investorDTO));
        return investorDTO;
    }

    public InvestorDTO read(Integer id) throws UserNotFoundException {
        InvestorDTO investorDTO = InvestorConverter.fromModel(investorRepository.findById(id).get());
        if(investorRepository.existsById(InvestorConverter.toModel(investorDTO).getId())) {
            throw new UserNotFoundException("User not found");
        }
        return investorDTO;
    }

    public InvestorDTO update(Integer id, InvestorDTO investorDTO) throws UserNotFoundException {
        if(id != null) {
            Investor newInvestor = investorRepository.findById(id).get();
            newInvestor.setFirstname(investorDTO.getFirstname());
            newInvestor.setLastname(investorDTO.getLastname());
            return InvestorConverter.fromModel(investorRepository.save(newInvestor));
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    public void delete(Integer id) throws UserNotFoundException {
        if(id != null) {
            if(investorRepository.findById(id).isPresent()) {
                investorRepository.deleteById(id);
            } else {
                throw new UserNotFoundException("User not found");
            }
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

}
