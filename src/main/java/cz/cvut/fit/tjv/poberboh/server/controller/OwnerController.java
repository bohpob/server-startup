package cz.cvut.fit.tjv.poberboh.server.controller;

import cz.cvut.fit.tjv.poberboh.server.dto.OwnerDTO;
import cz.cvut.fit.tjv.poberboh.server.exception.AlreadyExistException;
import cz.cvut.fit.tjv.poberboh.server.exception.NotFoundException;
import cz.cvut.fit.tjv.poberboh.server.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OwnerDTO create(@RequestBody OwnerDTO ownerDTO) throws AlreadyExistException {
        return ownerService.create(ownerDTO);
    }

    @GetMapping("/{id}")
    public OwnerDTO read(@PathVariable Integer id) throws NotFoundException {
        return ownerService.read(id);
    }

    @PutMapping("/{id}")
    public OwnerDTO update(@PathVariable Integer id, @RequestBody OwnerDTO ownerDTO) throws NotFoundException {
        return ownerService.update(id, ownerDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) throws NotFoundException {
        if (ownerService.readById(id).isEmpty()) {
            throw new NotFoundException("Owner not found");
        } else {
            ownerService.delete(id);
        }
    }

    @GetMapping
    private List<OwnerDTO> readAll() {
        return ownerService.readAll();
    }
}
