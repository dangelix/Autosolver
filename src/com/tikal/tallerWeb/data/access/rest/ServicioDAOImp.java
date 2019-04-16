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

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.tikal.tallerWeb.data.access.ServicioDAO;
import com.tikal.tallerWeb.modelo.entity.FoliadorServicio;
import com.tikal.tallerWeb.modelo.entity.PresupuestoEntity;
import com.tikal.tallerWeb.modelo.entity.ServicioEntity;


import technology.tikal.taller.automotriz.model.index.servicio.ServicioIndex;
import technology.tikal.taller.automotriz.model.servicio.metadata.ServicioMetadata;
import technology.tikal.taller.automotriz.model.servicio.moneda.Moneda;

/**
 * @author Nekorp
 */
@Service
public class ServicioDAOImp implements ServicioDAO {

	// @Autowired
	// @Qualifier("taller-RestTemplateFactory")
	// private RestTemplateFactory factory;
	//
	@Override
	public void guardar(ServicioEntity dato) {
		Date d = new Date();
		System.out.println("ESTATUS inicial:"+dato.getMetadata().getStatus());
		if (dato.getMetadata() == null) {
			ServicioMetadata sm = new ServicioMetadata();
			sm.setStatus("Diagnóstico");
			System.out.println("ESTATUS:"+sm.getStatus());
			dato.setMetadata(sm);
		}else{
			if(dato.getMetadata().getStatus()==null){
				dato.getMetadata().setStatus("Diagnóstico");
				System.out.println("ESTATUS2:"+dato.getMetadata().getStatus());
			}
		}
		
		if (dato.getIdServicio()==null) {
			dato.setFechaInicio(d);
			System.out.println("si entro a lo del folio....");
			if (ObjectifyService.ofy().load().type(FoliadorServicio.class).list().isEmpty()){
				FoliadorServicio f= new FoliadorServicio();
				f.getFolio();
				dato.setIdServicio(f.getFolio());
				f.incrementar();
				ObjectifyService.ofy().save().entity(f).now(); 
			}else{
				FoliadorServicio f=ObjectifyService.ofy().load().type(FoliadorServicio.class).list().get(0);
				//int numero = (int) (Math.random() * 9999) + 1;
				dato.setIdServicio(f.getFolio());
				//dato.setIdServicio(numero);
				f.incrementar();
				ObjectifyService.ofy().save().entity(f).now(); 
			}
			
		} 
		ObjectifyService.ofy().save().entity(dato).now();
		System.out.println("ESTATUS guard:"+dato.getMetadata().getStatus());
	}

	@Override
	public ServicioEntity cargar(Long id) {
		return ObjectifyService.ofy().load().type(ServicioEntity.class).id(id).now();
	}

	@Override
	public List<ServicioIndex> getIndiceServicios() {
		List<ServicioEntity> servs = ObjectifyService.ofy().load().type(ServicioEntity.class).order("-fechaInicio").list();
		List<ServicioIndex> result = new ArrayList<ServicioIndex>();
		for (ServicioEntity s : servs) {
			if (s.getMetadata().getStatus().compareTo("Finalizado") != 0) {
				ServicioIndex si = new ServicioIndex();
				si.setCobranza(s.getCobranza());
				si.setDescripcion(s.getDescripcion());
				si.setFechaInicio(s.getFechaInicio());
				si.setId(s.getIdServicio());
				si.setIdAuto(s.getIdAuto());
				si.setIdCliente(s.getIdCliente());
				si.setStatus(s.getMetadata().getStatus());
				List<PresupuestoEntity> presupuesto = ofy().load().type(PresupuestoEntity.class)
						.filter("id", si.getId()).list();
				float total = 0;
				for (PresupuestoEntity pr : presupuesto) {
					total += Float.parseFloat(pr.getPrecioCliente().getValue()) * pr.getCantidad();
				}
				si.setCostoTotal(new Moneda());
				si.getCostoTotal().setValue(total + "");
				result.add(si);
			}
		}
		return result;
	}

	

	@Override
	public List<ServicioIndex> getIndiceServicios(Long sinceId) {
		Map<String, Object> map = new HashMap<>();
		map.put("idServicio", sinceId);
		// PaginaServicioIndex r =
		// factory.getTemplate().getForObject(factory.getRootUlr() +
		// "/index/servicio?sinceId={idServicio}", PaginaServicioIndex.class,
		// map);
		return null;
	}

	@Override
	public List<ServicioIndex> getIndiceServiciosMismoAuto(String numeroSerie) {
		Map<String, Object> map = new HashMap<>();
		map.put("numeroSerieAuto", numeroSerie);
		List<ServicioIndex> servicios = ObjectifyService.ofy().load().type(ServicioIndex.class)
				.filter("idAuto", numeroSerie).list();
		return servicios;
	}

	@Override
	public List<ServicioIndex> getIndiceServiciosPorStatus(String status) {
		Map<String, Object> map = new HashMap<>();
		map.put("status", status);
		List<ServicioEntity> servicios = ObjectifyService.ofy().load().type(ServicioEntity.class).order("-fechaInicio").list();
		System.out.println("servicios en dao:"+servicios.size());
		List<ServicioIndex> ret = new ArrayList<ServicioIndex>();
		List<PresupuestoEntity> lista=ofy().load().type(PresupuestoEntity.class).order("indice").list();
		//int tama=0;
		for (ServicioEntity s : servicios) {
			if (s.getMetadata().getStatus().compareTo("Finalizado") != 0) {
				//tama++;
				ServicioIndex si = new ServicioIndex();
				si.setCobranza(s.getCobranza());
				si.setDescripcion(s.getDescripcion());
				si.setFechaInicio(s.getFechaInicio());
				si.setId(s.getIdServicio());
				si.setIdAuto(s.getIdAuto());
				si.setIdCliente(s.getIdCliente());
				si.setStatus(s.getMetadata().getStatus());
				 	 	
				List<PresupuestoEntity> presupuesto= new ArrayList<PresupuestoEntity>();
				for(PresupuestoEntity p: lista){
		    		if(p.getId()==null){
		    			ofy().delete().entity(p).now();
		    			continue;
		    		}
		    		if(p.getId().compareTo(s.getIdServicio())==0){
		    			presupuesto.add(p);
		    		}
		    	}
				
				float total = 0;
				for (PresupuestoEntity pr : presupuesto) {
					if(pr.isAutorizado()){
						total += Float.parseFloat(pr.getPrecioCliente().getValue()) * pr.getCantidad();
					}
				}
				si.setCostoTotal(new Moneda());
				si.getCostoTotal().setValue(total + "");
				ret.add(si);
				System.out.println("lista return :"+ret.size());
//				if(tama>=20){
//					break;
//				}
			}
		}
		// PaginaServicioIndex r =
		// factory.getTemplate().getForObject(factory.getRootUlr() +
		// "/index/servicio?statusServicio={status}", PaginaServicioIndex.class,
		// map);
		// return r.getItems();
		return ret;
	}

	@Override
	public List<ServicioEntity> getByDate_(DateTime fechaInicial, DateTime fechaFinal){
		Date hoy= new Date();
		SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-mm-dd");
		String fecha = dt1.format(hoy);
		
		try {
			hoy= dt1.parse(fecha);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<ServicioEntity> servicios=  ObjectifyService.ofy().load().type(ServicioEntity.class).filter("fechaInicio >=",fechaInicial).filter("fechaInicio <=",fechaFinal).list();
	//	List<ServicioEntity> servicios = ObjectifyService.ofy().load().type(ServicioEntity.class).filter("fechaInicio >",hoy).list();
		List<ServicioEntity> res = new ArrayList<ServicioEntity>();
		Date fi = fechaInicial.toDate();
		Date ff = fechaFinal.toDate();
		for (ServicioEntity servicio : servicios) {
			if (servicio.getFechaInicio().getDate() >= fi.getDate()
					&& servicio.getFechaInicio().getMonth() >= fi.getMonth()
					&& servicio.getFechaInicio().getYear() >= fi.getYear()
					&& servicio.getFechaInicio().getDate() <= ff.getDate()
					&& servicio.getFechaInicio().getMonth() <= ff.getMonth()
					&& servicio.getFechaInicio().getYear() <= ff.getYear()) {
				res.add(servicio);
			}
		}
		return res;
	}
	
	@Override
	public List<ServicioEntity> getByDate(DateTime fechaInicial, DateTime fechaFinal){
		System.out.println("----*****-inicio:"+fechaInicial);
    	System.out.println("-----******fin:"+fechaFinal);
    	Date f1= fechaInicial.toDate();
		Date f2= fechaFinal.toDate();
		System.out.println("TSTMP inicio:"+f1);
    	System.out.println("TMSTMP fin:"+f2);
		
	
		List<ServicioEntity> servicios= ObjectifyService.ofy().load().type(ServicioEntity.class).filter("fechaInicio >=",f1).filter("fechaInicio <=",f2).list();
		
		return servicios;
	}
	
	@Override
	public List<ServicioIndex> getServiciosHoy() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Key<ServicioEntity> getKey(Long id){
		return Key.create(ServicioEntity.class,id);
	}

	public void crearFoliador(int folio){
		FoliadorServicio f= new FoliadorServicio();
		f.setFolio(folio);
		ObjectifyService.ofy().save().entity(f);
	}
	@Override
	public List<ServicioEntity> getAllServices(){
		return ofy().load().type(ServicioEntity.class).order("fechaInicio").list();
	}

	@Override
	public int cuentaPags() {
		return ((ofy().load().type(ServicioEntity.class).order("fechaInicio").count()-1)/20)+1;
		
	}

	@Override
	public List<ServicioEntity> getAllServicesP(int pag) {
		return ofy().load().type(ServicioEntity.class).order("fechaInicio").offset((pag-1)*20).limit(20).list();
	}
	
	@Override
	public List<ServicioIndex> getIndiceServiciosPag(int pag) {
		Map<String, Object> map = new HashMap<>();
	//	map.put("status", status);
		List<ServicioEntity> servicios = ObjectifyService.ofy().load().type(ServicioEntity.class).order("-fechaInicio").offset((pag-1)*20).limit(20).list();
		System.out.println("servicios en dao:"+servicios.size());
		List<ServicioIndex> ret = new ArrayList<ServicioIndex>();
		List<PresupuestoEntity> lista=ofy().load().type(PresupuestoEntity.class).order("indice").list();
		//int tama=0;
		for (ServicioEntity s : servicios) {
			if (s.getMetadata().getStatus().compareTo("Finalizado") != 0) {
				//tama++;
				ServicioIndex si = new ServicioIndex();
				si.setCobranza(s.getCobranza());
				si.setDescripcion(s.getDescripcion());
				si.setFechaInicio(s.getFechaInicio());
				si.setId(s.getIdServicio());
				si.setIdAuto(s.getIdAuto());
				si.setIdCliente(s.getIdCliente());
				si.setStatus(s.getMetadata().getStatus());
				 	 	
				List<PresupuestoEntity> presupuesto= new ArrayList<PresupuestoEntity>();
				for(PresupuestoEntity p: lista){
		    		if(p.getId()==null){
		    			ofy().delete().entity(p).now();
		    			continue;
		    		}
		    		if(p.getId().compareTo(s.getIdServicio())==0){
		    			presupuesto.add(p);
		    		}
		    	}
				
				float total = 0;
				for (PresupuestoEntity pr : presupuesto) {
					if(pr.isAutorizado()){
						total += Float.parseFloat(pr.getPrecioCliente().getValue()) * pr.getCantidad();
					}
				}
				si.setCostoTotal(new Moneda());
				si.getCostoTotal().setValue(total + "");
				ret.add(si);
				System.out.println("lista return :"+ret.size());
//				if(tama>=20){
//					break;
//				}
			}
		}
		// PaginaServicioIndex r =
		// factory.getTemplate().getForObject(factory.getRootUlr() +
		// "/index/servicio?statusServicio={status}", PaginaServicioIndex.class,
		// map);
		// return r.getItems();
		return ret;
	}
	
	
	@Override
	public List<ServicioIndex> getIndiceServicios1() {
		Map<String, Object> map = new HashMap<>();
	//	map.put("status", status);
		List<ServicioEntity> servicios = ObjectifyService.ofy().load().type(ServicioEntity.class).order("-fechaInicio").list();
		System.out.println("servicios en dao:"+servicios.size());
		List<ServicioIndex> ret = new ArrayList<ServicioIndex>();
		List<PresupuestoEntity> lista=ofy().load().type(PresupuestoEntity.class).order("indice").list();
		//int tama=0;
		for (ServicioEntity s : servicios) {
			if (s.getMetadata().getStatus().compareTo("Finalizado") != 0) {
				//tama++;
				ServicioIndex si = new ServicioIndex();
				si.setCobranza(s.getCobranza());
				si.setDescripcion(s.getDescripcion());
				si.setFechaInicio(s.getFechaInicio());
				si.setId(s.getIdServicio());
				si.setIdAuto(s.getIdAuto());
				si.setIdCliente(s.getIdCliente());
				si.setStatus(s.getMetadata().getStatus());
				 	 	
				List<PresupuestoEntity> presupuesto= new ArrayList<PresupuestoEntity>();
				for(PresupuestoEntity p: lista){
		    		if(p.getId()==null){
		    			ofy().delete().entity(p).now();
		    			continue;
		    		}
		    		if(p.getId().compareTo(s.getIdServicio())==0){
		    			presupuesto.add(p);
		    		}
		    	}
				
				float total = 0;
				for (PresupuestoEntity pr : presupuesto) {
					if(pr.isAutorizado()){
						total += Float.parseFloat(pr.getPrecioCliente().getValue()) * pr.getCantidad();
					}
				}
				si.setCostoTotal(new Moneda());
				si.getCostoTotal().setValue(total + "");
				ret.add(si);
				System.out.println("lista return :"+ret.size());
//				if(tama>=20){
//					break;
//				}
			}
		}
		// PaginaServicioIndex r =
		// factory.getTemplate().getForObject(factory.getRootUlr() +
		// "/index/servicio?statusServicio={status}", PaginaServicioIndex.class,
		// map);
		// return r.getItems();
		return ret;
	}
}
