package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.clasificacion.actividad.economica;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.business.DatosExtraPatronBusinessLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.business.PatronSustitucionFusionBusinessLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.business.SujetoObligadoServiceBusinessLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.business.arp.ArpBusiness;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.SujetoObligadoServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.clasificacion.ClasificacionServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.clasificacion.actividad.economica.ClasificacionActividadEconomicaServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.AfiliacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.arp.ArpBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.clasificacion.actividad.economica.ActividadEcServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.reporte.ManejadorReportesRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rule.RuleServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.GenerarCodigoQRServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.MovimientoPatronalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.MovtoPatSujetoObligadoBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.clasificacion.AdjuntosClasificacion;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisEnum;
import mx.gob.imss.ctirss.delta.model.clasificacion.TipoCausaAnalisisEnum;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.SindoAplicacionEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.firma.RespuestaFirmadoSimple;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DoctoReqTramiteOrigenSol;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.BuzonClasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.MovtoPatSujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sindo.MovimientoPatronalType;
import mx.gob.imss.ctirss.delta.tramite.service.entity.TramiteServiceEntityLocal;

@Stateless(name = "clasificacionActividadEconomicaServiceBusiness", mappedName = "clasificacionActividadEconomicaServiceBusiness")
public class ClasificacionActividadEconomicaServiceBusiness extends
		AbstractServiceBusiness implements
		ActividadEcServiceBusinessLocal,
		ActividadEcServiceRemote {

    private static final Logger log = LoggerFactory.getLogger(ClasificacionActividadEconomicaServiceBusiness.class);

	@EJB
	ClasificacionActividadEconomicaServiceEntityLocal clasificacionServiceEntity;

	@EJB
	RuleServiceBusinessRemote ruleService;

	@EJB
	private SolicitudServiceBusinessRemote solicitudServiceBusiness;
	
	@EJB
	private SujetoObligadoServiceBusinessLocal sujetoObligadoService;
	
	@EJB
	private SujetoObligadoServiceBusinessRemote sujObligService;
		
	@EJB
	private mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudServiceBusinessRemote gceSolicitudService;  
	
	@EJB
	private MovimientoPatronalBusinessRemote movimientoPatronalBusinessRemote;
	
	@EJB
	private SujetoObligadoServiceBusinessRemote sujetoObligadoS;
		
	@EJB
	private SolicitudBusinessRemote solicitudService;
	
	@EJB
	private FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;

    @EJB
    private MovtoPatSujetoObligadoBusinessRemote movtoPatSujetoObligadoBusinessRemote;

    @EJB
    private AfiliacionServiceBusinessRemote afiliacionService;
    
    @EJB
    private ManejadorReportesRemote manejadorReportes;
    
    @EJB
    private TramiteServiceEntityLocal tramiteServiceEntityLocal;
    
    @EJB
    private DatosExtraPatronBusinessLocal datosExtraPatronBusinessLocal;
    
    @EJB
    private PatronSustitucionFusionBusinessLocal patronSustitucionFusionBusinessLocal;
    
    @EJB
    private ClasificacionServiceEntityLocal clasificacionServiceEntityLocal;
    
    @EJB
    private ArpBusinessRemote arpBusinessRemote;
    
	@EJB(mappedName = "generarCodigoQRServiceBusiness")
	private GenerarCodigoQRServiceBusinessRemote generarCodigoQRServiceBusinessRemote;
	
	@EJB(mappedName = "documentoProbatorioServiceBusiness")
	private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusiness;
	
	@EJB
	private SujetoObligadoServiceEntityLocal sujetoObligadoServiceEntityLocal;
    
    

    
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.clasificacion
	 * .
	 * actividad.economica.ClasificacionActividadEconomicaServiceBusinessRemote#
	 * obtenerClasificacionPorSujetoObligado(java.lang.Long)
	 */
	@Override
	public Clasificacion obtenerClasificacionPorSujetoObligado(
			Long cveIdSujetoObligado) throws GestionPatronalBusinessException {
		
		if (cveIdSujetoObligado == null) {
			throw new GestionPatronalBusinessException(
					"El identificador del sujeto obligado es requerido");
		}
		
		return clasificacionServiceEntity
				.consultarPorPatronSujetoObligado(cveIdSujetoObligado);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.imss.ctirss.delta.gestion.patronal.service.business.clasificacion
	 * .actividad.economica.ClasificacionActividadEconomicaServiceBusinessLocal#
	 * asignarNuevaClasificacion
	 * (mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion)
	 */
	@Override
	public void asignarNuevaClasificacion(Clasificacion clasificacion, Long cveCausa)
			throws GestionPatronalBusinessException {
		String rfc = null;

		if (clasificacion.getId() == null
				|| clasificacion.getFraccion() == null) {
			throw new GestionPatronalBusinessException(
					"El identificador de la clasificaciï¿½n y la Fracciï¿½n asociada son requeridos");
		}

		if (clasificacion.getSujetoObligado().getTipoPersonaFiscal()
				.equals(TipoPersonaFiscal.FISICA)) {
			rfc = clasificacion.getSujetoObligado().getFisica().getRfc();
		} else if (clasificacion.getSujetoObligado().getTipoPersonaFiscal()
				.equals(TipoPersonaFiscal.MORAL)) {
			rfc = clasificacion.getSujetoObligado().getMoral().getRfc();
		}

		log.debug("RFC recuperado: " + rfc);
		System.out
				.println("\n\n\n\n\n\n\n\n\n\n\n\n\n************************************************* REALIZANDO ACTUALIZACION DE LA CLASIFICACION \n\n\n\n\n\n\n\n\n\n\n\n\n******************************************************************* ");

		clasificacionServiceEntity.actualizarClasificacion(clasificacion, cveCausa);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.clasificacion
	 * .
	 * actividad.economica.ClasificacionActividadEconomicaServiceBusinessRemote#
	 * obtenerFraccionPorIdentificador(java.lang.Long)
	 */
	@Override
	public Fraccion obtenerFraccionPorIdentificador(Long idFraccion) {
		return clasificacionServiceEntity
				.consultarFraccionPorIdentificador(idFraccion);
	}

	@Override
	public SujetoObligado obtenerClasificacionEnTramitePorPatron(
			Long cveIdPatronSujetoObligado) {

		return null;
	}
	
	/**
	 * Metodo creado para pasar el sujeto obligado que ya se consulto en el metodo
	 * que finaliza los tramites de modificacion en el SRT
	 */
	@Override
	public SujetoObligado afectarClasificacionActividadEconomica(SujetoObligado so, Solicitud solicitud, SujetoObligado soActual) throws GestionPatronalBusinessException {
		log.debug("El id del tramite para MSRT es " + so.getCveIdSujetoObligado());
		return concluirSolicitudModificacionSRT(so, solicitud, soActual);
	}

	@Override
	public SujetoObligado afectarClasificacionActividadEconomica(SujetoObligado so, Solicitud solicitud) throws GestionPatronalBusinessException {
		return concluirSolicitudModificacionSRT(so, solicitud);
	}
	
	@Override
	public void ejecutarProcesoSincronizacionSINDO(String noFolio, SujetoObligado so, Date fechaEfecto, Long cveCausa, Integer cveAplicacion, Integer tipoMovto, Integer origenMvto){

		StringBuffer regPatronalDiezDigitos=new StringBuffer();
		
		if(so.getNumeroRegistroPatronal().length()==10)
			regPatronalDiezDigitos.append(so.getNumeroRegistroPatronal());
		else if(so.getNumeroRegistroPatronal().length()>10)
			regPatronalDiezDigitos.append(so.getNumeroRegistroPatronal().substring(0,10));
		else if(so.getNumeroRegistroPatronal().length()==8)
			regPatronalDiezDigitos.append(
					so.getNumeroRegistroPatronal()).append(
					so.getModalidad().getNumModalidad());		
		
		MovimientoPatronalType mpt = new MovimientoPatronalType();
		
		mpt.setCausa(cveCausa.intValue());
		mpt.setCiz(so.getSubdelegacion().getDelegacion().getCiz());
		mpt.setDelegacionOrigen(Integer.valueOf(so.getSubdelegacion().getDelegacion().getClave()));
		mpt.setSubdelegacionOrigen(Integer.valueOf(so.getSubdelegacion().getClave()));
		mpt.setClase(so.getClasificacion().getFraccion().getClase().getClave().intValue());
		mpt.setClaveAplicacion(cveAplicacion);//VALOR FIJO
		mpt.setDigitoVerificador(Integer.valueOf(so.getDigVerificador()));
		mpt.setFraccion(Integer.valueOf(so.getClasificacion().getFraccion().getNumFraccion().trim()));
		mpt.setGrupo(Integer.valueOf(so.getClasificacion().getFraccion().getGrupo().getNumGrupo().trim()));
		mpt.setDivision(Integer.valueOf(so.getClasificacion().getFraccion().getGrupo().getDivision().getNumDivision().trim()));
		mpt.setGiro(so.getClasificacion().getGiro());
		mpt.setNumeroFolio(noFolio);
		mpt.setOrigenMovimiento(origenMvto);
		if (so.getClasificacion().getPrimaSugerida() != null) {
			mpt.setPrima(so.getClasificacion().getPrimaSugerida().doubleValue());
		} else {
			mpt.setPrima(so.getClasificacion().getFraccion().getPrimaSRT().doubleValue());
		}
		mpt.setRegistroPatronal(regPatronalDiezDigitos.toString());
		mpt.setTipoMovimiento(tipoMovto);
		
		if(tipoMovto.intValue() == 6) {
			mpt.setFechaMovimiento(fechaEfecto);
			mpt.setFechaRecepcion(Calendar.getInstance().getTime());
		} else {
			mpt.setFechaMovimiento(Calendar.getInstance().getTime());
		}
        mpt.setFechaCambioCla(Integer.parseInt(String.format("%1$ty%1$tm", fechaEfecto))); //Fecha surte efecto en formato AAmm ej 1309 para septiembre del 2013
		System.err.println(" Parametros Movimiento Patronal Type: "+mpt);
		movimientoPatronalBusinessRemote.enviarModificacionPatronal(mpt);
	}
	
	/**
	 * Se obtiene la clasificaciï¿½n con los valores equivalentes en la BDTU.
	 * Debido a que los identificadores proporcionados por el componente del 
	 * clasificador no son los mismos que en la base BDTU se obtienen los 
	 * correspondientes y se asignan los valores al objeto de clasificacion.
	 * 
	 * Esto se realiza mediante la comparaciï¿½n de los identificadores proporcionados
	 * por el clasificador y los atributos num_xxx de cada entidad.
	 * @author Hugo Martinez
	 * @Date 10/07/2012
	 * @param clasificacion
	 * @return Clasificacion
	 */
	@Override
	public Clasificacion obtenerClasificacionEquivalente(Clasificacion clasificacion){
		if(clasificacion.getFraccion().getId()== null)
			return clasificacion;
		System.err.println("Clasificacion original: Fraccion ["+clasificacion.getFraccion().getId()+"] Grupo ["+clasificacion.getFraccion().getGrupo().getId()+"] Division [ "+clasificacion.getFraccion().getGrupo().getDivision().getId()+" ]");
		System.err.println("Clasificacion NUM original: Fraccion ["+clasificacion.getFraccion().getNumFraccion()+"] Grupo ["+clasificacion.getFraccion().getGrupo().getNumGrupo()+"] Division [ "+clasificacion.getFraccion().getGrupo().getDivision().getNumDivision()+" ]");
		if(clasificacion.getFraccion().getGrupo().getNumGrupo()!= null && clasificacion.getFraccion().getGrupo().getNumGrupo()!= ""){
			System.err.println("Ya se tenia una clasificacion equivalente");
			return clasificacion;
		}
		System.err.println("Se genera la clasificacion equivalente");
		Fraccion fraccionEquivalente = clasificacionServiceEntity.consultarFraccionEquivalente(clasificacion.getFraccion());
		super.log.debug("Fraccion equivalente: "+fraccionEquivalente);
		clasificacion.setFraccion(fraccionEquivalente);
		System.err.println("Clasificacion equivalente : Fraccion ["+clasificacion.getFraccion().getId()+"] Grupo ["+clasificacion.getFraccion().getGrupo().getId()+"] Division [ "+clasificacion.getFraccion().getGrupo().getDivision().getId()+" ] Clase ["+ clasificacion.getFraccion().getClase().getClave() +"]");
		System.err.println("Clasificacion NUM equivalente: Fraccion ["+clasificacion.getFraccion().getNumFraccion()+"] Grupo ["+clasificacion.getFraccion().getGrupo().getNumGrupo()+"] Division [ "+clasificacion.getFraccion().getGrupo().getDivision().getNumDivision()+" ] Clase ["+ clasificacion.getFraccion().getClase().getClave() +"]");
		
		
		return clasificacion;
	}

	@Override
	public Solicitud crearSolicitudAnexoV(String numeroRegistroPatronal, Usuario usuario) {
		SujetoObligado sujetoTramite = new SujetoObligado();
		sujetoTramite.setNumeroRegistroPatronal(numeroRegistroPatronal);
		//Se obtiene la informaciï¿½n de la clasificaciï¿½n actual y toda su actividad economica, esta informaciï¿½n serï¿½ despuï¿½s
		//almacenada como detalle del trï¿½mite
		sujetoTramite=sujetoObligadoService.obtenerDetalleSujetoObligadoActividadEconomica(sujetoTramite);
		sujetoTramite.getClasificacion().setFecEfecto(Calendar.getInstance().getTime());
		sujetoTramite.getClasificacion().setFecPresentacion(Calendar.getInstance().getTime());
		Solicitud solicitud = null;
		try {
			
			solicitud = solicitudServiceBusiness.generarSolicitud(TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION, EstadoSolicitudEnum.ATENDIDA, 
					usuario, TipoTramiteEnum.CLASIFICACION_ANEXO_V, EstadoTramiteEnum.CERRADO, sujetoTramite, false, false);
			
		} catch (GestionPatronalBusinessException e) {
			// La posible excepciï¿½n era la validaciï¿½n de trï¿½mite ï¿½nico, sin embargo para una solicitud de anexo V esa validaciï¿½n
			// se omite y no debe presentarse en ningï¿½n momento. Si eso sucede serï¿½ por un error de codificaciï¿½n.
			e.printStackTrace();
		}
		return solicitud;
	}

	@Override
	public Solicitud obtenerSolicitudAnterior(Solicitud solicitudBase)
			throws GestionPatronalBusinessException {
		if(solicitudBase.getSolicitudId()==null){
			throw new GestionPatronalBusinessException("msg.parametro.requerido",10004);
		}
		solicitudBase.setTipoSolicitud(new TipoSolicitud());
		solicitudBase.getTipoSolicitud().setIdTipoSolicitud(TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION.getValor().longValue());
		Solicitud solicitudAnterior = solicitudServiceBusiness.obtenerSolicitudAnteriorPorTipo(solicitudBase);
		if(solicitudAnterior == null ){
			throw new GestionPatronalBusinessException("solicitud.anterior.no.encontrada", 10003);
		}
		return solicitudAnterior;
	}

	@Override
	public SujetoObligado obtenerClasificacionActividadEconomicaAnteriorPorSolicitud(
			Long idSolicitudActual) throws GestionPatronalBusinessException {
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(idSolicitudActual);
		
		solicitud = obtenerSolicitudAnterior(solicitud);
		TramiteSujetoObligado tso = (TramiteSujetoObligado)solicitud.getTramites().get(0);
		
		return tso.getSujetoObligado();
	}

	@Override
	public SujetoObligado concluirClasificacionActividadEconomica(
			SujetoObligado sujetoTramite, Solicitud solicitud) throws GestionPatronalBusinessException {
		SujetoObligado sujeto= afectarClasificacionActividadEconomica(sujetoTramite, solicitud);
		try {
			//agregamos SO para que no truene al crear tramite para MACII
			if(solicitud.getSujetoObligado() == null && sujeto != null){
				log.debug("::Agregando sujeto obligado");
				solicitud.setSujetoObligado(sujeto);
			}				
			gceSolicitudService.cancelarAnalisisPorRegistroPatronal(sujetoTramite.getNumeroRegistroPatronal(), EstatusAnalisisEnum.CANCELADO_POR_NUEVO_TRAMITE_DE_GCE.getClave() ,solicitud);
		} catch (Exception e) {
			e.printStackTrace();
			throw new GestionPatronalBusinessException("error.cancelar.analisis");
		}
		
		return sujeto;
	}

	@Override
	public Solicitud crearSolicitudAltaPatronal(String numeroRegistroPatronal,
			Usuario usuario) {
		SujetoObligado sujetoTramite = new SujetoObligado();
		sujetoTramite.setNumeroRegistroPatronal(numeroRegistroPatronal);
		//Se obtiene la informaciï¿½n de la clasificaciï¿½n actual y toda su actividad economica, esta informaciï¿½n serï¿½ despuï¿½s
		//almacenada como detalle del trï¿½mite
		sujetoTramite=sujetoObligadoService.obtenerDetalleSujetoObligadoActividadEconomica(sujetoTramite);
		sujetoTramite.getClasificacion().setFecEfecto(Calendar.getInstance().getTime());
		sujetoTramite.getClasificacion().setFecPresentacion(Calendar.getInstance().getTime());
		Solicitud solicitud = null;
		try {			
			solicitud = solicitudServiceBusiness.generarSolicitud(TipoSolicitudEnum.ALTA_PATRONAL, EstadoSolicitudEnum.ATENDIDA, 
					usuario, TipoTramiteEnum.ALTA_SRT, EstadoTramiteEnum.CERRADO, sujetoTramite, false, false);
			solicitud.setSujetoObligado(sujetoTramite);
		} catch (GestionPatronalBusinessException e) {
			// La posible excepciï¿½n era la validaciï¿½n de trï¿½mite ï¿½nico, sin embargo para una solicitud de anexo V esa validaciï¿½n
			// se omite y no debe presentarse en ningï¿½n momento. Si eso sucede serï¿½ por un error de codificaciï¿½n.
			e.printStackTrace();
		}
		return solicitud;

	}

	@Override
	public void actualizarClasificacion(Clasificacion clasificacion, Integer cveCausa, Integer tpoMovimiento) throws GestionPatronalBusinessException{
		if(clasificacion!=null && clasificacion.getSujetoObligado()!=null){
			SujetoObligado sujeto = clasificacion.getSujetoObligado();
			if(sujeto.getCveIdSujetoObligado() == null ){
				if(sujeto.getNumeroRegistroPatronal()==null || 
						(sujeto.getNumeroRegistroPatronal()!=null && sujeto.getNumeroRegistroPatronal()==""))
					throw new GestionPatronalBusinessException("error.parametro.requerido");
				if(sujeto.getModalidad()==null)
					throw new GestionPatronalBusinessException("error.parametro.requerido");
				if(sujeto.getModalidad().getNumModalidad()==null || 
						(sujeto.getModalidad().getNumModalidad()!=null && sujeto.getModalidad().getNumModalidad()==""))
					throw new GestionPatronalBusinessException("error.parametro.requerido");
			}
			
			
		}else{
			throw new GestionPatronalBusinessException("error.parametro.requerido");
		}
		
		if(clasificacion.getId()==null)
			throw new GestionPatronalBusinessException("error.parametro.requerido");	
		
		if(clasificacion.getFraccion()==null)
			throw new GestionPatronalBusinessException("error.parametro.requerido");
		
		if(clasificacion.getFraccion().getId()==null){
			if(clasificacion.getFraccion().getNumFraccion()==null || (clasificacion.getFraccion().getNumFraccion()!=null &&
																		clasificacion.getFraccion().getNumFraccion()==""))
				throw new GestionPatronalBusinessException("error.parametro.requerido");
			if(clasificacion.getFraccion().getGrupo()==null)
				throw new GestionPatronalBusinessException("error.parametro.requerido");
			if(clasificacion.getFraccion().getGrupo().getNumGrupo()==null || 
					(clasificacion.getFraccion().getGrupo().getNumGrupo()!=null 
					&& clasificacion.getFraccion().getGrupo().getNumGrupo()==""))
				throw new GestionPatronalBusinessException("error.parametro.requerido");
			if(clasificacion.getFraccion().getGrupo().getDivision()==null)
				throw new GestionPatronalBusinessException("error.parametro.requerido");
			if(clasificacion.getFraccion().getGrupo().getDivision().getNumDivision()==null || 
					(clasificacion.getFraccion().getGrupo().getDivision().getNumDivision()!=null 
					&& clasificacion.getFraccion().getGrupo().getDivision().getNumDivision()==""))
				throw new GestionPatronalBusinessException("error.parametro.requerido");
			
		}
		
		if(clasificacion.getSujetoObligado().getClasificacion().getId()!=null)
			clasificacionServiceEntity.actualizarClasificacionPorIdentificador(clasificacion, cveCausa, tpoMovimiento);
		else
			clasificacionServiceEntity.actualizarClasificacionPorRegistroPatronal(clasificacion, cveCausa, tpoMovimiento);
		
	}

    public void bajaPatronal(String numeroRegistroPatronal) throws GestionPatronalBusinessException {
        log.debug("bajaPatronal registorPatronal: {}", numeroRegistroPatronal);
        String registroPatronalBasico = numeroRegistroPatronal.replaceFirst("(?<=\\S+)\\d\\d$", "");
        log.info("registroPatronal: {}", registroPatronalBasico);

        MovtoPatSujetoObligado movtoPatSujetoObligado = movtoPatSujetoObligadoBusinessRemote.getMovtoPatSujetoObligadoFromRegPatornal(registroPatronalBasico);
        Integer causa = movtoPatSujetoObligado.getCausa();
        clasificacionServiceEntity.bajaClasificacionPorRegistroPatronal(registroPatronalBasico, causa);

        afiliacionService.crearSolicitudAutomaticaDeBaja(numeroRegistroPatronal, causa, null);
    }

	@Override
	public Fraccion obtenerFraccionClaseActiva(Long cveIdFraccion) throws GestionPatronalBusinessException {
		Fraccion fraccionActiva = clasificacionServiceEntity.obtenerFraccionClaseActiva(cveIdFraccion);
		if(fraccionActiva==null)
			throw new GestionPatronalBusinessException("error.clase.activa.inexistente");
		return fraccionActiva;
	}

	@Override
	public byte[] generarAvisoModificacionSRT(Long idSolicitud) throws GestionPatronalBusinessException {
		Solicitud solicitud = solicitudServiceBusiness.consultarSolicitudPorId(Long.valueOf(idSolicitud));
		return generarAvisoModificacionSRT(solicitud);
	}

	@Override
	public byte[] generarAcuseModificacionSRT(Long idSolicitud) throws GestionPatronalBusinessException {
		Solicitud solicitud = solicitudServiceBusiness.consultarSolicitudPorId(Long.valueOf(idSolicitud));
		return generarAcuseModificacionSRT(solicitud);
	}
	
	private String tipoTramite(Integer codigo){
		TipoTramiteEnum tipo = TipoTramiteEnum.obternerEnumById(codigo);
		switch(tipo){
			case ACTIVIDAD_ECONOMICA:
					return "D";
			case DISPOSICION_DE_LEY:
					return "E";
			case INCORPORACION_DE_ACTIVIDADES:
					return "F";
			case COMPRA_DE_ACTIVOS:
					return "J";
			case COMODATO:
					return "K";
			case ENAJENACION:
					return "L";
			case ARRENDAMIENTO:
					return "M";
			case FIDEICOMISO_TRASLATIVO:
					return "N";
			case ACTUALIZACION_CENTRO_TRABAJO:
				return "CT";		
			case CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO:
				return "ACD";
			default:
				return "D";
		}
	}
	
	private String obtenerNombreCompletoPersonaFisica(Fisica fisica){

		StringBuffer nombreCompleto = new StringBuffer();
		if(fisica.getPrimerApellido()!=null && fisica.getPrimerApellido()!="null"){
			nombreCompleto.append(fisica.getPrimerApellido());
			nombreCompleto.append(" ");
		}
		if(fisica.getSegundoApellido()!=null && fisica.getSegundoApellido()!="null"){
			nombreCompleto.append(fisica.getSegundoApellido());
			nombreCompleto.append(" ");
		}
		if(fisica.getNombre()!=null && fisica.getNombre()!="null")
			nombreCompleto.append(fisica.getNombre());
		
		return nombreCompleto.toString();
	}

	@Override
	public byte[] obtenerDocumentoModificacionSRTPorSolicitud(Long idSolicitud,
			Integer idTipoDocumento) throws GestionPatronalBusinessException {
		Solicitud solicitud = solicitudServiceBusiness.consultarSolicitudPorId(idSolicitud);

		return obtenerDocumentoModificacionSRTPorSolicitud(solicitud, idTipoDocumento);			
	}
	
	@Override
	public byte[] obtenerDocumentoModificacionSRTPorSolicitud(Solicitud solicitud,
			Integer idTipoDocumento) throws GestionPatronalBusinessException {
		String secuenciaNotaria = null;
		Tramite tramite = solicitud.getTramites().get(0);
		if(solicitud.getCadenaOriginal() != null) {
			RespuestaFirmadoSimple selloDigital = firmaDigitalBusinessRemote.getSelloDigital(solicitud.getCadenaOriginal(), solicitud.getSecuenciaDeNotaria(), null);
			secuenciaNotaria = solicitud.getSecuenciaDeNotaria();
			if(selloDigital != null) {
				solicitud.setSelloDigital(selloDigital.getSello());
				solicitud.setSecuenciaDeNotaria(selloDigital.getId());
				solicitud.setNumeroSerieCertificado(selloDigital.getNoSerie());
			}
		}
		
		byte[] documento=null;
		TipoDocumentoTramiteEnum tipo=TipoDocumentoTramiteEnum.obternerEnumById(idTipoDocumento);
		switch(tipo){
			case ACUSE:
				if(solicitud.getDocumentoAcuse()!=null)
					documento=solicitud.getDocumentoAcuse();
				else
					documento=generarAcuseModificacionSRT(solicitud);
				break;
			case COMPROBANTE:
				documento = (byte[]) tramiteServiceEntityLocal.getDocumentoPorTipoIdTramite(tramite.getTramiteId(), DocumentoPorTipoEnum.AVISO_DE_MODIFICACION.getId());
				
				if(documento == null) {
					documento=generarAvisoModificacionSRT(solicitud);
					log.warn("secuencia de notaria del tramite: " + secuenciaNotaria);
					log.warn("secuencia de notaria del sello: " + solicitud.getSecuenciaDeNotaria());
					if(solicitud.getSecuenciaDeNotaria() != null) {
						firmaDigitalBusinessRemote.guardarArchivoFirmado(secuenciaNotaria, "avisoModificacionSRT.pdf", documento);
					}
				}
				
				break;
			default:
				throw new GestionPatronalBusinessException("El tipo de documento solicitado no es valido");
		}
		
		return documento;
	}
	
	private byte[] generarAcuseModificacionSRT(Solicitud solicitud) throws GestionPatronalBusinessException{
		
		Usuario usuario = solicitud.getSolicitante();
		Tramite tramite = solicitud.getTramites().get(0);
		TramiteSujetoObligado tso = (TramiteSujetoObligado)tramite;
		SujetoObligado sujeto = tso.getSujetoObligado();//En el momento de la invocaciï¿½n este tramite contiene la informaciï¿½n anterior de la persona antes de impactar
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		Boolean aviso = false;
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String fecha = " "+formatter.format(solicitud.getFechaSolicitud())+" ";
		parametros.put("P_FECSOLCITUD", fecha);
		parametros.put("P_MOSTRAR_LEY", "1");
		log.debug("P_FECSOLCITUD "+fecha);
		parametros.put("P_FOLIO_SOLCT", solicitud.getNoFolioSolicitud());
		log.debug("P_FOLIO_SOLCT "+solicitud.getNoFolioSolicitud());
		parametros.put("P_CVE_TRAMITE", tipoTramite(tramite.getTipoTramite().getIdTipoTramite()));
		log.debug("P_CVE_TRAMITE D");
		parametros.put("P_DESCTRAMITE", tramite.getTipoTramite().getDescripcion());
		log.debug("P_DESCTRAMITE "+tramite.getTipoTramite().getDescripcion());
		parametros.put("IS_FISICA", false);
		parametros.put("IS_MORAL", false);
		
		
		String numRP = "";
		if(sujeto.getModalidad()!= null && sujeto.getDigVerificador()!=null){
			numRP = sujeto.getNumeroRegistroPatronal()
			+sujeto.getModalidad().getNumModalidad()
			+sujeto.getDigVerificador();
		}else{
			numRP = sujeto.getNumeroRegistroPatronal();
		}
		parametros.put("P_REGPATRONAL", numRP);
		log.debug("P_REGPATRONAL "+numRP);
		if(sujeto.getTipoPersonaFiscal() == TipoPersonaFiscal.FISICA){
			parametros.put("IS_FISICA", true);
			Fisica fisica = sujeto.getFisica();
			fisica  = (Fisica)sujetoObligadoService.obtenerPersonaPorIdentificador(fisica.getIdPersona());
			String nombreCompleto = ""; 
			nombreCompleto = obtenerNombreCompletoPersonaFisica(fisica);
			log.debug("P_RAZONSOCIAL ");
			parametros.put("P_NOMBRE_PERS", nombreCompleto);
			log.debug("P_NOMBRE_PERS "+nombreCompleto);
			if(aviso){
				parametros.put("P_NOMBRE_PERS", fisica.getNombre()!=null ? fisica.getNombre() : "");
				log.debug("P_NOMBRE_PERS "+fisica.getNombre());
				parametros.put("P_PATERNO_PER", fisica.getPrimerApellido()!=null ? fisica.getPrimerApellido() : "");
				log.debug("P_PATERNO_PER "+fisica.getPrimerApellido());
				parametros.put("P_MATERNO_PER", fisica.getSegundoApellido()!=null ? fisica.getSegundoApellido() : "");
				log.debug("P_MATERNO_PER "+fisica.getSegundoApellido());
			}
			parametros.put("P_RFC_PERSONA", fisica.getRfc());
			log.debug("P_RFC_PERSONA "+fisica.getRfc());
			parametros.put("P_CURPPERSONA", fisica.getCurp());
			log.debug("P_CURPPERSONA "+fisica.getCurp());
			parametros.put("P_NOMB_COMERC", sujeto.getNombreComercial());
			log.debug("P_NOMB_COMERC "+sujeto.getNombreComercial());
			
		}else{
			parametros.put("IS_MORAL", true);
			Moral moral = sujeto.getMoral();
			Long idPersonaMoral = moral.getIdPersona()!=null ?moral.getIdPersona() :moral.getCveMoral();
			moral = (Moral)sujetoObligadoService.obtenerPersonaMoralPorIdentificador(idPersonaMoral);
			parametros.put("P_RAZONSOCIAL", moral.getRazonSocial());
			log.debug("P_RAZONSOCIAL "+moral.getRazonSocial());
			parametros.put("P_NOMBRE_PERS", " ");
			log.debug("P_NOMBRE_PERS ");
			parametros.put("P_RFC_PERSONA", moral.getRfc());
			log.debug("P_RFC_PERSONA "+moral.getRfc());
			parametros.put("P_NOMB_COMERC", sujeto.getNombreComercial());
			log.debug("P_NOMB_COMERC "+sujeto.getNombreComercial());
			
		}
				
		Subdelegacion subdelegacion = sujeto.getSubdelegacion();
		
		if(subdelegacion != null){
			parametros.put("P_CVE_DELEGAC", subdelegacion.getDelegacion().getClave());
			log.debug("P_CVE_DELEGAC "+subdelegacion.getDelegacion().getClave());
			parametros.put("P_DES_DELEGAC", subdelegacion.getDelegacion().getDescripcion());
			log.debug("P_DES_DELEGAC "+subdelegacion.getDelegacion().getDescripcion());
			parametros.put("P_CVE_SUB_DEL", subdelegacion.getClave());
			log.debug("P_CVE_SUB_DEL "+subdelegacion.getClave());
			parametros.put("P_DES_SUB_DEL", subdelegacion.getDescripcion());
			log.debug("P_DES_SUB_DEL "+subdelegacion.getDescripcion());
		}else{
			parametros.put("P_CVE_DELEGAC", "");
			log.debug("P_CVE_DELEGAC");
			parametros.put("P_DES_DELEGAC", "");
			log.debug("P_DES_DELEGAC");
			parametros.put("P_CVE_SUB_DEL", "");
			log.debug("P_CVE_SUB_DEL");
			parametros.put("P_DES_SUB_DEL", "");
			log.debug("P_DES_SUB_DEL");
		}
		
		Clasificacion clasificacion = tso.getSujetoObligado().getClasificacion();
		
		if(clasificacion.getFecPresentacion() == null){
			clasificacion.setFecPresentacion(Calendar.getInstance().getTime());
		}
		if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo()) || tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO.getCodigo())){
			fecha = clasificacion.getFecPresentacion() !=null ? " "+formatter.format(clasificacion.getFecPresentacion())+" " : " ";
			log.debug("Se agregan parametros de centro de trabajo: " + tso.getSujetoObligado().getCntroTrabajo());
			CentroTrabajo cTrabajo = tso.getSujetoObligado().getCntroTrabajo();
			parametros.put("CT_CALLE", cTrabajo.getVialidadPrimaria().getNombre());
			
			String numeroExt = cTrabajo.getNumExterior1()!=null && StringUtils.isNotBlank(cTrabajo.getNumExterior1().toString()) ? cTrabajo.getNumExterior1().toString() :"";
			numeroExt +=  cTrabajo.getNumExteriorAlf()!=null && StringUtils.isNotBlank(cTrabajo.getNumExteriorAlf()) ? " "+cTrabajo.getNumExteriorAlf().toUpperCase() : "";
			
			parametros.put("CT_NUM_EXT", numeroExt);
			
			String numeroInt = cTrabajo.getNumInterior()!=null && StringUtils.isNotBlank(cTrabajo.getNumInterior().toString()) ? cTrabajo.getNumInterior().toString() :"";
			numeroInt +=  cTrabajo.getNumInteriorAlf()!=null && StringUtils.isNotBlank(cTrabajo.getNumInteriorAlf()) ? " "+cTrabajo.getNumInteriorAlf().toLowerCase() : "";
			parametros.put("CT_NUM_INT", numeroInt);
			
			String entreCalleA= cTrabajo.getVialidadReferenciaPrimaria()!=null 
					? cTrabajo.getVialidadReferenciaPrimaria().getNombre() : "";
			String entreCalleB = cTrabajo.getVialidadReferenciaSecundaria()!=null 
					? cTrabajo.getVialidadReferenciaSecundaria().getNombre() : "";
					
			parametros.put("CT_ENTRE_CALLE_A", entreCalleA);
			parametros.put("CT_ENTRE_CALLE_B", entreCalleB);
			parametros.put("CT_COLONIA", cTrabajo.getAsentamiento().getNombre());
			parametros.put("CT_LOCALIDAD", cTrabajo.getAsentamiento().getLocalidad().getNombre());
			parametros.put("CT_DELEGACION", cTrabajo.getAsentamiento().getLocalidad().getMunicipio().getNombre());
			parametros.put("CT_ENTIDAD", cTrabajo.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre());
			parametros.put("CT_CP", cTrabajo.getCodigoPostal().getCodigoPostal());
			String telefonoFijo="";
			String telefonoFijo2="";
			String correoElectronico="";
			if(cTrabajo.getMediosContacto()!=null)
				for(MedioContacto medio : cTrabajo.getMediosContacto()){
					if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TELEFONO_FIJO)){
						if(medio.getIdVista()==1)
							telefonoFijo = medio.getDesFormaContacto();
						if(medio.getIdVista()==2)
							telefonoFijo2 = medio.getDesFormaContacto();
					}else if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_CORREO_ELECTRONICO)){
						correoElectronico = medio.getDesFormaContacto();
					}
				}
			
						
			
			if(telefonoFijo2!=null && telefonoFijo2.equalsIgnoreCase("||"))
				telefonoFijo2="";
			
			
			log.debug("CT_TEL_FIJO_A: "+telefonoFijo);
			log.debug("CT_TEL_FIJO_B: "+telefonoFijo2);
			log.debug("CT_CORREO: "+correoElectronico);
			parametros.put("CT_TEL_FIJO_A", parseTelefono(telefonoFijo));
			parametros.put("CT_TEL_FIJO_B", parseTelefono(telefonoFijo2));
			parametros.put("CT_CORREO", correoElectronico);
		}else{ 
			fecha = clasificacion.getFecPresentacion() !=null ? " "+formatter.format(tso.getFechaPresentacion())+" " : " ";
		}
		
		parametros.put("P_FECPRESENTA", fecha);
		log.debug("P_FECPRESENTA "+fecha);
		
		if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo()))
			fecha = clasificacion.getFecEfecto() !=null ? " "+formatter.format(clasificacion.getFecEfecto())+" " : " ";
		else if(tso.getFechaEfecto() != null)
			fecha = tso.getFechaEfecto() != null ? " "+formatter.format(tso.getFechaEfecto())+" " : " ";
		else 
			fecha = tso.getSujetoObligado() != null && 
					tso.getSujetoObligado().getClasificacion() != null &&
					tso.getSujetoObligado().getClasificacion().getFecEfecto() != null ?
							" " + formatter.format(tso.getSujetoObligado().getClasificacion().getFecEfecto()) + " " : " ";
		
		parametros.put("P_FECH_EFECTO", fecha);
		log.debug("P_FECH_EFECTO "+fecha);
		StringBuffer nombreUsuarioCompleto = new StringBuffer();
		nombreUsuarioCompleto.append(" ");
		if(usuario!=null && usuario.getNomNombre()!=null){
			nombreUsuarioCompleto.append(usuario.getNomNombre());
			if(usuario.getNomPaterno()!=null)
				nombreUsuarioCompleto.append(" "+ usuario.getNomPaterno());
			if(usuario.getNomMaterno()!=null)
				nombreUsuarioCompleto.append(" "+ usuario.getNomMaterno());
		}
		String nombreArchivo = "";
		
		nombreArchivo = "Acuse_"+solicitud.getNoFolioSolicitud()+".pdf";
		log.debug("NOMBRE REPORTE: "+nombreArchivo);
		
		
		byte[] reporte = manejadorReportes.ejecutaAcuse(parametros);
		
		
		log.debug("ENVIANDO CORREO ELECTRONICO: "+nombreArchivo);
		//afiliacionService.notificarPorCorreoElectronico(sujeto, solicitud.getSolicitudId(), TipoAccionAfectacionEnum.ATTACH_ACUSE.getValor().intValue());
		log.debug("SE FINALIZO EL ENVIO DE CORREO ELECTRONICO: "+nombreArchivo);
			
		
		return reporte;
	}
	
	
	private byte[] generarAcuseModificacionSRTVentanilla(Solicitud solicitud) throws GestionPatronalBusinessException{
		
		Usuario usuario = solicitud.getSolicitante();
		Tramite tramite = solicitud.getTramites().get(0);
		TramiteSujetoObligado tso = (TramiteSujetoObligado)tramite;
		SujetoObligado sujeto = tso.getSujetoObligado();//En el momento de la invocaciï¿½n este tramite contiene la informaciï¿½n anterior de la persona antes de impactar
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		Boolean aviso = false;
		BufferedImage imagenCodeQR = generarCodigoQRServiceBusinessRemote.generadorCodigoQrCadena(solicitud.getCadenaOriginal(), 250, 250);
		parametros.put("imagenQR", imagenCodeQR);
		
		SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd 'de' yyyy, HH:mm:ss", new Locale("es", "ES"));
		String fecha = " "+formatter.format(solicitud.getFechaSolicitud())+" ";
		parametros.put("P_FECSOLCITUD", fecha);
		parametros.put("P_MOSTRAR_LEY", "1");
		log.debug("P_FECSOLCITUD "+fecha);
		parametros.put("P_FOLIO_SOLCT", solicitud.getNoFolioSolicitud());
		log.debug("P_FOLIO_SOLCT "+solicitud.getNoFolioSolicitud());
		parametros.put("P_CVE_TRAMITE", tipoTramite(tramite.getTipoTramite().getIdTipoTramite()));
		log.debug("P_CVE_TRAMITE D");
		parametros.put("P_DESCTRAMITE", tramite.getTipoTramite().getDescripcion());
		log.debug("P_DESCTRAMITE "+tramite.getTipoTramite().getDescripcion());
		parametros.put("IS_FISICA", false);
		parametros.put("IS_MORAL", false);
		String numRP = "";
		if (sujeto.getNumeroRegistroPatronal().length() == 8) {
			numRP = sujeto.getNumeroRegistroPatronal()
					+sujeto.getModalidad().getNumModalidad()
					+sujeto.getDigVerificador();
		} else{
			numRP = sujeto.getNumeroRegistroPatronal();
		}
		
		parametros.put("P_REGPATRONAL", numRP);
		log.debug("P_REGPATRONAL "+numRP);
		if(sujeto.getTipoPersonaFiscal() == TipoPersonaFiscal.FISICA){
			parametros.put("IS_FISICA", true);
			Fisica fisica = sujeto.getFisica();
			fisica  = (Fisica)sujetoObligadoService.obtenerPersonaPorIdentificador(fisica.getIdPersona());
			String nombreCompleto = ""; 
			nombreCompleto = obtenerNombreCompletoPersonaFisica(fisica);
			log.debug("P_RAZONSOCIAL ");
			parametros.put("P_NOMBRE_PERS", nombreCompleto);
			log.debug("P_NOMBRE_PERS "+nombreCompleto);
			if(aviso){
				parametros.put("P_NOMBRE_PERS", fisica.getNombre()!=null ? fisica.getNombre() : "");
				log.debug("P_NOMBRE_PERS "+fisica.getNombre());
				parametros.put("P_PATERNO_PER", fisica.getPrimerApellido()!=null ? fisica.getPrimerApellido() : "");
				log.debug("P_PATERNO_PER "+fisica.getPrimerApellido());
				parametros.put("P_MATERNO_PER", fisica.getSegundoApellido()!=null ? fisica.getSegundoApellido() : "");
				log.debug("P_MATERNO_PER "+fisica.getSegundoApellido());
			}
			parametros.put("P_RFC_PERSONA", fisica.getRfc());
			log.debug("P_RFC_PERSONA "+fisica.getRfc());
			parametros.put("P_CURPPERSONA", fisica.getCurp());
			log.debug("P_CURPPERSONA "+fisica.getCurp());
			parametros.put("P_NOMB_COMERC", sujeto.getNombreComercial());
			log.debug("P_NOMB_COMERC "+sujeto.getNombreComercial());
			
		}else{
			parametros.put("IS_MORAL", true);
			Moral moral = sujeto.getMoral();
			Long idPersonaMoral = moral.getIdPersona()!=null ?moral.getIdPersona() :moral.getCveMoral();
			moral = (Moral)sujetoObligadoService.obtenerPersonaMoralPorIdentificador(idPersonaMoral);
			parametros.put("P_RAZONSOCIAL", moral.getRazonSocial());
			log.debug("P_RAZONSOCIAL "+moral.getRazonSocial());
			parametros.put("P_NOMBRE_PERS", " ");
			log.debug("P_NOMBRE_PERS ");
			parametros.put("P_RFC_PERSONA", moral.getRfc());
			log.debug("P_RFC_PERSONA "+moral.getRfc());
			parametros.put("P_NOMB_COMERC", sujeto.getNombreComercial());
			log.debug("P_NOMB_COMERC "+sujeto.getNombreComercial());
			
		}
				
		Subdelegacion subdelegacion = sujeto.getSubdelegacion();
		
		if(subdelegacion != null){
			parametros.put("P_CVE_DELEGAC", subdelegacion.getDelegacion().getClave());
			log.debug("P_CVE_DELEGAC "+subdelegacion.getDelegacion().getClave());
			parametros.put("P_DES_DELEGAC", subdelegacion.getDelegacion().getDescripcion());
			log.debug("P_DES_DELEGAC "+subdelegacion.getDelegacion().getDescripcion());
			parametros.put("P_CVE_SUB_DEL", subdelegacion.getClave());
			log.debug("P_CVE_SUB_DEL "+subdelegacion.getClave());
			parametros.put("P_DES_SUB_DEL", subdelegacion.getDescripcion());
			log.debug("P_DES_SUB_DEL "+subdelegacion.getDescripcion());
		}else{
			parametros.put("P_CVE_DELEGAC", "");
			log.debug("P_CVE_DELEGAC");
			parametros.put("P_DES_DELEGAC", "");
			log.debug("P_DES_DELEGAC");
			parametros.put("P_CVE_SUB_DEL", "");
			log.debug("P_CVE_SUB_DEL");
			parametros.put("P_DES_SUB_DEL", "");
			log.debug("P_DES_SUB_DEL");
		}
		
		Clasificacion clasificacion = tso.getSujetoObligado().getClasificacion();
		
		if(clasificacion.getFecPresentacion() == null){
			clasificacion.setFecPresentacion(Calendar.getInstance().getTime());
		}
		if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo()) || tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO.getCodigo())){
			fecha = clasificacion.getFecPresentacion() !=null ? " "+formatter.format(clasificacion.getFecPresentacion())+" " : " ";
			log.debug("Se agregan parametros de centro de trabajo: " + tso.getSujetoObligado().getCntroTrabajo());
			CentroTrabajo cTrabajo = tso.getSujetoObligado().getCntroTrabajo();
			parametros.put("CT_CALLE", cTrabajo.getVialidadPrimaria().getNombre());
			
			String numeroExt = cTrabajo.getNumExterior1()!=null && StringUtils.isNotBlank(cTrabajo.getNumExterior1().toString()) ? cTrabajo.getNumExterior1().toString() :"";
			numeroExt +=  cTrabajo.getNumExteriorAlf()!=null && StringUtils.isNotBlank(cTrabajo.getNumExteriorAlf()) ? " "+cTrabajo.getNumExteriorAlf().toUpperCase() : "";
			
			parametros.put("CT_NUM_EXT", numeroExt);
			
			String numeroInt = cTrabajo.getNumInterior()!=null && StringUtils.isNotBlank(cTrabajo.getNumInterior().toString()) ? cTrabajo.getNumInterior().toString() :"";
			numeroInt +=  cTrabajo.getNumInteriorAlf()!=null && StringUtils.isNotBlank(cTrabajo.getNumInteriorAlf()) ? " "+cTrabajo.getNumInteriorAlf().toLowerCase() : "";
			parametros.put("CT_NUM_INT", numeroInt);
			
			String entreCalleA= cTrabajo.getVialidadReferenciaPrimaria()!=null 
					? cTrabajo.getVialidadReferenciaPrimaria().getNombre() : "";
			String entreCalleB = cTrabajo.getVialidadReferenciaSecundaria()!=null 
					? cTrabajo.getVialidadReferenciaSecundaria().getNombre() : "";
					
			parametros.put("CT_ENTRE_CALLE_A", entreCalleA);
			parametros.put("CT_ENTRE_CALLE_B", entreCalleB);
			parametros.put("CT_COLONIA", cTrabajo.getAsentamiento().getNombre());
			parametros.put("CT_LOCALIDAD", cTrabajo.getAsentamiento().getLocalidad().getNombre());
			parametros.put("CT_DELEGACION", cTrabajo.getAsentamiento().getLocalidad().getMunicipio().getNombre());
			parametros.put("CT_ENTIDAD", cTrabajo.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre());
			parametros.put("CT_CP", cTrabajo.getCodigoPostal().getCodigoPostal());
			String telefonoFijo="";
			String telefonoFijo2="";
			String correoElectronico="";
			if(cTrabajo.getMediosContacto()!=null)
				for(MedioContacto medio : cTrabajo.getMediosContacto()){
					if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TELEFONO_FIJO)){
						if(medio.getIdVista()==1)
							telefonoFijo = medio.getDesFormaContacto();
						if(medio.getIdVista()==2)
							telefonoFijo2 = medio.getDesFormaContacto();
					}else if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_CORREO_ELECTRONICO)){
						correoElectronico = medio.getDesFormaContacto();
					}
				}
			
						
			
			if(telefonoFijo2!=null && telefonoFijo2.equalsIgnoreCase("||"))
				telefonoFijo2="";
			
			
			log.debug("CT_TEL_FIJO_A: "+telefonoFijo);
			log.debug("CT_TEL_FIJO_B: "+telefonoFijo2);
			log.debug("CT_CORREO: "+correoElectronico);
			parametros.put("CT_TEL_FIJO_A", parseTelefono(telefonoFijo));
			parametros.put("CT_TEL_FIJO_B", parseTelefono(telefonoFijo2));
			parametros.put("CT_CORREO", correoElectronico);
		}else{ 
			fecha = clasificacion.getFecPresentacion() !=null ? " "+formatter.format(tso.getFechaPresentacion())+" " : " ";
		}
		
		parametros.put("P_FECPRESENTA", fecha);
		log.debug("P_FECPRESENTA "+fecha);
		
		if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo()))
			fecha = clasificacion.getFecEfecto() !=null ? " "+formatter.format(clasificacion.getFecEfecto())+" " : " ";
		else if(tso.getFechaEfecto() != null)
			fecha = tso.getFechaEfecto() != null ? " "+formatter.format(tso.getFechaEfecto())+" " : " ";
		else 
			fecha = tso.getSujetoObligado() != null && 
					tso.getSujetoObligado().getClasificacion() != null &&
					tso.getSujetoObligado().getClasificacion().getFecEfecto() != null ?
							" " + formatter.format(tso.getSujetoObligado().getClasificacion().getFecEfecto()) + " " : " ";
		
		parametros.put("P_FECH_EFECTO", fecha);
		log.debug("P_FECH_EFECTO "+fecha);
		parametros.put("P_CADENA_ORIGINAL", solicitud.getCadenaOriginal());
		parametros.put("P_SELLO_DIGITAL", solicitud.getSelloDigital());
		parametros.put("P_SEC_NOTARIAL", solicitud.getSecuenciaDeNotaria());
		parametros.put("P_NUM_SERIE", solicitud.getNumeroSerieCertificado());
		StringBuffer nombreUsuarioCompleto = new StringBuffer();
		nombreUsuarioCompleto.append(" ");
		if(usuario!=null && usuario.getNomNombre()!=null){
			nombreUsuarioCompleto.append(usuario.getNomNombre());
			if(usuario.getNomPaterno()!=null)
				nombreUsuarioCompleto.append(" "+ usuario.getNomPaterno());
			if(usuario.getNomMaterno()!=null)
				nombreUsuarioCompleto.append(" "+ usuario.getNomMaterno());
		}
		List<Documento> documentosReq = new ArrayList<Documento>();
		Documento documentoAux = new Documento();
		documentosReq.add(documentoAux);
		
		
		List<DoctoReqTramiteOrigenSol> documentosReqTra =  new ArrayList<DoctoReqTramiteOrigenSol>();
		try {
			documentosReqTra = documentoProbatorioServiceBusiness.getDocumentosRequeridosPorTipoTramiteOrigenSol(
					solicitud.getTramites().get(0).getTipoTramite().getIdTipoTramite().longValue(), OrigenSolicitudEnum.VENTANILLA.getId());
		} catch (DocumentoProbatorioException e) {
			e.printStackTrace();
		}
		
		for (DoctoReqTramiteOrigenSol doctoReqTramite : documentosReqTra) {
			documentosReq.add(doctoReqTramite.getDocumentoPorTipo().getDocumento());
		}
		parametros.put("P_DOC_REQUERIDOS", documentosReq);
		String nombreArchivo = "";
		
		nombreArchivo = "Acuse_"+solicitud.getNoFolioSolicitud()+".pdf";
		log.debug("NOMBRE REPORTE: "+nombreArchivo);
		
		
		byte[] reporte = null;
		if( tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.SUSTITUCION_PATRONAL.getCodigo())
				|| tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.FUSION.getCodigo())){
			log.debug(" TIPOTRAMITE FUSION o SUST PATRONAL :" + tramite.getTipoTramite().getIdTipoTramite());
			reporte = manejadorReportes.ejecutaAcuseVentanillaSustFusion(parametros);
		} else {
			reporte = manejadorReportes.ejecutaAcuseVentanilla(parametros);
		}
		
		
		log.debug("ENVIANDO CORREO ELECTRONICO: "+nombreArchivo);
		//afiliacionService.notificarPorCorreoElectronico(sujeto, solicitud.getSolicitudId(), TipoAccionAfectacionEnum.ATTACH_ACUSE.getValor().intValue());
		log.debug("SE FINALIZO EL ENVIO DE CORREO ELECTRONICO: "+nombreArchivo);
			
		
		return reporte;
	}
	
	private String parseTelefono(String telefono){
		if(telefono==null || (telefono !=null && telefono.equals("")))
			return "";
		
		String[] telefonoSeccion = telefono.split("\\|");
		
		StringBuffer telefonoFormateado = new StringBuffer(); 
		telefonoFormateado.append("(").append(telefonoSeccion[0]).append(")");
		telefonoFormateado.append(" ").append(telefonoSeccion[1]);
		if(telefonoSeccion.length>2)
			telefonoFormateado.append("-").append(telefonoSeccion[2]);
		
		return telefonoFormateado.toString();
	}
	
	@Override
	public byte[] obtenerCartaTerminosFiel(Solicitud solicitud)
			throws GestionPatronalBusinessException {
		
		Tramite tramite = solicitud.getTramites().get(0);
		Integer idTipoTramite = tramite.getTipoTramite().getIdTipoTramite();
		
		
		byte[] reporte = null;
		
		reporte = (byte[]) tramiteServiceEntityLocal.getDocumentoPorTipoIdTramite(tramite.getTramiteId(), DocumentoPorTipoEnum.CARTA_TERMINOS_FIEL.getId());
		
		if(reporte == null) {
			if(idTipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo())) {
				reporte = manejadorReportes.ejecutaCartaTerminosFielRepresentante(solicitud);
				if(solicitud.getSecuenciaDeNotaria() != null) {
					firmaDigitalBusinessRemote.guardarArchivoFirmado(solicitud.getSecuenciaDeNotaria(), "cartaTerminosRepresentante.pdf", reporte);
				}
			} else{
				reporte = manejadorReportes.ejecutaCartaTerminosFiel(solicitud);
				if(solicitud.getSecuenciaDeNotaria() != null) {
					firmaDigitalBusinessRemote.guardarArchivoFirmado(solicitud.getSecuenciaDeNotaria(), "cartaTerminos.pdf", reporte);
				}
			}
		}
		
		return reporte;
	}

	private byte[] generarAvisoModificacionSRT(Solicitud solicitud) throws GestionPatronalBusinessException {
		
		Usuario usuario = solicitud.getSolicitante();
		Tramite tramite = solicitud.getTramites().get(0);
		TramiteSujetoObligado tso = (TramiteSujetoObligado)tramite;
		SujetoObligado sujeto = tso.getSujetoObligado();//En el momento de la invocaciï¿½n este tramite contiene la informaciï¿½n anterior de la persona antes de impactar
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		byte[] reporte = null;
		//boolean fueFirmada = StringUtils.isNotBlank(solicitud.getCadenaOriginal());
		Boolean fueFirmada = false;
		Boolean aviso = true;
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String fecha = " "+formatter.format(solicitud.getFechaSolicitud())+" ";
		parametros.put("P_FECSOLCITUD", fecha);
		parametros.put("P_MOSTRAR_LEY", "1");
		log.debug("P_FECSOLCITUD "+fecha);
		parametros.put("P_FOLIO_SOLCT", solicitud.getNoFolioSolicitud());
		log.debug("P_FOLIO_SOLCT "+solicitud.getNoFolioSolicitud());
		parametros.put("P_CVE_TRAMITE", tipoTramite(tramite.getTipoTramite().getIdTipoTramite()));
		log.debug("P_CVE_TRAMITE D");
		parametros.put("P_DESCTRAMITE", tramite.getTipoTramite().getDescripcion());
		log.debug("P_DESCTRAMITE "+tramite.getTipoTramite().getDescripcion());
		parametros.put("IS_FISICA", false);
		parametros.put("IS_MORAL", false);
		
		
		String tipoModificacion = tramite.getTipoTramite().getDescripcion();
		String numRP = "";
		if(sujeto.getModalidad()!= null && sujeto.getDigVerificador()!=null && sujeto.getNumeroRegistroPatronal() != null && sujeto.getNumeroRegistroPatronal().length() < 11){
			numRP = sujeto.getNumeroRegistroPatronal()
			+sujeto.getModalidad().getNumModalidad()
			+sujeto.getDigVerificador();
		}else{
			numRP = sujeto.getNumeroRegistroPatronal();
		}
		parametros.put("P_REGPATRONAL", numRP);
		log.debug("P_REGPATRONAL "+numRP);
		if(sujeto.getTipoPersonaFiscal() == TipoPersonaFiscal.FISICA){
			parametros.put("IS_FISICA", true);
			Fisica fisica = sujeto.getFisica();
			fisica  = (Fisica)sujetoObligadoService.obtenerPersonaPorIdentificador(fisica.getIdPersona());
			String nombreCompleto = ""; 
			nombreCompleto = obtenerNombreCompletoPersonaFisica(fisica);
			log.debug("P_RAZONSOCIAL ");
			parametros.put("P_NOMBRE_PERS", nombreCompleto);
			log.debug("P_NOMBRE_PERS "+nombreCompleto);
			if(aviso){
				parametros.put("P_NOMBRE_PERS", fisica.getNombre()!=null ? fisica.getNombre() : "");
				log.debug("P_NOMBRE_PERS "+fisica.getNombre());
				parametros.put("P_PATERNO_PER", fisica.getPrimerApellido()!=null ? fisica.getPrimerApellido() : "");
				log.debug("P_PATERNO_PER "+fisica.getPrimerApellido());
				parametros.put("P_MATERNO_PER", fisica.getSegundoApellido()!=null ? fisica.getSegundoApellido() : "");
				log.debug("P_MATERNO_PER "+fisica.getSegundoApellido());
			}
			parametros.put("P_RFC_PERSONA", fisica.getRfc());
			log.debug("P_RFC_PERSONA "+fisica.getRfc());
			parametros.put("P_CURPPERSONA", fisica.getCurp());
			log.debug("P_CURPPERSONA "+fisica.getCurp());
			parametros.put("P_NOMB_COMERC", sujeto.getNombreComercial());
			log.debug("P_NOMB_COMERC "+sujeto.getNombreComercial());
			
		}else{
			parametros.put("IS_MORAL", true);
			Moral moral = sujeto.getMoral();
			Long idPersonaMoral = moral.getIdPersona()!=null ? moral.getIdPersona() : moral.getCveMoral();
			moral = (Moral)sujetoObligadoService.obtenerPersonaMoralPorIdentificador(idPersonaMoral);
			parametros.put("P_RAZONSOCIAL", moral.getRazonSocial());
			log.debug("P_RAZONSOCIAL "+moral.getRazonSocial());
			parametros.put("P_NOMBRE_PERS", " ");
			log.debug("P_NOMBRE_PERS ");
			parametros.put("P_RFC_PERSONA", moral.getRfc());
			log.debug("P_RFC_PERSONA "+moral.getRfc());
			parametros.put("P_NOMB_COMERC", sujeto.getNombreComercial());
			log.debug("P_NOMB_COMERC "+sujeto.getNombreComercial());
			
		}
				
		Subdelegacion subdelegacion = sujeto.getSubdelegacion();
		
		if(subdelegacion != null){
			parametros.put("P_CVE_DELEGAC", subdelegacion.getDelegacion().getClave());
			log.debug("P_CVE_DELEGAC "+subdelegacion.getDelegacion().getClave());
			parametros.put("P_DES_DELEGAC", subdelegacion.getDelegacion().getDescripcion());
			log.debug("P_DES_DELEGAC "+subdelegacion.getDelegacion().getDescripcion());
			parametros.put("P_CVE_SUB_DEL", subdelegacion.getClave());
			log.debug("P_CVE_SUB_DEL "+subdelegacion.getClave());
			parametros.put("P_DES_SUB_DEL", subdelegacion.getDescripcion());
			log.debug("P_DES_SUB_DEL "+subdelegacion.getDescripcion());
		}else{
			parametros.put("P_CVE_DELEGAC", "");
			log.debug("P_CVE_DELEGAC");
			parametros.put("P_DES_DELEGAC", "");
			log.debug("P_DES_DELEGAC");
			parametros.put("P_CVE_SUB_DEL", "");
			log.debug("P_CVE_SUB_DEL");
			parametros.put("P_DES_SUB_DEL", "");
			log.debug("P_DES_SUB_DEL");
		}
		
		Clasificacion clasificacion = tso.getSujetoObligado().getClasificacion();
		
		if(clasificacion.getFecPresentacion() == null){
			clasificacion.setFecPresentacion(Calendar.getInstance().getTime());
		}
		if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo()) || tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO.getCodigo())){
			fecha = clasificacion.getFecPresentacion() !=null ? " "+formatter.format(clasificacion.getFecPresentacion())+" " : " ";
			log.debug("Se agregan parametros de centro de trabajo: " + tso.getSujetoObligado().getCntroTrabajo());
			CentroTrabajo cTrabajo = tso.getSujetoObligado().getCntroTrabajo();
			
			if((cTrabajo == null || cTrabajo.getVialidadPrimaria() == null) && tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO.getCodigo())) {
				cTrabajo = sujetoObligadoS.getCentroTrabajo(tso.getSujetoObligado().getCveIdSujetoObligado());
				parametros.put("CT_CALLE", cTrabajo.getDescripcion());
				parametros.put("CT_NUM_EXT", "");
				parametros.put("CT_NUM_INT", "");
				parametros.put("CT_ENTRE_CALLE_A", "");
				parametros.put("CT_ENTRE_CALLE_B", "");
				parametros.put("CT_COLONIA", "");
				parametros.put("CT_LOCALIDAD", "");
				parametros.put("CT_DELEGACION", "");
				parametros.put("CT_ENTIDAD", "");
				parametros.put("CT_CP", "");
				parametros.put("CT_TEL_FIJO_A", "");
				parametros.put("CT_TEL_FIJO_B", "");
				parametros.put("CT_CORREO", "");
			} else {
				parametros.put("CT_CALLE", cTrabajo.getVialidadPrimaria().getNombre());
				

				String numeroExt = cTrabajo.getNumExterior1()!=null && StringUtils.isNotBlank(cTrabajo.getNumExterior1().toString()) ? cTrabajo.getNumExterior1().toString() :"";
				numeroExt +=  cTrabajo.getNumExteriorAlf()!=null && StringUtils.isNotBlank(cTrabajo.getNumExteriorAlf()) ? " "+cTrabajo.getNumExteriorAlf().toUpperCase() : "";
				
				parametros.put("CT_NUM_EXT", numeroExt);
				
				String numeroInt = cTrabajo.getNumInterior()!=null && StringUtils.isNotBlank(cTrabajo.getNumInterior().toString()) ? cTrabajo.getNumInterior().toString() :"";
				numeroInt +=  cTrabajo.getNumInteriorAlf()!=null && StringUtils.isNotBlank(cTrabajo.getNumInteriorAlf()) ? " "+cTrabajo.getNumInteriorAlf().toUpperCase() : "";
				parametros.put("CT_NUM_INT", numeroInt);
				
				String entreCalleA= cTrabajo.getVialidadReferenciaPrimaria()!=null 
						? cTrabajo.getVialidadReferenciaPrimaria().getNombre() : "";
				String entreCalleB = cTrabajo.getVialidadReferenciaSecundaria()!=null 
						? cTrabajo.getVialidadReferenciaSecundaria().getNombre() : "";
						
				parametros.put("CT_ENTRE_CALLE_A", entreCalleA);
				parametros.put("CT_ENTRE_CALLE_B", entreCalleB);
				parametros.put("CT_COLONIA", cTrabajo.getAsentamiento().getNombre());
				parametros.put("CT_LOCALIDAD", cTrabajo.getAsentamiento().getLocalidad().getNombre());
				parametros.put("CT_DELEGACION", cTrabajo.getAsentamiento().getLocalidad().getMunicipio().getNombre());
				parametros.put("CT_ENTIDAD", cTrabajo.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre());
				parametros.put("CT_CP", cTrabajo.getCodigoPostal().getCodigoPostal());
				String telefonoFijo="";
				String telefonoFijo2="";
				String correoElectronico="";
				if(cTrabajo.getMediosContacto()!=null)
					for(MedioContacto medio : cTrabajo.getMediosContacto()){
						if(medio.getIdVista() != null && medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TELEFONO_FIJO)){
							if(medio.getIdVista() != null && medio.getIdVista()==1)
								telefonoFijo = medio.getDesFormaContacto();
							if(medio.getIdVista() != null  && medio.getIdVista()==2)
								telefonoFijo2 = medio.getDesFormaContacto();
						}else if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_CORREO_ELECTRONICO)){
							correoElectronico = medio.getDesFormaContacto();
						}
					}
				log.debug("CT_TEL_FIJO_A: "+telefonoFijo);
				log.debug("CT_TEL_FIJO_B: "+telefonoFijo2);
				log.debug("CT_CORREO: "+correoElectronico);
				if(telefonoFijo2!=null && telefonoFijo2.equalsIgnoreCase("||"))
					telefonoFijo2="";
				
				parametros.put("CT_TEL_FIJO_A", parseTelefono(telefonoFijo));
				parametros.put("CT_TEL_FIJO_B", parseTelefono(telefonoFijo2));
				parametros.put("CT_CORREO", correoElectronico);
			}

		}else{ 
			fecha = clasificacion.getFecPresentacion() !=null ? " "+formatter.format(tso.getFechaPresentacion())+" " : " ";
		}
		
		parametros.put("P_FECPRESENTA", fecha);
		log.debug("P_FECPRESENTA "+fecha);
		
		if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo()))
			fecha = clasificacion.getFecEfecto()!=null ? " "+formatter.format(clasificacion.getFecEfecto())+" " : " ";
		else
			fecha = clasificacion.getFecEfecto()!=null ? " "+formatter.format(clasificacion.getFecEfecto())+" " : " ";
		
		parametros.put("P_FECH_EFECTO", fecha);
		log.debug("P_FECH_EFECTO "+fecha);
		StringBuffer nombreUsuarioCompleto = new StringBuffer();
		nombreUsuarioCompleto.append(" ");
		if(usuario!=null && usuario.getNomNombre()!=null){
			nombreUsuarioCompleto.append(usuario.getNomNombre());
			if(usuario.getNomPaterno()!=null)
				nombreUsuarioCompleto.append(" "+ usuario.getNomPaterno());
			if(usuario.getNomMaterno()!=null)
				nombreUsuarioCompleto.append(" "+ usuario.getNomMaterno());
		}
		String nombreArchivo = "";
		Fraccion fracc = clasificacion.getFraccion();
		parametros.put("P_NUM_FRACCIO", fracc.getNumFraccion());
		log.debug("P_NUM_FRACCIO "+fracc.getNumFraccion());
		Grupo grupo = fracc.getGrupo();
		parametros.put("P_NUMER_GRUPO", grupo.getNumGrupo());
		log.debug("P_NUMER_GRUPO "+grupo.getNumGrupo());
		Division division = grupo.getDivision();
		parametros.put("P_NUM_DIVISIO", division.getNumDivision());
		log.debug("P_NUM_DIVISIO "+division.getNumDivision());
		parametros.put("P_CLASE_CLASI", fracc.getClase().getDescripcion());
		log.debug("P_CLASE_CLASI "+fracc.getClase().getDescripcion());
		parametros.put("P_PRIMA_CLASI", clasificacion.getPrimaSRTActual());
		log.debug("P_USUARIO "+ usuario.getUsuario());
		parametros.put("P_USUARIO", usuario.getUsuario());
		log.debug("P_TIPO_MODIFICACION "+tipoModificacion);
		parametros.put("P_TIPO_MODIFICACION", tipoModificacion);
		
		nombreArchivo = "AvisoModificacion_"+solicitud.getNoFolioSolicitud()+".pdf";
		
		//respaldamos los sujetos obligados de fusion
		List<SujetoObligado> sujetosSustitucionFusion = sujeto.getSujetosObligados();
		log.error("respaldo los sujetos obligados de fusion y sustitucion, el patron trae sujetos ? " + (sujetosSustitucionFusion != null && !sujetosSustitucionFusion.isEmpty()));
		log.debug("NOMBRE REPORTE: "+nombreArchivo);
		sujeto.setNumeroRegistroPatronal(sujeto.getNumeroRegistroPatronal()
				+sujeto.getModalidad().getNumModalidad()+sujeto.getDigVerificador());
		sujeto=sujetoObligadoService.obtenerDetalleSujetoObligadoActividadEconomica(sujeto);
		sujeto.setSujetosObligados(sujetosSustitucionFusion);
		System.err.println("Finalizo la consulta de sujetoObligadoActividadEconomica: "+sujeto);
		List<SujetoObligado> sujetos = new ArrayList<SujetoObligado>();
		sujetos.add(sujeto);
		
		System.err.println("FUE FIRMADA----->: "+ fueFirmada);
		if(fueFirmada){
			parametros.put("P_CAD_ORIG", solicitud.getCadenaOriginal());
			parametros.put("P_SELLO", solicitud.getSelloDigital());
			parametros.put("P_SEC_NOT", solicitud.getSecuenciaDeNotaria());
			parametros.put("P_NUM_SERIE", solicitud.getNumeroSerieCertificado());
			System.err.println("Se agregaron datos de firma a reporte");
			System.err.println("P_CAD_ORIG: "+solicitud.getCadenaOriginal());
			System.err.println("P_SELLO: "+solicitud.getSelloDigital());
			System.err.println("P_SEC_NOT: "+solicitud.getSecuenciaDeNotaria());
			System.err.println("P_NUM_SERIE: "+solicitud.getNumeroSerieCertificado());
		}
		System.err.println(" ejecutaAvisoDeModificacionMovPat----->: "+ fueFirmada);
		
		if(solicitud.getOrigenSolicitud() != null && 
				solicitud.getOrigenSolicitud().getIdOrigenSolicitud().intValue() ==
						OrigenSolicitudEnum.VENTANILLA.getId().intValue()) {
			 reporte = manejadorReportes.ejecutaAvisoDeModificacionMovPat(parametros, sujetos);
		}else {
			 reporte = manejadorReportes.ejecutaAvisoDeModificacion(parametros, sujetos);
		}
		
		
		return reporte;
	}
	
	@Override
	public SujetoObligado concluirSolicitudModificacionSRT(SujetoObligado so, Solicitud solicitud) throws GestionPatronalBusinessException {
		return this.concluirSolicitudModificacionSRT(so, solicitud, null);
	}
	
	private SujetoObligado concluirSolicitudModificacionSRT(SujetoObligado so, Solicitud solicitud, SujetoObligado soActual) throws GestionPatronalBusinessException {
		TramiteSujetoObligado tso = (TramiteSujetoObligado)solicitud.getTramites().get(0);
		//pbtenemos el tipo de tramite que se ejecuta
		Integer tipoTramite = tso.getTipoTramite().getIdTipoTramite();
		//causs que se enviara a SINDO
		Long cveCausa = null;
		//El movimiento 06 es para todos los tramites de modificacion en el SRT excepto reanudacion de actividades
		Integer tipoMovimiento = 06; 
		//Valor por default para los movimientos a SINDO para servicios digitales para movPat se envia el origen 0
		Integer origenMovimiento = 06;
		if(solicitud.getSolicitante() != null && solicitud.getSolicitante().getCorreoConfirmacion() != null){
			log.debug("el solicitante no es nulo y tiene valor en el coampo de confirmacionCorreo como " + solicitud.getSolicitante().getCorreoConfirmacion() );			
			if(solicitud.getSolicitante().getCorreoConfirmacion().equals(SindoAplicacionEnum.MOV_PAT.getCodigo()+"")) {
					origenMovimiento =SindoAplicacionEnum.MOV_PAT.getCodigo();
					log.debug("se seteo el origen del movimiento de movPat como " + origenMovimiento);
			}
					
		}

		//verificamos si el tramite es de alta patronal
		boolean esAltaPatronal = tipoTramite.equals(TipoTramiteEnum.ALTA_SRT.getCodigo()) || tipoTramite.equals(TipoTramiteEnum.ALTA_SRT_PM.getCodigo());

		//Si el tipo de movimiento es reanudacion de actividades, el tipo de movimiento que se enviara a SINDO es 03
		/*if(tipoTramite.equals(TipoTramiteEnum.REANUDACION_DE_ACTIVIDADES.getCodigo())) {
			origenMovimiento= 03;
		}*/
		
		//obtenemos la causa que se enviara a SINDO
		cveCausa = esAltaPatronal ? 0L : clasificacionServiceEntity.obtenerCausaPorTipoTramite(tipoTramite.longValue(), 1L);
		
		log.debug("La causa que se enviara a SINDO para el tramite " + tso.getTramiteId() + " es " + cveCausa + " el tipoMovimiento es " + tipoMovimiento +
				" y el origen movimiento es " + origenMovimiento);
		
		//Obtenemos el patron actual si no lo pasaron como parametro
		SujetoObligado sujetoObligadoActual = soActual == null ? sujetoObligadoService.obtenerDetalleSujetoObligadoActividadEconomica(so) : soActual;
		//Establecemos las fechas de efecto y presentacion
		sujetoObligadoActual.getClasificacion().setFecEfecto(so.getClasificacion().getFecEfecto());
		sujetoObligadoActual.getClasificacion().setFecPresentacion(so.getClasificacion().getFecPresentacion());
		//Solo si el tramite no es alta patronal estableceramos la nueva prima
		if(!esAltaPatronal){
			//cambiamos la prima por la que capturo el usuario en caso de ser fusion o sustitucion
			if (tipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL.getCodigo())
					|| tipoTramite.equals(TipoTramiteEnum.FUSION.getCodigo())
					|| tipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL_SUBCONTRATACION.getCodigo())
					|| tipoTramite.equals(TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO.getCodigo())
			) {
				//Establecemos la prima capturada en pantalla ya que es responsabilidad del usuario capturar la nueva prima
				so.getClasificacion().setPrimaSRTActual(so.getClasificacion().getPrimaSRTFusionSust());
								
				//seteamos el centro de trabajo e indicador de baja antes de terminar el tramite
				log.debug(":::::::::::: Seteamos el centro de trabajo e indicador de baja antes de terminar el tramite");
				if(tipoTramite.equals(TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO.getCodigo())
						&& so.getSujetosObligados() != null) {
					log.debug("::::::::::::Total de registros patronales con domicilio anterior: " + so.getSujetosObligados().size());
					log.debug("::::::::::::Tipo tramite: " + tipoTramite);
					for(SujetoObligado sujeto : so.getSujetosObligados() ) {
						log.debug("::::: Registro de domicilio anterior: " + sujeto.getNumeroRegistroPatronal() + " - " + sujeto.getCveIdSujetoObligado());
						String indicadorBaja = sujeto.getDescSituacionBaja() != null ? "2": "0";
						sujeto.getClasificacion().setIndBaja(indicadorBaja);						
						CentroTrabajo cntroTrabajo = sujObligService.getCentroTrabajo(sujeto.getCveIdSujetoObligado());
						sujeto.setCntroTrabajo(cntroTrabajo);						
					}
				}else{
					log.debug(":::::::::::: El tramite no tiene lista de patrones con domicilio anterior");
				}
				
				sujetoObligadoActual.setSujetosObligados(so.getSujetosObligados());
				
				//Insertamos los patrones fusionados/sustituidos
				if (tipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL.getCodigo())
						|| tipoTramite.equals(TipoTramiteEnum.FUSION.getCodigo())
						|| tipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL_SUBCONTRATACION.getCodigo())
				) {
					patronSustitucionFusionBusinessLocal.insertarPatronFusionado(so);
				}
			}else {
				//Si el tramite es de reanudacion de actividades quitaremos la baja
				if(tipoTramite.equals(TipoTramiteEnum.REANUDACION_DE_ACTIVIDADES.getCodigo())) {
					datosExtraPatronBusinessLocal.ejecutarReanudacionActividades(so.getCveIdSujetoObligado());
				}
				//Si el tramite pertenece al grupo de tramites de clasificacion ahora aplicamos la prima sugerida
				if( tipoTramite.equals(TipoTramiteEnum.ACTIVIDAD_ECONOMICA.getCodigo()) ||
					tipoTramite.equals(TipoTramiteEnum.DISPOSICION_DE_LEY.getCodigo()) ||
					tipoTramite.equals(TipoTramiteEnum.INCORPORACION_DE_ACTIVIDADES.getCodigo()) ||
					tipoTramite.equals(TipoTramiteEnum.COMPRA_DE_ACTIVOS.getCodigo()) ||
					tipoTramite.equals(TipoTramiteEnum.COMODATO.getCodigo()) ||
					tipoTramite.equals(TipoTramiteEnum.ENAJENACION.getCodigo()) ||
					tipoTramite.equals(TipoTramiteEnum.ARRENDAMIENTO.getCodigo()) ||
					tipoTramite.equals(TipoTramiteEnum.FIDEICOMISO_TRASLATIVO.getCodigo()) ||
					tipoTramite.equals(TipoTramiteEnum.ESCISION.getCodigo()) ||
					tipoTramite.equals(TipoTramiteEnum.REANUDACION_DE_ACTIVIDADES.getCodigo())
				){
					so.getClasificacion().setPrimaSRTActual(so.getClasificacion().getPrimaSRTSugerida());					
				}
			}
			
			String acotacion=evaluaPrima(sujetoObligadoActual.getClasificacion().getId(), so.getClasificacion(), cveCausa);
			log.error("Acotacion: "+acotacion);
			solicitud.setObservacion(acotacion);
			
			if(solicitud.getTipoSolicitud().getIdTipoSolicitud().equals(TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO.getValor().longValue())){
				log.debug("No se actualiza la clasificaciï¿½n en la solicitud por ser tramite de centro de trabajo");
				sujetoObligadoActual.setCntroTrabajo(tso.getSujetoObligado().getCntroTrabajo());//Se conserva la info del centro trabajo en el trï¿½mite
			}
			
			log.debug("::: Antes de actualizar solicitud al finalizar");
			// Guardamos los valores para indicar si el patron modifico su prima y si se encontraron solicitudes similares
			if( tipoTramite.equals(TipoTramiteEnum.ACTIVIDAD_ECONOMICA.getCodigo()) ||
				tipoTramite.equals(TipoTramiteEnum.DISPOSICION_DE_LEY.getCodigo()) ||
				tipoTramite.equals(TipoTramiteEnum.INCORPORACION_DE_ACTIVIDADES.getCodigo()) ||
				tipoTramite.equals(TipoTramiteEnum.COMPRA_DE_ACTIVOS.getCodigo()) ||
				tipoTramite.equals(TipoTramiteEnum.COMODATO.getCodigo()) ||
				tipoTramite.equals(TipoTramiteEnum.ENAJENACION.getCodigo()) ||
				tipoTramite.equals(TipoTramiteEnum.ARRENDAMIENTO.getCodigo()) ||
				tipoTramite.equals(TipoTramiteEnum.FIDEICOMISO_TRASLATIVO.getCodigo()) ||
				tipoTramite.equals(TipoTramiteEnum.ESCISION.getCodigo()) ||
				tipoTramite.equals(TipoTramiteEnum.REANUDACION_DE_ACTIVIDADES.getCodigo()) ||
				tipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL.getCodigo()) ||
				tipoTramite.equals(TipoTramiteEnum.FUSION.getCodigo()) ||
				tipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL_SUBCONTRATACION.getCodigo()) ||	
				tipoTramite.equals(TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO.getCodigo())
			){
				log.debug("::: Agregando prima sugerida por patron y solicitudes similares");
				log.debug("::: sujetoObligadoActual.getClasificacion().getIndPrimaSugerida(): " + sujetoObligadoActual.getClasificacion().getIndPrimaSugerida());
				log.debug("::: sujetoObligadoActual.getClasificacion().getIndSolSimilares(): " + sujetoObligadoActual.getClasificacion().getIndSolSimilares());

				log.debug("::: so.getClasificacion().getIndPrimaSugerida(): " + so.getClasificacion().getIndPrimaSugerida());
				log.debug("::: so.getClasificacion().getIndSolSimilares(): " + so.getClasificacion().getIndSolSimilares());
				
				if(so.getClasificacion().getIndPrimaSugerida() != null) {
					sujetoObligadoActual.getClasificacion().setIndPrimaSugerida(so.getClasificacion().getIndPrimaSugerida());
				}
				if(so.getClasificacion().getIndSolSimilares() != null) {
					sujetoObligadoActual.getClasificacion().setIndSolSimilares(so.getClasificacion().getIndSolSimilares());
					//Guardamos mensaje en buzon de Clasificacion
					String msj = "La solicitud " + so.getClasificacion().getIndSolSimilares()
							+ " se ha marcado como improcedente por la existencia de un nuevo tr&aacute;mite con folio "
							+ solicitud.getNoFolioSolicitud() + ".";
					this.insertaMensajeBuzon(sujetoObligadoActual.getNumeroRegistroPatronal(), tipoTramite.toString(),
							solicitud.getNoFolioSolicitud(), msj);
				}
			}		
			
			//Ponemos la solicitud como atendida y el tramite como cerrado
			solicitudServiceBusiness.actualizarSolicitud(solicitud, EstadoTramiteEnum.CERRADO, sujetoObligadoActual);
			//Usamos el nuevo metodo que no consulta tantas veces la solicitud, solo la consulta para actualizar
		//	solicitudServiceBusiness.actualizarSolicitudCerrada(solicitud, sujetoObligadoActual);

		}
		
		//Verificamos si es patron por clase
		if(sujetoObligadoActual.getClasificacion().getIndRegPatClase()!=null && sujetoObligadoActual.getClasificacion().getIndRegPatClase().intValue()==1) {
			so.getClasificacion().setIndRegPatClase(sujetoObligadoActual.getClasificacion().getIndRegPatClase());
		}else{
			so.getClasificacion().setIndRegPatClase(0);
		}
		
		//Para sindo se toma la subdelegacion asociada al centro de trabajo, se agrega este dato al sujetoObligado
		if(!esAltaPatronal){
			so.setSubdelegacion(sujetoObligadoActual.getSubdelegacion());
		}
		//guardamos la nueva clasificacion del patron
		clasificacionServiceEntity.guardarNuevaClasificacionActividadEconomica(so, cveCausa);
		//Generamos el folio de SINDO
		String noFolioSindo = buildNumeroFolio(so);
		//Enviamos el movimiento 06 o 03 a SINDO para notificar la modificacion en el SRT
		if(!esAltaPatronal) {
			if(tipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL.getCodigo()) 
					|| tipoTramite.equals(TipoTramiteEnum.FUSION.getCodigo())
					|| tipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL_SUBCONTRATACION.getCodigo())
					|| tipoTramite.equals(TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO.getCodigo())
			) {
				//se setea correctamente la prima de donde la toma SINDO
				so.getClasificacion().getFraccion().setPrimaSRT(so.getClasificacion().getPrimaSRTFusionSust());
			}else if( tipoTramite.equals(TipoTramiteEnum.ACTIVIDAD_ECONOMICA.getCodigo()) ||
					tipoTramite.equals(TipoTramiteEnum.DISPOSICION_DE_LEY.getCodigo()) ||
					tipoTramite.equals(TipoTramiteEnum.INCORPORACION_DE_ACTIVIDADES.getCodigo()) ||
					tipoTramite.equals(TipoTramiteEnum.COMPRA_DE_ACTIVOS.getCodigo()) ||
					tipoTramite.equals(TipoTramiteEnum.COMODATO.getCodigo()) ||
					tipoTramite.equals(TipoTramiteEnum.ENAJENACION.getCodigo()) ||
					tipoTramite.equals(TipoTramiteEnum.ARRENDAMIENTO.getCodigo()) ||
					tipoTramite.equals(TipoTramiteEnum.FIDEICOMISO_TRASLATIVO.getCodigo()) ||
					tipoTramite.equals(TipoTramiteEnum.ESCISION.getCodigo()) ||
					tipoTramite.equals(TipoTramiteEnum.REANUDACION_DE_ACTIVIDADES.getCodigo())
			){
				so.getClasificacion().getFraccion().setPrimaSRT(so.getClasificacion().getPrimaSRTSugerida());
			}
			log.error("El numero de folio que se envia para sindo del tramite " + tso.getTramiteId() + " es " + noFolioSindo);
			ejecutarProcesoSincronizacionSINDO(noFolioSindo, so, tso.getSujetoObligado().getClasificacion().getFecEfecto(), cveCausa, 1, tipoMovimiento, origenMovimiento);
		}

		return so;
	}
	
	private String evaluaPrima(Long idClasificacionActual, Clasificacion nuevaClasificacion, Long cveCausa)throws GestionPatronalBusinessException{
		Clasificacion clasificacionActual = clasificacionServiceEntity.obtenerClasificacionPorId(idClasificacionActual);
		Fraccion nuevaFraccion = obtenerFraccionClaseActiva(nuevaClasificacion.getFraccion().getId());
		String acotacion="";
		log.error("Evaluando prima");
		BigDecimal numPrimaPago = null;
		BigDecimal primaActual = clasificacionActual.getPrimaSRTActual();
		BigDecimal nuevaPrimaMedia = nuevaFraccion.getPrimaSRT();
		Long idFraccionActual=clasificacionActual.getFraccion().getId();
		Long idFraccionNueva=nuevaFraccion.getId();
		
		Long idClaseActual = clasificacionActual.getFraccion().getClase().getClave();
		Long idClaseNuevaFraccion = nuevaFraccion.getClase().getClave();
		
		if(cveCausa.intValue() == TipoCausaAnalisisEnum.FUSION.getClave() || cveCausa.intValue() == TipoCausaAnalisisEnum.SUSTITUCION.getClave()
				|| cveCausa.intValue() == TipoCausaAnalisisEnum.SUSTITUCION_SUBCONTRATACION.getClave()){
			
			acotacion="Sustitucion o fusion, se asigna la prima media capturada: "+nuevaPrimaMedia;
			numPrimaPago = nuevaClasificacion.getPrimaSRTActual();
		}else if(cveCausa.intValue() != TipoCausaAnalisisEnum.CAMBIO_POR_DISPOSICION_DE_LEY_O_DEL_RACERF.getClave()){
			if(idFraccionActual.equals(idFraccionNueva)){
				log.error("Misma fracci?n, se conserva la prima: "+primaActual);
				acotacion="Misma fracci?n, se conserva la prima: "+primaActual;
				numPrimaPago = primaActual;
			}else {//Distinta Fracci?n
				log.error("Distinta fraccion");
				if(idClaseActual.equals(idClaseNuevaFraccion)){ //Misma clase conserva la prima
					log.error("Misma clase, se conserva la prima anterior: "+primaActual);
					acotacion="Cambio de fracci?n, conserva la misma clase, se conserva la prima anterior: "+primaActual;
					numPrimaPago = primaActual;
				}else{
					log.error("Distinta clase, se asigna la prima media de la nueva clase: "+nuevaPrimaMedia);
					acotacion="Cambio de fracci?n y clase, se asigna la prima media de la nueva clase: "+nuevaPrimaMedia;
					numPrimaPago = nuevaPrimaMedia;
				}
				
			}
			System.err.println("Se actualiza la prima por no ser cambio por disposicion de ley");
		}else{
			log.error("Cambio por disposici?n de ley, se conserva la prima: "+primaActual);
			acotacion="Cambio por disposici?n de ley, se conserva la prima: "+primaActual;
			numPrimaPago = primaActual;//Se conserva la prima anterior en cambio por disposici?n de ley
		}
		
		if(numPrimaPago.longValue() < primaActual.longValue()){
			log.error("Se marcar? registro para ser verificado en MAC");
			acotacion="IMPORTANTE: La prima asignada: [ "+ numPrimaPago +" ] es menor a la prima previa: [ "+primaActual+" ]";
		}
	
		return acotacion;
	}
	
	@Override
	public SujetoObligado afectarTramiteModificacionPatronal(SujetoObligado so, Tramite tramite) {
		//Obtenemos el tramite
		TramiteSujetoObligado tso = (TramiteSujetoObligado)tramite;
		Long cveCausa = null;
		//El movimiento 06 es para todos los tramites de modificacion en el SRT exceptio reanudacion
		Integer tipoMovimiento = 06; 
		Integer origenMovimiento = 06;
		//Obtenemos el tipo del tramite
		Integer tipoTramite = tso.getTipoTramite().getIdTipoTramite();
		
		//Si el tipo de movimiento es reandacion de actividades
		/*if(tipoTramite.equals(TipoTramiteEnum.REANUDACION_DE_ACTIVIDADES.getCodigo())) {
			tipoMovimiento = 03;
			origenMovimiento= 03;
		}*/
		//De acuerdo al tipo de movimiento obtenemos la causa que se enviara a SINDO
		if(tipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo()))
			cveCausa = 42L;//TODO CORREGIR
		else if(tipoTramite.equals(TipoTramiteEnum.ALTA_SRT.getCodigo()) || tipoTramite.equals(TipoTramiteEnum.ALTA_SRT_PM.getCodigo()))
			cveCausa = 0L;//TODO CORREGIR SE IMPLEMENTO PARA EVITAR EL FALLO AL GUARDAR LA NUEVA CLASIFICACION
		else
			cveCausa = clasificacionServiceEntity.obtenerCausaPorTipoTramite(tso.getTipoTramite().getIdTipoTramite().longValue(), 1L);
		
		//obtenemos los datos actuales del patron
		SujetoObligado sujetoObligadoActual = sujetoObligadoService.obtenerDetalleSujetoObligadoActividadEconomica(so);
		log.debug("Se almacena la clasificaciï¿½n anterior en el trï¿½mite [" + sujetoObligadoActual + "]");
		//Establecemos las fecha de efecto y presentacion
		sujetoObligadoActual.getClasificacion().setFecEfecto(so.getClasificacion().getFecEfecto());
		sujetoObligadoActual.getClasificacion().setFecPresentacion(so.getClasificacion().getFecPresentacion());

		//Establecemos si el patron es por clase
		if(sujetoObligadoActual.getClasificacion().getIndRegPatClase() != null && sujetoObligadoActual.getClasificacion().getIndRegPatClase().intValue() == 1) {
			so.getClasificacion().setIndRegPatClase(sujetoObligadoActual.getClasificacion().getIndRegPatClase());			
		}else{
			so.getClasificacion().setIndRegPatClase(0);
		}
		
		System.err.println("Clasificacion a asignar: Fraccion ["+so.getClasificacion().getFraccion().getId()+"] Grupo ["+so.getClasificacion().getFraccion().getGrupo()+"]");
		
		//Para sindo se toma la subdelegacion asociada al centro de trabajo, se agrega este dato al sujetoObligado
		if(!tso.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ALTA_SRT.getCodigo())){
			so.setSubdelegacion(sujetoObligadoActual.getSubdelegacion());
		}
		//guardamos la nueva clasificacion
		clasificacionServiceEntity.guardarNuevaClasificacionActividadEconomica(so, cveCausa);
		//Enviamos el movimiento a SINDO
		String noFolioSindo = buildNumeroFolio(so);
		if(!tipoTramite.equals(TipoTramiteEnum.ALTA_SRT.getCodigo()) && !tipoTramite.equals(TipoTramiteEnum.ALTA_SRT_PM.getCodigo())){
			ejecutarProcesoSincronizacionSINDO(noFolioSindo,so, tso.getSujetoObligado().getClasificacion().getFecEfecto(),cveCausa,1,tipoMovimiento,origenMovimiento);
		}

		return so;

	}

    private String buildNumeroFolio(SujetoObligado so) {
        StringBuilder bufFolio = new StringBuilder();
        Formatter fmtr = new Formatter();
        fmtr.format("%03d", Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
        String diaJuliano = fmtr.toString();
        String clave = so.getSubdelegacion().getClave();
        log.info(String.format("Dia juliano : %s folio previo: %s%s", diaJuliano, clave, diaJuliano));
        bufFolio.append(clave).append("411");
        fmtr.close();
        return bufFolio.toString();
    }
	
	@Override
    public Fraccion obtenerFraccionPornumFraccionCompleta(String fraccionCompleta)throws GestionPatronalBusinessException{
		return clasificacionServiceEntity.consultarFraccionPorFraccionCompleta(fraccionCompleta);
    }
	
	@Override
	public AdjuntosClasificacion guardarArchivoAdjunto(AdjuntosClasificacion adjuntosClasificacion) throws Exception {
		try {
			clasificacionServiceEntity.guardarArchivoAdjunto(adjuntosClasificacion);			
		} catch (Exception e) {
			log.error("::: Error al intentar guardar registro de archivo adjunto - folio: "
					+ adjuntosClasificacion.getRefFolio()
					+ ", archivo: "
					+ adjuntosClasificacion.getNombreArchivo()
					+ ", ruta: "
					+ adjuntosClasificacion.getRutaArchivo()
					+ ", tipoTramite: "
					+ adjuntosClasificacion.getRutaArchivo());
			e.printStackTrace();
			throw e;
		}
		return adjuntosClasificacion;
	}

	@Override
	public void quitarArchivoAdjunto(String folio, String nombreArchivo) throws Exception {
		try {
			clasificacionServiceEntity.quitarArchivoAdjunto(folio, nombreArchivo);			
		} catch (Exception e) {
			log.error("::: Error al intentar quitar registro de archivo adjunto - folio: "
					+ folio + ", archivo: " + nombreArchivo);
			e.printStackTrace();
			throw e;
		}						
	}

	@Override
	public List<AdjuntosClasificacion> consultarArchivoAdjunto(String folio) throws Exception {
		try {
			return clasificacionServiceEntity.consultarArchivoAdjunto(folio);			
		} catch (Exception e) {
			log.error("::: Error al consultar archivos adjuntos - folio: " + folio);
			e.printStackTrace();
			throw e;
		}	
	}

	@Override
	public String buscaSolicitudesSimilares(String nrp, String cveIdTipoTramite, String fechaSurteEfecto){
		String resp = "";
		List<String> respL;
		try {
			respL = clasificacionServiceEntityLocal.buscaSolicitudesSimilares(nrp, cveIdTipoTramite, fechaSurteEfecto);
			for (Iterator<String> iterator = respL.iterator(); iterator.hasNext();) {
				resp += iterator.next() + ",";
			}
			if(resp != null && resp.length() > 1) {
				log.debug("::: Solicitudes similares encontradas: " + resp + ", cantidad: " + respL.size() + ", nrp: " + nrp
						+ ", cveIdTipoTramite: " + cveIdTipoTramite + ". fechaSurteEfecto: " + fechaSurteEfecto);
				resp = resp.substring(0, resp.length()-1);
			}else {
				log.debug("::: No se encontraron solicitudes similares, nrp: " + nrp
						+ ", cveIdTipoTramite: " + cveIdTipoTramite + ". fechaSurteEfecto: " + fechaSurteEfecto);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		return resp;
	}
	
	@Override
	public void insertaMensajeBuzon(String nrp, String cveIdTipoTramite, String folio, String mensaje){
		log.debug("::: Insertando mensaje en buzon de clasificacion");
		log.debug("::: nrp: " + nrp + ", cveIdTipoTramite: " + cveIdTipoTramite 
				+ ",folio: " + folio + ", mensaje: " + mensaje);
		try {
			clasificacionServiceEntityLocal.insertaMensajeBuzon(nrp, cveIdTipoTramite, folio, mensaje);
		}catch(Exception e) {
			log.debug("Error al insertar mensaje en buzon de clasificacion: "+ e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public BuzonClasificacion consultaMensajeBuzon(String nrp) {
		log.debug("::: Buscando mensaje en buzon de clasificacion, nrp: " + nrp);
		try {
			return clasificacionServiceEntityLocal.consultaMensajeBuzon(nrp);
		}catch(Exception e) {
			log.debug("Error al consultar mensaje en buzon de clasificacion: "+ e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public byte[] obtenerDoctosResultantesModificacionSRTVentanilla(Solicitud solicitud) throws GestionPatronalBusinessException {
		String secuenciaNotaria = null;
		Tramite tramite = solicitud.getTramites().get(0);
		List<ByteArrayOutputStream> listByteArray = new ArrayList<ByteArrayOutputStream>();
		ByteArrayOutputStream stream = null;
		if(solicitud.getCadenaOriginal() != null) {
			RespuestaFirmadoSimple selloDigital = firmaDigitalBusinessRemote.getSelloDigital(solicitud.getCadenaOriginal(), null, null);
			secuenciaNotaria = solicitud.getSecuenciaDeNotaria();
			if(selloDigital != null) {
				solicitud.setSelloDigital(selloDigital.getSello());
				solicitud.setSecuenciaDeNotaria(selloDigital.getTramite());
				solicitud.setNumeroSerieCertificado(selloDigital.getNoSerie());
			}
		}
		
		byte[] documentos=null;
		byte[] documentoAcuse=null;
		byte[] documentoAMSRT=null;
		byte[] documentoTIP=null;
		documentoAcuse=generarAcuseModificacionSRTVentanilla(solicitud);
		stream = new ByteArrayOutputStream(documentoAcuse.length);
		stream.write(documentoAcuse, 0, documentoAcuse.length);
		listByteArray.add( stream );
		
		documentoAMSRT =generarAvisoModificacionSRT(solicitud);
		stream = new ByteArrayOutputStream(documentoAMSRT.length);
		stream.write(documentoAMSRT, 0, documentoAMSRT.length);
		listByteArray.add( stream );
		
		try {
			documentoTIP = (byte[]) arpBusinessRemote.getReporteTipPersona(solicitud, tramite);
			stream = new ByteArrayOutputStream(documentoTIP.length);
			stream.write(documentoTIP, 0, documentoTIP.length);
			listByteArray.add( stream );
		} catch (Exception e) {
			log.error("ocurrio un error al generar el TIP ", e);
			e.printStackTrace();
		}
		try {
			documentos = manejadorReportes.concatenaPDFs(listByteArray, true).toByteArray();
		}catch (Exception e) {
			log.error("ocurrio un error al concatenar los documentos ", e);
			throw new GestionPatronalBusinessException("Error al concatenar los documentos resultantes " + e.getMessage());
		}
		log.debug("secuencia de notaria del tramite: " + secuenciaNotaria);
		log.debug("secuencia de notaria del sello: " + solicitud.getSecuenciaDeNotaria());
		if(solicitud.getSecuenciaDeNotaria() != null) {
				firmaDigitalBusinessRemote.guardarArchivoFirmado(secuenciaNotaria, "avisoModificacionSRT.pdf", documentos);
				log.debug("pase el guardado de la firma");
		}
		
		return documentos;
	}
	
	@Override
	public byte[] generarComprobanteCitaInternet(Solicitud solicitud) throws GestionPatronalBusinessException{
		
		Usuario usuario = solicitud.getSolicitante();
		Tramite tramite = solicitud.getTramites().get(0);
		TramiteSujetoObligado tso = (TramiteSujetoObligado)tramite;
		SujetoObligado sujeto = tso.getSujetoObligado();//En el momento de la invocaciï¿½n este tramite contiene la informaciï¿½n anterior de la persona antes de impactar
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd 'de' MMMM 'del año' yyyy", new Locale("es", "ES"));
		String fecha = " "+formatter.format(new Date())+" ";
		String fechaCita = " "+formatter.format(solicitud.getCitaSolicitud().getFechaHora())+" ";
		parametros.put("P_FECPRESENTA", fecha);
		log.debug("P_FECPRESENTA "+fecha);
		parametros.put("P_FECSOLCITUD", fecha);
		log.debug("P_FECSOLCITUD "+fecha);
		parametros.put("P_FECCITA", fechaCita);
		log.debug("P_FECCITA "+fechaCita);
		parametros.put("P_FOLIO_SOLCT", solicitud.getNoFolioSolicitud());
		log.debug("P_FOLIO_SOLCT "+solicitud.getNoFolioSolicitud());
		parametros.put("P_DESCTRAMITE", tramite.getTipoTramite().getDescripcion());
		log.debug("P_DESCTRAMITE "+tramite.getTipoTramite().getDescripcion());
		parametros.put("IS_FISICA", false);
		parametros.put("IS_MORAL", false);

		if(sujeto.getTipoPersonaFiscal() == TipoPersonaFiscal.FISICA){
			parametros.put("IS_FISICA", true);
			Fisica fisica = sujeto.getFisica();
			fisica  = (Fisica)sujetoObligadoService.obtenerPersonaPorIdentificador(fisica.getIdPersona());
			String nombreCompleto = ""; 
			nombreCompleto = obtenerNombreCompletoPersonaFisica(fisica);
			log.debug("P_RAZONSOCIAL ");
			parametros.put("P_NOMBRE_PERS", nombreCompleto);
			log.debug("P_NOMBRE_PERS "+nombreCompleto);
			parametros.put("P_RFC_PERSONA", fisica.getRfc());
			log.debug("P_RFC_PERSONA "+fisica.getRfc());
			parametros.put("P_CURPPERSONA", fisica.getCurp());
			log.debug("P_CURPPERSONA "+fisica.getCurp());
			parametros.put("P_NOMB_COMERC", sujeto.getNombreComercial());
			log.debug("P_NOMB_COMERC "+sujeto.getNombreComercial());
		}else{
			parametros.put("IS_MORAL", true);
			Moral moral = sujeto.getMoral();
			Long idPersonaMoral = moral.getIdPersona()!=null ?moral.getIdPersona() :moral.getCveMoral();
			moral = (Moral)sujetoObligadoService.obtenerPersonaMoralPorIdentificador(idPersonaMoral);
			parametros.put("P_RAZONSOCIAL", moral.getRazonSocial());
			log.debug("P_RAZONSOCIAL "+moral.getRazonSocial());
			parametros.put("P_NOMBRE_PERS", " ");
			log.debug("P_NOMBRE_PERS ");
			parametros.put("P_RFC_PERSONA", moral.getRfc());
			log.debug("P_RFC_PERSONA "+moral.getRfc());
			parametros.put("P_NOMB_COMERC", sujeto.getNombreComercial());
			log.debug("P_NOMB_COMERC "+sujeto.getNombreComercial());
			
		}
				
		Subdelegacion subdelegacion = solicitud.getCitaSolicitud().getSubdelegacion();
		
		if(subdelegacion != null){
			parametros.put("P_CVE_DELEGAC", subdelegacion.getDelegacion().getClave());
			log.debug("P_CVE_DELEGAC "+subdelegacion.getDelegacion().getClave());
			parametros.put("P_DES_DELEGAC", subdelegacion.getDelegacion().getDescripcion());
			log.debug("P_DES_DELEGAC "+subdelegacion.getDelegacion().getDescripcion());
			parametros.put("P_CVE_SUB_DEL", subdelegacion.getClave());
			log.debug("P_CVE_SUB_DEL "+subdelegacion.getClave());
			parametros.put("P_DES_SUB_DEL", subdelegacion.getDescripcion());
			log.debug("P_DES_SUB_DEL "+subdelegacion.getDescripcion());
			
			String direccion = sujetoObligadoServiceEntityLocal.getDescDomicilioSubdelegacion(subdelegacion.getId());
			log.info("Esta es la direccion ---------->" + direccion);
			parametros.put("P_DIRECCION_SUB_DEL", direccion);
			
			
		}else{
			parametros.put("P_CVE_DELEGAC", "");
			log.debug("P_CVE_DELEGAC");
			parametros.put("P_DES_DELEGAC", "");
			log.debug("P_DES_DELEGAC");
			parametros.put("P_CVE_SUB_DEL", "");
			log.debug("P_CVE_SUB_DEL");
			parametros.put("P_DES_SUB_DEL", "");
			log.debug("P_DES_SUB_DEL");
		}
		
		StringBuffer nombreUsuarioCompleto = new StringBuffer();
		nombreUsuarioCompleto.append(" ");
		if(usuario!=null && usuario.getNomNombre()!=null){
			nombreUsuarioCompleto.append(usuario.getNomNombre());
			if(usuario.getNomPaterno()!=null)
				nombreUsuarioCompleto.append(" "+ usuario.getNomPaterno());
			if(usuario.getNomMaterno()!=null)
				nombreUsuarioCompleto.append(" "+ usuario.getNomMaterno());
		}
		List<Documento> documentosReq = new ArrayList<Documento>();
		Documento documentoAux = new Documento();
		documentosReq.add(documentoAux);
		
		
		
		List<DoctoReqTramiteOrigenSol> documentosReqTra =  new ArrayList<DoctoReqTramiteOrigenSol>();
		try {
			documentosReqTra = documentoProbatorioServiceBusiness.getDocumentosRequeridosPorTipoTramiteOrigenSol(
					solicitud.getTramites().get(0).getTipoTramite().getIdTipoTramite().longValue(), OrigenSolicitudEnum.VENTANILLA.getId());
		} catch (DocumentoProbatorioException e) {
			e.printStackTrace();
		}
		Integer contador = 0;
		for (DoctoReqTramiteOrigenSol doctoReqTramite : documentosReqTra) {
			Documento documentoAux2 = new Documento();
			String documentoDetalle;
			contador = contador+1;
			if (doctoReqTramite.getCveIdDoctoReqTramOrgSol().equals(12L)) {
				//String[] refDetalle = doctoReqTramite.getRefDetalleDoctoRequerido().split("[|]");
				documentoDetalle = contador.toString()+".- "+ doctoReqTramite.getDocumentoPorTipo().getDocumento().getDesDocumento().concat(". ");
			} else {
				documentoDetalle = contador.toString()+".- "+ doctoReqTramite.getDocumentoPorTipo().getDocumento().getDesDocumento().concat(". ").concat(doctoReqTramite.getRefDetalleDoctoRequerido() != null?doctoReqTramite.getRefDetalleDoctoRequerido():"");
			}
			
			documentoAux2.setDesDocumento(documentoDetalle);
			documentosReq.add(documentoAux2);
		}
		parametros.put("P_DOC_REQUERIDOS", documentosReq);
		String nombreArchivo = "";
		
		nombreArchivo = "ComprobanteCita_"+solicitud.getNoFolioSolicitud()+".pdf";
		log.debug("NOMBRE REPORTE: "+nombreArchivo);
		
		
		byte[] reporte = manejadorReportes.ejecutaComprobanteCita(parametros, "ComprobanteCita.jrxml", false);
			
		
		return reporte;
	}
	
	@Override
	public byte[] generarAcuseCancelacion(Solicitud solicitud) throws GestionPatronalBusinessException{
		
		Usuario usuario = solicitud.getSolicitante();
		Tramite tramite = solicitud.getTramites().get(0);
		String mensajeAcuse1 = "Mediante el presente se hace constar la cancelación del trámite";
		String mensajeAcuse2 = "a petición del patrón o representante legal.";
		
		SujetoObligado sujeto = solicitud.getSujetoObligado();//En el momento de la invocaciï¿½n este tramite contiene la informaciï¿½n anterior de la persona antes de impactar
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		BufferedImage imagenCodeQR = generarCodigoQRServiceBusinessRemote.generadorCodigoQrCadena(solicitud.getCadenaOriginal(), 250, 250);
		parametros.put("imagenQR", imagenCodeQR);
		
		SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd 'de' yyyy, HH:mm:ss", new Locale("es", "ES"));
		String fecha = " "+formatter.format(new Date())+" ";
		parametros.put("P_FECPRESENTA", fecha);
		log.debug("P_FECPRESENTA "+fecha);
		parametros.put("P_FECSOLCITUD", fecha);
		log.debug("P_FECSOLCITUD "+fecha);
		parametros.put("P_FOLIO_SOLCT", solicitud.getNoFolioSolicitud());
		log.debug("P_FOLIO_SOLCT "+solicitud.getNoFolioSolicitud());
		parametros.put("P_DESCTRAMITE", mensajeAcuse1 + " " + tramite.getTipoTramite().getDescripcion() + " "+ mensajeAcuse2);
		log.debug("P_DESCTRAMITE "+tramite.getTipoTramite().getDescripcion());
		parametros.put("IS_FISICA", false);
		parametros.put("IS_MORAL", false);
		String numRP = "";
		if (sujeto.getNumeroRegistroPatronal().length() == 8) {
			numRP = sujeto.getNumeroRegistroPatronal()
					+sujeto.getModalidad().getNumModalidad()
					+sujeto.getDigVerificador();
		} else{
			numRP = sujeto.getNumeroRegistroPatronal();
		}
		
		parametros.put("P_REGPATRONAL", numRP);
		log.debug("P_REGPATRONAL "+numRP);
		if(sujeto.getTipoPersonaFiscal() == TipoPersonaFiscal.FISICA){
			parametros.put("IS_FISICA", true);
			Fisica fisica = sujeto.getFisica();
			fisica  = (Fisica)sujetoObligadoService.obtenerPersonaPorIdentificador(fisica.getIdPersona());
			String nombreCompleto = ""; 
			nombreCompleto = obtenerNombreCompletoPersonaFisica(fisica);
			log.debug("P_RAZONSOCIAL ");
			parametros.put("P_NOMBRE_PERS", nombreCompleto);
			log.debug("P_NOMBRE_PERS "+nombreCompleto);
			parametros.put("P_RFC_PERSONA", fisica.getRfc());
			log.debug("P_RFC_PERSONA "+fisica.getRfc());
			parametros.put("P_CURPPERSONA", fisica.getCurp());
			log.debug("P_CURPPERSONA "+fisica.getCurp());
			parametros.put("P_NOMB_COMERC", sujeto.getNombreComercial());
			log.debug("P_NOMB_COMERC "+sujeto.getNombreComercial());
			
		}else{
			parametros.put("IS_MORAL", true);
			Moral moral = sujeto.getMoral();
			Long idPersonaMoral = moral.getIdPersona()!=null ?moral.getIdPersona() :moral.getCveMoral();
			moral = (Moral)sujetoObligadoService.obtenerPersonaMoralPorIdentificador(idPersonaMoral);
			parametros.put("P_RAZONSOCIAL", moral.getRazonSocial());
			log.debug("P_RAZONSOCIAL "+moral.getRazonSocial());
			parametros.put("P_NOMBRE_PERS", " ");
			log.debug("P_NOMBRE_PERS ");
			parametros.put("P_RFC_PERSONA", moral.getRfc());
			log.debug("P_RFC_PERSONA "+moral.getRfc());
			parametros.put("P_NOMB_COMERC", sujeto.getNombreComercial());
			log.debug("P_NOMB_COMERC "+sujeto.getNombreComercial());
			
		}
		parametros.put("P_CADENA_ORIGINAL", solicitud.getCadenaOriginal());
		parametros.put("P_SELLO_DIGITAL", solicitud.getSelloDigital());
		parametros.put("P_SEC_NOTARIAL", solicitud.getSecuenciaDeNotaria());
		StringBuffer nombreUsuarioCompleto = new StringBuffer();
		nombreUsuarioCompleto.append(" ");
		if(usuario!=null && usuario.getNomNombre()!=null){
			nombreUsuarioCompleto.append(usuario.getNomNombre());
			if(usuario.getNomPaterno()!=null)
				nombreUsuarioCompleto.append(" "+ usuario.getNomPaterno());
			if(usuario.getNomMaterno()!=null)
				nombreUsuarioCompleto.append(" "+ usuario.getNomMaterno());
		}
		List<Documento> documentosReq = new ArrayList<Documento>();
		Documento documentoAux = new Documento();
		documentosReq.add(documentoAux);
		
		String nombreArchivo = "";
		
		nombreArchivo = "Acuse_"+solicitud.getNoFolioSolicitud()+".pdf";
		log.debug("NOMBRE REPORTE: "+nombreArchivo);
		
		
		byte[] reporte = manejadorReportes.ejecutaAcuseCancelacion(parametros);
		
		
		log.debug("ENVIANDO CORREO ELECTRONICO: "+nombreArchivo);
		//afiliacionService.notificarPorCorreoElectronico(sujeto, solicitud.getSolicitudId(), TipoAccionAfectacionEnum.ATTACH_ACUSE.getValor().intValue());
		log.debug("SE FINALIZO EL ENVIO DE CORREO ELECTRONICO: "+nombreArchivo);
			
		
		return reporte;
	}
	
}
