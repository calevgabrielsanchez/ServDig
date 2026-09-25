package mx.gob.imss.ctirss.delta.cobranza.service.utility;

import java.awt.image.BufferedImage;
import java.util.Date;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.cobranza.exception.EstadoAdeudoException;
import mx.gob.imss.ctirss.delta.cobranza.modelo.DatosValidacionCartaNoAdeudoWrapper;
import mx.gob.imss.ctirss.delta.model.firma.RespuestaFirmadoSimple;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Local
public interface CartaNoAdeudoServiceUtilityLocal {

	Solicitud prepararSolicitudCartaNoAdeudo(Persona persona, String usuario,
			DatosValidacionCartaNoAdeudoWrapper wrapper, Date fechaInicio, Long idOrigen);

	String generarCadenaOriginal(Solicitud solicitud, Persona persona, String usuario);

	String generarCadenaOriginalReconstruida(Solicitud solicitud, Persona persona, String usuario);

	FirmaElectronica generarFirmaElectronica(
			RespuestaFirmadoSimple selloDigital, String cadenaOriginal);

	byte[] prepararTemplateCartaNoAdeudo(Persona persona, Solicitud solicitud,
			FirmaElectronica firmaElectronica, BufferedImage imagenCodeQR,
			DatosValidacionCartaNoAdeudoWrapper wrapper,
			String usuario, Boolean juicioEnProceso, Boolean auditoriaEnProceso
			, Boolean convenioEnProceso)
			throws EstadoAdeudoException;

	byte[] reconstruirTemplateCartaNoAdeudo(Persona persona, Solicitud solicitud,
										 FirmaElectronica firmaElectronica, BufferedImage imagenCodeQR,
										 DatosValidacionCartaNoAdeudoWrapper wrapper,
										 String usuario, Boolean juicioEnProceso, Boolean auditoriaEnProceso
			, Boolean convenioEnProceso)
			throws EstadoAdeudoException;

	Date obtenerFechaFinVigencia(Date fechaSolicitud, int diasIncremento);

}
