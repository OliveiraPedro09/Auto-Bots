package com.autobots.automanager.modelos.cadastrador;

import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVeiculo;
import com.autobots.automanager.repositorios.RepositorioVenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CadastradorVeiculo {

    @Autowired
    private RepositorioVeiculo repositorioVeiculo;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private RepositorioVenda repositorioVenda;

    @Autowired
    private CadastradorVenda cadastradorVenda;

    public Veiculo cadastrarVeiculo(Veiculo veiculo){
        Veiculo setVeiculo = new Veiculo();
        setVeiculo.setModelo(veiculo.getModelo());
        setVeiculo.setPlaca(veiculo.getPlaca());
        setVeiculo.setTipo(veiculo.getTipo());

        repositorioVeiculo.save(setVeiculo);

        if (veiculo.getProprietario() != null) {
            Usuario proprietario = new Usuario();
            proprietario.setNome(veiculo.getProprietario().getNome());
            proprietario.getVeiculos().add(setVeiculo);
            setVeiculo.setProprietario(proprietario);
        }
        if (veiculo.getVendas() != null) {
            for (Venda venda : veiculo.getVendas()) {
                Venda vendaAtual = cadastradorVenda.cadastrarVenda(venda);
                vendaAtual.setVeiculo(setVeiculo);
                setVeiculo.getVendas().add(vendaAtual);
            }
        }

        repositorioVeiculo.save(setVeiculo);

        return setVeiculo;
    }
}
