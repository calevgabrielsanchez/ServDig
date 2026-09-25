package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.utility.DocumentoProbatorioServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser.DoctoReqTramiteParser;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser.DocumentoParser;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser.DocumentoPorTipoParser;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser.DocumentoProbatorioParser;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser.TipoDocumentoProbatorioParser;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoProbatorioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CURP;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DoctoReqTramite;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DoctoReqTramiteOrigenSol;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.persistence.DgCatMunicipio;
import mx.gob.imss.ctirss.delta.persistence.DgCatMunicipioPK;
import mx.gob.imss.ctirss.delta.persistence.DicDocumento;
import mx.gob.imss.ctirss.delta.persistence.DicTipoDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.persistence.DitActa;
import mx.gob.imss.ctirss.delta.persistence.DitCurp;
import mx.gob.imss.ctirss.delta.persistence.DitDoctoReqTramOrigenSol;
import mx.gob.imss.ctirss.delta.persistence.DitDoctoReqTramite;
import mx.gob.imss.ctirss.delta.persistence.DitDoctosPersona;
import mx.gob.imss.ctirss.delta.persistence.DitDoctosPersonaPK;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoPorTipo;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.persistence.DitNacimiento;

@Stateless(name="documentoProbatorioServiceEntity", mappedName = "documentoProbatorioServiceEntity")
public class DocumentoProbatorioServiceEntity extends AbstractServiceEntity
		implements DocumentoProbatorioServiceEntityLocal {

	@EJB
	private DocumentoProbatorioServiceUtilityLocal utility;

	
	@Override
	public Long getNumeroDocumentosProbatoriosActivos(Long idTipoTramite,
			Boolean obligatorio) {
		
		Long cantidadDocumentos = 0L;
		Criteria query = this.getSession().createCriteria(DitDoctoReqTramite.class);
		query.setProjection(Projections.rowCount());
		query.createAlias("dicTipoTramite", "tipo");
		query.add(Restrictions.eq("tipo.cveIdTipoTramite", idTipoTramite));
		
		Criteria docto = query.createCriteria("ditDocumentoPorTipo");
		docto.createAlias("dicTipoDocumentoProbatorio", "tipoDocto");
		docto.add(Restrictions.ne("tipoDocto.cveIdTipoDocumentoProbator", TipoDocumentoProbatorioEnum.RESULTANTE.getId()));
		
		if(obligatorio != null) {
			query.add(Restrictions.eq("indDoctoReqCaptura", obligatorio ? 1 : 0));
		}
		
		query.add(Restrictions.isNull("fecRegistrosBaja"));
		
		cantidadDocumentos = (Long) query.uniqueResult();
		
		return cantidadDocumentos;
	}

	@Override
	public List<DocumentoProbatorio> registrarDocumentos(
			List<DocumentoProbatorio> documentos) {

		this.log.debug("Entrando a registrarDocumentos!");

		Iterator<DocumentoProbatorio> itDocProbatorios = documentos.iterator();
		DitDocumentoProbatorio ditDocumentoProbatorio = null;
		DocumentoProbatorio documentoProbatorio = null;

		while (itDocProbatorios.hasNext()) {
			try {

				documentoProbatorio = itDocProbatorios.next();
				
				ditDocumentoProbatorio = utility
						.transformarDocumentoProbatorio(documentoProbatorio);
				
				if(documentoProbatorio instanceof Nacimiento){
					
					DitActa ditActa = ditDocumentoProbatorio.getDitActa();
					DitNacimiento ditNacimiento = ditActa.getDitNacimiento();
					ditDocumentoProbatorio.setDitActa(null);
					ditActa.setDitDocumentoProbatorio(null);
					ditActa.setDitNacimiento(null);
					ditNacimiento.setDitActa(null);
	
					this.log.debug("Se va a guardar el ditDocumentoProbatorio!");
					this.em.persist(ditDocumentoProbatorio);
					this.em.flush();
					this.log.debug("Se guardo exitosamente el ditDocumentoProbatorio!");
	
					this.log.debug("Se va a guardar el ditActa!");
					ditActa.setCveIdDocumentoProbatorio(ditDocumentoProbatorio
							.getCveIdDocumentoProbatorio());
					this.em.persist(ditActa);
					this.log.debug("Se guardo exitosamente el ditActa!");
	
					this.log.debug("Se va a guardar el ditNacimiento!");
					ditNacimiento
							.setCveIdDocumentoProbatorio(ditDocumentoProbatorio
									.getCveIdDocumentoProbatorio());
					this.em.persist(ditNacimiento);
					this.log.debug("Se guardo exitosamente el ditNacimiento!");
	
					
					this.em.flush();
					
					
					//Se settea el id de docProbatorio
					documentoProbatorio
							.setIdDocumentoProbatorio(ditDocumentoProbatorio
									.getCveIdDocumentoProbatorio().intValue());
					
				}else if( documentoProbatorio instanceof CURP){
					
					//Documento probatorio del CURP
					
					this.log.debug("Guardando un documento probatorio tipo CURP");
					DitCurp ditCurp = ditDocumentoProbatorio.getDitCurp();
					ditDocumentoProbatorio.setDitCurp(null);
					this.em.persist(ditDocumentoProbatorio);
					this.em.flush();
					
					this.log.debug("Guardando del detalle del documento CURP");
					
					
					ditCurp.setCveIdDocumentoProbatorio(ditDocumentoProbatorio.getCveIdDocumentoProbatorio());
					this.em.persist(ditCurp);
					this.em.flush();
					
					
					documentoProbatorio
					.setIdDocumentoProbatorio(ditDocumentoProbatorio
							.getCveIdDocumentoProbatorio().intValue());
				}

			} catch (TransformacionException e) {
				log.error("Hubo error en la transformacion del documento probatorio -> "
						+ e.getMessage());
			}
		}

		return documentos;
	}

	@Override
	public List<DocumentoProbatorio> consultarDocumentosDePersona(
			Persona persona) {

		StringBuffer consulta = new StringBuffer();

		consulta.append("select doctosPersona from DitDoctosPersona doctosPersona ");
		consulta.append("join doctosPersona.ditDocumentoProbatorio documento  ");
		consulta.append("where doctosPersona.id.cveIdPersona = :idPersona");

		Query query = this.em.createQuery(consulta.toString());
		query.setParameter("idPersona", persona.getIdPersona());

		List<DitDoctosPersona> documentosPersona = query.getResultList();

		Iterator<DitDoctosPersona> itDocsPersona = documentosPersona.iterator();
		List<DocumentoProbatorio> documentosProbatorios = new ArrayList<DocumentoProbatorio>();
		DocumentoProbatorio documentoProbatorio = null;

		while (itDocsPersona.hasNext()) {
			try {
				documentoProbatorio = utility
						.transformarDocumentoProbatorio(itDocsPersona.next()
								.getDitDocumentoProbatorio());

				if (documentoProbatorio != null) {
					documentosProbatorios.add(documentoProbatorio);
				}

			} catch (TransformacionException e) {
				e.printStackTrace();
			}

		}

		return documentosProbatorios;
	}
	
	@Override
	public void asociarDocumentoAPersona(DocumentoProbatorio documento,
			Long cvePersona) throws DocumentoProbatorioException {

		this.log.debug("Se va a asociar el documento probatorio "
				+ documento.getIdDocumentoProbatorio() + " a la persona "
				+ cvePersona);

		if (documento != null && documento.getIdDocumentoProbatorio() != null
				&& cvePersona != null) {
			DitDoctosPersonaPK pkPersona = new DitDoctosPersonaPK();

			pkPersona.setCveIdDocumentoProbatorio(documento
					.getIdDocumentoProbatorio());
			pkPersona.setCveIdPersona(cvePersona);

			DitDoctosPersona ditDoctoPersona = new DitDoctosPersona();
			ditDoctoPersona.setId(pkPersona);

			this.em.persist(ditDoctoPersona);
		} else {
			throw new DocumentoProbatorioException(
					"No se puede asociar el documento probatorio a la persona, ya que no se cuenta con ambos identificadores");
		}
	}

	@Override
	public void modificarDocumentoProbatorio(
			DocumentoProbatorio documentoProbatorio) throws DocumentoProbatorioException {

		this.log.debug("Se va a modificar el documento probatorio "
				+ documentoProbatorio.getIdDocumentoProbatorio());
		
		if(documentoProbatorio instanceof Nacimiento){
			DitNacimiento ditNacimiento = this.em.find(DitNacimiento.class, documentoProbatorio.getIdDocumentoProbatorio().longValue());
			Nacimiento nacimiento = (Nacimiento) documentoProbatorio;
			
			ditNacimiento.setNumAnio(nacimiento.getAnio());
			ditNacimiento.setCveCrip(nacimiento.getCrip());
			ditNacimiento.getDitActa().setNumFoja(nacimiento.getNoFoja());
			ditNacimiento.getDitActa().setNumLibro(nacimiento.getNoLibro());
			ditNacimiento.getDitActa().setNumActa(nacimiento.getNoActa());
			ditNacimiento.getDitActa().setRefNumTomo(nacimiento.getTomo());
			
			DgCatMunicipioPK id = new DgCatMunicipioPK();
			id.setCveEnt(nacimiento.getMunicipio().getEntidadFederativa().getClave());
			id.setCveMun(nacimiento.getMunicipio().getClave());
			
			DgCatMunicipio dgCatMunicipio = new DgCatMunicipio();
			dgCatMunicipio.setId(id);
			
			ditNacimiento.getDitActa().setDgCatMunicipio(dgCatMunicipio);
			
			ditNacimiento.getDitDocumentoProbatorio().setFecRegistroActualizado(new Date());
		} else if(documentoProbatorio instanceof CURP){
			DitCurp ditDocumentoRenapo = this.em.find(DitCurp.class, documentoProbatorio.getIdDocumentoProbatorio().longValue());
			CURP documentoProbatorioRENAPO = (CURP) documentoProbatorio;
			
			if(documentoProbatorioRENAPO.getAnioRegistro() != null){
				ditDocumentoRenapo.setNumAnio(new BigDecimal(documentoProbatorioRENAPO.getAnioRegistro()));
			}
			
			ditDocumentoRenapo.setNumFolioExtranjero(documentoProbatorioRENAPO.getNumFolioExtranjero());
			ditDocumentoRenapo.setNumActa(documentoProbatorioRENAPO.getNoActa());
			
			ditDocumentoRenapo.getDitDocumentoProbatorio().setFecRegistroActualizado(new Date());
		}
		
	}

	@Override
	public void eliminarDesasociarDocumentoProbatorioPersona(
			DocumentoProbatorio documentoProbatorio, Long cvePersona) {

		this.log.debug("Se va a eliminar el documento probatorio "
				+ documentoProbatorio.getIdDocumentoProbatorio());

		DitDocumentoProbatorio ditDocumento = DocumentoProbatorioParser
				.modelToPersist(documentoProbatorio);

		ditDocumento = this.em.find(DitDocumentoProbatorio.class,
				ditDocumento.getCveIdDocumentoProbatorio());
		this.em.remove(ditDocumento);

	}

	@Override
	public List<DoctoReqTramite> obtenerListaDeDocumentos(
			Long cveIdTipoTramite, Boolean resultantes) {
		
		List<DitDoctoReqTramite> ditDoctoReqTramites = null;
		List<DoctoReqTramite> doctoReqTramites = null;
		
		Criteria queryDocto = this.getSession().createCriteria(DitDoctoReqTramite.class);
		queryDocto.createAlias("dicTipoTramite", "tipo");
		queryDocto.add(Restrictions.eq("tipo.cveIdTipoTramite", cveIdTipoTramite));
		queryDocto.add(Restrictions.isNull("fecRegistrosBaja"));
		
		Criteria tipoDoc = queryDocto.createCriteria("ditDocumentoPorTipo");
		
		Criteria tipoDocumento = tipoDoc.createCriteria("dicTipoDocumentoProbatorio");
		if(resultantes) {
			tipoDocumento.add(Restrictions.eq("cveIdTipoDocumentoProbator", TipoDocumentoProbatorioEnum.RESULTANTE.getId()));
		} else {
			tipoDocumento.add(Restrictions.ne("cveIdTipoDocumentoProbator", TipoDocumentoProbatorioEnum.RESULTANTE.getId()));
		}
		
		ditDoctoReqTramites = queryDocto.list();
		
		if(ditDoctoReqTramites != null && !ditDoctoReqTramites.isEmpty()) {
			doctoReqTramites = DoctoReqTramiteParser.persistToModelList(ditDoctoReqTramites);
		}
		
		return doctoReqTramites;
	}

	@Override
	public List<Documento> obtenerListaDeDocumentos(List<Long> tiposTramite,
			Boolean resultantes) {
		List<DicDocumento> dicDocumentos = null;
		List<Documento> documentos = null;
		
		Criteria queryDocto = this.getSession().createCriteria(DitDocumentoPorTipo.class);
		queryDocto.setProjection(Projections.distinct(Projections.property("dicDocumento")));
		
		Criteria queryTipoTramite = queryDocto.createCriteria("ditDoctoReqTramites");
		queryTipoTramite.createAlias("dicTipoTramite", "tipo");
		queryTipoTramite.add(Restrictions.isNull("fecRegistrosBaja"));
		queryDocto.add(Restrictions.in("tipo.cveIdTipoTramite", tiposTramite));
		
		Criteria queryResultantes = queryDocto.createCriteria("dicTipoDocumentoProbatorio");
		if(resultantes) {
			queryResultantes.add(Restrictions.eq("cveIdTipoDocumentoProbator", TipoDocumentoProbatorioEnum.RESULTANTE.getId()));
		} else {
			queryResultantes.add(Restrictions.ne("cveIdTipoDocumentoProbator", TipoDocumentoProbatorioEnum.RESULTANTE.getId()));
		}
		
		dicDocumentos = queryDocto.list();
		
		if(dicDocumentos != null && !dicDocumentos.isEmpty()) {
			documentos = DocumentoParser.PersistToModelList(dicDocumentos);
		}
		
		return documentos;
	}

	@Override
	public List<DocumentoPorTipo> obtenerDocumentosPorTipoByTipoTramite(
			List<Long> tiposTramite) {
		List<DitDocumentoPorTipo> dicDocumentos = null;
		List<DocumentoPorTipo> documentos = null;
		
		Criteria queryDocto = this.getSession().createCriteria(DitDocumentoPorTipo.class);
		
		Criteria queryTipoTramite = queryDocto.createCriteria("ditDoctoReqTramites");
		queryTipoTramite.createAlias("dicTipoTramite", "tipo");
		queryTipoTramite.add(Restrictions.isNull("fecRegistrosBaja"));
		queryDocto.add(Restrictions.in("tipo.cveIdTipoTramite", tiposTramite));
		
		Criteria queryResultantes = queryDocto.createCriteria("dicTipoDocumentoProbatorio");
		queryResultantes.add(Restrictions.ne("cveIdTipoDocumentoProbator", TipoDocumentoProbatorioEnum.RESULTANTE.getId()));
		
		dicDocumentos = queryDocto.list();
		
		if(dicDocumentos != null && !dicDocumentos.isEmpty()) {
			try {
				documentos = DocumentoPorTipoParser.PersistToModelList(dicDocumentos);
			} catch (DocumentoProbatorioException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return documentos;
	}

	@Override
	public List<TipoDocumentoProbatorio> getTiposDocumentoProbatorios() {
		List<TipoDocumentoProbatorio> tipos = null;
		List<DicTipoDocumentoProbatorio> dicTipos = null;
		
		Criteria queryTipos = this.getSession().createCriteria(DicTipoDocumentoProbatorio.class);
		dicTipos = queryTipos.list();
		
		if(dicTipos != null && !dicTipos.isEmpty()) {
			tipos = TipoDocumentoProbatorioParser.persistToModelList(dicTipos);
		}
		
		return tipos;
	}
	
	@Override
	public void eliminarDocumentoProbatorio(DocumentoProbatorio documentoProbatorio) {

		this.log.debug("Se va a eliminar el documento probatorio "
				+ documentoProbatorio.getIdDocumentoProbatorio());

		DitDocumentoProbatorio ditDocumento = this.em.find(DitDocumentoProbatorio.class,
				documentoProbatorio.getIdDocumentoProbatorio().longValue());
		ditDocumento.setFecRegistroBaja(new Date());
				
		this.em.persist(ditDocumento);

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DoctoReqTramiteOrigenSol> getDocumentosReqTramiteOrigenSol(
			Long cveIdTipoTramite, Boolean resultantes, Long cveIdOrigenSolicitud) throws DocumentoProbatorioException {
		
		List<DitDoctoReqTramOrigenSol> ditDoctoReqTramOrigenSol = null;
		List<DoctoReqTramiteOrigenSol> doctoReqTramites = null;
		try {
		Criteria queryDocto = this.getSession().createCriteria(DitDoctoReqTramOrigenSol.class);
		queryDocto.createAlias("dicTipoTramite", "tipo");
		queryDocto.createAlias("dicOrigenSolicitud", "origen");
		queryDocto.add(Restrictions.eq("tipo.cveIdTipoTramite", cveIdTipoTramite));
		queryDocto.add(Restrictions.eq("origen.cveIdOrigenSolicitud", cveIdOrigenSolicitud));
		queryDocto.add(Restrictions.isNull("fecRegistrosBaja"));
		
		Criteria tipoDoc = queryDocto.createCriteria("ditDocumentoPorTipo");
		
		Criteria tipoDocumento = tipoDoc.createCriteria("dicTipoDocumentoProbatorio");
		if(resultantes) {
			tipoDocumento.add(Restrictions.eq("cveIdTipoDocumentoProbator", TipoDocumentoProbatorioEnum.RESULTANTE.getId()));
		} else {
			tipoDocumento.add(Restrictions.ne("cveIdTipoDocumentoProbator", TipoDocumentoProbatorioEnum.RESULTANTE.getId()));
		}
		
		ditDoctoReqTramOrigenSol = queryDocto.list();
		
		if(ditDoctoReqTramOrigenSol != null && !ditDoctoReqTramOrigenSol.isEmpty()) {
			doctoReqTramites = DoctoReqTramiteParser.persistToModelOrigenSolList(ditDoctoReqTramOrigenSol);
		}
		
		return doctoReqTramites;
		}catch(Exception e) {
			log.error("error al consultar los documentos por tipo tramite y origen " , e);
			throw new DocumentoProbatorioException(e.getMessage());
		}
	}
	
	@Override
	public List<Documento> obtenerDocumentosMovPatInternet(
			List<Long> tiposTramite, Boolean resultantes) {

		log.debug(":: Parametros obtenerDocumentosPorTipoByTipoTramiteOrigenSol, tiposTramite: " + tiposTramite + ", resultantes: " + resultantes);
		
		List<DitDoctoReqTramOrigenSol> ditDoctoReqTramOrigenSol = null;
		List<Documento> documentos = new ArrayList<Documento>();
		List<Long> tiposPersona = new ArrayList<Long>();
		tiposPersona.add(new Long(0));
		tiposPersona.add(new Long(1));
		tiposPersona.add(new Long(2));

		Criteria queryDocto = this.getSession().createCriteria(DitDoctoReqTramOrigenSol.class);
		queryDocto.createAlias("dicTipoTramite", "tipo");
		queryDocto.createAlias("dicOrigenSolicitud", "origen");
		queryDocto.add(Restrictions.in("tipo.cveIdTipoTramite", tiposTramite));
		queryDocto.add(Restrictions.eq("origen.cveIdOrigenSolicitud", OrigenSolicitudEnum.INTERNET.getId() ));
		queryDocto.add(Restrictions.isNull("fecRegistrosBaja"));	
		
		queryDocto.add(Restrictions.in("cveIdTipoPersona", tiposPersona));
		queryDocto.addOrder(Order.asc("indOrdenLista"));
		
		Criteria tipoDoc = queryDocto.createCriteria("ditDocumentoPorTipo");
		
		Criteria tipoDocumento = tipoDoc.createCriteria("dicTipoDocumentoProbatorio");
		if(resultantes) {
			tipoDocumento.add(Restrictions.eq("cveIdTipoDocumentoProbator", TipoDocumentoProbatorioEnum.RESULTANTE.getId()));
		} else {
			tipoDocumento.add(Restrictions.ne("cveIdTipoDocumentoProbator", TipoDocumentoProbatorioEnum.RESULTANTE.getId()));
		}
		
		ditDoctoReqTramOrigenSol = queryDocto.list();
		
		if(ditDoctoReqTramOrigenSol != null && !ditDoctoReqTramOrigenSol.isEmpty()) {
			for (Iterator<DitDoctoReqTramOrigenSol> iterator = ditDoctoReqTramOrigenSol.iterator(); iterator.hasNext();) {
				documentos.add(DoctoReqTramiteParser.persistToModelMovPatInternet(iterator.next()));
			}
		}
		
		return documentos;
		
		
	}
	
//	@Override
//	public List<DocumentoProbatorio> consultarDocumentosDePersona(
//			Persona persona) {
//
//		StringBuffer consulta = new StringBuffer();
//
//		consulta.append("select doctosPersona from DitDoctosPersona doctosPersona ");
//		consulta.append("join doctosPersona.ditDocumentoProbatorio documento  ");
//		consulta.append("where doctosPersona.id.cveIdPersona = :idPersona");
//
//		Query query = this.em.createQuery(consulta.toString());
//		query.setParameter("idPersona", persona.getIdPersona());
//
//		List<DitDoctosPersona> documentosPersona = query.getResultList();
//
//		Iterator<DitDoctosPersona> itDocsPersona = documentosPersona.iterator();
//		List<DocumentoProbatorio> documentosProbatorios = new ArrayList<DocumentoProbatorio>();
//		DocumentoProbatorio documentoProbatorio = null;
//
//		while (itDocsPersona.hasNext()) {
//			try {
//				documentoProbatorio = utility
//						.transformarDocumentoProbatorio(itDocsPersona.next()
//								.getDitDocumentoProbatorio());
//
//				if (documentoProbatorio != null) {
//					documentosProbatorios.add(documentoProbatorio);
//				}
//
//			} catch (TransformacionException e) {
//				e.printStackTrace();
//			}
//
//		}
//
//		return documentosProbatorios;
//	}
	
	
}
