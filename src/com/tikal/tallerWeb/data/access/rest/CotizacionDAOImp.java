package com.tikal.tallerWeb.data.access.rest;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.tikal.tallerWeb.data.access.CotizacionDAO;
import com.tikal.tallerWeb.modelo.entity.CotizacionEntity;
import com.tikal.tallerWeb.modelo.entity.ServicioEntity;

public class CotizacionDAOImp implements CotizacionDAO {

	@Override
	public boolean guarda(CotizacionEntity c) {
		ofy().save().entity(c).now();
		return true;
	}

	@Override
	public boolean guarda(List<CotizacionEntity> lista) {
		ofy().save().entities(lista).now();
		return true;
	}

	@Override
	public List<CotizacionEntity> consultar(String tipo, int modelo) {
		int modeloi = modelo - 2;
		int modelof = modelo + 2;
		List<CotizacionEntity> lista = ofy().load().type(CotizacionEntity.class).filter("tipo", tipo)
				.filter("modelo <=", modelof).filter("modelo >=", modeloi).filter("proveedor !=","Cliente").filter("proveedor !=","cliente").filter("proveedor !=","CLIENTE").list();
		if (lista.size() == 0) {
			return new ArrayList<CotizacionEntity>();
		}
		return lista;
	}

	@Override
	public List<CotizacionEntity> consultarFull(String tipo, int modelo, List<String> conceptos) {
		int modeloi = modelo - 2;
		int modelof = modelo + 2;
		List<CotizacionEntity> lista= new ArrayList<CotizacionEntity>();
		for(String concepto:conceptos){
		lista.addAll(ofy().load().type(CotizacionEntity.class).filter("tipo", tipo).filter("concepto",concepto)
				.filter("modelo <=", modelof).filter("modelo >=", modeloi).list());
		}
		return lista;
	}

	@Override
	public List<CotizacionEntity> consultar(Long idServicio) {
		List<CotizacionEntity> lista= new ArrayList<CotizacionEntity>();
		lista.addAll(ofy().load().type(CotizacionEntity.class).filter("servicio",idServicio).list());
		return lista;
	}

	@Override
	public void eliminar(List<CotizacionEntity> borrar) {
		ofy().delete().entities(borrar).now();
		
	}

}
