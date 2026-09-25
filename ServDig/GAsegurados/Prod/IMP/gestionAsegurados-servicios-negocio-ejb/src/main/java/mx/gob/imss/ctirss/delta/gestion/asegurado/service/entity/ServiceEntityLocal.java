package mx.gob.imss.ctirss.delta.gestion.asegurado.service.entity;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.nss.Serie;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;

@Local
public interface ServiceEntityLocal {

	String generaNss(Long cveIdSerie, Long numAnioNacimiento, Long numAnioRegistro);

	String altaPersonaNss(Fisica fisica, Serie serie);
	
	String asignacionNss(Fisica fisica, String sNss);

	/**
	 * Método que se encarga de cambiar la relación hacia la persona de un NSS
	 * 
	 * @param nss
	 * @param idPersonaDuenia
	 * @param idPersonaAsignar
	 */
	void cambiarDuenioNSS(String nss, Long idPersonaDuenia,
			Long idPersonaAsignar);
	Long obtenerIdPersonaPorNSS(String NSS);
	
	AsignacionNSS obtenerAseguradoPorNss(String nss);
	
	AsignacionNSS obtenerAseguradoPorNssConBajaLogica(String nss);

    AsignacionNSS obtenerAseguradoPorNss(String nss, String curp);
	
	AsignacionNSS obtenerAseguradoPorIdAsignacion(Long idAsignacion);
}
