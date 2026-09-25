package mx.gob.imss.distss.delta.rtt.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.riesgosTrabajo.RiesgosTrabajoException;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.RespuestaEscrito;

@Remote
public interface EscritoDesacuerdoExternoRemote {
	
	RespuestaEscrito consultarInfoEscrito(String folioRecepcion) throws RiesgosTrabajoException;
}
