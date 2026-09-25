package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DoctoReqTramite;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DoctoReqTramiteOrigenSol;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DitDoctoReqTramOrigenSol;
import mx.gob.imss.ctirss.delta.persistence.DitDoctoReqTramite;

public class DoctoReqTramiteParser {
	
	private static final Logger logger = Logger.getLogger(DoctoReqTramiteParser.class);

	
	
	public static List<DoctoReqTramite> persistToModelList(List<DitDoctoReqTramite> entradaList){
		List<DoctoReqTramite> salidaList=new ArrayList<DoctoReqTramite>();
		if(entradaList.size() > 0){
			for(DitDoctoReqTramite entrada:entradaList){
				salidaList.add(persistToModel(entrada));
			}
		}				
		return salidaList;
	}
	
	private static DoctoReqTramite persistToModel(DitDoctoReqTramite entrada){
		DoctoReqTramite salida=null;
		if(entrada!=null){
			
				salida=new DoctoReqTramite();
				salida.setCveIdDoctoReqTramite(entrada.getCveIdDoctoReqTramite());
				salida.setRefCapturaDocumentoObligatorio(entrada.getIndDoctoReqCaptura());
				salida.setRefDocumentoOpcional(entrada.getIndDoctoOpcional());
				salida.setRefCargaDocumentoObigatorio(entrada.getIndDoctoReqDigitalizacion());
				try {
					salida.setDocumentoPorTipo(DocumentoPorTipoParser.persisToModel(entrada.getDitDocumentoPorTipo()));
				} catch (DocumentoProbatorioException e) {
					logger.error(e);
				}
			
		}
		return salida;
	}

	public static DoctoReqTramiteOrigenSol persistToModelOrigenSol(DitDoctoReqTramOrigenSol entrada){
		DoctoReqTramiteOrigenSol salida=null;
		if(entrada!=null){
			
				salida=new DoctoReqTramiteOrigenSol();
				salida.setCveIdDoctoReqTramOrgSol(entrada.getCveIdDoctoReqTramOrgSol());
				salida.setRefCapturaDocumentoObligatorio(entrada.getIndDoctoReqCaptura());
				salida.setRefDocumentoOpcional(entrada.getIndDoctoOpcional());
				salida.setRefCargaDocumentoObigatorio(entrada.getIndDoctoReqDigitalizacion());
				salida.setRefDetalleDoctoRequerido(entrada.getRefDetalleDoctoRequerido());
				
				OrigenSolicitud origenSolicitud = new OrigenSolicitud();
				origenSolicitud.setIdOrigenSolicitud(entrada.getDicOrigenSolicitud().getCveIdOrigenSolicitud());
				origenSolicitud.setDescripcion(entrada.getDicOrigenSolicitud().getDesOrigenSolicitud());
				salida.setOrigenSolicitud(origenSolicitud);
				
				try {
					salida.setDocumentoPorTipo(DocumentoPorTipoParser.persisToModel(entrada.getDitDocumentoPorTipo()));
				} catch (DocumentoProbatorioException e) {
					logger.error(e);
				}
			
		}
		return salida;
	}

	public static DocumentoPorTipo persistToModelOrigenSolTipo(DitDoctoReqTramOrigenSol entrada){
		DocumentoPorTipo salida=null;
		if(entrada!=null){
				salida=new DocumentoPorTipo();
				salida.setIdDocumentoPorTipo(entrada.getCveIdDoctoReqTramOrgSol());

				Documento doc = new Documento();
				doc.setCveIdDocumento(entrada.getDitDocumentoPorTipo().getDicDocumento().getCveIdDocumento());
//FALTA REVISAR SI SE PUEDE AGREGAR TOOLTIP
//<td>${doctoReqTramite.documentoPorTipo.documento.desDocumento}
//<span class="delta-tooltip icono-help" data-toggle="tooltip" data-html="true" title="${doctoReqTramite.refDetalleDoctoRequerido}" ></span>
//</td>
				String detalleDoc = "";
				if(entrada.getRefDetalleDoctoRequerido() != null) {
					detalleDoc = "(" + entrada.getRefDetalleDoctoRequerido() + ")";
				}
				doc.setDesDocumento(entrada.getDitDocumentoPorTipo().getDicDocumento().getDesDocumento() + detalleDoc);
				salida.setDocumento(doc);
				
				TipoDocumentoProbatorio tp = new TipoDocumentoProbatorio();
				tp.setIdTipoDocumentoProbatorio(new Integer(entrada.getDitDocumentoPorTipo().getDicTipoDocumentoProbatorio().getCveIdTipoDocumentoProbator()+""));
				tp.setDescripcion(entrada.getDitDocumentoPorTipo().getDicTipoDocumentoProbatorio().getDesTipoDocumentoProbatorio());
				
				salida.setTipoDocumentoProbatorio(tp);
			
		}
		return salida;
	}

	public static List<DoctoReqTramiteOrigenSol> persistToModelOrigenSolList(List<DitDoctoReqTramOrigenSol> entradaList){
		List<DoctoReqTramiteOrigenSol> salidaList=new ArrayList<DoctoReqTramiteOrigenSol>();
		if(entradaList.size() > 0){
			for(DitDoctoReqTramOrigenSol entrada : entradaList){
				salidaList.add(persistToModelOrigenSol(entrada));
			}
		}				
		return salidaList;
	}

	
	public static List<DocumentoPorTipo> persistToModelOrigenSolTipoList(List<DitDoctoReqTramOrigenSol> entradaList){
		List<DocumentoPorTipo> salidaList=new ArrayList<DocumentoPorTipo>();
		if(entradaList.size() > 0){
			for(DitDoctoReqTramOrigenSol entrada : entradaList){
				salidaList.add(persistToModelOrigenSolTipo(entrada));
			}
		}				
		return salidaList;
	}
	
	public static Documento persistToModelMovPatInternet(DitDoctoReqTramOrigenSol entrada){
		Documento salida = null;
		if(entrada!=null){
			salida = new Documento();
			salida.setCveIdDocumento(entrada.getDitDocumentoPorTipo().getDicDocumento().getCveIdDocumento());
			String detalleDoc = "";
			if(entrada.getRefDetalleDoctoRequerido() != null) {
				detalleDoc = "(" + entrada.getRefDetalleDoctoRequerido() + ")";
			}
			salida.setDesDocumento(entrada.getDitDocumentoPorTipo().getDicDocumento().getDesDocumento() + detalleDoc);
		}
		return salida;
	}

	
}
