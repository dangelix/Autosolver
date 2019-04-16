package com.tikal.tallerWeb.modelo.entity;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import technology.tikal.taller.automotriz.model.cliente.Cliente;

@Entity
public class ClienteEntity extends Cliente {
	
	@Id public Long idCliente;
	
	public ClienteEntity(){}

	public Long getIdCliente() {
		return idCliente;
	}

}
