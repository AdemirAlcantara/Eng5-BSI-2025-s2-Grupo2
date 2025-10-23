package com.example.saodamiao.DAO;

import com.example.saodamiao.Model.CestaBasica;
import com.example.saodamiao.Singleton.Conexao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CestaBasicaDAO implements IDAO<CestaBasica>{

    public CestaBasicaDAO(){}


    @Override
    public boolean gravar(CestaBasica entidade, Conexao conexao) {
        String sql = "INSERT INTO tipo_cesta_basica (tamanho) VALUES ('#1')";
        sql = sql.replace("#1", entidade.getTamanho());
        return conexao.manipular(sql);
    }

    @Override
    public boolean alterar(CestaBasica entidade, int id, Conexao conexao) {
        String sql = "UPDATE tipo_cesta_basica SET tamanho = '#1' WHERE idcestas_basicas = '#2'";
        sql = sql.replace("#1", entidade.getTamanho());
        sql = sql.replace("#2", String.valueOf(id));
        return conexao.manipular(sql);
    }

    @Override
    public boolean apagar(CestaBasica entidade, Conexao conexao) {

        // apagar itens cesta

        String sql = "DELETE FROM tipo_cesta_basica WHERE idcestas_basicas = '#1'";
        sql = sql.replace("#1", String.valueOf(entidade.getId()));
        return conexao.manipular(sql);
    }

    @Override
    public List<CestaBasica> pegarListaToda(Conexao conexao) {
        List<CestaBasica> cestas = new ArrayList<>();
        String sql = "SELECT * FROM tipo_cesta_basica";
        try{
            ResultSet rs = conexao.consultar(sql);
            while(rs.next())
                cestas.add(new CestaBasica(rs.getInt("idcestas_basicas"), rs.getString("tamanho")));
            rs.close();
        }
        catch(SQLException sqlException){
            throw new RuntimeException(sqlException);
        }
        return cestas;
    }
}
