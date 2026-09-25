/**
 *
 *
 **/
package mx.gob.imss.ctirss.delta.tramite.service.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.solicitud.DocumentoPorTipoConversorLocal;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoProbatorioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TramiteSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicTipoTramite;
import mx.gob.imss.ctirss.delta.persistence.DitDoctoReqTramite;
import mx.gob.imss.ctirss.delta.persistence.DitDoctoResultanteTramite;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoPorTipo;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePatSujObligado;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePatSujObligadoPK;
import mx.gob.imss.ctirss.delta.tramite.service.utility.TramiteServiceUtilityLocal;

import org.hibernate.Criteria;
import org.hibernate.NonUniqueResultException;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * 
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Autor: Hugo Armando Mart�nez Cham�nica
 * @Proyecto: delta
 * @Archivo: TramiteServiceEntity.java
 * @Paquete: mx.gob.imss.ctirss.delta.tramite.service.entity
 * @Fecha: 17:57:55
 */
@Stateless
public class TramiteServiceEntity extends AbstractServiceEntity implements
		TramiteServiceEntityLocal {

	@EJB
	private TramiteServiceUtilityLocal tramiteServiceUtilityLocal;
	
	@EJB
	private DocumentoPorTipoConversorLocal documentoPorTipoConversorLocal;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.imss.ctirss.delta.tramite.service.entity.TramiteServiceEntityLocal
	 * #consultarTramites()
	 */

	@Override
	public List<TramiteSolicitud> consultarTramites() {

		List<TramiteSolicitud> listaTramites = new ArrayList<TramiteSolicitud>();
		return listaTramites;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.imss.ctirss.delta.tramite.service.entity.TramiteServiceEntityLocal
	 * #insertarTramite(mx.gob.imss.ctirss.delta.model.gestion.patronal.Tramite,
	 * mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal)
	 */
	@Override
	public void insertarTramite(TramiteSolicitud tramite,
			TipoPersonaFiscal tipoPersona) {
		// DicEstadoTramite estado =
		// (DicEstadoTramite)this.getSession().load(DicEstadoTramite.class,
		// tramite.getEstadoTramite().getIdEstadoTramitePersona());
		// DicTipoTramite tipo =
		// (DicTipoTramite)this.getSession().load(DicTipoTramite.class,
		// tramite.getTipoTramite().getIdTipoTramite());
		// DitSolicitud solicitud =
		// (DitSolicitud)this.getSession().load(DitSolicitud.class,
		// tramite.getSolicitud().getSolicitudId());
		// if(tipoPersona.equals(TipoPersonaFiscal.FISICA)){
		// DitTramitePersonaFisica tpf =
		// tramiteServiceUtilityLocal.convertirModelToEntityTramitePersonaFisica(tramite);
		// // tpf.setDicEstadoTramite(estado);
		// // tpf.setDicTipoTramite(tipo);
		// // tpf.setDitSolicitud(solicitud);
		// this.getSession().save(tpf);
		// }else if(tipoPersona.equals(TipoPersonaFiscal.MORAL)){
		// DitTramitePersonaMoral tpm =
		// tramiteServiceUtilityLocal.convertirModelToEntityTramitePersonaMoral(tramite);
		// // tpm.setDicEstadoTramite(estado);
		// // tpm.setDicTipoTramite(tipo);
		// // tpm.setDitSolicitud(solicitud);
		// this.getSession().save(tpm);
		// }

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.imss.ctirss.delta.tramite.service.entity.TramiteServiceEntityLocal
	 * #actualizarEstadoTramite(java.lang.Long, java.lang.Long)
	 */
	@Override
	public void actualizarEstadoTramite(Long idSolicitud, Long idPersona,
			Long idNuevoEstado, TipoPersonaFiscal tipPersona) {
		// DicEstadoTramite estadoTramite =
		// (DicEstadoTramite)this.getSession().load(DicEstadoTramite.class,
		// idNuevoEstado);
		// if(tipPersona.equals(TipoPersonaFiscal.FISICA)){
		// DitTramitePersonaFisicaPK id = new DitTramitePersonaFisicaPK();
		// id.setCveIdPersona(idPersona);
		// id.setCveIdSolicitud(idSolicitud);

		// DitTramitePersonaFisica tramite =
		// (DitTramitePersonaFisica)this.getSession().load(DitTramitePersonaFisica.class,
		// id);
		// tramite.setDicEstadoTramite(estadoTramite);
		// this.getSession().saveOrUpdate(tramite);
		// }
		// if(tipPersona.equals(TipoPersonaFiscal.MORAL)){
		// DitTramitePersonaMoralPK id = new DitTramitePersonaMoralPK();
		// id.setCveIdPersonaMoral(idPersona);
		// id.setCveIdSolicitud(idSolicitud);

		// DitTramitePersonaMoral tramite =
		// (DitTramitePersonaMoral)this.getSession().load(DitTramitePersonaMoral.class,
		// id);
		// tramite.setDicEstadoTramite(estadoTramite);
		// this.getSession().saveOrUpdate(tramite);
		// }

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteSolicitud> consultarTramitesPorSujetoObligado(
			Long idPatronSujetoObligado) {
		Criteria query = this.getSession().createCriteria(
				DitTramitePatSujObligado.class);
		query.createAlias("ditPatronSujetoObligado", "ditPatronSujetoObligado")
				.add(Restrictions.eq(
						"ditPatronSujetoObligado.cveIdPatronSujetoObligado",
						idPatronSujetoObligado));
		query.createAlias("ditTramite", "ditTramite").add(
				Restrictions.isNull("ditTramite.fecRegistroBaja"));
		query.createAlias("ditTramite.dicEstadoTramite", "dicEstadoTramite")
		.add(Restrictions.eq("dicEstadoTramite.cveIdEstadoTramite",
				EstadoTramiteEnum.INICIADO.getCodigo().longValue()));

		List<DitTramitePatSujObligado> tramites = query.list();
		List<TramiteSolicitud> tramitesSolicitud = Collections.emptyList();

		if (tramites != null) {
			tramitesSolicitud = new ArrayList<TramiteSolicitud>();
			for (DitTramitePatSujObligado tramite : tramites) {
				tramitesSolicitud.add(tramiteServiceUtilityLocal
						.convertirEntityToModel(tramite.getDitTramite()));
			}

		}

		return tramitesSolicitud;
	}

	@Override
	public void asociarTramite(Long cveIdTramite, Long cveIdPatronSujetoObligado) {
		DitTramitePatSujObligado entity = new DitTramitePatSujObligado();
		DitPatronSujetoObligado ditPat = (DitPatronSujetoObligado) this
				.getSession().load(DitPatronSujetoObligado.class,
						cveIdPatronSujetoObligado);
		// DitTramite ditTramite =
		// (DitTramite)this.getSession().load(DitTramite.class, cveIdTramite);
		
		//Se modifica la relaci�n forma de relacionar los tramites de sujeto obligado debido al cambio de mapeo que corrige la llave compuesta CVE_ID_TRAMITE Y CVE_ID_PAT_SUJ_OBLIGADO
		//entity.setDitPatronSujetoObligado(ditPat);
		DitTramitePatSujObligadoPK pk = new DitTramitePatSujObligadoPK();
		pk.setCveIdTramite(cveIdTramite);
		pk.setCveIdPatronSujetoObligado(cveIdPatronSujetoObligado);
		entity.setId(pk);
		//entity.setCveIdTramite(cveIdTramite);

		this.getSession().persist(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean existeTramiteActivoPorSujetoObligadoYTipo(
			TipoTramiteEnum tipo, Long cveIdPatronSujetoObligado) {

		Criteria criteria = this.getSession().createCriteria(
				DitTramitePatSujObligado.class);
		criteria.createAlias("ditTramite", "ditTramite")
				.createAlias("ditTramite.dicTipoTramite", "dicTipoTramite")
				.add(Restrictions.eq("dicTipoTramite.cveIdTipoTramite", tipo
						.getCodigo().longValue()));
		criteria.createAlias("ditPatronSujetoObligado",
				"ditPatronSujetoObligado").add(
				Restrictions.eq(
						"ditPatronSujetoObligado.cveIdPatronSujetoObligado",
						cveIdPatronSujetoObligado));
		criteria.createAlias("ditTramite.dicEstadoTramite", "dicEstadoTramite")
				.add(Restrictions.and(Restrictions.ne("dicEstadoTramite.cveIdEstadoTramite",
										EstadoTramiteEnum.CERRADO.getCodigo().longValue()), 
									Restrictions.ne("dicEstadoTramite.cveIdEstadoTramite",
										EstadoTramiteEnum.CANCELADO.getCodigo().longValue())) );

		List<DitTramitePatSujObligado> tramitesSujetoObligado = criteria.list();
		boolean existe = tramitesSujetoObligado != null ? (tramitesSujetoObligado
				.size() > 0 ? true : false) : false;
		
				System.out.println("tramites encontrados que son activos: "+tramitesSujetoObligado.size());
				
		return existe;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean existeTramitesActivoPorSujetoObligadoYTipos(
			List<TipoTramiteEnum> tipos, Long cveIdPatronSujetoObligado, List<Long> estados) {
		
		List<Long> listaTipos= new ArrayList<Long>();
		
		if(estados == null){
			estados= new ArrayList<Long>();
			estados.add(EstadoTramiteEnum.CERRADO.getCodigo().longValue());
			estados.add(EstadoTramiteEnum.CANCELADO.getCodigo().longValue());
		}
		
		for(TipoTramiteEnum tipo : tipos){
			listaTipos.add(tipo.getCodigo().longValue());
		}
		
		Criteria criteria = this.getSession().createCriteria(
				DitTramitePatSujObligado.class);
		criteria.createAlias("ditTramite", "ditTramite")
				.createAlias("ditTramite.dicTipoTramite", "dicTipoTramite")
				.add(Restrictions.in("dicTipoTramite.cveIdTipoTramite", listaTipos));
		criteria.createAlias("ditPatronSujetoObligado",
				"ditPatronSujetoObligado").add(
				Restrictions.eq(
						"ditPatronSujetoObligado.cveIdPatronSujetoObligado",
						cveIdPatronSujetoObligado));
		criteria.createAlias("ditTramite.dicEstadoTramite", "dicEstadoTramite")
				.add(Restrictions.not(Restrictions.in("dicEstadoTramite.cveIdEstadoTramite", estados))
//						Restrictions.and(
//						Restrictions.ne("dicEstadoTramite.cveIdEstadoTramite",
//						EstadoTramiteEnum.CERRADO.getCodigo().longValue()), 
//						Restrictions.ne("dicEstadoTramite.cveIdEstadoTramite",
//						EstadoTramiteEnum.CANCELADO.getCodigo().longValue())) 
					);
		
		List<DitTramitePatSujObligado> tramitesSujetoObligado = criteria.list();
		boolean existe = tramitesSujetoObligado != null ? (tramitesSujetoObligado
				.size() > 0 ? true : false) : false;

		return existe;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteSolicitud> consultarTramitesActivosPorSujetoObligado(
			Long cveIdSujetoObligado) {

		Criteria query = this.getSession().createCriteria(
				DitTramitePatSujObligado.class);
		query.createAlias("ditPatronSujetoObligado", "ditPatronSujetoObligado")
				.add(Restrictions.eq(
						"ditPatronSujetoObligado.cveIdPatronSujetoObligado",
						cveIdSujetoObligado));
		query.createAlias("ditTramite", "ditTramite").add(
				Restrictions.isNull("ditTramite.fecRegistroBaja"));
		query.createAlias("ditTramite.dicEstadoTramite", "dicEstadoTramite")
		.add(Restrictions.eq("dicEstadoTramite.cveIdEstadoTramite",
				EstadoTramiteEnum.ACTIVO.getCodigo().longValue()));

		List<DitTramitePatSujObligado> tramites = query.list();
		List<TramiteSolicitud> tramitesSolicitud = Collections.emptyList();

		if (tramites != null) {
			tramitesSolicitud = new ArrayList<TramiteSolicitud>();
			for (DitTramitePatSujObligado tramite : tramites) {
				tramitesSolicitud.add(tramiteServiceUtilityLocal
						.convertirEntityToModel(tramite.getDitTramite()));
			}

		}

		
		return tramitesSolicitud;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteSolicitud> consultarTramitesDeSujetoObligadoEnCursoCreadosPorUsuario(
			Long cveIdSujetoObligado, Long idUsuario) {
		Criteria query = this.getSession().createCriteria(
				DitTramitePatSujObligado.class);
		query.createAlias("ditPatronSujetoObligado", "ditPatronSujetoObligado")
				.add(Restrictions.eq(
						"ditPatronSujetoObligado.cveIdPatronSujetoObligado",
						cveIdSujetoObligado));
		query.createAlias("ditTramite", "ditTramite").add(
				Restrictions.isNull("ditTramite.fecRegistroBaja"));
		query.createAlias("ditTramite.dicEstadoTramite", "dicEstadoTramite")
		.add(Restrictions.ne("dicEstadoTramite.cveIdEstadoTramite",
				EstadoTramiteEnum.CERRADO.getCodigo().longValue()));
		query.createAlias("ditTramite.ditSolicitud", "ditSolicitud")
				.createAlias("ditSolicitud.ditUsuario", "ditUsuario")
				.add(Restrictions.eq("ditUsuario.cveIdUsuario", idUsuario));
		
		
		
		List<DitTramitePatSujObligado> tramites = query.list();
		List<TramiteSolicitud> tramitesSolicitud = Collections.emptyList();

		if (tramites != null) {
			tramitesSolicitud = new ArrayList<TramiteSolicitud>();
			for (DitTramitePatSujObligado tramite : tramites) {
				tramitesSolicitud.add(tramiteServiceUtilityLocal
						.convertirEntityToModel(tramite.getDitTramite()));
			}

		}
		return tramitesSolicitud;
	}

	@Override
	public List<TramiteSolicitud> consultarTramitesActivosDeSujetoObligado(
			Long cveIdSujetoObligado) {
		Criteria query = this.getSession().createCriteria(
				DitTramitePatSujObligado.class);
		
		query.createAlias("ditPatronSujetoObligado", "ditPatronSujetoObligado")
		.add(Restrictions.eq(
				"ditPatronSujetoObligado.cveIdPatronSujetoObligado",
				cveIdSujetoObligado));
		query.createAlias("ditTramite", "ditTramite").add(
				Restrictions.isNull("ditTramite.fecRegistroBaja"));
		query.createAlias("ditTramite.dicEstadoTramite", "dicEstadoTramite")
			.add(Restrictions.eq("dicEstadoTramite.cveIdEstadoTramite",
		EstadoTramiteEnum.ACTIVO.getCodigo().longValue()));
		
		
		@SuppressWarnings("unchecked")
		List<DitTramitePatSujObligado> tramites = query.list();
	
		List<TramiteSolicitud> tramitesSolicitud = Collections.emptyList();

		if (tramites != null) {
			tramitesSolicitud = new ArrayList<TramiteSolicitud>();
			for (DitTramitePatSujObligado tramite : tramites) {
				tramitesSolicitud.add(tramiteServiceUtilityLocal
						.convertirEntityToModel(tramite.getDitTramite()));
			}

		}
		return tramitesSolicitud;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TramiteSolicitud> consultarTramitesActivos() {
		
		
		
//		Criteria query = this.getSession().createCriteria(
//				DitTramitePatSujObligado.class);

		Criteria query = this.getSession().createCriteria(
				DitTramite.class);
		
//		query.add(Restrictions.isNull("ditTramite.fecRegistroBaja"));
		query.add(Restrictions.isNull("fecRegistroBaja"));
		
		/*query.createAlias("ditTramite.dicEstadoTramite", "dicEstadoTramite")
				.add(Restrictions.in("dicEstadoTramite.cveIdEstadoTramite", new Object[]{
						EstadoTramiteEnum.INICIADO.getCodigo().longValue(),
						EstadoTramiteEnum.ACTIVO.getCodigo().longValue()
				}));*/
		
//		query.createAlias("ditTramite.dicTipoTramite", "dicTipoTramite")
		query.createAlias("dicTipoTramite", "dicTipoTramite")
		.add(Restrictions.in("dicTipoTramite.cveIdTipoTramite", new Object[]{
				TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL.getCodigo().longValue(),
				TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO.getCodigo().longValue(),
				TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA.getCodigo().longValue(),
				TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO.getCodigo().longValue(),
				TipoTramiteEnum.ACTUALIZACION_SOCIO.getCodigo().longValue(),
				TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo().longValue(),
				TipoTramiteEnum.ACTIVIDAD_ECONOMICA.getCodigo().longValue(),
				TipoTramiteEnum.DISPOSICION_DE_LEY.getCodigo().longValue(),
				TipoTramiteEnum.INCORPORACION_DE_ACTIVIDADES.getCodigo().longValue(),
				TipoTramiteEnum.COMPRA_DE_ACTIVOS.getCodigo().longValue(),
				TipoTramiteEnum.COMODATO.getCodigo().longValue(),
				TipoTramiteEnum.ENAJENACION.getCodigo().longValue(),
				TipoTramiteEnum.ARRENDAMIENTO.getCodigo().longValue(),
				TipoTramiteEnum.FIDEICOMISO_TRASLATIVO.getCodigo().longValue(),
				TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo().longValue()				
		}));
		
//		query.createAlias("ditTramite.dicEstadoTramite", "dicEstadoTramite")
		query.createAlias("dicEstadoTramite", "dicEstadoTramite")
		.add(Restrictions.in("dicEstadoTramite.cveIdEstadoTramite", new Object[]{
//				EstadoTramiteEnum.INICIADO.getCodigo().longValue(),
				EstadoTramiteEnum.ACTIVO.getCodigo().longValue()
		}));
		
		query.createAlias("ditSolicitud", "ditSolicitud")
		.add(Restrictions.in("ditSolicitud.dicEstadoSolicitud.cveIdEstadoSolicitud", new Object[]{
				EstadoSolicitudEnum.EDICION_BACKOFFICE.getCodigo().longValue(),
				EstadoSolicitudEnum.EDICION_VENTANILLA.getCodigo().longValue(),
				EstadoSolicitudEnum.PARA_PROCESAR_BACKOFFICE.getCodigo().longValue(),
				EstadoSolicitudEnum.PRESENTARSE_EN_VENTANILLA.getCodigo().longValue()}));

		
		
		List<DitTramite> tramites = query.list();
		List<TramiteSolicitud> tramitesSolicitud = Collections.emptyList();

		if (tramites != null) {
			tramitesSolicitud = new ArrayList<TramiteSolicitud>();
			for (DitTramite tramite : tramites) {
				tramitesSolicitud.add(tramiteServiceUtilityLocal
						.convertirEntityToModel(tramite));
			}

		}
		return tramitesSolicitud;
	}

	@Override
	public TipoTramite consultarTipoTramite(Integer id) {
		DicTipoTramite entity = em.find(DicTipoTramite.class, id);
		DicTipoTramite entityTest = em.find(DicTipoTramite.class, 2);
		TipoTramite tipoTramite=tramiteServiceUtilityLocal.convertirEntityToModelTipoTramite(entity);
		tipoTramite.setGuiaDetallada(entityTest.getRefGuiaDetallada());
		tipoTramite.setGuiaRapida(entityTest.getRefGuiaRapida());
		return tipoTramite;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TipoTramite> listarTipoTramitesPorModulo(Long idModulo) {
		StringBuffer bfr = new StringBuffer();
		bfr.append("select tt from DicTipoTramite tt, DicModulo m ");
		bfr.append("where m.cveIdModulo = :idModulo and m in elements(tt.dicModulos)");
		
		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("idModulo", idModulo);
		
		List<DicTipoTramite> tiposTramite = query.getResultList();
		List<TipoTramite> tipos = new ArrayList<TipoTramite>();
		System.err.println("Tipos de tramite encontrados: "+tiposTramite.size());
		for(DicTipoTramite entity : tiposTramite){
			tipos.add(
					tramiteServiceUtilityLocal.convertirEntityToModelTipoTramite(entity)
					);
		}
		System.err.println("Tipos de tramite convertidos: "+tiposTramite.size());
		return tipos;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentoPorTipo> getDocumentosResultantesPorTipoTramite(
			Long idTipoTramite) {
		
		List<DitDocumentoPorTipo> documentosPorTipo = null;
		List<DocumentoPorTipo> documentos = null;
		Criteria consulta = this.getSession().createCriteria(DitDoctoReqTramite.class);
		consulta.add(Restrictions.isNull("fecRegistrosBaja"));
		
		consulta.setProjection(Projections.property("ditDocumentoPorTipo"));
		
		consulta.createAlias("dicTipoTramite", "tipoTramite");
		consulta.add(Restrictions.eq("tipoTramite.cveIdTipoTramite", idTipoTramite));
		
		Criteria tipoDocumento = consulta.createCriteria("ditDocumentoPorTipo").createCriteria("dicTipoDocumentoProbatorio");
		tipoDocumento.add(Restrictions.eq("cveIdTipoDocumentoProbator", TipoDocumentoProbatorioEnum.RESULTANTE.getId()));
		
		documentosPorTipo = (List<DitDocumentoPorTipo>) consulta.list();
		
		documentos = documentoPorTipoConversorLocal.convertirEntityToModelList(documentosPorTipo);
		
		return documentos;
	}

	
	@Override
	public Object getDocumentoPorTipoIdTramite(Long idTramite,
			Long idDocumentoPorTipo) {
		Criteria consultaDocto = this.getSession().createCriteria(DitDoctoResultanteTramite.class);
		
		consultaDocto.createAlias("ditTramite", "tramite");
		consultaDocto.add(Restrictions.eq("tramite.cveIdTramite", idTramite));
		consultaDocto.createAlias("ditDocumentoPorTipo", "tipoDocto");
		consultaDocto.add(Restrictions.eq("tipoDocto.cveIdDoctoProbPorTipo",idDocumentoPorTipo));
		
		DitDoctoResultanteTramite doctoRes = null;
		
		try {
			doctoRes = (DitDoctoResultanteTramite) consultaDocto.uniqueResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(doctoRes != null) {
			return doctoRes.getRefDocumentoResultante();
		}
		
		return null;
	}

	@Override
	public void actualizarDocumentosTramite(Long idTramite,
			Long idDocumentoTipo, Object bytes) {
		
		DitDoctoResultanteTramite doctoRes = new DitDoctoResultanteTramite();
		doctoRes.setDitDocumentoPorTipo(new DitDocumentoPorTipo());
		doctoRes.setDitTramite(new DitTramite());
		
		doctoRes.getDitDocumentoPorTipo().setCveIdDoctoProbPorTipo(idDocumentoTipo);
		doctoRes.getDitTramite().setCveIdTramite(idTramite);
		doctoRes.setRefDocumentoResultante((byte[])bytes);
		
		doctoRes.setFecRegistroAlta(new Date());
		
		this.em.persist(doctoRes);
	}
	
	
	/**
	 * Metodo que consulta los tramites asociados a una lista de modulos si el modulo es null devuevle
	 * el catalogo de tramites sin filtrar
	 * @param modulos
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TipoTramite> getTramitesByModulos(List<Modulo> modulos){
		
		StringBuffer bfr = new StringBuffer();
		bfr.append(" Select catTramite from DicTipoTramite catTramite ");
		//where de filtro de modulos de el filtro de modulos
		List<DicTipoTramite> lstDicTipoTramite  = null;
		List<TipoTramite> lstTipoTramite  = null;
		if(modulos!= null && !modulos.isEmpty()){
			this.log.debug("entre al filtro de modulos");
			//agregarCondicional(condicionPrevia, bfr);
			StringBuffer strModulos = new StringBuffer();
			int lstSize = modulos.size();
			for(int i=0; i<modulos.size(); i++){
				Modulo modulo = (Modulo)modulos.get(i);
				strModulos.append(modulo.getIdModulo());
				if( (lstSize-1) > i){
					strModulos.append(", ");
				}
			}
			bfr.append(" join catTramite.dicModulos dicModulos ");
			bfr.append(" where dicModulos.cveIdModulo in ("+strModulos + ")");
		}
		bfr.append(" order by catTramite.desTipoTramite asc ");		
		
		Query query = this.em.createQuery(bfr.toString());
		lstDicTipoTramite  =  query.getResultList();
		
		if(lstDicTipoTramite!= null && !lstDicTipoTramite.isEmpty()){
			lstTipoTramite = new  ArrayList();
			for(DicTipoTramite dicTipoTramite : lstDicTipoTramite){
				TipoTramite tipoTram = new TipoTramite();
				tipoTram.setDescripcion(dicTipoTramite.getDesTipoTramite());
				tipoTram.setIdTipoTramite(dicTipoTramite.getCveIdTipoTramite().intValue());
				lstTipoTramite.add(tipoTram);
			}
		}
		
		return lstTipoTramite;
		
	}
	
}
