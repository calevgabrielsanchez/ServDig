package mx.gob.imss.ctirss.correccion.seguimiento.pagos.service.ejb.dao;


import java.util.List;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model.CrtCobranzaPagos;
import mx.gob.imss.ctirss.correccion.seguimiento.service.interfaces.pagos.PagosService;

public interface PagosDAO <T extends AbstractModel> extends PagosService<T>{
	
	public List<CrtCobranzaPagos> getDetalle(CrtCobranzaPagos cobranzaPagos);
}
