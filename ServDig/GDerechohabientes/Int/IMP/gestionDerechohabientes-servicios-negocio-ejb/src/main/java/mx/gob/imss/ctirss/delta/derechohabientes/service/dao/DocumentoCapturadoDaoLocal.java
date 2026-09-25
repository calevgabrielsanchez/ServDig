package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import javax.ejb.Local;

@Local
public interface DocumentoCapturadoDaoLocal {

	Object save(Object obj) throws Exception;

}