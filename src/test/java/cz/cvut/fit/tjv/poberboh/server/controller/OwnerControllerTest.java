package cz.cvut.fit.tjv.poberboh.server.controller;

import cz.cvut.fit.tjv.poberboh.server.dto.OwnerDTO;
import cz.cvut.fit.tjv.poberboh.server.entity.Owner;
import cz.cvut.fit.tjv.poberboh.server.exception.NotFoundException;
import cz.cvut.fit.tjv.poberboh.server.service.OwnerService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OwnerController.class)
@AutoConfigureMockMvc
class OwnerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    private OwnerService ownerService;

    @Test
    void createOwnerTest() throws Exception {
        OwnerDTO createOwnerDTO = new OwnerDTO("Username", "Firstname", "Lastname");
        BDDMockito.given(ownerService.create(any(OwnerDTO.class))).willReturn(createOwnerDTO);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/owners")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"username\":\"Username\", \"firstname\":\"Firstname\", \"lastname\":\"Lastname\"}")
                ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(createOwnerDTO.getUsername())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", CoreMatchers.is(createOwnerDTO.getFirstname())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname", CoreMatchers.is(createOwnerDTO.getLastname())));

        BDDMockito.given(ownerService.create(any(OwnerDTO.class))).willThrow(NotFoundException.class);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"Username\", \"firstname\":\"Firstname\", \"lastname\":\"Lastname\"}")
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
        Mockito.verify(ownerService, Mockito.atLeast(2)).create(any(OwnerDTO.class));
    }

    @Test
    void readOwnerTest() throws Exception {
        OwnerDTO readOwnerDTO = new OwnerDTO("Username", "Firstname", "Lastname");
        BDDMockito.given(ownerService.read(any(Integer.class))).willReturn(readOwnerDTO);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/owners/1")
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(readOwnerDTO.getUsername())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", CoreMatchers.is(readOwnerDTO.getFirstname())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname", CoreMatchers.is(readOwnerDTO.getLastname())));

        Mockito.verify(ownerService, Mockito.atLeastOnce()).read(any(Integer.class));
    }

    @Test
    void updateOwnerTest() throws Exception {
        OwnerDTO updateOwnerDTO = new OwnerDTO("Username", "Firstname", "Lastname");
        BDDMockito.given(ownerService.update(any(Integer.class), any(OwnerDTO.class))).willReturn(updateOwnerDTO);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/owners/1", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"username\":\"Username\", \"firstname\":\"Firstname\", \"lastname\":\"Lastname\"}")
                ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(updateOwnerDTO.getUsername())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", CoreMatchers.is(updateOwnerDTO.getFirstname())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname", CoreMatchers.is(updateOwnerDTO.getLastname())));


        BDDMockito.given(ownerService.update(any(Integer.class), any(OwnerDTO.class))).willThrow(NotFoundException.class);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/owners/1", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"Username\", \"firstname\":\"Firstname\", \"lastname\":\"Lastname\"}")
        ).andExpect(MockMvcResultMatchers.status().isNotFound());

        Mockito.verify(ownerService, Mockito.atLeast(2)).update(any(Integer.class), any(OwnerDTO.class));
    }

    @Test
    void deleteOwnerTest() throws Exception {
        Owner deleteOwner = new Owner("Username", "Firstname", "Lastname");

        BDDMockito.given(ownerService.readById(any(Integer.class))).willReturn(Optional.of(deleteOwner));

        mockMvc.perform(delete("/owners/1"))
                .andExpect(status().isOk());

        BDDMockito.given(ownerService.readById(any(Integer.class))).willReturn(Optional.empty());

        mockMvc.perform(delete("/owners/1"))
                .andExpect(status().isNotFound());

        verify(ownerService, times(1)).delete(any(Integer.class));
    }
}