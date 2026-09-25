package mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.beneficio.RespuestaCancelacionBeneficio;

@Remote
public interface CancelarBeneficioServiceBusinessRemote {

	/**
	 * Servicio para cancelar un beneficio y su relaci&oacute;n
	 * correspondiente a la persona y/o patron.
	 * 
	 * @param rfc
	 * @param nrp
	 * @param fechaBaja
	 * @param indicadorInstitucion
	 */
	RespuestaCancelacionBeneficio cancelarBeneficio(String rfc, String nrp, 
		String nss,	String fechaBajaRIF, int indicadorInstitucion);
	
}
