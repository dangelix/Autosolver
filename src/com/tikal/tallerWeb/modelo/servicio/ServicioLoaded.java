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
package com.tikal.tallerWeb.modelo.servicio;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.tikal.tallerWeb.modelo.preferencias.PreferenciasEdicionActual;

import technology.tikal.customers.model.Customer;
import technology.tikal.taller.automotriz.model.auto.Auto;
import technology.tikal.taller.automotriz.model.servicio.Servicio;
import technology.tikal.taller.automotriz.model.servicio.auto.damage.DamageDetail;
import technology.tikal.taller.automotriz.model.servicio.bitacora.Evento;
import technology.tikal.taller.automotriz.model.servicio.costo.RegistroCosto;

/**
 *
 * @author Nekorp
 */
public class ServicioLoaded {
    private Servicio servicio;
    private List<RegistroCosto> costo;
    private List<Evento> bitacora;
    private List<DamageDetail> damage;
    private Auto auto;
    private Customer cliente;
    private PreferenciasEdicionActual preferenciasEdicion;
    
    public ServicioLoaded() {
        preferenciasEdicion = new PreferenciasEdicionActual();
    }
    
    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public List<RegistroCosto> getCosto() {
        return costo;
    }

    public void setCosto(List<RegistroCosto> costo) {
        this.costo = costo;
    }

    public List<Evento> getBitacora() {
        return bitacora;
    }

    public void setBitacora(List<Evento> bitacora) {
        this.bitacora = bitacora;
    }

    public List<DamageDetail> getDamage() {
        return damage;
    }

    public void setDamage(List<DamageDetail> damage) {
        this.damage = damage;
    }

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public Customer getCliente() {
        return cliente;
    }

    public void setCliente(Customer cliente) {
        this.cliente = cliente;
    }

    public boolean isNuevo() {
        return this.getServicio().getId() == null;
    }

    public PreferenciasEdicionActual getPreferenciasEdicion() {
        return preferenciasEdicion;
    }

    public void setPreferenciasEdicion(PreferenciasEdicionActual preferenciasEdicion) {
        this.preferenciasEdicion = preferenciasEdicion;
    }
    @Override
    public String toString() {
        String result = "";
        if (this.getServicio().getId() == null) {
            result = StringUtils.join(new String[]{
                this.auto.getTipo(),
                this.auto.getColor()
                }, ' ');
            result = StringUtils.normalizeSpace(result);
            if (StringUtils.isEmpty(result)) {
                result = "Nuevo servicio";
            }
        } else {
            result = StringUtils.join(new String[]{
                this.servicio.getId().toString(),
                this.auto.getTipo(),
                this.auto.getColor()
                }, ' ');
            result = StringUtils.normalizeSpace(result);
        }
        return result;
    }
}
