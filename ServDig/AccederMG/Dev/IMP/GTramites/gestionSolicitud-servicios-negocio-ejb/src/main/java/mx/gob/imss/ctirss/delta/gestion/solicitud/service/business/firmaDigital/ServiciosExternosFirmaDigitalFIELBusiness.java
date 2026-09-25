package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.firmaDigital;

import java.net.URL;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.firma.FirmaDigitalException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.idse.pkcs7.webservices.IWSFirmaDigital;
import mx.gob.imss.idse.pkcs7.webservices.Pkcs7Bean;
import mx.gob.imss.idse.pkcs7.webservices.Pkcs7BeanTipoCertificado;
import mx.gob.imss.idse.pkcs7.webservices.Pkcs7BeanTipoNotaria;
import mx.gob.imss.idse.pkcs7.webservices.RespuestaAuthenticateBean;
import mx.gob.imss.idse.pkcs7.webservices.WSFirmaDigitalLocator;

import org.apache.axis.client.Stub;

@Stateless(mappedName = "serviciosExternosFirmaDigitalFIELBusiness")
public class ServiciosExternosFirmaDigitalFIELBusiness extends
		AbstractServiceUtility implements
		ServiciosExternosFirmaDigitalFIELBusinessLocal {

	@Override
	public FirmaElectronica validarCertificado(FirmaElectronica firmaElectronica)
			throws FirmaDigitalException {

		log.info("Se va a validar el certificado FIEL");
		
		String resultado = null;
		
		// Se llenan los datos de entrada para la consulta al WS
		Pkcs7Bean pk7Remoto = new Pkcs7Bean();
		Pkcs7BeanTipoCertificado tipoCertificado = Pkcs7BeanTipoCertificado.fromValue(firmaElectronica.getTipoCertificado());

		// TODO: CHECAR QUÉ SE DEBE PONER AQUÍ EN EL TIPO DE NOTARIA
		Pkcs7BeanTipoNotaria tipoNotaria = Pkcs7BeanTipoNotaria.fromValue("ALTA_PATRONAL");
		
		pk7Remoto.setPkcs7(firmaElectronica.getsPKCS7().trim());
		pk7Remoto.setTipoCertificado(tipoCertificado);
		pk7Remoto.setTipoNotaria(tipoNotaria);

		log.info("PKCS a validar -> " + pk7Remoto.getPkcs7());
		
		RespuestaAuthenticateBean remoteResponse = null;
		// Se realiza la validacion del certificado
		remoteResponse = enviaInfoWS(pk7Remoto);
		
		log.info("C\u00f3digo de respuesta -> " + remoteResponse.getCodRespuesta());
		log.info("Mensaje de respuesta -> " + remoteResponse.getMsgRespuesta());

		firmaElectronica.setCodRespuesta(Integer.toString(remoteResponse.getCodRespuesta()));
		firmaElectronica.setMsgRespuesta(remoteResponse.getMsgRespuesta());

		if (firmaElectronica.getCodRespuesta().equals(Integer.toString(ConstantesFirmaDigital.CODIGO_RESPUESTA_VALIDO))) {

			/*
			 * Se valida que el RFC devuelto por el WS sea el mismo que el RFC
			 * que viene como parámetro
			 *
			if (StringUtils.isNotEmpty(firmaElectronica.getRfc()) && 
					!remoteResponse.getDatosFielBean().getRfc().equalsIgnoreCase(firmaElectronica.getRfc())) {
				log.info("RFC de la firma distinto al del certificado");
				resultado = ConstantesFirmaDigital.RFC_DIFERENTE_PATRON;
				throw new FirmaDigitalException(resultado);
			} */
			
			log.info("Certificado v\u00e1lido");
			
			firmaElectronica.setRfc(remoteResponse.getDatosFielBean().getRfc());
			firmaElectronica.setCurp(remoteResponse.getDatosFielBean().getCurp());
			firmaElectronica.setCadenaOriginal(remoteResponse.getCadenaOriginal());
			firmaElectronica.setIniciaVigenciaCertificado(remoteResponse.getDatosFielBean().getIniciaVigencia());
			firmaElectronica.setFinVigenciaCertificado(remoteResponse.getDatosFielBean().getFinVigencia());
			firmaElectronica.setNombreCompleto(remoteResponse.getDatosFielBean().getNombreCompleto());
			firmaElectronica.setSerialCertificado(remoteResponse.getDatosFielBean().getSerial());

			// TODO: MASE - CHECAR SI ESTA SECUENCIA ES DE LA NOTARIA
			firmaElectronica.setSecuenciaNotaria(Long.toString(remoteResponse.getSecuencia()));
			
			log.debug("Datos del certificado FIEL:");
			log.debug("RFC -> " + firmaElectronica.getRfc());
			log.debug("Curp -> " + firmaElectronica.getCurp());
			log.debug("Cadena original -> " +  firmaElectronica.getCadenaOriginal());
			log.debug("Inicio de vigencia -> " + firmaElectronica.getIniciaVigenciaCertificado());
			log.debug("Fin de vigencia -> " + firmaElectronica.getFinVigenciaCertificado());
			log.debug("Nombre completo -> " + firmaElectronica.getNombreCompleto());
			log.debug("Serial certificado: " + firmaElectronica.getSerialCertificado());
		} else if (firmaElectronica.getCodRespuesta().equals(Integer.toString(ConstantesFirmaDigital.NO_CERTIFICADO_SAT))) {
			log.info("Certificado no v\u00e1lido");
			resultado = firmaElectronica.getMsgRespuesta() != null ? 
					firmaElectronica.getMsgRespuesta().toUpperCase() : ConstantesFirmaDigital.CERTIFICADO_INVALIDO;
			throw new FirmaDigitalException(resultado);
		}else if (firmaElectronica.getCodRespuesta().equals("11")) {
			log.info("Certificado caducado");
			resultado = ConstantesFirmaDigital.CERTIFICADO_CADUCADO;
			throw new FirmaDigitalException(resultado);
		} else if (firmaElectronica.getMsgRespuesta().equals("NADA")) {
			log.info("Servicio no disponible");
			resultado = ConstantesFirmaDigital.SERVICIO_FIEL_NO_DISPONIBLE;
			throw new FirmaDigitalException(resultado);
		} else {
			log.info("Otro c\u00f3digo de error -> " + firmaElectronica.getMsgRespuesta());
			resultado = ConstantesFirmaDigital.CERTIFICADO_INVALIDO;
			throw new FirmaDigitalException(resultado);
		} 

		log.info("Se termina la validaci\u00f3n del certificado");

		return firmaElectronica;
	}

	@Override
	public FirmaElectronica guardarNotaria(FirmaElectronica firmaElectronica)
			throws FirmaDigitalException {

		// TODO: MASE - Quitar estos datos que son dummy
		firmaElectronica.setReciboNotarial("675348/978UD-978");
		
		return firmaElectronica;
	}

	/**
	 * Realiza el llamado al WS para realizar la validación del certificado
	 * 
	 * @param pk7Remoto
	 * @return
	 * @throws FirmaDigitalException
	 */
	private RespuestaAuthenticateBean enviaInfoWS(Pkcs7Bean pk7Remoto)
			throws FirmaDigitalException {
		RespuestaAuthenticateBean remoteResponse = new RespuestaAuthenticateBean();
		try {
			String sEndPoint = ConstantesFirmaDigital.ENDPOINT_WS_FIRMA_FIEL;
			URL url = new URL(sEndPoint);
			WSFirmaDigitalLocator locator = new WSFirmaDigitalLocator();
			IWSFirmaDigital stub = locator.getWSFirmaDigital(url);
			((Stub) stub).setTimeout(10000);
			remoteResponse = stub.authenticate(pk7Remoto);
		} catch (Exception e) {
			if (e.getMessage().contains(
					"java.net.SocketTimeoutException: Read timed out")) {
				throw new FirmaDigitalException(
						"El tiempo de espera del WS de firma electr\u00F3nica expir\u00F3, intenta m\u00e1s tarde");
			} else {
				throw new FirmaDigitalException(e.getMessage());
			}
		}

		return remoteResponse;
	}
}
