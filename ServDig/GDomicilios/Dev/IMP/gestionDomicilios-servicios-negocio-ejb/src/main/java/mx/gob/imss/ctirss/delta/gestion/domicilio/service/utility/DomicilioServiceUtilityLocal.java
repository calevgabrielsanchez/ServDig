/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.domicilio.service.utility;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioCamino;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioCarretera;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.MunicipioIMSS;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DgAsentamiento;
import mx.gob.imss.ctirss.delta.persistence.DgCatLocalidad;
import mx.gob.imss.ctirss.delta.persistence.DgCodigosPostale;
import mx.gob.imss.ctirss.delta.persistence.DgDomicilioGeografico;
import mx.gob.imss.ctirss.delta.persistence.DgDomiciliosCamino;
import mx.gob.imss.ctirss.delta.persistence.DgDomiciliosCarretera;
import mx.gob.imss.ctirss.delta.persistence.DicDelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicMunicipioImss;
import mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DitDomicilioSat;

/**
 * @author vanderluk
 *
 */
@Local
public interface DomicilioServiceUtilityLocal {

	
	/**
	 * 
	 * @param entity
	 * @return
	 * @throws TransformacionException
	 */
	Asentamiento transformarAsentamiento(DgAsentamiento entity) throws TransformacionException;
	
	
	/**
	 * 
	 * @param entity
	 * @return
	 * @throws TransformacionException
	 */
	CodigoPostal transformarCodigoPostal(DgCodigosPostale entity) throws TransformacionException;
	
	
	/**
	 * Crea un Domicilio a partir del objeto de persistencia.
	 * 
	 * @param entity
	 * @return
	 * @throws TransformacionException
	 */
	Domicilio transformarDomicilio(DgDomicilioGeografico entity) throws TransformacionException;
	
	
	
	/**
	 * Transforma un Domicilio (Modelo) a las clases de persistencia.
	 * @param modelo
	 * @return
	 * @throws TransformacionException
	 */
	DgDomicilioGeografico transformarDomicilio (Domicilio modelo) throws TransformacionException;
	
	/**
	 * Transforma un DomicilioFiscal (Modelo) a DitDomicilioSat (clase de
	 * persistencia).
	 * 
	 * @param modelo
	 * @return
	 * @throws TransformacionException
	 */
	DitDomicilioSat transformarDomicilioFiscal(Domicilio modelo)
			throws TransformacionException;

	/**
	 * Crea un DomicilioFiscal a partir del objeto de persistencia.
	 * 
	 * @param entity
	 * @return
	 * @throws TransformacionException
	 */
	DomicilioFiscal transformarDomicilioFiscal(DitDomicilioSat entity)
			throws TransformacionException;
	
	/**
	 * Metodo que parsea un objeto domicilioGeografico y devulve un string con la direccion
	 * @param entrada
	 * @return
	 * @throws TransformacionException
	 */
	String persisToModelDesDireccion(
			DgDomicilioGeografico entrada) throws TransformacionException;

	/**
	 * Transforma el entity de una delegaci�n al modelo
	 * 
	 * @param entity
	 * @return
	 */
	Delegacion convertirEntityToModelDelegacion(DicDelegacion entity);

	/**
	 * Transforma el entity de una subdelegaci�n al modelo
	 * @param entity
	 * @return
	 */
	Subdelegacion convertirEntityToModelSubdelegacion(DicSubdelegacion entity);

	/**
	 * Transforma un entity de localidad a objeto localidad
	 * @param entity
	 * @return
	 * @throws TransformacionException
	 */
	
	Localidad transformarLocalidad(DgCatLocalidad entity)
			throws TransformacionException;
	
	/**
	 * Metodo que transforma un objeto de persistencia a negocio de DomiciliosCamino
	 * @param entity DgDomiciliosCamino
	 * @return DomicilioCamino
	 */
	DomicilioCamino convertirEntityToModelDomicilioCamino(DgDomiciliosCamino entity);
	
	/**
	 * Metodo que transforma un objeto de persistencia a negocio de DomiciliosCamino
	 * @param entity DgDomiciliosCarretera
	 * @return DomicilioCarretera
	 */
	DomicilioCarretera convertirEntituToModelDomicilioCarretera(DgDomiciliosCarretera entity);
	
	List<MunicipioIMSS> convertirMunicipioIMSS (List<DicMunicipioImss> municipiosImss);
}