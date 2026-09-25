package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.GrupoFamiliarDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.UmfCodigoPostalDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.entity.TramiteModalidadEntityLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.RequisitosMinimosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.util.DateUtils;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.CalidadParentescoParser;
import mx.gob.imss.ctirss.delta.derechohabientes.ws.bussiness.DerechohabienteWSClientRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesWebSserviceException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.DatosInsuficientesParaConsultaException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudTramiteBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoBajaDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.BajaDerechohabienteDto;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.RazonRegistroEnum;
import mx.gob.imss.ctirss.delta.model.enums.RazonResultadoEnum;
import mx.gob.imss.ctirss.delta.model.enums.SexoEnum;
import mx.gob.imss.ctirss.delta.model.enums.SubestadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.VarianteRegistroEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaDomicilio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;
import mx.gob.imss.ctirss.delta.model.util.Constants;

@Stateless(name = "requisitosMinimosService", mappedName = "requisitosMinimosService")
public class RequisitosMinimosService extends AbstractServiceBusiness implements RequisitosMinimosServiceRemote,RequisitosMinimosServiceLocal{

	//EJBS locales
	@EJB
	private GrupoFamiliarDaoLocal grupoFamiliarDaoLocal;
	@EJB
	private TramiteModalidadEntityLocal tramiteModalidadEntityLocal;
	@EJB
	private UmfCodigoPostalDaoLocal umfCodigoPostalDaoLocal;
	@EJB
	private GrupoFamiliarServiceLocal grupoFamiliarServiceLocal;
	@EJB
	private BajaDerechohabienteServiceLocal bajaDerechohabienteServiceLocal;
	
	
	//EJBS de otros proyectos
	@EJB(name="derechohabienteWSClientService" ,mappedName="derechohabienteWSClientService") 
	private DerechohabienteWSClientRemote derechohabienteWSClientRemote;
	@EJB(name = "solicitudTramiteBusiness", mappedName = "solicitudTramiteBusiness")
	private SolicitudTramiteBusinessRemote solicitudTramiteBusinessRemote;
	@EJB(name="sujetoObligadoServiceBusiness" ,mappedName="sujetoObligadoServiceBusiness") 
	private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusinessRemote;
	@EJB(name = "personaFisicaServiceBusiness", mappedName = "personaFisicaServiceBusiness")
	private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusinessRemote;
	
	
	@EJB(name = "localizarPersonaFisicaEnRENAPOServiceBusiness", mappedName = "localizarPersonaFisicaEnRENAPOServiceBusiness")
	private LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote localizarPersonaFisicaEnRENAPOServiceBusiness;
	
	
	
	//Variables estaticas
	private static final String ESTATUS_RENAPO_INVALIDO = "Invalido";
	private static final String KEY_ESTADO_REQUISITOS = "correcto";
	private static final String KEY_MENSAJE_REQUISITOS = "mensaje";
	private static final String KEY_CONCUBINA_REGISTRADA = "concubinaRegistrada";
	public static final long NUMERO_MAXIMO_HIJOS = 30;
	public static final long NUMERO_MAXIMO_PADRES = 2;
	public static final long NUMERO_MAXIMO_CONYUGE = 1;
	private static final String ERROR_DE_DATOS_BUSQUEDA_PERSONAS= "Los datos personales del beneficiario presentan inconsistencias, favor de acudir a la Subdelegaci�n m�s cercana para corregir o actualizar su informaci�n."; 
	private static final String MODALIDAD_BECARIO = "37";
	
	/**
	 * Metodo para hacer las validaciones de si se permite o no modificacion e domicilio en el registro de derechohabiente
	 * dependiendo del parentesco, de la bandera de patron IMSS y de los roles con los que cuente la persona dentro del instituto
	 */
	@Override
	public Map<String, Object> validarEleccionDeDomicilioYUmfPorPersonaParentesco(Fisica fisica, Long idParentesco, Domicilio domicilioAsegurado,
			Boolean patronIMSS) 
		throws IllegalArgumentException{
		
		if(fisica == null || idParentesco == null) {
			throw new IllegalArgumentException("Los datos para las validaciones de domicilio y roles no estan completos");
		}
		//Objetos que contendran las validaciones
		Map<String, Object> result = new HashMap<String, Object>();
		String mensajeDomicilio = "";
		String mensajeUmf = "";
		Boolean setUmfAsegurado = true;
		Boolean permiteSeleccionUmf = false;
		Boolean setDomicilioAsegurado = true;
		Boolean permiteUbicarDomcilio = false;
		Boolean error = false;
		String mensajeError = "";
		PersonaDomicilio personaDomicilio = null;
		
		
		final String MENSAJE_DOMICILIO_ASEGURADO = "Para el parentesco a registrar no es posible tener un domicilio distinto al del asegurado / pensionado" +
		". Da clic en <strong>Aceptar</strong> para continuar.";
		final String MENSAJE_UMF_ASEGURADO = "Para el parentesco a registrar no es posible cambiar los datos de adscripci&oacute;n con los que ya cuenta el asegurado / pensionado." +
		" Por lo tanto estos ser&aacute;n los que ya se tiene asignados.";
		
		//Id de la persona a registrar
		Long idPersona = fisica.getIdPersona(); 
		log.debug("El id de la persona es: " + idPersona);
		//Verificamos si es asegurado pensionado
		boolean isAsegurado = idParentesco.equals(ParentescoEnum.ASEGURADO.getId()) || idParentesco.equals(ParentescoEnum.PENSIONADO.getId());
		//Verificamo si re registra a un padre
		boolean isPadre = idParentesco.equals(ParentescoEnum.PADRES.getId()) || idParentesco.equals(ParentescoEnum.MADRE.getId());
		//verificamos si se esta registrando a un concubina
		boolean isConcubina = idParentesco.equals(ParentescoEnum.CONCUBINA.getId()) || idParentesco.equals(ParentescoEnum.CONCUBINARIO.getId());
		log.debug("el parentesco a registrar es asegurado: " + isAsegurado +", es padre? " + isPadre + ", es concubina? " + isConcubina + ", tiene patron IMSS" + patronIMSS);
		//Verificamos si el id de la persona no viene nulo
		if(idPersona == null) {
			log.debug("la persona no cuenta con id");
			//En caso de que venga nulo verificaremos el parentesco para saber si puede o no seleccionado su propio domicilio
			//si el parenscto es asegurado o no es padre o concubina o si es padre pero el patron es IMSS le permitiremos que elija su domicilio
			permiteUbicarDomcilio = isAsegurado || (!isConcubina && !isPadre) || (isPadre && patronIMSS);
			//Como es persona nueva elegiremos el domicilio del asegurado por default
			setDomicilioAsegurado = true;
			//Si se permite el cambio de domicilio 
			if(permiteUbicarDomcilio) {
				log.debug("Se permite elegir el domicilio de la persona a registrar");
				//Permitiremos que eliga la umf que quiera
				permiteSeleccionUmf = true;
				//no estableceremos por default la umf del asegurado
				setUmfAsegurado = false;
			} else {
				log.debug("El domicilio de la persona sera el mismo que el del asegurado o pensionado");
				//Si no es posible modificar el domicilio mostramos el mensaje de domicilio
				mensajeDomicilio = MENSAJE_DOMICILIO_ASEGURADO;
				//Si no se permite el cambio de umf mostramos mensaje de umf
				mensajeUmf = MENSAJE_UMF_ASEGURADO;
				//Se seteara la umd del asegurado
				setUmfAsegurado = true;
				//no podra elegir otra UMF
				permiteSeleccionUmf = false;
			}
		} else{
			//El id de la persona no viene nulo
			if(!isAsegurado && domicilioAsegurado == null) {
				
				// -------------------------------------------------------------------------------
				// Cuando se registra un derechohabiente, si el asegurado no tiene domicilio
				// lanza un excepci�n y el JS no funciona
				// -------------------------------------------------------------------------------
				
				
				//throw new IllegalArgumentException("El domicilio del asegurado no esta seteado");
			}
			
			//TODO ya no se valida si es patron o rl
			/*
			//bandera para verificar si es patron
			boolean isPatron = false;
			//bandera para verificar si es representante legal
			boolean isRL = false;*/
			
			try {
				Boolean tieneDomicilio = false;
				//BBuscamos en BDTU el domicilio parrticular de la persona
				personaDomicilio = grupoFamiliarDaoLocal.getPersonaFDom(idPersona);
				//seteamos la bandera de si tiene domicilio o no
				tieneDomicilio = personaDomicilio != null && personaDomicilio.getCvePersonaDomicilio() != null;
				//si no tiene domicilio
				if(!tieneDomicilio) { //IF - No tiene domicilio -
					log.debug("No se encontro domicili para la persona: " + idPersona);
					//Establecemos el domicilio del asegurado
					setDomicilioAsegurado = true;
					//Si el parentesco no es ni concubina ni padre o si es padre y es patron IMSS se permite vivir donde sea
					permiteUbicarDomcilio = isAsegurado || (!isConcubina && !isPadre) || (isPadre && patronIMSS);
					//Velidamos si permite ubicar domicilio
					if(permiteUbicarDomcilio) {
						log.debug("Se permite elegir el domicilio de la persona a registrar");
						//No se seteara la UMF del asegurado ya que puede elegir el domicilio que quiera
						setUmfAsegurado = false;
						//Se permitira elegir la umf que sea de acuerdo al domicilio del asegurado
						permiteSeleccionUmf = true;
					} else {
						log.debug("El domicilio de la persona sera el mismo que el del asegurado o pensionado");
						//Se muestra mensaje de domicilio
						mensajeDomicilio = MENSAJE_DOMICILIO_ASEGURADO;
						//Se muestra mensaje de UMF
						mensajeUmf = MENSAJE_UMF_ASEGURADO;
						//Se setean los datos de adscripcion del asegurado
						setUmfAsegurado = true;
						//No permite elegir una UMF distinta a la del asegurado
						permiteSeleccionUmf = false;
					}
				} else { //else -tiene domicilio -
					log.debug("Se encontro al menos un domicilio particular para la persona");
					/*
					//Verificamos si es patron
					isPatron = grupoFamiliarServiceLocal.esPatron(idPersona);
					isRL = false
					log.debug("La persona a registrar es patron ? " + isPatron);
					//Si no es patron
					if(!isPatron) {
						//Verificamos si es representante legal
						isRL = grupoFamiliarServiceLocal.esRepresentanteLegal(idPersona);
						log.debug("la persona a registrar es Representante Legal? " + isRL);
					} 
					
					//Si es patron o represnetante legal
					if(isPatron || isRL) { //IF - es patron o represntante legaal
						log.debug("la persona es patron o representante legal");
						//Creamos el mensaje que se mostrara en pantalla de la eleccion de domicilio
						String rolEncontrado = isPatron ? "Patr&oacute;n" : "Representante Legal";
						mensajeDomicilio = "No es posible modificar el domicilio ya que la persona cuenta con el rol de <strong>"+rolEncontrado+"</strong>" +
						", por lo tanto el domicilio del integrante ser&aacute; el que ya se tiene asignado. De clic en <strong>Siguiente</strong> para continuar.";
						//No es posible cambiar el domicilio de la persona a registrar
						permiteUbicarDomcilio = false;
						//No seteamos el domicilio del asegurado
						setDomicilioAsegurado = false;
						
						if((!isConcubina && !isPadre) || (isPadre && patronIMSS)) { 
							log.debug("la persona a registrar no se registrara como concubina o padre no IMSS");
							//si no es concubina o padre no IMSS permite seleccionar la UMF
							permiteSeleccionUmf = true;
							//seteamos la UMF del asegurado
							setUmfAsegurado = false;
						} else {
							log.debug("La persona a registrar tiene el parentesco de concubina o padre no IMSS");
							permiteSeleccionUmf = false;
							setUmfAsegurado = true;
							
							Boolean mismoDomicilioAsegurado = this.validarDomicilioARegistrar(domicilioAsegurado, personaDomicilio.getDomicilio());
							if(!mismoDomicilioAsegurado) {
								log.debug("la persona no tiene el mismo domicilio que el asegurado");
								error = true;
								mensajeError = "El registro de esta persona no puede llevarse a cabo, ya que el domicilio no puede ser modificado y la persona " +
										"tiene el rol de <strong>" + rolEncontrado+"</strong> y el parentesco" +
										" no permite que la persona tenga un domicilio diferente al del asegurado / pensionado.";
							}
						}
					}//fin IF - es patron o representante legal
					else { //else - no es patron ni representante legal*/
						log.debug("la persona no es patron ni representante legal");
						//Si no es patron o RL setearemos 
						//Si el parentesco no es ni concubina ni padre o si es padre y es patron IMSS se permite vivir donde sea
						permiteUbicarDomcilio = isAsegurado || (!isConcubina && !isPadre) || (isPadre && patronIMSS);
						//si no permite ubicar un domicili distinto se seteara el domicili del asegurado
						setDomicilioAsegurado = !permiteUbicarDomcilio;
						//mostramos el mensaje del mismo domicilio del domicilio
						if(setDomicilioAsegurado) {
							mensajeDomicilio = MENSAJE_DOMICILIO_ASEGURADO;
						}
						//Si usamos el domicilio sel asegurado seleccionaremos la umf que tenga el segurado
						setUmfAsegurado = setDomicilioAsegurado;
						//mostramos el mensaje, en caso de que se tenga que setear la misma umf del asegurado
						if(setUmfAsegurado) {
							mensajeUmf = MENSAJE_UMF_ASEGURADO;
						}
						//Si seteamos el domidilio del asegurado tambien seteramos la umf del asegurado
						permiteSeleccionUmf = !setUmfAsegurado;
					}//fin else - no es patron ni represnetante legal
				//} //fin else - tiene domicilio -
			
			} catch(Exception e) {
				error = true;
				mensajeError = "0";
				log.error("Ocurrio un error al hacer las validaciones de roles y domicilio", e);
			}
		}
		result.put("mensajeDomicilio", mensajeDomicilio);
		result.put("mensajeUmf", mensajeUmf);
		result.put("setUmfAsegurado", setUmfAsegurado);
		result.put("permiteSeleccionUmf", permiteSeleccionUmf);
		result.put("setDomicilioAsegurado", setDomicilioAsegurado);
		result.put("permiteUbicarDomcilio", permiteUbicarDomcilio);
		result.put("personaDomicilio", personaDomicilio);
		result.put("error", error);
		result.put("mensajeError", mensajeError);
		
		return result;
	}
	
	
	/**
	 * Metodo para validar si 
	 */
	@Override
	public Map<String, Object> obtenerSolicitudRegistroAseguradoPensionado(
			AsignacionNSS asignacionNSS, Long idOrigenSolicitud) {
		Map<String,Object> result = new HashMap<String, Object>();
		
		Boolean correcto = true;
		String mensaje = "";
		Solicitud solicitudActiva = null;
		
		//Buscamos las solicitudes activas, para que en caso de haberla retomarla
		try {
			List<Long> tiposTramite = new ArrayList<Long>();
			List<Long> estadosTramite = new ArrayList<Long>();
			estadosTramite.add(EstadoTramiteEnum.INICIADO.getId());
			estadosTramite.add(3L);
			
			tiposTramite.add(TipoTramiteEnum.REGISTRO_ASEGURADO.getCodigo().longValue());
			tiposTramite.add(TipoTramiteEnum.REGISTRO_PENSIONADO.getCodigo().longValue());
			

			//Buscamos las solicitudes dependiento del parentesco
			List<Solicitud> solicitudesRegistro = solicitudTramiteBusinessRemote.getSolicitudesPersona(null,tiposTramite, estadosTramite, null, null, asignacionNSS.getIdPersona(), true, 1, true);
			
			if(solicitudesRegistro != null && !solicitudesRegistro.isEmpty()) {
				log.debug("Se encontro una solicitud activa");
				solicitudActiva = solicitudesRegistro.get(0);
			}
			
			//Si ya existe una solicitud de registro activa
			if(solicitudActiva != null) {
				result.put("solicitudActiva", solicitudActiva);
				
				if(!solicitudActiva.getOrigenSolicitud().getIdTipoSolicitud().equals(idOrigenSolicitud)) {
					log.debug("la solicitud no fue iniciada desde ventanilla");
					correcto = false;
					mensaje = "No es posible registrar al asegurado / pensionado ya que existe una solicitud del mismo tipo iniciada por otro medio";
					
				} else {
					log.debug("la solicitud fue iniciada desde ventanilla");
					correcto = true;
					mensaje = "Se detecto una solicitud de registro de asegurado / pensionado es necesario concluirla para registrar otros parentescos";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Ocurrio un error al consultar la ultima solicitud de registro", e);
		}
		
		result.put(KEY_ESTADO_REQUISITOS, correcto);
		result.put(KEY_MENSAJE_REQUISITOS, mensaje);
		
		
		
		return result;
	}


    @Override
    public Map<String, Object> obtenerSolicitudCambioClinica(
            AsignacionNSS asignacionNSS, Long idOrigenSolicitud) {
        Map<String,Object> result = new HashMap<String, Object>();

        Boolean correcto = true;
        String mensaje = "";
        Solicitud solicitudActiva = null;

        //Buscamos las solicitudes activas, para que en caso de haberla retomarla
        try {
            List<Long> tiposTramite = new ArrayList<Long>();
            List<Long> estadosTramite = new ArrayList<Long>();
            estadosTramite.add(EstadoTramiteEnum.INICIADO.getId());
            estadosTramite.add(3L);

            tiposTramite.add(TipoTramiteEnum.CAMBIO_CLINICA.getCodigo().longValue());

            List<Solicitud> solicitudes = solicitudTramiteBusinessRemote.getSolicitudesPersona(null,tiposTramite, estadosTramite, null, null, asignacionNSS.getIdPersona(), true, 1, true);

            if(solicitudes != null && !solicitudes.isEmpty()) {
                log.debug("Se encontro una solicitud activa");
                solicitudActiva = solicitudes.get(0);
            }

            //Si ya existe una solicitud de registro activa
            if(solicitudActiva != null) {
                result.put("solicitudActiva", solicitudActiva);

                if(!solicitudActiva.getOrigenSolicitud().getIdTipoSolicitud().equals(idOrigenSolicitud)) {
                    log.debug("la solicitud no fue iniciada desde portal ciudadano");
                    correcto = false;
                    mensaje = "No es posible el cambio de clinica ya que existe una solicitud del mismo tipo iniciada por otro medio";
                } else {
                    log.debug("la solicitud fue iniciada desde portal ciudadano");
                    correcto = true;
                    mensaje = "Se detecto una solicitud de cambio de clinica, debe concluirse para iniciar una nueva";
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
            log.error("Ocurrio un error al consultar la ultima solicitud de cambio de clinica", e);
        }

        result.put(KEY_ESTADO_REQUISITOS, correcto);
        result.put(KEY_MENSAJE_REQUISITOS, mensaje);



        return result;
    }



	/**
	 * Metodo para saber si un nss puede o no realizar un tipo de tramite, el metodo consulta el parentesco con el que cuenta 
	 * el idasignacion nss y en caso de ser pensionado busca los tramites que puede hacer el pensionado, en caso de ser un asegurado
	 * se buscaran las modalidades que tiene activas y se vera cual de ellas puede hacer el tramite solicitado, en caso de que ninguna
	 * de las modalidades lo pueda hacer, el metodo retorna false
	 * @param idAsignacionNss
	 * @param idTipoTramite
	 * @return
	 */
	@Override
	public Map<String, Object> tramitePermitidoParaAseguradoPensionado(Long idAsignacionNss, Long idTipoTramite) throws DerechohabientesBusinessException{
		log.debug("Entro a validar si el asegurado con idAsignacionNSS: " + idAsignacionNss + " puede realizar el tramite: " + idTipoTramite);
		
		CabezaGrupoFamiliar cabeza = null;
		try{
			cabeza= grupoFamiliarDaoLocal.getCabezaGrupoFamiliarWS(idAsignacionNss);
			}catch(Exception e){
				log.error("error al consultar la cabeza de grupo", e);
				throw new DerechohabientesBusinessException(e.getMessage());
			}
		
		//Una vez que se consulto la cabeza se verifica si se tiene derecho al tramite
		return tramitePermitidoParaAseguradoPensionado(cabeza, idTipoTramite,false, null);

	}

	/**
	 * Metodo para saber si un nss puede o no realizar un tipo de tramite, el metodo consulta el parentesco con el que cuenta 
	 * el idasignacion nss y en caso de ser pensionado busca los tramites que puede hacer el pensionado, en caso de ser un asegurado
	 * se buscaran las modalidades que tiene activas y se vera cual de ellas puede hacer el tramite solicitado, en caso de que ninguna
	 * de las modalidades lo pueda hacer, el metodo retorna false
	 * @param CabezaGrupoFamiliar debe contener al menos el atributo asignacionnss
	 * @param idTipoTramite
	 * @return
	 */
	public Map<String, Object> tramitePermitidoParaAseguradoPensionado(CabezaGrupoFamiliar cabeza, Long idTipoTramite, Boolean consultarModalidades, List<Long> idsModalidades) throws DerechohabientesBusinessException{
		log.debug("Entro a validar si el asegurado con idAsignacionNSS: " + cabeza.getAsignacionNSS() + " puede realizar el tramite: " + idTipoTramite);
		Boolean permitido = false;
		
		//Verifico si la cabeza de grupo familiar trae el parntesco
		cabeza = this.validarInfoCabeza(cabeza);
		
		//Si es pensionado se checan los tramites para pensionado
		if(cabeza.getCalidadParentesco().getIdParentesco().equals(ParentescoEnum.PENSIONADO.getId())) {
			log.debug("Se validara si el pensionado cuenta con derecho al tramite " + idTipoTramite);
			permitido = tramiteModalidadEntityLocal.tramitePermitidoParaPensionado(idTipoTramite);

		} else {
			idsModalidades = this.validaModalidadesAsegurado(cabeza, consultarModalidades,idsModalidades);
			
			log.debug("las modalidades del asegurado son: " + idsModalidades);
			
			if(idsModalidades == null || idsModalidades.isEmpty()) {
				return this.getMapResquisitos(false, "No es posible realizar el tr&aacute;mite ya que "
						+ "el asegurado no cuenta con ninguna modalidad relacionada.");
			}
			
			//Se valida si el tramite esta permitido para alguna de las modalidades del asegurado
			permitido = this.tramitePermiditoPorModalidades(idTipoTramite, idsModalidades);
		
		}
		
		if(!permitido) {
			//Si el asegurado es  becario modalidad 37, se permite el registro de beneficiarios   
			if(!cabeza.getPatronSujetoObligado().getModalidad().getNumModalidad().equals(MODALIDAD_BECARIO)){
				return this.getMapModalidadNoPermitida();
			}
		}

		return this.getMapResquisitos(true, Constants.MOTIVO_APROBADO);
	}
	
	/**
	 * Metodo para saber si el tramite es permitido para las modalidades con las que cuenta el asegurado
	 * @param idTipoTramite
	 * @param idsModalidades
	 */
	@Override
	public Boolean tramitePermiditoPorModalidades(Long idTipoTramite,
			List<Long> idModalidades) {
		//Se invoca el metodo de tramite-modalidad
		return tramiteModalidadEntityLocal.tramitePermitidoPorModalidades(idTipoTramite, idModalidades, false);
	}


	/**
	 * Metodo para saber si un tramite esta permitido para un pensionado
	 */
	@Override
	public Boolean tramitePermitidoParaPensionado(Long idTipoTramite) {
		
		return tramiteModalidadEntityLocal.tramitePermitidoParaPensionado(idTipoTramite);
	}
	
	private Long validarTipoTramite(TramiteRegistroDerechohabiente registro) {
		log.debug("Se valida si el tramite de registro tra el tipo de registro");
		Long idTipoTramite = 0L;
		
		if(registro.getTipoTramite() == null || registro.getTipoTramite().getIdTipoTramite()==null) {
			log.debug("el tramite no trae tipo");
			idTipoTramite = this.getTipoTramitePorParentesco(registro.getParentesco().getIdParentesco());
			log.debug("El tramite de acuerdo al parentesco encontrado es: " + idTipoTramite);
		} else {
			log.debug("El tramite si trae tipo de tramite: " + registro.getTipoTramite().getIdTipoTramite());
			idTipoTramite = registro.getTipoTramite().getIdTipoTramite().longValue();
		}
		return idTipoTramite;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Map<String, Object> requisitosMinimosRegistro(
			TramiteRegistroDerechohabiente registro, CabezaGrupoFamiliar cabeza, Long idOrigenSolicitud, Long idUmfUsuario, Boolean consultarModalidades,List<Long> idsModalidadesActivas) throws DerechohabientesBusinessException {
		//Resultado
		Map<String, Object> resultado = new HashMap<String, Object>();
		//Validamos la informacion de la cabeza
		cabeza = this.validarInfoCabeza(cabeza);
		//Datos del asegurado
		AsignacionNSS nss = registro.getDatosAsegurado();
		
		//razon de registro
		Long idRazonRegistro = registro.getRazonRegistro().getIdRazonRegistro();
		//Obtenemos el parentesco
		Long parentesco = registro.getParentesco().getIdParentesco();
		//Verificamos si el parentesco es MADRE de ser asi, lo cambiamos por padres
		parentesco = parentesco.equals(ParentescoEnum.MADRE.getId()) ? ParentescoEnum.PADRES.getId() : parentesco;
		//verificamos si el parentesco es concubia
		parentesco = parentesco.equals(ParentescoEnum.CONCUBINA.getId()) ? ParentescoEnum.CONCUBINARIO.getId() : parentesco;
		//Fecha de nacimiento del derechohabiente
		Date fechaNacimiento = registro.getFisica().getFechaNacimiento();
		//mostramos la fecha de nacimiento
		log.debug("La fecha de nacimiento del derechohabiente es: " + fechaNacimiento);
		//Verificamos
		Boolean registroPermitidoPorModalidad = false;
		//el sexo del asegurado para caso de registro de concubina(rio)
		Integer idSexoAsegurado = nss.getSexo().getIdSexo();
		//tipo de registro a realizar
		
		//Se valida primero la situacion de vigencia del asegurado pensionado
		if(idOrigenSolicitud.longValue() != OrigenSolicitudEnum.INTERNET_TSPI.getId() && 
				idOrigenSolicitud.longValue() != OrigenSolicitudEnum.VENTANILLA_TSPI.getId()){
			if(!parentesco.equals(ParentescoEnum.ASEGURADO.getId()) && 
					cabeza.getEstadoDerechohabiente().getIdEstadoDerechohabiente().equals(EstadoDerechohabienteEnum.BAJA.getId())){
				return this.getMapResquisitos(false, "EL asegurado no cuenta con un estado de vigencia valido parar poder realizar el tramite de registro de beneficiarios");
			}
		}
		
		
		
		Long idTipoTramite = validarTipoTramite(registro);
		if(consultarModalidades) {
			//modalidades del asegurado
			idsModalidadesActivas = this.obtenerModalidadesAsegurado(cabeza);
		}
		
		//validamos si existe la relacion entre la umf y el codigo postal
		resultado = this.validaCorrespondenciaDomicilioUMF(idOrigenSolicitud, idUmfUsuario, registro.getDomicilio());
		
		if(this.getEstadoValidaciones(resultado)) {
			//Se valida existencia de otra solicitud
			resultado = this.validarSolicitudPendiente(nss, parentesco,registro.getTramiteId());
		}
		
		//Si el domicilio corresponde con la umf continuamos con las siguientes validaciones
		if(this.getEstadoValidaciones(resultado)) {
			//Se valida si la razon de registro es por laudo
			if(idRazonRegistro.equals(RazonRegistroEnum.POR_LAUDO.getId())){
				resultado = this.getMapResquisitos(true, Constants.SOL_LAUDO);
			}else if(idRazonRegistro.equals(RazonRegistroEnum.POR_ACUERDO.getId())){
				resultado = this.getMapResquisitos(true, Constants.SOL_ACUERDO);
			}else if(idRazonRegistro.equals(RazonRegistroEnum.POR_AMPARO.getId())){
				resultado = this.getMapResquisitos(true, Constants.SOL_AMPARO);
			} else if(idRazonRegistro.equals(RazonRegistroEnum.RECIEN_NACIDO.getId())) {
				//validamos que ya se encuentre registrado el asegurado dentro del grupo familiar
				Boolean existeAsegurado = this.existeAseguradoOPensionao(cabeza.getAsignacionNSS());
				//Si ya se encuentra el aseguado registrado validamos los requisitos de acuedo al parentesco a rregistrar
				if(existeAsegurado) {
					log.debug("El registro es de un recien nacido");
					//Verificamos si se permite el registro de recien nacido
					registroPermitidoPorModalidad = this.validarRegistroRecienNacido(cabeza, idSexoAsegurado.longValue(), consultarModalidades, idsModalidadesActivas);
					
					if(!registroPermitidoPorModalidad) {
						resultado = this.getMapModalidadNoPermitida();
					} else {
						//Se valida el numero de hijos
						resultado = this.validacionesRecienNacidos(nss, cabeza, registro.getFisica(), idsModalidadesActivas);
					}
				} else {
					//en caso de que el asegurado no se encuentre registrado aun y
					//se quiera egistrar cualquier otro parentesco mandaremos el error de que no es posible
					return this.getMapResquisitos(false, Constants.MENSAJE_ERROR_ASEGURADO_NO_REGISTRADO);
				}
			} else{
				log.debug("El registro no es de un recien nacido");
				
				/**
				 * Validamos si a quien vamos a registrar a alguien con el parentesco asegurado y si su estado es baja 
				 * y ademas que no tenga modalidades activas ni patron de ultimo movimiento
				 */
				if(idOrigenSolicitud.longValue() != OrigenSolicitudEnum.INTERNET_TSPI.getId() && 
						idOrigenSolicitud.longValue() != OrigenSolicitudEnum.VENTANILLA_TSPI.getId()	){
					if(parentesco.equals(ParentescoEnum.ASEGURADO.getId()) && 
							cabeza.getEstadoDerechohabiente().getIdEstadoDerechohabiente().equals(EstadoDerechohabienteEnum.BAJA.getId())
							&& (idsModalidadesActivas == null || idsModalidadesActivas.isEmpty()) && cabeza.getPatronSujetoObligado() == null ) {
						
						return this.getMapResquisitos(false, "No se tiene ninguna modalidad registrada para poder realizar el tramite de registro");
					}
					//Verificamos si la modalidad permite el registro
					resultado = this.tramitePermitidoParaAseguradoPensionado(cabeza,idTipoTramite,consultarModalidades, idsModalidadesActivas);
				}
								
				if(!this.getEstadoValidaciones(resultado)) {
					return resultado;
				} else {
					if(parentesco.equals(ParentescoEnum.PENSIONADO.getId()) || parentesco.equals(ParentescoEnum.ASEGURADO.getId())) {
						//Se validan los requisitos del asegurado yo pensionado
						resultado = this.validacionesParentescoAseguradoPensionado(cabeza.getAsignacionNSS(), parentesco);
					} else {
						//validamos que ya se encuentre registrado el asegurado dentro del grupo familiar
						Boolean existeAsegurado = this.existeAseguradoOPensionao(cabeza.getAsignacionNSS());
						//Si ya se encuentra el aseguado registrado validamos los requisitos de acuedo al parentesco a rregistrar
						if(existeAsegurado) {
							//se validara que los apellidos de los hijos correspondan con los del asegurado cuando no se trate de adopcion o reconocimiento
							Boolean validarApellidos = !registro.getVarianteRegistro().equals(VarianteRegistroEnum.ADOPCION.getId()) && !registro.getVarianteRegistro().equals(VarianteRegistroEnum.RECONOCIMIENTO.getId()); 
							//Si el parentesco es hijo
							if(parentesco.equals(ParentescoEnum.HIJOS.getId())) {
								//Si el parentesco es hijos se llaman las validaciones de los hijos
								resultado = this.validacionesHijos(nss, registro.getFisica(), idOrigenSolicitud, validarApellidos);
							} else if(parentesco.equals(ParentescoEnum.CONYUGE.getId())) {	
								//se validan los requisitos para conyuges
								resultado = this.validacionesConyuge(nss, registro.getFisica(), false, idOrigenSolicitud);
							} else if(parentesco.equals(ParentescoEnum.PADRES.getId())){
								//se validan los requisitos para padres
								resultado = this.validacionesPadres(nss, registro.getFisica(), idOrigenSolicitud, validarApellidos);
							} else if(parentesco.equals(ParentescoEnum.CONCUBINARIO.getId())){//requisitos para parentesco concubina/concubinario		
								//se validan los requisitos para padres
								resultado = this.validacionesConcubina(nss, registro.getFisica(),false, registro.getIndHijosProcreados(),idOrigenSolicitud);
							}
						} else {
							//en caso de que el asegurado no se encuentre registrado aun y
							//se quiera egistrar cualquier otro parentesco mandaremos el error de que no es posible
							return this.getMapResquisitos(false, Constants.MENSAJE_ERROR_ASEGURADO_NO_REGISTRADO);
						}
					}
				}
			}
		}
		
		return resultado;
	}
	
	/**
	 * Metodo para validar si existe relacion entre la umf y el codigo postal
	 * @param idOrigenSolicitud
	 * @param idUmfUsuario
	 * @param domicilio
	 * @return
	 */
	private Map<String,Object> validaCorrespondenciaDomicilioUMF(Long idOrigenSolicitud, Long idUmfUsuario, Domicilio domicilio) {
		Map<String,Object> result = this.getMapResquisitos(true, Constants.MOTIVO_APROBADO);
		
		if(idOrigenSolicitud.equals(OrigenSolicitudEnum.VENTANILLA.getId()) && idUmfUsuario != null) {  
			Boolean corresponde = umfCodigoPostalDaoLocal.existeRelacionEntreUmfYCp(domicilio.getCodigoPostal().getCodigoPostal(), idUmfUsuario);
			
			if(!corresponde) {
				result = this.getMapResquisitos(corresponde, Constants.UMF_NO_CORRESPONDE);
			} 
		}
		
		return result;
	}
	
	/**
	 * Metodo para obtener si las validaciones son correctas
	 * @param validaciones
	 * @return
	 */
	private Boolean getEstadoValidaciones(Map<String, Object> validaciones) {
		Boolean correcto = false;
		
		correcto = (Boolean) validaciones.get(KEY_ESTADO_REQUISITOS);
		
		return correcto;
	}
	
	private Map<String,Object> getMapModalidadNoPermitida(){
		Map<String,Object> resultado = new HashMap<String, Object>();
		
		resultado.put(KEY_ESTADO_REQUISITOS, false);
		resultado.put(KEY_MENSAJE_REQUISITOS, Constants.NO_MODALIDAD);
		
		return resultado;
	}
	
	private Map<String, Object> getMapResquisitos(Boolean correcto, String mensaje) {
		Map<String,Object> resultado = new HashMap<String, Object>();
		
		resultado.put(KEY_ESTADO_REQUISITOS, correcto);
		resultado.put(KEY_MENSAJE_REQUISITOS, mensaje);
		
		return resultado;
	}

	@Override
	public Map<String, Object> requisitosMinimosCorreccion(GrupoFamiliar integrante,AsignacionNSS asignacionNss, CabezaGrupoFamiliar cabeza, Boolean consultarMod, List<Long> idsModalidad) throws DerechohabientesBusinessException {
		
		//Resultado
		Map<String, Object> resultado = new HashMap<String, Object>();
		//id de la persona a modificat
		Long idPersona = integrante.getDerechohabiente().getIdPersona();
		//el id del nuevo parentesco
		Long idNuevoParentesco = integrante.getParentesco().getIdParentesco();
		//la nueva curo
		String curpNueva = integrante.getDerechohabiente().getCurp();
		//Bandera para verificar si hubo cambio de parentesco
		Boolean cambioParentesco = false;
		//nueva persona
		Fisica nuevosDatos = integrante.getDerechohabiente();
		//Integrante afectado
		GrupoFamiliar integranteAfectado = null;
		
		Fisica fisicaRenapo = null;
		
		//en caso de que el parentesco sea concubina lo ponemos como concubinario ya que es el unico que existe en bdtu
		idNuevoParentesco = idNuevoParentesco.equals(ParentescoEnum.CONCUBINA.getId()) ? ParentescoEnum.CONCUBINARIO.getId() : idNuevoParentesco;
		//en caso de que el parentesco sea madre lo cambiaremos por padres ya que el parentesco madre no existe en BDTU
		idNuevoParentesco = idNuevoParentesco.equals(ParentescoEnum.MADRE.getId()) ? ParentescoEnum.PADRES.getId() : idNuevoParentesco;
		
		Sexo sexoDerechohabiente = integrante.getDerechohabiente().getSexo();
		
		resultado = this.getMapResquisitos(true, Constants.MOTIVO_APROBADO);
		
		
		if( sexoDerechohabiente == null || sexoDerechohabiente.getIdSexo() == null){
			return this.getMapResquisitos(false, Constants.SEXO_INVALIDO);
		}
		
		
		// -------------------------------------------------------------------------------------------------------
		// Para la correcci�n: 
		// 	- Los beneficiarios deben capturar la fecha de nacimiento mediante ICA, en caso de no estar asignada.
		// 	- El asegurado no puede cambiar sus datos de mediante ICA y puede no traer la fecha de nacimiento
		// -------------------------------------------------------------------------------------------------------
		if( !idNuevoParentesco.equals(ParentescoEnum.ASEGURADO) &&  !idNuevoParentesco.equals(ParentescoEnum.PENSIONADO) ){
			if( integrante.getDerechohabiente().getFechaNacimiento() == null ){
				return this.getMapResquisitos(false, Constants.FECHA_NACIMIENTO_INVALIDA);
			}
		}
		
		// ----------------------------------------------------------------------
		// Si env�a la curp es por que la cambio, en caso de que no se
		// env�e es por que no cambio la curp
		// ----------------------------------------------------------------------
		log.debug("Existe Integrante :"+curpNueva);
		try{
		integranteAfectado = grupoFamiliarDaoLocal.getIntegranteSinVigencia(asignacionNss.getIdAsignacionNSS(),idPersona);
		}	catch(Exception e) {
			log.error("error al consultar los integrantes del grupo familiar", e);
			return  this.getMapResquisitos(false, "Ocurri&oacute; un error al intentar validar los requisitos para la correccion. " + e.getCause().getMessage());
		}
		
		if( (curpNueva != null) && ( curpNueva.length() == 18 ) ){
			
			
			if( grupoFamiliarDaoLocal.existeIntegranteGrupoFamiliarPorCurp(asignacionNss.getIdAsignacionNSS(), curpNueva.toUpperCase()) ){
				// ---------------------------------------------------------------------------
				// Existe un integrante en el mismo grupo con la curp que intenta asignar
				// ---------------------------------------------------------------------------
				return this.getMapResquisitos(false, Constants.CURP_REPETIDA_EN_GRUPO);
			}
			//adicion de validacion para que no se pueda modificar la CURP si ya esta calificada
			
			try{
				 fisicaRenapo = this.localizarPersonaFisicaEnRENAPOServiceBusiness
								.localizarPersonaFisicaEnRENAPOxCURP(integranteAfectado.getDerechohabiente().getCurp());
			}catch (Exception e) {
				log.error("error al consultar la CURP historica en RENAPO" ,e);
			}
			
			if(integranteAfectado.getDerechohabiente().getCurp() != null){
			//se busca la persona de renapo para recuperar el CURP y comparar si es una CURP historica
				
					if(fisicaRenapo != null && !fisicaRenapo.getCurp().equals(curpNueva)){
						return this.getMapResquisitos(false, Constants.CURP_ACTUALIZAR_NO_HISTORICA);
					}
			}
			
		}
		
		if(this.getEstadoValidaciones(resultado)) {
			try {
				//Obtenemos al integrantes afectado por la correccion
				//Verificamos si hubo cambio de parentesco
				cambioParentesco = !integranteAfectado.getParentesco().getIdParentesco().equals(idNuevoParentesco);
				
				//En caso de haberlo validaremos si el asegurado cuenta con derecho al registro del nuevo parentesco
				if(cambioParentesco) {
					Long idTipoTramiteAValidar = this.getTipoTramitePorParentesco(idNuevoParentesco);
					//Validamos si la modalidad permite el tipo de tramite
					resultado = this.tramitePermitidoParaAseguradoPensionado(cabeza, idTipoTramiteAValidar, consultarMod, idsModalidad);
					if(fisicaRenapo != null){
						nuevosDatos.setNombre(fisicaRenapo.getNombre());
						nuevosDatos.setPrimerApellido(fisicaRenapo.getPrimerApellido());
						nuevosDatos.setSegundoApellido(fisicaRenapo.getSegundoApellido());
						nuevosDatos.setLugarNacimiento(fisicaRenapo.getLugarNacimiento());
					}else{
						nuevosDatos.setNombre(integranteAfectado.getDerechohabiente().getNombre());
						nuevosDatos.setPrimerApellido(integranteAfectado.getDerechohabiente().getPrimerApellido());
						nuevosDatos.setSegundoApellido(integranteAfectado.getDerechohabiente().getSegundoApellido());
						nuevosDatos.setLugarNacimiento(integranteAfectado.getDerechohabiente().getLugarNacimiento());
					}
					//en caso de que la modalidad no lo permite
					if(!this.getEstadoValidaciones(resultado)) {
						return resultado;
					} else {
						
						//Se validan los requisitos
						if(idNuevoParentesco.equals(ParentescoEnum.HIJOS.getId())) {
							//TODO verificar el nuevo parametro que se tiene que mandar
							resultado = this.validacionesHijos(asignacionNss, nuevosDatos, OrigenSolicitudEnum.VENTANILLA.getId(), null);
						} else if(idNuevoParentesco.equals(ParentescoEnum.PADRES.getId())) {
							resultado = this.validacionesPadres(asignacionNss, nuevosDatos, OrigenSolicitudEnum.VENTANILLA.getId(), null);
						} else if(idNuevoParentesco.equals(ParentescoEnum.CONCUBINARIO.getId())) {
							resultado = this.validacionesConcubina(asignacionNss, nuevosDatos,true, null, OrigenSolicitudEnum.VENTANILLA.getId());
						} else if(idNuevoParentesco.equals(ParentescoEnum.CONYUGE.getId())) {
							resultado = this.validacionesConyuge(asignacionNss, nuevosDatos,true, OrigenSolicitudEnum.VENTANILLA.getId());
						}
					}
				} 
				
				
				// ---------------------------------------------------------------------------------
				// Al recien nacido no se le puede cambiar la fecha de nacimiento.
				// La fecha que captur� cuando se registr�, debe ser la misma de la correcci�n
				// ---------------------------------------------------------------------------------
				if(idNuevoParentesco.equals(ParentescoEnum.HIJOS.getId())) {
					if( (integranteAfectado.getIndRecienNacido() != null) && (integranteAfectado.getIndRecienNacido() == 1) ){
						if( !integrante.getDerechohabiente().getFechaNacimiento().equals(integranteAfectado.getDerechohabiente().getFechaNacimiento()) ){
							resultado = this.getMapResquisitos(false, Constants.FECHA_NACIMIENTO_RECIEN_NACIDO_CAMBIO);
						}
					}
				}
				
				
			} catch(DerechohabientesBusinessException e) {
				e.printStackTrace();
				resultado = this.getMapResquisitos(false, "Ocurri&oacute; un error al intentar validar los requisitos para la correccion. " + e.getMessage());
			}
		}
		
		
		if( (integrante.getDomicilio() == null) || 
				( (integrante.getDomicilio().getClave() == null) && 
						( integrante.getDomicilio().getCodigoPostal().getCodigoPostal() == null || integrante.getDomicilio().getCodigoPostal().getCodigoPostal().isEmpty()   ) )  ){
			// ---------------------------------------------------------------------------
			// No tiene domicilio asignado
			// ---------------------------------------------------------------------------
			resultado = this.getMapResquisitos(false, Constants.DOMICILIO_INVALIDO);
		}
		
		
		return resultado;
	}
	
	/**
	 * Funcion que obtiene el tipo de tramite de registro dependiendo del parentesco que se le pase
	 * @param idParentesco
	 * @return
	 */
	public Long getTipoTramitePorParentesco(Long idParentesco) {
		Long tipoTramite = 0L;
		//Verificamos si el parentesco es MADRE de ser asi, lo cambiamos por padres
		idParentesco = idParentesco.equals(ParentescoEnum.MADRE.getId()) ? ParentescoEnum.PADRES.getId() : idParentesco;
		//verificamos si el parentesco es concubia
		idParentesco = idParentesco.equals(ParentescoEnum.CONCUBINA.getId()) ? ParentescoEnum.CONCUBINARIO.getId() : idParentesco;
		
		if(idParentesco.equals(ParentescoEnum.ASEGURADO.getId())) {
			tipoTramite = TipoTramiteEnum.REGISTRO_ASEGURADO.getCodigo().longValue();
		} else if(idParentesco.equals(ParentescoEnum.PENSIONADO.getId())) {
			tipoTramite = TipoTramiteEnum.REGISTRO_PENSIONADO.getCodigo().longValue();
		}else if(idParentesco.equals(ParentescoEnum.HIJOS.getId())) {
			tipoTramite = TipoTramiteEnum.REGISTRO_HIJOS.getCodigo().longValue();
		}else if(idParentesco.equals(ParentescoEnum.CONYUGE.getId())) {
			tipoTramite = TipoTramiteEnum.REGISTRO_CONYUGUE.getCodigo().longValue();
		} else if(idParentesco.equals(ParentescoEnum.PADRES.getId())) {
			tipoTramite = TipoTramiteEnum.REGISTRO_PADRES.getCodigo().longValue();
		} else {
			tipoTramite = TipoTramiteEnum.REGISTRO_CONCUBINA_RIO.getCodigo().longValue();
		}
		
		return tipoTramite;
	}
	
	private List<Long> validaModalidadesAsegurado(CabezaGrupoFamiliar cabeza, Boolean consultarModalidadesActivas, List<Long> idsModalidades) throws DerechohabientesBusinessException{
		
		if(idsModalidades == null || idsModalidades.isEmpty()) {
			if(consultarModalidadesActivas) {
				//De lo contrario si el parentesco es asegurado se verificaran las modaliades
				log.debug("Se consultan las modalidades del asegurado");
				idsModalidades = this.obtenerModalidadesAsegurado(cabeza);
			} else {
				idsModalidades = new ArrayList<Long>();
				//Se vuelve a validar que el patron venga en caso contrario las modalidades se iran vacias
				if(cabeza.getPatronSujetoObligado() != null) {
					Long cvePatronSO = sujetoObligadoServiceBusinessRemote.getCvePatronSujetoObligadoPorCveIdPatronGeneral(cabeza.getPatronSujetoObligado().getCveIdSujetoObligado());
					Modalidad mod = sujetoObligadoServiceBusinessRemote.getModalidadPatron(cvePatronSO);
					idsModalidades.add(mod.getIdModalidad());
				}
			}
		}
		
		return idsModalidades;
	}
	
	/**
	 * Metodo para validar que la informacion de la cabeza necesaria para los requisitos sea correcta
	 * @param cabeza
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	private CabezaGrupoFamiliar validarInfoCabeza(CabezaGrupoFamiliar cabeza) throws DerechohabientesBusinessException {
		
		//Verifico si la cabeza de grupo familiar trae el parntesco
		if(cabeza.getCalidadParentesco() == null || cabeza.getCalidadParentesco().getIdParentesco() == null) {
			//En dado caso de que no lo traiga se consulta a la cabeza de grupo familiar
			try {
				cabeza = grupoFamiliarDaoLocal.getCabezaGrupoFamiliarWS(cabeza.getAsignacionNSS());
			} catch(DerechohabientesWebSserviceException e) {
				//Si ocurre un error se lanza la excepcion
				this.lanzarExcepcionWsVigencia(e);
			}catch(Exception e){
				this.lanzarExcepcionWsVigencia( new DerechohabientesWebSserviceException(e.getMessage()));
			
			}
		}
		
		return cabeza;
		
	}
	
	/**
	 * requisitos para recien nacido
	 * @param nss
	 * @param recienNacido
	 * @param idsModalidades
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	private Map<String,Object> validacionesRecienNacidos(AsignacionNSS nss, CabezaGrupoFamiliar cabeza,Fisica recienNacido,List<Long> idsModalidades) throws DerechohabientesBusinessException {
		log.debug("Se entra a validar los requisitos para hijos recien nacidos");
		//Resultado
		Map<String, Object> resultado = new HashMap<String, Object>();
		//indicadores
		Boolean correcto = true;
		//mensaje
		String mensaje = Constants.MOTIVO_APROBADO;
		//Fecha de nacimiento del recien nacido
		Date fechaNacimiento = recienNacido.getFechaNacimiento();
		//Se valida el numero de hijos
		Boolean numeroIntegrantesValidos = false;
		//parentesco del recien nacido
		Long parentesco = ParentescoEnum.HIJOS.getId();
		
		try {
			numeroIntegrantesValidos = this.validarNumeroIntegrantesPorParentesco(nss.getIdAsignacionNSS(), parentesco);
		}catch(DerechohabientesWebSserviceException e) {
			this.lanzarExcepcionWsVigencia(e);
		}
	
		
		try{
			if(numeroIntegrantesValidos) {
				/*
				//si el numero de hijos es valido se consulta a los hijos recien nacidos
				List<GrupoFamiliar> recienNacidos = grupoFamiliarDaoLocal.getIntegrantesRecienNacidos(nss.getIdAsignacionNSS());
				//si se encuentran a otros hijos recien naciso se verificara el numero
				if(recienNacidos != null && !recienNacidos.isEmpty()) {
					
					GrupoFamiliar integranteRecienNacido = recienNacidos.get(0);
					String fechaRecienNacidoARegistrar = DateUtils.dateToStringConFormato(fechaNacimiento, "dd/MM/yyyy");
					String fechaRecienNacidoRegistrado = DateUtils.dateToStringConFormato(integranteRecienNacido.getDerechohabiente().getFechaNacimiento(), "dd/MM/yyyy");
					//Se comparan las fechas del recien nacido a registrar con los registrados
					if(!fechaRecienNacidoARegistrar.equals(fechaRecienNacidoRegistrado)) {
						correcto = false;
						mensaje = MENSAJE_FECHA_NACIMIENTO_RN;
					}
					//si la fecha del recien nacido concuerda con la de los otros, se valida que no tenga ya 7
					if(correcto) {
						//se verifica que no haya 7 recien nacidos
						if(recienNacidos.size() >= NUMERO_MAXIMO_RECIEN_NACIDOS) {
							correcto = false;
							mensaje = MENSAJE_NUMERO_RECIEN_NACIDOS;
						}
					}
				}*/
				
				Long numeroRecienNacidos = grupoFamiliarDaoLocal.getNumeroRecienNacidos(nss.getIdAsignacionNSS());
				log.debug("el numero de hijos recien nacidos es: " + numeroRecienNacidos);
				if(numeroRecienNacidos.longValue() >= Constants.NUMERO_MAXIMO_RECIEN_NACIDOS) {
					correcto = false;
					mensaje = Constants.MENSAJE_NUMERO_RECIEN_NACIDOS;
				}
				//Si se pasan las validaciones de numeros y fechas de nacimiento de recien nacidos, se valida la fecha de nacimiento
				if(correcto) {
					
					//Verificamos cuantos dias han pasado desde el nacimiento del recien nacido
					//13/01/2016 se agrega uno ya que debe considerarse el dia de nacimiento y tomando
					//como ejemplo que si el RN nacion el 1 de enero y se intenta registrar el dia 31 de enero
					//la aplicacion lo dejaria pasar ya que la resta daria 30, cuando en realidad ya han pasado
					//31 dias de su nacimiento por lo que deberia, es por eso que se agrega un dia
					int diasDesdeNacimiento = DateUtils.getDaysBetweenDates(new Date(), fechaNacimiento) + 1;
					//Validamos que venga bien la informacion de la cabeza de grupo familiar
					cabeza = this.validarInfoCabeza(cabeza);
					//obtenemos el id del parentesco de la cabeza de grupo familiar
					Long parentescoCabeza = cabeza.getCalidadParentesco().getIdParentesco();
					//verificamos si el id del parentesco indica si es pensionado
					Boolean isPensionado = parentescoCabeza.equals(ParentescoEnum.PENSIONADO.getId());
					//si no es pensionado validaremos las modalidades del asegurado
					//si las modalidades del asegurado vienen nulas o vacias
					if(!isPensionado && (idsModalidades == null || idsModalidades.isEmpty())) {
						idsModalidades = this.obtenerModalidadesAsegurado(cabeza);
					}

					/*

					//Se omite esta validacion y se pone valor por defualt 100 al numero de dias permitidos
					//Con base a la actualizacion al caso de uso de registro de derechohabiente version 0.15 del 22/04/2020

					//dias maximos de nacimiento, se ponen 30 en caso de que sea modalidad 33
					int diasPermitidos = 30;
					//Si no tiene la modalidad 33 o Si tiene mas de una modalidad quiere decir que aunque tenga la modalidad treinta y trees
					//existen otras que si le permiten que el recien nacido tenga maximo 40 dias
					if(isPensionado || !idsModalidades.contains(ModalidadEnum.TREINTAYTRES.getId()) || idsModalidades.size() > 1) {
						diasPermitidos = 40;
					} 
					*/

					int diasPermitidos = 162;

					//verificamos si los dias desde el nacimiento no son mayores
					if(diasDesdeNacimiento > diasPermitidos) {
						correcto = false;
						mensaje = Constants.RECIEN_NACIDOS;
					}
				}
			}

		} catch(Exception e) {
			log.error("Ocurrio un error desconocido",e);
			DerechohabientesBusinessException.throwException("Ocurrio un error al verificar los requisitos para recien nacido");
		}
	
		//seteamos el resultado
		resultado.put(KEY_ESTADO_REQUISITOS, correcto);
		resultado.put(KEY_MENSAJE_REQUISITOS, mensaje);
		
		return resultado;
	}
	
	/**
	 * Metodo para obtener las modalidades del patron
	 * @param cabeza
	 * @return
	 * @throws Exception
	 */
	public List<Long> obtenerModalidadesAsegurado(CabezaGrupoFamiliar cabeza ) throws DerechohabientesBusinessException{
		List<Long> idsModalidades = new ArrayList<Long>();
		cabeza = this.validarInfoCabeza(cabeza);
		Long idParentesco = cabeza.getCalidadParentesco().getIdParentesco();
		Boolean isPensionado = idParentesco.equals(ParentescoEnum.PENSIONADO.getId());
		
		/*cambio en servicio ya ahy un servicio que busca modalidades*/
		/*
		try {
			log.debug("La persona es un asegurado, se buscaran las modalidades activas con el id de asignacion: " + cabeza.getAsignacionNSS());
			//Si no es un pensionado se buscan los patrones activos
			List<SujetoObligado> patronesActivos = derechohabienteWSClientRemote.getPatronesVigentesPorAsignacionNSS(cabeza.getAsignacionNSS());
			//Si la lista de patrones no es nula ni vacias
			if(patronesActivos != null && !patronesActivos.isEmpty()) {
				//Se obtienen sus modalidades
				log.debug("Se encontraron " + patronesActivos.size() + " patrones activos");
				log.debug("Las modalidades activas del asegurado son:\n");
				for(SujetoObligado sujeto: patronesActivos) {
					log.debug(" - " + sujeto.getModalidad().getIdModalidad());
					idsModalidades.add(sujeto.getModalidad().getIdModalidad());
				}
			} else {//En caso de que no se encuentren patrones activos
				//Se verifica que el patron sujeto del ultimo movimiento no se nulo
				if(cabeza.getPatronSujetoObligado() == null) {
					//en caso de serlo se consulta la cabeza de grupo familiar
					cabeza = derechohabienteWSClientRemote.obtieneInfoCabGpoFam(cabeza.getAsignacionNSS(), false);
				}
				
				//Se vuelve a validar que el patron venga en caso contrario las modalidades se iran vacias
				if(cabeza.getPatronSujetoObligado() != null) {
					Long cvePatronSO = sujetoObligadoServiceBusinessRemote.getCvePatronSujetoObligadoPorCveIdPatronGeneral(cabeza.getPatronSujetoObligado().getCveIdSujetoObligado());
					Modalidad mod = sujetoObligadoServiceBusinessRemote.getModalidadPatron(cvePatronSO);
					idsModalidades.add(mod.getIdModalidad());
				}
			}
		} catch(DerechohabientesWebSserviceException e) {
			this.lanzarExcepcionWsVigencia(e);
		}
		*/
		List <Modalidad> lstModalidades = grupoFamiliarServiceLocal.getModalidadesActivas(cabeza.getAsignacionNSS());
		
		if(lstModalidades != null && !lstModalidades.isEmpty()){
			for(Modalidad modalidad: lstModalidades) {
				idsModalidades.add(modalidad.getIdModalidad());
			}
		}
		
		//Si no se obtienen modalidades se lanza una exception ciempre yh cuando el parnetesco de la cabeza sea asegurado
		if((idsModalidades == null || idsModalidades.isEmpty()) && !isPensionado ) {
			//Si la modalidad es becario no se lanza la excepcion, ya que para becarios no se tienen idsModalidades
			if (cabeza.getPatronSujetoObligado().getModalidad().getNumModalidad().equals(MODALIDAD_BECARIO)) {
				log.debug("las modalidades estan vacias, pero es un becario");
			} else {
				log.debug("las modalidades estan vacias");
				DerechohabientesBusinessException.throwException(
					Constants.MENSAJE_MODALIDAD_NO_VALIDAD, Constants.MENSAJE_MODALIDAD_NO_VALIDAD);
			}
		}
			
		return idsModalidades;
	}
	
	/**
	 * Metodo para validar el registro de un recien nacido
	 * @param idModalidades
	 * @param idSexoAsegurado
	 * @param idParentescoCabeza
	 * @return
	 */
	private Boolean validarRegistroRecienNacido(CabezaGrupoFamiliar cabeza, Long idSexoAsegurado, Boolean consultarModalidades,List<Long> idModalidadesActivas) throws DerechohabientesBusinessException{
		log.debug("Se validaran los requisitos para recien nacido");
		Boolean permitido = false;
		
		//Verifico si la cabeza de grupo familiar trae el parntesco
		if(cabeza.getCalidadParentesco() == null || cabeza.getCalidadParentesco().getIdParentesco() == null) {
			//En dado caso de que no lo traiga se consulta a la cabeza de grupo familiar
			try {
				cabeza = grupoFamiliarDaoLocal.getCabezaGrupoFamiliarWS(cabeza.getAsignacionNSS());
			} catch(DerechohabientesWebSserviceException e) {
				//Si ocurre un error se lanza la excepcion
				this.lanzarExcepcionWsVigencia(e);
			}catch(Exception e){
				this.lanzarExcepcionWsVigencia(new DerechohabientesWebSserviceException(e.getMessage()));
			}
		}
		//Se obtiene el parentesco de la cabeza
		Long idParentescoCabeza = cabeza.getCalidadParentesco().getIdParentesco();
		//Si el parentesco es pensionado o becario
		if(idParentescoCabeza.equals(ParentescoEnum.PENSIONADO.getId()) || cabeza.getPatronSujetoObligado().getModalidad().getNumModalidad().equals(MODALIDAD_BECARIO)) {
			//el registro de hijos recien nacidos es permitido
			permitido = true;
		} else {
			log.debug("El parentesco de la persona que quiere registrar un recien nacido es asegurado");
			idModalidadesActivas = this.validaModalidadesAsegurado(cabeza, consultarModalidades,idModalidadesActivas);
			
			//las modalidades que permiten registro de recien nacido
			List<Long> idMods = this.idModalidadesRecienNacido();
			//Si alguna de las modalidades activas permite el registro de recien nacido se permite
			for(Long idModActiva: idModalidadesActivas) {
				if(idMods.contains(idModActiva)) {
					log.debug("la modalidad es permitidad para recien nacidos");
					permitido = true;
					break;
				} else {
					log.debug("La modalidad con id: " + idModActiva + " no permite el registro de recien nacidos");
				}
			}
			
			//Si no es permitido
			if(!permitido) {
				log.debug("la modalidad no permite el registro de recien nacidos, se valida si es la 33 y es mujer");
				//Se verifica si alguna de las modalidades es trece
				if(idModalidadesActivas.contains(ModalidadEnum.TREINTAYTRES.getId())) {
					log.debug("Las modalidades activas contienen la 33");
					//Si es asi se verifica si el sexo del asegurado es mujer
					if(idSexoAsegurado.equals(SexoEnum.MUJER.getId())) {
						log.debug("el sexo del asegurado es mujer");
						//De ser asi el tramite es permitido
						permitido = true;
					}
				}
			}
		}
		
		return permitido;
	}
	
	/**
	 * Modalidades que permiten el registro de hijos recien nacidos
	 * @return
	 */
	private List<Long> idModalidadesRecienNacido() {
		//10, 13, 14, 30, 31, 34, 35, 36, 38, 42, 43, 44, Pensi�n, Becarios
		
		List<Long> idsModalidades = new ArrayList<Long>();
		idsModalidades.add(ModalidadEnum.DIEZ.getId());
		idsModalidades.add(ModalidadEnum.TRECE.getId());
		idsModalidades.add(ModalidadEnum.CATORCE.getId());
		idsModalidades.add(ModalidadEnum.DIECISIETE.getId());
		idsModalidades.add(ModalidadEnum.TREINTA.getId());
		idsModalidades.add(ModalidadEnum.TREINTAYUNO.getId());
		idsModalidades.add(ModalidadEnum.TREINTAYCUATRO.getId());
		idsModalidades.add(ModalidadEnum.TREINTAYCINCO.getId());
		idsModalidades.add(ModalidadEnum.TREINTAYSEIS.getId());
		idsModalidades.add(ModalidadEnum.TREINTAYOCHO.getId());
		idsModalidades.add(ModalidadEnum.CUARENTAYDOS.getId());
		idsModalidades.add(ModalidadEnum.CUARENTAYTRES.getId());
		idsModalidades.add(ModalidadEnum.CUARENTAYCUATRO.getId());
		
		return idsModalidades;
	}

	/**
	 * Validaciones de hijos
	 * @param nss
	 * @param cabeza
	 * @param persona
	 * @param idOrigenSolicitud
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	private Map<String,Object> validacionesHijos(AsignacionNSS nss, Fisica persona, Long idOrigenSolicitud, Boolean validarApellidos) throws DerechohabientesBusinessException{
		log.debug("Se entra a validar los requisitos para parentesco hijos");
		validarApellidos = validarApellidos == null ? false: validarApellidos;
		//Resultado
		Map<String, Object> resultado = new HashMap<String, Object>();
		//indicadores
		Boolean correcto = true;
		//mensaje
		String mensaje = Constants.MOTIVO_APROBADO;
		//id persona a registrar
		Long idPersonaDerechohabiente = persona.getIdPersona();
		//Sexo de la persona
		Integer idSexoDerechohabiente = persona.getSexo().getIdSexo();
		//parentesco a validar
		Long idParentescoaValidar = ParentescoEnum.HIJOS.getId();
		
		try {
			log.debug("Se valida el numero de hijos");
			Boolean numeroIntegrantesValidos = this.validarNumeroIntegrantesPorParentesco(nss.getIdAsignacionNSS(), idParentescoaValidar);
			//Si tiene mas de 16 hijos no importando el estado no podra registrar uno mas
			if(!numeroIntegrantesValidos) {
				correcto = false;
				mensaje = Constants.NUMERO_MAXIMO_HIJOS;
			} else { //Si todavia puede registrar hijos
				log.debug("El numero de hijos es menor a 86");
				//verificamos si tenemos que validar los apellidos de la person que se tiene que registrar como hijo
				if(validarApellidos) {
					//El sexo del asegurado
					Long idSexoAsegurado = nss.getSexo().getIdSexo().longValue();
					//el apellido paterno del asegurado
					String apellidoAsegurado = this.getCadenaSinNSinEspacios(nss.getPrimerApellido());
					//el primer apellido de la persona a registrar como hijo(a)
					String primerApellidoIntegrante = this.getCadenaSinNSinEspacios(persona.getPrimerApellido());
					//el segundo apellido de la persona a registrar como hijo(a)
					String segundoApellidoIntegrante = this.getCadenaSinNSinEspacios(persona.getSegundoApellido());
					//si el sexo del asegurado es hombre
					if(idSexoAsegurado.equals(SexoEnum.HOMBRE.getId())) {
						//el primer apellido del integrante tiene que coincidir con el primer apellido del asegurado
						if(!primerApellidoIntegrante.equals(apellidoAsegurado)) {
							correcto = false;
							mensaje = Constants.MENSAJE_ERROR_APELLIDOS_H;
						}
					} else {//Si el sexo del asegurado es mujer
						//el segundo apellido del hijo debe coincidir con el apellido materno del asegurado
						if(!segundoApellidoIntegrante.equals(apellidoAsegurado)) {
							correcto = false;
							mensaje = Constants.MENSAJE_ERROR_APELLIDOS_M;
						}
					}
				}
				
				if(correcto) {
					//No se valida edad ya que siempre se registrara a un hijo
					//Si el sexo es mujer
					if(idSexoDerechohabiente.equals(SexoEnum.MUJER.getId())) {
						log.debug("El id del parentesco del hijo es mujer");
						List<GrupoFamiliar> integrantesExistentes = null;
						
						if(idPersonaDerechohabiente != null) {
							log.debug("el id de la persona no es nulo, se buscara si la hija esta registrada en otros grupos");
							//buscamos a la persona en otros grupos familiares
							integrantesExistentes = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliarEstado(idPersonaDerechohabiente, EstadoDerechohabienteEnum.VIGENTE.getId());
							//verificamos que la lista no venga nula o vacia
							if(integrantesExistentes != null && !integrantesExistentes.isEmpty()){
								log.debug("La hija esta registrada en otros grupos");
								//Verificamos que no este registrada como concubina o conyuge en otro grupo familiar
								for(GrupoFamiliar integrante : integrantesExistentes){
									if(integrante.getParentesco().equals(ParentescoEnum.CONCUBINARIO.getId()) || integrante.getParentesco().equals(ParentescoEnum.CONYUGE.getId())){
										log.debug("la hija esta registrada como conyuge o concubina en el grupo familiar con nss" + integrante.getAsignacionNSS().getNss());
										//De ser asi no permitimos que se registre a la persona
										correcto = false;
										mensaje = Constants.HIJA_REGISTRADA;
										break;
									}							
								}
							}
						}//fin if persona no nula
					} 
				}
			}
			
			//Si todo es correcto validaremos la edad con respecto al asegurado
			if(correcto) {
				//Buscamos al asegurado para verificar edades
				GrupoFamiliar asegurado = grupoFamiliarDaoLocal.getIntegranteSinVigencia(nss.getIdAsignacionNSS(), nss.getIdPersona());
				Map<String, Object>validaciones = this.validacionesEdadPadresHijos(asegurado.getDerechohabiente(), persona, idParentescoaValidar, idOrigenSolicitud);
				correcto = (Boolean) validaciones.get(KEY_ESTADO_REQUISITOS);
				mensaje = (String) validaciones.get(KEY_MENSAJE_REQUISITOS);
			}
			
		} catch(DerechohabientesWebSserviceException e) {
			log.error("error al consultar el ws de vigencia", e);
			resultado = this.getMapResquisitos(false, "Ocurri&oacute; un error al consultar la vigencia: " + e.getMessage());
		} catch(Exception e) {
			log.error("Ocurrio un error desconocido",e);
			resultado = this.getMapResquisitos(false, "Ocurri&oacute; un error desconocido al verificar los requisitos para el tr&aacute;mite");
		}
		
		//seteamos el resultado
		resultado.put(KEY_ESTADO_REQUISITOS, correcto);
		resultado.put(KEY_MENSAJE_REQUISITOS, mensaje);
		
		return resultado;
	}
	
	private Boolean existeAseguradoOPensionao(Long idAsignacionNSS) {
		List<Long> parentescosAsegurados = new ArrayList<Long>();
		parentescosAsegurados.add(ParentescoEnum.ASEGURADO.getId());
		parentescosAsegurados.add(ParentescoEnum.PENSIONADO.getId());
		
		//se valida si ya cuenta con registro como derechohabiente
		Boolean existeAsegurado = false;
		
		existeAsegurado = !grupoFamiliarDaoLocal.getNumeroDeIntegrantesPorListParentesco(idAsignacionNSS, parentescosAsegurados).equals(0L);
		
		return existeAsegurado;
	}
	/**
	 * Validaciones para el parentesco asegurado o pensionado
	 * @param nss
	 * @param parentesco
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	private Map<String,Object> validacionesParentescoAseguradoPensionado(Long cveIdAsignacionNSS, Long parentesco) throws DerechohabientesBusinessException{
		
		//Resultado
		Map<String, Object> resultado = new HashMap<String, Object>();
		//indicadores
		Boolean correcto = true;
		//mensaje
		String mensaje = Constants.MOTIVO_APROBADO;
		
		try {
			
			if(parentesco.equals(ParentescoEnum.PENSIONADO.getId())) {
				//Buscamos que no haya registrado ya un p�nsionado en el grupo familiar
				Boolean existeCabezaegistrada = this.existeAseguradoOPensionao(cveIdAsignacionNSS);
				//Verificamos que la lista de integrantes no sea nula y no este vacia
				if(existeCabezaegistrada) {
					//De ser asi indicamos que el pensionado ya esta registrado
					correcto = false;
					mensaje = Constants.ASEGURADO_PENSIONADO_UNICO;
				} else {
					//De lo contrario lo dejamos pasar
					correcto = true;
					mensaje = Constants.MOTIVO_APROBADO;
				}
			} else if(parentesco.equals(ParentescoEnum.ASEGURADO.getId())) {
				//Buscamos que no haya registrado ya un aseguradp en el grupo familiar
				Boolean existeCabezaegistrada = this.existeAseguradoOPensionao(cveIdAsignacionNSS);
				//Verificamos que la lista de integrantes no sea nula y no este vacia
				if(existeCabezaegistrada) {
					//De ser asi indicamos que el pensionado ya esta registrado
					correcto = false;
					mensaje = Constants.ASEGURADO_PENSIONADO_UNICO;
				} else {
					correcto = true;
					mensaje = Constants.MOTIVO_APROBADO;
					//Solo si estamos en el paso del domicilio o en el registro por ventanilla validamos la circunscripcion del domicilio del asegurado
					/*if(pasoTramite == null || pasoTramite.equals(PasoRegistroEnum.CAPTURA_DOMICILIO.getId())) {
						Asegurado miAsegurado = patronDao.getAsegurado(nss.getIdAsignacionNSS(),patron.getCveIdSujetoObligado());
						
						Boolean autorizacionPermanente = false;
						
						if(miAsegurado != null)
							autorizacionPermanente = miAsegurado.isAutorizacionPermanente();
						
						//Verificamos si tiene autorizacion permanente
						if(autorizacionPermanente) {
							//de ser asi lo dejamos pasar
							correcto = true;
							mensaje = Constants.MOTIVO_APROBADO;
						} else {
							//de lo contrario verificamos si esta en la misma circunscripcion que el patron
							Boolean mismaCircunscripcion =circunscripcionDerechohabiente(domicilio.getCodigoPostal().getCodigoPostal(), nss.getIdAsignacionNSS(), cabeza);
							//de ser asi lo dejamos pasar
							if(mismaCircunscripcion) {
								correcto = true;
								mensaje = Constants.MOTIVO_APROBADO;
							} else {
								//Si no le indicamos que esta mal la circunscripcion
								correcto = false;
								mensaje = Constants.CIRCUNSCRIPCION;
							}
						}
					} else {
						//Si no estamos en ventanilla y no es el paso de la captura de domicilio, lo dejamos pasar
						correcto = true;
						mensaje = Constants.MOTIVO_APROBADO;
					//}*/
				}
			}
			
		} catch(Exception e) {
			log.error("Ocurrio un error desconocido",e);
			DerechohabientesBusinessException.throwException("Ocurrio un error al verificar si el candidato ya se encuentra registrado");
		}
		//seteamos el resultado
		resultado.put(KEY_ESTADO_REQUISITOS, correcto);
		resultado.put(KEY_MENSAJE_REQUISITOS, mensaje);
		
		return resultado;
	}
	
	/**
	 * validaciones para el parentesco conyuge
	 * @param nss
	 * @param fisica
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	private Map<String, Object> validacionesConyuge(AsignacionNSS nss, Fisica conyuge, Boolean correccion, Long origenSolicitud ) throws DerechohabientesBusinessException {
		correccion = correccion == null ? false : correccion;
		//Resultado
		Map<String, Object> resultado = new HashMap<String, Object>();
		//indicadores
		Boolean correcto = true;
		//mensaje
		String mensaje = Constants.MOTIVO_APROBADO;
		//parentesco
		Long parentesco = ParentescoEnum.CONYUGE.getId();
		//id de la persona
		Long idPersonaDerechohabiente = conyuge.getIdPersona();
		//indicador de concubina registrada
		Boolean concubinaRegistrada = false;
		Boolean existeConyuge = false;
		Boolean existeConcubina = false;
		Long estado = new Long(EstadoDerechohabienteEnum.VIGENTE.getId());
		Long estadoBaja = new Long(EstadoDerechohabienteEnum.BAJA.getId());
		Long subEstadoSuspension = new Long(SubestadoDerechohabienteEnum.SUSPENCION_ADMINISTRATIVA.getId());
		String mismoSexoConyuge = "";
	
		
		try {
			
			if(origenSolicitud.equals(OrigenSolicitudEnum.INTERNET_TSPI.getId()) || origenSolicitud.equals(OrigenSolicitudEnum.VENTANILLA_TSPI.getId())) {
				if(!this.validaEsposaConcubinaBajaTSPI(nss.getIdAsignacionNSS())) {
					resultado.put(KEY_ESTADO_REQUISITOS, false);
					resultado.put(KEY_MENSAJE_REQUISITOS, "Este grupo familiar ya cuenta con el n�mero m�ximo de Conyuges o Concubina(rio) permitidos");
					resultado.put(KEY_CONCUBINA_REGISTRADA, true);
					return resultado;
				}
					
			}
			
			//Buscamos que no haya registrado ya un p�nsionado en el grupo familiar
			existeConyuge = this.existePersonaConParentesco(nss.getIdAsignacionNSS(), parentesco, estado.intValue(), correccion, conyuge.getIdPersona(), null);
			if(!existeConyuge){
				existeConyuge = this.existePersonaConParentesco(nss.getIdAsignacionNSS(), parentesco, estadoBaja.intValue(), correccion, conyuge.getIdPersona(), subEstadoSuspension);
			}
			//Verificamos que la lista de integrantes no sea nula y no este vacia
			if(existeConyuge) {
				//De ser asi indicamos que el pensionado ya esta registrado
				correcto = false;
				mensaje = Constants.NUMERO_MAXIMO_CONYUGE;
			} else{
				
				//Ahora verificamos que no se encuentre registrada una concubina
				 existeConcubina = this.existePersonaConParentesco(nss.getIdAsignacionNSS(), ParentescoEnum.CONCUBINARIO.getId(), estado.intValue(), correccion, conyuge.getIdPersona(), null);
				 if(!existeConcubina){
					 existeConcubina = this.existePersonaConParentesco(nss.getIdAsignacionNSS(), ParentescoEnum.CONCUBINARIO.getId(), estadoBaja.intValue(), correccion, conyuge.getIdPersona(), subEstadoSuspension);
					}
				//Si encontramos uina concubina vigente dentro del grupo familiar no lo dejamos pasar
				if(existeConcubina) {
					correcto = false;
					mensaje = Constants.CONCUBINA_REGISTRADA;
					concubinaRegistrada = true;
				} else {
					concubinaRegistrada = true;
					//De lo contrario ahora validaremos en caso de que el id de persona no sea nula, que la persona no este registrada como contyuge
					//o concubina (rio) en otro grupo familiar
					if(idPersonaDerechohabiente != null) {
							Map<String, Object> validaciones = validarExistenciaConcubinaConyugeEnOtroGrupo(conyuge, nss.getIdAsignacionNSS());
							correcto = (Boolean) validaciones.get(KEY_ESTADO_REQUISITOS);
							mensaje = (String) validaciones.get(KEY_MENSAJE_REQUISITOS);
					}else {
							//De lo contrario si no exite ni cungyuge registrado(a) lo dejamos pasar
						correcto = true;
						mensaje = Constants.MOTIVO_APROBADO;
					}
				}
				//Se validara edad minima del conyuge
				if(correcto) {
					long idSexoConuyuge = conyuge.getSexo().getIdSexo().longValue();
					long edadConyuge = DateUtils.getEdad(conyuge.getFechaNacimiento());
					
					log.debug("El sexo del conyuge a registrar es " + idSexoConuyuge + " y tiene " + edadConyuge + " anios");
					if(idSexoConuyuge == SexoEnum.HOMBRE.getId()) {
						if(edadConyuge < Constants.EDAD_MINIMA_CONYUGE_HOMBRE) {
							correcto = false;
							mensaje = Constants.MENSAJE_ERROR_EDAD_CONYUGE_HOMBRE;
						}
					} else {
						if(edadConyuge < Constants.EDAD_MINIMA_CONYUGE_MUJER) {
							correcto = false;
							mensaje = Constants.MENSAJE_ERROR_EDAD_CONYUGE_MUJER;
						}
					}
				}
				if(correcto) {
					//WO1977508 / 5167133 - Registro de Parejas del Mismo Sexo en Concubinato.
					//Se crea indicador para saber si es del mismo sexo (1) o diferente sexo(0) o sin idicador dependiendo si el 
					//asegurado o pensionado tiene CURP registrada
					mismoSexoConyuge = "";
					if (nss.getCurp() != null ) {
						String sexoCurp = nss.getCurp().substring(10,11);
						String sexoConyuge = conyuge.getSexo().getIdSexo() == 1 ? "H" : "M"; 
						mismoSexoConyuge = sexoCurp.equals(sexoConyuge) ? "1" : "0";
					} else {
						if ( nss.getSexo() != null) {
							mismoSexoConyuge = nss.getSexo().getIdSexo() == conyuge.getSexo().getIdSexo() ? "1" : "0";
						}
					}
					log.debug("Indicador de mismo sexo conyuge= " + mismoSexoConyuge);
				}				
			}
		} catch(DerechohabientesWebSserviceException e) {
			log.error("error al consultar el ws de vigencia", e);
			DerechohabientesBusinessException.throwException(e.getMessage());
		} catch(Exception e) {
			log.error("Ocurrio un error desconocido",e);
			DerechohabientesBusinessException.throwException("Ocurrio un error al verificar si el candidato ya se encuentra registrado ["+ e +"]"  );
		}
		
		
		//seteamos el resultado
		resultado.put(KEY_ESTADO_REQUISITOS, correcto);
		resultado.put(KEY_MENSAJE_REQUISITOS, mensaje);
		resultado.put(KEY_CONCUBINA_REGISTRADA, concubinaRegistrada);
		
		return resultado;
	}
	
	private Map<String, Object> validacionesConcubina(AsignacionNSS nss, Fisica concubina, Boolean correccion, Integer hijosProcreados, Long origenSolicitud)  throws DerechohabientesBusinessException{
		correccion = correccion == null ? false : correccion; 
		hijosProcreados = hijosProcreados == null ? 0: hijosProcreados;
		//Resultado
		Map<String, Object> resultado = new HashMap<String, Object>();
		//indicadores
		Boolean correcto = true;
		//mensaje
		String mensaje = Constants.MOTIVO_APROBADO;
		//id del parentesco
		Long parentesco = ParentescoEnum.CONCUBINARIO.getId();
		//id del sexo del 
		Integer idSexoAsegurado = nss.getSexo().getIdSexo();
		//id sexo de la persona que se va a registrar
		Integer idSexoDerechohabiente = concubina.getSexo().getIdSexo();
		//id de la persona que se registrara
		Long idPersonaDerechohabiente = concubina.getIdPersona();
		//indicador de hijos procreados
		boolean hijosProc = hijosProcreados.intValue() == 1;
		String mismoSexoConcubinario = "";
		
		Boolean existeConyuge = false;
		Boolean existeConcubina = false;
		log.debug("Se empiezan a validar los requisitos minimos para registro de concubina(rio)");
		//Estados y parentesco a buscar
		Long idEstadoDere = EstadoDerechohabienteEnum.VIGENTE.getId();
		Long idEstadoBaja = new Long(EstadoDerechohabienteEnum.BAJA.getId());
		Long subEstadoSuspension = new Long(SubestadoDerechohabienteEnum.SUSPENCION_ADMINISTRATIVA.getId());
		
		try {
			
			if(origenSolicitud.equals(OrigenSolicitudEnum.INTERNET_TSPI.getId()) || origenSolicitud.equals(OrigenSolicitudEnum.VENTANILLA_TSPI.getId())) {
				if(!this.validaEsposaConcubinaBajaTSPI(nss.getIdAsignacionNSS())) {
					resultado.put(KEY_ESTADO_REQUISITOS, false);
					resultado.put(KEY_MENSAJE_REQUISITOS, "Este grupo familiar ya cuenta con el n�mero m�ximo de Conyuges o Concubina(rio) permitidos");
					resultado.put(KEY_CONCUBINA_REGISTRADA, true);
					return resultado;
				}
					
			}
			//validamos si ya esxiste una conyuge vigente en el grupo
			 existeConyuge = this.existePersonaConParentesco(nss.getIdAsignacionNSS(), ParentescoEnum.CONYUGE.getId(), idEstadoDere.intValue(), correccion, concubina.getIdPersona(), null);
			if(!existeConyuge){
				existeConyuge = this.existePersonaConParentesco(nss.getIdAsignacionNSS(), ParentescoEnum.CONYUGE.getId(), idEstadoBaja.intValue(), correccion, concubina.getIdPersona(), subEstadoSuspension);
			}
			//si existe conyuge activa se retorna un error
			if(existeConyuge) {
				correcto = false;
				mensaje = "El grupo familiar ya tiene registrado(a) a un(a) conyuge";
			} else {
				//Se busca si se tiene algun concubinario en estado activo
				existeConcubina = this.existePersonaConParentesco(nss.getIdAsignacionNSS(), parentesco, idEstadoDere.intValue(),correccion,concubina.getIdPersona(), null);
				if(!existeConcubina){
					existeConcubina = this.existePersonaConParentesco(nss.getIdAsignacionNSS(), parentesco, idEstadoBaja.intValue(), correccion, concubina.getIdPersona(), subEstadoSuspension);
				}
				
				//Verificamos que no tenga una concubina activa
				if(existeConcubina){
					log.debug("Se tiene al menos un concubinario registrado en estado activo");
					//de ser asi mandamos el error
					correcto = false;
					mensaje = Constants.NUMERO_MAXIMO_CONCUBINARIO;
				}else{//Si no tiene concubina activa
					log.debug("No se tienen concubinarios activos, se validara que el sexo no sea igual al del asegurado");
					//WO1977508 / 5167133 - Registro de Parejas del Mismo Sexo en Concubinato. Se elimina validación de sexo.
					//Se crea indicador para saber si es del mismo sexo (1) o diferente sexo(0) o sin idicador dependiendo si el 
					//asegurado o pensionado tiene CURP registrada
					//Validamos que el sexo de la concubina(rio) no sea el mismo que el del asegurado
					//if(idSexoDerechohabiente.equals(idSexoAsegurado)){
					//	log.debug("El conciubinario tiene el mismo sexo que el asegurado pensionado");
					//	//se ser el mismo sexo mostramos un error
					//	correcto = false;
					//	mensaje = Constants.SEXO_PAREJA;
					//} else {
						//si son de sexo opuesto se validara que la edad de la concubina(rio)
						//sea de al menos 19 anios
						long edadConcubina_rio= DateUtils.getEdad(concubina.getFechaNacimiento());
						long edadComparasion =  hijosProc ? Constants.EDAD_MINIMA_CONCUBINA_RIO_HIJOS_PROCREADOS : Constants.EDAD_MINIMA_CONCUBINA_RIO;
						//si la edad es menor retornamos con error
						if(edadConcubina_rio < edadComparasion) {
							correcto = false;
							mensaje = hijosProc ? Constants.MENSAJE_ERROR_EDAD_CONCUBINA_RIO_HIJOS : Constants.MENSAJE_ERROR_EDAD_CONCUBINA_RIO;
						}
					//}
					//Se crea indicador para saber si es del mismo sexo o no
					mismoSexoConcubinario = "";
					if (nss.getCurp() != null ) {
						String sexoCurp = nss.getCurp().substring(10,11);
						String sexoConcubina = idSexoDerechohabiente == 1 ? "H" : "M"; 
						mismoSexoConcubinario = sexoCurp.equals(sexoConcubina) ? "1" : "0";
					} else {
						if ( nss.getSexo() != null) {
							mismoSexoConcubinario = nss.getSexo().getIdSexo() == idSexoDerechohabiente ? "1" : "0";
						}
					}
					log.debug("Indicador de mismo sexo concubinato = " + mismoSexoConcubinario);
				}
			}
			
			log.debug("Existen hijos procreados? " + hijosProc);
			//Se validara que no haya una baja de concubina o conyuge en los ultimos 5 anios
			//en caso de que no haya hijos procreados
			if(correcto && !hijosProc) {
				log.debug("Se validara si existen antecedentes de concubinas o esposas registradas");
				Map<String, Object> validaciones = validarExistenciaConcubinaDesdesHace5Anios(nss);
				correcto = (Boolean) validaciones.get(KEY_ESTADO_REQUISITOS);
				mensaje = (String) validaciones.get(KEY_MENSAJE_REQUISITOS);
			}
			//Si las validaciones de existencia se hicieron correctamente se validad rechazos
			if(correcto) {
				log.debug("las validaciones de existencia de concubina han sido pasadas");
				log.debug("Se validara que no exista una solicitud de registro de concubina con una razon de rechazo no permitidfa");
				Map<String, Object> validaciones = this.validarSolicitudRegistroRechazada(nss, parentesco);
				correcto = (Boolean) validaciones.get(KEY_ESTADO_REQUISITOS);
				mensaje = (String) validaciones.get(KEY_MENSAJE_REQUISITOS);

				if(correcto && idPersonaDerechohabiente != null) {
					log.debug("Se paso la validacion de solicitudes para concubinas, se validara que no exista en otro grupo");
						validaciones = this.validarExistenciaConcubinaConyugeEnOtroGrupo(concubina,nss.getIdAsignacionNSS());
						correcto = (Boolean) validaciones.get(KEY_ESTADO_REQUISITOS);
						mensaje = (String) validaciones.get(KEY_MENSAJE_REQUISITOS);
					log.debug("Resultado de la validacion de existencia en otro grupo famliar: " + correcto);
				}
			}
		} catch(DerechohabientesWebSserviceException e) {
			log.error("error al consultar el ws de vigencia", e);
			DerechohabientesBusinessException.throwException(e.getMessage());
		} catch(Exception e) {
			log.error("Ocurrio un error desconocido",e);
			DerechohabientesBusinessException.throwException("Ocurrio un error al verificar si el candidato ya se encuentra registrado [" +e+"]");
		}
		
		//seteamos el resultado
		resultado.put(KEY_ESTADO_REQUISITOS, correcto);
		resultado.put(KEY_MENSAJE_REQUISITOS, mensaje);
		
		return resultado;
	}
	
	private Map<String, Object> validarExistenciaConcubinaDesdesHace5Anios(AsignacionNSS nss) {
		Map<String, Object> result = new HashMap<String, Object>();
		//indicadores
		Boolean correcto = true;
		//mensaje
		String mensaje = Constants.MOTIVO_APROBADO;
		//tipos de baja que se buscaran
		Long[] tiposBaja = {TipoBajaDerechohabienteEnum.DIVORCIO.getId(),TipoBajaDerechohabienteEnum.TERMINO_CONCUBINATO.getId()};
		List<Long> idsParentescos = new ArrayList<Long>();
		idsParentescos.add(ParentescoEnum.CONCUBINARIO.getId());
		idsParentescos.add(ParentescoEnum.CONYUGE.getId());
		
		List<GrupoFamiliar> bajas = grupoFamiliarDaoLocal.getIntegrantesEnBajaDesdeHaceXAnios(nss.getIdAsignacionNSS(), idsParentescos,5, tiposBaja);
		
		if(bajas != null && !bajas.isEmpty()) {
			correcto = false;
			mensaje = Constants.MENSAJE_ERROR_ANTECEDENTES_CONCUBINAS_CONYUGE;
		}
		
		//seteamos el resultado
		result.put(KEY_ESTADO_REQUISITOS, correcto);
		result.put(KEY_MENSAJE_REQUISITOS, mensaje);

		return result;
	}
	/**
	 * 
	 * @param nss
	 * @param padre
	 * @param idOrigenSolicitud
	 * @param validarApellidos - en el caso del registro se valida cuando el registro no es de adopcion o reconocimiento
	 * @return 
	 * @throws DerechohabientesBusinessException
	 */
	private Map<String, Object> validacionesPadres(AsignacionNSS nss, Fisica padre,Long idOrigenSolicitud, Boolean validarApellidos) throws DerechohabientesBusinessException{
		validarApellidos = validarApellidos == null ? false : validarApellidos;
		//Resultado
		Map<String, Object> resultado = new HashMap<String, Object>();
		//indicadores
		Boolean correcto = true;
		//mensaje
		String mensaje = Constants.MOTIVO_APROBADO;
		//parentesco a validad
		Long parentesco = ParentescoEnum.PADRES.getId();
		
		
		log.debug("Se empiezan a validar los requisitos minimos para padres que no tienen que ver con la circunscripcion");
		GrupoFamiliar asegurado = null;
		try {
			log.debug("Se bucara al asegurado con id: " + nss.getIdPersona());
			//Buscamos al asegurado para verificar edades
			asegurado = grupoFamiliarDaoLocal.getIntegranteSinVigencia(nss.getIdAsignacionNSS(), nss.getIdPersona());
			
			if(asegurado != null){
				log.debug("Se encontro al segurado y se verificara existencia de padres");
				//Verificamos si ya existe el numero maximo de padres
				log.debug("Se validadra que no haya mas de dos padres en el grupo");
				/*List<GrupoFamiliar> integrantes = null;				
				integrantes = grupoFamiliarDaoLocal.findGrupoFamiliarByParentesco(nss.getIdAsignacionNSS(), parentesco);*/
				Boolean numeroPadresPermitido = this.validarNumeroIntegrantesPorParentesco(nss.getIdAsignacionNSS(),parentesco);
				//El numero de padres es el maximo
				if(!numeroPadresPermitido){
					log.debug("Ya existe el numero maximo de padres");
					correcto = false;
					mensaje = Constants.NUMERO_MAXIMO_PADRES;
				} /*else if(integrantes.size() < 2){ //si no verificamos que el sexo de los pades sea diferente
					//TODO ya no se valida el sexo de los padres
					log.debug("Se validara que el sexo del padre no sea el mismo");
					for(GrupoFamiliar unIntegrante : integrantes){
						if(unIntegrante.getDerechohabiente().getSexo().getIdSexo().equals(idSexoDerechohabiente)){
							correcto = false;
							mensaje = Constants.SEXO_PADRES;
						}else{
							correcto = true;
							mensaje = Constants.MOTIVO_APROBADO;
						}
					}				
				}*/else{
					//Se verifica si se tiene que validar apellidos
					if(validarApellidos) {
						//El sexo del asegurado
						Long idSexoPadre = padre.getSexo().getIdSexo().longValue();
						//el apellido paterno del asegurado
						String apellidoPadre = this.getCadenaSinNSinEspacios(padre.getPrimerApellido());
						//el primer apellido de la persona a registrar como hijo(a)
						String primerApellidoAsegurado = this.getCadenaSinNSinEspacios(nss.getPrimerApellido());
						//el segundo apellido de la persona a registrar como hijo(a)
						String segundoApellidoAsegurado = this.getCadenaSinNSinEspacios(nss.getSegundoApellido());
						//si el sexo del asegurado es hombre
						if(idSexoPadre.equals(SexoEnum.HOMBRE.getId())) {
							//el primer apellido del integrante tiene que coincidir con el primer apellido del asegurado
							if(!primerApellidoAsegurado.equals(apellidoPadre)) {
								correcto = false;
								mensaje = Constants.MENSAJE_ERROR_APELLIDO_PADRE_H;
							}
						} else {//Si el sexo del asegurado es mujer
							//el segundo apellido del hijo debe coincidir con el apellido materno del asegurado
							if(!segundoApellidoAsegurado.equals(apellidoPadre)) {
								correcto = false;
								mensaje = Constants.MENSAJE_ERROR_APELLIDO_PADRE_M;
							}
						}
					} 
				}
			} else {
				correcto = false;
				mensaje = "No se encontr&oacute; al intregrante asegurado o pensionado dentro del grupo familiar.";
				log.debug("No se encontro al asegurado o pensionado");
			}
			
			//Se validan solicitudes registradas
			if(correcto) {
				Map<String, Object> validaciones = this.validarSolicitudRegistroRechazada(nss,parentesco);
				correcto = (Boolean) validaciones.get(KEY_ESTADO_REQUISITOS);
				mensaje = (String) validaciones.get(KEY_MENSAJE_REQUISITOS);
			
				//Si todo esta correcto verificaremos las edades el asegurado contra la del integrante a registrar
				if(correcto) {
					resultado = this.validacionesEdadPadresHijos(asegurado.getDerechohabiente(), padre, parentesco, idOrigenSolicitud);
					return resultado;
				}
				//requisitos = rechazoYdefuncion(requisitos, registroDerechohabiente, idPersona, idParentesco);
				//requisitos = personaInteresadaSolDao.validaTramiteRegistroConcubanaPadresPendiente(requisitos,idParentesco,sexoIntegrante, asignacionNss);				
			}
			
			
		} catch(DerechohabientesWebSserviceException e) {
			log.error("error al consultar el ws de vigencia", e);
			DerechohabientesBusinessException.throwException(e.getMessage());
		} catch(DerechohabientesBusinessException e) {
			log.error("error al consultar el ws de vigencia", e);
			DerechohabientesBusinessException.throwException(e.getMessage());
		} catch(Exception e) {
			log.error("Ocurrio un error desconocido",e);
			DerechohabientesBusinessException.throwException("Ocurrio un error al verificar si el candidato ya se encuentra registrado");
		}
		
		//seteamos el resultado
		resultado.put(KEY_ESTADO_REQUISITOS, correcto);
		resultado.put(KEY_MENSAJE_REQUISITOS, mensaje);
		
		return resultado;
	}
	
	private String getCadenaSinNSinEspacios(String cadena) {
		String cadena2 = "";
		
		if(cadena != null) {
			log.debug("La cadena sin quitas n ni espacios: " + cadena);
			cadena2 = cadena.trim().toUpperCase().replace('\u00D1', '#');
			log.debug("La cadena sin n ni espacios: " + cadena2);
		}
		return cadena2;
	}
	/**
	 * 
	 * @param idAsignacionNss
	 * @param idParentesco
	 * @return
	 */
	public Boolean validarNumeroIntegrantesPorParentesco(Long idAsignacionNss, Long idParentesco) throws DerechohabientesWebSserviceException{
		Boolean numeroIntegrantesValidos = true;
		Long numeroDeIntegrantes = 0L;
		numeroDeIntegrantes = grupoFamiliarDaoLocal.getNumeroDeIntegrantesPorParentesco(idAsignacionNss, idParentesco);
		
		//para el parentesco hijos no nos importa el estado
		if(idParentesco.equals(ParentescoEnum.HIJOS.getId())) {
			log.debug("Se validara el numero de hijos");
			//Verificamos cuantos hijos tiene registrado
			log.debug("El numero de integrantes hijos es: " + numeroDeIntegrantes);
			if(numeroDeIntegrantes.longValue() >= NUMERO_MAXIMO_HIJOS) {
				log.debug("El numero maximo de hijos fue sobrepasado");
				numeroIntegrantesValidos = false;
			}
		} else if(idParentesco.equals(ParentescoEnum.CONYUGE.getId()) || idParentesco.equals(ParentescoEnum.CONCUBINARIO.getId())) {
			log.debug("Se validara el numero de conyuges");
			
			if(numeroDeIntegrantes.longValue() >= NUMERO_MAXIMO_CONYUGE) {
				log.debug("El numero de conyuges por lo tanto no es valido");
				numeroIntegrantesValidos = false;
			}
		} else if(idParentesco.equals(ParentescoEnum.PADRES.getId())) {
			log.debug("Se valida el numero maximo de padres");
			
			if(numeroDeIntegrantes.longValue() >= NUMERO_MAXIMO_PADRES) {
				log.debug("El numero de padres tiene su maximo");
				numeroIntegrantesValidos = false;
			}
		}
		
		return numeroIntegrantesValidos;
	}
	
	public Boolean existePersonaConParentesco(Long idAsignacionNss, Long idParentesco,Integer estadoDerechohabiente, Boolean correccion, Long idPersona, Long subEstadoDerechohabiente) 
			throws DerechohabientesWebSserviceException {
		correccion = correccion!= null ? correccion : false;
		log.debug("Se validara el numero de conyuges");
		GrupoFamiliar integrante = null;
		if(estadoDerechohabiente != null) {
			List<GrupoFamiliar> integrantes = derechohabienteWSClientRemote.getGrupoFamiliarPorParentescoYEstado(idAsignacionNss, idParentesco.intValue(), estadoDerechohabiente);
			
			if(integrantes != null && !integrantes.isEmpty()) {
				if(correccion) {
					
					if(subEstadoDerechohabiente != null) {
						for(GrupoFamiliar inte: integrantes) {
							if(inte.getSubEstadoDerechohabiente() != null && inte.getSubEstadoDerechohabiente().getIdSubEstadoDerechohabiente().equals(subEstadoDerechohabiente)){
								integrante = inte;
								break;
							}
						}
						
						if(integrante == null) {
							return false;
						}
					} else {
						integrante = integrantes.get(0);
					}
					
					log.debug("El tramite es de correccion y se quiere cambiar el parentesco a conyuge, verificamos el id de la persona con el parentesco anterior");
					//Si el id de la persona corresponde, la dejamos pasar como si no existiera la concubina o conyuge
					if(integrante.getDerechohabiente().getIdPersona().equals(idPersona)){
						return false;
					} else { //Si no corresponden los ids quiere decir que ya hay optra concubina registrada que no es la persona a
						//la cual se le quiere cambiar el parentesco a conyuge
						return true;
					}
				} else {
					integrante = integrantes.get(0);
					
					if((subEstadoDerechohabiente != null &&integrante.getSubEstadoDerechohabiente().getIdSubEstadoDerechohabiente().equals(subEstadoDerechohabiente)) || subEstadoDerechohabiente == null){
						log.debug("El numero de conyuges o concubina activas o en suspension es: " + integrantes.size() + " por lo tanto no es valido");
						return true;
					}
				}
			}
		} else {
			Long numeroDeIntegrantes = grupoFamiliarDaoLocal.getNumeroDeIntegrantesPorParentesco(idAsignacionNss, idParentesco);
			if(numeroDeIntegrantes != null && numeroDeIntegrantes.intValue() > 0) {
				return true;
			}
		}
		
		return false;
	}
	
	
	
	private Boolean validaEsposaConcubinaBajaTSPI(Long idAsignacionNss) 
			throws DerechohabientesWebSserviceException {
		
		log.debug("Se validara el numero de conyuges/concubinas en baja que no tienen baja administrativa, divorcio o concubinato");
	
		
			List<GrupoFamiliar> integrantesConcubina = derechohabienteWSClientRemote.getGrupoFamiliarPorParentescoYEstado(
					idAsignacionNss, new Long(ParentescoEnum.CONCUBINARIO.getId()).intValue(), new Long(EstadoDerechohabienteEnum.BAJA.getId()).intValue());
			List<GrupoFamiliar> integrantesEsposa= derechohabienteWSClientRemote.getGrupoFamiliarPorParentescoYEstado(
					idAsignacionNss, new Long(ParentescoEnum.CONYUGE.getId()).intValue(), new Long(EstadoDerechohabienteEnum.BAJA.getId()).intValue());
			
			List<Long> idsEsposaConcubinaBaja = new ArrayList<Long>();
			if(integrantesEsposa != null && !integrantesEsposa.isEmpty()) {
					for(GrupoFamiliar baja: integrantesEsposa) {
						idsEsposaConcubinaBaja.add(baja.getDerechohabiente().getIdPersona());
					}	
			}
			if(integrantesConcubina != null && !integrantesConcubina.isEmpty()) {
				for(GrupoFamiliar baja: integrantesConcubina) {
					idsEsposaConcubinaBaja.add(baja.getDerechohabiente().getIdPersona());
				}	
			} 
			
			if(idsEsposaConcubinaBaja.isEmpty()) {
				return true;
			}
			log.debug("si tiene esposas o concubinas en baja y son [ " + idsEsposaConcubinaBaja.size()+ "] se validara si tienen o no bajas def concubinato o divorcio" );
			
			List<Long> tipoBajas= new  ArrayList <Long>();
			tipoBajas.add(TipoBajaDerechohabienteEnum.DEFUNCION.getId());
			tipoBajas.add(TipoBajaDerechohabienteEnum.TERMINO_CONCUBINATO.getId());
			tipoBajas.add(TipoBajaDerechohabienteEnum.DIVORCIO.getId());
			
			
			List <BajaDerechohabienteDto> bajas =	bajaDerechohabienteServiceLocal.getBajaDerechohabiente(idAsignacionNss, idsEsposaConcubinaBaja, tipoBajas, null);
					
			if(bajas == null || bajas.isEmpty())
				return false;
			
			/*
			List<Long> esposasFinaless = idsEsposaConcubinaBaja;
			for(BajaDerechohabienteDto baja : bajas) {
				for(int indice = 0; indice <= idsEsposaConcubinaBaja.size() ; indice++) {
					if(baja.getCveIdPersonaIntegrante().equals(idsEsposaConcubinaBaja.get(indice))) {
						esposasFinaless.remove(indice);
					}
				}
			}
			*/
			
			for(BajaDerechohabienteDto baja : bajas) {
				idsEsposaConcubinaBaja.remove(baja.getCveIdPersonaIntegrante());
			}
			
			if(idsEsposaConcubinaBaja.isEmpty()) {
				return true;
			}
		return false;
	}
	
	/**
	 * Metodo para validar si una persona ya se encuentra dentro del grupo familiar, este metodo solo es para el 
	 * registro de derechohabientes
	 */
	@Override
	public Map<String, Object> validaPersonaRegistrada(Fisica fisica, Long idAsignacionNss) throws DerechohabientesBusinessException {
		//Mapa con los resultados de las validaciones
		Map<String, Object> result =  new HashMap<String, Object>();
		log.debug("Entro a validar persona registrada por ventanilla");
		//Persona encontrada
		Fisica fisicaLocalizada = null;
		//El id de la persona
		Long idPersona = fisica.getIdPersona();
		List<Fisica> fisicasEncontradas = new ArrayList<Fisica>();
		String curpPersona = fisica.getCurp();
		try {
			fisicasEncontradas = personaFisicaServiceBusinessRemote.localizarPersonaFisicaPorDatosBasicosEnImssConFechaOMesYAniodeNacimiento(fisica);
		} catch (DatosInsuficientesParaConsultaException e) {
			log.error("no existen datos suficientes para la busqueda", e);
			throw new DerechohabientesBusinessException(ERROR_DE_DATOS_BUSQUEDA_PERSONAS);
		}
		//Si la persona trae id de persona se validara que esa persona no este registrada en el grupo familiar
		/*
		TODO Se comenta esta parte ya que no importa que tenga id, se deben de buscar todas las personas que
		que coincidan con los datos de la persona a registrar para verificar que ninguna este registrada ya en el grupo
		Mario Teran
		22/04/2015
		*/
		 
		/*if(idPersona != null) {
			log.debug("la persona trae el id: " + idPersona);
			fisicasEncontradas.add(fisica);
		} else {
			log.debug("la persona no trae id de persona, se procede a la busqueda de la misma en las entidades RENAPO e IMSS");
			//Si no trae id buscaremos a la personay la setearemos
			//Se vaalida si los datos de la persona estan registrados		
			try{
				fisicasEncontradas = serviceBusinessRemote.localizarPersonaFisica(fisica);
				result.put(KEY_ESTADO_REQUISITOS, true);
			} catch(CURPNoLocalizadoEnEntidadExternaException e) {
				log.error("Ocurrio un error al consultar el ws de personas", e);
				result.put(KEY_ESTADO_REQUISITOS, false);
				result.put(KEY_MENSAJE_REQUISITOS, e.getSituacion());
			}
			catch (ClienteWebserviceRenapoCurpException e) {
				log.error("Ocurrio un error al consultar el ws de personas", e);
				result.put(KEY_ESTADO_REQUISITOS, false);
				result.put(KEY_MENSAJE_REQUISITOS, e.getSituacion());
			} catch(Exception e) {
				log.error("Ocurrio un error al consultar el ws de personas", e);
				result.put(KEY_ESTADO_REQUISITOS, false);
				result.put(KEY_MENSAJE_REQUISITOS, e.getMessage());
			}
		}*/
		
		/** Persona localizada */
		if(fisicasEncontradas != null && !fisicasEncontradas.isEmpty()){	
			
			log.debug("Se encontraron " + fisicasEncontradas.size());
			
			List<Long> idPersonas = new ArrayList<Long>();
			
			for(Fisica fisicaE: fisicasEncontradas) {
				if(fisicaE.getIdPersona() != null) {
					idPersonas.add(fisicaE.getIdPersona());
				}
			}
			
			if(idPersonas.isEmpty()) {
				log.debug("No se encontraron personas con id, se seteara de nuevo a la persona de RENAPO");
				fisicaLocalizada = fisicasEncontradas.get(0);
			}  else {
				log.debug("Se buscara si la persona ya se encuentra en el grupo famliar");
				List<GrupoFamiliar> integrantes = grupoFamiliarDaoLocal.getIntegrantesGrupoFamiliarByAsignacionNss(idAsignacionNss, idPersonas, null);
				boolean personaLocalizada = false;
				if(integrantes != null && integrantes.size() > 0) {
					//se hace la validacion de baja administrativa en el grupo familiar
					for(GrupoFamiliar integrante :  integrantes ){
						
						if(integrante.getFechaRegistroBaja() == null){
							personaLocalizada = true;
							System.out.println("la persona ya se encuentra registrada en el grupo familiar");
							result.put(KEY_ESTADO_REQUISITOS, false);
							result.put(KEY_MENSAJE_REQUISITOS, "Esta persona ya se encuentra registrada dentro del grupo familiar");
							fisicaLocalizada = integrantes.get(0).getDerechohabiente();
							break;
						}
					}
					
				}  
				if(!personaLocalizada){
					System.out.println("la persona no se encuentra registrada en el grupo familiar");
					result.put(KEY_ESTADO_REQUISITOS, true);
					if(idPersona == null) {
						
						
						if(fisicasEncontradas.size() == 1) {
							Fisica fisica_0 = fisicasEncontradas.get(0);
							log.debug("el estatus de renapo es: " + fisica_0.getEstatusRenapo());
							boolean reemplazar = fisica_0.getEstatusRenapo() == null ? true : !fisica_0.getEstatusRenapo().equals(ESTATUS_RENAPO_INVALIDO);
							if(reemplazar) {
								log.debug("Se reemplaza a la persona con la que trae el id: " + fisica_0.getIdPersona());
								fisicaLocalizada = fisica_0;
								fisicaLocalizada.setFechaNacimiento(fisica.getFechaNacimiento());
							} else {
								log.debug("No se reemplaza a la persona");
								fisicaLocalizada = fisica;
							}
						} else {
							fisicaLocalizada = fisica;
						}
					} else {
						log.debug("No se reemplaza a la persona porque el id de la persona no es nulo");
						fisicaLocalizada = fisica;
					}
				}
			}
			
			fisicaLocalizada.setCorreoElectronico(fisica.getCorreoElectronico());
			fisicaLocalizada.setFacebook(fisica.getFacebook());
			fisicaLocalizada.setTelefonoFijo(fisica.getTelefonoFijo());
			fisicaLocalizada.setTelefonoMovil(fisica.getTelefonoMovil());
			fisicaLocalizada.setTwitter(fisica.getTwitter());
			
			fisicaLocalizada.setEstadoCivil(fisica.getEstadoCivil());
			fisicaLocalizada.setCurp(curpPersona);
			result.put("personaLocalizada", fisicaLocalizada);
		} else {
			result.put(KEY_ESTADO_REQUISITOS, true);
			result.put(KEY_MENSAJE_REQUISITOS, Constants.MOTIVO_APROBADO);
			result.put("personaLocalizada", fisica);
		}
		
		return result;
	}

	/**
	 * Metodo para validar que la edad de los integrantes hijos y padres no sen inconsistentes de acuerdo a las siguientes reglas:
	 * 1. Para el parentesco hijos, la edad del integrante a registrar no puede ser mayor a la del asegurado pensionado
	 * 2. Para el parentesco padres, la edad del integrante a registrar debe ser al menos 10 anios mayor que la del asegurado pensionado
	 * @param asegurado - Objeto persona que debe contar con la fecha de nacimiento o el mes y anio de nacimiento
	 * @param integrante  - Objeto fisica que debe contener al menos la fecha de necimiento
	 * @param idParentesco - El parentesco a validar solo es valido el parentesco hijos y padres
	 */
	@Override
	public Map<String, Object> validacionesEdadPadresHijos(Fisica asegurado,
			Fisica integrante, Long idParentesco, Long idOrigenSolicitud) throws IllegalArgumentException{
		//Se realizan las validaciones de los parametros necesarios
		validacionParametrosEdad(asegurado, integrante, idParentesco);
		//Map con el resultado de las validaciones
		Map<String, Object> resultado = new HashMap<String, Object>();
		//Fecha de nacimiento del asegurado
		Date fechaNacimientoAsegurado = asegurado.getFechaNacimiento();
		//Fecha de nacimiento del integrante
		Date fechaNacimientoIntegrante = integrante.getFechaNacimiento();
		//Se valida que parentesco es
		Boolean esHijo = idParentesco.equals(ParentescoEnum.HIJOS.getId());
		log.debug("El parentesco a validar es hijo? " + esHijo);
		//Variables de resultado
		Boolean correcto = true;
		Boolean aseguradoConFecha = true;
		String mensaje = Constants.MOTIVO_APROBADO;
		
		
		//Si la fecha de nacimiento del asegurado es nula, se procede a calcular una en base al mes y anio de nacimiento
		if(fechaNacimientoAsegurado == null) {
			aseguradoConFecha = false;
			//Mes y anio de nacimiento del asegurado
			Integer mesNacimiento = asegurado.getMesRegistroNac();
			Integer anioNacimiento = asegurado.getAnioRegistroNac();
			//Si el parnetesco es hijo el dia se pondra al ultimo dia del mes, si el parentesco es padres se pondra al primero
			Integer diaNacimiento = esHijo ? mesNacimiento.equals(2) ? 28 : 30 : 1;
				
			log.debug("La fecha de nacimiento del asegurado, pensionado es nula, se procede a comparar con el mes y anio registrados");
			log.debug("El mes de nacimiento es : " + mesNacimiento);
			//Verificamos si el mes de nacimiento es 0, de ser asi lo dejamos
			mesNacimiento = mesNacimiento != null ? (mesNacimiento == 0 ? 0 : mesNacimiento -1) : null;
			//Checamos si el anio es diferente de nul y el mes no, en ese caso seteamos el mes de nacimiento a 0
			if(anioNacimiento != null && mesNacimiento == null ){
				mesNacimiento = 0;
			}
			
			log.debug("El anio de nacimiento es: " + anioNacimiento);
			//Si la persona tiene mes y anio de nacimiento 
			if(mesNacimiento != null && anioNacimiento != null) {
				log.debug("Se creara una fecha de nacimimiento a partir del anio y mes de nacimiento");
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, anioNacimiento+1900);
				cal.set(Calendar.MONTH, mesNacimiento);
				cal.set(Calendar.DATE, diaNacimiento);
				
				fechaNacimientoAsegurado = cal.getTime();
				log.debug("La nueva fecha de nacimiento es: " + fechaNacimientoAsegurado);
			}
		}
		
		//Una vez que ya ha sido calculada la nueva fecha de nacimiento
		if(fechaNacimientoAsegurado == null && (idOrigenSolicitud.equals(OrigenSolicitudEnum.INTERNET.getId()) || 
				idOrigenSolicitud.equals(OrigenSolicitudEnum.PORTAL_CIUDADANO.getId()))) {
			correcto = false;
			mensaje = "La persona con parentesco Asegurado/pensionado no cuenta con fecha de nacimiento, por lo tanto no es " +
			"posible validar la diferencia de edades. Acuda a ventanilla para realizar el registro.";
		} else if(fechaNacimientoAsegurado != null) {
			Long edadAseg = DateUtils.getEdadRedondeadaEnAnios(fechaNacimientoAsegurado);
			log.debug("La edad del asegurado es: " +edadAseg);
			Long edadFam = DateUtils.getEdadRedondeadaEnAnios(fechaNacimientoIntegrante);
			log.debug("La edad del integrantes es: " + edadFam);

			//Validamos el parentesco a registrar
			if(esHijo) { // si el parentesco es hijo
				if(!DateUtils.validarEdadHijos(fechaNacimientoIntegrante, fechaNacimientoAsegurado, aseguradoConFecha)){
					log.debug("la edad del integrante hijo es mayor a la del asegurado");
					correcto = false;
					mensaje = "La edad de los integrantes hijos debe ser de al menos 10 a�os de diferencia con respecto a la edad del asegurado / pensionado.";
				}
			} else { //Si el parentesco es padres
//				if((edadFam.intValue() - edadAseg.intValue()) <= 10){ //la edad del inegrantes debe sera al menos 10 anios mayor que la del asegurado
				if(!DateUtils.validarEdadPadres(fechaNacimientoIntegrante, fechaNacimientoAsegurado)){
					log.debug("La difererencia de edades es menor a 10 a�os");
					correcto = false;
					mensaje = Constants.EDAD_PADRES;
				} 
			}
		} else {
			correcto = true;
			//Mensamos mensaje indicando que el asegurado pensionado no cuenta con fecha de nacimiento
			if(esHijo) {
				mensaje = "No se encontr&oacute; la fecha de nacimiento del asegurado(a) / pensionado. " +
				"Es responsabilidad del tramitador validar que la edad de los integrantes hijos" +
				" no sea mayor a la edad del asegurado / pensionado.";
			} else {
				mensaje = "No se encontr&oacute; la fecha de nacimiento del asegurado(a) / pensionado. " +
				"Es responsabilidad del tramitador validar que la edad del integrante padre o madre sea" +
				" al menos 10 a&ntilde;os mayor que la del asegurado / pensionado.";
			}
		}
		
		//seteamos el resultado
		resultado.put(KEY_ESTADO_REQUISITOS, correcto);
		resultado.put(KEY_MENSAJE_REQUISITOS, mensaje);
		
		return resultado;
	}
	
	/**
	 * Metodo para validar Si una persona que se va a registrar como concubina o conyuge
	 * ya se encuentra registrada en otro grupo famliar con parentesco conyuge o concubina
	 * @param idPersona - el id de la persona que se buscara en los grupos familiares como conyuge o concubina
	 * @param idAsignacionNssActual - se manda en caso de que no se quiera que se busque en el grupo familiar actual
	 * @return
	 */
	public Map<String, Object> validarExistenciaConcubinaConyugeEnOtroGrupo(Fisica integranteValidar, Long idAsignacionActual) throws Exception{
		
		log.debug("********entre al metodo para validar la concubina o esposa ****************");
		System.out.println("********entre al metodo para validar la concubina o esposa ****************");
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean correcto = true;
		String mensaje = Constants.MOTIVO_APROBADO;
		List<GrupoFamiliar> registrosEnOtroGF = null;
		//seteo de los estados que buscara la consulta
		List<Long> lstCveIdEstado = new ArrayList<Long>();
		lstCveIdEstado.add(EstadoDerechohabienteEnum.VIGENTE.getId());
		lstCveIdEstado.add(EstadoDerechohabienteEnum.BAJA.getId());
		
		//seteo de los parentescos que buscara la consulta
		List<Long> lstCveIdParentesco = new ArrayList<Long>();
		lstCveIdParentesco.add(ParentescoEnum.CONCUBINARIO.getId());
		lstCveIdParentesco.add(ParentescoEnum.CONYUGE.getId());
		
		List<Long> lstIdPersonaDerechohabiente =  new ArrayList <Long>();
		//De lo contrario ahora validaremos en caso de que el id de persona no sea nula, que la persona no este registrada como contyuge
		//o concubina (rio) en otro grupo familiar
		if(integranteValidar != null) {
			List<Fisica> lstPersonasBDTU = null;
			try {
				lstPersonasBDTU = personaFisicaServiceBusinessRemote.localizarPersonaFisicaPorDatosBasicosEnImssConFechaOMesYAniodeNacimiento(integranteValidar);
			} catch (DatosInsuficientesParaConsultaException e) {
				log.error("no existen datos suficientes para la busqueda", e);
			}
			if(lstPersonasBDTU != null && !lstPersonasBDTU.isEmpty()){
				for(Fisica localizada: lstPersonasBDTU ){
					lstIdPersonaDerechohabiente.add(localizada.getIdPersona());
				}
				
			}
			lstIdPersonaDerechohabiente.add(integranteValidar.getIdPersona());
			registrosEnOtroGF = null;
			registrosEnOtroGF = grupoFamiliarDaoLocal.findIntegrantesByParentescoEstado(lstIdPersonaDerechohabiente, lstCveIdParentesco, lstCveIdEstado,idAsignacionActual);
			
			if(registrosEnOtroGF != null && !registrosEnOtroGF.isEmpty()){
				for(GrupoFamiliar derechabienteEncontrado: registrosEnOtroGF){
					if(derechabienteEncontrado.getSubEstadoDerechohabiente().getIdSubEstadoDerechohabiente().longValue() == 
							SubestadoDerechohabienteEnum.SUSPENCION_ADMINISTRATIVA.getId() 
							|| derechabienteEncontrado.getEstadoDerechohabiente().getIdEstadoDerechohabiente().longValue() ==
							EstadoDerechohabienteEnum.VIGENTE.getId()){
						result.put(KEY_ESTADO_REQUISITOS, false);
						result.put(KEY_MENSAJE_REQUISITOS, Constants.PARENTESCO_REGISTRADO_ATLA_CORRECCION);
						return result;
					}
					
				}
				
			} 
		
			
		}
		//seteamos el resultado
		result.put(KEY_ESTADO_REQUISITOS, correcto);
		result.put(KEY_MENSAJE_REQUISITOS, mensaje);
		return result;
	}
	
	private Map<String,Object> validarSolicitudPendiente(AsignacionNSS asegurado, Long idParentesco, Long idTramite) {
		
		Map<String, Object> resultado = getMapResquisitos(true, Constants.MOTIVO_APROBADO);
		List<Long> tiposTramite = new ArrayList<Long>();
		Long idTipoTramite = this.getTipoTramitePorParentesco(idParentesco);
		tiposTramite.add(idTipoTramite);
		
		List<Long> estadosTramite = new ArrayList<Long>();
		estadosTramite.add(EstadoTramiteEnum.INICIADO.getId());
		estadosTramite.add(EstadoTramiteEnum.ESPERA_AUTORIZACION.getId());
		
		//Solo si no es hijo validamos que no tenga una solicitud de otro tipo de tramite
		if(!idParentesco.equals(ParentescoEnum.HIJOS.getId())) {
			try {
				//Buscamos las solicitudes dependiento del parentesco
				List<Solicitud> solicitudesRegistro = solicitudTramiteBusinessRemote.getSolicitudesPersona(null,
					tiposTramite, estadosTramite, null, null, asegurado.getIdPersona(), true, 1, true);
				//Si existen solicitudes validaremos los tramite
				if(solicitudesRegistro != null && !solicitudesRegistro.isEmpty()) {
					//Si el tramite viene nulo quiere decir que es diferente el tramite que se hace 
					if(idTramite == null) {
						resultado = this.getMapResquisitos(false, Constants.SOL_PEND_APR);
					} else {
						// de lo contrario scamos el tramite de la solicitud para validar por el id del tramite
						Tramite tramite = solicitudesRegistro.get(0).getTramites().get(0);
						//Si los tramites son diferentes mandamos el error
						if(!idTramite.equals(tramite.getTramiteId())) {
							resultado = this.getMapResquisitos(false, Constants.SOL_PEND_APR);
						}
					}
				}
			} catch(Exception e) {
				log.error("Ocurrio un error al consultar las solicitudes canceladas y rechazadas");
			}
		}
		
		return resultado;
	}
	
	/**
	 * Metodo para validar si el registro de concubina o padres no tiene un rechazo de ser asi, deben haber pasado minimo 
	 * 180 dias para volver a solicitaerlo
	 * @param registro
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Map<String,Object> validarSolicitudRegistroRechazada(AsignacionNSS asegurado, Long idParentesco) throws IllegalArgumentException{
		Map<String, Object> result = new HashMap<String, Object>();
		
		Boolean correcto = true;
		String mensaje = Constants.MOTIVO_APROBADO;
		List<Long> tiposTramite = null;
		List<Long> estadosTramite = new ArrayList<Long>();
		//estadosTramite.add(EstadoTramiteEnum.CERRADO.getId());
		log.debug("solo se buscaran las solicitudes en estado cancelado");
		estadosTramite.add(7L);
		List<Solicitud> solicitudesRegistro = null;
		Tramite tramite = null;
		
		//Las razones de rechazo que no deben tener los tramites
		List<Long> razonesResultadoInvalidas = new ArrayList<Long>();
		razonesResultadoInvalidas.add(RazonResultadoEnum.CONVIVENCI_DEPENDENCIA_NO_COMPROBADA.getId());
		razonesResultadoInvalidas.add(RazonResultadoEnum.DOCUMENTOS_APOCRIFOS.getId());
		razonesResultadoInvalidas.add(RazonResultadoEnum.IMPROCEDENCIA.getId());
		
		log.debug("El id de la persona del asegurado / pensionado es: " + asegurado.getIdPersona());
		
		//Verificamos el parentesco para buscar las solicitudes de registro de padres o concubinas
		if(idParentesco.equals(ParentescoEnum.PADRES.getId()) || idParentesco.equals(ParentescoEnum.MADRE.getId())) {
			tiposTramite = new ArrayList<Long>();
			tiposTramite.add(TipoTramiteEnum.REGISTRO_PADRES.getCodigo().longValue());
		
			try {
				
				solicitudesRegistro = solicitudTramiteBusinessRemote.getSolicitudesPersona(null,
					tiposTramite, estadosTramite, null, null, asegurado.getIdPersona(), true, 1, true);
			} catch(Exception e) {
				log.error("Ocurrio un error al consultar las solicitudes canceladas y rechazadas");
			}
			
		} else if(idParentesco.equals(ParentescoEnum.CONCUBINARIO.getId()) || idParentesco.equals(ParentescoEnum.CONCUBINA.getId())){
			tiposTramite = new ArrayList<Long>();
			tiposTramite.add(TipoTramiteEnum.REGISTRO_CONCUBINA_RIO.getCodigo().longValue());
			
			try {
				solicitudesRegistro = solicitudTramiteBusinessRemote.getSolicitudesPersona(null,
					tiposTramite, estadosTramite, null, null, asegurado.getIdPersona(), true, 1, true);
			} catch(Exception e) {
				log.error("Ocurrio un error al consultar las solicitudes canceladas y rechazadas");
			}
			
		} else {
			throw new IllegalArgumentException();
		}
		
		if(solicitudesRegistro != null && !solicitudesRegistro.isEmpty()) {
			
			tramite = this.getTramiteRegistroFromSolicitud(solicitudesRegistro.get(0));
			
			if(tramite!= null && tramite.getRazonResultado() != null && tramite.getRazonResultado().getIdRazonResultado() != null) {
				if(razonesResultadoInvalidas.contains(tramite.getRazonResultado().getIdRazonResultado())){
					Date fechaTramite = tramite.getFechaConclusion() != null ? tramite.getFechaConclusion() : tramite.getFechaPresentacion();
					//obtenemos cuantos dias han pasado desde el tramite
					int diasDesdeConclusion = DateUtils.getDaysBetweenDates(new Date(),fechaTramite);
					log.debug("El numero de dias que han pasado desde el rechazo es: " + diasDesdeConclusion);
					if(diasDesdeConclusion < 180){
						correcto = false;
						mensaje = Constants.TRAMITE_RECHAZO;
					}													
				}
			}
		}
		
		//seteamos el resultado
		result.put(KEY_ESTADO_REQUISITOS, correcto);
		result.put(KEY_MENSAJE_REQUISITOS, mensaje);
		
		return result;
	}
	
	@Override
	public Map<String, Object> validacionExistenciaPersonaTramiteBajaCiudadano(
			Long idAsignacionNss, String curp) {
		return this.validacionExistenciaPersonaTramitesCiudadano(idAsignacionNss, curp, false);
	}

	@Override
	public Map<String, Object> validacionExistenciaPersonaTramiteProrrogaCiudadano(
			Long idAsignacionNss, String curp) {
		return this.validacionExistenciaPersonaTramitesCiudadano(idAsignacionNss, curp, true);
	}

	/**
	 * Metodo privado para verficiar la ecxistecia de la persona en el grupo familiar en base a la curp
	 * @param idAsignacionNss
	 * @param curp
	 * @param prorroga
	 * @return
	 */
	private Map<String, Object> validacionExistenciaPersonaTramitesCiudadano(Long idAsignacionNss, String curp, Boolean prorroga) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<GrupoFamiliar> grupoFamiliar = null;
		GrupoFamiliar integranteEncontrado = null;
		Boolean correcto = true;
		String mensaje = "Todos los datos son correctos";
		
		try {
			//buscamos a los integrantes con la curp dentro del grupo familiar
			grupoFamiliar = grupoFamiliarServiceLocal.getIntegrantesPorCurp(idAsignacionNss, curp, true);
			//verificamos si la persona con la curp proporcionada se encuentra en el grupo
			if(grupoFamiliar != null && !grupoFamiliar.isEmpty()) {
				//si solo existe una persona con la curp
				if(grupoFamiliar.size() == 1) {
					//Se pone a la persona que se encontro
					integranteEncontrado = grupoFamiliar.get(0);
					//se valida estado de la persona
					if(integranteEncontrado.getEstadoDerechohabiente() == null) {
						correcto = false;
						mensaje = "No se pudo obtener la vigencia para el integrante con CURP:  <strong>" + curp + "</strong>";
					} else {
						if(!prorroga && integranteEncontrado.getEstadoDerechohabiente().getIdEstadoDerechohabiente().equals(EstadoDerechohabienteEnum.BAJA.getId())) {
							correcto = false;
							mensaje = "El integrante ya se encuentra dado de baja.";
						}
					}
				} else {
					//de lo contrario se manda un error ya que no se podria determinar a quien se le va a aplicar la prorroga
					correcto = false;
					mensaje = "La CURP est&aacute; registrada mas de una vez en el grupo familiar, por favor acuda a ventanilla a realizar el tr&aacute;mite.";
				}
			} else {
				//si la lista viene nula o vacia se manda un error
				correcto = false;
				mensaje = "No se encontr&oacute; a la persona con la CURP <strong>" + curp + "</strong> dentro del grupo familiar.";
			}
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			//si ocurre alguna excepcion al tratar de consultar a la persona se manda error
			correcto = false;
			mensaje = "Ocurrio un error al verificar la existencia de la persona en grupo";
		}		
		
		result = this.getMapResquisitos(correcto, mensaje);
		result.put("integrante", integranteEncontrado);
		
		return result;
	}

	private void validacionParametrosEdad(Fisica asegurado,
			Fisica integrante, Long idParentesco)throws IllegalArgumentException {
		
		if(asegurado == null) {
			throw new IllegalArgumentException("La persona asegurada es necesaria para validar la edad");
		}
		
		if(integrante == null) {
			throw new IllegalArgumentException("La persona a registrar es necesaria para validar la edad");
		}
		
		if(integrante.getFechaNacimiento() == null) {
			throw new IllegalArgumentException("La fecha de nacimiento del integrante a registrar es necesaria");
		}
		
		if(idParentesco == null) {
			throw new IllegalArgumentException("El parentesco es necesario para validar las edades");
		}
		
		if(!idParentesco.equals(ParentescoEnum.PADRES.getId()) && !idParentesco.equals(ParentescoEnum.HIJOS.getId())) {
			throw new IllegalArgumentException("Los parentescos validos son padres e hijos.");
		}
	}

	private TramiteRegistroDerechohabiente getTramiteRegistroFromSolicitud(Solicitud solicitud) {
		TramiteRegistroDerechohabiente registro = null;
		
		for(Tramite tramite: solicitud.getTramites()) {
			if(tramite instanceof TramiteRegistroDerechohabiente) {
				registro = (TramiteRegistroDerechohabiente) tramite;
				break;
			}
		}
		return registro;
	}
	
	private void lanzarExcepcionWsVigencia(DerechohabientesWebSserviceException e) throws DerechohabientesBusinessException{
		log.error("Ocurrio un error al consultar el ws de vigencia", e);
		String situacion = e.getMessage() != null ? e.getMessage() : "Ocurrio un error al consultar el ws de vigencia";
		DerechohabientesBusinessException.throwException(situacion, situacion );
	}
	
}
