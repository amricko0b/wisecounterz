package xyz.amricko0b.wisecounterz.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.neovisionaries.i18n.CountryCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import xyz.amricko0b.wisecounterz.rest.json.serializer.CountryCodeSerializer;

/**
 * Spring context configuration for Json serialization via Jackson
 *
 * @author amricko0b
 */
@Configuration
public class JacksonConfiguration {

    /**
     * Re-map default objectMapper, to provide global serializers
     *
     * @return custom mapper
     */
    @Bean
    @Primary
    public ObjectMapper objectMapper() {

        final var mapper = new ObjectMapper();

        final var customSerializersModule = new SimpleModule();
        customSerializersModule.addKeySerializer(CountryCode.class, new CountryCodeSerializer());

        mapper.registerModule(customSerializersModule);
        return mapper;
    }
}
