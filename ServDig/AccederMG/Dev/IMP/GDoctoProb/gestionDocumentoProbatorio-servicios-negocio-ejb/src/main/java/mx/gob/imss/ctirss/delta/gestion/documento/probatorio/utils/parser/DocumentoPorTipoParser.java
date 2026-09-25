package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoPorTipo;

import org.apache.log4j.Logger;

public class DocumentoPorTipoParser {
	
	private static final Logger logger = Logger.getLogger(DocumentoPorTipoParser.class);
	
	public static DocumentoPorTipo persisToModel(DitDocumentoPorTipo entrada) throws DocumentoProbatorioException{
		DocumentoPorTipo salida=null;
		if(entrada !=null){
			try {
				salida = new DocumentoPorTipo();
				salida.setIdDocumentoPorTipo(entrada.getCveIdDoctoProbPorTipo());
				salida.setDocumento(DocumentoParser.persisToModel(entrada.getDicDocumento()));
				salida.setTipoDocumentoProbatorio(TipoDocumentoProbatorioParser.persisToModel(entrada.getDicTipoDocumentoProbatorio()));
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_DOCUMENTO_POR_TIPO, e);
				throw new DocumentoProbatorioException(ExceptionMessages.ERROR_PARSER_DOCUMENTO_POR_TIPO+" | "+e.getMessage());
			}
			
			
		}
		
		return salida;	
	}
	
	
	public static List<DocumentoPorTipo> PersistToModelList(List<DitDocumentoPorTipo> entradaList) throws DocumentoProbatorioException{
		List<DocumentoPorTipo> salidaList=new ArrayList<DocumentoPorTipo>();
		for(DitDocumentoPorTipo entrada:entradaList){
			salidaList.add(DocumentoPorTipoParser.persisToModel(entrada));
		}
		return salidaList;
	}

	

	public static DocumentoPorTipo persistToModel(
			DitDocumentoPorTipo entrada) {
		DocumentoPorTipo salida=null;
		if(entrada!=null){
			salida=new DocumentoPorTipo();
			salida.setIdDocumentoPorTipo(entrada.getCveIdDoctoProbPorTipo());
			salida.setTipoDocumentoProbatorio(TipoDocumentoProbatorioParser.persisToModel(entrada.getDicTipoDocumentoProbatorio()));
			salida.setDocumento(DocumentoParser.persisToModel(entrada.getDicDocumento()));
		}
		return salida;
	}

}
