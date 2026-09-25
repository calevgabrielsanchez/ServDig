package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;


import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.Rol;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;



@Remote
public interface GuiaTramiteServiceRemote {
	
	public TipoTramite getRutaTramite(TipoTramite tipoTramite,Rol rol) throws DerechohabientesBusinessException;
	

}