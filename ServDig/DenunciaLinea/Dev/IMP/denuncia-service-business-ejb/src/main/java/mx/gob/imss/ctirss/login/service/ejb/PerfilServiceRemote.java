package mx.gob.imss.ctirss.login.service.ejb;

import javax.ejb.Remote;

import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.login.service.interfaces.PerfilService;


@Remote
public interface PerfilServiceRemote<T extends AbstractModel> extends PerfilService<T> {

}
