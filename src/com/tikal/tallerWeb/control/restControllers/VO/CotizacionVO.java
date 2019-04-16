package com.tikal.tallerWeb.control.restControllers.VO;

import java.util.ArrayList;
import java.util.List;

import com.tikal.tallerWeb.modelo.entity.CotizacionEntity;

public class CotizacionVO {
	private List<CotizacionEntity> listcotizaciones;
	private String tipo;
	private String modelo;
	private List<String> proveedores;
	private List<PiezaCotizacionVO> costos;
	private String idServicio;
	private String cadena;
	private boolean full;
	
	
	public CotizacionVO(){
		this.costos= new ArrayList<PiezaCotizacionVO>();
		this.proveedores= new ArrayList<String>();
	}

	public List<PiezaCotizacionVO> getCostos() {
		return costos;
	}

	public void setCostos(List<PiezaCotizacionVO> costos) {
		this.costos = costos;
	}

	public List<CotizacionEntity> getListcotizaciones() {
		return listcotizaciones;
	}

	public void setListcotizaciones(List<CotizacionEntity> listcotizaciones) {
		this.listcotizaciones = listcotizaciones;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public List<String> getProveedores() {
		return proveedores;
	}

	public void setProveedores(List<String> proveedores) {
		this.proveedores = proveedores;
	}

	public String getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(String idServicio) {
		this.idServicio = idServicio;
	}

	public String getCadena() {
		return cadena;
	}

	public void setCadena(String cadena) {
		this.cadena = cadena;
	}

	public boolean isFull() {
		return full;
	}

	public void setFull(boolean full) {
		this.full = full;
	}
	
	
	
	
}
