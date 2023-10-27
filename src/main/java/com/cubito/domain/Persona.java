package com.cubito.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.Data; // Te genera los constructor y metodos necesarios.




@Data
@Entity
@Table(name = "persona") //Problemas de mapeo de la BD con otros S.O
public class Persona implements Serializable{ // INTERFAZ SERIABLIZABLE 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPersona;
    
    @NotBlank
    private String nombre;
    
    @NotBlank(message = "Apellido is mandatory")
    private String apellido;
    
}