/**
 *   Copyright 2013-2015 TIKAL-TECHNOLOGY
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.tikal.tallerWeb.data.access.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.googlecode.objectify.ObjectifyService;
import com.tikal.tallerWeb.data.access.BitacoraDAO;
//import com.tikal.tallerWeb.rest.util.RestTemplateFactory;
import com.tikal.tallerWeb.modelo.entity.EventoEntity;

import technology.tikal.taller.automotriz.model.servicio.bitacora.Evento;

/**
 * @author Nekorp
 */
@Service
public class BitacoraDAOImp implements BitacoraDAO {

	// @Autowired
	// @Qualifier("taller-RestTemplateFactory")
	// private RestTemplateFactory factory;

	 @Override
	public List<EventoEntity> cargar(Long idServicio) {
		Map<String, Object> map = new HashMap<>();
		map.put("idServicio", idServicio);
		Evento e = new Evento();

		return ObjectifyService.ofy().load().type(EventoEntity.class).filter("id", idServicio).list();
		// Evento[] r = factory.getTemplate().getForObject(factory.getRootUlr()
		// + "/servicios/{idServicio}/bitacora", Evento[].class, map);
		// return Arrays.asList(r);
		// return null;
	}

	@Override
	public List<EventoEntity> guardar(Long idServicio, List<EventoEntity> datos) {
		Map<String, Object> map = new HashMap<>();
		map.put("idServicio", idServicio);
		ObjectifyService.ofy().save().entities(datos);
		return ObjectifyService.ofy().load().type(EventoEntity.class).filter("id", idServicio).list();
		// Evento[] r = factory.getTemplate().postForObject(factory.getRootUlr()
		// + "/servicios/{idServicio}/bitacora", datos, Evento[].class, map);
		// return Arrays.asList(r);
	}

	public EventoEntity agregar(Long idServicio, EventoEntity evento) {
		evento.setId(idServicio);
		ObjectifyService.ofy().save().entities(evento).now();
		return evento;
	}

	@Override
	public EventoEntity cargarEvento(Long id) {

		return ObjectifyService.ofy().load().type(EventoEntity.class).id(id).now();
	}

	@Override
	public void borrarEvento(Long id) {
		// TODO Auto-generated method stub
		ObjectifyService.ofy().delete().type(EventoEntity.class).id(id).now();
	}

	@Override
	public EventoEntity guardar(EventoEntity datos) {
		ObjectifyService.ofy().save().entities(datos).now();
		return datos;
	}
}
