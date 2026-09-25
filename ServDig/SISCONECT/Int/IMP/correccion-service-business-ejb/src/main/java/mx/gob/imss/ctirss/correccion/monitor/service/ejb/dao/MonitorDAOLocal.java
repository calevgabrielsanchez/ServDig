package mx.gob.imss.ctirss.correccion.monitor.service.ejb.dao;

import javax.ejb.Local;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@Local
public interface MonitorDAOLocal <T extends AbstractModel> extends MonitorDAO <T>{

}
