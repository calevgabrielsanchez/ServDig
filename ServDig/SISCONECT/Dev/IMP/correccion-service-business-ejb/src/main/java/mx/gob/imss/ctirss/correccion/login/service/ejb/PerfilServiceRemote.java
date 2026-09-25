package mx.gob.imss.ctirss.correccion.login.service.ejb;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.login.service.interfaces.PerfilService;


@Remote
public interface PerfilServiceRemote<T extends AbstractModel> extends PerfilService<T> {

}
