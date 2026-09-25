package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.firmaDigital;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.firma.FirmaDigitalException;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;

@Local
public interface ServiciosExternosFirmaDigitalFIELBusinessLocal {

	/**
	 * Valida el certificado FIEL (vigencia, revocación, etc)
	 * 
	 * @param firmaElectronica con el PKCS a validar
	 * @return objeto con el resultado de la validación
	 * @throws FirmaDigitalException
	 */
	FirmaElectronica validarCertificado(FirmaElectronica firmaElectronica)
			throws FirmaDigitalException;

	/**
	 * Guarda en notaria la firma digital
	 * 
	 * @param firmaElectronica
	 * @return
	 * @throws FirmaDigitalException
	 */
	FirmaElectronica guardarNotaria(FirmaElectronica firmaElectronica)
			throws FirmaDigitalException;

}
