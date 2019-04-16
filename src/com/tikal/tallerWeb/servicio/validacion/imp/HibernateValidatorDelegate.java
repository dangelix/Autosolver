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
package com.tikal.tallerWeb.servicio.validacion.imp;

import java.util.Locale;
import java.util.Set;

//import javax.validation.ConstraintViolation;
//import javax.validation.Validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.AbstractMessageSource;

import com.tikal.tallerWeb.servicio.validacion.CalifacacionValidacion;
import com.tikal.tallerWeb.servicio.validacion.DetalleValidacion;
import com.tikal.tallerWeb.servicio.validacion.ValidationContext;

/**
 *
 * @author Nekorp
 * Esta mal planteado desde el inicio el sistema de validaciones, ya que esta pensado para validar objetos completos
 * pero se necesita por propiedad para marcar campos individuales en la gui
 */
public class HibernateValidatorDelegate extends ValidadorEncadenable {

//    private Validator validator;
    private String propertyPath;
    private AbstractMessageSource messageSource;
    
//    public HibernateValidatorDelegate(Validator validator) {
//        this.validator = validator;
//    }
    
    @Override
    public void doValidacion(Object o, DetalleValidacion detalle, ValidationContext ctx) {
//        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(o);
//        for (ConstraintViolation<Object> x: constraintViolations) {
//            if (StringUtils.equals(propertyPath, x.getPropertyPath().toString())) {
//                detalle.setCalificacion(CalifacacionValidacion.INVALIDO);
//                if (messageSource != null) {
//                    Locale locale = LocaleContextHolder.getLocale();
//                    String code = x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName() + "." + o.getClass().getSimpleName() + "." + x.getPropertyPath().toString();
//                    String msg = messageSource.getMessage(code, null, x.getMessage(), locale);
//                    detalle.setMensaje(msg);
//                } else {
//                    detalle.setMensaje(x.getMessage());
//                }
//                return;
//            }
//        }
//        detalle.setCalificacion(CalifacacionValidacion.VALIDO);
    }

//    public void setValidator(Validator validator) {
//        this.validator = validator;
//    }

    public void setPropertyPath(String propertyPath) {
        this.propertyPath = propertyPath;
    }

    public void setMessageSource(AbstractMessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
}
