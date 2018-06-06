package pl.asc.claimsservice.domain;

import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class ServiceCollection {
    private final Cover cover;
    private final Set<Service> services;

    Service add(String code, CoPayment coPayment, Limit limit) {
        Service svc = new Service(null, code, cover, limit, coPayment);
        services.add(svc);
        return svc;
    }


}
