package com.tikal.tallerWeb.modelo.usuario;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.tikal.tallerWeb.security.Rol;


@Entity
public class Usuario implements UserDetails{
	
	@Id Long id;
	@Index String usuario;
	private String pass;
	@Index String tipo;
	private List<Rol> authorities;


	@Override
	public List<Rol> getAuthorities() {
		return this.authorities;
	}
	
	public List<Rol> setAuthorities(List<Rol> authorities){
		return this.authorities = authorities;
	}

	@Override
	public String getPassword() {
		return this.pass;
	}
	public void resetPassword(){
		this.pass="";
	}

	@Override
	public String getUsername() {
		return this.usuario;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public boolean hasRole(String rol){
		for(Rol r:this.getAuthorities()){
			if(r.getName().compareTo(rol)==0){
				return true;
			}
		}
		return false;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
}
