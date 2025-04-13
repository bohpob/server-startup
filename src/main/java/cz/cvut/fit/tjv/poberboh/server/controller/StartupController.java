package cz.cvut.fit.tjv.poberboh.server.controller;

import cz.cvut.fit.tjv.poberboh.server.dto.StartupDTO;
import cz.cvut.fit.tjv.poberboh.server.exception.AlreadyExistException;
import cz.cvut.fit.tjv.poberboh.server.exception.NotFoundException;
import cz.cvut.fit.tjv.poberboh.server.service.StartupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/startups")
public class StartupController {

    @Autowired
    private StartupService startupService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StartupDTO create(@RequestBody StartupDTO startupDTO, @RequestParam Integer id) throws AlreadyExistException, NotFoundException {
        return startupService.create(startupDTO, id);
    }

    @GetMapping("/{id}")
    public StartupDTO read(@PathVariable Integer id) throws NotFoundException {
        return startupService.read(id);
    }

    @PutMapping("/{id}")
    public StartupDTO update(@PathVariable Integer id, @RequestBody StartupDTO startupDTO) throws NotFoundException {
        return startupService.update(id, startupDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) throws NotFoundException {
        if (startupService.readById(id).isEmpty()) {
            throw new NotFoundException("Startup not found");
        } else {
            startupService.delete(id);
        }
    }

    @RequestMapping("/add_investor/{id}")
    public StartupDTO addInvestor(@PathVariable Integer id, @RequestParam Integer investorID) throws NotFoundException, AlreadyExistException {
        return startupService.addInvestor(id, investorID);
    }

    @DeleteMapping("/delete_investor/{id}")
    public void deleteInvestor(@PathVariable Integer id, @RequestParam Integer investorID) throws NotFoundException {
        startupService.deleteInvestor(id, investorID);
    }

    @GetMapping
    private List<StartupDTO> readAll() {
        return startupService.readAll();
    }
}
