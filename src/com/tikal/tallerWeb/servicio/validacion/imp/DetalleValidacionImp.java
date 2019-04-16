/**
 *   Copyright 2013 Nekorp
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

package com.tikal.tallerWeb.servicio.validacion.imp;

import com.tikal.tallerWeb.servicio.validacion.CalifacacionValidacion;
import com.tikal.tallerWeb.servicio.validacion.DetalleValidacion;

/**
 *
 */
public class DetalleValidacionImp implements DetalleValidacion {

    private String mensaje;
    private CalifacacionValidacion calificacion;

    public DetalleValidacionImp() {
    }
    
    public DetalleValidacionImp(String mensaje, CalifacacionValidacion calificacion) {
        this.mensaje = mensaje;
        this.calificacion = calificacion;
    }
    
    @Override
    public String getMensaje() {
        return mensaje;
    }

    @Override
    public CalifacacionValidacion getCalificacion() {
        return calificacion;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setCalificacion(CalifacacionValidacion calificacion) {
        this.calificacion = calificacion;
    }
}
