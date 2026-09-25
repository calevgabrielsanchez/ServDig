package mx.gob.imss.ctirss.correccion.solicitud.service.ejb;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.correccion.correccion.service.interfaces.SolicitudService;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@Remote
public interface SolicitudServiceRemote<T extends AbstractModel> extends SolicitudService<T> {

}
