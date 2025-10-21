package com.example.saodamiao.Control;

import com.example.saodamiao.Model.Beneficiarios;
import com.example.saodamiao.Singleton.Erro;
import com.example.saodamiao.Singleton.Singleton;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value="/beneficiarios")
public class BeneficiariosControl {

    @PostMapping(value = "/cadastro")
    public ResponseEntity<Object> InserirBeneficiario(@RequestBody Beneficiarios beneficiarios)
    {
        if(!beneficiarios.isCPF(beneficiarios.getCpf()))
        {
            return ResponseEntity.badRequest().body(new Erro("CPF inválido!!"));
        }
        if(!Singleton.Retorna().StartTransaction())
        {
            return ResponseEntity.badRequest().body(new Erro(Singleton.Retorna().getMensagemErro()));
        }
        if(beneficiarios.getBeneficiariosDAO().pegarBeneficiario(beneficiarios.getCpf(),Singleton.Retorna()) != null)
        {
            return ResponseEntity.badRequest().body(new Erro("Já tem um Beneficiario cadastrado com esse CPF"));
        }
        if(!beneficiarios.getBeneficiariosDAO().gravar(beneficiarios, Singleton.Retorna())) {
            Singleton.Retorna().Rollback();
            return ResponseEntity.badRequest().body(new Erro("Problema ao gravar no banco de dados"));
        }
        Singleton.Retorna().Commit();
        return ResponseEntity.ok(beneficiarios);
    }


}
