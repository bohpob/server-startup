package cz.cvut.fit.tjv.poberboh.server.controller;

import cz.cvut.fit.tjv.poberboh.server.dto.StartupDTO;
import cz.cvut.fit.tjv.poberboh.server.entity.Startup;
import cz.cvut.fit.tjv.poberboh.server.exception.AlreadyExistException;
import cz.cvut.fit.tjv.poberboh.server.exception.NotFoundException;
import cz.cvut.fit.tjv.poberboh.server.service.StartupService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StartupController.class)
@AutoConfigureMockMvc
class StartupControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private StartupService startupService;

    @Test
    void createStartupTest() throws Exception {
        StartupDTO createStartupDTO = new StartupDTO("Name", 10000);
        BDDMockito.given(startupService.create(any(StartupDTO.class), any(Integer.class))).willReturn(createStartupDTO);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/startups?id=1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"Name\", \"investment\":\"10000\"}")
                ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(createStartupDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.investment", CoreMatchers.is(createStartupDTO.getInvestment())));

        BDDMockito.given(startupService.create(any(StartupDTO.class), any(Integer.class))).willThrow(NotFoundException.class);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/startups?id=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Name\", \"investment\":\"10000\"}")
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
        Mockito.verify(startupService, Mockito.atLeast(2)).create(any(StartupDTO.class), any(Integer.class));

        BDDMockito.given(startupService.create(any(StartupDTO.class), any(Integer.class))).willThrow(AlreadyExistException.class);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/startups?id=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Name\", \"investment\":\"10000\"}")
        ).andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(startupService, Mockito.atLeast(3)).create(any(StartupDTO.class), any(Integer.class));
    }

    @Test
    void readStartupTest() throws Exception {
        StartupDTO readStartupDTO = new StartupDTO("Name", 10000);
        BDDMockito.given(startupService.read(any(Integer.class))).willReturn(readStartupDTO);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/startups/1")
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(readStartupDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.investment", CoreMatchers.is(readStartupDTO.getInvestment())));

        Mockito.verify(startupService, Mockito.atLeastOnce()).read(any(Integer.class));
    }

    @Test
    void updateStartupTest() throws Exception {
        StartupDTO updateStartupDTO = new StartupDTO("Name", 10000);
        BDDMockito.given(startupService.update(any(Integer.class), any(StartupDTO.class))).willReturn(updateStartupDTO);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/startups/1", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"Name\", \"investment\":\"10000\"}")
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(updateStartupDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.investment", CoreMatchers.is(updateStartupDTO.getInvestment())));


        BDDMockito.given(startupService.update(any(Integer.class), any(StartupDTO.class))).willThrow(NotFoundException.class);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/startups/1", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Name\", \"investment\":\"10000\"}")
        ).andExpect(MockMvcResultMatchers.status().isNotFound());

        Mockito.verify(startupService, Mockito.atLeast(2)).update(any(Integer.class), any(StartupDTO.class));
    }

    @Test
    void deleteStartupTest() throws Exception {
        Startup deleteStartup = new Startup("Username", 10000);

        BDDMockito.given(startupService.readById(any(Integer.class))).willReturn(Optional.of(deleteStartup));

        mockMvc.perform(delete("/startups/1"))
                .andExpect(status().isOk());

        BDDMockito.given(startupService.readById(any(Integer.class))).willReturn(Optional.empty());

        mockMvc.perform(delete("/startups/1"))
                .andExpect(status().isNotFound());

        verify(startupService, times(1)).delete(any(Integer.class));
    }

    @Test
    void addInvestorTest() throws Exception {
        StartupDTO updateStartupDTO = new StartupDTO("Name", 10000);

        BDDMockito.given(startupService.addInvestor(any(Integer.class), any(Integer.class))).willReturn(updateStartupDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/startups/add_investor/1?investorID=1"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        BDDMockito.given(startupService.addInvestor(any(Integer.class), any(Integer.class))).willThrow(NotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/startups/add_investor/1?investorID=1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteInvestorTest() throws Exception {
        mockMvc.perform(delete("/startups/delete_investor/1?investorID=1"))
                .andExpect(status().isOk());
        verify(startupService, times(1)).deleteInvestor(any(Integer.class), any(Integer.class));
    }
}