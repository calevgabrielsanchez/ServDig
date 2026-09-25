package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.DiferenciasRENAPOContraSAT;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.utility.CompararPersonaFisicaEntidadExternaUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessLocal;

import org.apache.commons.lang.StringUtils;
/**
 * 120912
 * Esta clase corresponde al diagrama N2
 * @author ICCSRG
 *
 */
@Stateless(name = "localizarPersonaFisicaEnEntidadesExternasServiceBusiness", mappedName = "localizarPersonaFisicaEnEntidadesExternasServiceBusiness")
public class LocalizarPersonaFisicaEnEntidadesExternasServiceBusiness extends AbstractServiceBusiness implements LocalizarPersonaFisicaEnEntidadesExternasServiceBusinessRemote {

	@EJB
	private transient PersonaBusinessLocal personaBusiness;
	
	@EJB
	private CompararPersonaFisicaEntidadExternaUtilityLocal compararPersonaFisicaEntidadExternaUtility;

	@EJB
	private LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote localizarPersonaFisicaEnRENAPOServiceBusiness;

	@EJB
	private LocalizarPersonaFisicaEnSATServiceBusinessRemote localizarPersonaFisicaEnSATServiceBusiness;

	@EJB
	private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
	
	/**
	 * Este metodo se encargara de localizar a una persona fisica, tanto en el RENAPO como en el SAT y regresara otro objeto persona siempre y cuando haya sido
	 * localizada en ambas entidades externas
	 * @param personaFisicaSugerida
	 * @return
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws ClienteWebserviceSatRfcException
	 */
	@Override
	public Fisica localizarPersonaFisicaEnEntidadesExternas(Fisica personaFisicaSugerida) throws 
	ErrorComparacionDatosRENAPOException, ErrorComparacionDatosSATException, CURPNoLocalizadoEnEntidadExternaException, 
	RFCNoLocalizadoEnEntidadExternaException, ClienteWebserviceRenapoCurpException, ClienteWebserviceSatRfcException {
	
		Fisica personaFisicaRenapo = null;
		Fisica personaFisicaSat = null;
		Fisica candidato = new Fisica();
		
		// Paso 1. Buscar en RENAPO mediante la CURP
		personaFisicaRenapo = this.localizarPersonaFisicaEnRENAPO(personaFisicaSugerida); 				
		candidato = personaFisicaRenapo;	
				
		// Paso 2. Buscar en SAT mediante el RFC
		
		if(personaFisicaSugerida.getRfc()!= null && !personaFisicaSugerida.getRfc().isEmpty()){
			personaFisicaSat = this.localizarPersonaFisicaEnSAT(personaFisicaSugerida);
			candidato.setRfc(personaFisicaSat.getRfc());
		}

		// Paso 3. Se regresa el objeto candidato solo si existen las 2 certificaciones: RENAPO y SAT
		return candidato;
	}

	@Override
	public Fisica localizarCompararPersonaFisicaEnEntidadesExternasxCURPyRFC(
			Fisica personaFisicaSugerida)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			ErrorComparacionDatosRENAPOException,
			RFCNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceSatRfcException, ErrorComparacionDatosSATException, DiferenciasRENAPOContraSAT {
		// Paso 1. Buscar en RENAPO mediante la CURP
		Fisica personaFisicaRenapo = localizarCompararPersonaFisicaEnRENAPOxCURP(personaFisicaSugerida);
		Fisica candidato = personaFisicaRenapo;

		// Paso 2. Buscar en SAT mediante el RFC
		Fisica personaFisicaSat = null;
		if (StringUtils.isNotBlank(personaFisicaSugerida.getRfc())) {
			personaFisicaSat = localizarCompararPersonaFisicaEnSATxRFC(personaFisicaSugerida);
		} else {
			throw new ErrorValidacionDatosConsultaEnEntidaExternaException("El valor del RFC no puede ser vacio");
		}

		// Paso 3. Validar si existen diferencias entre SAT Y RENAPO
		if (personaFisicaRenapo != null && personaFisicaSat != null) {
			if (StringUtils.isBlank(personaFisicaSat.getCurp())) {
				throw new DiferenciasRENAPOContraSAT("Existen diferencias en el CURP de la persona en RENAPO y SAT");
			} else if (!personaFisicaRenapo.getCurp().equals(personaFisicaSat.getCurp())) {
				throw new DiferenciasRENAPOContraSAT("Existen diferencias en el CURP de la persona en RENAPO y SAT");
			}
		}

		// Paso 3. Se regresa el objeto candidato solo si existen las 2
		// certificaciones: RENAPO y SAT
		candidato.setRfc(personaFisicaSat.getRfc());
		return candidato;
	}

	@Override
	public Fisica localizarCompararPersonaFisicaEnRENAPOxCURP(
			Fisica personaFisicaSugerida)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			ErrorComparacionDatosRENAPOException {
		log.debug("localizarPersonaFisicaEnRENAPOxCURP {" + personaFisicaSugerida.getCurp() + "}");

		// Paso 1. Buscar en RENAPO mediante la CURP
		Fisica personaFisicaRenapo = localizarPersonaFisicaEnRENAPOServiceBusiness
				.localizarPersonaFisicaEnRENAPOxCURP(personaFisicaSugerida.getCurp());

		log.debug("Se localizo la persona en RENAPO, iniciamos la comparacion ...");
		boolean exitoComparacion = personaFisicaServiceBusiness
				.comparaDatosBasicosRENAPO(personaFisicaRenapo, personaFisicaSugerida);

		if (!exitoComparacion) {
			throw new ErrorComparacionDatosRENAPOException();
		}
		this.log.debug("Se supero exitosamente la comparacion del candidato ..." + personaFisicaRenapo);

		return personaFisicaRenapo;
	}

	@Override
	public Fisica localizarPersonaFisicaEnRENAPO(Fisica personaSugerida)  throws ErrorComparacionDatosRENAPOException, CURPNoLocalizadoEnEntidadExternaException, ClienteWebserviceRenapoCurpException{
		Fisica personaFisicaRenapo = null;
		Fisica candidato = new Fisica();
		this.log.debug("localizarPersonaFisicaEnRENAPO {" + personaSugerida.getCurp()+"}");
		// Paso 1. Buscar en RENAPO mediante la CURP
				personaFisicaRenapo = personaBusiness.buscarPersonaFisicaPorCurpEnRenapo(personaSugerida.getCurp());
				if(personaFisicaRenapo != null){
//					
					this.log.debug("Se localizo la persona en RENAPO, iniciamos la comparacion ...");
					
					candidato = compararPersonaFisicaEntidadExternaUtility.compararPersonaFisicaConRENAPO(personaSugerida, personaFisicaRenapo, personaSugerida);
					
					this.log.debug("Se supero exitosamente la comparacion del candidato ..." + candidato);
				}else{
					this.log.warn("EL WS DEL RENAPO NO ARROJO RESULTADOS");
					
					throw new CURPNoLocalizadoEnEntidadExternaException();
				}
		 
		return candidato;
	}

	@Override
	public Fisica localizarCompararPersonaFisicaEnSATxRFC(Fisica personaFisicaSugerida)
			throws RFCNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceSatRfcException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			ErrorComparacionDatosSATException {
		// Paso 2. Buscar en SAT mediante el RFC
		Fisica personaFisicaSat = localizarPersonaFisicaEnSATServiceBusiness
				.localizarPersonaFisicaEnSATxRFC(personaFisicaSugerida.getRfc());

		log.debug("Se localizo la persona en RENAPO, iniciamos la comparacion ...");
		boolean exitoComparacion = personaFisicaServiceBusiness
				.comparaDatosBasicosSAT(personaFisicaSat, personaFisicaSugerida);

		if (!exitoComparacion) {
			throw new ErrorComparacionDatosSATException();
		}
		this.log.debug("Se supero exitosamente la comparacion del candidato ..." + personaFisicaSat);

		return personaFisicaSat;
	}

	@Override
	public Fisica localizarPersonaFisicaEnSAT(Fisica personaSugerida)
			throws ErrorComparacionDatosSATException,
			RFCNoLocalizadoEnEntidadExternaException,ClienteWebserviceSatRfcException {
		
		Fisica personaFisicaSat = null;
		Fisica candidato = new Fisica();
		
		
		// Paso 2. Buscar en SAT mediante el RFC
				try{
					personaFisicaSat = personaBusiness.buscarPersonaFisicaPorRfcEnSat(personaSugerida.getRfc());
					if(personaFisicaSat != null){
						candidato = compararPersonaFisicaEntidadExternaUtility.compararPersonaFisicaConSAT(personaSugerida, personaFisicaSat, personaSugerida);
					}else{
						System.out.println("EL WS DEL SAT NO ARROJO RESULTADOS");
						throw new RFCNoLocalizadoEnEntidadExternaException();
					}
				}catch(ClienteWebserviceSatRfcException e){
					System.out.println("EL WS DEL SAT NO ESTA ARRIBA");
					throw e;
				}
		
		return candidato;
	}

	/**
	 * @throws ClienteWebserviceRenapoCurpException 
	 * 
	 */
	@Override
	public Boolean localizarPersonaFisicaEnRENAPOByCURP(Fisica personaSugerida)  throws ErrorComparacionDatosRENAPOException, CURPNoLocalizadoEnEntidadExternaException, ClienteWebserviceRenapoCurpException{
		Fisica personaFisicaRenapo = null;
		Boolean resultado = Boolean.FALSE;
		
		this.log.debug("localizarPersonaFisicaEnRENAPO {" + personaSugerida.getCurp()+"}");
		// Paso 1. Buscar en RENAPO mediante la CURP
				personaFisicaRenapo = personaBusiness.buscarPersonaFisicaPorCurpEnRenapo(personaSugerida.getCurp());
				if(personaFisicaRenapo != null){
//					
					this.log.debug("Se localizo la persona en RENAPO, iniciamos la comparacion ...");
					
					resultado = compararPersonaFisicaEntidadExternaUtility.comparaDatosBasicosRENAPO( personaFisicaRenapo, personaSugerida);
					
					this.log.debug("Se supero exitosamente la comparacion del candidato ..." + resultado);
				}else{
					this.log.warn("EL WS DEL RENAPO NO ARROJO RESULTADOS");
					
					throw new CURPNoLocalizadoEnEntidadExternaException();
				}
		 
		return resultado;
	}
}
