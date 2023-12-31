package com.cubito.service;

import com.cubito.dao.PersonaDAO;
import com.cubito.domain.Persona;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // Le indicamos que es un tipo servicio... Para poder injectar esta clase como una implementacion del service en el controlador de spring.
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    private PersonaDAO personaDAO;

    @Override
    @Transactional(readOnly = true) //manejo DB solo lectura
    public List<Persona> listarPersonas() {
        return (List<Persona>) personaDAO.findAll();
    }

    @Override
    @Transactional //Commmit o rollback
    public void guardar(Persona persona) {
        personaDAO.save(persona);
    }

    @Override
    @Transactional //same
    public void eliminar(Persona persona) {
        personaDAO.delete(persona);
    }

    @Override
    @Transactional(readOnly = true)
    public Persona encontrarPersona(Persona persona) {
        return personaDAO.findById(persona.getIdPersona()).orElse(null);
    }

}