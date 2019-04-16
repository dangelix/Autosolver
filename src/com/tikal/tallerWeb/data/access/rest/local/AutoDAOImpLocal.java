package com.tikal.tallerWeb.data.access.rest.local;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.googlecode.objectify.ObjectifyService;
import com.tikal.tallerWeb.data.access.rest.AutoDAOImp;
import com.tikal.tallerWeb.modelo.entity.AutoEntity;

public class AutoDAOImpLocal extends AutoDAOImp {
	@Override
	public AutoEntity cargar(String numeroSerie) {
		if (StringUtils.isEmpty(numeroSerie)) {
			return null;
		}
		List<AutoEntity> lista = ObjectifyService.ofy().load().type(AutoEntity.class).list();
		for (AutoEntity auto : lista) {
			if (auto.getNumeroSerie() != null) {
				if (auto.getNumeroSerie().compareTo(numeroSerie) == 0) {
					return auto;
				}
			}
		}
		return null;
	}
}
