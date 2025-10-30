package com.example.saodamiao.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCestaRequest {
    private String tamanhoCesta;  // "P", "M", "G"
    private ItemCestaDTO itemDTO; // {alimentoNome: "Arroz", quantidade: 2}
}