package com.tikal.tallerWeb.data.access.rest;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.tikal.tallerWeb.data.access.CobranzaDAO;
import com.tikal.tallerWeb.modelo.entity.ServicioEntity;

import technology.tikal.taller.automotriz.model.cobranza.PagoCobranza;
import technology.tikal.taller.automotriz.model.servicio.moneda.Moneda;

public class CobranzaDAOImp implements CobranzaDAO{

	@Override
	public void addPago(PagoCobranza pago, Long id) {
		// 
		ServicioEntity s=ofy().load().type(ServicioEntity.class).id(id).now();
//		if (pago.getMonto().equals(null) || pago.getMonto().getValue().equals("")){
//			Moneda monto = new Moneda();
//			monto.setValue("0.0");
//			pago.setMonto(monto);
//			System.out.println("monto en cero:"+pago.getMonto());
//		}
		s.getCobranza().getPagos().add(pago);
		ofy().save().entity(s);
	}

}
