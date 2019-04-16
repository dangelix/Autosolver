package com.tikal.tallerWeb.data.access.rest;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tikal.tallerWeb.data.access.UsuarioDAO;
import com.tikal.tallerWeb.modelo.usuario.Usuario;
import com.tikal.tallerWeb.security.Rol;

@Repository
public class UsuarioDAOImp implements UsuarioDAO{

	@Override
	public boolean crearUsuario(Usuario usuario) {
		ofy().save().entity(usuario).now();
		return true;
	}

	@Override
	public boolean eliminarUsuario(String usuario) {
		ofy().delete().entities(this.consultarUsuario(usuario)).now();
		return true;
	}

	@Override
	public Usuario consultarUsuario(String usuario) {
		List<Usuario> usu = ofy().load().type(Usuario.class).filter("usuario", usuario).list();
		if(usu.size()==0){
			return null;
		}
		Usuario nuevo= usu.get(0);
		Rol rol = new Rol();
		if(nuevo.getTipo().compareTo("Admnistrador")==0){
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
