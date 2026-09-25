package mx.gob.imss.ctirss.correccion.service.ejb.dao.impl;

import javax.ejb.Stateless;
import mx.gob.imss.ctirss.correccion.model.CrtRevDerivASubd;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.CrtRevDerivASubdDAOLocal;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.GenericHibernateDAO;

@Stateless
public class CrtRevDerivASubdHibernateDAO 
	   extends GenericHibernateDAO<CrtRevDerivASubd, Integer>
	   implements CrtRevDerivASubdDAOLocal{
}
