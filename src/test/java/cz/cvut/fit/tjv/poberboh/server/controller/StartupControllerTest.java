package cz.cvut.fit.tjv.poberboh.server.controller;

import cz.cvut.fit.tjv.poberboh.server.service.StartupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(StartupController.class)
@AutoConfigureMockMvc
class StartupControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private StartupService startupService;

    @Test
    void create() {
    }

    @Test
    void read() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void addInvestor() {
    }

    @Test
    void deleteInvestor() {
    }
}