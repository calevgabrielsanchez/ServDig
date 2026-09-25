package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import javax.ejb.Remote;

/**
 * @author Juan Manuel Marquez
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 12/04/2012
 */


@Remote
public interface PistasAuditoriaServiceRemote {

	public void insertPistasAuditoria(Object pistasAuditoria);
}
