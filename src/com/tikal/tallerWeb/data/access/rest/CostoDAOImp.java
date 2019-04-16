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

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tikal.tallerWeb.control.restControllers.VO.GruposCosto;
import com.tikal.tallerWeb.data.access.CostoDAO;
//import com.tikal.tallerWeb.rest.util.RestTemplateFactory;
import com.tikal.tallerWeb.modelo.entity.PresupuestoEntity;

/**
 * @author Nekorp
 */
@Service
public class CostoDAOImp implements CostoDAO {

//    @Autowired
//    @Qualifier("taller-RestTemplateFactory")
//    private RestTemplateFactory factory;
    
    @Override
    public List<GruposCosto> cargar(Long idServicio) {
    	List<PresupuestoEntity> lista= ofy().load().type(PresupuestoEntity.class).order("indice").list();
    	Map<String,List<PresupuestoEntity>> mapa= new LinkedHashMap<String,List<PresupuestoEntity>>();
    	
    	for(PresupuestoEntity p: lista){
    		if(p.getId()==null){
    			ofy().delete().entity(p).now();
    			continue;
    		}
    		if(p.getId().compareTo(idServicio)==0){
    			if(mapa.get(p.getGrupo())!=null){
    				mapa.get(p.getGrupo()).add(p);
    			}
    			else{
    				List<PresupuestoEntity> l= new ArrayList<PresupuestoEntity>();
    				l.add(p);
    				mapa.put(p.getGrupo(), l);
    			}
    		}
    	}
    	List<GruposCosto> grupos= new ArrayList<GruposCosto>();
    	for (String key : mapa.keySet()) {
    		GruposCosto grupo= new GruposCosto();
    		grupo.setNombre(key);
    		grupo.setPresupuestos(mapa.get(key));
    		grupo.setTipo(grupo.getPresupuestos().get(0).getTipo());
    		grupos.add(grupo);
    		System.out.println("grupo::"+grupo.getNombre());
    	}
        return grupos;
    }

    @Override
    public List<PresupuestoEntity> guardar(Long idServicio, List<PresupuestoEntity> datos) {
    	List<PresupuestoEntity> todos=ofy().load().type(PresupuestoEntity.class).list();
    	List<PresupuestoEntity> presups= new ArrayList<PresupuestoEntity>();
    	System.out.println("toodos::"+todos);
    	for(PresupuestoEntity p:todos){
    		System.out.println("p.getid::"+p.getId());
        	System.out.println("idservicio:"+idServicio);
    		if(p.getId()!=null && idServicio!=null){
	    		if(p.getId()==idServicio){
	    			presups.add(p);
	    			System.out.println("id pres ant:"+p.idPresupuesto);
	        		
	    		}
    		}
    	}
    	ArrayList<PresupuestoEntity> eliminados=new ArrayList<PresupuestoEntity>();
    	
    	for(PresupuestoEntity p:presups){   //todos los presupuestos del idSerficio
    		System.out.println("lista de los presupuestos ya guardados"+presups);
    		System.out.println("presupuesto guardado :"+p.idPresupuesto);
    		boolean esta=false;
    		System.out.println("lista de los nuevos presups:"+datos);
    		for(PresupuestoEntity p2:datos){ //los nuevos presupuestos que va a guardar
		    			
		        		System.out.println("id new presupuesto:"+p2.getIdPresupuesto());
		        		System.out.println("id old presupuesto:"+p.getIdPresupuesto());
		        		if (p2.getIdPresupuesto()==null){
		        			esta=true;
		        			break;
		        		}
		    			if(p.getIdPresupuesto().compareTo(p2.getIdPresupuesto())==0){
		    			//if(p.getIdPresupuesto() == p2.getIdPresupuesto()){p.getIdPresupuesto().compareTo(p2.getIdPresupuesto())==0 || 
		    				System.out.println("esta en true:"+esta);
		    				esta=true;
		    				break;
		    			}
    		}
    		System.out.println("esta en:"+esta);
    		if(esta==false){
    			eliminados.add(p);
    			System.out.println("presupuesto agregado a los que se borraran :"+p.idPresupuesto);
    		}
    	}
    	
    	ofy().save().entities(datos).now();
    	ofy().delete().entities(eliminados).now();
//        RegistroCosto[] r = factory.getTemplate().postForObject(factory.getRootUlr() + "/servicios/{idServicio}/costo", datos, RegistroCosto[].class, map);
//        return Arrays.asList(r);
        return datos;
    }

}
