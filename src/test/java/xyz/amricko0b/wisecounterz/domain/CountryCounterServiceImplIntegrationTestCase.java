package xyz.amricko0b.wisecounterz.domain;

import com.neovisionaries.i18n.CountryCode;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.amricko0b.wisecounterz.data.CountryCounterManager;
import xyz.amricko0b.wisecounterz.domain.CountryCounterService;

public class CountryCounterServiceImplIntegrationTestCase extends AbstractDomainIntegrationTestCase implements WithAssertions {

    @Autowired
    private CountryCounterManager manager;

    @Autowired
    private CountryCounterService service;

    @Test
    void test_getAllCounters_notPresent() {
        assertThat(service.getAllCounters()).hasSize(0);
    }

    @Test
    void test_getAllCounters_onePresent() {

        manager.initiate(CountryCode.CY);

        final var counters = service.getAllCounters();
        assertThat(counters).hasSize(1);
        assertThat(counters.get(CountryCode.CY)).isEqualTo(1L);
    }

    @Test
    void test_getAllCounters_multiplePresent() {

        manager.initiate(CountryCode.CY);
        manager.initiate(CountryCode.US);
        manager.initiate(CountryCode.RU);

        final var counters = service.getAllCounters();
        assertThat(counters).hasSize(3);
        assertThat(counters.get(CountryCode.CY)).isEqualTo(1L);
        assertThat(counters.get(CountryCode.US)).isEqualTo(1L);
        assertThat(counters.get(CountryCode.RU)).isEqualTo(1L);
    }
}
