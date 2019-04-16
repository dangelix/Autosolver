package com.tikal.tallerWeb.modelo.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import technology.tikal.taller.automotriz.model.servicio.bitacora.Evento;
import technology.tikal.taller.automotriz.model.servicio.bitacora.Evidencia;

@Entity
public class EventoEntity extends Evento{
	
	
	@Id private Long idEvento;
	
	public EventoEntity(){
	}
	
	public Long getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Long idServicio) {
		this.idEvento = idServicio;
	}

}
