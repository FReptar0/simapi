package mx.utez.simapi.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mx.utez.simapi.models.UserDetailImpl;
import mx.utez.simapi.models.Usuarios;
import mx.utez.simapi.repository.UsuariosRepository;

@Service
public class UsuariosDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuarios usuario = usuariosRepository.findByCorreo(username);
        if (usuario == null) {
            return null;
        } else {
            return UserDetailImpl.build(usuario);
        }
    }
    
}
