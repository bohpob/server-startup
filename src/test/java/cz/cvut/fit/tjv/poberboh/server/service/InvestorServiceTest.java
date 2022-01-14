package cz.cvut.fit.tjv.poberboh.server.service;

import cz.cvut.fit.tjv.poberboh.server.dto.InvestorDTO;
import cz.cvut.fit.tjv.poberboh.server.entity.Investor;
import cz.cvut.fit.tjv.poberboh.server.entity.Startup;
import cz.cvut.fit.tjv.poberboh.server.repository.InvestorRepository;
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
class InvestorServiceTest {

    @Autowired
    private InvestorService investorService;

    @MockBean
    private InvestorRepository investorRepository;

    @MockBean
    private StartupRepository startupRepository;

    private final Investor investor = new Investor("Username", "Firstname", "Lastname");
    private final InvestorDTO investorDTO = new InvestorDTO( "Username", "Firstname", "Lastname");
    private final InvestorDTO newInvestorDTO = new InvestorDTO("NewUsername", "newFirstName","newLastName");
    private final Startup startup = new Startup( 100,"startupName", 0);

    @Test
    void createInvestorTest() {
        BDDMockito.given(startupRepository.findById(startup.getId())).willReturn(java.util.Optional.of(startup));

        List<Integer> startupDTOList = new ArrayList<>();
        startupDTOList.add(100);
        investorDTO.setStartupIds(startupDTOList);

        InvestorDTO newInvestor = investorService.create(investorDTO, startup.getId());

        Assertions.assertEquals(investorDTO.getUsername(), newInvestor.getUsername());
        Assertions.assertEquals(investorDTO.getFirstname(), newInvestor.getFirstname());
        Assertions.assertEquals(investorDTO.getLastname(), newInvestor.getLastname());
        Assertions.assertEquals(investorDTO.getStartupList(), newInvestor.getStartupList());

        Mockito.verify(startupRepository,Mockito.times(1)).findById(startup.getId());
        Mockito.verify(investorRepository, Mockito.times(1)).save(investor);
        Mockito.verify(investorRepository, Mockito.times(1)).findByUsername(investorDTO.getUsername());
    }

    @Test
    void readInvestorTest() {
        BDDMockito.given(investorRepository.findById(1)).willReturn(java.util.Optional.of(investor));

        InvestorDTO investorDTO = investorService.read(1);

        Assertions.assertEquals(investorDTO.getUsername(), investor.getUsername());
        Assertions.assertEquals(investorDTO.getFirstname(), investor.getFirstname());
        Assertions.assertEquals(investorDTO.getLastname(), investor.getLastname());
        Assertions.assertEquals(investorDTO.getStartupList(), investor.getStartupsId());

        Mockito.verify(investorRepository, Mockito.times(1)).findById(any(Integer.TYPE));
    }

    @Test
    void updateInvestorTest() {
        BDDMockito.given(investorRepository.findById(1)).willReturn(java.util.Optional.of(investor));
        BDDMockito.given(investorRepository.save(investor)).willReturn(investor);

        InvestorDTO investorDTOTest = investorService.update(1, newInvestorDTO);

        Assertions.assertEquals(investor.getUsername(), investorDTOTest.getUsername());
        Assertions.assertEquals(investor.getFirstname(), investorDTOTest.getFirstname());
        Assertions.assertEquals(investor.getLastname(), investorDTOTest.getLastname());
        Assertions.assertEquals(investor.getUsername(), newInvestorDTO.getUsername());
        Assertions.assertEquals(investor.getFirstname(), newInvestorDTO.getFirstname());
        Assertions.assertEquals(investor.getLastname(), newInvestorDTO.getLastname());

        Mockito.verify(investorRepository, Mockito.times(1)).findById(any(Integer.TYPE));
        Mockito.verify(investorRepository, Mockito.times(1)).save(investor);
    }

    @Test
    void deleteInvestorTest() {
        investor.setInvestments(startup);
        List<Startup> startupList = new ArrayList<>();
        startupList.add(startup);
        Assertions.assertEquals(investor.getInvestments(), startupList);

        List<Investor> investorList = new ArrayList<>();
        investorList.add(investor);
        startup.setInvestors(investorList);
        Assertions.assertEquals(investorList, startup.getInvestors());
        startup.deleteInvestor(investor);
        List<Investor> nullInvestorsList = new ArrayList<>();
        Assertions.assertEquals(nullInvestorsList, startup.getInvestors());

        BDDMockito.given(investorRepository.findById(investor.getId())).willReturn(Optional.of(investor));
        investorService.delete(investor.getId());
        Mockito.verify(investorRepository, Mockito.atLeastOnce()).deleteById(investor.getId());
        Mockito.verify(investorRepository, Mockito.times(1)).deleteById(investor.getId());
    }
}