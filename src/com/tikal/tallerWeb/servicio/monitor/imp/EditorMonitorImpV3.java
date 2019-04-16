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

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Value;

import com.tikal.tallerWeb.servicio.Metadata;
import com.tikal.tallerWeb.servicio.monitor.EditorMonitor;


/**
 * @author Nekorp
 *
 */
public class EditorMonitorImpV3 implements EditorMonitor {

    private static final Logger LOGGER = Logger.getLogger(EditorMonitorImpV3.class);
    
    private EditorMonitorData data;
    private EditorLog currentUndo;
    private EditorLog currentRedo;
    @Value("#{appConfig['app.service.monitor.undo.max']}")
    private int undoSize;
    /**
     * control interno para ignorar cambios cuando el monitor no los considere importantes.
     */
    private boolean activo;
    /**
     * control externo para prenderlo/apagarlo.
     */
    private boolean encendido;

    protected EditorMonitorImpV3() {
        activo = false;
        encendido = false;
    }
    
    public void init() {
        data = new EditorMonitorData(undoSize);
    }
    
    @Before("execution(* org.nekorp.workflow.desktop.control.ControlServicio.*(..))")
    public void stopBeforeControlServicio() {
        LOGGER.debug("deteniendo el editor monitor");
        setActivo(false);
    }
    
    @AfterReturning("execution(* org.nekorp.workflow.desktop.control.ControlServicio.crearServicio(..)) || "
            + "execution(* org.nekorp.workflow.desktop.control.ControlServicio.cargaServicio(..)) || "
            + "execution(* org.nekorp.workflow.desktop.control.ControlServicio.cambiarServicio(..)) ||"
            + "execution(* org.nekorp.workflow.desktop.control.ControlServicio.guardaServicio(..))")
    public void startAfterLoad() {
        LOGGER.debug("iniciando el editor monitor");
        setActivo(true);
    }
    
    @Around("modelChange()")
    public void updateProperty(ProceedingJoinPoint pjp) throws Throwable {
        if (encendido && activo) {
            Object target = pjp.getTarget();
            Object proxyTarget = AopContext.currentProxy();
            if (!(target instanceof Metadata)) {
                String methodName = pjp.getSignature().getName();
                String propertyName = StringUtils.substringAfter(methodName, "set");
                String primeraLetra = propertyName.charAt(0) + "";
                propertyName = primeraLetra.toLowerCase() + StringUtils.substringAfter(propertyName, primeraLetra);
                processChange(proxyTarget, propertyName, pjp.getArgs()[0]);
            }
        }
        pjp.proceed();
    }
    
    private void processChange (Object target, String propertyName, Object value) {
        try {
            Object oldValue = PropertyUtils.getProperty(target, propertyName);
            
            EditorLog currentLog = new EditorLog();
            currentLog.setTarget(target);
            currentLog.setProperty(propertyName);
            currentLog.setValue(oldValue);
            
            EditorLog nextLog = new EditorLog();
            nextLog.setTarget(target);
            nextLog.setProperty(propertyName);
            nextLog.setValue(value);
            
            if (nextLog.equals(this.currentUndo)) {
                //es un undo, hay que agregarlo a la lista de redo.
                LOGGER.debug(getClass().getSimpleName() + " guardando redo:" + currentLog);
                this.data.pushRedo(currentLog);
            } else {
                //es un cambio del usuario o un redo, no importa hay que agregarlo para un posible undo
                LOGGER.debug(getClass().getSimpleName() + " guardando undo:" + currentLog);
                this.data.pushUndo(currentLog);
                if (!nextLog.equals(this.currentRedo)) {
                    //es un cambio del usuario y no un redo, hay que limpiar el redo
                    this.data.clearRedo();
                }
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            EditorMonitorImpV3.LOGGER.error(ex);
        }
    }
    
    @Override
    public void undo() {
        if (encendido && activo) {
            try {
                if (this.data.emptyUndo()) {
                    LOGGER.debug(getClass().getSimpleName() + " undo vacio");
                    return;
                }
                EditorLog log = this.data.popUndo();
                currentUndo = log;
                PropertyUtils.setProperty(log.getTarget(), log.getProperty(), log.getValue());
                currentUndo = null;
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                EditorMonitorImpV3.LOGGER.error(ex);
            }
        }
    }

    @Override
    public void redo() {
        if (encendido && activo) {
            try {
                if (this.data.emptyRedo()) {
                    LOGGER.debug(getClass().getSimpleName() + " redo vacio");
                    return;
                }
                EditorLog log = this.data.popRedo();
                currentRedo = log;
                PropertyUtils.setProperty(log.getTarget(), log.getProperty(), log.getValue());
                currentRedo = null;
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                EditorMonitorImpV3.LOGGER.error(ex);
            }
        }
    }
    
    @Override
    public void setEncendido(boolean encendido) {
        this.encendido = encendido;
    }
    
    protected void clear() {
        this.data.clearRedo();
        this.data.clearUndo();
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public EditorMonitorData getData() {
        return data;
    }

    public void setData(EditorMonitorData data) {
        if (data == null) {
            this.init();
        } else {
            this.data = data;
        }
    }

    public boolean isEncendido() {
        return encendido;
    }
}
