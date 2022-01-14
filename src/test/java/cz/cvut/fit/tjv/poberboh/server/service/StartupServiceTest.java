package cz.cvut.fit.tjv.poberboh.server.service;

import cz.cvut.fit.tjv.poberboh.server.dto.StartupDTO;
import cz.cvut.fit.tjv.poberboh.server.entity.Investor;
import cz.cvut.fit.tjv.poberboh.server.entity.Owner;
import cz.cvut.fit.tjv.poberboh.server.entity.Startup;
import cz.cvut.fit.tjv.poberboh.server.repository.InvestorRepository;
import cz.cvut.fit.tjv.poberboh.server.repository.OwnerRepository;
import cz.cvut.fit.tjv.poberboh.server.repository.StartupRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class StartupServiceTest {

    @Autowired
    private StartupService startupService;

    @MockBean
    private StartupRepository startupRepository;

    @MockBean
    private InvestorRepository investorRepository;

    @MockBean
    private OwnerRepository ownerRepository;

    Owner owner = new Owner("Username", "Firstname", "Lastname");
    Startup startup = new Startup("Name", 100);
    StartupDTO startupDTO = new StartupDTO("Name", 100);
    StartupDTO newStartupDTO = new StartupDTO("NewStartup", 200);
    Investor investor = new Investor("Username", "Firstname", "Lastname");
    Investor newInvestor = new Investor("NewUsername", "NewFirstname", "NewLastname");

    @Test
    void createStartupTest() {
        BDDMockito.given(ownerRepository.findById(1)).willReturn(Optional.of(owner));
        BDDMockito.given(startupRepository.findByName(startupDTO.getName())).willReturn(Optional.empty());
        startup.setOwner(owner);

        StartupDTO returnedStartupDTO = startupService.create(startupDTO, 1);

        Assertions.assertEquals(returnedStartupDTO.getName(), startupDTO.getName());
        Assertions.assertEquals(returnedStartupDTO.getInvestment(), startupDTO.getInvestment());

        Mockito.verify(ownerRepository, Mockito.times(1)).findById(1);
        Mockito.verify(startupRepository, Mockito.times(1)).findByName(startupDTO.getName());
        Mockito.verify(startupRepository, Mockito.times(1)).save(startup);
    }

    @Test
    void readStartupTest() {
        BDDMockito.given(startupRepository.findById(1)).willReturn(java.util.Optional.of(startup));
        List<Investor> investorList = new ArrayList<>();
        investorList.add(investor);
        startup.setInvestors(investorList);

        StartupDTO returnedStartupDTO = startupService.read(1);

        Assertions.assertEquals(returnedStartupDTO.getName(), startup.getName());
        Assertions.assertEquals(returnedStartupDTO.getInvestment(), startup.getInvestment());
        Assertions.assertEquals(returnedStartupDTO.getInvestorList().get(0).getUsername(), startup.getInvestors().get(0).getUsername());

        Mockito.verify(startupRepository, Mockito.times(2)).findById(any(Integer.TYPE));
    }

    @Test
    void updateStartupTest() {
        BDDMockito.given(startupRepository.findById(1)).willReturn(java.util.Optional.of(startup));
        BDDMockito.given(startupRepository.save(startup)).willReturn(startup);
        List<Investor> investorList = new ArrayList<>();
        investorList.add(investor);
        startup.setInvestors(investorList);

        StartupDTO returnedStartupDTO = startupService.update(1, newStartupDTO);

        Assertions.assertEquals(startup.getName(), returnedStartupDTO.getName());
        Assertions.assertEquals(startup.getInvestment(), returnedStartupDTO.getInvestment());
        Assertions.assertEquals(startup.getInvestors().get(0).getUsername(), returnedStartupDTO.getInvestorList().get(0).getUsername());

        Mockito.verify(startupRepository, Mockito.times(1)).findById(any(Integer.TYPE));
        Mockito.verify(startupRepository, Mockito.times(1)).save(startup);
    }

    @Test
    void deleteStartupTest() {
        BDDMockito.given(investorRepository.findByUsername(investor.getUsername())).willReturn(Optional.of(investor));
        List<Investor> investorList = new ArrayList<>();
        investorList.add(investor);
        startup.setInvestors(investorList);
        Assertions.assertEquals(investorList, startup.getInvestors());

        investor.setInvestments(startup);
        List<Startup> startupList = new ArrayList<>();
        startupList.add(startup);
        Assertions.assertEquals(investor.getInvestments(), startupList);
        investor.removeInvested(startup);
        List<Integer> nullInvestorsList = new ArrayList<>();
        Assertions.assertEquals(nullInvestorsList, investor.getStartupsId());

        BDDMockito.given(startupRepository.findById(1)).willReturn(Optional.of(startup));
        startupService.delete(1);
        Mockito.verify(startupRepository, Mockito.atLeastOnce()).deleteById(1);
        Mockito.verify(startupRepository, Mockito.times(1)).deleteById(1);
    }

    @Test
    void addInvestorTest() {
        BDDMockito.given(startupRepository.findById(startup.getId())).willReturn(Optional.of(startup));
        BDDMockito.given(investorRepository.findById(investor.getId())).willReturn(Optional.of(newInvestor));
        List<Investor> investorList = new ArrayList<>();
        startup.setInvestors(investorList);
        Assertions.assertEquals(investorList, startup.getInvestors());

        investorList.add(investor);
        startup.setInvestors(investorList);

        StartupDTO returnedInvestorDTO = startupService.addInvestor(startup.getId(), investor.getId());

        Assertions.assertEquals(returnedInvestorDTO.getInvestorList().get(0).getUsername(), startup.getInvestors().get(0).getUsername());
        Mockito.verify(startupRepository, Mockito.times(6)).findById(startup.getId());
        Mockito.verify(startupRepository, Mockito.times(1)).save(startup);
        Mockito.verify(investorRepository, Mockito.times(4)).findById(investor.getId());
    }
}