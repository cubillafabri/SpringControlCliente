package com.cubito.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncriptarPass {
    public static void main(String[] args) {
     
        var password = "123";
        System.out.println(password);
        System.out.println("Encriptado: "+ encriptarPass(password));
       
    }
    
    public static String encriptarPass(String pass){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(pass);
    }
    
}
