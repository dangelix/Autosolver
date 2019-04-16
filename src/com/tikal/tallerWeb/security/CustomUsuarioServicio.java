package com.tikal.tallerWeb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tikal.tallerWeb.data.access.rest.UsuarioDAOImp;
import com.tikal.tallerWeb.modelo.usuario.Usuario;


@Service
public class CustomUsuarioServicio {
	
	@Autowired
    private UsuarioDAOImp userDao;
    
    
   public Usuario loadUserByUsername(String usuario) throws UsernameNotFoundException {
       return userDao.consultarUsuario(usuario);
   }

}
