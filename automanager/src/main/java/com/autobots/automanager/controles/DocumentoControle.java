package com.autobots.automanager.controles;

import com.autobots.automanager.repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {
    @Autowired
    private ClienteRepositorio repositorio;

    @PostMapping("/cadastro")
    public void cadastroDocumento() {
    }

    @PostMapping("/atualizar")
    public void atualizarDocumento(){

    }

    @PostMapping("/excluir")
    public void excluirDocumento(){

    }
}
