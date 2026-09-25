package mx.imss.ctirss.menu.service.ejb;

import javax.ejb.Remote;

import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.menu.service.interfaces.MenuService;

@Remote
public interface MenuServiceRemote<T extends AbstractModel> extends MenuService<T> {

}
