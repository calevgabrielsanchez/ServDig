package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import javax.ejb.Local;


import mx.gob.imss.ctirss.delta.framework.exceptions.DocumentoException;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Local
public interface TramiteDocumentosServiceLocal {
	
	void insertarFirmaDigital(Solicitud solicitud);
	FirmaElectronica generaFirmaElectronica(AsignacionNSS asignacionNSS,
			Solicitud solicitud, String nombreTramite) throws DocumentoException;
}
