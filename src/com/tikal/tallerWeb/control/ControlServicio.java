/**
 *   Copyright 2015 TIKAL-TECHNOLOGY
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
package com.tikal.tallerWeb.control;

import com.tikal.tallerWeb.modelo.servicio.ServicioLoaded;

/**
 *
 * @author Nekorp
 */
public interface ControlServicio {
    /**
     * solicita que se cree un nuevo servicio.
     * @return 
     */
    boolean crearServicio();
    /**
     * solicita que se cargue un nuevo servicio.
     * @param idServicio el id del servicio.
     * @return true si lo cargo
     */
    boolean cargaServicio(Long idServicio);
    
    /**
     * solicita que se cambie a un servicio previamente cargado.
     * @param servicio
     * @return 
     */
    boolean cambiarServicio(ServicioLoaded servicio);
    
    /**
     * guarda el servicio actualmente cargado.
     * @return 
     */
    boolean guardaServicio();
    
    /**
     * elimina de la memoria un servicio cargado.
     */
    void cerrarServicio();
}
