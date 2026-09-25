package mx.gob.imss.ctirss.correccion.invitacion.service.ejb;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.invitacion.service.interfaces.InvitacionService;

@Remote
public interface InvitacionServiceRemote<T extends AbstractModel> extends InvitacionService<T>{

}
