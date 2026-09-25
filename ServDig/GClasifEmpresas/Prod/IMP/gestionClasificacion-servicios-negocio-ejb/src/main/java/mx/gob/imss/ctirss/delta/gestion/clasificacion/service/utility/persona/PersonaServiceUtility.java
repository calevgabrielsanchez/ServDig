/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo: PersonaServiceUtility.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.persona
 *  @Fecha: 17/08/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.persona;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.clasificacion.TitularSuplente;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitUsuarioFuncionario;

@Stateless
public class PersonaServiceUtility extends AbstractServiceUtility implements
		PersonaServiceUtilityLocal {

	@Override
	public TitularSuplente convertirEntityToModel(DitUsuarioFuncionario ditFuncionario) throws Exception {
		TitularSuplente titularSuplente=new TitularSuplente();
		titularSuplente.setDesCargo(ditFuncionario.getDesCargo());
		titularSuplente.setCveIdDelegacion(ditFuncionario.getDicDelegacion().getCveIdDelegacion());
		titularSuplente.setCveIdSubdelegacion(ditFuncionario.getDicSubdelegacion().getCveIdSubdelegacion());
		titularSuplente.setNomNombre(ditFuncionario.getDitUsuario().getDitPersona().getNomNombre());
		titularSuplente.setCveIdPersona(ditFuncionario.getDitUsuario().getDitPersona().getCveIdPersona());
		titularSuplente.setNomPrimerApellido(ditFuncionario.getDitUsuario().getDitPersona().getNomPrimerApellido());
		titularSuplente.setNomSegundoApellido(ditFuncionario.getDitUsuario().getDitPersona().getNomSegundoApellido());
		
		return titularSuplente;
	}

	@Override
	public TitularSuplente convertirEntityToModel(DitPersona ditPersona)throws Exception{
		TitularSuplente titularSuplente=new TitularSuplente();
		titularSuplente.setCveIdPersona(ditPersona.getCveIdPersona());
		titularSuplente.setNomNombre(ditPersona.getNomNombre());
		titularSuplente.setNomPrimerApellido(ditPersona.getNomPrimerApellido());
		titularSuplente.setNomSegundoApellido(ditPersona.getNomSegundoApellido());
		
		return titularSuplente;
	}
}
