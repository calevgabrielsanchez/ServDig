package mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.CorreoElectronicoRest;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.SelloDigitalRest;

@Remote
public interface IServiciosDigitalesUtilServiceRemote {
	
	
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
	

	/**
	 * Metodo que devuelve un listo con los dias inhabiles del año encuros
	 * @return
	 * @throws ServiciosRestException
	 */
	 List<Date> getDiasInhabilesPorAnio(Long numAnio)throws ServiciosRestException;
}
