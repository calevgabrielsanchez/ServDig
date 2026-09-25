package mx.imss.estrados.service.ejb.dao.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import mx.imss.estrados.dto.SsoVwUsuarioDTO;
import mx.imss.estrados.entity.NeeCatStatus;
import mx.imss.estrados.entity.NeeCatTipodocumento;
import mx.imss.estrados.entity.NeeDocumentosAdjuntos;
import mx.imss.estrados.entity.NeeNotificaciones;
import mx.imss.estrados.entity.SsoVwUsuario;
import mx.imss.estrados.paginado.dto.PaginadoRequest;
import mx.imss.estrados.repository.AbstractRespository;
import mx.imss.estrados.service.ejb.dao.ConsultaInternaDAO;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@Stateless
public class ConsultaInternaDAOImpl extends AbstractRespository implements ConsultaInternaDAO {
	
	/**
	 * Logger
	 */
	private final static Logger logger = Logger.getLogger(ConsultaExternaDAOImpl.class);
	
	/**
	 * Metodo para obtener el numero de registros de las notificaciones existentes en la base de datos
	 * @return Integer
	 */
	@Override
	public Integer contarTotalRegistros() {
		Criteria criteria = getSession().createCriteria(NeeNotificaciones.class);
		criteria.setProjection(Projections.rowCount());
		Integer totalRegistros = ((Long) criteria.uniqueResult()).intValue();
		return totalRegistros;
	}
	
	/**
	 * Metodo para obtener el numero de registros filtrados de las notificaciones existentes en la base de datos
	 * @return Integer
	 */
	@Override
	public Integer contarRegistrosFiltrados(PaginadoRequest paginadoRequest) {
		Criteria criteria = getSession().createCriteria(NeeNotificaciones.class);
		if (!paginadoRequest.getSearch().isEmpty()) {
			criteria.add(Restrictions.like("razonSocial", "%"+paginadoRequest.getSearch().toUpperCase()+"%"));
		}
		if (paginadoRequest.getSearchColumnaDocumento() != null && !paginadoRequest.getSearchColumnaDocumento().isEmpty()) {
			criteria.add(Restrictions.eq("neeCatTipodocumento.cveTipodocto", obtenerClaveTipoDocumento(paginadoRequest)));
		}
		if (paginadoRequest.getSearchColumnaStatus() != null && !paginadoRequest.getSearchColumnaStatus().isEmpty()) {
			criteria.add(Restrictions.eq("neeCatStatus.cveStatus", obtenerClaveStatus(paginadoRequest)));
		}
		if (paginadoRequest.getFiltroUsuarioSession().getIdDelegacion() != null) {
			criteria.add(Restrictions.eq("neeCatDelegacion.cveIdDelegacion", paginadoRequest.getFiltroUsuarioSession().getIdDelegacion()));
			criteria.add(Restrictions.eq("cveDepto", paginadoRequest.getFiltroUsuarioSession().getCveSSODepto()));
		}
		if (paginadoRequest.getFiltroUsuarioSession().getIdSubdelegacion() != null) {
			criteria.add(Restrictions.eq("neeCatSubdelegacion.cveIdSubdelegacion", paginadoRequest.getFiltroUsuarioSession().getIdSubdelegacion()));
			criteria.add(Restrictions.eq("cveDepto", paginadoRequest.getFiltroUsuarioSession().getCveSSODepto()));
		}
		criteria.setProjection(Projections.rowCount());
		Integer totalRegistrosMostrar = ((Long) criteria.uniqueResult()).intValue();
		return totalRegistrosMostrar;
	}
	
	/**
	 * Metodo para obtener un listado filtrado de las notificaciones existentes en la base de datos
	 * @param PaginadoRequest
	 * @return List<NeeNotificaciones>
	 */
	@Override
	public List<NeeNotificaciones> filtrar(PaginadoRequest paginadoRequest) {
		Criteria criteria = getSession().createCriteria(NeeNotificaciones.class);
		criteria.setFirstResult(paginadoRequest.getDisplayStart());
		criteria.setMaxResults(paginadoRequest.getDisplayLength());
		if (!paginadoRequest.getSearch().isEmpty()) {
			criteria.add(Restrictions.like("razonSocial", "%"+paginadoRequest.getSearch().toUpperCase()+"%"));
		}
		if (paginadoRequest.getSearchColumnaDocumento() != null && !paginadoRequest.getSearchColumnaDocumento().isEmpty()) {
			criteria.add(Restrictions.eq("neeCatTipodocumento.cveTipodocto", obtenerClaveTipoDocumento(paginadoRequest)));
		}
		if (paginadoRequest.getSearchColumnaStatus() != null && !paginadoRequest.getSearchColumnaStatus().isEmpty()) {
			criteria.add(Restrictions.eq("neeCatStatus.cveStatus", obtenerClaveStatus(paginadoRequest)));
		}
		if (paginadoRequest.getFiltroUsuarioSession().getIdDelegacion() != null) {
			criteria.add(Restrictions.eq("neeCatDelegacion.cveIdDelegacion", paginadoRequest.getFiltroUsuarioSession().getIdDelegacion()));
			criteria.add(Restrictions.eq("cveDepto", paginadoRequest.getFiltroUsuarioSession().getCveSSODepto()));
		}
		if (paginadoRequest.getFiltroUsuarioSession().getIdSubdelegacion() != null) {
			criteria.add(Restrictions.eq("neeCatSubdelegacion.cveIdSubdelegacion", paginadoRequest.getFiltroUsuarioSession().getIdSubdelegacion()));
			criteria.add(Restrictions.eq("cveDepto", paginadoRequest.getFiltroUsuarioSession().getCveSSODepto()));
		}
		String sortColum = null;
		switch (paginadoRequest.getFiltroColumna().getSortCol()) {
	        case 1:
	        	sortColum = "razonSocial";
	            break;
	        case 2:
	        	sortColum = "neeCatTipodocumento";
	            break;
	        case 3:
	        	sortColum = "fecPublicacion";
	            break;
	        case 4:
	        	sortColum = "neeCatStatus";
	            break;
	    }
		if (paginadoRequest.getFiltroColumna().getSortDir().equals("asc")) {
			criteria.addOrder( Order.asc(sortColum) );
			criteria.addOrder( Order.asc("cveNotificaciones") );
		} else {
			criteria.addOrder( Order.desc(sortColum) );
			criteria.addOrder( Order.desc("cveNotificaciones") );
		}
		List<NeeNotificaciones> listNeeNotificaciones = (List<NeeNotificaciones>) criteria.list();
		return listNeeNotificaciones;
	}
	
	/**
	 * Metodo para obtener de la base de datos el catalogo de Tipo de Documento
	 */
	@Override
	public List<NeeCatTipodocumento> obtenerCatalogoTipoDocumento() {
		List<NeeCatTipodocumento> listNeeCatTipodocumentos;
		Criteria criteria = getSession().createCriteria(NeeCatTipodocumento.class);
		listNeeCatTipodocumentos = criteria.list();
		return listNeeCatTipodocumentos;
	}
	
	/**
	 * Metodo para obtener de la base de datos el catalogo de status
	 */
	@Override
	public List<NeeCatStatus> obtenerCatalogoStatus() {
		List<NeeCatStatus> listNeeCatStatus;
		Criteria criteria = getSession().createCriteria(NeeCatStatus.class);
		
		listNeeCatStatus = criteria.list();
		return listNeeCatStatus;
	}

	/**
	 * Metodo para obtener de la base de datos la clave del Tipo de Documento
	 * @param paginadoRequest
	 * @return Integer
	 */
	public Integer obtenerClaveTipoDocumento(PaginadoRequest paginadoRequest) {
		Criteria criteria = getSession().createCriteria(NeeCatTipodocumento.class);
		criteria.add(Restrictions.eq("desTipodocumento", paginadoRequest.getSearchColumnaDocumento()));
		NeeCatTipodocumento neeCatTipodocumento = (NeeCatTipodocumento) criteria.list().get(0);
		Integer claveTipoDocumento = neeCatTipodocumento.getCveTipodocto();
		return claveTipoDocumento;
	}
	
	/**
	 * Metodo para obtener de la base de datos la clave del Status
	 * @param paginadoRequest
	 * @return Integer
	 */
	public Integer obtenerClaveStatus(PaginadoRequest paginadoRequest) {
		Criteria criteria = getSession().createCriteria(NeeCatStatus.class);
		criteria.add(Restrictions.eq("desEstatus", paginadoRequest.getSearchColumnaStatus()));
		NeeCatStatus neeCatStatus = (NeeCatStatus) criteria.list().get(0);
		Integer claveStatus = neeCatStatus.getCveStatus();
		return claveStatus;
	}
	
	/**
	 * Metodo para obtener de la base de datos el documento adjunto de una notificacion
	 * @param NeeDocumentosAdjuntos
	 * @return NeeDocumentosAdjuntos
	 */
	@Override
	public NeeDocumentosAdjuntos obtenerDocumentoAdjunto(NeeDocumentosAdjuntos neeDocumentosAdjuntos) {
		Criteria criteria = getSession().createCriteria(NeeDocumentosAdjuntos.class);
		criteria.add(Restrictions.eq("cveDoctoAdjunto", neeDocumentosAdjuntos.getCveDoctoAdjunto()));
		criteria.add(Restrictions.eq("neeNotificaciones.cveNotificaciones", neeDocumentosAdjuntos.getNeeNotificaciones().getCveNotificaciones()));
		neeDocumentosAdjuntos = (NeeDocumentosAdjuntos) criteria.list().get(0);
		return neeDocumentosAdjuntos;
	}

	@Override
	public SsoVwUsuario obtenerAreaNormativa(String cveUsuario) {
		SsoVwUsuario ssoVwUsuario;
		System.out.println("Entra 3 CURP");
		Criteria criteria = getSession().createCriteria(SsoVwUsuario.class);
		criteria.add(Restrictions.eq("desUsrCURP", cveUsuario));
		ssoVwUsuario = (SsoVwUsuario) criteria.list().get(0);
		return ssoVwUsuario;
	}
	
	/**
	 * Metodo para obtener el numero de registros filtrados de las notificaciones existentes en la base de datos
	 * @return Integer
	 */
	@Override
	public Integer contarRegistrosFiltradosManual(PaginadoRequest paginadoRequest) {
		Criteria criteria = getSession().createCriteria(NeeNotificaciones.class);
		if (!paginadoRequest.getSearch().isEmpty()) {
			criteria.add(Restrictions.like("razonSocial", "%"+paginadoRequest.getSearch().toUpperCase()+"%"));
		}
		if (paginadoRequest.getSearchColumnaDocumento() != null && !paginadoRequest.getSearchColumnaDocumento().isEmpty()) {
			criteria.add(Restrictions.eq("neeCatTipodocumento.cveTipodocto", obtenerClaveTipoDocumento(paginadoRequest)));
		}
		if (paginadoRequest.getSearchColumnaStatus() != null && !paginadoRequest.getSearchColumnaStatus().isEmpty()) {
			criteria.add(Restrictions.eq("neeCatStatus.cveStatus", obtenerClaveStatus(paginadoRequest)));
		}
		if (paginadoRequest.getFiltroUsuarioSession().getIdDelegacion() != null) {
			criteria.add(Restrictions.eq("neeCatDelegacion.cveIdDelegacion", paginadoRequest.getFiltroUsuarioSession().getIdDelegacion()));
			criteria.add(Restrictions.eq("cveDepto", paginadoRequest.getFiltroUsuarioSession().getCveSSODepto()));
		}
		if (paginadoRequest.getFiltroUsuarioSession().getIdSubdelegacion() != null) {
			criteria.add(Restrictions.eq("neeCatSubdelegacion.cveIdSubdelegacion", paginadoRequest.getFiltroUsuarioSession().getIdSubdelegacion()));
			criteria.add(Restrictions.eq("cveDepto", paginadoRequest.getFiltroUsuarioSession().getCveSSODepto()));
		}
		
		//criteria.add(Restrictions.eq("neeCatSubdelegacion.fecPublicacion", paginadoRequest.));
		
		criteria.setProjection(Projections.rowCount());
		Integer totalRegistrosMostrar = ((Long) criteria.uniqueResult()).intValue();
		return totalRegistrosMostrar;
	}

	@Override
	public Integer contarRegistrosFiltrados(Integer idEstatus, Date fechaEjecuta,
			SsoVwUsuarioDTO ssoVwUsuarioDTO) {
		Criteria criteria = getSession().createCriteria(NeeNotificaciones.class);
		
		if (idEstatus == 1) {
			criteria.add(Restrictions.eq("fecPublicacion", fechaEjecuta)).add(Restrictions.eq("neeCatStatus.cveStatus", idEstatus));
		} else {
			criteria.add(Restrictions.eq("fecRetiroPublicacion", fechaEjecuta)).add(Restrictions.eq("neeCatStatus.cveStatus", idEstatus));
		}
			
			
		criteria.setProjection(Projections.rowCount());
		Integer totalRegistrosMostrar = ((Long) criteria.uniqueResult()).intValue();
		return totalRegistrosMostrar;
	}
	
}
