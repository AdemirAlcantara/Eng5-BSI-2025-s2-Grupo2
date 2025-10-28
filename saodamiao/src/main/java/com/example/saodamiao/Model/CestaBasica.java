package com.example.saodamiao.Model;

import com.example.saodamiao.DAO.CestaBasicaDAO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CestaBasica {
    private int id;
    private String tamanho;
    private List<ItemCesta> itens;
    CestaBasicaDAO cestaBasicaDAO;

    public CestaBasica() {
        itens = new ArrayList<>();
        cestaBasicaDAO = new CestaBasicaDAO();
    }

    public CestaBasica(String tamanho){
        this.tamanho = tamanho;
        itens = new ArrayList<>();
        cestaBasicaDAO = new CestaBasicaDAO();
    }

    public CestaBasica(int id, String tamanho){
        this.id = id;
        this.tamanho = tamanho;
        itens = new ArrayList<>();
        cestaBasicaDAO = new CestaBasicaDAO();
    }
}