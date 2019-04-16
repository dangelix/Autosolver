/**
 *   Copyright 2012-2015 TIKAL-TECHNOLOGY
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

import java.util.List;

import com.tikal.tallerWeb.modelo.preferencias.PreferenciasUsuario;
import com.tikal.tallerWeb.modelo.reporte.ParametrosReporte;
import com.tikal.tallerWeb.modelo.reporte.global.ParametrosReporteGlobal;
import com.tikal.tallerWeb.modelo.reporte.orden.servicio.ParametrosReporteOS;
//import com.tikal.tallerWeb.view.model.servicio.ServicioIndexVB;

/**
 * 
 * @author Nekorp
 */
public interface WorkflowApp extends ControlServicio, ControlCliente, ControlAuto {
    void warmupCustomer();
    void warmupAutoIndex();
    void warmupServicioIndex();
    /**
     * solicita el inicaio de la aplicacion.
     */
    void startAplicacion();
    /**
     * inicia el cierre de la aplicacion
     */
    void closeAplicacion();
    
    /**
     * @return TODOS los servicios disponibles.
     */
//    List<ServicioIndexVB> getIndexServicios();
//    List<ServicioIndexVB> getIndexServicios(Long sinceId);
    
    PreferenciasUsuario getPreferenciasUsuario();
    void setPreferenciasUsuario(PreferenciasUsuario param);
    /**
     * genera un reporte con los datos que se tienen cargados en la aplicacion.
     * @param param la ruta donde se dejara el reporte.
     */
    void generaReporte(ParametrosReporte param);
    
    void generaOrdenServicio(ParametrosReporteOS param);
    
    void generaReporteGlobal(ParametrosReporteGlobal param);
    
    /**
     * no muestras las siguientes alertas.
     */
    void cancelarAlertas();
}
