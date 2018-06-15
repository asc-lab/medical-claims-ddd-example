package pl.asc.claimsservice.shared.cqs;

public interface CommandHandler<R, C extends  Command<R>> {
    R handle(C command);
}
