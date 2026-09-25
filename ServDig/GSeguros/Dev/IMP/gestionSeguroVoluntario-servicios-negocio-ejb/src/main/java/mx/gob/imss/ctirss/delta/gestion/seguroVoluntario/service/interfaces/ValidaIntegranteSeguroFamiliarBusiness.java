package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.digital.modelo.sindo.RespuestaValidacionTrabajador;

@Stateless(name = "validaIntegranteSeguroFamiliarBusiness", mappedName = "validaIntegranteSeguroFamiliarBusiness")
public class ValidaIntegranteSeguroFamiliarBusiness implements
		ValidaIntegranteSeguroFamiliarRemote {
    @EJB
    private ValidaIntegranteSeguroFamiliarLocal validaTrabajador;
    
	@Override
	public RespuestaValidacionTrabajador validaTrabajadorSeguroAnterior(
			Long idEmpleador, String nss) {
		return validaTrabajador.validaTrabajadorSeguroAnterior(idEmpleador, nss);
	}

}
