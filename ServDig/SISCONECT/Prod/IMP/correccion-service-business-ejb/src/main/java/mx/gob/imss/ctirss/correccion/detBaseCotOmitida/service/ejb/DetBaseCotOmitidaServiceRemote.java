package mx.gob.imss.ctirss.correccion.detBaseCotOmitida.service.ejb;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.correccion.detBaseCotOmitida.service.interfaces.DetBaseCotOmitidaService;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@Remote
public interface DetBaseCotOmitidaServiceRemote <T extends AbstractModel> extends DetBaseCotOmitidaService<T>{

}
