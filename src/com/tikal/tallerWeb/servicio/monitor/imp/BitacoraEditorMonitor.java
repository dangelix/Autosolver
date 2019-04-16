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
package com.tikal.tallerWeb.servicio.monitor.imp;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;


/**
 * @author  Nekorp
 *
 */
@Service
@Aspect
public class BitacoraEditorMonitor extends EditorMonitorImpV3 {

    private static final Logger LOGGER = Logger.getLogger(BitacoraEditorMonitor.class);
   
    @Pointcut("execution(* org.nekorp.workflow.desktop.view.model.bitacora..set*(..)) && "
            + "!execution(* org.nekorp.workflow.desktop.view.model.bitacora..setNombreEvento(..)) && "
            + "!execution(* org.nekorp.workflow.desktop.view.model.bitacora..setEvidencia(..)) && "
            + "!execution(* org.nekorp.workflow.desktop.view.model.bitacora.EvidenciaVB.set*(..))")
    public void modelChange() {
    }

    @Override
    public void setData(EditorMonitorData data) {
        //de momento se limpia el undo debido a que cambian los target de las listas
        super.setData(data);
        this.clear();
    }
}
