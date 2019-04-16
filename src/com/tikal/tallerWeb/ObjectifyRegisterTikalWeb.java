package com.tikal.tallerWeb;

import com.googlecode.objectify.ObjectifyService;
import com.tikal.tallerWeb.modelo.entity.AutoEntity;
import com.tikal.tallerWeb.modelo.entity.ClienteEntity;
import com.tikal.tallerWeb.modelo.entity.CotizacionEntity;
import com.tikal.tallerWeb.modelo.entity.EventoEntity;
import com.tikal.tallerWeb.modelo.entity.FoliadorServicio;
import com.tikal.tallerWeb.modelo.entity.PresupuestoEntity;
import com.tikal.tallerWeb.modelo.entity.ServicioEntity;
import com.tikal.tallerWeb.modelo.servicio.Person;
import com.tikal.tallerWeb.modelo.usuario.Usuario;

public class ObjectifyRegisterTikalWeb {
	ObjectifyRegisterTikalWeb() {
        ObjectifyService.register(Person.class);
        ObjectifyService.register(AutoEntity.class);
        ObjectifyService.register(ClienteEntity.class);
        ObjectifyService.register(EventoEntity.class);
        ObjectifyService.register(PresupuestoEntity.class);
        ObjectifyService.register(ServicioEntity.class);
        ObjectifyService.register(Usuario.class);
        ObjectifyService.register(CotizacionEntity.class);
        ObjectifyService.register(FoliadorServicio.class);
     
    }
}
