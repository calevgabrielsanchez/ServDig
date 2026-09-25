package mx.gob.imss.ctirss.correccion.catalogos.service.ejb;

import javax.ejb.Remote;
import mx.gob.imss.ctirss.correccion.catalogos.service.interfaces.TrabajadoresService;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@Remote
public interface TrabajadoresServiceRemote<T extends AbstractModel> extends TrabajadoresService<T> {

}
