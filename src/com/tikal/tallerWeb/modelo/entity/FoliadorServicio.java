package com.tikal.tallerWeb.modelo.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class FoliadorServicio {

	@Id private Long id;
	private int folio;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getFolio() {
		if (folio==0){
			folio=1;
		
		}
		return folio;
	}
	public void incrementar() {
		this.folio++;
	}
	
	public void setFolio(int folio){
		this.folio= folio;
	}
	
	
}
