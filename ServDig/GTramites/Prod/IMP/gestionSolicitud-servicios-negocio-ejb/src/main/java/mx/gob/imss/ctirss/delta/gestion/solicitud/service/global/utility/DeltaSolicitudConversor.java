package mx.gob.imss.ctirss.delta.gestion.solicitud.service.global.utility;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.global.model.ClasificacionTO;
import mx.gob.imss.ctirss.delta.global.model.PatronTO;
import mx.gob.imss.ctirss.delta.global.model.PersonaTO;
import mx.gob.imss.ctirss.delta.global.model.RegistroPatronalTO;
import mx.gob.imss.ctirss.delta.global.model.SolicitudTO;
import mx.gob.imss.ctirss.delta.global.model.TipoTramiteTO;
import mx.gob.imss.ctirss.delta.global.model.TramiteTO;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.EstadoAdministracionEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fiel;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
//import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Certificado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsignacionMasiva;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteBajaPersonaAutorizada;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteMoral;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramitePersonaAutorizada;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRiss;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvro;

import org.apache.commons.lang.StringUtils;

@Stateless
public class DeltaSolicitudConversor implements DeltaSolicitudConversorLocal {
	
	@EJB
	DomicilioServiceBusinessRemote domicilioService;
	@EJB
	PersonaBusinessRemote personaService;

	
	
	
	@Override
	public SolicitudTO transformarSolicitudAModeloGlobal(Solicitud deltaModel) {
		
		SolicitudTO globalModel = new SolicitudTO();
		globalModel.setEstadoSolicitud(deltaModel.getEstadoSolicitud());
		globalModel.setFechaSolicitud(deltaModel.getFechaSolicitud());
		globalModel.setNoFolioSolicitud(deltaModel.getNoFolioSolicitud());
		globalModel.setSolicitudId(deltaModel.getSolicitudId());
		globalModel.setTipoSolicitud(deltaModel.getTipoSolicitud());
		globalModel.setOrigenSolicitud(deltaModel.getOrigenSolicitud());
		globalModel.setObservacion(deltaModel.getObservacion());
		globalModel.setTramites(transformarTramitesAModeloGlobal(deltaModel.getTramites()));
		globalModel = obtenerPersonaRPAfectadoPorLaSolicitud(globalModel, deltaModel);
		globalModel.setCertificado(deltaModel.getCertificado());
		globalModel = complementarCerificado(globalModel, deltaModel);
		globalModel = agregarInformacionDeDomicilio(deltaModel, globalModel);		
//		globalModel.setCertificado(generaCerificadoDummy());
		return globalModel;
	}
	
	private SolicitudTO agregarInformacionDeDomicilio(Solicitud deltaModel, SolicitudTO globalModel){
		if(!deltaModel.getTipoSolicitud().getIdTipoSolicitud().equals(TipoSolicitudEnum.ALTA_PATRONAL.getValor().longValue()))
			return globalModel;
		
		Domicilio domicilioFiscalSolicitud = obtenerObjetoDomicilioFiscal(deltaModel);
		if(domicilioFiscalSolicitud!=null){
			globalModel = agregarInformacionDomicilio(globalModel, domicilioFiscalSolicitud);
		}
		
		return globalModel;
	}
	
	
	/**
	 * Obtiene la información requerida por idse para el registro de altas patronales en su base de datos.
	 * Para idse siempre se requerirá dar de alta el registro patronal asociado al dueño del NRP, es decir
	 * persona física o moral. El registro patronal se asociará con el representante legal en un proceso 
	 * asíncrono denominado alta de adicional por lo cual la información del certificado del representante
	 * legal no es requerido en la consulta de esta solicitud.
	 * 
	 * @param persona Objeto persona con id de persona y tipo de persona requeridos
	 * @return Certificado
	 */
	private Certificado obtenerInformacionCertificadoPersonaAfectada(Persona persona){
		return null;	
	}
	
	private SolicitudTO agregarInformacionDomicilio(SolicitudTO globalModel, Domicilio domicilio){
		
		globalModel.getPersona().setDomicilioFiscalCompleto(obtenerDomicilioCompleto(domicilio));
		if(domicilio.getAsentamiento()!=null
				&& domicilio.getAsentamiento().getLocalidad()!=null
				&& domicilio.getAsentamiento().getLocalidad().getMunicipio()!=null
				&& domicilio.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa()!=null)
			globalModel.getPersona().setEntidadFederativa(domicilio.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre());
		
		if(domicilio.getAsentamiento()!=null
				&& domicilio.getAsentamiento().getLocalidad()!=null
				&& domicilio.getAsentamiento().getLocalidad().getMunicipio()!=null)
			globalModel.getPersona().setMunicipio(domicilio.getAsentamiento().getLocalidad().getMunicipio().getNombre());
		
		
		if(domicilio.getAsentamiento()!=null
				&& domicilio.getAsentamiento().getLocalidad()!=null)
			globalModel.getPersona().setLocalidad(domicilio.getAsentamiento().getLocalidad().getNombre());
		
		
		return globalModel;
	}
	
	private PersonaTO agregarInformacionDomicilioActual(PersonaTO globalModel, Domicilio domicilio){
		
		globalModel.setDomicilioFiscalCompleto(obtenerDomicilioCompleto(domicilio));
		if(domicilio.getAsentamiento()!=null
				&& domicilio.getAsentamiento().getLocalidad()!=null
				&& domicilio.getAsentamiento().getLocalidad().getMunicipio()!=null
				&& domicilio.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa()!=null)
			globalModel.setEntidadFederativa(domicilio.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre());
		
		if(domicilio.getAsentamiento()!=null
				&& domicilio.getAsentamiento().getLocalidad()!=null
				&& domicilio.getAsentamiento().getLocalidad().getMunicipio()!=null)
			globalModel.setMunicipio(domicilio.getAsentamiento().getLocalidad().getMunicipio().getNombre());
		
		
		if(domicilio.getAsentamiento()!=null
				&& domicilio.getAsentamiento().getLocalidad()!=null)
			globalModel.setLocalidad(domicilio.getAsentamiento().getLocalidad().getNombre());
		
		
		return globalModel;
	}
	
	
	private String obtenerMedioContactoTramite(Solicitud solicitud, Long idTipoMedioContacto){
		String medio="";
		for(Tramite tramite : solicitud.getTramites()){
			if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ALTA_SRT.getCodigo()) ||
					tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ALTA_SRT_PM.getCodigo())){
				TramiteSujetoObligado tso = (TramiteSujetoObligado)tramite;
				List<MedioContacto> mediosContacto = tso.getSujetoObligado().getCntroTrabajo().getMediosContacto();
				for(MedioContacto contacto: mediosContacto){
					if(contacto.getTipoMedioContacto().getIdTipoMedioContacto().equals(idTipoMedioContacto)){
						if(idTipoMedioContacto.equals(TipoMedioContacto.TIPO_TELEFONO_FIJO)){
							if(contacto.getDesFormaContacto().equals("||"))
								continue;
							else
								return contacto.getDesFormaContacto();
						}
							
						return contacto.getDesFormaContacto();
					}
				}
			}
		}
		return medio;
	}
		
	private SolicitudTO complementarCerificado(SolicitudTO globalModel, Solicitud deltaModel){
		if(globalModel.getCertificado()!=null&& globalModel.getPersona()!=null){
			globalModel.getCertificado().setCurpFiel(globalModel.getPersona().getCurp());
			globalModel.getCertificado().setRfcAsociado(globalModel.getPersona().getRfc());
			globalModel.getCertificado().setEstatusFiel(2);
			globalModel.getCertificado().setIdRol(1);
			globalModel.getCertificado().setNombreCompleto(globalModel.getPersona().getRazonSocial());
			globalModel.getCertificado().setNombreUsuario(globalModel.getPersona().getRfc());
			
			String correo = obtenerMedioContactoTramite(deltaModel, TipoMedioContacto.TIPO_CORREO_ELECTRONICO);
			String telefono = obtenerMedioContactoTramite(deltaModel, TipoMedioContacto.TIPO_TELEFONO_FIJO);
			System.err.println("Correo para idse: "+correo);
			System.err.println("Telefono para idse: "+telefono);
			globalModel.getCertificado().setCorreoElectronico(correo);
			globalModel.getCertificado().setTelefono(telefono);
			Fiel fielPatron = obtenerDatosFielPatron(globalModel.getPersona());
			if(fielPatron!=null){
				System.out.println("Agregare datos fiel patron");
				globalModel.getCertificado().setClaveSerial(fielPatron.getClaveSerial());
				globalModel.getCertificado().setFechaValidaFin(fielPatron.getFechaValidaFin());
				globalModel.getCertificado().setFechaValidaInicio(fielPatron.getFechaValidaInicio());
			}
		}
		return globalModel;
	}
	
	private Fiel obtenerDatosFielPatron(PersonaTO persona){
		System.out.println("Entrando a datos fiel patron...........");
		Persona personConsulta = new Persona();
		personConsulta.setIdPersona(persona.getIdPersona());
		personConsulta.setTipoPersona(persona.getTipoPersona());
		System.out.println("El id de persona y tipo de persona para obtener los datos de la FIEL son ..........." + persona.getIdPersona() + persona.getTipoPersona());
		return personaService.obtenerDatosFiel(personConsulta);
	}
	
	public List<TramiteTO> transformarTramitesAModeloGlobal(List<Tramite> tramites){
		List<TramiteTO> listaGlobal = new ArrayList<TramiteTO>();
		for(Tramite tramite : tramites){
			listaGlobal.add(transformarTramiteAModeloGlobal(tramite));
		}
		return listaGlobal;
	}
	
	private SolicitudTO obtenerPersonaRPAfectadoPorLaSolicitud(SolicitudTO globalModel,Solicitud deltaModel){
		RegistroPatronalTO registroPatronal=null;
		System.err.println("Se determina persona afectada:::");
		PersonaTO persona = null;
		String correoDeNotificaciones =  null;
		if(deltaModel.getTipoSolicitud().getIdTipoSolicitud().equals(TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO.getValor().longValue())
		|| deltaModel.getTipoSolicitud().getIdTipoSolicitud().equals(TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION.getValor().longValue())
				){
			for(Tramite tramite:deltaModel.getTramites()){
				TramiteSujetoObligado tso = (TramiteSujetoObligado)tramite;
				registroPatronal = convertirRegistroPatronalAModeloGlobal(tso.getSujetoObligado());
				correoDeNotificaciones = obtenerCorreoElectronico(tramite);
				registroPatronal.getPatron().getPersona().setCorreoDeNotificaciones(correoDeNotificaciones);
				persona = registroPatronal.getPatron().getPersona();
			}
		}else if(deltaModel.getTipoSolicitud().getIdTipoSolicitud().equals(TipoSolicitudEnum.ALTA_PATRONAL.getValor().longValue())){
			//Debido a que el tramite de; datos generales puede o no exitir se toma la persona registrada en el tramite de altasrt
			for(Tramite tramite:deltaModel.getTramites()){
				
				
				if(tramite instanceof TramiteSujetoObligado){
					System.err.println("Se agrega tramite de alta");
					
					TramiteSujetoObligado tso = (TramiteSujetoObligado)tramite;
					registroPatronal = convertirRegistroPatronalAModeloGlobal(tso.getSujetoObligado());
					persona = registroPatronal.getPatron().getPersona();
					correoDeNotificaciones =  obtenerCorreoElectronico(tramite);
					System.err.println("Correo de notificaciones: "+correoDeNotificaciones);
					persona.setCorreoDeNotificaciones(correoDeNotificaciones);
					
					//Se agrega el nombre del representante legal a usuario responsable
					if(tso.getSujetoObligado().getRepresentantesLegales()!=null 
							&& tso.getSujetoObligado().getRepresentantesLegales().size() > 0){
						RepresentanteLegal rl = tso.getSujetoObligado().getRepresentantesLegales().get(0);
						globalModel.setUsuarioResponsable(obtenerNombreCompletoPersonaFisica(rl.getPersonaFisica()));
					}
					
				}
//				if(tramite instanceof TramiteFisica){
//					System.err.println("Se agrega tramite de datos generales fisica");	
//					TramiteFisica tramiteFisica = (TramiteFisica)tramite;
//					persona=convertirPersonaAModeloGlobal(tramiteFisica.getFisica());
//				}else if(tramite instanceof TramiteMoral){
//					System.err.println("Se agrega tramite de datos generales fisica");
//					TramiteMoral tramiteMoral = (TramiteMoral)tramite;
//					persona=convertirPersonaAModeloGlobal(tramiteMoral.getMoral());
//				}
			}
			//SI HAY TRAMITE ICA SE TOMAN LOS DATOS DE LA ENTIDAD EXTERNA
			for(Tramite tramite:deltaModel.getTramites()){
				if(tramite instanceof TramiteFisica){
					TramiteFisica icaFisica = (TramiteFisica)tramite;
					Fisica fisicaEntidadExterna = icaFisica.getDatosICA().getPersonaFisicaEE();
					
					String nombreCompleto = obtenerNombreCompletoPersonaFisica(fisicaEntidadExterna);
					persona.setRazonSocial(nombreCompleto);
					
				}
				
				if(tramite instanceof TramiteMoral){
					TramiteMoral icaMoral = (TramiteMoral)tramite;
					Moral moralEntidadExterna = icaMoral.getDatosICA().getPersonaMoralEE();
					persona.setRazonSocial(moralEntidadExterna.getRazonSocial());
					
				}
			}
			
		}else if(deltaModel.getTipoSolicitud().getIdTipoSolicitud().equals(TipoSolicitudEnum.ASIGNACION_NSS.getValor().longValue())){
			StringBuffer correosNotificacion = new StringBuffer();
			for(Tramite tramite:deltaModel.getTramites()){
				if(tramite instanceof TramiteAsignacionMasiva){
					TramiteAsignacionMasiva tam = (TramiteAsignacionMasiva)tramite;
					registroPatronal = new RegistroPatronalTO();
					registroPatronal.setNumeroRegistro(tam.getNrp());
					globalModel.setArchivoAProcesar(tam.getFileName());
					globalModel.setUsuarioResponsable(tam.getUsuario());
					if(tam.getCorreosContacto()!=null){
						globalModel.setCorreosNotificacion(new ArrayList<String>());
						
						for(CorreoElectronico correo:tam.getCorreosContacto()){
							correosNotificacion.append(correo.getCorreo()).append(";");
							globalModel.getCorreosNotificacion().add(correo.getCorreo());
						}
					}
					registroPatronal = convertirRegistroPatronalAModeloGlobal(tam.getSujetoObligado());
					persona = registroPatronal.getPatron().getPersona();
					persona.setCorreoDeNotificaciones(correosNotificacion.toString());
					
				}
			}
		}else if(deltaModel.getTipoSolicitud().getIdTipoSolicitud().equals(TipoSolicitudEnum.INCORPORACION_BENEFICIO.getValor().longValue())){
			for(Tramite tramite : deltaModel.getTramites()){
				if (tramite instanceof TramiteRiss) {
					TramiteRiss tramiteRiss = (TramiteRiss) tramite;
			
					if (tramiteRiss.getFisica() != null) {
						persona = convertirPersonaAModeloGlobal(tramiteRiss.getFisica());
						persona.setCorreoDeNotificaciones(correoDeNotificaciones);
						break;
					} else {
						/*
						 * El tramite RISS no siempre trae la persona física, en
						 * caso de no traerla se pone un -1 en el id persona
						 * para que el OSB no falle y pueda continuar con las
						 * diversas publicaciones al comet
						 */
						persona = new PersonaTO();
						persona.setIdPersona(-1L);
						TipoPersona tipoPersona = new TipoPersona();
						tipoPersona.setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
						persona.setTipoPersona(tipoPersona);
					}
				}
			}
		}else if(deltaModel.getTipoSolicitud().getIdTipoSolicitud().equals(TipoSolicitudEnum.COMPRA_SEGURO.getValor().longValue())){
			for(Tramite tramite : deltaModel.getTramites()){
			    Integer idTipoTramite = tramite.getTipoTramite().getIdTipoTramite();
				if(idTipoTramite.equals(TipoTramiteEnum.COMPRA_SEGURO_INDIVIDUAL.getCodigo())
						|| idTipoTramite.equals(TipoTramiteEnum.RENOVACION_SEGURO_INDIVIDUAL.getCodigo()) 
						|| idTipoTramite.equals(TipoTramiteEnum.COMPRA_SEGURO_DOMESTICO.getCodigo()) 
						|| idTipoTramite.equals(TipoTramiteEnum.RENOVACION_SEGURO_DOMESTICO.getCodigo()))
					persona = convertirPersonaAModeloGlobal(tramite.getPersona());
			}
		
		}else{
		
			for(Tramite tramite:deltaModel.getTramites()){
				if(tramite instanceof TramiteFisica){
					TramiteFisica tramiteFisica = (TramiteFisica)tramite;
					if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.BAJA_REPRESENTANTE_LEGAL.getCodigo()))
						persona = obtenerPersonaAfectadaTramiteBajaRepresentante(tramite);
					else
						persona=convertirPersonaAModeloGlobal(tramiteFisica.getFisica());
					
					String correoNotificaciones = obtenerCorreoElectronico(tramiteFisica);
					persona.setCorreoDeNotificaciones(correoNotificaciones);	
					System.err.println("Se agrega persona afectada de tramite Fisica: "+persona);
					
				}else if(tramite instanceof TramiteMoral){
					TramiteMoral tramiteMoral = (TramiteMoral)tramite;
					if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.BAJA_REPRESENTANTE_LEGAL.getCodigo()))
						persona = obtenerPersonaAfectadaTramiteBajaRepresentante(tramite);
					else
						persona=convertirPersonaAModeloGlobal(tramiteMoral.getMoral());
					System.err.println("Se agrega persona afectada de tramite Moral");
					
				}else if(tramite instanceof TramiteRepresentanteLegal){
					System.err.println("Se agrega persona afectada de tramite representante legal");
					TramiteRepresentanteLegal tso = (TramiteRepresentanteLegal)tramite;
					//System.err.println("Persona tramite: "+tso.getSujetoObligado().getFisica());
					System.err.println("Persona tramite: "+tso.getFisica());
					if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo())){
						System.err.println("Alta persona afectada de tramite representante");
						persona=convertirPersonaAModeloGlobal(tso.getFisica());
					}else{
						System.err.println("Baja persona afectada de tramite representante legal");
						persona=convertirPersonaAModeloGlobal(tso.getFisica());
					}
					System.err.println("Persona : "+persona);
				}else if(tramite instanceof TramitePersonaAutorizada){
					System.err.println("Se agrega persona afectada de tramite persona autorizada");
					TramitePersonaAutorizada tpa = (TramitePersonaAutorizada)tramite;
					TipoPersona tipoPersona = new TipoPersona();
					
					if(tpa.getPersonaFisica()!=null){
						tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
						tpa.getPersonaFisica().setTipoPersona(tipoPersona);
						System.err.println("Persona tramite: "+tpa.getPersonaFisica());
						persona=convertirPersonaAModeloGlobal(tpa.getPersonaFisica());
						System.err.println("Persona: "+persona);
					}else{
						tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
						tpa.getPersonaMoral().setTipoPersona(tipoPersona);
						System.err.println("Persona tramite: "+tpa.getPersonaMoral());
						persona=convertirPersonaAModeloGlobal(tpa.getPersonaMoral());
						System.err.println("Persona: "+persona);
					}
				}else if(tramite instanceof TramiteBajaPersonaAutorizada){
					System.err.println("Se agrega persona afectada de tramite baja persona autorizada");
					TramiteBajaPersonaAutorizada tpa = (TramiteBajaPersonaAutorizada)tramite;
					TipoPersona tipoPersona = new TipoPersona();
					
					if(tpa.getFisica()!=null){
						tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
						tpa.getFisica().setTipoPersona(tipoPersona);
						System.err.println("Persona tramite: "+tpa.getFisica());
						persona=convertirPersonaAModeloGlobal(tpa.getFisica());
						System.err.println("Persona: "+persona);
					}else{
						tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
						tpa.getMoral().setTipoPersona(tipoPersona);
						System.err.println("Persona tramite: "+tpa.getMoral());
						persona=convertirPersonaAModeloGlobal(tpa.getMoral());
						System.err.println("Persona: "+persona);
					}
				}
			}
		}
		
		globalModel.setPersona(persona);
		globalModel.setRegistroPatronal(registroPatronal);
		
		return globalModel;
	}
	
	private String obtenerNombreCompletoPersonaFisica(Fisica persona){
		StringBuffer nombreRL = new StringBuffer(); 
		
		if(StringUtils.isNotEmpty(persona.getNombre()) )
			nombreRL.append(persona.getNombre());
		if(StringUtils.isNotEmpty(persona.getPrimerApellido()) )
			nombreRL.append(" "+persona.getPrimerApellido());
		if(StringUtils.isNotEmpty(persona.getSegundoApellido()) )
			nombreRL.append(" "+persona.getSegundoApellido());
		
		return nombreRL.toString();
	}
	
	private String obtenerCorreoElectronico(Tramite tramite){
		String correo=null;
		if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ALTA_SRT.getCodigo())
				||tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ALTA_SRT_PM.getCodigo())
				||tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo())
				||tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ALTA_RIF.getCodigo())
				){
			TramiteSujetoObligado tso = (TramiteSujetoObligado)tramite;
			for(MedioContacto medio : tso.getSujetoObligado().getCntroTrabajo().getMediosContacto()){
				if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_CORREO_ELECTRONICO))
					correo = medio.getDesFormaContacto();
			}
			
		}else if (tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO.getCodigo())){
			System.err.println("Se obtiene correo de notificaciones para ACTUALIZACION_DATOS_CONTACTO");
			if(tramite instanceof TramiteFisica){
				System.err.println("Tramite Fisica en ACTUALIZACION_DATOS_CONTACTO");
				TramiteFisica tFisica = (TramiteFisica)tramite;
				List<MedioContacto> medios = tFisica.getDatosMDM().getPersonaFisica().getMediosContacto();
				for(MedioContacto medio : medios){
					System.err.println("medio en ACTUALIZACION_DATOS_CONTACTO: "+medio);
					EstadoAdministracionEnum estadoAdministracion = medio.getEstadoAdministracionMedioContacto();
					if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_CORREO_ELECTRONICO)
							&& (estadoAdministracion==null || (estadoAdministracion!=null && estadoAdministracion!=EstadoAdministracionEnum.ELIMINADO))){
						correo = medio.getDesFormaContacto();
						System.err.println("Correo de notificacion localizado: "+correo);
						break;
					}
				}
			}
		}
		
		return correo;
	}
	
	private PersonaTO convertirPersonaAModeloGlobal(Persona persona){
		PersonaTO globalModel = new PersonaTO();
		TipoPersona tipoPersona = new TipoPersona();
		globalModel.setIdPersona(persona.getIdPersona());
		globalModel.setRfc(persona.getRfc());
		if(persona instanceof Fisica){
			Fisica personaFisica = (Fisica)persona;
			globalModel.setCurp(personaFisica.getCurp());
			StringBuffer nombreCompleto = new StringBuffer();
			if(personaFisica.getNombre()!=null)
				nombreCompleto.append(personaFisica.getNombre()).append(" ");
			if(personaFisica.getPrimerApellido()!=null)
				nombreCompleto.append(personaFisica.getPrimerApellido()).append(" ");
			if(personaFisica.getSegundoApellido()!=null)
				nombreCompleto.append(personaFisica.getSegundoApellido());
			globalModel.setRazonSocial(nombreCompleto.toString());
			tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
		}else if(persona instanceof Moral){
			Moral personaMoral = (Moral)persona;
			globalModel.setRazonSocial(personaMoral.getRazonSocial());
			tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
		}
		globalModel=agregarInformacionDomicilioFiscalActual(persona,globalModel);
		System.err.println("Domicilio completo persona: "+globalModel.getDomicilioFiscalCompleto());
		globalModel.setTipoPersona(tipoPersona);
		return globalModel;
	}
	
	private PersonaTO agregarInformacionDomicilioFiscalActual(Persona deltaModel, PersonaTO globalModel){
		DomicilioFiscal domicilio=null;
		try {
			if(deltaModel!=null && deltaModel.getIdPersona()!=null){
				domicilio = domicilioService.consultarDomicilioFiscalPersona(deltaModel);
				globalModel = agregarInformacionDomicilioActual(globalModel, domicilio);
			}
		} catch (DomicilioNoLocalizadoException e) {
			/*
			 * LUDS se modifico para que en caso de que no contara con el
			 * domicilio fiscal no sea nulo el objeto de regreso.
			 */
			return globalModel;
		}
		
		
		return globalModel;
	}
	
	private RegistroPatronalTO convertirRegistroPatronalAModeloGlobal(SujetoObligado sujetoObligado){
		RegistroPatronalTO modeloGlobal = new RegistroPatronalTO();
		PersonaTO personaModeloGlobal = new PersonaTO();
		PatronTO patron = new PatronTO();
		modeloGlobal.setNumeroRegistro(sujetoObligado.getNumeroRegistroPatronal());
		modeloGlobal.setModalidad(sujetoObligado.getModalidad());
		modeloGlobal.setDigVerificador(sujetoObligado.getDigVerificador());
		modeloGlobal.setIdRegistroPatronal(sujetoObligado.getCveIdSujetoObligado());
		modeloGlobal.setSector(90);
		modeloGlobal.setDomicilioCompleto(obtenerDomicilioCompleto(sujetoObligado.getCntroTrabajo()));
		modeloGlobal.setLocalidadSINDO(obtenerLocalidadSINDO(sujetoObligado.getCntroTrabajo()));
		modeloGlobal.setClasificacion(transformarClasificacionAModeloGlobal(sujetoObligado.getClasificacion()));
		
		System.err.println("REG_PATRON GLOBAL: "+modeloGlobal);
		if(sujetoObligado.getMunicipioIMSS()!=null)
			modeloGlobal.setCveMunicipioImss(sujetoObligado.getMunicipioIMSS().getCvecMunicipioSINDO());
		if(sujetoObligado.getSubdelegacion()!=null){
			Subdelegacion subdelegacionAsignada = sujetoObligado.getSubdelegacion();
			subdelegacionAsignada.getDelegacion().setClave(
					String.format("%02d", Integer.parseInt(subdelegacionAsignada.getDelegacion().getClave()))
					);
			subdelegacionAsignada.setClave(
					String.format("%02d", Integer.parseInt(subdelegacionAsignada.getClave()))
					);
			modeloGlobal.setSubdelegacion(subdelegacionAsignada);
		}
		if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA))
			personaModeloGlobal = convertirPersonaAModeloGlobal(sujetoObligado.getFisica());
		else if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL))
			personaModeloGlobal = convertirPersonaAModeloGlobal(sujetoObligado.getMoral());
		
		patron.setPersona(personaModeloGlobal);
		modeloGlobal.setPatron(patron);
		
		return modeloGlobal;
	}
	
	private ClasificacionTO transformarClasificacionAModeloGlobal(Clasificacion clasificacion){
		if(clasificacion==null)
			return null;
		
		ClasificacionTO clasificacionTO=new ClasificacionTO();
		clasificacionTO.setFraccion(clasificacion.getFraccion());
		if(clasificacion.getFraccion()!=null && clasificacion.getFraccion().getNumFraccion()!=null)
			clasificacionTO.getFraccion().setNumFraccion(String.format("%02d", Integer.parseInt(clasificacion.getFraccion().getNumFraccion())));
		return clasificacionTO;
	}
	
	private String obtenerLocalidadSINDO(Domicilio domicilio){
		if(domicilio==null)
			return"";
		
		
		Asentamiento asentamiento = domicilio.getAsentamiento();
        Localidad localidad = asentamiento!=null ? asentamiento.getLocalidad() : null;
        
        if(localidad==null)
        	return"";
        
        String localidadSindo = new StringBuffer().append(localidad.getMunicipio().getNombre()).
        		append(" ").append(localidad.getMunicipio().getEntidadFederativa().getNombre()).toString().toUpperCase();
        
        return localidadSindo;
	}
	
	private String obtenerDomicilioCompleto(Domicilio domicilio){
        if(domicilio==null)
        	return "";
        
        String calle = StringUtils.isNotBlank(domicilio.getCalle()) ? domicilio.getCalle() :
        	domicilio.getVialidadPrimaria()!=null ? domicilio.getVialidadPrimaria().getNombre() : "" ; 
        
        StringBuffer domicilioStr = new StringBuffer(calle)
                .append(" ").append(safeNull(domicilio.getNumExterior1()));
                if(domicilio.getNumExteriorAlf()!=null)
                	domicilioStr.append(" ").append(safeNull(domicilio.getNumExteriorAlf()));
                if(domicilio.getNumInterior()!=null)
                	domicilioStr.append(" ").append(safeNull(domicilio.getNumInterior()));
                if(domicilio.getNumInteriorAlf()!=null)
                	domicilioStr.append(" ").append(safeNull(domicilio.getNumInteriorAlf()));
                if(domicilio.getAsentamiento()!=null && domicilio.getAsentamiento().getNombre()!=null)
                	domicilioStr.append(" ").append(safeNull(domicilio.getAsentamiento().getNombre()));
                
       return domicilioStr.toString().replaceAll("[\u00F1\u00D1]", "#");

	}
		
	public String obtenerDomicilioFiscalCompleto(Solicitud solicitud){
		StringBuffer domicilioFiscal = new StringBuffer();
		String domicilioFiscalCompleto = obtenerDomicilioFiscalDeSolicitud(solicitud);
		if(domicilioFiscalCompleto!=null)
			domicilioFiscal.append(domicilioFiscalCompleto);
		else 
			return null;
		return domicilioFiscal.toString();
	}
	
	public String obtenerDomicilioFiscalCompleto(Persona persona){
		StringBuffer domicilioFiscal = new StringBuffer();
		
		DomicilioFiscal domFiscal=null;
		try {
			if(persona!=null && persona.getIdPersona()!=null)
				domFiscal = domicilioService.consultarDomicilioFiscalPersona(persona);
		} catch (DomicilioNoLocalizadoException e) {
			return null;
		}
		
		
		if(domFiscal!=null)
			domicilioFiscal.append(obtenerDomicilioCompleto(domFiscal));
		else
			return null;
		
		return domicilioFiscal.toString();
	}
	
	private String obtenerDomicilioFiscalDeSolicitud(Solicitud solicitud){
		StringBuffer domicilioFiscal = new StringBuffer();
		Tramite tramiteDatosGenerales = null;
		for(Tramite tramite : solicitud.getTramites()){
			if(tramite.getTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES)){
				tramiteDatosGenerales=tramite;
				break;
			}
		}
		if(tramiteDatosGenerales!=null){
			if(tramiteDatosGenerales instanceof TramiteFisica){
				TramiteFisica tFisica = (TramiteFisica)tramiteDatosGenerales;
				DomicilioFiscal domFiscal = tFisica.getDatosICA().getPersonaFisicaEE().getDomicilioFiscal();
				if(domFiscal!=null){
					domicilioFiscal.append(obtenerDomicilioCompleto(domFiscal));
				}else{ 
					
					try {
						domFiscal = domicilioService.consultarDomicilioFiscalPersona(tFisica.getDatosICA().getPersonaFisicaIMSS());
						domicilioFiscal.append(obtenerDomicilioCompleto(domFiscal));
					} catch (DomicilioNoLocalizadoException e) {
						System.err.println("No se encontro domicilio fiscal....");
						return null;
					}
					
					return null;
				}
			}else if(tramiteDatosGenerales instanceof TramiteMoral){
				TramiteMoral tMoral = (TramiteMoral)tramiteDatosGenerales;
				DomicilioFiscal domFiscal = tMoral.getDatosICA().getPersonaMoralEE().getDomicilioFiscal();
				if(domFiscal!=null){
					domicilioFiscal.append(obtenerDomicilioCompleto(domFiscal));
				}else{
					try {
						domFiscal = domicilioService.consultarDomicilioFiscalPersona(tMoral.getDatosICA().getPersonaMoralIMSS());
						domicilioFiscal.append(obtenerDomicilioCompleto(domFiscal));
					} catch (DomicilioNoLocalizadoException e) {
						System.err.println("No se encontro domicilio fiscal....");
						return null;
					}
					
					return null;
				}
					
			}
			
		}else
			return null;
			
		System.err.println("Domicilio fiscal de ICA: "+domicilioFiscal.toString());
		return domicilioFiscal.toString();
	}
	
	private Domicilio obtenerObjetoDomicilioFiscal(Solicitud solicitud){
		
		Tramite tramiteDatosGenerales = null;
		DomicilioFiscal domFiscal = null;
		for(Tramite tramite : solicitud.getTramites()){
			if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES.getCodigo())
				|| tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DATOS_USUARIO.getCodigo())){
				tramiteDatosGenerales=tramite;
				break;
			}
		}
		try{
			if(tramiteDatosGenerales!=null){
				if(tramiteDatosGenerales instanceof TramiteFisica){
					TramiteFisica tFisica = (TramiteFisica)tramiteDatosGenerales;
					domFiscal = tFisica.getDatosICA().getPersonaFisicaEE().getDomicilioFiscal();
					if(domFiscal==null)
						domFiscal = domicilioService.consultarDomicilioFiscalPersona(tFisica.getDatosICA().getPersonaFisicaIMSS());
					
				}else if(tramiteDatosGenerales instanceof TramiteMoral){
					TramiteMoral tMoral = (TramiteMoral)tramiteDatosGenerales;
					domFiscal = tMoral.getDatosICA().getPersonaMoralEE().getDomicilioFiscal();
					if(domFiscal==null)
						domFiscal = domicilioService.consultarDomicilioFiscalPersona(tMoral.getDatosICA().getPersonaMoralIMSS());
				}
			}
		} catch (DomicilioNoLocalizadoException e) {
			System.err.println("No se encontro domicilio fiscal....");
			return null;
		}
		System.err.println("Domicilio fiscal de ICA: "+domFiscal);
		return domFiscal;
	}

	
	private String safeNull(String nullablestring) {
        if (nullablestring == null) {
            return "";
        }
        return nullablestring;
    }

    private String safeNull(Number nullable) {
        if (nullable == null) {
            return "";
        }
        return "" + nullable;
    }

	
	public TramiteTO transformarTramiteAModeloGlobal(Tramite tramite){
		TramiteTO modeloGlobal = new TramiteTO();
		modeloGlobal.setTramiteId(tramite.getTramiteId());
		modeloGlobal.setEstadoTramite(tramite.getEstadoTramite());
		modeloGlobal.setFechaTramite(tramite.getFechaTramite());
		modeloGlobal.setRazonResultado(tramite.getRazonResultado());
		modeloGlobal.setTipoTramite(transformarTipoTramiteAModeloGlobal(tramite.getTipoTramite()));
		System.err.println("TIPO TRAMITE: "+tramite.getTipoTramite().getIdTipoTramite());
		if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ALTA_SRT.getCodigo()) ||
				tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ALTA_SRT_PM.getCodigo())){
			System.err.println("SE ASIGNARA MODALIDAD Y MUNICIPIO");
			TramiteSujetoObligado tso = (TramiteSujetoObligado)tramite;
			modeloGlobal.setCveModalidad(tso.getSujetoObligado().getModalidad().getNumModalidad());
			modeloGlobal.setCveMunicipioImss(tso.getSujetoObligado().getMunicipioIMSS().getCvecMunicipioSINDO());
		}else{
			System.err.println("NO SE ASIGNARA MODALIDAD Y MUNICIPIO");
			modeloGlobal.setCveModalidad("");
			modeloGlobal.setCveMunicipioImss("");
		}
		return modeloGlobal;
	}
	
	public TipoTramiteTO transformarTipoTramiteAModeloGlobal(TipoTramite tipoTramite){
		TipoTramiteTO modeloGlobal = new TipoTramiteTO();
		modeloGlobal.setIdTipoTramite(tipoTramite.getIdTipoTramite());
		modeloGlobal.setDescripcion(tipoTramite.getDescripcion());
		return modeloGlobal;
	}
	
	private PersonaTO obtenerPersonaAfectadaTramiteBajaRepresentante(Tramite tramite){
		PersonaTO persona =new PersonaTO();
		persona.setTipoPersona(new TipoPersona());
		boolean tramiteFisica=false;
		List<RepresentanteLegal> representantes=null;
		if(tramite instanceof TramiteFisica) {
			tramiteFisica = true;
			TramiteFisica tf = ((TramiteFisica) tramite);
			representantes = tf.getFisica().getRepresentantesLegales();
		} else {
			TramiteMoral tm = ((TramiteMoral) tramite);
			representantes = tm.getMoral().getRepresentantesLegales();
		}
		
		for(RepresentanteLegal repLeg: representantes) {
						
			if(!tramiteFisica) {
				persona.setIdPersona(((TramiteMoral) tramite).getMoral().getIdPersona());
				persona.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
				
			} else {

				if(repLeg.getTipoPersonaRepresentada().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
					persona.setIdPersona( repLeg.getPersonaFisicaRepresentada().getIdPersona());
					persona.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
				}else{
					persona.setIdPersona(repLeg.getPersonaMoralRepresentada().getIdPersona());
					persona.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
				}
					
			}
		}
		return persona;
	}
}
