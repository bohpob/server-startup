package cz.cvut.fit.tjv.poberboh.server.service;

import cz.cvut.fit.tjv.poberboh.server.dto.OwnerDTO;
import cz.cvut.fit.tjv.poberboh.server.entity.Owner;
import cz.cvut.fit.tjv.poberboh.server.entity.Startup;
import cz.cvut.fit.tjv.poberboh.server.repository.OwnerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class OwnerServiceTest {

    @Autowired
    private OwnerService ownerService;

    @MockitoBean
    private OwnerRepository ownerRepository;

    Owner owner = new Owner("Username", "Firstname", "Lastname");
    OwnerDTO ownerDTO = new OwnerDTO("Username", "Firstname", "Lastname");
    OwnerDTO newOwnerDTO = new OwnerDTO("NewUsername", "NewFirstname", "NewLastname");
    private final Startup startup = new Startup( 100,"startupName", 0);

    @Test
    void createOwnerTest() {
        BDDMockito.given(ownerRepository.findByUsername(ownerDTO.getUsername())).willReturn(Optional.empty());

        OwnerDTO returnedOwner = ownerService.create(ownerDTO);

        Assertions.assertEquals(returnedOwner.getUsername(), ownerDTO.getUsername());
        Assertions.assertEquals(returnedOwner.getFirstname(), ownerDTO.getFirstname());
        Assertions.assertEquals(returnedOwner.getLastname(), ownerDTO.getLastname());
        Assertions.assertEquals(returnedOwner.getStartupList(), ownerDTO.getStartupList());

        Mockito.verify(ownerRepository, Mockito.times(1)).findByUsername(ownerDTO.getUsername());
        Mockito.verify(ownerRepository, Mockito.times(1)).save(any(Owner.class));
    }

    @Test
    void readOwnerTest() {
        BDDMockito.given(ownerRepository.findById(1)).willReturn(java.util.Optional.of(owner));
        List<Startup> startupList = new ArrayList<>();
        startupList.add(startup);
        owner.setStartupList(startupList);

        OwnerDTO returnedOwnerDTO = ownerService.read(1);

        Assertions.assertEquals(returnedOwnerDTO.getUsername(), owner.getUsername());
        Assertions.assertEquals(returnedOwnerDTO.getFirstname(), owner.getFirstname());
        Assertions.assertEquals(returnedOwnerDTO.getLastname(), owner.getLastname());
        Assertions.assertEquals(returnedOwnerDTO.getStartupList().get(0).getName(), owner.getStartupList().get(0).getName());

        Mockito.verify(ownerRepository, Mockito.times(1)).findById(any(Integer.TYPE));
    }

    @Test
    void updateOwnerTest() {
        BDDMockito.given(ownerRepository.findById(1)).willReturn(java.util.Optional.of(owner));
        BDDMockito.given(ownerRepository.save(owner)).willReturn(owner);
        List<Startup> startupList = new ArrayList<>();
        startupList.add(startup);
        owner.setStartupList(startupList);

        OwnerDTO returnedOwnerDTO = ownerService.update(1, newOwnerDTO);

        Assertions.assertEquals(owner.getUsername(), returnedOwnerDTO.getUsername());
        Assertions.assertEquals(owner.getFirstname(), returnedOwnerDTO.getFirstname());
        Assertions.assertEquals(owner.getLastname(), returnedOwnerDTO.getLastname());
        Assertions.assertEquals(owner.getUsername(), newOwnerDTO.getUsername());
        Assertions.assertEquals(owner.getFirstname(), newOwnerDTO.getFirstname());
        Assertions.assertEquals(owner.getLastname(), newOwnerDTO.getLastname());
        Assertions.assertEquals(owner.getStartupList().get(0).getName(), returnedOwnerDTO.getStartupList().get(0).getName());

        Mockito.verify(ownerRepository, Mockito.times(1)).findById(any(Integer.TYPE));
        Mockito.verify(ownerRepository, Mockito.times(1)).save(owner);
    }

    @Test
    void deleteOwnerTest() {
        BDDMockito.given(ownerRepository.findById(1)).willReturn(Optional.of(owner));
        ownerService.delete(1);
        Mockito.verify(ownerRepository, Mockito.atLeastOnce()).deleteById(1);
        Mockito.verify(ownerRepository, Mockito.times(1)).deleteById(1);
    }
}