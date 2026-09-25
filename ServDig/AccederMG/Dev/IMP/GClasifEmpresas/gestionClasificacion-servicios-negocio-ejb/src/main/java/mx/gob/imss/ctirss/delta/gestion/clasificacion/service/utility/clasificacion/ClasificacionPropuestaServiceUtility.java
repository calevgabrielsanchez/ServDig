
/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: ClasificacionPropuestaServiceUtility.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.clasificacion
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.clasificacion;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;
import mx.gob.imss.ctirss.delta.persistence.DitClasificacionPropuesta;

@Stateless
public class ClasificacionPropuestaServiceUtility extends AbstractServiceUtility implements ClasificacionPropuestaServiceUtilityLocal{

	@Override
	public AnalisisClasificacionEmpresas convertirEntityToModel(DitClasificacionPropuesta entity) throws PersistenceException {
		AnalisisClasificacionEmpresas model=new AnalisisClasificacionEmpresas();
		Clasificacion clasificacion=new Clasificacion();
		Fraccion fraccion=new Fraccion();
		Grupo grupo=new Grupo();
		Division division=new Division();
		
		model.setCveIdAnalisis(Long.valueOf(entity.getDitAnalisisCe().getCveIdAnalisis()));
		
		fraccion.setId(entity.getDicFraccion().getCveIdFraccion());
		grupo.setId(entity.getDicGrupo().getCveIdGrupo());
		division.setId(entity.getDicDivision().getCveIdDivision());
		grupo.setDivision(division);
		fraccion.setGrupo(grupo);
		clasificacion.setFraccion(fraccion);
		
		model.setClasificacionPropuesta(clasificacion);
		model.setActividadDetectada(entity.getDesActividadDetectada());
		
		return model;
	}

}
 
