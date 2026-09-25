/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:ServiceBusiness.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.asegurado.service.business
 *  @Fecha:20/02/2012
 */
package mx.gob.imss.ctirss.delta.gestion.asegurado.service.business;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CatalogosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.FinalizaSolicitudServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.UmfNoLocalizadaException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.AseguradoConRPAsignado;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.AsignacionNSSNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.GenerarNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.NSSYaExistenteException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaConNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaSinNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.SerieNssAgotadaException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.SolicitudNssCorreoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.WsAntecedentesAseguradoExcpetion;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorAlActivarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.NivelDeAsignacionSerieIndefinidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.SeriesNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.AsignacionNssPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ComparacionSinDiferenciasException;
import mx.gob.imss.ctirss.delta.exception.individuo.DatosInsuficientesICAException;
import mx.gob.imss.ctirss.delta.exception.individuo.DiferenciasRENAPOContraSAT;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.PortalCiudadanoException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.model.AttachmentContent;
import mx.gob.imss.ctirss.delta.framework.base.model.EmailPayloadType;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.ArgumentosInvalidosException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.DatosInsuficientesParaConsultaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudEnProcesoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.framework.util.Base64Cipher;
import mx.gob.imss.ctirss.delta.framework.util.Utilerias;
import mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.entity.AseguradoEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.entity.ServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.AseguradoServiciosExternosRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.SerieServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.SolicitudNssCorreoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.utility.ReporteHelperLocal;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.utility.ServiceUtility;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.CalificacionesPersonaBusinessServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PortalCiudadanoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration.EMailProducer;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.Movimiento06CorreccionBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.global.model.PersonaTO;
import mx.gob.imss.ctirss.delta.model.ModuloEnum;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AltaDatosAsignacionNSSType;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.asegurado.SolicitudNssCorreo;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoPerInteresadaSol;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.enums.OrigenConsultaNssEnum;
import mx.gob.imss.ctirss.delta.model.enums.SexoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDomicilioEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaInteresadaSolEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoSerieEnum;
import mx.gob.imss.ctirss.delta.model.gestion.asegurado.integracion.AseguradoWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.asegurado.integracion.ValidarAsignacionLocalizacionNssWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.asegurado.integracion.sindo.MovCorreccionesDatosAseguradoType;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.AfectarDatosPersonaWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Calificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.CalificacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.CiudadanoCurpCorreo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosConsulta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Identificador;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.CalificacionPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.nss.AsignacionMasivaWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.nss.AsignacionSerieNSS;
import mx.gob.imss.ctirss.delta.model.gestion.nss.ModuloOrigenAsignacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.nss.Serie;
import mx.gob.imss.ctirss.delta.model.gestion.nss.TipoSerie;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.PersonaInteresadaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultadoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteActualizacionAsegurado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsignacionMasiva;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCambioInformacionPersona;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.AfectarDatosPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.ComponentesExternosBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.utility.AfectarDatosPersonaUtilityRemote;
import net.sf.jasperreports.engine.JRParameter;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.core.io.ClassPathResource;


@Stateless(name = "serviceBusiness", mappedName = "serviceBusiness")
public class ServiceBusiness extends AbstractServiceBusiness implements
		ServiceBusinessRemote {
	private static final String MIME_PDF = "application/pdf";
	private static final String REGEX_CURP_FISICA = "^([a-zA-Z]{4})\\d{6}([hmHM]{1}[a-zA-Z]{2}" +
			"[b-df-hj-np-tv-zB-DF-HJ-NP-TV-Z]{3}[a-zA-Z0-9]{1}[0-9]{1})$";
	private static final int LONGITUD_CURP = 18;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private int ORIGEN_BDTU = 6;
	private static final String ESTATUS_RENAPO_INVALIDO = "Invalido";
	private static final String MENSAJE_ERROR_COMPARACION = "No se puede realizar el tr�mite de Asignaci�n del N�mero de Seguridad Social por internet ya que los datos estad�sticos localizados en el Instituto no coinciden con los datos encontrados en RENAPO, para poder realizar el tr�mite deber� presentarse en una subdelegaci�n del Instituto.";
	private static final String MENSAJE_GENERICO = "No se puede realizar el tr�mite de Asignaci�n del N�mero de Seguridad Social por internet ya que su informaci�n no pudo ser validada dentro del Instituto, para poder realizar el tr�mite deber� presentarse en una subdelegaci�n del Instituto.";
	private static final String MENSAJE_ACTUALIZACION_GENERICO = "Los datos registrados en el IMSS asociados a la CURP, presentan alguna inconsistencia, por favor acude a tu Subdelegaci�n para realizar la solicitud de regularizaci�n.";
	private static final String MENSAJE_SIN_DIFERENCIAS_CURP_RENAPO_IMSS = "Los datos asociados a tu CURP se encuentran actualizados";
	private static final String MENSAJE_GENERICO_ACCESO_APPS_MOVILES = "�Los datos registrados en el IMSS asociados a la CURP, presentan alguna inconsistencia, por favor acude a tu Subdelegaci�n para obtener tu N�mero de Seguridad Social; presentando: CURP, Acta de Nacimiento e Identificaci�n Oficial.";
	@EJB
	private transient ServiceEntityLocal serviceEntity;
	@EJB(mappedName = "personaBusiness")
	private PersonaBusinessRemote personaBusiness;
	@EJB(mappedName = "solicitudBusiness")
	private SolicitudBusinessRemote solicitudBusiness;
	@EJB(mappedName = "serieServiceBusiness")
	private SerieServiceBusinessRemote serieServiceBusiness;
	@EJB
	private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
	@EJB
	private transient AfectarDatosPersonaBusinessRemote afectarDatosPersonaBusiness;
	@EJB
	private DomicilioServiceBusinessRemote domicilioServiceBusiness;
	@EJB
	private ComponentesExternosBusinessRemote componentesExternosBusiness;
	@EJB
    private FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;
	@EJB
	private LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote localizarPersonaFisicaEnRENAPOServiceBusiness;
	@EJB
	private CalificacionesPersonaBusinessServiceRemote calificacionesPersonaBusinessService;
	@EJB(mappedName = "EMailQProducer")
	private EMailProducer eMailProducer;
	@EJB
	private SolicitudNssCorreoServiceBusinessRemote solicitudNssCorreoServiceBusiness;
	
	@EJB
	private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;
	@EJB
	private ReporteHelperLocal reporteHelperLocal;
	
	@EJB
	private AseguradoEntityLocal aseguradoEntity;
	
	@EJB(mappedName = "grupoFamiliarService")
	private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	
	@EJB(mappedName = "finalizaSolicitud")
	private FinalizaSolicitudServiceRemote finalizaSolicitudServiceRemote;
	
	@EJB(mappedName = "movimiento06CorreccionAsegurado")
	private Movimiento06CorreccionBusinessRemote movimiento06Correccion;
	
	@EJB(mappedName = "afectarDatosPersonaUtility")
	private AfectarDatosPersonaUtilityRemote afectarDatosPersonaUtility;
	
	@EJB(mappedName = "catalogosService")
	private CatalogosServiceRemote catalogosServiceRemote;
	
	@EJB(mappedName = "portalCiudadanoServiceBusiness")
	private PortalCiudadanoServiceBusinessRemote portalCiudadanoServiceBusiness;
	
	@EJB(mappedName="serviciosExternosAseguradosBusiness")
	private AseguradoServiciosExternosRemote serviciosExternosAseguradosBusiness;
	
	@EJB(mappedName = "documentoProbatorioServiceBusiness")
	private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusiness;
	
	
	
	@Override
	public Object getAcuseActualizacionDatos(String folioSolicitud,
			String titulo, FirmaElectronica fe,
			TramiteActualizacionAsegurado tramite) throws Exception {
		List<TramiteActualizacionAsegurado> list = new ArrayList<TramiteActualizacionAsegurado>();
		list.add(tramite);

		String plantilla = "acuseActualizacionCurp.jrxml";

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put(JRParameter.REPORT_LOCALE, new Locale("es","MX"));

		parametros.put("titulo", titulo);
		parametros.put("folioSolicitud", folioSolicitud);
		
		parametros.put("cadenaOriginal", fe.getCadenaOriginal());
		parametros.put("selloDigital", fe.getRecibo());
		parametros.put("secuenciaNotaria", fe.getSecuenciaNotaria());
		parametros.put("numeroSerie", fe.getSerialCertificado());
		
		parametros.put("fechaReporte", new Date());
		
		parametros.put("IMAGENES_DIR", new ClassPathResource("reportes/").getPath());
		
		ByteArrayOutputStream repo = reporteHelperLocal.ejecutaReporte(parametros, list, plantilla);
		
		byte[] documento = repo.toByteArray();
		log.debug("documento:"+documento);
		if(documento != null && fe.getSecuenciaNotaria() != null){
			log.debug("La secuencia de notarias es: " + fe.getSecuenciaNotaria());
			firmaDigitalBusinessRemote.guardarArchivoFirmado(fe.getSecuenciaNotaria(), "acuseActualizacionDatos.pdf", documento);
		}

		return documento;
	}

	@Override
	public Solicitud crearFinalizarTramiteActualizacionDatos(
			TramiteActualizacionAsegurado tramiteActualizacion) throws ImpactaAlmacenesWSException,
			IllegalArgumentException, SolicitudNoValidaException, SolicitudNoEncontradaException, TramiteNoEncontradoException,
			Exception{
		
		return crearFinalizarTramiteActualizacionDatos(tramiteActualizacion,OrigenSolicitudEnum.PORTAL_CIUDADANO);
	}
	
	

	@Override
	public Solicitud crearFinalizarTramiteActualizacionDatos(
			TramiteActualizacionAsegurado tramiteActualizacion, OrigenSolicitudEnum origenSolicitud)
			throws ImpactaAlmacenesWSException, IllegalArgumentException,
			Exception {
		//Se crea el objeto de la solicitud
				Solicitud solicitud = null;
				Fisica fisicaNueva = tramiteActualizacion.getFisicaNueva();
				Fisica fisicaAnterior = tramiteActualizacion.getFisicaAnterior();
				Fisica fisicaActualizar = new Fisica();
				fisicaActualizar.setIdPersona(fisicaAnterior.getIdPersona());
				Long idAsignacionNSS = tramiteActualizacion.getIdAsignacionNSS();
				boolean cambioCurp = false;
				boolean cambioFecha = false;
				
				
				//se busca la persona de renapo para recuperar el acta si es que cuenta con una
						Fisica fisicaRENAPO = this.localizarPersonaFisicaEnRENAPOServiceBusiness
								.localizarPersonaFisicaEnRENAPOxCURP(fisicaAnterior.getCurp());
					
						
				
				//se califica a la persona existente
				calificacionesPersonaBusinessService.calificarRENAPO(fisicaAnterior);
				
				if((StringUtils.isBlank(fisicaAnterior.getCurp()) && StringUtils.isNotBlank(fisicaNueva.getCurp()))
						|| !fisicaAnterior.getCurp().equals(fisicaNueva.getCurp())) {
					cambioCurp = true;
				}
				
				if(cambioCurp) {
					
					log.debug("se actualiza la curp de la persona: " + fisicaActualizar.getIdPersona() + " con curp: " + fisicaAnterior.getCurp() +
							" con la curp: " + fisicaNueva.getCurp());
					
					fisicaActualizar.setCurp(fisicaNueva.getCurp());
					fisicaAnterior.setCurp(fisicaNueva.getCurp());
				} else {
					log.debug("No se actualiza curp para la persona " + fisicaActualizar.getIdPersona());
				}
				
				if(fisicaAnterior.getFechaNacimiento() == null) {
					if(fisicaNueva.getFechaNacimiento() != null ) {
						cambioFecha = true;
					}
				} else if(!fisicaAnterior.getFechaNacimiento().equals(fisicaNueva.getFechaNacimiento())) {
					cambioFecha = true;
				}
				
				if(cambioFecha) {
					log.debug("Se actualiza la fecha de nacimiento de la persona: " + fisicaActualizar.getIdPersona() + " con fecha: " + fisicaAnterior.getFechaNacimiento() +
							" por la fecha " + fisicaNueva.getFechaNacimiento());
					fisicaActualizar.setFechaNacimiento(fisicaNueva.getFechaNacimiento());
					fisicaAnterior.setFechaNacimiento(fisicaNueva.getFechaNacimiento());
				} else {
					log.debug("No se actualiza fecha para la persona " + fisicaActualizar.getIdPersona());
				}
				
				if(cambioCurp || cambioFecha) {
					//actualizamos los datos de la persona
					personaBusiness.actualizarPersona(fisicaActualizar);
					
					//se va nula la persona original ya que no se emplea y solo se actualiza el CURP y fecha de nacimiento
					MovCorreccionesDatosAseguradoType movCorrecion = afectarDatosPersonaUtility.generarMovimientoActualizacionAseguradoSINDO(fisicaAnterior, null, null);
					log.debug("Movimiento 06 generado -> " + movCorrecion);
					
					movimiento06Correccion.encolarMovimiento06CorrecconAsegurado(movCorrecion);
					
					GrupoFamiliar integrante = null;
					//Verificamos si esta asociado a un grupo familiar con su nss
					//y en ese caso mandaremos el movimientos a los almacenes de vigencia
					try {
						integrante = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarSinVigencia(idAsignacionNSS, fisicaAnterior.getIdPersona());
					} catch (DerechohabientesBusinessException e1) {
						e1.printStackTrace();
						log.error("No fue posible obtener al integrante " + fisicaAnterior.getIdPersona() + " idAsignacion: " + idAsignacionNSS, e1);
					} catch (Exception e1) {
						e1.printStackTrace();
						log.error("No fue posible obtener al integrante " + fisicaAnterior.getIdPersona() + " idAsignacion: " + idAsignacionNSS, e1);
					}
					
					//verificamos si existe un error al mandar el movimiento
					if(integrante != null) {
						log.debug("Se encontro a la persona, registrada como asegurado");
						//seteamos los datos que se modificaron
						integrante.getDerechohabiente().setCurp(fisicaNueva.getCurp());
						integrante.getDerechohabiente().setFechaNacimiento(fisicaNueva.getFechaNacimiento());
			
						try {
							finalizaSolicitudServiceRemote.mandaMovimientoWS(integrante);
						} catch (IllegalArgumentException e) {
							log.debug("ocurrio un error de parametros");
							e.printStackTrace();
							throw e;
						} catch (ImpactaAlmacenesWSException e) {
							log.error("Ocurrio un error al carcular la vigencia");
							e.printStackTrace();
							throw e;
						} catch (Exception e) {
							log.error("ocurrio un error no esperado");
							e.printStackTrace();
							throw e;
						}
					}
			
					//Se crea un usuario con la CURP de la persona actualizada
					Usuario usuario = new Usuario();
					usuario.setCveIdUsuario(fisicaNueva.getCurp());
					usuario.setUsuario(fisicaNueva.getCurp());
			
					//Se crea un objeto solicitud con la informacion basica del tramite
					solicitud = crearSolicitudInicialPorEnum(
							EstadoSolicitudEnum.ATENDIDA, TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES,
							origenSolicitud, usuario);
			
					//Se setea el tramite a la solicitud
					solicitud = asociarTramiteSolicitudPorEnum(solicitud, tramiteActualizacion,
							TipoTramiteEnum.ACTUALIZACION_CURP_ASEGURADO, EstadoTramiteEnum.CERRADO);
			
					solicitud.setPersonaInteresadaSolicitud(new PersonaInteresadaSolicitud());
					solicitud.getPersonaInteresadaSolicitud().setPersona(new Persona());
					solicitud.getPersonaInteresadaSolicitud().getPersona().setIdPersona(fisicaAnterior.getIdPersona());
					solicitud.getPersonaInteresadaSolicitud().setTipoPersonaInteresadaSol(new TipoPerInteresadaSol());
					solicitud.getPersonaInteresadaSolicitud().getTipoPersonaInteresadaSol().setCveTipoInteresadaSol(TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId());
					// Se guarda la solicitud
					solicitud = solicitudBusiness.crear(solicitud);
					log.debug("Solicitud creada ...." + solicitud);
			
					solicitudBusiness.actualizaAConcluida(solicitud);
					
					TipoTramite tipoTramite = null;
					
					try {
						tipoTramite = catalogosServiceRemote.getCatalogoTipoTramite(solicitud.getTramites().get(0).getTipoTramite().getIdTipoTramite().longValue());
						solicitud.getTramites().get(0).setTipoTramite(tipoTramite);
					} catch(Exception e) {
						e.printStackTrace();
					}
					
					/*
					if(fisicaRENAPO.getActaNacimiento() != null){
						List<DocumentoProbatorio> documentoProbatorios=new ArrayList<DocumentoProbatorio>();
						documentoProbatorios.add(fisicaRENAPO.getActaNacimiento());
						documentoProbatorioServiceBusiness.procesaDocumentosGD(solicitud.getTramites().get(0).getTramiteId(),
								fisicaAnterior.getIdPersona(), documentoProbatorios);
					}*/
					
					
					Map<String, String> firma = firmaDigitalBusinessRemote.getCadenaOriginalYSelloDigital(solicitud, fisicaNueva, null, fisicaNueva.getNss());
					
					if(firma != null) {
						String cadenaOriginal = (String) firma.get("cadenaOriginal");
						String sellodigital = (String)firma.get("selloDigital");
						String secuenciaNot = (String)firma.get("tramite");
						String numeroSerie = (String)firma.get("numeroSerie");
						
						log.debug("La secuencia de notaria generada es : " + secuenciaNot);
						FirmaElectronica firmaElectronica = new FirmaElectronica();
						
				        firmaElectronica.setCadenaOriginal(cadenaOriginal);
				        firmaElectronica.setReciboNotarial(secuenciaNot);
				        firmaElectronica.setSecuenciaNotaria(secuenciaNot);
				        firmaElectronica.setSerialCertificado(numeroSerie);
				        firmaElectronica.setRecibo(sellodigital);
				        
				        solicitud.setFirmaElectronica(firmaElectronica);
						
						solicitud.setCadenaOriginal(cadenaOriginal);
						solicitud.setSecuenciaDeNotaria(secuenciaNot);
						solicitud.setSelloDigital(sellodigital);
						solicitud.setNumeroSerieCertificado(numeroSerie);
						
						firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud,firmaElectronica);
					}
					
					
				} else {
					throw new IllegalArgumentException("No se modifico ningun dato del asegurado");
				}
				
				return solicitud;
	}

	@Override
	public List<AsignacionNSS> buscarAsignacionNssPorCurpODatosBasicos(
			Fisica fisica) throws AsignacionNSSNoLocalizadoException, DatosInsuficientesParaConsultaException {
		
		List<AsignacionNSS> nsss = null;
		if(!StringUtils.isBlank(fisica.getCurp())) {
			nsss = personaBusiness.obtenerNsssByCurp(fisica.getCurp());
		} else {
			nsss = personaFisicaServiceBusiness.localizarNssPorDatosBasicosEnImss(fisica);
		}
		
		if(nsss == null || nsss.isEmpty()) {
			throw new AsignacionNSSNoLocalizadoException();
		}
		
		return nsss;
	}

	@Override
	public String generaNss(final Long idDelegacion,
			final Long idSubDelegacion, final Long numAnioNacimiento) {
		return serviceEntity.generaNss(idDelegacion, idSubDelegacion,
				numAnioNacimiento);
	}

	 @Override
	public void concluir(final Solicitud solicitud)
			throws SolicitudEnProcesoException {
        
		Solicitud solAux = this.solicitudBusiness.obtenerEstados(solicitud);
		
		if (solAux.getEstadoSolicitud().getIdEstadoSolicitud()
				.equals(EstadoSolicitudEnum.ATENDIDA.getCodigo())) {
			throw new SolicitudEnProcesoException("La solicitud " + solicitud.getNoFolioSolicitud() + " ya fue atendida");
		} else {
		 
	        solicitud.setEstadoSolicitud(new EstadoSolicitud(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo()));
	     
	        try {
	        	this.log.warn(" Actualizando el estado de la solicitud ....");
	        	solicitudBusiness.actualizarEstados(solicitud);
				this.log.warn(" Encolando la solicitud para su procesamiento ...");
				//sendMessage(solicitud);
				procesarSolicitud(solicitud);
				
			} catch (SolicitudNoEncontradaException e) {
				this.log.error("Al concluir", e);
			} catch (TramiteNoEncontradoException e) {
				this.log.error("Al concluir", e);
			}
		}
    }

	 /**
     * 191807 021012
     * MEtodo encargado de cancelar una solocitud pendiente
     * @param solicitud
     */
    @Override
    public void cancelar(Solicitud solicitud){
    	
		EstadoTramite estadoTramite = new EstadoTramite();
		estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
		estadoTramite.setDescripcion(EstadoTramiteEnum.CERRADO.getDescripcion());
	    
		for (Tramite tramite : solicitud.getTramites()) {
			tramite.setEstadoTramite(estadoTramite);
		}
		
		EstadoSolicitud estadoSolicitud = new EstadoSolicitud(); 
		estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.CANCELADA.getCodigo());
		estadoSolicitud.setDescripcion(EstadoSolicitudEnum.CANCELADA.getDescripcion());
		solicitud.setEstadoSolicitud(estadoSolicitud);
		
        try {
        	this.log.warn("cancelar(...): Actualizando el estado de la solicitud ....");
        	solicitudBusiness.actualizarEstados(solicitud);			
		} catch (SolicitudNoEncontradaException e) {
			this.log.error("Error al concluir la solicitud pendiente: ", e);
		} catch (TramiteNoEncontradoException e) {
			this.log.error("Error al concluir la solicitud pendiente: ", e);
		}
    }

	private void procesarSolicitud(final Solicitud solicitud)
			throws SolicitudEnProcesoException {
		try {
			log.info("getSolicitudId: " + solicitud.getSolicitudId());
			if (!solicitud.getTramites().isEmpty()
					&& solicitud.getTramites().get(0) instanceof TramiteAsegurado) {
				try {
					this.procesarTramiteSolicitud(solicitud);
				} catch (SolicitudNoEncontradaException e) {
					log.error("In onMessage() al procesar tramite asegurado", e);
					rechazarSolicitud(solicitud);
				} catch (Exception e) {
					log.error("In onMessage()", e);
					rechazarSolicitud(solicitud);
				}
			}
		} catch (Exception e) {
			log.error("In onMessage()", e);
			rechazarSolicitud(solicitud);
		}
	}

	private void rechazarSolicitud(final Solicitud solicitud) {
		solicitud.setEstadoSolicitud(new EstadoSolicitud());
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.RECHAZADA.getCodigo());
		solicitud.getEstadoSolicitud().setDescripcion(EstadoSolicitudEnum.RECHAZADA.getDescripcion());

		try {
			solicitudBusiness.actualizarEstados(solicitud);
		} catch (SolicitudNoEncontradaException e) {
			log.error("In actualizarEstados()", e);
		} catch (TramiteNoEncontradoException e) {
			log.error("In actualizarEstados()", e);
		} catch (Exception e) {
			log.error("In actualizarEstados()", e);
		}
	}

	@Override
	public Solicitud procesarTramiteSolicitud(final Solicitud solicitud)
			throws SolicitudNoEncontradaException,
			NivelDeAsignacionSerieIndefinidoException,
			SeriesNoLocalizadasException, ErrorAlActivarSerieException,
			SolicitudEnProcesoException {
		Solicitud solicitudModificada = null;

		// OBTENEMOS LOS OBJETOS DE LA SOLICITUD EN BASE AL CAMPO XML.
		log.debug("Solicitud a procesar id: " + solicitud.getSolicitudId());
		if (solicitud.getSolicitudId() != null) {

			final Solicitud solInBD = solicitudBusiness.consultar(solicitud);
			log.debug("El estado de la solicitud es: " + solInBD.getEstadoSolicitud().getDescripcion());

			if (solInBD.getEstadoSolicitud().getIdEstadoSolicitud().equals(
					EstadoSolicitudEnum.ATENDIDA.getCodigo())) {
				this.log.info("La solicitud de asignaci�n ya fue atendida");
				throw new SolicitudEnProcesoException("La solicitud ya fue atendida.");
			} else {

				final List<Tramite> listaTramite = solicitud.getTramites();
				if (listaTramite != null) {
					for (Tramite tramite : listaTramite) {
						log.info("Solicitud registrada. id: " + solicitud.getSolicitudId() + "   Tramite a procesar: " + tramite.getTramiteId());
						log.debug("TipoTramite: " + tramite.getTipoTramite().getIdTipoTramite());

						// SOLO PROCESA TRAMITES CON ESTADO REGISTRADO == INICIADO.
						final Boolean isEstadoTramRegistrado = EstadoTramiteEnum.INICIADO
								.getCodigo().equals(tramite.getEstadoTramite()
										.getIdEstadoTramitePersona());
						log.debug("isEstadoTramRegistrado  ? : " + isEstadoTramRegistrado);

						if (isEstadoTramRegistrado && (TipoTramiteEnum.REGISTRO_DE_PERSONA
								.getCodigo().equals(tramite.getTipoTramite().getIdTipoTramite()) || 
								TipoTramiteEnum.ASIGNACION_NSS.getCodigo().equals(tramite.getTipoTramite().getIdTipoTramite()))
								&& tramite instanceof TramiteAsegurado) {
							
							/*
							 * VERIFICAMOS QUE LA PERSONA ESTE CALIFICADA POR EL
							 * IMSS, RENAPO O SAT
							 */
							final Integer iCalificacion = (((TramiteAsegurado) tramite)
									.getFisica().getPersonaCalificaciones()
									.isEmpty()) ? null : Utilerias
									.convertir(((TramiteAsegurado) tramite).getFisica()
											.getPersonaCalificaciones().get(0)
											.getCalificacion().getIdCalificacion());

							log.debug("Calificacion de la persona : " + iCalificacion);
							
							if (CalificacionPersona.NO_VALIDADO.equals(iCalificacion)) {
								AsignacionNSS fisicaNSS = ((TramiteAsegurado) tramite).getFisica();
								
								log.warn("Calificacion no valida, se aplica ICA a la persona [idPersona:"
										+ fisicaNSS.getIdPersona() + "]");
								
								if (fisicaNSS.getIdPersona() != null) {
									ICADatosConsulta datosEntrada = new ICADatosConsulta();
									
									datosEntrada.setPersonaFisica(fisicaNSS);
									datosEntrada.setIndicadorConsultaRENAPO(true);
									datosEntrada.setIndicadorConsultaSAT(false);
									datosEntrada.setIndicadorMostrarPantalla(false);
									
									ICADatosRespuesta datosRespuesta = null;
									
									try {
										datosRespuesta = this.personaFisicaServiceBusiness
												.identificarCambios(datosEntrada);
										datosRespuesta = this.personaFisicaServiceBusiness
												.integrarCambios(datosRespuesta);
										((TramiteAsegurado) tramite).setIcaDatosRespuesta(datosRespuesta);
									} catch (PersonaNoEncontradaException e) {
										this.log.error(e);
									} catch (CURPNoLocalizadoEnEntidadExternaException e) {
										this.log.error(e);
									} catch (ClienteWebserviceRenapoCurpException e) {
										this.log.error(e);
									} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
										this.log.error(e);
									} catch (RFCNoLocalizadoEnEntidadExternaException e) {
										this.log.error(e);
									} catch (ClienteWebserviceSatRfcException e) {
										this.log.error(e);
									} catch (ErrorComparacionDatosRENAPOException e) {
										this.log.error(e);
									} catch (ComparacionSinDiferenciasException e) {
										this.log.error(e);
									} catch (DatosInsuficientesICAException e) {
										this.log.error(e);
									} catch (DiferenciasRENAPOContraSAT e) {
										this.log.error(e);
									} catch (PersonaFisicaNoEncontradaException e) {
										this.log.error(e);
									}
									
								} else {
									this.log.warn("La persona a aplicarle el ICA no cuenta con id, por lo tanto, no se ejecut� la operaci�n");
								}
							}

							log.trace("Ini alta persona: " + solicitud.getSolicitudId());

							TramiteAsegurado tramiteAsegurado = (TramiteAsegurado) tramite;

							// CAMBIA EL ESTADO DEL TRAMITE
							tramite.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
							tramite.getEstadoTramite().setDescripcion(EstadoTramiteEnum.CERRADO.getDescripcion());
							tramite.getRazonResultado().setIdRazonResultado(RazonResultadoEnum.NORMAL.getCodigo().longValue());
							tramite.getRazonResultado().setDescripcion(RazonResultadoEnum.NORMAL.getDescripcion());
							tramite.setResultado(Boolean.TRUE);

							try {
								log.trace("Ini alta serie: " + solicitud.getSolicitudId());
								AsignacionNSS asignacionNueva = serieServiceBusiness.registrarAsegurado(
										tramiteAsegurado.getFisica(), tramiteAsegurado.getAsignacionSerieNss(), tramiteAsegurado);
								
								tramiteAsegurado.getFisica().setNss(asignacionNueva.getNss());
								tramiteAsegurado.getFisica().setNssStr(asignacionNueva.getNss());
							} catch (ArgumentosInvalidosException e) {
								log.error("Error al dar de alta a la persona fisica. No se realizar\u00E1 el alta de la persona", e);
								continue;
							} catch (DomicilioNoValidoException e) {
								log.error("Error al dar de alta a la persona fisica. No se realizar\u00E1 el alta de la persona", e);
								continue;
							} catch (ClienteWebserviceSatRfcException e) {
								log.error("Error al dar de alta a la persona fisica. No se realizar\u00E1 el alta de la persona", e);
								continue;
							} catch (ClienteWebserviceRenapoCurpException e) {
								log.error("Error al dar de alta a la persona fisica. No se realizar\u00E1 el alta de la persona", e);
								continue;
							} catch (PersonaNoEncontradaException e) {
								log.error("Error al dar de alta a la persona fisica. No se realizar\u00E1 el alta de la persona", e);
								continue;
							}
							
						}
					}
				}
				
				solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getCodigo());
				solicitud.getEstadoSolicitud().setDescripcion(EstadoSolicitudEnum.ATENDIDA.getDescripcion());

				// GENERAMOS EL XML DE LA SOLICITUD Y LO ALMACENAMOS
				try {
					log.trace("Ini act estados: " + solicitud.getSolicitudId());
					solicitudModificada = solicitudBusiness.actualizarEstados(solicitud);
					log.trace("Medio act estados: " + solicitud.getSolicitudId());
					solicitudModificada = solicitudBusiness.actualizarTramites(solicitudModificada);
					log.trace("Fin act estados: " + solicitud.getSolicitudId());
				} catch (SolicitudNoEncontradaException e) {
					log.error("Error al actualizar estados", e);
				} catch (TramiteNoEncontradoException e) {
					log.error("Error al actualizar estados", e);
				}
			}
		}

		log.error("Fin procesar tramites soli: " + solicitud.getSolicitudId());
		return solicitudModificada;
	}

	@SuppressWarnings("unused")
	private void altaPersonaFisica(final TramiteAsegurado tramite) throws DomicilioNoValidoException {
		// GUARDA A LA PERSONA EN BASE DE DATOS
		final Fisica personaFisicaResultado = personaBusiness.altaPersonaFisica(tramite.getFisica());

		// CAMBIA EL ESTADO DEL TRAMITE
		tramite.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
		tramite.getEstadoTramite().setDescripcion(EstadoTramiteEnum.CERRADO.getDescripcion());
		tramite.getRazonResultado().setIdRazonResultado(RazonResultadoEnum.NORMAL.getCodigo().longValue());
		tramite.getRazonResultado().setDescripcion(RazonResultadoEnum.NORMAL.getDescripcion());
		tramite.setResultado(Boolean.TRUE);

		log.trace("Persona enviada.\n\n" + tramite.getFisica());
		log.trace("Persona recibida despues de darla de alta en BD.\n\n" + personaFisicaResultado);

		tramite.getFisica().setIdPersona(personaFisicaResultado.getIdPersona());
	}
	
	@Override
	public String altaPersonaNss(final Fisica fisica, final Serie serie) {
		return serviceEntity.altaPersonaNss(fisica, serie);
	}
	
	@Override
	public Map<String, Object> generarSolicitudAsignacionNSS(Fisica fisica,
			OrigenSolicitudEnum origenAsignacion, ModuloOrigenAsignacionEnum moduloOrigen)
			throws SolicitudException,
			CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			GenerarNSSException, ErrorComparacionDatosRENAPOException,
			PersonaConNSSException, SolicitudNoValidaException,
			DomicilioNoLocalizadoException, UmfNoLocalizadaException {
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
		Fisica fisicaValidaciones = null;
		Solicitud solicitud = null;
		
		/* 
		 * Primero se valida que la persona no tenga solicitudes registradas o en proceso,
		 * siempre y cuando exista en la base de datos
		 */
		if (fisica.getIdPersona() != null) {
			validarSolicitudesActivasYEnProceso(fisica);
		}
		
		// Se realizan las validaciones correspondientes a la asignaci�n de NSS
		fisicaValidaciones = validacionesNSS(fisica, false);

		/*
		 * Se genera la solicitud con la inforamci�n obtenida del proceso de
		 * validaciones
		 */
		solicitud = crearSolicitudAsignacionNSS(fisicaValidaciones,
				TipoSerieEnum.ORDINARIA, origenAsignacion, null, null, null,
				moduloOrigen);
		
		respuesta.put("SOLICITUD", solicitud);
		
		return respuesta;
	}

	public Fisica getDatosBasicosPersonaNueva(Fisica fisica)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException {
		String curpCapturado = fisica.getCurp();
		int numDatosBasicos = contarDatosBasicos(fisica);
		Fisica personaNueva = null;

		if (StringUtils.isNotBlank(curpCapturado) && numDatosBasicos == 0) {
			personaNueva = localizarPersonaFisicaEnRENAPOServiceBusiness
					.localizarPersonaFisicaEnRENAPOxCURP(curpCapturado);
		} else {
			personaNueva = fisica;
		}

		return personaNueva;
	}

	@Override
	public List<Fisica> localizarPersonaReglasDerechohabiente(Fisica fisica)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			ErrorComparacionDatosRENAPOException,
			DatosInsuficientesParaConsultaException {
		List<Fisica> personasEncontradas = new ArrayList<Fisica>();
		String curpCapturado = fisica.getCurp();
		Fisica fisicaRENAPO = null;
		
		if (StringUtils.isNotBlank(curpCapturado)) {
			fisicaRENAPO = localizarPersonaFisicaEnRENAPOServiceBusiness.localizarPersonaFisicaEnRENAPOxCURP(curpCapturado);
		}
		
		if(fisicaRENAPO != null) {
			//personasEncontradas.add(fisicaRENAPO);
			List<Fisica> personasAux = new ArrayList<Fisica>();
			List<Fisica> personasEncontradasIMSS = personaFisicaServiceBusiness.localizarPersonaFisicaPorDatosBasicosEnImssConFechaOMesYAniodeNacimiento(fisicaRENAPO);
			
			if(personasEncontradasIMSS != null && !personasEncontradasIMSS.isEmpty()){
				for(Fisica fis : personasEncontradasIMSS) {
					Fisica fisCom = fis;
					String curp_ceros = "000000000000000000";
					if((StringUtils.isEmpty(fis.getCurp()) || fisCom.getCurp().equals(curp_ceros)) && fisCom.getNss() == null) {
						fisCom.setCurp(fisicaRENAPO.getCurp());
					}
					if(fisCom.getFechaNacimiento() == null) {
						fisCom.setFechaNacimiento(fisicaRENAPO.getFechaNacimiento());
						fisCom.setFechaNacimientoFormateada(fisicaRENAPO.getFechaNacimientoFormateada());
					}
					fisCom.setActaNacimiento(fisicaRENAPO.getActaNacimiento());
					personasAux.add(fisCom);
				}
				
				personasEncontradas.addAll(personasAux);
			} else {
				personasEncontradas.add(fisicaRENAPO);
			}
		}
		
		return personasEncontradas;
	}

	@Override
	public List<Fisica> localizarPersonaFisica(Fisica fisica)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			ErrorComparacionDatosRENAPOException, DatosInsuficientesParaConsultaException {
		String curpCapturado = fisica.getCurp();
		int numDatosBasicos = contarDatosBasicos(fisica);

		List<Fisica> personasEncontradas = new ArrayList<Fisica>();
		List<Fisica> personasEncontradasConNSS = new ArrayList<Fisica>();
		Fisica fisicaBusqueda;

		if (StringUtils.isNotBlank(curpCapturado)) {
			/*
			 * Se busca en RENAPO el CURP capturado, en caso de que el servicio
			 * no encuentre el CURP lanza una excepci�n
			 */
			log.debug("---------------> Realizando busqueda a RENAPO (CURP)");
			Fisica fisicaRENAPO = localizarPersonaFisicaEnRENAPOServiceBusiness
					.localizarPersonaFisicaEnRENAPOxCURP(curpCapturado);

			/*
			 * Si se capturaron datos basicos, se realiza la comparacion con los
			 * datos arrojados por RENAPO, si son diferentes se lanza una
			 * excepcion notificando que los datos no corresponden
			 */
			if (numDatosBasicos > 0) {
				log.debug("---------------> Validacion de RENAPO vs Datos Basicos capturados");
				boolean exitoComparacion = personaFisicaServiceBusiness
						.comparaDatosBasicosRENAPO(fisicaRENAPO, fisica);

				if (!exitoComparacion) {
					throw new ErrorComparacionDatosRENAPOException();
				}
			}

			// Se buscan NSS a trav�s de la CURP
			log.debug("---------------> Validando si no existe una persona asignada con NSS: " + curpCapturado);
			List<Fisica> personasEncontradasNss = personaBusiness
					.obtenerPersonaNssByCurpNoIndActivo(curpCapturado);
			if (personasEncontradasNss !=null &&  !personasEncontradasNss.isEmpty()){
				personasEncontradasConNSS.addAll(personasEncontradasNss);
			}
			
			log.debug("---------------> Validando si no existe una persona asignada con NSS (Comparando con la CURP regresado por RENAPO): " + fisicaRENAPO.getCurp());
			if(!curpCapturado.equalsIgnoreCase(fisicaRENAPO.getCurp())){
				List<Fisica> personasEncontradasNssRenapo = personaBusiness
						.obtenerPersonaNssByCurpNoIndActivo(fisicaRENAPO.getCurp());

				if (personasEncontradasNssRenapo != null && !personasEncontradasNssRenapo.isEmpty()){
					personasEncontradasConNSS.addAll(personasEncontradasNssRenapo);
				}
			}
			

			/*
			 * Se considera como persona encontrada aquella que tenga un NSS y
			 * los datos en IMSS sean correspondan con RENAPO, en contrario se
			 * realiza la busqueda por datos estadisticos
			 */
			/*
			if (personasEncontradasNss != null && !personasEncontradasNss.isEmpty()
					&& personasEncontradasNss.size() == 1) {
				Fisica fisicaEncontrada = personasEncontradasNss.get(0);

				log.debug("---------------> Comparando persona asignada con NSS vs RENAPO");
				boolean exitoComparacion = personaFisicaServiceBusiness
						.comparaDatosBasicosRENAPO(fisicaRENAPO, fisicaEncontrada);

				log.debug("---------------> Resultado comparacion" + exitoComparacion);
				if (exitoComparacion) {
					personasEncontradas.add(fisicaEncontrada);
				}
			} else {
				// Se buscan NSS a trav�s de la CURP devuelto por RENAPO
				log.debug("---------------> Validando si no existe una persona asignada con NSS (Comparando con la CURP regresado por RENAPO): " + fisicaRENAPO.getCurp());
				List<Fisica> personasEncontradasNssRenapo = personaBusiness
						.obtenerPersonaNssByCurp(fisicaRENAPO.getCurp());

				if (personasEncontradasNssRenapo != null && !personasEncontradasNssRenapo.isEmpty()
						&& personasEncontradasNssRenapo.size() == 1) {
					Fisica fisicaEncontrada = personasEncontradasNssRenapo.get(0);

					log.debug("---------------> Comparando persona asignada con NSS vs RENAPO");
					boolean exitoComparacion = personaFisicaServiceBusiness
							.comparaDatosBasicosRENAPO(fisicaRENAPO, fisicaEncontrada);

					log.debug("---------------> Resultado comparacion" + exitoComparacion);
					if (exitoComparacion) {
						personasEncontradas.add(fisicaEncontrada);
					}
				}
			}*/

			fisicaBusqueda = fisicaRENAPO;
		} else {
			log.debug("---------------> el filtro se realizara por datos Basicos capturados");

			try {
				// Se eliminan los ceros de la izquierda
				Long idRenapo = Long.valueOf(
						fisica.getLugarNacimiento().getClave().replaceFirst("^0+(?!$)", ""));
				fisica.getLugarNacimiento().setIdRenapo(idRenapo);

				/*
				 * Se busca en RENAPO el CURP capturado, en caso de que el servicio
				 * no encuentre el CURP lanza una excepci�n
				 */
				log.debug("---------------> Realizando busqueda a RENAPO (Datos Basicos)");
				Fisica fisicaRENAPO = localizarPersonaFisicaEnRENAPOServiceBusiness
						.localizarPersonaFisicaEnRENAPOxDatosBasicos(fisica);

				log.debug("---------------> Validacion de RENAPO vs Datos Basicos capturados");
				boolean exitoComparacion = personaFisicaServiceBusiness
						.comparaDatosBasicosRENAPO(fisicaRENAPO, fisica);

				if (exitoComparacion) {
					// Se buscan NSS a trav�s de la CURP devuelto por RENAPO
					log.debug("---------------> Validando si no existe una persona asignada con NSS (Comparando con la CURP regresado por RENAPO): " + fisicaRENAPO.getCurp());
					List<Fisica> personasEncontradasNssRenapo = personaBusiness
							.obtenerPersonaNssByCurpNoIndActivo(fisicaRENAPO.getCurp());
	
					if (personasEncontradasNssRenapo != null && !personasEncontradasNssRenapo.isEmpty()){
						
					/*
							&& personasEncontradasNssRenapo.size() == 1) {
						Fisica fisicaEncontrada = personasEncontradasNssRenapo.get(0);
	
						log.debug("---------------> Comparando persona asignada con NSS vs RENAPO");
						exitoComparacion = personaFisicaServiceBusiness
								.comparaDatosBasicosRENAPO(fisicaRENAPO, fisicaEncontrada);
	
						log.debug("---------------> Resultado comparacion" + exitoComparacion);
						if (exitoComparacion) {
							personasEncontradas.add(fisicaEncontrada);
						}
						*/
						//se bajan todos los registros que se puedan encontrar con la curp devuelta por renapo
						personasEncontradasConNSS.addAll(personasEncontradasNssRenapo);
					}
				}

				fisicaBusqueda = fisicaRENAPO;
			} catch (CURPNoLocalizadoEnEntidadExternaException e) {
				fisicaBusqueda = fisica;
			} catch (ClienteWebserviceRenapoCurpException e) {
				fisicaBusqueda = fisica;
			}
		}

		//se cambio el if para que pueda hacer la busqueda de personas y no solo recuperar asegurados
		//if (personasEncontradas.isEmpty()) {
			
			
			log.debug("---------------> Consulta al IMSS por datos basicos");
			List<Fisica> personasEncontradasAux = personaFisicaServiceBusiness
					.localizarPersonaFisicaPorDatosBasicosEnImss(fisicaBusqueda);
			List<Fisica> personasEncontradasAuxConNSS = personaFisicaServiceBusiness.localizarPersonaFisicaPorDatosBasicosEnImssConNSS(fisicaBusqueda);

			
			/*
			 * Se genera un subconjuntos de las personas encontradas, para las
			 * siguientes validaciones
			 */
			List<Fisica> personasConNSS = new ArrayList<Fisica>();
			
			List<Fisica> personasConNSSSinCalificacion = new ArrayList<Fisica>();
			List<Fisica> personasConCurpConCalificacion = new ArrayList<Fisica>();
			List<Fisica> personasConCurpSinCalificacion = new ArrayList<Fisica>();
			List<Fisica> personasSinCurpConCalificacion = new ArrayList<Fisica>();
			List<Fisica> personasSinnCurpSinCalificacion = new ArrayList<Fisica>();

			for (Fisica fisicaAux : personasEncontradasAux) {
				if (StringUtils.isNotBlank(fisicaAux.getNss())) {
					if (fisicaAux.getPersonaCalificaciones() != null
							&& !fisicaAux.getPersonaCalificaciones().isEmpty()) {
						//personasConNSS.add(fisicaAux);
					} else {
						//personasConNSSSinCalificacion.add(fisicaAux);
					}
					
				} else if (StringUtils.isNotBlank(fisicaAux.getCurp())) {
					if (fisicaAux.getPersonaCalificaciones() != null
							&& !fisicaAux.getPersonaCalificaciones().isEmpty()) {
						personasConCurpConCalificacion.add(fisicaAux);
					} else {
						personasConCurpSinCalificacion.add(fisicaAux);
					}
				} else {
					if (fisicaAux.getPersonaCalificaciones() != null
							&& !fisicaAux.getPersonaCalificaciones().isEmpty()) {
						personasSinCurpConCalificacion.add(fisicaAux);
					} else {
						personasSinnCurpSinCalificacion.add(fisicaAux);
					}
				}
			}
			
			
			//se tiene que iterar la lista de personas encontradas(que en este punto tienen NSS) 
			//para sacar los NSS y ponerlos en un mapa y para sacar los duplicados que salgan por datos basicos
			Map<String, Fisica> mapPersonasNSS = new HashMap<String, Fisica>();
			if(!personasEncontradasConNSS.isEmpty()){
				for (Fisica fisicaNSS : personasEncontradasConNSS) {
					mapPersonasNSS.put(fisicaNSS.getNss(),fisicaNSS);
				}
			}
			//EN ESTE FOR SE ITERAN LA PERSONAS LOCALIZADAS POR DATOS BASICOS QUE TENGAN NSS Y SE PONEN EN EL MAPA
			//PARA SOBRE ESCRIBIR LOS NSS LOCALIZADOS
			if(!personasEncontradasAuxConNSS.isEmpty()){
				for (Fisica fisicaAux : personasEncontradasAuxConNSS) {
					if (StringUtils.isNotBlank(fisicaAux.getNss())) {
						mapPersonasNSS.put(fisicaAux.getNss(),fisicaAux);
					}
				}
			}
			personasEncontradas.addAll(mapPersonasNSS.values());

		this.log.debug("Conjunto de personas localizadas por datos b�sicos" + personasEncontradasAux.size());
			//this.log.debug("Subconjunto de personas con NSS " + personasConNSS.size());
			//this.log.debug("Subconjunto de personas con NSS Sin Calificacion " + personasConNSSSinCalificacion.size());
			this.log.debug("Subconjunto de personas con NSS encontradas " + personasEncontradasConNSS.size());
			this.log.debug("Subconjunto de personas con CURP y calificaciones " + personasConCurpConCalificacion.size());
			this.log.debug("Subconjunto de personas con CURP y sin calificaciones " + personasConCurpSinCalificacion.size());
			this.log.debug("Subconjunto de personas sin CURP y calificaciones " + personasSinCurpConCalificacion.size());
			this.log.debug("Subconjunto de personas sin CURP y sin calificaciones " + personasSinnCurpSinCalificacion.size());
			
			this.log.debug("TOTAL PERSONA ENCONTRADAS EN LA BUSQUEDA " + personasEncontradas.size());

			//personasEncontradas.addAll(personasConNSS);
			//personasEncontradas.addAll(personasConNSSSinCalificacion);
			personasEncontradas.addAll(personasConCurpConCalificacion);
			personasEncontradas.addAll(personasConCurpSinCalificacion);
			personasEncontradas.addAll(personasSinCurpConCalificacion);
			personasEncontradas.addAll(personasSinnCurpSinCalificacion);

			/*
			 * Si existe un unico registro en el IMSS se validan los datos encontrados
			 */
			if (!personasEncontradas.isEmpty() && personasEncontradas.size() == 1) {
				log.debug("---------------> Registro unico en BDTU, se realiza comprobacion");
				Fisica personaRegistrada = personasEncontradas.get(0);

				if (StringUtils.isNotBlank(personaRegistrada.getCurp())) {
					try {
						Fisica fisicaRegRENAPO = localizarPersonaFisicaEnRENAPOServiceBusiness
								.localizarPersonaFisicaEnRENAPOxCURP(personaRegistrada.getCurp());

						log.debug("---------------> Validacion de RENAPO vs Datos Basicos capturados");
						boolean exitoComparacion = personaFisicaServiceBusiness
								.comparaDatosBasicosRENAPO(fisicaRegRENAPO, personaRegistrada);

						if (!exitoComparacion) {
							personaRegistrada.setEstatusRenapo(ESTATUS_RENAPO_INVALIDO);
						} else if(!compararEquivalenciaPersonaRegistrada(personaRegistrada, fisicaBusqueda)){
							personaRegistrada.setEstatusRenapo(ESTATUS_RENAPO_INVALIDO);
						}
					} catch (CURPNoLocalizadoEnEntidadExternaException e) {
						personaRegistrada.setEstatusRenapo(ESTATUS_RENAPO_INVALIDO);
					} catch (ClienteWebserviceRenapoCurpException e) {
						personaRegistrada.setEstatusRenapo(ESTATUS_RENAPO_INVALIDO);
					} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
						personaRegistrada.setEstatusRenapo(ESTATUS_RENAPO_INVALIDO);
					}
				} else {
					personaRegistrada.setEstatusRenapo(ESTATUS_RENAPO_INVALIDO);
				}
			}

			/*
			 * Lista vacia, si se capturo el CURP o se encontro en RENAPO por busqueda en Datos Basicos
			 * se agrega la persona encontrada en RENAPO
			 */
			if ((StringUtils.isNotBlank(curpCapturado) || StringUtils.isNotBlank(fisicaBusqueda.getCurp()))
					&& personasEncontradas.isEmpty()) {
				log.debug("---------------> Lista vacia, buscan personas de CL3");
				//se valida si no existe un antecedente como estudiante con datos incompletos de fecha de nacimiento
				List <AsignacionNSS> aseguradosCL3 =personaFisicaServiceBusiness.localizarNssCl3PorDatosBasicosSinFechaNac(fisicaBusqueda);
				//log.debug("La lista de busquedas de cl3 tiene " + aseguradosCL3);
				//se valida que la consulta solo traiga un registro por datos basicos
				boolean existePersona = false;
				if(aseguradosCL3  != null && !aseguradosCL3.isEmpty() && aseguradosCL3.size() == 1 ){
					log.debug("Se verificara si la persona de cl3 puede usarse");
					Fisica  aseguradoCL3 = (Fisica) aseguradosCL3.get(0);
					log.debug("La curp que trae el asegurado es: " + aseguradoCL3.getCurp() + ", la curp capturada es " + curpCapturado + ", la de renapo es " + fisicaBusqueda.getCurp());
					//se valida que la persona tenga CURP ya sea el captruado o el devuelto por RENAPO
					if(StringUtils.isNotEmpty(aseguradoCL3.getCurp()) && ( 
							aseguradoCL3.getCurp().equalsIgnoreCase(fisicaBusqueda.getCurp())
						
							|| aseguradoCL3.getCurp().equalsIgnoreCase(curpCapturado))){
						
						this.log.debug("entre a asignar a la persona de CL3 idPersona " + aseguradoCL3.getIdPersona());
						aseguradoCL3.setCurp(fisicaBusqueda.getCurp());
						aseguradoCL3.setNss(null);
						aseguradoCL3.setFechaNacimiento(fisicaBusqueda.getFechaNacimiento());
						personasEncontradas.add(aseguradoCL3);
						existePersona = true;
					}
				}
				
				if(!existePersona){
					log.debug("---------------> Lista vacia, se agrega la persona encontrada en RENAPO");
					personasEncontradas.add(fisicaBusqueda);
				}
			}
		//}

		/* 
		 * Una vez que se tenga la lista de personas encontradas, se recorre la lista
		 * y se busca y reemplazan los # por "enies"
		 */
		String capitalNTilde = "\u00D1";
		for (Fisica fisicaAux : personasEncontradas) {
			if (StringUtils.isNotBlank(fisicaAux.getNombre())){
				fisicaAux.setNombre(fisicaAux.getNombre().replaceAll(
						"[#|&]", capitalNTilde));
			}
			
			if (StringUtils.isNotBlank(fisicaAux.getPrimerApellido())){
				fisicaAux.setPrimerApellido(fisicaAux
						.getPrimerApellido().replaceAll("[#|&]", capitalNTilde));
			}
			
			if (StringUtils.isNotBlank(fisicaAux.getSegundoApellido())){
				fisicaAux.setSegundoApellido(fisicaAux
						.getSegundoApellido().replaceAll("[#|&]", capitalNTilde));
			}
		}
		
		return personasEncontradas;
	}

	

	private boolean compararEquivalenciaPersonaRegistrada(Fisica personaRegistrada, Fisica fisicaBusqueda) {
		boolean isPersonaEquivalente;

		Date fechaNacPersonaRegistrada = personaRegistrada.getFechaNacimiento();
		Date fechaNacPersonaBusqueda = fisicaBusqueda.getFechaNacimiento();

		String segundoApellidoReg = personaRegistrada.getSegundoApellido();
		String segundoApellidoBusq = fisicaBusqueda.getSegundoApellido();
		log.debug("---------------> Comparando fecha Nacimiento Capturado:" + fechaNacPersonaBusqueda + " vs IMSS: " + fechaNacPersonaRegistrada);
		log.debug("---------------> Comparando Segundo Apellido Capturado:" + segundoApellidoBusq + " vs IMSS: " + segundoApellidoReg);

		if (fechaNacPersonaRegistrada.compareTo(fechaNacPersonaBusqueda) != 0) {
			isPersonaEquivalente = false;
		} else if(StringUtils.isBlank(segundoApellidoBusq) && StringUtils.isBlank(segundoApellidoReg)){
			isPersonaEquivalente = true;
		} else if(StringUtils.isNotBlank(segundoApellidoBusq) && StringUtils.isNotBlank(segundoApellidoReg)){
			if (segundoApellidoBusq.equals(segundoApellidoReg)) {
				isPersonaEquivalente = true;
			} else {
				isPersonaEquivalente = false;
			}
		} else {
			isPersonaEquivalente = false;
		}

		return isPersonaEquivalente;
	}

	private int contarDatosBasicos(Fisica personaFisica) {
		int numeroCampos = 0;

		if (StringUtils.isNotBlank(personaFisica.getNombre())) {
			log.debug("Nombre capturado");
			numeroCampos++;
		}
		if (StringUtils.isNotBlank(personaFisica.getPrimerApellido())) {
			log.debug("Primer Apellido capturado");
			numeroCampos++;
		}
		if (StringUtils.isNotBlank(personaFisica.getSegundoApellido())) {
			log.debug("Segundo Apellido capturado");
			numeroCampos++;
		}
		if (personaFisica.getSexo() != null && personaFisica.getSexo().getIdSexo() != null
				&& personaFisica.getSexo().getIdSexo() != -1) {
			log.debug("Sexo capturado");
			numeroCampos++;
		}
		if (personaFisica.getFechaNacimiento() != null) {
			log.debug("Fecha Nacimiento capturado");
			numeroCampos++;
		}
		if (personaFisica.getLugarNacimiento() != null) {
			log.debug("Lugar Nacimiento capturado");
			numeroCampos++;
		}

		return numeroCampos;
	}
	
	@Override
	public Fisica validacionesNSS(Fisica fisica,
			boolean validarSolicitudesActivas)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			GenerarNSSException, ErrorComparacionDatosRENAPOException,
			PersonaConNSSException, DomicilioNoLocalizadoException, UmfNoLocalizadaException {
						
		/*
		 * Se busca en RENAPO el CURP capturado, en caso de que el servicio no
		 * encuentre el CURP lanza una excepci�n
		 */
		Fisica fisicaRENAPO = this.localizarPersonaFisicaEnRENAPOServiceBusiness
				.localizarPersonaFisicaEnRENAPOxCURP(fisica.getCurp());

		ValidarAsignacionLocalizacionNssWrapper validacionWrapper = new ValidarAsignacionLocalizacionNssWrapper();
		validacionWrapper.setFisica(fisica);
		validacionWrapper.setValidarSolicitudesActivas(validarSolicitudesActivas);
		validacionWrapper.setValidarCalificaciones(true);
		validacionWrapper.setValidarFormatoCurp(true);

		return validacionesNSSCommon(fisicaRENAPO, validacionWrapper);
	}
	


	@Override
	public Fisica validacionesNSSSinConsultaRENAPO (Fisica fisica, Fisica fisicaRENAPO,
			boolean validarSolicitudesActivas)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			GenerarNSSException, ErrorComparacionDatosRENAPOException,
			PersonaConNSSException, DomicilioNoLocalizadoException, UmfNoLocalizadaException {

		ValidarAsignacionLocalizacionNssWrapper validacionWrapper = new ValidarAsignacionLocalizacionNssWrapper();
		validacionWrapper.setFisica(fisica);
		validacionWrapper.setValidarDomicilioUmf(true);
		validacionWrapper.setValidarSolicitudesActivas(validarSolicitudesActivas);
		validacionWrapper.setValidarCalificaciones(true);
		validacionWrapper.setValidarFormatoCurp(true);

		return validacionesNSSCommon(fisicaRENAPO, validacionWrapper);
	}

	@Override
	public Fisica validacionesNSSSinCalificacion(ValidarAsignacionLocalizacionNssWrapper validacionWrapper)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			GenerarNSSException, ErrorComparacionDatosRENAPOException,
			PersonaConNSSException, DomicilioNoLocalizadoException,
			UmfNoLocalizadaException {

		Fisica fisicaRENAPO = this.localizarPersonaFisicaEnRENAPOServiceBusiness
				.localizarPersonaFisicaEnRENAPOxCURP(validacionWrapper.getFisica().getCurp());

		return validacionesNSSCommon(fisicaRENAPO, validacionWrapper);
	}

	private Fisica validacionesNSSCommon(Fisica fisicaRENAPO,
			ValidarAsignacionLocalizacionNssWrapper validacionWrapper)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			GenerarNSSException, ErrorComparacionDatosRENAPOException,
			PersonaConNSSException, DomicilioNoLocalizadoException,
			UmfNoLocalizadaException {
		Fisica fisica = validacionWrapper.getFisica();

		boolean validarDifSoloFecNac = validacionWrapper.isValidarDifSoloFecNac();
		boolean validarCalificaciones = validacionWrapper.isValidarCalificaciones();
		boolean validarFormatoCurp = validacionWrapper.isValidarFormatoCurp();
		boolean validarDomicilioUmf = validacionWrapper.isValidarDomicilioUmf();
		boolean validaAseguradoCL3 = validacionWrapper.isValidaAseguradoCL3();

		String curpCapturado = fisica.getCurp();
		List<Fisica> personasEncontradas = null;
		Fisica fisicaRENAPOCurpLocalizado = null;
		Fisica fisicaEncontrada = null;

		log.debug("-----------------------------------> CURP_capturado ["
				+ curpCapturado + "] | CURP_renapo [" + fisicaRENAPO.getCurp() + "]");

		// Se buscan NSS a trav�s de la CURP
		personasEncontradas = personaBusiness.obtenerPersonaNssByCurpNoIndActivo(curpCapturado);

		if (personasEncontradas != null && !personasEncontradas.isEmpty()) {
			log.debug("-----------------------------------> Se encontraron " + personasEncontradas.size()
					+ " coincidencias con NSS en la b�squeda por CURP");

			if (personasEncontradas.size() > 1) {
				// La persona cuenta con m�s de un NSS
				log.debug("-----------------------------------> La persona con CURP "
						+ curpCapturado + " cuenta con m�s de un NSS");
				throw new GenerarNSSException(MENSAJE_GENERICO);
			} else {
				fisicaEncontrada = personasEncontradas.get(0);
				return compararPersonaVsRenapo(fisica, fisicaEncontrada, fisicaRENAPO, validarDifSoloFecNac);
			}
		} else {
			log.debug("-----------------------------------> No se encontraron coincidencias con NSS en la b�squeda por CURP");

			List<Fisica> personasConNSS = new ArrayList<Fisica>();
			List<Fisica> personasSinNSS = new ArrayList<Fisica>();
			List<Fisica> personasConCurpConCalificacion = new ArrayList<Fisica>();
			List<Fisica> personasConCurpSinCalificacion = new ArrayList<Fisica>();
			List<Fisica> personasEncontradasAuxConNSS = new ArrayList<Fisica>();

			// Se buscan a las personas que coincidan con datos b�sicos
			try {
				personasEncontradas = personaFisicaServiceBusiness
						.localizarPersonaFisicaPorDatosBasicosEnImssConFechaOMesYAniodeNacimiento(fisicaRENAPO);
				personasEncontradasAuxConNSS = personaFisicaServiceBusiness
						.localizarPersonaFisicaPorDatosBasicosEnImssConNSS(fisicaRENAPO);
			} catch (DatosInsuficientesParaConsultaException e) {
				log.error(e);
			}

			this.log.debug("-----------------------------------> Conjunto de personas localizadas por datos b�sicos "
					+ personasEncontradas.size());
			this.log.debug("-----------------------------------> Conjunto de personas localizadas por datos b�sicos con NSS "
					+ personasEncontradasAuxConNSS.size());

			/*
			 * Se genera los subconjuntos de persona con NSS y sin NSS a partir
			 * de las personas encontradas por datos b�sicos
			 */
			for (Fisica fisicaAux : personasEncontradas) {
				if (StringUtils.isBlank(fisicaAux.getNss())) {
					personasSinNSS.add(fisicaAux);
				}
			}

			/*
			 * Se separa en 2 consultas ya que ahora una persona puede tener mas
			 * de un NSS
			 */
			for (Fisica fisicaAux : personasEncontradasAuxConNSS) {
				if (StringUtils.isNotBlank(fisicaAux.getNss())) {
					personasConNSS.add(fisicaAux);
				}
			}

			// Se buscan a las personas que coincidan con el curp capturado
			personasEncontradas = personaBusiness
					.buscarPersonaFisicaPorCurpEnImss(curpCapturado);

			this.log.debug("Conjunto de personas localizadas por curp capturado "
					+ personasEncontradas.size());

			/*
			 * Se genera los subconjuntos de personas con y sin calificaiones
			 * vigentes a partir del curp capturado
			 */
			for (Fisica fisicaAux : personasEncontradas) {
				if (StringUtils.isNotBlank(fisicaAux.getCurp())) {
					obtenerCalificacionesVigentesCommon(fisicaAux);

					if (fisicaAux.getPersonaCalificaciones() != null
							&& !fisicaAux.getPersonaCalificaciones().isEmpty()) {
						personasConCurpConCalificacion.add(fisicaAux);
					} else {
						personasConCurpSinCalificacion.add(fisicaAux);
					}
				}
			}

			log.debug("Subconjunto de personas con NSS " + personasConNSS.size());
			log.debug("Subconjunto de personas sin NSS " + personasSinNSS.size());
			log.debug("Subconjunto de personas con CURP y calificaciones " + personasConCurpConCalificacion.size());
			log.debug("Subconjunto de personas con CURP y sin calificaciones " + personasConCurpSinCalificacion.size());

			if (!personasConNSS.isEmpty()) {
				/*
				 * Existen personas que coincidieron por datos b�sicos y tienen
				 * NSS
				 */
				log.debug("-----------------------------------> Se encontraron " + personasConNSS.size()
						+ " que coincidieron en datos b�sicos y tienen NSS");

				if (personasConNSS.size() == 1) {
					fisicaEncontrada = personasConNSS.get(0);
					log.debug("-----------------------------------> Persona localizada [idPersona:"
							+ fisicaEncontrada.getIdPersona() + "]");

					// Se checa si la persona cuenta con CURP
					validarFormatoCurp(fisicaEncontrada, validarFormatoCurp);

					// Se valida si la persona cuenta con calificaciones
					obtenerCalificacionesVigentesCommon(fisicaEncontrada);
					validarCalificaciones(fisicaEncontrada, validarCalificaciones);

					String curpEncontrado = fisicaEncontrada.getCurp();
					if (StringUtils.isNotBlank(curpEncontrado)) {
						
						if (curpEncontrado.equalsIgnoreCase("000000000000000000")
								|| curpEncontrado.length() != LONGITUD_CURP
								|| !curpEncontrado.matches(REGEX_CURP_FISICA)) {
							fisicaEncontrada.setCurp(null);
							return fisicaEncontrada;
						} else {
							fisicaRENAPOCurpLocalizado = personaBusiness.buscarPersonaFisicaPorCurpEnRenapo(
									fisicaEncontrada.getCurp());

							if (fisicaRENAPOCurpLocalizado != null) {
								String curpRenapoPersonaEncontrada = fisicaRENAPOCurpLocalizado.getCurp();
								String curpRenapo = fisicaRENAPO.getCurp();
			
								if (curpRenapoPersonaEncontrada.equals(curpRenapo)) {
									return compararPersonaVsRenapo(fisica,
											fisicaEncontrada, fisicaRENAPO,
											validarDifSoloFecNac);
								} else {
									throw new GenerarNSSException(MENSAJE_ERROR_COMPARACION);
								}
							} else {
								throw new GenerarNSSException(MENSAJE_ERROR_COMPARACION);
							}
						}
					} else {
						return fisicaEncontrada;
					}
				} else {
					/*
					 * Se encontraron varias coincidencias, por lo tanto, se
					 * manda a ventanilla
					 */
					log.debug("-----------------------------------> La persona con CURP "
							+ curpCapturado + " tiene varias coincidencias");

					throw new GenerarNSSException(MENSAJE_GENERICO);
				}
			} else {

				/*
				 * No existen personas que coincidieron por datos b�sicos y que
				 * tengan NSS
				 */

				log.debug("-----------------------------------> Se encontraron " + personasConCurpConCalificacion.size()
						+ " que coincidieron en datos b�sicos y cuentan con CURP y calificaciones");

				if (!personasConCurpConCalificacion.isEmpty()
						&& personasConCurpConCalificacion.size() == 1) {

					fisicaEncontrada = personasConCurpConCalificacion.get(0);
					try{
						if(validaAseguradoCL3){
							log.debug("---------------> no se encontraron personas, buscan personas de CL3 para persona con calificacion");
							//se valida si no existe un antecedente como estudiante con datos incompletos de fecha de nacimiento

							List <AsignacionNSS> aseguradosCL3 =personaFisicaServiceBusiness.localizarNssCl3PorDatosBasicosSinFechaNac(fisicaRENAPO);
							//se valida que la consulta solo traiga un registro por datos basicos
							if(aseguradosCL3  != null && !aseguradosCL3.isEmpty() && aseguradosCL3.size() == 1 ){
								Fisica  aseguradoCL3 = (Fisica) aseguradosCL3.get(0);
								//se valida que la persona tenga CURP ya sea el captruado o el devuelto por RENAPO
								if(StringUtils.isNotEmpty(aseguradoCL3.getCurp()) && ( 
										aseguradoCL3.getCurp().equalsIgnoreCase(fisicaRENAPO.getCurp())

										|| aseguradoCL3.getCurp().equalsIgnoreCase(curpCapturado))
										&& aseguradoCL3.getIdPersona() == fisicaEncontrada.getIdPersona()){

									this.log.debug("entre a asignar a la persona de CL3 idPersona " + aseguradoCL3.getIdPersona());
									aseguradoCL3.setCurp(fisicaRENAPO.getCurp());
									aseguradoCL3.setNss(null);
									aseguradoCL3.setFechaNacimiento(fisicaRENAPO.getFechaNacimiento());
									return agregarCalificacionFisicaRenapo(aseguradoCL3);
								}
							}

						}
					}catch(DatosInsuficientesParaConsultaException e){
						log.error("los datos nos son validos");
						throw new GenerarNSSException(MENSAJE_GENERICO);
					}
					
					return compararPersonaEncontradaVsRenapo(fisica, fisicaEncontrada,
							fisicaRENAPO, validarDifSoloFecNac, validarDomicilioUmf);
				} else if (!personasConCurpSinCalificacion.isEmpty()) {
					log.debug("-----------------------------------> Se encontraron " + personasConCurpSinCalificacion.size()
							+ " que coincidieron en datos b�sicos y cuentan con CURP y no tienen calificaciones");

					if (personasConCurpSinCalificacion.size() == 1) {
						fisicaEncontrada = personasConCurpSinCalificacion.get(0);
						try{
							if(validaAseguradoCL3){
								log.debug("---------------> no se encontraron personas, buscan personas de CL3 para persona sin calificacion");
								//se valida si no existe un antecedente como estudiante con datos incompletos de fecha de nacimiento
								List <AsignacionNSS> aseguradosCL3 =personaFisicaServiceBusiness.localizarNssCl3PorDatosBasicosSinFechaNac(fisicaRENAPO);
								//se valida que la consulta solo traiga un registro por datos basicos
								log.debug("<--------------------> los datos de la localizada son [" +fisicaEncontrada.getCurp() +" curp capturado " +curpCapturado
										+" y idPersona " + fisicaEncontrada.getIdPersona());
								if(aseguradosCL3  != null && !aseguradosCL3.isEmpty() && aseguradosCL3.size() == 1 ){
									
									Fisica  aseguradoCL3 = (Fisica) aseguradosCL3.get(0);
									log.debug("<--------------------> entre al if de un solo registro con CURP [" + aseguradoCL3.getCurp()+ "] y cveidPersona" +aseguradoCL3.getIdPersona()  );
									//se valida que la persona tenga CURP ya sea el captruado o el devuelto por RENAPO
									if(StringUtils.isNotEmpty(aseguradoCL3.getCurp()) && ( 
											aseguradoCL3.getCurp().equalsIgnoreCase(fisicaEncontrada.getCurp())
											|| aseguradoCL3.getCurp().equalsIgnoreCase(curpCapturado))
											&& aseguradoCL3.getIdPersona().equals(fisicaEncontrada.getIdPersona())){
										
										this.log.debug("entre a asignar a la persona de CL3 idPersona " + aseguradoCL3.getIdPersona());
										aseguradoCL3.setCurp(fisicaRENAPO.getCurp());
										aseguradoCL3.setNss(null);
										aseguradoCL3.setFechaNacimiento(fisicaRENAPO.getFechaNacimiento());
										return agregarCalificacionFisicaRenapo(aseguradoCL3);
									}
								}
								
							}
						}catch(DatosInsuficientesParaConsultaException e){
							log.error("los datos nos son validos");
							throw new GenerarNSSException(MENSAJE_GENERICO);
						}

						return compararPersonaEncontradaVsRenapo(fisica, fisicaEncontrada,
								fisicaRENAPO, validarDifSoloFecNac, validarDomicilioUmf);
					} else {
						/*
						 * Se encontraron varias coincidencias, por lo tanto, se
						 * manda a ventanilla
						 */
						log.debug("-----------------------------------> La persona con CURP "
								+ curpCapturado + " tiene varias coincidencias");
						throw new GenerarNSSException(MENSAJE_GENERICO);
					}
				} else {
					// No se encontraron registros coincidentes por CURP
					log.debug("-----------------------------------> Se encontraron " + personasSinNSS.size()
							+ " que coincidieron en datos b�sicos y no tienen NSS");

					if (!personasSinNSS.isEmpty()) {
						if (personasSinNSS.size() == 1) {
							fisicaEncontrada = personasSinNSS.get(0);

							log.debug("-----------------------------------> Persona localizada [idPersona:"
									+ fisicaEncontrada.getIdPersona() + "]");

							// Se checa si la persona cuenta con CURP
							validarFormatoCurp(fisicaEncontrada, validarFormatoCurp);

							// Se valida si la persona cuenta con calificaciones
							obtenerCalificacionesVigentesCommon(fisicaEncontrada);
							validarCalificaciones(fisicaEncontrada, validarCalificaciones);

							String curpEncontrado = fisicaEncontrada.getCurp();
							if (StringUtils.isNotBlank(curpEncontrado)) {
								if (curpEncontrado.equalsIgnoreCase("000000000000000000")
										|| curpEncontrado.length() != LONGITUD_CURP
										|| !curpEncontrado.matches(REGEX_CURP_FISICA)) {
									fisicaEncontrada.setCurp(null);
									return fisicaEncontrada;
								} else {
									fisicaRENAPOCurpLocalizado = personaBusiness
											.buscarPersonaFisicaPorCurpEnRenapo(fisicaEncontrada.getCurp());

									if (fisicaRENAPOCurpLocalizado != null) {
										String curpRenapoPersonaEncontrada = fisicaRENAPOCurpLocalizado.getCurp();
										String curpRenapo = fisicaRENAPO.getCurp();
			
										if (curpRenapoPersonaEncontrada.equals(curpRenapo)) {
											return compararPersonaEncontradaVsRenapo(
													fisica, fisicaEncontrada, fisicaRENAPO,
													validarDifSoloFecNac, validarDomicilioUmf);
										} else {
											throw new GenerarNSSException(MENSAJE_ERROR_COMPARACION);
										}
									} else {
										throw new GenerarNSSException(MENSAJE_ERROR_COMPARACION);
									}
								}
							} else {
								return fisicaEncontrada;
							}
						} else {
							/*
							 * Se encontraron varias coincidencias, por lo
							 * tanto, se manda a ventanilla
							 */
							log.debug("-----------------------------------> La persona con CURP " + curpCapturado
									+ " tiene varias coincidencias");
							throw new GenerarNSSException(MENSAJE_GENERICO);
						}
					} else {
							log.debug("---------------> Lista vacia, se agrega la persona encontrada en RENAPO");
						return agregarCalificacionFisicaRenapo(fisicaRENAPO);
					}
				}
			}
		}
	}

	private void validarCalificaciones(Fisica fisicaEncontrada,
			boolean validarCalificaciones) throws GenerarNSSException {
		if (fisicaEncontrada.getPersonaCalificaciones() != null
				&& !fisicaEncontrada.getPersonaCalificaciones().isEmpty()) {
			log.debug("-----------------------------------> La persona localizada cuenta con calificaciones");
		} else if (validarCalificaciones) {
			log.warn("-----------------------------------> La persona localizada no cuenta con calificaciones");
			throw new GenerarNSSException(MENSAJE_GENERICO);
		}
	}

	private void validarFormatoCurp(Fisica fisicaEncontrada,
			boolean validarFormatoCurp)
			throws ClienteWebserviceRenapoCurpException, GenerarNSSException {
		String curpFisicaEncontrada = fisicaEncontrada.getCurp();

		// Se checa si la persona cuenta con CURP
		if (StringUtils.isNotBlank(curpFisicaEncontrada)) {
			this.log.debug("La persona localizada cuenta con CURP");

			if (validarFormatoCurp
					&& (curpFisicaEncontrada.equalsIgnoreCase("000000000000000000")
							|| curpFisicaEncontrada.length() != LONGITUD_CURP
							|| !curpFisicaEncontrada.matches(REGEX_CURP_FISICA))) {
				log.error("-----------------------------------> La curp de la pesona [" + fisicaEncontrada.getIdPersona()
						+ "] localizada en el IMSS es igual a 000000000000000000");
				throw new ClienteWebserviceRenapoCurpException(
						"Mensaje: No se localiz� informaci�n en RENAPO con la CURP (000000000000000000) que se localiz� en el Instituto. Para poder realizar el tr�mite deber� presentarse en una subdelegaci�n del Instituto.",
						10005);
			}
		} else if (validarFormatoCurp) {
			log.warn("-----------------------------------> La persona localizada no cuenta con CURP");
			throw new GenerarNSSException(MENSAJE_GENERICO);
		}
	}

	private Fisica compararPersonaEncontradaVsRenapo(Fisica fisica,
			Fisica fisicaEncontrada, Fisica fisicaRENAPO,
			boolean validarDifSoloFecNac, boolean validarDomicilioUmf)
			throws ErrorComparacionDatosRENAPOException,
			DomicilioNoLocalizadoException, UmfNoLocalizadaException,
			GenerarNSSException {
		String curpCapturado = fisica.getCurp();

		log.debug("-----------------------------------> Persona localizada [idPersona:"
				+ fisicaEncontrada.getIdPersona() + "]");

		boolean exitoComparacion = personaFisicaServiceBusiness
				.comparaDatosBasicosRENAPO(fisicaRENAPO, fisicaEncontrada);
		int numDiferencias = personaFisicaServiceBusiness
				.comparaDiferenciaDatosBasicosRENAPO(fisicaRENAPO, fisicaEncontrada);

		if (exitoComparacion) {
			log.debug("-----------------------------------> Se encontr� persona [idPersona: "
					+ fisicaEncontrada.getIdPersona() + "] para asignar NSS");

			if (validarDomicilioUmf) {
				/*
				 * Se busca el domicilio particular de la persona encontrada
				 */
				obtenerDomicilioParticularPersonaAsegurar(fisicaEncontrada);
			}

			return fisicaEncontrada;
		} else {
			/*
			 * Los datos b�sicos no coincidieron, se debe mandar a ventanilla
			 */
			log.warn("-----------------------------------> Los datos de la persona con CURP " + curpCapturado
					+ " no coinciden con RENAPO");

			if (validarDifSoloFecNac && numDiferencias == 1
					&& diferenciaSolFecNacimiento(fisicaRENAPO, fisicaEncontrada)) {
				return fisicaEncontrada;
			} else {
				throw new GenerarNSSException(MENSAJE_ERROR_COMPARACION);
			}
		}
	}

	private Fisica compararPersonaVsRenapo(Fisica fisica,
			Fisica fisicaEncontrada, Fisica fisicaRENAPO,
			boolean validaDifSoloFecNac)
			throws ErrorComparacionDatosRENAPOException,
			PersonaConNSSException, GenerarNSSException {
		Long idPersonaPortal = fisica.getIdPersona();
		String curpCapturado = fisica.getCurp();

		log.debug("-----------------------------------> Se encontr� una coincidencia con NSS en la b�squeda por CURP [idPersona:"
				+ fisicaEncontrada.getIdPersona() + "]");

		boolean exitoComparacion = personaFisicaServiceBusiness
				.comparaDatosBasicosRENAPO(fisicaRENAPO, fisicaEncontrada);
		int numDiferencias = personaFisicaServiceBusiness
				.comparaDiferenciaDatosBasicosRENAPO(fisicaRENAPO, fisicaEncontrada);

		if (exitoComparacion) {
			/*
			 * Los datos b�sicos coincidieron, por lo tanto, se localiz� el NSS
			 */
			if (idPersonaPortal != null
					&& !idPersonaPortal.equals(fisicaEncontrada.getIdPersona())) {
				/*
				 * Si se viene del portal (ya se tiene id persona) se comparan
				 * los idPersona para saber si se tiene que reasignar el NSS
				 */
				// TODO el codigo del metodo se comento y se explica el porque
				// en el metodo
				cambiarDuenioNSS(fisicaEncontrada.getNss(),
						fisicaEncontrada.getIdPersona(), idPersonaPortal);
			}

			log.debug("-----------------------------------> La persona con CURP " + curpCapturado
					+ " ya cuenta con NSS [" + fisicaEncontrada.getNss() + "]");

			throw new PersonaConNSSException(fisicaEncontrada);
		} else {
			/*
			 * Los datos b�sicos no coincidieron, se debe mandar a ventanilla
			 */
			log.warn("-----------------------------------> Los datos de la persona con CURP " + curpCapturado
					+ " no coinciden con RENAPO");

			if (validaDifSoloFecNac && numDiferencias == 1
					&& diferenciaSolFecNacimiento(fisicaRENAPO, fisicaEncontrada)) {
				return fisicaEncontrada;
			} else {
				throw new GenerarNSSException(MENSAJE_ERROR_COMPARACION);
			}
		}
	}

	private Fisica agregarCalificacionFisicaRenapo(Fisica fisicaRENAPO) {
		/*
		 * No se cumpli� ninguna de las condiciones, se debe crear una nueva
		 * persona con calificaci�n RENAPO y la solicitud para NSS
		 */
		log.debug("-----------------------------------> Se va a crear una persona nueva y asignar NSS");

		Calificacion calificacion = new Calificacion();
		calificacion.setIdCalificacion(CalificacionEnum.VALIDADO_RENAPO.getCodigo().longValue());
		calificacion.setDescripcion(CalificacionEnum.VALIDADO_RENAPO.getDescripcion());
		PersonaCalificacion personaCalificacion = new PersonaCalificacion();
		personaCalificacion.setCalificacion(calificacion);
		personaCalificacion.setFechaCalificacion(new Date());

		if (fisicaRENAPO.getPersonaCalificaciones() == null) {
			fisicaRENAPO.setPersonaCalificaciones(new ArrayList<PersonaCalificacion>());
		} else {
			fisicaRENAPO.getPersonaCalificaciones().clear();
		}

		fisicaRENAPO.getPersonaCalificaciones().add(personaCalificacion);

		return fisicaRENAPO;
	}

	private boolean diferenciaSolFecNacimiento(Fisica fisicaRENAPO,
			Fisica fisicaEncontrada) {
		boolean isSoloDiferenciaFechaNac = false;
		Date fechaNacimientoRenapo = fisicaRENAPO.getFechaNacimiento();

		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");

		if (fisicaEncontrada.getFechaNacimiento() == null
				&& fisicaEncontrada.getMesRegistroNac() != null
				&& fisicaEncontrada.getAnioRegistroNac() != null) {
			String strFechaFormatoSimple = sdf.format(fechaNacimientoRenapo);
			String strAnioNacimientoRenapo = StringUtils.stripStart(strFechaFormatoSimple.substring(0, 2), "0");
			String strMesNacimientoRenapo = StringUtils.stripStart(strFechaFormatoSimple.substring(2, 4), "0");

			int anioNacimientoRenapo = NumberUtils.toInt(strAnioNacimientoRenapo);
			int mesNacimientoRenapo = NumberUtils.toInt(strMesNacimientoRenapo);

			if (anioNacimientoRenapo == fisicaEncontrada.getAnioRegistroNac()
					&& mesNacimientoRenapo == fisicaEncontrada.getMesRegistroNac()) {
				isSoloDiferenciaFechaNac = true;
			}
		}

		return isSoloDiferenciaFechaNac;
	}

	@Override
	public Map<String, Object> crearSolicitudRecuperacionNSS(Fisica fisica,
			OrigenSolicitudEnum origenAsignacion, Usuario usuario)
			throws SolicitudNoValidaException, SolicitudException {
		
		Map<String, Object> resultado = new HashMap<String, Object>();
		this.log.info("Se va a crear solicitud de recuperacion de la persona [idPersona:"
				+ fisica.getIdPersona()
				+ ", curp:"
				+ fisica.getCurp()
				+ "] de NSS con origen " + origenAsignacion.getDesc());
		
		TramiteAsegurado tramiteAsegurado = new TramiteAsegurado();
		AsignacionNSS asignacionNSS = new AsignacionNSS();
		Date fechaActual = new Date();
		Solicitud solicitud = new Solicitud();
		List<Tramite> tramites = new ArrayList<Tramite>();
		
		try {
			ConvertUtils.register(new DateConverter(null), Date.class);
			BeanUtils.copyProperties(asignacionNSS, fisica);
			tramiteAsegurado.setFisica(asignacionNSS);
			
			if (fisica.getIdPersona() == null) {
				asignacionNSS.setIdPersona(null);
			}
			
			this.log.debug("Copia de parametros para solicitud exitosa");
			
		} catch (IllegalAccessException e) {
			this.log.error(e.getStackTrace());
		} catch (InvocationTargetException e) {
			this.log.error(e.getStackTrace());
		}
		
		TipoSolicitud tipoSolicitud = new TipoSolicitud();
		tipoSolicitud.setIdTipoSolicitud(mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum.ASIGNACION_NSS
						.getValor().longValue());
		solicitud.setTipoSolicitud(tipoSolicitud);
		
		EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
		estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.ATENDIDA
				.getCodigo());
		solicitud.setEstadoSolicitud(estadoSolicitud);

		solicitud.setFechaSolicitud(fechaActual);
		solicitud.setFechaPresentacion(fechaActual);
		solicitud.setFechaConclusion(fechaActual);
		
		// Se settea el origen de la solicitud
		OrigenSolicitud origenSolicitud = new OrigenSolicitud();
		origenSolicitud.setIdTipoSolicitud(origenAsignacion.getId());
		solicitud.setOrigenSolicitud(origenSolicitud);
		
		tramiteAsegurado.setFechaTramite(fechaActual);
		tramiteAsegurado.setFechaPresentacion(fechaActual);
		tramiteAsegurado.setFechaConclusion(fechaActual);
		
		
		TipoTramite tipoTramite = new TipoTramite();
		tipoTramite.setIdTipoTramite(TipoTramiteEnum.LOCALIZACION_NSS.getCodigo());
		tramiteAsegurado.setTipoTramite(tipoTramite);
		
		EstadoTramite estadoTramite = new EstadoTramite();
		estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
		estadoTramite.setDescripcion(EstadoTramiteEnum.CERRADO.getDescripcion());
		tramiteAsegurado.setEstadoTramite(estadoTramite);
		
		tramites.add(tramiteAsegurado);
				
		solicitud.setTramites(tramites);
		
		SujetoObligado sujetoObligado = new SujetoObligado();
		
		sujetoObligado.setFisica(fisica);
		solicitud.setSujetoObligado(sujetoObligado);
		
		/*
		 * se setea el usuario siempre para saber quien fue el que realizo el tramite
		 */
		//if (origenAsignacion.getId() == OrigenSolicitudEnum.VENTANILLA.getId()
		 if ( usuario != null) {
						
			UsuarioFuncionario uf = usuario.getUsuarioFuncionario();
			
			if (uf != null && uf.getSubdelegacion() != null
					&& uf.getSubdelegacion().getId() != null
					&& !uf.getSubdelegacion().getId().equals(-1)) {
				
				/* 
				 * Debido a que a nivel base de datos, s�lo se puede
				 * relacionar una solicitud a nivel subdelegacional,
				 * no se toma en cuenta el nivel delegacional
				 */
				solicitud.setSubdelegacion(uf.getSubdelegacion());
			
				this.log.debug("El usuario tiene nivel subdelegacional");
			}
			this.log.debug("el usuario es" + usuario.getUsuario());
			solicitud.setSolicitante(usuario);
		}
		
		// Se crea la solicitud en base de datos
		solicitud = this.solicitudBusiness.crear(solicitud);

		this.log.debug("Se generara comprobante de localizacion de nss");
		//byte[] comprobanteLocalizacion = null;
		byte[] comprobanteLocalizacion = solicitudBusiness.obtenerDocumentoResultante(solicitud, null, DocumentoPorTipoEnum.COMPROBANTE_LOCALIZACION_NSS.getId().intValue());
		//generamos el comprobante con QR
		byte[] comprobanteQR = solicitudBusiness.obtenerDocumentoResultante(solicitud, null, DocumentoPorTipoEnum.COMPROBANTE_ASIGNACION_SIMPLE_QR.getId().intValue());
		
		if(comprobanteLocalizacion == null) {
			this.log.debug("hubo un error al generar el comprobante de localizacion de nss");
		} else {
			this.log.debug("El comprobante de localizacion de nss se genero correctamente");
			resultado.put("comprobante", comprobanteLocalizacion);
		}
		
		if(comprobanteQR == null ) {
			this.log.debug("hubo un error al generar el comprobante de localizacion de nss con QR");
		} else {
			this.log.debug("El comprobante de localizacion de nss se genero correctamente");
			resultado.put("comprobanteQR", comprobanteQR);
		}
		
		resultado.put("solicitud", solicitud);
		this.log.debug("Se creo exitosamente la solicitud de recuperacion de NSS");
		
		return resultado;
	}

	@Override
	public Solicitud crearSolicitudAsignacionNSS(Fisica fisica,
			TipoSerieEnum tipoSerie, OrigenSolicitudEnum origenAsignacion,
			String curpRENAPO, SolicitudNssCorreo nssCorreo, Usuario usuario,
			ModuloOrigenAsignacionEnum moduloOrigen)
			throws SolicitudNoValidaException, SolicitudException {
		
		this.log.debug("Se inicia la creacion de la solicitud de asignacion NSS con origen "
				+ origenAsignacion.getDesc() + " desde el modulo de " + moduloOrigen.getDesc());
		
		Date fechaActual = new Date();
		Solicitud solicitud = new Solicitud();
		AsignacionNSS asignacionNSS = new AsignacionNSS();
		TramiteAsegurado tramite = new TramiteAsegurado();
		List<Tramite> tramites = new ArrayList<Tramite>();
		UsuarioFuncionario uf = null;
		
		if (fisica.getLugarNacimiento() != null && StringUtils.isBlank(fisica.getLugarNacimiento().getNombre())) {
			fisica.setLugarNacimiento(this.domicilioServiceBusiness.getEstado(fisica.getLugarNacimiento().getClave()));
		}
		
		try {
			ConvertUtils.register(new DateConverter(null), Date.class);
			BeanUtils.copyProperties(asignacionNSS, fisica);
			tramite.setFisica(asignacionNSS);
			
			if (fisica.getIdPersona() == null) {
				asignacionNSS.setIdPersona(null);
			}
			
			this.log.debug("Copia de parametros para solicitud exitosa");
			
		} catch (IllegalAccessException e) {
			this.log.error(e);
		} catch (InvocationTargetException e) {
			this.log.error(e);
		}
		
		if(usuario != null) {
			uf = usuario.getUsuarioFuncionario();
		}
		
		AsignacionSerieNSS asignacionSerie = new AsignacionSerieNSS();
		
		this.log.debug("El tipo de serie recibido es -> " + tipoSerie);
		if (tipoSerie == null) {
			tipoSerie = TipoSerieEnum.ORDINARIA;
			this.log.debug("El tipo de serie recibido es nulo, se pone por default serie ORDINARIA");
		}
		
		TipoSerie tipoSerieObj = new TipoSerie();
		tipoSerieObj.setIdTipoSerie(tipoSerie.getClave());
		Serie serie = new Serie();
		serie.setTipoSerie(tipoSerieObj);
		
		if (uf != null) {
			asignacionSerie.setDelegacion(uf.getDelegacion());
			asignacionSerie.setSubdelegacion(uf.getSubdelegacion());
		}
				
		asignacionSerie.setSerie(serie);
		
		tramite.setAsignacionSerieNss(asignacionSerie);
		tramite.setCurpRENAPO(curpRENAPO);
		tramite.setNssCorreo(nssCorreo);
		
		TipoSolicitud tipoSolicitud = new TipoSolicitud();
		tipoSolicitud.setIdTipoSolicitud(mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum.ASIGNACION_NSS
						.getValor().longValue());
		solicitud.setTipoSolicitud(tipoSolicitud);
		
		EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
		estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.REGISTRADA
				.getCodigo());
		solicitud.setEstadoSolicitud(estadoSolicitud);

		solicitud.setFechaSolicitud(fechaActual);
		solicitud.setFechaPresentacion(fechaActual);
		
		/*
		 * Se settea el origen de la solicitud, siempre y cuando sea diferente
		 * de INTERNET, ya que por default la solicitud ya trae el origen
		 * INTERNET
		 */
		if (!origenAsignacion.getId().equals(OrigenSolicitudEnum.INTERNET.getId())) {
			OrigenSolicitud origenSolicitud = new OrigenSolicitud();
			origenSolicitud.setIdTipoSolicitud(origenAsignacion.getId());
			solicitud.setOrigenSolicitud(origenSolicitud);
		}
		
		SujetoObligado sujetoObligado = new SujetoObligado();

		tramite.setFechaTramite(fechaActual);
		tramite.setFechaPresentacion(fechaActual);
		
		TipoTramite tipoTramite = new TipoTramite();
		tipoTramite.setIdTipoTramite(TipoTramiteEnum.ASIGNACION_NSS.getCodigo());
		tramite.setTipoTramite(tipoTramite);
		
		EstadoTramite estadoTramite = new EstadoTramite();
		estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO.getCodigo());
		estadoTramite.setDescripcion(EstadoTramiteEnum.INICIADO.getDescripcion());
		tramite.setEstadoTramite(estadoTramite);
		
		switch (moduloOrigen) {
		case VENTANILLA:
			tramite.setOrigenAsegurado("ASEGURADO CREADO DESDE MODULO VENTANILLA");
			break;
		case INTERNET:
			tramite.setOrigenAsegurado("ASEGURADO CREADO DESDE MODULO INTERNET");
			break;
		case SIE:
			tramite.setOrigenAsegurado("ASEGURADO CREADO DESDE MODULO ESTUDIANTES");
			break;
		case SIME:
			tramite.setOrigenAsegurado("ASEGURADO CREADO DESDE MODULO SIME");
			break;
		case MOVILES:
			tramite.setOrigenAsegurado("ASEGURADO CREADO DESDE APLICACI�N MOVIL");
			break;
		default:
			break;
	}
		
		tramites.add(tramite);
		
		/*
		 * Se checa si la persona tiene id, si no lo
		 * tiene significa que es una persona nueva que se va a dar de alta, por
		 * lo tanto, se debe crear un tr�mite para la creaci�n de la persona,
		 * asociado a la misma solicitud
		 */
		if (fisica.getIdPersona() == null) {
			
			this.log.debug("La persona es nueva dentro de la asignacion de NSS, se crea un tramite de REGISTRO DE PERSONA");
			
			TramiteFisica tramiteRegistro = new TramiteFisica();

			tramiteRegistro.setFisica(fisica);
			
			tipoTramite = new TipoTramite();
			tipoTramite.setIdTipoTramite(TipoTramiteEnum.REGISTRO_DE_PERSONA.getCodigo());
			
			tramiteRegistro.setTipoTramite(tipoTramite);
			tramiteRegistro.setEstadoTramite(estadoTramite);
			
			tramiteRegistro.setFechaTramite(fechaActual);
			tramiteRegistro.setFechaPresentacion(fechaActual);
			
			tramites.add(tramiteRegistro);
		}
		
		solicitud.setTramites(tramites);
		
		sujetoObligado.setFisica(fisica);
		solicitud.setSujetoObligado(sujetoObligado);
		
		/*
		 * Se checa el origen de la solicitud, si es ventanilla y el usuario es
		 * diferente de nulo, la solicitud se tiene que relacionar al usuario
		 * firmado
		 */
		if (origenAsignacion.getId() == OrigenSolicitudEnum.VENTANILLA.getId()
				&& usuario != null) {
						
			solicitud.setSolicitante(usuario);
			
			if (uf != null && uf.getSubdelegacion() != null
					&& uf.getSubdelegacion().getId() != null
					&& !uf.getSubdelegacion().getId().equals(-1)) {
				
				/* 
				 * Debido a que a nivel base de datos, s�lo se puede
				 * relacionar una solicitud a nivel subdelegacional,
				 * no se toma en cuenta el nivel delegacional
				 */
				solicitud.setSubdelegacion(uf.getSubdelegacion());
			
				this.log.debug("El usuario tiene nivel subdelegacional");
			} 
		}
			
		this.log.debug("La solicitud de asignacion fue creada exitosamente!");
		
		return solicitud;
	}
	
	@Override
	public Solicitud guardarSolicitudAsignacionNSS(Solicitud solicitud,
			UnidadMedicaFamiliar umf, FirmaElectronica firmaElectronica)
			throws SolicitudNoValidaException {
		
		this.log.debug("Solicitud de NSS a guardar -> " + solicitud);
		
		// Se checa si viene la UMF
		if (umf != null) {
			// Se recorren los tramites
			for (Tramite tramite : solicitud.getTramites()) {
				if (tramite instanceof TramiteAsegurado) {
					TramiteAsegurado tramiteAsegurado = (TramiteAsegurado) tramite;
					tramiteAsegurado.getFisica().setUmf(umf);
				}
			}
		} else {
			this.log.warn("No se recibi� ninguna UMF para la creaci�n del la solicitud de Asignaci�n de NSS");
		}
		
		// Se crea la solicitud en base de datos
		solicitud = this.solicitudBusiness.crear(solicitud);
		
		if (firmaElectronica != null) {
			firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud, firmaElectronica);
		}
		
		this.log.debug("Solicitud de NSS guardada -> " + solicitud);
		
		return solicitud;
	}
	
	@Override
	public AseguradoWrapper procesarSolicitudAsignacionNSS(Long idSolicitud)
			throws SolicitudNoEncontradaException,
			AfectacionDatosPersonaException, PersonaNoEncontradaException,
			DomicilioNoValidoException,
			NivelDeAsignacionSerieIndefinidoException,
			SeriesNoLocalizadasException, ErrorAlActivarSerieException,
			TramiteNoEncontradoException,
			CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			PersonaSinCalificacionesException, SolicitudNoValidaException {
		
		this.log.info("Se inicia la finalizacion de la solicitud de Asignacion de NSS con id -> "
				+ idSolicitud);
		
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(idSolicitud);

		// Se busca la solicitud
		solicitud = solicitudBusiness.consultar(solicitud);
		AseguradoWrapper wrapper = null;
		wrapper = procesarSolicitudAsignacionNSSCommon(solicitud);
		return wrapper;
	}
	
	@Override
	public AseguradoWrapper procesarSolicitudAsignacionNSSSinConsulta(
			Solicitud solicitud) throws SolicitudNoEncontradaException,
			AfectacionDatosPersonaException, PersonaNoEncontradaException,
			DomicilioNoValidoException,
			NivelDeAsignacionSerieIndefinidoException,
			SeriesNoLocalizadasException, ErrorAlActivarSerieException,
			TramiteNoEncontradoException,
			CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			PersonaSinCalificacionesException, SolicitudNoValidaException {
		
		this.log.info("Se inicia la finalizacion (sin consulta) de la solicitud de Asignacion de NSS con id -> "
				+ solicitud.getSolicitudId());
				
		return procesarSolicitudAsignacionNSSCommon(solicitud);
		
	}
		
	private AseguradoWrapper procesarSolicitudAsignacionNSSCommon(Solicitud solicitud)
			throws SolicitudNoEncontradaException,
			AfectacionDatosPersonaException, PersonaNoEncontradaException,
			DomicilioNoValidoException,
			NivelDeAsignacionSerieIndefinidoException,
			SeriesNoLocalizadasException, ErrorAlActivarSerieException,
			TramiteNoEncontradoException,
			CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			PersonaSinCalificacionesException, SolicitudNoValidaException {
		
		Long idSolicitud = solicitud.getSolicitudId();
		
		// Bandera para indicar si se debe guardar o no el domicilio
		boolean datosComplementariosGuardados = false;
		AsignacionSerieNSS asignacionSerie = null;
				
		List<Tramite> tramites = solicitud.getTramites();
		AsignacionNSS fisica = null;
		Fisica fisicaNueva = null;
		String nss = null;
		UnidadMedicaFamiliar umf = null;
		AseguradoWrapper aseguradoWrapper = null;
		
		String msgErrorValidacion = "La solicitud  "
					+ solicitud.getNoFolioSolicitud() + " no ser� procesada debido a que la persona a dar de alta ya cuenta con NSS";

		// Se obtiene el origen de la solicitud
		OrigenSolicitud origenSolicitud = solicitud.getOrigenSolicitud();
		
		SolicitudNssCorreo nssCorreo = null;
		
		this.log.info("La solicitud con folio "
				+ solicitud.getNoFolioSolicitud()
				+ " esta por ser atendida, su origen es "
				+ origenSolicitud.getDescripcion() + "("
				+ origenSolicitud.getIdTipoSolicitud() + ")");
		
		TramiteAsegurado tramiteAsegurado = null;
		TramiteFisica tramiteFisica = null;
		
		for (Tramite tramite : tramites) {
			if (tramite instanceof TramiteAsegurado) {
				tramiteAsegurado = (TramiteAsegurado) tramite;
				this.log.info("Se encontro tramiteAsegurado con id -> " + tramiteAsegurado.getTramiteId());
			} else if (tramite instanceof TramiteFisica) {
				tramiteFisica = (TramiteFisica) tramite;
				this.log.info("Se encontro tramiteFisica con id -> " + tramiteFisica.getTramiteId());
			}
		}
		
		/*
		 * Antes de comenzar a procesar la solicitud, es necesario
		 * checar los tr�mties dentro de la solicitud, si se encuentra
		 * uno de REGISTRO DE PERSONA, se debe checar si la persona ya existe
		 * con un NSS, en caso de cumplirse NO se debe procesar la solicitud
		 * recibida.
		 */
		if (tramiteFisica != null && tramiteFisica.getFisica() != null
				&& tramiteFisica.getTipoTramite() != null
				&& tramiteFisica.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.REGISTRO_DE_PERSONA.getCodigo())) {
			
			this.log.info("Se va a realizar la validaci�n para confirmar el procesamiento de la solicitud de NSS "
					+ solicitud.getNoFolioSolicitud() + " ya que se encontr� un tr�mite de REGISTRO DE PERSONA");
			
			boolean isCalificacionIMSS = false;
			Fisica fisicaNuevaValidar = tramiteFisica.getFisica();
			
			this.log.debug("Persona nueva para realizar validaciones -> " + fisicaNuevaValidar);
			
			List<PersonaCalificacion> calificaciones = fisicaNuevaValidar.getPersonaCalificaciones();
			
			if (calificaciones != null) {
				for (PersonaCalificacion calificacion : calificaciones) {
					if (calificacion.getCalificacion().getIdCalificacion()
							.intValue() == CalificacionPersona.VALIDADO_IMSS.intValue()) {
						isCalificacionIMSS = true;
					}
				}
			}
			
			if (fisicaNuevaValidar.getIdPersona() == null && isCalificacionIMSS) {
				/*
				 * Si la persona NUEVA cuenta con calificaci�n IMSS significa que
				 * se esta dando de alta manualmente, por lo tanto, se debe procesar
				 * la solicitud de manera normal
				 */
				this.log.info("La persona encontrada tiene calificaci�n IMSS, la solicitud "
					+ solicitud.getNoFolioSolicitud() + " va a ser procesada de manera normal");
			} else {
				/*
				 * Ya que se sabe que la persona no fue capturada manualmente, se garantiza que 
				 * la persona a dar de alta trae CURP, por lo tanto, la b�squeda de las
				 * personas con NSS se puede realizar s�lo por CURP.
				 */
				List<Fisica> personasEncontradas = null;
				String curp = fisicaNuevaValidar.getCurp();

				this.log.info("La b�squeda para la validaci�n se realiza a trav�s de la CURP " + curp);
				
				personasEncontradas = this.personaBusiness
						.buscarPersonaFisicaPorCurpEnImss(curp);
													
				if (personasEncontradas != null) {
					for (Fisica fisicaAux : personasEncontradas) {
						boolean tieneCalificacionRENAPO = this.calificacionesPersonaBusinessService
								.tieneCalificacionEspecifica(fisicaAux,
										CalificacionEnum.VALIDADO_RENAPO);
						
						if (StringUtils.isNotBlank(fisicaAux.getNss()) && tieneCalificacionRENAPO) {
							this.log.error(msgErrorValidacion + " -> " + fisicaAux);
							throw new SolicitudNoValidaException(msgErrorValidacion);
						} 
					}
				}
				
			}
		} 
				
		this.log.info("Se comienza el procesamiento de la de la solicitud de NSS -> "
				+ solicitud.getNoFolioSolicitud());
		
		if (tramiteAsegurado != null) {
			
			// Tramite para la generaci�n y asignacion de NSS
			String curpRenapo = tramiteAsegurado.getCurpRENAPO();
			
			asignacionSerie = tramiteAsegurado.getAsignacionSerieNss();
			
			if (tramiteAsegurado.getNssCorreo() != null) {
				nssCorreo = tramiteAsegurado.getNssCorreo();
			}
			
			if (tramiteAsegurado.getFisica() != null) {
				log.debug("La persona que esta dentro del tramite asegurado no es nula");
				fisica = tramiteAsegurado.getFisica();
				log.debug("La curp de renapo es: " +  curpRenapo + " para la persona " + fisica.getIdPersona());
				umf = fisica.getUmf();
				
				this.log.debug("UMF asignacion -> " + umf);
				
				
				
				//if (origenSolicitud.getIdTipoSolicitud().equals(OrigenSolicitudEnum.VENTANILLA.getId())) {
					// Se obtiene la persona esta registrada se actualiza la informacion
				if (StringUtils.isNotBlank(curpRenapo) && fisica != null && fisica.getIdPersona() != null) {

					Fisica fisicaRENAPO = localizarPersonaFisicaEnRENAPOServiceBusiness.
							localizarPersonaFisicaEnRENAPOxCURP(curpRenapo);
					fisicaRENAPO.setIdPersona(fisica.getIdPersona());
					fisicaRENAPO.setCveFisica(fisica.getCveFisica());

					AfectarDatosPersonaWrapper personaWrapper = new AfectarDatosPersonaWrapper();
					personaWrapper.setFisica(fisicaRENAPO);
					personaWrapper.setModificarCURP(true);
					personaWrapper.setModificarFechaNacimiento(true);
					//se actualizara el anio y mes de nacimiento, el segundo parametro indica que los datos se obtendran a partir de la fecha de nacimiento
					//y no del mes y anio que traigan la persona que en esta caso seria null porque la persona que se manda es la de renapo
					personaWrapper.actualizarAnioMesNacimiento(true, true);

					calificacionesPersonaBusinessService.calificarRENAPO(fisicaRENAPO);
					log.debug("Se actualizara a la persona con la curp: " + fisicaRENAPO.getCurp() + " y id: " + fisicaRENAPO.getIdPersona());
					personaBusiness.afectarDatosPersona(personaWrapper);

					fisica.setCurp(fisicaRENAPO.getCurp());
					fisica.setFechaNacimiento(fisicaRENAPO.getFechaNacimiento());
				}
				//}
			} else {
				log.debug("El tramite de asegurado no trae persona fisica");
			}
			
			if (tramiteAsegurado.getIcaDatosRespuesta() != null) {
				log.debug("El tramite del asegurado trae ica");

				ICADatosRespuesta datosRespuesta = (ICADatosRespuesta) tramiteAsegurado
						.getIcaDatosRespuesta();

				TramiteCambioInformacionPersona infoPersona = new TramiteCambioInformacionPersona();
				infoPersona.setDatosICA(datosRespuesta);
				infoPersona.setTramiteId(tramiteAsegurado.getTramiteId());

				Modulo mod = new Modulo();
				mod.setIdModulo(ModuloEnum.ASIGNACION_NSS.getCodigo()
						.longValue());

				afectarDatosPersonaBusiness.afectarDatos(infoPersona, mod);
			} else {
				log.debug("El tramite no trae ica");
			}
		} else {
			log.debug("El tramite de asegurado es nulo");
		}
		
		if (tramiteFisica != null && tramiteFisica.getFisica() != null
				&& tramiteFisica.getTipoTramite() != null
				&& tramiteFisica.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.REGISTRO_DE_PERSONA.getCodigo())) {
				
			/*
			 * Este tramite se encuentra cuando la persona a asignarle NSS
			 * es nueva y se debe crear
			 */
			
			this.log.info("Se va a generar una persona nueva dentro del tramite de Asignacion NSS");
			
			Fisica fisicaRegistrar = tramiteFisica.getFisica();

			fisicaNueva = this.personaBusiness.altaPersonaFisica(fisicaRegistrar);
			
			datosComplementariosGuardados = true;
			
			this.log.info("Id persona nueva desde asignacion de NSS -> "
					+ fisicaNueva.getIdPersona());
			fisica.setIdPersona(fisicaNueva.getIdPersona());
		}
		
				
		/*
		 * Se checa si se tiene que guardar los datos complementarios
		 * (domicilio, medios y calificaciones), se va a guardar si no se guard�
		 * antes en el tr�mite de alta de persona.
		 */
		if (!datosComplementariosGuardados) { 
			if (fisica.getDomicilios() != null
				&& !fisica.getDomicilios().isEmpty()
				&& fisica.getDomicilios().get(0).getClave() == null) {
				try {
					this.log.info("Se va a guardar un domicilio nuevo para el asegurado");
					this.componentesExternosBusiness.guardarYAsociarDomiciliosPersona(fisica);
				} catch (DomicilioNoValidoException e) {
					this.log.error("Error al crear el domicilio del asegurado", e);
				}
			}
						
			if (fisica.getPersonaCalificaciones() == null
					|| fisica.getPersonaCalificaciones().isEmpty()) {
				// Se califica con RENAPO a la persona
				try {
					this.log.info("La persona [idPersona: "
							+ fisica.getIdPersona()
							+ "] no cuenta con calificaciones, por lo tanto, se va a calificar con RENAPO ");
					
					this.calificacionesPersonaBusinessService.calificarRENAPO(fisica);
				} catch (PersonaSinCalificacionesException e) {
					this.log.error("Error al calificar con RENAPO a la persona [idPersona: "
									+ fisica.getIdPersona() + "]", e);
				}
			}
		}
		
		AsignacionNSS asignacionNSS = null;
		boolean nssCorrecto = false;
		
		do {
			try {
				asignacionNSS = this.serieServiceBusiness.asignarNSS(asignacionSerie, fisica);
				nssCorrecto = true;
			} catch (NSSYaExistenteException e) {
				this.log.warn(e);
			} catch (SerieNssAgotadaException e) {
				this.log.warn(e);
			}
		} while (!nssCorrecto);
		
		nss = asignacionNSS.getNss();		
		
		this.log.info("NSS asignado -> " + nss);
		
		fisica.setNss(nss);
		
		/*
		 * Se checa si el (los) tramite(s) asociados a la solicitud ya cuentan
		 * con la relaci�n hacia la persona y se le pone el NSS reci�n calculado
		 * al tramite de asignaci�n de NSS
		 */
		if (tramiteAsegurado != null) {			
			tramiteAsegurado.getFisica().setNss(nss);
			
			if (tramiteAsegurado.getFisica().getIdPersona() == null) {
				if (fisicaNueva != null) {
					/*
					 * En caso de que se haya creado una persona nueva, se
					 * asocian los tramite de registro con la persona reci�n
					 * creada
					 */
					tramiteAsegurado.getFisica().setIdPersona(fisicaNueva.getIdPersona());			
				} else {
					tramiteAsegurado.getFisica().setIdPersona(fisica.getIdPersona());
				}
			}
		} 
		
		if (tramiteFisica != null) {			
			if (tramiteFisica.getFisica().getIdPersona() == null) {
				if (fisicaNueva != null) {
					/*
					 * En caso de que se haya creado una persona nueva, se
					 * asocian los tramite de registro con la persona reci�n
					 * creada
					 */
					tramiteFisica.getFisica().setIdPersona(fisicaNueva.getIdPersona());			
				} else {
					tramiteFisica.getFisica().setIdPersona(fisica.getIdPersona());
				}
			}
		}
	
				
		try {
			this.solicitudBusiness.actualizarTramites(solicitud);
		} catch (TramiteNoEncontradoException e) {
			this.log.error(e);
		}
		
		// Se cambia el estado de la solicitud a CONCLUIDA
		this.solicitudBusiness.actualizarSolicitudAEstatusConcluida(solicitud);
		
		/*
		 * Se ejecuta la operaci�n del NSS-Correo y se env�a el correo con el
		 * NSS asignado s�lo cuando el origen es INTERNET
		 */
		if ((origenSolicitud.getIdTipoSolicitud().equals(OrigenSolicitudEnum.INTERNET.getId())
				|| origenSolicitud.getIdTipoSolicitud().equals(OrigenSolicitudEnum.MOVILES.getId()))
				&& nssCorreo != null) {
			this.ejecutarOperacionValidacionCorreoCurp(nssCorreo);	
		}
		
		//byte[] comprobanteLocalizacion = null;
		byte[] comprobanteLocalizacion = solicitudBusiness.obtenerDocumentoResultante(solicitud, null, DocumentoPorTipoEnum.COMPROBANTE_ASIGNACION.getId().intValue());
		//generamos el comprobante con QR
		byte[] comprobanteQR = solicitudBusiness.obtenerDocumentoResultante(solicitud, null, DocumentoPorTipoEnum.COMPROBANTE_ASIGNACION_SIMPLE_QR.getId().intValue());

		Map<String, byte[]> doctos = new HashMap<String, byte[]>();
		if(comprobanteLocalizacion != null) {
			doctos.put("comprobanteLocalizacion"+fisica.getNss()+".pdf",comprobanteLocalizacion);
		}
		if(comprobanteQR != null) {
			doctos.put("tarjetaNSS"+fisica.getNss()+".pdf",comprobanteQR);
		}
		
		if(doctos.isEmpty()) {
			doctos = null;
		}
		
		/*
		 * Se envia el correo con el NSS asignado siempre y cuando se tenga
		 * correo capturado, para el caso de asignacion externa siempre se debe
		 * de cumplir la condicion ya que el correo es requerido, para
		 * ventanilla puede no venir
		 */
		if (fisica.getCorreoElectronico() != null
				&& StringUtils.isNotBlank(fisica.getCorreoElectronico().getCorreo())) {
			this.enviarCorreoNSS(fisica, solicitud.getNoFolioSolicitud(), true, idSolicitud, doctos);
		}
		
		/* 
		 * Se genera el wrapper con los datos escenciales para su retorno,
		 * se utiliza este objeto "simple" para que OSB no tenga problemas
		 * al transformalo.
		 */
		aseguradoWrapper = ServiceUtility.crearAseguradoWrapper(fisica);
		aseguradoWrapper.setEstatusRegistro(AseguradoWrapper.ASIGNADO);
		
		this.log.info("Se finalizo la solicitud de Asignacion NSS ["
				+ idSolicitud + "] y se devuelve la siguiente informacion: "
				+ aseguradoWrapper);
		
		return aseguradoWrapper;
	}
	
	@Override
	public void cambiarDuenioNSS(String nss, Long idPersonaDuenia,
			Long idPersonaAsignar) {
		/*******************************************************************************************************
		 * TODO
		 * 09/04/2015
		 * Mario Teran Blanco
		 * 
		 * Se comenta esta parte del codigo ya que no debe de cambiar el id de la persona dentro 
		 * de asignacion de nss, porque al cambiarlo provoca errores cuando el nss ya tiene un grupo familiar,
		 * y si el id de la persona relacionada al NSS no corresponde con el id de la persona registrada
		 * como asegurado o pensionado dentro del grupo la aplicacion de derechohabientes manda un error
		 * indicando que no se pudo localizar al asegurado.
		 ********************************************************************************************************
		 */				
		//this.serviceEntity.cambiarDuenioNSS(nss, idPersonaDuenia, idPersonaAsignar);
		
	}
	
	@Override
	public void enviarCorreoNSS(Fisica fisica, String folioSolicitud,
			boolean isAsignacion, Long idSolicitud, Map<String, byte[]> adjuntos) {
		
		String subject;
		Map<String,String> paramAdicionales = new HashMap<String, String>();
		
		if (isAsignacion) {
			subject = "N�mero de Seguridad Social asignado";
		} else {
			subject = "N�mero de Seguridad Social localizado";
		}
		
		paramAdicionales.put("folio", folioSolicitud);
		paramAdicionales.put("idSolicitud", idSolicitud.toString());
		
		this.enviarCorreoPersonaFisica(fisica, TipoTramiteEnum.ASIGNACION_NSS,
				paramAdicionales, subject, adjuntos);
	}
	
	@Override
	public void enviarCorreoPersonaFisica(Fisica fisica,
			TipoTramiteEnum tipoTramite, Map<String, String> paramAdicionales,
			String subject, Map<String, byte[]> adjuntos) {
		
		String sexo="";
		if (fisica.getSexo().getIdSexo().longValue() == SexoEnum.HOMBRE.getId()) {
			sexo="Hombre";
		} else {
			sexo="Mujer";
		}
		
		Map<String,String> parametrosCorreo = new HashMap<String, String>();
		parametrosCorreo.put("apellidoPaterno", fisica.getPrimerApellido());
		parametrosCorreo.put("apellidoMaterno", fisica.getSegundoApellido());
		parametrosCorreo.put("nombre", fisica.getNombre());
		parametrosCorreo.put("nss", fisica.getNss());
		parametrosCorreo.put("curp", fisica.getCurp());
		parametrosCorreo.put("fechaNacimiento", fisica.getFechaNacimientoFormateada());
		parametrosCorreo.put("lugarNacimiento", fisica.getLugarNacimiento().getNombre());
		parametrosCorreo.put("sexo", sexo);
		parametrosCorreo.put("fechaOperacion", DateFormat.getDateInstance(DateFormat.LONG, new Locale("es","MX")).format(new Date()));
		parametrosCorreo.put("idTipoTramite", tipoTramite.getCodigo().toString());
		parametrosCorreo.putAll(paramAdicionales);
		
		EmailPayloadType mailWrapper = new EmailPayloadType();
		mailWrapper.setSubject(subject);
		mailWrapper.setContent(" ");
		mailWrapper.setTo(fisica.getCorreoElectronico().getCorreo());		
		mailWrapper.setContentType("text/html");
		mailWrapper.setParameters(parametrosCorreo);
		

		// Configuracion de documento adjunto
		if (adjuntos != null) {
			AttachmentContent[] lstAtachments = new AttachmentContent[adjuntos.size()];
			int index = 0;
			for (Map.Entry<String, byte[]> entry : adjuntos.entrySet()) {
				AttachmentContent attachment = new AttachmentContent();
				String stringEncoded = Base64Cipher.simpleEncode(entry.getValue());
				attachment.setTextBody(stringEncoded);
				attachment.setContentDisposition(entry.getKey().replace('/', '-'));
				attachment.setContentType(MIME_PDF);
				lstAtachments[index] = attachment;
				index++;
			}

			// TODO Habilitar para mandar correo adjunto
			mailWrapper.setAttachments(lstAtachments);
			this.log.info("Se est� por enviar correo de NSS ("
					+ fisica.getNss() + " al siguiente correo: "
					+ fisica.getCorreoElectronico().getCorreo()
					+ " adjuntando " + adjuntos.size() + " archivos");
		} else {
			this.log.info("Se est� por enviar correo de NSS ("
					+ fisica.getNss() + " al siguiente correo: "
					+ fisica.getCorreoElectronico().getCorreo());
		}
		
		this.eMailProducer.agendarCorreoElectronico(mailWrapper);
	}

	@Override
	public void enviarCorreoPersonaFisicaContent(Fisica fisica,
			TipoTramiteEnum tipoTramite, Map<String, String> paramAdicionales,
			String subject, Map<String, byte[]> adjuntos, String content) {
		
		String sexo="";
		if (fisica.getSexo().getIdSexo().longValue() == SexoEnum.HOMBRE.getId()) {
			sexo="Hombre";
		} else {
			sexo="Mujer";
		}
		
		Map<String,String> parametrosCorreo = new HashMap<String, String>();
		parametrosCorreo.put("apellidoPaterno", fisica.getPrimerApellido());
		parametrosCorreo.put("apellidoMaterno", fisica.getSegundoApellido());
		parametrosCorreo.put("nombre", fisica.getNombre());
		parametrosCorreo.put("nss", fisica.getNss());
		parametrosCorreo.put("curp", fisica.getCurp());
		parametrosCorreo.put("fechaNacimiento", fisica.getFechaNacimientoFormateada());
		parametrosCorreo.put("lugarNacimiento", fisica.getLugarNacimiento().getNombre());
		parametrosCorreo.put("sexo", sexo);
		parametrosCorreo.put("fechaOperacion", DateFormat.getDateInstance(DateFormat.LONG, new Locale("es","MX")).format(new Date()));
		parametrosCorreo.put("idTipoTramite", tipoTramite.getCodigo().toString());
		parametrosCorreo.putAll(paramAdicionales);
		
		EmailPayloadType mailWrapper = new EmailPayloadType();
		mailWrapper.setSubject(subject);
		mailWrapper.setContent(content);
		mailWrapper.setTo(fisica.getCorreoElectronico().getCorreo());		
		mailWrapper.setContentType("text/html");
		mailWrapper.setParameters(parametrosCorreo);

		// Configuracion de documento adjunto
		if (adjuntos != null) {
			AttachmentContent[] lstAtachments = new AttachmentContent[adjuntos.size()];
			int index = 0;
			for (Map.Entry<String, byte[]> entry : adjuntos.entrySet()) {
				AttachmentContent attachment = new AttachmentContent();
				String stringEncoded = Base64Cipher.simpleEncode(entry.getValue());
				attachment.setTextBody(stringEncoded);
				attachment.setContentDisposition(entry.getKey().replace('/', '-'));
				attachment.setContentType(MIME_PDF);
				lstAtachments[index] = attachment;
				index++;
			}

			// TODO Habilitar para mandar correo adjunto
			mailWrapper.setAttachments(lstAtachments);
			this.log.info("Se est� por enviar correo de NSS ("
					+ fisica.getNss() + " al siguiente correo: "
					+ fisica.getCorreoElectronico().getCorreo()
					+ " adjuntando " + adjuntos.size() + " archivos");
		} else {
			this.log.info("Se est� por enviar correo de NSS ("
					+ fisica.getNss() + " al siguiente correo: "
					+ fisica.getCorreoElectronico().getCorreo());
		}
		
		this.eMailProducer.agendarCorreoElectronicoAcute(mailWrapper);
	}
	

	@Override
	public void encolarSolicitudAsignacionNSS(Solicitud solicitud)
			throws SolicitudNoEncontradaException, TramiteNoEncontradoException, SolicitudException {
		
		this.solicitudBusiness.enviarSolicitudAProceso(solicitud, null);
	}
	
	@Override
	public void ejecutarOperacionValidacionCorreoCurp(
			SolicitudNssCorreo nssCorreo) {
		
		switch (nssCorreo.getOperacionEjecutar()) {
		case 1:
			try {
				this.solicitudNssCorreoServiceBusiness.guardar(nssCorreo);
			} catch (SolicitudNssCorreoException e) {
				this.log.error(e);
			}
			break;
		case 2:
			this.solicitudNssCorreoServiceBusiness.reiniciarConsultaCorreoNSS(nssCorreo.getCorreo().getCorreo(), nssCorreo.getCveIdTipoSolicitud());
			break;

		case 3:
			this.solicitudNssCorreoServiceBusiness.registrarConsultaCorreoNSS(nssCorreo.getCorreo().getCorreo(), nssCorreo.getCveIdTipoSolicitud());
			break;
		default:
			this.log.warn("El c�digo de respuesta para la validacion de correo fue distinto al esperado codigoRespuesta["
					+ nssCorreo.getOperacionEjecutar() + "]");
			break;
		}
	}
	
	@Override
	public Solicitud obtenerSolicitudRegistrada(Long idPersona)
			throws SolicitudException {

		return obtenerSolicitudAsignacionCommon(idPersona,
				EstadoSolicitudEnum.REGISTRADA);
	}
	
	@Override
	public Solicitud obtenerSolicitudEnProceso(Long idPersona)
			throws SolicitudException {

		return obtenerSolicitudAsignacionCommon(idPersona,
				EstadoSolicitudEnum.PENDIENTE_AUTORIZACION);
	}
	
	private void obtenerDomicilioParticularPersonaAsegurar(Fisica fisica)
			throws DomicilioNoLocalizadoException, UmfNoLocalizadaException {
		
		Persona persona = new Persona();
		persona.setIdPersona(fisica.getIdPersona());
		
		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
		
		persona.setTipoPersona(tipoPersona);
		
		List<Long> tiposDomicilio = new ArrayList<Long>();
		tiposDomicilio.add(TipoDomicilioEnum.PARTICULAR.getId());
		
		List<Domicilio> domicilios = null; 
		
		try {
			domicilios = this.domicilioServiceBusiness.obtenerDomiciliosPersonaPorTipo(persona, tiposDomicilio);
		} catch (DomicilioNoLocalizadoException e) {
			e.setFisica(fisica);
			throw e;
		}
		
		/*
		 * Una vez que se consulto el domicilio particular, se usa el
		 * asentamiento para buscar si exsiten UMFs, en caso de no existir UMFs
		 * se lanza una excepci�n
		 */
		Asentamiento asentamiento = domicilios.get(0).getAsentamiento();
		List<UnidadMedicaFamiliar> umfs = null;
		
		try {
			umfs = this.domicilioServiceBusiness.getUmfByCodigoPostal(asentamiento.getCodigoPostal().getCodigoPostal());
					
			this.log.debug("Se encontraron " + umfs.size() + " para el domicilio de la persona");
			
			fisica.setDomicilios(domicilios);
			
		} catch (UmfNoLocalizadaException e) {
			e.setFisica(fisica);
			throw e;
		}
	}
	
	private void validarSolicitudesActivasYEnProceso(Fisica fisica)
			throws SolicitudException {
		
		Solicitud solicitudActiva = null;
		TipoPersonaEnum tipoPersona = TipoPersonaEnum.FISICA;
		
		try {
			solicitudActiva = this.solicitudBusiness
					.obtenerSolicitudDePersonaPorTipoSolicitudTramiteyEstado(fisica.getIdPersona(),
							tipoPersona, TipoSolicitudEnum.ASIGNACION_NSS,
							TipoTramiteEnum.ASIGNACION_NSS, EstadoSolicitudEnum.REGISTRADA);
			
			if (solicitudActiva == null) {
				solicitudActiva = this.solicitudBusiness
						.obtenerSolicitudDePersonaPorTipoSolicitudTramiteyEstado(fisica.getIdPersona(),
								tipoPersona, TipoSolicitudEnum.ASIGNACION_NSS,
								TipoTramiteEnum.ASIGNACION_NSS, EstadoSolicitudEnum.PARA_PROCESAR_BACKOFFICE);
			}
			
		} catch (SolicitudException e) {
			this.log.error(e);
			
			if (e.getMessage().contains("criterio")){
				throw new SolicitudException("La persona ya cuenta con una solicitud de asignaci�n de NSS pendiente por atender");
			} else {
				throw new SolicitudException(e.getMessage());
			}
		}
		
		if (solicitudActiva != null && solicitudActiva.getSolicitudId() != null) {
			throw new SolicitudException("La persona ya cuenta con una solicitud de asignaci�n de NSS pendiente por atender");
		}
	}
	
	private void obtenerCalificacionesVigentesCommon(Fisica fisica) {
		try {
			fisica.setPersonaCalificaciones(this.calificacionesPersonaBusinessService
					.obtenerCalificacionesVigentes(fisica));
		} catch (PersonaSinCalificacionesException e) {
			this.log.warn("La persona [idPersona:" + fisica.getIdPersona()
					+ "] no cuenta con calificaciones vigentes");
			fisica.setPersonaCalificaciones(null);
		}
	}

	private Solicitud obtenerSolicitudAsignacionCommon(Long idPersona,
			EstadoSolicitudEnum estadoSolicitud)
			throws SolicitudException {
		Solicitud solicitud = this.solicitudBusiness
				.obtenerSolicitudDePersonaPorTipoSolicitudTramiteyEstado(
						idPersona, TipoPersonaEnum.FISICA,
						TipoSolicitudEnum.ASIGNACION_NSS,
						TipoTramiteEnum.ASIGNACION_NSS, estadoSolicitud);

		return solicitud;
	}
	
	/**
	 * Metodo que valida las reglas de negocio para actualizar un asegurado, valida que exista el NSS
	 * en caso de tener curp que exista en renapo, valida que no sea un patrón 
	 * @param fisicaNSS
	 * @return
	 * @throws ArgumentosInvalidosException
	 * @throws AsignacionNSSNoLocalizadoException
	 * @throws GestionPatronalBusinessException
	 * @throws AseguradoConRPAsignado
	 */

	@Override
	public AsignacionNSS validaExisteNSSActualizacion(Fisica fisicaNSS)
			throws ArgumentosInvalidosException,
			AsignacionNSSNoLocalizadoException,
			GestionPatronalBusinessException, AseguradoConRPAsignado,
			ClienteWebserviceRenapoCurpException
	{
		AsignacionNSS objNssLocalizado = null;
		Fisica objFisicaCurp = null;
		if(fisicaNSS == null){
			throw new ArgumentosInvalidosException();  
		}
		objNssLocalizado = aseguradoEntity.consultarAsignacionNSS(fisicaNSS.getNss());
		
		//se regresa excepcion si no se localiza el NSS
		if(objNssLocalizado == null){
			throw new AsignacionNSSNoLocalizadoException();
		}
		
		
		List<SujetoObligado> listSujetoObligado = sujetoObligadoServiceBusiness.listarRegistrosPatronalesPorPersona(objNssLocalizado);
		if (listSujetoObligado != null && listSujetoObligado.size()>0){
			throw new AseguradoConRPAsignado();
		}
		
			if(objNssLocalizado.getCurp() != null){
				
					try {
						objFisicaCurp = localizarPersonaFisicaEnRENAPOServiceBusiness.localizarPersonaFisicaEnRENAPOxCURP(objNssLocalizado.getCurp());
						if(objFisicaCurp == null){
							objNssLocalizado.setCurp(null);
						}else{
							objNssLocalizado.setCurp(objFisicaCurp.getCurp());
						}
					} catch (CURPNoLocalizadoEnEntidadExternaException e) {
						objNssLocalizado.setCurp(null);
						e.printStackTrace();
					} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
						objNssLocalizado.setCurp(null);
						e.printStackTrace();
					}
				
						
			}
		return objNssLocalizado;
	}
	
	
	
	/**
	 * Metodo encargado de actualizar una persona con asignacion nss validando que no existan antecedentes, creando solicitud y tramite
	 * @param infoPersona
	 * @throws DatosInsuficientesParaConsultaException
	 * @throws PersonaConNSSException
	 * @throws SolicitudNoValidaException
	 * @throws AfectacionDatosPersonaException
	 * @throws PersonaNoEncontradaException
	 * @throws SolicitudNoEncontradaException
	 * @throws TramiteNoEncontradoException
	 */
	
	@Override
	public Solicitud actualizaAsignacionNSS(
			TramiteCambioInformacionPersona infoPersona, Usuario usuario)
			throws DatosInsuficientesParaConsultaException,
			PersonaConNSSException, SolicitudNoValidaException,
			AfectacionDatosPersonaException, PersonaNoEncontradaException,
			SolicitudNoEncontradaException, TramiteNoEncontradoException {
		
		Fisica objFisicaActualizar = infoPersona.getFisica();
		//se valida de que no exista otra persona con NSS con los mismo datos
		List <Fisica > fisicaLocalizadas = personaFisicaServiceBusiness.localizarPersonaFisicaPorDatosBasicosEnImss(objFisicaActualizar);
		if (fisicaLocalizadas != null && fisicaLocalizadas.size()>0){
			for (Fisica localizada : fisicaLocalizadas){
				if (localizada.getNss()!= null){
					
					if(!objFisicaActualizar.getNss().equals(localizada.getNss())){
						throw new PersonaConNSSException(localizada);
					}
				}
			}
		}
		
		//se crea un tramite de tipo asegurado para poder realizar el ica o mdb
		
		Solicitud objSolicitudActualizacionNSS = new Solicitud();		
		Date fechaRegistro = new Date();
		
		//llenado de las propiedades del objeto tramiteFisica
		
		TramiteAsegurado tramite = new TramiteAsegurado();
		EstadoTramiteEnum.INICIADO.getCodigo();
		EstadoTramite objEstadoTramite = new EstadoTramite();
		TipoTramite tipoTramite = new TipoTramite();
		
		tipoTramite.setIdTipoTramite(TipoTramiteEnum.ACTUALIZACION_DATOS_ASEGURADO.getCodigo());
		objEstadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO.getCodigo());
		
		tramite.setEstadoTramite(objEstadoTramite);
		tramite.setFechaPresentacion(fechaRegistro);
		tramite.setFechaTramite(fechaRegistro);
		tramite.setFisica((AsignacionNSS)objFisicaActualizar);
		tramite.setTipoTramite(tipoTramite);
		
		List<Tramite> lstTramiteReg = new ArrayList<Tramite>();
		lstTramiteReg.add(tramite);
		
		//llenado de la solicitud
		TipoSolicitud objTipoSol = new TipoSolicitud();
		objTipoSol.setIdTipoSolicitud(mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES.getValor().longValue());
	
		EstadoSolicitud objEstadoSol =new EstadoSolicitud();
		objEstadoSol.setIdEstadoSolicitud(EstadoSolicitudEnum.REGISTRADA.getCodigo());
		
		objSolicitudActualizacionNSS.setEstadoSolicitud(objEstadoSol);
		objSolicitudActualizacionNSS.setFechaSolicitud(fechaRegistro);
		objSolicitudActualizacionNSS.setFirmadaDigitalmente(false);
		objSolicitudActualizacionNSS.setTipoSolicitud(objTipoSol);
		objSolicitudActualizacionNSS.setSelloDigital(null);
		objSolicitudActualizacionNSS.setTramites(lstTramiteReg);
		
		objSolicitudActualizacionNSS.setSolicitante(usuario);
		
		Solicitud solicitudCreada = this.solicitudBusiness.crear(objSolicitudActualizacionNSS);
		
		objSolicitudActualizacionNSS.setSolicitudId(solicitudCreada.getSolicitudId());
		
		this.log.debug("Solicitud creada ...." + solicitudCreada);
		this.log.debug("El id de la solicitud creada es: " + solicitudCreada.getSolicitudId() + " id del obj: " + objSolicitudActualizacionNSS.getSolicitudId());
		
		infoPersona.setTramiteId((solicitudCreada.getTramites()).get(0).getTramiteId());

		Modulo mod = new Modulo();
		mod.setIdModulo(ModuloEnum.ASIGNACION_NSS.getCodigo().longValue());
		

		afectarDatosPersonaBusiness.afectarDatos(infoPersona, mod);
		
		solicitudBusiness.actualizarSolicitudAEstatusConcluida(objSolicitudActualizacionNSS.getSolicitudId());
		
		return solicitudCreada;
	}
		
	@Override
	public AseguradoWrapper generarNSSUnSoloPaso(Fisica fisica, Fisica fisicaRENAPO,
			ModuloOrigenAsignacionEnum moduloOrigen, Usuario usuario)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			GenerarNSSException, ErrorComparacionDatosRENAPOException,
			SolicitudNoValidaException, SolicitudException,
			UmfNoLocalizadaException, SolicitudNoEncontradaException,
			AfectacionDatosPersonaException, PersonaNoEncontradaException,
			DomicilioNoValidoException,
			NivelDeAsignacionSerieIndefinidoException,
			SeriesNoLocalizadasException, ErrorAlActivarSerieException,
			TramiteNoEncontradoException, PersonaSinCalificacionesException {
		
		this.log.debug("Se inicia el proceso de Asignaci�n de NSS en un s�lo paso");
		
		Fisica fisicaCandidata = null;
		Domicilio domicilio = null;
		String codigoPostal = null;
		AseguradoWrapper wrapper = null;
		boolean isRecuperacion = false;
		
		try {
			if (moduloOrigen.getId().equals(ModuloOrigenAsignacionEnum.SIE.getId())) {
				fisicaCandidata = this.validacionesNSSSinConsultaRENAPO(fisica, fisicaRENAPO, false);
			} else {
				fisicaCandidata = this.validacionesNSS(fisica, false);
			}
		} catch (DomicilioNoLocalizadoException e) {
			this.log.debug("La persona candidata para NSS no cuenta con domicilio");
			fisicaCandidata = e.getFisica();
		} catch (UmfNoLocalizadaException e) {
			this.log.debug("La persona candidata para NSS no cuenta con UMF para su domicilio");
			fisicaCandidata = e.getFisica();
		} catch (PersonaConNSSException e) {
			this.log.debug("La persona ya cuenta con NSS se va a crear la solicitud de recuperaci�n");
			fisicaCandidata = e.getFisica();
			isRecuperacion = true;
		}
		
		if (fisica.getDomicilios() != null && !fisica.getDomicilios().isEmpty()) {
			domicilio = fisica.getDomicilios().get(0); 					
			this.log.debug("Se toma el domicilio de la persona recibida como parametro");
		} else if (fisicaCandidata.getDomicilios() != null && !fisicaCandidata.getDomicilios().isEmpty()) {
			domicilio = fisicaCandidata.getDomicilios().get(0);
			this.log.debug("Se toma el domicilio de la persona encontrada en la base de datos");
		}
		
		if (domicilio != null) {
			codigoPostal = StringUtils.isNotBlank(domicilio.getCodigoPostal()
					.getCodigoPostal()) ? domicilio.getCodigoPostal()
					.getCodigoPostal() : domicilio.getAsentamiento()
					.getCodigoPostal().getCodigoPostal();
			this.log.debug("Codigo postal para asegurado -> " + codigoPostal);
		} else {
			codigoPostal = "0";
			this.log.debug("Asegurado sin codigo postal");
		}
		
		List<UnidadMedicaFamiliar> umfs = this.domicilioServiceBusiness.getUmfByCodigoPostal(codigoPostal);
		UnidadMedicaFamiliar umf = null;
		if (umfs != null && !umfs.isEmpty()) {
			umf = umfs.get(0);
			this.log.error("UMF encontrada para asegurado -> " + umf);
		}
		
		if (fisicaCandidata != null && !isRecuperacion) {
			Solicitud solicitud = this.crearSolicitudAsignacionNSS(fisicaCandidata, TipoSerieEnum.ORDINARIA,
					OrigenSolicitudEnum.VENTANILLA, null, null, usuario, moduloOrigen);
							
			solicitud = this.guardarSolicitudAsignacionNSS(solicitud, umf, null);
			
			wrapper = this.procesarSolicitudAsignacionNSS(solicitud.getSolicitudId());
		} else if (isRecuperacion) {
			fisicaCandidata.setUmf(umf);
			this.crearSolicitudRecuperacionNSS(fisicaCandidata, OrigenSolicitudEnum.VENTANILLA, usuario);
			wrapper = ServiceUtility.crearAseguradoWrapper(fisicaCandidata);
			wrapper.setEstatusRegistro(AseguradoWrapper.RECUPERADO);
		}
		
		this.log.debug("Se finaliza el proceso de Asignaci�n de NSS en un s�lo paso");
		
		return wrapper;
	}

	@Override
	public AseguradoWrapper generarNSSEstudiante(
			AltaDatosAsignacionNSSType estudiante, String nombreUsuario)
			throws NivelDeAsignacionSerieIndefinidoException,
			SeriesNoLocalizadasException, ErrorAlActivarSerieException {
		
		this.log.debug("Inicia Asignaci�n de NSS a estuadiante con CURP -> " + estudiante.getCurp());
		
		Fisica fisica = new Fisica();
		fisica.setCurp(estudiante.getCurp());
		fisica.setNombre(estudiante.getNombre());
		fisica.setPrimerApellido(estudiante.getApellidoPaterno());
		fisica.setSegundoApellido(estudiante.getApellidoMaterno());
		fisica.setFechaNacimiento(null);
		EntidadFederativa lugarNacimiento = new EntidadFederativa();
		lugarNacimiento.setClave(Integer.toString(estudiante.getLugarNacimiento()));
		fisica.setLugarNacimiento(lugarNacimiento);
		Sexo sexo = new Sexo();
		sexo.setIdSexo(estudiante.getSexo());
		fisica.setSexo(sexo);
		
		Date fechaNacimiento = null;	
		
		if (estudiante.getDiaNacimiento() > 0
				&& estudiante.getMesNacimiento() > 0
				&& estudiante.getAnioNacimiento() > 0) {

			try {
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(estudiante.getDiaNacimiento()));
				calendar.set(Calendar.MONTH,Integer.valueOf(estudiante.getMesNacimiento()) - 1);
				calendar.set(Calendar.YEAR, Integer.valueOf(estudiante.getAnioNacimiento()));
				
				fechaNacimiento = calendar.getTime();
			} catch (Exception e) {
				this.log.warn(
						"Error al tratar de genera la fecha de nacimiento a partir de los campos particulares:",
						e);
				fechaNacimiento = generarFechaNacimientoDesdeCURP(estudiante
						.getCurp());
			}
		} else {
			fechaNacimiento = generarFechaNacimientoDesdeCURP(estudiante
					.getCurp());
		}
		
		fechaNacimiento = DateUtils.truncate(fechaNacimiento, Calendar.DATE);
		fisica.setFechaNacimiento(fechaNacimiento);
		
		if(StringUtils.isNotBlank(estudiante.getCodigoPostal())) {
			Domicilio domicilio = new Domicilio();
			CodigoPostal cp = new CodigoPostal();
			cp.setCodigoPostal(estudiante.getCodigoPostal());
			domicilio.setCodigoPostal(cp);
			fisica.getDomicilios().add(domicilio);
		}
		
		Usuario usuario = new Usuario();
		usuario.setUsuario(nombreUsuario);
		
		AseguradoWrapper wrapper = null;
		Fisica fisicaRENAPO = null;
		
		try {
			
			/* 
			 * Se aplica regla de negocio para estudiantes, se debe
			 * consultar el CURP a RENAPO y validar los datos b�sicos
			 * entre lo devuelto por RENPO y lo que viene en el XML
			 */
			fisicaRENAPO = this.localizarPersonaFisicaEnRENAPOServiceBusiness
					.localizarPersonaFisicaEnRENAPOxCURP(fisica.getCurp());
			
			boolean exitoComparacion = this.personaFisicaServiceBusiness
					.comparaDatosBasicosRENAPO(fisicaRENAPO,
							fisica);
			
			if (!exitoComparacion) {
				throw new ErrorComparacionDatosRENAPOException("Los datos b�sicos del estudiante en el archivo no coinciden con los de la CURP que se valid� en RENAPO");
			}
			
			wrapper = this.generarNSSUnSoloPaso(fisica, fisicaRENAPO, ModuloOrigenAsignacionEnum.SIE, usuario);
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			wrapper = ServiceUtility.procesarErrorAsignacionEstudiantes(fisica, e.getMessage());
		} catch (ClienteWebserviceRenapoCurpException e) {
			wrapper = ServiceUtility.procesarErrorAsignacionEstudiantes(fisica, e.getMessage());
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			wrapper = ServiceUtility.procesarErrorAsignacionEstudiantes(fisica, e.getMessage());
		} catch (GenerarNSSException e) {
			wrapper = ServiceUtility.procesarErrorAsignacionEstudiantes(fisica, e.getMessage());
		} catch (ErrorComparacionDatosRENAPOException e) {
			wrapper = ServiceUtility.procesarErrorAsignacionEstudiantes(fisica, e.getMessage());
		} catch (SolicitudNoValidaException e) {
			wrapper = ServiceUtility.procesarErrorAsignacionEstudiantes(fisica, e.getMessage());
		} catch (SolicitudException e) {
			wrapper = ServiceUtility.procesarErrorAsignacionEstudiantes(fisica, e.getMessage());
		} catch (UmfNoLocalizadaException e) {
			wrapper = ServiceUtility.procesarErrorAsignacionEstudiantes(fisica, e.getMessage());
		} catch (SolicitudNoEncontradaException e) {
			wrapper = ServiceUtility.procesarErrorAsignacionEstudiantes(fisica, e.getMessage());
		} catch (AfectacionDatosPersonaException e) {
			wrapper = ServiceUtility.procesarErrorAsignacionEstudiantes(fisica, e.getMessage());
		} catch (PersonaNoEncontradaException e) {
			wrapper = ServiceUtility.procesarErrorAsignacionEstudiantes(fisica, e.getMessage());
		} catch (DomicilioNoValidoException e) {
			wrapper = ServiceUtility.procesarErrorAsignacionEstudiantes(fisica, e.getMessage());
		} catch (TramiteNoEncontradoException e) {
			wrapper = ServiceUtility.procesarErrorAsignacionEstudiantes(fisica, e.getMessage());
		} catch (PersonaSinCalificacionesException e) {
			wrapper = ServiceUtility.procesarErrorAsignacionEstudiantes(fisica, e.getMessage());
		} catch (Exception e){
			wrapper = ServiceUtility.procesarErrorAsignacionEstudiantes(fisica, e.getMessage());
		}
		
		this.log.debug("Finaliza Asignacion de NSS a estudiante -> " + wrapper);
		
		return wrapper;
	}
	
	@Override
	public Solicitud crearEncolarSolicitudAsignacionMasivaSIE(
			AsignacionMasivaWrapper wrapper, FirmaElectronica firma)
			throws SolicitudNoValidaException, SolicitudNoEncontradaException,
			TramiteNoEncontradoException, SolicitudException {

		Date fechaActual = new Date();
		
		SujetoObligado so = this.sujetoObligadoServiceBusiness
				.consultarPorRegistroPatronalBasic(wrapper.getNrp(), null);

		TramiteAsignacionMasiva tramiteAsigMasiva = new TramiteAsignacionMasiva();
		tramiteAsigMasiva.setNrp(wrapper.getNrp());
		tramiteAsigMasiva.setFileName(wrapper.getFileName());
		TipoTramite tipoTramite = new TipoTramite();
		tipoTramite.setIdTipoTramite(TipoTramiteEnum.ASIGNACION_MASIVA_NSS_SIE
				.getCodigo());
		tramiteAsigMasiva.setTipoTramite(tipoTramite);

		tramiteAsigMasiva.setFechaTramite(fechaActual);
		tramiteAsigMasiva.setFechaPresentacion(fechaActual);

		EstadoTramite estadoTramite = new EstadoTramite();
		estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO
				.getCodigo());
		estadoTramite.setDescripcion(EstadoTramiteEnum.INICIADO
				.getDescripcion());
		tramiteAsigMasiva.setEstadoTramite(estadoTramite);
		
		tramiteAsigMasiva.setCorreosContacto(wrapper.getCorreosContacto());
		
		tramiteAsigMasiva.setSujetoObligado(so);

		Solicitud solicitud = new Solicitud();
		TipoSolicitud tipoSolicitud = new TipoSolicitud();
		tipoSolicitud.setIdTipoSolicitud(TipoSolicitudEnum.ASIGNACION_NSS
				.getValor().longValue());
		solicitud.setTipoSolicitud(tipoSolicitud);

		EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
		estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.REGISTRADA
				.getCodigo());
		solicitud.setEstadoSolicitud(estadoSolicitud);

		solicitud.setFechaSolicitud(fechaActual);
		solicitud.setFechaPresentacion(fechaActual);

		solicitud.setOrigenSolicitud(wrapper.getOrigenSolicitud());

		if (wrapper.getUsuario() != null) {

			Usuario usuario = wrapper.getUsuario();
			UsuarioFuncionario uf = usuario.getUsuarioFuncionario();

			if (uf != null && uf.getSubdelegacion() != null
					&& uf.getSubdelegacion().getId() != null
					&& !uf.getSubdelegacion().getId().equals(-1)) {

				/*
				 * Debido a que a nivel base de datos, s�lo se puede relacionar
				 * una solicitud a nivel subdelegacional, no se toma en cuenta
				 * el nivel delegacional
				 */
				solicitud.setSubdelegacion(uf.getSubdelegacion());

				this.log.debug("El usuario tiene nivel subdelegacional");
			}
			this.log.debug("El usuario para la asignacion masiva es:" + usuario.getUsuario());
			solicitud.setSolicitante(usuario);
			tramiteAsigMasiva.setUsuario(usuario.getUsuario());
		}

		solicitud.getTramites().add(tramiteAsigMasiva);

		solicitud = this.solicitudBusiness.crear(solicitud);
		
		this.solicitudBusiness.enviarSolicitudAProceso(solicitud, firma);
		
		return solicitud;
	}
	
	private Date generarFechaNacimientoDesdeCURP(String curp) {

		DateFormat formatter = new SimpleDateFormat("yyMMdd");
		Date fechaNacimiento = null;

		this.log.debug("El registro no cuenta con los datos requeridos para la fecha de nacimiento, se va a obtener de la CURP");

		if (StringUtils.isNotBlank(curp)) {
			try {
				fechaNacimiento = formatter.parse(curp.substring(4, 10));
			} catch (ParseException e) {
				this.log.warn(e);
			}
		} else {
			this.log.debug("El registro no cuenta con CURP");
		}

		return fechaNacimiento;
	}

	@Override
	public void generaNSSET(AltaDatosAsignacionNSSType estudiante,
			String nombreUsuario) {
		System.err.println("Llegue al ejb");
		
		this.log.debug("Inicia Asignaci�n de NSS a estuadiante con CURP -> " + estudiante.getCurp());
		
		Fisica fisica = new Fisica();
		fisica.setCurp(estudiante.getCurp());
		fisica.setNombre(estudiante.getNombre());
		fisica.setPrimerApellido(estudiante.getApellidoPaterno());
		fisica.setSegundoApellido(estudiante.getApellidoMaterno());
		fisica.setFechaNacimiento(null);
		EntidadFederativa lugarNacimiento = new EntidadFederativa();
		lugarNacimiento.setClave(Integer.toString(estudiante.getLugarNacimiento()));
		fisica.setLugarNacimiento(lugarNacimiento);
		Sexo sexo = new Sexo();
		sexo.setIdSexo(estudiante.getSexo());
		fisica.setSexo(sexo);
		
		Date fechaNacimiento = null;	
		
		if (estudiante.getDiaNacimiento() > 0
				&& estudiante.getMesNacimiento() > 0
				&& estudiante.getAnioNacimiento() > 0) {

			try {
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(estudiante.getDiaNacimiento()));
				calendar.set(Calendar.MONTH,Integer.valueOf(estudiante.getMesNacimiento()) - 1);
				calendar.set(Calendar.YEAR, Integer.valueOf(estudiante.getAnioNacimiento()));
				
				fechaNacimiento = calendar.getTime();
			} catch (Exception e) {
				this.log.warn(
						"Error al tratar de genera la fecha de nacimiento a partir de los campos particulares:",
						e);
				fechaNacimiento = generarFechaNacimientoDesdeCURP(estudiante
						.getCurp());
			}
		} else {
			fechaNacimiento = generarFechaNacimientoDesdeCURP(estudiante
					.getCurp());
		}
		
		fechaNacimiento = DateUtils.truncate(fechaNacimiento, Calendar.DATE);
		fisica.setFechaNacimiento(fechaNacimiento);
		
		if(StringUtils.isNotBlank(estudiante.getCodigoPostal())) {
			Domicilio domicilio = new Domicilio();
			CodigoPostal cp = new CodigoPostal();
			cp.setCodigoPostal(estudiante.getCodigoPostal());
			domicilio.setCodigoPostal(cp);
			fisica.getDomicilios().add(domicilio);
		}
		
		Usuario usuario = new Usuario();
		usuario.setUsuario(nombreUsuario);
		
		AseguradoWrapper wrapper = null;
		Fisica fisicaRENAPO = null;
		
try {
			
			/* 
			 * Se aplica regla de negocio para estudiantes, se debe
			 * consultar el CURP a RENAPO y validar los datos b�sicos
			 * entre lo devuelto por RENPO y lo que viene en el XML
			 */
			fisicaRENAPO = this.localizarPersonaFisicaEnRENAPOServiceBusiness
					.localizarPersonaFisicaEnRENAPOxCURP(fisica.getCurp());
			
			boolean exitoComparacion = this.personaFisicaServiceBusiness
					.comparaDatosBasicosRENAPO(fisicaRENAPO,
							fisica);
			
			if (!exitoComparacion) {
				throw new ErrorComparacionDatosRENAPOException("Los datos b�sicos del estudiante en el archivo no coinciden con los de la CURP que se valid� en RENAPO");
			}
			
			//wrapper = this.generarNSSUnSoloPaso(fisica, fisicaRENAPO, ModuloOrigenAsignacionEnum.SIE, usuario);
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			wrapper = ServiceUtility.procesarErrorAsignacionEstudiantes(fisica, e.getMessage());
		} catch (ClienteWebserviceRenapoCurpException e) {
			wrapper = ServiceUtility.procesarErrorAsignacionEstudiantes(fisica, e.getMessage());
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			wrapper = ServiceUtility.procesarErrorAsignacionEstudiantes(fisica, e.getMessage());
//		} catch (GenerarNSSException e) {
//			wrapper = ServiceUtility.procesarErrorAsignacionEstudiantes(fisica, e.getMessage());
//		} catch (ErrorComparacionDatosRENAPOException e) {
//			wrapper = ServiceUtility.procesarErrorAsignacionEstudiantes(fisica, e.getMessage());
//		} catch (SolicitudNoValidaException e) {
//			wrapper = ServiceUtility.procesarErrorAsignacionEstudiantes(fisica, e.getMessage());
//		} catch (SolicitudException e) {
//			wrapper = ServiceUtility.procesarErrorAsignacionEstudiantes(fisica, e.getMessage());
//		} catch (UmfNoLocalizadaException e) {
//			wrapper = ServiceUtility.procesarErrorAsignacionEstudiantes(fisica, e.getMessage());
//		} catch (SolicitudNoEncontradaException e) {
//			wrapper = ServiceUtility.procesarErrorAsignacionEstudiantes(fisica, e.getMessage());
//		} catch (AfectacionDatosPersonaException e) {
//			wrapper = ServiceUtility.procesarErrorAsignacionEstudiantes(fisica, e.getMessage());
//		} catch (PersonaNoEncontradaException e) {
//			wrapper = ServiceUtility.procesarErrorAsignacionEstudiantes(fisica, e.getMessage());
//		} catch (DomicilioNoValidoException e) {
//			wrapper = ServiceUtility.procesarErrorAsignacionEstudiantes(fisica, e.getMessage());
//		} catch (TramiteNoEncontradoException e) {
//			wrapper = ServiceUtility.procesarErrorAsignacionEstudiantes(fisica, e.getMessage());
//		} catch (PersonaSinCalificacionesException e) {
//			wrapper = ServiceUtility.procesarErrorAsignacionEstudiantes(fisica, e.getMessage());
		} catch (Exception e){
			e.printStackTrace();
			wrapper = ServiceUtility.procesarErrorAsignacionEstudiantes(fisica, e.getMessage());
		}
		
		this.log.debug("Finaliza Asignacion de NSS a estudiante -> " + wrapper);
	
		
	}
	
	@Override
	public AsignacionNSS obtenerAsignacionNss(String nss) {
		return this.aseguradoEntity.consultarAsignacionNSS(nss);
	}

	private AsignacionNSS obtenerAsignacionNssConBajaLogica(String nss) {
		return this.aseguradoEntity.consultarAsignacionNSSEnBajaLogica(nss);
	}

	@Override
	public Long obtenerCveAsignacionNss(String nss) {
		
		Long cveAsignacionNss = null;
		
		AsignacionNSS asignacionNSS = this.aseguradoEntity.consultarAsignacionNSS(nss);
		
		if (asignacionNSS != null) {
			cveAsignacionNss = asignacionNSS.getIdAsignacionNSS();
		}
		
		return cveAsignacionNss;
	}
	
	
	/**
	 * Servicio que contiene la l�gica y validaciones para la b�squeda de la
	 * persona con NSS y curp para validar si se genera reporte de vigencia.
	 * 
	 * @param fisica
	 * @return
	 * @throws CURPNoLocalizadoEnEntidadExternaException
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws ErrorValidacionDatosConsultaEnEntidaExternaException
	 * @throws ErrorComparacionDatosRENAPOException
	 * @throws PersonaConNSSException
	 * @throws AsignacionNSSNoLocalizadoException
	 */
	@Override
	public Fisica validacionesNSSConsultaVigencia(Fisica fisica)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			ErrorComparacionDatosRENAPOException,
			AsignacionNSSNoLocalizadoException{

		log.info("Entrando a realizar validaciones para la consulta de vigencia");
		Fisica fisicaRENAPO = this.localizarPersonaFisicaEnRENAPOServiceBusiness.localizarPersonaFisicaEnRENAPOxCURP(fisica.getCurp());
		//Se obtiene asegurado por nss y curp para validar que el nss este asignado a la curp
		AsignacionNSS nssBdtu = this.obtenerAseguradoPorNss(fisica.getNss(), fisica.getCurp());

		if(nssBdtu == null){
			nssBdtu = this.obtenerAsignacionNssConBajaLogica(fisica.getNss());
			if (nssBdtu == null) {
				log.error("Excepcion controlada el NSS " + fisica.getNss() +  " no fue localizado en el IMSS" );
				throw new AsignacionNSSNoLocalizadoException(", el NSS " +fisica.getNss() +  " no fue localizado en el IMSS." );
			} else {
				log.error("Excepcion controlada el NSS " + fisica.getNss() +  " presenta baja logica" );
				throw new AsignacionNSSNoLocalizadoException(" Baja logica", 1);
			}
		}

		if(nssBdtu.getCurp() != null){
			log.info("Entrando a validar datos de BDTU contra RENAPO");
			if(!nssBdtu.getCurp().equalsIgnoreCase(fisica.getCurp()) ||
					!nssBdtu.getCurp().equalsIgnoreCase(fisicaRENAPO.getCurp())){
					try{
					Fisica fisicaNssCurp = this.localizarPersonaFisicaEnRENAPOServiceBusiness.localizarPersonaFisicaEnRENAPOxCURP(nssBdtu.getCurp());
					if(!fisicaNssCurp.getCurp().equalsIgnoreCase(fisica.getCurp()) ||
							!fisicaNssCurp.getCurp().equalsIgnoreCase(fisicaRENAPO.getCurp())){
							boolean exitoComparacion = personaFisicaServiceBusiness.comparaDatosBasicosAseguradoMesAnioNacRENAPO(fisicaRENAPO, nssBdtu);
							if (!exitoComparacion) {
								throw new ErrorComparacionDatosRENAPOException();
							}
						}
					}catch (Exception e){
						log.error("error al consultar el NSS de la BDTU en renapo" , e);
						throw new ErrorComparacionDatosRENAPOException();
					}
			} else if (nssBdtu.getCurp().equals(fisicaRENAPO.getCurp()) || nssBdtu.getCurp().equals(fisica.getCurp()) ) {
				return nssBdtu;
			}
		}

		boolean exitoComparacion = personaFisicaServiceBusiness.comparaDatosBasicosAseguradoMesAnioNacRENAPO(fisicaRENAPO, nssBdtu);
		if (!exitoComparacion) {
			throw new ErrorComparacionDatosRENAPOException();
		}
		
		return nssBdtu;
	}
	
	@Override
	public PersonaTO localizarNSSPorCurp(String curp, String correo)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			PersonaSinNSSException, ErrorComparacionDatosRENAPOException,
			SolicitudNoValidaException, SolicitudException,
			AsignacionNssPersonaException {

		// Validacion del formato de la CURP
		if (StringUtils.isBlank(curp) || curp.length() != LONGITUD_CURP) {
			throw new AsignacionNssPersonaException("La CURP tiene que ser de " + LONGITUD_CURP + " caracteres");
		} else if (!curp.matches(REGEX_CURP_FISICA)) {
			throw new AsignacionNssPersonaException("La CURP no cumple con el formato requerido");
		}

		// Validacion del formato de correo
		if (StringUtils.isBlank(correo)) {
			throw new AsignacionNssPersonaException("El correo no debe ser nulo o vac\u00EDo");
		} else if (!correo.matches(EMAIL_PATTERN)) {
			throw new AsignacionNssPersonaException("El correo electronico no cumple con el formato requerido");
		}

		// Se pasa a min�sculas el correo capturado
		String correoCapturado = correo.toLowerCase();

		// Se llena objeto con los datos de b�squeda
		Fisica fisica = new Fisica();
		fisica.setCurp(curp);
		CorreoElectronico correoElectronico = new CorreoElectronico();
		correoElectronico.setCorreo(correoCapturado);
		fisica.setCorreoElectronico(correoElectronico);

		// Validacion de consulta de NSS
		SolicitudNssCorreo nssCorreo = new SolicitudNssCorreo();
		nssCorreo.setCorreo(fisica.getCorreoElectronico());
		nssCorreo.setCurp(fisica.getCurp());
		nssCorreo.setCveIdTipoSolicitud(TipoSolicitudEnum.ASIGNACION_NSS.getValor().longValue());

		try {
			// Validacion de consultas realizadas por correo
			int codigoRespuesta = solicitudNssCorreoServiceBusiness.isConsultaRegistroNSSValid(nssCorreo, false);
			nssCorreo.setOperacionEjecutar(codigoRespuesta);

			// Validacion de NSS
			fisica = validacionesNSS(fisica, true);

			// Se transforma persona encontrada
			PersonaTO personaEncontrada = new PersonaTO();
			personaEncontrada.setNombre(fisica.getNombre());
			personaEncontrada.setPrimerApellido(fisica.getPrimerApellido());
			personaEncontrada.setSegundoApellido(fisica.getSegundoApellido());
			personaEncontrada.setSexo(fisica.getSexo());
			personaEncontrada.setFechaNacimiento(fisica.getFechaNacimiento());
			personaEncontrada.setErrorFormGeneral("La persona no cuenta con NSS");

			return personaEncontrada;
		} catch (PersonaConNSSException e) {
			ejecutarOperacionValidacionCorreoCurp(nssCorreo);

			// La persona ya cuenta con un NSS
			Usuario usuario = new Usuario();
			usuario.setUsuario(e.getFisica().getCurp());
			Fisica personaConNSS = e.getFisica();

			if (personaConNSS.getCorreoElectronico() != null) {
				personaConNSS.getCorreoElectronico().setCorreo(correo);
			} else {
				personaConNSS.setCorreoElectronico(new CorreoElectronico());
				personaConNSS.getCorreoElectronico().setCorreo(correo);
			}

			Solicitud solicitud = null;
			Map<String, Object> resultado= this.crearSolicitudRecuperacionNSS(
					personaConNSS, OrigenSolicitudEnum.MOVILES, usuario);
			solicitud = (Solicitud) resultado.get("solicitud");
			byte[] comprobante = (byte[]) resultado.get("comprobante");
			byte[] comprobanteQR = (byte[]) resultado.get("comprobanteQR");
			
			Map<String, byte[]> doctos = new HashMap<String, byte[]>();
			doctos.put("comprobanteLocalizacion"+personaConNSS.getNss()+".pdf",comprobante);
			doctos.put("tarjetaNSS"+personaConNSS.getNss()+".pdf",comprobanteQR);
			
			
			enviarCorreoNSS(personaConNSS, solicitud.getNoFolioSolicitud(),
					false, solicitud.getSolicitudId(), doctos);

			// Se transforma persona encontrada
			PersonaTO personaEncontrada = new PersonaTO();
			personaEncontrada.setNss(personaConNSS.getNss());
			personaEncontrada.setNombre(personaConNSS.getNombre());
			personaEncontrada.setPrimerApellido(personaConNSS.getPrimerApellido());
			personaEncontrada.setSegundoApellido(personaConNSS.getSegundoApellido());
			personaEncontrada.setSexo(personaConNSS.getSexo());
			personaEncontrada.setFechaNacimiento(personaConNSS.getFechaNacimiento());

			return personaEncontrada;
		} catch (GenerarNSSException gne) {
			throw new AsignacionNssPersonaException(gne.getMessage());
		} catch (UmfNoLocalizadaException unle) {
			throw new AsignacionNssPersonaException(unle.getMessage());
		} catch (DomicilioNoLocalizadoException dne) {
			throw new AsignacionNssPersonaException(dne.getMessage());
		} catch (SolicitudNssCorreoException snc) {
			throw new AsignacionNssPersonaException(snc.getMessage()); 
		}

		//throw new PersonaSinNSSException(fisica.getNombre(),fisica.getPrimerApellido(),fisica.getSegundoApellido(),fisica.getSexo(),fisica.getFechaNacimiento());
	}

	@Override
	public Long obtenerIdPersonaPorNSS(String NSS) throws Exception {
		Long idPersona = serviceEntity.obtenerIdPersonaPorNSS(NSS);
		if(idPersona==null)
			throw new  Exception("Para realizar el tr\u00E1mite es necesario que cuentes con un NSS, puedes obtenerlo ingresando al Portal Ciudadano del IMSS");
		return idPersona;
	}
	
	
	
	@Override
	public Solicitud procesarSolicitudAsignacionNSSLigero (Solicitud solicitud, TramiteFisica tramiteFisica, TramiteAsegurado tramiteAsegurado)
			throws SolicitudNoEncontradaException,
			AfectacionDatosPersonaException, PersonaNoEncontradaException,
			DomicilioNoValidoException,
			NivelDeAsignacionSerieIndefinidoException,
			SeriesNoLocalizadasException, ErrorAlActivarSerieException,
			TramiteNoEncontradoException,
			CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			PersonaSinCalificacionesException, SolicitudNoValidaException {
		
		Long idSolicitud = solicitud.getSolicitudId();
		
		AseguradoWrapper aseguradoWrapper = null;
		
		AsignacionNSS fisica = tramiteAsegurado.getFisica();
		Fisica fisicaNueva = null;
		boolean datosComplementariosGuardados = false;
		TramiteAsegurado tramiteAseguradoSolicitud = null;
		TramiteFisica tramiteFisicaSolicitud = null;
		List<Tramite> tramites = solicitud.getTramites();
		
		for (Tramite tramite : tramites) {
			if (tramite instanceof TramiteFisica) {
				tramiteFisicaSolicitud = (TramiteFisica) tramite;
			} else if (tramite instanceof TramiteAsegurado) {
				tramiteAseguradoSolicitud = (TramiteAsegurado) tramite;
			}
		}
		
						
					
		if (tramiteFisica != null && tramiteFisica.getFisica() != null&& tramiteFisica.getTipoTramite() != null
				&& tramiteFisica.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.REGISTRO_DE_PERSONA.getCodigo())) {
				
			/* 
			 * Este tramite se encuentra cuando la persona a asignarle NSS
			 * es nueva y se debe crear
			 */
			this.log.info("Se va a generar una persona nueva dentro del tramite de Asignacion NSS");
			
			Fisica fisicaRegistrar = tramiteFisica.getFisica();
			fisicaNueva = this.personaBusiness.altaPersonaFisica(fisicaRegistrar);
			datosComplementariosGuardados = true;
			
			this.log.info("Id persona nueva desde asignacion de NSS -> "+ fisicaNueva.getIdPersona());
			fisica.setIdPersona(fisicaNueva.getIdPersona());
		}
		
				
		/*
		 * Se checa si se tiene que guardar los datos complementarios
		 * (domicilio, medios y calificaciones), se va a guardar si no se guard�
		 * antes en el tr�mite de alta de persona.
		 */
		if (!datosComplementariosGuardados) { 
			if (fisica.getDomicilios() != null && !fisica.getDomicilios().isEmpty() && fisica.getDomicilios().get(0).getClave() == null) {
				try {
					this.log.info("Se va a guardar un domicilio nuevo para el asegurado");
					this.componentesExternosBusiness.guardarYAsociarDomiciliosPersona(fisica);
				} catch (DomicilioNoValidoException e) {
					this.log.error("Error al crear el domicilio del asegurado", e);
				}
			}
						
			if (fisica.getPersonaCalificaciones() == null || fisica.getPersonaCalificaciones().isEmpty()) {
				// Se califica con RENAPO a la persona
				try {
					this.log.info("La persona [idPersona: " + fisica.getIdPersona()	+ "] no cuenta con calificaciones, por lo tanto, se va a calificar con RENAPO ");
					this.calificacionesPersonaBusinessService.calificarRENAPO(fisica);
				} catch (PersonaSinCalificacionesException e) {
					this.log.error("Error al calificar con RENAPO a la persona [idPersona: "
									+ fisica.getIdPersona() + "]", e);
				}
			}
		}
		
		AsignacionNSS asignacionNSS = null;
		try{
			asignacionNSS = serieServiceBusiness.guardaAseguradoConNSS(fisica, tramiteAsegurado.getAsignacionSerieNss().getSerie());
			tramiteAsegurado.getFisica().setIdAsignacionNSS(asignacionNSS.getIdAsignacionNSS());
		}catch(Exception e){
			log.error("error", e);
		}
		
				
		/*
		 * Se checa si el (los) tramite(s) asociados a la solicitud ya cuentan
		 * con la relaci�n hacia la persona y se le pone el NSS reci�n calculado
		 * al tramite de asignaci�n de NSS
		 */
		ArrayList<Tramite> lstTramites = new ArrayList<Tramite>();
		EstadoTramite estadoCerrado = new EstadoTramite();
		estadoCerrado.setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo().intValue());;
		if (tramiteAsegurado != null) {			
			if(tramiteAseguradoSolicitud != null){
				tramiteAsegurado.setTramiteId(tramiteAseguradoSolicitud.getTramiteId());
				this.log.debug("el id del tramite asegurado es" + tramiteAseguradoSolicitud.getTramiteId());
			}
			if (tramiteAsegurado.getFisica().getIdPersona() == null) {
				if (fisicaNueva != null) {
					/*
					 * En caso de que se haya creado una persona nueva, se
					 * asocian los tramite de registro con la persona reci�n
					 * creada
					 */
					tramiteAsegurado.getFisica().setIdPersona(fisicaNueva.getIdPersona());			
				} else {
					tramiteAsegurado.getFisica().setIdPersona(fisica.getIdPersona());
				}
			}
			tramiteAsegurado.setFechaConclusion(new Date());
			tramiteAsegurado.setEstadoTramite(estadoCerrado);
			tramiteAsegurado.setTipoTramite(new TipoTramite(TipoTramiteEnum.ASIGNACION_NSS.getCodigo()));
			lstTramites.add(tramiteAsegurado);
		} 
		
		if (tramiteFisica != null) {	
			if(tramiteFisicaSolicitud != null){
				tramiteFisica.setTramiteId(tramiteFisicaSolicitud.getTramiteId());
				this.log.debug("el id del tramite fisica es" + tramiteFisicaSolicitud.getTramiteId());
			}
			
				
			if (tramiteFisica.getFisica().getIdPersona() == null) {
				if (fisicaNueva != null) {
					/*
					 * En caso de que se haya creado una persona nueva, se
					 * asocian los tramite de registro con la persona reci�n
					 * creada
					 */
					tramiteFisica.getFisica().setIdPersona(fisicaNueva.getIdPersona());			
				} else {
					tramiteFisica.getFisica().setIdPersona(fisica.getIdPersona());
				}
			}
			tramiteFisica.setFechaConclusion(new Date());
			tramiteFisica.setEstadoTramite(estadoCerrado);
			lstTramites.add(tramiteFisica);
		}
	
		
		
			solicitud.getTramites().clear();
			
			solicitud.setTramites(lstTramites);
			this.log.debug("se actualizan los tramites con los id originales " + tramiteAsegurado.getFisica().getNss());
				
		try {
			this.solicitudBusiness.actualizarTramites(solicitud);
		} catch (TramiteNoEncontradoException e) {
			this.log.error(e);
		}
		
		// Se cambia el estado de la solicitud a CONCLUIDA
		this.solicitudBusiness.actualizarSolicitudAEstatusConcluida(solicitud);
		
		
		
		/* 
		 * Se genera el wrapper con los datos escenciales para su retorno,
		 * se utiliza este objeto "simple" para que OSB no tenga problemas
		 * al transformalo.
		 */
		aseguradoWrapper = ServiceUtility.crearAseguradoWrapper(fisica);
		aseguradoWrapper.setEstatusRegistro(AseguradoWrapper.ASIGNADO);
		
		this.log.info("Se finalizo la solicitud de Asignacion NSS ["
				+ idSolicitud + "] y se devuelve la siguiente informacion: "
				+ aseguradoWrapper);
		
		return solicitud;
	}
	
	@Override
	public TramiteActualizacionAsegurado validacionesNSSActualizaCURP(Fisica fisica)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			ErrorComparacionDatosRENAPOException,
			PersonaConNSSException, AsignacionNssPersonaException,  GenerarNSSException, WsAntecedentesAseguradoExcpetion{
		
		
		String curpCapturado = fisica.getCurp();
		List<Fisica> personasEncontradas = null;
		Fisica fisicaRENAPOCurpLocalizado = null;
		Fisica fisicaEncontrada = null;
		String curpEncontrado = null;
		TramiteActualizacionAsegurado tramite = new TramiteActualizacionAsegurado();

		Fisica fisicaRENAPO = this.localizarPersonaFisicaEnRENAPOServiceBusiness
					.localizarPersonaFisicaEnRENAPOxCURP(fisica.getCurp());
		
		tramite.setFisicaNueva(fisicaRENAPO);
		
		
		
		personasEncontradas = personaBusiness.obtenerPersonaNssByCurpNoIndActivo(curpCapturado);

		if (personasEncontradas != null && !personasEncontradas.isEmpty()) {
			if (personasEncontradas.size() > 1) {
				// La persona cuenta con m�s de un NSS
				log.debug("-----------------------------------> La persona con CURP "
						+ curpCapturado + " cuenta con m�s de un NSS");
				throw new GenerarNSSException(MENSAJE_ACTUALIZACION_GENERICO);
			} else {
				fisicaEncontrada = personasEncontradas.get(0);
				if(serviciosExternosAseguradosBusiness.validaAseguradoConAntecedentePasoCambioAl(fisicaEncontrada.getNss())){
					log.debug("el asegurado cuenta con antecedentes");
					throw new GenerarNSSException(MENSAJE_ACTUALIZACION_GENERICO);
				}
				
				try{
					fisicaEncontrada = compararPersonaVsRenapo(fisica, fisicaEncontrada, fisicaRENAPO, true);
				}catch(PersonaConNSSException ex){
					fisicaEncontrada = ex.getFisica();
				}
				
				if(fisicaEncontrada.getCurp().equals(fisicaRENAPO.getCurp())
						&& fisicaEncontrada.getFechaNacimiento()!= null){
					throw new AsignacionNssPersonaException(MENSAJE_SIN_DIFERENCIAS_CURP_RENAPO_IMSS);
				}
				tramite.setFisicaAnterior(fisicaEncontrada);
				return tramite; 
			}
		}
		else{
			log.debug("-----------------------------------> No se encontraron coincidencias con NSS en la b�squeda por CURP");
	
			List<Fisica> personasConNSS = new ArrayList<Fisica>();
			List<Fisica> personasConCurpConCalificacion = new ArrayList<Fisica>();
			List<Fisica> personasConCurpSinCalificacion = new ArrayList<Fisica>();
			List<Fisica> personasEncontradasAuxConNSS = new ArrayList<Fisica>();
	
			// Se buscan a las personas que coincidan con datos b�sicos
			try {
				personasEncontradasAuxConNSS = personaFisicaServiceBusiness
						.localizarPersonaFisicaPorDatosBasicosEnImssConNSS(fisicaRENAPO);
			} catch (DatosInsuficientesParaConsultaException e) {
				log.error(e);
			}
	
			for (Fisica fisicaAux : personasEncontradasAuxConNSS) {
				if (StringUtils.isNotBlank(fisicaAux.getNss())) {
					personasConNSS.add(fisicaAux);
				}
			}

			if (!personasConNSS.isEmpty()) {
			
			log.debug("-----------------------------------> Se encontraron " + personasConNSS.size()
					+ " que coincidieron en datos b�sicos y tienen NSS");

				if (personasConNSS.size() == 1) {
					fisicaEncontrada = personasConNSS.get(0);
					log.debug("-----------------------------------> Persona localizada [idPersona:"
						+ fisicaEncontrada.getIdPersona() + "]");
					if(serviciosExternosAseguradosBusiness.validaAseguradoConAntecedentePasoCambioAl(fisicaEncontrada.getNss())){
						log.debug("el asegurado cuenta con antecedentes");
						throw new GenerarNSSException(MENSAJE_ACTUALIZACION_GENERICO);
					}
				// Se consulta si la persona cuenta con calificaciones
					obtenerCalificacionesVigentesCommon(fisicaEncontrada);
			//PARA ESTE FLUJO NO SE CONCIDERA VALIDAR LAS CALIFIACIONES
				//validarCalificaciones(fisicaEncontrada, validarCalificaciones);
					
					curpEncontrado = fisicaEncontrada.getCurp();
					if (StringUtils.isNotBlank(curpEncontrado)) {
						
						if (curpEncontrado.equalsIgnoreCase("000000000000000000")
								|| curpEncontrado.length() != LONGITUD_CURP
								|| !curpEncontrado.matches(REGEX_CURP_FISICA)) {
							fisicaEncontrada.setCurp(null);
							tramite.setFisicaAnterior(fisicaEncontrada);
							return tramite;
						} 
						//AL PARECER NO SERIA NECESARIO VOLVER A VALIDAR CONTRA RENAPO YA QUE ESTAMOS BUSCANDO LA UNICIDAD YA QUE LA CURP ES HIST�RICA
						//SOLO SE VALIDA QUE LA CURP DEVUELTA POR RENAPO SEA DISTINTA A LA LOCALIZADA EN EL REGISTRO
						else {
							/*
							fisicaRENAPOCurpLocalizado = personaBusiness.buscarPersonaFisicaPorCurpEnRenapo(
									fisicaEncontrada.getCurp());
	
							if (fisicaRENAPOCurpLocalizado != null) {
								String curpRenapoPersonaEncontrada = fisicaRENAPOCurpLocalizado.getCurp();
								String curpRenapo = fisicaRENAPO.getCurp();
			
								if (curpRenapoPersonaEncontrada.equals(curpRenapo)) {
									return compararPersonaVsRenapo(fisica,
											fisicaEncontrada, fisicaRENAPO,
											validarDifSoloFecNac);
								} else {
									throw new GenerarNSSException(MENSAJE_ERROR_COMPARACION);
								}
							} else {
								throw new GenerarNSSException(MENSAJE_ERROR_COMPARACION);
							}
							*/
							if(fisicaEncontrada.getCurp().equals(fisicaRENAPO.getCurp()) 
									&& fisicaEncontrada.getFechaNacimiento()!= null){
								throw new AsignacionNssPersonaException(MENSAJE_SIN_DIFERENCIAS_CURP_RENAPO_IMSS);
							}
							
							tramite.setFisicaAnterior(fisicaEncontrada);
							return tramite;
							
						}
					} else {
						tramite.setFisicaAnterior(fisicaEncontrada);
						return tramite;
					}
				} else {
					/*				 * Se encontraron varias coincidencias, por lo tanto, se
					 * manda a ventanilla
					 */
					log.debug("-----------------------------------> La persona con CURP "
							+ curpCapturado + " tiene varias coincidencias");
	
					throw new AsignacionNssPersonaException(MENSAJE_ACTUALIZACION_GENERICO);
				}
			} else {
				throw new AsignacionNssPersonaException(MENSAJE_ACTUALIZACION_GENERICO);
			}
		}
	}
	
	/*
	@Override
	public TramiteActualizacionAsegurado validacionesNSSActualizaCURPporNSS(Fisica fisica)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			ErrorComparacionDatosRENAPOException,
			PersonaConNSSException,
			AsignacionNSSNoLocalizadoException,
			AsignacionNssPersonaException,
			GenerarNSSException{
		
		
		String curpCapturado = fisica.getCurp();
		List<Fisica> personasEncontradas = null;
		Fisica fisicaRENAPOCurpLocalizado = null;
		Fisica fisicaEncontrada = null;
		
		AsignacionNSS asignacionLocalizado = null;
		TramiteActualizacionAsegurado tramite = new TramiteActualizacionAsegurado();

		Fisica fisicaRENAPO = this.localizarPersonaFisicaEnRENAPOServiceBusiness
					.localizarPersonaFisicaEnRENAPOxCURP(fisica.getCurp());
		
		asignacionLocalizado = this.obtenerAsignacionNss(fisica.getNss());
		
		if(asignacionLocalizado == null){
			throw new AsignacionNSSNoLocalizadoException("No se localiz� ning�n registro con el NSS capturado");
		}
		
		fisicaEncontrada = (Fisica)asignacionLocalizado;
		fisicaEncontrada.setNss(asignacionLocalizado.getNss());
		
		
		if (StringUtils.isNotBlank(fisicaEncontrada.getCurp())) {
			
			if(fisicaEncontrada.getCurp().equals(fisicaRENAPO.getCurp())
					&& fisicaEncontrada.getFechaNacimiento()!= null){
				throw new AsignacionNssPersonaException(MENSAJE_SIN_DIFERENCIAS_CURP_RENAPO_IMSS);
			}
		}
		
		try{
		fisicaEncontrada = compararPersonaVsRenapo(fisica, fisicaEncontrada, fisicaRENAPO, true);
		}catch(PersonaConNSSException ex){
			fisicaEncontrada = ex.getFisica();
		}
		tramite.setFisicaAnterior(fisicaEncontrada);
		tramite.setFisicaNueva(fisicaRENAPO);
		return tramite; 
	}
	
	**/
	
	@Override
	public TramiteActualizacionAsegurado validacionesNSSActualizaCURPporNSS(Fisica fisica)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			ErrorComparacionDatosRENAPOException,
			PersonaConNSSException,
			AsignacionNSSNoLocalizadoException,
			AsignacionNssPersonaException,
			GenerarNSSException, WsAntecedentesAseguradoExcpetion{
		
		
		String curpCapturado = fisica.getCurp();
		List<Fisica> personasEncontradas = null;
	
		Fisica fisicaEncontrada = null;
		boolean consultaDatosBasicos = false;
		TramiteActualizacionAsegurado tramite = new TramiteActualizacionAsegurado();

		Fisica fisicaRENAPO = this.localizarPersonaFisicaEnRENAPOServiceBusiness
					.localizarPersonaFisicaEnRENAPOxCURP(fisica.getCurp());
		
		tramite.setFisicaNueva(fisicaRENAPO);
		if(serviciosExternosAseguradosBusiness.validaAseguradoConAntecedentePasoCambioAl(fisica.getNss())){
			log.debug("el asegurado cuenta con antecedentes");
			throw new GenerarNSSException(MENSAJE_ACTUALIZACION_GENERICO);
		}
		
		//valida por CURP de renapo se cambio para que sea mas de uno ya que puede ser la cupr sin fecha
		List <Fisica> personasRenapo = personaBusiness.buscarPersonaFisicaPorCurpEnImss(fisicaRENAPO.getCurp());
		if(personasRenapo != null && personasRenapo.size()>1){
			log.debug("---------------------ya existe alguien con la CURP de renapo");
			throw new GenerarNSSException(MENSAJE_ACTUALIZACION_GENERICO);
		}
		
		personasEncontradas = personaBusiness.obtenerPersonaNssByCurpNoIndActivo(curpCapturado);
		
		if (personasEncontradas == null) {
			try {
				personasEncontradas = personaFisicaServiceBusiness
						.localizarPersonaFisicaPorDatosBasicosEnImssConNSS(fisicaRENAPO);
				consultaDatosBasicos = true;
			} catch (DatosInsuficientesParaConsultaException e) {
				log.error(e);
			}
		}

		if (personasEncontradas != null && !personasEncontradas.isEmpty()) {
			if (personasEncontradas.size() > 1) {
				// La persona cuenta con m�s de un NSS
				log.debug("-----------------------------------> La persona con CURP "
						+ curpCapturado + " cuenta con m�s de un NSS");
				throw new GenerarNSSException(MENSAJE_ACTUALIZACION_GENERICO);
			} else {
				fisicaEncontrada = personasEncontradas.get(0);
				
				try{
					this.validarFormatoCurp(fisicaEncontrada, true);
				}catch(Exception e){
					log.debug("la persona localizada no tiene una CURP valida");
					throw new GenerarNSSException(MENSAJE_ACTUALIZACION_GENERICO);
				}
				if(!fisicaEncontrada.getNss().equals(fisica.getNss())){
					log.debug("-----------------------------------> La persona localizada con la CURP no conciden con el NSS Capturado"
							+ curpCapturado );
					throw new GenerarNSSException(MENSAJE_ACTUALIZACION_GENERICO);
				
				}
				
				
				//compara datos estadisticos para validar que solo se actualiza la CURP
				//TODO no se valida datos estadisticos por que ya viene del a busqueda de la persona
/*
				try{
					fisicaEncontrada = compararPersonaVsRenapo(fisica, fisicaEncontrada, fisicaRENAPO, false);
				}catch(PersonaConNSSException ex){
					fisicaEncontrada = ex.getFisica();
				}
*/		
				//se valida que los CURPS no sean iguales
				
				//TODO se adiciona validacion para que considera la fecha de nacimiento para los casos que todos los datos son
				//correctos
				if(fisicaEncontrada.getCurp().equals(fisicaRENAPO.getCurp()) && fisicaEncontrada.getFechaNacimiento() != null){
					log.debug("------------------------------------->los CURPS SON iguales no hay que actualizar");
					throw new AsignacionNssPersonaException(MENSAJE_SIN_DIFERENCIAS_CURP_RENAPO_IMSS);
				}
				
				
				/* Se comenta la seccion de califiacioens ya que al no tener fecha de nac no estara calificado
				List <PersonaCalificacion> calificaciones = fisicaEncontrada.getPersonaCalificaciones();
				if(calificaciones != null && !calificaciones.isEmpty()){
					boolean isValidadoRenapo = false;
					for(PersonaCalificacion calificacion :calificaciones){
						if(calificacion.getCalificacion().getIdCalificacion().longValue() == CalificacionEnum.VALIDADO_RENAPO.getCodigo().longValue()){
							isValidadoRenapo = true;
							break;
						}
					}
					if(!isValidadoRenapo){
						log.debug("-----------------------------------> La persona tiene NSS y calificacion "
								+ curpCapturado + " y no tiene calificacion de RENAPO");
						throw new GenerarNSSException(MENSAJE_ACTUALIZACION_GENERICO);
					}
				}else{
					log.debug("-----------------------------------> La persona tiene NSS "
							+ curpCapturado + " y no tiene calificaciones");
					throw new GenerarNSSException(MENSAJE_ACTUALIZACION_GENERICO);
				}
				**/
				if(consultaDatosBasicos){
					//se consulta si la persona localizada con CURP coincide con la persona de RENAPO
					try{
					Fisica fisicaRENAPOLocalizada = this.localizarPersonaFisicaEnRENAPOServiceBusiness
							.localizarPersonaFisicaEnRENAPOxCURP(fisicaEncontrada.getCurp());
					//se valida que los CURPS sean iguales
						if(!fisicaRENAPOLocalizada.getCurp().equals(fisicaRENAPO.getCurp())){
							log.debug("------------------------------------->los CURPS NO SON iguales es otra persona curp persona BDTU [" +fisicaRENAPOLocalizada+"]"
									+ " CURP devuelta por esa persona de RENAPO [" +fisicaRENAPOLocalizada.getCurp()+"] CUPR de renapo primer CONSULTA [" +fisicaRENAPO.getCurp()+ "]" );
							throw new GenerarNSSException(MENSAJE_ACTUALIZACION_GENERICO);
						}
					}catch (Exception e){
						log.error("ocurrio un error al consultar en renapo con la CURP localizada en BDTU [ "+fisicaEncontrada.getCurp() + "]" ,e	);
						throw new GenerarNSSException(MENSAJE_ACTUALIZACION_GENERICO);
					}
					
					
				}
				//valida que la persona no tenga mas de un NSS
				try{
					List <AsignacionNSS> asingList  =  grupoFamiliarServiceRemote.getAsignacionNss(fisicaEncontrada.getIdPersona());
					if(asingList != null && asingList.size() != 1){
						log.debug("-----------------------------------> La persona tiene mas de 1 NSS" + curpCapturado );
						throw new GenerarNSSException(MENSAJE_ACTUALIZACION_GENERICO);
					}
				}catch(DerechohabientesBusinessException e){
					log.error("ocurrio un error al consultar los NSS de la persona [" + fisicaEncontrada.getIdPersona()+ "]", e);
					throw new GenerarNSSException(MENSAJE_ACTUALIZACION_GENERICO);
				}
				
				//valida que no existan mas personas con los mismos datos o con misma CURP o la CURP devuela por RENAPO//
				
				//TODO se cambia la validacion ya que no importa si ya existe una persona con esos datos ya que diferencia el NSS
				/*
				try{
					List<Fisica> fisicasDatos = this.personaFisicaServiceBusiness.localizarPersonaFisicaPorDatosBasicosEnImss(fisicaEncontrada);		
					//valida datos basicosS
					if(fisicasDatos!= null && fisicasDatos.size() >1){
						log.debug("-----------------------------------> existen personas con los mismos datos" );
						throw new GenerarNSSException(MENSAJE_ACTUALIZACION_GENERICO);
					}			
				}catch(DatosInsuficientesParaConsultaException e){
					log.error("error al querer consultar a la persona localizada" , e);
					throw new GenerarNSSException(MENSAJE_ACTUALIZACION_GENERICO); 
				} */
				
				
				tramite.setFisicaAnterior(fisicaEncontrada);
				return tramite; 
			}
		}
		else{
			log.debug("No se encontro informacion con el CURP ni capturado o renapo");
			throw new GenerarNSSException(MENSAJE_ACTUALIZACION_GENERICO); 
		}
	}
	
	
	/**
	 * Metodo que busca la curp en renapo y compara contra los datos estadisticos en bdtu de la persona con NSS
	 * valida que la persona BDTU tenga CURP aunque sea historica
	 * @param strCurp
	 * @param strNSS
	 * @return
	 * @throws AsignacionNssPersonaException
	 */
	@Override
	public boolean validaDatosPersonaRenapoPersonaNSSBdtu(String strCurp, String strNSS) throws AsignacionNssPersonaException{
		
		boolean isRegistroValido = false;
		
		Fisica fisicaEncontrada = null;
		Fisica fisicaRENAPO = null;
		/*validamos los datos de entrada */
		if(StringUtils.isEmpty(strCurp) || StringUtils.isEmpty(strNSS)){
			throw new AsignacionNssPersonaException("Los datos de entrada son nulos o vacios");
		}
		
		
		AsignacionNSS asignacionLocalizado = null;
		try{
			fisicaRENAPO = this.localizarPersonaFisicaEnRENAPOServiceBusiness
					.localizarPersonaFisicaEnRENAPOxCURP(strCurp);
		}catch(Exception e){
			log.error("ocurrio un error al recuperar los datos de la CURP " + strCurp , e);
			throw new AsignacionNssPersonaException(e.getMessage());
		}
		
		/*
		if(!fisicaRENAPO.getCurp().equals(strCurp)){
			log.debug("-----------------------------------> La CURP localizada en RENAPO  no coincide con la CURP capturada");
			throw new AsignacionNssPersonaException("La CURP localizada en RENAPO  no coincide con la CURP capturada");
		}*/
		
		asignacionLocalizado = this.obtenerAsignacionNss(strNSS);
		
		if(asignacionLocalizado == null){
			throw new AsignacionNssPersonaException("No se localiz� ning�n registro con el NSS capturado");
		}
		
		fisicaEncontrada = (Fisica)asignacionLocalizado;
		fisicaEncontrada.setNss(asignacionLocalizado.getNss());
		
		
		
		try{
			this.validarFormatoCurp(fisicaEncontrada, true);
		}catch(Exception e){
			log.debug("la persona localizada no tiene una CURP valida");
			throw new AsignacionNssPersonaException("EL Registro en BDTU no tiene CURP o es inv�lido");
		}
		/*
		if(!fisicaEncontrada.getCurp().equals(strCurp)){
			log.debug("-----------------------------------> La persona localizada con NSS no coincide con la CURP capturada");
			throw new AsignacionNssPersonaException("La persona localizada con NSS no coincide con la CURP capturada");
		
		}
		*/
		
		
		try{
		fisicaEncontrada = compararPersonaVsRenapo(fisicaEncontrada, fisicaEncontrada, fisicaRENAPO, true);
		}catch(PersonaConNSSException ex){
			fisicaEncontrada = ex.getFisica();
		}catch(Exception e){
			log.error("ocurrio un error o los datos no coinciden ", e);
			throw new AsignacionNssPersonaException("No coinciden los datos estad�sticos entre RENAPO y los localizados en el instituto");
		}
		/*se valida si la CURP de renapo con la localizada son distintas es CURP HISTORICA*/
		if(!fisicaRENAPO.getCurp().equals(fisicaEncontrada.getCurp())){
		try{
			Fisica fisicaRENAPOLocalizada = this.localizarPersonaFisicaEnRENAPOServiceBusiness
					.localizarPersonaFisicaEnRENAPOxCURP(fisicaEncontrada.getCurp());
			//se valida que los CURPS sean iguales
			if(fisicaRENAPOLocalizada.getCurp().equals(fisicaRENAPO.getCurp())){
				log.debug("------------------------------------->los CURPS NO SON iguales es otra persona");
				throw new AsignacionNssPersonaException("La CURP localizada en el instituto es distinta a la de RENAPO");
			}
			}catch (Exception e){
				log.error("ocurrio un error al consultar en renapo con la CURP localizada en BDTU [ "+fisicaEncontrada.getCurp() + "]" ,e	);
				throw new AsignacionNssPersonaException(e.getMessage());
			}
		
		}
		isRegistroValido = true;
		return isRegistroValido;
	}
	
	
	private Solicitud crearSolicitudInicialPorEnum(EstadoSolicitudEnum estadoSolicitudInicial,
				TipoSolicitudEnum tipoSolicitudInicial, OrigenSolicitudEnum origenSolicitudInicial, Usuario usuario)
				throws SolicitudNoValidaException {

			// Estado de la solicitud
			EstadoSolicitud estadoSolicitud;
			if (estadoSolicitudInicial != null) {
				estadoSolicitud = new EstadoSolicitud();
				estadoSolicitud.setIdEstadoSolicitud(estadoSolicitudInicial.getCodigo());
			} else {
				estadoSolicitud = null;
			}

			// Tipo solicitud
			TipoSolicitud tipoSolicitud = null;
			if (tipoSolicitudInicial != null) {
				tipoSolicitud = new TipoSolicitud();
				tipoSolicitud.setIdTipoSolicitud(tipoSolicitudInicial.getValor().longValue());
			} else {
				log.warn("la solicitud no tiene definido el tipo de solicitud.");
			}

			// Origen de Solicitud
			OrigenSolicitud origenSolicitud;
			if (origenSolicitudInicial != null) {
				origenSolicitud = new OrigenSolicitud();
				origenSolicitud.setIdTipoSolicitud(origenSolicitudInicial.getId());
			} else {
				origenSolicitud = null;
			}

			return crearSolicitudInicial(estadoSolicitud, tipoSolicitud, origenSolicitud, usuario);
		}
	
	//TODO se copian metodos de solicitud  en lo que se sube solicitudes a produccion
	 private Solicitud crearSolicitudInicial(EstadoSolicitud estadoSolicitud,
				TipoSolicitud tipoSolicitud, OrigenSolicitud origenSolicitud,
				Usuario usuario) throws SolicitudNoValidaException {
			Date fechaActual = new Date();

			Solicitud solicitud = new Solicitud();

			// Datos generales
			solicitud.setFechaSolicitud(fechaActual);

			// Estado de la solicitud
			if (estadoSolicitud != null) {
				Integer idEstadoSolicitud = estadoSolicitud.getIdEstadoSolicitud();

				if (idEstadoSolicitud != null
						&& idEstadoSolicitud.equals(EstadoSolicitudEnum.ATENDIDA.getCodigo())) {
					solicitud.setFechaConclusion(fechaActual);
				}
			} else {
				estadoSolicitud = new EstadoSolicitud();
				estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.REGISTRADA.getCodigo());
			}
			solicitud.setEstadoSolicitud(estadoSolicitud);

			// Tipo solicitud
			if (tipoSolicitud != null) {
				solicitud.setTipoSolicitud(tipoSolicitud);
			} else {
				log.warn("la solicitud no tiene definido el tipo de solicitud.");
			}

			// Origen de Solicitud
			if (origenSolicitud == null) {
				origenSolicitud = new OrigenSolicitud();
				origenSolicitud.setIdTipoSolicitud(OrigenSolicitudEnum.INTERNET.getId());
			}
			solicitud.setOrigenSolicitud(origenSolicitud);

			// Usuario asociado
			if (usuario != null) {
				solicitud.setSolicitante(usuario);
			}

			return solicitud;
		}
	
	 private Solicitud asociarTramiteSolicitudPorEnum(Solicitud solicitud,
				Tramite tramite, TipoTramiteEnum tipoTramiteInicial,
				EstadoTramiteEnum estadoTramiteInicial)
				throws SolicitudNoValidaException {

			Tramite tramiteInicial = inicializarTramitePorEnum(tramite,
					tipoTramiteInicial, estadoTramiteInicial);

			// Asociacion de solicitudes
			if (solicitud.getTramites() == null) {
				List<Tramite> listTramite = new ArrayList<Tramite>();
				listTramite.add(tramiteInicial);

				solicitud.setTramites(listTramite);
			} else {
				solicitud.getTramites().add(tramiteInicial);
			}

			return solicitud;
		}
	 
	private Tramite inicializarTramitePorEnum(Tramite tramite,
				TipoTramiteEnum tipoTramiteInicial, EstadoTramiteEnum estadoTramiteInicial)
				throws SolicitudNoValidaException {

			// Estado del tramite
			EstadoTramite estadoTramite;
			if (estadoTramiteInicial != null) {
				estadoTramite = new EstadoTramite();
				estadoTramite.setIdEstadoTramitePersona(estadoTramiteInicial.getCodigo());
				estadoTramite.setDescripcion(estadoTramiteInicial.getDescripcion());
			} else {
				estadoTramite = null;
			}

			// Tipo de tramite
			TipoTramite tipoTramite = null;
			if (tipoTramiteInicial != null) {
				tipoTramite = new TipoTramite();
				tipoTramite.setIdTipoTramite(tipoTramiteInicial.getCodigo());
				tramite.setTipoTramite(tipoTramite);
			} else {
				log.warn("al menos uno de sus tramites no tiene definido el tipo de tramite.");
			}

			return inicializarTramite(tramite, estadoTramite, tipoTramite);
		}
	
	private Tramite inicializarTramite(Tramite tramite,
			EstadoTramite estadoTramite, TipoTramite tipoTramite)
			throws SolicitudNoValidaException {
		Date fechaActual = new Date();

		// Datos generales
		tramite.setFechaTramite(fechaActual);
		tramite.setFechaPresentacion(fechaActual);

		// Estado del tramite
		if (estadoTramite != null) {
			Integer idEstadoTramite = estadoTramite.getIdEstadoTramitePersona();

			if (idEstadoTramite != null
					&& idEstadoTramite.equals(EstadoTramiteEnum.CERRADO.getCodigo())) {
				tramite.setFechaConclusion(fechaActual);
			}
		} else {
			estadoTramite = new EstadoTramite();
			estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO.getCodigo());
			estadoTramite.setDescripcion(EstadoTramiteEnum.INICIADO.getDescripcion());
		}
		tramite.setEstadoTramite(estadoTramite);

		// Tipo de tramite
		if (tipoTramite != null) {
			tramite.setTipoTramite(tipoTramite);
		} else {
			log.warn("al menos uno de sus tramites no tiene definido el tipo de tramite.");
		}

		return tramite;
	}
	
	
	@Override
	public PersonaTO validaAccesoPortalExterno(String curp, String correo)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			PersonaSinNSSException, ErrorComparacionDatosRENAPOException,
			SolicitudNoValidaException, SolicitudException,
			AsignacionNssPersonaException {

		// Validacion del formato de la CURP
		if (StringUtils.isBlank(curp) || curp.length() != LONGITUD_CURP) {
			throw new AsignacionNssPersonaException("La CURP tiene que ser de " + LONGITUD_CURP + " caracteres");
		} else if (!curp.matches(REGEX_CURP_FISICA)) {
			throw new AsignacionNssPersonaException("La CURP no cumple con el formato requerido");
		}

		// Validacion del formato de correo
		if (StringUtils.isBlank(correo)) {
			throw new AsignacionNssPersonaException("El correo no debe ser nulo o vac\u00EDo");
		} else if (!correo.matches(EMAIL_PATTERN)) {
			throw new AsignacionNssPersonaException("El correo electronico no cumple con el formato requerido");
		}

		// Se pasa a min�sculas el correo capturado
		String correoCapturado = correo.toLowerCase();

		// Se llena objeto con los datos de b�squeda
		Fisica fisica = new Fisica();
		fisica.setCurp(curp);
		CorreoElectronico correoElectronico = new CorreoElectronico();
		correoElectronico.setCorreo(correoCapturado);
		fisica.setCorreoElectronico(correoElectronico);
		
		CiudadanoCurpCorreo ciudadanoCurp = null;
		
		try{
			log.debug("voy a hacer la llamada a la validacion");
			ciudadanoCurp = portalCiudadanoServiceBusiness.validaRegistroCurpCorreoCiudadano(fisica.getCurp(), fisica.getCorreoElectronico().getCorreo());
			log.debug("sali de  a hacer la llamada a la validacion");
			// Validacion de NSS
			
			/*se cambia la llamda para que use el mismo m�todo que asegurados para permitir el acceso sin validar fecha de nac
			 * y clon 3 de asegurados.
			 */
			//Fisica localizada = validacionesNSS(fisica, true);
						// Se transforma persona encontrada sin NSS
			
			Fisica localizada = validacionesNSSIncluyeCL3(fisica, false);
			PersonaTO personaEncontrada = new PersonaTO();
			personaEncontrada.setNombre(localizada.getNombre());
			personaEncontrada.setPrimerApellido(localizada.getPrimerApellido());
			personaEncontrada.setSegundoApellido(localizada.getSegundoApellido());
			personaEncontrada.setSexo(localizada.getSexo());
			personaEncontrada.setFechaNacimiento(localizada.getFechaNacimiento());
			personaEncontrada.setErrorFormGeneral("La persona no cuenta con NSS");

			
				if(ciudadanoCurp == null){
					portalCiudadanoServiceBusiness.validarInicioCurpCorreo(fisica.getCurp(), fisica.getCorreoElectronico().getCorreo(), true);			
				}
	
				return personaEncontrada;
		}catch (PersonaConNSSException e) {
			//cacha como atributo el caso de encuentre asignacion
			Fisica personaConNSS = e.getFisica();

			if (personaConNSS.getCorreoElectronico() != null) {
				personaConNSS.getCorreoElectronico().setCorreo(correo);
			} else {
				personaConNSS.setCorreoElectronico(new CorreoElectronico());
				personaConNSS.getCorreoElectronico().setCorreo(correo);
			}
			// Se transforma persona encontrada
			PersonaTO personaEncontrada = new PersonaTO();
			personaEncontrada.setNss(personaConNSS.getNss());
			personaEncontrada.setNombre(personaConNSS.getNombre());
			personaEncontrada.setPrimerApellido(personaConNSS.getPrimerApellido());
			personaEncontrada.setSegundoApellido(personaConNSS.getSegundoApellido());
			personaEncontrada.setSexo(personaConNSS.getSexo());
			personaEncontrada.setFechaNacimiento(personaConNSS.getFechaNacimiento());
			if(ciudadanoCurp == null){
				try{
					portalCiudadanoServiceBusiness.validarInicioCurpCorreo(fisica.getCurp(), fisica.getCorreoElectronico().getCorreo(), true);			
			
				}catch(Exception ex){
					log.error("ocurrio un error al querer guardar la relacion CURP correo", ex);
				}
			}
			return personaEncontrada;
			
		}catch (PortalCiudadanoException e){
			throw new AsignacionNssPersonaException(e.getMessage());
		} catch (GenerarNSSException gne) {
			throw new AsignacionNssPersonaException(MENSAJE_GENERICO_ACCESO_APPS_MOVILES);
		} catch (UmfNoLocalizadaException unle) {
			throw new AsignacionNssPersonaException(unle.getMessage());
		} catch (DomicilioNoLocalizadoException dne) {
			throw new AsignacionNssPersonaException(dne.getMessage());
		}catch(Exception e){
			log.error("OCurrio un error no cachado", e);
			throw new AsignacionNssPersonaException(e.getMessage());
		}

		//throw new PersonaSinNSSException(fisica.getNombre(),fisica.getPrimerApellido(),fisica.getSegundoApellido(),fisica.getSexo(),fisica.getFechaNacimiento());
	}
	
	
	
	@Override
	public Fisica validacionesNSSIncluyeCL3(Fisica fisica,
			boolean validarSolicitudesActivas)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			GenerarNSSException, ErrorComparacionDatosRENAPOException,
			PersonaConNSSException, DomicilioNoLocalizadoException, UmfNoLocalizadaException {
						
		/*
		 * Se busca en RENAPO el CURP capturado, en caso de que el servicio no
		 * encuentre el CURP lanza una excepci�n
		 */
		Fisica fisicaRENAPO = this.localizarPersonaFisicaEnRENAPOServiceBusiness
				.localizarPersonaFisicaEnRENAPOxCURP(fisica.getCurp());

		ValidarAsignacionLocalizacionNssWrapper validacionWrapper = new ValidarAsignacionLocalizacionNssWrapper();
		validacionWrapper.setFisica(fisica);
		validacionWrapper.setValidarSolicitudesActivas(validarSolicitudesActivas);
		validacionWrapper.setValidarCalificaciones(true);
		validacionWrapper.setValidarFormatoCurp(true);
		validacionWrapper.setValidaAseguradoCL3(true);

		return validacionesNSSCommon(fisicaRENAPO, validacionWrapper);
	}
	
	
	/**	
	 * Metodo encargado de buscar el NSS en BDTU de no encontrarse buscan en las tablas de legados
	 * recibe parametro de legados para saber si se incluyen aun cuando si se encuentre en BDTU el NSS
	 * @param nss String con el NSS a 11 posicioens
	 * @param legados boleano para indicar si se consultan los legados aun cuando se localice en NSS en BDTU
	 * @return Lista de personas con datos basicos y el NSS se seteara el origen donde se encontr� el NSS en los indicadores
	 */
	public List<Fisica> getAseguradoByNSSLegadosyBDTU(String nss, Boolean legados) throws IllegalArgumentException{
		List<Fisica> lstFisicaNSS = null;
		
		if(StringUtils.isNotEmpty(nss) && nss.length()== 11){
			lstFisicaNSS = new ArrayList<Fisica>();
			Fisica fisicaValidada = null;
			try{
				try{
						fisicaValidada = personaFisicaServiceBusiness.localizarPersonaFisicaPorNss(nss);
				}catch(Exception e){
					log.warn("Ocurrio un erro cachado al consultar el NSS puede que no exista", e);
				}
				if(fisicaValidada != null){
					OrigenConsultaNssEnum enumOrigen = OrigenConsultaNssEnum.obtenerEnumById(ORIGEN_BDTU);
					Identificador identificaOrigan = new Identificador();
					identificaOrigan.setIdIdentificador(enumOrigen.getClave());
					identificaOrigan.setIdentificadora(enumOrigen.getDescripcion());
					List<Identificador> lstIndentificadores = new ArrayList<Identificador>();
					lstIndentificadores.add(identificaOrigan);
					fisicaValidada.setIdentificadores(lstIndentificadores);
					lstFisicaNSS.add(fisicaValidada);
					if(!legados){
						return lstFisicaNSS;
					}
				}
				lstFisicaNSS.addAll(aseguradoEntity.getAseguradoByNSSLegados(nss));
				return lstFisicaNSS;
			}catch(Exception e){
				log.warn("***** OCURRIO UN ERROR AL CONSULTAR AL NSS EN BDTU ****", e);
			}
		}else{
			log.error("el nss no puede ser nulo � tener una longutid diferetne de 11");
			throw new IllegalArgumentException("el nss no puede ser nulo � tener una longutid diferetne de 11");
		}
		
		return null;
	}

	public AsignacionNSS obtenerAseguradoPorNss(String nss){
			return serviceEntity.obtenerAseguradoPorNss(nss);
	}
	
	/**
	 * Busca una porsona con nss SIN validar la fecha de baja como filtro en base de datos
	 * @param nss
	 * @return
	 */
	@Override
	public AsignacionNSS obtenerAseguradoPorNssConBajaLogica(String nss){
		return serviceEntity.obtenerAseguradoPorNssConBajaLogica(nss);
	}
	
    public AsignacionNSS obtenerAseguradoPorNss(String nss, String curp){
			return serviceEntity.obtenerAseguradoPorNss(nss, curp);
	}	
	
	public AsignacionNSS obtenerAseguradoPorIdAsignacion(Long idAsignacion){
		return serviceEntity.obtenerAseguradoPorIdAsignacion(idAsignacion);
	}
	
	public String obtenerEstadoPendienteConfirmar(String nss){
		return aseguradoEntity.obtenerEstadoPendienteConfirmar(nss);
	}
	
}
