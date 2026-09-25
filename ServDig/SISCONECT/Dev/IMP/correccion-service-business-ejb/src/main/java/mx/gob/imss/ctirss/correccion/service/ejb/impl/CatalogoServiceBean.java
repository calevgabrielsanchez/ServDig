package mx.gob.imss.ctirss.correccion.service.ejb.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.service.AbstractService;
import mx.gob.imss.ctirss.correccion.model.DicDivision;
import mx.gob.imss.ctirss.correccion.model.DicGrupo;
import mx.gob.imss.ctirss.correccion.service.ejb.CatalogoServiceRemote;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.CatalogoDAOLocal;

import org.apache.log4j.Logger;


/**
 * @author Juan Manuel Lopez Lozano
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 28/08/2011
 */
@Stateless(name="catalogoService", mappedName = "catalogoService") 
public class CatalogoServiceBean <T extends AbstractModel> extends AbstractService implements CatalogoServiceRemote<T>{
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger
			.getLogger(CatalogoServiceBean.class);
	@EJB CatalogoDAOLocal<T> daoDelta;

	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.interfaces.ICatalogoService#agregar(mx.gob.imss.delta.framework.base.model.AbstractModel)
	 */
	public T agregar(T model) {
		daoDelta.agrega(setFieldsBeforeInsert(model));
		return model;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.interfaces.ICatalogoService#actualizar(mx.gob.imss.delta.framework.base.model.AbstractModel)
	 */
	public T actualizar(T model) {
		daoDelta.actualiza(setFieldsBeforeUpdate(model));
		return model;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.interfaces.ICatalogoService#eliminar(mx.gob.imss.delta.framework.base.model.AbstractModel)
	 */
	public void eliminar(T model) {
		daoDelta.elimina(setFieldsBeforeUpdate(model));
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.interfaces.ICatalogoService#consultar(mx.gob.imss.delta.framework.base.model.AbstractModel)
	 */
	public List<T> consultar(T filtro) {
		return daoDelta.consulta(filtro);
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.interfaces.ICatalogoService#consultar(mx.gob.imss.delta.framework.base.model.AbstractModel)
	 */
	public T consultaPorClave(T filtro) {
		return daoDelta.consultaPorClave(filtro);
	}	

	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.interfaces.ICatalogoService#pagina(mx.gob.imss.delta.base.paginador.model.DatosEntradaPaginador)
	 */
	public DatosSalidaPaginador<T> pagina(DatosEntradaPaginador<T> params) {
		return daoDelta.pagina(params);
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.interfaces.ICatalogoService#consultaDicGrupoXDicDivision(CveIdDivision)
	 */
	public List<T> consultaDicGrupoXDicDivision(Long CveIdDivision) {
		DicGrupo dicGrupo 		= new DicGrupo();
		DicDivision dicDivision = new DicDivision();
		dicDivision.setCveIdDivision(CveIdDivision);
		dicGrupo.setDicDivision(dicDivision);
		return (List<T>) daoDelta.consulta((T) dicGrupo);
	}//consultaDicGrupoXDicDivision

	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.interfaces.ICatalogoService#pagina(mx.gob.imss.delta.base.paginador.model.DatosEntradaPaginador)
	 */
	public DatosSalidaPaginador<T> paginaDiv(DatosEntradaPaginador<T> params) {
		logger.debug(".-.-CatalogoServiceBean - public DatosSalidaPaginador<T> paginaDiv(DatosEntradaPaginador<T> params) {");
		return daoDelta.paginaDiv(params);
	}	
	
	public DatosSalidaPaginador<T> paginaAnexoPagos(DatosEntradaPaginador<T> params) {
		logger.debug(".-.-CatalogoServiceBean - public DatosSalidaPaginador<T> paginaAnexoPagos(DatosEntradaPaginador<T> params) {");
		return daoDelta.paginaAnexoPagos(params);
	}
	
	public DatosSalidaPaginador<T> paginaAnexoPagosA(DatosEntradaPaginador<T> params) {
		logger.debug(".-.-CatalogoServiceBean - public DatosSalidaPaginador<T> paginaAnexoPagos(DatosEntradaPaginador<T> params) {");
		return daoDelta.paginaAnexoPagosA(params);
	}
	
	public DatosSalidaPaginador<T> paginaAnexoPagosR(DatosEntradaPaginador<T> params) {
		logger.debug(".-.-CatalogoServiceBean - public DatosSalidaPaginador<T> paginaAnexoPagos(DatosEntradaPaginador<T> params) {");
		return daoDelta.paginaAnexoPagosR(params);
	}
	
	public DatosSalidaPaginador<T> paginaAnexoPatrones(DatosEntradaPaginador<T> params) {
		logger.debug(".-.-CatalogoServiceBean - public DatosSalidaPaginador<T> paginaAnexoPagos(DatosEntradaPaginador<T> params) {");
		return daoDelta.paginaAnexoPatrones(params);
	}
	
	public DatosSalidaPaginador<T> paginaAnexoConceptos(DatosEntradaPaginador<T> params) {
		logger.debug(".-.-CatalogoServiceBean - public DatosSalidaPaginador<T> paginaAnexoPagos(DatosEntradaPaginador<T> params) {");
		return daoDelta.paginaAnexoConceptos(params);
	}
	
	public DatosSalidaPaginador<T> paginaAnexoConceptosC(DatosEntradaPaginador<T> params) {
		System.out.println(".-.-CatalogoServiceBean - public DatosSalidaPaginador<T> paginaAnexoPagos(DatosEntradaPaginador<T> params) {");
		return daoDelta.paginaAnexoConceptosC(params);
	}
	
	public DatosSalidaPaginador<T> paginaAnexoConceptosPAI(DatosEntradaPaginador<T> params) {
		System.out.println(".-.-CatalogoServiceBean - public DatosSalidaPaginador<T> paginaAnexoPagos(DatosEntradaPaginador<T> params) {");
		return daoDelta.paginaAnexoConceptosPAI(params);
	}
	
	public DatosSalidaPaginador<T> paginaAnexoConceptosAPAI(DatosEntradaPaginador<T> params) {
		System.out.println(".-.-CatalogoServiceBean - public DatosSalidaPaginador<T> paginaAnexoPagos(DatosEntradaPaginador<T> params) {");
		return daoDelta.paginaAnexoConceptosAPAI(params);
	}
	
	@Override
	public List<T> consultaSQL(String query) {
		logger.debug("query :: [" + query +"]");
		return daoDelta.consultaSQL(query);
	}	
	
public List<T> consultaLibrePorClave(Long claveConsultar, String query) {
		return daoDelta.consultaLibrePorClave(claveConsultar, query);
	}	

public DatosSalidaPaginador<T> paginaPromociones(DatosEntradaPaginador<T> params, Long subdel) {
	return daoDelta.paginaPromociones(params, subdel);
}
	
public DatosSalidaPaginador<T> paginaCorrecciones(DatosEntradaPaginador<T> params, Long subdel) {
	return daoDelta.paginaCorrrecciones(params, subdel);
}

public DatosSalidaPaginador<T> paginaAllPromociones(DatosEntradaPaginador<T> params, String subdel, String del) {
	return daoDelta.paginaAllPromociones(params, subdel, del);
}

@Override
public DatosSalidaPaginador<T> paginaTipo(DatosEntradaPaginador<T> params) {
	return daoDelta.paginaTipo(params);
}

@Override
public DatosSalidaPaginador<T> paginaOrigen(DatosEntradaPaginador<T> params) {
	return daoDelta.paginaOrigen(params);
}

@Override
public DatosSalidaPaginador<T> paginaTipoOrigen(DatosEntradaPaginador<T> params) {
	return daoDelta.paginaTipoOrigen(params);
}

@Override
public DatosSalidaPaginador<T> paginaCriterioSeleccion(DatosEntradaPaginador<T> params) {
	return daoDelta.paginaCriterioSeleccion(params);
}

@Override
public List<T> consultaLibrePorClavePag(Long claveConsultar, String query,
		int inicio, int tamPag) {
	return daoDelta.consultaLibrePorClavePagina(claveConsultar, inicio, tamPag, query);
}
	
	
}
