/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo:PersonaServiceEntity.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.persona
 *  @Fecha:17/08/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.persona;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.clasificacion.TitularSuplente;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.persona.PersonaServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitUsuarioFuncionario;

@Stateless
public class PersonaServiceEntity extends AbstractServiceEntity implements PersonaServiceEntityLocal {
	
	@EJB
	PersonaServiceUtilityLocal personaUtility;

	@SuppressWarnings("unchecked")
	@Override
	public List<TitularSuplente> consultaPersonaFuncionario()throws Exception {
		this.log.debug("consultaPersona():: ");
		List<DitUsuarioFuncionario> usuarioFuncionario=new ArrayList<DitUsuarioFuncionario>();
		List<TitularSuplente> lstTitularSuplente=new ArrayList<TitularSuplente>();
		TitularSuplente titularSuplente=null;
		
		Query query=null;
		query=em.createQuery(
				"select uf from DitUsuarioFuncionario uf, DitUsuario u, DitPersona p " +
				" where uf.ditUsuario.cveIdUsuario=u.cveIdUsuario and u.ditPersona.cveIdPersona=p.cveIdPersona ");
		usuarioFuncionario=(ArrayList<DitUsuarioFuncionario>)query.getResultList();
		for(DitUsuarioFuncionario uf:usuarioFuncionario){
			titularSuplente=new TitularSuplente();
			titularSuplente=personaUtility.convertirEntityToModel(uf);
			lstTitularSuplente.add(titularSuplente);
		}
		return lstTitularSuplente;
	}

	@Override
	public TitularSuplente consultaPersonaPorId(Long id) throws Exception{
		DitPersona ditPersona=new DitPersona();
		TitularSuplente titularSuplente=new TitularSuplente();
		Query query=null;
		query=em.createQuery("from DitPersona p where p.cveIdPersona= :idPersona");
		query.setParameter("idPersona", id);
		ditPersona=(DitPersona)query.getSingleResult();
		titularSuplente=personaUtility.convertirEntityToModel(ditPersona);
		return titularSuplente;
	}
}