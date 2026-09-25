package mx.gob.imss.ctirss.correccion.catalogos.service.ejb.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.service.ejb.GrupoCategoriaServiceRemote;
import mx.gob.imss.ctirss.correccion.catalogos.service.ejb.dao.GrupoCategoriaDAOLocal;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.service.AbstractService;

@Stateless(name="grupoCategoriaService", mappedName = "grupoCategoriaService")
public class GrupoCategoriaServiceBean <T extends AbstractModel> extends AbstractService implements GrupoCategoriaServiceRemote<T> {

	@EJB GrupoCategoriaDAOLocal<T> dao;
	
	public T agregar(T model) {
		dao.agrega(setFieldsBeforeInsert(model));
		return model;
	}
	
	public void eliminar(T model){
		dao.elimina(model);
	}
	
	public T modificar(T model) {
		dao.modifica(setFieldsBeforeUpdate(model));
		return model;
	}
	
	public List<T> consultar(T filtro) {
		return dao.consulta(filtro);
	}
	
	public T consultaPorClave(T filtro) {
		return dao.consultaPorClave(filtro);
	}

	public DatosSalidaPaginador<T> pagina(DatosEntradaPaginador<T> params){
		return dao.pagina(params);
	}

	

}
