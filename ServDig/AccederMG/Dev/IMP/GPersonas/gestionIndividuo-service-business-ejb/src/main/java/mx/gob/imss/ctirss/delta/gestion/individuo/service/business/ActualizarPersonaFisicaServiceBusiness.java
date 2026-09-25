package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.ComponentesExternosBusinessLocal;
import mx.gob.imss.ctirss.gestionpersonas.servicios.entity.PersonaEntityLocal;

@Stateless(name = "actualizarPersonaFisicaServiceBusiness", mappedName = "actualizarPersonaFisicaServiceBusiness")
public class ActualizarPersonaFisicaServiceBusiness extends AbstractServiceBusiness implements ActualizarPersonaFisicaServiceBusinessRemote{
	
    @EJB
    private ComponentesExternosBusinessLocal componentesExternosBusiness;   
    
    @EJB
    private transient PersonaEntityLocal personaEntity;
    
    
	/**
	 * 191807 081012
	 * Metodo encargado de actualizar una persona fisica en BD
	 * @param fisica
	 * @return
	 * @throws
	 */
	@Override
	public Fisica actualizarPersonaFisica(Fisica fisica){
		
        Fisica personaFisicaResultado = null;

        // Se verifica que la persona 'fisica' no sea nula
        if (fisica != null) {

        	try{
        		// Alta de los domicilios asignados a la persona
        		componentesExternosBusiness.altaDomicilios(fisica);
            	
        		// Alta de los medios de contacto asignados a la persona
        		componentesExternosBusiness.altaMediosContacto(fisica);
        		
        		// Alta de los documentos probatorios asignados a la persona
        		componentesExternosBusiness.altaDocumentosProbatorios(fisica);        	
         
        		personaFisicaResultado = personaEntity.actualizarPersonaFisica(fisica);
            
          	}catch(Exception e){
        		e.printStackTrace();
        	} 
        }
        
        return personaFisicaResultado;
	}

}
