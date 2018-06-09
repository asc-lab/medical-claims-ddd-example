package pl.asc.claimsservice.commands.registerpolicy.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PolicyVersionDto {
    private String policyNumber;
    private String productCode;
    private PersonDto policyHolder;
    private String accountNumber;
    private LocalDate policyValidFrom;
    private LocalDate policyValidTo;
    private Long versionNumber;
    private LocalDate versionValidFrom;
    private LocalDate versionValidTo;
    private List<CoverDto> covers;
}
