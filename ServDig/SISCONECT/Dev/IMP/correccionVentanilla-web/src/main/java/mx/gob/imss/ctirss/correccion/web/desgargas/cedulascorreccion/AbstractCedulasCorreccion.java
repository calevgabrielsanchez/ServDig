package mx.gob.imss.ctirss.correccion.web.desgargas.cedulascorreccion;


import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.common.utils.readerXLS.proces.ReadAndExportXLS;
import mx.gob.imss.xls.loader.vo.Catalog;
//import mx.gob.imss.common.utils.readAndExportXLS.process.ReadAndExportXLS;
//import mx.gob.imss.common.utils.readAndExportXLS.vo.Catalog;


/**
 * 
 * Contiene todos los elementos base de cada una de las cedulas.
 * 
 * @author Marco Antonio Nieto Plett
 *
 */
public abstract class AbstractCedulasCorreccion {
	
	/**
	 * Permite controlar los datos del encabezado del XLS
	 */
	protected Catalog encabezado;
	
	/**
	 * Permite incluir los registros patronales dentro del XLS
	 */
	protected Catalog registrosPatronales;
	
	/**
	 * Permite controlar la seguridad del archivo XLS
	 */
	protected Catalog seguridadArchivo;
	
	protected ReadAndExportXLS reXLS;
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	
	protected Integer idFolioCorreccion;
	protected Integer idCedula;
	
	
	protected String foliCorreccion;
	
	protected String periodoCorreccion;
	
	protected List<?> lsRegistrosPatronales;
	
	protected Catalog nombreArchivo;
	
	/**
	 * Genera el XLS requerido segun la plantilla seleccionada
	 * @param nombreArchivo
	 * @return true si exito false en cualquier otro caso
	 */
	public Boolean generarXLS(String nombreArchivo){
		
		if(reXLS!=null)		
			return reXLS.executePOI(response, request, nombreArchivo);
		else return false;
	}
	
	/**
	 * Genera la estructura requerida para los combos dentro del excel
	 * @param label
	 * @param id
	 * @return
	 */
	protected String formaCatalogoLabelId(String label,String id){
		
		return label+"["+id+"]";
		
	}
	
	/**
	 * Verifica si el request es valido
	 * @param request
	 * @return
	 * @throws Exception en caso de que sea NULL
	 */
	protected HttpServletRequest validaRequest(HttpServletRequest request) throws Exception{
		if(request==null) throw new Exception("El request ingresado no es valido: Request NULL");
		return request;
	}
	
	/**
	 * Verifica si el response es valido
	 * @param response
	 * @return
	 * @throws Exception en caso de que sea NULL
	 */
	protected HttpServletResponse validaResponse(HttpServletResponse response) throws Exception{
		if(response==null) throw new Exception("El response ingresado no es valido: Response NULL");
		return response;
	}
	
	/**
	 * Verifica si el Folio de la correccion es valido
	 * @param idFolioCorreccion
	 * @return
	 * @throws Exception en caso de que sea NULL o menor igual a cero
	 */
	protected Integer validaIdFolio(Integer idFolioCorreccion) throws Exception{
		if(idFolioCorreccion==null || idFolioCorreccion.intValue()<=0) throw new Exception("El folio de correccion ingresado no es valido");
		return idFolioCorreccion;
	}
	
	/**
	 * Verifica si la cedula seleccionada es valida
	 * @param idCedula
	 * @return
	 * @throws Exception en caso de que sea NULL o menor igual a cero
	 */
	protected Integer validaIdCedula(Integer idCedula) throws Exception{
		if(idCedula==null || idCedula.intValue()<=0) throw new Exception("La cedula de correccion ingresada no es valida");
		return idCedula;
	}
	/**
	 * Obtiene la ruta base del archivo agregando el seperados correcto de acuerdo al SO.
	 * <br> Hace uso de <code>request.getSession().getServletContext().getRealPath(File.separator)</code>
	 * @return
	 */
	protected String getRealBasePath(){
		final String prePath = request.getSession().getServletContext().getRealPath(File.separator).endsWith(File.separator) 
				? request.getSession().getServletContext().getRealPath(File.separator) 
				: request.getSession().getServletContext().getRealPath(File.separator)+File.separator;
		return prePath;
	}

	public String getFoliCorreccion() {
		return foliCorreccion;
	}

	public void setFoliCorreccion(String foliCorreccion) {
		this.foliCorreccion = foliCorreccion;
	}

	public String getPeriodoCorreccion() {
		return periodoCorreccion;
	}

	public void setPeriodoCorreccion(String periodoCorreccion) {
		this.periodoCorreccion = periodoCorreccion;
	}
	
	
	
}
