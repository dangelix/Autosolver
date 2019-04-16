package com.tikal.tallerWeb.data.access.rest.local;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.tikal.tallerWeb.data.access.rest.UsuarioDAOImp;
import com.tikal.tallerWeb.modelo.usuario.Usuario;
import com.tikal.tallerWeb.security.Rol;

public class UsuarioDAOImpLocal extends UsuarioDAOImp{
	@Override
	public Usuario consultarUsuario(String usuario) {
		List<Usuario> usu = ofy().load().type(Usuario.class).list();
		Usuario nuevo=null;
		for(Usuario u:usu){
			if(u.getUsername().compareTo(usuario)==0){
				nuevo=u;
				break;
			}
		}
		if(nuevo==null){
			return nuevo;
		}
		Rol rol = new Rol();
		if(nuevo.getTipo().compareTo("Administrador")==0){
			rol.setName("ROLE_ADMIN");

		}
		if(nuevo.getTipo().compareTo("Empleado")==0){
			//rol.setName("ROLE_USER");
			rol.setName("ROLE_ADMIN");

		}
		List<Rol> list = new ArrayList<Rol>();
		list.add(rol);
		nuevo.setAuthorities(list);
		return nuevo;
	}

}
