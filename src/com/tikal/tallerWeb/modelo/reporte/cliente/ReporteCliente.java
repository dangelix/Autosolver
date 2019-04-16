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

package com.tikal.tallerWeb.modelo.reporte.cliente;

import java.util.List;

/**
 *
 */
public class ReporteCliente {

    private String numeroDeServicio;
    private String nombreDelCliente;
    private String descripcionServicio;
    private String tiempoReparacion;
    private AutoRC auto;
    private List<RegistroCostoRC> registroMecanica;
    private List<RegistroCostoRC> registroHojalateriaPintura;
    private List<EventoRC> bitacora;

    public String getNumeroDeServicio() {
        return numeroDeServicio;
    }

    public void setNumeroDeServicio(String numeroDeServicio) {
        this.numeroDeServicio = numeroDeServicio;
    }

    public String getNombreDelCliente() {
        return nombreDelCliente;
    }

    public void setNombreDelCliente(String nombreDelCliente) {
        this.nombreDelCliente = nombreDelCliente;
    }

    public String getDescripcionServicio() {
        return descripcionServicio;
    }

    public void setDescripcionServicio(String descripcionServicio) {
        this.descripcionServicio = descripcionServicio;
    }

    public String getTiempoReparacion() {
        return tiempoReparacion;
    }

    public void setTiempoReparacion(String tiempoReparacion) {
        this.tiempoReparacion = tiempoReparacion;
    }

    public AutoRC getAuto() {
        return auto;
    }

    public void setAuto(AutoRC auto) {
        this.auto = auto;
    }

    public List<RegistroCostoRC> getRegistroMecanica() {
        return registroMecanica;
    }

    public void setRegistroMecanica(List<RegistroCostoRC> registroMecanica) {
        this.registroMecanica = registroMecanica;
    }

    public List<RegistroCostoRC> getRegistroHojalateriaPintura() {
        return registroHojalateriaPintura;
    }

    public void setRegistroHojalateriaPintura(List<RegistroCostoRC> registroHojalateriaPintura) {
        this.registroHojalateriaPintura = registroHojalateriaPintura;
    }

    public List<EventoRC> getBitacora() {
        return bitacora;
    }

    public void setBitacora(List<EventoRC> bitacora) {
        this.bitacora = bitacora;
    }
}
