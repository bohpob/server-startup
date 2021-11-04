package cz.cvut.fit.tjv.poberboh.server.controller;

import cz.cvut.fit.tjv.poberboh.server.dto.InvestorDTO;
import cz.cvut.fit.tjv.poberboh.server.exception.UserAlreadyExistException;
import cz.cvut.fit.tjv.poberboh.server.exception.UserNotFoundException;
import cz.cvut.fit.tjv.poberboh.server.service.InvestorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/investors")
public class InvestorController {

    @Autowired
    private InvestorService investorService;

    @PostMapping
    public InvestorDTO create(@RequestBody InvestorDTO investorDTO) throws UserAlreadyExistException {
        return investorService.create(investorDTO);
    }

    @GetMapping("/{id}")
    public InvestorDTO read(@PathVariable Integer id) throws UserNotFoundException {
        return investorService.read(id);
    }

    @PutMapping("/{id}")
    public InvestorDTO update(@PathVariable Integer id, @RequestBody InvestorDTO investorDTO) throws UserNotFoundException {
        return investorService.update(id, investorDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) throws UserNotFoundException {
        investorService.delete(id);
    }

}
