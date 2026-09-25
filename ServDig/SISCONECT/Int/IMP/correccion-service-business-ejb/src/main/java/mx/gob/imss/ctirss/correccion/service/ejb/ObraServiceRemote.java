package mx.gob.imss.ctirss.correccion.service.ejb;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.service.interfaces.IObraService;

@Remote
public interface ObraServiceRemote extends IObraService<AbstractModel>{

}
