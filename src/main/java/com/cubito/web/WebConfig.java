package com.cubito.web;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer{
    
    @Bean // lo agrega al contenedor de spring
    public LocaleResolver localeResolver(){
        var slr = new SessionLocaleResolver(); //i18n hace referencia a internacionlization
        
        slr.setDefaultLocale(new Locale("es"));
        return slr;
    }
    
    
    @Bean //Modificar el idioma de forma dinamica.
    public LocaleChangeInterceptor localeChangeInterceptor(){
        var lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");      
        return lci;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(localeChangeInterceptor());
    }
    
    @Override // Mapping de la url por default debido a que esta registringido...
    public void addViewControllers(ViewControllerRegistry registro){
        registro.addViewController("/").setViewName("index"); //
        registro.addViewController("/login"); // Sin pasar por un controlador, redirige al login...
        registro.addViewController("/errores/403").setViewName("/errores/403");
    }// Mapea path siin necesidad de un controlador...
    
    
}
