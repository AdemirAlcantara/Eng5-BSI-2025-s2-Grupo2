package com.example.saodamiao.DAO;

import com.example.saodamiao.Model.CestaBasica;
import com.example.saodamiao.Singleton.Conexao;

import java.util.List;

public class CestaBasicaDAO implements IDAO<CestaBasica>{

    public CestaBasicaDAO(){}


    @Override
    public boolean gravar(CestaBasica entidade, Conexao conexao) {
        return false;
    }

    @Override
    public boolean alterar(CestaBasica entidade, int id, Conexao conexao) {
        return false;
    }

    @Override
    public boolean apagar(CestaBasica entidade, Conexao conexao) {
        return false;
    }

    @Override
    public List<CestaBasica> pegarListaToda(Conexao conexao) {
        return List.of();
    }
}
