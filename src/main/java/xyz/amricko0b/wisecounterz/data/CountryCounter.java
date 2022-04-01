package xyz.amricko0b.wisecounterz.data;

import com.neovisionaries.i18n.CountryCode;
import lombok.*;

/**
 * Abstract DTO for country counter data objects.
 * Should be a superclass for JPA entity, or some other data access layer DTO (depends on actual DA implementation)
 *
 * @author amricko0b
 */
@Getter
@Setter
@ToString(of = "countryCode")
@EqualsAndHashCode(of = "countryCode")
public abstract class CountryCounter {

    /**
     * Code of the country for which counter is served
     */
    protected CountryCode countryCode;

    /**
     * Current value of counter
     */
    protected Long counter;
}
