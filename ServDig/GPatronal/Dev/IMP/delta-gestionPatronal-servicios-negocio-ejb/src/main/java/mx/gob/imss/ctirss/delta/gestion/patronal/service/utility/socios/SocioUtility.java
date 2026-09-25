package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.socios;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.socios.SocioServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.ServiceUtility;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.escritura.constitutiva.EscrituraConstitutivaServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSociedad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSocioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.AcuseVentanilla;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSocios;
import mx.gob.imss.ctirss.delta.persistence.DgAsentamiento;
import mx.gob.imss.ctirss.delta.persistence.DgCatEstado;
import mx.gob.imss.ctirss.delta.persistence.DgCatLocalidad;
import mx.gob.imss.ctirss.delta.persistence.DgCatMunicipio;
import mx.gob.imss.ctirss.delta.persistence.DgCatTipoDom;
import mx.gob.imss.ctirss.delta.persistence.DgDomicilioGeografico;
import mx.gob.imss.ctirss.delta.persistence.DgVialidad;
import mx.gob.imss.ctirss.delta.persistence.DicSexo;
import mx.gob.imss.ctirss.delta.persistence.DicTipoDomicilio;
import mx.gob.imss.ctirss.delta.persistence.DicTipoSociedad;
import mx.gob.imss.ctirss.delta.persistence.DitActaConstitutiva;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;
import mx.gob.imss.ctirss.delta.persistence.DitPersonafDom;
import mx.gob.imss.ctirss.delta.persistence.DitPersonamDom;
import mx.gob.imss.ctirss.delta.persistence.DitSocio;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

@Stateless
public class SocioUtility extends ServiceUtility implements
		SocioUtilityLocal {
	
	@EJB
	private EscrituraConstitutivaServiceUtilityLocal escrituraConstitutivaServiceUtility;	
	@EJB
	private SocioServiceEntityLocal socioServiceEntity;

	
	@Override
	public Socio convertirEntityToModelSocio(DitSocio ditSocio)
			throws Exception {		
		Socio socio = new Socio();	
		Persona persona=null;
		if (ditSocio.getIndTipoSocio().intValue() == 1){ // socio fisico
			socio.setIdSocio(ditSocio.getCveIdSocio());			
//			if(ditSocio.getIndExtranjero().intValue() == 1 && ditSocio.getIndResidenciaExtranjero().intValue() == 1){
//				socio.setRfc("");
//			}else {
			if(ditSocio.getDitPersonaFisica()!=null && !StringUtils.isBlank(ditSocio.getDitPersonaFisica().getRfc())){
				socio.setRfc(ditSocio.getDitPersonaFisica().getRfc());
				System.err.println("Asigno rfc socio de fisica: "+ditSocio.getDitPersonaFisica().getRfc());
			}else{
				socio.setRfc(ditSocio.getDitPersonaFisica().getDitPersona().getRfc());
				System.err.println("Asigno rfc socio de persona");
			}
//			}			
			if (ditSocio.getDitPersonaFisica() != null){
				socio.setCurp(ditSocio.getDitPersonaFisica().getDitPersona() != null ? ditSocio.getDitPersonaFisica().getDitPersona().getCurp() : "");
			} else {
				socio.setCurp("");
			}
			socio.setTipoSocio(this.socioServiceEntity.getTipoSocio(TipoSocioEnum.FISICO.getValor()));
			socio.setNombreRazonSocial(ditSocio.getNomNombre() + ' ' + ditSocio.getNomPrimerApellido() + ' ' + ditSocio.getNomSegundoApellido()); // esto por la mugre matriz de datos del erick, eric o erik, como sea!!!
			socio.setPrimerApellido(ditSocio.getNomPrimerApellido());
			socio.setSegundoApellido(ditSocio.getNomSegundoApellido());
			socio.setNombres(ditSocio.getNomNombre());
			socio.setEsPersonaFisica(true);
			
			Fisica fisica = convertirEntityToModelFisica(ditSocio.getDitPersonaFisica());
			persona = fisica;
			persona.setIdPersona(ditSocio.getDitPersonaFisica().getDitPersona().getCveIdPersona());
			persona.setTipoPersona(new TipoPersona());
			persona.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA.longValue());
			socio.setPersona(persona);
			socio.setPersonaFisica(fisica);
			
		} else if (ditSocio.getIndTipoSocio().intValue() == 2){ // socio moral
			socio.setIdSocio(ditSocio.getCveIdSocio());			
			if(ditSocio.getIndExtranjero().intValue() == 1 && ditSocio.getIndResidenciaExtranjero().intValue() == 1){
				socio.setRfc("");
			}else {
				socio.setRfc(ditSocio.getDitPersonaMoral().getRfc());
			}			
			socio.setTipoSocio(this.socioServiceEntity.getTipoSocio(TipoSocioEnum.MORAL.getValor()));
			socio.setNombreRazonSocial(ditSocio.getDesDenomRazonSocial());
			socio.setEsPersonaFisica(false);
			
			Moral moral = convertirEntityToModelMoral(ditSocio.getDitPersonaMoral());
			persona = moral;
			persona.setIdPersona(ditSocio.getDitPersonaMoral().getCveIdPersonaMoral());
			persona.setTipoPersona(new TipoPersona());
			persona.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL.longValue());
			socio.setPersona(persona);
			socio.setPersonaMoral(moral);

			DitActaConstitutiva acta = null;
			if (ditSocio.getDitPersonaMoral().getDitActaConstitutivas()!=null && ditSocio.getDitPersonaMoral().getDitActaConstitutivas().size()>0)
				acta = ditSocio.getDitPersonaMoral().getDitActaConstitutivas().get(0);
			
			System.err.println("Acta del socio moral: "+acta);			
			if(acta!=null)
				socio.setEscrituraConstitutiva(escrituraConstitutivaServiceUtility.convertirEntityToModel(acta)); 
			
			System.err.println("El socio : " + socio.getRfc() + " tiene acta: "+acta);			
			DicTipoSociedad tipoSociedad = ditSocio.getDitPersonaMoral().getDicTipoSociedad();
			if(tipoSociedad!=null)
				socio.setTipoSociedad(tipoSociedad.getDesTipoSociedad());
			
		} else if (ditSocio.getIndTipoSocio().intValue() == 3){ // socio fideicomiso
			socio.setIdSocio(ditSocio.getCveIdSocio());			
			if(ditSocio.getIndExtranjero().intValue() == 1 && ditSocio.getIndResidenciaExtranjero().intValue() == 1){
				socio.setRfc("");
			}else {
				socio.setRfc(ditSocio.getDitPersonaMoral().getRfc());
			}			
			Moral moral = convertirEntityToModelMoral(ditSocio.getDitPersonaMoral());
			persona = moral;
			persona.setIdPersona(ditSocio.getDitPersonaMoral().getCveIdPersonaMoral());
			persona.setTipoPersona(new TipoPersona());
			persona.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL.longValue());
			socio.setPersona(persona);
			socio.setPersonaMoral(moral);
			
			socio.setTipoSocio(this.socioServiceEntity.getTipoSocio(TipoSocioEnum.FIDEICOMISO.getValor()));
			socio.setNombreRazonSocial(ditSocio.getDesDenomRazonSocial());
			socio.setEsPersonaFisica(false);
			// seccion contrato
			socio.setNumeroInstrumetoProtocolizacion(ditSocio.getNumInstrumento() != null && ditSocio.getNumInstrumento() != "" ? Integer.parseInt(ditSocio.getNumInstrumento()) : 0);
			socio.setNotariaCorreduria(ditSocio.getNumNotaria());
			socio.setFechaExpedicionContrato(ditSocio.getFecContrato());
			EntidadFederativa estado = this.getEstadoFromDB(ditSocio.getCveEnt());
			if (estado != null){
				estado.setClave(ditSocio.getCveEnt());
				estado.setNombre(estado.getNombre());
				
			} else {
				estado = new EntidadFederativa();
				estado.setClave(ditSocio.getCveEnt());
			}
			socio.setEstado(estado);
		}
		socio.setIdPersonaMoralPatron(ditSocio.getDitPatron().getCveIdPersonaMoral());
		socio.setEsNacional(ditSocio.getIndExtranjero().intValue() == 0 ? true : false );
		socio.setEsDomicilioNacional(ditSocio.getIndResidenciaExtranjero().intValue() == 0 ? true : false );
		socio.setDomicilioFiscal(null); // TODO DomicilioFiscal pendiente en socios
		socio.setFecRegistroActualizado(ditSocio.getFecRegistroActualizado());
		socio.setFecRegistroAlta(ditSocio.getFecRegistroAlta());
		socio.setFecRegistroBaja(ditSocio.getFecRegistroBaja());
		// TODO verificar los demas valores por asignar para socios.		
		return socio;
	}
	
	public DitSocio prepararAltaSocio(Socio socio){
		DitSocio ditSocio = new DitSocio();
		//Asociar patron
		ditSocio.setDitPatron(new DitPersonaMoral());
		ditSocio.getDitPatron().setCveIdPersonaMoral(socio.getIdPersonaMoralPatron());
		ditSocio.setFecRegistroAlta(new Date());
		ditSocio.setIndExtranjero(BigDecimal.ZERO);
		ditSocio.setIndResidenciaExtranjero(BigDecimal.ZERO);		
		if(socio.getPersonaFisica()!=null){
			ditSocio.setDitPersonaFisica(new DitPersonaFisica());
			ditSocio.getDitPersonaFisica()
				.setCveIdPersonaFisica(socio.getPersonaFisica().getCveFisica());
			ditSocio.setIndTipoSocio(new BigDecimal(TipoPersona.TIPO_PERSONA_FISICA.toString()));
			ditSocio.setNomNombre(socio.getPersonaFisica().getNombre());
			ditSocio.setNomPrimerApellido(socio.getPersonaFisica().getPrimerApellido());
			ditSocio.setNomSegundoApellido(socio.getPersonaFisica().getSegundoApellido());
		}else{
			ditSocio.setDitPersonaMoral(new DitPersonaMoral());
			ditSocio.getDitPersonaMoral()
				.setCveIdPersonaMoral(socio.getPersonaMoral().getIdPersona());
			ditSocio.setIndTipoSocio(new BigDecimal(TipoPersona.TIPO_PERSONA_MORAL.toString()));
			ditSocio.setDesDenomRazonSocial(socio.getPersonaMoral().getRazonSocial());
		}
		return ditSocio;
	}
	
	public void validarSocioAlta(Socio socio)throws GestionPatronalBusinessException{
		//Validar 1) La empresa no debe ser socia de si misma. 2) Socio ya registrado.
		if (StringUtils.isNotBlank(socio.getRfcPersonaMoralPatron())) {			
			if(socio.getRfc().equals(socio.getRfcPersonaMoralPatron())){
				throw new GestionPatronalBusinessException("La empresa NO puede ser socia de sí misma.");							
			}else{
				List<Socio> listaSocios = socioServiceEntity.obtenerSociosPorIdPersonaMoralPatron(socio);
				if(!CollectionUtils.isEmpty(listaSocios)){
					for(Socio s : listaSocios){					
						if(s.getRfc()!=null && StringUtils.isNotBlank(s.getRfc()) && 
							s.getRfc().equals(socio.getRfc())){
							throw new GestionPatronalBusinessException("El socio ya se encuentra registrado.");
						}
					}				
				}
			}
		}
	}
	
	public Solicitud generarSolicitudAltaSocio(Socio socio, OrigenSolicitudEnum origenSolicitud,
			Usuario usuario){
		Date fechaActual = new Date();		
		
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitante(usuario);
		solicitud.setOrigenSolicitud(new OrigenSolicitud());
		solicitud.getOrigenSolicitud().setIdTipoSolicitud(origenSolicitud.getId());
		solicitud.setFechaSolicitud(fechaActual);
		solicitud.setFechaPresentacion(fechaActual);
		solicitud.setTipoSolicitud(new TipoSolicitud());
		solicitud.getTipoSolicitud().setIdTipoSolicitud(TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES.getValor().longValue());
		solicitud.setEstadoSolicitud(new EstadoSolicitud());
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.REGISTRADA.getCodigo());
		solicitud.setTramites(new ArrayList<Tramite>());
		TramiteSocios tramiteSocios = new TramiteSocios();
		tramiteSocios.setPatron(new Moral());
		tramiteSocios.getPatron().setIdPersona(socio.getIdPersonaMoralPatron());
		tramiteSocios.setListaSocios(null);
		if(socio.getTipoSocio().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
			tramiteSocios.setSociosFisico(socio.getPersonaFisica());
		}else{
			tramiteSocios.setSocioMoral(socio.getPersonaMoral());
		}		
		tramiteSocios.setEstadoTramite(new EstadoTramite());
		tramiteSocios.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO.getCodigo());
		tramiteSocios.getEstadoTramite().setDescripcion(EstadoTramiteEnum.INICIADO.getDescripcion());
		tramiteSocios.setTipoTramite(new TipoTramite());
		tramiteSocios.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.ACTUALIZACION_SOCIO.getCodigo());
		tramiteSocios.setFechaTramite(fechaActual);
		tramiteSocios.setFechaPresentacion(fechaActual);
		tramiteSocios.setAcuseVentanilla(new AcuseVentanilla());
		if(usuario != null){
			tramiteSocios.getAcuseVentanilla().setUsuarioVentanilla(usuario.getUsuario());
			if(usuario.getUsuarioFuncionario()!=null 
						&& usuario.getUsuarioFuncionario().getSubdelegacion()!=null){
				tramiteSocios.getAcuseVentanilla().setIdSubdelegacion(usuario
					.getUsuarioFuncionario().getSubdelegacion().getId());
			}		
		}
		solicitud.getTramites().add(tramiteSocios);
		return solicitud;
	}
	
	public Solicitud generarSolicitudBajaSocio(Socio socio, List<Socio> listaSocios, 
			OrigenSolicitudEnum origenSolicitud, Usuario usuario){
		Date fechaActual = new Date();		
		
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitante(usuario);
		solicitud.setOrigenSolicitud(new OrigenSolicitud());
		solicitud.getOrigenSolicitud().setIdTipoSolicitud(origenSolicitud.getId());		
		solicitud.setFechaSolicitud(fechaActual);
		solicitud.setFechaPresentacion(fechaActual);
		solicitud.setTipoSolicitud(new TipoSolicitud());
		solicitud.getTipoSolicitud().setIdTipoSolicitud(TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES.getValor().longValue());
		solicitud.setEstadoSolicitud(new EstadoSolicitud());
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.REGISTRADA.getCodigo());
		solicitud.setTramites(new ArrayList<Tramite>());		
		TramiteSocios tramiteSocios = new TramiteSocios();
		tramiteSocios.setPatron(new Moral());
		tramiteSocios.getPatron().setIdPersona(socio.getIdPersonaMoralPatron());
		tramiteSocios.setListaSocios(new ArrayList<Socio>());		
		tramiteSocios.setEstadoTramite(new EstadoTramite());
		tramiteSocios.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO.getCodigo());
		tramiteSocios.getEstadoTramite().setDescripcion(EstadoTramiteEnum.INICIADO.getDescripcion());
		tramiteSocios.setTipoTramite(new TipoTramite());
		tramiteSocios.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.BAJA_SOCIO.getCodigo());
		tramiteSocios.setFechaTramite(fechaActual);
		tramiteSocios.setFechaPresentacion(fechaActual);
		tramiteSocios.setAcuseVentanilla(new AcuseVentanilla());
		if(usuario != null){
			tramiteSocios.getAcuseVentanilla().setUsuarioVentanilla(usuario.getUsuario());
			if(usuario.getUsuarioFuncionario()!=null 
						&& usuario.getUsuarioFuncionario().getSubdelegacion()!=null){
				tramiteSocios.getAcuseVentanilla().setIdSubdelegacion(usuario
					.getUsuarioFuncionario().getSubdelegacion().getId());
			}
		}
		solicitud.getTramites().add(tramiteSocios);
		return solicitud;
	}	
	
	private Fisica convertirEntityToModelFisica(DitPersonaFisica entity) {
		Fisica fisica = new Fisica();
		fisica.setCveFisica(entity.getCveIdPersonaFisica());
		fisica.setIdPersona(entity.getDitPersona().getCveIdPersona());
		fisica.setNombre(entity.getDitPersona().getNomNombre());
		fisica.setPrimerApellido(entity.getDitPersona().getNomPrimerApellido());
		fisica.setSegundoApellido(entity.getDitPersona().getNomSegundoApellido());
		
		if(!StringUtils.isBlank(entity.getDitPersona().getRfc()))
			fisica.setRfc(entity.getDitPersona().getRfc());
		else
			fisica.setRfc(entity.getRfc());
		
		fisica.setCurp(entity.getDitPersona().getCurp());
		fisica.setSexo(convertirEntityToModelSexo(entity.getDitPersona().getDicSexo()));
		fisica.setFechaNacimiento(entity.getDitPersona().getFecNacimiento());
		
		return fisica;
	}


	private Moral convertirEntityToModelMoral(DitPersonaMoral entity) {
		Moral moral = new Moral();
		moral.setCveMoral(entity.getCveIdPersonaMoral());
		moral.setRazonSocial(entity.getDenominacionRazonSocial());
		moral.setRfc(entity.getRfc());
		moral.setTipoSociedad(convertirEntityToModelTipoSociedad(entity.getDicTipoSociedad()));
		
		return moral;
	}
	
	private Sexo convertirEntityToModelSexo(DicSexo entity) {
		
		if(entity== null)
			return new Sexo();
		Sexo model = new Sexo();
		model.setIdSexo(entity.getCveIdSexo().intValue());
		model.setDescripcion(entity.getDesSexo());
	
		return model;
	}
	
	private TipoSociedad convertirEntityToModelTipoSociedad(
			DicTipoSociedad entity) {
		if(entity== null)
			return new TipoSociedad();
		TipoSociedad model = new TipoSociedad();
		model.setIdTipoSociedad(entity.getCveIdTipoSociedad().longValue());
		model.setDescripcion(entity.getDesTipoSociedad());
		model.setDescripcionAbreviada(entity.getDesTipoSociedadAbrev());
		
		return model;
	}
	
	private EntidadFederativa getEstadoFromDB(String cveEnt) {		
		EntidadFederativa estado = null;		
		DgCatEstado estado2 = this.socioServiceEntity.getEstado(cveEnt);		
		if (estado2 != null){
			estado = new EntidadFederativa();
			estado.setClave(cveEnt);
			estado.setNombre(estado2.getNomEnt());
		}		
		return estado;
	}
	
	@Deprecated
	public Socio convertirEntityToModelSocioFisico(DitSocio entity)
			throws Exception {
		DitPersonaFisica persona = entity.getDitPersonaFisica();
//		String registroPatronal = entity.getDitPatronSujetoObligado().getDitPatronGenerals().get(0).getRegPatron();
		String razonSocial = persona.getDitPersona().getNomNombre()+" "+persona.getDitPersona().getNomPrimerApellido()+" "+persona.getDitPersona().getNomSegundoApellido();
		Socio model = new Socio();
		model.setIdSocio(entity.getCveIdSocio());
//		model.setRegistroPatronal(registroPatronal);
		model.setNombres(persona.getDitPersona().getNomNombre());
		model.setPrimerApellido(persona.getDitPersona().getNomPrimerApellido());
		model.setSegundoApellido(persona.getDitPersona().getNomSegundoApellido());
		model.setDenominacionRazonSocial(razonSocial);
		model.setRfc(persona.getDitPersona().getRfc());
		model.setCurp(persona.getDitPersona().getCurp());
		model.setEsNacional(Boolean.FALSE);
		model.setEsPersonaFisica(Boolean.TRUE);
		
		return model;
	}

	@Deprecated
	public DitSocio convertirModelToEntity(Socio socio) throws Exception {
		
		DitSocio ditSocio = new DitSocio();
		DitPersonaMoral ditPatron = new DitPersonaMoral();
		DitPersonaMoral ditPersonaMoral = new DitPersonaMoral();
		DitPersonaFisica ditPersonaFisica = new DitPersonaFisica();
		
		if(socio.getIdSocio()!=null)
			ditSocio.setCveIdSocio(socio.getIdSocio());
		
		//ditSocio.setDesDenomRazonSocial(socio.getDenominacionRazonSocial());
		ditSocio.setDesDenomRazonSocial(socio.getNombreRazonSocial());
		ditSocio.setFecContrato(socio.getFechaExpedicionContrato());
		ditSocio.setFecRegistroActualizado(socio.getFecRegistroActualizado());
		ditSocio.setFecRegistroAlta(socio.getFecRegistroAlta());
		ditSocio.setFecRegistroBaja(socio.getFecRegistroBaja());
		ditSocio.setIndExtranjero(socio.getEsNacional() ? BigDecimal.ZERO : BigDecimal.ONE );
		ditSocio.setIndResidenciaExtranjero(socio.getEsDomicilioNacional() ? BigDecimal.ZERO : BigDecimal.ONE );
		ditSocio.setIndTipoSocio(new BigDecimal(socio.getTipoSocio().getIdTipoPersona().intValue()));
		ditSocio.setNomNombre(socio.getNombres());
		ditSocio.setNomPrimerApellido(socio.getPrimerApellido());
		ditSocio.setNomSegundoApellido(socio.getSegundoApellido());
		ditSocio.setNumInstrumento(String.valueOf(socio.getNumeroInstrumetoProtocolizacion()));
		ditSocio.setNumNotaria(socio.getNotariaCorreduria());
		
		if (socio.getEstado() != null){
			ditSocio.setCveEnt(socio.getEstado().getClave());
		}
		
		if (socio.getIdPersonaMoralPatron() != null){
			ditPatron.setCveIdPersonaMoral(socio.getIdPersonaMoralPatron());
			ditSocio.setDitPatron(ditPatron);
		}
		
		if (socio.getTipoSocio().getIdTipoPersona().intValue() == 1){
			//ditPersonaFisica.setCveIdPersonaFisica(socio.getIdPersona());
			ditSocio.setDitPersonaFisica(ditPersonaFisica);
		} else {
			ditPersonaMoral.setCveIdPersonaMoral(socio.getIdPersona());
			ditSocio.setDitPersonaMoral(ditPersonaMoral);
			
			if (socio.getTipoSocio().getIdTipoPersona().intValue() == 2){ // socio moral
				
				if (socio.getEscrituraConstitutiva() != null){
					List<DitActaConstitutiva> ditActaConstitutivas = new ArrayList<DitActaConstitutiva>();
					ditActaConstitutivas.add(this.escrituraConstitutivaServiceUtility.convertirModelToEntity(socio.getEscrituraConstitutiva()));
					
					ditPersonaMoral.setDitActaConstitutivas(ditActaConstitutivas);
				}
			}
		}
		
		if (socio.getDomicilioFiscal() != null){
			if (socio.getEsDomicilioNacional().booleanValue()){
				
				DgDomicilioGeografico dgDomicilioGeografico = new DgDomicilioGeografico();
				DitPersonafDom ditPersonafDom = new DitPersonafDom();
				DitPersonamDom ditPersonamDom = new DitPersonamDom();
				DitPersona ditPersona = new DitPersona();
				DicTipoDomicilio dicTipoDomicilio = new DicTipoDomicilio();
				DgCatTipoDom dgCatTipoDom = new DgCatTipoDom();
				DgVialidad dgVialidadByCveViaPrin = new DgVialidad();
				DgVialidad dgVialidadByCveViaRef1 = new DgVialidad();
				DgVialidad dgVialidadByCveViaRef2 = new DgVialidad();
				DgVialidad dgVialidadByCveViaRef3 = new DgVialidad();
				
				dicTipoDomicilio.setCveIdTipoDomicilio(1L); // dom. fiscal
				dgCatTipoDom.setCveTipoDom(new Integer(1));
				
				dgDomicilioGeografico.setDgCatTipoDom(dgCatTipoDom); // aplica para domicilio geografico
				
				if (socio.getTipoSocio().getIdTipoPersona().intValue() == 1){ // datos de domicilio fiscal para socios fisicos
					
					dgDomicilioGeografico.setNomvial(socio.getDomicilioFiscal().getCalle());
					dgDomicilioGeografico.setNumextalf(socio.getDomicilioFiscal().getNumExteriorAlf());
					dgDomicilioGeografico.setNumintalf(socio.getDomicilioFiscal().getNumInteriorAlf());
					
					dgVialidadByCveViaPrin.setNomVia(socio.getDomicilioFiscal().getVialidadPrimaria() != null ? socio.getDomicilioFiscal().getVialidadPrimaria().getNombre() : "");
					dgVialidadByCveViaRef1.setNomVia(socio.getDomicilioFiscal().getVialidadReferenciaPrimaria() != null ? socio.getDomicilioFiscal().getVialidadReferenciaPrimaria().getNombre() : "");
					dgVialidadByCveViaRef2.setNomVia(socio.getDomicilioFiscal().getVialidadReferenciaSecundaria() != null ? socio.getDomicilioFiscal().getVialidadReferenciaSecundaria().getNombre() : "" );
					dgVialidadByCveViaRef3.setNomVia(socio.getDomicilioFiscal().getVialidadReferenciaPosterior() != null ? socio.getDomicilioFiscal().getVialidadReferenciaPosterior().getNombre() : "");
					
					dgDomicilioGeografico.setDgVialidadByCveViaPrin(dgVialidadByCveViaPrin); // ref. primaria
					dgDomicilioGeografico.setDgVialidadByCveViaRef1(dgVialidadByCveViaRef1); // ref. pirmaria, por si las moscotas del eriso, chia!!
					dgDomicilioGeografico.setDgVialidadByCveViaRef2(dgVialidadByCveViaRef2); // ref. sec
					dgDomicilioGeografico.setDgVialidadByCveViaRef3(dgVialidadByCveViaRef3); // ref. posterior
					
					if (socio.getDomicilioFiscal().getAsentamiento() != null){
						
						DgAsentamiento dgAsentamiento = new DgAsentamiento();
						dgAsentamiento.setNomAsen(socio.getDomicilioFiscal().getAsentamiento().getNombre());
						
						if (socio.getDomicilioFiscal().getAsentamiento().getLocalidad() != null){
							
							DgCatLocalidad dgCatLocalidad = new DgCatLocalidad();
							dgCatLocalidad.setNomLoc(socio.getDomicilioFiscal().getAsentamiento().getLocalidad().getNombre());
							
							if(socio.getDomicilioFiscal().getAsentamiento().getLocalidad().getMunicipio() != null){
								DgCatMunicipio dgCatMunicipio = new DgCatMunicipio();
								dgCatMunicipio.setNomMun(socio.getDomicilioFiscal().getAsentamiento().getLocalidad().getMunicipio().getNombre());
								
								if (socio.getDomicilioFiscal().getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa() != null){
									DgCatEstado dgCatEstado = new DgCatEstado();
									dgCatEstado.setNomEnt(socio.getDomicilioFiscal().getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre());
									dgCatMunicipio.setDgCatEstado(dgCatEstado );
								}
								dgCatLocalidad.setDgCatMunicipio(dgCatMunicipio );
							}
							dgDomicilioGeografico.setDgCatLocalidad(dgCatLocalidad);
						}
						dgDomicilioGeografico.setDgAsentamiento(dgAsentamiento);
					}
					
					ditPersona.setCveIdPersona(socio.getIdPersona());
					ditPersonafDom.setDitPersona(ditPersona);
					ditPersonafDom.setFecRegistroAlta(new Date());
					ditPersonafDom.setDgDomicilioGeografico(dgDomicilioGeografico);
					ditPersonafDom.setDicTipoDomicilio(dicTipoDomicilio);
					
					List<DitPersonafDom> ditPersonafDoms = new ArrayList<DitPersonafDom>();
					ditPersonafDoms.add(ditPersonafDom);
					ditPersona.setDitPersonafDoms(ditPersonafDoms);
					
					ditPersonaFisica.setDitPersona(ditPersona);
					
				} else { // datos de domicilio fiscal para socios morales y fideicomiso
					dgDomicilioGeografico.setNomvial(socio.getDomicilioFiscal().getCalle());
					dgDomicilioGeografico.setNumextalf(socio.getDomicilioFiscal().getNumExteriorAlf());
					dgDomicilioGeografico.setNumintalf(socio.getDomicilioFiscal().getNumInteriorAlf());
					
					dgVialidadByCveViaPrin.setNomVia(socio.getDomicilioFiscal().getVialidadPrimaria() != null ? socio.getDomicilioFiscal().getVialidadPrimaria().getNombre() : "");
					dgVialidadByCveViaRef1.setNomVia(socio.getDomicilioFiscal().getVialidadReferenciaPrimaria() != null ? socio.getDomicilioFiscal().getVialidadReferenciaPrimaria().getNombre() : "");
					dgVialidadByCveViaRef2.setNomVia(socio.getDomicilioFiscal().getVialidadReferenciaSecundaria() != null ? socio.getDomicilioFiscal().getVialidadReferenciaSecundaria().getNombre() : "" );
					dgVialidadByCveViaRef3.setNomVia(socio.getDomicilioFiscal().getVialidadReferenciaPosterior() != null ? socio.getDomicilioFiscal().getVialidadReferenciaPosterior().getNombre() : "");
					
					dgDomicilioGeografico.setDgVialidadByCveViaPrin(dgVialidadByCveViaPrin); // ref. primaria
					dgDomicilioGeografico.setDgVialidadByCveViaRef1(dgVialidadByCveViaRef1); // ref. pirmaria, por si las moscotas del eriso, chia!!
					dgDomicilioGeografico.setDgVialidadByCveViaRef2(dgVialidadByCveViaRef2); // ref. sec
					dgDomicilioGeografico.setDgVialidadByCveViaRef3(dgVialidadByCveViaRef3); // ref. posterior
					
					if (socio.getDomicilioFiscal().getAsentamiento() != null){
						
						DgAsentamiento dgAsentamiento = new DgAsentamiento();
						dgAsentamiento.setNomAsen(socio.getDomicilioFiscal().getAsentamiento().getNombre());
						
						if (socio.getDomicilioFiscal().getAsentamiento().getLocalidad() != null){
							
							DgCatLocalidad dgCatLocalidad = new DgCatLocalidad();
							dgCatLocalidad.setNomLoc(socio.getDomicilioFiscal().getAsentamiento().getLocalidad().getNombre());
							
							if(socio.getDomicilioFiscal().getAsentamiento().getLocalidad().getMunicipio() != null){
								DgCatMunicipio dgCatMunicipio = new DgCatMunicipio();
								dgCatMunicipio.setNomMun(socio.getDomicilioFiscal().getAsentamiento().getLocalidad().getMunicipio().getNombre());
								
								if (socio.getDomicilioFiscal().getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa() != null){
									DgCatEstado dgCatEstado = new DgCatEstado();
									dgCatEstado.setNomEnt(socio.getDomicilioFiscal().getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre());
									dgCatMunicipio.setDgCatEstado(dgCatEstado );
								}
								dgCatLocalidad.setDgCatMunicipio(dgCatMunicipio );
							}
							dgDomicilioGeografico.setDgCatLocalidad(dgCatLocalidad);
						}
						dgDomicilioGeografico.setDgAsentamiento(dgAsentamiento);
					}
					
					ditPersonamDom.setDitPersonaMoral(ditPersonaMoral);
					ditPersonamDom.setFecRegistroAlta(new Date());
					ditPersonamDom.setDgDomicilioGeografico(dgDomicilioGeografico);
					ditPersonamDom.setDicTipoDomicilio(dicTipoDomicilio);
					
					List<DitPersonamDom> ditPersonamDoms = new ArrayList<DitPersonamDom>();
					
					ditPersonamDoms.add(ditPersonamDom);
					
					ditPersonaMoral.setDitPersonamDoms(ditPersonamDoms);
					
					ditSocio.setDitPersonaMoral(ditPersonaMoral);
					
					
				}
			}
		}		
		//ditSocio.setDitSocioContactos(socio.getMediosContacto()); // TODO CESAREO datso contacto		
		return ditSocio;
	}

	@Deprecated
	public DitSocio convertirModelToEntity1(Socio socio,
			DitPersonaFisica ditPersonaFisica) throws Exception {
		DitSocio ditSocio = new DitSocio();
		DitPersonaMoral ditPatron = new DitPersonaMoral();
		
		ditSocio.setCveIdSocio(socio.getIdSocio());
		//ditSocio.setDesDenomRazonSocial(socio.getDenominacionRazonSocial());
		ditSocio.setDesDenomRazonSocial(socio.getNombreRazonSocial());
		ditSocio.setFecContrato(socio.getFechaExpedicionContrato());
		ditSocio.setFecRegistroActualizado(socio.getFecRegistroActualizado());
		ditSocio.setFecRegistroAlta(socio.getFecRegistroAlta() != null ? socio.getFecRegistroAlta() : new Date());
		ditSocio.setFecRegistroBaja(socio.getFecRegistroBaja());
		ditSocio.setIndExtranjero(socio.getEsNacional() ? BigDecimal.ZERO : BigDecimal.ONE );
		ditSocio.setIndResidenciaExtranjero(socio.getEsDomicilioNacional() ? BigDecimal.ZERO : BigDecimal.ONE );
		ditSocio.setIndTipoSocio(new BigDecimal(socio.getTipoSocio().getIdTipoPersona().intValue()));
		ditSocio.setNomNombre(socio.getNombres());
		ditSocio.setNomPrimerApellido(socio.getPrimerApellido());
		ditSocio.setNomSegundoApellido(socio.getSegundoApellido());
		ditSocio.setNumInstrumento(String.valueOf(socio.getNumeroInstrumetoProtocolizacion()));
		ditSocio.setNumNotaria(socio.getNotariaCorreduria());
		
		ditSocio.setDitPersonaFisica(ditPersonaFisica);
		
		if (socio.getEstado() != null){
			ditSocio.setCveEnt(socio.getEstado().getClave());
		}
		
		if (socio.getIdPersonaMoralPatron() != null){
			ditPatron.setCveIdPersonaMoral(socio.getIdPersonaMoralPatron());
			ditSocio.setDitPatron(ditPatron);
		}
		
		if (socio.getDomicilioFiscal() != null){
			if (socio.getEsDomicilioNacional().booleanValue()){
				
				DgDomicilioGeografico dgDomicilioGeografico = new DgDomicilioGeografico();
				DitPersonafDom ditPersonafDom = new DitPersonafDom();
				DitPersona ditPersona = new DitPersona();
				DicTipoDomicilio dicTipoDomicilio = new DicTipoDomicilio();
				DgCatTipoDom dgCatTipoDom = new DgCatTipoDom();
				DgVialidad dgVialidadByCveViaPrin = new DgVialidad();
				DgVialidad dgVialidadByCveViaRef1 = new DgVialidad();
				DgVialidad dgVialidadByCveViaRef2 = new DgVialidad();
				DgVialidad dgVialidadByCveViaRef3 = new DgVialidad();
				
				dicTipoDomicilio.setCveIdTipoDomicilio(1L); // dom. fiscal
				dgCatTipoDom.setCveTipoDom(new Integer(1));
				
				dgDomicilioGeografico.setDgCatTipoDom(dgCatTipoDom); // aplica para domicilio geografico
				
				dgDomicilioGeografico.setNomvial(socio.getDomicilioFiscal().getCalle());
				dgDomicilioGeografico.setNumextalf(socio.getDomicilioFiscal().getNumExteriorAlf());
				dgDomicilioGeografico.setNumintalf(socio.getDomicilioFiscal().getNumInteriorAlf());
				
				dgVialidadByCveViaPrin.setNomVia(socio.getDomicilioFiscal().getVialidadPrimaria() != null ? socio.getDomicilioFiscal().getVialidadPrimaria().getNombre() : "");
				dgVialidadByCveViaRef1.setNomVia(socio.getDomicilioFiscal().getVialidadReferenciaPrimaria() != null ? socio.getDomicilioFiscal().getVialidadReferenciaPrimaria().getNombre() : "");
				dgVialidadByCveViaRef2.setNomVia(socio.getDomicilioFiscal().getVialidadReferenciaSecundaria() != null ? socio.getDomicilioFiscal().getVialidadReferenciaSecundaria().getNombre() : "" );
				dgVialidadByCveViaRef3.setNomVia(socio.getDomicilioFiscal().getVialidadReferenciaPosterior() != null ? socio.getDomicilioFiscal().getVialidadReferenciaPosterior().getNombre() : "");
				
				dgDomicilioGeografico.setDgVialidadByCveViaPrin(dgVialidadByCveViaPrin); // ref. primaria
				dgDomicilioGeografico.setDgVialidadByCveViaRef1(dgVialidadByCveViaRef1); // ref. pirmaria, por si las moscotas del eriso, chia!!
				dgDomicilioGeografico.setDgVialidadByCveViaRef2(dgVialidadByCveViaRef2); // ref. sec
				dgDomicilioGeografico.setDgVialidadByCveViaRef3(dgVialidadByCveViaRef3); // ref. posterior
				
				if (socio.getDomicilioFiscal().getAsentamiento() != null){
					
					DgAsentamiento dgAsentamiento = new DgAsentamiento();
					dgAsentamiento.setNomAsen(socio.getDomicilioFiscal().getAsentamiento().getNombre());
					
					if (socio.getDomicilioFiscal().getAsentamiento().getLocalidad() != null){
						
						DgCatLocalidad dgCatLocalidad = new DgCatLocalidad();
						dgCatLocalidad.setNomLoc(socio.getDomicilioFiscal().getAsentamiento().getLocalidad().getNombre());
						
						if(socio.getDomicilioFiscal().getAsentamiento().getLocalidad().getMunicipio() != null){
							DgCatMunicipio dgCatMunicipio = new DgCatMunicipio();
							dgCatMunicipio.setNomMun(socio.getDomicilioFiscal().getAsentamiento().getLocalidad().getMunicipio().getNombre());
							
							if (socio.getDomicilioFiscal().getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa() != null){
								DgCatEstado dgCatEstado = new DgCatEstado();
								dgCatEstado.setNomEnt(socio.getDomicilioFiscal().getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre());
								dgCatMunicipio.setDgCatEstado(dgCatEstado );
							}
							dgCatLocalidad.setDgCatMunicipio(dgCatMunicipio );
						}
						dgDomicilioGeografico.setDgCatLocalidad(dgCatLocalidad);
					}
					dgDomicilioGeografico.setDgAsentamiento(dgAsentamiento);
				}
				
				ditPersona.setCveIdPersona(socio.getIdPersona());
				ditPersonafDom.setDitPersona(ditPersona);
				ditPersonafDom.setFecRegistroAlta(new Date());
				ditPersonafDom.setDgDomicilioGeografico(dgDomicilioGeografico);
				ditPersonafDom.setDicTipoDomicilio(dicTipoDomicilio);
				
				List<DitPersonafDom> ditPersonafDoms = new ArrayList<DitPersonafDom>();
				ditPersonafDoms.add(ditPersonafDom);
				ditPersona.setDitPersonafDoms(ditPersonafDoms);
				
				ditPersonaFisica.setDitPersona(ditPersona);
			}
		}
		
		return ditSocio;
	}
		

	
}
