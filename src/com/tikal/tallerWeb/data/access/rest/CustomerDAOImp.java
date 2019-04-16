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
package com.tikal.tallerWeb.data.access.rest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tikal.tallerWeb.control.MensajesControl;
import com.tikal.tallerWeb.data.access.CustomerDAO;

import technology.tikal.customers.model.Customer;
import technology.tikal.gae.error.model.BasicErrorMessage;

/**
 *
 * @author Nekorp
 */
@Service
public class CustomerDAOImp implements CustomerDAO {
    private static final Logger LOGGER = Logger.getLogger(CustomerDAOImp.class);
//    @Autowired
//    @Qualifier("customer-RestTemplateFactory")
//    private RestTemplateFactory factory;
    @Autowired
    private MensajesControl mensajesControl;
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public void guardar(Customer dato) {
        try {
            if (dato.getId() == null) {
//                URI resource = factory.getTemplate().postForLocation(factory.getRootUlr() + "/customer", dato);
//                String[] uri = StringUtils.split(resource.toString(), '/');
//                String id = uri[uri.length - 1];
//                CustomerPojo datoPjo = (CustomerPojo) dato;
//                datoPjo.setId(Long.valueOf(id));
            } else {
//                Map<String, Object> map = new HashMap<>();
//                map.put("id", dato.getId());
//                factory.getTemplate().postForLocation(factory.getRootUlr() + "/customer/{id}", dato, map);
            }
        }catch (HttpStatusCodeException ex) {
            try {
                BasicErrorMessage errorObj = objectMapper.readValue(ex.getResponseBodyAsString(), BasicErrorMessage.class);
                CustomerDAOImp.LOGGER.error("error msg: " + Arrays.toString(errorObj.getMessage()));
            } catch (Exception exd) {
                CustomerDAOImp.LOGGER.error(exd);
            }
            throw ex;
        }
    }


    @Override
    public Customer cargar(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
//        Customer r = factory.getTemplate().getForObject(factory.getRootUlr() + "/customer/{id}", Customer.class, map);
//        return r;
        return null;
    }

    @Override
    public Customer[] consultaTodos() {
//        PaginaCustomer r = factory.getTemplate().getForObject(factory.getRootUlr() + "/customer", PaginaCustomer.class);
//        LinkedList<Customer> filtrado = new LinkedList<>();
//        for(Customer x: r.getItems()) {
//            if (x instanceof ClienteMx) {
//                filtrado.add(x);
//            }
//        }
//        return filtrado.toArray(new Customer[filtrado.size()]);
    	return null;
    }

}
