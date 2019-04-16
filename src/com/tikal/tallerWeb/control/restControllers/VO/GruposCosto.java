package com.tikal.tallerWeb.control.restControllers.VO;

import java.util.List;

import com.tikal.tallerWeb.modelo.entity.PresupuestoEntity;

public class GruposCosto {
	String nombre;
	List<PresupuestoEntity> presupuestos;
	String tipo;
	
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<PresupuestoEntity> getPresupuestos() {
		return presupuestos;
	}
	public void setPresupuestos(List<PresupuestoEntity> presupuestos) {
		this.presupuestos = presupuestos;
	}
	
	
}
