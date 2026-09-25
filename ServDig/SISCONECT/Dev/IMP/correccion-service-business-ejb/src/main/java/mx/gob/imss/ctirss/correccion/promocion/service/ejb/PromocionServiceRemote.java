package mx.gob.imss.ctirss.correccion.promocion.service.ejb;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.promocion.service.interfaces.PromocionService;

@Remote
public interface PromocionServiceRemote<T extends AbstractModel> extends PromocionService<T>{

}
