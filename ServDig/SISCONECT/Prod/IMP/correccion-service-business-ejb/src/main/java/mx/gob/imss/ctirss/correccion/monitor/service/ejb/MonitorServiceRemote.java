package mx.gob.imss.ctirss.correccion.monitor.service.ejb;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.monitor.service.interfaces.MonitorService;

@Remote
public interface MonitorServiceRemote  <T extends AbstractModel> extends MonitorService<T>{

}
