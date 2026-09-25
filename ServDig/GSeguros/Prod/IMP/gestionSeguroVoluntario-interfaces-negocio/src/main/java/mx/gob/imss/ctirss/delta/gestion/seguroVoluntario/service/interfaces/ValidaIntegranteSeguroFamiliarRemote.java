package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.digital.modelo.sindo.RespuestaValidacionTrabajador;

@Remote
public interface ValidaIntegranteSeguroFamiliarRemote {
	/**
	 * Valida que un trabajador domestico no cuente con la compra de un seguro
	 * para el mismo patron
	 * 
	 * @param nss
	 * @return
	 */
	RespuestaValidacionTrabajador validaTrabajadorSeguroAnterior(Long idEmpleador, String nss);
}
