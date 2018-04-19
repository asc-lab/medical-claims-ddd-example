package pl.altkom.asc.wl.claim.domain.port.input;

import lombok.Getter;

/**
 * @author tdorosz
 */
@Getter
public class GenericResponse<T> {
    private final T result;
    private final ErrorCode errorCode;

    private GenericResponse(T result, ErrorCode errorCode) {
        this.result = result;
        this.errorCode = errorCode;
    }

    public static <T> GenericResponse<T> success(T result) {
        return new GenericResponse<>(result, null);
    }

    public static GenericResponse<String> error(ErrorCode code) {
        return new GenericResponse<>(null, code);
    }
}
