/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: ClasificacionEntityLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clasificacion
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clasificacion;

import javax.ejb.Local;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.persistence.DicFraccionClase;

@Local
public interface ClasificacionEntityLocal{
	Clasificacion consultaPorClave(Clasificacion model) throws PersistenceException;
	
	void actualizaFraccion(Clasificacion clasificacion, Fraccion fraccion) throws PersistenceException;
	
	void elimina(long idClasificacion) throws PersistenceException;

    DicFraccionClase selectDicFraccionClase(Fraccion fraccion);
    
    Clasificacion consultaPorId(Clasificacion model) throws PersistenceException;
    
    void actualizaFraccionyPrima(Clasificacion clasificacion, Fraccion fraccion) throws PersistenceException;
    
    void actualizaSolicitudyTramite(String cveIdSolicitud, Long cveIdEstadoSol, Long cveIdEstadoTram) throws PersistenceException;
}
