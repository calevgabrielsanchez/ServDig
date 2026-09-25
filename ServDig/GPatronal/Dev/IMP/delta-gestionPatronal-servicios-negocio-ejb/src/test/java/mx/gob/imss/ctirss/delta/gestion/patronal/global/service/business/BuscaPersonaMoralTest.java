package mx.gob.imss.ctirss.delta.gestion.patronal.global.service.business;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.SystemPropertyUtils;

import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ComparacionSinDiferenciasException;
import mx.gob.imss.ctirss.delta.exception.individuo.DatosInsuficientesICAException;
import mx.gob.imss.ctirss.delta.exception.individuo.DiferenciasRENAPOContraSAT;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.AfiliacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rule.RuleServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.test.EJBLocator;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.enums.ErroresModificacionPersonaEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosConsulta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteMoral;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;
import org.junit.Test;

public class BuscaPersonaMoralTest {
	private static final Logger LOG;

	static {
		LOG = LoggerFactory.getLogger(BuscaPersonaMoralTest.class);
	}

	private static final String KEY_RESPUESTA = "respuesta";
	private static final String KEY_TRAMITE_MOSTRAR = "tramiteMostrar";
	private static final String KEY_MSG_ERROR = "msgError";
	private static final String MOSTRAR_AP_ICA = "wizardAltaPatronalICA";
	private static final String MOSTRAR_AP_ICA_MORAL = "wizardAltaPatronalICAMoral";
	
	
	
	@Test
	public void validacionesParaCrearSolPMAlta() {
		LOG.debug(":: Inicio validacionesParaCrearSolPMAlta - " + new Date());
		//5237456
		Long idPM = new Long("5237456"); // walmart stage 3794562, walmart produccion 588596
		boolean anterior = false;
		Persona persona = new Persona();
		persona.setIdPersona(idPM);
		persona.setTipoPersona(new TipoPersona());
		persona.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);						

		ICADatosRespuesta icaDatosRespuesta = null;
		// Objeto para la forma auxiliar para invocar al servicio del ICA
		ICADatosConsulta icaDatosConsulta = new ICADatosConsulta();
		boolean isFisica = true;
		Moral objMoralRecuperado = null;
		
		try {
		ServiciosPersonaBusinessRemote ejb = EJBLocator.getServiciosPersonaBusiness();
		PersonaMoralBusinessRemote ejb2 = EJBLocator.getPersonaMoralBusiness();
		SolicitudPersonaBusinessRemote ejb3 = EJBLocator.getSolicitudPersonaBusinessRemote();
		LOG.debug("::: Obtuve servicios");
		
//		Long idOrigen =  new CommonValidator().getOrigenContext(request);
			try {
				// se setean los datos requeridos para hacer la consulta de
							// informaci�n completa
				icaDatosConsulta.setIndicadorConsultaSAT(Boolean.TRUE);
				icaDatosConsulta.setIndicadorMostrarPantalla(Boolean.TRUE);
				LOG.debug("::: Voy a buscar a la PM :" + persona.getIdPersona());
				if(anterior) {
					LOG.debug("::: Metodo anterior");
					objMoralRecuperado = ejb
						.buscarPersonaMoralyDPyDyMCEnIMSS(persona.getIdPersona());
					
				}else {
					LOG.debug("::: Metodo nuevo");
					objMoralRecuperado = ejb.buscarPMyDPyDyMCEnIMSS(persona.getIdPersona());
					LOG.debug("::: Pase la busqueda");
				}
				
				isFisica = false;
				persona.setRfc(objMoralRecuperado.getRfc());
				icaDatosConsulta.setPersonaMoral(objMoralRecuperado);
				if(anterior) {
					LOG.debug("::: Identificando cambios anterior");
					icaDatosRespuesta = ejb2.identificarCambios(icaDatosConsulta);
				}else {
					LOG.debug("::: Identificando cambios nuevo");
					icaDatosRespuesta = ejb2.identificarCambios_AP(icaDatosConsulta);
					LOG.debug("::: Pase identificar cambios nuevo");					
				}
			} catch (ComparacionSinDiferenciasException e1) {
				System.out.println(e1);
				icaDatosRespuesta = new ICADatosRespuesta();
				icaDatosRespuesta.setErrorFormGeneral(e1.getMessage());
				Map<String, String> mensajes = new HashMap<String, String>();
				mensajes.put(
						ErroresModificacionPersonaEnum.COMPARACION_SIN_DIFERENCIAS
								.getCodigo(), e1.getMessage());
				icaDatosRespuesta.setTraza(mensajes);
			}
			// Se obtienen los tramites de cambio de datos generales en caso de
			// existir cambios
			boolean existeDiferenciasIca = ejb3.existenDiferencias(icaDatosRespuesta.getCambios());

			if (existeDiferenciasIca) {
				/*TSe cambia la validacion para diferencias solo valida que no sea de nombre o razon social para persona moral
				 * se ser asi no continua el flujo y arroja una excepcion**/
				if(!isFisica  ){
					if(icaDatosRespuesta.getTraza().get("MSG_DIF_RFC") != null){
						icaDatosRespuesta.setErrorFormGeneral("La informaci�n de la Persona Moral presenta diferencias entre el IMSS y la entidades externa SAT en el RFC, acuda a la subdelegaci�n correspondiente para regularizar esta informaci�n");

					}
				}
			} else {
				icaDatosRespuesta = new ICADatosRespuesta();
				ComparacionSinDiferenciasException e1 = new ComparacionSinDiferenciasException();
				icaDatosRespuesta.setErrorFormGeneral(e1.getMessage());
				Map<String, String> mensajes = new HashMap<String, String>();
				mensajes.put(ErroresModificacionPersonaEnum.COMPARACION_SIN_DIFERENCIAS
								.getCodigo(), e1.getMessage());
				icaDatosRespuesta.setTraza(mensajes);
			}
			
		} catch (PersonaNoEncontradaException e) {
			e.printStackTrace();
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.PERSONA_NO_ENCONTRADA
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);

		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			e.printStackTrace();
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.ERROR_VALIDAR_DATOS_ENTIDAD_EXTERNA
				.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			e.printStackTrace();
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.RFC_NO_LOCALIZADO
				.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);

		} catch (DatosInsuficientesICAException e) {
			e.printStackTrace();
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.DATOS_INSUFICIENTES_ICA
				.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);


		} catch (ClienteWebserviceSatRfcException e) {
			e.printStackTrace();
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.ERROR_CONSULTA_SAT
				.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} 


			LOG.debug("::Iterando resultados:");
			for (Map.Entry<String,String> entry : icaDatosRespuesta.getTraza().entrySet()) {
			    System.out.println("Key = " + entry.getKey() + 
			                     ", Value = " + entry.getValue()); 
			}

		System.out.println(":::: Obtuve respuesta, icaDatosRespuesta");
		//System.out.println(icaDatosRespuesta);
			
		LOG.debug("::: FIN - " + new Date());

	}
	
	
	@Test
	public void validaPersonaParaAlta() {
		
		LOG.debug("Inicio validaPersonaParaAlta::" + new Date());
		Long cveIdPersona = new Long(5237456); // walmart stage 3794562, walmart produccion 588596
		boolean anterior = false;

		Map<String, Object> result = new HashMap<String, Object>();
		Integer validaMoralActa;
		Integer validaMoralSindicato;
		Moral patronPersonaMoral = null;

		try{
			SujetoObligadoServiceBusinessRemote service = EjbLocator.getSujetoObligadoServiceBusiness();
			ServiciosPersonaBusinessRemote ejb = EJBLocator.getServiciosPersonaBusiness();
			
			PersonaMoralBusinessRemote ejb2 = EJBLocator.getPersonaMoralBusiness();
			RuleServiceBusinessRemote ejb3 = EJBLocator.getRulesService();
			LOG.debug("Obtuve servicios ::" + new Date());
			
				//Para Internet primero se selecciona a quien se representara, es decir,
				//a nombre de que patron persona moral se realizara el tramite.
			service.validaRepresentanteLegalExistente(cveIdPersona, TipoPersonaEnum.MORAL.getId());
			if(anterior) {
				LOG.debug("::: Voy a buscar a la PM metodo anterior:" + cveIdPersona);
				patronPersonaMoral = ejb.buscarPersonaMoralyDPyDyMCEnIMSS(cveIdPersona);
			}else {
				LOG.debug("::: Voy a buscar a la PM metodo nuevo:" + cveIdPersona);
				patronPersonaMoral = ejb.buscarPMyDPyDyMCEnIMSS(cveIdPersona);	
			}
			LOG.debug("::Obteniendo acta");
			validaMoralActa = ejb2.consultaActaConstitutivaPersonaMoral(cveIdPersona);
			if (validaMoralActa == 0) {
				LOG.debug("::Consultando sindicato");
				validaMoralSindicato= ejb2.consultaSindicatoPersonaMoral(cveIdPersona);
				if (validaMoralSindicato > 0) {
					//ruleServiceBusiness.validarFraccionesConsistentesPorPatron(patronPersonaMoral.getRfc());
					//Regresa registro y se manda parametro 0 para seguir con Alta
					result.put(KEY_RESPUESTA, 0);
				} else {
					//No regresa registro y se manda parametro 1 para mostrar mensaje de error
					result.put(KEY_RESPUESTA, 1);
					//TramiteMostrar, faltan Datos Generales (TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES.getCodigo())
					result.put(KEY_TRAMITE_MOSTRAR, TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES.getCodigo());
					result.put(KEY_MSG_ERROR, "Usted no cuenta con la informaci&oacute;n requerida para realizar un alta patronal, por favor realice el tr&aacute;mite de actualizaci&oacute;n de datos particulares para completar la informaci&oacute;n referente a la constituci&oacute;n de la empresa.");
					System.out.println("Usted no cuenta con la informaci&oacute;n requerida para realizar un alta patronal, por favor realice el tr&aacute;mite de actualizaci&oacute;n de datos particulares para completar la informaci&oacute;n referente a la constituci&oacute;n de la empresa.");
				}
			} else {
				//Regresa registro y se manda parametro 0 para seguir con Alta
				//ruleServiceBusiness.validarFraccionesConsistentesPorPatron(patronPersonaMoral.getRfc());
				result.put(KEY_RESPUESTA, 0);
			}
			LOG.debug("::Validando socios");
			ejb3.validarSociosRequeridos(patronPersonaMoral.getIdPersona());
			
		}catch(GestionPatronalBusinessException gpbe){
			LOG.debug("::Entre a Exception");
			result.put(KEY_RESPUESTA, 1);
			result.put(KEY_MSG_ERROR, gpbe.getMessage());
			//Se agrega key tramiteMostrar para evaluar el valor en la vista
			result.put(KEY_TRAMITE_MOSTRAR, gpbe.getCodigo());
		} 	
		
		LOG.debug("::Iterando resultados");
		for (Map.Entry<String,Object> entry : result.entrySet()) {
            System.out.println("Key = " + entry.getKey() + 
                             ", Value = " + entry.getValue()); 
		}
		
		//System.out.println(patronPersonaMoral);
		
		LOG.debug("::FIN - " + new Date());
	}
	
	
	//////////////////////////////////////////////////////////////////
	//Metodo nuevo que se implemento en PortalController
	//////////////////////////////////////////////////////////////////

	@Test 
	public void buscarPMyDPyDyMCEnIMSS(){
		//5237456
		Long idPM = new Long("588596"); // walmart stage 3794562, walmart produccion 588596
		//3743625
		try {
			LOG.debug("::: Obteniendo EJB");
			ServiciosPersonaBusinessRemote ejb = EJBLocator.getServiciosPersonaBusiness();
			LOG.debug("::: Buscando a la persona " + idPM + ", " + new Date());
			Moral personaMoral = ejb.buscarPMyDPyDyMCEnIMSS(idPM);
			LOG.debug("::: IdPersona; " + personaMoral.getIdPersona());
			LOG.debug("::: Persona obtenida, " + new Date());
			System.out.println(personaMoral.toString());
			
			
			//para validar los campos que usa el portal
			
			System.out.println("----------------------------------------");
			System.out.println("personaMoral.getRfc(): " + personaMoral.getRfc());
			System.out.println("personaMoral.getRazonSocial(): " + personaMoral.getRazonSocial());
			String nombreCompleto = personaMoral.getRazonSocial();
			System.out.println("sustituyo " + nombreCompleto + " por  " + nombreCompleto.replace("\n", " ").replace("\"", "\\\""));
			System.out.println(nombreCompleto.replace("\n", " ").replace("\"", "\\\""));
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		LOG.debug("::: Fin");
	}	
	

	
	//Metodo anterior en PortalController que tarda mucho
//	@Test 
	public void buscarPersonaMoralyDPyDyMCEnIMSS(){
		Long idPM = new Long("588596"); // walmart stage 3794562, walmart produccion 588596
		try {
			LOG.debug("::: Obteniendo EJB: " + new Date());
			ServiciosPersonaBusinessRemote ejb = EJBLocator.getServiciosPersonaBusiness();
			LOG.debug("::: Buscando a la persona " + idPM + ", " + new Date());
			Moral pm = ejb.buscarPersonaMoralyDPyDyMCEnIMSS(idPM);
			LOG.debug("::: IdPersona; " + pm.getIdPersona());
			LOG.debug("::: Persona obtenida, " + new Date());
			System.out.println(pm.toString());
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		LOG.debug("::: Fin - " + new Date());
	}
	
	
	//Metodo sencillo que busca datos basicos y fiscales
	//No tarda
//	@Test 
	public void obtenerDatosFiscalesPersona(){
		Long idPM = new Long("588596"); // walmart stage 3794562, walmart produccion 588596
		try {
			LOG.debug("::: Obteniendo EJB en obtenerDatosFiscalesPersona");
			AfiliacionServiceBusinessRemote ejb = EJBLocator.getAfiliacionServiceBusiness();
			LOG.debug("::: Buscando a la persona " + idPM + ", " + new Date());
			Persona p = new Persona();
			p.setIdPersona(idPM);
			p.setTipoPersona(new TipoPersona());
			p.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);						
			SujetoObligado so = ejb.obtenerDatosFiscales(p);
			LOG.debug("::: Persona obtenida, " + new Date());
			Moral pm = so.getMoral();
			LOG.debug("::: IdPersona; " + pm.getIdPersona());
			System.out.println(pm.toString());
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		LOG.debug("::: Fin");
	}	
	
	
}
