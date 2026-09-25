package mx.gob.imss.distss.delta.rtt.service.entity;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.riesgosTrabajo.RiesgosTrabajoException;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.DomicilioEscritoDesacuerdo;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.MotivosDesacuerdo;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.TramiteEscritoDesacuerdo;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.DiasFestivos;

@Local
public interface ConsultaEscritoDesacuerdoLocal {


	TramiteEscritoDesacuerdo findEscritoDesacuerdoFolio(String folio) throws RiesgosTrabajoException;
	DomicilioEscritoDesacuerdo findDomEscritoDesacuerdo(Long idtramDomEsc) throws RiesgosTrabajoException;
	List<MotivosDesacuerdo> getMotivosDesacuerdoList(int idEscrito) throws RiesgosTrabajoException;
	List<MotivosDesacuerdo> getFraccionClaseList(String clase) throws RiesgosTrabajoException;

	boolean saveDomEscritoDes (DomicilioEscritoDesacuerdo domEscrDes, int querAct) throws RiesgosTrabajoException;

	List<TramiteEscritoDesacuerdo> findEscritoDesacuerdoRegPatronPeriodo( String regPatron,
		  Date fechaInicio,Date fechaFin, Delegacion delegacion,
		  Subdelegacion subdelegacion) throws RiesgosTrabajoException;

	TramiteEscritoDesacuerdo getEscritoSimple(String folioRecepcion) throws RiesgosTrabajoException;

	List<TramiteEscritoDesacuerdo> findEscritoDesacuerdoRegPatGralPeriodo( String regPatron,
		  Date fechaInicio,Date fechaFin, Delegacion delegacion,
		  Subdelegacion subdelegacion, PerfilUsuario perfilUsuario) throws RiesgosTrabajoException;

	byte[] generaReporteEscritoDesacuerdo(
			List<TramiteEscritoDesacuerdo> escritoDesacuerdos) throws RiesgosTrabajoException;

	TramiteEscritoDesacuerdo getTramoDuplicado(String folioImpugnado, long anVigencia);

	List<DiasFestivos> findDiasFestivos() throws RiesgosTrabajoException;
}
