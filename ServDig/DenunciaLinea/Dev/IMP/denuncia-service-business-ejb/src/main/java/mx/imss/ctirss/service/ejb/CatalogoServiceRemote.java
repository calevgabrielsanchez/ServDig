package mx.imss.ctirss.service.ejb;

import javax.ejb.Remote;

import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.service.interfaces.ICatalogoService;


/**
 * @author Juan Manuel Lopez Lozano
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 28/08/2011
 */
@Remote
public interface CatalogoServiceRemote<T extends AbstractModel> extends ICatalogoService<T>{

}
