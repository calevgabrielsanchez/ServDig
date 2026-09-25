package mx.gob.imss.ctirss.delta.gestion.patronal.service.business;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.IndividuoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rep.legal.RepresentanteLegalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosConsulta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;

public class ActualizarDatosGeneralesTest {
    private static final Logger log = LoggerFactory.getLogger(ActualizarDatosGeneralesTest.class);

    private IndividuoServiceBusinessRemote individuoServiceBusiness;
    private PersonaBusinessRemote personaBusiness;
    private RepresentanteLegalServiceBusinessRemote representanteLegalServiceBusinessRemote;

    @Before
    public void before() throws NamingException {
    	individuoServiceBusiness = EjbLocator.getIndividuoServiceBusiness();
        log.debug("::: Obtuve servicio EJB: {}", individuoServiceBusiness);
        personaBusiness = EjbLocator.getPersonaBusinessRemote();
        log.debug("::: Obtuve servicio EJB: {}", personaBusiness);
        representanteLegalServiceBusinessRemote = EjbLocator.getRLService();
        log.debug("::: Obtuve servicio EJB: {}", representanteLegalServiceBusinessRemote);
    }

    
	@Test
	public void initActualizarDatosPersona() {

		log.debug(":::INiciando... ");
	
		Integer idTipoPersona = new Integer("2");
		Long idPersona = new Long("148002");
		
		String curp = "";
		String rfc = "";
		boolean consultaRenapo = true;
		boolean consultaSat = true;
		
		// Objeto para la forma auxiliar para invocar al servicio del ICA
		ICADatosConsulta datosEntrada = new ICADatosConsulta();
		if(idTipoPersona.longValue() == TipoPersonaEnum.FISICA.getId()) {
			System.out.println(":::Tipo de persona fisica");
			Fisica fisica = new Fisica();
			fisica.setIdPersona(idPersona);			
			fisica.setCurp(curp);
			fisica.setRfc(rfc);
			datosEntrada.setPersonaFisica(fisica);
			
			if (consultaRenapo) {
				datosEntrada.setIndicadorConsultaRENAPO(Boolean.TRUE);
			} else {
				datosEntrada.setIndicadorConsultaRENAPO(Boolean.FALSE);
			}

			if (consultaSat) {
				datosEntrada.setIndicadorConsultaSAT(Boolean.TRUE);
			} else {
				datosEntrada.setIndicadorConsultaSAT(Boolean.FALSE);
			}
			
			datosEntrada.setIndicadorMostrarPantalla(Boolean.TRUE);
			
			System.out.println("wizardActualizarDatosFisicaInit");			
		} else {
			System.out.println(":::Tipo de persona moral");
			Moral moral = new Moral();
			moral.setCveMoral(idPersona);			
			moral.setRfc(rfc);
			datosEntrada.setPersonaMoral(moral);
			/*
			 * Siempre se manda TRUE a la consulta del SAT sin importar el
			 * indicador recibido, ya que para persona moral es la �nica entidad
			 * externa que se consulta
			 */
			datosEntrada.setIndicadorConsultaSAT(Boolean.TRUE);
			datosEntrada.setIndicadorMostrarPantalla(Boolean.TRUE);			
			System.out.println("wizardActualizarDatosMoralInit");			

		}

		// Se busca si existen solicitudes en proceso pendientes
		boolean existeSolRegistrada = false;
		boolean existeSolProceso = false;
		boolean solicitudMismoOrigen = false;
		Solicitud solicitudActiva = null;
		
		try {
//			Fisica personaSesion = null;
//			if (idPersonaSesion != null && idPersonaSesion > 0) {
//				personaSesion = serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(idPersonaSesion);
//				session.setAttribute(KEY_RFC_PERSONA_SESION, personaSesion.getRfc());
//			}
//			session.setAttribute(DATOS_PERSONA_SESION_KEY, personaSesion);
			
			
			log.debug(":::Recupere EJB ");
			
			SolicitudPersonaBusinessRemote ejb = EjbLocator.getSolicitudPersonaBusinessRemote();
			
			solicitudActiva = ejb.obtenerSolicitudRegistrada(
					idPersona, idTipoPersona.longValue(),TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES,
					TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES);
		
			if (solicitudActiva != null && solicitudActiva.getSolicitudId() != null) {
				existeSolRegistrada = true;
				solicitudMismoOrigen = validarSolicitudMismoOrigen(solicitudActiva, new Long(2));
			} else {
				solicitudActiva = ejb.obtenerSolicitudEnProceso(
						idPersona, idTipoPersona.longValue(),TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES,
						TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES);
				
				if (solicitudActiva != null && solicitudActiva.getSolicitudId() != null) {
					existeSolProceso = true;
					System.out.println("existeSolProceso: " + solicitudActiva.getOrigenSolicitud().getDescripcion());
				} else {
					solicitudActiva = new Solicitud();
				}
			}
		} catch (SolicitudException e) {
			e.printStackTrace();
		}
        
        String idPersonaInteresadaSol;
		/*
		 * Validaciones para saber si el tramite de actualizacion fue iniciado
		 * por la misma persona
		 */
//        if(existeSolRegistrada || existeSolProceso) {
//        	log.debug("::: Existe una solicitud en proceso o registradas");
//        	PersonaInteresadaSolicitud perInt = solicitudActiva.getPersonaInteresadaSolicitud();
//        	if(perInt != null) {
//        		log.debug("la persona interesada no es nula");
//				Long idPersonaInteresada = perInt.getPersona().getIdPersona();
//				log.debug("el id de la persona interesada pasada como parametro es: "
//						+ idPersonaInteresadaSol
//						+ "el id de la persona interesada sol es: "
//						+ idPersonaInteresadaSol);
//				if(!idPersonaInteresada.equals(idPersonaInteresadaSol)) {
//					tramiteIniciadoPorOtraPersona = true;
//				}
//			} else {
//				log.debug("la solicitud no cuenta con persona interesada");
//				if(!idPersona.equals(idPersonaInteresadaSol)){
//					tramiteIniciadoPorOtraPersona = true;
//				}
//			}
//        }

		//SETEO DE PROPIEDADES DEL USUARIO QUE REALIZA EL TRAMITE
//		WizardMediosParticularesController mediosController = new WizardMediosParticularesController();
//		mediosController.putUsuarioSesionbyUsuarioSSOonRequest(request);		
//		model.addAttribute("solicitudForm", solicitudActiva);
//		request.setAttribute("existeSolRegistrada", existeSolRegistrada);
//		request.setAttribute("existeSolProceso", existeSolProceso);
//		request.setAttribute("tramiteIniciadoPorOtraPersona", tramiteIniciadoPorOtraPersona);
//		request.setAttribute(KEY_SOLICITUD_MISMO_ORIGEN, solicitudMismoOrigen);
//		model.addAttribute("icaDatosEntrada", datosEntrada);
//		request.setAttribute("PEDIR_CURP", pedirCURP);
//		request.setAttribute("PEDIR_RFC", pedirRFC);
//		session.setAttribute(KEY_TIPO_SOLICITUD, TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES.getValor());
//		session.setAttribute(KEY_DESC_TIPO_SOLICITUD, DESC_TIPO_SOLICITUD);
//		session.setAttribute(KEY_PERSONA_INTERESADA_SESSION, idPersonaInteresadaSol);
//		List<Integer> listTipoTramite = new ArrayList<Integer>();
//		listTipoTramite.add(TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES.getCodigo());
//		session.setAttribute(KEY_TIPO_TRAMITE, listTipoTramite);
//
//		return view;

	}    
    
    
    
	public boolean validarSolicitudMismoOrigen(Solicitud solicitud, Long origenSolicitud){
		boolean solicitudMismoOrigen = false;
		if(solicitud.getOrigenSolicitud().getIdTipoSolicitud().equals(origenSolicitud)){
			solicitudMismoOrigen = true;
		}
		return solicitudMismoOrigen;		
	}
	

}

