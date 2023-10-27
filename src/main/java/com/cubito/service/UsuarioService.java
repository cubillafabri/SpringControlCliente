package com.cubito.service;

import com.cubito.dao.UsuarioDAO;
import com.cubito.domain.Rol;
import com.cubito.domain.Usuario;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Si cambiamos debemos configurar mas cosas en este caso de forma automatica...
@Service("userDetailsService") // El bean de springsecurity no se puede cambiar porque usa spring. (Es la instancia en websecurityCOnfig)
@Slf4j //logger
public class UsuarioService implements UserDetailsService{

    @Autowired
    private UsuarioDAO usuarioDao;
    
    @Override
    @Transactional(readOnly = true)// problema de lazilyinitialization-  no tengo session por lo tanto no puede recuperar los roles.. servicio debe ser transacional...
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findByUsername(username);
        if(usuario == null){ 
            throw new UsernameNotFoundException(username);
        }
        // para recuperar los roles... Generic... (NO rol class porque necesita algo especifico.)
        var roles = new ArrayList<GrantedAuthority>(); // Tipo de roles es grantedAuthority para poder manejarlo..
        
        for (Rol rol: usuario.getRoles()) {
            roles.add(new SimpleGrantedAuthority(rol.getNombre())); // envolver grantedautority.. SimpleGran....
        }
        
        return new User(usuario.getUsername(), usuario.getPassword(), roles); //spring security de forma automatica...
    }
    
}
