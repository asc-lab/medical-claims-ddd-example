package pl.asc.claimsservice.shared.cqs;

public interface QueryHandler<R, C extends Query<R>> {
    R handle(C var1);
}
