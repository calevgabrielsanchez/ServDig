package mx.gob.imss.cit.clienteServiciosComunes.services.impl;

import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.v2.EntradaAlta;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.v2.EntradaConsulta;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.v2.IESServicioSoap;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.v2.SalidaAlta;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.v2.SalidaConsulta;
import mx.gob.imss.cit.clienteServiciosComunes.services.BovedaPersonalV2Service;

public class BovedaPersonalV2ServiceImpl implements BovedaPersonalV2Service {

	private IESServicioSoap iESServicioSoap;

	public SalidaConsulta consultaDocumento(EntradaConsulta entradaConsulta) {
		return iESServicioSoap.consultaDocumento(entradaConsulta);
	}

	public SalidaAlta altaDocumento(EntradaAlta entradaAlta) {
		return iESServicioSoap.altaDocumento(entradaAlta);
	}

	public IESServicioSoap getiESServicioSoap() {
		return iESServicioSoap;
	}

	public void setiESServicioSoap(IESServicioSoap iESServicioSoap) {
		this.iESServicioSoap = iESServicioSoap;
	}

}
