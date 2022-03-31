package xyz.amricko0b.wisecounterz.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = CountersRestController.class)
@ComponentScan(basePackages = "xyz.amricko0b.wisecounterz")
public class CountersRestControllerIntegrationTestCase {

    @Autowired
    private MockMvc mvc;

    @Test
    void test_getAllCounters() throws Exception {
        mvc.perform(get("/api/rest/counters"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{}"));
    }

    @Test
    void test_incrementByCountryCode() throws Exception {
        mvc.perform(post("/api/rest/counters/cy"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"cy\": null}"));
    }
}
