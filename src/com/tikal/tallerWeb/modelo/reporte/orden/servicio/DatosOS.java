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
package com.tikal.tallerWeb.modelo.reporte.orden.servicio;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class DatosOS {

    private String logo;
    private String noServicio;
    private String fecha;
    private String asesor;
    private DatosClienteOS datosCliente;
    private DatosAutoOS datosAuto;
    private String observaciones;
    private InventarioDamageOS inventarioDamage;
    private List<DetalleCostoOS> costo;
    private String totalCosto;
    private String recomendaciones;
    
    public DatosOS() {
        datosCliente = new DatosClienteOS();
        datosAuto = new DatosAutoOS();
        inventarioDamage = new InventarioDamageOS();
        costo = new LinkedList<>();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getNoServicio() {
        return noServicio;
    }

    public void setNoServicio(String noServicio) {
        this.noServicio = noServicio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getAsesor() {
        return asesor;
    }

    public void setAsesor(String asesor) {
        this.asesor = asesor;
    }

    public DatosClienteOS getDatosCliente() {
        return datosCliente;
    }

    public void setDatosCliente(DatosClienteOS datosCliente) {
        this.datosCliente = datosCliente;
    }

    public DatosAutoOS getDatosAuto() {
        return datosAuto;
    }

    public void setDatosAuto(DatosAutoOS datosAuto) {
        this.datosAuto = datosAuto;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public InventarioDamageOS getInventarioDamage() {
        return inventarioDamage;
    }

    public void setInventarioDamage(InventarioDamageOS inventarioDamage) {
        this.inventarioDamage = inventarioDamage;
    }

    public List<DetalleCostoOS> getCosto() {
        return costo;
    }

    public void setCosto(List<DetalleCostoOS> costo) {
        this.costo = costo;
    }

    public String getTotalCosto() {
        return totalCosto;
    }

    public void setTotalCosto(String totalCosto) {
        this.totalCosto = totalCosto;
    }

    public String getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
    }
}
