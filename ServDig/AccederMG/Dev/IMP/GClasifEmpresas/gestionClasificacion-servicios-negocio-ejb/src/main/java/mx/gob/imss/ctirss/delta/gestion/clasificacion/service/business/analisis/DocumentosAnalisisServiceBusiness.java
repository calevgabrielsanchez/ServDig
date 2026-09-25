/**
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: DocumentosAnalisisServiceBusiness.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.analisis
 *  @Fecha: 29/10/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.analisis;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.analisis.DocumentosAnalisisServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.DocumentosAnalisisServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.sujetoobligado.SujetoObligadoServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Constantes;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.clasificacion.DocumentosAnalisis;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

@Stateless(name = "documentosAnalisisServiceBusiness", mappedName = "documentosAnalisisServiceBusiness")
public class DocumentosAnalisisServiceBusiness extends AbstractServiceBusiness implements
		DocumentosAnalisisServiceBusinessRemote {

	@EJB
	private DocumentosAnalisisServiceEntityLocal documentosAnalisisEntity;
	
	@EJB
	private SolicitudBusinessRemote solicitudBusiness;
	
	@EJB
	private SujetoObligadoServiceUtilityLocal sujetoObligadoUtility;

	/**
	 * Arma el objeto DocumentoAnalisis según la existencia o no de los diferentes
	 * tipos de documentos de análisis.
	 * @param cveIdSolicitud
	 * @return DocumentoAnalisis
	 * @throws Exception
	 */
	public DocumentosAnalisis validaExistenciaDocumentos(Long cveIdSolicitud) 
			throws Exception{
		DocumentosAnalisis doctos = new DocumentosAnalisis();		
		//obtiene la solicitud
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(cveIdSolicitud);
		
		solicitud = solicitudBusiness.consultar(solicitud);
		
		Tramite tramite = sujetoObligadoUtility.obtenerTramite(solicitud.getTramites(), 
				solicitud.getTipoSolicitud().getIdTipoSolicitud());
				
		String res = 
				documentosAnalisisEntity
					.obtenIdDocumento(cveIdSolicitud, Constantes.TIPO_DOCUMENTO_CLEM);
		
		if(res != null && res.trim().length() != 0){
			String[] r = res.split("\\|");
			doctos.setExisteClem(true);
			doctos.setCveIdClem(new Long(r[0]));
			if(r.length > 1 && r[1].trim().length() > 1){
				doctos.setBoIndFirma(true);
				doctos.setUrlClemFirma(r[1]);
			}else{
				doctos.setBoIndFirma(false);
			}
		}else{
			doctos.setExisteClem(false);
		}
		
		Long idSolicitud = 
			documentosAnalisisEntity
				.getIdDocumentoPorTipoIdTramite(tramite.getTramiteId(), DocumentoPorTipoEnum.AVISO_DE_MODIFICACION.getId());
		if(idSolicitud != null){
			doctos.setExisteAviso(true);
			doctos.setCveIdSolicitud(idSolicitud);
		}
		
		Long idDocumentoProbatorioTip = 
			documentosAnalisisEntity
				.getIdDocumentoPorTipoIdTramite(tramite.getTramiteId(), DocumentoPorTipoEnum.TIP.getId());
		if(idDocumentoProbatorioTip != null){
			doctos.setExisteTip(true);
			doctos.setCveIdDocumentoProbatorioTip(idDocumentoProbatorioTip);
		}
		
		Long idDocumentoProbatorioArp = 
			documentosAnalisisEntity
				.getIdDocumentoPorTipoIdTramite(tramite.getTramiteId(), DocumentoPorTipoEnum.ARP.getId());
		if(idDocumentoProbatorioArp != null){
			doctos.setExisteArp(true);
			doctos.setCveIdDocumentoProbatorioArp(idDocumentoProbatorioArp);
		}
			
		return doctos;
	}
	
	/**
	 * Obtiene el arreglo de Bytes de un documento de análisis según su tipo.
	 * @param id, tipoDocumento
	 * @return byte[]
	 * @throws Exception
	 */
	public byte[] obtenRefDocumento(Long id, int tipoDocumento) throws Exception{
		if(tipoDocumento == 1)
			return documentosAnalisisEntity.obtenRefDocumento(id, tipoDocumento);
		else
			return documentosAnalisisEntity.getRefDocumentoPorTipoIdTramite(id, tipoDocumento);
	}
	
}
