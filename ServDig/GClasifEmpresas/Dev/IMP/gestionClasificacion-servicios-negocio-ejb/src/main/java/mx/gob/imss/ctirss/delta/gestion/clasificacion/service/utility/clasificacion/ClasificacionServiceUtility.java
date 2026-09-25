/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: ClasificacionServiceUtility.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.clasificacion
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.clasificacion;

import javax.ejb.Stateless;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clase;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;
import mx.gob.imss.ctirss.delta.persistence.DicClase;
import mx.gob.imss.ctirss.delta.persistence.DicDivision;
import mx.gob.imss.ctirss.delta.persistence.DicFraccion;
import mx.gob.imss.ctirss.delta.persistence.DicGrupo;
import mx.gob.imss.ctirss.delta.persistence.DitAnalisisCe;
import mx.gob.imss.ctirss.delta.persistence.DitClasificacion;
import mx.gob.imss.ctirss.delta.persistence.DitClasificacionPropuesta;

@Stateless
public class ClasificacionServiceUtility extends AbstractServiceUtility implements ClasificacionServiceUtilityLocal {
	
	@Override
	public DitClasificacion convertirModelToEntity(Clasificacion model)
			throws Exception {
		DitClasificacion entity= new DitClasificacion();
		DicFraccion fraccion=new DicFraccion();
		DicDivision division=new DicDivision();
		DicGrupo grupo=new DicGrupo();
		DicClase dicClase=new DicClase();
		log.info("------------------" + "\n------------------");
		log.info("*****************************************************************************");
		log.info("IDGRUPO:: " + model.getFraccion().getGrupo().getId());
		log.info("IDDIVISION:: " + model.getFraccion().getGrupo().getDivision().getId());
		log.info("IDFRACCION:: " + model.getFraccion().getId());
		log.info("*****************************************************************************");
		log.info("------------------" + "\n------------------");
		grupo.setCveIdGrupo(model.getFraccion().getGrupo().getId());
		division.setCveIdDivision(model.getFraccion().getGrupo().getDivision().getId());
		fraccion.setCveIdFraccion(model.getFraccion().getId());
		dicClase.setNumPrimaMedia(model.getFraccion().getPrimaSRT());
		//fraccion.setDicClase(dicClase);
		grupo.setDicDivision(division);
		fraccion.setDicGrupo(grupo);
		//entity.setDicFraccion(fraccion);
		entity.setCveIdClasificacion(1L);
		log.info("------------------" + "\n------------------");
		log.info("*****************************************************************************");
		log.info("cveIdClasificacion:: " + entity.getCveIdClasificacion());
		log.info("*****************************************************************************");
		log.info("------------------" + "\n------------------");
			
		return entity;
	}

	@Override
	public Clasificacion convertirEntityToModel(DitClasificacion entity)
			throws Exception{
		Clasificacion model = new Clasificacion();
		Fraccion fraccion=new Fraccion();
		Division division=new Division();
		Clase clase=new Clase();
		Grupo grupo=new Grupo();
		try{
			if (entity.getDicFraccionClase() != null){
                DicFraccion dicFraccion = entity.getDicFraccionClase().getDicFraccion();
                DicClase dicClase = entity.getDicFraccionClase().getDicClase();
				fraccion.setId(dicFraccion.getCveIdFraccion());
				fraccion.setDescripcion(dicFraccion.getDesFraccion());
				fraccion.setNumFraccion(dicFraccion.getNumFraccion());
				fraccion.setPrimaSRT(dicClase.getNumPrimaMedia());
				
				clase.setClave(dicClase.getCveIdClase());
				clase.setDescripcion(dicClase.getDesClase());
				
				fraccion.setClase(clase);
				model.setFraccion(fraccion);
                DicGrupo dicGrupo = dicFraccion.getDicGrupo();
				if (dicGrupo != null){
					grupo.setId(dicGrupo.getCveIdGrupo());
					grupo.setDescripcion(dicGrupo.getDesGrupo());
					grupo.setNumGrupo(dicGrupo.getNumGrupo());
					model.getFraccion().setGrupo(grupo);
                    DicDivision dicDivision = dicGrupo.getDicDivision();
					if (dicDivision != null){
						division.setId(dicDivision.getCveIdDivision());
						division.setDescripcion(dicDivision.getDesDivision());
						division.setNumDivision(dicDivision.getNumDivision());
						model.getFraccion().getGrupo().setDivision(division);
					}
				}
			}
		}catch (Exception e){
			log.error(e.getMessage() , e);
			e.printStackTrace();
			throw e;
		}
		return model;
	}

	@Override
	public DitClasificacionPropuesta convertirModelToEntity(
			AnalisisClasificacionEmpresas model) throws Exception{
		DitClasificacionPropuesta entity=new DitClasificacionPropuesta();
		DitAnalisisCe dicAnalisis=new DitAnalisisCe();
		DicFraccion dicFraccion=new DicFraccion();
		DicGrupo dicGrupo=new DicGrupo();
		DicDivision dicDivision=new DicDivision();
		DicClase dicClase=new DicClase();
		dicAnalisis.setCveIdAnalisis(Long.parseLong(String.valueOf(model.getCveIdAnalisis())));
		entity.setDitAnalisisCe(dicAnalisis);
		dicDivision.setCveIdDivision(model.getClasificacionPropuesta().getFraccion().getGrupo().getDivision().getId());
		dicGrupo.setCveIdGrupo(model.getClasificacionPropuesta().getFraccion().getGrupo().getId());
		dicFraccion.setCveIdFraccion(model.getClasificacionPropuesta().getFraccion().getId());
		dicClase.setCveIdClase(model.getClasificacionPropuesta().getFraccion().getClase().getClave());
		dicGrupo.setDicDivision(dicDivision);
		dicFraccion.setDicGrupo(dicGrupo);
		//dicFraccion.setDicClase(dicClase);
		entity.setDicFraccion(dicFraccion);
		entity.setDesActividadDetectada(model.getActividadDetectada());
			
		return entity;
	}
}
