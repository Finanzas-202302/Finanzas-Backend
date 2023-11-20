package com.upc.Finanzas.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.upc.Finanzas.model.Credit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    private String firstname;
    private String lastname;
    private String email;
    private Long dni;
    private String vehicle;
    private Long userId;
    @JsonIgnore
    private List<Credit> credits;
}
