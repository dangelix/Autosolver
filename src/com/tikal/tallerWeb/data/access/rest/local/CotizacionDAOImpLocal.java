package com.tikal.tallerWeb.data.access.rest.local;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.tikal.tallerWeb.data.access.rest.CotizacionDAOImp;
import com.tikal.tallerWeb.modelo.entity.CotizacionEntity;

public class CotizacionDAOImpLocal extends CotizacionDAOImp{
	@Override
	public List<CotizacionEntity> consultar(String tipo, int modelo) {
		int modeloi = modelo - 2;
		int modelof = modelo + 2;
		List<CotizacionEntity> lista = ofy().load().type(CotizacionEntity.class).list();
		if (lista.size() == 0) {
			return new ArrayList<CotizacionEntity>();
		}
		else{
			List<CotizacionEntity> res=new ArrayList<CotizacionEntity>();
			for(CotizacionEntity c: lista){
				if(c.getTipo().compareToIgnoreCase(tipo)==0 &&c.getModelo()<=modelof&&c.getModelo()>=modeloi){
					res.add(c);
				}
			}
			return res;
		}
	}
}
