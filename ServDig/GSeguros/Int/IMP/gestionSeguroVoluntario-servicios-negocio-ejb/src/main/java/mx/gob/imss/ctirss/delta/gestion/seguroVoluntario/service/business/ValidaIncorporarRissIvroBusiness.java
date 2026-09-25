package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.BeneficioRissServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ValidaIncorporarRissIvroRemote;
import mx.gob.imss.digital.modelo.satRiss.DatosRiss;

@Stateless(name = "validaIncorporarRissIvroBusiness", mappedName = "validaIncorporarRissIvroBusiness")
public class ValidaIncorporarRissIvroBusiness implements ValidaIncorporarRissIvroRemote {

	/**
	 * Servicio para la incorporacion al beneficio RISS.
	 */
	@EJB
	private BeneficioRissServiceBusinessRemote beneficioRissServiceBusiness;
	
	
	/*
	 * (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ValidaIncorporarRissIvroRemote
	 * #validaIncorporacionBeneficioRiss(mx.gob.imss.digital.modelo.satRiss.DatosRiss)
	 */
	@Override
	public DatosRiss validaIncorporacionBeneficioRiss(DatosRiss riss){
		return beneficioRissServiceBusiness.validaIncorporacionBeneficioRissSeguroPersonal(riss);
	}
	    
}
