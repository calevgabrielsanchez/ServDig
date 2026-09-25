package mx.gob.imss.ctirss.correccion.web.desgargas.cedulascorreccion;

/**
 * Interface utilizada en todos los objetos que generan
 * cedulas de la construccion.
 * 
 * @author Marco Antonio Nieto Plett
 *
 */
public interface ICedulasCorreccion {
	
	/**
	 * Permite ingresar los datos principales del encabezado al XLS
	 */
	public void cargaEncabezado();
	
	/**
	 * Permite ingresar los registro patronales asociados 
	 * al folio de la correccion al XLS
	 */
	public void cargaRegistrosPatronales();
	
	/**
	 * Permite ingresar los elementos de seguridad dentro del XLS.
	 * Se utiliza el idFolioCorreccion y idCedulaContruccion
	 */
	public void cargaSeguridadArchivo();
	

}
