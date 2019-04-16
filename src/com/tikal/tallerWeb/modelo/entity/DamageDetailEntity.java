package com.tikal.tallerWeb.modelo.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import technology.tikal.taller.automotriz.model.servicio.auto.damage.DamageDetail;

@Entity
public class DamageDetailEntity extends DamageDetail{
	
	@Id public Long id;
	
	public DamageDetailEntity(){}

	public Long getId() {
		return id;
	}

}
