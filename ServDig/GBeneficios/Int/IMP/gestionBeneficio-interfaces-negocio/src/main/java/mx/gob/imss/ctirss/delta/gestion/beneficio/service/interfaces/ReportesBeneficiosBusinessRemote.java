package mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Remote
public interface ReportesBeneficiosBusinessRemote {

	String generarCadenaOriginal(Solicitud solicitud, Persona persona);
	
	byte[] generarReporteBeneficioRiss(Solicitud solicitud);
		
}
