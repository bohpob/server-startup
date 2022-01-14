package cz.cvut.fit.tjv.poberboh.server.controller;

import cz.cvut.fit.tjv.poberboh.server.service.OwnerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(OwnerController.class)
@AutoConfigureMockMvc
class OwnerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private OwnerService ownerService;

    @Test
    void create() throws Exception {
    }

    @Test
    void read() throws Exception {
    }

    @Test
    void update() throws Exception {
    }

    @Test
    void delete() throws Exception {
    }
}