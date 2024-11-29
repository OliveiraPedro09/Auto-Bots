package com.autobots.automanager.modelos.cadastrador;

import com.autobots.automanager.entitades.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Component
public class CadastradorEmpresa {

    @Autowired
    private CadastradorMercadoria cadastradorMercadoria;

    @Autowired
    private CadastradorUsuario cadastradorUsuario;

    @Autowired
    private CadastradorVeiculo cadastradorVeiculo;
    @Autowired
    private CadastradorVenda cadastradorVenda;

    public Empresa cadastrarEmpresa(Empresa empresa){
        Empresa setEmpresa = new Empresa();
        setEmpresa.setNomeFantasia(empresa.getNomeFantasia());
        setEmpresa.setRazaoSocial(empresa.getRazaoSocial());
        if (empresa.getTelefones() != null){
            for (Telefone telefone : empresa.getTelefones()){
                Telefone setTelefone = new Telefone();
                setTelefone.setDdd(telefone.getDdd());
                setTelefone.setNumero(telefone.getNumero());
                setEmpresa.getTelefones().add(setTelefone);
            }
        }

        if (empresa.getEndereco() != null) {
            Endereco setEndereco = new Endereco();
            setEndereco.setEstado(empresa.getEndereco().getEstado());
            setEndereco.setCidade(empresa.getEndereco().getCidade());
            setEndereco.setBairro(empresa.getEndereco().getBairro());
            setEndereco.setRua(empresa.getEndereco().getRua());
            setEndereco.setNumero(empresa.getEndereco().getNumero());
            setEndereco.setCodigoPostal(empresa.getEndereco().getCodigoPostal());
            if (empresa.getEndereco().getInformacoesAdicionais() != null) {
                setEndereco.setInformacoesAdicionais(empresa.getEndereco().getInformacoesAdicionais());
            }
            setEmpresa.setEndereco(setEndereco);
        }

        setEmpresa.setCadastro(new Date());

        if (empresa.getServicos() != null){
            for (Servico servico : empresa.getServicos()){
                setEmpresa.getServicos().add(servico);
            }
        }

        if (empresa.getUsuarios() != null){
            for (Usuario usuario : empresa.getUsuarios()){
                Usuario setUsuario = cadastradorUsuario.cadastrarUsuario(usuario);
                setEmpresa.getUsuarios().add(setUsuario);
            }
        }

        if (empresa.getVendas() != null){
            for (Venda venda : empresa.getVendas()){
                Venda setVenda = cadastradorVenda.cadastrarVenda(venda);
                setEmpresa.getVendas().add(setVenda);
            }
        }

        if (empresa.getMercadorias() != null){
            for (Mercadoria mercadoria : empresa.getMercadorias()){
                Mercadoria setMercadoria = cadastradorMercadoria.cadastrarMercadoria(mercadoria);
                setEmpresa.getMercadorias().add(setMercadoria);
            }
        }

        return setEmpresa;
    }
}
