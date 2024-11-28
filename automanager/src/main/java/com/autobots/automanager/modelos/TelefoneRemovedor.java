package com.autobots.automanager.modelos;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TelefoneRemovedor {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();

    public void excluir(Cliente cliente, Telefone telefone) {
        if (telefone != null) {
            if (!verificador.verificar(telefone.getDdd()) && !verificador.verificar(telefone.getNumero())) {
                cliente.getTelefones().remove(telefone);
            }
        }
    }

    public void excluir(Cliente cliente, List<Telefone> telefones) {
        for (Telefone telefoneExcluido : telefones) {
            if (telefoneExcluido.getId() != null) {
                excluir(cliente, telefoneExcluido);
            }
        }
    }
}