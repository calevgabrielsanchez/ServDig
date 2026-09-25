package mx.gob.imss.cit.cda.service.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import mx.gob.imss.cit.cda.service.solicitarinformacion.constants.SolicitarInformacionConstants;
import mx.gob.imss.cit.cda.service.utility.TramiteCDAUtilityLocal;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.DatosLaborales;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.MotivoAclaracion;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicInstitucion;
import mx.gob.imss.ctirss.delta.persistence.DicMotivoAclaracion;
import mx.gob.imss.ctirss.delta.persistence.DicOrigenCapturaNssCda;
import mx.gob.imss.ctirss.delta.persistence.DicTipoNssAclaracion;
import mx.gob.imss.ctirss.delta.persistence.DicTipoNssCorreccion;
import mx.gob.imss.ctirss.delta.persistence.DitBitSeparacionPersonas;
import mx.gob.imss.ctirss.delta.persistence.DitCorreccionDatosAsegurado;
import mx.gob.imss.ctirss.delta.persistence.DitDatosLaborales;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleAclaracion;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleNss;
import mx.gob.imss.ctirss.delta.persistence.DitTipoCertificacionCorreccion;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class CorreccionDatosAseguradoEntity extends AbstractServiceEntity
		implements CorreccionDatosAseguradoLocal {

	@EJB
	TramiteCDAUtilityLocal utility;

	private static Logger logger = LoggerFactory
			.getLogger(CorreccionDatosAseguradoEntity.class);
	private static final String CVE_TRAMITE = "cveIdTramite";

	/**
	 * Metodo para persistir la solicitud
	 */
	@Override
	public TramiteCorreccionCurp almacenarTramiteCDA(Long idTramite, String curp) {
		DitCorreccionDatosAsegurado ditCorreccionDatosAsegurado = obtenerTramiteCorreccion(
				idTramite, curp);
		if (ditCorreccionDatosAsegurado == null) {
			ditCorreccionDatosAsegurado = new DitCorreccionDatosAsegurado();
			DitTramite tramite = new DitTramite();
			tramite.setCveIdTramite(idTramite);
			ditCorreccionDatosAsegurado.setTramite(tramite);
			ditCorreccionDatosAsegurado.setRefCurp(curp);
			ditCorreccionDatosAsegurado.setFecRegistroAlta(new Date());
		}

		ditCorreccionDatosAsegurado.setFecRegistroActualizado(new Date());
		ditCorreccionDatosAsegurado = em.merge(ditCorreccionDatosAsegurado);
		log.debug("---CDA----El ID  de ditCorreccionDatosAsegurado: "
				+ ditCorreccionDatosAsegurado
						.getCveIdCorreccionDatosAsegurado());
		return utility.convertirEntityToModel(ditCorreccionDatosAsegurado);
	}

	/**
	 * Metodo para persistir los datos laborales
	 * 
	 * @param idTramiteCorreccion
	 * @param datosLaborales
	 */
	@Override
	public void almacenaDatosLaborales(Long idTramiteCorreccion,
			DatosLaborales datosLaborales) {
		DitDatosLaborales ddla = new DitDatosLaborales();
		DitCorreccionDatosAsegurado dcda = new DitCorreccionDatosAsegurado();
		dcda.setCveIdCorreccionDatosAsegurado(idTramiteCorreccion);

		ddla.setDitCorreccionDatosAsegurado(dcda);
		ddla.setNombrePatron(datosLaborales.getNombrePatron());
		ddla.setCveEnt(datosLaborales.getEntidadFederativa().getClave());
		ddla.setRefFecInscripcion(datosLaborales.getFechaInscripcion());
		ddla.setRefFecBaja(datosLaborales.getFechaBaja());
		ddla.setRefRegistroPatronal(datosLaborales.getNrp());
		ddla.setDesDomicilio(datosLaborales.getDomicilio());
		ddla.setDesActividad(datosLaborales.getActividad());
		ddla.setFecRegistroAlta(new Date());
		ddla.setFecRegistroActualizado(new Date());

		em.persist(ddla);
	}

	/**
	 * Metodo encargado de almacenar los motivos de aclaracion
	 * 
	 * @param idTramiteCorreccion
	 * @param motivoAclaracion
	 * 
	 */
	@Override
	public void almacenaMotivos(Long idTramiteCorreccion,
			MotivoAclaracion motivoAclaracion) {
		DitDetalleAclaracion dda = new DitDetalleAclaracion();
		DitCorreccionDatosAsegurado dcda = new DitCorreccionDatosAsegurado();
		DicInstitucion di = new DicInstitucion();
		DicMotivoAclaracion dma = new DicMotivoAclaracion();

		di.setCveIdInstitucion(motivoAclaracion.getInstitucion()
				.getIdInstitucion().longValue());
		dma.setCveIdMotivoAclaracion(motivoAclaracion.getIdMotivoAclaracion()
				.longValue());
		dcda.setCveIdCorreccionDatosAsegurado(idTramiteCorreccion);

		dda.setMotivoAclaracion(dma);
		dda.setCorreccionDatosAsegurado(dcda);
		dda.setInstitucion(di);
		dda.setDesDetalle(motivoAclaracion.getDetalleAclaracion());
		dda.setDesMotivoAclaracion(motivoAclaracion.getDescripcionMotivo());
		dda.setFecRegistroAlta(new Date());
		dda.setFecRegistroActualizado(new Date());

		em.persist(dda);

	}

	/**
	 * Metodo para saber si existe una solicitud asociada al CURP que se ingreso
	 */
	@Override
	public Long getIdTramiteActivo(List<String> curps,
			List<Integer> estadosValidos, String origen) {
		StringBuffer sql = new StringBuffer();
		sql.append("select correccion from DitCorreccionDatosAsegurado correccion ");
		sql.append("where correccion.tramite.dicEstadoTramite.cveIdEstadoTramite in (:estadosTramite) and correccion.refCurp in(:curps) ");
		sql.append("and correccion.tramite.dicTipoTramite.cveIdTipoTramite=:cveTipoTramite ");
		if (estadosValidos.contains(EstadoTramiteEnum.BAJA_IMPROCEDENCIA
				.getCodigo()) && origen.equals(SolicitarInformacionConstants.ORIGEN_SOLICITUD_INTERNET)) {
			sql.append("and correccion.tramite.ditSolicitud.fecRegistroAlta > :fechaLimite ");
		}
		sql.append(" order by correccion.fecRegistroAlta desc ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("curps", curps);
		query.setParameter("estadosTramite", estadosValidos);
		query.setParameter("cveTipoTramite",
				TipoTramiteEnum.CORRECCION_DATOS_ASEGURADO.getCodigo());
		if (estadosValidos.contains(EstadoTramiteEnum.BAJA_IMPROCEDENCIA
				.getCodigo()) && origen.equals(SolicitarInformacionConstants.ORIGEN_SOLICITUD_INTERNET)) {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.DATE, -DIAS_MAXIMOS_ESPERA);
			query.setParameter("fechaLimite", c.getTime());
		}
		List<DitCorreccionDatosAsegurado> result = null;
		Long resultFinal = null;
		try {
			result = (List<DitCorreccionDatosAsegurado>) query.getResultList();
			resultFinal = result.size() >= 1 ? obtenerIdFinal(result) : null;
			log.debug("---CDA----El ID  a regresar es: " + resultFinal);
			return resultFinal;
		} catch (NoResultException nre) {
			log.debug("--CDA-- No se pudo encontrar un idTramite asociado a la CURP");
		}
		return resultFinal;
	}

	private DitCorreccionDatosAsegurado obtenerTramiteCorreccion(
			Long idTramite, String curp) {
		StringBuffer sql = new StringBuffer();
		sql.append("select correccion from DitCorreccionDatosAsegurado correccion ");
		sql.append("where correccion.tramite.dicEstadoTramite.cveIdEstadoTramite not in (:estadosTramite)  ");
		if (StringUtils.isNotBlank(curp)) {
			sql.append(" and correccion.refCurp=:curp ");
		}
		sql.append("and correccion.tramite.dicTipoTramite.cveIdTipoTramite=:cveTipoTramite ");
		sql.append("and correccion.tramite.cveIdTramite=:cveIdTramite ");
		sql.append("and correccion.fecRegistroBaja is null ");
		
		Query query = em.createQuery(sql.toString());
		if (StringUtils.isNotBlank(curp)) {
			query.setParameter("curp", curp);
		}
		query.setParameter("estadosTramite",
				Arrays.asList(EstadoTramiteEnum.CERRADO.getCodigo(),EstadoTramiteEnum.CANCELADO.getCodigo(),
						EstadoTramiteEnum.BAJA_IMPROCEDENCIA
						.getCodigo()));
		query.setParameter("cveTipoTramite",
				TipoTramiteEnum.CORRECCION_DATOS_ASEGURADO.getCodigo());
		query.setParameter(CVE_TRAMITE, idTramite);

		return (query.getResultList() != null && query.getResultList().size() > 0) ? (DitCorreccionDatosAsegurado) query
				.getSingleResult() : null;
	}

	public DicEstadoTramite consultarEstadoTramiteById(Long idTramite) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT tramite.dicEstadoTramite FROM DitTramite tramite ");
		sql.append("WHERE tramite.cveIdTramite = :cveIdTramite ");
		Query query = em.createQuery(sql.toString());
		query.setParameter(CVE_TRAMITE, idTramite);

		return (DicEstadoTramite) query.getSingleResult();

	}

	@Override
	public DitDetalleNss bloquearNSS(String nss, Long idCorreccionDatosAseg,
			Long idOrigen, String observaciones) {
		DitDetalleNss detalleNss = new DitDetalleNss();
		DicOrigenCapturaNssCda origen = new DicOrigenCapturaNssCda();
		DitCorreccionDatosAsegurado correccionDatos = new DitCorreccionDatosAsegurado();
		correccionDatos.setCveIdCorreccionDatosAsegurado(idCorreccionDatosAseg);

		detalleNss.setNss(nss);
		detalleNss.setCorreccionDatosAsegurado(correccionDatos);
		detalleNss.setFecRegistroAlta(new Date());
		detalleNss.setFecRegistroActualizado(new Date());
		detalleNss.setObservaciones(observaciones);
		origen.setCveIdOrigenCapturaNssCda(idOrigen);
		detalleNss.setDicOrigenCapturaNssCda(origen);
		em.persist(detalleNss);
		em.flush();
		return detalleNss;
	}

	@Override
	public DitDetalleNss consultarBloqueoNSS(String nss) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT detalle FROM DitDetalleNss detalle ");
		sql.append("WHERE detalle.nss = :nss ");
		sql.append("AND detalle.correccionDatosAsegurado.tramite.dicTipoTramite.cveIdTipoTramite in (:cveTipoTramite) ");
		sql.append("AND detalle.correccionDatosAsegurado.tramite.dicEstadoTramite.cveIdEstadoTramite in (:estadosTramite) ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("nss", nss);
		query.setParameter("cveTipoTramite", Arrays.asList(
				TipoTramiteEnum.CORRECCION_DATOS_ASEGURADO.getCodigo(),
				TipoTramiteEnum.CORRECCION_DE_NOMBRE.getCodigo(),
				TipoTramiteEnum.CORRECCION_DE_DATOS_ESTADISTICOS.getCodigo(),
				TipoTramiteEnum.CANCELADO_POR_DUPLICIDAD.getCodigo(),
				TipoTramiteEnum.CORRESPONDE_A_UN_HOMONIMO.getCodigo(),
				TipoTramiteEnum.NO_EXISTE_EN_CANASE.getCodigo(),
				TipoTramiteEnum.CORRESPONDE_A_OTRO_ASEGURADO.getCodigo()));

		query.setParameter("estadosTramite", Arrays.asList(
				EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE.getCodigo(),
				EstadoTramiteEnum.EN_ESPERA_AUTORIZACION.getCodigo(),
				EstadoTramiteEnum.EN_ESPERA_TRAMITADOR.getCodigo(),
				EstadoTramiteEnum.ERROR_SINDO.getCodigo(),
				EstadoTramiteEnum.ENVIA_CERTIFICACION_SINDO.getCodigo(),
				EstadoTramiteEnum.PROCESADO_SINDO.getCodigo(),
				EstadoTramiteEnum.SIN_RESPONSABLE.getCodigo(),
				EstadoTramiteEnum.ANALISIS_COMPLETADO.getCodigo()));

		DitDetalleNss detalle = null;
		try {
			detalle = (DitDetalleNss) query.getSingleResult();
		} catch (NoResultException e) {
			logger.error(
					"---CDA--- Sin resultados el NSS {} no esta bloqueado", nss);
		} catch (NonUniqueResultException e) {
			logger.error(
					"---CDA--- Sin resultados el NSS {} no esta bloqueado", nss);
		}

		return detalle;
	}

	// TODO insertar metodo guardado solicitar info aqui
	public int insertarResponableAutorizadorCorreccion(Long idTramite,
			int tipoUsr) {

		StringBuffer sql = new StringBuffer();

		sql.append("update DIT_CORRECCION_DATOS_ASEG set IND_TIPO_SOLICITUD_INFO = :cveTipoUsr where CVE_ID_TRAMITE = :cveIdTramite");

		javax.persistence.Query query = em.createNativeQuery(sql.toString());

		query.setParameter(CVE_TRAMITE, idTramite);
		query.setParameter("cveTipoUsr", tipoUsr);

		query.executeUpdate();

		int numSolicitudesActualizadas = query.executeUpdate();
		log.error("Se actualizaron los sig numero de solicitudes: "
				+ numSolicitudesActualizadas);
		return numSolicitudesActualizadas;

	}

	public String obtenerResponsableTramiteCDA(Long idSolicitud) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT solicitud.cveIdUsuario FROM DitSolicitud solicitud ");
		sql.append("WHERE solicitud.cveIdSolicitud = :idSolicitud ");

		Query query = em.createQuery(sql.toString());
		query.setParameter("idSolicitud", idSolicitud);

		String usuarioResponsable = (String) query.getSingleResult();

		return usuarioResponsable;

	}

	public String obtenerResponsableTramiteCDA(String folio) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT solicitud.cveIdUsuario FROM DitSolicitud solicitud ");
		sql.append("WHERE solicitud.refFolio = :folio ");

		Query query = em.createQuery(sql.toString());
		query.setParameter("folio", folio);

		String usuarioResponsable;
		try {
			usuarioResponsable = (String) query.getSingleResult();
		} catch (NoResultException e) {
			logger.error("---CDA--- Sin resultados para folio " + folio, e);
			usuarioResponsable = null;
		} catch (NonUniqueResultException e) {
			logger.error("---CDA--- Sin resultados para folio " + folio, e);
			usuarioResponsable = null;
		}

		return usuarioResponsable;

	}

	public void actualizarSubdelegacionSolicitud(Solicitud sol) {
		StringBuffer sql = new StringBuffer();

		sql.append("update DIT_SOLICITUD set CVE_ID_SUBDELEGACION = :subdelegacion where CVE_ID_SOLICITUD = :idSolicitud");

		javax.persistence.Query query = em.createNativeQuery(sql.toString());

		query.setParameter("subdelegacion", sol.getSubdelegacion().getId());
		query.setParameter("idSolicitud", sol.getSolicitudId());

		int numSolicitudesActualizadas = query.executeUpdate();
		log.debug("Se actualizaron los sig numero de solicitudes: "
				+ numSolicitudesActualizadas);
	}

  private Long obtenerIdFinal_step1(Map<Long, Long> iniciados,  List<DitCorreccionDatosAsegurado> result){
    Long resultFinal = null;
    
    if (iniciados.size() > 1) {
				resultFinal = 0L;
			} else if (iniciados.size() == 1) {
				resultFinal = iniciados.get(1L);
			} else if (iniciados.isEmpty()) {
				resultFinal = result.get(0).getTramite().getCveIdTramite();
			}
    
    
    return resultFinal;
  }
  
	public Long obtenerIdFinal(List<DitCorreccionDatosAsegurado> result) {
		Long resultFinal = null;
		Map<Long, Long> iniciados = new HashMap<Long, Long>();
		log.debug("Total de registros encontrados: " + result.size());
		if (result.size() > 1) {
			for (int i = 0; i < result.size(); i++) {
				if (result.get(i).getTramite().getDicEstadoTramite()
						.getCveIdEstadoTramite() == 1) {
					iniciados.put(result.get(i).getTramite()
							.getDicEstadoTramite().getCveIdEstadoTramite(),
							result.get(i).getTramite().getCveIdTramite());
				}
			}
			
      resultFinal = this.obtenerIdFinal_step1(iniciados,result);
      
		} else if (result.size() == 1) {
			resultFinal = result.get(0).getTramite().getCveIdTramite();
		}
		return resultFinal;
	}

	/**
	 * Actualiza el tipo del tramite con respecto al tipo de regularicazion que
	 * le aplica
	 * 
	 * @param cveIdTramite
	 * @param cvdTipoTramite
	 */
	public int actualizarTipoTramite(Long cveIdTramite, Long cvdTipoTramite,
			Long cveIdEstadoTramite) {

		StringBuilder strUpdate = new StringBuilder("update DIT_TRAMITE ");

		strUpdate.append("set CVE_ID_TIPO_TRAMITE = :cvdTipoTramite, ");
		strUpdate.append(" FEC_REGISTRO_ACTUALIZADO = sysdate,");
		strUpdate.append(" CVE_ID_ESTADO_TRAMITE = :cveIdEstadoTramite");
		strUpdate.append(" where CVE_ID_TRAMITE = :cveIdTramite");

		SQLQuery query = this.getSession().createSQLQuery(strUpdate.toString());
		query.setParameter("cvdTipoTramite", cvdTipoTramite);
		query.setParameter("cveIdEstadoTramite", cveIdEstadoTramite);
		query.setParameter("cveIdTramite", cveIdTramite);

		return query.executeUpdate();

	}

	/**
	 * Actualiza el tipo de regulariacion CDA
	 * 
	 * @param cveIdTramite
	 * @param cvdIdTipoCorreccion
	 */
	public int actualizarRegNss(String cveIdTramite, Long cvdIdTipoCorreccion) {
		int resultado = 0;

		StringBuilder strUpdate = new StringBuilder(
				"update DIT_CORRECCION_DATOS_ASEG co ");

		strUpdate
				.append(" SET co.CVE_ID_TIPO_REGULACION = :cvdIdTipoCorreccion, ");
		strUpdate.append(" co.FEC_REGISTRO_ACTUALIZADO =sysdate");
		strUpdate.append(" where co.CVE_ID_TRAMITE= :cveIdTramite");

		try {

			SQLQuery query = this.getSession().createSQLQuery(
					strUpdate.toString());
			query.setParameter("cvdIdTipoCorreccion", cvdIdTipoCorreccion);
			query.setParameter(CVE_TRAMITE, cveIdTramite);
			return query.executeUpdate();
		} catch (Exception e) {
			log.error("ERROR CDA CVE_ID_TIPO_REGULACION", e);
		}

		return resultado;
	}

	/**
	 * Guarda la certificacion que le aplicara al nss
	 * 
	 * @param certificacion
	 */
	public void guardarCertificacionNss(
			DitTipoCertificacionCorreccion certificacion) {
		em.persist(certificacion);
	}

	/**
	 * Obtiene el tipo de aclaracion
	 * 
	 * @param idTipoAclaracion
	 * @return DicTipoNssAclaracion
	 */
	public DicTipoNssAclaracion obtenerTipoAclaracion(Long idTipoAclaracion) {

		return em.find(DicTipoNssAclaracion.class, idTipoAclaracion.intValue());
	}

	/**
	 * Recupera la lista de nss relacionados a una solictitud
	 * 
	 * @param cveSolicitud
	 * @param tramitesIniciales
	 * @return lista de tramites de un solicitud
	 */
	public List<DitDetalleNss> obtenerTramitesSol(Long cveSolicitud,
			boolean tramitesIniciales) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT det FROM DitDetalleNss det ");
		sql.append("WHERE det.correccionDatosAsegurado.tramite.ditSolicitud = :cveSolicitud ");
		if (tramitesIniciales) {
			sql.append("AND det.tramiteInicial = :tramitesIniciales ");
		}

		Query query = em.createQuery(sql.toString());
		query.setParameter("cveSolicitud", cveSolicitud);
		if (tramitesIniciales) {
			query.setParameter("tramitesIniciales", "1");
		}

		return (List<DitDetalleNss>) query.getResultList();
	}

	/**
	 * Se obtiene las aclaraciones para un nss
	 * 
	 * @param cveIdDetalle
	 * @return lista de Aclaraciones
	 */
	public List<DitTipoCertificacionCorreccion> obtenerAclaracionesTramite(
			Long cveIdDetalle) {
		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT ac FROM DitTipoCertificacionCorreccion ac ");
		sql.append(" WHERE ac.ditDetalleNss.cveDetalleNss = :cveIdDetalle ");
		sql.append(" and ac.fecRegistroBaja is null");

		Query query = em.createQuery(sql.toString());
		query.setParameter("cveIdDetalle", cveIdDetalle);

		return (List<DitTipoCertificacionCorreccion>) query.getResultList();
	}

	/**
	 * Se obtiene las regulaciones para un nss
	 * 
	 * @param nss
	 * @return lista de DES_TIPO_NSS
	 */
	public List<String> obtenerRegulacionNss(String nss) {
		StringBuilder sql = new StringBuilder();

		sql.append(" select DISTINCT ta.DES_TIPO_NSS from DIT_SOLICITUD s");
		sql.append(" left JOIN DIT_TRAMITE t on s.CVE_ID_SOLICITUD= t.CVE_ID_SOLICITUD");
		sql.append(" left JOIN DIT_CORRECCION_DATOS_ASEG cda on cda.CVE_ID_TRAMITE = t.CVE_ID_TRAMITE");
		sql.append(" left JOIN DIT_DETALLE_NSS d on cda.CVE_ID_CORRECCION_DATOS_ASEG= d.CVE_ID_CORRECCION_DATOS_ASEG");
		sql.append(" left JOIN DIC_TIPO_NSS_ACLARACION ta on ta.CVE_ID_TIPO_NSS_ACLARACION = d.CVE_ID_CORRECCION_DATOS_ASEG");
		sql.append(" left JOIN DIC_TIPO_NSS_ACLARACION tna on ta.CVE_ID_TIPO_NSS_ACLARACION = tna.CVE_ID_TIPO_NSS_ACLARACION");
		sql.append(" where d.CVE_NSS = :nss ");

		Query query = em.createQuery(sql.toString());
		query.setParameter("nss", nss);

		return (List<String>) query.getResultList();
	}

	/**
	 * Elimina las correcion de un NSS
	 * 
	 * @param idDetalleNss
	 * @return numero de registros eliminados
	 */
	public int eliminarCorrrecionesNss(Long idDetalleNss) {

		StringBuilder strUpdate = new StringBuilder(
				"UPDATE DIT_MOV_ACLARACION_NSS_CDA c");
//		strUpdate.append(" SET C.FEC_REGISTRO_BAJA = sysdate, ");
		strUpdate.append(" SET c.FEC_REGISTRO_ACTUALIZADO =sysdate");
		strUpdate.append(" where c.CVE_ID_DETALLE_NSS_CDA= :idDetalleNss");

		SQLQuery query = this.getSession().createSQLQuery(strUpdate.toString());
		query.setParameter("idDetalleNss", idDetalleNss);

		return query.executeUpdate();
	}

	/**
	 * Elimina las correciones de un NSS creadas por sistema
	 * 
	 * @param idDetalleNss
	 * @return numero de registros eliminados
	 */
	public int eliminarCorrrecionesNssPorSistema(Long idDetalleNss) {
		StringBuilder strUpdate = new StringBuilder(
				"DELETE DIT_MOV_ACLARACION_NSS_CDA c");
		strUpdate.append(" WHERE c.CVE_ID_DETALLE_NSS_CDA = :idDetalleNss AND c.IND_MOV_SISTEMA IS NOT NULL");
		SQLQuery query = this.getSession().createSQLQuery(strUpdate.toString());
		query.setParameter("idDetalleNss", idDetalleNss);
		return query.executeUpdate();		
	}
	
	
	/**
	 * Obtiene el detalle nss por id
	 * 
	 * @param idDetalleNss
	 * @return DitDetalleNss
	 */
	public DitDetalleNss obtenerDetalleNss(Long idDetalleNss) {
		return em.find(DitDetalleNss.class, idDetalleNss);
	}

	/**
	 * METODOS DE NUEVOS PARA LA NUEVA IMPLEMENTACION
	 * 
	 */

	/**
	 * Metodo que actualiza el tipo de Nss al Detalle Nss
	 * 
	 * @param idDetalle
	 * @param cveIdTipoNss
	 */
	public void actualizarTipoDetalleNss(Long idDetalle, Long cveIdTipoNss) {
		log.debug("id del detalle : " + idDetalle);
		log.debug("cveIdTipoNss : " + cveIdTipoNss);
		DitDetalleNss detalle = em.find(DitDetalleNss.class, idDetalle);

		DicTipoNssCorreccion tipoNss = em.find(DicTipoNssCorreccion.class,
				cveIdTipoNss);
		detalle.setDicTipoNss(tipoNss);
		em.merge(detalle);

	}

	/**
	 * Obtiene detalle nss por tramite y numero de seguridad social
	 * 
	 * @param idTramite
	 * @param nss
	 * @return
	 */
	public DitDetalleNss obtenerDetalleNss(Long idTramite, String nss) {
		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT det FROM DitDetalleNss det ");
		sql.append(" WHERE det.nss = :nss ");
		sql.append(" and det.correccionDatosAsegurado.tramite.cveIdTramite = :idTramite");
		sql.append(" and ROWNUM = 1");

		Query query = em.createQuery(sql.toString());
		query.setParameter("nss", nss);
		query.setParameter("idTramite", idTramite);

		return (DitDetalleNss) query.getSingleResult();
	}

	public DitCorreccionDatosAsegurado obtenerCorreccionDatosAsegurado(
			Long cveIdTramite) {
		StringBuilder sql = new StringBuilder();

		sql.append(" FROM DitCorreccionDatosAsegurado cd ");
		sql.append(" WHERE cd.tramite.cveIdTramite = :cveIdTramite ");

		Query query = em.createQuery(sql.toString());
		query.setParameter("cveIdTramite", cveIdTramite);

		return (DitCorreccionDatosAsegurado) query.getSingleResult();
	}
	


	/**
	 * Metodo para obtener nss asociados a curp del asegurado
	 * 
	 * @param curp
	 * @return
	 */
	public List<String> obtenerNssDitAsignacionPorCurp(String curp) {
		List<String> listaNss = new ArrayList<String>();
		if(validacionAutomatica(curp)){
			StringBuffer sql = new StringBuffer();
			sql.append("select asignacion.numNss from DitAsignacionNss asignacion ");
			sql.append("where asignacion.ditPersona.curp =:curp ");
			sql.append("and asignacion.fecRegistroBaja is null");
			Query query = this.em.createQuery(sql.toString());
			query.setParameter("curp", curp);
			
			try {
				listaNss = (List<String>) query.getResultList();
			} catch (NoResultException nre) {
				log.debug("No se localizó el NSS: " + curp);
			}
		}		
		return listaNss;
	}
	 private boolean validacionAutomatica(String curp)
	    {
		 List<String> listaNss = null;
		 listaNss = validarSTEP1(curp);
		 boolean validacion= false;
		 if(!listaNss.isEmpty())
		 {
			 log.debug("--CDA--validacionAutomatica con el curp: " + curp);
			 List<String> numNSS;
		    	
		    	
		    	StringBuffer sql = new StringBuffer();
						
				sql.append("SELECT NSS.NUM_NSS FROM DIT_DETALLE_NSS_CDA NSS WHERE NSS.NUM_NSS =:nss ");
							
				javax.persistence.Query queryDescripcion = em.createNativeQuery(sql.toString());
				queryDescripcion.setParameter("nss", listaNss.get(0));
				numNSS =  queryDescripcion.getResultList();
				log.debug("--CDA--NSS otenido: " + listaNss.toString());
				log.debug("--CDA--validacionAutomatica con el numNSS: " + numNSS);
				if(numNSS.isEmpty())
				{
					validacion = true;
				}								
		 }
		 return validacion;
	    }
	 private List<String> validarSTEP1(String varcurp) {
					
				StringBuffer sql = new StringBuffer();
				sql.append("select asignacion.numNss from DitAsignacionNss asignacion ");
				sql.append("where asignacion.ditPersona.curp =:curp ");
				sql.append("and asignacion.fecRegistroBaja is null");
				Query query = this.em.createQuery(sql.toString());
				query.setParameter("curp", varcurp);
				List<String> listadNss = null;
				try {					
					listadNss = (List<String>) query.getResultList();
				} catch (NoResultException nre) {
					log.debug("No se localizó el NSS: " + varcurp);
				}
					
			return listadNss;
		}
	 
	 
	 /**
		 * Metodo para saber si existe una solicitud asociada al CURP que se ingreso
		 */
		@Override
		public List<Object> ValidacionTramiteExiste (List<String> curps	) 
		{
			StringBuffer sql = new StringBuffer();

			
			sql.append(" SELECT DT.CVE_ID_TRAMITE ");
			sql.append(" FROM DIT_SOLICITUD DS , DIT_TRAMITE DT , DIT_CORRECCION_DATOS_ASEG DCDA ");
			sql.append(" WHERE DS.CVE_ID_SOLICITUD = DT.CVE_ID_SOLICITUD ");
			sql.append(" AND DCDA.CVE_ID_TRAMITE = DT.CVE_ID_TRAMITE ");
			sql.append(" AND(DS.CVE_ID_ESTADO_SOLICITUD = 1 OR DS.CVE_ID_ESTADO_SOLICITUD=5) ");
			sql.append(" AND (DT.CVE_ID_ESTADO_TRAMITE =75 OR DT.CVE_ID_ESTADO_TRAMITE =4 OR DT.CVE_ID_ESTADO_TRAMITE =37) ");
			sql.append(" AND DCDA.REF_CURP =:curp");

			javax.persistence.Query query = em.createNativeQuery(sql.toString());

			query.setParameter("curp", curps.get(0));

			List<Object> idTramite =  query.getResultList();

			
			return idTramite;
		}
		
		
		@Override
		public List<String> ObtenerIDTramite (String numFolio) 
		{
			StringBuffer sql = new StringBuffer();
			
			sql.append(" select dt.cve_id_tramite ");
			sql.append(" from dit_solicitud ds  ");
			sql.append(" inner join dit_tramite dt on ds.cve_id_solicitud = dt.cve_id_solicitud ");
			sql.append(" where CVE_ID_ESTADO_TRAMITE = '88' ");
			sql.append(" and ds.ref_folio =:folio  ");

			javax.persistence.Query query = em.createNativeQuery(sql.toString());

			query.setParameter("folio", numFolio);

			List<String> idTramite =  query.getResultList();
			log.debug("--CDA-Resultado lista Certificacion idtramite: " + idTramite.toString());
			if(!idTramite.isEmpty() || idTramite != null)
			{
				log.debug("--CDA-inicia actualizacion tramite certificacion: " + idTramite);
				
				StringBuffer sql1 = new StringBuffer();

				sql1.append("UPDATE DIT_TRAMITE set CVE_ID_ESTADO_TRAMITE = '2' where CVE_ID_TRAMITE = :cveIdTramitee");

				javax.persistence.Query query1 = em.createNativeQuery(sql1.toString());

				query1.setParameter("cveIdTramitee", idTramite.get(0));
				
				int numSolicitudesActualizadas = query1.executeUpdate();
				log.error("Se actualizaron los sig numero de solicitudes: "
						+ numSolicitudesActualizadas);
				
//				String queryTipoTramite = "UPDATE DIT_TRAMITE set CVE_ID_ESTADO_TRAMITE = '2' where CVE_ID_TRAMITE ="+Integer.valueOf(idTramite.get(0));
//				         
//				this.em.createNativeQuery(queryTipoTramite).executeUpdate();
				log.debug("--CDA-inicia actualizacion tramite certificacion FIN: " + idTramite);
			}
			
			return idTramite;
		}
		
		@Override
		public void ActulizarTipoTramite (String idTramite	) 
		{
			log.debug("--CDA-inicia actualizacion tramite certificacionooo: " + idTramite);
			
			String queryTipoTramite = "UPDATE DIT_TRAMITE set CVE_ID_ESTADO_TRAMITE = '2' where CVE_ID_TRAMITE ="+Integer.valueOf(idTramite);
			         
			this.em.createNativeQuery(queryTipoTramite).executeUpdate();
			log.debug("--CDA-inicia actualizacion tramite certificacion FIN: " + idTramite);
					
		}
		
	public void eliminarMovimientoPorDetalleNss(Long cveIdDetalleNss,
			Long cveIdTipoTramiteCorrNss) {
		log.debug("--eliminarMovimientoPorDetalleNss: cveIdDetalleNss "
				+ cveIdDetalleNss);
		log.debug("--eliminarMovimientoPorDetalleNss cveIdDetalleNss: "
				+ cveIdDetalleNss);
		String queryEliminarBlanqueamiento = "  delete from DIT_MOV_ACLARACION_NSS_CDA where CVE_ID_DETALLE_NSS_CDA =:cveIdDetalleNss "
				+ "and CVE_ID_TIPO_TRAM_CORREC_NSS =:cveIdTipoTramiteCorrNss ";
		javax.persistence.Query query = em
				.createNativeQuery(queryEliminarBlanqueamiento.toString());
		query.setParameter("cveIdDetalleNss", cveIdDetalleNss);
		query.setParameter("cveIdTipoTramiteCorrNss", cveIdTipoTramiteCorrNss);
		int eliminados = query.executeUpdate();
		log.debug("--eliminarMovimientoPorDetalleNss eliminados: " + eliminados);
	}

	@Override
	public void guardarActualizarBitacoraSeparacionPersonas(DitBitSeparacionPersonas bitacora) {

		if (bitacora.getCveIdPersonaNuevo() == null) {
			log.info("Se guarda nuevo registro en la bitacora de separacion de personas");
			em.persist(bitacora);
		} else {
			log.info("Se actualiza la bitacora de separacion de personas");
			em.merge(bitacora);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DitBitSeparacionPersonas> obtenerBitacoraSeparacionPersonas(Long cveIdTramite, boolean regActualizado) {

		log.info("Consultando bitacora de separacion de personas");
		Criteria criteria = getSession().createCriteria(DitBitSeparacionPersonas.class);
		criteria.add(Restrictions.eq("cveIdTramite", cveIdTramite));
		if (regActualizado) {
			criteria.add(Restrictions.isNull("fecRegistroActualizado"));
		}
		List<DitBitSeparacionPersonas> bitacora = criteria.list();

		return !bitacora.isEmpty() ? bitacora : null;
	}

	/**
	 *
	 * @param nss Lista de nss para validar si ya existen en la tabla de pensionados
	 * @return Regresa true si uno o mas NSS ya estan pensionados
	 */
	@Override
	public boolean consultaNSSPension(List<String> nss) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT count(*) ");
		sql.append("FROM MGPBDTU9X.DIT_PENSIONADOS_DIARIO ");
		sql.append("WHERE CVE_NSS IN (");

		boolean first = true;
		for (int i = 0; i < nss.size(); i++) {
			if (!first) {
				sql.append(", ");
			}
			sql.append("?");
			first = false;
		}
		sql.append(")");

		Query query = em.createNativeQuery(sql.toString());

		int paramIndex = 1;
		for (String nssValue : nss) {
			query.setParameter(paramIndex++, nssValue);
		}

		long count = ((Number) query.getSingleResult()).longValue();


		return count > 0;
	}

	@Override
	public void cancelarSolicitudTramiteInstancia(Long idSolicitud, Long idTramite, List<String> nss) {

		String observacion = "IMPROCEDENCIA POR PENSION " + StringUtils.join(nss.toArray(), ", ");

		String querySolicitud = "UPDATE DIT_SOLICITUD SET CVE_ID_ESTADO_SOLICITUD = 3, "
				+ "REF_OBSERVACION = '" + observacion + "', "
				+ "FEC_REGISTRO_ACTUALIZADO = SYSDATE "
				+ "WHERE CVE_ID_SOLICITUD IN (" + idSolicitud + ")";

		String queryTramite = "UPDATE DIT_TRAMITE SET CVE_ID_ESTADO_TRAMITE = 7, "
				+ "FEC_REGISTRO_ACTUALIZADO = SYSDATE "
				+ "WHERE CVE_ID_SOLICITUD IN (" + idSolicitud + ")";

		String queryInstancia = "UPDATE DIT_INSTANCIA SET CVE_ID_EDO_INSTANCIA = 2, "
				+ "FEC_REGISTRO_ACTUALIZADO = SYSDATE "
				+ "WHERE CVE_ID_TRAMITE IN (" + idTramite + ")";

		this.em.createNativeQuery(querySolicitud).executeUpdate();
		this.em.createNativeQuery(queryTramite).executeUpdate();
		this.em.createNativeQuery(queryInstancia).executeUpdate();
		this.em.flush();
	}

}
