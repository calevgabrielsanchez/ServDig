package mx.gob.imss.ctirss.delta.service.ejb.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractService;
import mx.gob.imss.ctirss.delta.framework.exceptions.CatalogoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TechnicalPersistenceException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.service.ejb.dao.CatalogoDAOLocal;
import mx.gob.imss.ctirss.delta.service.interfaces.CatalogoServiceRemote;



/**
 * @author Juan Manuel Lopez Lozano
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 28/08/2011
 */

//@WebService(name = "CatalogoServiceRemote", serviceName = "CatalogoServiceRemote", targetNamespace = "urn:CatalogoServiceRemote")
//
//@Stateless(name="catalogoService", mappedName = "catalogoService") 
public class CatalogoServiceBean <T extends AbstractModel> extends AbstractService implements CatalogoServiceRemote<T>{
	
	
	public CatalogoServiceBean() {
		super();
	}

	@EJB CatalogoDAOLocal<T> daoDelta;	

	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.interfaces.ICatalogoService#agregar(mx.gob.imss.delta.framework.base.model.AbstractModel)
	 */
	@WebMethod
	public T agregar(T model)  throws CatalogoException, TechnicalPersistenceException{
		try{
			daoDelta.agrega(setFieldsBeforeInsert(model));
			return model;
		}catch(PersistenceException pe){
			throw new TechnicalPersistenceException(pe.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.interfaces.ICatalogoService#actualizar(mx.gob.imss.delta.framework.base.model.AbstractModel)
	 */
	@WebMethod
	public T actualizar(T model)  throws CatalogoException, TechnicalPersistenceException{
		try{
			T objModificado 	= setFieldsBeforeUpdate(model);
			T modelPersistido 	= this.consultaPorClave(objModificado); 
			objModificado   	= setExcludedFieldsBeforeUpdate(objModificado, modelPersistido);
			daoDelta.actualiza(objModificado);
			return model;			
		}catch(PersistenceException pe){
			throw new TechnicalPersistenceException(pe.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.interfaces.ICatalogoService#eliminar(mx.gob.imss.delta.framework.base.model.AbstractModel)
	 */
	@WebMethod
	public void eliminar(T model)  throws CatalogoException, TechnicalPersistenceException{
		try{
			T objModificado		= setFieldsBeforeUpdate(model);
			T modelPersistido 	= this.consultaPorClave(objModificado); 
			objModificado 		= setExcludedFieldsBeforeDelete(objModificado, modelPersistido);
			daoDelta.elimina(objModificado);			
		}catch(PersistenceException pe){
			throw new TechnicalPersistenceException(pe.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.interfaces.ICatalogoService#consultar(mx.gob.imss.delta.framework.base.model.AbstractModel)
	 */
	@WebMethod
	public List<T> consultar(T filtro)  throws CatalogoException, TechnicalPersistenceException{
		try{
			return daoDelta.consulta(filtro);
		}catch(PersistenceException pe){
			throw new TechnicalPersistenceException(pe.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.interfaces.ICatalogoService#consultar(mx.gob.imss.delta.framework.base.model.AbstractModel)
	 */
	@WebMethod
	public T consultaPorClave(T filtro)  throws CatalogoException, TechnicalPersistenceException{
		try{
			return daoDelta.consultaPorClave(filtro);			
		}catch(PersistenceException pe){
			throw new TechnicalPersistenceException(pe.getMessage());
		}
	}	

	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.interfaces.ICatalogoService#pagina(mx.gob.imss.delta.base.paginador.model.DatosEntradaPaginador)
	 */
	@WebMethod
	public DatosSalidaPaginador<T> pagina(DatosEntradaPaginador<T> params) {
		return daoDelta.pagina(params);
	}

	
}
