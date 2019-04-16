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
package com.tikal.tallerWeb.control.imp;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tikal.tallerWeb.control.AuthorityController;
//import com.tikal.tallerWeb.view.model.security.PermisosAutoView;
//import com.tikal.tallerWeb.view.model.security.PermisosBitacoraView;
//import com.tikal.tallerWeb.view.model.security.PermisosClienteView;
//import com.tikal.tallerWeb.view.model.security.PermisosCobranzaView;
//import com.tikal.tallerWeb.view.model.security.PermisosCostoView;
//import com.tikal.tallerWeb.view.model.security.PermisosInventarioDamageView;
//import com.tikal.tallerWeb.view.model.servicio.EdicionServicioMetadata;
//import com.tikal.tallerWeb.view.model.servicio.ServicioVB;

/**
 * @author Nekorp
 */
@Service
@Aspect
public class AuthorityControllerImp //implements AuthorityController 
{

//    @Autowired
//    @Qualifier("permisosClienteView")
//    private PermisosClienteView permisosCliente;
//    @Autowired
//    @Qualifier("permisosAutoView")
//    private PermisosAutoView permisosAuto;
//    @Autowired
//    private PermisosBitacoraView permisosBitacora;
//    @Autowired
//    private PermisosCostoView permisosCosto;
//    @Autowired
//    private PermisosInventarioDamageView permisosInventarioDamage;
//    @Autowired
//    private PermisosCobranzaView permisosCobranza;
//    @Autowired
//    @Qualifier(value = "servicio")
//    private ServicioVB servicioVB;
//    @Autowired
//    private EdicionServicioMetadata metadataServicio;
//    
//    @Pointcut("execution(* org.nekorp.workflow.desktop.control.ControlServicio.*(..))")
//    public void applySecurityDirectivePointCut() {
//    }
//    
//    @Pointcut("execution(* org.nekorp.workflow.desktop.control.ControlServicio.crearServicio(..)) || "
//            + "execution(* org.nekorp.workflow.desktop.control.ControlServicio.cargaServicio(..)) || "
//            + "execution(* org.nekorp.workflow.desktop.control.ControlServicio.cambiarServicio(..)) ||"
//            + "execution(* org.nekorp.workflow.desktop.control.ControlServicio.guardaServicio(..))")
//    public void cargarServicioPointCut(){
//    }
//    
//    @Pointcut("execution(* org.nekorp.workflow.desktop.control.ControlServicio.cerrarServicio(..))")
//    public void cerrarServicioPointCut(){
//    }
//    
//    @Before("cargarServicioPointCut()")
//    public void preCarga() {
//        metadataServicio.setEdicionEnPausa(true);
//    }
//    
//    @AfterReturning("applySecurityDirectivePointCut()")
//    @Override
//    public void applySecurityDirective() {
//        //creo que quieren este
//        //boolean puedeEditar = !(servicioVB.getStatus().equals("Cancelado") || servicioVB.getStatus().equals("Terminado"));
//        //pero pidieron esto
//        boolean puedeEditar = !(servicioVB.getStatus().equals("Terminado"));
//        permisosBitacora.setModificarEventos(puedeEditar);
//        permisosCosto.setPuedeEditarCostos(puedeEditar);
//        permisosInventarioDamage.setPuedeEditar(puedeEditar);
//        permisosCliente.setPuedeEditar(puedeEditar);
//        permisosAuto.setPuedeEditar(puedeEditar);
//        permisosCobranza.setModificarPagos(puedeEditar);
//        //Esto es un parche a falta de editormonitor
//        metadataServicio.setEditado(puedeEditar);
//    }
//    
//    @AfterReturning(pointcut="cargarServicioPointCut()", returning="returnVal")
//    @Override
//    public void applySecurityDirectiveAfterLoad(JoinPoint jp, boolean returnVal) {
//        if (metadataServicio.getServicioActual() != null) {
//            metadataServicio.setServicioCargado(true);
//            metadataServicio.setEditando(true);
//            metadataServicio.setEdicionEnPausa(false);
//        } else {
//            metadataServicio.setServicioCargado(false);
//            metadataServicio.setEditando(false);
//        }
//    }
//    
//    @AfterReturning("cerrarServicioPointCut()")
//    @Override
//    public void applySecurityDirectiveAfterClose() {
//        metadataServicio.setServicioCargado(false);
//        metadataServicio.setEditando(false);
//        //Esto es un parche a falta de editormonitor
//        metadataServicio.setEditado(false);
//    }
}
