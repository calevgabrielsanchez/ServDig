/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;

/**
 * 
 * @author jponte
 */
@Local
public interface EjbEstatusSolicitudDaoLocal {

	public void altaEstadoSolicitud(EstadoSolicitud datosEstadoSolicitud);
}
