/**
 * 
 */
package mx.gob.imss.ctirss.correccion.folio.service.interfaces;

import java.util.Date;

import mx.gob.imss.ctirss.correccion.framework.utils.TipoCorreccion;

/**
 * @author vaguirre
 * 
 */
public interface FoliadorService {

	/**
	 * Regresar el el folio siguiente para su uso en un nuevo documento.
	 * 
	 * @param cveDel
	 * @param cveSubDel
	 * @param anio
	 *            A–o del folio
	 * @param tipoDocumento
	 * @return
	 */
	String recuperarSiguienteFolio(Long cveDel, Long cveSubDel, Integer anio,
			TipoCorreccion tipoDocumento);

	/**
	 * Regresar el el folio siguiente para su uso en un nuevo documento.
	 * 
	 * @param cveDel
	 * @param cveSubDel
	 * @param fechaAnio
	 *            Fecha usada para sacar el a–o del folio
	 * @param tipoDocumento
	 * @return
	 */
	String recuperarSiguienteFolio(Long cveDel, Long cveSubDel, Date fechaAnio,
			TipoCorreccion tipoDocumento);

	/**
	 * Regresar el el folio siguiente para su uso en un nuevo documento.
	 * 
	 * @param cveDel
	 * @param cveSubDel
	 * @param anio
	 *            A–o del folio
	 * @param tipoDocumento
	 * @return
	 */
	String recuperarSiguienteFolio(String cveDel, String cveSubDel,
			Integer anio, TipoCorreccion tipoDocumento);
	
	
	/**
	 * Regresar el el folio siguiente para su uso en un nuevo documento.
	 * 
	 * @param cveDel
	 * @param cveSubDel
	 * @param fechaAnio
	 *            Fecha usada para sacar el a–o del folio
	 * @param tipoDocumento
	 * @return
	 */
	String recuperarSiguienteFolio(String cveDel, String cveSubDel, Date fechaAnio,
			TipoCorreccion tipoDocumento);

	
	String recuperarFolioSiguienteSinActualizar(Long cveDel, Long cveSubDel,
			Integer anio, TipoCorreccion tipoDocumento);
	
}
