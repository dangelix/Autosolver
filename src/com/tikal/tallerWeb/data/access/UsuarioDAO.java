package com.tikal.tallerWeb.data.access;

import com.tikal.tallerWeb.modelo.usuario.*;

public interface UsuarioDAO {
	
	public boolean crearUsuario(Usuario usuario);
	public boolean eliminarUsuario(String usuario);
	public Usuario consultarUsuario(String usuario);
	
	

}
