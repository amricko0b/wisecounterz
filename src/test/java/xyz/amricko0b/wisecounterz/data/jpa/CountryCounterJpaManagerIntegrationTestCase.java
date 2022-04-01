package xyz.amricko0b.wisecounterz.data.jpa;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.amricko0b.wisecounterz.WithDomainAssertions;

public class CountryCounterJpaManagerIntegrationTestCase extends AbstractJpaTestCase implements WithDomainAssertions {

    @Autowired
    private CountryCounterJpaManager manager;

    @Test
    void test_initiateSomeThenFindAll() {

        manager.initiate(CountryCode.CY);
        manager.initiate(CountryCode.US);
        manager.initiate(CountryCode.RU);

        final var counters = manager.findAll();

        assertThat(counters).hasSize(3)
                .anySatisfy(counterCriteria(CountryCode.CY, 1L))
                .anySatisfy(counterCriteria(CountryCode.US, 1L))
                .anySatisfy(counterCriteria(CountryCode.RU, 1L));
    }

    @Test
    void test_initiateSameCounterTwice() {
        manager.initiate(CountryCode.CY);
        manager.initiate(CountryCode.CY);

        assertThat(manager.findAll()).hasSize(1);
    }
}
