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
public class DatosCostoRG {

    private String manoDeObra;
    private double costoManoDeObra;
    private double costoRefacciones;
    private double ivaCosto;
    private double manoDeObraFacturado;
    private double refaccionesFacturado;
    private double ivaFacturado;

    public DatosCostoRG() {
        manoDeObra = "";
    }
    public String getManoDeObra() {
        return manoDeObra;
    }

    public void setManoDeObra(String manoDeObra) {
        this.manoDeObra = manoDeObra;
    }

    public double getCostoManoDeObra() {
        return costoManoDeObra;
    }

    public void setCostoManoDeObra(double costoManoDeObra) {
        this.costoManoDeObra = costoManoDeObra;
    }

    public double getCostoRefacciones() {
        return costoRefacciones;
    }

    public void setCostoRefacciones(double costoRefacciones) {
        this.costoRefacciones = costoRefacciones;
    }

    public double getIvaCosto() {
        return ivaCosto;
    }

    public void setIvaCosto(double ivaCosto) {
        this.ivaCosto = ivaCosto;
    }

    public double getManoDeObraFacturado() {
        return manoDeObraFacturado;
    }

    public void setManoDeObraFacturado(double manoDeObraFacturado) {
        this.manoDeObraFacturado = manoDeObraFacturado;
    }

    public double getRefaccionesFacturado() {
        return refaccionesFacturado;
    }

    public void setRefaccionesFacturado(double refaccionesFacturado) {
        this.refaccionesFacturado = refaccionesFacturado;
    }

    public double getIvaFacturado() {
        return ivaFacturado;
    }

    public void setIvaFacturado(double ivaFacturado) {
        this.ivaFacturado = ivaFacturado;
    }

}
