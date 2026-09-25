package mx.gob.imss.ctirss.delta.gestion.patronal.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.EjbLocator;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DoctoReqTramiteOrigenSol;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;

public class ObtieneDocsXTramiteTest {
	
	
	private static final Logger log;

	static {
		log = LoggerFactory.getLogger(ObtieneDocsXTramiteTest.class);
	}	
	
	
	//Metodo nuevo utilizado en MOVPAT Ventanilla para obtener documentos por tramite	
	@Test
	public void getDocumentosClasificadosPorTramite() {
		try {
			log.debug("::: Obteniendo EJB");
			DocumentoProbatorioServiceBusinessRemote ejb = EjbLocator.getDoctoProbService();
			log.debug("::: Consultando documentos");
			
		 	List<Long> tiposTramite = new ArrayList<Long>();
		 	List<Map<String, Object>> documentos = null;
			List<Long> tiposPersona = new ArrayList<Long>();
			
			tiposTramite.add((new Long(7))); //tipo de tramite
			tiposPersona.add(new Long(0)); // tipo persona default para documentos que son para PF y PM
			tiposPersona.add(new Long(2)); //tipo de persona del tramite
			
			documentos = ejb.getDocumentosClasificadosPorTipoTramite(tiposTramite);
			
			if(documentos != null) {
				for (Iterator iterator = documentos.iterator(); iterator.hasNext();) {
					Map<String, Object> map = (Map<String, Object>) iterator.next();
					String t = (String)map.get("titulo");
					System.out.println(":: t: " + t);
					ArrayList<Documento> docs = (ArrayList<Documento>) map.get("documentos");
					for (Iterator<Documento> iterator2 = docs.iterator(); iterator2.hasNext();) {
						Documento d = iterator2.next();
						System.out.println(":: d.getDesDocumento(): " + d.getDesDocumento());					
					}
					//System.out.println(map.toString());
				}				
			}else {
				log.debug("El tramite no regreso documentos");
			}

			log.info("::: Termine");
				


		} catch (Exception e) {
			log.error("error al consultar los documentos requeridos para el tramite ", e);
		}
	}
	
//ESTE ES EL BUENO para probar nuevos catalogos en ventanilla y portal
	//Metodo nuevo utilizado en MOVPAT Ventanilla para obtener documentos por tramite	
	@Test
	public void getDocumentosRequeridos() {
		Integer idTipoTramite = new Integer("7");
		try {
			log.debug("::: Obteniendo EJB");
			DocumentoProbatorioServiceBusinessRemote ejb = EjbLocator.getDoctoProbService();
			log.debug("::: Consultando documentos");
			List<DoctoReqTramiteOrigenSol> documentos = ejb.getDocumentosRequeridosPorTipoTramiteOrigenSol(
					new Long(idTipoTramite), OrigenSolicitudEnum.VENTANILLA.getId());
			
			List<DoctoReqTramiteOrigenSol> documentosReqTra =  new ArrayList<DoctoReqTramiteOrigenSol>();
			try {
				documentosReqTra = ejb.getDocumentosRequeridosPorTipoTramiteOrigenSol(
						new Long(idTipoTramite), OrigenSolicitudEnum.VENTANILLA.getId());
			} catch (DocumentoProbatorioException e) {
				e.printStackTrace();
			}			
				
			Long documentosRequeridos = documentos != null ? documentos.size() : 0L;
			log.debug("Documentos encontrados ---------> " + documentosRequeridos);
			String tooltipDomicilio = "";
			log.debug("::: Imprimiendo resultados");
			for (DoctoReqTramiteOrigenSol doctoReqTramiteOrigenSol : documentos) {
				if (doctoReqTramiteOrigenSol.getCveIdDoctoReqTramOrgSol().equals(12L)) {// 12 - Domicilio
					String[] arrayDoc = doctoReqTramiteOrigenSol.getRefDetalleDoctoRequerido().split("[|]");
					for (String doc : arrayDoc) {
						tooltipDomicilio += "<li>" + doc + "</li>";
					}
					doctoReqTramiteOrigenSol.setRefDetalleDoctoRequerido(tooltipDomicilio);
					break;
				}
			}

			for (DoctoReqTramiteOrigenSol doctoReqTramiteOrigenSol : documentos) {
				System.out.println(doctoReqTramiteOrigenSol.getDocumentoPorTipo().getDocumento().getDesDocumento() + " - " + doctoReqTramiteOrigenSol.getRefDetalleDoctoRequerido());
				System.out.println("----------------------------------------------------------------------------");
			}

		} catch (Exception e) {
			log.error("error al consultar los documentos requeridos para el tramite ", e);
		}
	}
	
	//Metodo actual de MOVPAT y MAC II para obtener lista de documentos
	@Test
	public void obtieneDoctos(){
		List<Long> tiposTramite = new ArrayList<Long>();
		List<Map<String, Object>> documentos = null;
		String idTipoTramite = "21"; // 17-Arrendamiento
		String[] tiposTramites = idTipoTramite.split(",");
		for(String cveTipoT: tiposTramites) {
			tiposTramite.add(new Long(cveTipoT));
		}
		
		List<Long> tiposPersona = new ArrayList<Long>();
		tiposPersona.add(new Long(0)); // tipo persona default para documentos que son para PF y PM
		tiposPersona.add(new Long(2)); //tipo de persona del tramite
		
		try {
			log.info("::: Obteniendo EJB y consultando docs");
			documentos = EjbLocator.getDoctoProbService().getDocumentosClasificadosPorTipoTramite(tiposTramite);
			if(documentos != null) {
				for (Iterator iterator = documentos.iterator(); iterator.hasNext();) {
					Map<String, Object> map = (Map<String, Object>) iterator.next();
					String t = (String)map.get("titulo");
					System.out.println(":: t: " + t);
					ArrayList<Documento> docs = (ArrayList<Documento>) map.get("documentos");
					for (Iterator<Documento> iterator2 = docs.iterator(); iterator2.hasNext();) {
						Documento d = iterator2.next();
						System.out.println(":: d.getDesDocumento(): " + d.getDesDocumento());					
					}
					//System.out.println(map.toString());
				}				
			}else {
				log.debug("El tramite no regreso documentos");
			}

			log.info("::: Termine");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
