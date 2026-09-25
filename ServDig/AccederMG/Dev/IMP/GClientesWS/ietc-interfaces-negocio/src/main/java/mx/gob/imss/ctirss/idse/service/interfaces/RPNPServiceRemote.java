package mx.gob.imss.ctirss.idse.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.idse.model.RequerimientoIDSEBean;
import mx.gob.imss.ctirss.idse.model.RequerimientoIETCBean;

@Remote
public interface RPNPServiceRemote {
	
	void registrarAltaPatronalIDSE(RequerimientoIETCBean ietcParameters, RequerimientoIDSEBean idseParameters);
}
