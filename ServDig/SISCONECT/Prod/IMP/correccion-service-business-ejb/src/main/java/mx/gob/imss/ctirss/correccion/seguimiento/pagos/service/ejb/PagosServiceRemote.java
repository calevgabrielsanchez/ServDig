package mx.gob.imss.ctirss.correccion.seguimiento.pagos.service.ejb;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.seguimiento.service.interfaces.pagos.PagosService;

@Remote
public interface PagosServiceRemote <T extends AbstractModel> extends PagosService<T>{

}
