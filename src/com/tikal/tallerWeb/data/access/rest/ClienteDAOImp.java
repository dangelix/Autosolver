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

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.objectify.ObjectifyService;
import com.tikal.tallerWeb.control.MensajesControl;
import com.tikal.tallerWeb.data.access.ClienteDAO;
import com.tikal.tallerWeb.modelo.entity.ClienteEntity;

/**
 * @author Nekorp
 */
@Service
public class ClienteDAOImp implements ClienteDAO {
//    @Autowired
//    @Qualifier("taller-RestTemplateFactory")
//    private RestTemplateFactory factory;
    @Autowired
    private MensajesControl mensajesControl;
    @Override
    public void guardar(ClienteEntity dato) {
       // if (dato.getId() == null) {
//            URI resource = factory.getTemplate().postForLocation(factory.getRootUlr() + "/clientes", dato);
//            String[] uri = StringUtils.split(resource.toString(), '/');
//            String id = uri[uri.length - 1];
//            dato.setId(Long.valueOf(id));
        	//dato.setId(Long.valueOf(ObjectifyService.ofy().load().type(ClienteEntity.class).list().size()));
        	
       // } else {
//            Map<String, Object> map = new HashMap<>();
//            map.put("id", dato.getId());
//            factory.getTemplate().postForLocation(factory.getRootUlr() + "/clientes/{id}", dato, map);
        	
       // }
        ObjectifyService.ofy().save().entity(dato).now();
    }

    @Override
    public List<ClienteEntity> consultaTodos() {
//        PaginaCliente r = factory.getTemplate().getForObject(factory.getRootUlr() + "/clientes", PaginaCliente.class);
//        return r.getItems();
    	return ObjectifyService.ofy().load().type(ClienteEntity.class).list();
    }

    @Override
    public List<ClienteEntity> consultaTodosGroupBy() {
//        PaginaCliente r = factory.getTemplate().getForObject(factory.getRootUlr() + "/clientes", PaginaCliente.class);
//        return r.getItems();
    	return ObjectifyService.ofy().load().type(ClienteEntity.class).list();
    }
    
    @Override
    public ClienteEntity cargar(Long id) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("id", id);
//        Cliente r = factory.getTemplate().getForObject(factory.getRootUlr() + "/clientes/{id}", Cliente.class, map);
//        return r;
    	return ObjectifyService.ofy().load().type(ClienteEntity.class).id(id).now();
    	 
    }

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

	@Override
	public List<ClienteEntity> buscarClientes(String nombre) {
		List<ClienteEntity> result= new ArrayList<ClienteEntity>();
		List<ClienteEntity> todos = this.consultaTodos();
		for(ClienteEntity cliente: todos){
			if(cliente.getNombre().toLowerCase().contains(nombre.toLowerCase())){
				result.add(cliente);
			}
		}
		return result;
	}
	
	@Override
	public List<ClienteEntity> buscarClientesGroupBy(String nombre) {
		List<ClienteEntity> result= new ArrayList<ClienteEntity>();
		List<ClienteEntity> todos = this.consultaTodosGroupBy();
		for(ClienteEntity cliente: todos){
			if(cliente.getNombre().toLowerCase().contains(nombre.toLowerCase())){
				
				result.add(cliente);
			}
		}
		return result;
	}
	
	

//    @Override
//    public Cliente buscarUnico(String name) {
//        String url;
//        if (StringUtils.isEmpty(name)) {
//            return null;
//        } else {
//            url = getRootUlr() + "/clientes?filtroNombre={nombre}";
//        }
//        Map<String, Object> map = new HashMap<>();
//        map.put("nombre", name);
//        PaginaCliente r = getTemplate().getForObject(url, PaginaCliente.class, map);
//        if (r.getItems().size() != 1) { //no se encontro un unico resultado
//            return null;
//        }
//        return r.getItems().get(0);
//    }

    

}
