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

package com.tikal.tallerWeb.servicio.reporte.global;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tikal.tallerWeb.data.access.ClienteDAO;
import com.tikal.tallerWeb.data.access.RenglonDAO;
import com.tikal.tallerWeb.modelo.entity.ClienteEntity;
import com.tikal.tallerWeb.modelo.reporte.global.RenglonRG;

import technology.tikal.taller.automotriz.model.servicio.Servicio;

/**
 * @author Nekorp
 */
@Component
public class RenglonFactoryRG implements DataFactoryRG<RenglonRG> {

	// @Autowired
	// @Qualifier("taller-RestTemplateFactory")
	// private RestTemplateFactory factory;
	
	@Autowired
	private RenglonDAO renglonDao;
	
	@Autowired
	private ClienteDAO customerDao;

	@Override
	public RenglonRG build(Servicio data) {
		Map<String, Object> map = new HashMap<>();
		map.put("idServicio", data.getId());
		// RenglonRG r = factory.getTemplate().getForObject(factory.getRootUlr()
		// + "/reportes/global/renglones/servicio/{idServicio}",
		// RenglonRG.class, map);
		return renglonDao.getRenglon(data.getId());
		// return null;
	}

	private void fillClienteData(RenglonRG r, ClienteEntity customer) {
		if (customer != null) {
			if (customer.getNombre() != null) {
				r.getDatosCliente().setNombre(customer.getNombre());
			}
			if (customer.getContacto() != null) {
				r.getDatosCliente().setContacto(customer.getContacto());
			}
			if (customer.getDomicilio() != null) {
				String direccion = "";
				direccion = direccion + " " + customer.getDomicilio().getCalle();
				direccion = direccion + " " + customer.getDomicilio().getNumInterior();
				direccion = direccion + " " + customer.getDomicilio().getCodigoPostal();
				r.getDatosCliente().setDireccion(direccion);
				r.getDatosCliente().setColonia(customer.getDomicilio().getColonia());
				r.getDatosCliente().setCiudad(customer.getDomicilio().getCiudad());
			}

			if (customer.getTelefonoContacto().size() > 0) {
				r.getDatosCliente().setTelefono(customer.getTelefonoContacto().get(0).getValor());
			}
		}
	}
}
