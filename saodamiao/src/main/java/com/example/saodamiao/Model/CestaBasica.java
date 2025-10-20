package com.example.saodamiao.Model;

import com.example.saodamiao.DAO.CestaBasicaDAO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CestaBasica {
    private long id;
    private String tamanho;
    private List<ItensCesta> itens;
    CestaBasicaDAO cestaBasicaDAO;

    public CestaBasica() {
        cestaBasicaDAO = new CestaBasicaDAO();
    }

    public CestaBasica(String tamanho){
        this.tamanho = tamanho;
        itens = new ArrayList<ItensCesta>();
        cestaBasicaDAO = new CestaBasicaDAO();
    }

}