package com.autobots.automanager.controles;

import com.autobots.automanager.DTO.CredencialDto;
import com.autobots.automanager.entitades.Credencial;
import com.autobots.automanager.servicos.CredencialServico;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credenciais")
public class CredencialControle {

    @Autowired
    private CredencialServico credencialServico;

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @PostMapping("/cadastrar/{idUsuario}")
    public ResponseEntity<?> cadastrarCredencial(@PathVariable Long idUsuario, @RequestBody CredencialDto credencial, Authentication authentication) {
        String username = authentication.name();
        try {
            credencialServico.cadastrarCredencial(idUsuario, credencial, username);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarCredencial(@PathVariable Long id) {
        try {
            credencialServico.deletarCredencial(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarCredencial(@PathVariable Long id, @RequestBody CredencialDto credencial, Authentication authentication) {
        String username = authentication.name();
        try {
            credencialServico.atualizarCredencial(id, credencial, username);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/listar")
    public ResponseEntity<List<Credencial>> listarCredenciais() {
        List<Credencial> credenciais = credencialServico.listarCredenciais();
        if (credenciais.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(credenciais);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/visualizar/{id}")
    public ResponseEntity<?> visualizarCredencial(@PathVariable Long id) {
        Credencial credencial = credencialServico.visualizarCredencial(id);
        if (credencial == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(credencial);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR', 'CLIENTE')")
    @GetMapping("/usuario/{idUsuario}/listar")
    public ResponseEntity<?> listarCredenciaisUsuario(@PathVariable Long idUsuario, Authentication authentication) {
        String username = authentication.name();
        try {
            Credencial credencial = credencialServico.listarCredenciaisUsuario(idUsuario, username);
            if (credencial != null) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(credencial);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
