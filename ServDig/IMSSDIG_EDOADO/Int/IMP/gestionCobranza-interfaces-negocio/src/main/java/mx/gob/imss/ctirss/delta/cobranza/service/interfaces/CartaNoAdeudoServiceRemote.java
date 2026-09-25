package mx.gob.imss.ctirss.delta.cobranza.service.interfaces;

import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.cobranza.exception.EstadoAdeudoException;
import mx.gob.imss.ctirss.delta.model.enums.RespuestaOpinion32DEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;

@Remote
public interface CartaNoAdeudoServiceRemote {

	Map<String, Object> generarSolicitudCartaNoAdeudo(Persona persona,
			String usuario, Long idOrigen) throws EstadoAdeudoException;
	
	RespuestaOpinion32DEnum getOpinionRFC(Persona persona,
			String usuario, Long idOrigen) throws EstadoAdeudoException;

	void validarPersonaMismoRFC(Persona persona) throws EstadoAdeudoException;

	Map<String, Object> generarSolicitudPorFolioCartaNoAdeudo(String noFolioSolicitud, Long idOrigen)  throws EstadoAdeudoException;

}
