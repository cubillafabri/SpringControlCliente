package com.cubito.dao;

import com.cubito.domain.Persona;
import org.springframework.data.repository.CrudRepository;

public interface PersonaDAO extends CrudRepository<Persona,Long>{
    // Se puede agregar metodos personalidos que retorna en orden...
}
