package com.autobots.automanager.modelos.cadastrador;

import com.autobots.automanager.DTO.UsuarioDto;
import com.autobots.automanager.entitades.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
public class CadastradorUsuario {
    public static Usuario cadastrarUsuario(UsuarioDto usuario){
        Usuario setUsario = new Usuario();
        setUsario.setNome(usuario.nome());
        setUsario.setNomeSocial(usuario.nomeSocial());
        setUsario.setPerfis(usuario.perfis());

        for (Telefone telefone : usuario.telefones()){
            Telefone setTelefone = new Telefone();
            setTelefone.setDdd(telefone.getDdd());
            setTelefone.setNumero(telefone.getNumero());
            setUsario.getTelefones().add(setTelefone);
        }

        Endereco setEndereco = new Endereco();
        setEndereco.setEstado(usuario.endereco().getEstado());
        setEndereco.setCidade(usuario.endereco().getCidade());
        setEndereco.setBairro(usuario.endereco().getBairro());
        setEndereco.setRua(usuario.endereco().getRua());
        setEndereco.setNumero(usuario.endereco().getNumero());
        setEndereco.setCodigoPostal(usuario.endereco().getCodigoPostal());
        if (usuario.endereco().getInformacoesAdicionais() !=null){
            setEndereco.setInformacoesAdicionais(usuario.endereco().getInformacoesAdicionais());
        }
        setUsario.setEndereco(setEndereco); // setEndereco de dentro do parenteses é o objeto criado acima, ficou confuso eu sei :(

        for (Documento documento : usuario.documentos()){
            Documento setDocumento = new Documento();
            setDocumento.setTipo(documento.getTipo());
            setDocumento.setNumero(documento.getNumero());
            setUsario.getDocumentos().add(setDocumento);
        }

        for (Email email : usuario.emails()){
            Email setEmail = new Email();
            setEmail.setEndereco(email.getEndereco());
            setUsario.getEmails().add(setEmail);
        }

        Set<CredencialUsuarioSenha> credenciaisUsuarioSenha = usuario.credenciais();

        for (CredencialUsuarioSenha credencialUsuarioSenha : credenciaisUsuarioSenha){
            CredencialUsuarioSenha credencial = new CredencialUsuarioSenha();
            credencial.setNomeUsuario(credencialUsuarioSenha.getNomeUsuario());
            credencial.setSenha(credencialUsuarioSenha.getSenha());
            credencial.setInativo(false);
            credencial.setUltimoAcesso(new Date());
            credencial.setCriacao(new Date());
            setUsario.getCredenciais().add(credencial);
        }

        if (usuario.mercadorias() != null) {
            for (Mercadoria mercadoria : usuario.mercadorias()){
                Mercadoria setMercadoria = new Mercadoria();
                setMercadoria.setNome(mercadoria.getNome());
                setMercadoria.setValor(mercadoria.getValor());
                setMercadoria.setQuantidade(mercadoria.getQuantidade());
                setMercadoria.setCadastro(new Date());
                setMercadoria.setFabricao(new Date());
                setMercadoria.setValidade(new Date());
                setMercadoria.setDescricao(mercadoria.getDescricao());
                setUsario.getMercadorias().add(setMercadoria);
            }
        }

        if (usuario.vendas() != null){
            setUsario.setVendas(usuario.vendas());
        }

        if (usuario.veiculos() != null){
            setUsario.setVeiculos(usuario.veiculos());
        }
        return setUsario;
    }

    public Usuario cadastrarUsuario(Usuario usuario){
        Usuario setUsario = new Usuario();
        setUsario.setNome(usuario.getNome());
        setUsario.setNomeSocial(usuario.getNomeSocial());
        setUsario.setPerfis(usuario.getPerfis());

        for (Telefone telefone : usuario.getTelefones()){
            Telefone setTelefone = new Telefone();
            setTelefone.setDdd(telefone.getDdd());
            setTelefone.setNumero(telefone.getNumero());
            setUsario.getTelefones().add(setTelefone);
        }

        Endereco setEndereco = new Endereco();
        setEndereco.setEstado(usuario.getEndereco().getEstado());
        setEndereco.setCidade(usuario.getEndereco().getCidade());
        setEndereco.setBairro(usuario.getEndereco().getBairro());
        setEndereco.setRua(usuario.getEndereco().getRua());
        setEndereco.setNumero(usuario.getEndereco().getNumero());
        setEndereco.setCodigoPostal(usuario.getEndereco().getCodigoPostal());
        if (usuario.getEndereco().getInformacoesAdicionais() != null){
            setEndereco.setInformacoesAdicionais(usuario.getEndereco().getInformacoesAdicionais());
        }
        setUsario.setEndereco(setEndereco);

        for (Documento documento : usuario.getDocumentos()){
            Documento setDocumento = new Documento();
            setDocumento.setTipo(documento.getTipo());
            setDocumento.setNumero(documento.getNumero());
            setDocumento.setDataEmissao(new Date());
            setUsario.getDocumentos().add(setDocumento);
        }

        for (Email email : usuario.getEmails()){
            Email setEmail = new Email();
            setEmail.setEndereco(email.getEndereco());
            setUsario.getEmails().add(setEmail);
        }

        for (Credencial credencial : usuario.getCredenciais()) {
            if (credencial instanceof CredencialUsuarioSenha) {
                CredencialUsuarioSenha credencialCadastrada = new CredencialUsuarioSenha();
                CredencialUsuarioSenha original = (CredencialUsuarioSenha) credencial;
                credencialCadastrada.setNomeUsuario(original.getNomeUsuario());
                credencialCadastrada.setSenha(original.getSenha());
                credencialCadastrada.setInativo(false);
                credencialCadastrada.setCriacao(new Date());
                credencialCadastrada.setUltimoAcesso(new Date());
                setUsario.getCredenciais().add(credencialCadastrada);
            } else if (credencial instanceof CredencialCodigoBarra) {
                CredencialCodigoBarra credencialCadastrada = new CredencialCodigoBarra();
                CredencialCodigoBarra original = (CredencialCodigoBarra) credencial;
                credencialCadastrada.setCodigo(original.getCodigo());
                credencialCadastrada.setInativo(false);
                credencialCadastrada.setCriacao(new Date());
                credencialCadastrada.setUltimoAcesso(new Date());
                setUsario.getCredenciais().add(credencialCadastrada);
            }
        }

        if (usuario.getMercadorias() != null){
            for (Mercadoria mercadoria : usuario.getMercadorias()){
                Mercadoria setMercadoria = new Mercadoria();
                setMercadoria.setNome(mercadoria.getNome());
                setMercadoria.setValor(mercadoria.getValor());
                setMercadoria.setQuantidade(mercadoria.getQuantidade());
                setMercadoria.setCadastro(new Date());
                setMercadoria.setFabricao(new Date());
                setMercadoria.setValidade(new Date());
                setMercadoria.setDescricao(mercadoria.getDescricao());
                setUsario.getMercadorias().add(setMercadoria);
            }
        }

        if (usuario.getVendas() != null){
            setUsario.setVendas(usuario.getVendas());
        }

        if (usuario.getVeiculos() != null){
            setUsario.setVeiculos(usuario.getVeiculos());
        }

        return setUsario;
    }
}
