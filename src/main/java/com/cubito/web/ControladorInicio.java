package com.cubito.web;

import com.cubito.domain.Persona;
import com.cubito.service.PersonaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller //Spring MVC
@Slf4j
public class ControladorInicio {

    @Autowired //injeccion es lo mismo que injected
    private PersonaService personaService;

    @GetMapping("/")
    public String inicio(Model model, @AuthenticationPrincipal User user) {
        var personas = personaService.listarPersonas();
        //log.info(personas.toString());
        log.info("Ejecutando el controlador Sprign MVC");
        log.info("Usuario logeado: " +user);

        model.addAttribute("personas", personas);

        return "index"; 
    }

    @GetMapping("/agregar") // no usamos mas new Persona();
    public String agregar(Persona persona) { // Injectamos un objeto tipo persona... dentro del metodo o como parametro -spring lo injecta-
  
        return "modificar"; //Vista de agregar y modificar. 
    }
    
    @PostMapping("/guardar")
    public String guardar(@Valid Persona persona, Errors errores){
        if(errores.hasErrors()){ // No funca...
            return "modificar";
        }
        personaService.guardar(persona);
        return "redirect:/";
    }
    
    
    @GetMapping("/editar/{idPersona}") // Path Variable /editar/1 debe ser igual que el atributo de la clase persona...
    public String editar(Persona persona, Model model) { // Injectamos un objeto tipo persona... dentro del metodo o como parametro -spring lo injecta- lo asocia de forma directa...
        persona = personaService.encontrarPersona(persona); // Esto busca encontrar a esa persona por id.
        model.addAttribute("persona", persona); // Esto permite compartir los datos con la vista 
        return "modificar"; //Vista de agregar y modificar. 
    }
    
    @GetMapping("/eliminar") //De forma automatica lo reconoce...
    public String eliminar(Persona persona){
        personaService.eliminar(persona);
        return "redirect:/"; 
    }

}