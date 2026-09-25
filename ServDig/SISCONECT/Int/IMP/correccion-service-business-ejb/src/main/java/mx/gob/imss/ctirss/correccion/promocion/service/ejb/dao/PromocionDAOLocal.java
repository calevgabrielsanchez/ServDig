package mx.gob.imss.ctirss.correccion.promocion.service.ejb.dao;

import java.util.Date;

import javax.ejb.Local;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;

@Local
public interface PromocionDAOLocal<T extends AbstractModel> extends PromocionDAO<T>{

	

}
