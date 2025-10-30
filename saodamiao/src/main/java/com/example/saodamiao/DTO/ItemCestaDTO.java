package com.example.saodamiao.DTO;

import com.example.saodamiao.Model.Alimento;
import com.example.saodamiao.Model.CestaBasica;
import com.example.saodamiao.Model.ItemCesta;
import com.example.saodamiao.Singleton.Conexao;
import com.example.saodamiao.Singleton.Singleton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCestaDTO {
    private String alimentoNome;
    private int quantidade;

    public ItemCesta toItemCesta(CestaBasica cestaPai) {
        ItemCesta item = new ItemCesta();
        Alimento alimento = new Alimento().getAlimentoDAO().ResgatarAlimento(this.alimentoNome, Singleton.Retorna());
        if (alimento == null) {
            throw new RuntimeException("Alimento não encontrado: " + this.alimentoNome);
        }
        item.setAlimento(alimento);
        item.setCesta(cestaPai);
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