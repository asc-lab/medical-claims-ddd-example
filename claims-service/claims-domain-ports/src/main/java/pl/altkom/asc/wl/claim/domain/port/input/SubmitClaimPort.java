package pl.altkom.asc.wl.claim.domain.port.input;

/**
 * @author tdorosz
 */
public interface SubmitClaimPort {
    GenericResponse<String> process(NewClaimCommand newClaimCommand);
}
