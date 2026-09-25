package mx.gob.imss.cit.cda.service.utility;

import java.net.MalformedURLException;

import javax.ejb.Local;

import mx.gob.imss.cit.clienteServiciosComunes.model.CreateDocumentReq;
import mx.gob.imss.cit.clienteServiciosComunes.model.CreateDocumentRes;
import mx.gob.imss.cit.clienteServiciosComunes.model.DeleteDocumentReq;
import mx.gob.imss.cit.clienteServiciosComunes.model.DeleteDocumentRes;
import mx.gob.imss.cit.clienteServiciosComunes.model.DocumentReq;
import mx.gob.imss.cit.clienteServiciosComunes.model.DocumentRes;

@Local
public interface BovedaUtilityLocal {
	
	String TIPO_DOCUMENTAL_CDA = "D:cda:imss";
	String TIPO_DOCUMENTAL_HISTORICO = "cmis:document";
	String RUTA_CDA = "/CDA";
	
	DocumentRes getDocument(DocumentReq documentReq) throws MalformedURLException;
	DocumentRes getDocumentV2(DocumentReq documentReq) throws MalformedURLException;

	CreateDocumentRes createDocument(CreateDocumentReq createDocumentReq);
	CreateDocumentRes createDocumentV2(CreateDocumentReq createDocumentReq);
	
	DeleteDocumentRes deleteDocumentV2(DeleteDocumentReq deleteDocumentReq);
	DeleteDocumentRes logicDeleteDocumentV2(DeleteDocumentReq deleteDocumentReq);

}
