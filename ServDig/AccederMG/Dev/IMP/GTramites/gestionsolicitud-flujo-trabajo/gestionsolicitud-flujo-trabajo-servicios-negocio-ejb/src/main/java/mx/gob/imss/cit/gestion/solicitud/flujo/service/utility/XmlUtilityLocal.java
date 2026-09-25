package mx.gob.imss.cit.gestion.solicitud.flujo.service.utility;

import java.io.Serializable;

public interface XmlUtilityLocal {

	String convertToXMLWithNoReferences(final Serializable data);

	Object convertToModel(final String xmlAsString);
}
