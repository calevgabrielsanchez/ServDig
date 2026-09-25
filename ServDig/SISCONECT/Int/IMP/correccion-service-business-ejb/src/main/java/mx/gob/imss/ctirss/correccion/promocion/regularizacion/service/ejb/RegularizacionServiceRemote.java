package mx.gob.imss.ctirss.correccion.promocion.regularizacion.service.ejb;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.promocion.regularizacion.service.interfaces.RegularizacionService;

@Remote
public interface RegularizacionServiceRemote<T extends AbstractModel> extends RegularizacionService<T> {

}
