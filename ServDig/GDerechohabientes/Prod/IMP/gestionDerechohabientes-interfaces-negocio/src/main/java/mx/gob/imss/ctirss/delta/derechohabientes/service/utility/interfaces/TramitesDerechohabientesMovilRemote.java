package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.TramiteDerechohabientesMovilDto;
import mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes.CambioClinicaResponse;
import mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes.EnvioCorreoResponse;
import mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes.RegistroDerechohabienteResponse;
import mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes.ValidaRequisitosResponse;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;

@Remote
public interface TramitesDerechohabientesMovilRemote {
	/**
	 * Metodo para la creacion y finalizacion de un tramite de registro de derechohsbientes
	 * @param datosTramite
	 * @return
	 * @throws TramiteMovilException
	 */
	RegistroDerechohabienteResponse ejecutaRegistroDerechohabiente(TramiteDerechohabientesMovilDto datosTramite, Domicilio domicilio, String correo);
	/**
	 * Metodo para la creacion y finalizacion de un tramite de cambio de clinica
	 * @param datosTramite
	 * @return
	 * @throws TramiteMovilException
	 */
	CambioClinicaResponse ejecutaCambioClinicaDerechohabiente(TramiteDerechohabientesMovilDto datosTramite, Domicilio domicilio, String correo);
	
	EnvioCorreoResponse enviarCorreoConDocumentos(String folioSolicitud, String correo);
	
	ValidaRequisitosResponse validaRequisitosTramite(String strCurp, String strCorreo, int idTipoTramite);
}