package com.tikal.tallerWeb.modelo.entity;

import java.util.Date;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class CotizacionEntity {
	
	@Id private Long id;
	@Index private String tipo;
	@Index private int modelo;
	@Index private String proveedor;
	@Index private String concepto;
	@Index private int indice;
	@Index private Date fecha;
	@Index private Long servicio;
	private boolean selected;
	
	public CotizacionEntity(){
		fecha = new Date();
	}
	
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	String precio;
	String tiempo;
	
	
	public Long getServicio() {
		return servicio;
	}
	public void setServicio(Long servicio) {
		this.servicio = servicio;
	}
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public int getModelo() {
		return modelo;
	}
	public void setModelo(int modelo) {
		this.modelo = modelo;
	}
	public String getProveedor() {
		return proveedor;
	}
	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}
	public String getTiempo() {
		return tiempo;
	}
	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}
	
	public boolean guardable(){
		if(this.getConcepto()==null){
			return false;
		}
		if(this.getPrecio()==null){
			return false;
		}
		if(this.getProveedor()==null){
			return false;
		}
		if(this.getTiempo()==null){
			return false;
		}
		if(this.getTipo()==null){
			return false;
		}
		if(this.getConcepto().length()==0||this.getPrecio().length()==0||this.getProveedor().length()==0||this.getTiempo().length()==0||this.getTipo().length()==0){
			return false;
		}
		return true;
	}
	public int getIndice() {
		return indice;
	}
	public void setIndice(int indice) {
		this.indice = indice;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
}
