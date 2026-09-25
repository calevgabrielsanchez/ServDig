package mx.gob.imss.cit.clienteServiciosComunes.services;

import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.v2.EntradaAlta;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.v2.EntradaConsulta;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.v2.SalidaAlta;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.v2.SalidaConsulta;

public interface BovedaPersonalV2Service {

	SalidaConsulta consultaDocumento(EntradaConsulta entradaConsulta);

	SalidaAlta altaDocumento(EntradaAlta entradaAlta);
}
