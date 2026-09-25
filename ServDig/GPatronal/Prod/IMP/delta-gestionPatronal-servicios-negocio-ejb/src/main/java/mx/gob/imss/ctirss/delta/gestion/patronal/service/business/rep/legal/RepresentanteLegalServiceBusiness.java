package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.rep.legal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.domicilio.AsociarDomicilioException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.patronal.RepresentanteLegalInvalidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.patronal.RepresentanteLegalYaExisteException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.DiferenciasRENAPOContraSAT;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ConsultaPersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.business.rule.RuleServiceBusinessLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.business.solicitud.SolicitudServiceBusinessLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.SujetoObligadoServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.rep.legal.RepresentanteLegalServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.AfiliacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rep.legal.RepresentanteLegalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fiel;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Certificado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteMoral;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitRepresentanteLegal;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;

@Stateless(name="representanteLegalServiceBusiness" ,mappedName="representanteLegalServiceBusiness")
public class RepresentanteLegalServiceBusiness extends AbstractServiceBusiness
		implements RepresentanteLegalServiceBusinessRemote, RepresentanteLegalServiceBusinessLocal {
	
	
	@EJB
	private RepresentanteLegalServiceEntityLocal representanteLegalServiceEntity;
	@EJB
	private MediosContactoServiceBusinessRemote mediosContactoService;
	@EJB
	private RuleServiceBusinessLocal ruleServiceBusinessLocal;
	@EJB
	private SolicitudServiceBusinessLocal solicitudService;
	@EJB
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@EJB 
	private AfiliacionServiceBusinessRemote afiliacionService;
	@EJB
	private SujetoObligadoServiceEntityLocal sujetoObligadoEntity;
	@EJB 
	private PersonaBusinessRemote personaBusiness;
	@EJB
	private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
	@EJB
	private PersonaMoralBusinessRemote personaMoralBusiness;
	@EJB
	private DomicilioServiceBusinessRemote domicilioService;
	@EJB 
	private ConsultaPersonaFisicaServiceBusinessRemote consultaPersonaFisicaServiceBusinessRemote;
	
	
	@Override
	public Boolean isRepresentanteLegal(Long idPersona) {
		return sujetoObligadoEntity.isRepresentanteLegal(idPersona);
	}

	@Override
	public DatosSalidaPaginador<RepresentanteLegal> paginarRepresentanteLegal(
			DatosEntradaPaginador<RepresentanteLegal> datatablein) {
		return  representanteLegalServiceEntity.paginar(datatablein);
	}

	@Override
	public RepresentanteLegal getRepresentanteLegal(
			RepresentanteLegal representanteLegal) {
		RepresentanteLegal representanteLegal1 = null;
		try {
			representanteLegal1 = representanteLegalServiceEntity.get(representanteLegal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return representanteLegal1;
	}

	@Override
	public void agregarRepresentanteLegal(RepresentanteLegal representanteLegal)
			throws RepresentanteLegalInvalidoException,
			RepresentanteLegalYaExisteException {
		
		// 1. Validamos si se puede agregar este representante legal (no debera haber otro con el mismo id de persona)
		representanteLegalServiceEntity.validaExisteRepresentanteLegal(representanteLegal);
		try {
			// 2. Agregamos el Rep legal
			representanteLegalServiceEntity.persistir(representanteLegal);
		} catch (Exception e) {
			throw new RepresentanteLegalInvalidoException();
		}
	}


	@Override
	public void modificarRepresentanteLegal(
			RepresentanteLegal representanteLegal)
			throws RepresentanteLegalInvalidoException,
			RepresentanteLegalYaExisteException {
		try {
			//1. Validamos si se puede agregar el Representante Legal
			//representanteLegalServiceEntity.validaExisteRepresentanteLegal(representanteLegal);
			
			// 2. Actualizamos el RepresentanteLegal
			representanteLegalServiceEntity.actualizar(representanteLegal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.business.rep.legal.RepresentanteLegalServiceBusinessLocal#obtenerRepresentanteLegalPorSujetoObligado(java.lang.Long)
	 */
	@Override
	public List<RepresentanteLegal> obtenerRepresentanteLegalPorSujetoObligado(
			Long idPatronSujetoObligado) {
		RepresentanteLegal model = new RepresentanteLegal();
		model.setCveIdPatronSujetoObligado(idPatronSujetoObligado);
		log.debug("Se buscan representantes legales para el patrón .... "+idPatronSujetoObligado);
		List<RepresentanteLegal> repLegales=representanteLegalServiceEntity.consultarRepresentanteLegalPorSujetoObligado(model);
		List<RepresentanteLegal> repLegalesCompleted = new ArrayList<RepresentanteLegal>();
		if(repLegales!=null){
			for(RepresentanteLegal rLegal : repLegales){
				log.debug("Representante Legal encontrado: "+rLegal.getCveIdRepresentanteLegal()+" "+rLegal.getPersonaFisica().getNombre());
				Persona persona = new Persona();
				persona.setIdPersona(rLegal.getCveIdPersona());
				TipoPersona tipoPersona = new TipoPersona();
				//Los Representantes legales son todos personas físicas sin excepción
				tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
				persona.setTipoPersona(tipoPersona);
				List<? extends MedioContacto> mediosContacto = Collections.emptyList();
				try {
					mediosContacto = mediosContactoService.consultarMedioDeContactoPersona(persona);					
				} catch (PersonaSinMedioDeContactoException e) {
					mediosContacto = Collections.emptyList();
				}
				for(Object mCon : mediosContacto){
					MedioContacto auxMedio = (MedioContacto)mCon;
					if(TipoMedioContacto.TIPO_TELEFONO_FIJO.
							equals(auxMedio.getTipoMedioContacto().
									getIdTipoMedioContacto())){
						rLegal.getPersonaFisica().setTelefonoFijo((TelefonoFijo)mCon);
					}
					if(TipoMedioContacto.TIPO_TELEFONO_MOVIL.
							equals(auxMedio.getTipoMedioContacto().
									getIdTipoMedioContacto())){
						rLegal.getPersonaFisica().setTelefonoMovil((TelefonoMovil)mCon);
					}
					if(TipoMedioContacto.TIPO_CORREO_ELECTRONICO.
							equals(auxMedio.getTipoMedioContacto().
									getIdTipoMedioContacto())){
						rLegal.getPersonaFisica().setCorreoElectronico((CorreoElectronico)mCon);
					}
				}			
				repLegalesCompleted.add(rLegal);
			}
		}
		return repLegalesCompleted;
	}

	@Override
	public void eliminarRepresentanteLegal(
			RepresentanteLegal representanteLegal, String tipoPersonaFiscal)
			throws GestionPatronalBusinessException {		
		this.ruleServiceBusinessLocal.validarLimiteMinRepresentanteLegal(representanteLegal, tipoPersonaFiscal);		
		try {
			this.representanteLegalServiceEntity.borrar(representanteLegal);
		} catch (Exception e) {
			throw new GestionPatronalBusinessException(e.getMessage());
		}
	}

	@Override
	public void validaExisteRepresentanteLegal(
			RepresentanteLegal representanteLegal)
			throws RepresentanteLegalInvalidoException,
			RepresentanteLegalYaExisteException {		
		representanteLegalServiceEntity.validaExisteRepresentanteLegal(representanteLegal);
		
	}

	@Override
	public void actualizarRepresentantesLegales(Long cveIdSolicitud,
			List<RepresentanteLegal> representantes, OrigenSolicitudEnum origenSolicitud) {
		
		List<RepresentanteLegal> representantesActualizados= new ArrayList<RepresentanteLegal>();
		boolean actualizarDatosPersonaRepresentada=false;
		Long idPersonaRepresentadaNueva=null;
		for (RepresentanteLegal representanteLegal : representantes) {
			switch(representanteLegal.getAccion()){
				case AGREGAR:
					log.debug(">>> AGREGAR REPRESENTANTE LEGAL: " + representanteLegal);
					// posible inclusion de personasGPServiceBusiness para registrar a la p. fisica
				try {
					Long cveIdTipoPoder = representanteLegal.getCveIdTipoPoder();
					if (representanteLegal.getPersonaFisica().getIdPersona() != null && representanteLegal.getPersonaFisica().getIdPersona().intValue() > 0){
						representanteLegal.setFecRegistroAlta(new Date());
						if(representanteLegal.getTipoPersonaRepresentada().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
							if(representanteLegal.getPersonaFisicaRepresentada().getIdPersona()== null){//La persona no ha sido dada de alta en bdtu
								Persona personaCreada = personaBusiness.registrarPersonaConDatosSat(representanteLegal.getPersonaFisicaRepresentada(), true);
								personaFisicaServiceBusiness.generarICAPersonaFisica(personaCreada.getIdPersona(), personaCreada.getRfc(), 
								        representanteLegal.getPersonaFisicaRepresentada().getCurp());
//								representanteLegal.getPersonaFisicaRepresentada().setIdPersona(personaCreada.getIdPersona());
								representanteLegal.setPersonaFisicaRepresentada((Fisica)personaCreada);
								representanteLegal.setCveIdPersona(personaCreada.getIdPersona());
								idPersonaRepresentadaNueva=personaCreada.getIdPersona();
								actualizarDatosPersonaRepresentada=true; //Se actualizan datos cuando una persona es nueva en bdtu
							}else{
								if(origenSolicitud.equals(OrigenSolicitudEnum.INTERNET))
									personaBusiness.registrarDatosCertificadoPersona(representanteLegal.getPersonaFisicaRepresentada());
								
							}
							
							
							Fisica patronFisico = representanteLegal.getPersonaFisicaRepresentada();
							
							try {
								//Solo para validar si tiene domicilio fiscal
								domicilioService.consultarDomicilioFiscalPersona(patronFisico);
								
							} catch (DomicilioNoLocalizadoException e) {
								try {
									Fisica personaFisicaSAT = personaBusiness.buscarPersonaFisicaPorRfcEnSat(patronFisico.getRfc());
									DomicilioFiscal domicilioFiscal= personaFisicaSAT.getDomicilioFiscal();
									domicilioFiscal=domicilioService.registrarDomicilioFiscal(domicilioFiscal);
									domicilioService.asociarDomicilioFiscalPersonaFisica(domicilioFiscal.getClave(), patronFisico.getCveFisica());
								} catch (ClienteWebserviceSatRfcException e1) {
									log.debug("No se pudo consultar el servicio del SAT al agregar al socio, no se registrará su domicilio fiscal");
								} catch (DomicilioNoValidoException e1) {
									log.debug("Error al insertar el domicilio fiscal");
								} catch (AsociarDomicilioException e1) {
									log.debug("Error al asociar el domicilio fiscal");
								}


							}
								
						}else if(representanteLegal.getTipoPersonaRepresentada().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_MORAL)){
							if(representanteLegal.getPersonaMoralRepresentada().getIdPersona()== null){//La persona no ha sido dada de alta en bdtu
								Persona personaCreada = personaBusiness.registrarPersonaConDatosSat(representanteLegal.getPersonaMoralRepresentada(), true);
								personaMoralBusiness.generarICAPersonaMoral(personaCreada.getIdPersona(), personaCreada.getRfc());
								representanteLegal.getPersonaMoralRepresentada().setIdPersona(personaCreada.getIdPersona());
								representanteLegal.setPersonaMoralRepresentada((Moral)personaCreada);
								representanteLegal.setCveIdPersona(personaCreada.getIdPersona());
								idPersonaRepresentadaNueva=personaCreada.getIdPersona();
								actualizarDatosPersonaRepresentada=true; //Se actualizan datos cuando una persona es nueva en bdtu
							}else{
								if(origenSolicitud.equals(OrigenSolicitudEnum.INTERNET))
									personaBusiness.registrarDatosCertificadoPersona(representanteLegal.getPersonaMoralRepresentada());
								
							}
							
							
							Moral personaMoral = representanteLegal.getPersonaMoralRepresentada();
							
							try {
								//Solo para validar si tiene domicilio fiscal
								domicilioService.consultarDomicilioFiscalPersona(personaMoral);
								
							} catch (DomicilioNoLocalizadoException e) {
								try {
									Moral personaMoralSAT = personaBusiness.buscarPersonaMoralPorRfcEnSat(personaMoral.getRfc());
									if(personaMoralSAT!=null){//Se agregó porque existen rfc en bdtu que no están en el SAT.
										DomicilioFiscal domicilioFiscal= personaMoralSAT.getDomicilioFiscal();
										domicilioFiscal=domicilioService.registrarDomicilioFiscal(domicilioFiscal);
										domicilioService.asociarDomicilioFiscalPersonaMoral(domicilioFiscal.getClave(), personaMoral.getIdPersona());
									}else{
										log.debug("No se encontró la persona moral en el sat, no se registrará su domicilio fiscal");
									}
								} catch (ClienteWebserviceSatRfcException e1) {
									log.debug("No se pudo consultar el servicio del SAT al agregar al socio, no se registrará su domicilio fiscal");
								} catch (DomicilioNoValidoException e1) {
									log.debug("Error al insertar el domicilio fiscal");
								} catch (AsociarDomicilioException e1) {
									log.debug("Error al asociar el domicilio fiscal");
								}


							}
							
								
						}
						representanteLegal.setCveIdTipoPoder(cveIdTipoPoder);//Se preserva el tipo de poder seleccionado
						representantesActualizados.add(representanteLegal);
						this.representanteLegalServiceEntity.asociarRepresentanteLegal(representanteLegal);
						
					} else {
						throw new Exception(
								"NO PUEDE CONCLUIRSE EL TRAMITE PARA ESTE REPRESENTANTE LEGAL ["
										+ representanteLegal
										+ "] DATOS DE \'PERSONA\' INCOMPLETOS ["
										+ (representanteLegal
												.getPersonaFisica()
												.getIdPersona() != null ? "Id de persona fisica "
												+ representanteLegal
														.getPersonaFisica()
														.getIdPersona()
														.intValue()
												+ " indica valor por defecto por no haber sido proporcionado desde el servicio de \'PERSONAS\'"
												: "Id de persona fisica es nulo")
										+ ']');
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
				break;
				
				case MODIFICAR:
					log.debug(">>> MODIFICAR REPRESENTANTE LEGAL: " + representanteLegal);					
				try {
					representanteLegal.setFecRegistroActualizado(new Date());
					this.representanteLegalServiceEntity.actualizar(representanteLegal);					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
				case ELIMINAR:
					log.debug(">>> ELIMINAR REPRESENTANTE LEGAL: " + representanteLegal);
				try {
					//representanteLegal.setFecRegistroBaja(new Date()); ya lo realiza en borrar
					this.representanteLegalServiceEntity.borrar(representanteLegal);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
		
		//TODO
		if(actualizarDatosPersonaRepresentada)
			actualizarInformacionTramite(cveIdSolicitud, idPersonaRepresentadaNueva);
		
		if(origenSolicitud.equals(OrigenSolicitudEnum.INTERNET))
			actualizarFielRepresentante(cveIdSolicitud);
		
	}	
	
	private void actualizarInformacionTramite(Long cveIdSolicitud, Long idPersonaRepresentadaNueva){
		if(cveIdSolicitud==null)
			return;
		Solicitud solicitud = null;
		List<Tramite> listTramiteModif = new ArrayList<Tramite>();
		solicitud = solicitudService.consultarSolicitudPorId(cveIdSolicitud);
		for (Tramite tramiteActual : solicitud.getTramites()) {
			if (tramiteActual instanceof TramiteRepresentanteLegal) {
				TramiteRepresentanteLegal tramiteRP = (TramiteRepresentanteLegal) tramiteActual;
				
				if(tramiteRP.getFisicaRepresentada()!=null) {
					tramiteRP.getFisicaRepresentada().setIdPersona(idPersonaRepresentadaNueva);;
				} else {
					tramiteRP.getMoralRepresentada().setIdPersona(idPersonaRepresentadaNueva);;
					
				}
				listTramiteModif.add(tramiteRP);
			} 
		}
		solicitud.setTramites(listTramiteModif);
		
		solicitudService.actualizarTramites(solicitud);
	}
	
	private void actualizarFielRepresentante(Long cveIdSolicitud){
		if(cveIdSolicitud==null)
			return;
		Solicitud solicitud = null;
		solicitud = solicitudService.consultarSolicitudPorId(cveIdSolicitud);
		for (Tramite tramiteActual : solicitud.getTramites()) {
			if (tramiteActual instanceof TramiteRepresentanteLegal) {
				TramiteRepresentanteLegal tramiteRP = (TramiteRepresentanteLegal) tramiteActual;
				Persona persona = tramiteRP.getFisica();
				persona = agregarDatosFielFirmante(persona, solicitud.getCertificado());
				TipoPersona tipoPersona = new TipoPersona();
				tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
				persona.setTipoPersona(tipoPersona);
				personaBusiness.registrarDatosCertificadoPersona(persona);
				break;
			} 
		}
		
		
	}
	
	private Persona agregarDatosFielFirmante(Persona persona, Certificado certificado){
		Fiel fiel = new Fiel();
		fiel.setClaveSerial(certificado.getClaveSerial());
		fiel.setFechaValidaInicio(certificado.getFechaValidaInicio());
		fiel.setFechaValidaFin(certificado.getFechaValidaFin());
		persona.setFiel(fiel);
		
		return persona;
	}
	
	@Override
	public String gestionarTramiteRepresentanteLegal(Long idSolicitud,
			RepresentanteLegal representante, Usuario usuario) throws GestionPatronalBusinessException {
		SujetoObligado sujetoTramite = construirObjetoDeConsultaDeTramite(representante);
		Solicitud solicitud = null;
		if(idSolicitud!=null && idSolicitud > 0)
			solicitud = solicitudService.consultarSolicitudPorId(idSolicitud);
		Tramite tramite = solicitudService.obtenerTramitePorTipo(solicitud, TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL);
		if(tramite!=null){
			TramiteSujetoObligado tso = (TramiteSujetoObligado)tramite;
			sujetoTramite = tso.getSujetoObligado();
			List<RepresentanteLegal> movimientos = obtenerMovimientosDeTramite(sujetoTramite);
			validaExisteMovimientoPrevioParaRepresentante(representante, movimientos);
			movimientos.add(representante);
			actualizaMovimientosEnTramite(sujetoTramite, movimientos);
		}else{
			inicializaSujetoTramite(sujetoTramite, representante );
		}
		
		return (String)afiliacionService.gestionarTramiteActualizacionAfiliacion(idSolicitud,
				sujetoTramite, 
				TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL, usuario, false).get("operacion");
		
	}
	
	/**
	 * Se construye un objeto con los datos requeridos para la consulta de solicitud activa
	 * @author Hugo Martinez
	 * @Date 31/07/2012
	 * @param repLegal
	 * @return SujetoObligado
	 */
	private SujetoObligado construirObjetoDeConsultaDeTramite(RepresentanteLegal representante){
		SujetoObligado sujetoTramite = new SujetoObligado();
		
		Long idTipoPersona = representante.getTipoPersonaRepresentada().getIdTipoPersona();
		log.debug("Tipo persona owner del tramite: "+idTipoPersona);
		if(idTipoPersona.equals(TipoPersona.TIPO_PERSONA_FISICA)){
			Fisica pf = new Fisica();
			pf.setIdPersona(representante.getCveIdPersona());
			sujetoTramite.setFisica(pf);
			sujetoTramite.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
		}else if(idTipoPersona.equals(TipoPersona.TIPO_PERSONA_MORAL)){
			Moral pm = new Moral();
			pm.setIdPersona(representante.getCveIdPersona());
			sujetoTramite.setMoral(pm);
			sujetoTramite.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
		}
		return sujetoTramite;
	}
	
	/**
	 * Obtiene los movimientos de representantes legales del objeto
	 * tramite proporcionado en base al identificador de tipo de persona
	 * fiscal.
	 * @author Hugo Martinez
	 * @Date 31/07/2012
	 * @param sujetoTramite
	 * @return List<RepresentanteLegal>
	 */
	private List<RepresentanteLegal> obtenerMovimientosDeTramite(SujetoObligado sujetoTramite){
		List<RepresentanteLegal> movimientos = null;
		if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA))
			movimientos = sujetoTramite.getFisica().getRepresentantesLegales();
		else if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL))
			movimientos = sujetoTramite.getMoral().getRepresentantesLegales();
		return movimientos!=null ? movimientos : new ArrayList<RepresentanteLegal>();
	}
	
	/**
	 * Valida si existe un movimiento previo para el representante legal.
	 * La regla a validar es:
	 * Solo puede realizarse un movimiento (alta, baja, modificación) del 
	 * representante legal por trámite, es decir, no puede modificarse y agregarse
	 * el mismo representante dentro del mismo tramite.
	 * 
	 * @author Hugo Martinez
	 * @Date 31/07/2012
	 * @param representanteLegal
	 * @throws GestionPatronalBusinessException
	 */
	private void validaExisteMovimientoPrevioParaRepresentante(RepresentanteLegal representanteLegal, List<RepresentanteLegal> movimientos) throws GestionPatronalBusinessException{
		
		for(RepresentanteLegal movimiento:movimientos){
			if(movimiento.getCveIdPersona().equals(representanteLegal.getCveIdPersona())){
				throw new GestionPatronalBusinessException("Se tiene registrado un movimiento previo para este representante. " +
						"<br>Por favor verifique la información proporcionada.");
			}
		}
	}
	
	/**
	 * Actualiza la lista de movimientos de representantes legales dentro del tramite
	 * @author Hugo Martinez
	 * @Date 31/07/2012
	 * @param sujetoTramite
	 * @param movimientos
	 */
	private void actualizaMovimientosEnTramite(SujetoObligado sujetoTramite, List<RepresentanteLegal> movimientos){
		if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
			sujetoTramite.getFisica().setRepresentantesLegales(movimientos);
		}else if (sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
			sujetoTramite.getMoral().setRepresentantesLegales(movimientos);
		}
	}
	
	
	/**
	 * Complementa la información de un objeto sujeto
	 * obligado con la lista de movimientos de representantes legales
	 * para almacenarlos posteriormente como un trámite.
	 * @author Hugo Martinez
	 * @Date 31/07/2012
	 * @param sujetoTramite
	 * @param movimiento
	 */
	private void inicializaSujetoTramite(SujetoObligado sujetoTramite, RepresentanteLegal movimiento){
		List<RepresentanteLegal> movimientos = new ArrayList<RepresentanteLegal>();
		movimientos.add(movimiento);
		if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
			sujetoTramite.getFisica().setRepresentantesLegales(movimientos);
			sujetoTramite.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
		}else if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
			sujetoTramite.getMoral().setRepresentantesLegales(movimientos);
			sujetoTramite.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
		}
	}

	@Override
	public void eliminarMovimientosDelTramite(Long idSolicitud, RepresentanteLegal representante, Usuario usuario) {
		SujetoObligado sujetoTramite = construirObjetoDeConsultaDeTramite(representante);
		Solicitud solicitud = solicitudService.consultarSolicitudPorId(idSolicitud);
		Tramite tramite = solicitudService.obtenerTramitePorTipo(solicitud, TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL);
		
		//		Tramite tramite = solicitudService.obtenerTramiteDeSolicitudActivaPorTipo(sujetoTramite, 
//				TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL, usuario);
//		
		sujetoTramite = obtenerSujetoTramite(tramite, sujetoTramite);
		
		List<RepresentanteLegal> movimientos = obtenerMovimientosDeTramite(sujetoTramite);
		
		RepresentanteLegal movimientoAEliminar = null;
		for(RepresentanteLegal movimiento :movimientos){
			if(movimiento.getCveIdPersona().equals(representante.getCveIdPersona()))
				movimientoAEliminar = movimiento;
		}
		movimientos.remove(movimientoAEliminar);
		
		actualizaMovimientosEnTramite(sujetoTramite, movimientos);
		try {
			afiliacionService.gestionarTramiteActualizacionAfiliacion(idSolicitud,
					sujetoTramite, 
					TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL, null, false);
		} catch (GestionPatronalBusinessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public DatosSalidaPaginador<RepresentanteLegal> obtenerMovimientosDeTramite( DatosEntradaPaginador<RepresentanteLegal> datatablein, Usuario usuario, Long idSolicitud){
		
		SujetoObligado sujetoTramite = construirObjetoDeConsultaDeTramite(datatablein.getModelo());
		Tramite tramite = null;
		if(idSolicitud !=null && idSolicitud>0){
			Solicitud solicitud = solicitudService.consultarSolicitudPorId(idSolicitud);
			tramite = solicitudService.obtenerTramitePorTipo(solicitud, TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL);
		}
//		Tramite tramite = solicitudService.obtenerTramiteDeSolicitudActivaPorTipo(sujetoTramite, 
//				TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL, usuario);
		List<RepresentanteLegal> movimientos = null;
		if(tramite!= null){
			sujetoTramite = obtenerSujetoTramite(tramite, sujetoTramite);
			movimientos = obtenerMovimientosDeTramite(sujetoTramite);	
		}
		
		if(movimientos == null)
			movimientos = new ArrayList<RepresentanteLegal>();
		
		DatosSalidaPaginador<RepresentanteLegal> result = new DatosSalidaPaginador<RepresentanteLegal>();
		
		result.setiTotalRecords(0);
		result.setiTotalRecords(movimientos.size());
		result.setAaData(movimientos);
		return result;
	}
	
	
	private SujetoObligado obtenerSujetoTramite(Tramite tramite, SujetoObligado sujetoTramite){
		if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
			TramiteFisica tf = (TramiteFisica)tramite;
			sujetoTramite.setFisica(tf.getFisica());
		}else if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
			TramiteMoral tm = (TramiteMoral)tramite;
			sujetoTramite.setMoral(tm.getMoral());
		}
		return sujetoTramite;
	}
	
	@Override
	public List<RepresentanteLegal> obtenerRepresentantesLegalesPorPersona(
			Long cveIdPersona, TipoPersonaEnum tipoPersona) {		
		return representanteLegalServiceEntity.obtenerRepresentantesPorPersona(cveIdPersona, tipoPersona);
	}

	@Override
	public List<RepresentanteLegal> obtenerRepresentantesPorRFCMoral(String rfc) {
		return representanteLegalServiceEntity.obtenerRepresentantesPorRFCMoral(rfc);
	}	
	
	@Override
	public Fisica getPersonaByCurp(String curp) {
		log.debug("CONSULTANDO INFORMACION POR CURP "+curp);
		return representanteLegalServiceEntity.getPersonaByCurp(curp);
	}
	
	@Override
	public DatosSalidaPaginador<RepresentanteLegal> paginarRepresentanteLegalParaManejoDeTramite(
			DatosEntradaPaginador<RepresentanteLegal> datatablein) {
		return  representanteLegalServiceEntity.paginarParaManejoDeTramite(datatablein);
	}

	@Override
	public RepresentanteLegal obtenerRLporIdPersonaFisica(Long cveIdPersonaRepresentada, TipoPersonaEnum tipoPersona)
			throws Exception {
		//return this.representanteLegalServiceEntity.obtenerRLporIdPersonaFisica(cveIdPersona);
		return this.representanteLegalServiceEntity.obtenerRepresentantesPorPersona(cveIdPersonaRepresentada, tipoPersona).get(0);
			
	}

	@Override
	public RepresentanteLegal obtenerRLporTipoPatronYIdPersonaRepresentante(
			Long cveIdPersonaRepresentada, TipoPersonaEnum tipoPersona,
			Long idPersonaRepresentante) throws Exception {
		List<RepresentanteLegal> representantes = this.representanteLegalServiceEntity.obtenerRepresentantesPorPersona(cveIdPersonaRepresentada, tipoPersona);
		for(RepresentanteLegal representante: representantes){
			if(representante.getPersonaFisica().getIdPersona().equals(idPersonaRepresentante)){
				return representante;
			}
		}
		return null;
	}

	@Override
	public boolean esRepresentanteDePersona(Long cveIdPersonaRepresentante,
			Long cveIdPersonaRepresentada,
			TipoPersonaEnum tipoPersonaRepresentada) {
		return representanteLegalServiceEntity.esRepresentanteDeLaPersona(cveIdPersonaRepresentante, cveIdPersonaRepresentada, tipoPersonaRepresentada);
	}
	
	@Override
	public List<RepresentanteLegal> obtenerRepresentantesLegalesConActosAdmonPorPersona(
			Long cveIdPersona, TipoPersonaEnum tipoPersona) {		
		return representanteLegalServiceEntity.obtenerRepresentantesConActosAdmonPorPersona(cveIdPersona, tipoPersona);
	}
	
	@Override
	public List<Persona> obtenerPersonasRepresentadasPorRepresentanteLegal(
			Long cveIdPersona) {
		
		
		
		List<Fisica> fisicas = sujetoObligadoEntity.consultarRepresentadosFisicosPorRepresentanteLegal(cveIdPersona);
		List<Moral> morales = sujetoObligadoEntity.consultarRepresentadosMoralesPorRepresentanteLegal(cveIdPersona);
		
		List<Persona> personasRepresentadas = new ArrayList<Persona>();
		personasRepresentadas.addAll(fisicas);
		personasRepresentadas.addAll(morales);
		
		return personasRepresentadas;
//		List<SujetoObligado> sujetos = sujetoObligadoEntity.consultarSujetosRepresentadosPorRepresentanteLegalDatosBaseFisica(cveIdPersona);
//		List<SujetoObligado> sujetosM = sujetoObligadoEntity.consultarSujetosRepresentadosPorRepresentanteLegalDatosBaseMoral(cveIdPersona);
//		
//		sujetos.addAll(sujetosM);
//		Map<Long,Persona> personas = null;
//		List<Persona> personaList = new ArrayList<Persona>();
//		if(sujetos!= null & sujetos.size() > 0 ){
//			personas = new TreeMap<Long,Persona>();
//			for(SujetoObligado sujeto : sujetos){
//				if(sujeto.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
//					if(sujeto.getNombreComercial()==null || (sujeto.getNombreComercial()!=null &&  sujeto.getNombreComercial().equals("") )){
//						sujeto.setNombreComercial(sujeto.getFisica().getNombre()+" "
//								+ sujeto.getFisica().getPrimerApellido()+ " "
//								+sujeto.getFisica().getSegundoApellido());
//					}
//					Persona personaParaAgregar = sujeto.getFisica();
//					personaParaAgregar.setTipoPersona(new TipoPersona());
//					personaParaAgregar.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
//					personas.put(sujeto.getFisica().getIdPersona(), personaParaAgregar);
//				}else if(sujeto.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
//					if(sujeto.getNombreComercial()==null || (sujeto.getNombreComercial()!=null &&  sujeto.getNombreComercial().equals("") )){
//						sujeto.setNombreComercial(sujeto.getMoral().getRazonSocial());
//					}
//					Persona personaParaAgregar = sujeto.getMoral();
//					personaParaAgregar.setTipoPersona(new TipoPersona());
//					personaParaAgregar.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
//					personas.put(sujeto.getMoral().getIdPersona(), personaParaAgregar);
//				}
//			}
//		}
//		
//		if(personas!= null){
//			Iterator<Persona> itPersona = personas.values().iterator();
//			while( itPersona.hasNext() ){
//				personaList.add(itPersona.next());
//			}
//		}
//		return personaList;
	}

	@Override
	public List<RepresentanteLegal> obtenerRepresentadosPorIdPersona(
			Long idPersona) {
		List<RepresentanteLegal> lista = null;
		
		try {
			lista = representanteLegalServiceEntity.getRepresentadPorIdPersonaRepresentante(idPersona);
			/*lista = representanteLegalServiceEntity.obtenerRLsporIdPersonaFisica(idPersona);
			
			Map<Long,RepresentanteLegal> personas = null;
			if(lista!= null & lista.size() > 0 ){
				personas = new TreeMap<Long,RepresentanteLegal>();
				for(RepresentanteLegal sujeto : lista){
					if(sujeto.getSujetoObligado().getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
						personas.put(sujeto.getSujetoObligado().getFisica().getIdPersona(), sujeto);
					}else if(sujeto.getSujetoObligado().getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
						personas.put(sujeto.getSujetoObligado().getMoral().getIdPersona(), sujeto);
					}
				}
			}
			
			if(personas!= null){
				lista = new ArrayList<RepresentanteLegal>();
				Iterator<RepresentanteLegal> itPersona = personas.values().iterator();
				while( itPersona.hasNext() ){
					lista.add(itPersona.next());
				}
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lista;
	}

	@Override
	public void concluirSolicitudBajaRepresentanteLegal(Solicitud solicitud) throws SolicitudNoEncontradaException, TramiteNoEncontradoException, SolicitudException {
		// TODO Auto-generated method stub
		solicitud.setFechaConclusion(Calendar.getInstance().getTime());
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getCodigo());	

		if(solicitud.getSolicitudId() == null) {
			throw new SolicitudException("La solicitud no cuenta con identificador");
		} 
		//Actualizamos los estados de la solicitud
		solicitudBusinessRemote.actualizarEstados(solicitud);
		//Actualizamos los tramites
		for(Tramite tramite: solicitud.getTramites()) {
			tramite.getEstadoTramite().setIdEstadoTramitePersona(
					EstadoTramiteEnum.CERRADO.getCodigo());
			tramite.setFechaConclusion(Calendar.getInstance().getTime());
		}

		Tramite tramite = solicitud.getTramites().get(0);
		
		if(tramite.getTramiteId() == null) {
			throw new SolicitudException("Los tramite no contienen un identificador valido");
		}
		
		List<RepresentanteLegal> representantes = null;
		
		if(tramite instanceof TramiteFisica) {
			representantes = ((TramiteFisica) tramite).getFisica().getRepresentantesLegales();
		} else {
			representantes = ((TramiteMoral) tramite).getMoral().getRepresentantesLegales();
		}
		

		for(RepresentanteLegal repLeg: representantes) {
			representanteLegalServiceEntity.borrar(repLeg.getSujetoObligado(), repLeg.getPersonaFisica().getIdPersona(),false);
			/*RepresentanteLegal repL = new RepresentanteLegal();
			repL.setPersonaFisica(repLeg.getPersonaFisica());
			repL.setTipoPersonaRepresentada(new TipoPersona());
			
			if(!tramiteFisica) {
				repL.setCveIdPersona(((TramiteMoral) tramite).getMoral().getIdPersona());
				repL.getTipoPersonaRepresentada().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
				
			} else {
				if(repLeg.getSujetoObligado() == null) {
					if(tramiteFisica) {
						repL.setCveIdPersona(((TramiteFisica) tramite).getFisica().getIdPersona());
						repL.setTipoPersonaRepresentada(repLeg.getTipoPersonaRepresentada());
					} else {
						repL.setCveIdPersona(((TramiteMoral) tramite).getMoral().getIdPersona());
						repL.setTipoPersonaRepresentada(repLeg.getTipoPersonaRepresentada());
					}
					
				} else {
					SujetoObligado sujeto = repLeg.getSujetoObligado();
					
					if(sujeto.getFisica() == null) {
						repL.setCveIdPersona(sujeto.getMoral().getCveMoral());
						repL.getTipoPersonaRepresentada().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
					} else {
						repL.setCveIdPersona(sujeto.getFisica().getCveFisica());
						repL.getTipoPersonaRepresentada().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
					}
				}
			}
			
			try {
				representanteLegalServiceEntity.borrar(repL);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
	}

	@Override
	public void agregarRepresentantesLegalesARegistroPatronal(
			List<RepresentanteLegal> representantesLegales) {
		representanteLegalServiceEntity.asociarRepresentantesLegalesARegistroPatronal(representantesLegales);
	}

	@Override
	public void agregarRepresentanteLegalARegistroPatronal(
			RepresentanteLegal representantLegal) {
		representanteLegalServiceEntity.asociarRepresentanteLegalARegistroPatronal(representantLegal);
	}

	@Override
	public void concluirSolicitudBajaRepresentanteLegal(Long idSolcitud)
			throws SolicitudNoEncontradaException,
			TramiteNoEncontradoException, SolicitudException {
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(idSolcitud);
		solicitud = solicitudBusinessRemote.consultar(solicitud);
		concluirSolicitudBajaRepresentanteLegal(solicitud);
	}

	@Override
	public void afectarTramiteBajaRepresentantesLegales(Tramite tramite)
			throws GestionPatronalBusinessException {
		List<RepresentanteLegal> representantes = null;
		Persona personaRepresentada = null;
		if(tramite instanceof TramiteFisica) {
			TramiteFisica tf = ((TramiteFisica) tramite);
			personaRepresentada = tf.getFisica();
			representantes = tf.getFisica().getRepresentantesLegales();
			
		} else {
			TramiteMoral tm = ((TramiteMoral) tramite);
			personaRepresentada = tm.getMoral();
			representantes = tm.getMoral().getRepresentantesLegales();
		}
		
		for(RepresentanteLegal repLeg: representantes) {
			representanteLegalServiceEntity.registrarBajaRepresentante(repLeg);
		}		
	}
	
	@Override
	public void altaRepresentanteLegal(RepresentanteLegal representanteLegal) throws GestionPatronalBusinessException{
		
		//Validar si se inserta RL en DIT_PERSONA y/o DIT_PERSONA_FISICA
		//(El representado PF/PM ya debe exixtir en IMSS, pues lo que se realizar es el Alta de RL)
		validarRepresentanteLegalEnImss(representanteLegal);
		representanteLegalServiceEntity.altaRepresentanteLegal(representanteLegal);
	}
	
	@Override
	public void existeRelacionRepresentanteLegalPorIdentificadores(RepresentanteLegal 
			representanteLegal) throws RepresentanteLegalYaExisteException{
		
		DitRepresentanteLegal result = representanteLegalServiceEntity
			.consultarRelacionRepresentanteLegalPorIdentificadores(representanteLegal);
		if(result != null){
			throw new RepresentanteLegalYaExisteException();
		}
	}
	
	@Override
	public Fisica localizarRL(Fisica rl)  throws GestionPatronalBusinessException{
		Fisica fisica = new Fisica();
		fisica.setTipoPersona(new TipoPersona());
		fisica.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
		fisica.setRfc(rl.getRfc());
		fisica.setCurp(rl.getCurp());			
		fisica = buscarPersonaFisica(fisica);
		if(fisica==null)
			throw new GestionPatronalBusinessException("La persona física no fue localizada en la entidades externas SAT y RENAPO.");
		
		return fisica;
	}
	
	private void validarRepresentanteLegalEnImss(RepresentanteLegal representanteLegal)
			throws GestionPatronalBusinessException {
		Fisica personaFisica=representanteLegal.getPersonaFisica();
		String rfcRL = personaFisica.getRfc();
		boolean complementarDatos = false;
		if(personaFisica != null && personaFisica.getIdPersona()==null){
			try {				
				//Se valida nuevamente si ya existe la PF
				personaFisica=buscarPersonaFisica(personaFisica);	
				if(personaFisica!=null && personaFisica.getCveFisica() != null 
						&& personaFisica.getCveFisica().equals(-1L)){
					personaFisica.setCveFisica(null);
				}
								
				if(personaFisica.getIdPersona()==null){
					//Dar de alta PF
					personaFisica = personaFisicaServiceBusiness.registrar(personaFisica);
					//Actualizamos la informacion del socio
					personaFisicaServiceBusiness.generarICAPersonaFisica(personaFisica.getIdPersona(), personaFisica.getRfc(), 
					        personaFisica.getCurp());
					
				}else if(personaFisica.getCveFisica()==null){
					personaFisica = personaFisicaServiceBusiness.guardarPersonaFisica(personaFisica);
				}else{
					complementarDatos = true;					
				}
			} catch (AbstractException e) {
				e.printStackTrace();
				throw new GestionPatronalBusinessException("Se presentó una falla al registrar al Representante Legal como persona.");
			}
		}else if(personaFisica != null && personaFisica.getCveFisica()==null){
			//No esta como PF, valida nuevamente si ya existe la PF
			personaFisica=buscarPersonaFisica(personaFisica);
			if(personaFisica!=null && personaFisica.getCveFisica()!=null 
					&& personaFisica.getCveFisica().equals(-1L)){
				personaFisica.setCveFisica(null);
			}
			//Dar de Alta PF
			if(personaFisica.getCveFisica()==null){
				personaFisica = personaFisicaServiceBusiness.guardarPersonaFisica(personaFisica);
			}else{
				complementarDatos = true;
			}
		}
		if(complementarDatos && personaFisica.getIdPersona()!=null){
			personaFisicaServiceBusiness.complementarDatosPersonas(rfcRL, personaFisica.getIdPersona());
		}		
		representanteLegal.setPersonaFisica(personaFisica);
	}
	
	
	
	private Fisica buscarPersonaFisica(Fisica persona)throws GestionPatronalBusinessException{
		try {
			String rfc=persona.getRfc();
			persona = consultaPersonaFisicaServiceBusinessRemote.getPersonaByCurpImssEntidadesExternas(persona);
			if(persona!=null)
				persona.setRfc(rfc);
			
			return persona;
		} catch (ClienteWebserviceRenapoCurpException e) {
			log.error(e);
			throw new GestionPatronalBusinessException("No fue posible comunicar con la entidad RENAPO.");
		} catch (ClienteWebserviceSatRfcException e) {
			log.error(e);
			throw new GestionPatronalBusinessException("No fue posible comunicar con la entidad SAT.");
		} catch (ErrorComparacionDatosRENAPOException e) {
			log.error(e);
			throw new GestionPatronalBusinessException(e.getMessage());
		} catch (ErrorComparacionDatosSATException e) {
			log.error(e);
			throw new GestionPatronalBusinessException(e.getMessage());
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			log.error(e);
			throw new GestionPatronalBusinessException(e.getMessage());
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			log.error(e);
			throw new GestionPatronalBusinessException(e.getMessage());
		} catch (DiferenciasRENAPOContraSAT e) {
			log.error(e);
			throw new GestionPatronalBusinessException(e.getMessage());
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			log.error(e);
			throw new GestionPatronalBusinessException(e.getMessage());
		} catch (PersonaSinCalificacionesException e) {
			log.error(e);
			throw new GestionPatronalBusinessException(e.getMessage());
		}	
	}

	@Override
	public List<Persona> obtenerPersonasRepresentadasPorRepresentanteLegalDIC(
			Long cveIdPersona) {
		// TODO Auto-generated method stub
		Persona repre = null;
		List<Persona> personasRepresentadas = new ArrayList<Persona>();
		try {
			repre=personaBusiness.buscarPersnaPorID(cveIdPersona);
			if(repre!=null){
				personasRepresentadas.add(repre);	
			}else{
				return null;
			}
		} catch (PersonaNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Fisica> fisicas = sujetoObligadoEntity.consultarRepresentadosFisicosPorRepresentanteLegal(cveIdPersona);
		List<Moral> morales = sujetoObligadoEntity.consultarRepresentadosMoralesPorRepresentanteLegal(cveIdPersona);
		personasRepresentadas.addAll(fisicas);
		personasRepresentadas.addAll(morales);				
		return personasRepresentadas;
		
	}
	
}
