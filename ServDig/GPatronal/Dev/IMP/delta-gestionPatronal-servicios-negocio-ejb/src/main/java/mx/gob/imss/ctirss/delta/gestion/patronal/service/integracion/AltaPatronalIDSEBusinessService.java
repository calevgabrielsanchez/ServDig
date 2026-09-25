package mx.gob.imss.ctirss.delta.gestion.patronal.service.integracion;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.integracion.AltaPatronalIDSEIntegrador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fiel;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.integracion.patronal.CertificadoIDSE;
import mx.gob.imss.ctirss.delta.model.gestion.integracion.patronal.DatosAltaIDSE;
import mx.gob.imss.ctirss.delta.model.gestion.integracion.patronal.PersonaIDSE;
import mx.gob.imss.ctirss.delta.model.gestion.integracion.patronal.RegistroIDSE;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteMoral;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

@Stateless(name = "altaIdse", mappedName = "altaIdse")
public class AltaPatronalIDSEBusinessService extends AbstractServiceBusiness
		implements AltaPatronalIDSEIntegrador {

	private static final Logger log = LoggerFactory
		.getLogger(AltaPatronalIDSEBusinessService.class);
	
	@EJB
	private SujetoObligadoServiceBusinessRemote registroPatronalService; 
	@EJB
	private SolicitudBusinessRemote solicitudBusiness;
	@EJB
	private PersonaBusinessRemote personaBusiness;
	@EJB
	private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
	@EJB
	private PersonaMoralBusinessRemote personaMoralBusiness;
	@EJB
	private DomicilioServiceBusinessRemote domicilioServiceBusiness;
	@EJB
	private MediosContactoServiceBusinessRemote mediosContactoServiceBusiness;
	@EJB
	SolicitudServiceBusinessRemote solicitudService;
	
	@Override
	public RegistroIDSE prepararDatosMovimientosIdse(String folioSolicitud)
			throws SolicitudNoEncontradaException {
		
		RegistroIDSE registroIDSE =  obtenerDatosParaIdse(folioSolicitud);
		log.info("RegistroIDSE {}", registroIDSE.toString());
		
		return registroIDSE;
	}
	
	private RegistroIDSE obtenerDatosParaIdse(String folioSolicitud)
			throws SolicitudNoEncontradaException {
		
		Solicitud solicitud = new Solicitud();
		solicitud.setNoFolioSolicitud(folioSolicitud);
		
		solicitud = this.solicitudBusiness.consultarFolio(solicitud);
		
		int cveTipoSolicitud = solicitud.getTipoSolicitud().getIdTipoSolicitud().intValue();
		TipoSolicitudEnum tipoSolicitud = TipoSolicitudEnum.obtenerEnumById(cveTipoSolicitud);
		OrigenSolicitudEnum origenSolicitud = OrigenSolicitudEnum
			.getById(solicitud.getOrigenSolicitud().getIdTipoSolicitud());
		
		RegistroIDSE registroIDSE =  new RegistroIDSE();
		PersonaIDSE patron = null;
		PersonaIDSE representanteLegal = null;
		CertificadoIDSE  certificado = new CertificadoIDSE();
		List<PersonaIDSE> representantes = new ArrayList<PersonaIDSE>();
		int idTipoTramite = 0;
		
		switch (tipoSolicitud) {
		case ALTA_PATRONAL:
			for (Tramite tramite : solicitud.getTramites()) {
				if (tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ALTA_SRT.getCodigo()) ||
						tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ALTA_SRT_PM.getCodigo())) {					
					
					idTipoTramite = tramite.getTipoTramite().getIdTipoTramite();					
					TramiteSujetoObligado tso = (TramiteSujetoObligado) tramite;
					SujetoObligado so = tso.getSujetoObligado();
					List<RepresentanteLegal> listaRLs = so.getRepresentantesLegales();
					String nrp = so.getNumeroRegistroPatronal();
					so = registroPatronalService.obtenerDetalleSujetoObligadoActividadEconomica(so);
					//Setear valores originales del tramite.
					so.setRepresentantesLegales(listaRLs);
					so.setNumeroRegistroPatronal(nrp);
					
					String domicilioCompletoPatron = null;
					String correoPatron = null;					
					String domicilioCT = null;
					String telefonoCT = null;
					String correoCT = null;
					String localidadSINDO = null;					
					String cveSector="90";
					
					if(so.getCntroTrabajo()!=null){
						domicilioCT = obtenerDomicilioCompleto(so.getCntroTrabajo(), true);						
						correoCT = obtenerCorreoCentroTrabajo(so.getCntroTrabajo());
						localidadSINDO = obtenerLocalidadSINDO(so.getCntroTrabajo());		
						telefonoCT = obtenerTelefonoCentroTrabajo(so.getCntroTrabajo());
						if(telefonoCT!=null)
							telefonoCT = telefonoCT.replace("|", "-");						
					}
					
					Integer clase = so.getClasificacion().getFraccion().getClase().getClave().intValue();
					String cveDelegacion = so.getSubdelegacion().getDelegacion().getClave();
					String cveMunicipio = so.getMunicipioIMSS().getCvecMunicipioSINDO();
					String cveSubdelegacion = so.getSubdelegacion().getClave();
					Fraccion fraccion = so.getClasificacion().getFraccion();
					Division division = fraccion.getGrupo().getDivision();
					Grupo grupo = fraccion.getGrupo();
					String fraccionCompleta = division.getNumDivision() + grupo.getNumGrupo() 
						+ String.format("%02d", Integer.parseInt(fraccion.getNumFraccion()));
					String actividad = so.getClasificacion().getFraccion().getDescripcionDetallada();
					if(actividad.length()>40)
						actividad=actividad.substring(0,40);
					
					//PATRON
					if (so.getTipoPersonaFiscal().getCodigo()
							.equals(TipoPersonaFiscal.FISICA.getCodigo())) {						
						Fisica fisica = so.getFisica();
						patron = obtenerPersonaRegistroIDSE(fisica);						
						domicilioCompletoPatron = obtenerDomicilioCompleto(so.getDomicilioFiscal(), false);
						correoPatron = obtenerCorreo(fisica.getMediosContactoFiscales());						
						certificado = obtenerCertificado(fisica);
						patron.setCertificado(certificado);		
						log.info("Se tiene un ALTA PATRONA PERSONA FISICA para enviar a IDSE {}",  fisica.getRfc());
					} else {
						Moral moral = so.getMoral();
						patron = obtenerPersonaRegistroIDSE(moral);						
						domicilioCompletoPatron = obtenerDomicilioCompleto(so.getDomicilioFiscal(), false);
						correoPatron = obtenerCorreo(moral.getMediosContactoFiscales());						
						certificado = obtenerCertificado(moral);
						patron.setCertificado(certificado);
						log.info("Se tiene un ALTA PATRONA PERSONA MORAL para enviar a IDSE {}",  moral.getRfc());
					}
					//REPRESENTANTE LEGAL
					if(!CollectionUtils.isEmpty(so.getRepresentantesLegales())){
						log.info("Se tiene un ALTA PATRONA con REPRESENTANTE LEGAL para enviar a IDSE{}");
						Fisica repLegalFisica = null;
						for (RepresentanteLegal repLegal : so.getRepresentantesLegales()) {
							repLegalFisica = repLegal.getPersonaFisica();
							repLegalFisica = complementarFisica(repLegalFisica);
							representanteLegal = obtenerPersonaRegistroIDSE(repLegalFisica);							
							certificado = obtenerCertificado(repLegalFisica);
							representanteLegal.setCertificado(certificado);							
							representantes.add(representanteLegal);
						}						
					}
					// Se asignan el domicilio y correo fiscal
					patron.setDomicilioFiscal(domicilioCompletoPatron);
					patron.setCorreoElectronico(correoPatron);
					registroIDSE.setNrp(so.getNumeroRegistroPatronal());
					registroIDSE.setRazonSocial(patron.getNombreRazonSocial());
					//Informacion del CT
					registroIDSE.setDomicilioCentroTrabajo(domicilioCT);
					registroIDSE.setTelefono(telefonoCT);
					registroIDSE.setCveSubdelegacion(cveSubdelegacion);
					registroIDSE.setCveDelegacion(cveDelegacion);
					registroIDSE.setCveMunicipio(cveMunicipio);
					registroIDSE.setCveSector(cveSector);
					registroIDSE.setLocalidad(localidadSINDO);
					registroIDSE.setActividad(actividad);
					registroIDSE.setFraccion(fraccionCompleta);
					registroIDSE.setClase(clase);
					registroIDSE.setCorreo(correoCT);										
					break;
				}
			}
			break;

		case ACTUALIZACION_DATOS_PATRONALES:
			for (Tramite tramite : solicitud.getTramites()) {
				
				if (tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo())) {
					log.debug("Se tiene un ALTA DE REPRESENTANTE LEGAL para enviar a IDSE");					
					idTipoTramite = tramite.getTipoTramite().getIdTipoTramite();					
					TramiteRepresentanteLegal tRL = (TramiteRepresentanteLegal) tramite;
					Fisica fisica = tRL.getFisica();
					fisica = complementarFisica(fisica);
					representanteLegal = obtenerPersonaRegistroIDSE(fisica);
					certificado = obtenerCertificado(fisica);
					representanteLegal.setCertificado(certificado);
					representantes.add(representanteLegal);					
					if (tRL.getFisicaRepresentada() != null) {
						log.debug("El REPRESENTADO es persona FISICA");
						Fisica fisicaRepresentada = complementarFisica(tRL.getFisicaRepresentada());
						patron = obtenerPersonaRegistroIDSE(fisicaRepresentada);
						certificado = obtenerCertificado(fisicaRepresentada);
						patron.setCertificado(certificado);
					} else {
						log.debug("El REPRESENTADO es persona MORAL");
						Moral moralRepresentada = complementarMoral(tRL.getMoralRepresentada());
						patron = obtenerPersonaRegistroIDSE(moralRepresentada);
						certificado = obtenerCertificado(moralRepresentada);
						patron.setCertificado(certificado);
					}
				} else if (tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.BAJA_REPRESENTANTE_LEGAL.getCodigo())) {
					log.info("Se tiene una BAJA DE REPRESENTANTE LEGAL/REPRESENTADO para enviar a IDSE");					
					idTipoTramite = tramite.getTipoTramite().getIdTipoTramite();					
					List<RepresentanteLegal> representantesBaja = null;					
					if (tramite instanceof TramiteFisica) {
						TramiteFisica tramFisica = (TramiteFisica) tramite;
						representantesBaja = tramFisica.getFisica().getRepresentantesLegales();
					} else if (tramite instanceof TramiteMoral) {
						TramiteMoral tramMoral = (TramiteMoral) tramite;
						representantesBaja = tramMoral.getMoral().getRepresentantesLegales();
					}					
					Fisica fisicaDadaBaja = null;
					Fisica patronFisica = null;
					Moral patronMoral = null;
					
					for (RepresentanteLegal repLegal : representantesBaja) {
						if (patronFisica == null && repLegal.getPersonaFisicaRepresentada() != null) {
							patronFisica = repLegal.getPersonaFisicaRepresentada();
						} else if (patronMoral == null && repLegal.getPersonaMoralRepresentada() != null) {
							patronMoral = repLegal.getPersonaMoralRepresentada();
						}						
						fisicaDadaBaja = complementarFisica(repLegal.getPersonaFisica());						
						representanteLegal = obtenerPersonaRegistroIDSE(fisicaDadaBaja);
						certificado = obtenerCertificado(fisicaDadaBaja);
						representanteLegal.setCertificado(certificado);
						representantes.add(representanteLegal);
					}					
					if (patronFisica != null) {
						patronFisica = complementarFisica(patronFisica);
						patron = obtenerPersonaRegistroIDSE(patronFisica);
						certificado = obtenerCertificado(patronFisica);
						patron.setCertificado(certificado);
					} else if (patronMoral != null) {
						patronMoral = complementarMoral(patronMoral);
						patron = obtenerPersonaRegistroIDSE(patronMoral);
						certificado = obtenerCertificado(patronMoral);
						patron.setCertificado(certificado);
					}
				}
				break;
				
			}
			break;

		default:
			log.error("Se recibió una solicitud de tipo no válido para generar registroIDSE");
			break;
		}
		
		registroIDSE.setPatron(patron);
		if(!CollectionUtils.isEmpty(representantes)){
			PersonaIDSE[] representantesIDSE = (PersonaIDSE[]) representantes.toArray(new PersonaIDSE[representantes.size()]);
			registroIDSE.setRepresentantes(representantesIDSE);
		}		
		registroIDSE.setEstatus(1);
		registroIDSE.setIdTipoTramite(idTipoTramite);
		registroIDSE.setIdOrigenSolicitud(origenSolicitud.getId().intValue());
				
		return registroIDSE;
	}
	
	public void writeResult(String writeFileName, String text) {
		try {
			FileWriter fileWriter = new FileWriter(writeFileName, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.newLine();
			bufferedWriter.write(text);
			// Always close files.
			bufferedWriter.close();

		} catch (IOException ex) {
			System.out.println("Error writing to file '" + writeFileName + "'");
		}
	}
	
	private String obtenerCorreo(List<MedioContacto> medios) {
		for (MedioContacto medio : medios) {
			if (medio.getTipoMedioContacto().getIdTipoMedioContacto()
					.equals(TipoMedioContacto.TIPO_CORREO_ELECTRONICO)) {
				return medio.getDesFormaContacto();
			}
		}
		return "";
	}
	
	private String obtenerCorreoCentroTrabajo(CentroTrabajo centroTrabajo) {
		for (MedioContacto medio : centroTrabajo.getMediosContacto()) {
			if (medio.getTipoMedioContacto().getIdTipoMedioContacto()
					.equals(TipoMedioContacto.TIPO_CORREO_ELECTRONICO)) {
				return medio.getDesFormaContacto();
			}
		}
		return "";
	}
	
	
	private String obtenerTelefonoCentroTrabajo(CentroTrabajo cntroTrabajo) {
		for (MedioContacto medio : cntroTrabajo.getMediosContacto()) {
			if (medio.getTipoMedioContacto().getIdTipoMedioContacto()
					.equals(TipoMedioContacto.TIPO_TELEFONO_FIJO)) {
				if (medio.getDesFormaContacto().equals("||"))
					continue;
				else
					return medio.getDesFormaContacto();
			}
		}
		return "";
	}
	
	private String obtenerDomicilioCompleto(Domicilio domicilio, boolean isForSindoIdse) {
		if (domicilio == null)
			return "";

		String calle = StringUtils.isNotBlank(domicilio.getCalle()) ? domicilio.getCalle()
				: domicilio.getVialidadPrimaria() != null ? domicilio.getVialidadPrimaria().getNombre() : "";
		StringBuffer domicilioStr = new StringBuffer(calle);		
		if (domicilio.getNumExterior1() != null && !domicilio.getNumExterior1().equals(0))
			domicilioStr.append(" ").append(safeNull(domicilio.getNumExterior1()));
		if (domicilio.getNumExteriorAlf() != null)
			domicilioStr.append(" ").append(safeNull(domicilio.getNumExteriorAlf()));
		if (domicilio.getNumInterior() != null && !domicilio.getNumInterior().equals(0))
			domicilioStr.append(" ").append(safeNull(domicilio.getNumInterior()));
		if (domicilio.getNumInteriorAlf() != null)
			domicilioStr.append(" ").append(safeNull(domicilio.getNumInteriorAlf()));
		if (domicilio.getAsentamiento() != null
				&& domicilio.getAsentamiento().getNombre() != null)
			domicilioStr.append(" ").append(safeNull(domicilio.getAsentamiento().getNombre()));
		if (domicilio.getAsentamiento() != null
				&& domicilio.getAsentamiento().getLocalidad() != null
				&& domicilio.getAsentamiento().getLocalidad().getMunicipio() != null)
			domicilioStr.append(" ").append(safeNull(domicilio.getAsentamiento().getLocalidad().getMunicipio().getNombre()));
		if (domicilio.getAsentamiento() != null
				&& domicilio.getAsentamiento().getLocalidad() != null
				&& domicilio.getAsentamiento().getLocalidad().getMunicipio() != null
				&& domicilio.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa()!=null)
			domicilioStr.append(", ").append(safeNull(domicilio.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre()));
		
		if (domicilio.getCodigoPostal() != null)
			domicilioStr.append(", C.P. ").append(safeNull(domicilio.getCodigoPostal().getCodigoPostal()));

		
		
		String domicilioCompleto = domicilioStr.toString();
		
		int maxLength = 0;
		if (isForSindoIdse) {
			domicilioCompleto = domicilioCompleto.replaceAll("[\u00F1\u00D1]", "#");
			maxLength = 40;
		} else {
			maxLength = 200;
		}
		
		domicilioCompleto = domicilioCompleto != null ? domicilioCompleto.replace(" null", "") : " ";
		domicilioCompleto = domicilioCompleto != null ? domicilioCompleto.replace(" NULL", "") : " ";
		
		domicilioCompleto = domicilioCompleto != null ? domicilioCompleto
				.length() > maxLength ? domicilioCompleto.substring(0, maxLength) : domicilioCompleto : "Sin domicilio";

		return domicilioCompleto;
	}
	
	private PersonaIDSE obtenerPersonaRegistroIDSE(Fisica fisica) {
		PersonaIDSE persona = new PersonaIDSE();		
		persona.setRfc(fisica.getRfc());
		persona.setNombreRazonSocial(fisica.getNombreCompleto());
		persona.setNombreUsuario(fisica.getRfc());
		persona.setCurp(fisica.getCurp());
		persona.setDomicilioFiscal(obtenerDomicilioCompleto(fisica.getDomicilioFiscal(), false));
		persona.setCorreoElectronico(obtenerCorreo(fisica.getMediosContactoFiscales()));		
		persona.setTipoPersona(TipoPersonaFiscal.FISICA.getCodigo());		
		return persona;
	}
	
	private PersonaIDSE obtenerPersonaRegistroIDSE(Moral moral) {
		PersonaIDSE persona = new PersonaIDSE();		
		persona.setRfc(moral.getRfc());
		StringBuffer razonSocial = new StringBuffer();
		razonSocial.append(moral.getRazonSocial());
		if(moral.getTipoSociedad()!=null&&moral.getTipoSociedad().getDescripcionAbreviada()!=null)
			razonSocial.append(" ").append(moral.getTipoSociedad().getDescripcionAbreviada());
		
		persona.setNombreRazonSocial(razonSocial.toString());
		persona.setNombreUsuario(moral.getRfc());
		persona.setDomicilioFiscal(obtenerDomicilioCompleto(moral.getDomicilioFiscal(), false));
		persona.setCorreoElectronico(obtenerCorreo(moral.getMediosContactoFiscales()));
		persona.setTipoPersona(TipoPersonaFiscal.MORAL.getCodigo());		
		return persona;
	}
	
	private String safeNull(Number nullable) {
        if (nullable == null) {
            return "";
        }
        
        if(nullable !=null && nullable.equals(0))
        	return "";
        return "" + nullable;
    }
	
	private String safeNull(String nullablestring) {
        if (nullablestring == null) {
            return "";
        }else if (nullablestring.equalsIgnoreCase("NULL")) {
            return "";
        }
        nullablestring=nullablestring.trim();
        return nullablestring;
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
	
	private Fisica complementarFisica(Fisica fisica) {		
		Fisica fisicaCmp = this.personaBusiness.getPersonaFisica(fisica.getIdPersona());		
		if (fisicaCmp.getDomicilioFiscal() == null
				|| CollectionUtils.isEmpty(fisicaCmp.getMediosContactoFiscales())) {
			try {
				long cveFisica = this.personaFisicaServiceBusiness.obtenerIDPersonaFisica(fisicaCmp.getIdPersona());
				fisicaCmp.setCveFisica(cveFisica);
				
				if (fisicaCmp.getDomicilioFiscal() == null) {
					try {
						DomicilioFiscal domFiscal = this.domicilioServiceBusiness
								.consultarDomicilioFiscalPersona(fisicaCmp);
						fisicaCmp.setDomicilioFiscal(domFiscal);
					} catch (DomicilioNoLocalizadoException e) {
						log.warn("Error complementarFisica", e);
					}
				}				
				if (CollectionUtils.isEmpty(fisicaCmp.getMediosContactoFiscales())) {
					try {
						List<MedioContacto> mediosFiscales = this.mediosContactoServiceBusiness
								.consultarMediosFiscalesPersona(fisicaCmp);
						fisicaCmp.setMediosContactoFiscales(mediosFiscales);
					} catch (PersonaSinMedioDeContactoException e) {
						log.warn("Error complementarFisica", e);
					}
				}
			} catch (PersonaFisicaNoEncontradaException e) {
				log.warn("Error complementarFisica", e);
			}
		}		
		return fisicaCmp;
	}
	
	private Moral complementarMoral(Moral moral) {		
		long cveMoral = moral.getCveMoral() == null ? moral.getIdPersona() : moral.getCveMoral();		
		Moral moralCmp = this.personaMoralBusiness.getPersonaMoral(cveMoral);		
		if (moralCmp.getDomicilioFiscal() == null) {
			try {
				DomicilioFiscal domFiscal = this.domicilioServiceBusiness
						.consultarDomicilioFiscalPersona(moralCmp);
				moralCmp.setDomicilioFiscal(domFiscal);
			} catch (DomicilioNoLocalizadoException e) {
				log.warn("Error complementarMoral", e);
			}
		}
		
		if (CollectionUtils.isEmpty(moralCmp.getMediosContactoFiscales())) {
			try {
				List<MedioContacto> mediosFiscales = this.mediosContactoServiceBusiness
						.consultarMediosFiscalesPersona(moralCmp);
				moralCmp.setMediosContactoFiscales(mediosFiscales);
			} catch (PersonaSinMedioDeContactoException e) {
				log.warn("Error complementarMoral", e);
			}
		}		
		return moralCmp;
	}
	
	private CertificadoIDSE obtenerCertificado(Persona personaFisicaMoral) {
		CertificadoIDSE certificado = null;		
		Persona persona = new Persona();
		TipoPersona tipoPersona = new TipoPersona();		
		if (personaFisicaMoral instanceof Fisica) {
			tipoPersona.setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
			persona.setIdPersona(personaFisicaMoral.getIdPersona());
			//persona.setIdPersonaFisica(((Fisica) personaFisicaMoral).getCveFisica());
			System.out.println("Validacion de persona fisica: "+persona.getIdPersona());
			
		} else {
			Moral moral = (Moral)personaFisicaMoral;
			long cveMoral = moral.getCveMoral() == null ? moral.getIdPersona() : moral.getCveMoral();			
			tipoPersona.setIdTipoPersona(TipoPersonaEnum.MORAL.getId());
			persona.setIdPersona(cveMoral);
		}		
		persona.setTipoPersona(tipoPersona);	
		
		Fiel fiel = this.personaBusiness.obtenerDatosFiel(persona);		
		certificado = new CertificadoIDSE();
		certificado.setEstatus(2);
		if (fiel != null) {
			certificado.setClaveSerial(fiel.getClaveSerial());			
		}else{
			certificado.setClaveSerial("00000000000000000000");
		}
		
		return certificado;
	}
	
	
	@Override
	public DatosAltaIDSE obtenerDatosAltaPatronal(String nrp, int tipoPersona) {
		DatosAltaIDSE datosIDSE = new DatosAltaIDSE();
		
		SujetoObligado registroPatronal = new SujetoObligado();
		registroPatronal.setNumeroRegistroPatronal(nrp);
		TipoPersonaFiscal tipoPersonaFiscal = tipoPersona == TipoPersona.TIPO_PERSONA_FISICA.intValue() ? TipoPersonaFiscal.FISICA : TipoPersonaFiscal.MORAL;
		
		registroPatronal.setTipoPersonaFiscal(tipoPersonaFiscal);
		registroPatronal = registroPatronalService.obtenerDetalleSujetoObligadoActividadEconomica(registroPatronal);
		
		Solicitud solicitudAlta = solicitudService.obtenerSolicitudAltaPatronalPorNRP(registroPatronal.getCveIdSujetoObligado());
		
		Fiel fiel = new Fiel();
		fiel.setClaveSerial(solicitudAlta.getCertificado().getClaveSerial());
		fiel.setCurpFiel(registroPatronal.getFisica().getCurp());
		fiel.setFechaValidaFin(solicitudAlta.getCertificado().getFechaValidaFin());
		fiel.setFechaValidaInicio(solicitudAlta.getCertificado().getFechaValidaInicio());
		fiel.setIdRol(1);
		fiel.setEstatusFiel(2);
		fiel.setRfcAsociado(StringUtils.isNotBlank(registroPatronal.getFisica().getRfc()) ? registroPatronal.getFisica().getRfc().toUpperCase() : registroPatronal.getFisica().getRfc());
		
		String actividad = registroPatronal.getClasificacion().getFraccion().getDescripcionDetallada();
		if(actividad.length()>40)
			actividad=actividad.substring(0,40);
		
		Integer clase = registroPatronal.getClasificacion().getFraccion().getClase().getClave().intValue();
		String correo = obtenerCorreoCentroTrabajo(registroPatronal.getCntroTrabajo());
		correo = StringUtils.isNotBlank(correo) ? correo.length() > 40 ? correo.substring(0, 40) : correo : correo;
		
		String curpFiel = StringUtils.isNotBlank(fiel.getCurpFiel()) ? fiel.getCurpFiel().toUpperCase() : fiel.getCurpFiel();
		String cveDelegacion = registroPatronal.getSubdelegacion().getDelegacion().getClave();
		String cveMunicipio = registroPatronal.getMunicipioIMSS().getCvecMunicipioSINDO();
		String cveSubdelegacion = registroPatronal.getSubdelegacion().getClave();
		String domicilioCompleto = obtenerDomicilioCompleto(registroPatronal.getCntroTrabajo());
		
		domicilioCompleto=domicilioCompleto!= null ? domicilioCompleto.replace(" null", "") : " ";
		domicilioCompleto=domicilioCompleto!= null ? domicilioCompleto.replace(" NULL", "") : " ";
		
		domicilioCompleto=domicilioCompleto!= null ? 
				domicilioCompleto.length() > 40 ? domicilioCompleto.substring(0, 40) : domicilioCompleto 
				: "Sin domicilio";
				
		Fraccion fraccion = registroPatronal.getClasificacion().getFraccion();
		Division division = fraccion.getGrupo().getDivision();
		Grupo grupo = fraccion.getGrupo();
		String rfc = StringUtils.isNotBlank(fiel.getRfcAsociado()) ? fiel.getRfcAsociado().toUpperCase() : fiel.getRfcAsociado();
		String fraccionCompleta = division.getNumDivision() + grupo.getNumGrupo() + String.format("%02d", Integer.parseInt(fraccion.getNumFraccion()));
		String localidadSINDO = obtenerLocalidadSINDO(registroPatronal.getCntroTrabajo());
		localidadSINDO = StringUtils.isNotBlank(localidadSINDO) ? localidadSINDO.length() > 40 ? localidadSINDO.substring(0, 40) : localidadSINDO : localidadSINDO;
		
		String nombreCompleto = tipoPersona == TipoPersona.TIPO_PERSONA_FISICA.intValue() ? 
				obtenerNombreCompletoPersonaFisica(registroPatronal.getFisica())
				: registroPatronal.getMoral().getRazonSocial();
		String nrpID = obtenerNRP(registroPatronal);
		String telefono = obtenerTelefonoCentroTrabajo(registroPatronal.getCntroTrabajo());
		telefono = telefono.replace("|", "-");
		String claveSerial = fiel.getClaveSerial();
		Integer estatusFiel = 2;
		String nombreUsuario=rfc;
		Date fechaValidaInicio = fiel.getFechaValidaInicio();
		Date fechaValidaFin = fiel.getFechaValidaFin();
		Integer idRol=1;
		String digVer = registroPatronal.getDigVerificador();
		String razonSocial=nombreCompleto;
		String cveSector="90";
		String representanteLegal = nombreCompleto;
		Date fechaRecepcion = Calendar.getInstance().getTime();
		Date fechaActivacion = fechaRecepcion;
		
		datosIDSE.setRfc(rfc);
		datosIDSE.setActividad(actividad);
		datosIDSE.setClase(clase);
		datosIDSE.setCorreo(correo);
		datosIDSE.setCurpFiel(curpFiel);
		datosIDSE.setCveDelegacion(cveDelegacion);
		datosIDSE.setCveMunicipio(cveMunicipio);
		datosIDSE.setCveSector(cveSector);
		datosIDSE.setCveSerial(claveSerial);
		datosIDSE.setCveSubdelegacion(cveSubdelegacion);
		datosIDSE.setDigitoVerificador(digVer);
		datosIDSE.setDomicilio(domicilioCompleto);
		datosIDSE.setFechaFinVigencia(fechaValidaFin);
		datosIDSE.setFechaInicioVigencia(fechaValidaInicio);
		datosIDSE.setFraccion(fraccionCompleta);
		datosIDSE.setIdRol(idRol);
		datosIDSE.setLocalidad(localidadSINDO);
		datosIDSE.setNombreCompleto(nombreCompleto);
		datosIDSE.setNombreUsuario(nombreUsuario);
		datosIDSE.setNrp(nrpID);
		datosIDSE.setStatusFiel(estatusFiel);
		datosIDSE.setTelefono(telefono);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:MM:ss");
		StringBuffer msgIdse = new StringBuffer();
		msgIdse.append(claveSerial).append("|");
		msgIdse.append(nombreCompleto).append("|");
		msgIdse.append(estatusFiel).append("|");
		msgIdse.append(correo).append("|");
		msgIdse.append(curpFiel).append("|");
		msgIdse.append(nombreUsuario).append("|");
		msgIdse.append(rfc).append("|");
		msgIdse.append(sdf.format(fechaValidaInicio)).append("|");
		msgIdse.append(sdf.format(fechaValidaFin)).append("|");
		msgIdse.append(telefono).append("|");
		msgIdse.append(idRol).append("|");
		msgIdse.append(cveDelegacion).append("|");
		msgIdse.append(cveSubdelegacion).append("|");
		msgIdse.append(nrpID).append("|");
		msgIdse.append(digVer).append("|");
		msgIdse.append(razonSocial).append("|");
		msgIdse.append(cveMunicipio).append("|");
		msgIdse.append(cveSector).append("|");
		msgIdse.append(rfc).append("|");
		msgIdse.append(domicilioCompleto.trim()).append("|");
		msgIdse.append(localidadSINDO).append("|");
		msgIdse.append(actividad).append("|");
		msgIdse.append(fraccionCompleta).append("|");
		msgIdse.append(clase).append("|");
		msgIdse.append(correo).append("|");
		msgIdse.append(representanteLegal).append("|");
		msgIdse.append(sdf.format(fechaRecepcion)).append("|");
		msgIdse.append(sdf.format(fechaActivacion)).append("|");
		msgIdse.append(tipoPersona);
		
		writeResult("C:\\IMSS\\sincronizaIDSE.txt", msgIdse.toString());		

		return datosIDSE;
	}
	
	
	private String obtenerNombreCompletoPersonaFisica(Fisica persona){
		StringBuffer nombre = new StringBuffer(); 
		
		if(StringUtils.isNotEmpty(persona.getNombre()) )
			nombre.append(persona.getNombre());
		if(StringUtils.isNotEmpty(persona.getPrimerApellido()) )
			nombre.append(" "+persona.getPrimerApellido());
		if(StringUtils.isNotEmpty(persona.getSegundoApellido()) )
			nombre.append(" "+persona.getSegundoApellido());
		
		return nombre.toString();
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
	
	
	private String obtenerNRP(SujetoObligado registroPatronal){
		StringBuffer nrp = new StringBuffer();
		if(registroPatronal.getNumeroRegistroPatronal().length()==10){
			nrp.append(registroPatronal.getNumeroRegistroPatronal());
		}else if(registroPatronal.getNumeroRegistroPatronal().length()==8){
			nrp.append(registroPatronal.getNumeroRegistroPatronal());
			nrp.append(registroPatronal.getModalidad().getNumModalidad());
		}else{// mayor a 10
			nrp.append(registroPatronal.getNumeroRegistroPatronal().substring(0, 10));
		}
		return nrp.toString();
	}
}
