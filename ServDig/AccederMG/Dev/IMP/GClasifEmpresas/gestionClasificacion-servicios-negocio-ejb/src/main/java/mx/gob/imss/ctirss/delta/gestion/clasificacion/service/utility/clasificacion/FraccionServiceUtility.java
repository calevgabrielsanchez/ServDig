/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: FraccionServiceUtility.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.clasificacion
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.clasificacion;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Utiles;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.clasificacion.actividad.economica.ActividadEcServiceRemote;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clase;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;
import mx.gob.imss.ctirss.delta.persistence.DicClase;
import mx.gob.imss.ctirss.delta.persistence.DicDivision;
import mx.gob.imss.ctirss.delta.persistence.DicFraccion;
import mx.gob.imss.ctirss.delta.persistence.DicFraccionClase;
import mx.gob.imss.ctirss.delta.persistence.DicGrupo;
import mx.gob.imss.ctirss.delta.persistence.DitClasificacion;

@Stateless
public class FraccionServiceUtility extends AbstractServiceUtility implements FraccionServiceUtilityLocal {

    @EJB
    private ActividadEcServiceRemote clasificacionActividadEconomicaService;
	
	@Override
	public Fraccion convertirEntityToModel(DicFraccion entity) throws Exception{
		Fraccion fraccion=null;
		
		try{
			if (entity != null){
				
				log.info("------------------" + "\n------------------");
				log.info("****************** DENTRO DE convertirEntityToModel *****************************");
				log.info("IDFRACCION:: " + entity.getNumFraccion());
				log.info("IDGRUPO:: " + entity.getDicGrupo().getNumGrupo());
				log.info("IDDIVISION:: " + entity.getDicGrupo().getDicDivision().getNumDivision());
				log.info("*********************************************************************************");
				log.info("------------------" + "\n------------------");
				
                fraccion = clasificacionActividadEconomicaService.obtenerFraccionClaseActiva(entity.getCveIdFraccion());
			}
		}catch (Exception e){
			log.error(e.getMessage() , e);
			e.printStackTrace();
			throw e;
		}
		return fraccion;
	}

	/**
	 * {@inheritDoc}
	 * @see FraccionServiceUtilityLocal#convertirEntityToModelFraccion(DicFraccion)
	 */
	@Override
	public Fraccion convertirEntityToModelFraccion(
			final DicFraccion dicFraccion) {
		Fraccion fraccion = new Fraccion();
		fraccion.setId(dicFraccion.getCveIdFraccion());
		fraccion.setDescripcion(Utiles.toUpperCase(dicFraccion.getDesFraccion()));
		fraccion.setDescripcionDetallada(Utiles.toUpperCase(dicFraccion.getDesActividad()));
		fraccion.setGrupo(convertirEntityToModelGrupo(dicFraccion.getDicGrupo()));
        fraccion.setNumFraccion(dicFraccion.getNumFraccion());
        //TODO: LUDS esto es un parche....
        if(dicFraccion.getDicFraccionClases() != null && !dicFraccion.getDicFraccionClases().isEmpty() ){
        	DicFraccionClase dicFraccionClase = dicFraccion.getDicFraccionClases().get(0);
            fraccion.setClase(convertirEntityToModelClase(dicFraccionClase.getDicClase()));
            fraccion.setPrimaSRT(dicFraccionClase.getDicClase().getNumPrimaMedia());
        }
        
        return fraccion;
    }
	
	
	/**
	 * {@inheritDoc}
	 * @see FraccionServiceUtilityLocal#convertirEntityToModel(DitClasificacion)
	 */
	@Override
	public Clasificacion convertirEntityToModel(
			final DitClasificacion ditClasificacion) {
		Clasificacion clasificacion = new Clasificacion();
		clasificacion.setId(ditClasificacion.getCveIdClasificacion());
        clasificacion.setFraccion(convertirEntityToModelFraccion(ditClasificacion.getDicFraccionClase()));
		clasificacion.setIndDistribuyeEntrega(ditClasificacion.getIndDistribucionEntrega().intValue());
		clasificacion.setIndServiciosATerceros(ditClasificacion.getIndServicioOtrasPersonas().intValue());
		clasificacion.setIndTransporteAjeno(ditClasificacion.getIndTransporteAjeno() == null ? 0 : ditClasificacion.getIndTransporteAjeno().intValue());
		clasificacion.setIndTransportePropio(ditClasificacion.getIndTransportePropio() == null ? 0 : ditClasificacion.getIndTransportePropio().intValue());
		clasificacion.setIndPrestaServicioPersonal(ditClasificacion.getIndPrestaServicioPersonal() == null ? 0 : ditClasificacion.getIndPrestaServicioPersonal().intValue());
		clasificacion.setGiro(Utiles.toUpperCase(ditClasificacion.getManifestacion()));
		return clasificacion;
	}
	
	/**
	 * {@inheritDoc}
	 * @see FraccionServiceUtilityLocal#convertirEntityToModelFraccion(DicFraccionClase)
	 */
	@Override
	public Fraccion convertirEntityToModelFraccion(
			final DicFraccionClase dicFraccionClase) {
        DicFraccion dicFraccion = dicFraccionClase.getDicFraccion();
		Fraccion fraccion = convertirEntityToModelFraccion(dicFraccion);
        fraccion.setClase(convertirEntityToModelClase(dicFraccionClase.getDicClase()));
        fraccion.setPrimaSRT(dicFraccionClase.getDicClase().getNumPrimaMedia());
        return fraccion;
    }
	
    /**
     * Genera un objeto Grupo a partir del objeto enviado como parámetro
     * 
     * @param dicGrupo
     * @return grupo
     */
	@Override
    public Grupo convertirEntityToModelGrupo(final DicGrupo dicGrupo) {
        final Grupo grupo = new Grupo();
        grupo.setId(dicGrupo.getCveIdGrupo());
        grupo.setDescripcion(Utiles.toUpperCase(dicGrupo.getDesGrupo()));
        grupo.setDivision(convertirEntityToModelDivision(dicGrupo.getDicDivision()));
        grupo.setNumGrupo(dicGrupo.getNumGrupo());
        return grupo;
    }
    
    /**
     * Genera un objeto Clase a partir del objeto enviado como parámetro
     * 
     * @param dicClase
     * @return clase
     */
    @Override
    public Clase convertirEntityToModelClase(final DicClase dicClase) {
        final Clase clase = new Clase();
        clase.setClave(dicClase.getCveIdClase());
        clase.setDescripcion(Utiles.toUpperCase(dicClase.getDesClase()));
        return clase;
    }
    
    /**
     * Genera un objeto Division a partir del objeto enviado como parámetro
     * 
	 * @param dicDivision
	 * @return division
	 */
    @Override
	public Division convertirEntityToModelDivision(
			final DicDivision dicDivision) {
		final Division division = new Division();
		division.setId(dicDivision.getCveIdDivision());
		division.setDescripcion(Utiles.toUpperCase(dicDivision.getDesDivision()));
		division.setNumDivision(dicDivision.getNumDivision());
		return division;
	}
    
    
    /**
	 * {@inheritDoc}
	 * @see FraccionServiceUtilityLocal#convertirEntityToModelFraccion(DicFraccion)
	 */
    @Override
	public Fraccion convertirEntityToModelFraccionClaseActiva(
			final DicFraccion dicFraccion) {
		Fraccion fraccion = new Fraccion();
		fraccion.setId(dicFraccion.getCveIdFraccion());
		fraccion.setDescripcion(Utiles.toUpperCase(dicFraccion.getDesFraccion()));
		fraccion.setDescripcionDetallada(Utiles.toUpperCase(dicFraccion.getDesActividad()));
		fraccion.setGrupo(convertirEntityToModelGrupo(dicFraccion.getDicGrupo()));
        fraccion.setNumFraccion(dicFraccion.getNumFraccion());
        //TODO: LUDS esto es un parche....
        if(dicFraccion.getDicFraccionClases() != null && !dicFraccion.getDicFraccionClases().isEmpty() ){
	       	for(DicFraccionClase dicFraccionClase : dicFraccion.getDicFraccionClases() ) {
	       		if(dicFraccionClase.getFecFin() == null) {
	       			fraccion.setClase(convertirEntityToModelClase(dicFraccionClase.getDicClase()));
	       			fraccion.setPrimaSRT(dicFraccionClase.getDicClase().getNumPrimaMedia());
	       			break;
	       		}
	       	}
        }
        return fraccion;
    }
    
	
}
 
