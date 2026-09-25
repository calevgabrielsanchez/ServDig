package mx.gob.imss.ctirss.delta.gestion.individuo.service.utility;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaEstado;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoPersona;
import mx.gob.imss.ctirss.delta.persistence.DitHistEstadoPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;

@Stateless(name="estadosPersonaFisicaServiceUtility", mappedName="estadosPersonaFisicaServiceUtility")
public class EstadosPersonaFisicaServiceUtility extends AbstractServiceUtility implements EstadosPersonaFisicaServiceUtilityLocal{

	/**
	 * Metodo encargado de realizar la transformacion de un objeto de entidad a uno de modelo
	 * @param ditHistEstadoPersona
	 * @return
	 */
	@Override
	public PersonaEstado transformarAModelo(DitHistEstadoPersona ditHistEstadoPersona) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Metodo encargado de realizar la transformacion de un objeto de modelo a uno de entidad
	 * @param personaEstado
	 * @param fisica
	 * @return
	 */
	@Override
	public DitHistEstadoPersona transformarAEntidad(PersonaEstado personaEstado, Fisica fisica) {
		
		DitHistEstadoPersona ditHistEstadoPersona = null;
		DitPersona ditPersona = null;
				
		if(personaEstado != null){			
			
			DicEstadoPersona dicEstadoPersona = new DicEstadoPersona();
			dicEstadoPersona.setCveEstadoPersona(personaEstado.getEstadoPersona().getIdEstadoPersona().intValue());
			
			ditHistEstadoPersona = new DitHistEstadoPersona();
			ditHistEstadoPersona.setDicEstadoPersona(dicEstadoPersona);
			
			ditPersona = new DitPersona();
			ditPersona.setCveIdPersona(fisica.getIdPersona());
			
			ditHistEstadoPersona.setDitPersona(ditPersona);
			
			ditPersona.getDitHistEstadoPersonas().add(ditHistEstadoPersona);
			
		}
		
		return ditHistEstadoPersona;
	}
	
}
