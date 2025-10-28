package com.example.saodamiao.DTO;

import com.example.saodamiao.Model.Alimento;
import com.example.saodamiao.Model.ItemCesta;
import com.example.saodamiao.Singleton.Conexao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCestaDTO {
    private String alimentoNome;
    private int quantidade;

    public ItemCesta toItemCesta(Conexao conexao) {
        ItemCesta item = new ItemCesta();
        Alimento alimento = new Alimento();
        alimento.setNome(this.alimentoNome);
        item.setAlimento(alimento);
        item.setQtde(this.quantidade);
        return item;
    }

    public static ItemCestaDTO toItemCestaDTO(ItemCesta item) {
        ItemCestaDTO dto = new ItemCestaDTO();
        dto.setAlimentoNome(item.getAlimento().getNome());
        dto.setQuantidade(item.getQtde());
        return dto;
    }
}