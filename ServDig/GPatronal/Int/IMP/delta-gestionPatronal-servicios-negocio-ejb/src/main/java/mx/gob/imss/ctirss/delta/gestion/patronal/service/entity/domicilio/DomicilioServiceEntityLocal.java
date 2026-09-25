/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:DomicilioServiceEntity.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.domicilio
 *  @Fecha:02/03/2012
 */
package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.domicilio;

import javax.ejb.Local;


/**
 * @author Lucio Duran Silva
 *
 */
@Local
public interface DomicilioServiceEntityLocal {

	
//	/**
//	 * Consulta por codigo postal la localida, municipio y entidad federativa
//	 * @param geocoder
//	 * @return
//	 * @throws DomicilioNoLocalizadoException
//	 */
//	public List<GeocoderDomicilio> consultaLocalidadAproximada(GeocoderDomicilio geocoder) throws DomicilioNoLocalizadoException;
	
	void hello();
}
