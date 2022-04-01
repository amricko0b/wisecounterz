package xyz.amricko0b.wisecounterz.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class CountersRestControllerIntegrationTestCase extends AbstractRestIntegrationTestCase {

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
