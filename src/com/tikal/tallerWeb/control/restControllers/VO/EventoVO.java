package com.tikal.tallerWeb.control.restControllers.VO;

import com.tikal.tallerWeb.modelo.entity.EventoEntity;

public class EventoVO {
	private EventoEntity evento;
	private String fecha;
	public EventoEntity getEvento() {
		return evento;
	}
	public void setEvento(EventoEntity evento) {
		this.evento = evento;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	
	
}
