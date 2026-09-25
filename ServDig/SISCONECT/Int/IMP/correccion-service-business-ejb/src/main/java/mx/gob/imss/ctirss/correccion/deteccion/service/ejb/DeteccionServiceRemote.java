package mx.gob.imss.ctirss.correccion.deteccion.service.ejb;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.correccion.deteccion.service.interfaces.DeteccionService;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@Remote
public interface DeteccionServiceRemote<T extends AbstractModel> extends DeteccionService<T> {

}
