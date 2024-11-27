package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.ClienteSelecionador;
import com.autobots.automanager.modelo.EnderecoAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private ClienteSelecionador clienteSelecionador;

    @Autowired
    private EnderecoAtualizador atualizador;

    @GetMapping("/atualizar/{id}")
    public void atualizarEndereco(@RequestBody Endereco endereco,@PathVariable Long id) {
        List<Cliente> clientes = clienteRepositorio.findAll();
        Cliente cliente = clienteSelecionador.selecionar(clientes, id);
        atualizador.atualizar(cliente.getEndereco(), endereco);
        clienteRepositorio.save(cliente);
    }
}
