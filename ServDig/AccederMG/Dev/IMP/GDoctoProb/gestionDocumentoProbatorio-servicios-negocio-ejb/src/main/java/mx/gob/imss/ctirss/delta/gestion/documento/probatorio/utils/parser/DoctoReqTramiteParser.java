package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DoctoReqTramite;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DoctoReqTramiteOrigenSol;
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


	public static List<DoctoReqTramiteOrigenSol> persistToModelOrigenSolList(List<DitDoctoReqTramOrigenSol> entradaList){
		List<DoctoReqTramiteOrigenSol> salidaList=new ArrayList<DoctoReqTramiteOrigenSol>();
		if(entradaList.size() > 0){
			for(DitDoctoReqTramOrigenSol entrada : entradaList){
				salidaList.add(persistToModelOrigenSol(entrada));
			}
		}				
		return salidaList;
	}

	
	
}
