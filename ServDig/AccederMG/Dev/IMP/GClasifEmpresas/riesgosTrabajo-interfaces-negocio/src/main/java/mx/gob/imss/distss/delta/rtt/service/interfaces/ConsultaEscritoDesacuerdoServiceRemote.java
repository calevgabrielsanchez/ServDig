package mx.gob.imss.distss.delta.rtt.service.interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.riesgosTrabajo.RiesgosTrabajoException;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.DomicilioEscritoDesacuerdo;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.MotivosDesacuerdo;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.TramiteEscritoDesacuerdo;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.DiasFestivos;


@Remote
public interface ConsultaEscritoDesacuerdoServiceRemote {

	/**
	 * Consulta de Escritos de Desacuerdo Por Folio
	 * @param folio
	 * @return
	 * @throws RiesgosTrabajoException
	 */
	public TramiteEscritoDesacuerdo findEscritoDesacuerdoFolio(
			String folio) throws RiesgosTrabajoException;

	/**
	 * Consulta de Escrito de Desacuerdo Por patron delegaci�n y subdelegacion
	 * @param regPatron
	 * @param delegacion
	 * @param subdelegacion
	 * @return
	 * @throws RiesgosTrabajoException
	 */
	public List<TramiteEscritoDesacuerdo> findEscritoDesacuerdoRegPatron(
			String regPatron, Delegacion delegacion, Subdelegacion subdelegacion, Date fechaInicio, Date fechaFin)
			throws RiesgosTrabajoException;

	/**
	 * Consulta de Escrito de Desacuerdo Por periodo, delegaci�n y subdelegacion
	 * @param fechaInicio
	 * @param fechaFin
	 * @param delegacion
	 * @param subdelegacion
	 * @return
	 * @throws RiesgosTrabajoException
	 */
	public List<TramiteEscritoDesacuerdo> findEscritoDesacuerdoPeriodo(
			Date fechaInicio, Date fechaFin, Delegacion delegacion,
			Subdelegacion subdelegacion) throws RiesgosTrabajoException;

	/**
	 * @param regPatron
	 * @param fechaInicio
	 * @param fechaFin
	 * @param delegacion
	 * @param subdelegacion
	 * @return
	 * @throws RiesgosTrabajoException
	 */
	public List<TramiteEscritoDesacuerdo> findEscritoDesacuerdoRegPatronPeriodo(
			String regPatron, Date fechaInicio, Date fechaFin, Delegacion delegacion,
			Subdelegacion subdelegacion) throws RiesgosTrabajoException;

	/**
	 * Consulta de Escrito de Desacuerdo por perfil patron delegaci�n y subdelegacion
	 * @param regPatron
	 * @param fechaInicio
	 * @param fechaFin
	 * @param delegacion
	 * @param subdelegacion
	 * @return
	 * @throws RiesgosTrabajoException
	 */

	public List<TramiteEscritoDesacuerdo> findEscritoDesacuerdoRegPatronGral(
			String regPatron, Delegacion delegacion, Subdelegacion subdelegacion, Date fechaInicio, Date fechaFin,
			PerfilUsuario perfilUsuario) throws RiesgosTrabajoException;

	/**
	 * Genera reporte de Escrito de Desacuerdo
	 * @param escritoDesacuerdos
	 * @return
	 * @throws RiesgosTrabajoException
	 */

	public byte[] generaReporteEscritoDesacuerdo(
			List<TramiteEscritoDesacuerdo> escritoDesacuerdos) throws RiesgosTrabajoException;

	/**
	 * Guarda el domicilio del RP
	 * @param domEscrDes
	 * @param querAct
	 * @return
	 * @throws RiesgosTrabajoException
	 */
	public boolean saveDomEscritoDes (DomicilioEscritoDesacuerdo domEscrDes, int querAct) throws RiesgosTrabajoException;

	/**
	 * Busca el domicilio del RP y valida existencia
	 * @param idtramDomEsc
	 * @return
	 * @throws RiesgosTrabajoException
	 */
	public DomicilioEscritoDesacuerdo findDomEscritoDesacuerdo(Long idtramDomEsc) throws RiesgosTrabajoException;

	/**
	 * Busca el listado de motivos para registrar un Escrito
	 * @return
	 * @throws RiesgosTrabajoException
	 */
	List<MotivosDesacuerdo> getMotivosDesacuerdoList(int idEscrito) throws RiesgosTrabajoException;

	/**
	 * Busca el listado de fraccion clase para registrar un Escrito
	 * @return
	 * @throws RiesgosTrabajoException
	 */
	List<MotivosDesacuerdo> getFraccionClaseList(String clase) throws RiesgosTrabajoException;

	/**
	 * Consulta de Escritos de Desacuerdo Por Folio
	 * @param folioImpugnado
	 * @param anVigencia
	 * @return
	 * @throws RiesgosTrabajoException
	 */
	public TramiteEscritoDesacuerdo getTramoDuplicado(String folioImpugnado, long anVigencia) throws RiesgosTrabajoException;

	List<DiasFestivos> getDiasFestivos () throws RiesgosTrabajoException;
}
