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

import java.util.HashMap;
import java.util.Map;

import com.tikal.tallerWeb.servicio.validacion.ValidationContext;

/**
 *
 */
public class ValidationContextImp implements ValidationContext {

    private Map<String,Object> datos;
    
    public ValidationContextImp() {
        this.datos = new HashMap<>();
    }
    
    @Override
    public Object getContextValue(String key) {
        return datos.get(key);
    }

    @Override
    public void putContextValue(String key, Object value) {
        this.datos.put(key, value);
    }
}
