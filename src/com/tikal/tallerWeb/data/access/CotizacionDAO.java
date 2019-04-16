package com.tikal.tallerWeb.data.access;

import java.util.List;

import com.tikal.tallerWeb.modelo.entity.CotizacionEntity;

public interface CotizacionDAO {

	public boolean guarda(CotizacionEntity c);
	public boolean guarda(List<CotizacionEntity> lista);
	public List<CotizacionEntity> consultar(String tipo, int modelo);
	public List<CotizacionEntity> consultarFull(String tipo, int modelo,List<String> conceptos);
	public List<CotizacionEntity> consultar(Long idServicio);
	public void eliminar(List<CotizacionEntity> borrar);
}
