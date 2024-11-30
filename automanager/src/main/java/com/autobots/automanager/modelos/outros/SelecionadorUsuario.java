
package com.autobots.automanager.modelos.outros;

import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SelecionadorUsuario {

    public Usuario selecionarUsername(List<Usuario> usuarios, String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCredencial().getNomeUsuario().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

    public List<Usuario> selecionarPorCargo(List<Usuario> usuarios, PerfilUsuario cargo){
        List<Usuario> filtrados = new ArrayList<Usuario>();
        for(Usuario usuario : usuarios) {
            if(usuario.getPerfis().contains(cargo)) {
                filtrados.add(usuario);
            }
        }
        return filtrados;
    }
}
