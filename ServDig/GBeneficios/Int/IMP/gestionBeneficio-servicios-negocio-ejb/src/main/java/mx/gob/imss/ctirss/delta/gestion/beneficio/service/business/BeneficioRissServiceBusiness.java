package mx.gob.imss.ctirss.delta.gestion.beneficio.service.business;

import gob.imss.webservice.imss.riss.implementacion.ClienteWebserviceValidarRiss;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.beneficio.BeneficioRissException;
import mx.gob.imss.ctirss.delta.exception.beneficio.PersonaNoValidaBeneficioRissException;
import mx.gob.imss.ctirss.delta.exception.beneficio.RifException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaConVariosNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaSinNSSException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.framework.base.model.EmailPayloadType;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceImssRissException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.framework.util.AttributeComparator;
import mx.gob.imss.ctirss.delta.framework.util.DateUtils;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.entity.BeneficioServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.BeneficioRissServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.utility.BeneficioServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.utility.BeneficioWSUtil;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.CalificacionesPersonaBusinessServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ConsultaSeguroIvroServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration.EMailProducer;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.EncolarMovimientoRissBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ParametrosServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.beneficio.Beneficio;
import mx.gob.imss.ctirss.delta.model.beneficio.DescuentoBeneficio;
import mx.gob.imss.ctirss.delta.model.beneficio.RespuestaRifSat;
import mx.gob.imss.ctirss.delta.model.beneficio.TipoBeneficio;
import mx.gob.imss.ctirss.delta.model.enums.ApartadoPersonaBeneficioEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParametroSistemaEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoBeneficioEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoContactoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.CalificacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRiss;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitBeneficio;
import mx.gob.imss.ctirss.delta.persistence.DitPatSujObligBeneficio;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.satRiss.DatosRiss;
import mx.gob.imss.ctirss.delta.model.enums.EstadoBeneficioEnum;

import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.util.CollectionUtils;

@Stateless(name = "beneficioRissServiceBusiness", mappedName = "beneficioRissServiceBusiness")
public class BeneficioRissServiceBusiness extends AbstractServiceBusiness
		implements BeneficioRissServiceBusinessRemote {
	
	private static final String SUBJECT = "Notificaci\u00F3n de Beneficio";
	private static final String CONTENT_TYPE = "text/html";
	private static final String PREFIJO_VALIDACION_RIF = "_VALIDA_RIF ";

	@EJB
    private SolicitudBusinessRemote solicitudBusinessRemote;
	@EJB
	private BeneficioServiceEntityLocal beneficioServiceEntity;
	@EJB
	private BeneficioServiceUtilityLocal beneficioServiceUtility;
	@EJB
	private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;
	@EJB
	private PersonaBusinessRemote personaBusiness;
	@EJB
	private ServiciosPersonaBusinessRemote serviciosPersonaBusiness;
	@EJB
	private MediosContactoServiceBusinessRemote contactoServiceBusinessRemote;
	@EJB
	private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
	@EJB
	private EncolarMovimientoRissBusinessRemote encolarMovimientoRissBusiness;
	@EJB
	private ServiceBusinessRemote serviceBusiness;
    @EJB
    private ParametrosServiceBusinessRemote parametrosServiceBusinessRemote;
	@EJB
	private CalificacionesPersonaBusinessServiceRemote calificacionesPersonaBusinessService;

	@EJB
	private ConsultaSeguroIvroServiceRemote consultaSeguroIvroServiceRemote;

    
    @EJB(mappedName = "EMailQProducer")
	private EMailProducer eMailProducer;
    
    @Override
	public Beneficio obtenerBeneficioPorNRP(String nrp, Date fechaInicio, Date fechaFin) 
			throws BeneficioRissException {
		log.debug("NRP["+nrp+"], fechaInicio["+fechaInicio+"], fechaFin["+fechaFin+"] ");
		beneficioServiceUtility.validarFiltrosBeneficios(nrp, fechaInicio, fechaFin);
		List<Beneficio> listaBeneficios =  obtenerBeneficiosPorNRP(nrp, false);
		Beneficio beneficio =  beneficioServiceUtility.validarBeneficioPorPeriodo(listaBeneficios, fechaInicio, fechaFin);
		beneficio = obtenerInformacionDetalleTramite(beneficio, true);
		return beneficio;
	}
	
    @Override
	public Beneficio obtenerBeneficioPorNSS(String nss, Date fechaInicio, Date fechaFin) 
			throws BeneficioRissException {
		log.debug("NSS["+nss+"], fechaInicio["+fechaInicio+"], fechaFin["+fechaFin+"] ");
		Beneficio beneficio = new Beneficio();
		beneficioServiceUtility.validarFiltrosBeneficios(nss, fechaInicio, fechaFin);
		try {
			Fisica fisica = personaFisicaServiceBusiness.localizarPersonaFisicaPorNss(nss);	
			List<Beneficio> listaBeneficios = obtenerBeneficiosPorIdPersona(fisica.getIdPersona(), false);
			beneficio =  beneficioServiceUtility
				.validarBeneficioPorPeriodo(listaBeneficios, fechaInicio, fechaFin);
			if(beneficio.getListaDescuentosBeneficio()!=null&&!beneficio.getListaDescuentosBeneficio().isEmpty()){
				DescuentoBeneficio descuentoBeneficio = beneficio.getListaDescuentosBeneficio().get(0);
				log.debug("Descuento actual: "+beneficio.getDescuentoActual().getPorcentajeDescuento());
				log.debug("Se cambia por: "+descuentoBeneficio.getPorcentajeDescuento());
			    beneficio.getDescuentoActual().setIdDescuentoBeneficio(descuentoBeneficio.getIdDescuentoBeneficio());
			    beneficio.getDescuentoActual().setAnioFiscal(descuentoBeneficio.getAnioFiscal());
			    beneficio.getDescuentoActual().setPorcentajeDescuento(descuentoBeneficio.getPorcentajeDescuento());
			    beneficio.getDescuentoActual().setFechaFin(descuentoBeneficio.getFechaFin());
			    beneficio.getDescuentoActual().setFecInicio(descuentoBeneficio.getFecInicio());
			}
			beneficio.setFisica(fisica);
			beneficio = obtenerInformacionDetalleTramite(beneficio, false);
        } catch (AbstractException e) {
            log.error(e);			
            beneficioServiceUtility.lanzarErrorBeneficioRiss(e.getMessage());			
        }
		return beneficio;
	}
	
    @Override
	public List<Beneficio> obtenerBeneficiosPorNRP(String nrp, boolean soloBeneficiosActivos) 
			throws BeneficioRissException{
		List<Beneficio>  listaBeneficios = new ArrayList<Beneficio>();
		if (nrp != null && nrp.length() >= 10){
			SujetoObligado sujetoObligado = new SujetoObligado();
			sujetoObligado.setNumeroRegistroPatronal(nrp);
			sujetoObligado.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
			sujetoObligado = sujetoObligadoServiceBusiness
				.obtenerDetalleSujetoObligadoActividadEconomica(sujetoObligado);
			if(sujetoObligado != null && sujetoObligado.getCveIdSujetoObligado() != null){
				listaBeneficios = beneficioServiceEntity.obtenerBeneficiosSujetoObligado(sujetoObligado, 
					(soloBeneficiosActivos ? beneficioServiceUtility.getBeneficioEstadoActivo() : null));				
			}
		}else{
			//beneficioServiceUtility.lanzarErrorBeneficioRiss("beneficioRiss.so.errorFormatoNrp");
			beneficioServiceUtility.lanzarErrorBeneficioRiss("El formato del NRP no es valido.");
		}
		return listaBeneficios;
	}
	
    @Override
	public List<Beneficio> obtenerBeneficiosPorIdPersona(Long idPersona, 
			boolean soloBeneficiosActivos){
		List<Beneficio>  listaBeneficios = new ArrayList<Beneficio>();
		Fisica fisica = new Fisica();
		fisica.setTipoPersona(new TipoPersona());
		fisica.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
		fisica.setIdPersona(idPersona);		
		listaBeneficios = beneficioServiceEntity.obtenerBeneficiosPersona(fisica, 
			(soloBeneficiosActivos ? beneficioServiceUtility.getBeneficioEstadoActivo() : null));
		return listaBeneficios;
	}
	
    @Override
	public Solicitud crearSolicitudRiss(Beneficio beneficio, Usuario usuario, 
			Long idOrigenSolicitud) throws SolicitudNoValidaException {		
		Solicitud solicitud = this.solicitudBusinessRemote.crear(beneficioServiceUtility
			.crearSolicitudRiss(beneficio, usuario, idOrigenSolicitud));		
		return solicitud;
	}
		
    @Override
	public Solicitud encolarSolicitudRiss(Long idSolicitud, FirmaElectronica firma)
			throws SolicitudNoEncontradaException, TramiteNoEncontradoException, SolicitudException {		
		Solicitud solicitud = obtenerSolicitudPorId(idSolicitud);
		this.solicitudBusinessRemote.enviarSolicitudAProceso(solicitud, firma);
		return solicitud;
	}
	
	@Override
	public Solicitud cancelarRechazarSolicitudRiss(Long idSolicitud,
			String causaCancelacion, boolean isRechazo)
			throws SolicitudNoEncontradaException, TramiteNoEncontradoException {
		
		EstadoSolicitudEnum edoSolicitud = null;		
		if (isRechazo) {
			edoSolicitud = EstadoSolicitudEnum.RECHAZADA;
		} else {
			edoSolicitud = EstadoSolicitudEnum.CANCELADA;
		}		
		Solicitud solicitud = new Solicitud();
		EstadoSolicitud estadoSolicitud = new EstadoSolicitud();		
		solicitud.setSolicitudId(idSolicitud);
		estadoSolicitud.setIdEstadoSolicitud(edoSolicitud.getCodigo());
		solicitud.setEstadoSolicitud(estadoSolicitud);
		solicitud.setObservacion(causaCancelacion);		
		solicitud = this.solicitudBusinessRemote.actualizarEstados(solicitud);		
		return solicitud;
	}
	
	@Override
	public Solicitud cancelarRechazarSolicitudRiss(Solicitud solicitud,
			String causaCancelacion, boolean isRechazo)
			throws SolicitudNoEncontradaException, TramiteNoEncontradoException {
		
		RespuestaRifSat respuestaRifSat=null;
		boolean respuestaInfonavit=false;		
		for(Tramite tramite : solicitud.getTramites()){
			if(esTramiteActivo(((TramiteRiss)tramite))){
				respuestaRifSat=((TramiteRiss)tramite).getRespuestaRifSat();
				respuestaInfonavit=((TramiteRiss)tramite).isRespuestaInfonavit();
				break;
			}
		}		
		actualizaTramiteRiss(solicitud, respuestaRifSat, respuestaInfonavit, null, causaCancelacion, false);
		
		return cancelarRechazarSolicitudRiss(solicitud.getSolicitudId(), causaCancelacion, isRechazo);		
	}
	
	@Override
	public void procesarSolicitudRiss(Long idSolicitud)
			throws BeneficioRissException {
		try {			
			Solicitud solicitud = new Solicitud();
			solicitud.setSolicitudId(idSolicitud);		
			solicitud = this.solicitudBusinessRemote.consultar(solicitud);			
			log.warn("PROCESANDO SOLICITUD RISS " + solicitud.getNoFolioSolicitud() + 
				" CON ESTADO " + solicitud.getEstadoSolicitud().getDescripcion());			
			if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(
					EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo())){
				Date fechaActual = new Date();
				TramiteRiss tramiteSO = beneficioServiceUtility.obtenerTramitePatron(solicitud);
				TramiteRiss tramitePF = beneficioServiceUtility.obtenerTramitePersonaFisica(solicitud);
				RespuestaRifSat respuestaRifSat=null;
				if(tramiteSO!=null && esTramiteActivo(tramiteSO)){
					respuestaRifSat = tramiteSO.getRespuestaRifSat();				
					
					Beneficio beneficioSO = new Beneficio();
					beneficioSO.setListaSujetosObligados(new ArrayList<SujetoObligado>());
					for(Long clave : tramiteSO.getListaCveIdSujetosObligados()){
						SujetoObligado so = new SujetoObligado();
						so.setCveIdSujetoObligado(clave);
						beneficioSO.getListaSujetosObligados().add(so);
					}
					if(tramiteSO.getIdBeneficioPatronExistente()!=null){
						//Asociar RPs a Beneficio Patron Existente (NO generar nuevo beneficio patron)
						List<DitPatSujObligBeneficio> listaDitPatSujObligBeneficio = beneficioServiceEntity
							.obtenerBeneficioPatronxIdBeneficio(tramiteSO.getIdBeneficioPatronExistente().longValue());
						DitPatSujObligBeneficio  ditPatSujObligBeneficioExistente = listaDitPatSujObligBeneficio.get(0);
						for(SujetoObligado so : beneficioSO.getListaSujetosObligados()){
							beneficioPatronHeredado(ditPatSujObligBeneficioExistente.getDitBeneficio(), so);
						}						
					}else{
						//Generar beneficio patron para los SOs.
						guardarBeneficio(beneficioSO, fechaActual, ((respuestaRifSat!=null 
							&& respuestaRifSat.getFechaAltaRif()!=null) ? respuestaRifSat.getFechaAltaRif() : new Date()));						
					}
				}
				if(tramitePF!=null && esTramiteActivo(tramitePF)){				
					if (respuestaRifSat == null) {
						respuestaRifSat = tramitePF.getRespuestaRifSat();
					}
					
					Beneficio beneficioPF = new Beneficio();
					beneficioPF.setFisica(tramitePF.getFisica());
					guardarBeneficio(beneficioPF, fechaActual, ((respuestaRifSat!=null 
						&& respuestaRifSat.getFechaAltaRif()!=null) ? respuestaRifSat.getFechaAltaRif() : new Date()));
				}						
				solicitudBusinessRemote.actualizarSolicitudAEstatusConcluida(solicitud.getSolicitudId());
				prepararLayoutMovimientoRiss(tramiteSO, tramitePF, fechaActual, respuestaRifSat);
			}
		} catch (TramiteNoEncontradoException e) {
			log.error(e);
			e.printStackTrace();
			beneficioServiceUtility.lanzarErrorBeneficioRiss(e.getMessage());
		} catch (SolicitudNoEncontradaException e) {
			log.error(e);
			e.printStackTrace();
			beneficioServiceUtility.lanzarErrorBeneficioRiss(e.getMessage());
		}
	}
	
	@Override
	public Beneficio guardarBeneficio(Beneficio beneficio, Date fechaActual, Date fechaSatRif) 
			throws BeneficioRissException {		
		return beneficioServiceEntity.guardarBeneficio(beneficio, fechaActual, 
			fechaSatRif, getParametroFechaInicioRifImss());
	}
		
	@Override
	public List<Beneficio> obtenerBeneficiosPatronesPorIds(List<SujetoObligado> listaSujetosObligados){
		List<Beneficio> beneficiosExistentesPatron = beneficioServiceEntity.
		obtenerBeneficiosPorIdsSujetosObligados(beneficioServiceUtility.
			getIdsSujetosObligados(listaSujetosObligados), null);
		return beneficiosExistentesPatron;
	}
	
	@Override
	public Beneficio obtenerPersonaBeneficio(Fisica fisica, Long origenSolicitud,
			boolean beneficioSoloPatron) throws PersonaNoValidaBeneficioRissException {

	    log.info("beneficioSoloPatron 1:"+beneficioSoloPatron);
		String mensajeErrorNss=null;			
		Beneficio beneficio = new Beneficio();
		beneficio.setEsPatron(false);
		fisica.setTipoPersona(new TipoPersona());
		fisica.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
		if (!beneficioSoloPatron) {
			fisica = complementarDatosPF(fisica);
		}
		
		//Obtener los RPs asociados
		if(StringUtils.isNotEmpty(fisica.getRfc()) && StringUtils.isNotBlank(fisica.getRfc())){
			beneficio.setListaSujetosObligados(obtenerSujetosObligadosParaBeneficio(fisica.getRfc()));
			//Filtrar RPs por IdPersona para INTERNET
			if(origenSolicitud.equals(OrigenSolicitudEnum.INTERNET.getId())){
				beneficio.setListaSujetosObligados(obtenerSujetosObligadosPersona(beneficio.getListaSujetosObligados(), fisica));
			}		
		}
		beneficio.setFisica(fisica);
		
		if(beneficioSoloPatron){
            log.info("beneficioSoloPatron es true");
			if(!CollectionUtils.isEmpty(beneficio.getListaSujetosObligados())){
                log.info("se marca como patron 1");
				beneficio.setEsPatron(true);
//				beneficio.getFisica().setIdPersona(null);
//				beneficio.getFisica().setNss(null);
			}else{
				beneficioServiceUtility.lanzarErrorPersonaNoValidaBeneficioRissException(
					"No cuentas con Registros Patronales para tener derecho al beneficio.");				
			}
		}else{
            log.info("beneficioSoloPatron es false");
			//Determinar si tiene asignado NSS
			try {				
				obtenerPersonaFisicaNSS(fisica);
			} catch (AbstractException e) {
				mensajeErrorNss=e.getMessage();
			}
			if(!CollectionUtils.isEmpty(beneficio.getListaSujetosObligados())){
                log.info("se marca como patron 2");
				beneficio.setEsPatron(true);
//				if(mensajeErrorNss!=null){
//					beneficio.getFisica().setIdPersona(null);
//					beneficio.getFisica().setNss(null);
//				}
			}else{
				//No tiene registros patronales y existe mensaje de error de NSS
				if(mensajeErrorNss!=null){
					beneficioServiceUtility.lanzarErrorPersonaNoValidaBeneficioRissException(
						"No cuentas con NSS y/o Registros Patronales para tener derecho al beneficio.");
				}
			}			
		}
		verificarNivelBeneficioOrtorgar(beneficio);		
		return beneficio;
	}
	
	@Override
	public Beneficio obtenerPersonaBeneficioVentanilla(Fisica fisica,
			List<SujetoObligado> sujetosObligados) 
			throws PersonaNoValidaBeneficioRissException {		
		Beneficio beneficio = new Beneficio();
		beneficio.setEsPatron(false);
		if (fisica != null && CollectionUtils.isEmpty(sujetosObligados)) {
			beneficio.setFisica(fisica);
		} else {
			beneficio.setFisica(fisica);
			beneficio.setListaSujetosObligados(sujetosObligados);
			beneficio.setEsPatron(true);
		}		
		verificarNivelBeneficioOrtorgar(beneficio);
		return beneficio;
	}
	
	@Override
	public Fisica obtenerPersonaFisicaParaBeneficio(String nss, Long idPersona) 
		throws PersonaNoValidaBeneficioRissException {
		Fisica fisica = new Fisica();
		fisica.setTipoPersona(new TipoPersona());
		fisica.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
		try {
			fisica = obtenerPersonaFisicaNSS(fisica);
		} catch (AbstractException e) {
			log.error(e);
			e.printStackTrace();
			beneficioServiceUtility.lanzarErrorPersonaNoValidaBeneficioRissException(e.getMessage());
		}
		fisica.setIdPersona(idPersona);		
		return fisica;
	}
	
	@Override
	public List<SujetoObligado> obtenerSujetosObligadosParaBeneficio(String rfc) 
			throws PersonaNoValidaBeneficioRissException {
		List<SujetoObligado> listaSujetosObligados = new ArrayList<SujetoObligado>();		
		try {
			Fisica fisica = new Fisica();
			fisica.setTipoPersona(new TipoPersona());
			fisica.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
			fisica.setRfc(rfc);			
			SujetoObligado so = new SujetoObligado();
			so.setFisica(fisica);	
			so.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
			so.setIdsModalidadesConsulta(beneficioServiceUtility.getIdsModalidades10y13());
			listaSujetosObligados = sujetoObligadoServiceBusiness.obtenerDetalleSujetoObligado(so);
		} catch (AbstractException e) {
			log.error(e);
			e.printStackTrace();
			beneficioServiceUtility.lanzarErrorPersonaNoValidaBeneficioRissException(e.getMessage());
		}
		return listaSujetosObligados; 
	}

    @Override
    public Beneficio validarSolicitudRiss(Solicitud solicitud, Beneficio beneficio)
            throws BeneficioRissException, ClienteWebserviceImssRissException {
        return this.validarSolicitudRiss(solicitud,beneficio,false);
    }

	@Override
	public Beneficio validarSolicitudRiss(Solicitud solicitud, Beneficio beneficio, boolean esRenovacion)
			throws BeneficioRissException, ClienteWebserviceImssRissException {
		
		beneficio.setTipoBeneficio(new TipoBeneficio());
		beneficio.getTipoBeneficio().setIdTipoBeneficio(TipoBeneficioEnum.RIF.getClave());
		beneficio.getTipoBeneficio().setDescripcion(TipoBeneficioEnum.RIF.getDesc());
		
		//Valida si ya cuenta con beneficio riss
		validarBeneficioExistente(beneficio);

		Fisica fisica = beneficio.getFisica();
		if (fisica != null && fisica.getIdPersona() != null
				&& fisica.getIdPersona() != 0L) {
			try {
				fisica.setNss(personaBusiness.obtenerNssVigentePersona(fisica.getIdPersona()));
			} catch (PersonaConVariosNSSException e) {
				throw new BeneficioRissException("La persona cuenta con m\u00E1s de un NSS");
			} catch (PersonaSinNSSException e) {
				fisica.setNss(null);
			}
		}

		if (fisica != null && StringUtils.isBlank(beneficio.getRfc()) 
					&& StringUtils.isNotBlank(fisica.getRfc())) {
			beneficio.setRfc(fisica.getRfc());
		}

        if (fisica != null && StringUtils.isBlank(beneficio.getCurp())
                && StringUtils.isNotBlank(fisica.getCurp())) {
            beneficio.setCurp(fisica.getCurp());
        }

		if (!CollectionUtils.isEmpty(beneficio.getListaSujetosObligados())) {
			List<String> nrps = new ArrayList<String>(beneficio.getListaSujetosObligados().size());
			StringBuffer nrp = new StringBuffer();
			for (SujetoObligado so : beneficio.getListaSujetosObligados()) {
				nrp.append(so.getNumeroRegistroPatronal());
				nrp.append(so.getModalidad().getNumModalidad());
				nrp.append(so.getDigVerificador());
				
				nrps.add(nrp.toString());
				nrp.delete(0, nrp.length());
			}			
			beneficio.setListaNRPsMod10y13(nrps);
		}		
		boolean obtenerCveAsignacion = false;		
		if (fisica != null
				&& StringUtils.isNotBlank(fisica.getNss())
				&& CollectionUtils.isEmpty(beneficio.getListaSujetosObligados())) {
			beneficio.setTipoApartado(ApartadoPersonaBeneficioEnum.APARTADO_A.getClave());
			obtenerCveAsignacion = true;
		} else if ((fisica == null || StringUtils.isBlank(fisica.getNss()))
				&& !CollectionUtils.isEmpty(beneficio.getListaSujetosObligados())) {
			beneficio.setTipoApartado(ApartadoPersonaBeneficioEnum.APARTADO_B.getClave());
		} else {
			beneficio.setTipoApartado(ApartadoPersonaBeneficioEnum.APARTADO_AB.getClave());
			obtenerCveAsignacion = true;
		}
		
		if (obtenerCveAsignacion) {
			AsignacionNSS asignacionNSS = serviceBusiness.obtenerAsignacionNss(fisica.getNss());
			beneficio.setCveAsignacion(asignacionNSS.getIdAsignacionNSS());
		}

		ClienteWebserviceValidarRiss validarRissWS = new ClienteWebserviceValidarRiss();
		RespuestaRifSat respuestaRifSat = null;
            respuestaRifSat = validarRissWS.validarRiss(beneficio,esRenovacion);
		this.log.debug("RESPUESTA WS -> " + respuestaRifSat);

		if (respuestaRifSat.getExito() == 0) {
			if (respuestaRifSat.getIndicadorDerecho()) {
				beneficioServiceUtility.validarVigenciaBeneficioRiss(respuestaRifSat.getFechaAltaRif(),
					getParametroFechaInicioRifImss());
				//Si se procesa un 35 (Independiente y Patron). Validar si NO cambio la respuesta
				//para otorgar beneficio a nivel trabajador independiente y patron. Si cambio
				//solo se puede dar el caso de otorgar beneficio a nivel patronal, esto por que
				//como trabajador incumple, pero debe otorgarse a nivel patronal. Si fallan las validaciones
				//como patron, NO se procesan beneficios y la solicitud es cancelada (la respuesta no es exitosa).
				boolean cancelarTramitePF=false;
				if(beneficio.getTipoApartado().equals(ApartadoPersonaBeneficioEnum.APARTADO_AB.getClave())
					&& respuestaRifSat.getTipoApartadoBeneficio().equals(ApartadoPersonaBeneficioEnum.APARTADO_B.getClave()) ){
					cancelarTramitePF=true;
					beneficio.getFisica().setIdPersona(null);
				}

				Date fechaAltaRifSat = respuestaRifSat.getFechaAltaRif();
				Date fechaAltaRifImss = getParametroFechaInicioRifImss();

				if (fechaAltaRifSat.before(fechaAltaRifImss)) {
					obtenerRangoDescuentosRiss(beneficio, fechaAltaRifImss);
				} else {
					obtenerRangoDescuentosRiss(beneficio, fechaAltaRifSat);
				}
				log.info("Datos correctos, se continua el proceso RISS");

				beneficio.setRespuestaRifSat(respuestaRifSat);
				actualizaTramiteRiss(solicitud, respuestaRifSat, true,
					beneficio.getListaDescuentosBeneficio(), null, cancelarTramitePF);
			} else {
				log.error("Ya no es candidato RISS");
				throw new BeneficioRissException(respuestaRifSat.getMotivoDeRechazo());
			}
		} else {
		    log.error("No se obtuvo respuesta del WS");
			throw new BeneficioRissException(respuestaRifSat.getDescripcion(),
                    respuestaRifSat.getClaveError());
		}			
		return beneficio;
	}

	public RespuestaRifSat validaEstadoBeneficio(Fisica persona) {

        RespuestaRifSat respuesta = new RespuestaRifSat();
        Beneficio beneficio;
        String mensajeError = null;
        Integer codigoError = null;
        try {

            beneficio = obtenerPersonaBeneficio(persona,6L,false);

            try{
                beneficio.setTipoBeneficio(new TipoBeneficio());
                beneficio.getTipoBeneficio().setIdTipoBeneficio(TipoBeneficioEnum.RIF.getClave());
                beneficio.getTipoBeneficio().setDescripcion(TipoBeneficioEnum.RIF.getDesc());

                //Valida si ya cuenta con beneficio riss
                validarBeneficioExistente(beneficio);
            } catch (BeneficioRissException e) {
               String mensaje = e.getMessage();
               if (mensaje.contains("Ya cuentas")){

                   if (persona != null && persona.getIdPersona() != null
                           && persona.getIdPersona() != 0L && StringUtils.isBlank(persona.getNss())) {
                       try {
                           persona.setNss(personaBusiness.obtenerNssVigentePersona(persona.getIdPersona()));
                       } catch (PersonaConVariosNSSException ex) {
                           throw new BeneficioRissException("La persona cuenta con m\u00E1s de un NSS");
                       } catch (PersonaSinNSSException ex) {
                           log.error("Persona sin NSS");
                           persona.setNss(null);
                       }
                   }
                    //Se completa el objeto beneficio para consulta del WS

                   if (persona != null && StringUtils.isBlank(beneficio.getRfc())
                           && StringUtils.isNotBlank(persona.getRfc())) {
                       beneficio.setRfc(persona.getRfc());
                   }


                   if (persona != null && StringUtils.isBlank(beneficio.getCurp())
                           && StringUtils.isNotBlank(persona.getCurp())) {
                       beneficio.setCurp(persona.getCurp());
                   }

                   if (!CollectionUtils.isEmpty(beneficio.getListaSujetosObligados())) {
                       List<String> nrps = new ArrayList<String>(beneficio.getListaSujetosObligados().size());
                       StringBuffer nrp = new StringBuffer();
                       for (SujetoObligado so : beneficio.getListaSujetosObligados()) {
                           nrp.append(so.getNumeroRegistroPatronal());
                           nrp.append(so.getModalidad().getNumModalidad());
                           nrp.append(so.getDigVerificador());

                           nrps.add(nrp.toString());
                           nrp.delete(0, nrp.length());
                       }
                       beneficio.setListaNRPsMod10y13(nrps);
                   }

                   if (persona != null
                           && StringUtils.isNotBlank(persona.getNss())
                           && CollectionUtils.isEmpty(beneficio.getListaSujetosObligados())) {
                       beneficio.setTipoApartado(ApartadoPersonaBeneficioEnum.APARTADO_A.getClave());
                   } else if ((persona == null || StringUtils.isBlank(persona.getNss()))
                           && !CollectionUtils.isEmpty(beneficio.getListaSujetosObligados())) {
                       beneficio.setTipoApartado(ApartadoPersonaBeneficioEnum.APARTADO_B.getClave());
                   } else {
                       beneficio.setTipoApartado(ApartadoPersonaBeneficioEnum.APARTADO_AB.getClave());
                   }

                    if(persona.getCveIdAsignacionNSS()!=null && !persona.getCveIdAsignacionNSS().equals(0L)) {
                        beneficio.setCveAsignacion(persona.getCveIdAsignacionNSS());
                    }else{
                        AsignacionNSS asignacionNSS = serviceBusiness.obtenerAsignacionNss(persona.getNss());
                        beneficio.setCveAsignacion(asignacionNSS.getIdAsignacionNSS());
                    }
                   log.info("Request -> " + beneficio);

                   ClienteWebserviceValidarRiss validarRissWS = new ClienteWebserviceValidarRiss();
                   RespuestaRifSat respuestaRifSat = null;
                       respuestaRifSat = validarRissWS.validarRiss(beneficio);
                   this.log.debug("RESPUESTA WS -> " + respuestaRifSat);

                   if (respuestaRifSat.getExito() == 0) {
                       if (respuestaRifSat.getIndicadorDerecho()) {
                           beneficioServiceUtility.validarVigenciaBeneficioRiss(respuestaRifSat.getFechaAltaRif(),
                                   getParametroFechaInicioRifImss());
                           respuesta.setCurp(persona.getCurp());
                           respuesta.setDescripcion("SI tiene beneficio y lo mantiene");
                           respuesta.setRfcVigente(persona.getRfc());
                           respuesta.setExito(1);
                           respuesta.setClaveError(0);
                           return respuesta;
                       } else {
                               mensajeError = respuestaRifSat.getMotivoDeRechazo();
                               codigoError = 1;
                               throw new BeneficioRissException(respuestaRifSat.getMotivoDeRechazo());
                       }
                   } else {
                       mensajeError = respuestaRifSat.getDescripcion();
                       codigoError = respuestaRifSat.getClaveError();
                       throw new BeneficioRissException(respuestaRifSat.getDescripcion(),
                               respuestaRifSat.getClaveError());
                   }
               }
            }

            mensajeError = "No cuenta con Beneficio en BDTU";
            codigoError = 0;

        } catch (PersonaNoValidaBeneficioRissException e) {
           log.error("Error: ",e);
            mensajeError = e.getMessage();
            codigoError = -1;
        } catch (ClienteWebserviceImssRissException e) {
            log.error("Error: ",e);
            mensajeError = e.getMessage();

            String validaMensaje = mensajeError.toUpperCase();
            if(validaMensaje.contains("ERROR DE COMUNICA")){
                codigoError = -1;
            } else if(validaMensaje.contains("SAT")||validaMensaje.contains("INFONAVIT")){
                codigoError = 1;
            } else{
                codigoError = -1;
            }

        } catch (BeneficioRissException e) {
            log.error("Error: "+e.getMessage());
            if(mensajeError==null&&codigoError==null){
                mensajeError = e.getMessage();
                codigoError = -1;
            }
        }

        respuesta.setCurp(persona.getCurp());
        respuesta.setDescripcion(mensajeError);
        respuesta.setMotivoDeRechazo(mensajeError);
        respuesta.setRfcVigente(persona.getRfc());
        respuesta.setExito(0);
        respuesta.setClaveError(codigoError);

        return respuesta;
    }

	public RespuestaRifSat validaEstadoBeneficio(Fisica persona,boolean esRenovacion) {

		RespuestaRifSat respuesta = new RespuestaRifSat();
		Beneficio beneficio;
		String mensajeError = null;
		Integer codigoError = null;
		try {

			beneficio = obtenerPersonaBeneficio(persona,6L,false);

			try{
				beneficio.setTipoBeneficio(new TipoBeneficio());
				beneficio.getTipoBeneficio().setIdTipoBeneficio(TipoBeneficioEnum.RIF.getClave());
				beneficio.getTipoBeneficio().setDescripcion(TipoBeneficioEnum.RIF.getDesc());

				//Valida si ya cuenta con beneficio riss
				validarBeneficioExistente(beneficio);
			} catch (BeneficioRissException e) {
				String mensaje = e.getMessage();
				if (mensaje.contains("Ya cuentas")){

					if (persona != null && persona.getIdPersona() != null
							&& persona.getIdPersona() != 0L && StringUtils.isBlank(persona.getNss())) {
						try {
							persona.setNss(personaBusiness.obtenerNssVigentePersona(persona.getIdPersona()));
						} catch (PersonaConVariosNSSException ex) {
							throw new BeneficioRissException("La persona cuenta con m\u00E1s de un NSS");
						} catch (PersonaSinNSSException ex) {
							log.error("Persona sin NSS");
							persona.setNss(null);
						}
					}
					//Se completa el objeto beneficio para consulta del WS

					if (persona != null && StringUtils.isBlank(beneficio.getRfc())
							&& StringUtils.isNotBlank(persona.getRfc())) {
						beneficio.setRfc(persona.getRfc());
					}


					if (persona != null && StringUtils.isBlank(beneficio.getCurp())
							&& StringUtils.isNotBlank(persona.getCurp())) {
						beneficio.setCurp(persona.getCurp());
					}

					if (!CollectionUtils.isEmpty(beneficio.getListaSujetosObligados())) {
						List<String> nrps = new ArrayList<String>(beneficio.getListaSujetosObligados().size());
						StringBuffer nrp = new StringBuffer();
						for (SujetoObligado so : beneficio.getListaSujetosObligados()) {
							nrp.append(so.getNumeroRegistroPatronal());
							nrp.append(so.getModalidad().getNumModalidad());
							nrp.append(so.getDigVerificador());

							nrps.add(nrp.toString());
							nrp.delete(0, nrp.length());
						}
						beneficio.setListaNRPsMod10y13(nrps);
					}

					if (persona != null
							&& StringUtils.isNotBlank(persona.getNss())
							&& CollectionUtils.isEmpty(beneficio.getListaSujetosObligados())) {
						beneficio.setTipoApartado(ApartadoPersonaBeneficioEnum.APARTADO_A.getClave());
					} else if ((persona == null || StringUtils.isBlank(persona.getNss()))
							&& !CollectionUtils.isEmpty(beneficio.getListaSujetosObligados())) {
						beneficio.setTipoApartado(ApartadoPersonaBeneficioEnum.APARTADO_B.getClave());
					} else {
						beneficio.setTipoApartado(ApartadoPersonaBeneficioEnum.APARTADO_AB.getClave());
					}

					if(persona.getCveIdAsignacionNSS()!=null && !persona.getCveIdAsignacionNSS().equals(0L)) {
						beneficio.setCveAsignacion(persona.getCveIdAsignacionNSS());
					}else{
						AsignacionNSS asignacionNSS = serviceBusiness.obtenerAsignacionNss(persona.getNss());
						beneficio.setCveAsignacion(asignacionNSS.getIdAsignacionNSS());
					}
					log.info("Request -> " + beneficio);
					log.info("El tramite es renovacion: "+esRenovacion);

					ClienteWebserviceValidarRiss validarRissWS = new ClienteWebserviceValidarRiss();
					RespuestaRifSat respuestaRifSat = null;
					respuestaRifSat = validarRissWS.validarRiss(beneficio,esRenovacion);
					this.log.debug("RESPUESTA WS -> " + respuestaRifSat);

					if (respuestaRifSat.getExito() == 0) {
						if (respuestaRifSat.getIndicadorDerecho()) {
							beneficioServiceUtility.validarVigenciaBeneficioRiss(respuestaRifSat.getFechaAltaRif(),
									getParametroFechaInicioRifImss());
							respuesta.setCurp(persona.getCurp());
							respuesta.setDescripcion("SI tiene beneficio y lo mantiene");
							respuesta.setRfcVigente(persona.getRfc());
							respuesta.setExito(1);
							respuesta.setClaveError(0);
							return respuesta;
						} else {

							mensajeError = respuestaRifSat.getMotivoDeRechazo();
							codigoError = 1;
                            log.info("El WS indica que ya no es apto para beneficio: "+mensajeError);
							throw new BeneficioRissException(mensajeError,codigoError);
						}
					} else {
						mensajeError = respuestaRifSat.getDescripcion();
						codigoError = respuestaRifSat.getClaveError();
						throw new BeneficioRissException(respuestaRifSat.getDescripcion(),
                                respuestaRifSat.getClaveError());
					}
				}
			}

			mensajeError = "No cuenta con Beneficio en BDTU";
			codigoError = 0;

		} catch (PersonaNoValidaBeneficioRissException e) {
			log.error("Error: ",e);
			mensajeError = e.getMessage();
			codigoError = -1;
		} catch (ClienteWebserviceImssRissException e) {
			log.error("Error: ",e);
			mensajeError = e.getMessage();

			String validaMensaje = mensajeError.toUpperCase();
			if(validaMensaje.contains("ERROR DE COMUNICA")){
				codigoError = -1;
			} else if(validaMensaje.contains("SAT")||validaMensaje.contains("INFONAVIT")){
				codigoError = 1;
			} else{
				codigoError = -1;
			}

		} catch (BeneficioRissException e) {
			log.error("Error: "+e.getMessage());
			log.error("errorMens: "+ mensajeError);
			log.error("codigoErr: "+codigoError);
			if(mensajeError==null&&codigoError==null){
				mensajeError = e.getMessage();
				codigoError = -1;
			}
		}

		respuesta.setCurp(persona.getCurp());
		respuesta.setDescripcion(mensajeError);
		respuesta.setMotivoDeRechazo(mensajeError);
		respuesta.setRfcVigente(persona.getRfc());
		respuesta.setExito(0);
		respuesta.setClaveError(codigoError);

		return respuesta;
	}

	@Override
	public Solicitud validarSolicitudRissEnProceso(Fisica fisica, 
			OrigenSolicitudEnum origenSolicitud){
		
		Solicitud solicitud = null;
		try {
			Long idSolicitud = 0L;
			//Valida si existe solicitud RISS en proceso asociada a la persona
			if (fisica.getIdPersona() != null) {
				idSolicitud = beneficioServiceEntity.solicitudEnProcesoFisicas(
					fisica.getIdPersona(),origenSolicitud);				
			}			
			if(idSolicitud <= 0L){
				//Valida si existe solicitud RISS en proceso asociada a la pf de los sujetos obligados
				List<SujetoObligado> listaSujetosObligados = obtenerSujetosObligadosParaBeneficio(fisica.getRfc());
				if((!CollectionUtils.isEmpty(listaSujetosObligados))){
					idSolicitud = beneficioServiceEntity.solicitudEnProcesoPatron(beneficioServiceUtility
						.getIdsSujetosObligados(listaSujetosObligados), origenSolicitud);
				}
			}			
			if (idSolicitud > 0L) {
				solicitud = obtenerSolicitudPorId(idSolicitud);
			}
			
		} catch (AbstractException e) {
			log.error("No existe solicitud en proceso.");
			log.error(e);
		}		
		return solicitud;
	}
	
	@Override
	public void heredarBeneficioAltaPatronal(SujetoObligado so, Long origenSolicitud) throws BeneficioRissException {
		
		if(so!=null && so.getTipoPersonaFiscal()!=null && 
			so.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA) &&
			so.getFisica()!=null && so.getFisica().getIdPersona()!=null){
			
			RespuestaRifSat respuestaRifSat = null;
			//Validar si es RIF para Ciudadano
			if(origenSolicitud.equals(OrigenSolicitudEnum.PORTAL_CIUDADANO.getId())){
				try {
					respuestaRifSat = getWebServicesValidaRifSat(so.getFisica().getRfc());
				} catch (AbstractException e) {
					crearSolicitudRechazoRif(so.getFisica(), "", origenSolicitud, e.getMessage());
					throw new BeneficioRissException(PREFIJO_VALIDACION_RIF + e.getMessage());
				}
			}
			
			//Obtener beneficio ACTIVO de Sujetos Obligados asociados a la misma PF, para heredar dicho beneficio.
			List<DitPatSujObligBeneficio> listaDitPatSujObligBeneficio = beneficioServiceEntity
				.obtenerBeneficioActivoPatrones(so.getFisica().getIdPersona());
			
			if((!CollectionUtils.isEmpty(listaDitPatSujObligBeneficio))){
				//Retomar el primer registro de DitPatSujObligBeneficio
				DitPatSujObligBeneficio  ditPatSujObligBeneficioExistente = listaDitPatSujObligBeneficio.get(0);				
				procesarHerenciaBeneficio(ditPatSujObligBeneficioExistente.getDitBeneficio(), so, true);
			}else{
				if(!origenSolicitud.equals(OrigenSolicitudEnum.PORTAL_CIUDADANO.getId())){
					//NO CIUDADANO. Verificar si el patron es RIF
					try {
						respuestaRifSat = getWebServicesValidaRifSat(so.getFisica().getRfc());						
					} catch (AbstractException e) {
						respuestaRifSat=null;
						log.error("No se otorga beneficio al SO: " + so.getCveIdSujetoObligado());
						log.error(e);
					}
				}
				
				//NO se heredo beneficio como Patron (Si es RIF, Otorgar beneficio al SO)				
				if(respuestaRifSat!=null){
					log.debug("Fecha RIF: " + respuestaRifSat.getFechaAltaRif());
					beneficioPatronNoHeredado(so, respuestaRifSat, origenSolicitud);
				}
			}
		}
	}

	@Override
	public void crearSolicitudRechazoRifAltaPatronal(Long idSolicitud, String mensaje) {
		try {
			// Se calida la solicitud donde se generara el rechazo
			List<EstadoSolicitudEnum> estadosValidos = new ArrayList<EstadoSolicitudEnum>();
			estadosValidos.add(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION);

			Solicitud sol = new Solicitud();
			sol.setSolicitudId(idSolicitud);

			Solicitud solicitud = solicitudBusinessRemote.consultar(sol);
			solicitudBusinessRemote.validarEstadoProcesamiento(solicitud, estadosValidos);

			Tramite tramiteAltaSRT = null;
			for (Tramite tramite : solicitud.getTramites()) {
				TipoTramiteEnum tipoTramite = TipoTramiteEnum.obternerEnumById(
						tramite.getTipoTramite().getIdTipoTramite());
				log.error("Tipo Tramite en solicitud de Alta: " + tipoTramite);
				switch (tipoTramite) {
				case ALTA_SRT:
					tramiteAltaSRT = tramite;
					break;
				case ALTA_SRT_PM:
					tramiteAltaSRT = tramite;
					break;
				default:
					log.info("Tipo de tramite invalido para generar Solicitud de rechazo");
				}
			}

			if (tramiteAltaSRT != null) {
				TramiteSujetoObligado tso = (TramiteSujetoObligado) tramiteAltaSRT;
				SujetoObligado sujetoobligado = tso.getSujetoObligado();

				OrigenSolicitud origenSolicitud = solicitud.getOrigenSolicitud();
				if (origenSolicitud.getIdTipoSolicitud().equals(OrigenSolicitudEnum.PORTAL_CIUDADANO.getId())
						&& StringUtils.isNotBlank(mensaje)
						&& mensaje.startsWith(PREFIJO_VALIDACION_RIF)) {
					crearSolicitudRechazoRif(sujetoobligado.getFisica(), "",
							origenSolicitud.getIdTipoSolicitud(), mensaje);
				}

			}
		} catch (Exception e) {
			log.error("Ocurrio un error al crear la solicitud de rechazo: " + e.getMessage());
		}
	}

	@Override
	public boolean esTramiteActivo(TramiteRiss tramiteRiss){
		return beneficioServiceUtility.esTramiteActivo(tramiteRiss);
	}
	
	@Override
	public String indicarRPsPendientes(Fisica fisica, OrigenSolicitudEnum origenSolicitud){
		String msg = null;
		try {		
			List<SujetoObligado> listaSO = obtenerSujetosObligadosParaBeneficio(fisica.getRfc());
			if(origenSolicitud.equals(OrigenSolicitudEnum.INTERNET)){
				fisica.setTipoPersona(new TipoPersona());
				fisica.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
				fisica=complementarDatosPF(fisica);				
				listaSO = obtenerSujetosObligadosPersona(listaSO, fisica);				
			}
			if((!CollectionUtils.isEmpty(listaSO))){
				int total = obtenerTotalRPsPendientesBeneficio(listaSO);
				if(total>0){
					msg = "Usted cuenta con " + total
						+ " Registros Patronales sin beneficio RISS, se iniciar&aacute; solicitud para el otorgamiento del Beneficio RISS";
				}
			}			
		} catch (PersonaNoValidaBeneficioRissException e) {
			msg = null;
		}		
		return msg;
	}
	
	@Override
	public DatosRiss validaIncorporacionBeneficioRissSeguroPersonal(DatosRiss riss){
		riss.setErrorFormGeneral(null);
		
		if(habilitarRissPortal(riss.getIdOrigenSolicitud())){
			try {
				Fisica fisica = new Fisica();
				fisica.setIdPersona(riss.getIdPersona());
				fisica.setRfc(riss.getRfc());
				
				//verifica si es compra o renovacion
                SegurosIvro seguros = new SegurosIvro();

				TipoTramiteEnum tramiteEjecucion = TipoTramiteEnum.COMPRA_SEGURO_INDIVIDUAL;
                try {
                    Persona persona = new Persona();
                    persona.setIdPersona(riss.getIdPersona());
                    seguros = consultaSeguroIvroServiceRemote.buscaUltimosSegurosIndividual(persona);

                    if(seguros!=null&&seguros.getSeguroIvro()!=null&&seguros.getSeguroIvro().length>0) {
						log.info("Parseo Objeto "
								+ ReflectionToStringBuilder.toString(seguros));

						SeguroIvro[] segurosIvro = seguros.getSeguroIvro();
						boolean renovar = this.puedeRenovarSeguro(segurosIvro);

						if (renovar) {
							tramiteEjecucion = TipoTramiteEnum.RENOVACION_SEGURO_INDIVIDUAL;
						}
					}
                } catch (Exception e) {
                    log.error("Error no controlado ", e);
                }

                //VERIFICA INCORPORACION RISS
                incorporacionBeneficioRiss(tramiteEjecucion, fisica,
                        riss.getIdOrigenSolicitud(), riss.getUsuario(), false);

			} catch (AbstractException e) {
                log.error(e);
                log.error("Mensaje error: " + e.getMessage());
                riss.setErrorFormGeneral(e.getMessage());
            } catch (Exception e){
                log.error(e);
                log.error("Mensaje error: " + e.getMessage());
                riss.setErrorFormGeneral(e.getMessage());
            }
		}		
		return riss;
	}
	
	@Override
	public void validaIncorporacionBeneficioRissAltaPatronal(Fisica fisica, 
			Long origenSolicitud, String usuario) throws BeneficioRissException, RifException {
		
		if(habilitarRissPortal(origenSolicitud)){
			

			try {
				RespuestaRifSat respuestaRifSat = getWebServicesValidaRifSat(fisica.getRfc());
				if(respuestaRifSat!=null){
					log.debug("AltaPatronal-IndicadorDerecho: " + respuestaRifSat.getIndicadorDerecho());
					log.debug("AltaPatronal-FechaAltaRif: " + respuestaRifSat.getFechaAltaRif());
				}
			} catch (RifException e) {
				if(origenSolicitud.equals(OrigenSolicitudEnum.PORTAL_CIUDADANO.getId())){
					crearSolicitudRechazoRif(fisica, usuario, origenSolicitud, e.getMessage());
				}
				//Propagar error NO RIF
				throw new RifException(e.getMessage());			
			}
			
			List<Beneficio> listaBeneficioPatronal = null;
			List<SujetoObligado> listaSujetosObligados = null;
			try {
				//Obtener beneficio PATRON
				if(BeneficioWSUtil.esCadenaNoVacia(fisica.getRfc())){			
					//Obtener RPs asociados (Para INTERNET se filtra por idPersona)
					listaSujetosObligados = obtenerSujetosObligadosParaBeneficio(fisica.getRfc());				
					if(origenSolicitud.equals(OrigenSolicitudEnum.INTERNET.getId())){
						listaSujetosObligados = obtenerSujetosObligadosPersona(listaSujetosObligados, fisica);
					}
					//Obtener beneficios patron
					if (!CollectionUtils.isEmpty(listaSujetosObligados)) {
						listaBeneficioPatronal = obtenerBeneficiosPatronesPorIds(listaSujetosObligados);
					}		
						
				}
			} catch (AbstractException e) {
				log.error(e);
			}
			//Si tiene RPs, y no cuenta con beneficio a nivel patronal. Se verifica la incorporacion del 
			// benefiico RISS solo a Nivel Patronal
			if( (!CollectionUtils.isEmpty(listaSujetosObligados)) 
					&&  CollectionUtils.isEmpty(listaBeneficioPatronal)){
				incorporacionBeneficioRiss(TipoTramiteEnum.ALTA_SRT, fisica, origenSolicitud, usuario, true);
			}				
		}	
	}
	
	@Override
	public RespuestaRifSat getWebServicesValidaRifSat(String rfc) throws RifException{
		RespuestaRifSat respuestaRifSat = BeneficioWSUtil
			.webServicesValidaDerechoRifSat(rfc);
		if (respuestaRifSat.getExito() == 0) {
			if (respuestaRifSat.getIndicadorDerecho()) {
				try {
					beneficioServiceUtility.validarVigenciaBeneficioRiss(respuestaRifSat.getFechaAltaRif(), 
						getParametroFechaInicioRifImss());
				} catch (AbstractException e) {
					throw new RifException(e.getMessage());
				}
			}else{
				throw new RifException(respuestaRifSat.getMotivoDeRechazo());
			}
		} else {
			throw new RifException(respuestaRifSat.getDescripcion(), respuestaRifSat.getClaveError());
		}		
		return respuestaRifSat;		
	}
	
	@Override
	public boolean habilitarRissPortal(Long idOrigenSolicitud){
		return beneficioServiceUtility.habilitarRissPortal(idOrigenSolicitud, getParametroHabilitarRissPortal());
	}
	
	//Metodos privados
	private int obtenerTotalRPsPendientesBeneficio(List<SujetoObligado> listaRPs){
		int totalRPsPendientes=0;
		if((!CollectionUtils.isEmpty(listaRPs))){
			//Obtener listas de SO con Beneficio
			List<Beneficio> listaBeneficioExistente = beneficioServiceEntity
				.obtenerBeneficiosPorIdsSujetosObligados(beneficioServiceUtility
					.getIdsSujetosObligados(listaRPs), null);			
			List<SujetoObligado> listaSujetosBeneficio = new ArrayList<SujetoObligado>();
			//Determinar si existen SO pendientes 
			if((!CollectionUtils.isEmpty(listaBeneficioExistente))){
				for(Beneficio beneficio : listaBeneficioExistente){
					listaSujetosBeneficio.addAll(beneficio.getListaSujetosObligados());							
				}
				//Determinar SO pendientes
				List<SujetoObligado> listaDiferencias = obtenerDiferenciaListas(
					listaRPs, listaSujetosBeneficio);				
				if((!CollectionUtils.isEmpty(listaDiferencias))){
					totalRPsPendientes = listaDiferencias.size();
				}
			}
		}
		return totalRPsPendientes;
	}
	
	private void prepararLayoutMovimientoRiss(TramiteRiss tramiteSO, 
			TramiteRiss tramitePF, Date fechaActual, RespuestaRifSat respuestaRifSat){		
		List<DescuentoBeneficio> listaDescuentosBeneficio = new ArrayList<DescuentoBeneficio>();
		List<SujetoObligado> listaSujetosObligados = new ArrayList<SujetoObligado>();
		Fisica fisica=null;
		boolean incluirMovimientoPF=false;
		if(tramiteSO!=null 
			&& !CollectionUtils.isEmpty(tramiteSO.getListaCveIdSujetosObligados())){
			listaDescuentosBeneficio = tramiteSO.getListaDescuentosBeneficio();
			for(Long clave : tramiteSO.getListaCveIdSujetosObligados()){
				SujetoObligado so = sujetoObligadoServiceBusiness
					.obtenerDetalleRegistroPatronalPorClaveTipoPersona(
						clave, TipoPersonaFiscal.FISICA);
				if(so!=null){
					listaSujetosObligados.add(so);					
				}									
			}
			if(tramitePF==null && !CollectionUtils.isEmpty(listaSujetosObligados)){
				fisica = personaBusiness.getPersonaFisica(listaSujetosObligados
					.get(0).getFisica().getIdPersona());
				if (fisica != null && fisica.getRfc() == null) {
					fisica.setRfc(tramiteSO.getRfcSolicitud());
				}
			}
		}
		if(tramitePF!=null && tramitePF.getFisica()!=null){
			incluirMovimientoPF=true;
			fisica = personaBusiness.getPersonaFisica(tramitePF.getFisica().getIdPersona());
			if (fisica != null && fisica.getRfc() == null) {
				fisica.setRfc(tramitePF.getRfcSolicitud());
			}
			
			if(CollectionUtils.isEmpty(listaDescuentosBeneficio)){
				listaDescuentosBeneficio=tramitePF.getListaDescuentosBeneficio();
			}
		}
		String patronGeneral = parametrosServiceBusinessRemote
			.obtenerParametroDeConfiguracion(ParametroSistemaEnum.PATRON_RISS_NSS.getCodigo());
		encolarMovimientoRissBusiness.encolarMovimientosRiss(beneficioServiceUtility
			.prepararLayoutMovimientoAltaRiss(listaDescuentosBeneficio, listaSujetosObligados,
				incluirMovimientoPF, fisica, fechaActual, respuestaRifSat, patronGeneral));
	}
	
	private void procesarHerenciaBeneficio(DitBeneficio beneficioExistente, SujetoObligado so, 
			boolean heredaBeneficioPatron){
		try {					
			//Transformar beneficio
			Beneficio beneficio = beneficioServiceUtility
				.convertirEntityToModel(beneficioExistente);					
			Long idSolicitud = 0L;
			
			if(!CollectionUtils.isEmpty(beneficio.getListaSujetosObligados())){				
				//Obtener idSolicitud en la que se dieron de alta los SO existentes.
				idSolicitud = beneficioServiceEntity.obtenerIdSolicitudPatron(
						beneficioServiceUtility.getIdsSujetosObligados(beneficio.getListaSujetosObligados()));
				//TODO: Puede NO existir la solicitud en la que se dio de alta el beneficio a nivel patron.
				// Esto ocurre si primero se otorgo a nivel trabajador y despues se dio un Alta Patronal
				// y se heredo el beneficio, por lo cual, no hay una solicitud riss a nivel patron.
				if(idSolicitud <= 0L){
					Long idPersona = ((beneficio.getListaSujetosObligados().get(0).getFisica() != null &&
						beneficio.getListaSujetosObligados().get(0).getFisica().getIdPersona()!=null) ? 
							beneficio.getListaSujetosObligados().get(0).getFisica().getIdPersona() : 0L);					
					//Obtener idSolicitud en la que se dio de alta el trabajador independiente.
					idSolicitud = beneficioServiceEntity.obtenerIdSolicitudFisicas(idPersona);					
				}
			}else{
				idSolicitud = beneficioServiceEntity
				.obtenerIdSolicitudFisicas(beneficio.getFisica().getIdPersona());
			}
			
		
			Solicitud solicitud = null;
			if (idSolicitud > 0L) {
				solicitud = obtenerSolicitudPorId(idSolicitud);
			}
			
			if(solicitud!=null && solicitud.getSolicitudId()!=null){
				//Debe existir informacion en el detalle del tramite, con base a ello se obtendra informacion.
				RespuestaRifSat respuestaRifSat = null;
				if(solicitud!=null && (!CollectionUtils.isEmpty(solicitud.getTramites()))){
					beneficio.setIdSolicitud(solicitud.getSolicitudId());
					for(Tramite tramite : solicitud.getTramites()){
						TramiteRiss tramiteRiss = ((TramiteRiss)tramite);
						if(esTramiteActivo(tramiteRiss)){
							log.debug("Id Solicitud relacionada "+ solicitud.getSolicitudId());
							respuestaRifSat = tramiteRiss.getRespuestaRifSat();
							break;
						}
					}
				}
				
				if(respuestaRifSat!=null){
					List<SujetoObligado> listaSO = new ArrayList<SujetoObligado>();
					listaSO.add(so);					
					if(heredaBeneficioPatron){	
						beneficioPatronHeredado(beneficioExistente, so);
					}else{
						//Generar Beneficio a Nivel Patronal (Es el primer SO que da de alta)
						Date fechaActual = beneficioExistente.getFecInicioVigencia();
						Date fechaSatRif = respuestaRifSat.getFechaAltaRif();
						beneficio.setEsPatron(true);						
						beneficio.setFisica(null);
						beneficio.setListaSujetosObligados(listaSO);						
						guardarBeneficio(beneficio, fechaActual, fechaSatRif);
						log.debug("Beneficio RISS Generado " + beneficio.getIdBeneficio());
					}												
					//Encolar movimiento alta
					encolarMovimientoRissBusiness.encolarMovimientosRiss(
						beneficioServiceUtility.movimientosAltaPatrones(
							beneficio.getListaDescuentosBeneficio(), listaSO, 
								so.getFisica(), new Date(), respuestaRifSat.getFechaAltaRif(), respuestaRifSat));							
				}else{
					log.debug("No existe detalle de la respuesta SAT respecto al RIF.");
				}
			}else{
				log.debug("Existe beneficio pero NO existe solicitud asociada.");
			}
		} catch (AbstractException e) {
			log.error(e);
			e.printStackTrace();
		}
	}
	
	private void beneficioPatronHeredado(DitBeneficio beneficioExistente, SujetoObligado so){
		DitPatronSujetoObligado ditPatronSujetoObligado = new DitPatronSujetoObligado();
		ditPatronSujetoObligado.setCveIdPatronSujetoObligado(so.getCveIdSujetoObligado());					
		DitPatSujObligBeneficio  nuevoPatronBeneficio = null;						
		//Clonar el beneficio patron existente y asociar sujeto obligado
		DitPatSujObligBeneficio ditPatSujObligBeneficio = beneficioExistente.getDitPatSujObligBeneficios().get(0);
		nuevoPatronBeneficio = (DitPatSujObligBeneficio)ditPatSujObligBeneficio.clone();
		nuevoPatronBeneficio.setCveIdPatSujObligBeneficio(0l);
		nuevoPatronBeneficio.setDitPatronSujetoObligado(ditPatronSujetoObligado);
		beneficioServiceEntity.guardarDitPatSujObligBeneficio(nuevoPatronBeneficio);
		log.debug("Se hereda Beneficio RISS " + beneficioExistente.getCveIdBeneficio());
	}
	
	private Solicitud obtenerSolicitudPorId(Long idSolicitud) throws SolicitudNoEncontradaException{
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(idSolicitud);
		solicitud = this.solicitudBusinessRemote.consultar(solicitud);
		return solicitud;
	}
	
	private Fisica obtenerPersonaFisicaNSS(Fisica fisica) 
			throws PersonaConVariosNSSException, PersonaSinNSSException {
		fisica.setNss(personaBusiness.obtenerNssVigentePersona(fisica.getIdPersona()));		
		return fisica;
	}
	
	private Fisica complementarDatosPF(Fisica fisica){
		Fisica fisicaCompleta = personaBusiness.getPersonaFisica(fisica.getIdPersona());
		fisicaCompleta.setRfc(fisica.getRfc());
		fisicaCompleta.setTipoPersona(fisica.getTipoPersona());
		return fisicaCompleta;
	}
	
	private void obtenerRangoDescuentosRiss(Beneficio beneficio, Date fechaAltaRif){
		Date fechaMaximaRif = beneficioServiceUtility.fechaFinRif10Anios(fechaAltaRif);		
		beneficio.setListaDescuentosBeneficio(beneficioServiceUtility.obtenerDescuentoBeneficio(
			beneficioServiceEntity.obtenerDicDescuentosRiss(), new Date(), fechaAltaRif, fechaMaximaRif));				
	}
	
	private void validarBeneficioExistente(Beneficio beneficio) throws BeneficioRissException{
        List<Integer> estadoBeneficio = new ArrayList<Integer>();
        estadoBeneficio.add(EstadoBeneficioEnum.ACTIVO.getClave());

        log.info("es patron: "+beneficio.getEsPatron());

		if(beneficio.getEsPatron()){
		    log.info("valida como patron");
			//Validar Beneficios como Patron
			if((!CollectionUtils.isEmpty(beneficio.getListaSujetosObligados()))){
			    log.info("valida como patron - Sujetos Obligados");
				beneficioServiceUtility.validarBeneficiosExistentes(
					beneficioServiceEntity.obtenerBeneficiosPorIdsSujetosObligados(
						beneficioServiceUtility.getIdsSujetosObligados(
								beneficio.getListaSujetosObligados()), null));
				
			}
			//Validar Beneficios como PF
			if(beneficio.getFisica()!=null && beneficio.getFisica().getIdPersona()!=null){
			    log.info("valida como patron - Persona fisica");
				beneficioServiceUtility.validarBeneficiosExistentes(
					beneficioServiceEntity.obtenerBeneficiosPersona(beneficio.getFisica(), null));
			}else{
			    log.info("persona FISICA es null");
            }
		}else{
			//Validar Beneficios como PF
			//busca solo si la persona tiene beneficios activos
            log.info("No es patron busca como Persona Fisica");
                        estadoBeneficio.add(EstadoBeneficioEnum.CANCELADO.getClave());
			beneficioServiceUtility.validarBeneficiosExistentes(
				beneficioServiceEntity.obtenerBeneficiosPersona(beneficio.getFisica(), estadoBeneficio));					
		}
		log.info("Sale de la funcion sin mandar error");
	}
		
	private void verificarNivelBeneficioOrtorgar(Beneficio beneficio){

        log.info("beneficio esPatron: "+beneficio.getEsPatron());

		if(beneficio.getEsPatron()){			
			//Validar Beneficios como TRABAJADOR
			if(beneficio.getFisica()!=null && beneficio.getFisica().getIdPersona()!=null){
				List<Beneficio> listaBeneficioExistente = beneficioServiceEntity
					.obtenerBeneficiosPersona(beneficio.getFisica(), null);
				//Ya cuenta con Beneficio como TRABAJADOR
                log.info("existe persona fisica y beneficio es vacio: "+CollectionUtils.isEmpty(listaBeneficioExistente));
				if((!CollectionUtils.isEmpty(listaBeneficioExistente))){
				    log.info("Existe beneficio y le quita la persona");
//					beneficio.getFisica().setIdPersona(null);
//					beneficio.getFisica().setNss(null);
				}
			}else{
			    log.info("Persona Fisica es null ");
            }
			//Validar Beneficios como PATRON
			if((!CollectionUtils.isEmpty(beneficio.getListaSujetosObligados()))){
			    log.info("valida como patron");
				//Obtener listas de SO con Beneficio
				List<Beneficio> listaBeneficioExistente = beneficioServiceEntity
					.obtenerBeneficiosPorIdsSujetosObligados(beneficioServiceUtility
						.getIdsSujetosObligados(beneficio.getListaSujetosObligados()), null);				
				List<SujetoObligado> listaSujetosBeneficio = new ArrayList<SujetoObligado>();
				//Determinar si existen SO pendientes 
				if((!CollectionUtils.isEmpty(listaBeneficioExistente))){
					beneficio.setIdBeneficioPatronExistente(listaBeneficioExistente.get(0).getIdBeneficio());
					for(Beneficio b : listaBeneficioExistente){
						listaSujetosBeneficio.addAll(b.getListaSujetosObligados());							
					}					
					//Determinar SO pendientes
					List<SujetoObligado> listaDiferencias = obtenerDiferenciaListas(
						beneficio.getListaSujetosObligados(), listaSujetosBeneficio);
					if((!CollectionUtils.isEmpty(listaDiferencias))){
						beneficio.setListaSujetosObligados(listaDiferencias);
					}else{
						beneficio.setIdBeneficioPatronExistente(null);
						//Remover la lista de SO solo si se evaluara como Trabajador sino no se mandara nada.
						if (beneficio.getFisica() != null && beneficio.getFisica().getIdPersona() != null) {
							beneficio.setListaSujetosObligados(null);
						}
						
					}
				}
			}
		}
	}
	
	private List<SujetoObligado> obtenerDiferenciaListas(List<SujetoObligado> lista1, List<SujetoObligado> lista2){
		List<SujetoObligado> listaSOFinal = new ArrayList<SujetoObligado>();
		if((!CollectionUtils.isEmpty(lista1)) && (!CollectionUtils.isEmpty(lista2))){			
			for(SujetoObligado so1 : lista1){
				boolean soExiste=false;
				for(SujetoObligado so2 : lista2){
					if(so1.getCveIdSujetoObligado().equals(so2.getCveIdSujetoObligado())){
						soExiste=true;
					}
				}				
				if (soExiste == false) {
					listaSOFinal.add(so1);
				}
				
			}
		}
		return listaSOFinal;	
	}
	
	
	private void actualizaTramiteRiss(Solicitud solicitud, RespuestaRifSat respuestaRifSat,
			boolean respuestaInfonavit, List<DescuentoBeneficio> listaDescuentosBeneficio, 
			String motivoRechazo, boolean cancelarTramitePF){
		try {
			Solicitud solActualizar = obtenerSolicitudPorId(solicitud.getSolicitudId());
			if(solActualizar!=null && (!CollectionUtils.isEmpty(solActualizar.getTramites()))){
				for(Tramite tramite : solActualizar.getTramites()){
					if( (cancelarTramitePF) && ((TramiteRiss)tramite).getFisica()!=null){
						//Cancelar tramite Trabajador
						EstadoTramite estadoTramite = new EstadoTramite();
						estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.CANCELADO.getCodigo());
						estadoTramite.setDescripcion(EstadoTramiteEnum.CANCELADO.getDescripcion());
						((TramiteRiss)tramite).setEstadoTramite(estadoTramite);
					}
					((TramiteRiss)tramite).setRespuestaRifSat(respuestaRifSat);
					((TramiteRiss)tramite).setTipoApartadoCandidato(beneficioServiceUtility
						.obtenerTipoApartadoBeneficio(((TramiteRiss)tramite)));					
					((TramiteRiss)tramite).setRespuestaInfonavit(respuestaInfonavit);
					((TramiteRiss)tramite).setListaDescuentosBeneficio(listaDescuentosBeneficio);
					((TramiteRiss)tramite).setMotivoRechazo(motivoRechazo);					
				}
				solicitudBusinessRemote.actualizarTramites(solActualizar);
			}
			//Actualizar solicitud con la descripcion que trae la respuesta.
			if(respuestaRifSat!=null && StringUtils.isNotEmpty(respuestaRifSat.getDescripcion()) 
					&& StringUtils.isNotBlank(respuestaRifSat.getDescripcion())){
				log.info("Respuesta exito SAT - " + respuestaRifSat.getDescripcion());
				solActualizar.setObservacion(respuestaRifSat.getDescripcion());
				solicitudBusinessRemote.actualizarEstados(solActualizar);
			}
		} catch (AbstractException e) {
			log.error(e);
			e.printStackTrace();
		}
	}

	private Beneficio obtenerInformacionDetalleTramite(Beneficio beneficio, boolean consultaPorNRP){
		if(beneficio!=null){
			try {
    				Long idSolicitud = 0L;
				if(consultaPorNRP){
					Fisica fisica = null;
					//Obtener idSolicitud que genero el patron (Por medio de las claves de los Sujetos Obligados)
					if(!CollectionUtils.isEmpty(beneficio.getListaSujetosObligados())){
						idSolicitud = beneficioServiceEntity.obtenerIdSolicitudPatron(
							beneficioServiceUtility.getIdsSujetosObligados(
								beneficio.getListaSujetosObligados()));
						fisica = beneficio.getListaSujetosObligados().get(0).getFisica();
					}
					//TODO: No existe la solicitud en la que se dio de alta el beneficio a nivel patron.
					//Esto puede ocurrir si primero se otorgo a nivel trabajador y despues se dio un Alta Patronal
					//y se heredo el beneficio, por lo cual, no hay una solicitud riss a nivel patron.
					if(idSolicitud <= 0L && fisica!=null && fisica.getIdPersona()!=null){
						//Obtener idSolicitud que genero para el patron como persona fisica.
						idSolicitud = beneficioServiceEntity
							.obtenerIdSolicitudFisicas(fisica.getIdPersona());
					}					
				}else{
					if(beneficio.getFisica()!=null && beneficio.getFisica().getIdPersona()!=null){
						//Obtener NRPs de los SOs con modalidad 10 y 13 activos
						List<SujetoObligado> listaSO10y13 = obtenerSujetosObligadosParaBeneficio(beneficio.getFisica().getRfc());
						if(!CollectionUtils.isEmpty(listaSO10y13)){
							List<String> listaNRPs10y13 = new ArrayList<String>();
							for(SujetoObligado so : listaSO10y13){
								StringBuilder nrp11 = new StringBuilder();
								nrp11.append(so.getNumeroRegistroPatronal());
								nrp11.append(so.getModalidad()!=null ? so.getModalidad().getNumModalidad() : "");
								nrp11.append(so.getDigVerificador()!=null ? so.getDigVerificador() : "");
								listaNRPs10y13.add(nrp11.toString());
							}
							beneficio.setListaNRPsMod10y13(listaNRPs10y13);
						}
						//Obtener idSolicitud que genero la persona fisica
						idSolicitud = beneficioServiceEntity
							.obtenerIdSolicitudFisicas(beneficio.getFisica().getIdPersona());
					}
				}
				
				//Obtener solicitud para sacar informacion del detalle del tramite
				Solicitud solicitud = null;
				if (idSolicitud > 0L) {
					solicitud = obtenerSolicitudPorId(idSolicitud);
				}
				
				RespuestaRifSat respuestaRifSat = null;
				if(solicitud!=null && (!CollectionUtils.isEmpty(solicitud.getTramites()))){
					beneficio.setIdSolicitud(solicitud.getSolicitudId());
					for(Tramite tramite : solicitud.getTramites()){
						if(esTramiteActivo(((TramiteRiss)tramite))){
							respuestaRifSat = ((TramiteRiss)tramite).getRespuestaRifSat();
							if (respuestaRifSat != null) {
								beneficio.setIndicadorApartadoC(respuestaRifSat.getIndicadorC());
							}
						}
					}
				}
			} catch (AbstractException e) {
				log.error("Error al obtener informacion de la solicitud.");
				log.error(e);
			}
		}
		return beneficio;
	}
	
	private List<SujetoObligado> obtenerSujetosObligadosPersona(List<SujetoObligado> listaSO, Fisica fisica){
		List<SujetoObligado> listaSOHelper = new ArrayList<SujetoObligado>();
		if((!CollectionUtils.isEmpty(listaSO))){
			for(SujetoObligado so : listaSO){
				if(fisica.getIdPersona().equals(so.getFisica().getIdPersona())){
					listaSOHelper.add(so);
				}
			}
		}
		return listaSOHelper;			
	}
	
	private void incorporacionBeneficioRiss(TipoTramiteEnum tramiteEjecucion, 
			Fisica fisica, Long idOrigenSolicitud, String usuario,
			boolean beneficioSoloPatron) throws BeneficioRissException {
		Usuario usuarioTramite = new Usuario();
		usuarioTramite.setUsuario(usuario);
		Solicitud solicitud = null;
		try{			
			//Preparar beneficio (T.Independiente y/o Patron).
			Beneficio beneficio = obtenerPersonaBeneficio(
				fisica, idOrigenSolicitud, beneficioSoloPatron);
			//Generar solicitud RISS
			solicitud = crearSolicitudRiss(beneficio, usuarioTramite, idOrigenSolicitud);
			boolean esRenovacion = false;
			if(tramiteEjecucion.equals(TipoTramiteEnum.RENOVACION_SEGURO_INDIVIDUAL)){
                esRenovacion = true;
            }else{
                esRenovacion = false;
            }
            //Validar riss.
            beneficio = validarSolicitudRiss(solicitud, beneficio,esRenovacion);

			//Cambiar estado a PENDIENTE AUTORIZACION.
			solicitud.setEstadoSolicitud(new EstadoSolicitud());
			solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo());
			solicitudBusinessRemote.actualizarEstados(solicitud);
			//Guadar beneficio, concluir solicitud y generar movimientos.
			procesarSolicitudRiss(solicitud.getSolicitudId());
			//Se envia correo de notificacion de Aplicacion de Beneficio RISS
			enviarNotificacionBeneficio(fisica, beneficio, solicitud, tramiteEjecucion);
		} catch (AbstractException e) {
			try {
				log.error("ERROR: " , e);
				cancelarRechazarSolicitudRiss(solicitud.getSolicitudId(), (StringUtils.isBlank(e.getMessage()) ? 
					"Error inesperado al incorporar beneficio RISS" : e.getMessage()), true);
			} catch (AbstractException e1) {
				log.error("No se pudo cancelar solicitud RISS : " + e1);
				e1.printStackTrace();
			}
			//Propagar Error
			throw new BeneficioRissException(e.getMessage());
		}
	}
	
	private void beneficioPatronNoHeredado(SujetoObligado so, 
			RespuestaRifSat respuestaRifSat, Long origenSolicitud) throws BeneficioRissException {
		try{
			//Preparar beneficio Patron
			Beneficio beneficio = beneficioServiceUtility
				.prepararBeneficioPatronSinValidaciones(so, respuestaRifSat);
			//Calcular rangos de descuento
			Date fechaAltaRifSat = respuestaRifSat.getFechaAltaRif();
			Date fechaAltaRifImss = getParametroFechaInicioRifImss();

			if (fechaAltaRifSat.before(fechaAltaRifImss)) {
				obtenerRangoDescuentosRiss(beneficio, fechaAltaRifImss);
			} else {
				obtenerRangoDescuentosRiss(beneficio, fechaAltaRifSat);
			}

			//Crear tramite RISS Patron.
			Solicitud solicitud = crearSolicitudRissPendienteAutorizacion(beneficio, origenSolicitud);
			//Guadar beneficio, concluir solicitud y generar movimientos.
			procesarSolicitudRiss(solicitud.getSolicitudId());
			enviarNotificacionBeneficio(so.getFisica(), beneficio, solicitud, TipoTramiteEnum.ALTA_SRT);
		} catch (AbstractException e) {
			throw new BeneficioRissException(e.getMessage());
		}
	}
		
	private void enviarNotificacionBeneficio(Fisica personaBeneficio,
			Beneficio beneficio, Solicitud solicitud,
			TipoTramiteEnum tramiteEjecucion) {
		String correo = obtenCorreo(personaBeneficio);

		// Sin correo no se envia ninguna notificacion
		if (StringUtils.isNotBlank(correo)) {
			String folioSolicitud = solicitud.getNoFolioSolicitud();
			Long idSolicitud = solicitud.getSolicitudId();
			Integer tipoTramiteAltaRiss = TipoTramiteEnum.ALTA_RIF.getCodigo();
			Map<String, String> parametrosCorreo = new HashMap<String, String>();
			Map<String, String> extraparametrosCorreo = new TreeMap<String, String>();
			
			try {
				Fisica fisica = serviciosPersonaBusiness
						.buscarPersonaFisicayDPyDyMCEnIMSS(personaBeneficio.getIdPersona());
				parametrosCorreo.put("nombreCompleto", fisica.getNombreCompleto());
				if (StringUtils.isNotBlank(fisica.getCurp())) {
					parametrosCorreo.put("curp", fisica.getCurp());
				}
			} catch (PersonaFisicaNoEncontradaException e) {
				log.error("ocurrio un error al consultar las solicitudes abiertas para la persona "
								+ "[" + personaBeneficio.getIdPersona() + "]", e);
			}
			parametrosCorreo.put("folio", folioSolicitud);
			parametrosCorreo.put("fechaOperacion", DateFormat.getDateInstance(DateFormat.LONG,
							new Locale("es", "MX")).format(new Date()));
			parametrosCorreo.put("idSolicitud", idSolicitud.toString());
			parametrosCorreo.put("idTipoTramite", tipoTramiteAltaRiss.toString());
			parametrosCorreo.put("rfc", personaBeneficio.getRfc());
			parametrosCorreo.put("tipoBeneficio", beneficio.getTipoBeneficio().getDescripcion());

			if (tramiteEjecucion.equals(TipoTramiteEnum.ALTA_SRT)) {
				parametrosCorreo.put("descripcionTipoTramite",
								"Alta Patronal e Inscripci\u00F3n en el Seguro de Riesgos de Trabajo SRT");
			} else {
				parametrosCorreo.put("descripcionTipoTramite", "Incorporaci\u00F3n Voluntaria al R\u00E9gimen Obligatorio");
			}

			if (beneficio.getFisica() != null && beneficio.getFisica().getIdPersona() != null) {
				parametrosCorreo.put("tipoDescuento", "Trabajador Independiente");
			} else if (!CollectionUtils.isEmpty(beneficio.getListaSujetosObligados())) {
				parametrosCorreo.put("tipoDescuento", "Patr\u00F3n");
			}

			List<DescuentoBeneficio> listaDescuentoBeneficio = beneficio.getListaDescuentosBeneficio();
			List<String> lstAtributos = new ArrayList<String>();
			lstAtributos.add("fecInicio");
			lstAtributos.add("porcentajeDescuento");
			AttributeComparator.sort(lstAtributos , listaDescuentoBeneficio);

			if (!CollectionUtils.isEmpty(listaDescuentoBeneficio)) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				int totalDescuentos = listaDescuentoBeneficio.size();
				parametrosCorreo.put("totalDescuentos", String.valueOf(totalDescuentos));

				for (int index = 0; index < totalDescuentos; index++) {
					DescuentoBeneficio descuentoBeneficio = listaDescuentoBeneficio.get(index);

					String strFechaInicio = sdf.format(descuentoBeneficio.getFecInicio());
					String strFechaFin = sdf.format(descuentoBeneficio.getFechaFin());
					BigDecimal porcentajeDescuento = descuentoBeneficio.getPorcentajeDescuento();

					StringBuffer sbDatosDescuento = new StringBuffer();
					sbDatosDescuento.append(strFechaInicio).append(";")
							.append(strFechaFin).append(";")
							.append(porcentajeDescuento).append(" %");

					extraparametrosCorreo.put("descuento_" + index, sbDatosDescuento.toString());
				}
			}

			EmailPayloadType mailWrapper = new EmailPayloadType();
			mailWrapper.setSubject(SUBJECT);
			mailWrapper.setContent("");
			mailWrapper.setTo(correo);		
			mailWrapper.setContentType(CONTENT_TYPE);
			mailWrapper.setParameters(parametrosCorreo);
			mailWrapper.setExtraparameters(extraparametrosCorreo);
			eMailProducer.agendarCorreoElectronico(mailWrapper);
		}
	}

	private String obtenCorreo(Fisica titular) {
        mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona personaCorreo =
        		new mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona();
        personaCorreo.setIdPersona(titular.getIdPersona());
        personaCorreo.setTipoPersona(new TipoPersona());
        personaCorreo.getTipoPersona().setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
        String correo = null;
        try {
            List<MedioContacto> medios = contactoServiceBusinessRemote
                    .consultarMedioDeContactoPersona(personaCorreo);
            for (MedioContacto medio : medios) {
                if (medio.getTipoMedioContacto().getIdTipoMedioContacto()
                        .equals(TipoContactoEnum.CORREO_ELECTRONICO.getId())) {
                    if (StringUtils.isNotBlank(correo)) {
                        correo += ", " + correo;
                    } else {
                        correo = medio.getDesFormaContacto();
                    }
                }
            }
        } catch (PersonaSinMedioDeContactoException e) {
            log.debug("Persona sin medios de contacto");
        }
        return correo;
    }

	private Solicitud crearSolicitudRissPendienteAutorizacion(Beneficio beneficio, 
			Long origenSolicitud) throws SolicitudNoValidaException {		
		Solicitud solicitud = beneficioServiceUtility
			.crearSolicitudRiss(beneficio, null, origenSolicitud);		
		EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
		estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo());
		solicitud.setObservacion(TipoBeneficioEnum.RIF.getDesc());
		solicitud.setEstadoSolicitud(estadoSolicitud);
		solicitud = this.solicitudBusinessRemote.crear(solicitud);		
		return solicitud;
	}
	
	private void crearSolicitudRechazoRif(Fisica fisica, String usuario, 
			Long idOrigenSolicitud, String motivoRechazo){
		try {
			Usuario usuarioTramite = new Usuario();
			usuarioTramite.setUsuario(usuario);			
			solicitudBusinessRemote.crear(beneficioServiceUtility
					.crearSolicitudRechazoRif(fisica, usuarioTramite, idOrigenSolicitud, motivoRechazo));
		} catch (SolicitudNoValidaException e) {
			log.error(e);
			e.printStackTrace();
		}
	}
	
	private Long getParametroHabilitarRissPortal(){
		String parametro = parametrosServiceBusinessRemote
			.obtenerParametroDeConfiguracion(ParametroSistemaEnum.HABILITA_RISS_PORTAL.getCodigo());
		return ((StringUtils.isNotEmpty(parametro) && StringUtils.isNotBlank(parametro)) ? new Long(parametro) : 0L);
	}
	

	private Date getParametroFechaInicioRifImss(){
		String parametro = parametrosServiceBusinessRemote
			.obtenerParametroDeConfiguracion(ParametroSistemaEnum.FECHA_INICIO_RIF_IMSS.getCodigo());
		if(!(StringUtils.isNotEmpty(parametro) && StringUtils.isNotBlank(parametro))){
			parametro="01/01/2015";
		}
		log.info("Fecha Inicio Rif IMSS " + parametro);
		return DateUtils.dateToDateConFormato(parametro, "dd/MM/yyyy");
	}

    private boolean puedeRenovarSeguro(SeguroIvro[] seguros) {
        boolean renovar = false;

        if (seguros.length > 0) {
            SeguroIvro seguro = seguros[0];
            Calendar fechaIniRenovaion = Calendar.getInstance();
            Calendar fechaFinRenovaion = Calendar.getInstance();
            Calendar hoy = Calendar.getInstance();
            hoy.setTime(new Date());

            fechaFinRenovaion.setTime(seguro.getFechaFin());
            fechaFinRenovaion.set(Calendar.DAY_OF_MONTH, 1);
            fechaFinRenovaion.add(Calendar.MONTH, 1);
            fechaFinRenovaion.set(Calendar.DATE, +30);

            //El usuario tiene 30 dias previos a su fin de vigencia para renovar
            //por ejemplo si su seguro tiene fecha de fin de vigencia del 31 de diciembre
            //la fecha en la que puede iniciar su renovacion es el 2 de diciembre
            //el 1 de diciembre no puede renovar aun
            fechaIniRenovaion.setTime(seguro.getFechaFin());
            fechaIniRenovaion.add(Calendar.DAY_OF_YEAR, -29);
            fechaIniRenovaion = org.apache.commons.lang.time.DateUtils
                    .truncate(fechaIniRenovaion, Calendar.DATE);
            renovar =(hoy.after(fechaIniRenovaion) && hoy.before(fechaFinRenovaion))
                    || org.apache.commons.lang.time.DateUtils.isSameDay(hoy, fechaIniRenovaion)|| org.apache.commons.lang.time.DateUtils.isSameDay(hoy, fechaFinRenovaion);
            if( renovar &&
                    ( seguro.getEstadoSeguro().getIdEstadoSeguro() == 2  // Activo
                            || seguro.getEstadoSeguro().getIdEstadoSeguro() == 5  ) ) // Concluido   {
                return true;
        }

        return false;
    }
}
