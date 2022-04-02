package xyz.amricko0b.wisecounterz.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.neovisionaries.i18n.CountryCode;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.support.TransactionTemplate;
import xyz.amricko0b.wisecounterz.data.CountryCounterFactory;
import xyz.amricko0b.wisecounterz.data.jpa.entity.CountryCounterEntity;
import xyz.amricko0b.wisecounterz.data.jpa.repository.CountryCounterRepository;
import xyz.amricko0b.wisecounterz.domain.CountryCounterService;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class CountersRestControllerIntegrationTestCase extends AbstractRestIntegrationTestCase implements WithAssertions {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CountryCounterRepository repository;

    @Autowired
    private CountryCounterFactory<CountryCounterEntity> factory;

    @Autowired
    private CountryCounterService service;

    @Autowired
    private TransactionTemplate inTransaction;

    @Test
    void test_getAllCounters() throws Exception {

        inTransaction.executeWithoutResult(tx -> {
            repository.save(factory.createInitial(CountryCode.CO));
            repository.save(factory.createInitial(CountryCode.DE));
            repository.save(factory.createInitial(CountryCode.RO));

            tx.flush();
        });

        mvc.perform(get("/api/rest/counters"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.co").value(1))
                .andExpect(jsonPath("$.de").value(1))
                .andExpect(jsonPath("$.ro").value(1));
    }

    @Test
    void test_incrementByWrongCountryCode_fails() throws Exception {
        mvc.perform(post("/api/rest/counters/xyz"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void test_incrementByCountryCode() throws Exception {
        mvc.perform(post("/api/rest/counters/cy"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"cy\": 1}"));
    }

    @Test
    void test_subsequentIncrements() throws Exception {
        mvc.perform(post("/api/rest/counters/ru"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ru").value(1));

        mvc.perform(post("/api/rest/counters/ru"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ru").value(2));
    }

    @Test
    void test_concurrentIncrements() throws Exception {

        // To emulate concurrent load we need thread pool
        final var executor = Executors.newFixedThreadPool(5);

        // Submit concurrent requests
        IntStream.range(1, 6).forEach(i -> {
            executor.execute(() -> {
                try {
                    mvc.perform(post("/api/rest/counters/ac"))
                            .andExpect(status().isOk())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
                } catch (Exception ex) {
                    fail("Test was unable to make HTTP request");
                }
            });
        });

        executor.awaitTermination(5L, TimeUnit.SECONDS);

        mvc.perform(get("/api/rest/counters"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ac").value(5));
    }
}
