package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.DerechohabienteDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.PistasAuditoriaServiceRemote;

/**
 * @author Juan Manuel Marquez 
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 12/04/2012
 */
@Stateless( name = "pistasAuditoriaService", mappedName = "pistasAuditoriaService")
public class PistasAuditoriaService implements PistasAuditoriaServiceRemote{

	@EJB
	private DerechohabienteDaoLocal registrarDerechohabienteDaoLocal;
	
	

	@Override
	public void insertPistasAuditoria(Object pistasAuditoria) {
		// TODO Auto-generated method stub
		
	}

}
