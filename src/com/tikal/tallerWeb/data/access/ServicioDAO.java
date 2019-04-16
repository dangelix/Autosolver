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

package com.tikal.tallerWeb.data.access;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.googlecode.objectify.Key;
import com.tikal.tallerWeb.modelo.entity.ServicioEntity;

import technology.tikal.taller.automotriz.model.index.servicio.ServicioIndex;

/**
 * @author Nekorp
 */
public interface ServicioDAO {

    void guardar(ServicioEntity dato);
    
    ServicioEntity cargar(Long id);
    
    List<ServicioEntity> getByDate(DateTime fechaInicial, DateTime fechaFinal);
    
    List<ServicioEntity> getByDate_(DateTime fechaInicial, DateTime fechaFinal);
    
    List<ServicioIndex> getIndiceServicios();
    
    /**
     * consulta async
     * @param cmd 
     */
    List<ServicioIndex> getIndiceServicios(Long sinceId);
    
    List<ServicioIndex> getIndiceServiciosMismoAuto(String numeroSerie);
    
    List<ServicioIndex> getIndiceServiciosPorStatus(String status);
    
    List<ServicioIndex> getIndiceServiciosPag(int pag);
    
    List<ServicioIndex> getIndiceServicios1();
    
    List<ServicioIndex> getServiciosHoy();
    
    public List<ServicioEntity> getAllServices();
    
    public List<ServicioEntity> getAllServicesP(int pag);
    
	public Key<ServicioEntity> getKey(Long id);
	
	public void crearFoliador(int folio);
	
	public int cuentaPags();
	
}
