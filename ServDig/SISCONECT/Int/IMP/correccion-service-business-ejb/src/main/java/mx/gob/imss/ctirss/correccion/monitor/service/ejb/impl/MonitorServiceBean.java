package mx.gob.imss.ctirss.correccion.monitor.service.ejb.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.service.AbstractService;
import mx.gob.imss.ctirss.correccion.model.CrtErrorCargaCed;
import mx.gob.imss.ctirss.correccion.monitor.service.ejb.MonitorServiceRemote;
import mx.gob.imss.ctirss.correccion.monitor.service.ejb.dao.MonitorDAOLocal;

@Stateless(name="monitorService", mappedName = "monitorService")
public class MonitorServiceBean <T extends AbstractModel> extends AbstractService implements MonitorServiceRemote<T>{

	@EJB MonitorDAOLocal<T> monitorDao;
	
	public  T agregar(T model) {
		// TODO Auto-generated method stub
		return monitorDao.agregar(model);
	}


	public void eliminar(T model) {
		// TODO Auto-generated method stub
		monitorDao.eliminar(model);
	}


	public T modificar(T model) {
		// TODO Auto-generated method stub
		monitorDao.modificar(model);
		return model;
	}


	public List<T> consultar(T filtro) {
		// TODO Auto-generated method stub
		return monitorDao.consultar(filtro);
	}


	public T consultaPorClave(T filtro) {
		// TODO Auto-generated method stub
		return monitorDao.consultaPorClave(filtro);
	}


	@Override
	public List<T> consultarErrores(CrtErrorCargaCed crtErrorCargaCed) {
		// TODO Auto-generated method stub
		return monitorDao.consultarErrores(crtErrorCargaCed);
	}

}
