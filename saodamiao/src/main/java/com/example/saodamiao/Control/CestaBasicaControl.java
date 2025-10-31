package com.example.saodamiao.Control;

import com.example.saodamiao.DTO.CestaBasicaDTO;
import com.example.saodamiao.DTO.CestaBasicaRequest;
import com.example.saodamiao.Model.CestaBasica;
import com.example.saodamiao.Model.ItemCesta;
import com.example.saodamiao.Singleton.Erro;
import com.example.saodamiao.Singleton.Singleton;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "apis/cestas")
@CrossOrigin(origins = "*")
public class CestaBasicaControl {

    @PostMapping(value = "/inserir")
    public ResponseEntity<Object> inserirCesta(@RequestBody CestaBasicaDTO cestaDTO) {
        CestaBasica cesta = cestaDTO.toCestaBasica();

        if (!Singleton.Retorna().StartTransaction()) {
            return ResponseEntity.status(500).body(new Erro(Singleton.Retorna().getMensagemErro()));
        }

        // Grava a cesta básica
        if (!cesta.getCestaBasicaDAO().gravar(cesta, Singleton.Retorna())) {
            Singleton.Retorna().Rollback();
            return ResponseEntity.badRequest().body(new Erro("Problema ao gravar cesta no banco de dados"));
        }

        // Obtém o ID da cesta recém-inserida
        int idCesta = cesta.getCestaBasicaDAO().getUltimoIdInserido(Singleton.Retorna());
        cesta.setId(idCesta);

        // Grava os itens da cesta
        if (cesta.getItens() != null && !cesta.getItens().isEmpty()) {
            for (ItemCesta item : cesta.getItens()) {
                if (!item.getItemCestaDAO().gravar(item, Singleton.Retorna())) {
                    Singleton.Retorna().Rollback();
                    return ResponseEntity.badRequest().body(new Erro("Problema ao gravar itens da cesta no banco de dados"));
                }
            }
        }

        Singleton.Retorna().Commit();
        return ResponseEntity.ok(cestaDTO);
    }

    @PutMapping(value = "/atualizar")
    public ResponseEntity<Object> atualizarCesta(@RequestBody CestaBasicaRequest cestaRequest) {
        CestaBasica cesta = cestaRequest.cestaDTO().toCestaBasica();
        List<CestaBasica> cestasExistentes = cesta.getCestaBasicaDAO().buscarPorTamanho(cestaRequest.tamanhoAtual(), Singleton.Retorna());
        if (cestasExistentes.isEmpty()) {
            return ResponseEntity.badRequest().body(new Erro("Cesta não encontrada"));
        }
        int idCesta = cestasExistentes.getFirst().getId();
        cesta.setId(idCesta);
        if (!Singleton.Retorna().StartTransaction()) {
            return ResponseEntity.status(500).body(new Erro(Singleton.Retorna().getMensagemErro()));
        }
        if (!cestaRequest.tamanhoAtual().equals(cestaRequest.cestaDTO().getTamanho())) {
            if (!cesta.getCestaBasicaDAO().alterar(cesta, idCesta, Singleton.Retorna())) {
                Singleton.Retorna().Rollback();
                return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar cesta"));
            }
        }
        ItemCesta itemTemp = new ItemCesta();
        List<ItemCesta> itensAntigos = itemTemp.getItemCestaDAO().buscarItensCesta(idCesta, Singleton.Retorna());
        for (ItemCesta itemAntigo : itensAntigos) {
            if (!itemAntigo.getItemCestaDAO().apagar(itemAntigo, Singleton.Retorna())) {
                Singleton.Retorna().Rollback();
                return ResponseEntity.badRequest().body(new Erro("Erro ao remover itens antigos"));
            }
        }
        if (cesta.getItens() != null && !cesta.getItens().isEmpty()) {
            for (ItemCesta item : cesta.getItens()) {
                if (!item.getItemCestaDAO().gravar(item, Singleton.Retorna())) {
                    Singleton.Retorna().Rollback();
                    return ResponseEntity.badRequest().body(new Erro("Erro ao adicionar novos itens"));
                }
            }
        }
        Singleton.Retorna().Commit();
        return ResponseEntity.ok(cestaRequest);
    }

    @DeleteMapping(value = "/deletar")
    public ResponseEntity<Object> deletarCesta(@RequestBody CestaBasicaDTO cestaDTO) {
        CestaBasica cesta = cestaDTO.toCestaBasica();

        // Busca a cesta existente pelo tamanho para obter o ID
        List<CestaBasica> cestasExistentes = cesta.getCestaBasicaDAO()
                .buscarPorTamanho(cestaDTO.getTamanho(), Singleton.Retorna());

        if (cestasExistentes.isEmpty()) {
            return ResponseEntity.badRequest().body(new Erro("Cesta não encontrada"));
        }

        cesta.setId(cestasExistentes.getFirst().getId());

        if (!Singleton.Retorna().StartTransaction()) {
            return ResponseEntity.status(500).body(new Erro(Singleton.Retorna().getMensagemErro()));
        }

        if (!cesta.getCestaBasicaDAO().apagar(cesta, Singleton.Retorna())) {
            Singleton.Retorna().Rollback();
            return ResponseEntity.badRequest().body(new Erro(Singleton.Retorna().getMensagemErro()));
        }

        Singleton.Retorna().Commit();
        return ResponseEntity.ok(cestaDTO);
    }

    @GetMapping(value = "/lista-tudo")
    public ResponseEntity<Object> getCestas() {
        CestaBasica cesta = new CestaBasica();
        List<CestaBasica> cestas = cesta.getCestaBasicaDAO().pegarListaToda(Singleton.Retorna());

        if (cestas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Para cada cesta, carrega seus itens através da entidade ItemCesta
        ItemCesta itemCesta = new ItemCesta();
        for (CestaBasica c : cestas) {
            List<ItemCesta> itensCesta = itemCesta.getItemCestaDAO().buscarItensCesta(c.getId(), Singleton.Retorna());
            c.setItens(itensCesta);
        }

        List<CestaBasicaDTO> cestasDTO = CestaBasicaDTO.getListDTO(cestas);
        return ResponseEntity.ok(cestasDTO);
    }
}
