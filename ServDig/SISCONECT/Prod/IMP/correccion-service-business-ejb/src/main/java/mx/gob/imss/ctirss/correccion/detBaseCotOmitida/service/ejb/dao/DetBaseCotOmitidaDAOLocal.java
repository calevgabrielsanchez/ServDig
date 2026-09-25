package mx.gob.imss.ctirss.correccion.detBaseCotOmitida.service.ejb.dao;

import javax.ejb.Local;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@Local
public interface DetBaseCotOmitidaDAOLocal <T extends AbstractModel> extends DetBaseCotOmitidaDAO<T>{

}
