package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.individuo.estado.EstadosNoExistentesException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.utility.EstadosPersonaFisicaServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaEstado;
import mx.gob.imss.ctirss.delta.persistence.DitHistEstadoPersona;
import mx.gob.imss.ctirss.gestionpersonas.servicios.entity.EstadosPersonaFisicaServiceEntityLocal;

/**
 * 181012
 * @author ICCSRG
 *
 */
@Stateless(name="estadosPersonaFisicaServiceBusiness", mappedName="estadosPersonaFisicaServiceBusiness")
public class EstadosPersonaFisicaServiceBusiness extends AbstractServiceBusiness implements EstadosPersonaFisicaServiceBusinessRemote{

	@EJB
	EstadosPersonaFisicaServiceEntityLocal estadosPersonaFisicaServiceEntity;
	
	@EJB
	EstadosPersonaFisicaServiceUtilityLocal estadosPersonaFisicaServiceUtility;
	
	
	/**
	 * Metodo encargado de realizar el registro de los estados asociados a una persona fisica
	 * @param fisica
	 * @throws EstadosNoExistentesException 
	 */
	@Override
	public void registrar(Fisica fisica) throws EstadosNoExistentesException {
		
		List<PersonaEstado> personaEstados = fisica.getPersonaEstados();
		if(personaEstados == null){
			throw new EstadosNoExistentesException();
		}

		// Iteramos la lista de los estados, y guardamos en BD uno por uno
		for(PersonaEstado personaEstado: personaEstados){
			DitHistEstadoPersona ditHistEstadoPersona = estadosPersonaFisicaServiceUtility.transformarAEntidad(personaEstado, fisica);
			estadosPersonaFisicaServiceEntity.registrar(ditHistEstadoPersona);
		}
		
	}

	/**
	 * Metodo encargado de realizar la actualizacion de los estados asociados a una persona fisica
	 * @param fisica
	 */
	@Override
	public void actualizar(Fisica fisica) {
		// TODO Auto-generated method stub
	}
	
	

}
