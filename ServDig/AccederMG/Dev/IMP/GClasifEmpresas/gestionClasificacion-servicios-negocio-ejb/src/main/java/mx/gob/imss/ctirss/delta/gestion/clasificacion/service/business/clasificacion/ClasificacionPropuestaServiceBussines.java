/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: ClasificacionPropuestaServiceBussines.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.clasificacion
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.clasificacion;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.clasificacion.ClaseNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.ClasificacionException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clasificacion.ClasificacionPropuestaServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clasificacion.ClasificacionPropuestaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;

@Stateless(name = "clasificacionPropuestaBusiness", mappedName = "clasificacionPropuestaBusiness")
public class ClasificacionPropuestaServiceBussines extends AbstractServiceBusiness implements ClasificacionPropuestaServiceBusinessRemote {

	@EJB
	private ClasificacionPropuestaServiceEntityLocal clasificacionPropuestaEntity;

	/* (non-Javadoc)
	 * 
	 * @see mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clasificacion.ClasificacionPropuestaBusinessRemote
	 * #obtenerClaseDeClasificacionPropuesta(String cveIdAnalisis)
	 */
	@Override
	public AnalisisClasificacionEmpresas obtenerClaseDeClasificacionPropuesta(String cveIdAnalisis)
			throws ClaseNoEncontradaException {
		AnalisisClasificacionEmpresas retVal = null;
		log.info("OBTENER CLASE A PARTIR DEL ID DEL ANALISIS");
		retVal = clasificacionPropuestaEntity.consultaPorIdAnalisis(new Long(cveIdAnalisis));
		return retVal;
	}

	/* (non-Javadoc)
	 * 
	 * @see mx.gob.imss.ctirss.delta.gestion.patron.clasificacion.propuesta.service.business.interfaces.ClasificacionPropuestaBusinessRemote
	 * #borrarClasficiacionPropuesta(mx.gob.imss.ctirss.delta.gestion.patron.model.Clasificacion)
	 */
	@Override
	public void borrarClasificacionPropuesta(long cveIdAnalisis)
			throws ClasificacionException {
		log.debug("INICIO PARA BORRAR LA CLASIFICACION PROPUESTA");
		try {
			this.clasificacionPropuestaEntity.elimina(cveIdAnalisis);
		} catch (Exception e) {
			log.error("ERROR- " + e.getMessage());
			throw new ClasificacionException("No se logr\u00F3 borrar la clasificaci\u00F3n propuesta.", 1600);
		}
		log.debug("FIN PARA GUARDAR LA CLASIFICACION PROPUESTA");
	}
	
	@Override
	public AnalisisClasificacionEmpresas consultaPorIdAnalisis(Long cveIdAnalisis) throws ClaseNoEncontradaException {
		return clasificacionPropuestaEntity.consultaPorIdAnalisis(cveIdAnalisis);
	}
}