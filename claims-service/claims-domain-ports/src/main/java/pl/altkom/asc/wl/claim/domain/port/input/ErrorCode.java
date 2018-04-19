package pl.altkom.asc.wl.claim.domain.port.input;

/**
 * @author tdorosz
 */
public enum ErrorCode {
    CLAIMS_001("policy doesn't exists");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
