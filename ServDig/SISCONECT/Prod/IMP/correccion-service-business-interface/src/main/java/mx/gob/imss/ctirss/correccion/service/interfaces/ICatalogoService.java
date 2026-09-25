package mx.gob.imss.ctirss.correccion.service.interfaces;

import java.util.List;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;




/**
 * @author Lucio Duran Silva
 * 
 *
 */
public interface ICatalogoService<T extends AbstractModel>  {

	/**
	 * Matodo para agregar un elemento al catalogo.
	 * @param model
	 * @return
	 */
	public T agregar(T model);
	
	/**
	 * Matodo para modificar un elemento del catalogo
	 * @param model
	 * @return
	 */
	public T actualizar(T model);
	
	
	/**
	 * Matodo para eliminar un elemento del catalogo
	 * @param model
	 */
	public void eliminar(T model);
	
	
	/**
	 * Matodo para realizar consultas de los elementos del catalgo
	 * @param filtro
	 * @return
	 */
	public List<T> consultar(T filtro);
	
	/**
	 * Matodo para realizar consulta por clave de un elemento del catalogo
	 * @param filtro
	 * @return
	 */
	public T consultaPorClave(T filtro);			
		
	/**
	 * Metodo para realizar la consulta de los datos 
	 * @param params
	 * @return
	 */
	public DatosSalidaPaginador<T> pagina(DatosEntradaPaginador<T> params);

	/**
	 * Metodo para realizar la consulta de los datos 
	 * @param params
	 * @return
	 */
	public DatosSalidaPaginador<T> paginaDiv(DatosEntradaPaginador<T> params);
	
	public DatosSalidaPaginador<T> paginaAnexoPagos(DatosEntradaPaginador<T> params);
	
	public DatosSalidaPaginador<T> paginaAnexoPagosA(DatosEntradaPaginador<T> params);
	
	public DatosSalidaPaginador<T> paginaAnexoPagosR(DatosEntradaPaginador<T> params);

	public DatosSalidaPaginador<T> paginaAnexoPatrones(DatosEntradaPaginador<T> params) ;
	
	public DatosSalidaPaginador<T> paginaAnexoConceptos(DatosEntradaPaginador<T> params) ;
	
	public DatosSalidaPaginador<T> paginaAnexoConceptosC(DatosEntradaPaginador<T> params) ;
	
	public DatosSalidaPaginador<T> paginaAnexoConceptosPAI(DatosEntradaPaginador<T> params) ;
	
	public DatosSalidaPaginador<T> paginaAnexoConceptosAPAI(DatosEntradaPaginador<T> params) ;
	
	public DatosSalidaPaginador<T> paginaPromociones(DatosEntradaPaginador<T> params, Long subdel);

	public DatosSalidaPaginador<T> paginaCorrecciones(DatosEntradaPaginador<T> params, Long subdel);
	
	public DatosSalidaPaginador<T> paginaAllPromociones(DatosEntradaPaginador<T> params, String subdel, String del) ;
	
	public DatosSalidaPaginador<T> paginaTipo(DatosEntradaPaginador<T> params);
	
	public DatosSalidaPaginador<T> paginaOrigen(DatosEntradaPaginador<T> params);
	
	public DatosSalidaPaginador<T> paginaTipoOrigen(DatosEntradaPaginador<T> params);
	
	public DatosSalidaPaginador<T> paginaCriterioSeleccion(DatosEntradaPaginador<T> params);
	/**
	 * Matodo para realizar una consulta de los grupos en base a la divisian
	 * @param CveIdDivision clave de la divisian a consultar
	 * @return
	 */
	public List<T> consultaDicGrupoXDicDivision(Long CveIdDivision);	
	
	
	public List<T> consultaLibrePorClave(Long claveConsultar, String query) ;
	
	public List<T> consultaLibrePorClavePag(Long claveConsultar, String query,int inicio,int tamPag) ;
	
	public List<T> consultaSQL(String query) ;
	
}

