package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import javax.ejb.Local;

import mx.gob.imss.digital.modelo.sindo.RespuestaValidacionTrabajador;

@Local
public interface ValidaIntegranteSeguroFamiliarLocal {
	/**
     * valida que un integrante del seguro familiar no cuente con otro seguro contratado para el mismo seguro
     * @param nss nuemero de seguridad social
     * @return respuesta de la validacion
     */
    RespuestaValidacionTrabajador validaTrabajadorSeguroAnterior(Long idEmpleador, String nss);
}
