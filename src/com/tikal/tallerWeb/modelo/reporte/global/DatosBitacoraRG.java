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

package com.tikal.tallerWeb.modelo.reporte.global;

import java.util.Date;

/**
 *
 */
public class DatosBitacoraRG {

    private String diagnostico;
    private Date fechaIngresoAuto;
    private Date fechaEntregaAuto;
    private String recomendaciones;

    public DatosBitacoraRG() {
        diagnostico = "";
        recomendaciones = "";
    }
    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public Date getFechaIngresoAuto() {
        return fechaIngresoAuto;
    }

    public void setFechaIngresoAuto(Date fechaIngresoAuto) {
        this.fechaIngresoAuto = fechaIngresoAuto;
    }

    public Date getFechaEntregaAuto() {
        return fechaEntregaAuto;
    }

    public void setFechaEntregaAuto(Date fechaEntregaAuto) {
        this.fechaEntregaAuto = fechaEntregaAuto;
    }

    public String getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

}
