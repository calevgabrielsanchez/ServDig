/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo: PersonaServiceBusiness.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.persona
 *  @Fecha:17/08/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.persona;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.model.clasificacion.TitularSuplente;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.persona.PersonaServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.persona.PersonaServiceBusinessRemote;

@Stateless(name="personaServiceBusiness", mappedName="personaServiceBusiness")
public class PersonaServiceBusiness extends AbstractServiceBusiness implements
		PersonaServiceBusinessRemote {
	
	@EJB
	PersonaServiceEntityLocal personaEntity;

	@Override
	public List<TitularSuplente> consultaPersona()throws Exception{
		List<TitularSuplente> lstTitularSuplente=new ArrayList<TitularSuplente>();
		lstTitularSuplente=personaEntity.consultaPersonaFuncionario();
		
		return lstTitularSuplente;
	}

	@Override
	public TitularSuplente consultaPersonaPorId(Long id) throws Exception{
		return personaEntity.consultaPersonaPorId(id);
	}
}
