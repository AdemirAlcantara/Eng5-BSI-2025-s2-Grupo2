package com.example.saodamiao.Control;

import com.example.saodamiao.Model.Alimento;
import com.example.saodamiao.Model.Cliente;
import com.example.saodamiao.Singleton.Conexao;
import com.example.saodamiao.Singleton.Singleton;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value="apis/clientes")
public class ClienteControl {

    @PostMapping(value = "/inserir")
    public ResponseEntity<Object> InsereAlimento(@RequestBody Cliente cliente){
        if(cliente.isCPF(cliente.getCpf()))
            if(cliente.getClienteDAO().gravar(cliente, Singleton.Retorna()))
                return ResponseEntity.ok(cliente);
        else
            return ResponseEntity.badRequest().body("CPF Inválido!!");
        return ResponseEntity.badRequest().body("Falha ao inserir o Cliente!!");
    }

}
