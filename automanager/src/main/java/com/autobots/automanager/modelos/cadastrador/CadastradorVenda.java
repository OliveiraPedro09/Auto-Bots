package com.autobots.automanager.modelos.cadastrador;

import com.autobots.automanager.DTO.MercadoriaDto;
import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.entitades.Servico;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.repositorios.RepositorioMercadoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class CadastradorVenda {

    @Autowired
    private CadastradorMercadoria cadastradorMercadoria;

    @Autowired
    private RepositorioMercadoria repositorioMercadoria;

    @Autowired
    private CadastradorUsuario cadastradorUsuario;

    public Venda cadastrarVenda(Venda venda){
        Venda setVenda = new Venda();
        setVenda.setCadastro(new Date());
        setVenda.setIdentificacao(venda.getIdentificacao());
        if (venda.getCliente() != null){
            Usuario usuario = cadastradorUsuario.cadastrarUsuario(venda.getCliente());
            setVenda.setCliente(usuario);
        }
        if (venda.getMercadorias() != null){
            for (Mercadoria mercadoria : venda.getMercadorias()){
                MercadoriaDto mercadoriaDto = new MercadoriaDto(
                        mercadoria.getNome(),
                        mercadoria.getQuantidade(),
                        mercadoria.getValor(),
                        Optional.ofNullable(mercadoria.getDescricao())
                );
                Mercadoria novaMercadoria = cadastradorMercadoria.cadastrarMercadoria(mercadoriaDto);
                repositorioMercadoria.save(novaMercadoria);
                setVenda.getMercadorias().add(novaMercadoria);
            }
        }

        if (venda.getFuncionario() != null){
            Usuario usuarioFuncionario = cadastradorUsuario.cadastrarUsuario(venda.getFuncionario());
            setVenda.setFuncionario(usuarioFuncionario);
        }

        if (venda.getServicos() != null){
            for (Servico servico : venda.getServicos()) {
                setVenda.getServicos().add(servico);
            }
        }

        if (venda.getVeiculo() != null){
            setVenda.setVeiculo(venda.getVeiculo());
        }

        return setVenda;
    }
}
