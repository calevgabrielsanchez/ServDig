package mx.gob.imss.ctirss.correccion.service.ejb;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.service.interfaces.ISelectService;

/**
 * @author Juan Manuel Lopez Lozano
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 08/10/2011
 */
@Remote
public interface SelectServiceRemote extends ISelectService<AbstractModel>{

}
