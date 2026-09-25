package mx.imss.ctirss.denuncia.service.ejb;

import javax.ejb.Remote;

import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.login.service.interfaces.IDenunciaService;

@Remote
public interface DenunciaServiceRemote <T extends AbstractModel> extends IDenunciaService<T>{

}
