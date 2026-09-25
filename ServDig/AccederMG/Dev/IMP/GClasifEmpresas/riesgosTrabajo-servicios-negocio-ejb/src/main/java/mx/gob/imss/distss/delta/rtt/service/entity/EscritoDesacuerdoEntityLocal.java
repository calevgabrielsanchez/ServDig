package mx.gob.imss.distss.delta.rtt.service.entity;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.riesgosTrabajo.RiesgosTrabajoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.CausaDesacuerdo;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.DomicilioEscritoDesacuerdo;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.TramiteEscritoDesacuerdo;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.MotivosDesacuerdo;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.PatronRiesgosTrabajo;

@Local
public interface EscritoDesacuerdoEntityLocal {

	TramiteEscritoDesacuerdo guardarEscrito(TramiteEscritoDesacuerdo tramiteEscritoDesacuerdo);
	String generarFolioRecepcion(PatronRiesgosTrabajo patronRiesgosTrabajo) throws SolicitudNoValidaException;
	String getConsecutivoSubdelegacion(Subdelegacion subdelegacion) throws SolicitudNoValidaException;
	List<CausaDesacuerdo> getCausasDesacuerdo(Long idMateria);
}
