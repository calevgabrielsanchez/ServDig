package mx.gob.imss.ctirss.correccion.catalogos.service.ejb;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.correccion.catalogos.service.interfaces.GrupoCategoriaService;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@Remote
public interface GrupoCategoriaServiceRemote <T extends AbstractModel> extends GrupoCategoriaService<T> {

}
