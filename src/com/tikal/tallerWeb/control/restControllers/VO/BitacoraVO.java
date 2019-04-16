package com.tikal.tallerWeb.control.restControllers.VO;

import java.util.List;

import com.tikal.tallerWeb.modelo.entity.EventoEntity;

public class BitacoraVO {
	private String id;
	private List<EventoEntity> eventos;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<EventoEntity> getEventos() {
		return eventos;
	}
	public void setEventos(List<EventoEntity> eventos) {
		this.eventos = eventos;
	}
	
	
}
