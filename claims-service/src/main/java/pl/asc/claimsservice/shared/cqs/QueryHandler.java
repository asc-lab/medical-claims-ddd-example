package pl.asc.claimsservice.shared.cqs;

import de.triology.cb.CommandHandler;

public interface QueryHandler<R, C extends Query<R>> extends CommandHandler<R,C> {
    R handle(C var1);
}
