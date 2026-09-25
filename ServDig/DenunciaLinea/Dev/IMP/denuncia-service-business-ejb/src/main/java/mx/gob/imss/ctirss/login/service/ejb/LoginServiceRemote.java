package mx.gob.imss.ctirss.login.service.ejb;

import javax.ejb.Remote;

import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.login.service.interfaces.LoginService;;


@Remote
public interface LoginServiceRemote<T extends AbstractModel> extends LoginService<T> {

}
