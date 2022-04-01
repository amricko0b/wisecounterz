package xyz.amricko0b.wisecounterz.data.jpa;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;
import xyz.amricko0b.wisecounterz.WithDomainAssertions;
import xyz.amricko0b.wisecounterz.data.CountryCounterFactory;
import xyz.amricko0b.wisecounterz.data.jpa.entity.CountryCounterEntity;

public class CountryCounterEntityFactoryTestCase implements WithDomainAssertions {

    private CountryCounterFactory<CountryCounterEntity> factory = new CountryCounterEntityFactory();

    @Test
    void test_createInitial() {
        assertThat(factory.createInitial(CountryCode.CY))
                .is(forCountry(CountryCode.CY))
                .has(counterValue(1L));
    }

}
