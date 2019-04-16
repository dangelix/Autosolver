package com.tikal.tallerWeb.control.restControllers.VO;

import java.util.List;

import com.tikal.tallerWeb.modelo.entity.CotizacionEntity;
import com.tikal.tallerWeb.rest.util.NewServiceObject;

public class DatosServicioVO {
	NewServiceObject servicio;
	List<GruposCosto> presupuesto;
	List<String> imagenes;
	List<CotizacionEntity> cotizaciones;
	//List<ItemProveedor> itemProvs;
	
	public NewServiceObject getServicio() {
		return servicio;
	}
	public void setServicio(NewServiceObject servicio) {
		this.servicio = servicio;
	}
	public List<GruposCosto> getPresupuesto() {
		return presupuesto;
	}
	public void setPresupuesto(List<GruposCosto> presupuesto) {
		this.presupuesto = presupuesto;
	}
	public List<CotizacionEntity> getCotizaciones() {
		return cotizaciones;
	}
	public void setCotizaciones(List<CotizacionEntity> cotizaciones) {
		this.cotizaciones = cotizaciones;
	}
	
	
	
	
}
