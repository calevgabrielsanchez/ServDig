package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.utility.CompararPersonaMoralEntidadExternaUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessLocal;
/**
 * 120912
 * Esta clase corresponde al diagrama N2
 * @author ICCSRG
 *
 */
@Stateless(name = "localizarPersonaMoralEnEntidadesExternasServiceBusiness", mappedName = "localizarPersonaMoralEnEntidadesExternasServiceBusiness")
public class LocalizarPersonaMoralEnEntidadesExternasServiceBusiness implements LocalizarPersonaMoralEnEntidadesExternasServiceBusinessRemote {

	@EJB
	private transient PersonaBusinessLocal personaBusiness;
	
	@EJB
	private CompararPersonaMoralEntidadExternaUtilityLocal compararPersonaMoralEntidadExternaUtility;
	
	/**
	 * Este metodo se encargara de localizar a una persona moral en el SAT y regresara otro objeto persona siempre y cuando haya sido localizada en SAT
	 * @param personaMoralSugerida
	 * @return
	 * @throws ClienteWebserviceSatRfcException
	 */
	@Override
	public Moral localizarPersonaMoralEnEntidadesExternas(Moral personaMoralSugerida) throws ErrorComparacionDatosSATException, RFCNoLocalizadoEnEntidadExternaException {
	
		Moral personaMoralSat = null;
		Moral candidato = new Moral();
		
		// Paso 1. Buscar en SAT mediante el RFC
		try{
			personaMoralSat = personaBusiness.buscarPersonaMoralPorRfcEnSat(personaMoralSugerida.getRfc());
			if(personaMoralSat != null){
				candidato = compararPersonaMoralEntidadExternaUtility.compararPersonaMoralConSAT(candidato, personaMoralSugerida, personaMoralSat);
			}else{
				System.out.println("EL WS DEL SAT NO ARROJO RESULTADOS");
				throw new RFCNoLocalizadoEnEntidadExternaException();
			}
		}catch(ClienteWebserviceSatRfcException e){
			System.out.println("EL WS DEL SAT NO ESTA ARRIBA");
		}
				
		// Paso 3. Se regresa el objeto candidato solo si existen las 2 certificaciones: RENAPO y SAT
		return candidato;
	}

}
