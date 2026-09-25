/**
 * ICatalogoService.java
 * mx.gob.imss.delta.service.interfaces
 * service-business-interface
 */
package mx.imss.ctirss.service.interfaces;

import java.sql.Connection;
import java.util.List;

import mx.imss.ctirss.base.paginador.model.DatosEntradaPaginador;
import mx.imss.ctirss.base.paginador.model.DatosSalidaPaginador;
import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.model.DltUsuarioden;




/**
 * @author Lucio Duran Silva
 * 
 *
 */
public interface ICatalogoService<T extends AbstractModel>  {

	/**
	 * M�todo para agregar un elemento al catalogo.
	 * @param model
	 * @return
	 */
	public T agregar(T model);
	

	/**
	 * M�todo para modificar un elemento del catalogo
	 * @param model
	 * @return
	 */
	public T actualizar(T model);
	
	
	/**
	 * M�todo para eliminar un elemento del catalogo
	 * @param model
	 */
	public void eliminar(T model);
	
	
	/**
	 * M�todo para realizar consultas de los elementos del catalgo
	 * @param filtro
	 * @return
	 */
	public List<T> consultar(T filtro);
	
	/**
	 * M�todo para realizar consulta por clave de un elemento del catalogo
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
	
	public DatosSalidaPaginador<T> paginaPromociones(DatosEntradaPaginador<T> params, Long subdel);

	public DatosSalidaPaginador<T> paginaCorrecciones(DatosEntradaPaginador<T> params, Long subdel);
	
	public DatosSalidaPaginador<T> paginaAllPromociones(DatosEntradaPaginador<T> params, String subdel, String del) ;
	
	public DatosSalidaPaginador<T> paginaTipo(DatosEntradaPaginador<T> params);
	
	public DatosSalidaPaginador<T> paginaOrigen(DatosEntradaPaginador<T> params);
	
	public DatosSalidaPaginador<T> paginaTipoOrigen(DatosEntradaPaginador<T> params);
	
	public DatosSalidaPaginador<T> paginaCriterioSeleccion(DatosEntradaPaginador<T> params);
	/**
	 * M�todo para realizar una consulta de los grupos en base a la divisi�n
	 * @param CveIdDivision clave de la divisi�n a consultar
	 * @return
	 */
	public List<T> consultaDicGrupoXDicDivision(Long CveIdDivision);	
	
	
	public List<T> consultaLibrePorClave(Long claveConsultar, String query) ;
	
	public byte[] consultaLibrePorClaveObjeto(Long claveConsultar, String query) ;
	
	public List<T> consultaSQL(String query) ;
	
	public DatosSalidaPaginador<T> paginaPatronesDenunciados(DatosEntradaPaginador<T> params);
	public DatosSalidaPaginador<T> paginaDenuncias(DatosEntradaPaginador<T> params, DltUsuarioden user);
	
}
