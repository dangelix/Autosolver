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

import java.util.HashMap;
import java.util.Map;

import com.tikal.tallerWeb.servicio.monitor.MonitorCatalog;
import com.tikal.tallerWeb.servicio.monitor.imp.EditorMonitorData;

/**
 *
 * @author Nekorp
 */
public class PreferenciasEdicionActual {
    private String currentTab;
    private String currentDamageTab;
    private Integer evidenciaIndex;
    private Integer thumbIndex;
    private Map<MonitorCatalog, EditorMonitorData> monitorData;
    
    public PreferenciasEdicionActual() {
        monitorData = new HashMap<>();
    }
    
    public String getCurrentTab() {
        return currentTab;
    }

    public void setCurrentTab(String currentTab) {
        this.currentTab = currentTab;
    }

    public String getCurrentDamageTab() {
        return currentDamageTab;
    }

    public void setCurrentDamageTab(String currentDamageTab) {
        this.currentDamageTab = currentDamageTab;
    }

    public Integer getEvidenciaIndex() {
        return evidenciaIndex;
    }

    public void setEvidenciaIndex(Integer evidenciaIndex) {
        this.evidenciaIndex = evidenciaIndex;
    }

    public Integer getThumbIndex() {
        return thumbIndex;
    }

    public void setThumbIndex(Integer thumbIndex) {
        this.thumbIndex = thumbIndex;
    }

    public Map<MonitorCatalog, EditorMonitorData> getMonitorData() {
        return monitorData;
    }

    public void setMonitorData(Map<MonitorCatalog, EditorMonitorData> monitorData) {
        this.monitorData = monitorData;
    }
}
