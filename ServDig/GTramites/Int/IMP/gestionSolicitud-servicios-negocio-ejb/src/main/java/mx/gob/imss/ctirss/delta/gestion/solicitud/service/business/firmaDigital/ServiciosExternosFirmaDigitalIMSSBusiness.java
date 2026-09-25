package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.firmaDigital;

import java.io.IOException;
import java.security.cert.X509Certificate;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.firma.FirmaDigitalException;
import mx.gob.imss.ctirss.delta.exception.firma.RegistroPatronalInvalidoEnCertificadoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.interoper._NotariaResponse;
import mx.gob.imss.interoper._ValidaCertificadoResponse;
import sun.security.pkcs.ParsingException;
import weblogic.utils.encoders.BASE64Decoder;

@Stateless(mappedName = "serviciosExternosFirmaDigitalIMSSBusiness")
public class ServiciosExternosFirmaDigitalIMSSBusiness extends
		AbstractServiceUtility implements
		ServiciosExternosFirmaDigitalIMSSBusinessLocal {

	@Override
	public void validarRegistroPatronalEnCertificado(
			FirmaElectronica firmaElectronica)
			throws RegistroPatronalInvalidoEnCertificadoException {
		
		X509Certificate certificado = null;
		String registroPatronalCertificado = new String();
		
		ServiciosExternosFirmaDigitalIMSSBusiness servicios = new ServiciosExternosFirmaDigitalIMSSBusiness();
		
		certificado = servicios.getObtenerCertificadoPorPkcs7(firmaElectronica.getsPKCS7());
		registroPatronalCertificado = servicios.obtenerRegistroPatronalDelCertificado(certificado);

		log.debug("##### REGISTRO DEL CERTIFICADO ES: " + registroPatronalCertificado );
		log.debug("##### REGISTRO DE LA SOLICITUD ES: " + firmaElectronica.getRegistroPatronal() );
		
		if (!registroPatronalCertificado.equals(firmaElectronica.getRegistroPatronal())){
			throw new RegistroPatronalInvalidoEnCertificadoException();
		}
	}
	
	@Override
	public FirmaElectronica validarCertificado(FirmaElectronica firmaElectronica)
			throws FirmaDigitalException {

		String sPKCS7 = firmaElectronica.getsPKCS7();

		try {
			// valida el parámetro sPKCS7
			if (sPKCS7 != null && !sPKCS7.trim().equals("")) {
				// Invoca al Servicio Web
				
				// TODO: MASE - IMPLEMENTAR LLAMADO A WS Y QUITAR DATOS DUMMY
//				pki = new PKI_Impl();
//				svc = pki.getPKISoap();
//				stub = ((StubImpl) svc);
//				stub._setTargetEndpoint(new URL(url));
				
//				_ValidaCertificadoResponse respuesta = svc
//						.validaCertificado(sPKCS7);

				// TODO: MASE - QUITAR DATOS DUMMY
				_ValidaCertificadoResponse respuesta = new _ValidaCertificadoResponse();
				respuesta.setValido(true);
				respuesta.setNumeroSerie("NUM_SERIE_56789_DUMMY");
				respuesta.setSecuenciaFirma("9712345678");
				
				// Si el certificado es válido obtendrá el serial
				if (respuesta.getValido()) {

					this.log.debug("Certificado valido!");
					this.log.debug("Numero serie certificado -> "
							+ respuesta.getNumeroSerie());

					firmaElectronica.setSerialCertificado(respuesta.getNumeroSerie());

					/*
					 * linea temporal mientras el WebService implementa el
					 * método, al implementarlo se deberá comentar la línea
					 * siguiente:
					 */
					firmaElectronica.setSerialCertificado(arregalSerial(firmaElectronica
							.getSerialCertificado()));
					this.log.debug("arregalSerial(this.serial_certificado)"
							+ arregalSerial(firmaElectronica.getSerialCertificado()));

					firmaElectronica
							.setSecuenciaNotaria(respuesta.getSecuenciaFirma());
				} else {
					/*
					 * Si el certificado no es válido obtiene el código de error
					 * y el mensaje ocurrido
					 */
					this.log.debug("Certificado invalido!");
					firmaElectronica
							.setCodRespuesta(respuesta.getErrorNumber());
					firmaElectronica.setMsgRespuesta(respuesta
							.getErrorMessage());
				}
			}
		} catch (/*RemoteException*/ Exception e) {
			this.log.error(e);
			throw new FirmaDigitalException(e.getMessage());
		}

		return firmaElectronica;
	}
	
	@Override
	public FirmaElectronica guardarEnNotaria(FirmaElectronica firmaElectronica)
			throws FirmaDigitalException {
				
		String sPKCS7 = firmaElectronica.getsPKCS7();
		
		try {
			
			// TODO: MASE - IMPLEMENTAR LLAMADO A WS Y QUITAR DATOS DUMMY
//			pki = new PKI_Impl();
//			svc = pki.getPKISoap();
//			stub = ((StubImpl) svc);
//			stub._setTargetEndpoint(new URL(url));
			
			// valida el parámetro sPKCS7
			if (sPKCS7 != null && !sPKCS7.trim().equals("")) {
				
				// Invoca al Servicio Web
				// TODO: MASE - IMPLEMENTAR WS Y QUITAR DATOS DUMMY
//				_NotariaResponse respuesta = svc.notaria(sPKCS7);
				
				_NotariaResponse respuesta = new _NotariaResponse();
				respuesta.setErrorNumber(0);
				respuesta.setRecibo("RECIBO TEST");
				respuesta.setReciboNotarial("675348/978UD-978");
				
				int iCodError = respuesta.getErrorNumber();
				
				if (iCodError == 0) {
					firmaElectronica.setRecibo(respuesta.getRecibo());
					firmaElectronica.setReciboNotarial(respuesta.getReciboNotarial());
				} else {
					// En caso de error
					firmaElectronica.setCodRespuesta(Integer.toString(iCodError));
					firmaElectronica.setMsgRespuesta(respuesta.getErrorMessage());
				}
			}
		} catch (/*RemoteException*/ Exception e) {
			this.log.error(e);
			throw new FirmaDigitalException(e.getMessage());
		}
		return firmaElectronica;
	}

	/**
	 * Método temporal (está dentro de seguridataAPI), se utilizará en ésta
	 * clase mientras se implementa en la respuesta del WebService El método
	 * obtiene el serial y elimina los datos de relleno para retornar el serial
	 * original
	 * 
	 * @param serial
	 * @return
	 */
	private String arregalSerial(String serial) {
		String serialCorregido = serial.trim();
		if (serial.length() > 22) {
			serialCorregido = "";
			for (int i = 0; i < serial.length(); i++)
				if (i % 2 == 1)
					serialCorregido = serialCorregido + serial.charAt(i);
		}
		return serialCorregido;
	}
	
	/**
	 * Obtiene los datos del certificado mediante el pkcs7.
	 * 
	 * @param pkcs7 Cadena a tratar para obtener los datos.
	 * @return Objeto <code>X509Certificate</code> el cual contiene las
	 *         propiedades del certificado.
	 * @throws Exception En caso de error.
	 */
	private X509Certificate getObtenerCertificadoPorPkcs7(String pkcs7)
			throws RegistroPatronalInvalidoEnCertificadoException {
		
		sun.security.pkcs.PKCS7 pkcs7Obj;
		X509Certificate certificate = null;

		try {
			log.debug("##### OBTENIENDO EL CERTIFICADO DEL PKCS7");
			pkcs7Obj = new sun.security.pkcs.PKCS7(new BASE64Decoder().decodeBuffer(pkcs7));
			X509Certificate certificates[] = pkcs7Obj.getCertificates();
			
			for (int i = 0; i < certificates.length; i++) {
				certificate = certificates[i];
				log.debug("Cadena es: " + certificates[i]);
			}
		} catch (ParsingException e) {
			throw new RegistroPatronalInvalidoEnCertificadoException();
		} catch (IOException e) {
			throw new RegistroPatronalInvalidoEnCertificadoException();
		}

		return certificate;
	}

	/**
	 * Método que realiza el parse del PKCS7 y devuelve el NRP (Registro
	 * Patronal).
	 * 
	 * @param certificado Cadena separada por "|".
	 * @return NPIE (Registro patronal).
	 * @throws RegistroPatronalInvalidoEnCertificadoException
	 */
	private String obtenerRegistroPatronalDelCertificado(
			X509Certificate certificado)
			throws RegistroPatronalInvalidoEnCertificadoException {

		String[] cadenaCertificado = certificado.getSubjectDN().getName()
				.split(",");
		String rp = new String();

		for (String str : cadenaCertificado) {
			if (str.indexOf("OU=") > 0) {
				rp = str.replaceAll("\\s", "").trim().substring(3);
				break;
			}
		}
		
		log.debug("##### EL PATRON ES .:::::" + rp);
		
		if (rp.equals(""))
			throw new RegistroPatronalInvalidoEnCertificadoException();

		return rp;
	}
}
