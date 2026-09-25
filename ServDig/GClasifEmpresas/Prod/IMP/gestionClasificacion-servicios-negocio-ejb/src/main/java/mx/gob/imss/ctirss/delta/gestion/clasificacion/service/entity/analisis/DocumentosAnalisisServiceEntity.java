/**
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo:DocumentosAnalisisServiceEntity.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.analisis
 *  @Fecha:29/10/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.analisis;
 
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Constantes;
import mx.gob.imss.ctirss.delta.model.clasificacion.ElementoBitacora;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisEnum;
import mx.gob.imss.ctirss.delta.persistence.DitDoctoResultanteTramite;

import org.hibernate.Criteria;
import org.hibernate.NonUniqueResultException;
import org.hibernate.criterion.Restrictions;

@Stateless
public class DocumentosAnalisisServiceEntity extends AbstractServiceEntity implements DocumentosAnalisisServiceEntityLocal{
	
	
	@Override
	public Long getIdDocumentoPorTipoIdTramite(Long idTramite,
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
			return doctoRes.getCveIdDocResultante();
		}
		
		return null;
	}
	
	@Override
	public byte[] getRefDocumentoPorTipoIdTramite(Long cveIdDocResultante,
			int tipoDocumento) {
		
		Criteria consultaDocto = this.getSession().createCriteria(DitDoctoResultanteTramite.class);

		consultaDocto.add(Restrictions.eq("cveIdDocResultante", cveIdDocResultante));

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

	
	/**
	 * Obtiene el id de un documento de análisis según su tipo.
	 * @param cveIdSolicitud, tipoDocumento
	 * @return Long
	 * @throws PersistenceException
	 */
	public String obtenIdDocumento(Long cveIdSolicitud, int tipoDocumento) throws PersistenceException{

		List<Object[]> response= null;
		String id = null;
		String acuse = null;
		
		try{
			String strquery = "select dc.cveIdClem, dc.acuse from DitDatosClem dc, DitAnalisisCe ace, DitSolicitud s where s.cveIdSolicitud = "
					+ cveIdSolicitud
					+ " and ace.cveIdSolicitud = s.cveIdSolicitud and dc.ditAnalisisCe.cveIdAnalisis = ace.cveIdAnalisis and dc.fecRegistroBaja is null";
			;
			
			//Obtendra el nombre del campo clave y el campo descripcion del modelo pasado como parametro
			log.info("Query de consulta = "+ strquery);
			org.hibernate.Query query = this.getSession().createQuery(strquery.toString());
			response = query.list();
			for (Object[] obj : response) {
				//id = new Long(((BigDecimal)obj[1]).toString()).toString();
				id = ((Long)obj[0]).toString();				
				if(obj.length > 1 && obj[1] != null){
					acuse = (String) obj[1];
				}
			}
		}catch(RuntimeException re){
			re.printStackTrace();
			throw new PersistenceException(re);
		}	
		
		String ret = null;
		if(id != null){
			ret = id;
		}else{
			return null;
		}
		if(acuse != null){
			ret += "|" + acuse;
		}
		
		return ret;
	}
	
	
	/**
	 * Obtiene el arreglo de Bytes de un documento de análisis según su tipo.
	 * @param id, tipoDocumento
	 * @return byte[]
	 * @throws PersistenceException
	 */
	public byte[] obtenRefDocumento(Long id, int tipoDocumento) throws PersistenceException{

		byte[] refDocumento = null;
		
		String strQuery = "";
		switch(tipoDocumento){
			case Constantes.TIPO_DOCUMENTO_CLEM : 
				strQuery = Constantes.QUERY_REF_DOCUMENTO_ANALISIS_CLEM;
				break;
//			case Constantes.TIPO_DOCUMENTO_AVISO :
//				strQuery = Constantes.QUERY_REF_DOCUMENTO_ANALISIS_AVISO;
//				break;
//			case Constantes.TIPO_DOCUMENTO_TIP :
//				strQuery = Constantes.QUERY_REF_DOCUMENTO_ANALISIS_TIP;
//				break;
//			default: //case Constantes.TIPO_DOCUMENTO_ARP :
//				strQuery = Constantes.QUERY_REF_DOCUMENTO_ANALISIS_ARP;
//				break;
		}
		
		try{
			Query query = em.createQuery(strQuery);
			query.setParameter("id", id);
			
			List<Object> lista = (List<Object>)query.getResultList();
			if(!lista.isEmpty() && lista.get(0) != null){
				refDocumento = (byte[])lista.get(0);
			}
			
		}catch (Exception exc){
			log.error("Error en m\u00E9todo obtenerDocumentosAnalisis.obtenRefDocumento()");
			log.error(exc.getMessage());
			throw new PersistenceException(exc);
		}
		
		return refDocumento;
	}


}