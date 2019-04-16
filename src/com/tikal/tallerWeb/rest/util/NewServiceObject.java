package com.tikal.tallerWeb.rest.util;

import java.util.Date;

import com.tikal.tallerWeb.modelo.entity.AutoEntity;
import com.tikal.tallerWeb.modelo.entity.ClienteEntity;
import com.tikal.tallerWeb.modelo.entity.ServicioEntity;

import technology.tikal.taller.automotriz.model.auto.Auto;
import technology.tikal.taller.automotriz.model.cliente.Cliente;
import technology.tikal.taller.automotriz.model.servicio.Servicio;

public class NewServiceObject {
	public AutoEntity auto;
	public ClienteEntity cliente;
	public ServicioEntity servicio;
	public Date fechaPago;
	
	public AutoEntity getAuto() {
		return auto;
	}

	public void setAuto(AutoEntity auto) {
		this.auto = auto;
	}

	public ClienteEntity getCliente() {
		return cliente;
	}

	public void setCliente(ClienteEntity cliente) {
		this.cliente = cliente;
	}

	public ServicioEntity getServicio() {
		return servicio;
	}

	public void setServicio(ServicioEntity servicio) {
		this.servicio = servicio;
	}

	public NewServiceObject(){
		
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}
	
}
