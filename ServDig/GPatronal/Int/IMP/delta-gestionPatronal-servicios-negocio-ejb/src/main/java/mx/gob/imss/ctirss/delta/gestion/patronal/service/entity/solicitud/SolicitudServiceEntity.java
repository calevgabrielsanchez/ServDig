/**
 *
 *
 **/
package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.solicitud;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.PropietarioSolicitudUtil;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.solicitud.SolicitudUtilityLocal;
import mx.gob.imss.ctirss.delta.model.ModuloEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FiltroSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DicModalidad;
import mx.gob.imss.ctirss.delta.persistence.DicOrigenSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DicRazonCancelacion;
import mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicTipoSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DicTipoTramite;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleTramite;
import mx.gob.imss.ctirss.delta.persistence.DitPatronGeneral;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitudDocumento;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaMoral;
import mx.gob.imss.ctirss.delta.persistence.DitUmfTurno;
import mx.gob.imss.ctirss.delta.tramite.service.utility.TramiteServiceUtilityLocal;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * 
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Autor: Hugo Armando Martínez Chamónica
 * @Proyecto: delta
 * @Archivo: SolicitudServiceEntity.java
 * @Paquete: mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.solicitud
 * @Fecha: 10:29:18
 */
@Stateless
public class SolicitudServiceEntity extends AbstractServiceEntity implements
		SolicitudServiceEntityLocal {

	@EJB
	private SolicitudUtilityLocal solicitudUtilityLocal;

	@EJB
	private TramiteServiceUtilityLocal tramiteUtilityLocal;

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.solicitud.
	 * SolicitudServiceEntityLocal#consultarSolicitud(java.lang.Long)
	 */
	@Override
	public Solicitud consultarSolicitud(Long idSolicitud,
			TipoPersonaFiscal tipoPersona) {
		DitSolicitud entity = this.getEntityManager().find(DitSolicitud.class,
				idSolicitud);
		Solicitud solicitud = solicitudUtilityLocal
				.convertEntityToModelSolicitud(entity);

		return solicitud;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.solicitud.
	 * SolicitudServiceEntityLocal
	 * #insertarSolicitud(mx.gob.imss.ctirss.delta.model
	 * .gestion.patronal.Solicitud,
	 * mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal)
	 */
	@Override
	public void insertarSolicitud(Solicitud model) {
		System.out.println("Insertando solicitud - service entity: "
				+ model.toString());
		DitSolicitud solicitud = solicitudUtilityLocal
				.convertirModelToEntitySolicitud(model);
		DicTipoSolicitud tipo = (DicTipoSolicitud) this.getSession().load(
				DicTipoSolicitud.class,
				solicitud.getDicTipoSolicitud().getCveIdTipoSolicitud());
		DicEstadoSolicitud estado = (DicEstadoSolicitud) this.getSession()
				.load(DicEstadoSolicitud.class,
						solicitud.getDicEstadoSolicitud()
								.getCveIdEstadoSolicitud());
		DicRazonCancelacion razon = null;
		DitUmfTurno umfTurno = null;
		if (model.getRazonCancelacion() != null) {
			razon = (DicRazonCancelacion) this.getSession().load(
					DicRazonCancelacion.class,
					model.getRazonCancelacion().getIdRazonCancelacion());
		}
		
		/*DitUsuario usuario = (DitUsuario) this.getSession().load(
				DitUsuario.class, model.getSolicitante().getCveIdUsuario());
				*/
		solicitud.setDicTipoSolicitud(tipo);
		solicitud.setDicEstadoSolicitud(estado);
		solicitud.setDicRazonCancelacion(razon);
		solicitud.setCveIdUsuario(model.getSolicitante().getUsuario());
		solicitud.setDitUmfTurno(umfTurno);
		Object returnValue = this.getSession().save(solicitud);
		model.setSolicitudId(solicitud.getCveIdSolicitud());
		System.out.println("SOLICITUD_SERVICE_ENTITY ID SOLICITUD: "
				+ solicitud.getCveIdSolicitud());
		System.out.println("Return value: " + returnValue.toString());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.solicitud.
	 * SolicitudServiceEntityLocal#actualizarEstadoSolicitud(java.lang.Long,
	 * java.lang.Long)
	 */
	@Override
	public void actualizarEstadoSolicitud(Long idSolicitud,
			Long idEstadoSolicitud) {
		DitSolicitud sol = (DitSolicitud) this.getSession().load(
				DitSolicitud.class, idSolicitud);
		DicEstadoSolicitud estado = (DicEstadoSolicitud) this.getSession()
				.load(DicEstadoSolicitud.class, idEstadoSolicitud);
		sol.setDicEstadoSolicitud(null);
		sol.setDicEstadoSolicitud(estado);
		this.getSession().saveOrUpdate(sol);
	}

	// ******************************** Metodos del nuevo flujo

	@Override
	public Solicitud obtenerSolicitudEnCaptura(Long idPatronSujetoObligado,
			TipoSolicitudEnum tipoSolicitud, TipoTramiteEnum tipoTramite) {
		this.log.debug(" Obteniendo solicitud activa del Patron :::"
				+ idPatronSujetoObligado);

		StringBuffer bfr = new StringBuffer();
		bfr.append(" Select solicitud from DitTramitePatSujObligado tso ");
		bfr.append(" join tso.ditTramite as tramite ");
		bfr.append(" join tramite.ditSolicitud as solicitud ");
		bfr.append(" where tso.ditPatronSujetoObligado.cveIdPatronSujetoObligado = :idSujetoObligado and ");
		bfr.append(" solicitud.dicTipoSolicitud.cveIdTipoSolicitud = :idTipoSolicitud and ");
		bfr.append(" solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud = :idEstadoSolicitud ");
		if(tipoTramite!=null){
			bfr.append(" and tramite.dicTipoTramite.cveIdTipoTramite = :idTipoTramite ");
		}
		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("idSujetoObligado", idPatronSujetoObligado);
		query.setParameter("idTipoSolicitud", tipoSolicitud.getValor().longValue());
		query.setParameter("idEstadoSolicitud", EstadoSolicitudEnum.REGISTRADA.getCodigo().longValue());
		if(tipoTramite!=null){
			query.setParameter("idTipoTramite", tipoTramite.getCodigo().longValue());
		}

		Object entity = null;
		try {
			entity = query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
		Solicitud solicitud = null;

		if (entity != null) {
			DitSolicitud ditSolicitud = (DitSolicitud) entity;
			solicitud = solicitudUtilityLocal
					.convertEntityToModelSolicitud(ditSolicitud);
		}

		return solicitud;
	}

	@Override
	public Solicitud obtenerSolicitudEnCapturaPorPersona(Long idPersona,
			TipoPersonaFiscal tipo, TipoSolicitudEnum tipoSolicitud) {
		this.log.debug(" Obteniendo solicitud activa de la persona :::"
				+ idPersona);

		StringBuffer bfr = new StringBuffer();

		if (tipo.equals(TipoPersonaFiscal.FISICA)) {
			bfr.append(" Select solicitud from DitTramitePersonaFisica tpf ");
			bfr.append(" join tpf.ditTramite as tramite ");
			bfr.append(" join tramite.ditSolicitud as solicitud ");
			bfr.append(" where tpf.id.cveIdPersona = :idPersona and ");
			if (!tipoSolicitud.getValor().equals(
					TipoSolicitudEnum.TODAS.getValor())) {
				bfr.append(" solicitud.dicTipoSolicitud.cveIdTipoSolicitud = :idTipoSolicitud and ");
			}
			bfr.append(" solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud = :idEstadoSolicitud ");
		} else {
			bfr.append(" Select solicitud from DitTramitePersonaMoral tpm ");
			bfr.append(" join tpm.ditTramite as tramite ");
			bfr.append(" join tramite.ditSolicitud as solicitud ");
			bfr.append(" where tpm.id.cveIdPersonaMoral = :idPersona and ");
			if (!tipoSolicitud.getValor().equals(
					TipoSolicitudEnum.TODAS.getValor())) {
				bfr.append(" solicitud.dicTipoSolicitud.cveIdTipoSolicitud = :idTipoSolicitud and ");
			}
			bfr.append(" solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud = :idEstadoSolicitud ");

		}

		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("idPersona", idPersona);
		if (!tipoSolicitud.getValor()
				.equals(TipoSolicitudEnum.TODAS.getValor())) {
			query.setParameter("idTipoSolicitud", tipoSolicitud.getValor()
					.longValue());
		}
		query.setParameter("idEstadoSolicitud", EstadoSolicitudEnum.REGISTRADA
				.getCodigo().longValue());

		Object entity = null;
		try {
			entity = query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
		Solicitud solicitud = null;
		if (entity != null) {
			DitSolicitud ditSolicitud = (DitSolicitud) entity;
			solicitud = solicitudUtilityLocal
					.convertEntityToModelSolicitud(ditSolicitud);
		}

		return solicitud;
	}

	@Override
	public Solicitud obtenerSolicitudEnProceso(Long idPatronSujetoObligado,
			TipoSolicitudEnum tipoSolicitud, TipoTramiteEnum tipoTramite) {
		this.log.debug(" Obteniendo solicitud en proceso del Patron :::"
				+ idPatronSujetoObligado);

		StringBuffer bfr = new StringBuffer();
		bfr.append(" Select solicitud from DitTramitePatSujObligado tso ");
		bfr.append(" join tso.ditTramite as tramite ");
		bfr.append(" join tramite.ditSolicitud as solicitud ");
		bfr.append(" where tso.ditPatronSujetoObligado.cveIdPatronSujetoObligado = :idSujetoObligado and ");
		bfr.append(" solicitud.dicTipoSolicitud.cveIdTipoSolicitud = :idTipoSolicitud and ");
		bfr.append(" solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud IN :idsEstadoSolicitud ");
		if(tipoTramite!=null){
			bfr.append(" and tramite.dicTipoTramite.cveIdTipoTramite = :idTipoTramite ");
		}

		List<Long> idsEstadoSolicitud = new ArrayList<Long>();
		// idsEstadoSolicitud.add(EstadoSolicitudEnum.REGISTRADA.getCodigo().longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo().longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.EDICION_VENTANILLA.getCodigo().longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.EDICION_BACKOFFICE.getCodigo().longValue());

		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("idSujetoObligado", idPatronSujetoObligado);
		query.setParameter("idTipoSolicitud", tipoSolicitud.getValor().longValue());
		query.setParameter("idsEstadoSolicitud", idsEstadoSolicitud);
		if(tipoTramite!=null){
			query.setParameter("idTipoTramite", tipoTramite.getCodigo().longValue());
		}

		Object entity = null;
		try {
			entity = query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
		Solicitud solicitud = null;

		if (entity != null) {
			DitSolicitud ditSolicitud = (DitSolicitud) entity;
			solicitud = solicitudUtilityLocal
					.convertEntityToModelSolicitud(ditSolicitud);
		}

		return solicitud;
	}

	@Override
	public Solicitud obtenerSolicitudEnProcesoPorPersona(Long idPersona,
			TipoPersonaFiscal tipo, TipoSolicitudEnum tipoSolicitud) {
		this.log.debug(" Obteniendo solicitud en proceso de la persona :::"
				+ idPersona);
		this.log.debug(" Tipo de la persona :::" + tipo);
		StringBuffer bfr = new StringBuffer();

		if (tipo.equals(TipoPersonaFiscal.FISICA)) {
			bfr.append(" Select solicitud from DitTramitePersonaFisica tpf ");
			bfr.append(" join tpf.ditTramite as tramite ");
			bfr.append(" join tramite.ditSolicitud as solicitud ");
			bfr.append(" where tpf.id.cveIdPersona = :idPersona and ");
			bfr.append(" solicitud.dicTipoSolicitud.cveIdTipoSolicitud = :idTipoSolicitud and ");
			bfr.append(" solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud IN :idsEstadoSolicitud ");
		} else {
			bfr.append(" Select solicitud from DitTramitePersonaMoral tpm ");
			bfr.append(" join tpm.ditTramite as tramite ");
			bfr.append(" join tramite.ditSolicitud as solicitud ");
			bfr.append(" where tpm.id.cveIdPersonaMoral = :idPersona and ");
			bfr.append(" solicitud.dicTipoSolicitud.cveIdTipoSolicitud = :idTipoSolicitud and ");
			bfr.append(" solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud IN :idsEstadoSolicitud ");

		}

		List<Long> idsEstadoSolicitud = new ArrayList<Long>();
		// idsEstadoSolicitud.add(EstadoSolicitudEnum.REGISTRADA.getCodigo().longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION
				.getCodigo().longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.EDICION_VENTANILLA
				.getCodigo().longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.EDICION_BACKOFFICE
				.getCodigo().longValue());

		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("idPersona", idPersona);
		query.setParameter("idTipoSolicitud", tipoSolicitud.getValor()
				.longValue());
		query.setParameter("idsEstadoSolicitud", idsEstadoSolicitud);

		Object entity = null;
		try {
			entity = query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
		Solicitud solicitud = null;
		if (entity != null) {
			DitSolicitud ditSolicitud = (DitSolicitud) entity;
			solicitud = solicitudUtilityLocal
					.convertEntityToModelSolicitud(ditSolicitud);
		}

		return solicitud;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Solicitud> listarSolicitudesActivasPorPatron(
			Long idPatronSujetoObligado) {
		this.log.debug(" Obteniendo solicitudes activas del Patron :::"
				+ idPatronSujetoObligado);

		StringBuffer bfr = new StringBuffer();
		bfr.append(" Select DISTINCT solicitud from DitTramitePatSujObligado tso ");
		bfr.append(" join tso.ditTramite as tramite ");
		bfr.append(" join tramite.ditSolicitud as solicitud ");
		bfr.append(" where tso.ditPatronSujetoObligado.cveIdPatronSujetoObligado = :idSujetoObligado and ");
		bfr.append(" solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud = :idEstadoSolicitud ");

		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("idSujetoObligado", idPatronSujetoObligado);
		query.setParameter("idEstadoSolicitud", EstadoSolicitudEnum.REGISTRADA
				.getCodigo().longValue());

		List<DitSolicitud> entities = query.getResultList();
		List<Solicitud> solicitudes = null;
		if (entities != null) {
			solicitudes = new ArrayList<Solicitud>();
			for (DitSolicitud entity : entities) {
				solicitudes.add(solicitudUtilityLocal
						.convertEntityToModelSolicitud(entity));
			}
		}
		return solicitudes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Solicitud> listarSolicitudesActivasPorPersona(Long idPersona,
			TipoPersonaFiscal tipo) {
		this.log.debug(" Obteniendo solicitudes activas de la persona :::"
				+ idPersona);

		StringBuffer bfr = new StringBuffer();

		if (tipo.equals(TipoPersonaFiscal.FISICA)) {
			bfr.append(" Select DISTINCT solicitud from DitTramitePersonaFisica tpf ");
			bfr.append(" join tpf.ditTramite as tramite ");
			bfr.append(" join tramite.ditSolicitud as solicitud ");
			bfr.append(" where tpf.id.cveIdPersona = :idPersona and ");
			bfr.append(" solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud = :idEstadoSolicitud ");
		} else {
			bfr.append(" Select DISTINCT solicitud from DitTramitePersonaMoral tpm ");
			bfr.append(" join tpm.ditTramite as tramite ");
			bfr.append(" join tramite.ditSolicitud as solicitud ");
			bfr.append(" where tpm.id.cveIdPersonaMoral = :idPersona and ");
			bfr.append(" solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud = :idEstadoSolicitud ");

		}

		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("idPersona", idPersona);
		query.setParameter("idEstadoSolicitud", EstadoSolicitudEnum.REGISTRADA
				.getCodigo().longValue());

		List<DitSolicitud> entities = query.getResultList();
		List<Solicitud> solicitudes = null;
		if (entities != null) {
			solicitudes = new ArrayList<Solicitud>();
			for (DitSolicitud entity : entities) {
				solicitudes.add(solicitudUtilityLocal
						.convertEntityToModelSolicitud(entity));
			}
		}
		return solicitudes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Solicitud> listarSolicitudesEnProcesoPorPatron(
			Long idPatronSujetoObligado, boolean esTramitador) {
		this.log.debug(" Obteniendo solicitud en proceso del Patron :::"
				+ idPatronSujetoObligado);
		StringBuffer bfrTT = new StringBuffer();
		bfrTT.append("select tt.cveIdTipoTramite from DicTipoTramite tt, DicModulo m ");
		bfrTT.append("where m.cveIdModulo = :idModulo and m in elements(tt.dicModulos)");

		Query queryTT = this.em.createQuery(bfrTT.toString());
		queryTT.setParameter("idModulo", ModuloEnum.PATRONES.getCodigo()
				.longValue());

		// List<Long> idTipoTramites = queryTT.getResultList();

		StringBuffer bfr = new StringBuffer();
		bfr.append(" Select DISTINCT solicitud from DitTramitePatSujObligado tso ");
		bfr.append(" join tso.ditTramite as tramite ");
		bfr.append(" join tramite.ditSolicitud as solicitud ");
		bfr.append(" join tramite.dicTipoTramite as tipoTramite ");
		bfr.append(" where tso.ditPatronSujetoObligado.cveIdPatronSujetoObligado = :idSujetoObligado and ");
		bfr.append(" solicitud.dicTipoSolicitud.cveIdTipoSolicitud IN :idsTipoSolicitud and ");
		// bfr.append(" tipoTramite.cveIdTipoTramite IN (");
		// bfr.append("					select tt.cveIdTipoTramite from DicTipoTramite tt, DicModulo m ");
		// bfr.append("					where m.cveIdModulo = :idModulo and m in elements(tt.dicModulos)) and ");
		bfr.append(" solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud IN :idsEstadoSolicitud ");

		List<Long> idsEstadoSolicitud = new ArrayList<Long>();
		idsEstadoSolicitud.add(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION
				.getCodigo().longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.EDICION_VENTANILLA
				.getCodigo().longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.EDICION_BACKOFFICE
				.getCodigo().longValue());
		// idsEstadoSolicitud.add(EstadoSolicitudEnum.REGISTRADA.getCodigo().longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.PRESENTARSE_EN_VENTANILLA
				.getCodigo().longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.PARA_PROCESAR_BACKOFFICE
				.getCodigo().longValue());

		if (!esTramitador)
			idsEstadoSolicitud.add(EstadoSolicitudEnum.REGISTRADA.getCodigo()
					.longValue());

		List<Long> idsTipoSolicitud = new ArrayList<Long>();
		idsTipoSolicitud.add(TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO
				.getValor().longValue());
		idsTipoSolicitud.add(TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION
				.getValor().longValue());

		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("idSujetoObligado", idPatronSujetoObligado);
		query.setParameter("idsEstadoSolicitud", idsEstadoSolicitud);
		query.setParameter("idsTipoSolicitud", idsTipoSolicitud);
		// query.setParameter("idModulo",
		// ModuloEnum.PATRONES.getCodigo().longValue());
		// query.setParameter("tiposTramites", idTipoTramites);

		List<DitSolicitud> entities = null;
		entities = query.getResultList();
		List<Solicitud> solicitudes = null;
		if (entities != null) {
			solicitudes = new ArrayList<Solicitud>();
			for (DitSolicitud entity : entities) {
				Solicitud nuevaSolicitud = solicitudUtilityLocal
						.convertEntityToModelSolicitud(entity);
				SujetoObligado sujeto = new SujetoObligado();
				// Se agrega la información de registro patronal a la solicitud
				if (entity.getDitTramites() != null
						&& entity.getDitTramites().size() > 0) {
					DitPatronSujetoObligado so = entity.getDitTramites().get(0)
							.getDitTramitePatSujObligados().get(0)
							.getDitPatronSujetoObligado();
					DicModalidad modalidad = so.getDicModalidad();
					sujeto.setCveIdSujetoObligado(so
							.getCveIdPatronSujetoObligado());
					DitPatronGeneral patronGeneral = so.getDitPatronGenerals()
							.get(0);

					sujeto.setNumeroRegistroPatronal(patronGeneral
							.getRegPatron());
					sujeto.setDigVerificador(patronGeneral.getDigVer());
					sujeto.setModalidad(new Modalidad());
					sujeto.getModalidad().setIdModalidad(
							modalidad.getCveIdModalidad());
					sujeto.getModalidad().setNumModalidad(
							modalidad.getNumModalidad());
					sujeto.getModalidad().setDescripcion(
							modalidad.getDesModalidad());

				}
				nuevaSolicitud.setSujetoObligado(sujeto);
				solicitudes.add(nuevaSolicitud);
			}
		}
		return solicitudes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Solicitud> listarSolicitudesEnProcesoPorPersona(Long idPersona,
			TipoPersonaFiscal tipo) {
		this.log.debug(" Obteniendo solicitud en proceso de la persona :::"
				+ idPersona);

		StringBuffer bfr = new StringBuffer();

		if (tipo.equals(TipoPersonaFiscal.FISICA)) {
			bfr.append(" Select DISTINCT solicitud from DitTramitePersonaFisica tpf ");
			bfr.append(" join tpf.ditTramite as tramite ");
			bfr.append(" join tramite.ditSolicitud as solicitud ");
			bfr.append(" join tramite.dicTipoTramite as tipoTramite ");
			bfr.append(" where tpf.id.cveIdPersona = :idPersona and ");
			bfr.append(" solicitud.dicTipoSolicitud.cveIdTipoSolicitud IN :idsTipoSolicitud and ");
			// bfr.append(" tipoTramite.cveIdTipoTramite IN (");
			// bfr.append("					select tt.cveIdTipoTramite from DicTipoTramite tt, DicModulo m ");
			// bfr.append("					where m.cveIdModulo = :idModulo and m in elements(tt.dicModulos)) and ");
			bfr.append(" solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud IN :idsEstadoSolicitud ");
		} else {
			bfr.append(" Select DISTINCT solicitud from DitTramitePersonaMoral tpm ");
			bfr.append(" join tpm.ditTramite as tramite ");
			bfr.append(" join tramite.ditSolicitud as solicitud ");
			bfr.append(" join tramite.dicTipoTramite as tipoTramite ");
			bfr.append(" where tpm.id.cveIdPersonaMoral = :idPersona and ");
			bfr.append(" solicitud.dicTipoSolicitud.cveIdTipoSolicitud IN :idsTipoSolicitud and ");
			// bfr.append(" tipoTramite.cveIdTipoTramite IN (");
			// bfr.append("					select tt.cveIdTipoTramite from DicTipoTramite tt, DicModulo m ");
			// bfr.append("					where m.cveIdModulo = :idModulo and m in elements(tt.dicModulos)) and ");
			bfr.append(" solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud IN :idsEstadoSolicitud ");
		}

		List<Long> idsEstadoSolicitud = new ArrayList<Long>();
		idsEstadoSolicitud.add(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION
				.getCodigo().longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.EDICION_VENTANILLA
				.getCodigo().longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.EDICION_BACKOFFICE
				.getCodigo().longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.REGISTRADA.getCodigo()
				.longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.PRESENTARSE_EN_VENTANILLA
				.getCodigo().longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.PARA_PROCESAR_BACKOFFICE
				.getCodigo().longValue());
		// idsEstadoSolicitud.add(EstadoSolicitudEnum.ATENDIDA.getCodigo().longValue());

		List<Long> idsTipoSolicitud = new ArrayList<Long>();
		idsTipoSolicitud.add(TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES
				.getValor().longValue());

		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("idPersona", idPersona);
		query.setParameter("idsEstadoSolicitud", idsEstadoSolicitud);
		query.setParameter("idsTipoSolicitud", idsTipoSolicitud);
		// query.setParameter("idModulo",
		// ModuloEnum.PATRONES.getCodigo().longValue());
		// query.setParameter("tiposTramites", idTipoTramites);

		List<DitSolicitud> entities = null;
		List<Solicitud> solicitudes = null;

		entities = query.getResultList();
		System.err.println("solicitudes por persona encontradas: " + entities);
		if (entities != null) {
			System.err.println("solicitudes por persona encontradas: "
					+ entities.size());
			solicitudes = new ArrayList<Solicitud>();
			for (DitSolicitud entity : entities) {
				solicitudes.add(solicitudUtilityLocal
						.convertEntityToModelSolicitud(entity));
			}
		}
		return solicitudes;
	}

	@Override
	public void eliminarTramite(Long idTramite) {
		DitTramite tramite = em.find(DitTramite.class, idTramite);
		// TODO: LUDS Se modifico esta parte por un cambio en dittramite, en
		// donde se requiere obtener la lista de personas afectadas por un
		// tramite, por lo tanto es una lista
		List<DitTramitePersonaFisica> tramitePersonaFisicaList = tramite
				.getDitTramitePersonaFisica();
		DitTramitePersonaFisica tpf = null;
		if (tramitePersonaFisicaList != null
				&& !tramitePersonaFisicaList.isEmpty()) {
			tpf = tramite.getDitTramitePersonaFisica().get(0);
		}

		DitTramitePersonaMoral tpm = tramite.getDitTramitePersonaMoral();
		DitDetalleTramite detalle = tramite.getDitDetalleTramite();

		if (tpf != null) {
			em.remove(tpf);
		}
		if (tpm != null) {
			em.remove(tpm);
		}
		em.remove(detalle);
		em.remove(tramite);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DatosSalidaPaginador<Solicitud> listarSolicitudesPorFiltro(
			DatosEntradaPaginador<Solicitud> input, FiltroSolicitud filtros) {
		StringBuffer bfr = new StringBuffer();
		System.err.println("Ejecutando consulta con folio: "
				+ filtros.getFolio());
		String truncatedInitDate = null;
		String truncatedFinalDate = null;
		String truncatedConclusionInitDate = null;
		String truncatedConclusionFinalDate = null;

		// SUBQUERIES

		StringBuffer bfrTramitePersonaFisica = new StringBuffer();
		bfrTramitePersonaFisica
				.append("select tpf.id.cveIdTramite from DitTramitePersonaFisica tpf ");
		bfrTramitePersonaFisica.append("join tpf.ditPersona pf ");
		bfrTramitePersonaFisica.append("where pf.rfc like '%"
				+ filtros.getRfc() + "%'");

		StringBuffer bfrTramitePersonaMoral = new StringBuffer();
		bfrTramitePersonaMoral
				.append("select tpm.id.cveIdTramite from DitTramitePersonaMoral tpm ");
		bfrTramitePersonaMoral.append("join tpm.ditPersonaMoral pm ");
		bfrTramitePersonaMoral.append("where pm.rfc like '%" + filtros.getRfc()
				+ "%'");

		StringBuffer bfrTramiteRPFisica = new StringBuffer();
		bfrTramiteRPFisica
				.append("select pso2.cveIdPatronSujetoObligado from DitPatronSujetoObligado pso2 ");
		bfrTramiteRPFisica.append("join pso2.ditPersonaFisica pf2 ");
		bfrTramiteRPFisica.append("where pf2.rfc like '% " + filtros.getRfc()
				+ "%'");

		StringBuffer bfrTramiteRPMoral = new StringBuffer();
		bfrTramiteRPMoral
				.append("select pso3.cveIdPatronSujetoObligado from DitPatronSujetoObligado pso3 ");
		bfrTramiteRPMoral.append("join pso3.ditPersonaMoral pm2 ");
		bfrTramiteRPMoral.append("where pm2.rfc like '%" + filtros.getRfc()
				+ "%'");

		StringBuffer bfrTramiteSujetoObligado = new StringBuffer();
		bfrTramiteSujetoObligado
				.append("select tso.cveIdTramite from DitTramitePatSujObligado tso ");
		bfrTramiteSujetoObligado
				.append("join tso.ditPatronSujetoObligado pso ");
		bfrTramiteSujetoObligado
				.append("where pso.cveIdPatronSujetoObligado in ( ");
		bfrTramiteSujetoObligado.append(bfrTramiteRPFisica.toString());
		bfrTramiteSujetoObligado
				.append(" ) or pso.cveIdPatronSujetoObligado in ( ");
		bfrTramiteSujetoObligado.append(bfrTramiteRPMoral.toString());
		bfrTramiteSujetoObligado.append(" ) ");

		StringBuffer bfrTramiteRP = new StringBuffer();
		bfrTramiteRP
				.append("select tso.cveIdTramite from DitTramitePatSujObligado tso ");
		bfrTramiteRP.append("join tso.ditPatronSujetoObligado pso ");
		bfrTramiteRP.append("join pso.ditPatronGenerals patronGeneral ");
		bfrTramiteRP.append("where patronGeneral.regPatron = :regPatronal ");
		if (filtros.getRp().length() > 8) {
			bfrTramiteRP
					.append("and pso.dicModalidad.numModalidad = :numModalidad ");
		}
		if (filtros.getRp().length() > 10) {
			bfrTramiteRP.append("and patronGeneral.digVer = :digVerificador ");
		}

		StringBuffer bfrTramiteFecPresentacion = new StringBuffer();
		bfrTramiteFecPresentacion
				.append("select t.cveIdTramite from DitTramite t where ");
		bfrTramiteFecPresentacion
				.append(" trunc( t.fecPresentacion ) between TO_DATE(:fechaInicioPresentacion ,'yyyy-MM-dd') and TO_DATE(:fechaFinPresentacion , 'yyyy-MM-dd') ");

		StringBuffer bfrTramiteFecConclusion = new StringBuffer();
		bfrTramiteFecConclusion
				.append("select t.cveIdTramite from DitTramite t where ");
		bfrTramiteFecConclusion
				.append(" trunc( t.fecConclusion ) between TO_DATE(:fechaInicioConclusion,'yyyy-MM-dd') and TO_DATE(:fechaFinConclusion, 'yyyy-MM-dd') ");

		boolean condicionPrevia = false;

		// QUERY BASE
		bfr.append(" Select DISTINCT solicitud from DitSolicitud solicitud ");
		bfr.append(" join solicitud.ditTramites ditTramites ");

		// if( !StringUtils.isBlank(filtros.getFolio()) ){
		// System.err.println("Ejecutando consulta con folio: "+filtros.getFolio());
		// bfr.append(" where  solicitud.refFolio like '%"+filtros.getFolio()+"%' ");
		// condicionPrevia=true;
		// }
		if (!StringUtils.isBlank(filtros.getRfc())
				&& StringUtils.isBlank(filtros.getRp())) {
			System.err.println("Ejecutando consulta por rfc: "
					+ filtros.getRfc());
			agregarCondicional(condicionPrevia, bfr);
			condicionPrevia = true;
			bfr.append(" ( ditTramites.cveIdTramite in ( ");
			bfr.append(bfrTramitePersonaFisica.toString());
			bfr.append(" ) or ditTramites.cveIdTramite in ( ");
			bfr.append(bfrTramitePersonaMoral.toString());
			bfr.append(" ) or ditTramites.cveIdTramite in ( ");
			bfr.append(bfrTramiteSujetoObligado.toString());
			bfr.append(" ) )");
		} else if (!StringUtils.isBlank(filtros.getRfc())
				&& !StringUtils.isBlank(filtros.getRp())) {
			agregarCondicional(condicionPrevia, bfr);
			condicionPrevia = true;
			bfr.append(" ( ditTramites.cveIdTramite in ( ");
			bfr.append(bfrTramitePersonaFisica.toString());
			bfr.append(" ) or ditTramites.cveIdTramite in ( ");
			bfr.append(bfrTramitePersonaMoral.toString());
			bfr.append(" ) or ditTramites.cveIdTramite in ( ");
			bfr.append(bfrTramiteRP.toString());
			bfr.append(" ) )");
		} else if (StringUtils.isBlank(filtros.getRfc())
				&& !StringUtils.isBlank(filtros.getRp())) {
			agregarCondicional(condicionPrevia, bfr);
			condicionPrevia = true;
			bfr.append(" ( ditTramites.cveIdTramite in ( ");
			bfr.append(bfrTramiteRP.toString());
			bfr.append(" ) )");
		}

		if (filtros.getFechaInicioPresentacion() != null
				&& filtros.getFechaFinPresentacion() != null) {
			// agregarCondicional(condicionPrevia, bfr);
			// condicionPrevia=true;
			// bfr.append(" ditTramites.cveIdTramite in ( ");
			// bfr.append(bfrTramiteFecPresentacion.toString());
			// bfr.append(" ) ");
			agregarCondicional(condicionPrevia, bfr);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			truncatedInitDate = sdf
					.format(filtros.getFechaInicioPresentacion());
			truncatedFinalDate = sdf.format(filtros.getFechaFinPresentacion());
			bfr.append(" trunc( solicitud.fecPresentacion ) between TO_DATE(:fechaInicioPresentacion ,'yyyy-MM-dd') and TO_DATE(:fechaFinPresentacion , 'yyyy-MM-dd') ");
			condicionPrevia = true;
		}

		if (filtros.getFechaInicioConclusion() != null
				&& filtros.getFechaFinConclusion() != null) {
			// agregarCondicional(condicionPrevia, bfr);
			// condicionPrevia=true;
			// bfr.append(" ditTramites.cveIdTramite in ( ");
			// bfr.append(bfrTramiteFecConclusion.toString());
			// bfr.append(" ) ");

			agregarCondicional(condicionPrevia, bfr);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			truncatedConclusionInitDate = sdf.format(filtros
					.getFechaInicioConclusion());
			truncatedConclusionFinalDate = sdf.format(filtros
					.getFechaFinConclusion());
			bfr.append(" trunc ( solicitud.fecConclusion ) between TO_DATE(:fechaInicioConclusion,'yyyy-MM-dd') and TO_DATE(:fechaFinConclusion,'yyyy-MM-dd') ");
			condicionPrevia = true;
		}

		if (filtros.getTramiteId() != null && filtros.getTramiteId() > 0) {
			agregarCondicional(condicionPrevia, bfr);
			bfr.append(" ditTramites.dicTipoTramite.cveIdTipoTramite = :idTipoTramite ");
			condicionPrevia = true;
		}

		if (filtros.getIdEstadoSolicitud() != null
				&& filtros.getIdEstadoSolicitud() > 0) {
			agregarCondicional(condicionPrevia, bfr);
			bfr.append(" solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud = :idEstadoSolicitud ");
			condicionPrevia = true;
		}

		if (filtros.getIdDelegacion() != null && filtros.getIdDelegacion() > 0) {
			agregarCondicional(condicionPrevia, bfr);
			bfr.append(" ditTramites.cveIdTramite IN ( ");
			bfr.append("		select tso.cveIdTramite from DitTramitePatSujObligado tso ");
			bfr.append("			join tso.ditPatronSujetoObligado pso ");
			bfr.append("			join pso.ditSubdelPatSujOblig ditSubdelPatSujOblig ");
			bfr.append("			join ditSubdelPatSujOblig.dicSubdelegacion dicSubdelegacion ");
			bfr.append("			join dicSubdelegacion.dicDelegacion dicDelegacion ");
			bfr.append("		where dicDelegacion.cveIdDelegacion = :idDelegacion");
			bfr.append("	)	");
			condicionPrevia = true;
		}

		// if(filtros.getIdSubdelegacion()!= null &&
		// filtros.getIdSubdelegacion() > 0 ){
		// agregarCondicional(condicionPrevia, bfr);
		// bfr.append(" solicitud.dicSubdelegacion.cveIdSubdelegacion = :idSubdelegacion");
		// condicionPrevia=true;
		// }

		// Se agrega para limitar los tipos de solicitud a mostrar
		agregarCondicional(condicionPrevia, bfr);
		bfr.append(" ( solicitud.dicTipoSolicitud.cveIdTipoSolicitud = ")
				.append(TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES
						.getValor().longValue());
		bfr.append(" or (solicitud.dicTipoSolicitud.cveIdTipoSolicitud = ")
				.append(TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION
						.getValor().longValue());
		if (filtros.getIdSubdelegacion() != null
				&& filtros.getIdSubdelegacion() > 0) {
			bfr.append(" and solicitud.dicSubdelegacion.cveIdSubdelegacion = :idSubdelegacion");
		}
		bfr.append(")");

		bfr.append(" or (solicitud.dicTipoSolicitud.cveIdTipoSolicitud = ")
				.append(TipoSolicitudEnum.ALTA_PATRONAL.getValor().longValue());
		if (filtros.getIdSubdelegacion() != null
				&& filtros.getIdSubdelegacion() > 0) {
			bfr.append(" and solicitud.dicSubdelegacion.cveIdSubdelegacion = :idSubdelegacion");
		}
		bfr.append(")");
		bfr.append(" or (solicitud.dicTipoSolicitud.cveIdTipoSolicitud = ")
				.append(TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO
						.getValor().longValue());
		if (filtros.getIdSubdelegacion() != null
				&& filtros.getIdSubdelegacion() > 0) {
			bfr.append(" and solicitud.dicSubdelegacion.cveIdSubdelegacion = :idSubdelegacion");
		}
		bfr.append(")");
		bfr.append(")");
		bfr.append(" order by solicitud.refFolio asc");

		Query query = this.em.createQuery(bfr.toString());
		Query queryTotal = this.em.createQuery(bfr.toString());

		if (!StringUtils.isBlank(filtros.getRp())) {
			if (filtros.getRp().length() >= 8) {
				queryTotal.setParameter("regPatronal", filtros.getRp()
						.substring(0, 8));
				query.setParameter("regPatronal",
						filtros.getRp().substring(0, 8));
			}
			if (filtros.getRp().length() > 8 && filtros.getRp().length() >= 10) {
				queryTotal.setParameter("numModalidad", filtros.getRp()
						.substring(8, 10));
				query.setParameter("numModalidad",
						filtros.getRp().substring(8, 10));
			}
			if (filtros.getRp().length() == 11) {
				queryTotal.setParameter("digVerificador", filtros.getRp()
						.substring(filtros.getRp().length() - 1));
				query.setParameter("digVerificador",
						filtros.getRp().substring(filtros.getRp().length() - 1));
			}

		}
		if (filtros.getFechaInicioPresentacion() != null
				&& filtros.getFechaFinPresentacion() != null) {
			queryTotal.setParameter("fechaInicioPresentacion",
					truncatedInitDate);
			query.setParameter("fechaInicioPresentacion", truncatedInitDate);

			queryTotal.setParameter("fechaFinPresentacion", truncatedFinalDate);
			query.setParameter("fechaFinPresentacion", truncatedFinalDate);
		}

		if (filtros.getFechaInicioConclusion() != null
				&& filtros.getFechaFinConclusion() != null) {
			queryTotal.setParameter("fechaInicioConclusion",
					truncatedConclusionInitDate);
			query.setParameter("fechaInicioConclusion",
					truncatedConclusionInitDate);

			queryTotal.setParameter("fechaFinConclusion",
					truncatedConclusionFinalDate);
			query.setParameter("fechaFinConclusion",
					truncatedConclusionFinalDate);
		}

		if (filtros.getTramiteId() != null && filtros.getTramiteId() > 0) {
			queryTotal.setParameter("idTipoTramite", filtros.getTramiteId());
			query.setParameter("idTipoTramite", filtros.getTramiteId());
		}

		if (filtros.getIdEstadoSolicitud() != null
				&& filtros.getIdEstadoSolicitud() > 0) {
			queryTotal.setParameter("idEstadoSolicitud",
					filtros.getIdEstadoSolicitud());
			query.setParameter("idEstadoSolicitud",
					filtros.getIdEstadoSolicitud());
		}

		if (filtros.getIdDelegacion() != null && filtros.getIdDelegacion() > 0) {
			queryTotal.setParameter("idDelegacion", filtros.getIdDelegacion());
			query.setParameter("idDelegacion", filtros.getIdDelegacion());
		}

		if (filtros.getIdSubdelegacion() != null
				&& filtros.getIdSubdelegacion() > 0) {
			queryTotal.setParameter("idSubdelegacion",
					filtros.getIdSubdelegacion());
			query.setParameter("idSubdelegacion", filtros.getIdSubdelegacion());
		}

		List<DitSolicitud> entitiesTotales = queryTotal.getResultList();
		Integer totalresult = entitiesTotales.size();

		query.setFirstResult(input.getiDisplayStart());
		query.setMaxResults(input.getiDisplayLength());

		List<DitSolicitud> entitiesADesplegar = query.getResultList();

		List<Solicitud> solicitudes = new ArrayList<Solicitud>();
		System.err.println("solicitudes por persona encontradas: "
				+ totalresult);
		if (entitiesADesplegar != null) {
			System.err.println("solicitudes por persona encontradas: "
					+ entitiesADesplegar.size());
			solicitudes = new ArrayList<Solicitud>();
			for (DitSolicitud entity : entitiesADesplegar) {
				Solicitud nuevaSolicitud = solicitudUtilityLocal
						.convertEntityToModelSolicitud(entity);
				SujetoObligado sujetoObligadoSolicitud = solicitudUtilityLocal
						.convertirEntityToModelSujetoObligadoSolicitud(entity);
				nuevaSolicitud.setSujetoObligado(sujetoObligadoSolicitud);
				solicitudes.add(nuevaSolicitud);
			}
		}

		DatosSalidaPaginador<Solicitud> output = new DatosSalidaPaginador<Solicitud>();
		output.setAaData(solicitudes);
		output.setiTotalRecords(totalresult);
		output.setiTotalDisplayRecords(totalresult);

		return output;
	}

	private void agregarCondicional(boolean condicionPrevia, StringBuffer bfr) {
		if (condicionPrevia) {
			bfr.append(" and ");
		} else {
			bfr.append(" where ");
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public DatosSalidaPaginador<Solicitud> listarSolicitudesPorFiltroParaPatron(
			DatosEntradaPaginador<Solicitud> input, FiltroSolicitud filtros) {
		StringBuffer bfr = new StringBuffer();
		System.err.println("Ejecutando consulta con folio: "
				+ filtros.getFolio());
		String truncatedInitDate = null;
		String truncatedFinalDate = null;
		String truncatedConclusionInitDate = null;
		String truncatedConclusionFinalDate = null;

		// SUBQUERIES

		StringBuffer bfrTramitePersonaFisica = new StringBuffer();
		bfrTramitePersonaFisica
				.append("select tpf.id.cveIdTramite from DitTramitePersonaFisica tpf ");
		bfrTramitePersonaFisica.append("join tpf.ditPersona pf ");
		bfrTramitePersonaFisica.append("where pf.rfc like '%"
				+ filtros.getRfc() + "%'");

		StringBuffer bfrTramitePersonaMoral = new StringBuffer();
		bfrTramitePersonaMoral
				.append("select tpm.id.cveIdTramite from DitTramitePersonaMoral tpm ");
		bfrTramitePersonaMoral.append("join tpm.ditPersonaMoral pm ");
		bfrTramitePersonaMoral.append("where pm.rfc like '%" + filtros.getRfc()
				+ "%'");

		StringBuffer bfrTramiteRPFisica = new StringBuffer();
		bfrTramiteRPFisica
				.append("select pso2.cveIdPatronSujetoObligado from DitPatronSujetoObligado pso2 ");
		bfrTramiteRPFisica.append("join pso2.ditPersonaFisica pf2 ");
		bfrTramiteRPFisica.append("where pf2.rfc like '% " + filtros.getRfc()
				+ "%'");

		StringBuffer bfrTramiteRPMoral = new StringBuffer();
		bfrTramiteRPMoral
				.append("select pso3.cveIdPatronSujetoObligado from DitPatronSujetoObligado pso3 ");
		bfrTramiteRPMoral.append("join pso3.ditPersonaMoral pm2 ");
		bfrTramiteRPMoral.append("where pm2.rfc like '%" + filtros.getRfc()
				+ "%'");

		StringBuffer bfrTramiteSujetoObligado = new StringBuffer();
		bfrTramiteSujetoObligado
				.append("select tso.cveIdTramite from DitTramitePatSujObligado tso ");
		bfrTramiteSujetoObligado
				.append("join tso.ditPatronSujetoObligado pso ");
		bfrTramiteSujetoObligado
				.append("where pso.cveIdPatronSujetoObligado in ( ");
		bfrTramiteSujetoObligado.append(bfrTramiteRPFisica.toString());
		bfrTramiteSujetoObligado
				.append(" ) or pso.cveIdPatronSujetoObligado in ( ");
		bfrTramiteSujetoObligado.append(bfrTramiteRPMoral.toString());
		bfrTramiteSujetoObligado.append(" ) ");

		StringBuffer bfrTramiteRP = new StringBuffer();
		bfrTramiteRP
				.append("select tso.cveIdTramite from DitTramitePatSujObligado tso ");
		bfrTramiteRP.append("join tso.ditPatronSujetoObligado pso ");
		bfrTramiteRP.append("join pso.ditPatronGenerals patronGeneral ");
		bfrTramiteRP.append("where patronGeneral.regPatron = :regPatronal ");
		if (filtros.getRp().length() > 8) {
			bfrTramiteRP
					.append("and pso.dicModalidad.numModalidad = :numModalidad ");
		}
		if (filtros.getRp().length() > 10) {
			bfrTramiteRP.append("and patronGeneral.digVer = :digVerificador ");
		}

		boolean condicionPrevia = false;

		// QUERY BASE
		bfr.append(" Select DISTINCT solicitud from DitSolicitud solicitud ");
		bfr.append(" join solicitud.ditTramites ditTramites ");

		// if( !StringUtils.isBlank(filtros.getFolio()) ){
		// System.err.println("Ejecutando consulta con folio: "+filtros.getFolio());
		// bfr.append(" where  solicitud.refFolio like '%"+filtros.getFolio()+"%' ");
		// condicionPrevia=true;
		// }
		if (!StringUtils.isBlank(filtros.getRfc())
				&& StringUtils.isBlank(filtros.getRp())) {
			System.err.println("Ejecutando consulta por rfc: "
					+ filtros.getRfc());
			agregarCondicional(condicionPrevia, bfr);
			condicionPrevia = true;
			bfr.append(" ( ditTramites.cveIdTramite in ( ");
			bfr.append(bfrTramitePersonaFisica.toString());
			bfr.append(" ) or ditTramites.cveIdTramite in ( ");
			bfr.append(bfrTramitePersonaMoral.toString());
			bfr.append(" ) or ditTramites.cveIdTramite in ( ");
			bfr.append(bfrTramiteSujetoObligado.toString());
			bfr.append(" ) )");
		} else if (!StringUtils.isBlank(filtros.getRfc())
				&& !StringUtils.isBlank(filtros.getRp())) {
			agregarCondicional(condicionPrevia, bfr);
			condicionPrevia = true;
			bfr.append(" ( ditTramites.cveIdTramite in ( ");
			bfr.append(bfrTramitePersonaFisica.toString());
			bfr.append(" ) or ditTramites.cveIdTramite in ( ");
			bfr.append(bfrTramitePersonaMoral.toString());
			bfr.append(" ) or ditTramites.cveIdTramite in ( ");
			bfr.append(bfrTramiteRP.toString());
			bfr.append(" ) )");
		} else if (StringUtils.isBlank(filtros.getRfc())
				&& !StringUtils.isBlank(filtros.getRp())) {
			agregarCondicional(condicionPrevia, bfr);
			condicionPrevia = true;
			bfr.append(" ( ditTramites.cveIdTramite in ( ");
			bfr.append(bfrTramiteRP.toString());
			bfr.append(" ) )");
		}

		if (filtros.getFechaInicioPresentacion() != null
				&& filtros.getFechaFinPresentacion() != null) {
			agregarCondicional(condicionPrevia, bfr);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			truncatedInitDate = sdf
					.format(filtros.getFechaInicioPresentacion());
			truncatedFinalDate = sdf.format(filtros.getFechaFinPresentacion());
			bfr.append(" trunc( solicitud.fecPresentacion ) between TO_DATE(:fechaInicioPresentacion ,'yyyy-MM-dd') and TO_DATE(:fechaFinPresentacion , 'yyyy-MM-dd') ");
			condicionPrevia = true;
		}

		if (filtros.getFechaInicioConclusion() != null
				&& filtros.getFechaFinConclusion() != null) {
			agregarCondicional(condicionPrevia, bfr);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			truncatedConclusionInitDate = sdf.format(filtros
					.getFechaInicioConclusion());
			truncatedConclusionFinalDate = sdf.format(filtros
					.getFechaFinConclusion());
			bfr.append(" trunc ( solicitud.fecConclusion ) between TO_DATE(:fechaInicioConclusion,'yyyy-MM-dd') and TO_DATE(:fechaFinConclusion,'yyyy-MM-dd') ");
			condicionPrevia = true;
		}

		if (filtros.getTramiteId() != null && filtros.getTramiteId() > 0) {
			agregarCondicional(condicionPrevia, bfr);
			bfr.append(" ditTramites.dicTipoTramite.cveIdTipoTramite = :idTipoTramite ");
			condicionPrevia = true;
		}

		if (filtros.getIdEstadoSolicitud() != null
				&& filtros.getIdEstadoSolicitud() > 0) {
			agregarCondicional(condicionPrevia, bfr);
			bfr.append(" solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud = :idEstadoSolicitud ");
			condicionPrevia = true;
		} else if (filtros.getIdEstadoSolicitud() == null
				|| (filtros.getIdEstadoSolicitud() != null && filtros
						.getIdEstadoSolicitud() <= 0)) {
			agregarCondicional(condicionPrevia, bfr);
			bfr.append(" solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud IN :idEstadoSolicitud ");
			condicionPrevia = true;
		}

		if (filtros.getIdDelegacion() != null && filtros.getIdDelegacion() > 0) {
			agregarCondicional(condicionPrevia, bfr);
			bfr.append(" ditTramites.cveIdTramite IN ( ");
			bfr.append("		select tso.cveIdTramite from DitTramitePatSujObligado tso ");
			bfr.append("			join tso.ditPatronSujetoObligado pso ");
			bfr.append("			join pso.ditSubdelPatSujOblig ditSubdelPatSujOblig ");
			bfr.append("			join ditSubdelPatSujOblig.dicSubdelegacion dicSubdelegacion ");
			bfr.append("			join dicSubdelegacion.dicDelegacion dicDelegacion ");
			bfr.append("		where dicDelegacion.cveIdDelegacion = :idDelegacion");
			bfr.append("	)	");
			condicionPrevia = true;
		}

		if (filtros.getIdSubdelegacion() != null
				&& filtros.getIdSubdelegacion() > 0) {
			agregarCondicional(condicionPrevia, bfr);
			bfr.append(" solicitud.dicSubdelegacion.cveIdSubdelegacion = :idSubdelegacion");
			condicionPrevia = true;
			// bfr.append(" ditTramites.cveIdTramite IN ( ");
			// bfr.append("		select tso.cveIdTramite from DitTramitePatSujObligado tso ");
			// bfr.append("			join tso.ditPatronSujetoObligado pso ");
			// bfr.append("			join pso.ditSubdelPatSujOblig ditSubdelPatSujOblig ");
			// bfr.append("			join ditSubdelPatSujOblig.dicSubdelegacion dicSubdelegacion ");
			// bfr.append("		where dicSubdelegacion.cveIdSubdelegacion = :idSubdelegacion");
			// bfr.append("	)	");
		}

		// Se agrega para limitar los tipos de solicitud a mostrar
		agregarCondicional(condicionPrevia, bfr);
		bfr.append(" ( solicitud.dicTipoSolicitud.cveIdTipoSolicitud = ")
				.append(TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES
						.getValor().longValue());
		bfr.append(" or solicitud.dicTipoSolicitud.cveIdTipoSolicitud = ")
				.append(TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION
						.getValor().longValue());
		bfr.append(" or solicitud.dicTipoSolicitud.cveIdTipoSolicitud = ")
				.append(TipoSolicitudEnum.ALTA_PATRONAL.getValor().longValue());
		bfr.append(" or solicitud.dicTipoSolicitud.cveIdTipoSolicitud = ")
				.append(TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO
						.getValor().longValue()).append(" ) ");
		bfr.append(" order by solicitud.refFolio asc");

		Query query = this.em.createQuery(bfr.toString());
		Query queryTotal = this.em.createQuery(bfr.toString());

		if (!StringUtils.isBlank(filtros.getRp())) {
			if (filtros.getRp().length() >= 8) {
				queryTotal.setParameter("regPatronal", filtros.getRp()
						.substring(0, 8));
				query.setParameter("regPatronal",
						filtros.getRp().substring(0, 8));
			}
			if (filtros.getRp().length() > 8 && filtros.getRp().length() >= 10) {
				queryTotal.setParameter("numModalidad", filtros.getRp()
						.substring(8, 10));
				query.setParameter("numModalidad",
						filtros.getRp().substring(8, 10));
			}
			if (filtros.getRp().length() == 11) {
				queryTotal.setParameter("digVerificador", filtros.getRp()
						.substring(filtros.getRp().length() - 1));
				query.setParameter("digVerificador",
						filtros.getRp().substring(filtros.getRp().length() - 1));
			}

		}
		if (filtros.getFechaInicioPresentacion() != null
				&& filtros.getFechaFinPresentacion() != null) {
			queryTotal.setParameter("fechaInicioPresentacion",
					truncatedInitDate);
			query.setParameter("fechaInicioPresentacion", truncatedInitDate);

			queryTotal.setParameter("fechaFinPresentacion", truncatedFinalDate);
			query.setParameter("fechaFinPresentacion", truncatedFinalDate);
		}

		if (filtros.getFechaInicioConclusion() != null
				&& filtros.getFechaFinConclusion() != null) {
			queryTotal.setParameter("fechaInicioConclusion",
					truncatedConclusionInitDate);
			query.setParameter("fechaInicioConclusion",
					truncatedConclusionInitDate);

			queryTotal.setParameter("fechaFinConclusion",
					truncatedConclusionFinalDate);
			query.setParameter("fechaFinConclusion",
					truncatedConclusionFinalDate);
		}

		if (filtros.getTramiteId() != null && filtros.getTramiteId() > 0) {
			queryTotal.setParameter("idTipoTramite", filtros.getTramiteId());
			query.setParameter("idTipoTramite", filtros.getTramiteId());
		}

		if (filtros.getIdEstadoSolicitud() != null
				&& filtros.getIdEstadoSolicitud() > 0) {
			queryTotal.setParameter("idEstadoSolicitud",
					filtros.getIdEstadoSolicitud());
			query.setParameter("idEstadoSolicitud",
					filtros.getIdEstadoSolicitud());
		} else if (filtros.getIdEstadoSolicitud() == null
				|| (filtros.getIdEstadoSolicitud() != null && filtros
						.getIdEstadoSolicitud() <= 0)) {
			List<Long> idsEstadoValidos = new ArrayList<Long>();
			idsEstadoValidos.add(EstadoSolicitudEnum.ATENDIDA.getCodigo()
					.longValue());
			idsEstadoValidos.add(EstadoSolicitudEnum.CANCELADA.getCodigo()
					.longValue());
			idsEstadoValidos.add(EstadoSolicitudEnum.RECHAZADA.getCodigo()
					.longValue());
			queryTotal.setParameter("idEstadoSolicitud", idsEstadoValidos);
			query.setParameter("idEstadoSolicitud", idsEstadoValidos);
		}

		if (filtros.getIdDelegacion() != null && filtros.getIdDelegacion() > 0) {
			queryTotal.setParameter("idDelegacion", filtros.getIdDelegacion());
			query.setParameter("idDelegacion", filtros.getIdDelegacion());
		}

		if (filtros.getIdSubdelegacion() != null
				&& filtros.getIdSubdelegacion() > 0) {
			queryTotal.setParameter("idSubdelegacion",
					filtros.getIdSubdelegacion());
			query.setParameter("idSubdelegacion", filtros.getIdSubdelegacion());
		}

		List<DitSolicitud> entitiesTotales = queryTotal.getResultList();
		Integer totalresult = entitiesTotales.size();

		query.setFirstResult(input.getiDisplayStart());
		query.setMaxResults(input.getiDisplayLength());

		List<DitSolicitud> entitiesADesplegar = query.getResultList();

		List<Solicitud> solicitudes = new ArrayList<Solicitud>();
		System.err.println("solicitudes por persona encontradas: "
				+ totalresult);
		if (entitiesADesplegar != null) {
			System.err.println("solicitudes por persona encontradas: "
					+ entitiesADesplegar.size());
			solicitudes = new ArrayList<Solicitud>();
			for (DitSolicitud entity : entitiesADesplegar) {
				Solicitud nuevaSolicitud = solicitudUtilityLocal
						.convertEntityToModelSolicitud(entity);
				SujetoObligado sujetoObligadoSolicitud = solicitudUtilityLocal
						.convertirEntityToModelSujetoObligadoSolicitud(entity);
				nuevaSolicitud.setSujetoObligado(sujetoObligadoSolicitud);
				solicitudes.add(nuevaSolicitud);
			}
		}

		DatosSalidaPaginador<Solicitud> output = new DatosSalidaPaginador<Solicitud>();
		output.setAaData(solicitudes);
		output.setiTotalRecords(totalresult);
		output.setiTotalDisplayRecords(totalresult);

		return output;
	}

	@Override
	public Solicitud consultarDetalle(Long idSolicitud) {
		DitSolicitud entity = em.find(DitSolicitud.class, idSolicitud);
		DicEstadoSolicitud estado = em.find(DicEstadoSolicitud.class, entity
				.getDicEstadoSolicitud().getCveIdEstadoSolicitud());
		System.err.println("Estado: " + estado.getDesEstadoSolicitud());
		entity.setDicEstadoSolicitud(estado);
		System.err.println("Checando Estado de la solicitud consultarDetalle ");
		Solicitud solicitudEncontrada = solicitudUtilityLocal
				.convertEntityToModelSolicitud(entity);
		System.err.println("Estado de la solicitud consultarDetalle 1: "
				+ solicitudEncontrada.getEstadoSolicitud().getDescripcion());
		SujetoObligado sujetoObligadoSolicitud = solicitudUtilityLocal
				.convertirEntityToModelSujetoObligadoSolicitud(entity);
		System.err.println("sujetoObligadoSolicitud: "
				+ sujetoObligadoSolicitud);
		solicitudEncontrada.setSujetoObligado(sujetoObligadoSolicitud);
		System.err.println("Estado de la solicitud consultarDetalle 2: "
				+ solicitudEncontrada.getEstadoSolicitud().getDescripcion());
		return solicitudEncontrada;
	}

	@Override
	public Solicitud consultarDetalle(String folio)
			throws SolicitudNoEncontradaException {

		StringBuffer bfr = new StringBuffer();
		bfr.append(" select solicitud from DitSolicitud solicitud ");
		bfr.append(" where solicitud.refFolio = :folio ");

		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("folio", folio);
		
		Solicitud solicitudEncontrada = null;
		
		try {
			DitSolicitud entity = (DitSolicitud) query.getSingleResult();
			
			this.log.debug("Estado: "
					+ entity.getDicEstadoSolicitud().getDesEstadoSolicitud());

			solicitudEncontrada = solicitudUtilityLocal
					.convertEntityToModelSolicitud(entity);

			this.log.debug("Estado de la solicitud consultarDetalle 1: "
					+ solicitudEncontrada.getEstadoSolicitud().getDescripcion());

			SujetoObligado sujetoObligadoSolicitud = solicitudUtilityLocal
					.convertirEntityToModelSujetoObligadoSolicitud(entity);

			this.log.debug("sujetoObligadoSolicitud: " + sujetoObligadoSolicitud);

			solicitudEncontrada.setSujetoObligado(sujetoObligadoSolicitud);

			this.log.debug("Estado de la solicitud consultarDetalle 2: "
					+ solicitudEncontrada.getEstadoSolicitud().getDescripcion());
		} catch (NoResultException e) {
			this.log.warn("La solicitud con folio " + folio + " no fue encontrada");
			throw new SolicitudNoEncontradaException(folio);
		}

		return solicitudEncontrada;
	}

	@Override
	public Solicitud obtenerSolicitudPendienteDeAsignarPorPatron(
			Long idPatronSujetoObligado, TipoSolicitudEnum tipoSolicitud) {
		this.log.debug(" Obteniendo solicitud pendiente de asignar del Patron :::"
				+ idPatronSujetoObligado);

		StringBuffer bfr = new StringBuffer();
		bfr.append(" Select solicitud from DitTramitePatSujObligado tso ");
		bfr.append(" join tso.ditTramite as tramite ");
		bfr.append(" join tramite.ditSolicitud as solicitud ");
		bfr.append(" where tso.ditPatronSujetoObligado.cveIdPatronSujetoObligado = :idSujetoObligado and ");
		bfr.append(" solicitud.dicTipoSolicitud.cveIdTipoSolicitud = :idTipoSolicitud and ");
		bfr.append(" solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud IN :idsEstadoSolicitud ");

		List<Long> idsEstadoSolicitud = new ArrayList<Long>();
		idsEstadoSolicitud.add(EstadoSolicitudEnum.PARA_PROCESAR_BACKOFFICE
				.getCodigo().longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.PRESENTARSE_EN_VENTANILLA
				.getCodigo().longValue());

		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("idSujetoObligado", idPatronSujetoObligado);
		query.setParameter("idTipoSolicitud", tipoSolicitud.getValor()
				.longValue());
		query.setParameter("idsEstadoSolicitud", idsEstadoSolicitud);

		Object entity = null;
		try {
			entity = query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
		Solicitud solicitud = null;

		if (entity != null) {
			DitSolicitud ditSolicitud = (DitSolicitud) entity;
			solicitud = solicitudUtilityLocal
					.convertEntityToModelSolicitud(ditSolicitud);
		}

		return solicitud;
	}

	@Override
	public Solicitud obtenerSolicitudPendienteDeAsignarPorPersona(
			Long idPersona, TipoPersonaFiscal tipo,
			TipoSolicitudEnum tipoSolicitud) {
		this.log.debug(" Obteniendo solicitud pendiente de asignar de la persona :::"
				+ idPersona);

		StringBuffer bfr = new StringBuffer();

		if (tipo.equals(TipoPersonaFiscal.FISICA)) {
			bfr.append(" Select solicitud from DitTramitePersonaFisica tpf ");
			bfr.append(" join tpf.ditTramite as tramite ");
			bfr.append(" join tramite.ditSolicitud as solicitud ");
			bfr.append(" where tpf.id.cveIdPersona = :idPersona and ");
			bfr.append(" solicitud.dicTipoSolicitud.cveIdTipoSolicitud = :idTipoSolicitud and ");
			bfr.append(" solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud IN :idsEstadoSolicitud ");
		} else {
			bfr.append(" Select solicitud from DitTramitePersonaMoral tpm ");
			bfr.append(" join tpm.ditTramite as tramite ");
			bfr.append(" join tramite.ditSolicitud as solicitud ");
			bfr.append(" where tpm.id.cveIdPersonaMoral = :idPersona and ");
			bfr.append(" solicitud.dicTipoSolicitud.cveIdTipoSolicitud = :idTipoSolicitud and ");
			bfr.append(" solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud IN :idsEstadoSolicitud ");

		}

		List<Long> idsEstadoSolicitud = new ArrayList<Long>();
		idsEstadoSolicitud.add(EstadoSolicitudEnum.PARA_PROCESAR_BACKOFFICE
				.getCodigo().longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.PRESENTARSE_EN_VENTANILLA
				.getCodigo().longValue());

		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("idPersona", idPersona);
		query.setParameter("idTipoSolicitud", tipoSolicitud.getValor()
				.longValue());
		query.setParameter("idsEstadoSolicitud", idsEstadoSolicitud);

		Object entity = null;
		try {
			entity = query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
		Solicitud solicitud = null;
		if (entity != null) {
			DitSolicitud ditSolicitud = (DitSolicitud) entity;
			solicitud = solicitudUtilityLocal
					.convertEntityToModelSolicitud(ditSolicitud);
		}

		return solicitud;

	}

	@Override
	public Solicitud obtenerSolicitudPendientePorSujetoObligado(
			Long idPatronSujetoObligado, TipoSolicitudEnum tipoSolicitud) {
		this.log.debug(" Obteniendo solicitud en proceso del Patron :::"
				+ idPatronSujetoObligado);

		StringBuffer bfr = new StringBuffer();
		bfr.append(" Select solicitud from DitTramitePatSujObligado tso ");
		bfr.append(" join tso.ditTramite as tramite ");
		bfr.append(" join tramite.ditSolicitud as solicitud ");
		bfr.append(" where tso.ditPatronSujetoObligado.cveIdPatronSujetoObligado = :idSujetoObligado and ");
		bfr.append(" solicitud.dicTipoSolicitud.cveIdTipoSolicitud = :idTipoSolicitud and ");
		bfr.append(" solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud IN :idsEstadoSolicitud ");

		List<Long> idsEstadoSolicitud = new ArrayList<Long>();
		idsEstadoSolicitud.add(EstadoSolicitudEnum.PARA_PROCESAR_BACKOFFICE
				.getCodigo().longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.PRESENTARSE_EN_VENTANILLA
				.getCodigo().longValue());

		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("idSujetoObligado", idPatronSujetoObligado);
		query.setParameter("idTipoSolicitud", tipoSolicitud.getValor()
				.longValue());
		query.setParameter("idsEstadoSolicitud", idsEstadoSolicitud);

		Object entity = null;
		try {
			entity = query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
		Solicitud solicitud = null;

		if (entity != null) {
			DitSolicitud ditSolicitud = (DitSolicitud) entity;
			solicitud = solicitudUtilityLocal
					.convertEntityToModelSolicitud(ditSolicitud);
		}

		return solicitud;

	}

	@Override
	public Solicitud obtenerSolicitudPendientePorPersona(Long idPersona,
			TipoPersonaFiscal tipo, TipoSolicitudEnum tipoSolicitud) {
		this.log.debug(" Obteniendo solicitud en proceso de la persona :::"
				+ idPersona);

		StringBuffer bfr = new StringBuffer();

		if (tipo.equals(TipoPersonaFiscal.FISICA)) {
			bfr.append(" Select solicitud from DitTramitePersonaFisica tpf ");
			bfr.append(" join tpf.ditTramite as tramite ");
			bfr.append(" join tramite.ditSolicitud as solicitud ");
			bfr.append(" where tpf.id.cveIdPersona = :idPersona and ");
			bfr.append(" solicitud.dicTipoSolicitud.cveIdTipoSolicitud = :idTipoSolicitud and ");
			bfr.append(" solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud IN :idsEstadoSolicitud ");
		} else {
			bfr.append(" Select solicitud from DitTramitePersonaMoral tpm ");
			bfr.append(" join tpm.ditTramite as tramite ");
			bfr.append(" join tramite.ditSolicitud as solicitud ");
			bfr.append(" where tpm.id.cveIdPersonaMoral = :idPersona and ");
			bfr.append(" solicitud.dicTipoSolicitud.cveIdTipoSolicitud = :idTipoSolicitud and ");
			bfr.append(" solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud IN :idsEstadoSolicitud ");

		}

		List<Long> idsEstadoSolicitud = new ArrayList<Long>();
		idsEstadoSolicitud.add(EstadoSolicitudEnum.PARA_PROCESAR_BACKOFFICE
				.getCodigo().longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.PRESENTARSE_EN_VENTANILLA
				.getCodigo().longValue());

		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("idPersona", idPersona);
		query.setParameter("idTipoSolicitud", tipoSolicitud.getValor()
				.longValue());
		query.setParameter("idsEstadoSolicitud", idsEstadoSolicitud);

		Object entity = null;
		try {
			entity = query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
		Solicitud solicitud = null;
		if (entity != null) {
			DitSolicitud ditSolicitud = (DitSolicitud) entity;
			solicitud = solicitudUtilityLocal
					.convertEntityToModelSolicitud(ditSolicitud);
		}

		return solicitud;
	}

	@Override
	public void actualizarDocumentos(Solicitud solicitud) {
		Criteria criteria = this.getSession().createCriteria(
				DitSolicitudDocumento.class);
		criteria.add(Restrictions.eq("cveIdSolicitud",
				solicitud.getSolicitudId()));
		DitSolicitudDocumento solicitudDocumento = (DitSolicitudDocumento) criteria
				.uniqueResult();
		if (solicitudDocumento == null) {
			solicitudDocumento = new DitSolicitudDocumento();
			solicitudDocumento.setCveIdSolicitud(solicitud.getSolicitudId());
			solicitudDocumento.setRefComprobanteTramite(solicitud
					.getDocumentoComprobante());
			solicitudDocumento.setRefAcuseRecibo(solicitud.getDocumentoAcuse());
			solicitudDocumento.setFecRegistroAlta(Calendar.getInstance()
					.getTime());
			solicitudDocumento.setFecRegistroActualizado(Calendar.getInstance()
					.getTime());
			this.getSession().persist(solicitudDocumento);
		} else {
			solicitudDocumento.setFecRegistroActualizado(Calendar.getInstance()
					.getTime());
			if (solicitud.getDocumentoComprobante() != null)
				solicitudDocumento.setRefComprobanteTramite(solicitud
						.getDocumentoComprobante());
			if (solicitud.getDocumentoAcuse() != null)
				solicitudDocumento.setRefAcuseRecibo(solicitud
						.getDocumentoAcuse());
			this.getSession().update(solicitudDocumento);
		}
	}

	@Override
	public Solicitud consultarSolicitudPorFolio(String folio) {

		StringBuffer bfr = new StringBuffer();

		bfr.append(" Select solicitud from DitSolicitud solicitud ");
		bfr.append(" where solicitud.refFolio = :folio ");

		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("folio", folio);

		DitSolicitud entity = (DitSolicitud) query.getSingleResult();

		Solicitud solicitud = null;

		if (entity != null) {
			DitSolicitud ditSolicitud = (DitSolicitud) entity;
			solicitud = solicitudUtilityLocal
					.convertEntityToModelSolicitud(ditSolicitud);
		}

		return solicitud;
	}

	@Override
	public void actualizarFechaConclusionDeTramites(Long idSolicitud,
			Date fechaConclusion) {
		DitSolicitud sol = (DitSolicitud) this.getSession().load(
				DitSolicitud.class, idSolicitud);
		for (DitTramite ditTramite : sol.getDitTramites()) {
			ditTramite.setFecConclusion(fechaConclusion);
			ditTramite.setFecRegistroActualizado(fechaConclusion);
			this.getSession().update(ditTramite);
		}
		sol.setFecRegistroActualizado(fechaConclusion);
		this.getSession().update(sol);
	}

	@Override
	public TipoTramite consultarDetalleTipoTramite(Long codigo) {
		DicTipoTramite entity = (DicTipoTramite) this.getSession().load(
				DicTipoTramite.class, codigo);
		return tramiteUtilityLocal.convertirEntityToModelTipoTramite(entity);
	}

	@Override
	public Solicitud publicarDocumentos(Solicitud solicitud) {
		DitSolicitudDocumento documentos = em.find(DitSolicitudDocumento.class,
				solicitud.getSolicitudId());
		if (documentos != null) {
			solicitud.setDocumentoAcuse(documentos.getRefAcuseRecibo());
			solicitud.setDocumentoComprobante(documentos
					.getRefComprobanteTramite());
		}
		return solicitud;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Solicitud obtenerSolicitudAnteriorPorTipo(Solicitud solicitud) {
		StringBuffer bfr = new StringBuffer();

		bfr.append(" Select solicitud from DitTramitePatSujObligado tso ");
		bfr.append(" join tso.ditTramite as tramite ");
		bfr.append(" join tramite.ditSolicitud as solicitud ");
		bfr.append(" where solicitud.cveIdSolicitud < :idSolicitud and ");
		bfr.append(" solicitud.dicTipoSolicitud.cveIdTipoSolicitud = :idTipoSolicitud and ");
		bfr.append(" solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud IN :idsEstadoSolicitud and ");
		bfr.append(" tso.ditPatronSujetoObligado.cveIdPatronSujetoObligado = :idSujetoObligado ");
		bfr.append(" order by solicitud.cveIdSolicitud desc ");

		List<Long> idsEstadoSolicitud = new ArrayList<Long>();
		idsEstadoSolicitud.add(EstadoSolicitudEnum.ATENDIDA.getCodigo()
				.longValue());

		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("idSolicitud", solicitud.getSolicitudId());
		query.setParameter("idTipoSolicitud", solicitud.getTipoSolicitud()
				.getIdTipoSolicitud());
		query.setParameter("idsEstadoSolicitud", idsEstadoSolicitud);
		query.setParameter("idSujetoObligado", solicitud.getSujetoObligado()
				.getCveIdSujetoObligado());

		List<DitSolicitud> entities = null;
		try {
			entities = query.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
		Solicitud solicitudAnterior = null;

		if (entities != null && entities.size() > 0) {
			DitSolicitud ditSolicitud = entities.get(0);
			solicitudAnterior = solicitudUtilityLocal
					.convertEntityToModelSolicitud(ditSolicitud);
		}

		return solicitudAnterior;
	}

	@Override
	public void actualizarDatosSolicitud(Solicitud solicitud) {
		DitSolicitud ditSolicitud = em.find(DitSolicitud.class,
				solicitud.getSolicitudId());
		if (solicitud.getRazonCancelacion() != null
				&& solicitud.getRazonCancelacion().getIdRazonCancelacion() != null) {
			DicRazonCancelacion razonCancelacion = em.find(
					DicRazonCancelacion.class, solicitud.getRazonCancelacion()
							.getIdRazonCancelacion());
			ditSolicitud.setDicRazonCancelacion(razonCancelacion);
		}
		ditSolicitud.setFecPresentacion(solicitud.getFechaPresentacion());
		ditSolicitud.setFecConclusion(solicitud.getFechaConclusion());
		ditSolicitud
				.setFecRegistroActualizado(Calendar.getInstance().getTime());
		if (solicitud.getSolicitante() != null) {
			System.err.println("Se agrega rechazado por: "
					+ solicitud.getSolicitante().getUsuario());
			ditSolicitud.setRefObservacion(solicitud.getSolicitante()
					.getUsuario());
		}else if(solicitud.getObservacion()!=null){
			ditSolicitud.setRefObservacion(solicitud.getObservacion());
		}
			
		if (solicitud.getSubdelegacion() != null && solicitud.getSubdelegacion().getId() != null ) {
			DicSubdelegacion dicSubdelegacion  = em.find(DicSubdelegacion.class, solicitud.getSubdelegacion().getId());
			ditSolicitud.setDicSubdelegacion(dicSubdelegacion);
		}
		
		if (solicitud.getOrigenSolicitud() != null && solicitud.getOrigenSolicitud().getIdOrigenSolicitud() != null) {
			DicOrigenSolicitud origen = em.find(DicOrigenSolicitud.class, solicitud.getOrigenSolicitud().getIdOrigenSolicitud());
			ditSolicitud.setDicOrigenSolicitud(origen);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Solicitud> listarSolicitudesDeRPEnProceso(
			Long cveIdSubdelegacion) {
		this.log.debug(" Obteniendo solicitud en proceso para RP :::");

		StringBuffer bfr = new StringBuffer();
		bfr.append(" Select DISTINCT solicitud from DitTramitePatSujObligado tso ");
		bfr.append(" join tso.ditTramite as tramite ");
		bfr.append(" join tramite.ditSolicitud as solicitud ");
		bfr.append(" join tramite.dicTipoTramite as tipoTramite ");
		bfr.append(" where solicitud.dicTipoSolicitud.cveIdTipoSolicitud IN :idsTipoSolicitud and ");
		bfr.append(" solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud IN :idsEstadoSolicitud ");
		if (cveIdSubdelegacion != null && cveIdSubdelegacion > 0)
			bfr.append(" and solicitud.dicSubdelegacion.cveIdSubdelegacion = :idSubdelegacion ");

		List<Long> idsEstadoSolicitud = new ArrayList<Long>();
		idsEstadoSolicitud.add(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION
				.getCodigo().longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.EDICION_VENTANILLA
				.getCodigo().longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.EDICION_BACKOFFICE
				.getCodigo().longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.PRESENTARSE_EN_VENTANILLA
				.getCodigo().longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.PARA_PROCESAR_BACKOFFICE
				.getCodigo().longValue());

		List<Long> idsTipoSolicitud = new ArrayList<Long>();
		idsTipoSolicitud.add(TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO
				.getValor().longValue());
		idsTipoSolicitud.add(TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION
				.getValor().longValue());

		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("idsEstadoSolicitud", idsEstadoSolicitud);
		query.setParameter("idsTipoSolicitud", idsTipoSolicitud);
		if (cveIdSubdelegacion != null && cveIdSubdelegacion != 0)
			query.setParameter("idSubdelegacion", cveIdSubdelegacion);

		List<DitSolicitud> entities = null;
		entities = query.getResultList();
		List<Solicitud> solicitudes = null;
		if (entities != null) {
			solicitudes = new ArrayList<Solicitud>();
			for (DitSolicitud entity : entities) {
				Solicitud nuevaSolicitud = solicitudUtilityLocal
						.convertEntityToModelSolicitud(entity);
				SujetoObligado sujeto = new SujetoObligado();
				// Se agrega la información de registro patronal a la solicitud
				if (entity.getDitTramites() != null
						&& entity.getDitTramites().size() > 0) {
					DitPatronSujetoObligado so = entity.getDitTramites().get(0)
							.getDitTramitePatSujObligados().get(0)
							.getDitPatronSujetoObligado();
					DitPatronGeneral pg = so.getDitPatronGenerals().get(0);
					DicModalidad mod = so.getDicModalidad();
					sujeto.setCveIdSujetoObligado(so
							.getCveIdPatronSujetoObligado());
					sujeto.setNumeroRegistroPatronal(pg.getRegPatron());
					sujeto.setModalidad(new Modalidad());
					sujeto.getModalidad().setIdModalidad(
							mod.getCveIdModalidad());
					sujeto.getModalidad()
							.setNumModalidad(mod.getNumModalidad());
					sujeto.getModalidad().setDescripcion(mod.getDesModalidad());
					sujeto.setDigVerificador(pg.getDigVer());
				}
				nuevaSolicitud.setSujetoObligado(sujeto);
				solicitudes.add(nuevaSolicitud);
			}
		}
		return solicitudes;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Solicitud> listarSolicitudesDePersonaEnProceso(
			TipoPersonaFiscal tipo, TipoSolicitudEnum tipoSolicitud,
			Long cveIdSubdelegacion) {
		this.log.debug(" Obteniendo solicitud en proceso de personas :::");
		this.log.debug(" Tipo de la persona :::" + tipo);
		StringBuffer bfr = new StringBuffer();

		if (tipo.equals(TipoPersonaFiscal.FISICA)) {
			bfr.append(" Select DISTINCT solicitud from DitTramitePersonaFisica tpf ");
			bfr.append(" join tpf.ditTramite as tramite ");
			bfr.append(" join tramite.ditSolicitud as solicitud ");
			bfr.append(" where solicitud.dicTipoSolicitud.cveIdTipoSolicitud = :idTipoSolicitud and ");
			bfr.append(" solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud IN :idsEstadoSolicitud ");
		} else {
			bfr.append(" Select DISTINCT solicitud from DitTramitePersonaMoral tpm ");
			bfr.append(" join tpm.ditTramite as tramite ");
			bfr.append(" join tramite.ditSolicitud as solicitud ");
			bfr.append(" where solicitud.dicTipoSolicitud.cveIdTipoSolicitud = :idTipoSolicitud and ");
			bfr.append(" solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud IN :idsEstadoSolicitud ");
		}
		// TODO Se verificará con el usuario si este filtro aplica o no para las
		// solicitudes de afiliación
		// if(cveIdSubdelegacion!=null && cveIdSubdelegacion > 0)
		// bfr.append(" and solicitud.dicSubdelegacion.cveIdSubdelegacion = :idSubdelegacion ");

		List<Long> idsEstadoSolicitud = new ArrayList<Long>();
		idsEstadoSolicitud.add(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION
				.getCodigo().longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.EDICION_VENTANILLA
				.getCodigo().longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.EDICION_BACKOFFICE
				.getCodigo().longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.PRESENTARSE_EN_VENTANILLA
				.getCodigo().longValue());
		idsEstadoSolicitud.add(EstadoSolicitudEnum.PARA_PROCESAR_BACKOFFICE
				.getCodigo().longValue());

		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("idTipoSolicitud", tipoSolicitud.getValor()
				.longValue());
		query.setParameter("idsEstadoSolicitud", idsEstadoSolicitud);
		// TODO Se verificará con el usuario si este filtro aplica o no para las
		// solicitudes de afiliación
		// if(cveIdSubdelegacion!=null && cveIdSubdelegacion > 0)
		// query.setParameter("idSubdelegacion", cveIdSubdelegacion);

		List<DitSolicitud> entities = null;
		List<Solicitud> solicitudes = null;

		entities = query.getResultList();
		System.err.println("solicitudes por persona encontradas: " + entities);
		if (entities != null) {
			System.err.println("solicitudes por persona encontradas: "
					+ entities.size());
			solicitudes = new ArrayList<Solicitud>();
			for (DitSolicitud entity : entities) {
				solicitudes.add(solicitudUtilityLocal
						.convertEntityToModelSolicitud(entity));
			}
		}
		return solicitudes;

	}

	@Override
	public List<Solicitud> listarSolicitudesPorTipoYEstado(
			List<Long> idsTipoSolicitud, List<Long> idsEstadoSolicitud) {
		StringBuffer bfr = new StringBuffer();
		bfr.append(" Select DISTINCT solicitud from DitSolicitud solicitud ");
		bfr.append(" where solicitud.dicTipoSolicitud.cveIdTipoSolicitud IN :idsTipoSolicitud and ");
		bfr.append(" solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud IN :idsEstadoSolicitud ");

		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("idsEstadoSolicitud", idsEstadoSolicitud);
		query.setParameter("idsTipoSolicitud", idsTipoSolicitud);

		@SuppressWarnings("unchecked")
		List<DitSolicitud> entities = query.getResultList();
		List<Solicitud> solicitudes = null;
		if (entities != null) {
			solicitudes = new ArrayList<Solicitud>();
			for (DitSolicitud entity : entities) {
				Solicitud nuevaSolicitud = solicitudUtilityLocal
						.convertEntityToModelSolicitud(entity);
				solicitudes.add(nuevaSolicitud);
			}
		}
		return solicitudes;
	}

	public Map<String, Object> listarSolicitudesPropietarioPorFiltro(
			DatosEntradaPaginador<Solicitud> input, FiltroSolicitud filtros,
			PropietarioSolicitudUtil propietario) {
		Map<String, Object> resultado = new TreeMap<String, Object>();

		StringBuffer bfr = new StringBuffer();
		System.err.println("Ejecutando consulta con folio: "
				+ filtros.getFolio());
		String truncatedInitDate = null;
		String truncatedFinalDate = null;
		String truncatedConclusionInitDate = null;
		String truncatedConclusionFinalDate = null;

		// SUBQUERIES

		StringBuffer bfrTramitePersonaFisica = new StringBuffer();
		bfrTramitePersonaFisica
				.append("select tpf.id.cveIdTramite from DitTramitePersonaFisica tpf ");
		bfrTramitePersonaFisica.append("join tpf.ditPersona pf ");
		bfrTramitePersonaFisica.append("where pf.rfc like '%"
				+ filtros.getRfc() + "%'");

		StringBuffer bfrTramitePersonaMoral = new StringBuffer();
		bfrTramitePersonaMoral
				.append("select tpm.id.cveIdTramite from DitTramitePersonaMoral tpm ");
		bfrTramitePersonaMoral.append("join tpm.ditPersonaMoral pm ");
		bfrTramitePersonaMoral.append("where pm.rfc like '%" + filtros.getRfc()
				+ "%'");

		StringBuffer bfrTramiteRPFisica = new StringBuffer();
		bfrTramiteRPFisica
				.append("select pso2.cveIdPatronSujetoObligado from DitPatronSujetoObligado pso2 ");
		bfrTramiteRPFisica.append("join pso2.ditPersonaFisica pf2 ");
		bfrTramiteRPFisica.append("where pf2.rfc like '% " + filtros.getRfc()
				+ "%'");

		StringBuffer bfrTramiteRPMoral = new StringBuffer();
		bfrTramiteRPMoral
				.append("select pso3.cveIdPatronSujetoObligado from DitPatronSujetoObligado pso3 ");
		bfrTramiteRPMoral.append("join pso3.ditPersonaMoral pm2 ");
		bfrTramiteRPMoral.append("where pm2.rfc like '%" + filtros.getRfc()
				+ "%'");

		StringBuffer bfrTramiteSujetoObligado = new StringBuffer();
		bfrTramiteSujetoObligado
				.append("select tso.cveIdTramite from DitTramitePatSujObligado tso ");
		bfrTramiteSujetoObligado
				.append("join tso.ditPatronSujetoObligado pso ");
		bfrTramiteSujetoObligado
				.append("where pso.cveIdPatronSujetoObligado in ( ");
		bfrTramiteSujetoObligado.append(bfrTramiteRPFisica.toString());
		bfrTramiteSujetoObligado
				.append(" ) or pso.cveIdPatronSujetoObligado in ( ");
		bfrTramiteSujetoObligado.append(bfrTramiteRPMoral.toString());
		bfrTramiteSujetoObligado.append(" ) ");

		StringBuffer bfrTramiteRP = new StringBuffer();
		bfrTramiteRP
				.append("select tso.cveIdTramite from DitTramitePatSujObligado tso ");
		bfrTramiteRP.append("join tso.ditPatronSujetoObligado pso ");
		bfrTramiteRP.append("join pso.ditPatronGenerals patronGeneral ");
		bfrTramiteRP.append("where patronGeneral.regPatron = :regPatronal ");
		if (filtros.getRp().length() > 8) {
			bfrTramiteRP
					.append("and pso.dicModalidad.numModalidad = :numModalidad ");
		}
		if (filtros.getRp().length() > 10) {
			bfrTramiteRP.append("and patronGeneral.digVer = :digVerificador ");
		}

		StringBuffer bfrTramiteFecPresentacion = new StringBuffer();
		bfrTramiteFecPresentacion
				.append("select t.cveIdTramite from DitTramite t where ");
		bfrTramiteFecPresentacion
				.append(" trunc( t.fecPresentacion ) between TO_DATE(:fechaInicioPresentacion ,'yyyy-MM-dd') and TO_DATE(:fechaFinPresentacion , 'yyyy-MM-dd') ");

		StringBuffer bfrTramiteFecConclusion = new StringBuffer();
		bfrTramiteFecConclusion
				.append("select t.cveIdTramite from DitTramite t where ");
		bfrTramiteFecConclusion
				.append(" trunc( t.fecConclusion ) between TO_DATE(:fechaInicioConclusion,'yyyy-MM-dd') and TO_DATE(:fechaFinConclusion, 'yyyy-MM-dd') ");

		boolean condicionPrevia = false;

		// QUERY BASE
		bfr.append(" Select DISTINCT solicitud from DitSolicitud solicitud ");
		bfr.append(" join solicitud.ditTramites ditTramites ");

		if (!StringUtils.isBlank(filtros.getRfc())
				&& StringUtils.isBlank(filtros.getRp())) {
			System.err.println("Ejecutando consulta por rfc: "
					+ filtros.getRfc());
			agregarCondicional(condicionPrevia, bfr);
			condicionPrevia = true;
			bfr.append(" ( ditTramites.cveIdTramite in ( ");
			bfr.append(bfrTramitePersonaFisica.toString());
			bfr.append(" ) or ditTramites.cveIdTramite in ( ");
			bfr.append(bfrTramitePersonaMoral.toString());
			bfr.append(" ) or ditTramites.cveIdTramite in ( ");
			bfr.append(bfrTramiteSujetoObligado.toString());
			bfr.append(" ) )");
		} else if (!StringUtils.isBlank(filtros.getRfc())
				&& !StringUtils.isBlank(filtros.getRp())) {
			agregarCondicional(condicionPrevia, bfr);
			condicionPrevia = true;
			bfr.append(" ( ditTramites.cveIdTramite in ( ");
			bfr.append(bfrTramitePersonaFisica.toString());
			bfr.append(" ) or ditTramites.cveIdTramite in ( ");
			bfr.append(bfrTramitePersonaMoral.toString());
			bfr.append(" ) or ditTramites.cveIdTramite in ( ");
			bfr.append(bfrTramiteRP.toString());
			bfr.append(" ) )");
		} else if (StringUtils.isBlank(filtros.getRfc())
				&& !StringUtils.isBlank(filtros.getRp())) {
			agregarCondicional(condicionPrevia, bfr);
			condicionPrevia = true;
			bfr.append(" ( ditTramites.cveIdTramite in ( ");
			bfr.append(bfrTramiteRP.toString());
			bfr.append(" ) )");
		}

		if (filtros.getFechaInicioPresentacion() != null
				&& filtros.getFechaFinPresentacion() != null) {
			agregarCondicional(condicionPrevia, bfr);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			truncatedInitDate = sdf
					.format(filtros.getFechaInicioPresentacion());
			truncatedFinalDate = sdf.format(filtros.getFechaFinPresentacion());
			bfr.append(" trunc( solicitud.fecPresentacion ) between TO_DATE(:fechaInicioPresentacion ,'yyyy-MM-dd') and TO_DATE(:fechaFinPresentacion , 'yyyy-MM-dd') ");
			condicionPrevia = true;
		}

		if (filtros.getFechaInicioConclusion() != null
				&& filtros.getFechaFinConclusion() != null) {
			agregarCondicional(condicionPrevia, bfr);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			truncatedConclusionInitDate = sdf.format(filtros
					.getFechaInicioConclusion());
			truncatedConclusionFinalDate = sdf.format(filtros
					.getFechaFinConclusion());
			bfr.append(" trunc ( solicitud.fecConclusion ) between TO_DATE(:fechaInicioConclusion,'yyyy-MM-dd') and TO_DATE(:fechaFinConclusion,'yyyy-MM-dd') ");
			condicionPrevia = true;
		}

		if (filtros.getTramiteId() != null && filtros.getTramiteId() > 0) {
			agregarCondicional(condicionPrevia, bfr);
			bfr.append(" ditTramites.dicTipoTramite.cveIdTipoTramite = :idTipoTramite ");
			condicionPrevia = true;
		}

		if (filtros.getIdEstadoSolicitud() != null
				&& filtros.getIdEstadoSolicitud() > 0) {
			agregarCondicional(condicionPrevia, bfr);
			bfr.append(" solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud = :idEstadoSolicitud ");
			condicionPrevia = true;
		}

		if (filtros.getIdDelegacion() != null && filtros.getIdDelegacion() > 0) {
			agregarCondicional(condicionPrevia, bfr);
			bfr.append(" ditTramites.cveIdTramite IN ( ");
			bfr.append("		select tso.cveIdTramite from DitTramitePatSujObligado tso ");
			bfr.append("			join tso.ditPatronSujetoObligado pso ");
			bfr.append("			join pso.ditSubdelPatSujOblig ditSubdelPatSujOblig ");
			bfr.append("			join ditSubdelPatSujOblig.dicSubdelegacion dicSubdelegacion ");
			bfr.append("			join dicSubdelegacion.dicDelegacion dicDelegacion ");
			bfr.append("		where dicDelegacion.cveIdDelegacion = :idDelegacion");
			bfr.append("	)	");
			condicionPrevia = true;
		}

		if (propietario.equals(PropietarioSolicitudUtil.REGISTRO_PATRONAL)
				&& filtros.getIdSubdelegacion() != null
				&& filtros.getIdSubdelegacion() > 0) {
			agregarCondicional(condicionPrevia, bfr);
			bfr.append(" solicitud.dicSubdelegacion.cveIdSubdelegacion = :idSubdelegacion");
			condicionPrevia = true;
		}

		// Se agrega para limitar los tipos de solicitud a mostrar
		agregarCondicional(condicionPrevia, bfr);
		bfr.append(" ( solicitud.dicTipoSolicitud.cveIdTipoSolicitud = ")
				.append(TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES
						.getValor().longValue());
		bfr.append(" or solicitud.dicTipoSolicitud.cveIdTipoSolicitud = ")
				.append(TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION
						.getValor().longValue());
		bfr.append(" or solicitud.dicTipoSolicitud.cveIdTipoSolicitud = ")
				.append(TipoSolicitudEnum.ALTA_PATRONAL.getValor().longValue());
		bfr.append(" or solicitud.dicTipoSolicitud.cveIdTipoSolicitud = ")
				.append(TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO
						.getValor().longValue()).append(" ) ");
		bfr.append(" order by solicitud.refFolio asc");

		Query query = this.em.createQuery(bfr.toString());
		Query queryTotal = this.em.createQuery(bfr.toString());

		if (!StringUtils.isBlank(filtros.getRp())) {
			if (filtros.getRp().length() >= 8) {
				queryTotal.setParameter("regPatronal", filtros.getRp()
						.substring(0, 8));
				query.setParameter("regPatronal",
						filtros.getRp().substring(0, 8));
			}
			if (filtros.getRp().length() > 8 && filtros.getRp().length() >= 10) {
				queryTotal.setParameter("numModalidad", filtros.getRp()
						.substring(8, 10));
				query.setParameter("numModalidad",
						filtros.getRp().substring(8, 10));
			}
			if (filtros.getRp().length() == 11) {
				queryTotal.setParameter("digVerificador", filtros.getRp()
						.substring(filtros.getRp().length() - 1));
				query.setParameter("digVerificador",
						filtros.getRp().substring(filtros.getRp().length() - 1));
			}

		}
		if (filtros.getFechaInicioPresentacion() != null
				&& filtros.getFechaFinPresentacion() != null) {
			queryTotal.setParameter("fechaInicioPresentacion",
					truncatedInitDate);
			query.setParameter("fechaInicioPresentacion", truncatedInitDate);

			queryTotal.setParameter("fechaFinPresentacion", truncatedFinalDate);
			query.setParameter("fechaFinPresentacion", truncatedFinalDate);
		}

		if (filtros.getFechaInicioConclusion() != null
				&& filtros.getFechaFinConclusion() != null) {
			queryTotal.setParameter("fechaInicioConclusion",
					truncatedConclusionInitDate);
			query.setParameter("fechaInicioConclusion",
					truncatedConclusionInitDate);

			queryTotal.setParameter("fechaFinConclusion",
					truncatedConclusionFinalDate);
			query.setParameter("fechaFinConclusion",
					truncatedConclusionFinalDate);
		}

		if (filtros.getTramiteId() != null && filtros.getTramiteId() > 0) {
			queryTotal.setParameter("idTipoTramite", filtros.getTramiteId());
			query.setParameter("idTipoTramite", filtros.getTramiteId());
		}

		if (filtros.getIdEstadoSolicitud() != null
				&& filtros.getIdEstadoSolicitud() > 0) {
			queryTotal.setParameter("idEstadoSolicitud",
					filtros.getIdEstadoSolicitud());
			query.setParameter("idEstadoSolicitud",
					filtros.getIdEstadoSolicitud());
		}

		if (filtros.getIdDelegacion() != null && filtros.getIdDelegacion() > 0) {
			queryTotal.setParameter("idDelegacion", filtros.getIdDelegacion());
			query.setParameter("idDelegacion", filtros.getIdDelegacion());
		}

		if (propietario.equals(PropietarioSolicitudUtil.REGISTRO_PATRONAL)
				&& filtros.getIdSubdelegacion() != null
				&& filtros.getIdSubdelegacion() > 0) {
			queryTotal.setParameter("idSubdelegacion",
					filtros.getIdSubdelegacion());
			query.setParameter("idSubdelegacion", filtros.getIdSubdelegacion());
		}

		@SuppressWarnings("unchecked")
		List<DitSolicitud> entitiesTotales = queryTotal.getResultList();
		Integer totalresult = entitiesTotales.size();

		query.setFirstResult(input.getiDisplayStart());
		query.setMaxResults(input.getiDisplayLength());

		@SuppressWarnings("unchecked")
		List<DitSolicitud> entitiesADesplegar = query.getResultList();

		resultado.put("ElementosTotales", totalresult);
		resultado.put("entidades", entitiesADesplegar);

		return resultado;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Solicitud> listarSolicitudesDeRegistroPatronalPorId(
			Long idPatronSujetoObligado) {
		this.log.debug(" Obteniendo solicitud en proceso del Patron :::"
				+ idPatronSujetoObligado);
		StringBuffer bfrTT = new StringBuffer();
		bfrTT.append("select tt.cveIdTipoTramite from DicTipoTramite tt, DicModulo m ");
		bfrTT.append("where m.cveIdModulo = :idModulo and m in elements(tt.dicModulos)");

		Query queryTT = this.em.createQuery(bfrTT.toString());
		queryTT.setParameter("idModulo", ModuloEnum.PATRONES.getCodigo()
				.longValue());

		// List<Long> idTipoTramites = queryTT.getResultList();

		StringBuffer bfr = new StringBuffer();
		bfr.append(" Select DISTINCT solicitud from DitTramitePatSujObligado tso ");
		bfr.append(" join tso.ditTramite as tramite ");
		bfr.append(" join tramite.ditSolicitud as solicitud ");
		bfr.append(" join tramite.dicTipoTramite as tipoTramite ");
		bfr.append(" where tso.ditPatronSujetoObligado.cveIdPatronSujetoObligado = :idSujetoObligado");
//		bfr.append(" solicitud.dicTipoSolicitud.cveIdTipoSolicitud IN :idsTipoSolicitud and ");
		// bfr.append(" tipoTramite.cveIdTipoTramite IN (");
		// bfr.append("					select tt.cveIdTipoTramite from DicTipoTramite tt, DicModulo m ");
		// bfr.append("					where m.cveIdModulo = :idModulo and m in elements(tt.dicModulos)) and ");
//		bfr.append(" solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud IN :idsEstadoSolicitud ");

//		List<Long> idsEstadoSolicitud = new ArrayList<Long>();
//		idsEstadoSolicitud.add(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION
//				.getCodigo().longValue());
//		idsEstadoSolicitud.add(EstadoSolicitudEnum.EDICION_VENTANILLA
//				.getCodigo().longValue());
//		idsEstadoSolicitud.add(EstadoSolicitudEnum.EDICION_BACKOFFICE
//				.getCodigo().longValue());
//		// idsEstadoSolicitud.add(EstadoSolicitudEnum.REGISTRADA.getCodigo().longValue());
//		idsEstadoSolicitud.add(EstadoSolicitudEnum.PRESENTARSE_EN_VENTANILLA
//				.getCodigo().longValue());
//		idsEstadoSolicitud.add(EstadoSolicitudEnum.PARA_PROCESAR_BACKOFFICE
//				.getCodigo().longValue());

		
//		List<Long> idsTipoSolicitud = new ArrayList<Long>();
//		idsTipoSolicitud.add(TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO
//				.getValor().longValue());
//		idsTipoSolicitud.add(TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION
//				.getValor().longValue());

		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("idSujetoObligado", idPatronSujetoObligado);
//		query.setParameter("idsEstadoSolicitud", idsEstadoSolicitud);
//		query.setParameter("idsTipoSolicitud", idsTipoSolicitud);
		// query.setParameter("idModulo",
		// ModuloEnum.PATRONES.getCodigo().longValue());
		// query.setParameter("tiposTramites", idTipoTramites);

		List<DitSolicitud> entities = null;
		entities = query.getResultList();
		List<Solicitud> solicitudes = null;
		if (entities != null) {
			solicitudes = new ArrayList<Solicitud>();
			for (DitSolicitud entity : entities) {
				Solicitud nuevaSolicitud = solicitudUtilityLocal
						.convertEntityToModelSolicitud(entity);
				SujetoObligado sujeto = new SujetoObligado();
				// Se agrega la información de registro patronal a la solicitud
				if (entity.getDitTramites() != null
						&& entity.getDitTramites().size() > 0) {
					DitPatronSujetoObligado so = entity.getDitTramites().get(0)
							.getDitTramitePatSujObligados().get(0)
							.getDitPatronSujetoObligado();
					DicModalidad modalidad = so.getDicModalidad();
					sujeto.setCveIdSujetoObligado(so
							.getCveIdPatronSujetoObligado());
					DitPatronGeneral patronGeneral = so.getDitPatronGenerals()
							.get(0);

					sujeto.setNumeroRegistroPatronal(patronGeneral
							.getRegPatron());
					sujeto.setDigVerificador(patronGeneral.getDigVer());
					sujeto.setModalidad(new Modalidad());
					sujeto.getModalidad().setIdModalidad(
							modalidad.getCveIdModalidad());
					sujeto.getModalidad().setNumModalidad(
							modalidad.getNumModalidad());
					sujeto.getModalidad().setDescripcion(
							modalidad.getDesModalidad());

				}
				nuevaSolicitud.setSujetoObligado(sujeto);
				solicitudes.add(nuevaSolicitud);
			}
		}
		return solicitudes;
	}

	@Override
	public void cancelarSolicitudPorFolio(String folio) {
		try{
		StringBuffer query = new StringBuffer();
		query.append("update DIT_SOLICITUD set CVE_ID_ESTADO_SOLICITUD = :idEstadoSolicitud");
		query.append(" where REF_FOLIO =:folio");
		Query updSolQuery=em.createNativeQuery(query.toString());
		updSolQuery.setParameter("idEstadoSolicitud", EstadoSolicitudEnum.CANCELADA.getCodigo());
		updSolQuery.setParameter("folio", folio);
		
		int numSolicitudesActualizadas = updSolQuery.executeUpdate();
		log.error("Se cancelaron el sig numero de solicitudes: "+numSolicitudesActualizadas);
		
		StringBuffer queryTr = new StringBuffer();
		queryTr.append("update DIT_TRAMITE set CVE_ID_ESTADO_TRAMITE = :idEstadoTramite");
		queryTr.append(" where CVE_ID_SOLICITUD IN ( SELECT CVE_ID_SOLICITUD FROM DIT_SOLICITUD WHERE REF_FOLIO =:folio )");
		Query updTrQuery=em.createNativeQuery(queryTr.toString());
		updTrQuery.setParameter("idEstadoTramite", EstadoTramiteEnum.CANCELADO.getCodigo());
		updTrQuery.setParameter("folio", folio);
		
		int numTramitesCancelados = updTrQuery.executeUpdate();
		
		log.error("Se cancelaron el sig numero de tramites: "+numTramitesCancelados);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public Long obtenerIdSolicitudAlta(Long IdPatron) {
		StringBuffer query = new StringBuffer();
		query.append("select ditTramite from DitTramitePatSujObligado ditTramitePatSujObligado join ditTramitePatSujObligado.ditTramite ditTramite");
		query.append(" where ditTramitePatSujObligado.ditPatronSujetoObligado.cveIdPatronSujetoObligado = ").append(IdPatron);
		query.append(" and ditTramitePatSujObligado.ditTramite.dicTipoTramite.cveIdTipoTramite=1 and  ditTramitePatSujObligado.ditTramite.dicEstadoTramite.cveIdEstadoTramite=2");
		Query solQuery=em.createQuery(query.toString());
		DitTramite tramiteAlta=(DitTramite)solQuery.getSingleResult();
		return tramiteAlta.getDitSolicitud().getCveIdSolicitud();
	}
	
}
