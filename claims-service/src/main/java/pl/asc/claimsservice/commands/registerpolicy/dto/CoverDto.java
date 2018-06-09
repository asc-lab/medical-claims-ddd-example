package pl.asc.claimsservice.commands.registerpolicy.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoverDto {
    private String code;
    private List<ServiceDto> services;
}
