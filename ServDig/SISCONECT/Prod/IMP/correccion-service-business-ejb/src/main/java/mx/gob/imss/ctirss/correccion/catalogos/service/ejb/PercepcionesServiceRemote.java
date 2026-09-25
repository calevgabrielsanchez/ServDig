package mx.gob.imss.ctirss.correccion.catalogos.service.ejb;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.correccion.catalogos.service.interfaces.PercepcionesService;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@Remote
public interface PercepcionesServiceRemote<T extends AbstractModel> extends PercepcionesService<T> {

}
