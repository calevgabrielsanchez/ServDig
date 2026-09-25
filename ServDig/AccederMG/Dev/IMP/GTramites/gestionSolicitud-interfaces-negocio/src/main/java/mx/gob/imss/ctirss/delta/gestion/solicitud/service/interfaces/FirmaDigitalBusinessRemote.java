package mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces;

import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.firma.ErrorEnInvocacionRecursoRemotoException;
import mx.gob.imss.ctirss.delta.exception.firma.FirmaDigitalException;
import mx.gob.imss.ctirss.delta.exception.firma.RecursoRemotoNoDisponibleException;
import mx.gob.imss.ctirss.delta.exception.firma.RegistroPatronalInvalidoEnCertificadoException;
import mx.gob.imss.ctirss.delta.model.firma.Archivo;
import mx.gob.imss.ctirss.delta.model.firma.RespuestaFirmadoSimple;
import mx.gob.imss.ctirss.delta.model.firma.RespuestaFirmadoXmlSimple;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Remote
public interface FirmaDigitalBusinessRemote {
	
	/**
	 * Servicio que procesa una firma electrónica con FIEL, se encarga de
	 * orquestar los servicios de validación y guardado en notaria
	 * 
	 * @param firmaElectronica
	 * @return
	 * @throws FirmaDigitalException
	 */
	FirmaElectronica procesarFirmaConFIEL(FirmaElectronica firmaElectronica)
			throws FirmaDigitalException;

	/**
	 * Servicio que procesa una firma electrónica con certificado IMSS, se
	 * encarga de orquestar los servicios de validación y guardado en notaria
	 * 
	 * @param firmaElectronica
	 * @return
	 * @throws RegistroPatronalInvalidoEnCertificadoException
	 * @throws RecursoRemotoNoDisponibleException
	 * @throws ErrorEnInvocacionRecursoRemotoException
	 * @throws FirmaDigitalException
	 */
	FirmaElectronica procesarFirmaConIMSS(FirmaElectronica firmaElectronica)
			throws RegistroPatronalInvalidoEnCertificadoException,
			RecursoRemotoNoDisponibleException,
			ErrorEnInvocacionRecursoRemotoException, FirmaDigitalException;

	/**
	 * Servicio que valida un certificado FIEL
	 * 
	 * @param firmaElectronica
	 * @return
	 * @throws FirmaDigitalException
	 */
	FirmaElectronica validarCertificadoFIEL(FirmaElectronica firmaElectronica)
			throws FirmaDigitalException;

	void insertarSolicitudFirmaDigital(Solicitud solicitud, FirmaElectronica firma);
	
	/**
	 * Metodo para obtener la firma electronica de una solicitud
	 * @param solicitud
	 * @return
	 */
	FirmaElectronica getFirmaElectronica(Solicitud solicitud);
	
	/**
	 * Servivio que obtiene el sello Digital a traves de la cadena original 
	 * @param cadenaOriginal String
	 * @return String
	 */
	RespuestaFirmadoSimple getSelloDigital(String cadenaOriginal,String secuenciaNotaria, String rfc);
	
	FirmaElectronica convertirRespuestaFirmadoSimple(String cadenaOriginal,RespuestaFirmadoSimple firmado);
	
	/**
	 * Servicio que obtiene la cadena original a partir de la solicitud
	 * @param solicitud
	 * @return
	 */
	String generarCadenaOriginal(Solicitud solicitud, Persona persona, String rp, String nss); 
	
	/**
	 * Servicio que obtiene la cadena original y el sello digital
	 * los parametros seran devueltos en un mapa con las llaves cadenaOriginal y selloDigital
	 * @param solicitud
	 * @return
	 */
	Map<String,String> getCadenaOriginalYSelloDigital(Solicitud solicitud, Persona persona, String rp, String nss);
	
	/**
	 * Metodo para guardar archivo firmado pasando el id del tramite que es guardado como sello notarial
	 * @param selloNotaria
	 * @param archivo
	 */
	void guardarArchivoFirmado(String secuenciaNotaria, String nombreArchivo,byte[] archivo);
	
	/**
	 * 
	 * @param archivo
	 */
	void guardarArchivoFirmado(String secuenciaNotaria, Archivo archivo);
	
	/**
	 * 
	 * @param selloNotaria
	 * @param archivoBase64
	 */
	void guardarArchivoFirmado(String secuenciaNotaria, String nombreArchivo,String archivoBase64);

	/**
	 * Servicio para firmar un XML
	 */
	RespuestaFirmadoXmlSimple firmarXML(String xml, String nombreArchivo);
}
