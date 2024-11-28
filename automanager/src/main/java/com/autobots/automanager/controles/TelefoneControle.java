package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelos.*;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/telefone")
public class TelefoneControle {
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private ClienteSelecionador clienteSelecionador;

    @Autowired
    private AdicionadorLinkTelefone adicionadorLinkTelefone;

    @Autowired
    private TelefoneCadastrador telefoneCadastrador;

    @Autowired
    private TelefoneAtualizador telefoneAtualizador;

    @Autowired
    private TelefoneRemovedor telefoneRemovedor;

    @Autowired
    private TelefoneSelecionador telefoneSelecionador;

    @Autowired
    private TelefoneRepositorio telefoneRepositorio;

    @GetMapping("/visualizar/{id}")
    public ResponseEntity<Telefone> visualizarTelefone(@PathVariable long id){
        List<Telefone> telefones = telefoneRepositorio.findAll();
        Telefone telefone = telefoneSelecionador.selecionar(telefones, id);
        if (telefone == null) {
            ResponseEntity<Telefone> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        } else {
            adicionadorLinkTelefone.adicionarLink(telefone);
            ResponseEntity<Telefone> resposta = new ResponseEntity<>(telefone, HttpStatus.FOUND);
            return resposta;
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Telefone>> listarTelefones() {
        List<Telefone> telefones = telefoneRepositorio.findAll();
        if (telefones.isEmpty()) {
            ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        } else {
            adicionadorLinkTelefone.adicionarLink(telefones);
            ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(telefones, HttpStatus.OK);
            return resposta;
        }
    }

    @GetMapping("cliente/{clienteid}")
    public ResponseEntity<List<Telefone>> visualizarTelefonesCliente (@PathVariable long clienteid){
        List<Cliente> clientes = clienteRepositorio.findAll();
        Cliente cliente = clienteSelecionador.selecionar(clientes, clienteid);
        if (cliente == null){
            ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        } else {
            adicionadorLinkTelefone.adicionarLink(cliente.getTelefones());
            ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(cliente.getTelefones(), HttpStatus.OK);
            return resposta;
        }
    }

    @PostMapping("/cadastrar/{clienteid}")
    public ResponseEntity<?> cadastrarTelefone(@RequestBody List<Telefone> telefones, @PathVariable long clienteid){
        HttpStatus status = HttpStatus.CONFLICT;
        List<Cliente> clientes = clienteRepositorio.findAll();
        Cliente cliente = clienteSelecionador.selecionar(clientes, clienteid);
        if (cliente == null) {
            status = HttpStatus.NOT_FOUND;
        } else {
            telefoneCadastrador.cadastro(cliente, telefones);
            clienteRepositorio.save(cliente);
            status = HttpStatus.CREATED;
        }
        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/remover/{clienteid}")
    public ResponseEntity<?> removerTelefone(@RequestBody List<Telefone> telefone,@PathVariable long clienteid){
        HttpStatus status = HttpStatus.CONFLICT;
        List<Cliente> clientes = clienteRepositorio.findAll();
        Cliente cliente = clienteSelecionador.selecionar(clientes, clienteid);
        if (cliente == null){
            status = HttpStatus.NOT_FOUND;
        } else {
            telefoneRemovedor.excluir(cliente, telefone);
            clienteRepositorio.save(cliente);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }

    @PutMapping("/atualizar/{clienteid}")
    public ResponseEntity<?> atualizarTelefone(@RequestBody List<Telefone> telefones, @PathVariable long clienteid){
        HttpStatus status = HttpStatus.CONFLICT;
        List<Cliente> clientes = clienteRepositorio.findAll();
        Cliente cliente = clienteSelecionador.selecionar(clientes, clienteid);
        if (cliente == null) {
            status = HttpStatus.BAD_REQUEST;
        } else {
            telefoneAtualizador.atualizar(cliente.getTelefones(), telefones);
            clienteRepositorio.save(cliente);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }
}
