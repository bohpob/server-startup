package cz.cvut.fit.tjv.poberboh.server.controller;

import cz.cvut.fit.tjv.poberboh.server.dto.StartupDTO;
import cz.cvut.fit.tjv.poberboh.server.exception.AlreadyExistException;
import cz.cvut.fit.tjv.poberboh.server.exception.NotFoundException;
import cz.cvut.fit.tjv.poberboh.server.service.StartupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/startups")
public class StartupController {

    @Autowired
    private StartupService startupService;

    @PostMapping
    public StartupDTO create(@RequestBody StartupDTO startupDTO, @RequestParam Integer id) throws AlreadyExistException, NotFoundException {
        return startupService.create(startupDTO, id);
    }

    @GetMapping("/{id}")
    public StartupDTO read(@PathVariable Integer id) throws NotFoundException {
        return startupService.read(id);
    }

    @PutMapping("/{id}")
    public StartupDTO update(@PathVariable Integer id,@RequestBody StartupDTO startupDTO) throws NotFoundException {
        return startupService.update(id, startupDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) throws NotFoundException {
        startupService.delete(id);
    }

}
