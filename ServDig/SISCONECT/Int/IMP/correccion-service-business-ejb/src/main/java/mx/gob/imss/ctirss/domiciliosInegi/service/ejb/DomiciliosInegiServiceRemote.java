package mx.gob.imss.ctirss.domiciliosInegi.service.ejb;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.domiciliosInegi.service.interfaces.DomiciliosInegiService;

@Remote
public interface DomiciliosInegiServiceRemote<T extends AbstractModel> extends DomiciliosInegiService<T> {

}
