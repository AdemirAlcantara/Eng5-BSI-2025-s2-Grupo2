package com.example.saodamiao.Model;

import com.example.saodamiao.DAO.ItemCestaDAO;
import lombok.Data;

@Data
public class ItemCesta {
    private CestaBasica cesta;
    private Alimento alimento;
    private int qtde;
    private ItemCestaDAO itemCestaDAO;

    public ItemCesta() {
        this.itemCestaDAO = new ItemCestaDAO();
    }

    public ItemCesta(CestaBasica cesta, Alimento alimento, int qtde) {
        this.cesta = cesta;
        this.alimento = alimento;
        this.qtde = qtde;
        itemCestaDAO = new ItemCestaDAO();
    }
}