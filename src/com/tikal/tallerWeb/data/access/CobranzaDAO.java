package com.tikal.tallerWeb.data.access;

import technology.tikal.taller.automotriz.model.cobranza.PagoCobranza;

public interface CobranzaDAO {

	public void addPago(PagoCobranza pago, Long id);
}
