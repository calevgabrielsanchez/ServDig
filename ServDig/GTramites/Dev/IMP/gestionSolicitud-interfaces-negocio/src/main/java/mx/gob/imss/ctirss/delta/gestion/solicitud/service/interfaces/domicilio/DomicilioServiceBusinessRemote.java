/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:DomicilioServiceBusiness.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.domicilio
 *  @Fecha:02/03/2012
 */
package mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.domicilio;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.domicilio.AsentamientoNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;

/**
 * @author Lucio Duran Silva
 *
 */
@Remote
public interface DomicilioServiceBusinessRemote {

	
	
	
	
	
	/**
	 * Consulta los asentamientos que correspondan al codigo postal requerido.
	 * @param codigo
	 * @return
	 * @throws DomicilioNoLocalizadoException
	 */
	List<Asentamiento> getAsentamientoPorCodigoPosta(CodigoPostal codigo) throws DomicilioNoLocalizadoException;
	
	
	/**
	 * Consulta los asentamientos que correspondan al municipio requerido.
	 * @param municipio
	 * @return
	 * @throws DomicilioNoLocalizadoException
	 */
	List<Asentamiento> getAsentamientoPorMunicipio(Municipio municipio) throws DomicilioNoLocalizadoException;
	
	
	/**
	 * Obtiene el detalle del asentamiento 
	 * @param asentamiento
	 * @return
	 * @throws DomicilioNoLocalizadoException
	 * @throws AsentamientoNoLocalizadoException 
	 */
	Asentamiento getAsentamiento(Asentamiento asentamiento ) throws DomicilioNoLocalizadoException, AsentamientoNoLocalizadoException;
	
	
	/**
	 * 
	 * @param domicilio
	 * @return
	 * @throws DomicilioNoValidoException
	 */
	Domicilio registrarDomicilio(Domicilio domicilio) throws DomicilioNoValidoException;
	
	
	/**
	 * 
	 * @param domicilio
	 * @return
	 * @throws DomicilioNoLocalizadoException
	 */
	Domicilio consultarDomicilio(Domicilio domicilio) throws DomicilioNoLocalizadoException;
	
	
	
}
