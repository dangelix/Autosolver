/**
 *   Copyright 2013-2015 TIKAL-TECHNOLOGY
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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tikal.tallerWeb.servicio.validacion.CalifacacionValidacion;
import com.tikal.tallerWeb.servicio.validacion.DetalleValidacion;
import com.tikal.tallerWeb.servicio.validacion.PoliticaValidacion;
import com.tikal.tallerWeb.servicio.validacion.ResultadoValidacion;
import com.tikal.tallerWeb.servicio.validacion.ValidacionBeanFactory;
import com.tikal.tallerWeb.servicio.validacion.ValidationContext;

/**
 * @author Nekorp
 */
@Service
public class ValidacionBeanFactoryImp implements ValidacionBeanFactory {
   
    @Value("TODOVALIDO")
    private PoliticaValidacion politica;
    
    public ValidacionBeanFactoryImp() {
        
    }

    public ValidacionBeanFactoryImp(PoliticaValidacion politica) {
        this.politica = politica;
    }
    
    @Override
    public DetalleValidacion buildDetalle() {
        return new DetalleValidacionImp();
    }
    
    @Override
    public DetalleValidacion buildDetalle(String mensaje, CalifacacionValidacion calificacion) {
        return new DetalleValidacionImp(mensaje, calificacion);
    }

    @Override
    public DetalleValidacion buildDetalle(CalifacacionValidacion calificacion) {
        return new DetalleValidacionImp("", calificacion);
    }

    @Override
    public ResultadoValidacion buildResultado() {
        ResultadoValidacion r = new ResultadoValidacionImp();
        r.setPoliticaValidacion(politica);
        return r;
    }

    @Override
    public ValidationContext buildContext() {
        return new ValidationContextImp();
    }

    public PoliticaValidacion getPolitica() {
        return politica;
    }

    public void setPolitica(PoliticaValidacion politica) {
        this.politica = politica;
    }
}
