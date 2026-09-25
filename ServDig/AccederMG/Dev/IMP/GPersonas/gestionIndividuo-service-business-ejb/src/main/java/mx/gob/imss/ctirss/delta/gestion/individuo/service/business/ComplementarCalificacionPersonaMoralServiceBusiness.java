/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.utility.CompararPersonaMoralEntidadExternaUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Calificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.CalificacionPersona;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessLocal;

/**
 * 130912
 * @author Samuel Rodríguez Grajeda
 *
 */
@Stateless(name = "complementarCalificacionPersonaMoralServiceBusiness", mappedName = "complementarCalificacionPersonaMoralServiceBusiness")
public class ComplementarCalificacionPersonaMoralServiceBusiness extends AbstractServiceBusiness implements ComplementarCalificacionPersonaMoralServiceBusinessRemote {
	
	
	@EJB
	private PersonaBusinessLocal personaBusiness;
	
	
	@EJB
	private CompararPersonaMoralEntidadExternaUtilityLocal compararPersonaMoralEntidadExternaUtility;

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ComplementarCalificacionPersonaMoralServiceBusinessRemote#complementarCalificaciones(mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral, mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral)
	 */
	
	/**
	 * 
	 * @param candidato
	 * @param entrada
	 * @return
	 */
	@Override
	public Moral complementarCalificaciones(Moral candidato, Moral entrada) throws ErrorComparacionDatosSATException, ClienteWebserviceSatRfcException{
		
		Moral c = null;
		boolean hasCalificacionIMSS = false;
		boolean hasCalificacionSAT = false;
		// 1. Obtenemos la calificacion del candidato.
		List<PersonaCalificacion> calificaciones = candidato.getPersonaCalificaciones();
		for(PersonaCalificacion pc: calificaciones){
			Calificacion c1 = pc.getCalificacion();
			if( c1.getIdCalificacion().intValue() == CalificacionPersona.VALIDADO_IMSS.intValue()){
				hasCalificacionIMSS = true;
				log.info("Cuenta con calificacion IMSS: " + hasCalificacionIMSS);
			}else if(c1.getIdCalificacion().intValue() == CalificacionPersona.VALIDADO_SAT.intValue()){
				hasCalificacionSAT = true;
			}
		}
		
		// 2. Revisamos si es necesario complementar la calificacion de SAT
		if( !hasCalificacionSAT){
			c = this.complementarCalificacionSAT(candidato, entrada);
		}
		
		return c;
	}
	
	
	/**
	 * 
	 * @param candidato
	 * @param entrada
	 * @return
	 * @throws ErrorComparacionDatosSATException
	 */
	private Moral complementarCalificacionSAT(Moral candidato, Moral entrada) throws ErrorComparacionDatosSATException, ClienteWebserviceSatRfcException{
		
		String rfc = entrada.getRfc();
		try {
			//Obtenemos la informacion del SAT.
			 Moral entidad = this.personaBusiness.buscarPersonaMoralPorRfcEnSat(rfc);
			 
			 // Comparamos los elementos de la informacion de entrada y de la entidad
			 candidato = this.compararPersonaMoralEntidadExternaUtility.compararPersonaMoralConSAT(candidato, entidad, entrada);
			 
		} catch (ClienteWebserviceSatRfcException e) {
			// TODO: En caso de que el servicio no este disponible...
			throw e;
		}
		return candidato;
		
	}





}
