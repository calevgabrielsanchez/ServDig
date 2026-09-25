package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.digital.modelo.satRiss.DatosRiss;

@Remote
public interface ValidaIncorporarRissIvroRemote {

	
	/**
	 * Valida posible incorporacion al beneficio RISS.
	 * 
	 * @param  riss
	 * @return datosRiss
	 */
	DatosRiss validaIncorporacionBeneficioRiss(DatosRiss riss);
    
}
