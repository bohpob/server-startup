package cz.cvut.fit.tjv.poberboh.server.controller;

import cz.cvut.fit.tjv.poberboh.server.dto.InvestorDTO;
import cz.cvut.fit.tjv.poberboh.server.entity.Investor;
import cz.cvut.fit.tjv.poberboh.server.exception.AlreadyExistException;
import cz.cvut.fit.tjv.poberboh.server.exception.NotFoundException;
import cz.cvut.fit.tjv.poberboh.server.service.InvestorService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(InvestorController.class)
@AutoConfigureMockMvc
class InvestorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private InvestorService investorService;

    @Test
    void createInvestorTest() throws Exception {
        InvestorDTO createInvestorDTO = new InvestorDTO("Username", "Firstname", "Lastname");
        BDDMockito.given(investorService.create(any(InvestorDTO.class), any(Integer.class))).willReturn(createInvestorDTO);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/investors?id=1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"username\":\"Username\", \"firstname\":\"Firstname\", \"lastname\":\"Lastname\"}")
                ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(createInvestorDTO.getUsername())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", CoreMatchers.is(createInvestorDTO.getFirstname())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname", CoreMatchers.is(createInvestorDTO.getLastname())));

        BDDMockito.given(investorService.create(any(InvestorDTO.class), any(Integer.class))).willThrow(NotFoundException.class);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/investors?id=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"Username\", \"firstname\":\"Firstname\", \"lastname\":\"Lastname\"}")
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
        Mockito.verify(investorService, Mockito.atLeast(2)).create(any(InvestorDTO.class), any(Integer.class));

        BDDMockito.given(investorService.create(any(InvestorDTO.class), any(Integer.class))).willThrow(AlreadyExistException.class);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/investors?id=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"Username\", \"firstname\":\"Firstname\", \"lastname\":\"Lastname\"}")
        ).andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(investorService, Mockito.atLeast(3)).create(any(InvestorDTO.class), any(Integer.class));
    }

    @Test
    void readInvestorTest() throws Exception {
        InvestorDTO readInvestorDTO = new InvestorDTO("Username", "Firstname", "Lastname");
        BDDMockito.given(investorService.read(any(Integer.class))).willReturn(readInvestorDTO);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/investors/1")
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(readInvestorDTO.getUsername())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", CoreMatchers.is(readInvestorDTO.getFirstname())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname", CoreMatchers.is(readInvestorDTO.getLastname())));

        Mockito.verify(investorService, Mockito.atLeastOnce()).read(any(Integer.class));
    }

    @Test
    void updateInvestorTest() throws Exception {
        InvestorDTO updateInvestorDTO = new InvestorDTO("Username", "Firstname", "Lastname");
        BDDMockito.given(investorService.update(any(Integer.class), any(InvestorDTO.class))).willReturn(updateInvestorDTO);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/investors/1", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"username\":\"Username\", \"firstname\":\"Firstname\", \"lastname\":\"Lastname\"}")
                ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(updateInvestorDTO.getUsername())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", CoreMatchers.is(updateInvestorDTO.getFirstname())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname", CoreMatchers.is(updateInvestorDTO.getLastname())));


        BDDMockito.given(investorService.update(any(Integer.class), any(InvestorDTO.class))).willThrow(NotFoundException.class);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/investors/1", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"Username\", \"firstname\":\"Firstname\", \"lastname\":\"Lastname\"}")
        ).andExpect(MockMvcResultMatchers.status().isNotFound());

        Mockito.verify(investorService, Mockito.atLeast(2)).update(any(Integer.class), any(InvestorDTO.class));
    }

    @Test
    void deleteInvestorTest() throws Exception {
        Investor deleteInvestor = new Investor("Username", "Firstname", "Lastname");

        BDDMockito.given(investorService.readById(any(Integer.class))).willReturn(Optional.of(deleteInvestor));

        mockMvc.perform(delete("/investors/1"))
                .andExpect(status().isOk());

        BDDMockito.given(investorService.readById(any(Integer.class))).willReturn(Optional.empty());

        mockMvc.perform(delete("/investors/1"))
                .andExpect(status().isNotFound());

        verify(investorService, times(1)).delete(any(Integer.class));
    }
}