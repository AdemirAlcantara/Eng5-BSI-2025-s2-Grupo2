package com.example.saodamiao.DAO;

import com.example.saodamiao.Model.CestaBasica;
import com.example.saodamiao.Model.ItensCesta;
import com.example.saodamiao.Singleton.Conexao;

import java.util.List;

public class ItensCestaDAO implements IDAO<ItensCesta>{

    public ItensCestaDAO() {
    }


    @Override
    public boolean gravar(ItensCesta entidade, Conexao conexao) {
        return false;
    }

    @Override
    public boolean alterar(ItensCesta entidade, int id, Conexao conexao) {
        return false;
    }

    @Override
    public boolean apagar(ItensCesta entidade, Conexao conexao) {
        return false;
    }

    @Override
    public List<ItensCesta> pegarListaToda(Conexao conexao) {
        return List.of();
    }
}
