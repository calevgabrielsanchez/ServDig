/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Leticia Torres
 *  @Proyecto: delta
 *  @Archivo: ConfiguracionCeBusinessRemote.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis
 *  @Fecha: 18/10/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.clasificacion.ConfiguracionCe;

@Remote
public interface ConfiguracionCeBusinessRemote {

	/**
	 * Configura las operaciones que pueden realizarse en el análisis, por
	 * estatus del análisis y tipo de rol
	 * 
	 * @param cveIdAnalisis
	 * @param cveIdUsuario
	 * @param cveIdRol
	 * @return configuracionCe
	 */
	ConfiguracionCe configurarOperaciones(final long cveIdAnalisis,
			final String cveIdUsuario, final int cveIdRol);

}
