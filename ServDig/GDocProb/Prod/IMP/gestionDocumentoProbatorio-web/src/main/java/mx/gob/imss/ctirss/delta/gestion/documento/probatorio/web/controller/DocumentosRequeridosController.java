package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.web.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/documentos/requeridos/")
public class DocumentosRequeridosController extends AbstractController {
	
	@Autowired
	private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;
	
	@RequestMapping(value = "/clasificados/{idTipoTramite}")
	public String getDocumentosClasificados(@PathVariable String idTipoTramite, HttpServletRequest request, Model model) {
		List<Long> tiposTramite = new ArrayList<Long>();
		List<Map<String, Object>> documentos = null;
		Long documentosRequeridos = 0L;
		String[] tiposTramites = idTipoTramite.split(",");
		
		for(String cveTipoT: tiposTramites) {
			tiposTramite.add(new Long(cveTipoT));
		}
		
		//Verificamos si el tramite es ee MOVPAT 
		boolean tramiteMovpat = false;
		for (Iterator<Long> iterator = tiposTramite.iterator(); iterator.hasNext();) {
			Integer tipoTramite = new Integer(iterator.next().intValue());
			if (tipoTramite.equals(TipoTramiteEnum.ACTIVIDAD_ECONOMICA.getCodigo())
					|| tipoTramite.equals(TipoTramiteEnum.DISPOSICION_DE_LEY.getCodigo())
					|| tipoTramite.equals(TipoTramiteEnum.INCORPORACION_DE_ACTIVIDADES.getCodigo())
					|| tipoTramite.equals(TipoTramiteEnum.COMPRA_DE_ACTIVOS.getCodigo())
					|| tipoTramite.equals(TipoTramiteEnum.COMODATO.getCodigo())
					|| tipoTramite.equals(TipoTramiteEnum.ENAJENACION.getCodigo())
					|| tipoTramite.equals(TipoTramiteEnum.ARRENDAMIENTO.getCodigo())
					|| tipoTramite.equals(TipoTramiteEnum.FIDEICOMISO_TRASLATIVO.getCodigo())
					|| tipoTramite.equals(TipoTramiteEnum.ESCISION.getCodigo())
					|| tipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL.getCodigo())
					|| tipoTramite.equals(TipoTramiteEnum.FUSION.getCodigo())
					|| tipoTramite.equals(TipoTramiteEnum.REANUDACION_DE_ACTIVIDADES.getCodigo())
					|| tipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL_SUBCONTRATACION.getCodigo())
					|| tipoTramite.equals(TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO.getCodigo())
					|| tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo())) {
				tramiteMovpat = true;
				break;
			}
		}
		
		if(!tramiteMovpat) {
			log.debug("::: Los tramites no son de MOVPAT internet, se sigue flujo normal");
			documentos = documentoProbatorioServiceBusinessRemote.getDocumentosClasificadosPorTipoTramite(tiposTramite);
			documentosRequeridos = documentos != null ? documentos.size() : 0L;
			model.addAttribute("documentos", documentos);
			model.addAttribute("documentosRequeridos", documentosRequeridos);
		}else {
			log.debug("::: Los tramites son de MOVPAT internet, se consultan las nuevas listas en getDocumentosReqTramiteOrigenSol()");
			List<Documento> documentosMovPat = documentoProbatorioServiceBusinessRemote.getDocumentosPorTramitesMovPatInternet(tiposTramite);
			model.addAttribute("documentosMovPat", documentosMovPat);			
		}
		
		return "listaDocumentosRequeridos";	
		
		/**
		 * List<DoctoReqTramite> doctoReqTramites = null;
		if(tiposTramites.length == 1) {
			doctoReqTramites = documentoProbatorioServiceBusinessRemote.getDocumentosRequeridosPorTipoTramite(new Long(tiposTramites[0]));
		} else if(tiposTramites.length > 1) {
			List<DoctoReqTramite> aux = null;
			
			for(String tipoTramite: tiposTramites) {
				aux = documentoProbatorioServiceBusinessRemote.getDocumentosRequeridosPorTipoTramite(new Long(tipoTramite));
				
				if(aux != null && doctoReqTramites == null) {
					doctoReqTramites = aux;
				} else if(aux != null && doctoReqTramites != null) {
					doctoReqTramites.addAll(aux);
				}
			}
		}**/
	
		
	}


	@RequestMapping(value = "/{idTipoTramite}")
	public String getDocumentosRequeridos(@PathVariable String idTipoTramite, HttpServletRequest request, Model model) {
		List<Long> tiposTramite = new ArrayList<Long>();
		List<Documento> documentos = null;
		String[] tiposTramites = idTipoTramite.split(",");
		
		for(String cveTipoT: tiposTramites) {
			tiposTramite.add(new Long(cveTipoT));
		}
		
		documentos = documentoProbatorioServiceBusinessRemote.getDocumentosPorTramites(tiposTramite);
		
		model.addAttribute("documentos", documentos);
		
		return "listaDocumentosRequeridos";	
		/**
		 * List<DoctoReqTramite> doctoReqTramites = null;
		if(tiposTramites.length == 1) {
			doctoReqTramites = documentoProbatorioServiceBusinessRemote.getDocumentosRequeridosPorTipoTramite(new Long(tiposTramites[0]));
		} else if(tiposTramites.length > 1) {
			List<DoctoReqTramite> aux = null;
			
			for(String tipoTramite: tiposTramites) {
				aux = documentoProbatorioServiceBusinessRemote.getDocumentosRequeridosPorTipoTramite(new Long(tipoTramite));
				
				if(aux != null && doctoReqTramites == null) {
					doctoReqTramites = aux;
				} else if(aux != null && doctoReqTramites != null) {
					doctoReqTramites.addAll(aux);
				}
			}
		}**/
	
		
	}

}
