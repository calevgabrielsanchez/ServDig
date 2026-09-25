package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.AsignacionNssDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.EnvioCorreoServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.model.EmailPayloadType;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration.EMailProducer;
import mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes.EnvioCorreoResponse;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNss;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;

@Stateless( name = "envioCorreoService", mappedName = "envioCorreoService")
public class EnvioCorreoServiceBusiness extends AbstractServiceBusiness implements EnvioCorreoServiceRemote {
	
	@EJB(mappedName = "EMailQProducer")
	private EMailProducer eMailProducer;
	
	@EJB 
	private AsignacionNssDaoLocal asignacionNssDao;
	
	@Override
	public EnvioCorreoResponse enviarCorreoPorNssMovil(String nss,String destinatario) {
		log.info("Inicia el envio de correo por NSS a los JMS");
		EnvioCorreoResponse envioCorreoResponse = null;
		Map<String,String> parametrosCorreo = new HashMap<String, String>();
		parametrosCorreo.put("idTipoTramite", String.valueOf(TipoTramiteEnum.CONSULTA_DE_VIGENCIA_DE_DERECHOS.getCodigo()));
		
		EmailPayloadType mailWrapper = new EmailPayloadType();
		mailWrapper.setSubject("Correo de confirmación del IMSS");
		mailWrapper.setContent(" ");
		mailWrapper.setTo(destinatario);
		mailWrapper.setContentType("text/html");
		try {
			DitAsignacionNss asigna = asignacionNssDao.getAsignacionNSSbyNSS(nss);
			if (asigna != null && asigna.getDitPersona() != null) {
				DitPersona persona=asigna.getDitPersona();
		 	    parametrosCorreo.put("apellidoPaterno",persona.getNomPrimerApellido());
				parametrosCorreo.put("apellidoMaterno", persona.getNomSegundoApellido());
				parametrosCorreo.put("nombre", persona.getNomNombre());
				parametrosCorreo.put("nss", nss);
				parametrosCorreo.put("curp", persona.getCurp());
				parametrosCorreo.put("fechaNacimiento", persona.getFecNacimiento().toString());
				parametrosCorreo.put("lugarNacimiento","");
				parametrosCorreo.put("sexo", persona.getDicSexo().getDesSexo());
				
				String patronFecha = "EEEE dd 'de' MMMM 'de' yyyy', siendo las' hh:mm:ss 'hrs.'";
				Locale locale = new Locale("es","MX");
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patronFecha, locale);
				String strFecha = simpleDateFormat.format(Calendar.getInstance().getTime());
				strFecha = strFecha.substring(0, 1).toUpperCase() + strFecha.substring(1, strFecha.length());
				parametrosCorreo.put("fechaOperacion", strFecha);
				
				log.info("Enviando correo al destinatario: "+destinatario+", Nombre: "+persona.getNomPrimerApellido()+" "+persona.getNomSegundoApellido()+" "+persona.getNomNombre());
				mailWrapper.setParameters(parametrosCorreo);
				eMailProducer.agendarCorreoElectronico(mailWrapper);
				envioCorreoResponse = new EnvioCorreoResponse("0001","El correo está siendo procesado para su envío.");
				log.info("Finaliza el envio de correo por NSS a los JMS");
			} else {
				envioCorreoResponse = new EnvioCorreoResponse("0002", "No se encontró a la persona con el NSS proporcionado");
			}
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return envioCorreoResponse;
	}
}