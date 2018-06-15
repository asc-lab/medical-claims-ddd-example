package pl.asc.claimsservice.shared.cqs;

public interface CommandBus {
    <R,C extends Command<R>> R executeCommand(C command);
    <R,Q extends Query<R>> R executeQuery(Q query);
}
