package pl.asc.claimsservice.api.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;
import pl.asc.claimsservice.commands.registerpolicy.RegisterPolicyCommand;
import pl.asc.claimsservice.commands.registerpolicy.dto.PolicyVersionDto;
import pl.asc.claimsservice.shared.cqs.CommandBus;

@Slf4j
@Service
@RequiredArgsConstructor
public class PolicyVersionCreatedMessageHandler {
    private final CommandBus bus;

    @StreamListener(target = "input")
    public void onPolicyVersionCreated(PolicyVersionDto policyVersion) {
        log.info("Policy version created message received");
        bus.executeCommand(new RegisterPolicyCommand(policyVersion));
        log.info("Policy version created message handled");
    }
}
