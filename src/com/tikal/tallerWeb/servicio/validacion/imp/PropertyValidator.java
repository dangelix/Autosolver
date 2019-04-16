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

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.tikal.tallerWeb.servicio.validacion.DetalleValidacion;
import com.tikal.tallerWeb.servicio.validacion.ValidationContext;

/**
 *
 */
public abstract class PropertyValidator extends ValidadorEncadenable {

    private String property;

    @Override
    public void doValidacion(Object o, DetalleValidacion detalle, ValidationContext ctx) {
        if (property != null) {
            try {
                doFinalValidation(BeanUtils.getProperty(o, property), detalle, ctx);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                throw new IllegalArgumentException("la propiedad no es accesible", ex);
            }
        } else {
            doFinalValidation(o, detalle, ctx);
        }
    }
    
    public abstract void doFinalValidation(Object o, DetalleValidacion detalle, ValidationContext ctx);
    
}
