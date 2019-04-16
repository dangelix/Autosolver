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

package com.tikal.tallerWeb.modelo.alerta;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
/**
 *
 */

@Entity	
public class AlertaServicio implements DatosAlerta {
	@Id 
	private Long id;
    private String nombreCliente;
    private String tipoAuto;
    private String marcaAuto;
    private String placasAuto;
    private String kilometrajeAuto;
    private String descripcionServicio;
    private String kilometrajeServicio;

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getTipoAuto() {
        return tipoAuto;
    }

    public void setTipoAuto(String tipoAuto) {
        this.tipoAuto = tipoAuto;
    }

    public String getMarcaAuto() {
        return marcaAuto;
    }

    public void setMarcaAuto(String marcaAuto) {
        this.marcaAuto = marcaAuto;
    }

    public String getPlacasAuto() {
        return placasAuto;
    }

    public void setPlacasAuto(String placasAuto) {
        this.placasAuto = placasAuto;
    }

    public String getKilometrajeAuto() {
        return kilometrajeAuto;
    }

    public void setKilometrajeAuto(String kilometrajeAuto) {
        this.kilometrajeAuto = kilometrajeAuto;
    }

    public String getDescripcionServicio() {
        return descripcionServicio;
    }

    public void setDescripcionServicio(String descripcionServicio) {
        this.descripcionServicio = descripcionServicio;
    }

    public String getKilometrajeServicio() {
        return kilometrajeServicio;
    }

    public void setKilometrajeServicio(String kilometrajeServicio) {
        this.kilometrajeServicio = kilometrajeServicio;
    }
}
