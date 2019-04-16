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

import com.tikal.tallerWeb.modelo.entity.EventoEntity;

import technology.tikal.taller.automotriz.model.servicio.bitacora.Evento;

/**
 * @author Nekorp
 */
public interface BitacoraDAO {

    List<EventoEntity> guardar(Long idServicio, List<EventoEntity> datos);
    public EventoEntity guardar(EventoEntity datos);
    public List<EventoEntity> cargar(Long idServicio) ;
    public EventoEntity agregar(Long idServicio,EventoEntity evento);
    public EventoEntity cargarEvento(Long id);
    public void borrarEvento(Long id);
}
