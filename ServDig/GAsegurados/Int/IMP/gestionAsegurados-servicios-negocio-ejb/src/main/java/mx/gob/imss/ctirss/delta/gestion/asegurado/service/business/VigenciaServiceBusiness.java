package mx.gob.imss.ctirss.delta.gestion.asegurado.service.business;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.TramiteDocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.AsignacionNSSNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.SolicitudNssCorreoException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.model.AttachmentContent;
import mx.gob.imss.ctirss.delta.framework.base.model.EmailPayloadType;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.DocumentoException;
import mx.gob.imss.ctirss.delta.framework.util.Base64Cipher;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.SolicitudNssCorreoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.VigenciaServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration.EMailProducer;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.asegurado.SolicitudNssCorreo;
import mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes.EnvioCorreoResponse;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = "vigenciaServiceBusiness", mappedName = "vigenciaServiceBusiness")
public class VigenciaServiceBusiness implements VigenciaServiceRemote {
	
	private static final Logger logger = LoggerFactory.getLogger(VigenciaServiceBusiness.class);
	
	private static final String MIME_PDF = "application/pdf";
	
	private String MSG_ERROR_COMPARACION = "No se puede realizar el trámite Consulta de Vigencia " +
			" de Derechos por internet ya que los datos estadísticos localizados en el Instituto no coinciden " + 
					 "con los datos encontrados en RENAPO, para poder realizar el trámite deberá presentarse en la " +
					 "subdelegación del Instituto más cercana para actualizar su información.";
	
	private String MSG_ERROR_NSS = "No se puede realizar el trámite de Consulta de Vigencia de Derechos por internet " +
			"ya que el Número de Seguridad Social no fue  localizado en el Instituto, para poder realizar el trámite " +
			"deberá presentarse en la  subdelegación del Instituto más cercana a su domicilio para aclarar su situación";
	
	@EJB
	SolicitudNssCorreoServiceBusinessRemote solicitudNssCorreoServiceBusinessRemote;
	
	@EJB
	ServiceBusinessRemote serviceBusinessRemote;
	
	@EJB
	GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	
	@EJB
	TramiteDocumentosServiceRemote tramiteDocumentosServiceRemote;
	
	@EJB(mappedName = "EMailQProducer")
	private EMailProducer eMailProducer;
	
	@Override
	public EnvioCorreoResponse consultaVigenciaMovilesEnvioCorreo(String curp, String nss, String correoElectronico) {
		System.out.println("Entra ConsultaVigenica");
		byte[] bytesAcuse;
	
		EnvioCorreoResponse envioCorreoResponse = null;
		Fisica fisica = new Fisica();
		Fisica fisicaEncontrada = null;
		int codigoRespuesta = 0;
		try {
			// Se pasa a minúsculas el correo capturado y el de confirmación
			fisica.setCorreoElectronico(new CorreoElectronico());
			fisica.getCorreoElectronico().setCorreo(correoElectronico.toLowerCase());
			fisica.setCurp(curp);
			fisica.setNss(nss);
	
			// Se nulea correoElectronicoFiscal de fisica para que no se guarde en la solicitud y se tenga duplicado el correo.
			fisica.setCorreoElectronicoFiscal(null);
			
			SolicitudNssCorreo nssCorreo = new SolicitudNssCorreo();
			nssCorreo.setCorreo(fisica.getCorreoElectronico());
			nssCorreo.setCurp(fisica.getCurp());
			nssCorreo.setCveIdTipoSolicitud(TipoSolicitudEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOS.getValor().longValue());
			
		
			// Valida la relacion Correo => CURP, si existe esta relacion se valida que la consulta no haya excedido los intentos maximos del día por tipo de servicio
			codigoRespuesta = solicitudNssCorreoServiceBusinessRemote.isConsultaRegistroNSSValid(nssCorreo, true);
			nssCorreo.setOperacionEjecutar(codigoRespuesta);
			// Se busca el curp capturado en RENAPO
			fisicaEncontrada = serviceBusinessRemote.validacionesNSSConsultaVigencia(fisica);
			fisicaEncontrada.setCorreoElectronico(fisica.getCorreoElectronico());
			
			AsignacionNSS asignacionNSS = null;
			try {
				asignacionNSS = grupoFamiliarServiceRemote.getAsignacionNssSinPersona(fisica.getNss(), false);
				asignacionNSS.setCorreoElectronico(fisica.getCorreoElectronico());
				
				//Identificadores para el tipo de tramite, solicitud
				Map<String, Integer> identificadoresMap = new HashMap<String, Integer>();
				identificadoresMap.put("tramite", TipoTramiteEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOHAB.getCodigo());
				identificadoresMap.put("solicitud", TipoSolicitudEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOS.getValor()); 
				identificadoresMap.put("origenSolicitud", OrigenSolicitudEnum.MOVILES.getId().intValue()); 

				
				Map<String, Object> resultado = tramiteDocumentosServiceRemote.generaTramiteDocumentoReporteConstanciaVigencia(asignacionNSS, null, identificadoresMap, true);
				bytesAcuse = (byte[]) resultado.get("documento");
				
				//Envío de correo
				try {
					tramiteDocumentosServiceRemote.enviaCorreo(asignacionNSS, bytesAcuse, null);
					envioCorreoResponse = new EnvioCorreoResponse("0001","El correo está siendo procesado para su envío.");
				} catch(Exception ex) {
					envioCorreoResponse = new EnvioCorreoResponse("0002", "Error al enviar el correo.");
					ex.printStackTrace();
				}
				// METODO ALTERNO QUE ENVIA CORREO POR MEDIO DE QUES Y QUE YA CUENTA CON FORMATO GOB MX (VER EXISTENCIA DE PLANTILLA EN SERVIDOR OSB)
//				envioCorreoResponse = enviaCorreoPorJms(asignacionNSS, bytesAcuse);
				
				//Se actualiza el numero de intentos de consulta
				serviceBusinessRemote.ejecutarOperacionValidacionCorreoCurp(nssCorreo);
			} catch (DerechohabientesBusinessException e2) {
				e2.printStackTrace();
				envioCorreoResponse = new EnvioCorreoResponse("0002", e2.getMessage());
				tramiteDocumentosServiceRemote.generarSolicitudRechazo(OrigenSolicitudEnum.INTERNET.getId(), fisica.getCurp(), e2.getMessage());
			} catch(DocumentoException ex){
				tramiteDocumentosServiceRemote.generarSolicitudRechazo(OrigenSolicitudEnum.INTERNET.getId(), fisica.getCurp(), ex.getMessage());
				logger.error(ex.getMessage());
				envioCorreoResponse = new EnvioCorreoResponse("0002", "Ocurrió un error al generar el reporte de vigencia.");
			} catch (Exception e2) {
				e2.printStackTrace();
				tramiteDocumentosServiceRemote.generarSolicitudRechazo(OrigenSolicitudEnum.INTERNET.getId(), fisica.getCurp(), e2.getMessage());
				envioCorreoResponse = new EnvioCorreoResponse("0002", "Ocurrió un error al generar el reporte de vigencia.");
			}
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			logger.error(e.getMessage());
			envioCorreoResponse = new EnvioCorreoResponse("0002", "No se localizó información en RENAPO con la CURP capturada.");
			tramiteDocumentosServiceRemote.generarSolicitudRechazo(OrigenSolicitudEnum.INTERNET.getId(), fisica.getCurp(), "Mensaje: No se localizó información en RENAPO con la CURP capturada.");
		} catch (ClienteWebserviceRenapoCurpException e) {
			logger.error(e.getMessage());
			envioCorreoResponse = new EnvioCorreoResponse("0002", "No se localizó información en RENAPO con la CURP capturada.");
			tramiteDocumentosServiceRemote.generarSolicitudRechazo(OrigenSolicitudEnum.INTERNET.getId(), fisica.getCurp(), "Mensaje: No se localizó información en RENAPO con la CURP capturada.");
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			logger.error(e.getMessage());
			envioCorreoResponse = new EnvioCorreoResponse("0002", e.getMessage());
			tramiteDocumentosServiceRemote.generarSolicitudRechazo(OrigenSolicitudEnum.INTERNET.getId(), fisica.getCurp(), e.getMessage());
		} catch (ErrorComparacionDatosRENAPOException e) {
			logger.error(e.getMessage());
			envioCorreoResponse = new EnvioCorreoResponse("0002", MSG_ERROR_COMPARACION);
			tramiteDocumentosServiceRemote.generarSolicitudRechazo(OrigenSolicitudEnum.INTERNET.getId(), fisica.getCurp(), e.getMessage());
		} catch (SolicitudNssCorreoException e) {
			//Esta exception debe mandar al home (a traves de la pantalla de salida)
			logger.error(e.getMessage());
			envioCorreoResponse = new EnvioCorreoResponse("0002", e.getMessage());
			tramiteDocumentosServiceRemote.generarSolicitudRechazo(OrigenSolicitudEnum.INTERNET.getId(), fisica.getCurp(), e.getMessage());
		} catch (AsignacionNSSNoLocalizadoException e) {
			envioCorreoResponse = new EnvioCorreoResponse("0002", MSG_ERROR_NSS);
			tramiteDocumentosServiceRemote.generarSolicitudRechazo(OrigenSolicitudEnum.INTERNET.getId(), fisica.getCurp(), e.getMessage());
		}catch (Exception e) {
			// TODO: handle exception
			envioCorreoResponse = new EnvioCorreoResponse("0002", "Ocurrió un error al generar el reporte de vigencia.");
			tramiteDocumentosServiceRemote.generarSolicitudRechazo(OrigenSolicitudEnum.INTERNET.getId(), fisica.getCurp(), e.getMessage());

		}
		return envioCorreoResponse;
	}
	
	private EnvioCorreoResponse enviaCorreoPorJms(AsignacionNSS asignacionNSS, byte[] bytesAcuse) {
		EnvioCorreoResponse envioCorreoResponse = null;
		logger.info("Inicia el envio de correo por NSS a los JMS");
		
		try {
			Map<String,String> parametrosCorreo = new HashMap<String, String>();
			parametrosCorreo.put("idTipoTramite", String.valueOf(TipoTramiteEnum.CONSULTA_DE_VIGENCIA_DE_DERECHOS.getCodigo()));
			
			EmailPayloadType mailWrapper = new EmailPayloadType();
			mailWrapper.setSubject("IMSS DIGITAL REPORTE DE VIGENCIA DE DERECHOS");
			mailWrapper.setContent(" ");
			mailWrapper.setTo(asignacionNSS.getCorreoElectronico().getCorreo());
			mailWrapper.setContentType("text/html");
			
			AttachmentContent attachment = new AttachmentContent();
			attachment.setTextBody(Base64Cipher.simpleEncode(bytesAcuse));
			attachment.setContentDisposition("reporteVigenciaDerechos.pdf");
			attachment.setContentType(MIME_PDF);
			mailWrapper.setAttachment(attachment);
			
			parametrosCorreo.put("apellidoPaterno",asignacionNSS.getPrimerApellido());
			parametrosCorreo.put("apellidoMaterno", asignacionNSS.getSegundoApellido());
			parametrosCorreo.put("nombre", asignacionNSS.getNombre());
			parametrosCorreo.put("nss", asignacionNSS.getNss());
			parametrosCorreo.put("curp", asignacionNSS.getCurp());
			parametrosCorreo.put("fechaNacimiento", asignacionNSS.getFechaNacimientoFormateada());
			parametrosCorreo.put("lugarNacimiento",asignacionNSS.getLugarNacimiento().getNombre());
			parametrosCorreo.put("sexo", asignacionNSS.getSexo().getDescripcion());
			parametrosCorreo.put("correoElectronico", asignacionNSS.getCorreoElectronico().getCorreo());
			
			String patronFecha = "EEEE dd 'de' MMMM 'de' yyyy', siendo las' hh:mm:ss 'hrs.'";
			Locale locale = new Locale("es","MX");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patronFecha, locale);
			String strFecha = simpleDateFormat.format(Calendar.getInstance().getTime());
			strFecha = strFecha.substring(0, 1).toUpperCase() + strFecha.substring(1, strFecha.length());
			parametrosCorreo.put("fechaOperacion", strFecha);
			
			logger.info("Enviando correo al destinatario: "+asignacionNSS.getCorreoElectronico().getCorreo()+", Nombre: "+asignacionNSS.getPrimerApellido()+" "+asignacionNSS.getSegundoApellido()+" "+asignacionNSS.getNombre());
			mailWrapper.setParameters(parametrosCorreo);
			eMailProducer.agendarCorreoElectronico(mailWrapper);
			logger.info("Finaliza el envio de correo por NSS a los JMS");
			envioCorreoResponse = new EnvioCorreoResponse("0001","El correo está siendo procesado para su envío.");
		} catch (Exception e) {
			e.printStackTrace();
			envioCorreoResponse = new EnvioCorreoResponse("0002", "Error al enviar el correo.");
		}
		return envioCorreoResponse;
	}

}
