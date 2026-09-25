/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.solicitud.firma;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.cert.X509Certificate;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.firma.ErrorEnInvocacionRecursoRemotoException;
import mx.gob.imss.ctirss.delta.exception.firma.ModelAccessException;
import mx.gob.imss.ctirss.delta.exception.firma.RecursoRemotoNoDisponibleException;
import mx.gob.imss.ctirss.delta.exception.firma.RegistroPatronalInvalidoEnCertificadoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.solicitud.SolicitudServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.firma.FirmaElectronicaBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import sun.security.pkcs.ParsingException;
import weblogic.utils.encoders.BASE64Decoder;

@Stateless(name="firmaElectronicaBusiness", mappedName="firmaElectronicaBusiness")
public class FirmaElectronicaBusiness extends
		AbstractServiceBusiness implements FirmaElectronicaBusinessRemote {

	@EJB
	SolicitudServiceEntityLocal solicitudEntity;
	
	@EJB
	FirmaDigitalBusinessRemote firmaDigitalBusiness;
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.smod.solicitud.businesservice.IFirmaElectronicaBusinessService#obtenerCadenaAFirmar(java.math.BigDecimal)
	 */
	@Override
	public String obtenerCadenaAFirmar(Solicitud solicitud,SujetoObligado sujetoObligado)
			throws RecursoRemotoNoDisponibleException,
			ErrorEnInvocacionRecursoRemotoException, ModelAccessException {
		
		StringBuilder cadenaOriginal = new StringBuilder();				
		try {
			cadenaOriginal.append(generarCadenaAFirmar(solicitud, sujetoObligado));
			
		} catch (Exception e) {
			log.error("ERROR [FirmaelectronicaBusiness-obtenerCadenaAFirmar]: " + e.getMessage());
			e.printStackTrace();
			cadenaOriginal.append("|||||");
		}
		return cadenaOriginal.toString();
	}

	
	private String generarCadenaAFirmar(Solicitud solicitud, SujetoObligado sujetoObligado){
		
		StringBuilder cadenaCadenaAFirmar = new StringBuilder();
		
		// SE ARMA LA CADENA
		if (sujetoObligado!=null){			
			cadenaCadenaAFirmar.append(sujetoObligado.getNumeroRegistroPatronal()).append("|");			
			cadenaCadenaAFirmar.append(solicitud.getFechaSolicitud()).append("|");		
			if (sujetoObligado.getFisica()!=null)
				cadenaCadenaAFirmar.append(sujetoObligado.getFisica().getRfc()).append("|");
			else
				cadenaCadenaAFirmar.append("|");			
			cadenaCadenaAFirmar.append(solicitud.getNoFolioSolicitud()).append("|");			
			cadenaCadenaAFirmar.append(solicitud.getObservacion()).append("|");
			if (sujetoObligado.getDomicilioFiscal()!=null){
				if (sujetoObligado.getDomicilioFiscal().getAsentamiento()!=null)
					if (sujetoObligado.getDomicilioFiscal().getAsentamiento().getLocalidad()!=null)
						if (sujetoObligado.getDomicilioFiscal().getAsentamiento().getLocalidad().getMunicipio()!=null)
						cadenaCadenaAFirmar.append(sujetoObligado.getDomicilioFiscal().getAsentamiento().getLocalidad().getMunicipio().getNombre().toString()).append("|");
			}
			else
				cadenaCadenaAFirmar.append("|");
			cadenaCadenaAFirmar.append(sujetoObligado.getSubdelegacion().getDescripcion()).append("|");
		}
		
		log.debug("<OTIKA-business>"+cadenaCadenaAFirmar);
		return cadenaCadenaAFirmar.toString();
	}

	@SuppressWarnings("unused")
	@Override
	public Solicitud firmarSolicitud(FirmaElectronica firma)
			throws RecursoRemotoNoDisponibleException,
			ErrorEnInvocacionRecursoRemotoException, ModelAccessException,
			RegistroPatronalInvalidoEnCertificadoException {
		
		BigDecimal ESTATUS_SOLICITUD_CONCLUIDA = new BigDecimal(6);
		BigDecimal ESTATUS_MOVIMIENTO_PENDIENTE_DE_PROCESAR = new BigDecimal(2);
		BigDecimal TIPO_MOVIMIENTO = BigDecimal.ONE;
		BigDecimal TIPO_CAUSA_MOVIMIENTO = BigDecimal.ONE;
		
		FirmaElectronica firmaElectronica = null;
		Solicitud solicitud = new Solicitud();		
					
		try {
//			rules.validaRegistroPatronaEnCertificado(firma);//utility
			validaRegistroPatronaEnCertificado(firma);			
			solicitud = new Solicitud();			
			solicitud.setSolicitudId(new Long((firma.getIdSolicitud().toString())));
			// COMIENZA EL PROCESO DE FIRMA ELECTRONICA
//			firmaElectronica = firmaElectronicaProxy.guardarEnNotaria(firma);//utility
			firmaElectronica = guardarEnNotaria(firma);

			// AHORA ACTUALIZA LA SOLICITUD CON LOS DATOS DE LA FIRMA Y LA ACTUALIZACION DEL ESTATUS.
			solicitud.setSecuenciaDeNotaria(firmaElectronica.getRecibo());
			solicitud.setSelloDigital(firmaElectronica.getReciboNotarial());
			//solicitud.setEstadoSolicitud(new EstadoSolicitud());
			solicitud.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
			firmaDigitalBusiness.insertarSolicitudFirmaDigital(solicitud, firmaElectronica);

			// GENERA EL TRAMITE PARA LA SOLICITUD.			
			log.debug("##### AGREGANDO MOVIMIENTO");			
			log.debug("##### AGREGO MOVIMIENTO");
		}
		/*catch (RegistroPatronalInvalidoEnCertificadoException e) {
			log.error("##### ERROR RegistroPatronalInvalidoEnCertificadoException: " + e.getMessage());
			e.printStackTrace();
			throw e;
		}*/
		catch (Exception e) {
			log.error("##### ERROR Exception: " + e.getMessage());
			e.printStackTrace();
			throw new ModelAccessException(e.getMessage());
		}
		log.debug("************************** FIN DE LA FIRMA Y ALTA DE MOVIMIENTO **************************");
		
		return solicitud;
	}

	public void validaRegistroPatronaEnCertificado(
			FirmaElectronica firmaElectronica)
			throws RegistroPatronalInvalidoEnCertificadoException {
		X509Certificate certificado = null;
		String registroPatronalCertificado = new String();
		
		certificado = this.getObtenerCertificadoPorPkcs7(firmaElectronica.getsPKCS7());
		registroPatronalCertificado = obtenerRegistroPatronalDelCertificado(certificado);

		log.debug("##### REGISTRO DEL CERTIFICADO ES: " + registroPatronalCertificado );
		log.debug("##### REGISTRO DE LA SOLICITUD ES: " + firmaElectronica.getRegistroPatronal() );
		
		if (!registroPatronalCertificado.equals(firmaElectronica.getRegistroPatronal())){
			throw new RegistroPatronalInvalidoEnCertificadoException();
		}
	}
	
	/**
	 * Obtiene los datos del certificado mediante el pkcs7.
	 * @param pkcs7 Cadena a tratar para obtener los datos.
	 * @return Objeto <code>X509Certificate</code> el cual contiene las propiedades del certificado.
	 * @throws Exception En caso de error.
	 */
	private X509Certificate getObtenerCertificadoPorPkcs7(String pkcs7) throws RegistroPatronalInvalidoEnCertificadoException {
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
		}catch (ParsingException e) {
			throw new RegistroPatronalInvalidoEnCertificadoException();
		}catch (IOException e) {
			throw new RegistroPatronalInvalidoEnCertificadoException();
		}


        return certificate;
    }
	
	/**
	 * Método que realiza el parse del PKCS7 y devuelve el NPIE (REgistro patronal).
	 * @param certificado Cadena separada por "|".
	 * @return NPIE (Registro patronal).
	 * @throws RegistroPatronalInvalidoEnCertificadoException 
	 */
	private String obtenerRegistroPatronalDelCertificado(X509Certificate certificado) throws RegistroPatronalInvalidoEnCertificadoException {
        
		String []cadenaCertificado = certificado.getSubjectDN().getName().split(",");
		String rp = new String();
		
		for(String str : cadenaCertificado)
		{
			if (str.indexOf("OU=") > 0)
			{
				rp = str.replaceAll("\\s", "").trim().substring(3);
				break;
			}
		}
		log.debug("##### EL PATRON ES .:::::" + rp);
		if (rp.equals("")) 
			throw new RegistroPatronalInvalidoEnCertificadoException();
			
		return rp;
    }
	
	public FirmaElectronica guardarEnNotaria(FirmaElectronica firmaElectronica) throws RecursoRemotoNoDisponibleException, ErrorEnInvocacionRecursoRemotoException {

		/*try {
			//pki = new PKI_Impl();
			//svc = pki.getPKISoap();
			//stub = ((StubImpl) svc);
			//stub._setTargetEndpoint(new URL(this.getUrl()));
			//stub._setTargetEndpoint(new URL(""));
		} catch (IOException e) {
			 
			e.printStackTrace();
		}
		
		try {
			 
			_NotariaResponse respuesta = svc.notaria(firmaElectronica.getsPKCS7());
			int iCodError = respuesta.getErrorNumber();
			if (iCodError == 0) {
				firmaElectronica.setRecibo(respuesta.getRecibo());
				firmaElectronica.setReciboNotarial(respuesta.getReciboNotarial());
			} else {
    	    	log.info("error al guardar en notaria");
    	    	log.info("codigo:"+respuesta.getErrorNumber()+"\nmensaje error:\n"+respuesta.getErrorMessage());
    	    	throw new ErrorEnInvocacionRecursoRemotoException();
			}			
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new RecursoRemotoNoDisponibleException();
		}*/
		
		firmaElectronica.setRecibo("234234");
		firmaElectronica.setReciboNotarial("334533454353453");		
		return firmaElectronica;
	}
	
}
