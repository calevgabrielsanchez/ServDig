package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.DocumentacionTramiteModel;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentacionTramite;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentacionTramitePK;

public class DocumentacionTramiteModelParser {
	
	private static final Logger logger = Logger.getLogger(DocumentacionTramiteModelParser.class);
	
	public static final DitDocumentacionTramite modelToPersist(DocumentacionTramiteModel entrada ) throws DerechohabientesBusinessException{
		DitDocumentacionTramite salida=null;
		DitDocumentacionTramitePK id=null;
		if(entrada!=null){
			try {
				salida=new DitDocumentacionTramite();
				id=new DitDocumentacionTramitePK();
				//Carlos
//				id.setCveIdPersona(entrada.getIdPersona());
//				id.setCveIdSolicitud(entrada.getIdSolicitud());
//				id.setCveIdTipoTramite(entrada.getIdTramite());
				salida.setId(id);
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_DOCUMENTACION_TRAMITE, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_DOCUMENTACION_TRAMITE+" | "+e.getMessage());
			}
			

		}
		return salida;
	}

}
