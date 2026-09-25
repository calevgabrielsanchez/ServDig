package mx.gob.imss.ctirss.correccion.administracion.auditor.ejb;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.correccion.administracion.auditor.AuditorService;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@Remote
public interface AuditorServiceRemote <T extends AbstractModel> extends AuditorService<T>{

}
