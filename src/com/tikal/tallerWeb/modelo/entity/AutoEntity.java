package com.tikal.tallerWeb.modelo.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import technology.tikal.taller.automotriz.model.auto.Auto;
import technology.tikal.taller.automotriz.model.auto.Equipamiento;

@Entity
public class AutoEntity{
	@Id public Long idAuto;
	@Index String numeroSerie;
	String numeroEconomico;
	String color;
	Equipamiento equipamiento;
	String marca;
	String modelo;
	@Index String placas;
	String tipo;
	String version;
	
	
	
	public String getNumeroEconomico() {
		return numeroEconomico;
	}

	public void setNumeroEconomico(String numeroEconomico) {
		this.numeroEconomico = numeroEconomico;
	}

	public String getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	public AutoEntity(){}
	
	public AutoEntity(Auto a){
		this.setColor(a.getColor());
		this.setEquipamiento(a.getEquipamiento());
		this.setMarca(a.getMarca());
		this.setModelo(a.getModelo());
		this.setNumeroSerie(a.getNumeroSerie());
		this.setPlacas(a.getPlacas());
		this.setTipo(a.getTipo());
		this.setVersion(a.getVersion());
		
	}
	public Long getIdEntity() {
		return idAuto;
	}

	public Long getIdAuto() {
		return idAuto;
	}

	public void setIdAuto(Long idAuto) {
		this.idAuto = idAuto;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Equipamiento getEquipamiento() {
		return equipamiento;
	}

	public void setEquipamiento(Equipamiento equipamiento) {
		this.equipamiento = equipamiento;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getPlacas() {
		return placas;
	}

	public void setPlacas(String placas) {
		this.placas = placas;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	
}
