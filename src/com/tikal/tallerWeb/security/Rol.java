package com.tikal.tallerWeb.security;

import org.springframework.security.core.GrantedAuthority;

public class Rol implements GrantedAuthority{
    
    private String name;
     
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getAuthority() {
        return this.name;
    }
}