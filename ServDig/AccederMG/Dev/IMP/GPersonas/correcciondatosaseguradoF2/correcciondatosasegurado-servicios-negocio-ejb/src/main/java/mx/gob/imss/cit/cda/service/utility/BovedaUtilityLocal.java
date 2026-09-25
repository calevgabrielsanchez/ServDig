package mx.gob.imss.cit.cda.service.utility;

import mx.gob.imss.cit.cda.service.model.DocumentoBovedaDTO;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.BovedaCDAException;

import javax.ejb.Local;

@Local
public interface BovedaUtilityLocal {

    String TIPO_DOCUMENTAL = "D:cda:imss";
    String RUTA = "/CDA";
    String IDENTIFICADOR = "cda";
    String FOLIO_TRAMITE = "folioTramite";
    String NAME = "name";
    String ID = "id";

    DocumentoBovedaDTO getDocument(String idDocumentoBoveda) throws BovedaCDAException;

    String createDocument(DocumentoBovedaDTO documentoBovedaDTO) throws BovedaCDAException;

    String deleteDocument(String idDocumentoBoveda) throws BovedaCDAException;

}
