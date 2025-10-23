package com.example.saodamiao.Model;

import com.example.saodamiao.DAO.ItensCestaDAO;
import lombok.Data;

@Data
public class ItensCesta {
    private CestaBasica cesta;
    private Alimento alimento;
    private int qtde;
    private ItensCestaDAO itensCestaDAO;

    public ItensCesta() {
        this.itensCestaDAO = new ItensCestaDAO();
    }

    public ItensCesta(CestaBasica cesta, Alimento alimento, int qtde) {
        this.cesta = cesta;
        this.alimento = alimento;
        this.qtde = qtde;
        itensCestaDAO = new ItensCestaDAO();
    }
}