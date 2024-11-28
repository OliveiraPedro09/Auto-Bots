package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelos.*;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.DocumentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.autobots.automanager.modelo.DocumentoSelecionador;

import javax.print.Doc;
import java.util.List;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private ClienteSelecionador clienteSelecionador;

    @Autowired
    private DocumentoRepositorio documentoRepositorio;

    @Autowired
    private AdicionadorLinkDocumento adicionadorLinkDocumento;

    @Autowired
    private AdicionadorLinkCliente adicionadorLinkCliente;

    @Autowired
    private DocumentoSelecionador documentoSelecionador;

    @Autowired
    private DocumentoAtualizador documentoAtualizador;

    @Autowired
    private DocumentoCadastrador documentoCadastrador;

    @GetMapping("/visualizar/{id}")
        public ResponseEntity<Documento> visualizarDocumento(@PathVariable long id) {
        List<Documento> documentos = documentoRepositorio.findAll();
        Documento documento = documentoSelecionador.selecionar(documentos, id);
        if (documento == null){
            ResponseEntity<Documento> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        } else {
            adicionadorLinkDocumento.adicionarLink(documento);
            ResponseEntity<Documento> resposta = new ResponseEntity<>(documento, HttpStatus.OK);
            return resposta;
        }
    }

    @GetMapping("/cliente/{clienteid}")
    public ResponseEntity<List<Documento>> visualizarDocumentosCliente(@PathVariable long clienteid) {
        List<Cliente> clientes = clienteRepositorio.findAll();
        Cliente cliente = clienteSelecionador.selecionar(clientes, clienteid);
        if (clienteRepositorio == null){
            ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        } else {
            adicionadorLinkDocumento.adicionarLink(cliente.getDocumentos());
            ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(cliente.getDocumentos(), HttpStatus.OK);
            return resposta;
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Documento>> listarDocumentos() {
        List<Documento> documentos = documentoRepositorio.findAll();
        if (documentos.isEmpty()) {
            ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        } else {
            adicionadorLinkDocumento.adicionarLink(documentos);
            ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(documentos, HttpStatus.OK);
            return resposta;
        }
    }

    @PostMapping("/cadastrar/{clienteid}")
    public ResponseEntity<?> cadastrarDocumento(@RequestBody List<Documento> documentos, @PathVariable long clienteid) {
        HttpStatus status = HttpStatus.CONFLICT;
        List<Cliente> clientes = clienteRepositorio.findAll();
        Cliente cliente = clienteSelecionador.selecionar(clientes, clienteid);
        if (cliente == null){
            status = HttpStatus.NOT_FOUND;
        } else {
            documentoCadastrador.cadastro(cliente, documentos);
            clienteRepositorio.save(cliente);
            status = HttpStatus.CREATED;
        }
        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/remover/{clienteid}")
    public ResponseEntity<?> removerDocumento(@RequestBody List<Documento> documento, @PathVariable long clienteid){
        HttpStatus status = HttpStatus.CONFLICT;
        List<Cliente> clientes = clienteRepositorio.findAll();
        Cliente cliente = clienteSelecionador.selecionar(clientes, clienteid);
        if (cliente == null){
            status = HttpStatus.NOT_FOUND;
        } else {
            documentoCadastrador.cadastro(cliente, documento);
            clienteRepositorio.save(cliente);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }

    @PutMapping("/atualizar/{clienteid}")
    public ResponseEntity<?> atualizarDocumento(@RequestBody List<Documento> documento, @PathVariable long clienteid){
        HttpStatus status = HttpStatus.CONFLICT;
        List<Cliente> clientes = clienteRepositorio.findAll();
        Cliente cliente = clienteSelecionador.selecionar(clientes, clienteid);
        if (cliente == null){
            status = HttpStatus.NOT_FOUND;
        } else {
            documentoAtualizador.atualizar(cliente.getDocumentos(), documento);
            clienteRepositorio.save(cliente);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }
}
