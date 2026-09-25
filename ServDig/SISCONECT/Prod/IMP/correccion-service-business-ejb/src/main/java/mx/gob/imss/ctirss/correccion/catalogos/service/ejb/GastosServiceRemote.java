package mx.gob.imss.ctirss.correccion.catalogos.service.ejb;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.correccion.catalogos.service.interfaces.GastosService;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@Remote
public interface GastosServiceRemote<T extends AbstractModel> extends GastosService<T> {

}
