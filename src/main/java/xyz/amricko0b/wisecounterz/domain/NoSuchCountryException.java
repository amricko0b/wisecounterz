package xyz.amricko0b.wisecounterz.domain;

/**
 * Domain exception, which is thrown when unsupported country code provided somehow
 *
 * @author amricko0b
 */
public class NoSuchCountryException extends RuntimeException {

    /**
     * Populate invalid country code to exception message
     *
     * @param actualCode actual invalid code
     */
    public NoSuchCountryException(String actualCode) {
        super("There is no such country: " + actualCode);
    }
}
