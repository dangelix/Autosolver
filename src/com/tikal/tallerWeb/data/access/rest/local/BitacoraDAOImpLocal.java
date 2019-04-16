package com.tikal.tallerWeb.data.access.rest.local;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.ObjectifyService;
import com.tikal.tallerWeb.data.access.rest.BitacoraDAOImp;
import com.tikal.tallerWeb.modelo.entity.EventoEntity;

public class BitacoraDAOImpLocal extends BitacoraDAOImp{

	@Override
	public List<EventoEntity> cargar(Long idServicio) {
		List<EventoEntity> lista=ObjectifyService.ofy().load().type(EventoEntity.class).list();
		List<EventoEntity> result=new ArrayList<EventoEntity>();
		for(EventoEntity e:lista){
			if(e.getId().compareTo(idServicio)==0){
				result.add(e);
			}
		}
		return result;
	}
}
