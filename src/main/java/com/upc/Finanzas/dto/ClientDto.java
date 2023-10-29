package com.upc.Finanzas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
