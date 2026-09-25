/**
*
*
**/
package mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity;


import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.firma.FirmaDigitalException;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;


/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hugo Armando Martínez Chamónica
 *  @Proyecto: delta
 *  @Archivo: SolicitudServiceEntityLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.solicitud
 *  @Fecha: 10:26:49
 */
@Local
public interface SolicitudFirmaDigitalServiceEntityLocal {
	
	FirmaElectronica consultarFirmaElectronica(Solicitud solicitud);
	void insertarSolicitudFirmaDigital(Solicitud solicitud, FirmaElectronica firma);
	void insertarSolicitudFirmaDigitalThrowError(Solicitud solicitud, FirmaElectronica firma)throws FirmaDigitalException;
	
}
