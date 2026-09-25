/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.utility.CompararPersonaFisicaEntidadExternaUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Calificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.CalificacionPersona;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessLocal;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

import org.apache.commons.lang.StringUtils;

/**
 * @author Lucio Duran Silva
 *
 */
@Stateless(name = "complementarCalificacionPersonaFisicaServiceBusiness", mappedName = "complementarCalificacionPersonaFisicaServiceBusiness")
public class ComplementarCalificacionPersonaFisicaServiceBusiness extends
		AbstractServiceBusiness implements
		ComplementarCalificacionPersonaFisicaServiceBusinessRemote {

	@EJB
	private ServiciosPersonaBusinessRemote serviciosPersonaBusinessRemote;
	@EJB
	private PersonaBusinessLocal personaBusiness;

	@EJB
	private CompararPersonaFisicaEntidadExternaUtilityLocal compararPersonaFisicaEntidadExternaUtility;

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ComplementarCalificacionPersonaFisicaServiceBusinessRemote#complementarCalificaciones(mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica, mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica)
	 */
	
	/**
	 * 
	 * @param candidato
	 * @param entrada
	 * @return
	 * @throws RFCNoLocalizadoEnEntidadExternaException 
	 * @throws CURPNoLocalizadoEnEntidadExternaException 
	 * @throws ClienteWebserviceRenapoCurpException 
	 */
	@Override
	public Fisica complementarCalificaciones(Fisica candidato, Fisica entrada)
			throws ErrorComparacionDatosRENAPOException,
			ErrorComparacionDatosSATException,
			ClienteWebserviceSatRfcException,RFCNoLocalizadoEnEntidadExternaException,
			CURPNoLocalizadoEnEntidadExternaException, ClienteWebserviceRenapoCurpException {

		boolean hasCalificacionIMSS = false;
		boolean hasCalificacionRENAPO = false;
		boolean hasCalificacionSAT = false;

		/*
		 * Debemos de obtener el detalle de la persona fisica candidato
		 */
		try {
			candidato = serviciosPersonaBusinessRemote.buscarPersonaFisicayDPyDyMCEnIMSS(candidato.getIdPersona());
		} catch (PersonaFisicaNoEncontradaException e) {
			this.log.error(e);
		}
		
		// 1. Obtenemos la calificacion del candidato.
		List<PersonaCalificacion> calificaciones = candidato.getPersonaCalificaciones();
		for(PersonaCalificacion pc: calificaciones){
			Calificacion c1 = pc.getCalificacion();
			this.log.debug("Calificacion del registro ..." + c1);
			if( c1.getIdCalificacion().intValue() ==CalificacionPersona.VALIDADO_IMSS.intValue()){
				hasCalificacionIMSS = true;
				this.log.debug("Calificacion en el IMSS ..." + hasCalificacionIMSS);
			}else if(c1.getIdCalificacion().intValue() ==CalificacionPersona.VALIDADO_RENAPO.intValue()){
				hasCalificacionRENAPO = true;
				this.log.debug("Calificacion en el RENAPO ..." + hasCalificacionRENAPO);
			} else if(c1.getIdCalificacion().intValue() ==CalificacionPersona.VALIDADO_SAT.intValue()){
				hasCalificacionSAT = true;
				this.log.debug("Calificacion en el CURP ..." + hasCalificacionSAT);
			}
		}

		// 2. Revisamos si es necesario complementar la calificacion de RENAPO
		if(!hasCalificacionRENAPO){
			this.log.debug(" Iniciamos la consulta al RENAPO...");
			candidato = this.complementarCalificacionRENAPO(candidato, entrada);
		}else{
				if(candidato.getCurp() != null && !entrada.getCurp().equals(candidato.getCurp())){
					candidato = this.complementarCalificacionRENAPO(candidato, entrada);
				}
		}
		
		

		// 3. Revisamos si es necesario complementar la calificacion de SAT
		String rfc = entrada.getRfc();
		if (!hasCalificacionSAT && StringUtils.isNotBlank(rfc)) {
			try {
				candidato = this.complementarCalificacionSAT(candidato, entrada);
			} catch (RFCNoLocalizadoEnEntidadExternaException e) {
				this.log.debug("Prerando la respuesta para presentar la vista con la informacion obtenida, sin informacion del SAT.. =[" + candidato +"]=");
				throw e;
			}
		}
this.log.debug("Regresando el candidato .." + candidato);
		return candidato;
	}
	
	
	
	/**
	 * 
	 * @param candidato
	 * @param entrada
	 * @return
	 * @throws ErrorComparacionDatosRENAPOException
	 * @throws CURPNoLocalizadoEnEntidadExternaException 
	 * @throws ClienteWebserviceRenapoCurpException 
	 */
	private Fisica complementarCalificacionRENAPO(Fisica candidato, Fisica entrada) throws ErrorComparacionDatosRENAPOException, CURPNoLocalizadoEnEntidadExternaException, ClienteWebserviceRenapoCurpException{
		String curp = entrada.getCurp();
		//Obtenemos los datos de la entidad
		Fisica entidad = this.personaBusiness.buscarPersonaFisicaPorCurpEnRenapo(curp);
		
		if(entidad == null){
			throw new CURPNoLocalizadoEnEntidadExternaException();
		}
		
		// Comparamos los datos de la persona y actualizamos los datos de la persona.
		candidato = this.compararPersonaFisicaEntidadExternaUtility.compararPersonaFisicaConRENAPO(candidato, entidad, entrada);
		return candidato;
	}
	
	/**
	 * 
	 * @param candidato
	 * @param entrada
	 * @return
	 * @throws ErrorComparacionDatosSATException
	 * @throws RFCNoLocalizadoEnEntidadExternaException 
	 */
	private Fisica complementarCalificacionSAT(Fisica candidato, Fisica entrada)
			throws ErrorComparacionDatosSATException,
			ClienteWebserviceSatRfcException,
			RFCNoLocalizadoEnEntidadExternaException {
		
		String rfc = entrada.getRfc();
		try {
			//Obtenemos la informacion del SAT.
			 Fisica entidad = this.personaBusiness.buscarPersonaFisicaPorRfcEnSat(rfc);
			 
			 this.log.debug("Datos encontrados en el SAT " + entidad);
			 if(entidad == null){
				 this.log.warn("No se localizo a la persona en el SAT, el RFC consultado es :" + rfc);
				 throw new RFCNoLocalizadoEnEntidadExternaException();
			 }else{
				 // Comparamos los elementos de la informacion de entrada y de la entidad
				 candidato = this.compararPersonaFisicaEntidadExternaUtility.compararPersonaFisicaConSAT(candidato, entidad, entrada);
			 }
			 

			 
		} catch (ClienteWebserviceSatRfcException e) {
			// TODO: En caso de que el servicio no este disponible...
			throw e;
		}
		this.log.debug("complementarCalificacionSAT ... "+ candidato);
		return candidato;
		
	}





}
