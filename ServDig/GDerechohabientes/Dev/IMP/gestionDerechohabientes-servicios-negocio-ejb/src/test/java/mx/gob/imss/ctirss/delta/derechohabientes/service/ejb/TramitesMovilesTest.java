package mx.gob.imss.ctirss.delta.derechohabientes.service.ejb;

import mx.gob.imss.ctirss.delta.derechohabientes.service.test.EjbLocator;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.TramitesDerechohabientesMovilRemote;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.TramiteDerechohabientesMovilDto;
import mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes.CambioClinicaResponse;
import mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes.EnvioCorreoResponse;
import mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes.RegistroDerechohabienteResponse;
import mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes.ValidaRequisitosResponse;
import mx.gob.imss.digital.modelo.domicilio.Asentamiento;
import mx.gob.imss.digital.modelo.domicilio.EntidadFederativa;
import mx.gob.imss.digital.modelo.domicilio.Localidad;
import mx.gob.imss.digital.modelo.domicilio.Municipio;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TramitesMovilesTest {
	
	private static final Logger LOG;

	static {
		LOG = LoggerFactory.getLogger(TramitesMovilesTest.class);
	}
	
	@Test
	public void testRequisitos() {

		String correo = "juc.mmx@gmail.com";
		String curp = "JARA951228MDFCMR04";
		int idTramite = 36;
		TramitesDerechohabientesMovilRemote tramitesMoviles = EjbLocator.getTramitesDerechohabientes();

		ValidaRequisitosResponse respuesta = tramitesMoviles.validaRequisitosTramite(curp, correo, idTramite);
		
		if(respuesta.getCodigo().equals("000")) {
			TramiteDerechohabientesMovilDto res = respuesta.getTramiteDerechohabienteMovilDto();
			System.out.println("Los dastos del tramite son idPersona: " + res.getIdPersona() + ", idAsignacion: " + res.getIdAsignacionNSS());
		} else {
			System.out.println("la respuesta es null: " + respuesta.getCodigo() == null ? "la respuesta es nula" : ("la respuesta es de tipo " + respuesta.getCodigo().getClass()));
			System.out.println("Ocurrio un error al generar el registro codigo " +respuesta.getCodigo() + ", descirpcion: " + respuesta.getMensaje());
		
		}
		
		/*
		System.out.println("Voy a mandar la exception");
		try {
			System.out.println("Mando exception");
			throw new NullPointerException();
		} catch(Exception e) {
			System.out.println("ocurrio un error " + e.toString());
			System.out.println("ocurrio un error " + e.getMessage() == null);
			System.out.println("ocurrio un error " + e.getCause() == null);
			System.out.println("ocurrio un error " + e.getLocalizedMessage() == null);
			//System.out.println("ocurrio un error " + e.getCause().getMessage());
			e.printStackTrace();
		}*/
	}
	
	@Test
	public void testTramiteRegistroDerechohabienteMovil() {
		
		TramitesDerechohabientesMovilRemote tramitesMoviles = EjbLocator.getTramitesDerechohabientes();
		
		String correo = "JARA951228MDFCMR04@pruebas.com";
		Long idAsignacionNSS = 85867626L;
		Long idPersona = 145654590L;
		Long idParentesco = 5L;
		Long idConsultorio = 864L; //umf 20
		
		
		mx.gob.imss.digital.modelo.domicilio.Domicilio domicilioDigRecortado = new mx.gob.imss.digital.modelo.domicilio.Domicilio();
		domicilioDigRecortado.setNumExteriorAlf("1 A");
		domicilioDigRecortado.setCalle("Calle falsa");
		domicilioDigRecortado.setCodigoPostal("07700");
		domicilioDigRecortado.setAsentamiento(new Asentamiento());
		domicilioDigRecortado.getAsentamiento().setClave("011858");
		domicilioDigRecortado.getAsentamiento().setLocalidad(new Localidad());
		domicilioDigRecortado.getAsentamiento().getLocalidad().setMunicipio(new Municipio());
		domicilioDigRecortado.getAsentamiento().getLocalidad().getMunicipio().setClave("005");
		domicilioDigRecortado.getAsentamiento().getLocalidad().getMunicipio().setEntidadFederativa(new EntidadFederativa());
		domicilioDigRecortado.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().setClave("09");
		
		TramiteDerechohabientesMovilDto tramiteDto = new TramiteDerechohabientesMovilDto(idAsignacionNSS,idPersona, idParentesco, idConsultorio);
	
		RegistroDerechohabienteResponse respuesta = tramitesMoviles.ejecutaRegistroDerechohabiente(tramiteDto, domicilioDigRecortado, correo);
		
		if(respuesta.getCodigo().equals("000")) {
			tramitesMoviles.enviarCorreoConDocumentos(respuesta.getFolio(), correo);
			System.out.println("El folio de la solicitud generada es: " + respuesta.getFolio());
		} else {
			System.out.println("Ocurrio un error al generar el registro codigo " +respuesta.getCodigo() + ", descirpcion: " + respuesta.getMensaje());
		}
		
	}
	
	@Test
	public void testTramiteCambioClinica() {
		
		TramitesDerechohabientesMovilRemote tramitesMoviles = EjbLocator.getTramitesDerechohabientes();
		String correo = "JARA951228MDFCMR04@pruebas.com";
		Long idAsignacionNSS = 85400614L;
		Long idPersona = 145170052L;
		Long idParentesco = 5L;
		Long idConsultorio = 8316L;
		
		
		mx.gob.imss.digital.modelo.domicilio.Domicilio domicilioDigRecortado = new mx.gob.imss.digital.modelo.domicilio.Domicilio();
		domicilioDigRecortado.setNumExteriorAlf("2 A");
		domicilioDigRecortado.setCalle("Cambio de clinica");
		domicilioDigRecortado.setCodigoPostal("07700");
		domicilioDigRecortado.setAsentamiento(new Asentamiento());
		domicilioDigRecortado.getAsentamiento().setClave("011858");
		domicilioDigRecortado.getAsentamiento().setLocalidad(new Localidad());
		domicilioDigRecortado.getAsentamiento().getLocalidad().setMunicipio(new Municipio());
		domicilioDigRecortado.getAsentamiento().getLocalidad().getMunicipio().setClave("005");
		domicilioDigRecortado.getAsentamiento().getLocalidad().getMunicipio().setEntidadFederativa(new EntidadFederativa());
		domicilioDigRecortado.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().setClave("09");
		
		
		TramiteDerechohabientesMovilDto tramiteDto = new TramiteDerechohabientesMovilDto(idAsignacionNSS, idPersona, idParentesco, idConsultorio);
		
		CambioClinicaResponse respuesta = tramitesMoviles.ejecutaCambioClinicaDerechohabiente(tramiteDto, domicilioDigRecortado, correo);
		if(respuesta.getCodigo().equals("000")) {
			System.out.println("El folio de la solicitud generada es: " + respuesta.getFolio());
			System.out.println("Se enviara correo para la solicitud: " + respuesta.getFolio());
			EnvioCorreoResponse respCorreo = tramitesMoviles.enviarCorreoConDocumentos(respuesta.getFolio(), correo);
			
			if(respCorreo.getCodigo().equals("000")) {
				System.out.println("El correo fue enviado correctamente (" + respCorreo.getCodigo() + ","+respCorreo.getMensaje()+")");
			} else {
				System.out.println("Ocurrio un error al generar el registro codigo " +respuesta.getCodigo() + ", descirpcion: " + respuesta.getMensaje());
			}
		} else {
			System.out.println("Ocurrio un error al generar el registro codigo " +respuesta.getCodigo() + ", descirpcion: " + respuesta.getMensaje());
		}
	}
	
	@Test
	public void enviarCorreo() {
		TramitesDerechohabientesMovilRemote tramitesMoviles = EjbLocator.getTramitesDerechohabientes();
		String correo = "JARA951228MDFCMR04@pruebas.com";
		String folio = "14468624953283603007";//"14358930150323574809";//
		EnvioCorreoResponse respuesta = tramitesMoviles.enviarCorreoConDocumentos(folio, correo);
		
		if(respuesta.getCodigo().equals("000")) {
			System.out.println("Se ha enviado el correo: ");
		} else {
			System.out.println("Ocurrio un error al enviar el coreeo codigo " +respuesta.getCodigo() + ", descirpcion: " + respuesta.getMensaje());
		}
	}

}
