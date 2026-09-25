/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Leticia Torres
 *  @Proyecto: delta
 *  @Archivo: GCESujetoObligadoServiceBusiness.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.analisis
 *  @Fecha: 09/01/2013
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.analisis;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.delta.exception.clasificacion.ClasificacionException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.GCESujetoObligadoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.GCESujetoObligadoServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.GCESujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.sujetoobligado.SujetoObligadoServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;

@Stateless(name = "gceSujetoObligadoServiceBusiness", mappedName = "gceSujetoObligadoServiceBusiness")
public class GCESujetoObligadoServiceBusiness extends AbstractServiceBusiness
		implements GCESujetoObligadoServiceBusinessRemote {
	
	@EJB
	private SolicitudBusinessRemote solicitudBusiness;
	
//	@EJB
//	private SolicitudServiceBusinessRemote solicitudServiceBusiness;

	@EJB
	private GCESujetoObligadoServiceEntityLocal gceSujetoObligadoServiceEntity;
	
	@EJB
	private SujetoObligadoServiceUtilityLocal sujetoObligadoUtility;

	/**
	 * {@inheritDoc}
	 * @throws GCESujetoObligadoException
	 * @throws PersistenceException 
	 * @throws ClasificacionException 
	 * @see GCESujetoObligadoServiceBusinessRemote#validarEstadoRegistroPatronal(Solicitud)
	 */
	@Override
	public void validarEstadoRegistroPatronal(final Solicitud solicitudParam)
			throws GCESujetoObligadoException, PersistenceException, ClasificacionException {
		validarParametro(solicitudParam);
		validarParametro(solicitudParam.getSolicitudId());
		
		final Solicitud solicitud = obtenerSolicitud(solicitudParam);
		
		SujetoObligado sujetoObligado = sujetoObligadoUtility.obtenerTramiteSujetoObligado(solicitud.getTramites(),
				solicitud.getTipoSolicitud().getIdTipoSolicitud()).getSujetoObligado();
		
		if(sujetoObligado == null || sujetoObligado.getCveIdSujetoObligado() == null){
			throw new ClasificacionException(
					"El ID de Sujeto Obligado es NULL", 604);
		}
		
		solicitud.setSujetoObligado(sujetoObligado);
		
		final DitPatronSujetoObligado ditPatronSujetoObligado = gceSujetoObligadoServiceEntity.obtenerPatronSujetoObligado(sujetoObligado.getCveIdSujetoObligado());
		
		if (null == ditPatronSujetoObligado) {
			throw new GCESujetoObligadoException(
					"No existe el patrón sujeto obligado", 603);
		} else if (null != ditPatronSujetoObligado.getFecRegistroBaja()) {
//			Se cancela el cambio de estatus 
//			solicitudServiceBusiness.cancelarAnalisisPorRegistroPatronal(
//					sujetoObligado.getNumeroRegistroPatronal(),
//					EstatusAnalisisEnum.CANCELADO_POR_BAJA_PATRONAL.getClave(),
//					solicitud);
			throw new ClasificacionException(
					"El registro patronal se encuentra dado de baja", 604);
		}
	}
	
	/**
	 * Obtiene la información de la solicitud enviada como parámetro; en caso de
	 * que la solicitudno exista lanza una excepción de negocio
	 * 
	 * @param solicitudParam
	 * @return solcitud
	 * @throws GCESujetoObligadoException
	 */
	private Solicitud obtenerSolicitud(final Solicitud solicitudParam)
			throws GCESujetoObligadoException {
		Solicitud solicitud = null;
		try {
			solicitud = solicitudBusiness.consultar(solicitudParam);
			if (null == solicitud) {
				throw new GCESujetoObligadoException("No existe la solicitud",
						602);
			}
		} catch (final SolicitudNoEncontradaException e) {
			log.error(e.getMessage(), e);
			throw new GCESujetoObligadoException("No existe la solicitud", 602);
		}
		return solicitud;
	}
	
	/**
	 * Determina que el parámetro de entrada sea válido
	 * 
	 * @param parametro
	 * @throws GCESujetoObligadoException
	 */
	private void validarParametro(final Object parametro)
			throws GCESujetoObligadoException {
		if (null == parametro) {
			throw new GCESujetoObligadoException(
					"Parámetros de entrada inválidos", 601);
		} else if (parametro instanceof String) {
			final String parametroStr = (String) parametro;
			if (parametroStr.trim().isEmpty()) {
				throw new GCESujetoObligadoException(
						"Parámetros de entrada inválidos", 601);
			}
		}
	}

}
