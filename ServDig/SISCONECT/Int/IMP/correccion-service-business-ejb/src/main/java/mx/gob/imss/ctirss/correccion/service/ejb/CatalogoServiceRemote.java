package mx.gob.imss.ctirss.correccion.service.ejb;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;


/**
 * @author Juan Manuel Lopez Lozano
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 28/08/2011
 */
@Remote
public interface CatalogoServiceRemote<T extends AbstractModel> extends ICatalogoService<T>{

}
