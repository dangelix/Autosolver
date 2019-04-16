package com.tikal.tallerWeb.control.restControllers.VO;

import java.util.ArrayList;
import java.util.List;

import com.tikal.tallerWeb.modelo.entity.CotizacionEntity;

public class PiezaCotizacionVO {
	private String concepto;
	private List<CotizacionEntity> costos;
	
	public PiezaCotizacionVO(){
		this.costos= new ArrayList<CotizacionEntity>();
	}
	
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public List<CotizacionEntity> getCostos() {
		return costos;
	}
	public void setCostos(List<CotizacionEntity> costos) {
		this.costos = costos;
	}
	
	
}
