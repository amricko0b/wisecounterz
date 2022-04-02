package xyz.amricko0b.wisecounterz.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import xyz.amricko0b.wisecounterz.domain.CountryCodeSanitizer;
import xyz.amricko0b.wisecounterz.domain.CountryCounterService;
import xyz.amricko0b.wisecounterz.domain.NoSuchCountryException;
import xyz.amricko0b.wisecounterz.rest.json.CountryCountersJson;

import java.util.Collections;

/**
 * RESTFull controller for Countries collection
 *
 * @author amricko0b
 */
@RequiredArgsConstructor
@RestController
@Tag(name = "Counters API", description = "Provides access to counters management")
@RequestMapping(path = "/api/rest/counters")
public class CountersRestController {

    private final CountryCounterService countryCounterService;

    /**
     * Getting all info by country counters.
     *
     * @return a json object, where keys are country codes and values are country values
     */
    @Operation(description = "Get all available country counters")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returns all available countries along with their counters"),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal error",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject("{\"status\": 500, \"message\": \"...\"")
                                    }
                            )
                    }
            ),
    })
    @GetMapping(produces = "application/json")
    public CountryCountersJson getAllCountryCounters() {
        final var counters = countryCounterService.getAllCounters();
        return new CountryCountersJson(counters);
    }

    /**
     * Increment a counter for country of choice.
     *
     * @param rawCountryCode raw country code as string
     * @return object, representing current counter value after incrementation
     */
    @Operation(description = "Increment the country counter by country code")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returns current value of the counter after it has been incremented"),
            @ApiResponse(
                    responseCode = "400",
                    description = "No such country",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject("{\"status\": 400, \"message\": \"There is no such country: ...\"")
                                    }
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal error",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject("{\"status\": 500, \"message\": \"...\"")
                                    }
                            )
                    }
            ),
    })
    @PostMapping(path = "/{countryCode}", produces = "application/json")
    public CountryCountersJson postCounterByCountryCode(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "ISO 3166-1 alpha-2 country code lowercase, i.e. ru, us, cy"
            )
            @PathVariable("countryCode") String rawCountryCode
    ) {
        try {
            // Run incrementation logic and return current value
            final var incremented = countryCounterService.incrementCounterByRaw(rawCountryCode);
            return new CountryCountersJson(
                    Collections.singletonMap(incremented.getFirst(), incremented.getSecond())
            );

        } catch (NoSuchCountryException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }
}
