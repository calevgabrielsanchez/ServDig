package mx.gob.imss.ctirss.domiciliosInegi.service.ejb.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.service.AbstractService;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;
import mx.gob.imss.ctirss.domiciliosInegi.service.ejb.DomiciliosInegiServiceRemote;
import mx.gob.imss.ctirss.domiciliosInegi.service.ejb.dao.DomiciliosInegiDAOLocal;

@Stateless(name="domiciliosInegiService", mappedName = "domiciliosInegiService")
public class DomiciliosInegiServiceBean<T extends AbstractModel> extends AbstractService implements DomiciliosInegiServiceRemote<T>{
	
	@EJB DomiciliosInegiDAOLocal<T> dao;
	
	public T agregar(T model) {
		//dao.agrega(setFieldsBeforeInsert(model));
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
	
//	public T consultaPorClave(T filtro) {
//		return dao.consultaPorClave(filtro);
//	}
	
	public T consultaPorClave(T filtro) {

		T ob;
		DgDomicilioGeografico domi=(DgDomicilioGeografico) filtro;
		domi=dao.getDomicilioBDTU(domi);
		if(domi!=null){
			ob=(T) domi;	
		}else{
			ob=null;
		}
		
		return ob;
	}

	public DatosSalidaPaginador<T> pagina(DatosEntradaPaginador<T> params){
		return dao.pagina(params);
	}

	

}
