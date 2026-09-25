package mx.gob.imss.ctirss.correccion.menu.service.ejb;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.menu.service.interfaces.MenuService;

@Remote
public interface MenuServiceRemote<T extends AbstractModel> extends MenuService<T> {

}
