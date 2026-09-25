package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.Solicitud;

/**
 * @author Juan Manuel Marquez
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 12/04/2012
 */
@Local
public interface PistasAuditoriaDaoLocal {

	public Solicitud busquedaSolicitud(Long folio);
}
