package com.autobots.automanager.controles;

import com.autobots.automanager.modelo.ClienteSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/telefone")
public class TelefoneControle {
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private ClienteSelecionador clienteSelecionador;

    @PostMapping("/cadastro")
    public void cadastroTelefone() {

    }

    @PostMapping("/atualizar")
    public void atualizarTelefone(){

    }

    @PostMapping("/excluir")
    public void excluirTelefone(){

    }
}
