package xyz.amricko0b.wisecounterz.data.jpa.entity;

import com.neovisionaries.i18n.CountryCode;
import lombok.NoArgsConstructor;
import xyz.amricko0b.wisecounterz.data.CountryCounter;
import xyz.amricko0b.wisecounterz.data.jpa.converter.CountryCodeAttributeConverter;

import javax.persistence.*;

/**
 * {@link CountryCounter} as JPA entity.
 * <p>
 * BE CAREFUL: treating instances of this class as instances of CountryCounter could be dangerous without transaction!
 *
 * @author amricko0b
 */
@Entity(name = "CountryCounter")
@NoArgsConstructor
@Table(name = "country_counter")
public class CountryCounterEntity extends CountryCounter {

    @Id
    @Override
    @Enumerated(EnumType.STRING)
    @Column(name = "country_code")
    @Convert(converter = CountryCodeAttributeConverter.class)
    public CountryCode getCountryCode() {
        return super.getCountryCode();
    }

    @Override
    @Column(name = "counter")
    public Long getCounter() {
        return super.getCounter();
    }
}
