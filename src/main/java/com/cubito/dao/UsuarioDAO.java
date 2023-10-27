package com.cubito.dao;

import com.cubito.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

// Long idUsuario de tipo usuario...
public interface UsuarioDAO extends JpaRepository<Usuario, Long>{
    Usuario findByUsername(String username); 
}
