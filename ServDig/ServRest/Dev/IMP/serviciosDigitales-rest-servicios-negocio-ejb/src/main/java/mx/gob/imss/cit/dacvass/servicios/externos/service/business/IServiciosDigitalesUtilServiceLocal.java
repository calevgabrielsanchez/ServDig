package mx.gob.imss.cit.dacvass.servicios.externos.service.business;

import javax.ejb.Local;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.CorreoElectronicoRest;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.SelloDigitalRest;


@Local
public interface IServiciosDigitalesUtilServiceLocal {
	
	/**
	 * Metodo encargado de enviar correco electronico
	 * @param correoDto
	 * @throws Exception
	 */
	void enviarCorreo(CorreoElectronicoRest correoRest) throws ServiciosRestException;
	
	
	/**
	 * Servivio que obtiene el sello Digital a traves de la cadena origina
	 * @param cadenaOriginal String
	 * @return String
	 */
	SelloDigitalRest getSelloDigital(SelloDigitalRest selloRest) throws ServiciosRestException;

}
