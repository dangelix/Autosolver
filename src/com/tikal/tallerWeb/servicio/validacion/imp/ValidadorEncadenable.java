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
import com.tikal.tallerWeb.servicio.validacion.PoliticaValidacion;
import com.tikal.tallerWeb.servicio.validacion.ResultadoValidacion;
import com.tikal.tallerWeb.servicio.validacion.ValidacionBeanFactory;
import com.tikal.tallerWeb.servicio.validacion.Validador;
import com.tikal.tallerWeb.servicio.validacion.ValidationContext;

/**
 *
 */
public abstract class ValidadorEncadenable implements Validador {

    private Validador siguiente;
    
    private ValidacionBeanFactory factory;
    
    @Override
    public ResultadoValidacion valida(Object o) {
        return this.valida(o,this.factory.buildResultado());
    }
    
    @Override
    public ResultadoValidacion valida(Object o, ResultadoValidacion acarreo) {
        return this.valida(o,this.factory.buildResultado(), factory.buildContext());
    }

    @Override
    public ResultadoValidacion valida(Object o, ResultadoValidacion acarreo, ValidationContext ctx) {
        DetalleValidacion detalle = this.factory.buildDetalle();
        acarreo.addDetalle(detalle);
        doValidacion(o, detalle, ctx);
        if (acarreo.getPoliticaValidacion() == PoliticaValidacion.TODOVALIDO && detalle.getCalificacion() != CalifacacionValidacion.VALIDO) {
            return acarreo;
        }
        if (acarreo.getPoliticaValidacion() == PoliticaValidacion.NADAINCORRECTO && detalle.getCalificacion() == CalifacacionValidacion.INVALIDO) {
            return acarreo;
        }
        if (siguiente != null) {
            return siguiente.valida(o, acarreo, ctx);
        }
        return acarreo;
    }
    
    public abstract void doValidacion(Object o, DetalleValidacion detalle, ValidationContext ctx);
    
    public Validador getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Validador siguiente) {
        this.siguiente = siguiente;
    }

    public ValidacionBeanFactory getFactory() {
        return factory;
    }

    public void setFactory(ValidacionBeanFactory factory) {
        this.factory = factory;
    }
}

