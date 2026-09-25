package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.TipoTramiteParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser.DocumentoPorTipoParser;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DoctoReqTramite;
import mx.gob.imss.ctirss.delta.persistence.DitDoctoReqTramite;

import org.apache.log4j.Logger;

public class DoctoReqTramiteParser {
	
	private static final Logger logger = Logger.getLogger(DoctoReqTramiteParser.class);

	
	
	public static List<DoctoReqTramite> persistToModelList(List<DitDoctoReqTramite> entradaList) throws DerechohabientesBusinessException {
		List<DoctoReqTramite> salidaList=new ArrayList<DoctoReqTramite>();
		if(entradaList.size() > 0){
			for(DitDoctoReqTramite entrada:entradaList){
				salidaList.add(persistToModel(entrada));
			}
		}				
		return salidaList;
	}

	private static DoctoReqTramite persistToModel(DitDoctoReqTramite entrada) throws DerechohabientesBusinessException {
		DoctoReqTramite salida=null;
		if(entrada!=null){
			try {
				salida=new DoctoReqTramite();
				salida.setCveIdDoctoReqTramite(entrada.getCveIdDoctoReqTramite());
				salida.setRefCapturaDocumentoObligatorio(entrada.getIndDoctoReqCaptura());
				salida.setRefDocumentoOpcional(entrada.getIndDoctoOpcional());
				salida.setRefCargaDocumentoObigatorio(entrada.getIndDoctoReqDigitalizacion());
				salida.setDocumentoPorTipo(DocumentoPorTipoParser.persisToModel(entrada.getDitDocumentoPorTipo()));
				salida.setTipoTramite(TipoTramiteParser.persisToModel(entrada.getDicTipoTramite()));
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_DOCTO_REQ_TRAMITE+" | "+e.getMessage());
			}
			
		}
		return salida;
	}

}
