package xyz.amricko0b.wisecounterz.data.jpa;

import org.springframework.stereotype.Component;
import xyz.amricko0b.wisecounterz.data.CountryCounterFactory;
import xyz.amricko0b.wisecounterz.data.jpa.entity.CountryCounterEntity;

/**
 * JPA implementation of {@link CountryCounterFactory} that creates JPA entities
 *
 * @author amricko0b
 */
@Component
public class CountryCounterEntityFactory extends CountryCounterFactory<CountryCounterEntity> {

    @Override
    public CountryCounterEntity allocate() {
        return new CountryCounterEntity();
    }
}
