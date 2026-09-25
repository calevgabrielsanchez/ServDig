/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: SolicitudServiceEntity.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.solicitud
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.solicitud;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import mx.gob.imss.ctirss.delta.exception.clasificacion.ClasificacionException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.analisis.AnalisisServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.bitacora.BitacoraServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clasificacion.FraccionEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.tramite.TramiteServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.bitacora.BitacoraServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.solicitud.SolicitudServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.sujetoobligado.SujetoObligadoServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Constantes;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisEnum;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisModel;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusNoCancelacionEnum;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosAnalisisConsulta;
import mx.gob.imss.ctirss.delta.model.clasificacion.GrupoAnalisisCeEnum;
import mx.gob.imss.ctirss.delta.model.clasificacion.SolicitudConcluida;
import mx.gob.imss.ctirss.delta.model.clasificacion.TipoCancelacionEnum;
import mx.gob.imss.ctirss.delta.model.clasificacion.TipoRegistroEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DicEstatusAnalisisCe;
import mx.gob.imss.ctirss.delta.persistence.DitAnalisisCe;
import mx.gob.imss.ctirss.delta.persistence.DitHistEstatusAnalisis;
import mx.gob.imss.ctirss.delta.persistence.DitPatronGeneral;
import mx.gob.imss.ctirss.delta.persistence.DitReintentoRPC;
import mx.gob.imss.ctirss.delta.persistence.DitSubdelPatSujOblig;
import mx.gob.imss.ctirss.delta.persistence.DivSolicitudConcluida;

@Stateless
public class SolicitudServiceEntity extends AbstractServiceEntity implements
		SolicitudServiceEntityLocal {

	@EJB
	private SolicitudServiceUtilityLocal solicitudUtility;

	@EJB
	private AnalisisServiceEntityLocal analisisServiceEntity;

	@EJB
	private BitacoraServiceEntityLocal bitacoraServiceEntity;

	@EJB
	private SolicitudServiceBusinessRemote solicitudBusiness;

	@EJB
	private BitacoraServiceUtilityLocal bitacoraServiceUtility;

	@EJB
	private TramiteServiceEntityLocal tramiteServiceEntity;
	
	@EJB
	private SujetoObligadoServiceUtilityLocal sujetoObligadoUtility;

	@EJB
	private FraccionEntityLocal fraccionEntity;


	@SuppressWarnings("unchecked")
	public DatosSalidaPaginador<SolicitudConcluida> consultarSolicitudesConcluidas(
			DatosEntradaPaginador<FiltrosAnalisisConsulta> parametrosPaginador) {

		DatosSalidaPaginador<SolicitudConcluida> response = new DatosSalidaPaginador<SolicitudConcluida>();
		List<SolicitudConcluida> result = null;

		/* Objeto con los filtros seleccionados en la vista */
		FiltrosAnalisisConsulta filtro = parametrosPaginador.getModelo();

		log.debug("*******************************************************************************************************************");
		log.debug("*******************************************************************************************************************");
		log.debug("FILTRO");
		log.debug(filtro.toString());
		log.debug("*******************************************************************************************************************");
		log.debug("*******************************************************************************************************************");

		Criteria criteria = this.getSession().createCriteria(DivSolicitudConcluida.class);
		criteria.add(Restrictions.isNotNull("regPatron"));
		
		if (filtro.getGrupoTramite() != null) {
			// filtro por grupo de tramite 1-Altas  2-Modificaciones
			if(!filtro.getGrupoTramite().equals("")){
				criteria.add(Restrictions.eq("cveIdGrupoAnalisisCe", new BigDecimal(filtro.getGrupoTramite())));
			}
			// si la peticion viene de MODIFICACIONES, se agrega filtro por tipo de movimiento
			if ("2".equals(filtro.getGrupoTramite())) {
				if (null != filtro.getTipoMovimiento() && !(filtro.getTipoMovimiento().trim().length() < 1)) {
					criteria.add(Restrictions.eq("cveIdTipoTramite", new BigDecimal(filtro.getTipoMovimiento())));
				}
			}
		}

		if (filtro.getRegistroPatronal() != null && !filtro.getRegistroPatronal().equals("")) {
			if(filtro.getRegistroPatronal().length() == 11)
				criteria.add(Restrictions.eq("regPatronCompleto", filtro.getRegistroPatronal()));
			else
				criteria.add(Restrictions.eq("regPatron", filtro.getRegistroPatronal()));
		}

		if (null != filtro.getPeriodoInicio() && null != filtro.getPeriodoFin()) {
			final StringBuilder restriction = new StringBuilder(16);
			restriction
					.append("trunc(FEC_PRESENTACION) between ")
					.append("to_date('")
					.append(Constantes.FORMATO_FECHA_YYYY_MM_DD.format(filtro.getPeriodoInicio()))
					.append("', 'yyyy-mm-dd') and ")
					.append("to_date('")
					.append(Constantes.FORMATO_FECHA_YYYY_MM_DD.format(filtro.getPeriodoFin())).append("', 'yyyy-mm-dd')");
			criteria.add(Restrictions.sqlRestriction(restriction.toString()));
		}

		if (filtro.getTipoPersona() != null && !filtro.getTipoPersona().equals("-1") && !filtro.getTipoPersona().equals("")) {
			criteria.add(Restrictions.eq("cveIdTipoPersona", new BigDecimal(filtro.getTipoPersona())));
		}

		log.info("filtro.getTipoRegistro() ... " + filtro.getTipoRegistro());
		log.info("TipoRegistroEnum.ARP.getClave() ... " + TipoRegistroEnum.ARP.getClave());
		log.info("TipoRegistroEnum.RPC.getClave() ... " + TipoRegistroEnum.RPC.getClave());
		log.info("TipoRegistroEnum.PSP.getClave() ... " + TipoRegistroEnum.PSP.getClave());
		
		if (filtro.getTipoRegistro() != null && !filtro.getTipoRegistro().equals("")) {
			int auxTipoRegistro = Integer.parseInt(filtro.getTipoRegistro());

			if (Integer.parseInt(TipoRegistroEnum.ARP.getClave()) == auxTipoRegistro) {
				Criterion criteria1 = Restrictions.isNull("indPrestaServicioPersonal");
				Criterion criteria2 = Restrictions.eq("indPrestaServicioPersonal", new BigDecimal(Constantes.IND_NO_ACTIVO.toString()));
				criteria.add(Restrictions.or(criteria1, criteria2));
				Criterion criteria3 = Restrictions.isNull("indRegPatClase");
				Criterion criteria4 = Restrictions.eq("indRegPatClase", new BigDecimal(Constantes.IND_NO_ACTIVO.toString()));
				criteria.add(Restrictions.or(criteria3, criteria4));
			} else if (Integer.parseInt(TipoRegistroEnum.RPC.getClave()) == auxTipoRegistro) {// Registro Patronal por Clase
				criteria.add(Restrictions.eq("indPrestaServicioPersonal", new BigDecimal(Constantes.IND_ACTIVO.toString())));
				criteria.add(Restrictions.eq("indRegPatClase", new BigDecimal(Constantes.IND_ACTIVO.toString())));
			} else if (Integer.parseInt(TipoRegistroEnum.PSP.getClave()) == auxTipoRegistro) {// Prestadores Servicio Personal
				criteria.add(Restrictions.eq("indPrestaServicioPersonal", new BigDecimal(Constantes.IND_ACTIVO.toString())));
				Criterion criteria1 = Restrictions.isNull("indRegPatClase");
				Criterion criteria2 = Restrictions.eq("indRegPatClase", new BigDecimal(Constantes.IND_NO_ACTIVO.toString()));
				criteria.add(Restrictions.or(criteria1, criteria2));
			}
		}

		if (filtro.getEstatus() != null && !filtro.getEstatus().equals("")) {
			if (Integer.valueOf(filtro.getEstatus()) == EstatusAnalisisEnum.PENDIENTE_DE_ANALISIS.getClave()) {
				Criterion status = Restrictions.eq("cveIdEstatusAnalisis", new BigDecimal(filtro.getEstatus()));
				Criterion statusnull = Restrictions.isNull("cveIdEstatusAnalisis");
				LogicalExpression orExp = Restrictions.or(status, statusnull);
				criteria.add(orExp);
			} else {
				criteria.add(Restrictions.eq("cveIdEstatusAnalisis", new BigDecimal(filtro.getEstatus())));
			}
		}

		if (filtro.getDelegacion() != null && !filtro.getDelegacion().equals("-1")
				&& !filtro.getDelegacion().equals("")) {
			criteria.add(Restrictions.eq("cveIdDelegacion", new BigDecimal(filtro.getDelegacion())));

		}
		if (filtro.getSubDelegacion() != null && !filtro.getSubDelegacion().equals("-1")
				&& !filtro.getSubDelegacion().equals("")) {
			criteria.add(Restrictions.eq("cveIdSubdelegacion", new BigDecimal(filtro.getSubDelegacion())));
		}

		criteria.addOrder(Order.desc("fecPresentacion"));
		criteria.addOrder(Order.desc("cveIdSolicitud"));

		/**
		 * Total records, after filtering (i.e. the total number of records
		 * after filtering has been applied - not just the number of records
		 * being returned in this result set)
		 */
		int iTotalDisplayRecords = 0;
		criteria.setProjection(Projections.rowCount());
		iTotalDisplayRecords = ((Long) criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
		criteria.setProjection(null);
		
		List<DivSolicitudConcluida> entities = criteria
				.setFirstResult(parametrosPaginador.getiDisplayStart())
				.setMaxResults(parametrosPaginador.getiDisplayLength()).list();

		/* Convertimos la lista de entities a una lista de modelo */
		try {
			result = solicitudUtility.convertirListOfVsolicitudconcluidasToSolicitudConcluida(entities);
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.setAaData(result);
		response.setiTotalDisplayRecords(iTotalDisplayRecords);
		response.setiTotalRecords(iTotalDisplayRecords);

		return response;
	}

	/**
	 * {@inheritDoc}
	 * @throws ClasificacionException 
	 * @throws PersistenceException 
	 * 
	 * @see SolicitudServiceEntityLocal#cancelarAnalisisPorRegistroPatronal(String,
	 *      int, mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud)
	 */
	@Override
	public void cancelarAnalisisPorRegistroPatronal(final String regPatronal, final int estadoCancelacion,
			final mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud) throws PersistenceException, ClasificacionException {
		
		//Obtenemos el tipo de tramite que se ejecuta
//		TramiteSujetoObligado tso = (TramiteSujetoObligado)solicitud.getTramites().get(0);
//		Integer tipoTramite = tso.getTipoTramite().getIdTipoTramite();
		
		//Modificacion para obtener los tramites para consider las solicitudes de alta patronal que tienen mas de un tramite 5080703 / WO1939337
		Tramite tram = sujetoObligadoUtility.obtenerTramite(solicitud.getTramites(), solicitud.getTipoSolicitud().getIdTipoSolicitud());
		Integer tipoTramite = tram.getTipoTramite().getIdTipoTramite();
		
		log.debug("::: En cancelarAnalisisPorRegistroPatronal, regPatronal: " 
				+ regPatronal + ", por tipo de tramite: " + tipoTramite);

		if( (tipoTramite.equals(TipoTramiteEnum.ACTIVIDAD_ECONOMICA.getCodigo()) 
				|| tipoTramite.equals(TipoTramiteEnum.DISPOSICION_DE_LEY.getCodigo()) 
				|| tipoTramite.equals(TipoTramiteEnum.INCORPORACION_DE_ACTIVIDADES.getCodigo()) 
				|| tipoTramite.equals(TipoTramiteEnum.COMPRA_DE_ACTIVOS.getCodigo()) 
				|| tipoTramite.equals(TipoTramiteEnum.COMODATO.getCodigo()) 
				|| tipoTramite.equals(TipoTramiteEnum.ENAJENACION.getCodigo()) 
				|| tipoTramite.equals(TipoTramiteEnum.ARRENDAMIENTO.getCodigo()) 
				|| tipoTramite.equals(TipoTramiteEnum.FIDEICOMISO_TRASLATIVO.getCodigo()) 
				|| tipoTramite.equals(TipoTramiteEnum.ESCISION.getCodigo()) 
				|| tipoTramite.equals(TipoTramiteEnum.REANUDACION_DE_ACTIVIDADES.getCodigo()) 
				|| tipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL.getCodigo()) 
				|| tipoTramite.equals(TipoTramiteEnum.FUSION.getCodigo()) 
				|| tipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL_SUBCONTRATACION.getCodigo()) 
				|| tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo()) 
			    || tipoTramite.equals(TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO.getCodigo())
			) && estadoCancelacion == EstatusAnalisisEnum.CANCELADO_POR_NUEVO_TRAMITE_DE_GCE.getClave()					
		){
			log.debug("::: Tramite de cambio de clasificacion en el SRT, llamado para pasar a improcedente tramites anteriores y agregar analisis");
			generaAnalisisMOVPAT(regPatronal, solicitud);
		}else {
			AnalisisClasificacionEmpresas model = new AnalisisClasificacionEmpresas();
			Solicitud sol = new Solicitud();
			List<DivSolicitudConcluida> vista = null;
			switch (regPatronal.trim().length()) {
			case 8:
				vista = consultaSolicitudesPorRP(regPatronal);
				log.debug("La longitud del RP es de 8");
				break;
			case 10:
				vista = consultaSolicitudesPorRP(regPatronal.substring(0, 8),
						regPatronal.substring(8));
				log.debug("La longitud del RP es de 10");
				break;
			case 11:
				vista = consultaSolicitudesPorRP(regPatronal.substring(0, 8),
						regPatronal.substring(8, 10), regPatronal.substring(10));
				log.debug("La longitud del RP es de 11");
				break;
			}
			
			DitPatronGeneral patronGeneral = consultaPatronGeneral(regPatronal);
			DitSubdelPatSujOblig subdelegacionRegistroPatronal = consultarSubdelegacionDeRegistroPatronal(patronGeneral
					.getDitPatronSujetoObligado().getCveIdPatronSujetoObligado());
			
			sol.setId(solicitud.getSolicitudId());
			model.setSolicitud(sol);
			model.setClasificacionAnterior(solicitud.getSujetoObligado().getClasificacion());
			model.setClasificacionActual(consultaClasificacionActualPorRP(regPatronal));
			model.setClaveUsuarioAsignado(null);
			model.setCveIdSubdelegacion(subdelegacionRegistroPatronal.getDicSubdelegacion().getCveIdSubdelegacion());

			Tramite tramite = sujetoObligadoUtility.obtenerTramite(solicitud.getTramites(), 
					solicitud.getTipoSolicitud().getIdTipoSolicitud());
			model.setCveIdGrupoAnalisisCe(tramiteServiceEntity.consultaGrupoAnalisisPorTipoTramite(tramite.getTipoTramite().getIdTipoTramite().longValue()));

			if (vista == null || vista.isEmpty()) {
				analisisServiceEntity.agregaAnalisis(model);
			} else {
				excluyeSolicitud: for (DivSolicitudConcluida sc : vista) {
					log.error("Solicitud encontrada: "+sc.getCveIdSolicitud());
					log.error("Solicitud enviada: "+solicitud.getSolicitudId());
					if (solicitud.getSolicitudId() == sc.getCveIdSolicitud().longValue()) {
						if (estadoCancelacion == EstatusAnalisisEnum.CANCELADO_POR_NUEVO_TRAMITE_DE_GCE.getClave()) {
							analisisServiceEntity.agregaAnalisis(model);
							continue excluyeSolicitud;
						}
					}
					if (sc.getCveIdAnalisis() == null) {
						cambiaEstatusAnalisis(sc, analisisServiceEntity.generaAnalisisCancelacion(sc.getCveIdSolicitud().longValue(),
											  tramiteServiceEntity.consultaGrupoAnalisisPorTipoTramite(sc.getCveIdTipoTramite().longValue())),
											  estadoCancelacion, false);
						log.debug("Genero nuevo analisis");
					} else {
						cambiaEstatusAnalisis(sc, sc.getCveIdAnalisis().longValue(), estadoCancelacion, true);
						log.debug("Ya cuenta con analisis");
					}
				}
			}
			
		}
		
	}	
		
	////Crear id Analisis e Historico
	@Override
	public void crearAnalisisPorRegistroPatronalDictamen(final String regPatronal,
			final mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud) throws PersistenceException, ClasificacionException {
		AnalisisClasificacionEmpresas model = new AnalisisClasificacionEmpresas();
		AnalisisClasificacionEmpresas clasEmp = null;
		Solicitud sol = new Solicitud();
		
		//Verificar si se busca primero si ya existe la solicitud en DIT_ANALISIS
		try {
			log.debug("::: Verificando si la solicitud " + solicitud.getSolicitudId()  + " ya tiene analisis");
			clasEmp = analisisServiceEntity.consultaDetalleAnalisis(new BigDecimal(solicitud.getSolicitudId()));
		} catch (Exception e) {
			log.debug("Ocurrio un error al buscar el analisis con solitud " + solicitud.getSolicitudId());
			e.printStackTrace();
			throw new ClasificacionException(e.getMessage(), null);
		}
		
		if(clasEmp == null) {
			DitPatronGeneral patronGeneral = consultaPatronGeneral(regPatronal);
			DitSubdelPatSujOblig subdelegacionRegistroPatronal = consultarSubdelegacionDeRegistroPatronal(patronGeneral
					.getDitPatronSujetoObligado().getCveIdPatronSujetoObligado());
			
			sol.setId(solicitud.getSolicitudId());
			model.setSolicitud(sol);
			model.setClasificacionAnterior(solicitud.getSujetoObligado().getClasificacion());
			model.setClasificacionActual(consultaClasificacionActualPorRP(regPatronal));
			model.setClaveUsuarioAsignado(null);
			model.setCveIdSubdelegacion(subdelegacionRegistroPatronal.getDicSubdelegacion().getCveIdSubdelegacion());

			Tramite tramite = sujetoObligadoUtility.obtenerTramite(solicitud.getTramites(), 
					solicitud.getTipoSolicitud().getIdTipoSolicitud());
			model.setCveIdGrupoAnalisisCe(tramiteServiceEntity.consultaGrupoAnalisisPorTipoTramite(tramite.getTipoTramite().getIdTipoTramite().longValue()));

			// Se crea registro de analisis para el tramite de Dictamen
			analisisServiceEntity.agregaAnalisis(model);
		}else {
			log.debug("::: La solicitud " + solicitud.getSolicitudId() + " ya tiene registro de analisis");		
		}

	}
	
	
	private DitSubdelPatSujOblig consultarSubdelegacionDeRegistroPatronal(Long cveIdRegistroPatronal){
		DitSubdelPatSujOblig subdelegacion = null;
		subdelegacion = this.em.find(DitSubdelPatSujOblig.class, cveIdRegistroPatronal);		
		return subdelegacion;
	}
	
	/**
	 * Consulta de la vista DivSolicitudConcluida por RP
	 * 
	 * @param regPatronal
	 * @return
	 */
	private final List<DivSolicitudConcluida> consultaSolicitudesPorRP(
			final String... regPatronal) {
		String sql = " from DivSolicitudConcluida sc  where ";
		switch (regPatronal.length) {
		case 3:
			sql += " sc.digVer = :digVer and ";
		case 2:
			sql += " sc.numModalidad = :numModalidad and ";
		case 1:
			sql += " sc.regPatron = :regPatron ";
			break;
		}
		sql += " and sc.cveIdGrupoAnalisisCe in " + "("
				+ GrupoAnalisisCeEnum.INSCRIPCION_INICIAL.getClave() + ", "
				+ GrupoAnalisisCeEnum.MODIFICACION_PATRONAL.getClave() + ") ";

		Query query = em.createQuery(sql);
		log.debug("Se ha armado el Query");
		switch (regPatronal.length) {
		case 3:
			query.setParameter("digVer", regPatronal[2]);
		case 2:
			query.setParameter("numModalidad", regPatronal[1]);
		case 1:
			query.setParameter("regPatron", regPatronal[0]);
			break;
		}
		log.debug("Ya fueron enviados los parametros");
		return (List<DivSolicitudConcluida>) query.getResultList();
	}

	/**
	 * Cancela los Analisis relacionados a partir de un Registro Patronal
	 * seleccionado anteriormente
	 * 
	 * @param divSolicitudConcluida
	 * @param cveIdAnalisis
	 * @param estadoCancelacion
	 * @param consultaHistorico
	 * @throws ClasificacionException 
	 * @throws PersistenceException 
	 */
	private final void cambiaEstatusAnalisis(
			final DivSolicitudConcluida divSolicitudConcluida,
			final Long cveIdAnalisis, final int estadoCancelacion,
			final boolean consultaHistorico) throws PersistenceException, ClasificacionException {
		DitAnalisisCe ditAnalisisCe = new DitAnalisisCe();
		DicEstatusAnalisisCe dicEstatusAnalisisCe = new DicEstatusAnalisisCe();
		String sql = "from DitAnalisisCe a where a.cveIdAnalisis = :cveIdAnalisis";
		Query query = em.createQuery(sql);
		query.setParameter("cveIdAnalisis", cveIdAnalisis);

		ditAnalisisCe = (DitAnalisisCe) query.getSingleResult();
		if (null != ditAnalisisCe.getDicEstatusAnalisisCe()) {
			boolean respuesta = esAnalisisCancelable(ditAnalisisCe
					.getDicEstatusAnalisisCe().getCveIdEstatusAnalisis());
			if (respuesta) {
				dicEstatusAnalisisCe.setCveIdEstatusAnalisis(estadoCancelacion);
				ditAnalisisCe.setIndActivo(false);
				ditAnalisisCe.setDicEstatusAnalisisCe(dicEstatusAnalisisCe);
				enviaABitacora(divSolicitudConcluida, cveIdAnalisis,
						estadoCancelacion, consultaHistorico);
			} else {
				ditAnalisisCe.setIndActivo(false);
			}
			em.merge(ditAnalisisCe);
		}
	}

	/**
	 * Envia Registro de Cancelacion de Analisis a Bitacora
	 * 
	 * @param divSolicitudConcluida
	 * @throws ClasificacionException 
	 * @throws PersistenceException 
	 */
	private final void enviaABitacora(
			final DivSolicitudConcluida divSolicitudConcluida,
			final Long cveIdAnalisis, final int estadoCancelacion,
			final boolean consultaHistorico) throws PersistenceException, ClasificacionException {
		EstatusAnalisisModel estatusAnalisis = new EstatusAnalisisModel();
		AnalisisClasificacionEmpresas clasifAnt = new AnalisisClasificacionEmpresas();
		AnalisisClasificacionEmpresas clasifAct = new AnalisisClasificacionEmpresas();
		mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud = new mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud();
		TramiteSujetoObligado tramite = new TramiteSujetoObligado();

		if (consultaHistorico) {
			estatusAnalisis = obtenerUltimoRegistroHistorico(cveIdAnalisis);
		} else {
			clasifAct
					.setClasificacionActual(consultaClasificacionActualPorRP(divSolicitudConcluida.getRegPatron()));
			solicitud = solicitudBusiness
					.consultarSolicitudPorId(divSolicitudConcluida
							.getCveIdSolicitud().longValue());
			if (solicitud != null && solicitud.getTramites() != null && !solicitud.getTramites().isEmpty()) {
				tramite = sujetoObligadoUtility.obtenerTramiteSujetoObligado(solicitud.getTramites(), 
						solicitud.getTipoSolicitud().getIdTipoSolicitud());
				clasifAnt.setClasificacionAnterior(tramite.getSujetoObligado().getClasificacion());
			}
			if (clasifAnt != null && clasifAnt.getClasificacionAnterior() != null) {
				estatusAnalisis.setFraccionAnterior(clasifAnt
						.getClasificacionAnterior().getFraccion());
			}
			if (clasifAct != null) {
				estatusAnalisis.setFraccionActual(clasifAct.getClasificacionActual().getFraccion());
			}
		}

		estatusAnalisis.setCveIdEstatus((long) estadoCancelacion);
		estatusAnalisis.setCveIdAnalisis(cveIdAnalisis);
		estatusAnalisis.setComentario(obtenerMensajeCancelacion(estadoCancelacion));
		estatusAnalisis.setCveIdDelegacion(divSolicitudConcluida.getCveIdDelegacion().longValue());
		estatusAnalisis.setCveIdSubdelegacion(divSolicitudConcluida.getCveIdSubdelegacion().longValue());
		bitacoraServiceEntity.guardaBitacora(estatusAnalisis);
		log.debug("Se envio a Bitacora satisfactoriamente");
	}

	/**
	 * Consulta Clasificacion Actual por RP
	 * 
	 * @param regPatron
	 * @return
	 */
	private final Clasificacion consultaClasificacionActualPorRP(
			final String regPatron) {
		DitPatronGeneral ditPatronGeneral = consultaPatronGeneral(regPatron);
		if (ditPatronGeneral != null) {
			return fraccionEntity.obtenerClasificacionActual(ditPatronGeneral
							.getDitPatronSujetoObligado().getCveIdPatronSujetoObligado());
		}
		return null;
	}

	private final DitPatronGeneral consultaPatronGeneral(final String regPatron) {
		DitPatronGeneral ditPatronGeneral = null;
		String sql = "from DitPatronGeneral pg where pg.regPatron=:regPatron";
		Query query = em.createQuery(sql);

		switch (regPatron.length()) {
		case 8:
			query.setParameter("regPatron", regPatron);
			break;
		case 10:
			query.setParameter("regPatron", regPatron.substring(0, 8));
			break;
		case 11:
			query.setParameter("regPatron", regPatron.substring(0, 8));
			break;
		}

		if (query.getResultList() != null && query.getResultList().size() > 0) {
			ditPatronGeneral = (DitPatronGeneral) query.getResultList().get(0);
		}
		return ditPatronGeneral;
	}

	/**
	 * Consulta del Historico el ultimo Registro encontrado
	 * 
	 * @param cveIdAnalisis
	 * @return
	 */
	public EstatusAnalisisModel obtenerUltimoRegistroHistorico(
			final Long cveIdAnalisis) {
		List<DitHistEstatusAnalisis> ditHistEstatusAnalisisList = new ArrayList<DitHistEstatusAnalisis>();
		EstatusAnalisisModel estatusAnalisis = new EstatusAnalisisModel();
		try {
			String sql = "from DitHistEstatusAnalisis h where h.cveHistEstatusAnalisis=(select max(h2.cveHistEstatusAnalisis) "
					+ "from DitHistEstatusAnalisis h2 where h2.ditAnalisisCe.cveIdAnalisis=:cveIdAnalisis)";
			Query query = em.createQuery(sql);
			query.setParameter("cveIdAnalisis", cveIdAnalisis);
			ditHistEstatusAnalisisList = (List<DitHistEstatusAnalisis>) query
					.getResultList();
			if (!ditHistEstatusAnalisisList.isEmpty()) {
				estatusAnalisis = bitacoraServiceUtility
						.convertirEntityToModel(ditHistEstatusAnalisisList.get(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return estatusAnalisis;
		}
		return estatusAnalisis;
	}

	/**
	 * Verifica si la solicitud tiene varias clasificaciones
	 * 
	 * @param cveIdSolicitud
	 * @return boolean
	 */
	public boolean consultaReintentoRPC(Long cveIdSolicitud) {
		boolean bReintento = false;
		String sql = "from DitReintentoRPC rrpc where rrpc.ditSolicitud.cveIdSolicitud = :cveIdSolicitud";
		Query query = em.createQuery(sql);
		query.setParameter("cveIdSolicitud", cveIdSolicitud);
		List<DitReintentoRPC> lista = query.getResultList();
		if (lista.size() > 0) {
			if (lista.get(0).getIndReintentoRpc() == 1) {
				bReintento = true;
			}
		}
		return bReintento;
	}

	/**
	 * Determina si el analisis puede ser cancelado
	 * 
	 * @param estadoAnalisis
	 * @return true -> Si el analisis puede ser cancelado false -> Si el
	 *         analisis no puede ser cancelado
	 */
	private boolean esAnalisisCancelable(final long estadoAnalisis) {
		boolean respuesta = Boolean.FALSE;
		final EstatusNoCancelacionEnum[] estatusNoCancelacionEnums = EstatusNoCancelacionEnum.values();
		for (final EstatusNoCancelacionEnum estatusNoCancelacionEnum : estatusNoCancelacionEnums) {
			if (estadoAnalisis == estatusNoCancelacionEnum.getClave()) {
				respuesta = Boolean.FALSE;
				break;
			} else {
				respuesta = Boolean.TRUE;
			}
		}
		return respuesta;
	}

	/**
	 * Obtiene el mensaje de cancelacion que le corresponde al estado de
	 * cancelacon enviado como parametro
	 * 
	 * @param estadoCancelacion
	 * @return mensaje de cancelacion
	 */
	private String obtenerMensajeCancelacion(final int estadoCancelacion) {
		String mensaje = null;
		final TipoCancelacionEnum[] tipoCancelacionEnums = TipoCancelacionEnum.values();
		for (final TipoCancelacionEnum tipoCancelacionEnum : tipoCancelacionEnums) {
			if (estadoCancelacion == tipoCancelacionEnum.getClave()) {
				mensaje = tipoCancelacionEnum.getDescripcion();
				break;
			}
		}
		return mensaje;
	}

	/**
	 * Obtiene Registro Patronal Completo,
	 * el orden a enviar es: RP, Modalidad y Digito Verificador
	 */
	@Override
	public String obtenerRegistroPatronalCompleto(String... regPatron){
		String sql = " from DivSolicitudConcluida sc  where ";
		DivSolicitudConcluida divSolicitudConcluida=new DivSolicitudConcluida();
		switch (regPatron.length){
			case 2: sql += " sc.numModalidad = :numModalidad and ";
			case 1: sql += " sc.regPatron = :regPatron "; break;
		}
		sql += " and sc.cveIdGrupoAnalisisCe in " + "("
				+ GrupoAnalisisCeEnum.INSCRIPCION_INICIAL.getClave() + ", "
				+ GrupoAnalisisCeEnum.MODIFICACION_PATRONAL.getClave() + ") ";

		Query query = em.createQuery(sql);
		switch (regPatron.length){
			case 10: query.setParameter("numModalidad", regPatron[1]);
			case 8: query.setParameter("regPatron", regPatron[0]); break;
		}
		divSolicitudConcluida=(DivSolicitudConcluida)query.getResultList().get(0);
		return (divSolicitudConcluida.getRegPatron()
				+ divSolicitudConcluida.getNumModalidad()
				+ divSolicitudConcluida.getDigVer());
	}
	
	private void generaAnalisisMOVPAT(String nrp,
			mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud) {
				
        boolean agregarAnalisis = false;
        DitSubdelPatSujOblig subdelegacionRegistroPatronal = null;
        Tramite tramite = null;
        TramiteSujetoObligado tso = new TramiteSujetoObligado();
        AnalisisClasificacionEmpresas clasEmp = null;

        try {
        	
    		//Verificar si se busca primero si ya existe la solicitud en DIT_ANALISIS
        	log.debug("::: Verificando si la solicitud " + solicitud.getSolicitudId()  + " ya tiene analisis");
    		clasEmp = analisisServiceEntity.consultaDetalleAnalisis(new BigDecimal(solicitud.getSolicitudId()));
    		
    		if(clasEmp == null) {    		
                log.debug("::: Generando analisis nrp: " + nrp + ", solicitud: " + solicitud.getSolicitudId());
                Solicitud sol = new Solicitud();
                AnalisisClasificacionEmpresas model = new AnalisisClasificacionEmpresas();    
                DitPatronGeneral patronGeneral = consultaPatronGeneral(nrp);
                subdelegacionRegistroPatronal = consultarSubdelegacionDeRegistroPatronal(patronGeneral
                        .getDitPatronSujetoObligado().getCveIdPatronSujetoObligado());

                tramite = obtenerTramite(solicitud.getTramites());

                model.setCveIdGrupoAnalisisCe(tramiteServiceEntity.consultaGrupoAnalisisPorTipoTramite(tramite.getTipoTramite().getIdTipoTramite().longValue()));

                sol.setId(solicitud.getSolicitudId());
                model.setSolicitud(sol);
                
                model.setClasificacionAnterior(solicitud.getSujetoObligado().getClasificacion());
                model.setClasificacionActual(consultaClasificacionActualPorRP(nrp));

                tso = (TramiteSujetoObligado) obtenerTramite(solicitud.getTramites());
                
                // se imprime lo que viene en PrimaSRTActual ya que podria darse el caso de que la prima anterior fue modificada
                if(solicitud.getSujetoObligado().getClasificacion().getPrimaSRTActual() != null) {
                    model.getClasificacionAnterior().getFraccion().setPrimaSRT(solicitud.getSujetoObligado().getClasificacion().getPrimaSRTActual());
                }
                
                //modificacion para guardar la prima correcta en la clasificacion actual
                //cuando el patron no modifico la prima sugerida
                if(tso.getSujetoObligado().getClasificacion().getIndPrimaSugerida() == null ||
                		tso.getSujetoObligado().getClasificacion().getIndPrimaSugerida().equals("0")) {
                	log.debug("::: Solicitud " + solicitud.getSolicitudId()  + " NO viene con cambio en la prima sugerida");
                	Integer tipoTramite = tso.getTipoTramite().getIdTipoTramite();
        			if(!(tipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL.getCodigo())
        					|| tipoTramite.equals(TipoTramiteEnum.FUSION.getCodigo())
        					|| tipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL_SUBCONTRATACION.getCodigo())
        					|| tipoTramite.equals(TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO.getCodigo())
        				) && tso.getSujetoObligado().getClasificacion().getPrimaSRTActual() != null
        			) {
        				model.getClasificacionActual().getFraccion().setPrimaSRT(tso.getSujetoObligado().getClasificacion().getPrimaSRTActual()); 
        			}else if(tso.getSujetoObligado().getClasificacion().getPrimaSRTFusionSust() != null){
        				model.getClasificacionActual().getFraccion().setPrimaSRT(tso.getSujetoObligado().getClasificacion().getPrimaSRTFusionSust());
        			}            	
                }

                // si el patron modifico la prima en el tramite 
                if(tso.getSujetoObligado().getClasificacion().getIndPrimaSugerida() != null &&
                		tso.getSujetoObligado().getClasificacion().getIndPrimaSugerida().equals("1")) {
                	log.debug("::: Solicitud " + solicitud.getSolicitudId()  + " SI tiene con cambio en la prima sugerida");
                	Integer tipoTramite = tso.getTipoTramite().getIdTipoTramite();
        			if(!(tipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL.getCodigo())
        					|| tipoTramite.equals(TipoTramiteEnum.FUSION.getCodigo())
        					|| tipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL_SUBCONTRATACION.getCodigo())
        					|| tipoTramite.equals(TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO.getCodigo())
        				)
        			) {
        				model.getClasificacionActual().getFraccion().setPrimaSRT(tso.getSujetoObligado().getClasificacion().getPrimaSRTSugerida()); 
        			}else {
        				model.getClasificacionActual().getFraccion().setPrimaSRT(tso.getSujetoObligado().getClasificacion().getPrimaSRTFusionSust());
        			}            	
                }

                model.setClaveUsuarioAsignado(null);
                model.setCveIdSubdelegacion(subdelegacionRegistroPatronal.getDicSubdelegacion().getCveIdSubdelegacion());

                analisisServiceEntity.agregaAnalisis(model);
                agregarAnalisis = true;
                log.debug(":::Analisis agregado rp: " + nrp + ", solicitud: " + solicitud.getSolicitudId());
    			
    		}else {
    			log.debug("::: La solicitud " + solicitud.getSolicitudId() + " ya tiene registro de analisis");		
    		}
            
        }catch(Exception e) {
            log.debug("::: Error en generaAnalisisMOVPAT: " + e.getMessage());
            e.printStackTrace();
            agregarAnalisis = false;
        }

        if(agregarAnalisis) {
            actualizaImprocedenteAnalisisMOVPAT(nrp, new BigDecimal(solicitud.getSolicitudId()),
                    subdelegacionRegistroPatronal, tramite.getTipoTramite().getIdTipoTramite().longValue());
        }
		
	}	

	private void actualizaImprocedenteAnalisisMOVPAT(String nrp, BigDecimal idSolAct,
			DitSubdelPatSujOblig subdelegacionRegistroPatronal, long tipoTramite) {
		try {
			log.debug("::: Obteniendo solicitudes para el patron: "+nrp+", solicitud actual " + idSolAct.toString());
			if(nrp.length() > 8) {
				nrp = nrp.substring(0, 8);
			}
			String query = "SELECT DISTINCT sol.CVE_ID_SOLICITUD, tr.CVE_ID_TRAMITE, tr.CVE_ID_TIPO_TRAMITE, " +
					              "ace.CVE_ID_ANALISIS, ace.CVE_ID_ESTATUS_ANALISIS " +
	                "FROM DIT_SOLICITUD sol " +
	                "JOIN DIT_TRAMITE tr ON sol.CVE_ID_SOLICITUD = tr.CVE_ID_SOLICITUD " +
	                "JOIN DIT_TRAMITE_PAT_SUJ_OBLIGADO pso ON tr.CVE_ID_TRAMITE = pso.CVE_ID_TRAMITE " + 
	                "JOIN DIT_PATRON_GENERAL pg ON pso.CVE_ID_PATRON_SUJETO_OBLIGADO = pg.CVE_ID_PATRON_SUJETO_OBLIGADO " +
	                "LEFT JOIN DIT_ANALISIS_CE ace ON sol.CVE_ID_SOLICITUD = ace.CVE_ID_SOLICITUD " +
	                "WHERE pg.REG_PATRON = '"+nrp+"' " +
					"AND tr.CVE_ID_TIPO_TRAMITE IN(" +
						TipoTramiteEnum.ACTIVIDAD_ECONOMICA.getCodigo().toString() 
						+ "," + TipoTramiteEnum.DISPOSICION_DE_LEY.getCodigo().toString() 
						+ "," + TipoTramiteEnum.INCORPORACION_DE_ACTIVIDADES.getCodigo().toString()   
						+ "," + TipoTramiteEnum.COMPRA_DE_ACTIVOS.getCodigo().toString()  
						+ "," + TipoTramiteEnum.COMODATO.getCodigo().toString()  
						+ "," + TipoTramiteEnum.ENAJENACION.getCodigo().toString() 
						+ "," + TipoTramiteEnum.ARRENDAMIENTO.getCodigo().toString() 
						+ "," + TipoTramiteEnum.FIDEICOMISO_TRASLATIVO.getCodigo().toString() 
						+ "," + TipoTramiteEnum.ESCISION.getCodigo().toString() 
						+ "," + TipoTramiteEnum.REANUDACION_DE_ACTIVIDADES.getCodigo().toString()  
						+ "," + TipoTramiteEnum.SUSTITUCION_PATRONAL.getCodigo().toString() 
						+ "," + TipoTramiteEnum.FUSION.getCodigo().toString() 
						+ "," + TipoTramiteEnum.SUSTITUCION_PATRONAL_SUBCONTRATACION.getCodigo().toString()  
						+ "," + TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo().toString() 
						+ "," + TipoTramiteEnum.ALTA_SRT.getCodigo().toString()
						+ "," + TipoTramiteEnum.ALTA_SRT_PM.getCodigo().toString()
						+ "," + TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO.getCodigo().toString()
				  + ") " +
					"AND sol.CVE_ID_TIPO_SOLICITUD IN("+ TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION.getValor().toString() + "," +
					 TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO.getValor().toString() + ", " + TipoSolicitudEnum.ALTA_PATRONAL.getValor().toString() + ") " +
					"AND tr.CVE_ID_ESTADO_TRAMITE = "+EstadoTramiteEnum.CERRADO.getValor().toString()+" " +
					"AND sol.CVE_ID_ESTADO_SOLICITUD = "+EstadoSolicitudEnum.ATENDIDA.getValor().toString()+" ";
			
			SQLQuery querySol = this.getSession().createSQLQuery(query);
			List<Object[]> resultado = (List<Object[]>)querySol.list();
			if(!resultado.isEmpty()) {
				log.debug(":: Recorriendo resultados, se pasaran los tramites encontrados a estado improcedente solicitud actual: "
								+ idSolAct.toString());
				for(Object[] res: resultado) {
					BigDecimal solId = (BigDecimal)res[0];
					if(!solId.toString().equals(idSolAct.toString())) { //omitimos la solicitud actual
						DivSolicitudConcluida sc = new DivSolicitudConcluida();
						sc.setCveIdSolicitud(solId);
						sc.setCveIdDelegacion(new BigDecimal(subdelegacionRegistroPatronal.getDicSubdelegacion().getDicDelegacion().getCveIdDelegacion()));
						sc.setCveIdSubdelegacion(new BigDecimal(subdelegacionRegistroPatronal.getDicSubdelegacion().getCveIdSubdelegacion()));
						sc.setCveIdTipoTramite(new BigDecimal(tipoTramite));
						sc.setRegPatron(nrp);
						if(res[3] != null){ //si tienen analisis 
							BigDecimal idAnalisis = (BigDecimal)res[3];
							BigDecimal estatusB = (BigDecimal) res[4];
							int estatus = estatusB.intValue();
							log.debug("::: Solicitud: "+solId.toString()+", Analisis: " + idAnalisis + ", estatus: " + estatus);
							sc.setCveIdAnalisis(idAnalisis);						
							if(estatus != EstatusAnalisisEnum.RATIFICADO_AUTORIZADO.getClave() &&
							   estatus != EstatusAnalisisEnum.RECTIFICADO_AUTORIZADO.getClave() &&
							   estatus != EstatusAnalisisEnum.RECHAZADO_POR_REGLA_RPC.getClave() &&
							   estatus != EstatusAnalisisEnum.CANCELADO_POR_NUEVO_TRAMITE_DE_GCE.getClave() &&
							   estatus != EstatusAnalisisEnum.CANCELADO_POR_BAJA_PATRONAL.getClave() &&
							   estatus != EstatusAnalisisEnum.POR_REGULARIZAR.getClave() &&
							   estatus != EstatusAnalisisEnum.SOLICITUD_DESECHADA.getClave() 
							){ //pasamos a improcedente
								log.debug("::: Pasamos la solicitud " + solId.toString()  + " a improcedente");
								cambiaEstatusAnalisis(sc, sc.getCveIdAnalisis().longValue(),
										EstatusAnalisisEnum.CANCELADO_POR_NUEVO_TRAMITE_DE_GCE.getClave(), true);	
							}
						}else{ //si el tramite no tiene analisis lo agregamos con estado de improcedente
							log.debug("::: La solicitud " + solId.toString()
									+ " no tiene analisis, se genera nuevo con estatus de improcedente, nrp: " + nrp);
							cambiaEstatusAnalisis(sc, analisisServiceEntity.generaAnalisisCancelacion(sc.getCveIdSolicitud().longValue(),
									  tramiteServiceEntity.consultaGrupoAnalisisPorTipoTramite(sc.getCveIdTipoTramite().longValue())),
									EstatusAnalisisEnum.CANCELADO_POR_NUEVO_TRAMITE_DE_GCE.getClave(), false);
						}
					}
				}
			}else {
				log.debug("::: No se encontraron tramites que se puedan pasar a improcedente, nrp: " + nrp);
			}
			
		}catch(Exception e) {
			log.debug("::: Error en generaAnalisisMOVPAT: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	private Tramite obtenerTramite(List<Tramite> tramites) {
		Tramite tr = null;
		for (Iterator<Tramite> iterator = tramites.iterator(); iterator.hasNext();) {
			Tramite tramite = iterator.next();
			Long idTipoTramite = tramite.getTipoTramite().getIdTipoTramite().longValue();
			if(idTipoTramite.equals(TipoTramiteEnum.ALTA_SRT.getCodigo().longValue())
					|| idTipoTramite.equals(TipoTramiteEnum.ALTA_SRT_PM.getCodigo().longValue())
					|| idTipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo().longValue())	
					|| idTipoTramite.equals(TipoTramiteEnum.ACTIVIDAD_ECONOMICA.getCodigo().longValue())
					|| idTipoTramite.equals(TipoTramiteEnum.DISPOSICION_DE_LEY.getCodigo().longValue())
					|| idTipoTramite.equals(TipoTramiteEnum.INCORPORACION_DE_ACTIVIDADES.getCodigo().longValue())
					|| idTipoTramite.equals(TipoTramiteEnum.COMPRA_DE_ACTIVOS.getCodigo().longValue())
					|| idTipoTramite.equals(TipoTramiteEnum.COMODATO.getCodigo().longValue())
					|| idTipoTramite.equals(TipoTramiteEnum.ENAJENACION.getCodigo().longValue())
					|| idTipoTramite.equals(TipoTramiteEnum.ARRENDAMIENTO.getCodigo().longValue())
					|| idTipoTramite.equals(TipoTramiteEnum.FIDEICOMISO_TRASLATIVO.getCodigo().longValue())
					|| idTipoTramite.equals(TipoTramiteEnum.ESCISION.getCodigo().longValue())
					|| idTipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL.getCodigo().longValue())
					|| idTipoTramite.equals(TipoTramiteEnum.FUSION.getCodigo().longValue())
					|| idTipoTramite.equals(TipoTramiteEnum.REANUDACION_DE_ACTIVIDADES.getCodigo().longValue())
					|| idTipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL_SUBCONTRATACION.getCodigo().longValue())
					|| idTipoTramite.equals(TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO.getCodigo().longValue())
			) {					
				tr = tramite;
				break;
			}					

		}
		return tr;
	}	
	
}

