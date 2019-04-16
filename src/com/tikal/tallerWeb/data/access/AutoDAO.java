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

import java.util.List;

import com.tikal.tallerWeb.modelo.entity.AutoEntity;

import technology.tikal.taller.automotriz.model.index.servicio.ServicioIndexAutoData;

/**
 * @author Nekorp
 */
public interface AutoDAO {

    void guardar(AutoEntity dato);
    
    List<ServicioIndexAutoData> buscar(final String numeroSerie, final List<ServicioIndexAutoData> cmd);
    
    AutoEntity cargar(String numeroSerie);
    
    AutoEntity cargar(long id);
    
   	List<ServicioIndexAutoData> getIndiceAutos();
   	
   	List<AutoEntity> buscar(String numeroSerie);
   	List<AutoEntity> buscarPlacas(String numeroSerie);
   	public List<AutoEntity> buscarAutos(String serie);
	public List<AutoEntity> buscarAutosPlaca(String placa);
    public List<AutoEntity> consultaTodos();
    public AutoEntity cargarPlaca(String placa);
    
    
    /**
     * consulta todos de manera asyncrona
     * @param cmd 
     */
}
