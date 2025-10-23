package com.example.saodamiao.DAO;

import com.example.saodamiao.Model.Alimento;
import com.example.saodamiao.Model.CestaBasica;
import com.example.saodamiao.Model.ItensCesta;
import com.example.saodamiao.Singleton.Conexao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItensCestaDAO implements IDAO<ItensCesta>{

    public ItensCestaDAO() {}

    @Override
    public boolean gravar(ItensCesta entidade, Conexao conexao) {
        String sql = "INSERT INTO itens_cesta VALUES (cestas_basicas_idcestas_basicas, alimentos_idalimentos, qtde) VALUES (#1, #2, #3)";
        sql = sql.replace("#1", String.valueOf(entidade.getCesta().getId()));
        sql = sql.replace("#2", String.valueOf(entidade.getAlimento().getId()));
        sql = sql.replace("#3", String.valueOf(entidade.getQtde()));
        return conexao.manipular(sql);
    }

    @Override
    public boolean alterar(ItensCesta entidade, int id, Conexao conexao){
        return false;
    }

    public boolean alterarQtde(ItensCesta entidade, Conexao conexao) {
        String sql = "UPDATE itens_cesta SET qtde = #1 WHERE cestas_basicas_idcestas_basicas = #2 AND alimentos_idalimentos = #3";
        sql = sql.replace("#1", String.valueOf(entidade.getQtde()));
        sql = sql.replace("#2", String.valueOf(entidade.getCesta().getId()));
        sql = sql.replace("#3", String.valueOf(entidade.getAlimento().getId()));
        return  conexao.manipular(sql);
    }

    @Override
    public boolean apagar(ItensCesta entidade, Conexao conexao) {
        String sql = "DELETE FROM itens_cesta WHERE cestas_basicas_idcestas_basicas = #1 AND alimentos_idalimentos = #2";
        sql = sql.replace("#1", String.valueOf(entidade.getCesta().getId()));
        sql = sql.replace("#2", String.valueOf(entidade.getAlimento().getId()));
        return  conexao.manipular(sql);
    }

    @Override
    public List<ItensCesta> pegarListaToda(Conexao conexao) {
        String sql = "SELECT ic.*, " +
                     "a.nome as alimento_nome, " +
                     "a.tipo_alimento_tpa_id as alimento_tipo_id, " +
                     "tcb.tamanho " +
                     "FROM itens_cesta ic " +
                     "JOIN alimentos a ON ic.alimentos_idalimentos = a.idalimentos " +
                     "JOIN tipo_cesta_basica tcb ON ic.idcestas_basicas = tcb.idcestas_basicas";
        List<ItensCesta> itensCestas = new ArrayList<>();
        try{
            ResultSet rs = conexao.consultar(sql);
            while(rs.next())
                itensCestas.add(new ItensCesta(new CestaBasica(rs.getInt("cestas_basicas_idcestas_basicas"), rs.getString("tamanho")), new Alimento(rs.getInt("alimentos_idalimentos"), rs.getString("nome"), rs.getInt("alimento_tipo_id")), rs.getInt("qtde")));
            rs.close();
        }
        catch(SQLException sqlException){
            throw new RuntimeException(sqlException);
        }
        return itensCestas;
    }
}
