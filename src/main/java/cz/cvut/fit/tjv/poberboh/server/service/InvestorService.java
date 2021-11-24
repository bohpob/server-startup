package cz.cvut.fit.tjv.poberboh.server.service;

import cz.cvut.fit.tjv.poberboh.server.converter.InvestorConverter;
import cz.cvut.fit.tjv.poberboh.server.dto.InvestorDTO;
import cz.cvut.fit.tjv.poberboh.server.entity.Investor;
import cz.cvut.fit.tjv.poberboh.server.exception.UserAlreadyExistException;
import cz.cvut.fit.tjv.poberboh.server.exception.NotFoundException;
import cz.cvut.fit.tjv.poberboh.server.repository.InvestorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

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

    public InvestorDTO read(Integer id) throws NotFoundException {
        Optional<Investor> investor = investorRepository.findById(id);
        if (investor.isEmpty()) {
            throw new NotFoundException("Investor not found");
        }
        Investor investorToDTO = investor.get();
        return InvestorConverter.fromModel(investorToDTO);
    }

    public InvestorDTO update(Integer id, InvestorDTO investorDTO) throws NotFoundException {
        if(id != null) {
            Investor newInvestor = investorRepository.findById(id).get();
            newInvestor.setFirstname(investorDTO.getFirstname());
            newInvestor.setLastname(investorDTO.getLastname());
            return InvestorConverter.fromModel(investorRepository.save(newInvestor));
        } else {
            throw new NotFoundException("User not found");
        }
    }

    public void delete(Integer id) throws NotFoundException {
        if(id != null) {
            if(investorRepository.findById(id).isPresent()) {
                investorRepository.deleteById(id);
            } else {
                throw new NotFoundException("User not found");
            }
        } else {
            throw new NotFoundException("User not found");
        }
    }

}
