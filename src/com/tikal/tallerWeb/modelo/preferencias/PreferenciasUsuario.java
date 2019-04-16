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
package com.tikal.tallerWeb.modelo.preferencias;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Nekorp
 */
@Component
public class PreferenciasUsuario {
    @Value("#{appConfig['app.service.busqueda.firstId']}")
    private Long firstId;
    @Value("#{appConfig['app.service.busqueda.filtro.ultimo']}")
    private String ultimoFiltro;

    public Long getFirstId() {
        return firstId;
    }

    public void setFirstId(Long firstId) {
        this.firstId = firstId;
    }

    public String getUltimoFiltro() {
        return ultimoFiltro;
    }

    public void setUltimoFiltro(String ultimoFiltro) {
        this.ultimoFiltro = ultimoFiltro;
    }
}
