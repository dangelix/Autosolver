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

/**
 *
 */
public class RenglonRG {

    private DatosServicioRG datosServicio;
    private DatosClienteRG datosCliente;
    private DatosAutoRG datosAuto;
    private DatosBitacoraRG datosBitacora;
    private DatosCostoRG datosCosto;

    public RenglonRG() {
        datosServicio = new DatosServicioRG();
        datosCliente = new DatosClienteRG();
        datosAuto = new DatosAutoRG();
        datosBitacora = new DatosBitacoraRG();
        datosCosto = new DatosCostoRG();
    }
    public DatosServicioRG getDatosServicio() {
        return datosServicio;
    }

    public void setDatosServicio(DatosServicioRG datosServicio) {
        this.datosServicio = datosServicio;
    }

    public DatosClienteRG getDatosCliente() {
        return datosCliente;
    }	

    public void setDatosCliente(DatosClienteRG datosCliente) {
        this.datosCliente = datosCliente;
    }

    public DatosAutoRG getDatosAuto() {
        return datosAuto;
    }

    public void setDatosAuto(DatosAutoRG datosAuto) {
        this.datosAuto = datosAuto;
    }

    public DatosBitacoraRG getDatosBitacora() {
        return datosBitacora;
    }

    public void setDatosBitacora(DatosBitacoraRG datosBitacora) {
        this.datosBitacora = datosBitacora;
    }

    public DatosCostoRG getDatosCosto() {
        return datosCosto;
    }

    public void setDatosCosto(DatosCostoRG datosCosto) {
        this.datosCosto = datosCosto;
    }

}
