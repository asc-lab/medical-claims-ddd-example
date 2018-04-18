package pl.altkom.asc.wl.claim.domain.port.input;

/**
 * @author tdorosz
 */
public interface SubmitClaimPort {
    void process(NewClaimCommand newClaimCommand);
}
