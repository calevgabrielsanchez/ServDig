package mx.gob.imss.distss.delta.rtt.service.business;


import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.riesgosTrabajo.RiesgosTrabajoException;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.DomicilioEscritoDesacuerdo;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.MotivosDesacuerdo;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.TramiteEscritoDesacuerdo;
import mx.gob.imss.distss.delta.rtt.service.entity.ConsultaEscritoDesacuerdoLocal;
import mx.gob.imss.distss.delta.rtt.service.interfaces.ConsultaEscritoDesacuerdoServiceRemote;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.DiasFestivos;

@Stateless(name = "consultaEscritoDesacuerdoBusiness", mappedName = "consultaEscritoDesacuerdoBusiness")
public class ConsultaEscritoDesacuerdoService implements
		ConsultaEscritoDesacuerdoServiceRemote {

	@EJB
	private ConsultaEscritoDesacuerdoLocal consultaEscritoDesacuerdoLocal;

	@Override
	public TramiteEscritoDesacuerdo findEscritoDesacuerdoFolio(
			String folio) throws RiesgosTrabajoException {
		return consultaEscritoDesacuerdoLocal.findEscritoDesacuerdoFolio(folio);
	}

	@Override
	public List<TramiteEscritoDesacuerdo> findEscritoDesacuerdoRegPatron(
			String regPatron, Delegacion delegacion, Subdelegacion subdelegacion, Date fechaInicio, Date fechaFin)
			throws RiesgosTrabajoException {
		return consultaEscritoDesacuerdoLocal.findEscritoDesacuerdoRegPatronPeriodo( regPatron, fechaInicio, fechaFin, delegacion, subdelegacion);
	}

	@Override
	public List<TramiteEscritoDesacuerdo> findEscritoDesacuerdoPeriodo(
			Date fechaInicio, Date fechaFin, Delegacion delegacion,
			Subdelegacion subdelegacion) throws RiesgosTrabajoException {
		return consultaEscritoDesacuerdoLocal.findEscritoDesacuerdoRegPatronPeriodo( null, fechaInicio, fechaFin, delegacion, subdelegacion);
	}

	@Override
	public List<TramiteEscritoDesacuerdo> findEscritoDesacuerdoRegPatronPeriodo(
			String regPatron, Date fechaInicio, Date fechaFin,
			Delegacion delegacion, Subdelegacion subdelegacion)
			throws RiesgosTrabajoException {
		return consultaEscritoDesacuerdoLocal.findEscritoDesacuerdoRegPatronPeriodo(regPatron, fechaInicio, fechaFin, delegacion, subdelegacion);
	}

	@Override
	public List<TramiteEscritoDesacuerdo> findEscritoDesacuerdoRegPatronGral(
			String regPatron, Delegacion delegacion, Subdelegacion subdelegacion,
			Date fechaInicio, Date fechaFin, PerfilUsuario perfilUsuario)
			throws RiesgosTrabajoException {
		return consultaEscritoDesacuerdoLocal.findEscritoDesacuerdoRegPatGralPeriodo(regPatron, fechaInicio, fechaFin, delegacion, subdelegacion, perfilUsuario);
	}

	@Override
	public byte[] generaReporteEscritoDesacuerdo(
			List<TramiteEscritoDesacuerdo> escritoDesacuerdos)
			throws RiesgosTrabajoException {
		return consultaEscritoDesacuerdoLocal.generaReporteEscritoDesacuerdo(escritoDesacuerdos);
	}

	@Override
	public boolean saveDomEscritoDes(DomicilioEscritoDesacuerdo domEscrDes, int querAct) throws RiesgosTrabajoException {
		return consultaEscritoDesacuerdoLocal.saveDomEscritoDes(domEscrDes, querAct);
	}

	@Override
	public DomicilioEscritoDesacuerdo findDomEscritoDesacuerdo(Long idtramDomEsc) throws RiesgosTrabajoException {
		return consultaEscritoDesacuerdoLocal.findDomEscritoDesacuerdo(idtramDomEsc);
	}

	@Override
	public List<MotivosDesacuerdo> getMotivosDesacuerdoList(int idEscrito) throws RiesgosTrabajoException {
		return consultaEscritoDesacuerdoLocal.getMotivosDesacuerdoList(idEscrito);
	}

	@Override
	public TramiteEscritoDesacuerdo getTramoDuplicado(String folioImpugnado, long anVigencia) throws RiesgosTrabajoException {
		return consultaEscritoDesacuerdoLocal.getTramoDuplicado(folioImpugnado, anVigencia);
	}

	@Override
	public List<MotivosDesacuerdo> getFraccionClaseList(String clase) throws RiesgosTrabajoException {
		return consultaEscritoDesacuerdoLocal.getFraccionClaseList(clase);
	}

	@Override
	public List<DiasFestivos> getDiasFestivos() throws RiesgosTrabajoException {
		return consultaEscritoDesacuerdoLocal.findDiasFestivos();
	}
}
