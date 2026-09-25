package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.firmaDigital;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.firma.FirmaDigitalException;
import mx.gob.imss.ctirss.delta.exception.firma.RegistroPatronalInvalidoEnCertificadoException;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;

@Local
public interface ServiciosExternosFirmaDigitalIMSSBusinessLocal {

	/**
	 * Valida que el registro patronal recibido sea igual al del certificado
	 * 
	 * @param firmaElectronica
	 * @throws RegistroPatronalInvalidoEnCertificadoException
	 */
	void validarRegistroPatronalEnCertificado(FirmaElectronica firmaElectronica)
			throws RegistroPatronalInvalidoEnCertificadoException;
	
	/**
	 * Valida invocando al WebService, que lo valida mediante un PKCS7 como parámetro,
	 * si la respuesta del Web Service es afirmativa, el certificado es válido y
	 * en la respuesta se encontrará en una cadena el serial del certificado, si
	 * la respuesta es negativa retorna un código de error y el mensaje que
	 * describe al error sucedido
	 * 
	 * @param firmaElectronica firma que se desea validar
	 * @return firmaElectronica con el resultado de la validacion
	 * @throws InvocarWSSeguridataException
	 */
	FirmaElectronica validarCertificado(FirmaElectronica firmaElectronica)
			throws FirmaDigitalException;

	/**
	 * Guarda en notaria, recibe la firma digital 
	 * retorna como respuesta recibo, recibo notarial y documento
	 * original de que se efectuó esa transacción en la notaría, si no retorna
	 * un código y mensaje de error.
	 * 
	 * @param firmaElectronica con el PKCS7 setteado
	 * @return firmaElectronica con el resultado del proceso
	 * @throws FirmaDigitalException
	 */
	FirmaElectronica guardarEnNotaria(FirmaElectronica firmaElectronica)
			throws FirmaDigitalException;

	

}
