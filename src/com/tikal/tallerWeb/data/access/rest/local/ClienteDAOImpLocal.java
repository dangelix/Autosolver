package com.tikal.tallerWeb.data.access.rest.local;

import java.util.List;

import com.googlecode.objectify.ObjectifyService;
import com.tikal.tallerWeb.data.access.rest.ClienteDAOImp;
import com.tikal.tallerWeb.modelo.entity.ClienteEntity;

public class ClienteDAOImpLocal extends ClienteDAOImp{
	@Override
	public ClienteEntity buscarCliente(String nombre) {
		//System.out.println("liiueyrwuiyecgb erceiurciqu");
		List<ClienteEntity> lista=ObjectifyService.ofy().load().type(ClienteEntity.class).filter("nombre",nombre).list();
		System.out.println("lista:"+lista.size());
		if(lista.size()>0){
			for (ClienteEntity c:lista){
				System.out.println("nombre:"+c.getNombre());
				if (c.getNombre()==nombre){
					return c;
				}				
			}
		}
		return null;
	}
		
		
		
//		
//		List<ClienteEntity> lista=ObjectifyService.ofy().load().type(ClienteEntity.class).list();
//		for(ClienteEntity cliente:lista){
//			if(cliente.getNombre().toUpperCase().contains(nombre.toUpperCase())){
//				return cliente;
//			}
//		}
//		return null;
//	}
}
