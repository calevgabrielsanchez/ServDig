package mx.gob.imss.ctirss.delta.gestion.patronal.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.exception.individuo.ComparacionSinDiferenciasException;
import mx.gob.imss.ctirss.delta.exception.individuo.DatosInsuficientesICAException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.model.enums.CambioComparacionEnum;
import mx.gob.imss.ctirss.delta.model.enums.ErroresModificacionPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosConsulta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EscrituraConstitutiva;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;


public class ConsultaICATest {
	
	
	private static final Logger log;

	static {
		log = LoggerFactory.getLogger(ConsultaICATest.class);
	}

//	@Test
	public void identificarDiferenciasTramiteActDeDatos() {
		
    	ICADatosRespuesta icaDatosRespuesta = null;
		boolean anterior = false; //permite consultar los metodos anteriores y los nuevos

		ICADatosConsulta icaDatosConsulta = new ICADatosConsulta();
		icaDatosConsulta.setPersonaMoral(new Moral());
		icaDatosConsulta.getPersonaMoral().setCveMoral(new Long(588596)); // walmart stage 3794562, walmart produccion 588596
		icaDatosConsulta.getPersonaMoral().setRfc("NWM9709244W4"); //walmart stage OWM011023AWA, produccion NWM9709244W4
		
    	try {    		
    		
			System.out.println("::: Obteniendo cliente EJB para identificar cambios, identificarDiferenciasTramiteActDeDatos..." + new Date());
			PersonaMoralBusinessRemote ejb2 = EJBLocator.getPersonaMoralBusinessRemote();
    	
			/*
			 * Se pasa el atributo cveMoral al idPersona, ya que el servicio
			 * para identificar cambios espera el idPersona y no cveMoral
			 */
    		icaDatosConsulta.getPersonaMoral().setIdPersona(icaDatosConsulta.getPersonaMoral().getCveMoral());    		   		
			
    		if(anterior) {
        		icaDatosRespuesta = ejb2.identificarSoloCambios(icaDatosConsulta);	
    		}else {
        		//Cambio para obtener los datos de la PM - INC110489
    			icaDatosRespuesta = ejb2.identificarSoloCambios_AP(icaDatosConsulta);	
    		}
			
			log.debug("Para ver que en el flujo va los cambios, " +  icaDatosRespuesta.getCambios().get("tipoSociedad") + " - " +  icaDatosRespuesta.getCambios().get("nombreRazonSocial"));	

			boolean existenDiferencias = existenDiferencias(icaDatosRespuesta.getCambios());			
			
			if(icaDatosRespuesta.getPersonaMoralEE() != null && StringUtils.isNotBlank(icaDatosRespuesta.getPersonaMoralEE().getRazonSocial())) {
				//se escapan las comillas dobles
				String nombreConComillasEsc = icaDatosRespuesta.getPersonaMoralEE().getRazonSocial().replace("\"", "\\\"");
				icaDatosRespuesta.getPersonaMoralEE().setRazonSocial(nombreConComillasEsc);
			} 
			
			if(!existenDiferencias){
				System.out.println("::: NO hay diferencias");
				icaDatosRespuesta.setErrorFormGeneral("No existen diferencias entre la informaci�n registrada en IMSS, SAT y RENAPO.");
				Map<String, String> mensajes = new HashMap<String, String>();
				mensajes.put(ErroresModificacionPersonaEnum.COMPARACION_SIN_DIFERENCIAS
						.getCodigo(), "No existen diferencias entre la informaci�n registrada en IMSS, SAT y RENAPO.");
				icaDatosRespuesta.setTraza(mensajes);
				
				Moral persona=icaDatosRespuesta.getPersonaMoralIMSS();
				
				agregarCambioDeDatosComplementarios(icaDatosRespuesta);
			}
			/* se cambia la validacon para que se aplique el ICA solo si los datos del nombre y razon social son correctos **/
			else{
				if(icaDatosRespuesta.getTraza().get("MSG_DIF_RFC") != null){
					System.out.println("::: Hay diferencias");
					icaDatosRespuesta.setErrorFormGeneral("La informaci�n de la Persona Moral presenta diferencias entre el IMSS y la entidades externa SAT en el RFC, acuda a la subdelegaci�n correspondiente para regularizar esta informaci�n");
				}else {
					System.out.println("::: NO Hay diferencias  MSG_DIF_RFC");
				}
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
		} catch(DatosInsuficientesICAException e){
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
		
    	System.out.println("::: Respuesta obtenida..");
    	//System.out.println(icaDatosRespuesta);
    	System.out.println("::: FIN " + new Date());
	}
	
	
	
	
	//Metodo para obtener diferencias ICA con metodos optimizados y anteriores
	@Test
	public void identificarDiferenciasPM() {
		
		ICADatosConsulta icaDatosConsulta = new ICADatosConsulta();
		ICADatosRespuesta icaDatosRespuesta = new ICADatosRespuesta();
		Moral objMoralRecuperado = new Moral();
		Long idPM = new Long("588596"); // walmart stage 3794562, walmart produccion 588596
		boolean anterior = false; //permite consultar los metodos anteriores y los nuevos
		
		try {
			System.out.println("::: Comenzando proceso, identificarDiferenciasPM: "  + new Date());
			System.out.println("::: Obteniendo cliente EJB para consultar PM...");
			ServiciosPersonaBusinessRemote ejb = EJBLocator.getServiciosPersonaBusiness();
			// se setean los datos requeridos para hacer la consulta de
						// informaci�n completa
			icaDatosConsulta.setIndicadorConsultaSAT(Boolean.TRUE);
			icaDatosConsulta.setIndicadorMostrarPantalla(Boolean.TRUE);

			System.out.println("::: Consultando, persona moral: " + idPM);
			if(anterior) {
				//metodo anterior para buscar la PM
				System.out.println("::: Consultando por metodo anterior");
				objMoralRecuperado = ejb.buscarPersonaMoralyDPyDyMCEnIMSS(idPM);
			}else {
				//metodo nuevo para buscar la PM
				System.out.println("::: Consultando por metodo actual");
				objMoralRecuperado = ejb.buscarPMyDPyDyMCEnIMSS(idPM);				
			}
			System.out.println("::: Obteniendo cliente EJB para identificar cambios...");
			PersonaMoralBusinessRemote ejb2 = EJBLocator.getPersonaMoralBusinessRemote();
			icaDatosConsulta.setPersonaMoral(objMoralRecuperado);
			
			try {
				System.out.println("::: Identificando cambios, " + new Date());
				if(anterior) {
					icaDatosRespuesta = ejb2.identificarCambios(icaDatosConsulta);
				}else {
					icaDatosRespuesta = ejb2.identificarCambios_AP(icaDatosConsulta);
				}
				
			} catch (ComparacionSinDiferenciasException e1) {
				System.out.println(":::: ComparacionSinDiferenciasException, no hay diferencias");
				System.err.println(e1.getMessage());
			} 			

			SolicitudPersonaBusinessRemote ejb3 = EJBLocator.getSolicitudPersonaBusinessRemote();
			boolean existeDiferenciasIca = ejb3.existenDiferencias(icaDatosRespuesta.getCambios());
			if (!existeDiferenciasIca) {
				System.out.println("::: NO existen diferencias en ICA");
				icaDatosRespuesta = new ICADatosRespuesta();
				ComparacionSinDiferenciasException e1 = new ComparacionSinDiferenciasException();
				icaDatosRespuesta.setErrorFormGeneral(e1.getMessage());
				Map<String, String> mensajes = new HashMap<String, String>();
				mensajes.put(ErroresModificacionPersonaEnum.COMPARACION_SIN_DIFERENCIAS
								.getCodigo(), e1.getMessage());
				icaDatosRespuesta.setTraza(mensajes);
			}else {
				System.out.println("::: SI existen diferencias en ICA");
			}

			System.out.println("::: Respuesta obtenida, " + new Date());
			//System.out.println(icaDatosRespuesta);

			
		} catch (PersonaNoEncontradaException e) {
			e.printStackTrace();
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			e.printStackTrace();
		} catch (ClienteWebserviceSatRfcException e) {
			e.printStackTrace();
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			e.printStackTrace();
		} catch (DatosInsuficientesICAException e) {
			e.printStackTrace();
		}
				
		System.out.println("::: FIN de proceso: "  + new Date());
	}
	
	
//	@Test
	public void consultaDatosICA(){
		ICADatosConsulta icaDatosConsulta = new ICADatosConsulta();
		boolean anterior = false;
		Moral pm = new Moral();
		pm.setIdPersona(new Long("4604201"));
		pm.setRfc("CAC1705248M1");
		icaDatosConsulta.setPersonaMoral(pm);
		try {
			PersonaMoralBusinessRemote pmBs = EJBLocator.getPersonaMoralBusinessRemote();	
			ICADatosRespuesta icaDatosRespuesta = null;
			log.debug(":::: Obtuve EJB ....");
			if(anterior) {
				System.out.println("::: Consultando por metodo anterior");
				icaDatosRespuesta = pmBs.identificarCambios(icaDatosConsulta);
			}else {
				System.out.println("::: Consultando por metodo actual");
				icaDatosRespuesta = pmBs.identificarCambios_AP(icaDatosConsulta);				
			}
			log.debug(":::: Obtuve datos ICA");
			log.debug(icaDatosRespuesta.toString());
		} catch (PersonaNoEncontradaException e) {
			e.printStackTrace();
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			e.printStackTrace();
		} catch (ClienteWebserviceSatRfcException e) {
			e.printStackTrace();
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			e.printStackTrace();
		} catch (ComparacionSinDiferenciasException e) {
			e.printStackTrace();
		} catch (DatosInsuficientesICAException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug("Finaliza ejecuci�n");
	}

	public void agregarCambioDeDatosComplementarios(ICADatosRespuesta datosRespuesta){
		datosRespuesta.getCambios().put("datosComplementarios", CambioComparacionEnum.CAMBIO);
		Moral persona=datosRespuesta.getPersonaMoralIMSS();
		
		if(datosRespuesta.getCambios()==null)
			datosRespuesta.setCambios(new HashMap<String, CambioComparacionEnum>());
						
		if(persona.getEscrituraConstitutiva()!=null){
			datosRespuesta.getPersonaMoralEE().setEscrituraConstitutiva(persona.getEscrituraConstitutiva());
			datosRespuesta.getPersonaMoralIMSS().setEscrituraConstitutiva(persona.getEscrituraConstitutiva());
			datosRespuesta.getPersonaMoralIMSS().setRegistroSindicato(null);
			datosRespuesta.getPersonaMoralEE().setRegistroSindicato(null);
			datosRespuesta.getCambios().put("actaConstitutiva", CambioComparacionEnum.CAMBIO);
			datosRespuesta.getCambios().remove("registroSindicato");
			
		}else if(persona.getRegistroSindicato()!=null){
			datosRespuesta.getPersonaMoralIMSS().setRegistroSindicato(persona.getRegistroSindicato());
			datosRespuesta.getPersonaMoralEE().setRegistroSindicato(persona.getRegistroSindicato());
			datosRespuesta.getPersonaMoralEE().setEscrituraConstitutiva(null);
			datosRespuesta.getPersonaMoralIMSS().setEscrituraConstitutiva(null);
			datosRespuesta.getCambios().put("registroSindicato", CambioComparacionEnum.CAMBIO);
			datosRespuesta.getCambios().remove("actaConstitutiva");
			
		}else if(persona.getRegistroSindicato()==null && persona.getEscrituraConstitutiva()==null){
			datosRespuesta.getPersonaMoralEE().setEscrituraConstitutiva(new EscrituraConstitutiva());
			datosRespuesta.getPersonaMoralIMSS().setEscrituraConstitutiva(new EscrituraConstitutiva());
			datosRespuesta.getPersonaMoralIMSS().setRegistroSindicato(null);
			datosRespuesta.getPersonaMoralEE().setRegistroSindicato(null);
			datosRespuesta.getCambios().put("actaConstitutiva", CambioComparacionEnum.CAMBIO);
			datosRespuesta.getCambios().remove("registroSindicato");
			
		}
	}
	
	public boolean existenDiferencias(
			Map<String, CambioComparacionEnum> diferencias) {
		
		boolean existenDiferencias = false;
		
		if(diferencias != null && !diferencias.isEmpty()){
			for (Entry<String, CambioComparacionEnum> entry : diferencias.entrySet()) {
			    if(fueCambio(entry.getValue())) {
			    	existenDiferencias = true;
			    	break;
			    }
			}
		}
		
		return existenDiferencias;
		
	}
	
	private boolean fueCambio(CambioComparacionEnum cambio) {

		boolean fueCambio = false;

		if (cambio.getId().longValue() == CambioComparacionEnum.CAMBIO.getId()
				.longValue()
				|| cambio.getId().longValue() == CambioComparacionEnum.NUEVO
						.getId().longValue()) {
			fueCambio = true;
		}

		return fueCambio;
	}
	
}
