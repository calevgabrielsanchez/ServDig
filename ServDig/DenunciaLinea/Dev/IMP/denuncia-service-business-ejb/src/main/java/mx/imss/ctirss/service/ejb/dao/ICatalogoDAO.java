package mx.imss.ctirss.service.ejb.dao;

import java.sql.Connection;
import java.util.List;

import mx.imss.ctirss.base.paginador.model.DatosEntradaPaginador;
import mx.imss.ctirss.base.paginador.model.DatosSalidaPaginador;
import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.model.DltUsuarioden;


/**
 * @author Juan Manuel Lopez Lozano
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 28/08/2011
 */
public interface ICatalogoDAO<T extends AbstractModel> {

	/**
	 * Metodo para agregar un elemento al catalogo.
	 * @param model
	 * @return
	 */
	public T agrega(T model);
	
	
	
	/**
	 * Metodo para modificar un elemento del catalogo
	 * @param model
	 * @return
	 */
	public T actualiza(T model);
	
	
	/**
	 * Metodo para eliminar un elemento del catalogo
	 * @param model
	 */
	public void elimina(T model);
	
	
	/**
	 * Metodo para realizar consultas de los elementos del catalgo
	 * @param filtro
	 * @return
	 */
	public List<T> consulta(T filtro);	
	
	/**
	 * Metodo para realizar consulta por clave
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
	
	public DatosSalidaPaginador<T> paginaAnexoPatrones(DatosEntradaPaginador<T> params);
	
	public DatosSalidaPaginador<T> paginaPromociones(DatosEntradaPaginador<T> params, Long subdel) ;

	public DatosSalidaPaginador<T> paginaCorrrecciones(DatosEntradaPaginador<T> params, Long subdel) ;
	
	public DatosSalidaPaginador<T> paginaAnexoConceptos(DatosEntradaPaginador<T> params) ;
	
	public DatosSalidaPaginador<T> paginaAnexoConceptosC(DatosEntradaPaginador<T> params) ;
	
	public DatosSalidaPaginador<T> paginaAllPromociones(DatosEntradaPaginador<T> params, String subdel, String del) ;
	
	public DatosSalidaPaginador<T> paginaTipo(DatosEntradaPaginador<T> params);
	
	public DatosSalidaPaginador<T> paginaOrigen(DatosEntradaPaginador<T> params);
	
	public DatosSalidaPaginador<T> paginaTipoOrigen(DatosEntradaPaginador<T> params);
	
	public DatosSalidaPaginador<T> paginaCriterioSeleccion(DatosEntradaPaginador<T> params);
	/**
	 * Metodo para realizar una consulta libre por clave
	 * @param filtro
	 * @return
	 */
	public List<T> consultaLibrePorClave(Long claveConsultar, String query);	
	
	public byte[] consultaLibrePorClaveObjeto(Long claveConsultar, String query) ;
	
	/**
	 * Permite consultar con SQL ANSI
	 * @param query
	 * @return
	 */
	public List<T> consultaSQL(String query) ;
	public DatosSalidaPaginador<T> paginaPatronesDenunciados(DatosEntradaPaginador<T> params);
	public DatosSalidaPaginador<T> paginaDenuncias(DatosEntradaPaginador<T> params, DltUsuarioden user);
	
}
