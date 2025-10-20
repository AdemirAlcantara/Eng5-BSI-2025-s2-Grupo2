package com.example.saodamiao.Model;

import com.example.saodamiao.DAO.ItensCestaDAO;
import lombok.Data;

@Data
public class ItensCesta {
    private long id;
    private CestaBasica cesta;
    private Alimento alimento;
    private int qtde;
    private ItensCestaDAO itensCestaDAO;

    public ItensCesta() {
        this.itensCestaDAO = new ItensCestaDAO();
    }

    public ItensCesta(long id, CestaBasica cesta, Alimento alimento, int qtde) {
        this.id = id;
        this.cesta = cesta;
        this.alimento = alimento;
        this.qtde = qtde;
        itensCestaDAO = new ItensCestaDAO();
    }
}