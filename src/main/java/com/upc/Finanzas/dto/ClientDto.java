package com.upc.Finanzas.dto;

import com.upc.Finanzas.model.CalculateDebt;
import com.upc.Finanzas.model.DebtResult;
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
    private List<CalculateDebt> calculateDebts;
    private List<DebtResult> debtResults;
}
