package com.example.saodamiao.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCestaBuscaRequest {
    private String tamanhoCesta;  // "P", "M", "G" - usamos o tamanho como identificador
}