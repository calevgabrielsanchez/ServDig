package mx.gob.imss.ctirss.correccion.prorroga.service.ejb;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.prorroga.service.interfaces.ProrrogaService;

@Remote
public interface ProrrogaServiceRemote <T extends AbstractModel> extends ProrrogaService <T> {

}
