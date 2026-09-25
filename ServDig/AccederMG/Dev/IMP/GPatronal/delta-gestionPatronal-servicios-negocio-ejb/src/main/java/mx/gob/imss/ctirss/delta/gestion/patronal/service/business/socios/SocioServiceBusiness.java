package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.socios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.domicilio.AsociarDomicilioException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.DiferenciasRENAPOContraSAT;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.RegistroPersonaFisicaException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ConsultaPersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ConsultaPersonaMoralServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.socios.SocioServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.socios.SocioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.socios.SocioUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSocioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteMoral;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSocios;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;

import org.springframework.util.CollectionUtils;

@Stateless(name="socioServiceBusiness" ,mappedName="socioServiceBusiness")
public class SocioServiceBusiness extends AbstractServiceBusiness
		implements SocioServiceBusinessRemote, SocioServiceBusinessLocal {
	
	
	@EJB
	private SocioServiceEntityLocal socioServiceEntity;	
	@EJB
	private MediosContactoServiceBusinessRemote mediosContactoService;	
	@EJB
	private SolicitudServiceBusinessRemote solicitudService;	
	@EJB
	private SujetoObligadoServiceBusinessRemote sujetoObligadoService; 	
	@EJB
	private SujetoObligadoUtilityLocal sujetoObligadoUtility;
	@EJB 
	private ConsultaPersonaFisicaServiceBusinessRemote consultaPersonaFisicaServiceBusinessRemote;
	@EJB
	private ConsultaPersonaMoralServiceBusinessRemote consultaPersonaMoralServiceBusinessRemote;
	@EJB
	private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusinessRemote;
	@EJB
	private PersonaMoralBusinessRemote personaMoralBusinessRemote;
	@EJB
	private SocioUtilityLocal socioUtilityLocal;
	@EJB
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@EJB
	private transient PersonaBusinessRemote personaBusiness;
	@EJB
	private DomicilioServiceBusinessRemote domicilioService;
	
	
	public DatosSalidaPaginador<Socio> paginarSocios(
			DatosEntradaPaginador<Socio> datatablein) {		
		DatosSalidaPaginador<Socio> response = new DatosSalidaPaginador<Socio>();
		Integer iTotalRecords = 0;
		response.setAaData(new ArrayList<Socio>());
		if(datatablein!=null && datatablein.getModelo()!=null 
				&& datatablein.getModelo().getIdPersonaMoralPatron()!=null){
			log.info("Consultar Socios asociados (IdPersonaMoralPatron) - " 
				+ datatablein.getModelo().getIdPersonaMoralPatron());
			List<Socio> listaSocios = sociosPorIdPersonaMoralPatron(datatablein.getModelo());
			if(!CollectionUtils.isEmpty(listaSocios)){
				iTotalRecords = listaSocios.size();
				response.setAaData(listaSocios);
			}
		}
		response.setiTotalDisplayRecords(0);
		response.setiTotalRecords(iTotalRecords);
		log.info("Total Socios a paginar: " + iTotalRecords);		
		return response;
	}

	public List<Socio> sociosPorIdPersonaMoralPatron(Socio socio){
		List<Socio> listaSocios = socioServiceEntity.obtenerSociosPorIdPersonaMoralPatron(socio);
		if(!CollectionUtils.isEmpty(listaSocios)){
			for(Socio s : listaSocios){
				List<MedioContacto> mediosContactoF = null;
				if(s.getPersona()!=null){
					DomicilioFiscal domicilioF = sujetoObligadoUtility.convertDomicilioToDomicilioFiscal(
						sujetoObligadoService.obtenerDomicilioFiscal(s.getPersona()));
					try {
						mediosContactoF = mediosContactoService.consultarMediosFiscalesPersona(s.getPersona());
					} catch (PersonaSinMedioDeContactoException e) {
						log.error(e);
					}
					s.getPersona().setDomicilioFiscal(domicilioF);				
					s.getPersona().setMediosContactoFiscales(mediosContactoF);
				}
			}
		}
		return listaSocios;
	}
	
	public void afectarTramiteAltaSocios(Tramite tramite, Long idSolicitud) throws GestionPatronalBusinessException{
		//Obtener solicitud BD
		Solicitud solicitud = new Solicitud(idSolicitud);
		try {
			solicitud = solicitudBusinessRemote.consultarSinDatosTramite(solicitud);
		} catch (SolicitudNoEncontradaException se) {
			throw new GestionPatronalBusinessException(se.getMessage());
		}

        afectarTramiteAltaSocios(tramite, solicitud);

	}

    public void afectarTramiteAltaSocios(Tramite tramite, Solicitud solicitud) throws GestionPatronalBusinessException {
        //Procesar alta s�lo si se ha inicado el tramite.
        if(solicitud!= null && solicitud.getEstadoSolicitud().getIdEstadoSolicitud()
                .equals(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo())){
            TramiteSocios tramiteSocios = (TramiteSocios)tramite;
            Fisica personaFisica=null;
            Moral personaMoral=null;
            //Se valida si es socio Fisico/Moral y si existe (sino se dan de alta).
            if(tramiteSocios.getSociosFisico()!=null){
                //Socio Fisico
                personaFisica = tramiteSocios.getSociosFisico();
                if(personaFisica!=null && personaFisica.getCveFisica()!=null && personaFisica.getCveFisica().equals(-1L))
                    personaFisica.setCveFisica(null);

                if(personaFisica.getIdPersona()==null){
                    try {
                        //Se valida nuevamente si ya existe la PF
                        personaFisica=buscarPersonaFisica(personaFisica);
                        if(personaFisica!=null && personaFisica.getCveFisica()!=null && personaFisica.getCveFisica().equals(-1L))
                            personaFisica.setCveFisica(null);

                        if(personaFisica.getIdPersona()==null){
                            //Dar de alta PF
                            personaFisica = personaFisicaServiceBusinessRemote.registrar(personaFisica);
                            //Actualizamos la informacion del socio
                            personaFisicaServiceBusinessRemote.generarICAPersonaFisica(personaFisica.getIdPersona(), personaFisica.getRfc(),
                                    personaFisica.getCurp());
                            //Asociar tramite de alta de persona.
                            solicitudService.agregarTramiteASolicitud(solicitud.getSolicitudId(), creaTramitePersonaFisica(personaFisica));
                        }else if(personaFisica.getCveFisica()==null){
                            personaFisica = personaFisicaServiceBusinessRemote.guardarPersonaFisica(personaFisica);
                        }
                    } catch (RegistroPersonaFisicaException e) {
                        e.printStackTrace();
                        throw new GestionPatronalBusinessException("Se present� una falla al registrar el socio como persona f�sica");
                    } catch (AfectacionDatosPersonaException e) {
                        e.printStackTrace();
                        throw new GestionPatronalBusinessException("Se present� una falla al registrar el socio como persona f�sica");
                    }
                }else if(personaFisica.getCveFisica()==null){
                    //No esta como PF, valida nuevamente si ya existe la PF
                    personaFisica=buscarPersonaFisica(personaFisica);
                    if(personaFisica!=null && personaFisica.getCveFisica()!=null && personaFisica.getCveFisica().equals(-1L))
                        personaFisica.setCveFisica(null);

                    //Dar de Alta PF
                    if(personaFisica.getCveFisica()==null)
                        personaFisica = personaFisicaServiceBusinessRemote.guardarPersonaFisica(personaFisica);

                }

                try {
                    //Solo para validar si tiene domicilio fiscal
                    domicilioService.consultarDomicilioFiscalPersona(personaFisica);

                } catch (DomicilioNoLocalizadoException e) {
                    try {
                        Fisica personaFisicaSAT = personaBusiness.buscarPersonaFisicaPorRfcEnSat(personaFisica.getRfc());
                        DomicilioFiscal domicilioFiscal= personaFisicaSAT.getDomicilioFiscal();
                        domicilioFiscal=domicilioService.registrarDomicilioFiscal(domicilioFiscal);
                        domicilioService.asociarDomicilioFiscalPersonaFisica(domicilioFiscal.getClave(), personaFisica.getCveFisica());
                    } catch (ClienteWebserviceSatRfcException e1) {
                        log.debug("No se pudo consultar el servicio del SAT al agregar al socio, no se registrar� su domicilio fiscal");
                    } catch (DomicilioNoValidoException e1) {
                        log.debug("Error al insertar el domicilio fiscal");
                    } catch (AsociarDomicilioException e1) {
                        log.debug("Error al asociar el domicilio fiscal");
                    }


                }


                tramiteSocios.setSociosFisico(personaFisica);
            }else{
                //Socio Moral
                personaMoral = tramiteSocios.getSocioMoral();
                if(personaMoral.getIdPersona()==null){
                    //Se valida nuevamente si ya existe la PM
                    personaMoral=buscarPersonaMoral(personaMoral);
                    if(personaMoral.getIdPersona()==null){
                        //Dar de alta PM
                        personaMoral=personaMoralBusinessRemote.altaPersonaMoral(personaMoral);
                        //Actualizamos la informacion del socio
                        try {
                        	log.debug("antes de llegar a PersonaMoralBusiness es el que se comenta para generar PM");
                            personaMoralBusinessRemote.generarICAPersonaMoral_AP(personaMoral.getIdPersona(), personaMoral.getRfc());
                        } catch (AfectacionDatosPersonaException e) {
                            e.printStackTrace();
                            throw new GestionPatronalBusinessException("Se present� una falla al registrar el socio como persona moral");
                        }
                        //Asociar tramite de alta de persona.
                        solicitudService.agregarTramiteASolicitud(solicitud.getSolicitudId(), creaTramitePersonaMoral(personaMoral));
                    }
                }
                tramiteSocios.setSocioMoral(personaMoral);

                try {
                    //Solo para validar si tiene domicilio fiscal
                    domicilioService.consultarDomicilioFiscalPersona(personaMoral);

                } catch (DomicilioNoLocalizadoException e) {
                    try {
                        Moral personaMoralSAT = personaBusiness.buscarPersonaMoralPorRfcEnSat(personaMoral.getRfc());
                        DomicilioFiscal domicilioFiscal= personaMoralSAT.getDomicilioFiscal();
                        domicilioFiscal=domicilioService.registrarDomicilioFiscal(domicilioFiscal);
                        domicilioService.asociarDomicilioFiscalPersonaMoral(domicilioFiscal.getClave(), personaMoral.getIdPersona());
                    } catch (ClienteWebserviceSatRfcException e1) {
                        log.debug("No se pudo consultar el servicio del SAT al agregar al socio, no se registrar� su domicilio fiscal");
                    } catch (DomicilioNoValidoException e1) {
                        log.debug("Error al insertar el domicilio fiscal");
                    } catch (AsociarDomicilioException e1) {
                        log.debug("Error al asociar el domicilio fiscal");
                    }


                }

            }

            Socio socio = new Socio();
            socio.setIdPersonaMoralPatron(tramiteSocios.getPatron().getIdPersona());
            socio.setPersonaFisica(personaFisica);
            socio.setPersonaMoral(personaMoral);
            socio = socioServiceEntity.altaSocio(socio);
            log.info("Alta de Socio " + socio.getIdSocio());
        }
    }
	
	public void afectarTramiteBajaSocios(Tramite tramite, Long idSolicitud) throws GestionPatronalBusinessException{
		TramiteSocios tramiteSocios = (TramiteSocios)tramite;		
		List<Socio> listaBajaSocios = tramiteSocios.getListaSocios();
		if(!CollectionUtils.isEmpty(listaBajaSocios)){
			for(Socio socio : listaBajaSocios){
				log.info("Baja de Socio " + socio.getIdSocio());
				socioServiceEntity.bajaSocio(socio);
			}
		}		
	}
	
	public Socio localizarSocioAlta(Socio socio)throws GestionPatronalBusinessException{
		//Validar: 1) La empresa no debe ser socia de si misma. 2) Socio ya registrado.
		socioUtilityLocal.validarSocioAlta(socio);
		//Localizar socio
		if(socio.getTipoSocio().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
			Fisica fisica = new Fisica();
			fisica.setTipoPersona(new TipoPersona());
			fisica.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
			fisica.setRfc(socio.getRfc());
			fisica.setCurp(socio.getCurp());			
			fisica = buscarPersonaFisica(fisica);
			if(fisica==null)
				throw new GestionPatronalBusinessException("La persona f�sica no fue localizada en la entidades externas SAT y RENAPO.");
				
			socio.setPersonaFisica(fisica);
			socio.setPersonaMoral(null);					
		}else if(socio.getTipoSocio().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_MORAL)){
			Moral moral = new Moral();
			moral.setTipoPersona(new TipoPersona());
			moral.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
			moral.setRfc(socio.getRfc());			
			moral = buscarPersonaMoral(moral);
			if(moral==null)
				throw new GestionPatronalBusinessException("La persona moral no fue localizada en la entidad externa SAT.");
			socio.setPersonaMoral(moral);
			socio.setPersonaFisica(null);			
		}
		return socio;
	}
	
	public Solicitud generarSolicitudAltaSocio(Socio socio, OrigenSolicitudEnum origenSolicitud,
			Usuario usuario)throws GestionPatronalBusinessException{
		Solicitud solicitud = socioUtilityLocal
			.generarSolicitudAltaSocio(socio, origenSolicitud, usuario);
		try {
			solicitud = solicitudBusinessRemote.crear(solicitud);
		} catch (SolicitudNoValidaException e) {
			log.error(e);
			throw new GestionPatronalBusinessException(e.getMessage());
		}		
		return solicitud;
	}
	
	public Solicitud generarSolicitudBajaSocio(Socio socio, List<Socio> listaSocios, 
			OrigenSolicitudEnum origenSolicitud, Usuario usuario)throws GestionPatronalBusinessException{
		Solicitud solicitud = socioUtilityLocal
			.generarSolicitudBajaSocio(socio, listaSocios, origenSolicitud, usuario);
		try {
			solicitud = solicitudBusinessRemote.crear(solicitud);
		} catch (SolicitudNoValidaException e) {
			log.error(e);
			throw new GestionPatronalBusinessException(e.getMessage());
		}		
		return solicitud;
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
	
	private Moral buscarPersonaMoral(Moral persona)throws GestionPatronalBusinessException{
		try {
			persona = consultaPersonaMoralServiceBusinessRemote.consultarPersonaMoralPorRFCEnIMSSySAT_AP(persona);
			return persona;
		} catch (AbstractException e) {
			log.error(e);
			throw new GestionPatronalBusinessException(e.getMessage());
		}		
	}
	
	private TramiteFisica creaTramitePersonaFisica(Fisica personaFisica){
		TramiteFisica tpf =  new TramiteFisica();
		tpf.setFisica(personaFisica);
		tpf.setEstadoTramite(new EstadoTramite());
		tpf.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
		tpf.setTipoTramite(new TipoTramite());
		tpf.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.REGISTRO_DE_PERSONA.getCodigo());
		return tpf;
	}
	
	private TramiteMoral creaTramitePersonaMoral(Moral personaMoral){
		TramiteMoral tpm =  new TramiteMoral();
		tpm.setMoral(personaMoral);
		tpm.setEstadoTramite(new EstadoTramite());
		tpm.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
		tpm.setTipoTramite(new TipoTramite());
		tpm.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.REGISTRO_PERSONA_MORAL.getCodigo());
		return tpm;
	}


	
	
	
	@Deprecated
	public Socio agregarSocio(Socio oForm) throws Exception {		
		if (oForm.getEsPersonaFisica()) {
			socioServiceEntity.validaExisteSocioFisico(oForm);
			oForm = socioServiceEntity.agregarSocioFisico(oForm);
		} else {
			socioServiceEntity.validaExisteSocioMoral(oForm);
			oForm = socioServiceEntity.agregarSocioMoral(oForm);
		}		
		return oForm;		
	}

	@Deprecated
	public Socio modificarSocio(Socio oForm) {
		if (oForm.getEsPersonaFisica()) {
			oForm = socioServiceEntity.modificarSocioFisico(oForm);
		} else {
			oForm = socioServiceEntity.modificarSocioMoral(oForm);
		}
		return oForm;
	}

	@Deprecated
	public Socio getSocio(Socio socio) {		
		/*if (socio.getTipoSocio().getIdTipoPersona().intValue() == 1){ // s. fisico			
		} else if(socio.getTipoSocio().getIdTipoPersona().intValue() == 2){ // s. moral			
		}else if (socio.getTipoSocio().getIdTipoPersona().intValue() == 3){ // s. fideicomiso			
		}*/		
		return this.socioServiceEntity.getSocio(socio);
	}

	@Deprecated
	public void actualizarSocios(List<Socio> socios) {
		for (Socio socio : socios) {
			if(socio.getTipoSocio().getIdTipoPersona().equals(TipoSocioEnum.FISICO.getValor())){
				switch(socio.getAccion()){
					case AGREGAR:						
						System.err.println("Agregamos socio fisico: " + socio);
						socio.setFecRegistroAlta(new Date());
						
//						if(!socio.getEsNacional() && !socio.getEsDomicilioNacional()){
//							System.err.println("Es socio extranjero...");
//							socioServiceEntity.agregarSocioExtranjero(socio);
//						}else{
							socioServiceEntity.agregarSocio(socio);
//						}
					break;
					case MODIFICAR:
						System.err.println("Modificamos socio fisico: " + socio);
						socioServiceEntity.modificarSocio(socio);
					break;
					case ELIMINAR:
						System.err.println("Eliminamos socio fisico: " + socio);
						socioServiceEntity.eliminarSocio(socio);
					break;
				}
			} else if(socio.getTipoSocio().getIdTipoPersona().equals(TipoSocioEnum.MORAL.getValor())){
				switch(socio.getAccion()){
					case AGREGAR:
						System.err.println("Agregamos socio moral: " + socio);
						socio.setFecRegistroAlta(new Date());
						socioServiceEntity.agregarSocioMoral(socio);
					break;
					case MODIFICAR:
						System.err.println("Modificamos socio moral: " + socio);
						socioServiceEntity.modificarSocio(socio);
					break;
					case ELIMINAR:
						System.err.println("Eliminamos socio moral: " + socio);
						socioServiceEntity.eliminarSocio(socio);
					break;
				}
			} else if(socio.getTipoSocio().getIdTipoPersona().equals(TipoSocioEnum.FIDEICOMISO.getValor())){
				switch(socio.getAccion()){
					case AGREGAR:
						System.err.println("Agregamos socio fideicomiso: " + socio);
						socio.setFecRegistroAlta(new Date());
						socioServiceEntity.agregarSocioFideicomiso(socio);
					break;
					case MODIFICAR:
						System.err.println("Modificamos socio fideicomiso: " + socio);
						socioServiceEntity.modificarSocio(socio);
					break;
					case ELIMINAR:
						System.err.println("Eliminamos socio fideicomiso: " + socio);
						socioServiceEntity.eliminarSocio(socio);
					break;
				}
			}
		}
	}
	
	@Deprecated
	public List<Socio> obtenerSociosPorSujetoObligado(Long cveIdSujetoObligado) {
		
		Socio model = new Socio();
		model.setCveIdPatronSujetoObligado(cveIdSujetoObligado);
		System.err.println("Se buscan socios para el patr�n .... "+cveIdSujetoObligado);
		
		List<Socio> socios=socioServiceEntity.consultarSociosPorPerosna(model);
		
		List<Socio> sociosCompleted = new ArrayList<Socio>();
		if(socios!=null){
			for(Socio socio : socios){
				System.out.println("Socio encontrado: "+socio.getIdSocio() + ", RFC: " + socio.getRfc());
				Persona persona = new Persona();
				TipoPersona tipoPersona = new TipoPersona();
				
				persona.setIdPersona(socio.getIdPersona());
//				tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_?);
				
				
				
				List<? extends MedioContacto> mediosContacto = Collections.emptyList();
				try {
					if(socio.getIdPersona()!=null){
						Persona personaConsultaDomicilio = null;
						if(socio.getTipoSocio().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
							personaConsultaDomicilio = new Fisica();
							tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
						}else if(socio.getTipoSocio().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_MORAL)
								||socio.getTipoSocio().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FIDEICOMISO)){
							personaConsultaDomicilio = new Moral();
							tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
						}
						personaConsultaDomicilio.setIdPersona(socio.getIdPersona());
						Domicilio domicilio = sujetoObligadoService.obtenerDomicilioFiscal(personaConsultaDomicilio);
						if(domicilio!=null){
							System.out.println("Si hay domicilio fiscal para el socio: "+socio.getNombres());
							DomicilioFiscal domFiscal = sujetoObligadoUtility.convertDomicilioToDomicilioFiscal(domicilio);
							System.out.println("domicilio fical: "+domFiscal);
							socio.setDomicilioFiscal(domFiscal);
						}else{
							System.out.println("No hay domicilio fiscal para el socio: ");
						}
						System.out.println("Consultare medios de contacto para socio: " + socio.getIdPersona());
						mediosContacto = mediosContactoService.consultarMedioDeContactoPersona(persona);
						System.out.println("Obtuve medios de contacto");
					}
				} catch (PersonaSinMedioDeContactoException e) {
					mediosContacto = Collections.emptyList();
					System.out.println("La persona no tiene medios de contacto");
					log.debug("La persona no tiene medios de contacto");
				} catch(Exception e){
					e.printStackTrace();
					mediosContacto = Collections.emptyList();
					System.out.println("La persona no tiene medios de contacto o error en domicilio fiscal");
					log.debug("La persona no tiene medios de contacto");
				}
				for(Object mCon : mediosContacto){
					MedioContacto auxMedio = (MedioContacto)mCon;
					System.out.println("Verificando Telefono Fijo");
					if(TipoMedioContacto.TIPO_TELEFONO_FIJO.
							equals(auxMedio.getTipoMedioContacto().
									getIdTipoMedioContacto())){
						System.out.println("Agregando Telefono Fijo");
						socio.getPersona().setTelefonoFijo((TelefonoFijo)mCon);
					}
					System.out.println("Verificando Telefono Movil");
					if(TipoMedioContacto.TIPO_TELEFONO_MOVIL.
							equals(auxMedio.getTipoMedioContacto().
									getIdTipoMedioContacto())){
						System.out.println("Agregando Telefono Movil");
						socio.getPersona().setTelefonoMovil((TelefonoMovil)mCon);
					}
					System.out.println("Verificando Correo Electronico");
					if(TipoMedioContacto.TIPO_CORREO_ELECTRONICO.
							equals(auxMedio.getTipoMedioContacto().
									getIdTipoMedioContacto())){
						System.out.println("Agregando Correo");
						socio.getPersona().setCorreoElectronico((CorreoElectronico)mCon);
					}
				}
				System.out.println("domicilio fical del socio: "+socio.getDomicilioFiscal());
				sociosCompleted.add(socio);
			}
		}
		return sociosCompleted;
	}

	@Deprecated
	public DatosSalidaPaginador<Socio> obtenerMovimientosDeTramite(
			DatosEntradaPaginador<Socio> datatablein, Usuario usuario, Long idSolicitud) {
		SujetoObligado sujetoTramite = construirObjetoDeConsultaDeTramite(datatablein.getModelo());
		
		Tramite tramite = null;
		if(idSolicitud!=null && idSolicitud > 0){
			Solicitud solicitud = solicitudService.consultarSolicitudPorId(idSolicitud);
			tramite = solicitudService.obtenerTramitePorTipo(solicitud, TipoTramiteEnum.ACTUALIZACION_SOCIO);
		}
		
//		Tramite tramite = solicitudService.obtenerTramiteDeSolicitudActivaPorTipo(sujetoTramite, 
//				TipoTramiteEnum.ACTUALIZACION_SOCIO, usuario);
		List<Socio> movimientos = null;
		if(tramite!= null){
			sujetoTramite = obtenerSujetoTramite(tramite, sujetoTramite);
			movimientos = obtenerMovimientosDeTramite(sujetoTramite);	
		}
		
		if(movimientos == null)
			movimientos = new ArrayList<Socio>();
		
		DatosSalidaPaginador<Socio> result = new DatosSalidaPaginador<Socio>();
		
		result.setiTotalRecords(0);
		result.setiTotalRecords(movimientos.size());
		result.setAaData(movimientos);
		return result;
	}

	@Deprecated
	private List<Socio> obtenerMovimientosDeTramite(SujetoObligado sujetoTramite) {
		List<Socio> movimientos = null;
		if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA))
			movimientos = sujetoTramite.getFisica().getSocios();
		else if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL))
			movimientos = sujetoTramite.getMoral().getSocios();
		return movimientos!=null ? movimientos : new ArrayList<Socio>();
	}

	@Deprecated
	private SujetoObligado obtenerSujetoTramite(Tramite tramite,
			SujetoObligado sujetoTramite) {
		// TODO 
		if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
			TramiteFisica tf = (TramiteFisica)tramite;
			sujetoTramite.setFisica(tf.getFisica());
		}else if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
			TramiteMoral tm = (TramiteMoral)tramite;
			sujetoTramite.setMoral(tm.getMoral());
		}
		return sujetoTramite;
	}

	@Deprecated
	private SujetoObligado construirObjetoDeConsultaDeTramite(Socio socio) {
		SujetoObligado sujetoTramite = new SujetoObligado();
		Long idTipoPersona = 2L;  // TODO esto que?, falta ver lo de los putos fideicomiso y extranjero
		
		if(idTipoPersona.equals(TipoPersona.TIPO_PERSONA_FISICA)){
			Fisica pf = new Fisica();
			pf.setIdPersona(socio.getIdPersona());
			sujetoTramite.setFisica(pf);
			sujetoTramite.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
		}else if(idTipoPersona.equals(TipoPersona.TIPO_PERSONA_MORAL)){
			Moral pm = new Moral();
			pm.setIdPersona(socio.getIdPersona());
			sujetoTramite.setMoral(pm);
			sujetoTramite.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
		}
		return sujetoTramite;
	}
	
	@Deprecated
	public void eliminarSocio(Socio socio) {
		// TODO Auto-generated method stub		
	}
	
	@Deprecated
	public boolean existenAsignacionesAnterioresSocioPatron(Socio socio) {
		return this.socioServiceEntity.existenAsignacionesAnterioresSocioPatron(socio);
	}
	
	public List<MedioContacto> getMediosPersona(Persona persona) {
		List<MedioContacto> mediosContactoF = null;
		try {
			mediosContactoF = mediosContactoService.consultarMediosFiscalesPersona(persona);
		} catch (PersonaSinMedioDeContactoException e) {
			log.error(e);
		}
		return mediosContactoF;
	}
}
