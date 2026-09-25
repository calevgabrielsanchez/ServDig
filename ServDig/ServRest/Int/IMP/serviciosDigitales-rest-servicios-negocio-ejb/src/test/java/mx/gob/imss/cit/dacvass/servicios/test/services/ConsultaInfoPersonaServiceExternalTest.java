package mx.gob.imss.cit.dacvass.servicios.test.services;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import mx.gob.imss.ctirss.delta.framework.base.model.EmailPayloadType;

public class ConsultaInfoPersonaServiceExternalTest {
	
	//private static final Logger LOG;
	
	//private transient final IConsultaPersonaServiceRemote services = EjbLocator.getIConsultaInfoPersonaServiceExternalRemote();
	
	//private transient final EMailProducerRemote serviceCorreo = EjbLocator.getIEMailProducerRemote();

	/*
	static {
		LOG = LoggerFactory.getLogger(ConsultaInfoPersonaServiceExternalTest.class);
	}*/
	/*
	@Test
	public void consultaInfoPersonaSD() {
		System.out.println("iniciando el test");
		try {
		String curp = "SAHJ790331HDFLRN01";
		//DatosPersona persona =	services.getInfoPersonaServiciosDigitales(curp);
		System.out.println("ya regrese ");
		System.out.println("los datos son " + persona.getCurp());
		}catch (Exception e) {
			System.out.println("ocurrio un error al ejecutar la prueba" + e.getMessage());
		}
		
	}
	*/
	
	@Test
	public void envioCorreoService() {
		System.out.println("iniciando el test");
		try {
		//	EMailProducerRemote serviceCorreo = EjbLocator.getIEMailProducerRemote();
			//mx.gob.imss.ctirss.delta.model.email.EmailPayloadType data = new  mx.gob.imss.ctirss.delta.model.email.EmailPayloadType();
			
			Long idSolicitud = 5892L;//5364l se puede usar esta tambien
		/*	
			data.setTo("juan.salinas@softtek.com");
			data.setContent("Notificación Trámite conlcuído (Comprobantes)");
			data.setSubject("Portal digital IMSS - Solicitud Atendida");
			data.setContentType("text/html");
		*/
			/*	
			Map<String,String> parametros = new HashMap<String, String>();
			parametros.put("idTipoTramite", "170");
			parametros.put("nombreCompleto", "prueba");
			parametros.put("fechaOpreacion", "10/10/2020");
			parametros.put("href", "www.google.com");
			parametros.put("correo", "juan.salinas@softtek.com");
			parametros.put("folio", "11111");
			parametros.put("tipoOperacion", "1");
			*/
			
			//data.setParameters("fechaOperacion#@#30/07/2020#@#idTipoTramite#@#170#@#tipoOperacion#@#1#@#nombreCompleto#@#Muebles ESCO S.A. DE C.V.#@#href#@#http://www.google.com");
/*
			serviceCorreo.registrarCorreoElectronico(data);
			serviceCorreo.registrarCorreoElectronico(data);
			serviceCorreo.registrarCorreoElectronico(data);
			//serviceCorreo.registrarCorreoElectronico(data);
			//serviceCorreo.registrarCorreoElectronico(data);
	*/		
		System.out.println("finaliza el test");	
		}catch (Exception e) {
			System.out.println("ocurrio un error al ejecutar la prueba" + e.getMessage());
		}
		
	}
	
	
	@Test
	public void envioCorreoServiceOriginal() {
		System.out.println("iniciando el test");
		try {
			//EMailProducer serviceCorreo = EjbLocator.getEMailQProducer();
			EmailPayloadType data = new  EmailPayloadType();
			
			//Long idSolicitud = 5892L;//5364l se puede usar esta tambien
			
			data.setTo("juan.salinas@softtek.com");
			data.setContent("Notificación Trámite conlcuído (Comprobantes)");
			data.setSubject("Portal digital IMSS - Solicitud Atendida");
			data.setContentType("text/html");
			
			Map<String,String> parametros = new HashMap<String, String>();
			parametros.put("idTipoTramite", "170");
			parametros.put("nombreCompleto", "juan carlitos");
			parametros.put("fechaOperacion", "30/07/2020");
			parametros.put("href", "www.google.com");
			parametros.put("correo", "juan.salinas@softtek.com");
			parametros.put("folio", "11111");
			parametros.put("tipoOperacion", "1");
			data.setParameters(parametros);
			//data.setParameters("fechaOperacion#@#30/07/2020#@#idTipoTramite#@#170#@#tipoOperacion#@#1#@#nombreCompleto#@#Muebles ESCO S.A. DE C.V.#@#href#@#http://www.google.com");
/*
			serviceCorreo.agendarCorreoElectronico(data);
			serviceCorreo.agendarCorreoElectronico(data);
			serviceCorreo.agendarCorreoElectronico(data);
			serviceCorreo.agendarCorreoElectronico(data);
			serviceCorreo.agendarCorreoElectronico(data);
			serviceCorreo.agendarCorreoElectronico(data);
			serviceCorreo.agendarCorreoElectronico(data);
			serviceCorreo.agendarCorreoElectronico(data);
			serviceCorreo.agendarCorreoElectronico(data);
			serviceCorreo.agendarCorreoElectronico(data);
			*/
			
		System.out.println("finaliza el test");	
		}catch (Exception e) {
			System.out.println("ocurrio un error al ejecutar la prueba" + e.getMessage());
		}
		
	}

}
