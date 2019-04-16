package com.tikal.tallerWeb.modelo.entity;

import com.googlecode.objectify.annotation.Entity;

@Entity
public class EvidenceDetail {

	EventoEntity evento;
	String servicioId;
	public EventoEntity getEvento() {
		return evento;
	}
	public void setEvento(EventoEntity evento) {
		this.evento = evento;
	}
	public String getServicioId() {
		return servicioId;
	}
	public void setServicioId(String servicioId) {
		this.servicioId = servicioId;
	}
	
	
	
}
