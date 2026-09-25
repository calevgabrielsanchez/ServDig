package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;

import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.AsignacionNssDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.GrupoFamiliarDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.TramitePersonaFisicaDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.VigenciaDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.ManejadorReportesLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.PatronServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.TramiteDocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.util.DateUtils;
import mx.gob.imss.ctirss.delta.derechohabientes.util.DeltaUtils;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.SolicitudNssCorreoException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.dao.DocumentacionTramiteDAOLocal;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudTramiteBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.asegurado.SolicitudNssCorreo;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.ModServPresDerechohab;
import mx.gob.imss.ctirss.delta.model.derechohabiente.ServicioPrestDerechohab;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos.AseguradoDTO;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos.BeneficiarioDTO;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos.ComprobanteVigenciaDerechosDTO;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos.PatronDTO;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos.PrestacionesDTO;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos.ServiciosDTO;
import mx.gob.imss.ctirss.delta.model.derechohabientes.documentos.SolicitudRegistro;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.BeneficiarioSav002DTO;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.CartillaSaludDTO;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.DocumentacionCircunscripcionDTO;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.DocumentosBajaDTO;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.DocumentosCambioClinicaDTO;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.DocumentosProrrogaDTO;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.DocumentosRegistroDTO;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.RegistroDto;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.Reporte4305A;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.Sav001DTO;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.Sav002DTO;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.Sav005DTO;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.Sav006DTO;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.Sav007DTO;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.Sav010DTO;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.Sav017DTO;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteActualizacionCorreo;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.enums.CaracterEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.ServiciosPrestacionesEnum;
import mx.gob.imss.ctirss.delta.model.enums.SexoEnum;
import mx.gob.imss.ctirss.delta.model.enums.SubestadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCircunscripcionForanea;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteProrroga;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;
import mx.gob.imss.ctirss.delta.model.util.Constants;
import mx.gob.imss.ctirss.delta.persistence.DitActa;
import mx.gob.imss.ctirss.delta.persistence.DitActaTerminoUnionCivil;
import mx.gob.imss.ctirss.delta.persistence.DitActaUnionCivil;
import mx.gob.imss.ctirss.delta.persistence.DitCedulaProfesional;
import mx.gob.imss.ctirss.delta.persistence.DitCertificadoNacimiento;
import mx.gob.imss.ctirss.delta.persistence.DitCredElector;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.persistence.DitNacimiento;
import mx.gob.imss.ctirss.delta.persistence.DitPasaporte;
import mx.gob.imss.ctirss.delta.persistence.DitMatriculaConsular;
import mx.gob.imss.ctirss.delta.persistence.DitFormaMigratoria;
//import mx.gob.imss.distss.derechohabientes.adimss.schema.WSConsultaAdimssService;
//import mx.gob.imss.distss.derechohabientes.adimss.schema.WSConsultaAdimssService_Service;
import mx.gob.imss.distss.digital.jaxb.util.JaxbUtil;
import mx.gob.imss.vigenciaderechos.VigenciaDerechosWSClientRemote;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateless(name = "documentosService", mappedName = "documentosService")
public class DocumentosService extends AbstractServiceBusiness implements DocumentosServiceRemote,DocumentosServiceLocal {
	
	@EJB
	private TramitePersonaFisicaDaoLocal tramiteDaoLocal;
	@EJB
	private ManejadorReportesLocal manejadorReportes;
	@EJB
	private DocumentacionTramiteDAOLocal documentacionTramiteDao;
	@EJB
	private GrupoFamiliarDaoLocal grupoFamiliarDaoLocal;
	@EJB
	private ProrrogaDaoLocal prorrogaDao;
	@EJB
	private VigenciaDaoLocal vigenciaDao;
	@EJB
	private GrupoFamiliarServiceRemote grupoFamiliarService;
	@EJB
    private FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;
	@EJB
	private AsignacionNssDaoLocal asignacionNssDao;
	@EJB
	private VigenciaDerechosWSClientRemote vigenciaDerechosWS;
	@EJB(name = "tramiteService")
	private TramiteServiceLocal tramiteServiceLocal;
	@EJB(name = "solicitudBusiness", mappedName = "solicitudBusiness")
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@EJB(name = "solicitudTramiteBusiness", mappedName = "solicitudTramiteBusiness")
	private SolicitudTramiteBusinessRemote solicitudTramiteBusinessRemote;
	@EJB(name="domicilioServiceBusiness" ,mappedName="domicilioServiceBusiness" )
	private DomicilioServiceBusinessRemote domicilioServiceBusinessRemote;
	@EJB
	private PatronServiceRemote patronServices;
    @EJB(name = "personaBusiness", mappedName = "personaBusiness")
    private PersonaBusinessRemote personaBusinessRemote;
    @EJB
    private TramiteDocumentosServiceRemote tramiteDocumentosServiceRemote;
    
	
	private final String VALOR_DEFAULT = "-"; 
	private final int ASEGURADO_INCONSISTENTE = 3;
	private final int BENEFICIARIO_INCONSISTENTE = 4;
	private final int CALIDAD_ASEGURADO_PENSIONADO =1;
	
	private final String PATRON_BECARIO_IMSS_SIN_MODALIDAD = "Y549999537";
	private static final String RP17_CON_SERVICIO = "M6610218175";
	private static final String[] RPS_17 = new String[] {"A7711544174",RP17_CON_SERVICIO,"B3710738106"};
	private static final int DIAS_53 = 53;
	private static final int DIAS_56 = 56;

	private static final String DES_MODALIDAD_32_JCF = "PROGRAMA JOVENES CONSTRUYENDO EL FUTURO";
	private static final String DES_MODALIDAD_32_INST_EDUCATIVA = "SEGURO FACULTATIVO ESTUDIANTES";
	private static final String DES_MODALIDAD_32_CFE = "SEGURO FACULTATIVO IMSS / CFE";
	
	private Log log = LogFactory.getLog(DocumentosService.class);
	
	
	@Override
	public Object getAcuseRegistroMoviles(Solicitud solicitud) throws Exception {
		FirmaElectronica firma = null;
		TramiteRegistroDerechohabiente registro = null;
		GrupoFamiliar integrante = null;
		//verificamos si la solicitud no es nula
		if(solicitud != null) {
			//obtenemos los tados de la firma
			firma = this.getFirmaFromSolicitud(solicitud);
			//buscamos el tramite de registro de derechohabientes dentro de la solicitud
			for(Tramite tramite: solicitud.getTramites()) {
				if(tramite instanceof TramiteRegistroDerechohabiente) {
					registro = (TramiteRegistroDerechohabiente) tramite;
				}
			}
			//si no encontramos el tramite mandamos un error
			if(registro == null) {
				throw new Exception("La solicitud no cuenta con tramites de registro en clinica");
			}
			//creamos un objeto de tipo grupo familiar a partir del tramite de registro para no hacer consulta
			integrante = new GrupoFamiliar();
			integrante.setParentesco(registro.getParentesco());
			integrante.setDomicilio(this.complementarDomicilioParaAcuse(registro.getDomicilio()));
			integrante.setAsignacionNSS(registro.getDatosAsegurado());
			integrante.setDerechohabiente(this.convertirFisicaADerechohabiente(registro.getFisica()));
			integrante.setMedicoEnTurno(registro.getMedicoEnTurno());
			integrante.setFechaRegistroAlta(registro.getFechaTramite());
		} else {
			throw new Exception("La solicitud no puede ser nula");
		}
		//obtenemos el acuse de registro
		return getDocumentoAcuseDeRecibo(solicitud.getNoFolioSolicitud(), registro.getTipoTramite().getDescripcion(), firma, integrante);
	}
	
	/**
	 * Metodo para convertir una persona fisica en un derechohabiente
	 * @param fisica
	 * @return
	 */
	private Derechohabiente convertirFisicaADerechohabiente(Fisica fisica) {
		Derechohabiente derechohabiente = new Derechohabiente();
		derechohabiente.setNombre(fisica.getNombre());
		derechohabiente.setPrimerApellido(fisica.getPrimerApellido());
		derechohabiente.setSegundoApellido(fisica.getSegundoApellido());
		derechohabiente.setSexo(fisica.getSexo());
		derechohabiente.setCurp(fisica.getCurp());
		derechohabiente.setFechaNacimiento(fisica.getFechaNacimiento());
		derechohabiente.setCorreoElectronico(fisica.getCorreoElectronico());
		derechohabiente.setTelefonoFijo(fisica.getTelefonoFijo());
		derechohabiente.setTelefonoMovil(fisica.getTelefonoMovil());
		
		return derechohabiente;
	}

	@Override
	public Object getAcuseCambioClinicaMoviles(Solicitud solicitud) throws Exception {
		
		FirmaElectronica firma = null;
		TramiteCorreccionDerechohabiente cambioClinica = null;
		//Verificamos que la solicitud no venga nula
		if(solicitud != null) {
			//obtenemos los datos de la firma
			firma = this.getFirmaFromSolicitud(solicitud);
			//verificamos si existe el tramite de cambio de clinica
			for(Tramite tramite: solicitud.getTramites()) {
				if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.CAMBIO_CLINICA.getCodigo())) {
					cambioClinica = (TramiteCorreccionDerechohabiente) tramite;
				}
			}
			//si no existe un tramite de cambio de clinica mandamos un error indicandolo
			if(cambioClinica == null) {
				throw new Exception("La solicitud no cuenta con tramites de cambio de clinica");
			}
		} else {//si la solicitud viene nula mandamos un errror
			throw new Exception("La solicitud no puede ser nula");
		}
		//volteamos los domicilios del cml ya que cuando se finaliza la soliciud d cambio de clinica lo hace
		Domicilio domAnterior = cambioClinica.getDomicilio();
		Domicilio domActual = this.complementarDomicilioParaAcuse(cambioClinica.getDomicilioAnterior());
		
		cambioClinica.setDomicilioAnterior(domAnterior);
		cambioClinica.setDomicilio(domActual);
		
		return getDocumentoCambioClinica(solicitud.getNoFolioSolicitud(), cambioClinica.getTipoTramite().getDescripcion(), firma, cambioClinica);
	}
	
	private Domicilio complementarDomicilioParaAcuse(Domicilio domicilio) throws Exception{
		//converitmos a mayusculas los datos
		String calleActual =domicilio.getCalle(); 
		domicilio.setCalle( calleActual != null ? calleActual.toUpperCase() : "");
		String numExt = domicilio.getNumExteriorAlf();
		domicilio.setNumExteriorAlf(numExt != null ? numExt.toUpperCase() : "");
		String numInt = domicilio.getNumInteriorAlf();
		domicilio.setNumInteriorAlf(numInt != null ? numInt.toUpperCase() : "");
		Asentamiento asentamiento = domicilio.getAsentamiento();
		//si el nombre del asentemiento o del municipio o del estado no viene, consultamoes el asentamiento
		if(StringUtils.isBlank(asentamiento.getNombre())  || StringUtils.isBlank(asentamiento.getLocalidad().getMunicipio().getNombre()) || 
				StringUtils.isBlank(asentamiento.getLocalidad().getMunicipio().getEntidadFederativa().getNombre())) {
			domicilio.setAsentamiento(domicilioServiceBusinessRemote.getAsentamiento(domicilio.getAsentamiento()));
		}
		
		return domicilio;
	}
	
	/**
	 * metodo que obtiene la firma electronica a partir de una solicitud
	 * @param solicitud
	 * @return
	 */
	private FirmaElectronica getFirmaFromSolicitud(Solicitud solicitud) {
		
		FirmaElectronica firma = new FirmaElectronica();
		firma.setCadenaOriginal(solicitud.getCadenaOriginal());
		firma.setSecuenciaNotaria(solicitud.getSecuenciaDeNotaria());
		firma.setRecibo(solicitud.getSelloDigital());
		firma.setSerialCertificado(solicitud.getNumeroSerieCertificado());
		
		return firma;
	}

	@Override
	public Object getDocumentoCambioClinica(String folioSolicitud, String titulo, FirmaElectronica fe, TramiteCorreccionDerechohabiente tramite) throws DerechohabientesBusinessException {

		List<TramiteCorreccionDerechohabiente> list = new ArrayList<TramiteCorreccionDerechohabiente>();
		list.add(tramite);

		String plantilla = "acuseRecibCamClinica.jrxml";
		String subPlantilla = "personasCamClinica.jrxml";

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
		
		parametros.put("derechohabiente", ((Derechohabiente)tramite.getPersonas().get(0)));
		JasperReport jasperSubReport = null;
		try {
			jasperSubReport = JasperCompileManager.compileReport(new ClassPathResource("reportes/" + subPlantilla).getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DerechohabientesBusinessException(e.getMessage());
		}
		parametros.put("personasSubReporte", jasperSubReport);
		
		ByteArrayOutputStream repo = manejadorReportes.ejecutaReporte(parametros, list, plantilla);
		
		byte[] documento = repo.toByteArray();
		log.debug("documento:"+documento);
		if(documento != null && fe.getSecuenciaNotaria() != null){
			firmaDigitalBusinessRemote.guardarArchivoFirmado(fe.getSecuenciaNotaria(), "acuseCambioClinica.pdf", documento);
		}

		return documento;
	}
	
	@Override
	public Object getDocumentoAcuseDeRecibo(String folioSolicitud, String titulo, FirmaElectronica fe, GrupoFamiliar gf) throws DerechohabientesBusinessException {

		List<GrupoFamiliar> list = new ArrayList<GrupoFamiliar>();
		list.add(gf);

		String plantilla = "RegisAsegPensi.jrxml";

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
		
		ByteArrayOutputStream repo = manejadorReportes.ejecutaReporte(parametros, list, plantilla);
		
		byte[] documento = repo.toByteArray();
		log.debug("documento:"+documento);
		if(documento != null && fe.getSecuenciaNotaria() != null){
			firmaDigitalBusinessRemote.guardarArchivoFirmado(fe.getSecuenciaNotaria(), "acuse.pdf", documento);
		}

		return documento;
	}

	public Object getDocumentoSav001(AsignacionNSS nss)
			throws DerechohabientesBusinessException {

		List<Sav001DTO> sav01List = new ArrayList<Sav001DTO>();

		Sav001DTO sav01 = new Sav001DTO();
		sav01.setAgregadoIdentidad("01119635");
		sav01.setAutorizado("CPA");
		sav01.setConsultorio("22 V");
		sav01.setMesNacimiento("05");
		sav01.setNombreCompleto("SOSA CABALLERO JUAN CARLOS");
		sav01.setVencimiento("01-01-3000");

		sav01List.add(sav01);

		String plantilla = "SAV001.jrxml";

		Map<String, Object> parametros = new HashMap<String, Object>();

		parametros.put("curp", "SOCJ630505HHGSBN04");
		parametros.put("nss", "0100-63-0205-1");
		parametros.put("aPaterno", "SOSA");
		parametros.put("aMaterno", "CABALLERO");
		parametros.put("nombre", "JUAN CARLOS");
		parametros.put("modalidad", "32");

		parametros.put("titulo", "TARJETA DE ADSCRIPCION A CLINICA");
		ByteArrayOutputStream repo = manejadorReportes.ejecutaReporte(
				parametros, sav01List, plantilla);

		return repo.toByteArray();
	}

	@Override
	public Object getDocumentoSav002(AsignacionNSS nss, Long idSolicitud,
			String titulo) throws DerechohabientesBusinessException {

		Solicitud solicitud = null;
		try {
			solicitud = new Solicitud();
			solicitud.setSolicitudId(idSolicitud);
			solicitud = solicitudBusinessRemote.consultar(solicitud);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SolicitudRegistro solicitudRegistro = new SolicitudRegistro();
		solicitudRegistro.setDelegacion(solicitud.getCitaSolicitud().getUmf()
				.getSubdelegacion().getDelegacion().getDescripcion());
		solicitudRegistro.setFecha(DateUtils.dateFormat(solicitud
				.getFechaSolicitud()));
		if (solicitud.getFechaCita() != null)
			solicitudRegistro.setFechaCita(DateUtils.dateFormat(solicitud
					.getFechaCita()));
		solicitudRegistro.setFolio(solicitud.getNoFolioSolicitud());
		solicitudRegistro.setNombre(nss.getNombre() + " "
				+ nss.getPrimerApellido() + " " + nss.getSegundoApellido());
		solicitudRegistro.setNss("" + nss.getNssStr());
		solicitudRegistro.setSubdelegacion(solicitud.getCitaSolicitud()
				.getUmf().getSubdelegacion().getDescripcion());
		// UMF con 3 posiciones
		String umf = solicitud.getCitaSolicitud().getUmf().getNombreCorto();
		// Formatea a tres posiciones
		umf = stringTresPosiciones(umf, 3);

		solicitudRegistro.setUmf(umf);

		solicitudRegistro.setHora(solicitud.getCitaSolicitud().getTurno()
				.getHoraInicioTurno()
				+ " - "
				+ solicitud.getCitaSolicitud().getTurno().getHoraFinTurno());

		// consulta de documentos probatorios
		List<DitDocumentoProbatorio> documentos = null;
		try {
			documentos = documentacionTramiteDao
					.findDitDocumentosProbatorios(solicitud.getTramites()
							.get(0).getTramiteId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String listaDocumentos = "";

		for (DitDocumentoProbatorio documentoProbatorio : documentos) {

			// Cartilla militar
			if (documentoProbatorio.getDitDocumentoPorTipo().getDicDocumento()
					.getCveIdDocumento() == 37) {
				listaDocumentos += documentoProbatorio.getDitDocumentoPorTipo()
						.getDicDocumento().getDesDocumento().toUpperCase()
						+ " "
						+ documentoProbatorio.getDitCartillaMilitar()
								.getNumMatricula()
						+ " "
						+ documentoProbatorio.getFecExpedicion() + "<br>";
			}
			// Acta de reconocimiento
			else if (documentoProbatorio.getDitDocumentoPorTipo()
					.getDicDocumento().getCveIdDocumento() == 3) {
				listaDocumentos += documentoProbatorio.getDitDocumentoPorTipo()
						.getDicDocumento().getDesDocumento().toUpperCase()
						+ " "
						+ documentoProbatorio.getDitActa().getNumActa()
						+ " "
						+ documentoProbatorio.getDitActa().getFecSuceso()
						+ "<br>";
			} else {
				listaDocumentos += documentoProbatorio.getDitDocumentoPorTipo()
						.getDicDocumento().getDesDocumento().toUpperCase()
						+ "<br>";
			}

		}

		solicitudRegistro.setDocumentos(listaDocumentos);



		List<SolicitudRegistro> datosReporte = new ArrayList<SolicitudRegistro>();
		datosReporte.add(solicitudRegistro);

		String plantilla = "Solicitud.jasper";
		Map<String, String> plantillas = new HashMap<String, String>();
		Map<String, Object> parametros = new HashMap<String, Object>();

		Locale locale = new Locale("es", "ES");
		parametros.put(JRParameter.REPORT_LOCALE, locale);

		if(solicitud.getOrigenSolicitud().getIdTipoSolicitud().equals(OrigenSolicitudEnum.INTERNET.getId())) {
			parametros.put("nombreEmpleado", "TR\u00C1MITE NO PRESENCIAL CONCLUIDO CON FIEL SAT.");
		} else if (solicitud.getOrigenSolicitud().getIdTipoSolicitud().equals(OrigenSolicitudEnum.PORTAL_CIUDADANO.getId())) {
			parametros.put("nombreEmpleado", "TR\u00C1MITE CONCLUIDO FIRMADO POR EL IMSS.");
		} else {
			parametros.put("nombreEmpleado", "");
		}

		parametros.put("titulo", titulo);
		ByteArrayOutputStream repo = manejadorReportes
				.ejecutaReporteSubreporte(parametros, datosReporte, plantilla,
						plantillas);

		return repo.toByteArray();

	}

    @Override
    public Object generaSav002v2(AsignacionNSS nss,
                               FirmaElectronica firmaElectronica, Usuario usuario, Long idTramite, Long idOrigenSolicitud, Boolean registro)
            throws DerechohabientesBusinessException, Exception {
        Sav002DTO sav = null;
        List<Sav002DTO> datosReporte = null;
        String clave = "";

        CabezaGrupoFamiliar patron = grupoFamiliarDaoLocal.getCabezaGrupoFamiliar(nss.getIdAsignacionNSS());

        log.debug("patron: "+patron);

        try{
            clave = patron.getPatronSujetoObligado().getModalidad().getNumModalidad();
        }catch(NullPointerException e){
            // -------------------------------------------------------------
            // Los pensionados pueden no tener un patron
            // -------------------------------------------------------------
            log.debug("Patron Sujeto Obligado Null, idAsignacion:"+nss.getIdAsignacionNSS());
        }

        // De acuerdo al parametro idTramite es la forma de generar el reporte.
        if (idTramite != null) {
            sav = getDatosSav002(nss, idTramite,registro, patron);

            if(sav == null) {
                return null;
            }
            datosReporte = new ArrayList<Sav002DTO>();
            datosReporte.add(sav);
        } else {
            sav = getDatosSav002Reimpresion(nss, usuario, idTramite);
            sav.setModalidad(clave);
            // sav.setModalidad("" +
            // patron.getPatronSujetoObligado().getModalidad().getNumModalidad());
            datosReporte = new ArrayList<Sav002DTO>();
            datosReporte.add(sav);
        }

        if (firmaElectronica != null) {
            sav.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
            sav.setSelloDigital(firmaElectronica.getRecibo());
            sav.setSecuenciaNotarial(firmaElectronica.getSecuenciaNotaria());
            sav.setNumeroSerie(firmaElectronica.getSerialCertificado());
        }

        String plantilla = "SAV002v2.jasper";

        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("titulo", "SAV002");
        parametros.put("LOGO", new ClassPathResource("reportes/img/headerPicture.png").getPath());
		parametros.put("PIE", new ClassPathResource("reportes/img/footer.png").getPath());
        Locale locale = new Locale("es", "ES");
        parametros.put(JRParameter.REPORT_LOCALE, locale);

        parametros.put("lugar","");
        if( sav.getDomicilio() != null) {
            parametros.put("lugar", sav.getDomicilio());
        }


        parametros.put("fecha",DateUtils.dateFormatCustom(new Date(), "dd MMMMM yyyy").toUpperCase());
        parametros.put("clave", registro ? "01" : "02");

        String nombreEmpleado = "";
        if(idOrigenSolicitud.equals(OrigenSolicitudEnum.INTERNET.getId())) {
            nombreEmpleado = "TR\u00C1MITE NO PRESENCIAL CONCLUIDO CON FIEL SAT.";
        } else if (idOrigenSolicitud.equals(OrigenSolicitudEnum.PORTAL_CIUDADANO.getId()) || idOrigenSolicitud.equals(OrigenSolicitudEnum.MOVILES.getId())) {
            nombreEmpleado = "TR\u00C1MITE CONCLUIDO FIRMADO POR EL IMSS.";
        } else {
            nombreEmpleado = usuario != null ? usuario.getUsuario() : "";
        }

        parametros.put("nombreEmpleado", nombreEmpleado);
        ByteArrayOutputStream repo = manejadorReportes.ejecutaReporteCompilado(
                parametros, datosReporte, plantilla);

        return repo.toByteArray();
    }

    @Override
    public Object generaSav002(AsignacionNSS nss,
                               FirmaElectronica firmaElectronica, Usuario usuario, Long idTramite, Long idOrigenSolicitud, Boolean registro)
            throws DerechohabientesBusinessException, Exception {
    	

		//if(idOrigenSolicitud.equals(OrigenSolicitudEnum.PORTAL_CIUDADANO.getId()) ){
			//Si el origen es portal ciudadano se solicita el nuevo formato de sav002
    	try{
			return generaSav002v2(nss,firmaElectronica,usuario,idTramite,idOrigenSolicitud,registro);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}

    }

	private Sav002DTO getDatosSav002Reimpresion(AsignacionNSS nss,
			Usuario usuario, Long idTramite)
			throws DerechohabientesBusinessException, Exception {
		Sav002DTO sav = null;
		String agregadoIdentidad = "";
		List<GrupoFamiliar> grupoFamiliarList = new ArrayList<GrupoFamiliar>();

		List<Long> estados = new ArrayList<Long>();
		estados.add(EstadoDerechohabienteEnum.VIGENTE.getId());
		estados.add(EstadoDerechohabienteEnum.CONSERVACION_DERECHOS.getId());
		estados.add(EstadoDerechohabienteEnum.PENSION_TRAMITE.getId());
		estados.add(EstadoDerechohabienteEnum.CON_DERECHO.getId());
		estados.add(EstadoDerechohabienteEnum.FALLECIDO.getId());
		
		grupoFamiliarList = grupoFamiliarDaoLocal.findGrupoFamiliarByEstado(nss.getIdAsignacionNSS(), estados);

		Calendar fecha = new GregorianCalendar();
		String dia = "" + fecha.get(Calendar.DATE);
		String mes = "" + (fecha.get(Calendar.MONTH) + 1);
		// String anio = "" + fecha.get(Calendar.YEAR);

		if (grupoFamiliarList != null && grupoFamiliarList.size() > 0) {
			GrupoFamiliar gf = this.getIntegranteCabeza(grupoFamiliarList);
			
			
				sav = new Sav002DTO();
				
				
				// -----------------------------------------------------------------
				// Se calculan los datos para el derechohabiente en caso de que 
				// no existan candidatos en el tramite
				// -----------------------------------------------------------------
				
					// Calcular el Agregado de Identidad
					// int anioNac = new
					// Integer(DateUtils.dateFormatCustom(gf.getDerechohabiente().getFechaNacimiento(),
					// "yy"));
					agregadoIdentidad = DeltaUtils.getAgregadoIdentidad(gf
							.getCalidad().intValue(), gf.getDerechohabiente()
							.getSexo().getIdSexo(), gf.getDerechohabiente()
							.getFechaNacimiento(), gf.getDerechohabiente().getAnioRegistroNac());
					// stringTresPosiciones(""+gf.getCalidad(), 2)+
					// gf.getDerechohabiente().getSexo().getIdSexo()+
					// anioNac+
					// DeltaUtils.getDigito(gf.getDerechohabiente().getSexo().getIdSexo(),
					// gf.getCalidad().intValue(), DeltaUtils.getAnio(anioNac));
					sav.setAgregadoMedico(agregadoIdentidad);
					
					if( gf.getDerechohabiente().getFechaNacimiento() != null ){
						sav.setMesNacimiento(DateUtils.dateFormatCustom(gf.getDerechohabiente().getFechaNacimiento(), "MM"));
						sav.setEdad(""+ DateUtils.getEdad(gf.getDerechohabiente().getFechaNacimiento()));
						
					}else{
						sav.setMesNacimiento("");
						sav.setEdad("");
					}
	
					
					sav.setNombre(gf.getDerechohabiente().getNombre() + " "
							+ gf.getDerechohabiente().getPrimerApellido() + " "
							+ getValue(gf.getDerechohabiente().getSegundoApellido()));
	
					
					
				
				if( gf.getDomicilio() != null  ){
					sav.setCalleNumero(gf.getDomicilio().getNumExteriorAlf());
					sav.setColonia(gf.getDomicilio().getAsentamiento().getNombre());
					sav.setMunicipio(gf.getDomicilio().getAsentamiento().getLocalidad().getMunicipio().getNombre());
					sav.setDomicilio(gf.getDomicilio().getAsentamiento().getLocalidad().getMunicipio().getNombre()
							+ " "+ gf.getDomicilio().getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre());
				}else{
					sav.setCalleNumero("");
					sav.setColonia("");
					sav.setMunicipio("");
					sav.setDomicilio("");
				}
				
				// Calculando UMF a 3 posiciones
				String umf = gf.getMedicoEnTurno().getUnidadMedicaFamiliar()
						.getNombreCorto();

				// Formatea a tres posciones
				umf = stringTresPosiciones(umf, 3);

				sav.setClinica(umf);

				sav.setConsultorio(gf.getMedicoEnTurno().getConsultorio().getDescripcion());
				sav.setCurp(getValue(nss.getCurp()));

				sav.setDia(dia);
				if (gf.getDerechohabiente().getLugarNacimiento() != null)
					sav.setEntidadFederativaNacimiento(""+ gf.getDerechohabiente().getLugarNacimiento().getNombre());

				sav.setMes(mes);
				sav.setNombreAsegurado(nss.getNombre() + " "
						+ nss.getPrimerApellido() + " "
						+ getValue(nss.getSegundoApellido()));

				sav.setNss(nss.getNssStr());
				sav.setDocumentos("");

				

				// lugar = gf.getDomicilio().getAsentamiento().getLocalidad()
				// .getMunicipio().getNombre()
				// + ", "
				// + gf.getDomicilio().getAsentamiento().getLocalidad()
				// .getMunicipio().getEntidadFederativa()
				// .getNombre();
			
				for(GrupoFamiliar integrante: grupoFamiliarList) {
					sav.addBeneficiario( obtenerBeneficiarioSav002(integrante,true));
				}
		}

		return sav;
	}
	
	private GrupoFamiliar getIntegranteCabeza(List<GrupoFamiliar> grupo) {
		GrupoFamiliar cab = null;
		
		for(GrupoFamiliar integrante: grupo) {
			if(integrante.getParentesco().getIdParentesco().equals(ParentescoEnum.ASEGURADO.getId())
					|| integrante.getParentesco().getIdParentesco().equals(ParentescoEnum.PENSIONADO.getId())) {
				cab = integrante;
				break;
			}
		}
		
		return cab;
	}

	/**
	 * 
	 */
	public Object getDocumentoSav005(AsignacionNSS nss,
			FirmaElectronica firmaElectronica, Long idPersona,
			Long idEstadoTramite, Long idOrigenSolicitud, Usuario usuario) throws DerechohabientesBusinessException,
			Exception {
		Map<String, Object> parametros = new HashMap<String, Object>();
		List<Sav005DTO> sav05List = getDatosSav005(nss, firmaElectronica,
				idPersona, idEstadoTramite, idOrigenSolicitud,usuario);

		
		String plantilla = "SAV005.jasper";

		parametros.put("logo",
				new ClassPathResource("reportes/img/imss.jpg").getPath());
		parametros.put("MOVIMIENTO_PARCIAL", Boolean.TRUE);
		Locale locale = new Locale("es", "ES");
		parametros.put(JRParameter.REPORT_LOCALE, locale);
		ByteArrayOutputStream repo = manejadorReportes.ejecutaReporteCompilado(
				parametros, sav05List, plantilla);
		log.debug("se genera reporte sav005 con el idPersona: " + idPersona+ " y nss " + nss.getNss());
		return repo.toByteArray();
	}

	@Override
	public Object getDocumentoSav006(AsignacionNSS nss,
			FirmaElectronica firmaElectronica, Usuario usuario, Long id,
			Integer identificadorReporte) throws DerechohabientesBusinessException,
			Exception {
		
		List<Sav006DTO> reporte = new ArrayList<Sav006DTO>();
		Sav006DTO sav006 = null;
		
		mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum tipoTramiteEnum = mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum.obternerEnumById(identificadorReporte);
		
		switch (tipoTramiteEnum){
		case CAMBIO_CLINICA://idTramite
			sav006 = this.getDocumentoSav006PorCambioClinica(nss, usuario, id);
			break;
		case AUTORIZACION_SERVICIOS_CIRCUNSCRIPCION_FORANEA://idTramite
			sav006 = this.getDocumentoSav006PorCircunscripcionAutorizacion(nss, usuario, id);
			break;
		case SUSPENSION_SERVICIOS_CIRCUNSCRIPCION_FORANEA://idPersona
			sav006 = this.getDocumentoSav006PorCircunscripcionSuspencion(nss, usuario, id);
			break;
		default:
			break;
		}
		
		// Agregar datos de la firma electronica
		if (firmaElectronica != null) {
			sav006.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
			sav006.setSelloDigital(firmaElectronica.getRecibo());
			sav006.setSecuenciaNotarial(firmaElectronica
								.getSecuenciaNotaria());
			sav006.setNumeroSerie(firmaElectronica.getSerialCertificado());
		}
		
		reporte.add(sav006);
		Map<String, Object> parametros = new HashMap<String, Object>();
		String plantilla = "SAV006.jasper";
		parametros.put("logo",
				new ClassPathResource("reportes/img/imss.jpg").getPath());
		
		ByteArrayOutputStream repo = manejadorReportes.ejecutaReporteCompilado(
				parametros, reporte, plantilla);

		return repo.toByteArray();
	}
	
	private Sav006DTO getDocumentoSav006PorCambioClinica(AsignacionNSS nss, Usuario usuario, Long idTramite) throws DerechohabientesBusinessException, Exception{
		
		Sav006DTO sav006 = new Sav006DTO();
		Sav002DTO sav = getDatosSav002(nss, idTramite,true, null);
		
		sav006.setAsegurado(sav.getNombreAsegurado());
		sav006.setBeneficiario(sav.getNombre());
		sav006.setNss(nss.getNssStr());
		sav006.setCurp(nss.getCurp());
		sav006.setEmpleado(usuario.getUsuario());
		sav006.setCurpBeneficiario(sav.getCurp());
		sav006.setcSolicitante(sav.getClinica());
		sav006.setUmf(sav.getUmf());
		sav006.setCambioClinica("X");
		sav006.setLugar(sav.getDomicilio());
		sav006.setFecha(DateUtils.dateFormat(new Date()));
		sav006.setNuevoDomicilio(sav.getDomicilio());

		return sav006;
	}
	
	private Sav006DTO getDocumentoSav006PorCircunscripcionAutorizacion(AsignacionNSS nss, Usuario usuario, Long idTramite)
			throws DerechohabientesBusinessException, Exception{
		
		Sav006DTO sav006 = new Sav006DTO();
		Sav002DTO sav = getDatosSav002(nss, idTramite,true, null);
		

		GrupoFamiliar integrante = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(
				nss.getIdAsignacionNSS(), sav.getIdPersona());
		GrupoFamiliar asegurado = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(
				nss.getIdAsignacionNSS(), nss.getIdPersona());
		
		sav006.setAsegurado(asegurado.getDerechohabiente().getNombre()
				.toUpperCase()
				+ " "
				+ asegurado.getDerechohabiente().getPrimerApellido()
						.toUpperCase()
				+ " "
				+ (asegurado.getDerechohabiente().getSegundoApellido() == null ? ""
						: asegurado.getDerechohabiente().getSegundoApellido()
								.toUpperCase()));
		sav006.setCurp(asegurado.getDerechohabiente().getCurp());
		sav006.setNss(nss.getNssStr());
		sav006.setBeneficiario(integrante.getDerechohabiente().getNombre()
				.toUpperCase()
				+ " "
				+ integrante.getDerechohabiente().getPrimerApellido()
						.toUpperCase()
				+ " "
				+ (integrante.getDerechohabiente().getSegundoApellido() == null ? ""
						: integrante.getDerechohabiente().getSegundoApellido()
								.toUpperCase()));
		sav006.setCurpBeneficiario(integrante.getDerechohabiente().getCurp());
		sav006.setcSolicitante(asegurado.getMedicoEnTurno()
				.getUnidadMedicaFamiliar().getNombreCorto());
		sav006.setUmf(integrante.getMedicoEnTurno().getUnidadMedicaFamiliar()
				.getNombreCorto());
		sav006.setCambioClinica("X");

		sav006.setNuevoDomicilio("CALLE " + sav.getCalleNumero() + ", COLONIA "
				+ sav.getColonia() + ", " + sav.getDomicilio());
		sav006.setVigencia(integrante.getFechaFinVigencia() == null ? ""
				: DateUtils.dateFormatCustom(integrante.getFechaFinVigencia(),
						"dd/MM/yyyy"));
		
		return sav006;
	}

	private Sav006DTO getDocumentoSav006PorCircunscripcionSuspencion(AsignacionNSS nss, Usuario usuario, Long idPersona) 
			throws DerechohabientesBusinessException, Exception{
		
		Sav006DTO sav006 = new Sav006DTO();
		
		GrupoFamiliar integrante = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(
				nss.getIdAsignacionNSS(), idPersona);
		GrupoFamiliar asegurado = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(
				nss.getIdAsignacionNSS(), nss.getIdPersona());
		
		sav006.setAsegurado(asegurado.getDerechohabiente().getNombre()
				+ " "
				+ asegurado.getDerechohabiente().getPrimerApellido()
				+ " "
				+ (asegurado.getDerechohabiente().getSegundoApellido() != null ? asegurado
						.getDerechohabiente().getSegundoApellido() : ""));
		sav006.setBeneficiario(integrante.getDerechohabiente().getNombre()
				+ " "
				+ integrante.getDerechohabiente().getPrimerApellido()
				+ " "
				+ (integrante.getDerechohabiente().getSegundoApellido() != null ? integrante
						.getDerechohabiente().getSegundoApellido() : ""));
		sav006.setNss(nss.getNssStr());
		sav006.setCurpBeneficiario(integrante.getDerechohabiente().getCurp());
		sav006.setcSolicitante(asegurado.getMedicoEnTurno()
				.getUnidadMedicaFamiliar().getNombreCorto());
		sav006.setUmf(integrante.getMedicoEnTurno().getUnidadMedicaFamiliar()
				.getNombreCorto());
		sav006.setCambioClinica("X");
		
		String[] nombres = usuario.getFisica().getNombre().split(" ");
		String nombre = "";
		for (String s : nombres) {
			nombre += s.substring(0, 1).toUpperCase();
		}
		
		String tramitador = (usuario.getFisica().getPrimerApellido() != null ? usuario
				.getFisica().getPrimerApellido().substring(0, 1).toUpperCase()
				: "")
				+ (usuario.getFisica().getSegundoApellido() != null ? usuario
						.getFisica().getSegundoApellido().substring(0, 1)
						.toUpperCase() : "") + nombre;
		sav006.setEmpleado(tramitador);
		sav006.setCurp(nss.getCurp());
		
		return sav006;
	}
	
	@Override
	public Object getDocumentoSav007(AsignacionNSS nss,
			FirmaElectronica firmaElectronica, Long idPersona)
			throws DerechohabientesBusinessException, Exception {

		List<Long> tiposTramite = new ArrayList<Long>();
		tiposTramite.add(TipoTramiteEnum.PRORROGA_ESTUDIOS.getCodigo()
				.longValue());
		tiposTramite.add(TipoTramiteEnum.PRORROGA_ENFERMEDAD.getCodigo()
				.longValue());
		tiposTramite.add(TipoTramiteEnum.PRORROGA_OBSTETRICOS.getCodigo()
				.longValue());
		tiposTramite.add(TipoTramiteEnum.PRORROGA_VIGENCIA_PERMANENTE
				.getCodigo().longValue());
		tiposTramite.add(TipoTramiteEnum.PRORROGA_VIGENCIA_TEMPORAL.getCodigo()
				.longValue());
		tiposTramite.add(TipoTramiteEnum.PRORROGA_ACUERDOS.getCodigo()
				.longValue());
		tiposTramite.add(TipoTramiteEnum.PRORROGA_LAUDOS.getCodigo()
				.longValue());
		Sav007DTO sav007 = tramiteDaoLocal.getUltimoTramiteSAV007(nss, idPersona,
				tiposTramite);

		GrupoFamiliar integrante = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(
				nss.getIdAsignacionNSS(), idPersona);

		if (sav007 != null) {
			List<Sav007DTO> datos = new ArrayList<Sav007DTO>();
			if (integrante != null && integrante.getDomicilio() != null ) {
				String lugar = integrante.getDomicilio().getAsentamiento()
						.getLocalidad().getMunicipio().getNombre()
						+ ", "
						+ integrante.getDomicilio().getAsentamiento()
								.getLocalidad().getMunicipio()
								.getEntidadFederativa().getNombre();
				sav007.setLugar(lugar);
			} else {
				sav007.setLugar("");
			}

			// Agregar datos de la firma electronica
			if (firmaElectronica != null) {
				sav007.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
				sav007.setSelloDigital(firmaElectronica.getRecibo());
				sav007.setSecuenciaNotarial(firmaElectronica
						.getSecuenciaNotaria());
				sav007.setNumeroSerie(firmaElectronica.getSerialCertificado());
			}

			datos.add(sav007);

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("logo", new ClassPathResource(
					"reportes/img/imss.jpg").getPath());

			Locale locale = new Locale("es", "ES");
			parametros.put(JRParameter.REPORT_LOCALE, locale);

			String plantilla = "SAV007.jasper";

			ByteArrayOutputStream repo = manejadorReportes
					.ejecutaReporteCompilado(parametros, datos, plantilla);

			return repo.toByteArray();

		}

		return null;

	}

	@Override
	public Object getDocumentoSav017(Long idPersona, AsignacionNSS nss,
			FirmaElectronica firmaElectronica, Boolean autorizacion,
			TramiteCircunscripcionForanea circunscripcion, Long idOrigenSolicitud)
			throws DerechohabientesBusinessException {

		List<Sav017DTO> datos = new ArrayList<Sav017DTO>();
		// Sav017DTO sav017 = new Sav017DTO();
		byte[] res = null;
		Map<String, Object> parametros = new HashMap<String, Object>();

		datos = getDatosSav017(circunscripcion, idPersona, nss,
				firmaElectronica, autorizacion, parametros,idOrigenSolicitud);
		// datos.add(sav017);

		Locale locale = new Locale("es", "ES");
		parametros.put(JRParameter.REPORT_LOCALE, locale);

		String plantilla = "SAV017.jasper";

		try {
			ByteArrayOutputStream documento = manejadorReportes
					.ejecutaReporteCompilado(parametros, datos, plantilla);

			res = documento.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;

	}

	@Override
	public Object getDocumentoSav010(Long idTramite)
			throws DerechohabientesBusinessException {

		List<Sav010DTO> datos = new ArrayList<Sav010DTO>();
		Sav010DTO sav010 = new Sav010DTO();
		sav010.setClinica("1");
		sav010.setCurp("GOZS750829MHGNRL03");
		sav010.setFecha("");
		sav010.setFechaBaja(new Date());
		sav010.setLugar("PACHUCA, HIDALGO A");
		sav010.setNombreDerechohabiente("SANTIAGO GONZALEZ LAURA");
		sav010.setCurpDerechohabiente("SAGL011103MHGNNRA2");
		sav010.setNss("0100-75-0196-6");
		sav010.setPatron("B702443010");
		sav010.setUmf("HGZMF 01 PACHUCA");
		sav010.setEmpleado("CPA");

		datos.add(sav010);

		String plantilla = "SAV010.jasper";

		Map<String, Object> parametros = new HashMap<String, Object>();
		Locale locale = new Locale("es", "ES");
		parametros.put(JRParameter.REPORT_LOCALE, locale);
		ByteArrayOutputStream repo = manejadorReportes.ejecutaReporteCompilado(
				parametros, datos, plantilla);

		return repo.toByteArray();

	}

	/**
	 * Genera la Cartilla Nacional Salud
	 */
	public Object getCartillaNacionalSalud(Long idDerechohabiente,
			AsignacionNSS nss, FirmaElectronica firmaElectronica)
			throws DerechohabientesBusinessException, Exception {

		Map<String, Object> parametros = new HashMap<String, Object>();
		List<CartillaSaludDTO> datosReporte = getDatosCartillaSalud(
				idDerechohabiente, nss, firmaElectronica);
		Locale locale = new Locale("es", "ES");
		String plantilla = "cartillaSalud.jasper";

		parametros.put("titulo", "Cartilla Nacional de Salud");
		parametros.put("IMG_PREVENIMSS", new ClassPathResource("reportes/img/prevenIMSS.jpg").getPath());
		parametros.put(JRParameter.REPORT_LOCALE, locale);

		// ----------------------------------------------------------
		// Si no hay datos crea un reporte de una hoja en blanco
		// ----------------------------------------------------------
		if( datosReporte != null ){
			ByteArrayOutputStream repo = manejadorReportes.ejecutaReporteCompilado(parametros, datosReporte, plantilla);
			return repo.toByteArray();
		}
		
		return null;
	}

	/**
	 * Genera las Cartillas Nacional de Salud de un asegurado o pensionado en un solo pdf para Internet
	 */
	public Object getCartillasNacionalSalud(List<GrupoFamiliar> integrantes,
			AsignacionNSS nss, FirmaElectronica firmaElectronica)
			throws DerechohabientesBusinessException, Exception {
		List<JasperPrint> jasperPrints = new ArrayList<JasperPrint>();
		for (GrupoFamiliar grupoFamiliar : integrantes) {

			Map<String, Object> parametros = new HashMap<String, Object>();
			List<CartillaSaludDTO> datosReporte = getDatosCartillaSalud(
				grupoFamiliar.getDerechohabiente().getIdPersona(), nss, firmaElectronica);
			Locale locale = new Locale("es", "ES");
			String plantilla = "cartillaSalud.jasper";

			parametros.put("titulo", "Cartilla Nacional de Salud");
			parametros.put("IMG_PREVENIMSS", new ClassPathResource("reportes/img/prevenIMSS.jpg").getPath());
			parametros.put(JRParameter.REPORT_LOCALE, locale);

			// ----------------------------------------------------------
			// Si no hay datos crea un reporte de una hoja en blanco
			// ----------------------------------------------------------
			
			if( datosReporte != null ){
				JasperPrint jp = manejadorReportes.getReporteCompilado(parametros, datosReporte, plantilla);
				jasperPrints.add(jp);
			}
		}
		ByteArrayOutputStream repo = manejadorReportes.mergeReporteCompilado(jasperPrints);
		return repo.toByteArray();
//		return null;
	}


	// Mrtodo que devuelve datos para llenar cartilla de salud y citas medicas

	private List<CartillaSaludDTO> getDatosCartillaSalud(
			Long idDerechohabiente, AsignacionNSS nss,
			FirmaElectronica firmaElectronica)
			throws DerechohabientesBusinessException, Exception {
		List<CartillaSaludDTO> datos = new ArrayList<CartillaSaludDTO>();

		Domicilio domicilio = null;
		// Derechohabiente derechohabiente = null;
		// Calendar fecha = new GregorianCalendar();
		CartillaSaludDTO cartillaSalud = new CartillaSaludDTO();
		GrupoFamiliar integrante = null;
		// String dia="0";
		// String mes="0";
		String consultorio = "";
		String umf = "";

		
		
		if( !nss.isEstudiante() )
			integrante = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(nss.getIdAsignacionNSS(), idDerechohabiente);
		else
			integrante = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliarEstudiante(nss.getIdAsignacionNSS(), idDerechohabiente);
		
		
		
		
		// derechohabiente = integrante.getDerechohabiente();
		if (integrante.getEstadoDerechohabiente().getIdEstadoDerechohabiente()
				.longValue() != EstadoDerechohabienteEnum.BAJA.getId()) {
			domicilio = integrante.getDomicilio();
			umf = stringTresPosiciones(integrante.getMedicoEnTurno().getUnidadMedicaFamiliar().getNombreCorto(), 3);
			
			cartillaSalud.setUmf(umf);
			
			// Formatea a tres posciones
			consultorio = stringTresPosiciones(integrante.getMedicoEnTurno().getConsultorio().getIdConsultorio()+"", 2);
			cartillaSalud.setConsultorio(consultorio);

			cartillaSalud.setNombre(integrante.getDerechohabiente().getNombre());
			cartillaSalud.setCurp( getValue(integrante.getDerechohabiente().getCurp()));
			cartillaSalud.setNss(integrante.getAsignacionNSS().getNssStr());

			cartillaSalud.setApellidos(integrante.getDerechohabiente().getPrimerApellido()+ " " 
							+ getValue(integrante.getDerechohabiente().getSegundoApellido()) );
						
			

			// cartillaSalud.setHora(derechohabiente.getMedicoEnTurno().getTurno().getHoraInicioTurno()+" - "+
			// derechohabiente.getMedicoEnTurno().getTurno().getHoraFinTurno());
			cartillaSalud.setHora(""+ integrante.getMedicoEnTurno().getTurno().getDescripcion().toUpperCase().charAt(0));

			
			if( domicilio != null ){
				String prefijo = "";
				/*
				if(domicilio.getVialidadPrimaria().getTipoVialidad() != null && 
						StringUtils.isNotBlank(domicilio.getVialidadPrimaria().getTipoVialidad().getDescripcion())) {
					prefijo = domicilio.getVialidadPrimaria().getTipoVialidad().getDescripcion();
				}*/
				
				cartillaSalud.setCalleNumero(prefijo
					+ (domicilio.getVialidadPrimaria() != null ? getValue(domicilio.getVialidadPrimaria().getNombre()) : "")
					+ (domicilio.getNumExterior1() != null ? ", " + domicilio.getNumExterior1() : "")
					+ (domicilio.getNumExteriorAlf() != null ? ", " + domicilio.getNumExteriorAlf().toUpperCase() : "")
					+ (domicilio.getNumInterior() != null ? ", "+ domicilio.getNumInterior() : "")
					+ (domicilio.getNumInteriorAlf() != null ? ", " + domicilio.getNumInteriorAlf().toUpperCase() : ""));
	
				if( domicilio.getAsentamiento() != null ){
					cartillaSalud.setColonia("COLONIA "
						+ domicilio.getAsentamiento().getNombre() + ", "
						+ domicilio.getAsentamiento().getLocalidad().getMunicipio().getNombre() + ", "
						+ domicilio.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre() + ", "
					);
					
					cartillaSalud.setEntidadFederativa(domicilio.getAsentamiento()
							.getLocalidad().getMunicipio().getEntidadFederativa()
							.getNombre());
	
				}
				
				if( domicilio.getCodigoPostal() != null )
					domicilio.setColonia(domicilio.getColonia() + "C.P. " + domicilio.getCodigoPostal().getCodigoPostal());
			
			}else{
				cartillaSalud.setCalleNumero("");
				cartillaSalud.setColonia("");
				cartillaSalud.setEntidadFederativa("");
			}
				
			cartillaSalud.setMunicipio("");

			
			cartillaSalud.setEntidadFederativaNacimiento(
				integrante.getDerechohabiente().getLugarNacimiento() != null ? integrante.getDerechohabiente().getLugarNacimiento().getNombre() : "");

			
			if( integrante.getDerechohabiente().getFechaNacimiento() != null  ){
				cartillaSalud.setEdad(stringTresPosiciones(""+ DateUtils.getEdad(integrante.getDerechohabiente().getFechaNacimiento()), 3));
				
				/*
				 * fecha.setTime(derechohabiente.getFechaNacimiento());
				 * if(fecha.get(Calendar.DATE)<9){ dia+=fecha.get(Calendar.DATE);
				 * }else{ dia=""+fecha.get(Calendar.DATE); }
				 * 
				 * cartillaSalud.setDia(dia);
				 * 
				 * if((fecha.get(Calendar.MONTH) + 1)<9){
				 * mes+=(fecha.get(Calendar.MONTH) + 1); }else{
				 * mes=""+(fecha.get(Calendar.MONTH) + 1); }
				 * 
				 * cartillaSalud.setMes(mes);
				 * 
				 * cartillaSalud.setAnio("" + fecha.get(Calendar.YEAR));
				 */

				cartillaSalud.setDia(DateUtils.dateFormatCustom(integrante
						.getDerechohabiente().getFechaNacimiento(), "dd"));
				cartillaSalud.setMes(DateUtils.dateFormatCustom(integrante
						.getDerechohabiente().getFechaNacimiento(), "MM"));
				cartillaSalud.setAnio(DateUtils.dateFormatCustom(integrante
						.getDerechohabiente().getFechaNacimiento(), "yyyy"));

			}else{
				cartillaSalud.setEdad("");
				cartillaSalud.setDia("");
				cartillaSalud.setMes("");
				cartillaSalud.setAnio("");
			
			}
		
			cartillaSalud.setAgregadoMedico(integrante.getAgregadoMedico());

			// Agregar datos de la firma electronica
			cartillaSalud.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
			cartillaSalud.setSelloDigital(firmaElectronica.getRecibo());
			cartillaSalud.setSecuenciaNotarial(firmaElectronica.getSecuenciaNotaria());
			cartillaSalud.setNumeroSerie(firmaElectronica.getSerialCertificado());
		

			datos.add(cartillaSalud);
			
		} else {
			datos = null;
		}

		return datos;
	}

	@Override
	public Object getDocumentoReporte4305A(AsignacionNSS nss,
			FirmaElectronica firmaElectronica, Usuario usuario, Long idOrigenSolicitud)
			throws DerechohabientesBusinessException, Exception {

		Map<String, Object> parametros = new HashMap<String, Object>();
		ByteArrayOutputStream repo = null;
		Map<String, String> plantillas = new HashMap<String, String>();
		List<Reporte4305A> datos = new ArrayList<Reporte4305A>();

		datos = getDatosReporte4305A(nss, firmaElectronica, usuario, idOrigenSolicitud);

		plantillas.put("DetalleBeneficiarios.jasper",
				"SUBREPORTE_BENEFICIARIOS");

		try {
			repo = manejadorReportes.ejecutaReporteSubreporte(parametros,
					datos, "4305A.jasper", plantillas);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return repo.toByteArray();

	}

	@Override
	public Object getDocumentoRegistroDerechohabientes(AsignacionNSS nss,
			FirmaElectronica firmaElectronica, Long idTramite, String titulo,
			Usuario usuario) throws DerechohabientesBusinessException,
			Exception {

		Map<String, Object> parametros = new HashMap<String, Object>();
		Map<String, String> plantillas = new HashMap<String, String>();

		List<Reporte4305A> datos4305A = new ArrayList<Reporte4305A>();
		List<DocumentosRegistroDTO> datos = new ArrayList<DocumentosRegistroDTO>();
		List<CartillaSaludDTO> datosCartilla = new ArrayList<CartillaSaludDTO>();

		DocumentosRegistroDTO dato = new DocumentosRegistroDTO();
		ByteArrayOutputStream repo = null;
		String clave = "";
		CabezaGrupoFamiliar patron = null;
		Sav002DTO sav = null;

		sav = getDatosSav002(nss, idTramite,true, null);

		// Se agrega informacion del sellado al objeto Sav002DTO
		if (firmaElectronica != null) {
			sav.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
			sav.setSelloDigital(firmaElectronica.getRecibo());
			sav.setSecuenciaNotarial(firmaElectronica.getSecuenciaNotaria());
			sav.setNumeroSerie(firmaElectronica.getSerialCertificado());
		}

		patron = grupoFamiliarDaoLocal.getCabezaGrupoFamiliar(nss
				.getIdAsignacionNSS());
		clave = patron.getPatronSujetoObligado().getModalidad()
				.getNumModalidad();
		sav.setModalidad(patron.getPatronSujetoObligado().getModalidad()
				.getNumModalidad());

		// Para el reporte de cartilla
		datosCartilla = getDatosCartillaSalud(sav.getIdPersona(), nss,
				firmaElectronica);

		// Para el reporte 4395A
		datos4305A = getDatosReporte4305A(nss, firmaElectronica, usuario, OrigenSolicitudEnum.VENTANILLA.getId());

		dato.setDatos4305A(datos4305A);
		dato.setDatosSav002(new ArrayList<Sav002DTO>());
		dato.getDatosSav002().add(sav);

		if (datosCartilla != null)
			dato.setDatosCartilla(datosCartilla);

		datos.add(dato);

		plantillas.put("DetalleBeneficiarios.jasper",
				"SUBREPORTE_BENEFICIARIOS");
		plantillas.put("4305A.jasper", "SUBREPORTE_4305A");
		plantillas.put("SAV002.jasper", "SUBREPORTE_SAV002");
		if (datosCartilla != null)
			plantillas.put("cartillaSalud.jasper", "SUBREPORTE_CARTILLA");

		Locale locale = new Locale("es", "mx");
		parametros.put(JRParameter.REPORT_LOCALE, locale);

		parametros.put("clave", clave);
		parametros.put("lugar", sav.getDomicilio());
		parametros.put("fecha",
				DateUtils.dateFormatCustom(new Date(), "dd MMMMM yyyy")
						.toUpperCase());

		try {
			if (datosCartilla != null) {
				repo = manejadorReportes.ejecutaReporteSubreporte(parametros,
						datos, "DocumentosRegistroDerechohabiente.jasper",
						plantillas);
			} else {
				repo = manejadorReportes.ejecutaReporteSubreporte(parametros,
						datos, "DocumentosRegistroDerechohabienteSC.jasper",
						plantillas);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return repo.toByteArray();
	}

	@Override
	public Object getRechazoSolicitud(AsignacionNSS nss, String titulo,
			Long idTipoTramite) {
		SolicitudRegistro solicitudRechazada = new SolicitudRegistro();
		Map<String, String> plantillas = new HashMap<String, String>();
		solicitudRechazada.setNombre(nss.getNombre() + " "
				+ nss.getPrimerApellido() + " " + nss.getSegundoApellido());
		solicitudRechazada.setNss(nss.getNssStr());
		List<SolicitudRegistro> datos = new ArrayList<SolicitudRegistro>();

		// Validaciones para tipos de rechazos
		if (idTipoTramite != null) {
			System.out.println(" *************************  tipo de tramite "
					+ idTipoTramite);
			if (TipoTramiteEnum.REGISTRO_PADRES.getCodigo().longValue() == idTipoTramite
					.longValue()) {
				solicitudRechazada.setTipoRechazo("registro_padres");
			} else if (TipoTramiteEnum.REGISTRO_CONCUBINA_RIO.getCodigo()
					.longValue() == idTipoTramite.longValue()) {
				solicitudRechazada.setTipoRechazo("registro_concubina");
			} else {
				solicitudRechazada.setTipoRechazo("otro");
			}
		} else {
			solicitudRechazada.setTipoRechazo("otro");
		}

		datos.add(solicitudRechazada);

		Map<String, Object> parametros = new HashMap<String, Object>();
		Locale locale = new Locale("es", "ES");
		parametros.put(JRParameter.REPORT_LOCALE, locale);
		parametros.put("TITULO", titulo);
		ByteArrayOutputStream repo = null;
		try {
			repo = manejadorReportes.ejecutaReporteSubreporte(parametros,
					datos, "ComprobanteRechazoSolicitud.jasper", plantillas);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return repo.toByteArray();
	}

	@Override
	public List<GrupoFamiliar> getGrupoFamiliarSav007(Long cveIdAsignacionNSS)
			throws DerechohabientesBusinessException, Exception {
		List<GrupoFamiliar> lista = new ArrayList<GrupoFamiliar>();
		List<GrupoFamiliar> listaSav007 = new ArrayList<GrupoFamiliar>();
		
		List<Long> tipoVigencia = new ArrayList<Long>();
		tipoVigencia.add(EstadoDerechohabienteEnum.VIGENTE.getId());
		tipoVigencia.add(EstadoDerechohabienteEnum.VIGENTE_POR_PRORRGA.getId());
		
		//se cambia la llamda para recibir los posibles estados //
		/*lista = grupoFamiliarDaoLocal.findGrupoFamiliarPorEstado(nss,EstadoDerechohabienteEnum.VIGENTE.getId());*/
		lista = grupoFamiliarDaoLocal.findGrupoFamiliarByEstado(cveIdAsignacionNSS, tipoVigencia);

		List<Long> tiposTramite = new ArrayList<Long>();
		tiposTramite.add(TipoTramiteEnum.PRORROGA_ESTUDIOS.getCodigo()
				.longValue());
		tiposTramite.add(TipoTramiteEnum.PRORROGA_ENFERMEDAD.getCodigo()
				.longValue());
		tiposTramite.add(TipoTramiteEnum.PRORROGA_OBSTETRICOS.getCodigo()
				.longValue());
		tiposTramite.add(TipoTramiteEnum.PRORROGA_VIGENCIA_TEMPORAL.getCodigo()
				.longValue());
		tiposTramite.add(TipoTramiteEnum.PRORROGA_VIGENCIA_PERMANENTE
				.getCodigo().longValue());
		tiposTramite.add(TipoTramiteEnum.PRORROGA_ACUERDOS.getCodigo().longValue());
		tiposTramite.add(TipoTramiteEnum.PRORROGA_LAUDOS.getCodigo().longValue());
		

		for (GrupoFamiliar gd : lista) {

			Tramite tramite = tramiteDaoLocal.getUltimoTramitePersona(gd
					.getDerechohabiente().getIdPersona(), tiposTramite);

			if (tramite != null
					&& new Long(tramite.getEstadoTramite()
							.getIdEstadoTramitePersona())
							.equals(EstadoTramiteEnum.CERRADO.getId())) {

				listaSav007.add(gd);
			}
		}

		if (listaSav007 == null || listaSav007.size() <= 0) {
			throw new DerechohabientesBusinessException(
					ExceptionMessages.SIN_INFORMACION);
		}

		return listaSav007;
	}

	@Override
	public List<GrupoFamiliar> getGrupoFamiliarSav005(Long nss)
			throws DerechohabientesBusinessException, Exception {
		List<GrupoFamiliar> lista = new ArrayList<GrupoFamiliar>();
		List<GrupoFamiliar> listaSav005 = new ArrayList<GrupoFamiliar>();
		
		
		lista = grupoFamiliarDaoLocal.findGrupoFamiliarByEstadoVigente(nss);

		List<Long> tiposTramite = new ArrayList<Long>();

		tiposTramite
				.add(TipoTramiteEnum.CAMBIO_CLINICA.getCodigo().longValue());

		for (GrupoFamiliar gd : lista) {

			Tramite tramite = tramiteDaoLocal.getUltimoTramitePersona(gd
					.getDerechohabiente().getIdPersona(), tiposTramite);

			if (tramite != null
					&& tramite.getEstadoTramite().getIdEstadoTramitePersona() == EstadoTramiteEnum.CERRADO
							.getId()) {
				listaSav005.add(gd);
			}
		}

		if (listaSav005 == null || listaSav005.size() <= 0) {
			throw new DerechohabientesBusinessException(
					ExceptionMessages.SIN_INFORMACION);
		}

		return listaSav005;
	}

	@Override
	public Object getDocumentoBajaDerechohabientes(AsignacionNSS nss,
			Long idTramite, String titulo)
			throws DerechohabientesBusinessException, Exception {
		Map<String, Object> parametros = new HashMap<String, Object>();
		Map<String, String> plantillas = new HashMap<String, String>();
		// List<Reporte4305A> datos4305A =new ArrayList<Reporte4305A>();
		List<DocumentosBajaDTO> datos = new ArrayList<DocumentosBajaDTO>();
		DocumentosBajaDTO dato = new DocumentosBajaDTO();
		ByteArrayOutputStream repo = null;
		String clave = "";
		CabezaGrupoFamiliar patron = null;
		Sav002DTO sav = null;

		patron = grupoFamiliarDaoLocal.getCabezaGrupoFamiliar(nss.getIdAsignacionNSS());
		sav = getDatosSav002(nss, idTramite,false, patron);
		
		clave = patron.getPatronSujetoObligado().getModalidad().getNumModalidad();
		sav.setModalidad(patron.getPatronSujetoObligado().getModalidad().getNumModalidad());
		// datos4305A= getDatosReporte4305A(nss);
		// dato.setDatos4305A(datos4305A);
		dato.setDatosSav002(new ArrayList<Sav002DTO>());
		dato.getDatosSav002().add(sav);

		datos.add(dato);

		// plantillas.put("DetalleBeneficiarios.jasper","SUBREPORTE_BENEFICIARIOS");
		// plantillas.put("4305A.jasper","SUBREPORTE_4305A");
		plantillas.put("SAV002.jasper", "SUBREPORTE_SAV002");
		Locale locale = new Locale("es", "ES");
		parametros.put(JRParameter.REPORT_LOCALE, locale);
		parametros.put("clave", clave);
		parametros.put("lugar", sav.getDomicilio());
		parametros.put("fecha",
				DateUtils.dateFormatCustom(new Date(), "dd MMMMM yyyy")
						.toUpperCase());

		try {
			repo = manejadorReportes.ejecutaReporteSubreporte(parametros,
					datos, "DocumentosBaja.jasper", plantillas);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return repo.toByteArray();
	}

	@Override
	public Object getComprobanteVigenciaDerechos(AsignacionNSS nss,
			FirmaElectronica firmaElectronica)
			throws DerechohabientesBusinessException, Exception {

		return this.getComprobanteVigenciaDerechos(nss, firmaElectronica, null);

	}

	@Override
	public Object getComprobanteVigenciaDerechos(AsignacionNSS nss,
			FirmaElectronica firmaElectronica, Usuario usuario)
			throws DerechohabientesBusinessException, Exception {
		
		/*
		Long numeroIntegrantesRegistrados = grupoFamiliarDaoLocal.getNumeroDeIntegrantesPorParentesco(nss.getIdAsignacionNSS(), null);
		
		if(numeroIntegrantesRegistrados == 0) {
			String mensajeError = "El asegurado / pensionado con n&uacute;mero de seguridad social "+ nss.getNss() + " no se encuentra registrado a&uacute;n" +
					" como derechohabiente, por lo tanto no podr&aacute; obtener su comprobante de vigencia de derechos.";
			DerechohabientesBusinessException.throwException(mensajeError, mensajeError);
		}
		*/
		
		//cambio de llamada de asegurado a cabeza de grupoFamilair
		/*GrupoFamiliar asegurado = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(
				nss.getIdAsignacionNSS(), nss.getIdPersona());
				*/
		CabezaGrupoFamiliar patron = grupoFamiliarDaoLocal.getCabezaGrupoFamiliar(nss.getIdAsignacionNSS());
		
		long numintegrantes = grupoFamiliarService.getNumeroIntegrantesRegistrsdosPorParentesco(nss.getIdAsignacionNSS(), null);
		List<GrupoFamiliar> integrantes = null;
		GrupoFamiliar	asegurado = null;
			if(numintegrantes >0){
				asegurado = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(nss.getIdAsignacionNSS(), nss.getIdPersona());
				integrantes = grupoFamiliarDaoLocal.findGrupoFamiliar(nss
						.getIdAsignacionNSS());
			}
		//GrupoFamiliar	asegurado = null;
		if(patron == null ) {
			String mensajeError = "El asegurado / pensionado con n&uacute;mero de seguridad social "+ nss.getNss() + " no se encuentra registrado a&uacute;n" +
					" como derechohabiente, por lo tanto no podr&aacute; obtener su comprobante de vigencia de derechos.";
			DerechohabientesBusinessException.throwException(mensajeError, mensajeError);
		}
		
		
		if(asegurado == null){
			
			AsignacionNSS asignacion = asignacionNssDao.getAsignacionNSS(patron.getAsignacionNSS());
			asegurado = setGrupoFamiliarByAsingacionNSS(asignacion, patron);
		}
		
		ComprobanteVigenciaDerechosDTO comprobante = new ComprobanteVigenciaDerechosDTO();

		List<ComprobanteVigenciaDerechosDTO> datos = new ArrayList<ComprobanteVigenciaDerechosDTO>();
		Map<String, String> plantillas = new HashMap<String, String>();
		
		List<BeneficiarioDTO> beneficiarios = new ArrayList<BeneficiarioDTO>();
		List<ServiciosDTO> serviciosDTO = new ArrayList<ServiciosDTO>();
		ServiciosDTO servicioDTO = null;
		BeneficiarioDTO beneficiario = new BeneficiarioDTO();
		TramiteProrroga prorroga = null;
		Long idCaracter = null;
		
		// boolean contieneServicio=false;

		
		
		comprobante.setNss(asegurado.getAsignacionNSS().getNssStr());
		comprobante.setNombreAsegurado(asegurado.getDerechohabiente()
				.getNombre());
		comprobante.setPrimerApellidoAsegurado(asegurado.getDerechohabiente()
				.getPrimerApellido());
		comprobante.setSegundoApellidoAsegurado(asegurado.getDerechohabiente()
				.getSegundoApellido());
		comprobante.setCurp(asegurado.getDerechohabiente().getCurp());
		comprobante.setSexoAsegurado(asegurado.getDerechohabiente().getSexo()
				.getDescripcion());
		comprobante.setLugarNacimientoAsegurado(asegurado.getDerechohabiente()
				.getLugarNacimiento().getNombre());
		
		if(asegurado.getDerechohabiente().getFechaNacimiento() != null) {
			comprobante.setFechaNacimientoAsegurado(DateUtils.dateFormat(asegurado
				.getDerechohabiente().getFechaNacimiento()));
		} else {
			comprobante.setFechaNacimientoAsegurado("");
		}
		
		comprobante.setSituacion(asegurado.getEstadoDerechohabiente()
				.getDescripcion());
		if(asegurado.getMedicoEnTurno() != null){
			comprobante.setDelegacion(asegurado.getMedicoEnTurno()
					.getUnidadMedicaFamiliar().getSubdelegacion().getDelegacion()
					.getDescripcion());
			comprobante.setUmf(asegurado.getMedicoEnTurno()
					.getUnidadMedicaFamiliar().getDescripcion());
			comprobante.setTurno(asegurado.getMedicoEnTurno().getTurno()
					.getDescripcion());
			comprobante.setConsultorio(asegurado.getMedicoEnTurno()
					.getConsultorio().getDescripcion());
			comprobante.setAgregadoMedico(asegurado.getAgregadoMedico());
		}
		
		
		comprobante.setRegistroPatronal(patron.getPatronSujetoObligado()
				.getNumeroRegistroPatronal());
		comprobante.setModalidadPatron(patron.getPatronSujetoObligado()
				.getModalidad().getNumModalidad());
		comprobante.setTipoMovimiento(patron.getTipoMovtoAsegurado()
				.getDesTipoMvtoAsegurado());
		comprobante.setUltimoMovimiento(patron.getFechaUltimoMovAfiliacion());
		comprobante.setFechaExpedicion(new Date());
		comprobante.setFechaValidezConstancia(patron.getFechaValidezConstancia() );
		
		
		if(patron.getPatronSujetoObligado().getFisica() == null && patron.getPatronSujetoObligado().getMoral() != null ) {
			comprobante.setNombrePatron(patron.getPatronSujetoObligado().getMoral().getRazonSocial());
		} else if(patron.getPatronSujetoObligado().getFisica() != null ) {
			String nombreR = "";
			nombreR+=(!StringUtils.isEmpty(patron.getPatronSujetoObligado().getFisica().getNombre()) ? patron.getPatronSujetoObligado().getFisica().getNombre().trim() : "");
			nombreR+=(!StringUtils.isEmpty(patron.getPatronSujetoObligado().getFisica().getPrimerApellido()) ? " " + patron.getPatronSujetoObligado().getFisica().getPrimerApellido().trim() : "");
			nombreR+=(!StringUtils.isEmpty(patron.getPatronSujetoObligado().getFisica().getSegundoApellido()) ? " " +patron.getPatronSujetoObligado().getFisica().getSegundoApellido().trim() : "");
			
			comprobante.setNombrePatron(nombreR);
		}
		
		

		try {
			serviciosDTO = grupoFamiliarService.getServiciosGrupoFamiliar(nss.getIdAsignacionNSS());
		} catch (Exception e) {
			log.error("No se recuperaron los servicios", e);
		}

		if (serviciosDTO == null | serviciosDTO.isEmpty()) {
			servicioDTO = new ServiciosDTO();
			servicioDTO.setServicio("");
			servicioDTO.setSiNo("");
			serviciosDTO.add(servicioDTO);
		}

		comprobante.setServicios(serviciosDTO);

		// Determinar si el asegurado tiene derecho a servicio medico
		for (ServiciosDTO item : serviciosDTO) {
			if (item.getIdServicio().longValue() == ServiciosPrestacionesEnum.SERVICIO_MEDICO.getId()){
				comprobante.setServicioMedico(item.getSiNo());
				
			}
		}

		// Se agrega informacion de la firma digital
		if (firmaElectronica != null) {
			comprobante.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
			comprobante.setSelloDigital(firmaElectronica.getRecibo());
			comprobante.setSecuenciaNotarial(firmaElectronica
					.getSecuenciaNotaria());
			comprobante.setNumeroSerie(firmaElectronica.getSerialCertificado());
		}

		if (usuario != null){// && usuario.getIdUmf() != null) {
			//integrantes = this.filtrarPorUmf(integrantes, usuario.getIdUmf());

			if(usuario.getIdUmf() != null) {

				comprobante.setBandera("");

				// Tambien se imprime informacion del usuario
				String nombre = ( usuario.getNomNombre() == null ? "" : usuario.getNomNombre() );
				String paterno = ( usuario.getNomPaterno() == null ? "" : usuario.getNomPaterno() );
				String materno = ( usuario.getNomMaterno() == null ? "" : usuario.getNomMaterno() );
				
				comprobante.setNombreUsuario(nombre + " " + paterno + " " + materno);
				
				comprobante.setDelegacionUsuario(usuario.getUsuarioFuncionario().getDelegacion().getDescripcion());
				comprobante.setUnidadUsuario(usuario.getUsuarioFuncionario().getUnidadMedicaFamiliar().getDescripcion());
			}
		}

		if(integrantes != null && !integrantes.isEmpty()) {
			for (GrupoFamiliar grupoFamiliar : integrantes) {
			
				if( grupoFamiliar != null  ){
					beneficiario = new BeneficiarioDTO();
					if (grupoFamiliar.getEstadoDerechohabiente().getIdEstadoDerechohabiente() == EstadoDerechohabienteEnum.BAJA.getId()  ||
						grupoFamiliar.getEstadoDerechohabiente().getIdEstadoDerechohabiente() == EstadoDerechohabienteEnum.FALLECIDO.getId()){ 
						beneficiario.setServicioMedico("NO");	
						
					} else if(grupoFamiliar.getEstadoDerechohabiente().getIdEstadoDerechohabiente() == EstadoDerechohabienteEnum.VIGENTE.getId() && 
							grupoFamiliar.getParentesco().getIdParentesco().longValue() != ParentescoEnum.ASEGURADO.getId() &&  
							grupoFamiliar.getParentesco().getIdParentesco().longValue() != ParentescoEnum.PENSIONADO.getId()){
						
						beneficiario.setServicioMedico("SI");	
					} else{
						
						beneficiario.setServicioMedico(comprobante.getServicioMedico());
					}
	
					beneficiario.setNombreBen(grupoFamiliar.getDerechohabiente()
							.getNombre());
					beneficiario.setPrimerApellidoBen(grupoFamiliar
							.getDerechohabiente().getPrimerApellido());
					beneficiario.setSegundoApellidoBen(grupoFamiliar
							.getDerechohabiente().getSegundoApellido());
					beneficiario.setCurpBen(grupoFamiliar.getDerechohabiente()
							.getCurp());
					beneficiario.setParentescoBen(grupoFamiliar.getParentesco()
							.getDescripcion());
					beneficiario.setFechaNacimientoBen(grupoFamiliar
							.getDerechohabiente().getFechaNacimiento());
					
					if( grupoFamiliar.getDerechohabiente().getLugarNacimiento() != null ){
						beneficiario.setLugarNacimientoAsegurado( grupoFamiliar.getDerechohabiente().getLugarNacimiento().getNombre() );
					}
					
					if(grupoFamiliar.getDerechohabiente().getFechaNacimiento() != null) {
						beneficiario.setEdadBen(DateUtils.getEdad(grupoFamiliar
								.getDerechohabiente().getFechaNacimiento()));
					} 
					
					beneficiario.setSexoBen(grupoFamiliar.getDerechohabiente()
							.getSexo().getDescripcion());
					
					if(grupoFamiliar.getMedicoEnTurno() != null) {
						beneficiario.setDelegacionBen(grupoFamiliar.getMedicoEnTurno()
								.getUnidadMedicaFamiliar().getSubdelegacion()
								.getDelegacion().getDescripcion());
						beneficiario.setUmfBen(grupoFamiliar.getMedicoEnTurno()
								.getUnidadMedicaFamiliar().getDescripcion());
						beneficiario.setTurno(grupoFamiliar.getMedicoEnTurno().getTurno()
								.getDescripcion());
						beneficiario.setConsultorio(grupoFamiliar.getMedicoEnTurno()
								.getConsultorio().getDescripcion());
					}
					beneficiario.setAgregadoMedico(grupoFamiliar
							.getAgregadoMedico());
					beneficiario.setSituacionBen(grupoFamiliar
							.getEstadoDerechohabiente().getDescripcion());
					beneficiario.setVencimientoVigenciaBen(grupoFamiliar
							.getFechaFinVigencia());
					if (grupoFamiliar.getEstadoDerechohabiente()
							.getIdEstadoDerechohabiente() == EstadoDerechohabienteEnum.VIGENTE
							.getId()) {
						if (grupoFamiliar.getSubEstadoDerechohabiente()
								.getIdSubEstadoDerechohabiente() == SubestadoDerechohabienteEnum.TEMPORAL
								.getId()) {
							idCaracter = CaracterEnum.PROVISIONAL.getId();
						} else if (grupoFamiliar.getSubEstadoDerechohabiente()
								.getIdSubEstadoDerechohabiente() == SubestadoDerechohabienteEnum.PERMANENTE
								.getId()) {
							idCaracter = CaracterEnum.DEFINITIVO.getId();
						}
	
						prorroga = prorrogaDao.getProrrogaActiva(grupoFamiliar.getAsignacionNSS().getIdAsignacionNSS(),grupoFamiliar
								.getDerechohabiente().getIdPersona(), idCaracter);
	
						if (prorroga != null) {
							if (grupoFamiliar
									.getDerechohabiente()
									.getIdPersona()
									.equals(asegurado.getDerechohabiente()
											.getIdPersona())) {
								comprobante.setDetalleSituacion(prorroga.getTramite()
										.getTipoTramite().getDescripcion());
							}
							beneficiario.setDetalleSituacionBen(prorroga.getTramite()
									.getTipoTramite().getDescripcion());
	
						}
					} else if (grupoFamiliar.getEstadoDerechohabiente()
							.getIdEstadoDerechohabiente() == EstadoDerechohabienteEnum.BAJA
							.getId()) {
	
						List<Long> personas = new ArrayList<Long>();
						personas.add(grupoFamiliar.getDerechohabiente().getIdPersona());
						List<Long> estadosTramite = new ArrayList<Long>();
						estadosTramite.add(EstadoTramiteEnum.CERRADO.getId());
	
						List<Solicitud> solicituds = solicitudTramiteBusinessRemote
								.getSolicitudesPersona(personas, null, estadosTramite,
										null, null, nss.getIdPersona(), true, 1, true);
						boolean isAsegurado = grupoFamiliar.getDerechohabiente()
								.getIdPersona().equals(nss.getIdPersona());
	
						if (solicituds != null && !solicituds.isEmpty()) {
							Solicitud solBaja = solicituds.get(0);
							if (solBaja.getTipoSolicitud().getIdTipoSolicitud()
									.equals(TipoSolicitudEnum.BAJA.getId())) {
								if (isAsegurado) {
									comprobante.setDetalleSituacion(solBaja
											.getTramites().get(0).getTipoTramite()
											.getDescripcion());
								}
								beneficiario.setDetalleSituacionBen(solBaja
										.getTramites().get(0).getTipoTramite()
										.getDescripcion());
							}
						}
	
					}
	
					beneficiarios.add(beneficiario);
				}
				
			}
	}
		if (beneficiario != null && !beneficiarios.isEmpty())
			comprobante.setBeneficiarios(beneficiarios);
		else 
			comprobante.setBeneficiarios(new ArrayList<BeneficiarioDTO>());

		//Se buscan los patrones
		List<SujetoObligado> patronesActivos = grupoFamiliarService.getPatronesAsegurado(nss);
		List<PatronDTO> patronesAc = new ArrayList<PatronDTO>();
		if(patronesActivos != null && !patronesActivos.isEmpty()){
			for(SujetoObligado pat: patronesActivos){
				PatronDTO patDto = new PatronDTO();
				patDto.setRegistroPatronal(pat.getNumeroRegistroPatronal());
				patDto.setModalidad(pat.getModalidad().getNumModalidad());
				
				if(pat.getFisica() == null) {
					patDto.setNombreRazonSocial(pat.getMoral().getRazonSocial());
				} else {
					String nombreR = "";
					nombreR+=(!StringUtils.isEmpty(pat.getFisica().getNombre()) ? pat.getFisica().getNombre().trim() : "");
					nombreR+=(!StringUtils.isEmpty(pat.getFisica().getPrimerApellido()) ? " " + pat.getFisica().getPrimerApellido().trim() : "");
					nombreR+=(!StringUtils.isEmpty(pat.getFisica().getSegundoApellido()) ? " " + pat.getFisica().getSegundoApellido().trim() : "");
					
					patDto.setNombreRazonSocial(nombreR);
				}
				
				patronesAc.add(patDto);
			}
		}
		comprobante.setPatrones(patronesAc);
		
		datos.add(comprobante);

		plantillas.put("BeneficiariosVigenciaDerechosExterno.jasper","SUBREPORTE_BENEFICIARIOS");
		plantillas.put("ServiciosAseguradoExterno.jasper","SUBREPORTE_SERVICIOS");
		plantillas.put("patronesActivos.jasper","SUBREPORTE_PATRONES");

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("LOGO",
				new ClassPathResource("reportes/img/logo.jpg").getPath());
		parametros.put("LOGO_IMSS",
				new ClassPathResource("reportes/img/logo_imss_digital.jpg").getPath());
		
		ByteArrayOutputStream repo = null;
		try {
			repo = manejadorReportes.ejecutaReporteSubreporte(parametros,
					datos, "ComprobanteVigenciaDerechosExterno.jasper",
					plantillas);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return repo.toByteArray();

	}

	@Override
	public List<ComprobanteVigenciaDerechosDTO> getDatosVigenciaDerechos(
			AsignacionNSS nss) throws DerechohabientesBusinessException,
			Exception {
		GrupoFamiliar asegurado = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(
				nss.getIdAsignacionNSS(), nss.getIdPersona());
		ComprobanteVigenciaDerechosDTO comprobante = new ComprobanteVigenciaDerechosDTO();

		List<ComprobanteVigenciaDerechosDTO> datos = new ArrayList<ComprobanteVigenciaDerechosDTO>();
		List<GrupoFamiliar> integrantes = grupoFamiliarDaoLocal.findGrupoFamiliar(nss
				.getIdAsignacionNSS());
		List<BeneficiarioDTO> beneficiarios = new ArrayList<BeneficiarioDTO>();
		List<ServiciosDTO> serviciosDTO = new ArrayList<ServiciosDTO>();
		ServiciosDTO servicioDTO = null;
		BeneficiarioDTO beneficiario = new BeneficiarioDTO();
		TramiteProrroga prorroga = null;
		Long idCaracter = null;
		CabezaGrupoFamiliar patron = null;
		boolean contieneServicio = false;

		try {
			patron = grupoFamiliarDaoLocal.getCabezaGrupoFamiliar(nss
					.getIdAsignacionNSS());
		} catch (DerechohabientesBusinessException e1) {
			throw new DerechohabientesBusinessException(e1.getMessage());
		}

		comprobante.setNss(asegurado.getAsignacionNSS().getNssStr());
		comprobante.setNombreAsegurado(asegurado.getDerechohabiente()
				.getNombre());
		comprobante.setPrimerApellidoAsegurado(asegurado.getDerechohabiente()
				.getPrimerApellido());
		comprobante.setSegundoApellidoAsegurado(asegurado.getDerechohabiente()
				.getSegundoApellido());
		comprobante.setCurp(asegurado.getDerechohabiente().getCurp());
		comprobante.setSexoAsegurado(asegurado.getDerechohabiente().getSexo()
				.getDescripcion());
		comprobante.setLugarNacimientoAsegurado(asegurado.getDerechohabiente()
				.getLugarNacimiento().getNombre());
		if(asegurado.getDerechohabiente().getFechaNacimiento() != null) {
			comprobante.setFechaNacimientoAsegurado(DateUtils.dateFormat(asegurado
					.getDerechohabiente().getFechaNacimiento()));
		} else {
			comprobante.setFechaNacimientoAsegurado("");
		}
		comprobante.setSituacion(asegurado.getEstadoDerechohabiente()
				.getDescripcion());
		comprobante.setDelegacion(asegurado.getMedicoEnTurno()
				.getUnidadMedicaFamiliar().getSubdelegacion().getDelegacion()
				.getDescripcion());
		comprobante.setUmf(asegurado.getMedicoEnTurno()
				.getUnidadMedicaFamiliar().getDescripcion());
		comprobante.setRegistroPatronal(patron.getPatronSujetoObligado()
				.getNumeroRegistroPatronal());
		comprobante.setModalidadPatron(patron.getPatronSujetoObligado()
				.getModalidad().getNumModalidad());
		comprobante.setFechaExpedicion(new Date());
		comprobante.setFechaValidezConstancia( patron.getFechaValidezConstancia() );
		
		List<ServicioPrestDerechohab> servicios = vigenciaDao.findServicios();
		List<ModServPresDerechohab> servicioByAsegurado = vigenciaDao
				.getServiciosByAsegurado(nss.getIdAsignacionNSS());

		for (ServicioPrestDerechohab servicio : servicios) {

			servicioDTO = new ServiciosDTO();
			servicioDTO.setServicio(servicio.getNomServicioDerechohab());
			servicioDTO.setSiNo("NO");
			for (ModServPresDerechohab servicioAsignado : servicioByAsegurado) {
				if (servicioAsignado.getServicioPrestDerechohab()
						.getCveIdServicioDerechohab() == servicio
						.getCveIdServicioDerechohab()) {
					contieneServicio = true;
					break;
				}
			}
			if (contieneServicio) {
				servicioDTO.setSiNo("SI");
			}
			serviciosDTO.add(servicioDTO);
		}
		if (serviciosDTO.isEmpty()) {
			servicioDTO = new ServiciosDTO();
			servicioDTO.setServicio("");
			servicioDTO.setSiNo("");
			serviciosDTO.add(servicioDTO);
		}

		comprobante.setServicios(serviciosDTO);

		for (GrupoFamiliar grupoFamiliar : integrantes) {
			beneficiario = new BeneficiarioDTO();
			beneficiario.setNombreBen(grupoFamiliar.getDerechohabiente()
					.getNombre());
			beneficiario.setPrimerApellidoBen(grupoFamiliar
					.getDerechohabiente().getPrimerApellido());
			beneficiario.setSegundoApellidoBen(grupoFamiliar
					.getDerechohabiente().getSegundoApellido());
			beneficiario.setCurpBen(grupoFamiliar.getDerechohabiente()
					.getCurp());
			beneficiario.setParentescoBen(grupoFamiliar.getParentesco()
					.getDescripcion());
			beneficiario.setFechaNacimientoBen(grupoFamiliar
					.getDerechohabiente().getFechaNacimiento());
			if(grupoFamiliar.getDerechohabiente().getFechaNacimiento() != null) {
				beneficiario.setEdadBen(DateUtils.getEdad(grupoFamiliar
					.getDerechohabiente().getFechaNacimiento()));
			}
			beneficiario.setSexoBen(grupoFamiliar.getDerechohabiente()
					.getSexo().getDescripcion());
			beneficiario.setDelegacionBen(grupoFamiliar.getMedicoEnTurno()
					.getUnidadMedicaFamiliar().getSubdelegacion()
					.getDelegacion().getDescripcion());
			beneficiario.setUmfBen(grupoFamiliar.getMedicoEnTurno()
					.getUnidadMedicaFamiliar().getDescripcion());
			beneficiario.setSituacionBen(grupoFamiliar
					.getEstadoDerechohabiente().getDescripcion());
			beneficiario.setVencimientoVigenciaBen(grupoFamiliar
					.getFechaFinVigencia());
			if (grupoFamiliar.getEstadoDerechohabiente()
					.getIdEstadoDerechohabiente() == EstadoDerechohabienteEnum.VIGENTE
					.getId()) {
				if (grupoFamiliar.getSubEstadoDerechohabiente()
						.getIdSubEstadoDerechohabiente() == SubestadoDerechohabienteEnum.TEMPORAL
						.getId()) {
					idCaracter = CaracterEnum.PROVISIONAL.getId();
				} else if (grupoFamiliar.getSubEstadoDerechohabiente()
						.getIdSubEstadoDerechohabiente() == SubestadoDerechohabienteEnum.PERMANENTE
						.getId()) {
					idCaracter = CaracterEnum.DEFINITIVO.getId();
				}

				prorroga = prorrogaDao.getProrrogaActiva(grupoFamiliar.getAsignacionNSS().getIdAsignacionNSS(),grupoFamiliar
						.getDerechohabiente().getIdPersona(), idCaracter);

				if (prorroga != null) {
					if (grupoFamiliar
							.getDerechohabiente()
							.getIdPersona()
							.equals(asegurado.getDerechohabiente()
									.getIdPersona())) {
						comprobante.setDetalleSituacion(prorroga.getTramite()
								.getTipoTramite().getDescripcion());
					}
					beneficiario.setDetalleSituacionBen(prorroga.getTramite()
							.getTipoTramite().getDescripcion());

				}
			} else if (grupoFamiliar.getEstadoDerechohabiente()
					.getIdEstadoDerechohabiente() == EstadoDerechohabienteEnum.BAJA
					.getId()) {
				List<Tramite> tramites = tramiteDaoLocal.getTramitaByEstado(
						grupoFamiliar.getDerechohabiente().getIdPersona(),
						EstadoTramiteEnum.CERRADO.getId());
				Tramite tramite = null;
				if (tramites != null && tramites.size() > 0) {
					tramite = tramites.get(tramites.size() - 1);
					if (grupoFamiliar
							.getDerechohabiente()
							.getIdPersona()
							.equals(asegurado.getDerechohabiente()
									.getIdPersona())) {
						comprobante.setDetalleSituacion(tramite
								.getTipoTramite().getDescripcion());
					}
					beneficiario.setDetalleSituacionBen(tramite
							.getTipoTramite().getDescripcion());
				}
			}
			beneficiarios.add(beneficiario);
		}
		if (beneficiario != null && !beneficiarios.isEmpty())
			comprobante.setBeneficiarios(beneficiarios);

		datos.add(comprobante);

		return datos;
	}

	@Override
	public TramiteProrroga getProrrogaActiva() {
		return null;
	}

	private BeneficiarioDTO getIntegranteGrupoFamiliar(
			GrupoFamiliar integrante, BeneficiarioDTO beneficiario) {
		Calendar fechaNacimiento = new GregorianCalendar();
		if (integrante.getDerechohabiente().getFechaNacimiento() != null) {
			fechaNacimiento.setTime(integrante.getDerechohabiente()
					.getFechaNacimiento());
			beneficiario.setMesNacimiento(DateUtils.dateFormatCustom(integrante
					.getDerechohabiente().getFechaNacimiento(), "MM"));
			beneficiario.setAnioNacimiento(fechaNacimiento.get(Calendar.YEAR));
		}
		beneficiario
				.setNombreBen((integrante.getDerechohabiente()
						.getPrimerApellido() == null ? "" : integrante
						.getDerechohabiente().getPrimerApellido().toUpperCase())
						+ " "
						+ (integrante.getDerechohabiente().getSegundoApellido() == null ? ""
								: integrante.getDerechohabiente()
										.getSegundoApellido().toUpperCase())
						+ " "
						+ (integrante.getDerechohabiente().getNombre() == null ? ""
								: integrante.getDerechohabiente().getNombre()
										.toUpperCase()));
		beneficiario.setSexoBen(integrante.getDerechohabiente().getSexo()
				.getDescripcion().substring(0, 1));

		return beneficiario;
	}

	private List<Reporte4305A> getDatosReporte4305A(AsignacionNSS nss,
			FirmaElectronica firmaElectronica, Usuario usuario, Long idOrigenSolicitud)
			throws DerechohabientesBusinessException, Exception {
		List<Reporte4305A> datos = new ArrayList<Reporte4305A>();
		// Asegurado asegurado = null;
		Calendar fechaNacimiento = new GregorianCalendar();
		List<GrupoFamiliar> conyuge = null;
		List<GrupoFamiliar> padres = null;
		List<GrupoFamiliar> hijos = null;
		Integer numRegistro = 0;
		List<BeneficiarioDTO> beneficiarios = new ArrayList<BeneficiarioDTO>();
		BeneficiarioDTO beneficiario = null;
		GrupoFamiliar cabezaGrupoFamiliar = null;
		String domicilio = "";

		String tramitador = usuario != null ? usuario.getUsuario() : "";

		try {
			cabezaGrupoFamiliar = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(
					nss.getIdAsignacionNSS(), nss.getIdPersona());
			if (!cabezaGrupoFamiliar.getMedicoEnTurno()
					.getUnidadMedicaFamiliar().getIdUMF()
					.equals(usuario.getIdUmf())) {
				List<Long> estados = new ArrayList<Long>();
				estados.add(EstadoDerechohabienteEnum.VIGENTE.getId());
				estados.add(EstadoDerechohabienteEnum.CONSERVACION_DERECHOS
						.getId());
				estados.add(EstadoDerechohabienteEnum.CON_DERECHO.getId());
				List<GrupoFamiliar> integrantesEnUmf = grupoFamiliarDaoLocal
						.findIntegrantesPorUmfEstado(nss.getIdAsignacionNSS(),
								usuario.getIdUmf(), estados, null, null);
				if (!integrantesEnUmf.isEmpty()) {
					cabezaGrupoFamiliar = integrantesEnUmf.get(0);
				}
			}
		} catch (DerechohabientesBusinessException e) {
			throw new DerechohabientesBusinessException(e.getMessage());
		}

		Reporte4305A dato = new Reporte4305A();

		dato.setNombreAsegurado(nss.getNombre());
		dato.setApellidoPaternoAsegurado(nss.getPrimerApellido());
		dato.setApellidoMaternoAsegurado(nss.getSegundoApellido() != null ? nss
				.getSegundoApellido() : "");

		// Formateando CURP
		String curp = nss.getCurp();
		String curpFinal = "";
		if (curp != null) {
			for (int i = 0; i < curp.length(); i++) {
				curpFinal += curp.charAt(i) + "  ";
			}
		}

		dato.setCurpAsegurado(curpFinal);
		dato.setNss(nss.getNssStr());
		dato.setConsultorioTurno(stringTresPosiciones(cabezaGrupoFamiliar
				.getMedicoEnTurno().getConsultorio().getDescripcion(), 2)
				+ "      "
				+ cabezaGrupoFamiliar.getMedicoEnTurno().getTurno()
						.getDescripcion().toUpperCase().substring(0, 1));
		dato.setFechaExpedicion(new Date());

		if (cabezaGrupoFamiliar.getMedicoEnTurno().getUnidadMedicaFamiliar()
				.getIdUMF().longValue() == usuario.getIdUmf().longValue()) {
			
			if(cabezaGrupoFamiliar.getDomicilio()!= null) {
			domicilio = "CALLE ";

					if(cabezaGrupoFamiliar.getDomicilio().getVialidadPrimaria() != null
							&& cabezaGrupoFamiliar.getDomicilio().getVialidadPrimaria()
							.getNombre() != null){
						domicilio += cabezaGrupoFamiliar.getDomicilio().getVialidadPrimaria()
								.getNombre();
					}


					domicilio += ", "
						+ (cabezaGrupoFamiliar.getDomicilio().getNumExterior1() != null? cabezaGrupoFamiliar.getDomicilio().getNumExterior1() : "")
						+ " "
						+ (cabezaGrupoFamiliar.getDomicilio().getNumExteriorAlf() != null ? cabezaGrupoFamiliar
								.getDomicilio().getNumExteriorAlf().toUpperCase()
								+ ", "
								: "")
						+ ""
						+ (cabezaGrupoFamiliar.getDomicilio().getNumInterior() != null ? cabezaGrupoFamiliar
								.getDomicilio().getNumInterior() : "")
						+ " "
						+ (cabezaGrupoFamiliar.getDomicilio().getNumInteriorAlf() != null ? cabezaGrupoFamiliar
								.getDomicilio().getNumInteriorAlf().toUpperCase()
								: "")
						+ ", "
						+ "COLONIA ";

						if(cabezaGrupoFamiliar.getDomicilio().getAsentamiento() != null && cabezaGrupoFamiliar.getDomicilio().getAsentamiento().getNombre() != null){
							domicilio += cabezaGrupoFamiliar.getDomicilio().getAsentamiento().getNombre();

							if(cabezaGrupoFamiliar.getDomicilio().getAsentamiento().getLocalidad() != null
									&& cabezaGrupoFamiliar.getDomicilio().getAsentamiento().getLocalidad().getMunicipio() != null){

								if(cabezaGrupoFamiliar.getDomicilio().getAsentamiento().getLocalidad().getMunicipio().getNombre() != null){
									domicilio += ", "
											+ cabezaGrupoFamiliar.getDomicilio().getAsentamiento()
											.getLocalidad().getMunicipio().getNombre();
								}

								if(cabezaGrupoFamiliar.getDomicilio().getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa() != null){
									domicilio += ", "
											+ cabezaGrupoFamiliar.getDomicilio().getAsentamiento()
											.getLocalidad().getMunicipio()
											.getEntidadFederativa().getNombre();
								}


							}

						}

						if(cabezaGrupoFamiliar.getDomicilio().getCodigoPostal() != null
								&& cabezaGrupoFamiliar.getDomicilio().getCodigoPostal().getCodigoPostal() != null){

							domicilio += ", "+ "C.P. " + cabezaGrupoFamiliar.getDomicilio().getCodigoPostal().getCodigoPostal();
						}

			}
		}
		dato.setDomicilio(domicilio);

		// subreporte
		beneficiario = new BeneficiarioDTO();
		beneficiario.setNumeroRegistro(1);
		beneficiario.setCalidad("A/A");
		beneficiario.setNombreBen(dato.getApellidoPaternoAsegurado().toUpperCase()
				+ " "+ dato.getApellidoMaternoAsegurado().toUpperCase()+ " "+ dato.getNombreAsegurado().toUpperCase());
		beneficiario.setSexoBen(nss.getSexo().getDescripcion().substring(0, 1));

		if (nss.getFechaNacimiento() != null) {
			fechaNacimiento.setTime(nss.getFechaNacimiento());
			beneficiario.setMesNacimiento(DateUtils.dateFormatCustom(
					nss.getFechaNacimiento(), "MM"));
			beneficiario.setAnioNacimiento(fechaNacimiento.get(Calendar.YEAR));
		} 
		
		beneficiario.setObservaciones(tramitador);

		beneficiarios.add(beneficiario);

		beneficiario = new BeneficiarioDTO();
		beneficiario.setNumeroRegistro(2);
		beneficiario.setCalidad("E/C");

		beneficiarios.add(beneficiario);

		beneficiario = new BeneficiarioDTO();
		beneficiario.setNumeroRegistro(3);
		beneficiario.setCalidad("B/P");

		beneficiarios.add(beneficiario);

		beneficiario = new BeneficiarioDTO();
		beneficiario.setNumeroRegistro(4);
		beneficiario.setCalidad("B/M");

		beneficiarios.add(beneficiario);

		// si tiene conyuge
		// if(asegurado.getAsignacionNSS().getEstadoCivil()!=null &&
		// asegurado.getAsignacionNSS().getEstadoCivil().getIdEstadoCivil()==
		// EstadoCivilEnum.CASADO.getId()){
		//TODO descomentar toda esta parte ya que esta tronando porque l ws regresa datos qye no se encuentranen bdtu
		
		conyuge = grupoFamiliarDaoLocal.findGrupoFamiliarParentescoEstado(
				nss.getIdAsignacionNSS(), ParentescoEnum.CONYUGE.getId(),
				EstadoDerechohabienteEnum.VIGENTE.getId());

		if (conyuge == null || conyuge.size() == 0) {
			conyuge = grupoFamiliarDaoLocal.findGrupoFamiliarParentescoEstado(
					nss.getIdAsignacionNSS(),
					ParentescoEnum.CONCUBINARIO.getId(),
					EstadoDerechohabienteEnum.VIGENTE.getId());
		}
		if (conyuge == null || conyuge.size() == 0) {
			conyuge = grupoFamiliarDaoLocal.findGrupoFamiliarParentescoEstado(
					nss.getIdAsignacionNSS(),
					ParentescoEnum.PERSONA_EN_UNION_CIVIL.getId(),
					EstadoDerechohabienteEnum.VIGENTE.getId());
		}

		if (conyuge != null && conyuge.size() > 0) {
			conyuge = this.filtrarPorUmf(conyuge, usuario.getIdUmf());
			if (conyuge.size() > 0) {
				beneficiario = beneficiarios.get(1);
				beneficiario.setObservaciones(tramitador);
				getIntegranteGrupoFamiliar(conyuge.get(0), beneficiario);
				if (domicilio.equals("") && conyuge.get(0).getDomicilio() != null) {
					domicilio = "CALLE "
							+ conyuge.get(0).getDomicilio()
									.getVialidadPrimaria().getNombre()
							+ ", "
							+ (conyuge.get(0).getDomicilio().getNumExterior1() != null? conyuge.get(0).getDomicilio().getNumExterior1() : "")
							+ " "
							+ (conyuge.get(0).getDomicilio()
									.getNumExteriorAlf() != null ? conyuge
									.get(0).getDomicilio().getNumExteriorAlf()
									.toUpperCase()
									+ ", " : "")
							+ ""
							+ (conyuge.get(0).getDomicilio().getNumInterior() != null ? conyuge
									.get(0).getDomicilio().getNumInterior()
									: "")
							+ " "
							+ (conyuge.get(0).getDomicilio()
									.getNumInteriorAlf() != null ? conyuge
									.get(0).getDomicilio().getNumInteriorAlf()
									.toUpperCase() : "")
							+ ", "
							+ "COLONIA "
							+ conyuge.get(0).getDomicilio().getAsentamiento()
									.getNombre()
							+ ", "
							+ conyuge.get(0).getDomicilio().getAsentamiento()
									.getLocalidad().getMunicipio().getNombre()
							+ ", "
							+ conyuge.get(0).getDomicilio().getAsentamiento()
									.getLocalidad().getMunicipio()
									.getEntidadFederativa().getNombre()
							+ ", "
							+ "C.P. "
							+ conyuge.get(0).getDomicilio().getCodigoPostal()
									.getCodigoPostal();
				}
			}
		}

		// padres
		padres = grupoFamiliarDaoLocal.findGrupoFamiliarParentescoEstado(
				nss.getIdAsignacionNSS(), ParentescoEnum.PADRES.getId(),
				EstadoDerechohabienteEnum.VIGENTE.getId());
		padres = this.filtrarPorUmf(padres, usuario.getIdUmf());
		for (GrupoFamiliar padre : padres) {
			beneficiario = new BeneficiarioDTO();
			if (padre.getDerechohabiente().getSexo().getIdSexo() == SexoEnum.HOMBRE
					.getId()) {
				numRegistro = 2;
			} else {
				numRegistro = 3;
			}
			beneficiario = beneficiarios.get(numRegistro);
			beneficiario.setObservaciones(tramitador);
			getIntegranteGrupoFamiliar(padre, beneficiario);
		}

		hijos = grupoFamiliarDaoLocal.findGrupoFamiliarParentescoEstado(
				nss.getIdAsignacionNSS(), ParentescoEnum.HIJOS.getId(),
				EstadoDerechohabienteEnum.VIGENTE.getId());
		hijos = this.filtrarPorUmf(hijos, usuario.getIdUmf());
		numRegistro = 5;
		for (GrupoFamiliar hijo : hijos) {
			beneficiario = new BeneficiarioDTO();
			beneficiario.setNumeroRegistro(numRegistro);
			beneficiario.setCalidad("H/" + (numRegistro - 1));
			beneficiario.setObservaciones(tramitador);
			getIntegranteGrupoFamiliar(hijo, beneficiario);
			beneficiarios.add(beneficiario);
			numRegistro += 1;
			if (domicilio.equals("") && hijo.getDomicilio() != null) {
				domicilio = "CALLE "
						+ hijo.getDomicilio().getVialidadPrimaria().getNombre()
						+ ", "
						+ (hijo.getDomicilio().getNumExterior1() != null? hijo.getDomicilio().getNumExterior1() : "")
						+ " "
						+ (hijo.getDomicilio().getNumExteriorAlf() != null ? hijo
								.getDomicilio().getNumExteriorAlf()
								.toUpperCase()
								+ ", "
								: "")
						+ ""
						+ (hijo.getDomicilio().getNumInterior() != null ? hijo
								.getDomicilio().getNumInterior() : "")
						+ " "
						+ (hijo.getDomicilio().getNumInteriorAlf() != null ? hijo
								.getDomicilio().getNumInteriorAlf()
								.toUpperCase()
								: "")
						+ ", "
						+ "COLONIA "
						+ hijo.getDomicilio().getAsentamiento().getNombre()
						+ ", "
						+ hijo.getDomicilio().getAsentamiento().getLocalidad()
								.getMunicipio().getNombre()
						+ ", "
						+ hijo.getDomicilio().getAsentamiento().getLocalidad()
								.getMunicipio().getEntidadFederativa()
								.getNombre()
						+ ", "
						+ "C.P. "
						+ hijo.getDomicilio().getCodigoPostal()
								.getCodigoPostal();
			}
		}
		
		dato.setDomicilio(domicilio);
		dato.setBeneficiarios(beneficiarios);

		// Agregar datos de la firma electronica
		if (firmaElectronica != null) {
			dato.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
			dato.setSelloDigital(firmaElectronica.getRecibo());
			dato.setSecuenciaNotarial(firmaElectronica.getSecuenciaNotaria());
			dato.setNumeroSerie(firmaElectronica.getSerialCertificado());
		}

		datos.add(dato);
		return datos;
	}

	private Sav002DTO getDatosSav002(AsignacionNSS nss, Long idTramite, Boolean registro, CabezaGrupoFamiliar cabeza)
			throws DerechohabientesBusinessException, Exception {

		Tramite tramite = null;
		GrupoFamiliar gf = null;
		Sav002DTO sav = new Sav002DTO();

		log.debug("Entro al metodo que genera el documentos sav002");
		
		tramite = tramiteDaoLocal.getTramite(idTramite);
		
		if(cabeza != null) {
			cabeza = grupoFamiliarDaoLocal.getCabezaGrupoFamiliar(nss.getIdAsignacionNSS());
		}
		
		// Verificamos si el tramite afecta a una sola persona
		if (tramite.getPersona() != null) {
			log.debug("el tramite trae solo una persona "+nss.getIdAsignacionNSS() + " - " +tramite.getPersona().getIdPersona());
			gf = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(nss.getIdAsignacionNSS(),tramite.getPersona().getIdPersona());
		}// De lo contrario usamos al primero de la lista
		else {
			log.debug("el tramite trae mas de una persona "+nss.getIdAsignacionNSS() + " - " +tramite.getPersonas().get(0).getIdPersona());
			gf = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(nss.getIdAsignacionNSS(),tramite.getPersonas().get(0).getIdPersona());
		}
		
		
		
		sav.setModalidad("");
		
		try{
			sav.setModalidad(cabeza.getPatronSujetoObligado().getModalidad().getNumModalidad());
		}catch(NullPointerException e){
			// --------------------------------------------------------
			// Los pensionados pueden no tener un patron
			// --------------------------------------------------------
			log.debug("Sujeto Obligado NULL, idAsignacion: "+nss.getIdAsignacionNSS());
			
		}
		
		sav.setAgregadoMedico(gf.getAgregadoAfiliacion());

		if(gf.getDomicilio() != null ) {

			if(gf.getDomicilio().getVialidadPrimaria()!=null){

				sav.setCalleNumero(gf.getDomicilio().getVialidadPrimaria().getNombre()
						+ " " + gf.getDomicilio().getNumExterior1() + " "
						+ gf.getDomicilio().getNumExteriorAlf() != null ? gf
						.getDomicilio().getNumExteriorAlf() : "");

			}

			if(gf.getDomicilio().getAsentamiento() != null) {

				sav.setColonia(gf.getDomicilio().getAsentamiento().getNombre());

				if(gf.getDomicilio().getAsentamiento().getLocalidad() != null
						&& gf.getDomicilio().getAsentamiento().getLocalidad().getMunicipio() != null){

					sav.setMunicipio(gf.getDomicilio().getAsentamiento().getLocalidad().getMunicipio().getNombre());

					if(gf.getDomicilio().getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa() != null){

						sav.setDomicilio(gf.getDomicilio().getAsentamiento().getLocalidad().getMunicipio().getNombre()
											+ " "
											+ gf.getDomicilio().getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre());

					}

				}


			}
		}
		// Calculando UMF a 3 posiciones
		String umf = stringTresPosiciones(gf.getMedicoEnTurno()
				.getUnidadMedicaFamiliar().getNombreCorto(), 3);

		sav.setClinica(umf);

		sav.setConsultorio(gf.getMedicoEnTurno().getConsultorio()
				.getDescripcion());

		if(gf.getDerechohabiente().getFechaNacimiento() != null) {
			sav.setEdad(""+ DateUtils.getEdad(gf.getDerechohabiente()
						.getFechaNacimiento()));
			
			sav.setMesNacimiento(DateUtils.dateFormatCustom(gf.getDerechohabiente()
					.getFechaNacimiento(), "MM"));
		}
		
		
		
		if(gf.getDerechohabiente().getLugarNacimiento() != null) {
			sav.setEntidadFederativaNacimiento(gf.getDerechohabiente()
				.getLugarNacimiento().getNombre());
		}
		
		sav.setNombre((gf.getDerechohabiente().getNombre() == null ? "" : gf
				.getDerechohabiente().getNombre().toUpperCase())
				+ " "
				+ (gf.getDerechohabiente().getPrimerApellido() == null ? ""
						: gf.getDerechohabiente().getPrimerApellido()
								.toUpperCase())
				+ " "
				+ (gf.getDerechohabiente().getSegundoApellido() == null ? ""
						: gf.getDerechohabiente().getSegundoApellido()
								.toUpperCase()));
		log.debug("EL NOMBRE GF ES: " + gf.getDerechohabiente().getNombre());
		log.debug("EL NOMBRE DEL ASEGURADO ES: " + nss.getNombre()+nss.getPrimerApellido()+nss.getSegundoApellido());
		sav.setNombreAsegurado((nss.getNombre() == null ? "" : nss.getNombre()
				.toUpperCase())
				+ " "
				+ (nss.getPrimerApellido() == null ? "" : nss
						.getPrimerApellido().toUpperCase())
				+ " "
				+ (nss.getSegundoApellido() == null ? "" : nss
						.getSegundoApellido().toUpperCase()));

		sav.setCurp(getValue(nss.getCurp()));
		log.debug("EL CURP DEL ASEGURADO ES: "+ nss.getCurp());

		sav.setNss(nss.getNssStr());
		log.debug("EL NSS DEL ASEGURADO ES: "+ nss.getNssStr());

		

		sav.setIdPersona(gf.getDerechohabiente().getIdPersona());
		log.debug("EL NSS DEL ASEGURADO ES: "+ nss.getNssStr());
	
		// consulta de documentos probatorios
		List<DitDocumentoProbatorio> documentos = documentacionTramiteDao.findDitDocumentosProbatorios(idTramite);
		
		String listaDocumentos = "";

		for (DitDocumentoProbatorio documentoProbatorio : documentos) {
			if(documentoProbatorio.getDitDocumentoPorTipo() != null) {
			long cveTipoDoc = documentoProbatorio.getDitDocumentoPorTipo().getDicDocumento().getCveIdDocumento();
			String desTipoDoc = documentoProbatorio.getDitDocumentoPorTipo().getDicDocumento().getDesDocumento().toUpperCase();
			log.debug("cve_descripcion " + cveTipoDoc + ", " + desTipoDoc);
			if (cveTipoDoc == 1) {
				log.debug("EL NSS DEL ASEGURADO ES: "+ nss.getNssStr());

				DitActa ditActa = documentoProbatorio.getDitActa();
				if (ditActa != null && !ditActa.getNumFoja().equals("0") ) {
					log.debug("cve_descripcion " + cveTipoDoc + ", " + desTipoDoc);
			listaDocumentos += "<b>"+desTipoDoc+"</b>";
			listaDocumentos += " - NO. ACTA: " + ditActa.getNumActa()+ "|| NO. FOJA: " + ditActa.getNumFoja()
							+ (ditActa.getNumLibro() == null ? "" : "|| NO. LIBRO: " + ditActa.getNumLibro())
							+ ("|| ESTADO: " + ditActa.getDgCatMunicipio().getDgCatEstado().getNomEnt())
							+ ("|| MUNICIPIO: " + ditActa.getDgCatMunicipio().getNomMun())
							+ "|| FECHA SUCESO: "+ DateUtils.dateFormatCustom(ditActa.getFecSuceso(), "dd/MM/yyyy")
							+ "|| FECHA EXPEDICION: "+ DateUtils.dateFormatCustom(documentoProbatorio.getFecExpedicion(),"dd/MM/yyyy")
							+ (StringUtils.isNotBlank(ditActa.getRefNumTomo()) ? ("|| TOMO: " + ditActa.getRefNumTomo()) : "")
							+ (ditActa.getNumJuzgado() == null ? "": "|| NO. JUZGADO: " + ditActa.getNumJuzgado()) ;
						if(cveTipoDoc == 1) {
						DitNacimiento ditNacimiento = documentoProbatorio.getDitNacimiento();						
						String anioNacimiento = ditNacimiento.getNumAnio() != null ? ""+ditNacimiento.getNumAnio() : "";
					String crip = ditNacimiento.getCveCrip() != null ? ditNacimiento.getCveCrip() : "";
						listaDocumentos +=  "|| A&Ntilde;O: " + anioNacimiento+
								"|| CRIP: " + crip + "; ";
					}
			
			}
			}else if (cveTipoDoc != 1){
			listaDocumentos += "<b>"+desTipoDoc+"</b>";
			// comprobantes de domicilio
			if (cveTipoDoc == 7 || cveTipoDoc == 8 || cveTipoDoc == 9
					|| cveTipoDoc == 10 || cveTipoDoc == 11 || cveTipoDoc == 12
					|| cveTipoDoc == 13 || cveTipoDoc == 14 || cveTipoDoc == 15 || cveTipoDoc == 16
					|| cveTipoDoc == 17) {
				
				listaDocumentos += " - NO. FOLIO: "+ documentoProbatorio.getDitComprobanteDomicilio().getRefFolio()
						+ "|| FECHA EXPEDICION: "+ DateUtils.dateFormatCustom(documentoProbatorio.getFecExpedicion(),"dd/MM/yyyy") + "; ";
			}
			// ADIMSS
			else if (cveTipoDoc == 46) {
				listaDocumentos += " - NO. FOLIO: "
						+ documentoProbatorio.getDitAdimss().getRefFolio()
						+ "|| FECHA EXPEDICION: "+ DateUtils.dateFormatCustom(documentoProbatorio.getFecExpedicion(),"dd/MM/yyyy") + "; ";
			}
			// Matricula Consular 
			else if (cveTipoDoc == 130) {
				DitMatriculaConsular ditMatriculaConsular = documentoProbatorio.getDitMatriculaConsular();
				listaDocumentos += " - NO. DE DOCUMENTO: "+ ditMatriculaConsular.getNumeroDoc()+ 
						"|| AUTORIDAD QUE EMITE LA MATRICULA: " + ditMatriculaConsular.getAutoridadEmiteMat()+ 
						"|| FECHA EXPEDICION: "+ DateUtils.dateFormatCustom(ditMatriculaConsular.getFecExpedicion(),"dd/MM/yyyy") +
						"|| FECHA VENCIMIENTO: "+ DateUtils.dateFormatCustom(ditMatriculaConsular.getFecVencimiento(),"dd/MM/yyyy") + 
						"|| CALIDAD MIGRATORIA: "+ ditMatriculaConsular.getCalidadMigratoria()+ "; ";
			}
			// Forma Migratoria (FM2 y FM3)
			else if (cveTipoDoc == 208 || cveTipoDoc == 209) {
				DitFormaMigratoria ditFormaMigratoria = documentoProbatorio.getDitFormaMigratoria();
				listaDocumentos += " - NO. DE DOCUMENTO: "+ ditFormaMigratoria.getNumeroDoc()+ 
						"|| PAIS DE ORIGEN: " + ditFormaMigratoria.getDicPai().getDesPais()+ 
						"|| FECHA EXPEDICION: "+ DateUtils.dateFormatCustom(ditFormaMigratoria.getFecExpedicion(),"dd/MM/yyyy") +
						"|| FECHA VENCIMIENTO: "+ DateUtils.dateFormatCustom(ditFormaMigratoria.getFecVencimiento(),"dd/MM/yyyy") + 
						"|| CALIDAD MIGRATORIA: "+ ditFormaMigratoria.getDicCalidadCaracMigrat().getDesCalidadMigratoria()+ "; ";
			}
			// Cedula profesional
			else if (cveTipoDoc == 35) {
				DitCedulaProfesional ditCedula = documentoProbatorio.getDitCedulaProfesional();
				listaDocumentos += " - NO. DE CEDULA: "+ ditCedula.getNumCedula()+ ", PROFESION: "+ ditCedula.getNomProfesion()
						+ "|| FECHA EXPEDICION: "+ DateUtils.dateFormatCustom(documentoProbatorio.getFecExpedicion(),"dd/MM/yyyy") + "; ";
			}
			// Credencial de elector
			else if (cveTipoDoc == 36) {
				DitCredElector ditCredElector = documentoProbatorio.getDitCredElector();
				if(ditCredElector.getRefFolio() != null) {
					listaDocumentos += " - FOLIO: " + documentoProbatorio.getDitCredElector().getRefFolio();
				}
				listaDocumentos +=  " - A&Ntilde;O DE EXPEDICI&Oacute;N: " + ditCredElector.getNumAnioRegistro();
				String claveElector = ditCredElector.getCveElector();
				if(claveElector != null) {
					listaDocumentos +=  "|| CLAVE: "+ claveElector;
				}
				BigDecimal numEmision = ditCredElector.getNumEmision();
				if(numEmision != null) {
					listaDocumentos += "|| A&Ntilde;O DE EMISI&Oacute;N: " + ditCredElector.getNumEmision();
				}
				String tipoCredencia = ditCredElector.getRefModeloCredencial();
				String cadenaCodigo = "|| NUMERO (OCR) : ";
				if(tipoCredencia != null && tipoCredencia.equals("D") || tipoCredencia.equals("E")) {
					cadenaCodigo = "|| C&Oacute;DIGO DE IDENTIFICACI&Oacute;N DE CREDENCIAL : ";
				}
				listaDocumentos += cadenaCodigo+ ditCredElector.getRefCodigoSeguridad() + "; ";
			}
			// Cartilla militar
			else if (cveTipoDoc == 37) {
				listaDocumentos += " - MATRICULA: "
						+ documentoProbatorio.getDitCartillaMilitar()
								.getNumMatricula()
						+ " || FECHA EXPEDICION: "
						+ DateUtils.dateFormatCustom(
								documentoProbatorio.getFecExpedicion(),
								"dd/MM/yyyy") + "; ";
			}
			// pasaporte
			else if (cveTipoDoc == 38) {
				DitPasaporte ditPasaporte = documentoProbatorio.getDitPasaporte();
				listaDocumentos += " - NUMERO DE PASAPORTE: "+ ditPasaporte.getNumPasaporte()
						+ "|| FECHA EXPEDICION: "+ DateUtils.dateFormatCustom(documentoProbatorio.getFecExpedicion(),"dd/MM/yyyy")
						+ "|| FECHA CADUCIDAD: "
						+ DateUtils.dateFormatCustom(ditPasaporte.getFecCaducidad(),"dd/MM/yyyy") + "; ";
			}
			//acta union civil
			else if (cveTipoDoc == 210) {
				DitActaUnionCivil ditActaUnionCivil = documentoProbatorio.getDitActaUnionCivil();
				listaDocumentos += " - LUGAR DE EMISI&Oacute;N (ENTIDAD FEDERATIVA): "+ ditActaUnionCivil.getLugarEmision()+ 
						"|| FECHA DE EMISI&Oacute;N: "+ DateUtils.dateFormatCustom(ditActaUnionCivil.getFecEmision(),"dd/MM/yyyy") +
						"|| AUTORIDAD QUE EMITE EL DOCUMENTO: "+ ditActaUnionCivil.getDicAutoridadEmisora().getDesAutoridad()+  
						"|| ENTIDAD FEDERATIVA: "+ ditActaUnionCivil.getDgCatEstado().getNomEnt()+ 
						"|| N&Uacute;MERO DE REFERENCIA / FOLIO / N&Uacute;MERO DE CONTROL : "+ ditActaUnionCivil.getNoReferencia()+ "; ";
			}
			//acta termino union civil
			else if (cveTipoDoc == 211) {
				DitActaTerminoUnionCivil ditActaTerminoUnionCivil = documentoProbatorio.getDitActaTerminoUnionCivil();
				listaDocumentos += " - LUGAR DE EMISI&Oacute;N (ENTIDAD FEDERATIVA): "+ ditActaTerminoUnionCivil.getLugarEmision()+ 
						"|| FECHA DE EMISI&Oacute;N: "+ DateUtils.dateFormatCustom(ditActaTerminoUnionCivil.getFecEmision(),"dd/MM/yyyy") +
						"|| AUTORIDAD QUE EMITE EL DOCUMENTO: "+ ditActaTerminoUnionCivil.getDicAutoridadEmisora().getDesAutoridad()+  
						"|| ENTIDAD FEDERATIVA: "+ ditActaTerminoUnionCivil.getDgCatEstado().getNomEnt()+ 
						"|| N&Uacute;MERO DE REFERENCIA / FOLIO / N&Uacute;MERO DE CONTROL : "+ ditActaTerminoUnionCivil.getNoReferencia()+ "; ";
			}
			// Acta de reconocimiento
			else if (cveTipoDoc == 2 || cveTipoDoc == 3
					|| cveTipoDoc == 4 || cveTipoDoc == 5 || cveTipoDoc == 40
							|| cveTipoDoc == 41) {
				DitActa ditActa = documentoProbatorio.getDitActa();
				if (ditActa != null) {
					listaDocumentos += " - NO. ACTA: " + ditActa.getNumActa()+ "|| NO. FOJA: " + ditActa.getNumFoja()
							+ (ditActa.getNumLibro() == null ? "" : "|| NO. LIBRO: " + ditActa.getNumLibro())
							+ ("|| ESTADO: " + ditActa.getDgCatMunicipio().getDgCatEstado().getNomEnt())
							+ ("|| MUNICIPIO: " + ditActa.getDgCatMunicipio().getNomMun())
							+ "|| FECHA SUCESO: "+ DateUtils.dateFormatCustom(ditActa.getFecSuceso(), "dd/MM/yyyy")
							+ "|| FECHA EXPEDICION: "+ DateUtils.dateFormatCustom(documentoProbatorio.getFecExpedicion(),"dd/MM/yyyy")
							+ (StringUtils.isNotBlank(ditActa.getRefNumTomo()) ? ("|| TOMO: " + ditActa.getRefNumTomo()) : "")
							+ (ditActa.getNumJuzgado() == null ? "": "|| NO. JUZGADO: " + ditActa.getNumJuzgado());
//					if(cveTipoDoc == 1) {
//						DitNacimiento ditNacimiento = documentoProbatorio.getDitNacimiento();
//						String anioNacimiento = ditNacimiento.getNumAnio() != null ? ""+ditNacimiento.getNumAnio() : "";
//					String crip = ditNacimiento.getCveCrip() != null ? ditNacimiento.getCveCrip() : "";
//						listaDocumentos +=  "|| A&Ntilde;O: " + anioNacimiento+
//								"|| CRIP: " + crip;
//					}
				}
				listaDocumentos += "; ";
			//Acta pacto solidaridad civil	
			} else if (cveTipoDoc == 65) {
				DitActa ditActa = documentoProbatorio.getDitActa();
				if (ditActa != null) {
					listaDocumentos += " - NO. ACTA: " + ditActa.getNumActa()+ "|| NO. FOJA: " + ditActa.getNumFoja()
							+ (ditActa.getNumLibro() == null ? "" : "|| NO. LIBRO: " + ditActa.getNumLibro())
							+ "|| FECHA SUCESO: "+ DateUtils.dateFormatCustom(ditActa.getFecSuceso(), "dd/MM/yyyy")
							+ (StringUtils.isNotBlank(ditActa.getRefNumTomo()) ? ("|| TOMO: " + ditActa.getRefNumTomo()) : "");
				}
				listaDocumentos += "; ";
			}else if(cveTipoDoc == 24) {
				if (documentoProbatorio.getDitCertificadoNacimiento() != null) {
					DitCertificadoNacimiento certificado = documentoProbatorio.getDitCertificadoNacimiento();
					listaDocumentos += "- FOLIO: " + certificado.getRefFolio() + 
					"|| FECHA ALUMBRAMIENTO: " + DateUtils.dateFormatCustom(certificado.getFecAlumbramiento(),"dd/MM/yyyy") + 
					"|| SEXO: " + certificado.getDicSexo().getDesSexo() + 
					"|| LUGAR DE ALUMBRAMIENTO: " + certificado.getDesLugarAlumbramiento() +
					"|| FECHA DE EXPEDICI&Oacute;N: " + DateUtils.dateFormatCustom(documentoProbatorio.getFecExpedicion(),"dd/MM/yyyy") + "; "; 
				}
			} else {
				listaDocumentos +=  "<br>";
			}
			}
			}
		}

		sav.setDocumentos(listaDocumentos);
		
		
		// -----------------------------------------------------
		// Beneficiarios afectados por el tramite
		// -----------------------------------------------------
			try{
				sav.addBeneficiario( obtenerBeneficiarioSav002(gf,registro) );
			}catch(DerechohabientesBusinessException e){
				e.printStackTrace();
				// -----------------------------------------------------
				// Beneficiario en baja
				// -----------------------------------------------------
				log.debug("El beneficiario idAsignacionNSS:" +gf.getAsignacionNSS().getIdAsignacionNSS() + ", idPersona: " + gf.getDerechohabiente().getIdPersona()+" esta en baja,"
						+ " por lo tanto no se agrega al reporte",e);
			}
			
			log.debug("El xml del tramite es: " + tramite.getDetalleTramiteXml());
			
			if( tramite.getDetalleTramiteXml() != null ){
				
				Tramite unTramite = (Tramite) JaxbUtil.xmlToObject(tramite.getDetalleTramiteXml());
				if( unTramite instanceof TramiteCorreccionDerechohabiente ){
					Long idPersona = gf.getDerechohabiente().getIdPersona();
					TramiteCorreccionDerechohabiente correccion = (TramiteCorreccionDerechohabiente)unTramite;
					if( correccion.getCandidatosCambioClinica() != null ){
						
						
						
						for(Long idCandidato : correccion.getCandidatosCambioClinica()){
							
							if( !idCandidato.equals(idPersona) ){
								
								try{
									sav.addBeneficiario( obtenerBeneficiarioSav002(grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(nss.getIdAsignacionNSS(),idCandidato),registro));
								}catch(Exception e){
									// ------------------------------------------------------
									// Beneficiario en baja
									// Error al consultar al integrante del grupo familiar
									// ------------------------------------------------------
									log.debug(e);
								}
								
							}
							
						}
						
					} else if(correccion.getIdPersona() != null && !correccion.getIdPersona().equals(idPersona)) {
						try{
							sav.addBeneficiario( obtenerBeneficiarioSav002(grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(nss.getIdAsignacionNSS(),correccion.getIdPersona()),registro));
						}catch(Exception e){
							// ------------------------------------------------------
							// Beneficiario en baja
							// Error al consultar al integrante del grupo familiar
							// ------------------------------------------------------
							log.debug(e);
						}
					}
					
					
				}
				
				
			}
			
			if(sav.getBeneficiarios() == null || sav.getBeneficiarios().isEmpty()) {
				log.debug("No existen beneficiarios activos para generar el sav002, por lo tanto el documento no se genera");
				return null;
			}
		
		return sav;

	}

	
	private BeneficiarioSav002DTO obtenerBeneficiarioSav002(GrupoFamiliar candidato, Boolean registro) throws DerechohabientesBusinessException{
		
		//Se modifica el método para que pueda obtener los beneficiarios aún cuando esté en baja
		//ya no valida si se trata de un trámite de registro
		
		try{
			
			Derechohabiente derehohabiente = candidato.getDerechohabiente(); 
			BeneficiarioSav002DTO beneficiario = new BeneficiarioSav002DTO(); 
				
			beneficiario.setAgregadoMedico(candidato.getAgregadoAfiliacion());
			
			if(derehohabiente.getFechaNacimiento() != null) {
				beneficiario.setMesNacimiento(DateUtils.dateFormatCustom(derehohabiente.getFechaNacimiento(), "MM"));
				}
			
			beneficiario.setNombre((derehohabiente.getNombre() == null ? "" : derehohabiente.getNombre().toUpperCase())
					+ " " + (derehohabiente.getPrimerApellido() == null ? "" : derehohabiente.getPrimerApellido().toUpperCase())
					+ " " + (derehohabiente.getSegundoApellido() == null ? "" : derehohabiente.getSegundoApellido().toUpperCase()));
			
			return beneficiario;

		}catch(Exception e){
			e.printStackTrace();
			log.debug(e);
		}

		// ---------------------------------------------
		// Si esta en baja o se lanzo un excepcion
		// ---------------------------------------------
		throw new DerechohabientesBusinessException(); 
	}
	
	
	@SuppressWarnings("unused")
	private boolean cambiosPersonales(GrupoFamiliar integrante,
			TramiteCorreccionDerechohabiente correccion) {

		if (!integrante.getDerechohabiente().getNombre()
				.equals(correccion.getNombre()))
			return true;
		if (!integrante.getDerechohabiente().getPrimerApellido()
				.equals(correccion.getPrimerApellido()))
			return true;
		if (!integrante.getDerechohabiente().getSegundoApellido()
				.equals(correccion.getSegundoApellido()))
			return true;
		if (!integrante.getDerechohabiente().getSexo().getIdSexo()
				.equals(correccion.getSexo().getIdSexo()))
			return true;
		if (!integrante.getDerechohabiente().getEstadoCivil()
				.getIdEstadoCivil()
				.equals(correccion.getEstadoCivil().getIdEstadoCivil()))
			return true;
		if (integrante.getDerechohabiente().getCurp() != null) {
			if (correccion.getCurpCap() != null) {
				if (!integrante.getDerechohabiente().getCurp()
						.equals(correccion.getCurpCap()))
					return true;
			} else {
				return true;
			}
		} else {
			if (correccion.getCurpCap() != null) {
				return true;
			}
		}

		if (!integrante.getDerechohabiente().getLugarNacimiento().getClave()
				.equals(correccion.getLugarNacimiento().getClave()))
			return true;
		if (!integrante.getParentesco().getIdParentesco()
				.equals(correccion.getParentesco().getIdParentesco()))
			return true;
		if (!integrante.getDerechohabiente().getFechaNacimiento()
				.equals(correccion.getFechaNacimiento()))
			return true;
		if (!integrante.getDomicilio().getClave()
				.equals(correccion.getDomicilio().getClave()))
			return true;

		return false;
	}

	@Override
	public Object getDocumentosProrroga(AsignacionNSS nss,
			FirmaElectronica firmaElectronica, Long idTramite,
			Long idDerechohabiente) throws DerechohabientesBusinessException,
			Exception {

		Map<String, String> plantillas = new HashMap<String, String>();

		List<CartillaSaludDTO> datosCartilla = new ArrayList<CartillaSaludDTO>();
		List<Sav007DTO> datosSav07 = new ArrayList<Sav007DTO>();
		List<DocumentosProrrogaDTO> datosProrroga = new ArrayList<DocumentosProrrogaDTO>();

		DocumentosProrrogaDTO prorrogaDto = new DocumentosProrrogaDTO();
		ByteArrayOutputStream repo = null;

		// SAV007
		List<Long> tiposTramite = new ArrayList<Long>();
		tiposTramite.add(TipoTramiteEnum.PRORROGA_ESTUDIOS.getCodigo()
				.longValue());
		tiposTramite.add(TipoTramiteEnum.PRORROGA_ENFERMEDAD.getCodigo()
				.longValue());
		tiposTramite.add(TipoTramiteEnum.PRORROGA_OBSTETRICOS.getCodigo()
				.longValue());
		tiposTramite.add(TipoTramiteEnum.PRORROGA_ACUERDOS.getCodigo()
				.longValue());
		tiposTramite.add(TipoTramiteEnum.PRORROGA_VIGENCIA_PERMANENTE
				.getCodigo().longValue());
		tiposTramite.add(TipoTramiteEnum.PRORROGA_VIGENCIA_TEMPORAL.getCodigo()
				.longValue());
		tiposTramite.add(TipoTramiteEnum.PRORROGA_LAUDOS.getCodigo()
				.longValue());

		Sav007DTO sav007 = tramiteDaoLocal.getUltimoTramiteSAV007(nss,
				idDerechohabiente, tiposTramite);
		// Agregar datos de la firma electronica
		if (firmaElectronica != null) {
			sav007.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
			sav007.setSelloDigital(firmaElectronica.getRecibo());
			sav007.setSecuenciaNotarial(firmaElectronica.getSecuenciaNotaria());
			sav007.setNumeroSerie(firmaElectronica.getSerialCertificado());
		}

		GrupoFamiliar integrante = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(
				nss.getIdAsignacionNSS(), idDerechohabiente);

		if (sav007 != null) {

			if (integrante != null) {
				String lugar = integrante.getDomicilio().getAsentamiento()
						.getLocalidad().getMunicipio().getNombre()
						+ ", "
						+ integrante.getDomicilio().getAsentamiento()
								.getLocalidad().getMunicipio()
								.getEntidadFederativa().getNombre();
				sav007.setLugar(lugar);
			} else {
				sav007.setLugar("");
			}
		}
		datosSav07.add(sav007);

		// Cartilla
		datosCartilla = getDatosCartillaSalud(idDerechohabiente, nss,
				firmaElectronica);

		// Llenar objetos para el reporte
		prorrogaDto.setDatosCartilla(datosCartilla);
		prorrogaDto.setDatosSav007(datosSav07);
		datosProrroga.add(prorrogaDto);

		// Agregar parametros

		plantillas.put("SAV007.jasper", "SUBREPORTE_SAV007");
		plantillas.put("cartillaSalud.jasper", "SUBREPORTE_CARTILLA");

		Locale locale = new Locale("es", "ES");
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put(JRParameter.REPORT_LOCALE, locale);
		parametros.put("logo",
				new ClassPathResource("reportes/img/imss.jpg").getPath());

		// Llenar el reporte
		try {
			repo = manejadorReportes.ejecutaReporteSubreporte(parametros,
					datosProrroga, "DocumentosProrroga.jasper", plantillas);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return repo.toByteArray();

	}

	public Object getDocumentoSav011(AsignacionNSS nss,
			FirmaElectronica firmaElectronica, Usuario usuario,
			Long idBeneficiario) throws DerechohabientesBusinessException,
			Exception {
		GrupoFamiliar asegurado = null;
		GrupoFamiliar beneficiario = null;
		String calidad = "";
		// String enfermedad = "";
		List<AseguradoDTO> datos = new ArrayList<AseguradoDTO>();
		// Map<String, String> plantillas = new HashMap<String, String>();

		asegurado = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(nss.getIdAsignacionNSS(),
				nss.getIdPersona());

		AseguradoDTO aseguradoDTO = new AseguradoDTO();
		aseguradoDTO.setCurp(asegurado.getDerechohabiente().getCurp());
		aseguradoDTO.setNss(asegurado.getAsignacionNSS().getNssStr());
		aseguradoDTO.setNombreAsegurado(asegurado.getDerechohabiente()
				.getNombre()
				+ " "
				+ asegurado.getDerechohabiente().getPrimerApellido()
				+ " "
				+ asegurado.getDerechohabiente().getSegundoApellido());
		aseguradoDTO.setFechaHoy(DateUtils.dateFormatCustom(new Date(),
				"dd MMMMM yyyy").toUpperCase());
		
		String domicilio = "";
		if(asegurado != null && asegurado.getDomicilio() != null && asegurado.getDomicilio().getAsentamiento() != null && asegurado.getDomicilio().getAsentamiento().getLocalidad() != null
				&& asegurado.getDomicilio().getAsentamiento().getLocalidad().getMunicipio() != null ){
			if(asegurado.getDomicilio().getAsentamiento().getLocalidad().getMunicipio().getNombre() != null){
				domicilio = domicilio + asegurado.getDomicilio().getAsentamiento().getLocalidad().getMunicipio().getNombre();
			}
			domicilio = domicilio + " ";
			if(asegurado.getDomicilio().getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa() != null && asegurado.getDomicilio().getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre()!= null){
				domicilio = domicilio + asegurado.getDomicilio().getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre();
			}
		}
		
		aseguradoDTO.setLugar(domicilio);

		if (idBeneficiario != null) {

			beneficiario = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(
					nss.getIdAsignacionNSS(), idBeneficiario);

			aseguradoDTO.setApellidoMaterno(beneficiario.getDerechohabiente()
					.getSegundoApellido() != null ? beneficiario
					.getDerechohabiente().getSegundoApellido() : "");
			aseguradoDTO.setApellidoPaterno(beneficiario.getDerechohabiente()
					.getPrimerApellido());

			// Definir el parentesco
			if (beneficiario.getParentesco().getIdParentesco()
					.equals(ParentescoEnum.ASEGURADO.getId())) {
				calidad = "asegurado";
			} else if (beneficiario.getParentesco().getIdParentesco()
					.equals(ParentescoEnum.CONCUBINARIO.getId())) {
				calidad = "concubina";
			} else if (beneficiario.getParentesco().getIdParentesco()
					.equals(ParentescoEnum.CONYUGE.getId())) {
				calidad = "esposa";
			} else if (beneficiario.getParentesco().getIdParentesco()
					.equals(ParentescoEnum.PADRES.getId())) {
				calidad = "padre";
			} else if (beneficiario.getParentesco().getIdParentesco()
					.equals(ParentescoEnum.HIJOS.getId())) {
				calidad = "hijo";
			}else if (beneficiario.getParentesco().getIdParentesco()
					.equals(ParentescoEnum.PERSONA_EN_UNION_CIVIL.getId())) {
				calidad = "union civil";
			}
			aseguradoDTO.setCalidad(calidad);

			// Definir la enfermedad
			aseguradoDTO.setEnfermedad("obstetico");

			aseguradoDTO.setNombreBeneficiario(beneficiario
					.getDerechohabiente().getNombre());
		}

		// Agregar datos de la firma electronica
		if (firmaElectronica != null) {
			aseguradoDTO
					.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
			aseguradoDTO.setSelloDigital(firmaElectronica.getRecibo());
			aseguradoDTO.setSecuenciaNotarial(firmaElectronica
					.getSecuenciaNotaria());
			aseguradoDTO
					.setNumeroSerie(firmaElectronica.getSerialCertificado());
		}

		datos.add(aseguradoDTO);

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("LOGO",
				new ClassPathResource("reportes/img/imss.jpg").getPath());
		parametros.put("UMF", usuario.getUsuarioFuncionario()
				.getUnidadMedicaFamiliar().getNombreCorto());

		Locale locale = new Locale("es", "ES");
		parametros.put(JRParameter.REPORT_LOCALE, locale);

		ByteArrayOutputStream repo = null;
		try {
			repo = manejadorReportes.ejecutaReporteCompilado(parametros, datos,
					"SAV011.jasper");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return repo.toByteArray();
	}

	@Override
	public Object getDocumentosCambioDatos(AsignacionNSS nss,
			FirmaElectronica firmaElectronica, Long idTramite, String titulo,
			Usuario usuario) throws DerechohabientesBusinessException,
			Exception {

		Map<String, Object> parametros = new HashMap<String, Object>();
		Map<String, String> plantillas = new HashMap<String, String>();
		List<Reporte4305A> datos4305A = new ArrayList<Reporte4305A>();
		List<DocumentosRegistroDTO> datos = new ArrayList<DocumentosRegistroDTO>();
		List<CartillaSaludDTO> datosCartilla = new ArrayList<CartillaSaludDTO>();
		DocumentosRegistroDTO dato = new DocumentosRegistroDTO();
		ByteArrayOutputStream repo = null;
		String clave = "";
		CabezaGrupoFamiliar patron = null;
		Sav002DTO sav = null;
		patron = grupoFamiliarDaoLocal.getCabezaGrupoFamiliar(nss
				.getIdAsignacionNSS());
		clave = patron.getPatronSujetoObligado().getModalidad()
				.getNumModalidad();

		sav = getDatosSav002(nss, idTramite,true, patron);

		// Se agrega informacion del sellado al objeto Sav002DTO
		if (firmaElectronica != null) {
			sav.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
			sav.setSelloDigital(firmaElectronica.getRecibo());
			sav.setSecuenciaNotarial(firmaElectronica.getSecuenciaNotaria());
			sav.setNumeroSerie(firmaElectronica.getSerialCertificado());
		}

		
		if (sav != null)
			sav.setModalidad(clave);

		datosCartilla = getDatosCartillaSalud(sav.getIdPersona(), nss,
				firmaElectronica);

		datos4305A = getDatosReporte4305A(nss, firmaElectronica, usuario, OrigenSolicitudEnum.VENTANILLA.getId());
		dato.setDatos4305A(datos4305A);
		if (sav != null) {
			dato.setDatosSav002(new ArrayList<Sav002DTO>());
			dato.getDatosSav002().add(sav);
		}
		dato.setDatosCartilla(datosCartilla);

		datos.add(dato);

		plantillas.put("DetalleBeneficiarios.jasper",
				"SUBREPORTE_BENEFICIARIOS");
		plantillas.put("4305A.jasper", "SUBREPORTE_4305A");
		if (sav != null)
			plantillas.put("SAV002.jasper", "SUBREPORTE_SAV002");
		plantillas.put("cartillaSalud.jasper", "SUBREPORTE_CARTILLA");

		Locale locale = new Locale("es", "ES");
		parametros.put(JRParameter.REPORT_LOCALE, locale);
		parametros.put("clave", clave);
		parametros.put("lugar", sav.getDomicilio());
		parametros.put("fecha",
				DateUtils.dateFormatCustom(new Date(), "dd MMMMM yyyy")
						.toUpperCase());
		try {
			repo = manejadorReportes.ejecutaReporteSubreporte(parametros,
					datos, "DocumentosRegistroDerechohabiente.jasper",
					plantillas);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return repo.toByteArray();

	}

	@Override
	public Object getDocumentosCambioClinica(AsignacionNSS nss,
			FirmaElectronica firmaElectronica, Long idTramite, Usuario usuario,
			String titulo) throws DerechohabientesBusinessException, Exception {
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		Map<String, String> plantillas = new HashMap<String, String>();
		List<Reporte4305A> datos4305A = new ArrayList<Reporte4305A>();
		List<DocumentosCambioClinicaDTO> datos = new ArrayList<DocumentosCambioClinicaDTO>();
		List<CartillaSaludDTO> datosCartilla = new ArrayList<CartillaSaludDTO>();
		DocumentosCambioClinicaDTO dato = new DocumentosCambioClinicaDTO();
		ByteArrayOutputStream repo = null;
		String clave = "";
		CabezaGrupoFamiliar patron = null;
		Sav002DTO sav = null;
		List<Sav005DTO> sav05List = null;
		Sav006DTO sav006 = new Sav006DTO();
		boolean aseguradoAfectado = false;
		
		patron = grupoFamiliarDaoLocal.getCabezaGrupoFamiliar(nss
				.getIdAsignacionNSS());
		clave = patron.getPatronSujetoObligado().getModalidad()
				.getNumModalidad();

		sav = getDatosSav002(nss, idTramite,true, patron);

		// Se agrega informacion del sellado al objeto Sav002DTO
		if (firmaElectronica != null) {
			sav.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
			sav.setSelloDigital(firmaElectronica.getRecibo());
			sav.setSecuenciaNotarial(firmaElectronica.getSecuenciaNotaria());
			sav.setNumeroSerie(firmaElectronica.getSerialCertificado());
		}

		

		sav.setModalidad(patron.getPatronSujetoObligado().getModalidad()
				.getNumModalidad());

		datosCartilla = getDatosCartillaSalud(sav.getIdPersona(), nss,
				firmaElectronica);

		sav05List = getDatosSav005(nss, firmaElectronica, sav.getIdPersona(),
				EstadoTramiteEnum.CERRADO.getId(), OrigenSolicitudEnum.VENTANILLA.getId(), usuario);
		sav05List.get(0).setEmpleado(
				(usuario.getNomNombre() == null ? "" : usuario.getNomNombre())
						+ " "
						+ (usuario.getNomPaterno() == null ? "" : usuario
								.getNomPaterno())
						+ " "
						+ (usuario.getNomMaterno() == null ? "" : usuario
								.getNomMaterno()));

		datos4305A = getDatosReporte4305A(nss, firmaElectronica, usuario, OrigenSolicitudEnum.VENTANILLA.getId());

		dato.setDatos4305A(datos4305A);
		dato.setDatosSav002(new ArrayList<Sav002DTO>());
		dato.getDatosSav002().add(sav);

		// ////////////
		TramiteCorreccionDerechohabiente correccion = tramiteServiceLocal
				.getCorreccion(idTramite);
		if (correccion.getCandidatosCambioClinica() != null) {
			datosCartilla = new ArrayList<CartillaSaludDTO>();
			for (Long integrante : correccion.getCandidatosCambioClinica()) {
				List<CartillaSaludDTO> cartilla = getDatosCartillaSalud(
						integrante, nss, firmaElectronica);
				if (cartilla != null)
					datosCartilla.add(cartilla.get(0));

				if (integrante.equals(nss.getIdPersona())) {
					aseguradoAfectado = true;
				}
			}
		} else {
			if (correccion.getIdPersona().equals(nss.getIdPersona()))
				aseguradoAfectado = true;
		}
		// ///////////
		if (aseguradoAfectado) {
			sav05List.get(0).setCambioParcial(false);
		} else {
			sav05List.get(0).setCambioParcial(true);
		}

		dato.setDatosSav005(sav05List);
		dato.setDatosCartilla(datosCartilla);

		// Documento sav006
		sav006.setAsegurado(sav.getNombreAsegurado());
		sav006.setBeneficiario(sav.getNombre());
		sav006.setNss(nss.getNssStr());
		sav006.setCurp(nss.getCurp());
		sav006.setEmpleado(usuario.getNomNombre() + " "
				+ usuario.getNomPaterno() + " " + usuario.getNomMaterno());
		sav006.setCurpBeneficiario(sav.getCurp());
		sav006.setcSolicitante(sav.getClinica());
		sav006.setUmf(sav.getUmf());
		sav006.setCambioClinica("X");
		sav006.setLugar(sav.getDomicilio());
		sav006.setFecha(DateUtils.dateFormat(new Date()));
		sav006.setNuevoDomicilio(sav.getDomicilio());
		dato.setDatosSav006(new ArrayList<Sav006DTO>());
		dato.getDatosSav006().add(sav006);

		datos.add(dato);
		// solo falta el sav005
		plantillas.put("SAV005.jasper", "SUBREPORTE_SAV005");
		plantillas.put("DetalleBeneficiarios.jasper",
				"SUBREPORTE_BENEFICIARIOS");
		plantillas.put("4305A.jasper", "SUBREPORTE_4305A");
		plantillas.put("SAV002.jasper", "SUBREPORTE_SAV002");
		plantillas.put("SAV006.jasper", "SUBREPORTE_SAV006");
		plantillas.put("cartillaSalud.jasper", "SUBREPORTE_CARTILLA");

		Locale locale = new Locale("es", "ES");
		parametros.put(JRParameter.REPORT_LOCALE, locale);
		parametros.put("clave", clave);
		parametros.put("lugar", sav.getDomicilio());
		parametros.put("fecha",
				DateUtils.dateFormatCustom(new Date(), "dd MMMMM yyyy")
						.toUpperCase());
		parametros.put("LOGO",
				new ClassPathResource("reportes/img/imss.jpg").getPath());
		parametros.put("logo",
				new ClassPathResource("reportes/img/imss.jpg").getPath());

		try {
			repo = manejadorReportes.ejecutaReporteSubreporte(parametros,
					datos, "DocumentosCambioDatos.jasper", plantillas);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return repo.toByteArray();

	}

	@Override
	public Object getDocumentosCambioConsultorio(AsignacionNSS nss,
			FirmaElectronica firmaElectronica, Long idTramite, Usuario usuario,
			String titulo) throws DerechohabientesBusinessException, Exception {
		Map<String, Object> parametros = new HashMap<String, Object>();
		Map<String, String> plantillas = new HashMap<String, String>();
		List<Reporte4305A> datos4305A = new ArrayList<Reporte4305A>();
		List<DocumentosRegistroDTO> datos = new ArrayList<DocumentosRegistroDTO>();
		List<CartillaSaludDTO> datosCartilla = new ArrayList<CartillaSaludDTO>();

		DocumentosRegistroDTO dato = new DocumentosRegistroDTO();
		ByteArrayOutputStream repo = null;
		String clave = "";
		CabezaGrupoFamiliar patron = null;
		Sav002DTO sav = null;
		
		patron = grupoFamiliarDaoLocal.getCabezaGrupoFamiliar(nss
				.getIdAsignacionNSS());
		clave = patron.getPatronSujetoObligado().getModalidad()
				.getNumModalidad();

		sav = getDatosSav002(nss, idTramite,true, patron);

		// Se agrega informacion del sellado al objeto Sav002DTO
		if (firmaElectronica != null) {
			sav.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
			sav.setSelloDigital(firmaElectronica.getRecibo());
			sav.setSecuenciaNotarial(firmaElectronica.getSecuenciaNotaria());
			sav.setNumeroSerie(firmaElectronica.getSerialCertificado());
		}

		
		sav.setModalidad(patron.getPatronSujetoObligado().getModalidad()
				.getNumModalidad());

		datosCartilla = getDatosCartillaSalud(sav.getIdPersona(), nss,
				firmaElectronica);

		datos4305A = getDatosReporte4305A(nss, firmaElectronica, usuario, OrigenSolicitudEnum.VENTANILLA.getId());
		dato.setDatos4305A(datos4305A);
		dato.setDatosCartilla(datosCartilla);

		datos.add(dato);

		plantillas.put("DetalleBeneficiarios.jasper",
				"SUBREPORTE_BENEFICIARIOS");
		plantillas.put("4305A.jasper", "SUBREPORTE_4305A");
		plantillas.put("cartillaSalud.jasper", "SUBREPORTE_CARTILLA");
		parametros.put("clave", clave);
		parametros.put("lugar", sav.getDomicilio());
		parametros.put("fecha",
				DateUtils.dateFormatCustom(new Date(), "dd MMMMM yyyy")
						.toUpperCase());

		try {
			repo = manejadorReportes.ejecutaReporteSubreporte(parametros,
					datos, "DocumentosRegistroDerechohabiente.jasper",
					plantillas);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return repo.toByteArray();

	}

	@Override
	public Object getDocumentosCircunscripcion(AsignacionNSS nss,
			FirmaElectronica firmaElectronica, Long idTramite,
			boolean autorizacion, String titulo, Usuario usuario)
			throws DerechohabientesBusinessException, Exception {

		Map<String, Object> parametros = new HashMap<String, Object>();
		Map<String, String> plantillas = new HashMap<String, String>();
		List<DocumentacionCircunscripcionDTO> datos = new ArrayList<DocumentacionCircunscripcionDTO>();
		List<CartillaSaludDTO> datosCartilla = new ArrayList<CartillaSaludDTO>();
		DocumentacionCircunscripcionDTO dato = new DocumentacionCircunscripcionDTO();
		List<Sav017DTO> sav017 = null;
		ByteArrayOutputStream repo = null;
		String clave = "";
		CabezaGrupoFamiliar patron = null;
		Sav002DTO sav = null;
		Sav006DTO sav006 = new Sav006DTO();
		List<Reporte4305A> datos4305A = new ArrayList<Reporte4305A>();

		patron = grupoFamiliarDaoLocal.getCabezaGrupoFamiliar(nss
				.getIdAsignacionNSS());
		clave = patron.getPatronSujetoObligado().getModalidad()
				.getNumModalidad();
		
		sav = getDatosSav002(nss, idTramite,true, patron);
		// Se agrega informacion del sellado al objeto Sav002DTO
		if (firmaElectronica != null) {
			sav.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
			sav.setSelloDigital(firmaElectronica.getRecibo());
			sav.setSecuenciaNotarial(firmaElectronica.getSecuenciaNotaria());
			sav.setNumeroSerie(firmaElectronica.getSerialCertificado());
		}

		GrupoFamiliar integrante = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(
				nss.getIdAsignacionNSS(), sav.getIdPersona());
		GrupoFamiliar asegurado = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(
				nss.getIdAsignacionNSS(), nss.getIdPersona());

		
		sav.setModalidad(patron.getPatronSujetoObligado().getModalidad()
				.getNumModalidad());
		datosCartilla = getDatosCartillaSalud(sav.getIdPersona(), nss,
				firmaElectronica);
		sav017 = getDatosSav017(sav.getIdPersona(), nss, autorizacion,
				parametros, OrigenSolicitudEnum.VENTANILLA.getId());

		datos4305A = getDatosReporte4305A(nss, firmaElectronica, usuario, OrigenSolicitudEnum.VENTANILLA.getId());

		dato.setDatos4305A(datos4305A);
		dato.setDatosSav002(new ArrayList<Sav002DTO>());
		dato.getDatosSav002().add(sav);
		dato.setDatosSav017(new ArrayList<Sav017DTO>());
		dato.setDatosSav017(sav017);
		dato.setDatosCartilla(datosCartilla);

		// Documento sav006
		sav006.setAsegurado(asegurado.getDerechohabiente().getNombre()
				.toUpperCase()
				+ " "
				+ asegurado.getDerechohabiente().getPrimerApellido()
						.toUpperCase()
				+ " "
				+ (asegurado.getDerechohabiente().getSegundoApellido() == null ? ""
						: asegurado.getDerechohabiente().getSegundoApellido()
								.toUpperCase()));
		sav006.setCurp(asegurado.getDerechohabiente().getCurp());
		sav006.setNss(nss.getNssStr());
		sav006.setBeneficiario(integrante.getDerechohabiente().getNombre()
				.toUpperCase()
				+ " "
				+ integrante.getDerechohabiente().getPrimerApellido()
						.toUpperCase()
				+ " "
				+ (integrante.getDerechohabiente().getSegundoApellido() == null ? ""
						: integrante.getDerechohabiente().getSegundoApellido()
								.toUpperCase()));
		sav006.setCurpBeneficiario(integrante.getDerechohabiente().getCurp());
		sav006.setcSolicitante(asegurado.getMedicoEnTurno()
				.getUnidadMedicaFamiliar().getNombreCorto());
		sav006.setUmf(integrante.getMedicoEnTurno().getUnidadMedicaFamiliar()
				.getNombreCorto());
		sav006.setCambioClinica("X");

		sav006.setNuevoDomicilio("CALLE " + sav.getCalleNumero() + ", COLONIA "
				+ sav.getColonia() + ", " + sav.getDomicilio());
		sav006.setVigencia(integrante.getFechaFinVigencia() == null ? ""
				: DateUtils.dateFormatCustom(integrante.getFechaFinVigencia(),
						"dd/MM/yyyy"));

		dato.setDatosSav006(new ArrayList<Sav006DTO>());
		dato.getDatosSav006().add(sav006);

		datos.add(dato);
		// solo falta el sav017
		plantillas.put("DetalleBeneficiarios.jasper",
				"SUBREPORTE_BENEFICIARIOS");
		plantillas.put("4305A.jasper", "SUBREPORTE_4305A");
		plantillas.put("SAV017.jasper", "SUBREPORTE_SAV017");
		plantillas.put("cartillaSalud.jasper", "SUBREPORTE_CARTILLA");
		plantillas.put("SAV002.jasper", "SUBREPORTE_SAV002");
		plantillas.put("SAV006.jasper", "SUBREPORTE_SAV006");

		Locale locale = new Locale("es", "ES");
		parametros.put(JRParameter.REPORT_LOCALE, locale);
		parametros.put("clave", clave);
		parametros.put("lugar", sav.getDomicilio());
		parametros.put("fecha",
				DateUtils.dateFormatCustom(new Date(), "dd MMMMM yyyy")
						.toUpperCase());
		parametros.put("logo",
				new ClassPathResource("reportes/img/imss.jpg").getPath());

		try {
			repo = manejadorReportes.ejecutaReporteSubreporte(parametros,
					datos, "DocumentosCircunscripcionAuto.jasper", plantillas);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return repo.toByteArray();
	}

	@Override
	public Object getDocumentosSuspencionCircunscripcion(AsignacionNSS nss,
			Long idTramite, Long idPersona, String titulo, Usuario usuario)
			throws DerechohabientesBusinessException, Exception {

		Map<String, Object> parametros = new HashMap<String, Object>();
		Map<String, String> plantillas = new HashMap<String, String>();
		List<DocumentacionCircunscripcionDTO> datos = new ArrayList<DocumentacionCircunscripcionDTO>();
		DocumentacionCircunscripcionDTO dato = new DocumentacionCircunscripcionDTO();
		List<Sav017DTO> sav017 = null;
		ByteArrayOutputStream repo = null;
		String clave = "";
		CabezaGrupoFamiliar patron = null;
		GrupoFamiliar integrante = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(
				nss.getIdAsignacionNSS(), idPersona);
		GrupoFamiliar asegurado = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(
				nss.getIdAsignacionNSS(), nss.getIdPersona());

		patron = grupoFamiliarDaoLocal.getCabezaGrupoFamiliar(nss
				.getIdAsignacionNSS());
		Sav006DTO sav006 = new Sav006DTO();
		clave = patron.getPatronSujetoObligado().getModalidad()
				.getNumModalidad();

		TramiteCircunscripcionForanea c = tramiteDaoLocal
				.getCircunscripcionForanea(idTramite);

		sav017 = getDatosSav017(c, integrante.getDerechohabiente()
				.getIdPersona(), nss, null, false, parametros, OrigenSolicitudEnum.VENTANILLA.getId());

		// dato.setDatosSav017(new ArrayList<Sav017DTO>());
		dato.setDatosSav017(sav017);

		// Documento sav006
		sav006.setAsegurado(asegurado.getDerechohabiente().getNombre()
				+ " "
				+ asegurado.getDerechohabiente().getPrimerApellido()
				+ " "
				+ (asegurado.getDerechohabiente().getSegundoApellido() != null ? asegurado
						.getDerechohabiente().getSegundoApellido() : ""));
		sav006.setBeneficiario(integrante.getDerechohabiente().getNombre()
				+ " "
				+ integrante.getDerechohabiente().getPrimerApellido()
				+ " "
				+ (integrante.getDerechohabiente().getSegundoApellido() != null ? integrante
						.getDerechohabiente().getSegundoApellido() : ""));
		sav006.setNss(nss.getNssStr());
		sav006.setCurpBeneficiario(integrante.getDerechohabiente().getCurp());
		sav006.setcSolicitante(asegurado.getMedicoEnTurno()
				.getUnidadMedicaFamiliar().getNombreCorto());
		sav006.setUmf(integrante.getMedicoEnTurno().getUnidadMedicaFamiliar()
				.getNombreCorto());
		sav006.setCambioClinica("X");

		// Obteniendo el nombre del tramitador
		String[] nombres = usuario.getFisica().getNombre().split(" ");
		String nombre = "";
		for (String s : nombres) {
			nombre += s.substring(0, 1).toUpperCase();
		}
		String tramitador = (usuario.getFisica().getPrimerApellido() != null ? usuario
				.getFisica().getPrimerApellido().substring(0, 1).toUpperCase()
				: "")
				+ (usuario.getFisica().getSegundoApellido() != null ? usuario
						.getFisica().getSegundoApellido().substring(0, 1)
						.toUpperCase() : "") + nombre;
		sav006.setEmpleado(tramitador);
		sav006.setCurp(nss.getCurp());
		dato.setDatosSav006(new ArrayList<Sav006DTO>());
		dato.getDatosSav006().add(sav006);

		datos.add(dato);
		// solo falta el sav017
		plantillas.put("SAV017.jasper", "SUBREPORTE_SAV017");
		plantillas.put("SAV006.jasper", "SUBREPORTE_CARTILLA");
		parametros.put("clave", clave);
		parametros.put("fecha",
				DateUtils.dateFormatCustom(new Date(), "dd MMMMM yyyy")
						.toUpperCase());

		try {
			repo = manejadorReportes
					.ejecutaReporteSubreporte(parametros, datos,
							"DocumentosCircunscripcionSuspen.jasper",
							plantillas);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return repo.toByteArray();
	}

	private List<Sav005DTO> getDatosSav005(AsignacionNSS nss,
			FirmaElectronica firmaElectronica, long idPersona,
			long idEstadoTramite, Long idOrigenSolicitud, Usuario usuario) throws DerechohabientesBusinessException,
			Exception {

		List<Sav005DTO> sav05List = new ArrayList<Sav005DTO>();
		List<Long> tiposTramite = new ArrayList<Long>();
		// tiposTramite.add(TipoTramiteEnum.MODIFICACION.getCodigo().longValue());
		tiposTramite.add(TipoTramiteEnum.CAMBIO_CLINICA.getCodigo().longValue());
		 tiposTramite.add(TipoTramiteEnum.CAMBIO_CONSULTORIO_TURNO.getCodigo().longValue());

		Sav005DTO sav005 = tramiteDaoLocal.getUltimoTramiteSAV005(nss, idPersona, tiposTramite, idEstadoTramite, idOrigenSolicitud);

		if( sav005.getBeneficiarios() == null || sav005.getBeneficiarios().isEmpty()   )
			throw new DerechohabientesBusinessException("No existen beneficiarios vigentes");
		if(firmaElectronica !=null) {
		// Agregar datos de la firma electronica
			sav005.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
			sav005.setSelloDigital(firmaElectronica.getRecibo());
			sav005.setSecuenciaNotarial(firmaElectronica.getSecuenciaNotaria());
			sav005.setNumeroSerie(firmaElectronica.getSerialCertificado());
		}
		String nombreEmpleado = "";
		
		if(idOrigenSolicitud != null) {
			if(idOrigenSolicitud.equals(OrigenSolicitudEnum.INTERNET.getId())) {
				nombreEmpleado = "TR\u00C1MITE NO PRESENCIAL CONCLUIDO CON FIEL SAT.";
			} else if (idOrigenSolicitud.equals(OrigenSolicitudEnum.PORTAL_CIUDADANO.getId()) || idOrigenSolicitud.equals(OrigenSolicitudEnum.MOVILES.getId())){
				nombreEmpleado = "TR\u00C1MITE CONCLUIDO FIRMADO POR EL IMSS.";
			} else {
				nombreEmpleado = usuario != null ? usuario.getUsuario() : "";
			}
		}
		
		sav005.setEmpleado(nombreEmpleado);
		sav05List.add(sav005);

		return sav05List;
	}

	private List<Sav017DTO> getDatosSav017(Long idPersona, AsignacionNSS nss,
			Boolean autorizacion, Map<String, Object> parametros, Long idOrigenSolicitud)
			throws DerechohabientesBusinessException {

		TramiteCircunscripcionForanea circunscripcion = null;
		List<Sav017DTO> datos = new ArrayList<Sav017DTO>();
		Sav017DTO sav017 = new Sav017DTO();
		try {
			circunscripcion = tramiteDaoLocal.getCircunscripcionForanea(idPersona,
					nss, autorizacion);
			GrupoFamiliar grupoFamiliar = grupoFamiliarDaoLocal
					.getIntegranteGrupoFamiliar(nss.getIdAsignacionNSS(),
							circunscripcion.getPersona().getIdPersona());
			CabezaGrupoFamiliar cabezaGrupoFamiliar = grupoFamiliarService
					.cabezaGrupoFamiliar(grupoFamiliar.getAsignacionNSS()
							.getIdAsignacionNSS());

			sav017.setBeneficiario(grupoFamiliar.getDerechohabiente()
					.getNombre()
					+ " "
					+ grupoFamiliar.getDerechohabiente().getPrimerApellido()
					+ " "
					+ grupoFamiliar.getDerechohabiente().getSegundoApellido());
			sav017.setCalidad("" + grupoFamiliar.getCalidad());
			sav017.setCurp(grupoFamiliar.getDerechohabiente().getCurp());
			sav017.setMes(DateUtils.dateFormatCustom(grupoFamiliar
					.getDerechohabiente().getFechaNacimiento(), "MM"));
			sav017.setAnio(DateUtils.dateFormatCustom(grupoFamiliar
					.getDerechohabiente().getFechaNacimiento(), "yyyy"));
			sav017.setSexo(grupoFamiliar.getDerechohabiente().getSexo()
					.getDescripcion().substring(0, 1));

			// datos.add(sav017);

			parametros.put("logo", new ClassPathResource(
					"reportes/img/imss.jpg").getPath());
			parametros.put("nss", grupoFamiliar.getAsignacionNSS().getNssStr());
			parametros.put("curp", grupoFamiliar.getAsignacionNSS().getCurp());
			parametros.put("regPatronal", cabezaGrupoFamiliar
					.getPatronSujetoObligado().getNumeroRegistroPatronal());
			parametros.put("nombre", grupoFamiliar.getAsignacionNSS()
					.getNombre());
			parametros.put("aPaterno", grupoFamiliar.getAsignacionNSS()
					.getPrimerApellido());
			parametros.put("aMaterno", grupoFamiliar.getAsignacionNSS()
					.getSegundoApellido());

			parametros.put("dVerificador", cabezaGrupoFamiliar
					.getPatronSujetoObligado().getDigVerificador() == null ? ""
					: cabezaGrupoFamiliar.getPatronSujetoObligado()
							.getDigVerificador());

			parametros.put("tipoTrabajador", "5");

			// Checa si el tramite esta Activo7

			if (autorizacion) { // Activa
				parametros.put(
						"fecha",
						DateUtils.dateFormatCustom(
								circunscripcion.getFecInicioCircunscripcion(),
								"dd MMMMM yyyy").toUpperCase());
				parametros.put("autorizacion", true);
				parametros.put("obsevaciones",
						" " + circunscripcion.getObservacion());
				// Domicilio
				parametros.put("calle", circunscripcion.getDomicilioDestino()
						.getVialidadPrimaria().getNombre());
				parametros.put("numero", ""
						+ circunscripcion.getDomicilioDestino()
								.getNumExterior1());
				parametros.put("colonia", circunscripcion.getDomicilioDestino()
						.getAsentamiento().getNombre());
				parametros.put("municipio", circunscripcion
						.getDomicilioDestino().getAsentamiento().getLocalidad()
						.getMunicipio().getNombre());
				parametros.put("cp", ""
						+ circunscripcion.getDomicilioDestino()
								.getCodigoPostal().getCodigoPostal());
				parametros.put("entidad", circunscripcion.getDomicilioDestino()
						.getAsentamiento().getLocalidad().getMunicipio()
						.getEntidadFederativa().getNombre());
				parametros.put("umf", ""
						+ circunscripcion.getMedicoEnTurnoDestino()
								.getUnidadMedicaFamiliar().getNombreCorto());
				parametros.put("subDelegacion", ""
						+ circunscripcion.getMedicoEnTurnoDestino()
								.getUnidadMedicaFamiliar().getSubdelegacion()
								.getClave());
				parametros.put("delegacion", ""
						+ circunscripcion.getMedicoEnTurnoDestino()
								.getUnidadMedicaFamiliar().getSubdelegacion()
								.getDelegacion().getClave());

				parametros.put("lugar", circunscripcion.getDomicilioOrigen()
						.getAsentamiento().getLocalidad().getMunicipio()
						.getNombre()
						+ ", "
						+ circunscripcion.getDomicilioOrigen()
								.getAsentamiento().getLocalidad()
								.getMunicipio().getEntidadFederativa()
								.getNombre());
			} else {

				parametros.put(
						"fecha",
						DateUtils.dateFormatCustom(
								circunscripcion.getFecFinCircunscripcion(),
								"dd MMMMM yyyy").toUpperCase());
				parametros.put("obsevaciones", " "
						+ circunscripcion.getTramiteSuspension()
								.getObservacion());
				parametros.put("autorizacion", false);

				// Domicilio
				parametros.put("calle", circunscripcion.getDomicilioOrigen()
						.getVialidadPrimaria().getNombre());
				parametros.put("numero", ""
						+ circunscripcion.getDomicilioOrigen()
								.getNumExterior1());
				parametros.put("colonia", circunscripcion.getDomicilioOrigen()
						.getAsentamiento().getNombre());
				parametros.put("municipio", circunscripcion
						.getDomicilioOrigen().getAsentamiento().getLocalidad()
						.getMunicipio().getNombre());
				parametros.put("cp", ""
						+ circunscripcion.getDomicilioOrigen()
								.getCodigoPostal().getCodigoPostal());
				parametros.put("entidad", circunscripcion.getDomicilioOrigen()
						.getAsentamiento().getLocalidad().getMunicipio()
						.getEntidadFederativa().getNombre());
				parametros.put("umf", ""
						+ circunscripcion.getMedicoEnTurnoOrigen()
								.getUnidadMedicaFamiliar().getNombreCorto());
				parametros.put("subDelegacion", ""
						+ circunscripcion.getMedicoEnTurnoOrigen()
								.getUnidadMedicaFamiliar().getSubdelegacion()
								.getClave());
				parametros.put("delegacion", ""
						+ circunscripcion.getMedicoEnTurnoOrigen()
								.getUnidadMedicaFamiliar().getSubdelegacion()
								.getDelegacion().getClave());

				parametros.put("lugar", circunscripcion.getDomicilioDestino()
						.getAsentamiento().getLocalidad().getMunicipio()
						.getNombre()
						+ ", "
						+ circunscripcion.getDomicilioDestino()
								.getAsentamiento().getLocalidad()
								.getMunicipio().getEntidadFederativa()
								.getNombre());

			}

			if(idOrigenSolicitud.equals(OrigenSolicitudEnum.INTERNET.getId())) {
				parametros.put("empleado", "TR\u00C1MITE NO PRESENCIAL CONCLUIDO CON FIEL SAT.");
			} else{
			parametros.put("empleado", "");
			}

			datos.add(sav017);
		} catch (DerechohabientesBusinessException dex) {
			throw dex;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error no cachado de la aplicacion", e);
			DerechohabientesBusinessException
					.throwException(ExceptionMessages.DERECHOHABIENTE_ERROR);
		}
		return datos;
	}

	@Override
	public Object getDocumentosCuestionario(AsignacionNSS asignacionNSS,
			Long idTramite) throws DerechohabientesBusinessException, Exception {

		Map<String, Object> parametros = new HashMap<String, Object>();
		Map<String, String> plantillas = new HashMap<String, String>();
		List<DocumentacionCircunscripcionDTO> datos = new ArrayList<DocumentacionCircunscripcionDTO>();
		DocumentacionCircunscripcionDTO dato = new DocumentacionCircunscripcionDTO();
		dato.setDatosSav002(new ArrayList<Sav002DTO>());
		dato.getDatosSav002().add(new Sav002DTO());
		datos.add(dato);
		ByteArrayOutputStream repo = null;
		RegistroDto registro = new RegistroDto();

		registro.setTramiteRegistro(new TramiteRegistroDerechohabiente());
		registro.getTramiteRegistro().setTramiteId(idTramite);
		registro.setDatosAsegurado(asignacionNSS);

		if (registro.getTramiteRegistro().getParentesco().getIdParentesco() == ParentescoEnum.CONCUBINARIO
				.getId()
				|| registro.getTramiteRegistro().getParentesco()
						.getIdParentesco() == ParentescoEnum.CONCUBINA.getId()) {
			plantillas.put("CuestionarioConcubino12.jasper",
					"SUBREPORTE_CUESTIONARIO_1");
			plantillas.put("CuestionarioConcubino22.jasper",
					"SUBREPORTE_CUESTIONARIO_2");
			plantillas.put("CuestionarioBeneficiarioConcubino12.jasper",
					"SUBREPORTE_CUESTIONARIO_3");
			plantillas.put("CuestionarioBeneficiarioConcubino22.jasper",
					"SUBREPORTE_CUESTIONARIO_4");
		} else if (registro.getTramiteRegistro().getParentesco()
				.getIdParentesco() == ParentescoEnum.PADRES.getId()
				|| registro.getTramiteRegistro().getParentesco()
						.getIdParentesco() == ParentescoEnum.MADRE.getId()) {
			plantillas.put("CuestionarioPadres12.jasper",
					"SUBREPORTE_CUESTIONARIO_1");
			plantillas.put("CuestionarioPadres22.jasper",
					"SUBREPORTE_CUESTIONARIO_2");
			plantillas.put("CuestionarioBeneficiarioPadre12.jasper",
					"SUBREPORTE_CUESTIONARIO_3");
			plantillas.put("CuestionarioBeneficiarioPadre22.jasper",
					"SUBREPORTE_CUESTIONARIO_4");

			if (registro.getTramiteRegistro().getFisica().getSexo().getIdSexo() == SexoEnum.HOMBRE
					.getId())
				parametros.put("BENEFICIARIO", "padre");
		} else {
			parametros.put("BENEFICIARIO", "madre");
		}
		try {
			repo = manejadorReportes.ejecutaReporteSubreporte(parametros,
					datos, "CuestionarioConvivenciaDependencia.jasper",
					plantillas);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return repo.toByteArray();
	}

	private List<Sav017DTO> getDatosSav017(
			TramiteCircunscripcionForanea circunscripcion, Long idPersona,
			AsignacionNSS nss, FirmaElectronica firmaElectronica,
			Boolean autorizacion, Map<String, Object> parametros, Long idOrigenSolicitud)
			throws DerechohabientesBusinessException {
		Sav017DTO sav017 = new Sav017DTO();
		List<Sav017DTO> datos = new ArrayList<Sav017DTO>();
		try {
			if (circunscripcion == null) {
				circunscripcion = tramiteDaoLocal.getCircunscripcionForanea(
						idPersona, nss, autorizacion);
			}

			GrupoFamiliar grupoFamiliar = grupoFamiliarDaoLocal
					.getIntegranteGrupoFamiliar(nss.getIdAsignacionNSS(),
							circunscripcion.getPersona().getIdPersona());
			CabezaGrupoFamiliar cabezaGrupoFamiliar = grupoFamiliarService
					.cabezaGrupoFamiliar(nss.getIdAsignacionNSS());

			sav017.setBeneficiario(grupoFamiliar.getDerechohabiente()
					.getNombre()
					+ " "
					+ grupoFamiliar.getDerechohabiente().getPrimerApellido()
					+ " "
					+ grupoFamiliar.getDerechohabiente().getSegundoApellido());
			sav017.setCalidad("" + grupoFamiliar.getCalidad());
			sav017.setCurp(grupoFamiliar.getDerechohabiente().getCurp());
			sav017.setMes(DateUtils.dateFormatCustom(grupoFamiliar
					.getDerechohabiente().getFechaNacimiento(), "MM"));
			sav017.setAnio(DateUtils.dateFormatCustom(grupoFamiliar
					.getDerechohabiente().getFechaNacimiento(), "yyyy"));
			sav017.setSexo(grupoFamiliar.getDerechohabiente().getSexo()
					.getDescripcion().substring(0, 1));

			if (firmaElectronica != null) {
				sav017.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
				sav017.setSelloDigital(firmaElectronica.getRecibo());
				sav017.setSecuenciaNotarial(firmaElectronica
						.getSecuenciaNotaria());
				sav017.setNumeroSerie(firmaElectronica.getSerialCertificado());
			}

			datos.add(sav017);

			// parametros
			parametros.put("logo", new ClassPathResource(
					"reportes/img/imss.jpg").getPath());
			parametros.put("nss", grupoFamiliar.getAsignacionNSS().getNssStr());
			parametros.put("curp", grupoFamiliar.getAsignacionNSS().getCurp());
			
			parametros.put("nombre", grupoFamiliar.getAsignacionNSS()
					.getNombre());
			parametros.put("aPaterno", grupoFamiliar.getAsignacionNSS()
					.getPrimerApellido());
			parametros.put("aMaterno", grupoFamiliar.getAsignacionNSS()
					.getSegundoApellido());
			
			
			parametros.put("dVerificador", "");
			parametros.put("regPatronal","");
			
			try{
				parametros.put("regPatronal", cabezaGrupoFamiliar
					.getPatronSujetoObligado().getNumeroRegistroPatronal()+cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad());
			
				parametros.put("dVerificador", cabezaGrupoFamiliar
						.getPatronSujetoObligado().getDigVerificador() == null ? ""
						: cabezaGrupoFamiliar.getPatronSujetoObligado()
								.getDigVerificador());
				
			}catch(NullPointerException e){
				// --------------------------------------------------------
				// El pensionado puede no tener patrón
				// --------------------------------------------------------
				log.debug("Sujeto Obligado NULL, idAsignacion:"+nss.getIdAsignacionNSS());
				
			}
			
			
			
			
			parametros
					.put("obsevaciones",
							" " + circunscripcion.getObservacion() != null ? circunscripcion
									.getObservacion() : "");

			parametros.put("tipoTrabajador", "1");

			if (autorizacion) { // Activa

				if (circunscripcion.getFecInicioCircunscripcion() != null)
					parametros.put(
							"fecha",
							DateUtils.dateFormatCustom(
									circunscripcion
											.getFecInicioCircunscripcion(),
									"dd MMMMM yyyy").toUpperCase());
				else
					parametros.put(
							"fecha",
							DateUtils.dateFormatCustom(new Date(),
									"dd MMMMM yyyy").toUpperCase());

				parametros.put("autorizacion", true);
				// Domicilio
				parametros.put("calle", ""
						+ circunscripcion.getDomicilioDestino()
								.getVialidadPrimaria().getNombre());
				
				
				String numero = circunscripcion.getDomicilioDestino().getNumExterior1() != null ? circunscripcion.getDomicilioDestino().getNumExterior1()+" " : "";
				numero += circunscripcion.getDomicilioDestino().getNumExteriorAlf() != null ? circunscripcion.getDomicilioDestino().getNumExteriorAlf()+"" : "";
				parametros.put("numero",  numero);
				
				
				parametros.put("colonia", ""
						+ circunscripcion.getDomicilioDestino()
								.getAsentamiento().getNombre());
				parametros.put("municipio", ""
						+ circunscripcion.getDomicilioDestino()
								.getAsentamiento().getLocalidad()
								.getMunicipio().getNombre());
				parametros.put("cp", ""
						+ circunscripcion.getDomicilioDestino()
								.getCodigoPostal().getCodigoPostal());
				parametros.put("entidad", ""
						+ circunscripcion.getDomicilioDestino()
								.getAsentamiento().getLocalidad()
								.getMunicipio().getEntidadFederativa()
								.getNombre());
				parametros.put("umf", ""
						+ circunscripcion.getMedicoEnTurnoDestino()
								.getUnidadMedicaFamiliar().getNombreCorto());
				parametros.put("subDelegacion", ""
						+ circunscripcion.getMedicoEnTurnoDestino()
								.getUnidadMedicaFamiliar().getSubdelegacion()
								.getClave());
				parametros.put("delegacion", ""
						+ circunscripcion.getMedicoEnTurnoDestino()
								.getUnidadMedicaFamiliar().getSubdelegacion()
								.getDelegacion().getClave());
				
				
				
				if( circunscripcion.getDomicilioOrigen() != null ){
					parametros.put("lugar", " "
						+ circunscripcion.getDomicilioOrigen()
								.getAsentamiento().getLocalidad()
								.getMunicipio().getNombre()
						+ ", "
						+ circunscripcion.getDomicilioOrigen()
								.getAsentamiento().getLocalidad()
								.getMunicipio().getEntidadFederativa()
								.getNombre());
				}else{
					parametros.put("lugar", " ");
				}
				
			} else {
				GrupoFamiliar asegurado = grupoFamiliarDaoLocal
						.getIntegranteGrupoFamiliar(nss.getIdAsignacionNSS(),
								nss.getIdPersona());
				if (circunscripcion.getFecFinCircunscripcion() != null)
					parametros.put(
							"fecha",
							DateUtils.dateFormatCustom(
									circunscripcion.getFecFinCircunscripcion(),
									"dd MMMMM yyyy").toUpperCase());
				else
					parametros.put(
							"fecha",
							DateUtils.dateFormatCustom(new Date(),
									"dd MMMMM yyyy").toUpperCase());

				parametros.put("autorizacion", false);
				
				// Domicilio
				Domicilio domicilioAsegurado = asegurado.getDomicilio();
				
				parametros.put("calle", (domicilioAsegurado != null)? domicilioAsegurado.getVialidadPrimaria().getNombre():"");
				
				String numero = (domicilioAsegurado != null)? (domicilioAsegurado.getNumExterior1() != null ? domicilioAsegurado.getNumExterior1()+" " : ""):"";
				numero += (domicilioAsegurado != null)? (domicilioAsegurado.getNumExteriorAlf() != null ? domicilioAsegurado.getNumExteriorAlf()+"" : ""):"";
				
				parametros.put("numero", numero);
				parametros.put("colonia", (domicilioAsegurado != null)? domicilioAsegurado.getAsentamiento().getNombre():"");
				parametros.put("municipio", (domicilioAsegurado != null)? domicilioAsegurado.getAsentamiento().getLocalidad().getMunicipio().getNombre():"");
				parametros.put("cp", (domicilioAsegurado!= null)? domicilioAsegurado.getCodigoPostal().getCodigoPostal():"");
				parametros.put("entidad", (domicilioAsegurado!= null)?domicilioAsegurado.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre():"");
				
				parametros.put("umf", ""+ asegurado.getMedicoEnTurno().getUnidadMedicaFamiliar().getNombreCorto());
				parametros.put("subDelegacion", ""+ asegurado.getMedicoEnTurno().getUnidadMedicaFamiliar().getSubdelegacion().getClave());
				parametros.put("delegacion", ""+ asegurado.getMedicoEnTurno().getUnidadMedicaFamiliar().getSubdelegacion().getDelegacion().getClave());
				
				
				if( domicilioAsegurado != null ){
				parametros.put("lugar", " "+ domicilioAsegurado.getAsentamiento().getLocalidad().getMunicipio().getNombre()+ ", "+ circunscripcion.getDomicilioDestino()
								.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre());
				}else{
					parametros.put("lugar", " ");
				}
			}

			if(idOrigenSolicitud.equals(OrigenSolicitudEnum.INTERNET.getId())) {
				parametros.put("empleado", "TR\u00C1MITE NO PRESENCIAL CONCLUIDO CON FIEL SAT.");
			} else{
				parametros.put("empleado", " ");
			}
		} catch (DerechohabientesBusinessException dex) {
			throw dex;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error no cachado de la aplicacion", e);
			DerechohabientesBusinessException
					.throwException(ExceptionMessages.DERECHOHABIENTE_ERROR);
		}

		return datos;
	}

	private String stringTresPosiciones(String original, int tam) {
		String resultado = original;

		while (resultado.length() < tam) {
			resultado = "0" + resultado;
		}

		return resultado;
	}

	private List<GrupoFamiliar> filtrarPorUmf(List<GrupoFamiliar> integrantes,
			Long idUmf) {
		List<GrupoFamiliar> salida = new ArrayList<GrupoFamiliar>();

		if(integrantes != null && !integrantes.isEmpty()){
			for (GrupoFamiliar integrante : integrantes) {
				if (integrante.getMedicoEnTurno() != null) {
					if (integrante.getMedicoEnTurno().getUnidadMedicaFamiliar() != null) {
						if (integrante.getMedicoEnTurno().getUnidadMedicaFamiliar()
								.getIdUMF().longValue() == idUmf.longValue()) {
							salida.add(integrante);
						}
					}
				}
			}
		}
		return salida;
	}

	@Override
	public Object getDocumentoRegistroDerechohabientesDep(AsignacionNSS nss,
			FirmaElectronica firmaElectronica, Long idTramite,
			List<Long> personas, Long tipoTramite, String titulo,
			Usuario usuario) throws DerechohabientesBusinessException,
			Exception {

		Map<String, Object> parametros = new HashMap<String, Object>();
		Map<String, String> plantillas = new HashMap<String, String>();
		List<Reporte4305A> datos4305A = new ArrayList<Reporte4305A>();
		List<DocumentosRegistroDTO> datos = new ArrayList<DocumentosRegistroDTO>();
		List<CartillaSaludDTO> datosCartilla = new ArrayList<CartillaSaludDTO>();
		List<Sav005DTO> sav05List = null;
		List<Sav017DTO> sav017 = null;

		DocumentosRegistroDTO dato = new DocumentosRegistroDTO();
		ByteArrayOutputStream repo = null;
		String clave = "";
		CabezaGrupoFamiliar patron = null;
		Sav002DTO sav02 = null;

		patron = grupoFamiliarDaoLocal.getCabezaGrupoFamiliar(nss
				.getIdAsignacionNSS());
		clave = patron.getPatronSujetoObligado().getModalidad()
				.getNumModalidad();
		
		sav02 = getDatosSav002(nss, idTramite,true, patron);

		if (firmaElectronica != null) {
			sav02.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
			sav02.setSelloDigital(firmaElectronica.getRecibo());
			sav02.setSecuenciaNotarial(firmaElectronica.getSecuenciaNotaria());
			sav02.setNumeroSerie(firmaElectronica.getSerialCertificado());
		}

		
		sav02.setModalidad(patron.getPatronSujetoObligado().getModalidad()
				.getNumModalidad());
		for (Long persona : personas) {
			CartillaSaludDTO cartilla = getDatosCartillaSalud(persona, nss,
					firmaElectronica).get(0);
			if (cartilla != null)
				datosCartilla.add(cartilla);
		}

		if (tipoTramite.longValue() == TipoTramiteEnum.REGISTRO_DH_CAMBIO_UMF
				.getCodigo().longValue()) {
			sav05List = getDatosSav005(nss, firmaElectronica,
					sav02.getIdPersona(), EstadoTramiteEnum.CERRADO.getId(), OrigenSolicitudEnum.VENTANILLA.getId(),usuario);
			sav05List.get(0).setEmpleado(
					usuario.getNomNombre() + " " + usuario.getNomPaterno()
							+ " " + usuario.getNomMaterno());
			sav05List.get(0).setCambioParcial(true);
			dato.setDatosSav005(sav05List);
		}

		if (tipoTramite.longValue() == TipoTramiteEnum.REGISTRO_DH_CAMBIO_CIRCUNSCRIPCION
				.getCodigo().longValue()) {
			sav017 = getDatosSav017(sav02.getIdPersona(), nss, true, parametros, OrigenSolicitudEnum.VENTANILLA.getId());
			dato.setDatosSav017(new ArrayList<Sav017DTO>());
			dato.setDatosSav017(sav017);
		}

		datos4305A = getDatosReporte4305A(nss, firmaElectronica, usuario, OrigenSolicitudEnum.VENTANILLA.getId());
		dato.setDatos4305A(datos4305A);
		dato.setDatosSav002(new ArrayList<Sav002DTO>());
		dato.getDatosSav002().add(sav02);

		if (datosCartilla.size() > 0)
			dato.setDatosCartilla(datosCartilla);

		datos.add(dato);

		plantillas.put("DetalleBeneficiarios.jasper",
				"SUBREPORTE_BENEFICIARIOS");
		plantillas.put("4305A.jasper", "SUBREPORTE_4305A");
		plantillas.put("SAV002.jasper", "SUBREPORTE_SAV002");

		if (datosCartilla.size() > 0)
			plantillas.put("cartillaSalud.jasper", "SUBREPORTE_CARTILLA");

		if (tipoTramite.longValue() == TipoTramiteEnum.REGISTRO_DH_CAMBIO_UMF
				.getCodigo().longValue())
			plantillas.put("SAV005.jasper", "SUBREPORTE_SAV005");

		if (tipoTramite.longValue() == TipoTramiteEnum.REGISTRO_DH_CAMBIO_CIRCUNSCRIPCION
				.getCodigo().longValue())
			plantillas.put("SAV017.jasper", "SUBREPORTE_SAV017");

		Locale locale = new Locale("es", "mx");
		parametros.put(JRParameter.REPORT_LOCALE, locale);

		parametros.put("clave", clave);
		parametros.put("lugar", sav02.getDomicilio());
		parametros.put("fecha",
				DateUtils.dateFormatCustom(new Date(), "dd MMMMM yyyy")
						.toUpperCase());

		try {
			if (datosCartilla.size() > 0) {
				repo = manejadorReportes.ejecutaReporteSubreporte(parametros,
						datos, "DocumentosRegistroDerechohabienteDep.jasper",
						plantillas);
			} else {
				repo = manejadorReportes.ejecutaReporteSubreporte(parametros,
						datos, "DocumentosRegistroDerechohabienteDepSC.jasper",
						plantillas);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return repo.toByteArray();
	}

	@Override
	public Object concatenarByteStream(
			List<ByteArrayOutputStream> byteArrayOutputStream, boolean paginate) {
		return manejadorReportes.concatPDF(byteArrayOutputStream, paginate);
	}
	
	private GrupoFamiliar setGrupoFamiliarByAsingacionNSS(AsignacionNSS asignacion, CabezaGrupoFamiliar cabeza){
		GrupoFamiliar asegurado = new GrupoFamiliar();
		Derechohabiente derechohabiente = new Derechohabiente();
		asegurado.setAsignacionNSS(asignacion);
		
		
		derechohabiente.setNombre(asignacion.getNombre());
		derechohabiente.setPrimerApellido(asignacion.getPrimerApellido());
		derechohabiente.setSegundoApellido(asignacion.getSegundoApellido());
		derechohabiente.setCurp(asignacion.getCurp());
		derechohabiente.setSexo(asignacion.getSexo());
		derechohabiente.setLugarNacimiento(asignacion.getLugarNacimiento());
		derechohabiente.setFechaNacimiento(asignacion.getFechaNacimiento());
		derechohabiente.setIdPersona(asignacion.getIdPersona());
		asegurado.setEstadoDerechohabiente(cabeza.getEstadoDerechohabiente());
		asegurado.setSubEstadoDerechohabiente(cabeza.getSubEstadoDerechohabiente());
		asegurado.setDerechohabiente(derechohabiente);
		
		
		return asegurado;
		
	}

	@Override
	public Object getConstanciaVigenciaInternet(AsignacionNSS nss, FirmaElectronica firmaElectronica, Usuario usuario) throws DerechohabientesBusinessException, Exception {

		CabezaGrupoFamiliar patron = grupoFamiliarDaoLocal.getCabezaGrupoFamiliar(nss.getIdAsignacionNSS());
		boolean patronesVigentes = false;
		boolean muestraIntegrantes= false;
		boolean muestraInfoPatron = false;
		
		long numintegrantes = grupoFamiliarService.getNumeroIntegrantesRegistrsdosPorParentesco(nss.getIdAsignacionNSS(), null);
		List<GrupoFamiliar> integrantes = null;
		GrupoFamiliar	asegurado = null;
			
		//GrupoFamiliar	asegurado = null;
		if(patron == null ) {
			String mensajeError = "El asegurado / pensionado con n&uacute;mero de seguridad social "+ nss.getNss() + " no se encuentra registrado a&uacute;n" +
					" como derechohabiente, por lo tanto no podr&aacute; obtener su comprobante de vigencia de derechos.";
			DerechohabientesBusinessException.throwException(mensajeError, mensajeError);
		}
		
		if(numintegrantes >0){
			asegurado = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(nss.getIdAsignacionNSS(), nss.getIdPersona());		
		}
		
		if(asegurado == null){
			AsignacionNSS asignacion = asignacionNssDao.getAsignacionNSS(patron.getAsignacionNSS());
			asegurado = setGrupoFamiliarByAsingacionNSS(asignacion, patron);
		}
		
		ComprobanteVigenciaDerechosDTO comprobante = new ComprobanteVigenciaDerechosDTO();

		List<ComprobanteVigenciaDerechosDTO> datos = new ArrayList<ComprobanteVigenciaDerechosDTO>();
		Map<String, String> plantillas = new HashMap<String, String>();
		
		List<BeneficiarioDTO> beneficiarios = new ArrayList<BeneficiarioDTO>();
		List<ServiciosDTO> serviciosDTO = new ArrayList<ServiciosDTO>();
		List<PrestacionesDTO> lstPrestacionesDTO = new ArrayList<PrestacionesDTO>();
		//PrestacionesDTO prestacionesDTO = null;
		BeneficiarioDTO beneficiario = new BeneficiarioDTO();
		TramiteProrroga prorroga = null;
		Long idCaracter = null;
		

		
		//datos generales del asegurado
		comprobante.setNss(asegurado.getAsignacionNSS().getNssStr());
		comprobante.setNombreAsegurado(asegurado.getDerechohabiente()
				.getNombre());
		comprobante.setPrimerApellidoAsegurado(asegurado.getDerechohabiente()
				.getPrimerApellido());
		comprobante.setSegundoApellidoAsegurado(asegurado.getDerechohabiente()
				.getSegundoApellido());
		comprobante.setCurp(asegurado.getDerechohabiente().getCurp());
		comprobante.setSexoAsegurado(asegurado.getDerechohabiente().getSexo()
				.getDescripcion());
		comprobante.setLugarNacimientoAsegurado(asegurado.getDerechohabiente()
				.getLugarNacimiento().getNombre());
		
		if(asegurado.getDerechohabiente().getFechaNacimiento() != null) {
			comprobante.setFechaNacimientoAsegurado(DateUtils.dateFormat(asegurado
				.getDerechohabiente().getFechaNacimiento()));
		} else {
			comprobante.setFechaNacimientoAsegurado(this.VALOR_DEFAULT);
		}
		
		comprobante.setSituacion(asegurado.getEstadoDerechohabiente()
				.getDescripcion());
		if(asegurado.getMedicoEnTurno() != null){
			comprobante.setDelegacion(asegurado.getMedicoEnTurno()
					.getUnidadMedicaFamiliar().getSubdelegacion().getDelegacion()
					.getDescripcion());
			comprobante.setUmf(asegurado.getMedicoEnTurno()
					.getUnidadMedicaFamiliar().getDescripcion());
			comprobante.setTurno(asegurado.getMedicoEnTurno().getTurno()
					.getDescripcion());
			comprobante.setConsultorio(asegurado.getMedicoEnTurno()
					.getConsultorio().getDescripcion());
			comprobante.setAgregadoMedico(asegurado.getAgregadoMedico());
		}else{
			comprobante.setDelegacion(this.VALOR_DEFAULT);
			comprobante.setUmf(this.VALOR_DEFAULT);
			comprobante.setTurno(this.VALOR_DEFAULT);
			comprobante.setConsultorio(this.VALOR_DEFAULT);
			comprobante.setAgregadoMedico(this.VALOR_DEFAULT);
		}
		
		
		//Se seccion de patrones
		comprobante.setTipoMovimiento(patron.getTipoMovtoAsegurado()
				.getDesTipoMvtoAsegurado());
		comprobante.setUltimoMovimiento(patron.getFechaUltimoMovAfiliacion());
		comprobante.setFechaExpedicion(new Date());
		comprobante.setModalidadPatron("("+patron.getPatronSujetoObligado()
				.getModalidad().getNumModalidad()+") " + patron.getPatronSujetoObligado().getModalidad().getDesCorta());
		
		
		comprobante.setFechaValidezConstancia(patron.getFechaValidezConstancia() );
		//validacion de fecha de emicion si tiene mas de una mdalidad sera la fecha del dia si no la que regrese el ws
		List<SujetoObligado> patronesActivos = grupoFamiliarService.getPatronesAsegurado(nss);
		
		if(patronesActivos != null && !patronesActivos.isEmpty()){
			patronesVigentes = true;
			int countModalidades = 0;
			String strModalidadDesc = "";
			Map<String,Modalidad> descripcionesModalidad = new HashMap<String, Modalidad>();
			
			for(SujetoObligado pat: patronesActivos){
				descripcionesModalidad.put(pat.getModalidad().getNumModalidad(),pat.getModalidad());
			}
			
			List<Modalidad> modalidades = new ArrayList<Modalidad>(descripcionesModalidad.values());
			for(Modalidad moda: modalidades) {
				strModalidadDesc += "("+moda.getNumModalidad() +") " + moda.getDesCorta() + " ";
				countModalidades ++;
				if(moda.getIdModalidad() == ModalidadEnum.DIEZ.getId() ||
						moda.getIdModalidad() == ModalidadEnum.TRECE.getId() ||
						moda.getIdModalidad() == ModalidadEnum.CATORCE.getId() ||
						moda.getIdModalidad() == ModalidadEnum.TREINTA.getId() ||
						moda.getIdModalidad() == ModalidadEnum.TREINTAYTRES.getId() ||
						moda.getIdModalidad() == ModalidadEnum.TREINTAYCUATRO.getId() ||
						moda.getIdModalidad() == ModalidadEnum.TREINTAYCINCO.getId() ||
						moda.getIdModalidad() == ModalidadEnum.TREINTAYSEIS.getId() ||
						moda.getIdModalidad() == ModalidadEnum.TREINTAYOCHO.getId() ||
						moda.getIdModalidad() == ModalidadEnum.CUARENTAYDOS.getId() ||
						moda.getIdModalidad() == ModalidadEnum.CUARENTAYTRES.getId() ||
						moda.getIdModalidad() == ModalidadEnum.CUARENTAYCUATRO.getId()){
					muestraIntegrantes= true;
				}
				if(moda.getIdModalidad() == ModalidadEnum.DIEZ.getId() ||
						moda.getIdModalidad() == ModalidadEnum.TRECE.getId() ||
						moda.getIdModalidad() == ModalidadEnum.CATORCE.getId() ||
						moda.getIdModalidad() == ModalidadEnum.DIECISIETE.getId() ||
						moda.getIdModalidad() == ModalidadEnum.TREINTAYCUATRO.getId() ||
						moda.getIdModalidad() == ModalidadEnum.TREINTAYCINCO.getId() ||
						moda.getIdModalidad() == ModalidadEnum.TREINTAYSEIS.getId() ||
						moda.getIdModalidad() == ModalidadEnum.TREINTAYOCHO.getId() ||
						moda.getIdModalidad() == ModalidadEnum.CUARENTA.getId() ||
						moda.getIdModalidad() == ModalidadEnum.CUARENTAYDOS.getId() ||
						moda.getIdModalidad() == ModalidadEnum.CUARENTAYTRES.getId() ||
						moda.getIdModalidad() == ModalidadEnum.CUARENTAYCUATRO.getId()){
					muestraInfoPatron= true;
				}
			}
			
			if(countModalidades>1){
				comprobante.setFechaValidezConstancia(new Date());
			}
			
			comprobante.setModalidadPatron(strModalidadDesc);
		}
		Modalidad moda = patron.getPatronSujetoObligado().getModalidad();
		//se tiene que volver a evaluar las modalidades por aquellos que estan en conservacion de derechos y si no tubo algo modalidad vigente
		if(!muestraIntegrantes){
			if(moda.getIdModalidad() == ModalidadEnum.DIEZ.getId() ||
					moda.getIdModalidad() == ModalidadEnum.TRECE.getId() ||
					moda.getIdModalidad() == ModalidadEnum.CATORCE.getId() ||
					moda.getIdModalidad() == ModalidadEnum.TREINTA.getId() ||
					moda.getIdModalidad() == ModalidadEnum.TREINTAYTRES.getId() ||
					moda.getIdModalidad() == ModalidadEnum.TREINTAYCUATRO.getId() ||
					moda.getIdModalidad() == ModalidadEnum.TREINTAYCINCO.getId() ||
					moda.getIdModalidad() == ModalidadEnum.TREINTAYSEIS.getId() ||
					moda.getIdModalidad() == ModalidadEnum.TREINTAYOCHO.getId() ||
					moda.getIdModalidad() == ModalidadEnum.CUARENTAYDOS.getId() ||
					moda.getIdModalidad() == ModalidadEnum.CUARENTAYTRES.getId() ||
					moda.getIdModalidad() == ModalidadEnum.CUARENTAYCUATRO.getId()){
				muestraIntegrantes= true;
			}
		}
		//se valida la seccion de patron para las modalidades por aquellos en conservacion
		if(!muestraInfoPatron){
			if(moda.getIdModalidad() == ModalidadEnum.DIEZ.getId() ||
					moda.getIdModalidad() == ModalidadEnum.TRECE.getId() ||
					moda.getIdModalidad() == ModalidadEnum.CATORCE.getId() ||
					moda.getIdModalidad() == ModalidadEnum.DIECISIETE.getId() ||
					moda.getIdModalidad() == ModalidadEnum.TREINTAYCUATRO.getId() ||
					moda.getIdModalidad() == ModalidadEnum.TREINTAYCINCO.getId() ||
					moda.getIdModalidad() == ModalidadEnum.TREINTAYSEIS.getId() ||
					moda.getIdModalidad() == ModalidadEnum.TREINTAYOCHO.getId() ||
					moda.getIdModalidad() == ModalidadEnum.CUARENTA.getId() ||
					moda.getIdModalidad() == ModalidadEnum.CUARENTAYDOS.getId() ||
					moda.getIdModalidad() == ModalidadEnum.CUARENTAYTRES.getId() ||
					moda.getIdModalidad() == ModalidadEnum.CUARENTAYCUATRO.getId()){
				muestraInfoPatron= true;
			}
		}
		
		if(numintegrantes >0 && muestraIntegrantes){
			integrantes = grupoFamiliarDaoLocal.findGrupoFamiliar(nss.getIdAsignacionNSS());
		}
		if(!muestraIntegrantes){
			comprobante.setMensajeBeneficiarios("NO APLICA");
		}
		
		if(muestraInfoPatron){
			comprobante.setRegistroPatronal(patron.getPatronSujetoObligado()
					.getNumeroRegistroPatronal() + patron.getPatronSujetoObligado().getModalidad().getNumModalidad() 
					+  patron.getPatronSujetoObligado().getDigVerificador());
			
			if(patron.getPatronSujetoObligado().getFisica() == null && patron.getPatronSujetoObligado().getMoral() != null ) {
				comprobante.setNombrePatron(patron.getPatronSujetoObligado().getMoral().getRazonSocial());
			} else if(patron.getPatronSujetoObligado().getFisica() != null ) {
				String nombreR = "";
				nombreR+=(!StringUtils.isEmpty(patron.getPatronSujetoObligado().getFisica().getNombre()) ? patron.getPatronSujetoObligado().getFisica().getNombre().trim() : "");
				nombreR+=(!StringUtils.isEmpty(patron.getPatronSujetoObligado().getFisica().getPrimerApellido()) ? " " + patron.getPatronSujetoObligado().getFisica().getPrimerApellido().trim() : "");
				nombreR+=(!StringUtils.isEmpty(patron.getPatronSujetoObligado().getFisica().getSegundoApellido()) ? " " +patron.getPatronSujetoObligado().getFisica().getSegundoApellido().trim() : "");
				comprobante.setNombrePatron(nombreR);
			}
		}else{
//			comprobante.setRegistroPatronal("NO APLICA");
//			comprobante.setNombrePatron("NO APLICA");
			comprobante.setRegistroPatronal("-");
			comprobante.setNombrePatron("-");
		}
		
		//seteo de servicios
		serviciosDTO = grupoFamiliarService.getServiciosGrupoFamiliar(nss
				.getIdAsignacionNSS());
		comprobante.setServicios(serviciosDTO);

		//seteo de prestaciones
		lstPrestacionesDTO = grupoFamiliarService.getPrestacionesAsegurado(nss.getIdAsignacionNSS());
		comprobante.setPrestaciones(lstPrestacionesDTO);
		

		// Determinar si el asegurado tiene derecho a servicio medico
		for (ServiciosDTO item : serviciosDTO) {
			if (item.getIdServicio().longValue() == ServiciosPrestacionesEnum.SERVICIO_MEDICO.getId()){
				comprobante.setServicioMedico(item.getSiNo());
				
			}
		}

		// Se agrega informacion de la firma digital
		if (firmaElectronica != null) {
			comprobante.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
			comprobante.setSelloDigital(firmaElectronica.getRecibo());
			comprobante.setSecuenciaNotarial(firmaElectronica
					.getSecuenciaNotaria());
			comprobante.setNumeroSerie(firmaElectronica.getSerialCertificado());
		}

		if (usuario != null){// && usuario.getIdUmf() != null) {
			//integrantes = this.filtrarPorUmf(integrantes, usuario.getIdUmf());

			if(usuario.getIdUmf() != null) {

				comprobante.setBandera("");

				// Tambien se imprime informacion del usuario
				String nombre = ( usuario.getNomNombre() == null ? "" : usuario.getNomNombre() );
				String paterno = ( usuario.getNomPaterno() == null ? "" : usuario.getNomPaterno() );
				String materno = ( usuario.getNomMaterno() == null ? "" : usuario.getNomMaterno() );
				
				comprobante.setNombreUsuario(nombre + " " + paterno + " " + materno);
				
				comprobante.setDelegacionUsuario(usuario.getUsuarioFuncionario().getDelegacion().getDescripcion());
				comprobante.setUnidadUsuario(usuario.getUsuarioFuncionario().getUnidadMedicaFamiliar().getDescripcion());
			}
		}

		if(integrantes != null && !integrantes.isEmpty()) {
			for (GrupoFamiliar grupoFamiliar : integrantes) {
			
				if( grupoFamiliar != null  ){
					beneficiario = new BeneficiarioDTO();
					if (grupoFamiliar.getEstadoDerechohabiente().getIdEstadoDerechohabiente() == EstadoDerechohabienteEnum.BAJA.getId()  ||
						grupoFamiliar.getEstadoDerechohabiente().getIdEstadoDerechohabiente() == EstadoDerechohabienteEnum.FALLECIDO.getId()){ 
						beneficiario.setServicioMedico("NO");	
						
					} else if(grupoFamiliar.getEstadoDerechohabiente().getIdEstadoDerechohabiente() == EstadoDerechohabienteEnum.VIGENTE.getId() && 
							grupoFamiliar.getParentesco().getIdParentesco().longValue() != ParentescoEnum.ASEGURADO.getId() &&  
							grupoFamiliar.getParentesco().getIdParentesco().longValue() != ParentescoEnum.PENSIONADO.getId()){
							beneficiario.setServicioMedico("SI");	
					} else{
						beneficiario.setServicioMedico(comprobante.getServicioMedico());
					}
					if(grupoFamiliar.getParentesco().getIdParentesco().longValue() == ParentescoEnum.ASEGURADO.getId() ||  
							grupoFamiliar.getParentesco().getIdParentesco().longValue() != ParentescoEnum.PENSIONADO.getId()){
						break;
					}
					if(moda.getIdModalidad() == ModalidadEnum.TREINTAYTRES.getId() && !grupoFamiliar.getDerechohabiente().getNombre().equals("RECIEN NACIDO")){
						break;
					}
					
					beneficiario.setNombreBen(grupoFamiliar.getDerechohabiente()
							.getNombre());
					beneficiario.setPrimerApellidoBen(grupoFamiliar
							.getDerechohabiente().getPrimerApellido());
					beneficiario.setSegundoApellidoBen(grupoFamiliar
							.getDerechohabiente().getSegundoApellido());
					beneficiario.setCurpBen(grupoFamiliar.getDerechohabiente()
							.getCurp());
					beneficiario.setParentescoBen(grupoFamiliar.getParentesco()
							.getDescripcion());
					beneficiario.setFechaNacimientoBen(grupoFamiliar
							.getDerechohabiente().getFechaNacimiento());
					
					if( grupoFamiliar.getDerechohabiente().getLugarNacimiento() != null ){
						beneficiario.setLugarNacimientoAsegurado( grupoFamiliar.getDerechohabiente().getLugarNacimiento().getNombre() );
					}
					
					if(grupoFamiliar.getDerechohabiente().getFechaNacimiento() != null) {
						beneficiario.setEdadBen(DateUtils.getEdad(grupoFamiliar
								.getDerechohabiente().getFechaNacimiento()));
					} 
					
					beneficiario.setSexoBen(grupoFamiliar.getDerechohabiente()
							.getSexo().getDescripcion());
					beneficiario.setDelegacionBen(grupoFamiliar.getMedicoEnTurno()
							.getUnidadMedicaFamiliar().getSubdelegacion()
							.getDelegacion().getDescripcion());
					beneficiario.setUmfBen(grupoFamiliar.getMedicoEnTurno()
							.getUnidadMedicaFamiliar().getDescripcion());
					beneficiario.setTurno(grupoFamiliar.getMedicoEnTurno().getTurno()
							.getDescripcion());
					beneficiario.setConsultorio(grupoFamiliar.getMedicoEnTurno()
							.getConsultorio().getDescripcion());
					beneficiario.setAgregadoMedico(grupoFamiliar
							.getAgregadoMedico());
					beneficiario.setSituacionBen(grupoFamiliar
							.getEstadoDerechohabiente().getDescripcion());
					beneficiario.setVencimientoVigenciaBen(grupoFamiliar
							.getFechaFinVigencia());
					if (grupoFamiliar.getEstadoDerechohabiente()
							.getIdEstadoDerechohabiente() == EstadoDerechohabienteEnum.VIGENTE
							.getId()) {
						if (grupoFamiliar.getSubEstadoDerechohabiente()
								.getIdSubEstadoDerechohabiente() == SubestadoDerechohabienteEnum.TEMPORAL
								.getId()) {
							idCaracter = CaracterEnum.PROVISIONAL.getId();
						} else if (grupoFamiliar.getSubEstadoDerechohabiente()
								.getIdSubEstadoDerechohabiente() == SubestadoDerechohabienteEnum.PERMANENTE
								.getId()) {
							idCaracter = CaracterEnum.DEFINITIVO.getId();
						}
	
						prorroga = prorrogaDao.getProrrogaActiva(grupoFamiliar.getAsignacionNSS().getIdAsignacionNSS(),grupoFamiliar
								.getDerechohabiente().getIdPersona(), idCaracter);
	
						if (prorroga != null) {
							if (grupoFamiliar
									.getDerechohabiente()
									.getIdPersona()
									.equals(asegurado.getDerechohabiente()
											.getIdPersona())) {
								comprobante.setDetalleSituacion(prorroga.getTramite()
										.getTipoTramite().getDescripcion());
							}
							beneficiario.setDetalleSituacionBen(prorroga.getTramite()
									.getTipoTramite().getDescripcion());
	
						}
					} else if (grupoFamiliar.getEstadoDerechohabiente()
							.getIdEstadoDerechohabiente() == EstadoDerechohabienteEnum.BAJA
							.getId()) {
	
						List<Long> personas = new ArrayList<Long>();
						personas.add(grupoFamiliar.getDerechohabiente().getIdPersona());
						List<Long> estadosTramite = new ArrayList<Long>();
						estadosTramite.add(EstadoTramiteEnum.CERRADO.getId());
	
						List<Solicitud> solicituds = solicitudTramiteBusinessRemote
								.getSolicitudesPersona(personas, null, estadosTramite,
										null, null, nss.getIdPersona(), true, 1, true);
						boolean isAsegurado = grupoFamiliar.getDerechohabiente()
								.getIdPersona().equals(nss.getIdPersona());
	
						if (solicituds != null && !solicituds.isEmpty()) {
							Solicitud solBaja = solicituds.get(0);
							if (solBaja.getTipoSolicitud().getIdTipoSolicitud()
									.equals(TipoSolicitudEnum.BAJA.getId())) {
								if (isAsegurado) {
									comprobante.setDetalleSituacion(solBaja
											.getTramites().get(0).getTipoTramite()
											.getDescripcion());
								}
								beneficiario.setDetalleSituacionBen(solBaja
										.getTramites().get(0).getTipoTramite()
										.getDescripcion());
							}
						}
	
					}
	
					beneficiarios.add(beneficiario);
				}
				
			}
	}
		if (beneficiario != null && !beneficiarios.isEmpty())
			comprobante.setBeneficiarios(beneficiarios);
		else 
			comprobante.setBeneficiarios(new ArrayList<BeneficiarioDTO>());

		if(moda.getIdModalidad() == ModalidadEnum.TREINTAYTRES.getId() && beneficiarios.isEmpty()){
			comprobante.setMensajeBeneficiarios("NO APLICA");
		}
		
		datos.add(comprobante);

		plantillas.put("ConstanciaVigenciaDerechosBeneficiarios.jasper","SUBREPORTE_BENEFICIARIOS");
		
		

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("LOGO",
				new ClassPathResource("reportes/img/logo.jpg").getPath());
		parametros.put("LOGO_IMSS",
				new ClassPathResource("reportes/img/logo_imss_digital.jpg").getPath());
		
		ByteArrayOutputStream repo = null;
		try {
			if(patron.getCalidadParentesco().getIdParentesco().intValue() == ParentescoEnum.PENSIONADO.getId() &&
					!patronesVigentes){
				repo = manejadorReportes.ejecutaReporteSubreporte(parametros, datos, "ConstanciaVigenciaDerechosPensionado.jasper", plantillas);
			}else{
				plantillas.put("ConstanciaVigenciaDerechosServicios.jasper","SUBREPORTE_SERVICIOS");
				repo = manejadorReportes.ejecutaReporteSubreporte(parametros, datos, "ConstanciaVigenciaDerechos.jasper", plantillas);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DerechohabientesBusinessException("Error al generar el reporte de vigencia por internet" , e.getMessage());
		}

		return repo.toByteArray();

	}

	@Override
	public Object getConstanciaVigenciaInternetRecortado(AsignacionNSS nss, FirmaElectronica firmaElectronica, Usuario usuario) throws DerechohabientesBusinessException, Exception{
		return getConstanciaVigenciaInternetRecortadoMensaje(nss, firmaElectronica, usuario,null,true);
	}
	
	private Object getConstanciaVigenciaInternetRecortadoMensaje(AsignacionNSS nss, FirmaElectronica firmaElectronica, Usuario usuario, String mensaje,
			Boolean version2) throws DerechohabientesBusinessException, Exception {
		
		boolean muestraIntegrantes= false;
		String PATRON_JCF = "Y5845183325";
		String LEYENDA_JCF = "STPS JCF";
		
		boolean isPatronJCF = false;
		boolean isModalidad17 = false;
		boolean isPatron17ConServicios = false;
		boolean isBecarioIMSS = false;
		boolean isPatronModalidad32 = false;
		String descModalidad32 = null;
		
		List<GrupoFamiliar> integrantes = null;
		GrupoFamiliar	asegurado = null;
		
		List<BeneficiarioDTO> beneficiarios = new ArrayList<BeneficiarioDTO>();
		
		List<Modalidad> modalidades = null;

		BeneficiarioDTO beneficiario = new BeneficiarioDTO();
		String reportePrincipal = version2 ? "ConstanciaVigenciaDerechosRecortadoV2.jasper" :"ConstanciaVigenciaDerechosRecortado.jasper";
		String reportePensionado = version2 ? "ConstanciaVigenciaDerechosPensionadoV2.jasper" :"ConstanciaVigenciaDerechosPensionado.jasper";
		String subPatrones = version2 ? "ConstanciaVigenciaDerechosPatronesV2.jasper" : "ConstanciaVigenciaDerechosPatrones.jasper";
		String subBeneficiarios = version2 ? "ConstanciaVigenciaDerechosBeneficiariosV2.jasper" : "ConstanciaVigenciaDerechosBeneficiarios.jasper";
		String logo = version2 ? "reportes/img/headerPicture.png" : "reportes/img/logo.jpg";
		
		log.debug("generando la constancia con la info de BDTU para el NSS: " + nss.getIdAsignacionNSS());
		
		try{
		
		CabezaGrupoFamiliar patron = grupoFamiliarDaoLocal.getCabezaGrupoFamiliar(nss.getIdAsignacionNSS());
		long numintegrantes = grupoFamiliarService.getNumeroIntegrantesRegistrsdosPorParentesco(nss.getIdAsignacionNSS(), null);

		if(patron == null ) {
			String mensajeError = "El asegurado / pensionado con n&uacute;mero de seguridad social "+ nss.getNss() + " no se encuentra registrado a&uacute;n" +
					" como derechohabiente, por lo tanto no podr&aacute; obtener su comprobante de vigencia de derechos.";
			log.error(mensajeError);
			DerechohabientesBusinessException.throwException(mensajeError, mensajeError);
		}
		
		
		boolean aseguradoTienePatron = patron.getPatronSujetoObligado()==null?false:true;
		
		if(numintegrantes >0){
			try{
			asegurado = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(nss.getIdAsignacionNSS(), nss.getIdPersona());
			}catch(Exception e){
				log.error("no se encontro el asegurado en el grupo familiar idAsignacion: " + nss.getIdAsignacionNSS() + " idPersona: " + nss.getIdPersona(), e);
			}
		}
		
		if(asegurado == null){
			AsignacionNSS asignacion = asignacionNssDao.getAsignacionNSS(patron.getAsignacionNSS());
			asegurado = setGrupoFamiliarByAsingacionNSS(asignacion, patron);
		}

		ComprobanteVigenciaDerechosDTO comprobanteWS = new ComprobanteVigenciaDerechosDTO();
		try {
			comprobanteWS = vigenciaDerechosWS.getInfo(nss.getNss().substring(0, 10));
		} catch (Exception e){
			log.debug(e.getMessage() + " : " + nss.getNss());
			if (e.getMessage().contains("No existe el NSS solicitado")){
				//comprobanteWS.setServicioMedico("--");
				//JAS En caso de no estar adscrito a una UMF, se toma el valor del tag <ConDerechoSm> que regresa
				//el WS de cabeza de grupo
				comprobanteWS.setServicioMedico(patron.getConDerechoSm());
			}else {
				comprobanteWS.setServicioMedico(" ");
			}
		}	

		ComprobanteVigenciaDerechosDTO comprobante = new ComprobanteVigenciaDerechosDTO();
		comprobante.setEstadoDerechohabiente(patron.getEstadoDerechohabiente());
		comprobante.setSubEstadoDerechohabiente(patron.getSubEstadoDerechohabiente());
		
		comprobante.setFechaInicioVigencia(patron.getFechaInicioVigencia());
		comprobante.setFechaFinVigencia(patron.getFechaFinVigencia());
		
		List<ComprobanteVigenciaDerechosDTO> datos = new ArrayList<ComprobanteVigenciaDerechosDTO>();
		Map<String, String> plantillas = new HashMap<String, String>();
		
			
		//datos generales del asegurado
		comprobante.setNss(asegurado.getAsignacionNSS().getNssStr());
		comprobante.setNombreAsegurado(asegurado.getDerechohabiente().getNombre());
		comprobante.setPrimerApellidoAsegurado(asegurado.getDerechohabiente().getPrimerApellido());
		comprobante.setSegundoApellidoAsegurado(asegurado.getDerechohabiente().getSegundoApellido());
		comprobante.setCurp(asegurado.getDerechohabiente().getCurp());
		comprobante.setSexoAsegurado(asegurado.getDerechohabiente().getSexo() != null ? asegurado.getDerechohabiente().getSexo().getDescripcion() : this.VALOR_DEFAULT);
		comprobante.setLugarNacimientoAsegurado(asegurado.getDerechohabiente().getLugarNacimiento() != null ? asegurado.getDerechohabiente().getLugarNacimiento().getNombre() : this.VALOR_DEFAULT);
		
		if(asegurado.getDerechohabiente().getFechaNacimiento() != null) {
			comprobante.setFechaNacimientoAsegurado(DateUtils.dateFormat(asegurado.getDerechohabiente().getFechaNacimiento()));
		} else {
			comprobante.setFechaNacimientoAsegurado(this.VALOR_DEFAULT);
		}
		
		comprobante.setSituacion(asegurado.getEstadoDerechohabiente().getDescripcion());
		
		if(asegurado.getMedicoEnTurno() != null){
			comprobante.setDelegacion(asegurado.getMedicoEnTurno().getUnidadMedicaFamiliar().getSubdelegacion().getDelegacion().getDescripcion());
			comprobante.setUmf(asegurado.getMedicoEnTurno().getUnidadMedicaFamiliar().getDescripcion());
			comprobante.setTurno(asegurado.getMedicoEnTurno().getTurno().getDescripcion());
			comprobante.setConsultorio(asegurado.getMedicoEnTurno().getConsultorio().getDescripcion());
			comprobante.setAgregadoMedico(asegurado.getAgregadoMedico());
		}else{
			comprobante.setDelegacion(this.VALOR_DEFAULT);
			comprobante.setUmf(this.VALOR_DEFAULT);
			comprobante.setTurno(this.VALOR_DEFAULT);
			comprobante.setConsultorio(this.VALOR_DEFAULT);
			comprobante.setAgregadoMedico(this.VALOR_DEFAULT);
		}
		
		comprobante.setFechaExpedicion(new Date());
		
		//validacion de fecha de emicion si tiene mas de una mdalidad sera la fecha del dia si no la que regrese el ws
		List<PatronDTO> patronesAc = new ArrayList<PatronDTO>();
		List<SujetoObligado> patronesActivos = grupoFamiliarService.getPatronesAsegurado(nss);
		if(patronesActivos != null && !patronesActivos.isEmpty()){
			Map<String,Modalidad> descripcionesModalidad = new HashMap<String, Modalidad>();
		
			for(SujetoObligado pat: patronesActivos){
				
					PatronDTO patDto = new PatronDTO();
					patDto.setRegistroPatronal(pat.getNumeroRegistroPatronal() + pat.getModalidad().getNumModalidad() + pat.getDigVerificador());
					patDto.setModalidad(pat.getModalidad().getNumModalidad());
					
					if(pat.getFisica() == null) {
						patDto.setNombreRazonSocial(pat.getMoral().getRazonSocial());
					} else {
						StringBuilder nombreR = new StringBuilder();
						nombreR.append(!StringUtils.isEmpty(pat.getFisica().getNombre()) ? pat.getFisica().getNombre().trim() : "");
						nombreR.append(!StringUtils.isEmpty(pat.getFisica().getPrimerApellido()) ? " " + pat.getFisica().getPrimerApellido().trim() : "");
						nombreR.append(!StringUtils.isEmpty(pat.getFisica().getSegundoApellido()) ? " " + pat.getFisica().getSegundoApellido().trim() : "");
						
						patDto.setNombreRazonSocial(nombreR.toString());
					}
					
					
					if(isModalidad32(pat.getModalidad())) {
						isPatronModalidad32 = true;
						if(patDto.getRegistroPatronal().equals(PATRON_JCF)){
							isPatronJCF = true;
							patDto.setDesModalidad(DES_MODALIDAD_32_JCF);
							descModalidad32 = DES_MODALIDAD_32_JCF;
						}else if (patronServices.isRegistroPatronalnstitucionEducativa(pat.getNumeroRegistroPatronal() + pat.getModalidad().getNumModalidad())) {
							patDto.setDesModalidad(DES_MODALIDAD_32_INST_EDUCATIVA);
							descModalidad32 = DES_MODALIDAD_32_INST_EDUCATIVA;
						}else {
							patDto.setDesModalidad(DES_MODALIDAD_32_CFE);
							descModalidad32 = DES_MODALIDAD_32_CFE;
						}
					}
					
					patronesAc.add(patDto);
					descripcionesModalidad.put(pat.getModalidad().getNumModalidad(),pat.getModalidad());
					
										
					if(!asegurado.getEstadoDerechohabiente().getIdEstadoDerechohabiente().equals(EstadoDerechohabienteEnum.BAJA.getId())) {
						for(String rpMod17: RPS_17) {
							String rpValidar = patDto.getRegistroPatronal();
							if(rpValidar.equalsIgnoreCase(rpMod17)) {
								isModalidad17 = true;
								if(rpValidar.equalsIgnoreCase(RP17_CON_SERVICIO)) {
									isPatron17ConServicios = true;
								}
							}
						}
					}
					
				}
			
			
			modalidades = new ArrayList<Modalidad>(descripcionesModalidad.values());
			for(Modalidad moda: modalidades) {
				if(isModalidad1013143034353638424344(moda)) {
					muestraIntegrantes= true;
					break;
				}
			}
			
			//se evalua si tiene madalidad 32 no tenga una modalidad con mayor jerarquia para mostar seccion en el reporte
			if(isPatronModalidad32) {
				for(Modalidad moda: modalidades) {
					if(isModalidad1013141730354344(moda)) {
						isPatronModalidad32 = false;
						break;
					}
				}
			}
			
		}else{
	
			if(aseguradoTienePatron) {
				PatronDTO patronDTO = new PatronDTO();
				String nrp =  patron.getPatronSujetoObligado()
						.getNumeroRegistroPatronal() + patron.getPatronSujetoObligado().getModalidad().getNumModalidad() 
						+  patron.getPatronSujetoObligado().getDigVerificador();
				String nrpSinDV = patron.getPatronSujetoObligado()
						.getNumeroRegistroPatronal() + patron.getPatronSujetoObligado().getModalidad().getNumModalidad();
				
				if(!nrp.substring(0,10).equalsIgnoreCase(PATRON_BECARIO_IMSS_SIN_MODALIDAD) ){
					try{
						patronDTO.setRegistroPatronal(nrp);
						if(patron.getPatronSujetoObligado().getFisica() == null && patron.getPatronSujetoObligado().getMoral() != null ) {
							patronDTO.setNombreRazonSocial(patron.getPatronSujetoObligado().getMoral().getRazonSocial());
						} else if(patron.getPatronSujetoObligado().getFisica() != null ) {
							String nombreR = "";
							nombreR+=(!StringUtils.isEmpty(patron.getPatronSujetoObligado().getFisica().getNombre()) ? patron.getPatronSujetoObligado().getFisica().getNombre().trim() : "");
							nombreR+=(!StringUtils.isEmpty(patron.getPatronSujetoObligado().getFisica().getPrimerApellido()) ? " " + patron.getPatronSujetoObligado().getFisica().getPrimerApellido().trim() : "");
							nombreR+=(!StringUtils.isEmpty(patron.getPatronSujetoObligado().getFisica().getSegundoApellido()) ? " " +patron.getPatronSujetoObligado().getFisica().getSegundoApellido().trim() : "");
							patronDTO.setNombreRazonSocial(nombreR);
						}
						
					}catch(NullPointerException e){
						// -----------------------------------------------
						// Pensionado puede no tener patrón
						// -----------------------------------------------
						log.debug("Sujeto Obligado NULL, idAsignacion:"+nss.getIdAsignacionNSS());
					}
					
					//se evalua si el patron en modadlidad 32 para mostar leyendas en reporte
					if(isModalidad32(patron.getPatronSujetoObligado().getModalidad())){
						isPatronModalidad32 = true;
						if(nrp.equals(PATRON_JCF)){
							isPatronJCF = true;
							patronDTO.setDesModalidad(DES_MODALIDAD_32_JCF);
							descModalidad32 = DES_MODALIDAD_32_JCF;
						}else if (patronServices.isRegistroPatronalnstitucionEducativa(nrpSinDV)){
							patronDTO.setDesModalidad(DES_MODALIDAD_32_INST_EDUCATIVA);
							descModalidad32 = DES_MODALIDAD_32_INST_EDUCATIVA;
						}else {
							patronDTO.setDesModalidad(DES_MODALIDAD_32_CFE);
							descModalidad32 = DES_MODALIDAD_32_CFE;
						}
					}
					
					patronesAc.add(patronDTO);
				}else {
					isBecarioIMSS = true;
				}
				
				
				if(!asegurado.getEstadoDerechohabiente().getIdEstadoDerechohabiente().equals(EstadoDerechohabienteEnum.BAJA.getId())) {
					for(String rpMod17: RPS_17) {
						if(nrp.equalsIgnoreCase(rpMod17)) {
							isModalidad17 = true;
							if(nrp.equalsIgnoreCase(RP17_CON_SERVICIO)) {
								isPatron17ConServicios = true;
							}
						}
					}
				}
				
			}
			
		}
		
		comprobante.setPatrones(patronesAc);
		
		if(isPatronJCF) {
			if(comprobante.getAgregadoMedico() != null) {
				comprobante.setAgregadoMedico(comprobante.getAgregadoMedico() + " " + LEYENDA_JCF);
			} else {
				comprobante.setAgregadoMedico(LEYENDA_JCF);
			}
		}
		
		comprobante.setModalidad17(isModalidad17);
		comprobante.setPatron17ConServicios(isPatron17ConServicios);
		
		
		if( patron.getPatronSujetoObligado() != null && !isBecarioIMSS){
			Modalidad moda = patron.getPatronSujetoObligado().getModalidad();
			log.error("La modalidad es " + moda.getNumModalidad());
			log.error("La clave de la modalidad es " + moda.getIdModalidad());
			//se tiene que volver a evaluar las modalidades por aquellos que estan en conservacion de derechos y si no tubo algo modalidad vigente
			if(!muestraIntegrantes ){
				muestraIntegrantes= isModalidad1013143034353638424344(moda);
			}
			
		}else{
			if( patron.getCalidadParentesco().getIdParentesco().equals(ParentescoEnum.PENSIONADO.getId()) || isBecarioIMSS ){
				muestraIntegrantes= true;
			}
		}
		
		
		if((numintegrantes >0 && muestraIntegrantes) || (numintegrantes >0 && patron.getCalidadParentesco().getIdParentesco().intValue() == ParentescoEnum.PENSIONADO.getId())){
			integrantes = grupoFamiliarDaoLocal.findGrupoFamiliar(nss.getIdAsignacionNSS());
		}
		if(!muestraIntegrantes){
			comprobante.setMensajeBeneficiarios("NO APLICA");
		}
		
		//validacion para saber de donde se sacan los servicios de la prorroga o si tiene relacion de la 
		//modalidad del patron
		 if(asegurado.getEstadoDerechohabiente().getIdEstadoDerechohabiente().longValue() == EstadoDerechohabienteEnum.VIGENTE_POR_PRORRGA.getId()){
			 //comprobante.setServicioMedico("SI");
			 comprobante.setFechaValidezConstancia(new Date());
			 comprobante.setServicioIncapacidad("NO");
		 }else if(aseguradoTienePatron &&
				 new String(patron.getPatronSujetoObligado().getNumeroRegistroPatronal() +
						 patron.getPatronSujetoObligado().getModalidad().getNumModalidad()).equalsIgnoreCase(PATRON_BECARIO_IMSS_SIN_MODALIDAD)){
			 log.debug("seteando los servicios cuando tiene patron  y es becario[" + aseguradoTienePatron+ "]");
				// comprobante.setServicioMedico("SI");
				 comprobante.setFechaValidezConstancia(new Date());
				 comprobante.setServicioIncapacidad("NO");
		 }else{
			 //seteo de servicios
			 log.debug("seteando los servicios cuando no tiene prorroga y no tiene patrones [" + aseguradoTienePatron+ "]");
			 List<ServiciosDTO> serviciosDTO = grupoFamiliarService.getServiciosGrupoFamiliar(nss
					 .getIdAsignacionNSS());
			 // Determinar si el asegurado tiene derecho a servicio medico
			 for (ServiciosDTO item : serviciosDTO) {
				 if (item.getIdServicio().longValue() == ServiciosPrestacionesEnum.SERVICIO_MEDICO.getId()){
					// comprobante.setServicioMedico(item.getSiNo());
					 if(item.getSiNo().equalsIgnoreCase("SI")){
						 comprobante.setFechaValidezConstancia(new Date() );
					 }
				 }
				 if (item.getIdServicio().longValue() == ServiciosPrestacionesEnum.EXPEDICION_INCAPACIDAD_TRABAJO.getId()){
					 comprobante.setServicioIncapacidad(item.getSiNo());
				 }
			 }
		 }

		 comprobante.setServicioMedico(comprobanteWS.getServicioMedico());

		// Se agrega informacion de la firma digital
		if (firmaElectronica != null) {
			comprobante.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
			comprobante.setSelloDigital(firmaElectronica.getRecibo());
			comprobante.setSecuenciaNotarial(firmaElectronica
					.getSecuenciaNotaria());
			comprobante.setNumeroSerie(firmaElectronica.getSerialCertificado());
		}

		if (usuario != null && usuario.getIdUmf() != null) {

				comprobante.setBandera("");

				// Tambien se imprime informacion del usuario
				String nombre = ( usuario.getNomNombre() == null ? "" : usuario.getNomNombre() );
				String paterno = ( usuario.getNomPaterno() == null ? "" : usuario.getNomPaterno() );
				String materno = ( usuario.getNomMaterno() == null ? "" : usuario.getNomMaterno() );
				
				comprobante.setNombreUsuario(nombre + " " + paterno + " " + materno);
				
				comprobante.setDelegacionUsuario(usuario.getUsuarioFuncionario().getDelegacion().getDescripcion());
				comprobante.setUnidadUsuario(usuario.getUsuarioFuncionario().getUnidadMedicaFamiliar().getDescripcion());
		}

		if(integrantes != null && !integrantes.isEmpty()) {
			
			for (GrupoFamiliar grupoFamiliar : integrantes) {
				if( grupoFamiliar != null  ){
				
					beneficiario = new BeneficiarioDTO();
/*					if (grupoFamiliar.getEstadoDerechohabiente().getIdEstadoDerechohabiente() == EstadoDerechohabienteEnum.BAJA.getId()  ||
						grupoFamiliar.getEstadoDerechohabiente().getIdEstadoDerechohabiente() == EstadoDerechohabienteEnum.FALLECIDO.getId()){ 
						beneficiario.setServicioMedico("NO");	
						
					} else if(grupoFamiliar.getEstadoDerechohabiente().getIdEstadoDerechohabiente() == EstadoDerechohabienteEnum.VIGENTE.getId() && 
							grupoFamiliar.getParentesco().getIdParentesco().longValue() != ParentescoEnum.ASEGURADO.getId() &&  
							grupoFamiliar.getParentesco().getIdParentesco().longValue() != ParentescoEnum.PENSIONADO.getId()){
							beneficiario.setServicioMedico("SI");	
					} else{
						beneficiario.setServicioMedico(comprobante.getServicioMedico());
					}
*/					
					if (comprobanteWS.getBeneficiarios() != null) {
						for (BeneficiarioDTO benef : comprobanteWS.getBeneficiarios()) {
							if ( grupoFamiliar.getDerechohabiente().getIdPersona() ==  benef.getIdPersona().longValue()){
								beneficiario.setServicioMedico(benef.getServicioMedico());
								beneficiario.setAgregadoMedico(benef.getAgregadoMedico());
							}
						}
					}
					beneficiario.setNombreBen(grupoFamiliar.getDerechohabiente().getNombre());
					beneficiario.setPrimerApellidoBen(grupoFamiliar.getDerechohabiente().getPrimerApellido());
					beneficiario.setSegundoApellidoBen(grupoFamiliar.getDerechohabiente().getSegundoApellido());
					beneficiario.setCurpBen(grupoFamiliar.getDerechohabiente().getCurp());
					beneficiario.setParentescoBen(grupoFamiliar.getParentesco().getDescripcion());
					beneficiario.setFechaNacimientoBen(grupoFamiliar.getDerechohabiente().getFechaNacimiento());
					
					if( grupoFamiliar.getDerechohabiente().getLugarNacimiento() != null ){
						beneficiario.setLugarNacimientoAsegurado( grupoFamiliar.getDerechohabiente().getLugarNacimiento().getNombre() );
					}
					
					if(grupoFamiliar.getDerechohabiente().getFechaNacimiento() != null) {
						beneficiario.setEdadBen(DateUtils.getEdad(grupoFamiliar
								.getDerechohabiente().getFechaNacimiento()));
					} 
					
					beneficiario.setSexoBen(grupoFamiliar.getDerechohabiente()
							.getSexo().getDescripcion());
					
					
					if(grupoFamiliar.getMedicoEnTurno() != null){
						beneficiario.setDelegacionBen(grupoFamiliar.getMedicoEnTurno()
								.getUnidadMedicaFamiliar().getSubdelegacion()
								.getDelegacion().getDescripcion());
						beneficiario.setUmfBen(grupoFamiliar.getMedicoEnTurno()
								.getUnidadMedicaFamiliar().getDescripcion());
						beneficiario.setTurno(grupoFamiliar.getMedicoEnTurno().getTurno()
								.getDescripcion());
						beneficiario.setConsultorio(grupoFamiliar.getMedicoEnTurno()
								.getConsultorio().getDescripcion());
					}else{
						beneficiario.setDelegacionBen(this.VALOR_DEFAULT);
						beneficiario.setUmfBen(this.VALOR_DEFAULT);
						beneficiario.setTurno(this.VALOR_DEFAULT);
						beneficiario.setConsultorio(this.VALOR_DEFAULT);
					}
					
					
					
//					beneficiario.setAgregadoMedico(grupoFamiliar
//							.getAgregadoMedico());
					beneficiario.setSituacionBen(grupoFamiliar
							.getEstadoDerechohabiente().getDescripcion());
					beneficiario.setVencimientoVigenciaBen(grupoFamiliar
							.getFechaFinVigencia());
					if(grupoFamiliar.getParentesco().getIdParentesco().longValue() != ParentescoEnum.ASEGURADO.getId() &&  
							grupoFamiliar.getParentesco().getIdParentesco().longValue() != ParentescoEnum.PENSIONADO.getId()){
						
						beneficiarios.add(beneficiario);
					}
					
					
				}
				
			}
		}
		
		if (!isBecarioIMSS) {
		    setCalculoFechaFinVigenciaConstancia(patron, comprobante, modalidades);
		} else {
			//Para los denominados Becarios en la Constancia de Vigencia de Derechos se deberá¡ 
			//informar en el campo: Fecha de Constancia la fecha de generación de la constancia de vigencia.
			Date fechaHoy = org.apache.commons.lang3.time.DateUtils.truncate(new Date(), Calendar.DATE);
			comprobante.setFechaFinVigencia(fechaHoy);
		}

		 //Se coloca la fecha "12/12/2222" para poner en Vigencia: PENSIONADO cuando el Pensionado tenga una pension activa (RNGD05244)
		 if(patron.getCalidadParentesco().getIdParentesco().equals(ParentescoEnum.PENSIONADO.getId()) &&  grupoFamiliarDaoLocal.findPensionActiva(nss.getNss())){
			comprobante.setFechaValidezConstancia(new SimpleDateFormat("dd/MM/yyyy").parse("12/12/2222"));
		 }
		
		if (beneficiarios != null && !beneficiarios.isEmpty()){
			comprobante.setBeneficiarios(beneficiarios);
	
		}
		else{ 
			comprobante.setBeneficiarios(new ArrayList<BeneficiarioDTO>());
	
		}
		
		if(mensaje != null){
			comprobante.setMensajeBeneficiarios(mensaje);
		}
		
		log.error("Es modalidad 17 el asegurado: " + isModalidad17);
		log.error("Es modalidad 17 y tiene servicios: " + isPatron17ConServicios);
		
		log.error("la modalidad 32 queda como [" + isPatronModalidad32  + "] y la descripcion como descModalidad32 "+ descModalidad32);
		log.debug("la modalidad 32 queda como [" + isPatronModalidad32  + "] y la descripcion como descModalidad32 "+ descModalidad32);
		comprobante.setModalidad32(isPatronModalidad32);
		datos.add(comprobante);
		plantillas.put(subBeneficiarios,"SUBREPORTE_BENEFICIARIOS");
		
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("LOGO",new ClassPathResource(logo).getPath());
		parametros.put("LOGO_IMSS",new ClassPathResource("reportes/img/logo_imss_digital.jpg").getPath());
		parametros.put("descModalidad32",descModalidad32);
		
		if(version2) {
			parametros.put("PIE", new ClassPathResource("reportes/img/footer.png").getPath());
		}
		
		//Seteo de parametro de foto del asegurado
//		parametros.put("FOTO_ASEGURADO",this.getFotoAseguradoADIMSS(nss.getNss()));
		
		ByteArrayOutputStream repo = null;
		try {
			if(patron.getCalidadParentesco().getIdParentesco().intValue() == ParentescoEnum.PENSIONADO.getId() 
					|| asegurado.getEstadoDerechohabiente().getIdEstadoDerechohabiente().longValue() 
					== EstadoDerechohabienteEnum.VIGENTE_POR_PRORRGA.getId() || isBecarioIMSS){
				comprobante.setMensajeBeneficiarios(null);
				repo = manejadorReportes.ejecutaReporteSubreporte(parametros, datos, reportePensionado, plantillas);
			}else{
				plantillas.put(subPatrones,"SUBREPORTE_PATRONES");
				repo = manejadorReportes.ejecutaReporteSubreporte(parametros, datos, reportePrincipal, plantillas);
			}
		} catch (Exception e) {
			log.error("Oourrio un error inesperado: " + nss.getNss(), e);
			throw new DerechohabientesBusinessException("Error al generar el reporte de vigencia por internet" , e.getMessage());
		}

		return repo.toByteArray();
		} catch (Exception e) {
			log.error("Oourrio un error inesperado: " + nss.getNss(), e);
			throw new DerechohabientesBusinessException("Error al generar el reporte de vigencia por internet" , e.getMessage());
		}
	}

	@Override
	public Object getConstanciaVigenciaWS(AsignacionNSS nss, FirmaElectronica firmaElectronica, Usuario usuario) throws DerechohabientesBusinessException, Exception {
		CabezaGrupoFamiliar patron = null;
		Boolean version2 = true ;
		
		boolean isBecarioIMSS = false;
		
		String reportePrincipal = version2 ? "ConstanciaVigenciaDerechosRecortadoV2.jasper" :"ConstanciaVigenciaDerechosRecortado.jasper";
		String reportePensionado = version2 ? "ConstanciaVigenciaDerechosPensionadoV2.jasper" :"ConstanciaVigenciaDerechosPensionado.jasper";
		String subPatrones = version2 ? "ConstanciaVigenciaDerechosPatronesV2.jasper" : "ConstanciaVigenciaDerechosPatrones.jasper";
		String subBeneficiarios = version2 ? "ConstanciaVigenciaDerechosBeneficiariosV2.jasper" : "ConstanciaVigenciaDerechosBeneficiarios.jasper";
		String logo = version2 ? "reportes/img/headerPicture.png" : "reportes/img/logo.jpg";
		
		log.debug("generando la constancia a partir del WS de NSS");
		
		try{
		if(nss.getIdAsignacionNSS() != null && nss.getIdAsignacionNSS().intValue() != 0){
			patron = grupoFamiliarDaoLocal.getCabezaGrupoFamiliar(nss.getIdAsignacionNSS());
		}
		
		if (patron == null && nss.getIdAsignacionNSS() != null && nss.getIdAsignacionNSS().intValue() != 0) {
			String mensajeError = "El asegurado / pensionado con n&uacute;mero de seguridad social " + nss.getNss()
					+ " no se encuentra registrado a&uacute;n" + " como derechohabiente, por lo tanto no podr&aacute; obtener su comprobante de vigencia de derechos.";
			DerechohabientesBusinessException.throwException(mensajeError, mensajeError);
		}
		
		boolean aseguradoTienePatron = patron.getPatronSujetoObligado()==null?false:true;
		
		//Si el WS regresa que el asegurado no es inconsistente el reporte lo generamos de la BD
		//Si regresa que los beneficiarios son inconsistentes generamos el reporte de la BD pero mandamos el mensaje de error.
		//Si el WS regresa que el asegurado es inconsistente sacamos toda la información de la respuesta del WS.
		AsignacionNSS asignacion = grupoFamiliarService.getAsignacionNssSinPersona(nss.getNss(), true);
		if(!asignacion.isEstudiante()){
			if(asignacion != null  && asignacion.getEstadoInconsistencia() != null && asignacion.getEstadoInconsistencia().intValue() != ASEGURADO_INCONSISTENTE){
				if(asignacion.getEstadoInconsistencia().intValue() == BENEFICIARIO_INCONSISTENTE ){
					return getConstanciaVigenciaInternetRecortadoMensaje(asignacion, firmaElectronica, usuario, Constants.BENEFICIARIOS_INCONSISTENTE, true);
				}else{
					return getConstanciaVigenciaInternetRecortado(asignacion, firmaElectronica, usuario);
				}
			}
		}
		
		boolean muestraIntegrantes= false;
		ComprobanteVigenciaDerechosDTO comprobante = vigenciaDerechosWS.getInfo(nss.getNss().substring(0, 10));

		List<ComprobanteVigenciaDerechosDTO> datos = new ArrayList<ComprobanteVigenciaDerechosDTO>();
		Map<String, String> plantillas = new HashMap<String, String>();
		
		//Se seccion de patrones
		if(patron != null){
			comprobante.setTipoMovimiento(patron.getTipoMovtoAsegurado().getDesTipoMvtoAsegurado());
			comprobante.setUltimoMovimiento(patron.getFechaUltimoMovAfiliacion());
			if(aseguradoTienePatron) {
				comprobante.setModalidadPatron("("+patron.getPatronSujetoObligado().getModalidad().getNumModalidad()+") " + patron.getPatronSujetoObligado().getModalidad().getDesCorta());
			}
		}
		
		comprobante.setFechaExpedicion(new Date());
		
		
		if(comprobante.getServicioMedico().equalsIgnoreCase("NO")){
			comprobante.setFechaValidezConstancia(null);
		}else{
			comprobante.setFechaValidezConstancia(new Date());
		}
		
		
		
		List<PatronDTO> patronesAc = new ArrayList<PatronDTO>();
		List<SujetoObligado> patronesActivos = null;
		
		
		if(!asignacion.isEstudiante()){
			if(nss.getIdAsignacionNSS() != null && nss.getIdAsignacionNSS() != 0){
				patronesActivos = grupoFamiliarService.getPatronesAsegurado(nss);
			}
		}
		
		
		if(patronesActivos != null && !patronesActivos.isEmpty()){
			Map<String,Modalidad> descripcionesModalidad = new HashMap<String, Modalidad>();
			for(SujetoObligado pat: patronesActivos){
					
					PatronDTO patDto = new PatronDTO();
					patDto.setRegistroPatronal(pat.getNumeroRegistroPatronal() + pat.getModalidad().getNumModalidad() + pat.getDigVerificador());
					patDto.setModalidad(pat.getModalidad().getNumModalidad());
					
					if(pat.getFisica() == null) {
						patDto.setNombreRazonSocial(pat.getMoral().getRazonSocial());
					} else {
						String nombreR = "";
						nombreR+=(!StringUtils.isEmpty(pat.getFisica().getNombre()) ? pat.getFisica().getNombre().trim() : "");
						nombreR+=(!StringUtils.isEmpty(pat.getFisica().getPrimerApellido()) ? " " + pat.getFisica().getPrimerApellido().trim() : "");
						nombreR+=(!StringUtils.isEmpty(pat.getFisica().getSegundoApellido()) ? " " + pat.getFisica().getSegundoApellido().trim() : "");
						
						patDto.setNombreRazonSocial(nombreR);
					}
					
					patronesAc.add(patDto);
					descripcionesModalidad.put(pat.getModalidad().getNumModalidad(),pat.getModalidad());
				
			}
			
			List<Modalidad> modalidades = new ArrayList<Modalidad>(descripcionesModalidad.values());
			for(Modalidad moda: modalidades) {
				if(	isModalidad1013143034353638424344(moda)) {
					muestraIntegrantes= true;
					break;
				}
				
			}
			
		}else{
			if(patron != null && aseguradoTienePatron){
				String nrp = patron.getPatronSujetoObligado()
						.getNumeroRegistroPatronal() + patron.getPatronSujetoObligado().getModalidad().getNumModalidad()
						+  patron.getPatronSujetoObligado().getDigVerificador();
				
				if(! nrp.substring(0,10).equalsIgnoreCase(PATRON_BECARIO_IMSS_SIN_MODALIDAD)) {
					PatronDTO patronDTO = new PatronDTO();
					patronDTO.setRegistroPatronal(nrp); 
					
					if(patron.getPatronSujetoObligado().getFisica() == null && patron.getPatronSujetoObligado().getMoral() != null ) {
						patronDTO.setNombreRazonSocial(patron.getPatronSujetoObligado().getMoral().getRazonSocial());
					} else if(patron.getPatronSujetoObligado().getFisica() != null ) {
						String nombreR = "";
						nombreR+=(!StringUtils.isEmpty(patron.getPatronSujetoObligado().getFisica().getNombre()) ? patron.getPatronSujetoObligado().getFisica().getNombre().trim() : "");
						nombreR+=(!StringUtils.isEmpty(patron.getPatronSujetoObligado().getFisica().getPrimerApellido()) ? " " + patron.getPatronSujetoObligado().getFisica().getPrimerApellido().trim() : "");
						nombreR+=(!StringUtils.isEmpty(patron.getPatronSujetoObligado().getFisica().getSegundoApellido()) ? " " +patron.getPatronSujetoObligado().getFisica().getSegundoApellido().trim() : "");
						patronDTO.setNombreRazonSocial(nombreR);
					}
					patronesAc.add(patronDTO);
				}else{
					isBecarioIMSS = true;
				}
			}
			
		}
		
		comprobante.setPatrones(patronesAc);
		
		if(patron != null && aseguradoTienePatron && !isBecarioIMSS){
			Modalidad moda = patron.getPatronSujetoObligado().getModalidad();
			//se tiene que volver a evaluar las modalidades por aquellos que estan en conservacion de derechos y si no tubo algo modalidad vigente
			if(!muestraIntegrantes){
					muestraIntegrantes= isModalidad1013143034353638424344(moda);
				}
		}
		
		if(!muestraIntegrantes){
			comprobante.setMensajeBeneficiarios("NO APLICA");
		}
		
		/*
		if(nss.getIdAsignacionNSS() != null && nss.getIdAsignacionNSS() != 0){
			serviciosDTO = grupoFamiliarService.getServiciosGrupoFamiliar(nss.getIdAsignacionNSS());
			lstPrestacionesDTO = grupoFamiliarService.getPrestacionesAsegurado(nss.getIdAsignacionNSS());
		}
		comprobante.setServicios(serviciosDTO);
		comprobante.setPrestaciones(lstPrestacionesDTO); 	 
		*/
		

//		for (ServiciosDTO item : serviciosDTO) {
//			if (item.getIdServicio().longValue() == ServiciosPrestacionesEnum.SERVICIO_MEDICO.getId()){
//				comprobante.setServicioMedico(item.getSiNo());
//			}
//		}

		if (firmaElectronica != null) {
			comprobante.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
			comprobante.setSelloDigital(firmaElectronica.getRecibo());
			comprobante.setSecuenciaNotarial(firmaElectronica.getSecuenciaNotaria());
			comprobante.setNumeroSerie(firmaElectronica.getSerialCertificado());
		}

		if (usuario != null){
			if(usuario.getIdUmf() != null) {
				comprobante.setBandera("");
				String nombre = ( usuario.getNomNombre() == null ? "" : usuario.getNomNombre() );
				String paterno = ( usuario.getNomPaterno() == null ? "" : usuario.getNomPaterno() );
				String materno = ( usuario.getNomMaterno() == null ? "" : usuario.getNomMaterno() );
				comprobante.setNombreUsuario(nombre + " " + paterno + " " + materno);
				comprobante.setDelegacionUsuario(usuario.getUsuarioFuncionario().getDelegacion().getDescripcion());
				comprobante.setUnidadUsuario(usuario.getUsuarioFuncionario().getUnidadMedicaFamiliar().getDescripcion());
			}
		}

		if (comprobante.getBeneficiarios() == null){
			comprobante.setBeneficiarios(new ArrayList<BeneficiarioDTO>());
		}
		
		
		comprobante.setMensajeBeneficiarios(Constants.ASEGURADO_INCONSISTENTE);
		
		datos.add(comprobante);
		
		plantillas.put(subBeneficiarios,"SUBREPORTE_BENEFICIARIOS");
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("LOGO", new ClassPathResource(logo).getPath());
		parametros.put("LOGO_IMSS", new ClassPathResource("reportes/img/logo_imss_digital.jpg").getPath());
		
		if(version2) {
			parametros.put("PIE", new ClassPathResource("reportes/img/footer.png").getPath());
		}
		
		//Seteo de parametro de foto del asegurado
//		parametros.put("FOTO_ASEGURADO",this.getFotoAseguradoADIMSS(nss.getNss()));
				
		ByteArrayOutputStream repo = null;
		try {
			if(patron != null && (patron.getCalidadParentesco().getIdParentesco().intValue() == ParentescoEnum.PENSIONADO.getId()
					|| patron.getEstadoDerechohabiente().getIdEstadoDerechohabiente().longValue() 
					== EstadoDerechohabienteEnum.VIGENTE_POR_PRORRGA.getId())|| isBecarioIMSS){
				repo = manejadorReportes.ejecutaReporteSubreporte(parametros, datos, reportePensionado, plantillas);
			}else{
//				plantillas.put("ConstanciaVigenciaDerechosServicios.jasper","SUBREPORTE_SERVICIOS");
//				repo = manejadorReportes.ejecutaReporteSubreporte(parametros, datos, "ConstanciaVigenciaDerechos.jasper", plantillas);
				plantillas.put(subPatrones,"SUBREPORTE_PATRONES");
				repo = manejadorReportes.ejecutaReporteSubreporte(parametros, datos, reportePrincipal, plantillas);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DerechohabientesBusinessException("Error al generar el reporte de vigencia por internet" , e.getMessage());
		}
		
		return repo.toByteArray();
		}catch(Exception e){
			log.error("Ocurrio un erro al generar el reporte de vigencia " ,e);
			throw new DerechohabientesBusinessException("Error al generar el reporte de vigencia por internet" , e.getMessage());
		}

		

	}
	
	@Override
	public Object generarComprobanteTramiteARCO(Solicitud solicitud) throws Exception {


		String plantilla = "comprobanteARCO.jrxml";

		Map<String, Object> parametros = new HashMap<String, Object>();

		parametros.put(JRParameter.REPORT_LOCALE, new Locale("es","MX"));

		parametros.put("folioSolicitud", solicitud.getNoFolioSolicitud());
		parametros.put("cadenaOriginal", solicitud.getCadenaOriginal());
		parametros.put("selloDigital", solicitud.getSelloDigital());
		parametros.put("secuenciaNotaria", solicitud.getSecuenciaDeNotaria());
		parametros.put("numeroSerie", solicitud.getNumeroSerieCertificado());
		parametros.put("usuario", solicitud.getSolicitante() != null ? solicitud.getSolicitante().getUsuario() : "");
		parametros.put("nomUsuario", solicitud.getSolicitante() != null ? solicitud.getSolicitante().getFisica().getPrimerApellido()
				+ " " + solicitud.getSolicitante().getFisica().getSegundoApellido() + " " + solicitud.getSolicitante().getFisica().getNombre() : "");
		parametros.put("isARCO", "ARCO");
		parametros.put("fechaReporte", new Date());

		parametros.put("IMAGENES_DIR", new ClassPathResource("reportes/img/").getPath());


		ByteArrayOutputStream repo = manejadorReportes.ejecutaReporte(parametros, solicitud.getTramites(), plantilla);

		byte[] documento = repo.toByteArray();
		if(solicitud.getSecuenciaDeNotaria() != null){
			firmaDigitalBusinessRemote.guardarArchivoFirmado(solicitud.getSecuenciaDeNotaria(), "constanciaTramiteDerechosArco.pdf", documento);
		}

		return documento;
	}
	
	@Override
	public Object generarComprobanteTramiteAdministrativo(Solicitud solicitud) throws Exception{
		

		String plantilla = "comprobanteAdministrativo.jrxml";

		Map<String, Object> parametros = new HashMap<String, Object>();

		parametros.put(JRParameter.REPORT_LOCALE, new Locale("es","MX")); 

		parametros.put("folioSolicitud", solicitud.getNoFolioSolicitud());
		
		parametros.put("cadenaOriginal", solicitud.getCadenaOriginal());
		parametros.put("selloDigital", solicitud.getSelloDigital());
		parametros.put("secuenciaNotaria", solicitud.getSecuenciaDeNotaria());
		parametros.put("numeroSerie", solicitud.getNumeroSerieCertificado());
		parametros.put("usuario", solicitud.getSolicitante() != null ? solicitud.getSolicitante().getUsuario() : "");
		
		parametros.put("fechaReporte", new Date());
		
		parametros.put("IMAGENES_DIR", new ClassPathResource("reportes/img/").getPath());
		
		ByteArrayOutputStream repo = manejadorReportes.ejecutaReporte(parametros, solicitud.getTramites(), plantilla);
		
		byte[] documento = repo.toByteArray();
		log.debug("documento:"+documento);
		if(documento != null && solicitud.getSecuenciaDeNotaria() != null){
			firmaDigitalBusinessRemote.guardarArchivoFirmado(solicitud.getSecuenciaDeNotaria(), "constanciaTramite.pdf", documento);
		}

		return documento;
	}

	@Override
	public Object generarAcuseReciboElectronico(Solicitud solicitud, Fisica fisica) throws Exception{

		String plantilla = "";
		if (solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.ATENDIDA.getCodigo())){
		plantilla = "acuseReciboElectronico.jrxml";
		}else{
			plantilla = "acuseReciboElectronicoSolicitudPendiente.jrxml";
		}
		
		Map<String, Object> parametros = new HashMap<String, Object>();

		parametros.put(JRParameter.REPORT_LOCALE, new Locale("es","MX")); 

		parametros.put("folioSolicitud", solicitud.getNoFolioSolicitud());
		parametros.put("cadenaOriginal", solicitud.getCadenaOriginal());
		parametros.put("selloDigital", solicitud.getSelloDigital());
		parametros.put("secuenciaNotaria", solicitud.getSecuenciaDeNotaria());
		parametros.put("numeroSerie", solicitud.getNumeroSerieCertificado());
		parametros.put("usuario", solicitud.getSolicitante() != null ? solicitud.getSolicitante().getUsuario() : "");
		parametros.put("fecNacimiento", fisica.getFechaNacimiento());
		parametros.put("curp", fisica.getCurp());
		parametros.put("nombre", fisica.getNombre());
		parametros.put("primerApellido", fisica.getPrimerApellido());
		parametros.put("segundoApellido", fisica.getSegundoApellido());
		parametros.put("sexo", fisica.getSexo().getDescripcion());
		parametros.put("nss", fisica.getNss());
		if (solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.ATENDIDA.getCodigo())) {
			parametros.put("correoElectronico", fisica.getCorreoElectronico().getCorreo());
		}
		if (solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo())) {
			parametros.put("fechaSolicitud", solicitud.getFechaSolicitud());
		}
		parametros.put("IMAGENES_DIR", new ClassPathResource("reportes/img/").getPath());
		
		ByteArrayOutputStream repo = manejadorReportes.ejecutaReporte(parametros, solicitud.getTramites(), plantilla);
		
		byte[] documento = repo.toByteArray();
		log.debug("documento:"+documento);
		
		if(documento != null && solicitud.getSecuenciaDeNotaria() != null){
			firmaDigitalBusinessRemote.guardarArchivoFirmado(solicitud.getSecuenciaDeNotaria(), "constanciaTramite.pdf", documento);
		}

		return documento;
	}
	
	@Override
	public Object generarAcuseReciboElectronicoVentanilla (Solicitud solicitud) throws Exception{
		
		String plantilla = "";
		Map<String, Object> parametros = new HashMap<String, Object>();
		log.debug("----- LOS DATOS DE solicitud EN DOCUMENTOS ES: " + solicitud);
		Boolean isUsuarioVentanilla = false;
		Boolean aprobarTramite = false;
		Boolean rechazarTramite = false;
		TramiteActualizacionCorreo tramite = (TramiteActualizacionCorreo) solicitud.getTramites().get(0);
		log.debug("----- LOS DATOS DE tramite EN DOCUMENTOS ES: " + tramite);
		
		
		if (solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.ATENDIDA.getCodigo())){
			if (tramite.getUsuario().getNomNombre() != null && tramite.getUsuario().getNomPaterno() != null){
				isUsuarioVentanilla = true;
			}else{
				aprobarTramite= true;
			}
		}else{
			rechazarTramite= true;
		}
		
		log.debug("el tipo de accion es: " + isUsuarioVentanilla + aprobarTramite + rechazarTramite);
		
		if (aprobarTramite) {
			log.debug("actualizara la tabla con el nuevo correo");
	        try {
	        	Long tipoTramite = 67L;
	    		CorreoElectronico correoActualiza  = new CorreoElectronico();
	    		correoActualiza.setCorreo(tramite.getCorreoCapturado());
	        	SolicitudNssCorreo nssCorreo = new SolicitudNssCorreo();
	            nssCorreo.setCorreo(correoActualiza);
	            nssCorreo.setCurp(tramite.getPersona().getCurp());
	            nssCorreo.setCveIdTipoSolicitud(tipoTramite);
	            
	            int codigoActualizacion = tramiteDocumentosServiceRemote.validaCorreosRegistrados(nssCorreo);
	            this.tramiteDocumentosServiceRemote.actualizarPorCodigo(nssCorreo, codigoActualizacion);

			} catch (SolicitudNssCorreoException e) {
				super.log.error("No se pudo actualizar el correo");
				e.printStackTrace();
			}

		} 
		
		if (isUsuarioVentanilla) {
			plantilla = "acuseReciboElectronicoVentanilla.jrxml";
		} else if(aprobarTramite) {
			plantilla = "acuseReciboElectronico.jrxml";
		}else if (rechazarTramite){
			plantilla = "acuseReciboElectronicoVentanillaRechazo.jrxml";
		}
		
		AsignacionNSS nss = new AsignacionNSS();
		nss.setNombre(tramite.getPersona().getNombre());
		nss.setPrimerApellido(tramite.getPersona().getPrimerApellido());
		nss.setSegundoApellido(tramite.getPersona().getSegundoApellido());
		nss.setCurp(tramite.getPersona().getCurp());
		nss.setNss(tramite.getPersona().getNss());
		nss.setRfc(tramite.getPersona().getRfc());
		
		log.debug("el nss a pasar a firma electronica es: " + nss);

		FirmaElectronica firmaElectronica = tramiteDocumentosServiceRemote.generaFirmaElectronica(nss, solicitud, 
				"COMPROBANTE DE ACTUALIZACION DE CORREO");
		solicitud.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
		solicitud.setSelloDigital(firmaElectronica.getRecibo());
		solicitud.setSecuenciaDeNotaria(firmaElectronica.getSecuenciaNotaria());
		solicitud.setNumeroSerieCertificado(firmaElectronica.getSerialCertificado());

		log.debug("----- LOS DATOS DE solicitud firma electronica ES: " + solicitud);


		parametros.put(JRParameter.REPORT_LOCALE, new Locale("es","MX")); 

		parametros.put("folioSolicitud", solicitud.getNoFolioSolicitud());
		parametros.put("cadenaOriginal", solicitud.getCadenaOriginal());
		parametros.put("selloDigital", solicitud.getSelloDigital());
		parametros.put("secuenciaNotaria", solicitud.getSecuenciaDeNotaria());
		parametros.put("numeroSerie", solicitud.getNumeroSerieCertificado());
		parametros.put("usuario", solicitud.getSolicitante() != null ? solicitud.getSolicitante().getUsuario() : "");
		parametros.put("fecNacimiento", tramite.getPersona().getFechaNacimiento());
		parametros.put("curp", tramite.getPersona().getCurp());
		parametros.put("nombre", tramite.getPersona().getNombre());
		parametros.put("primerApellido", tramite.getPersona().getPrimerApellido());
		parametros.put("segundoApellido", tramite.getPersona().getSegundoApellido());
		
		if (isUsuarioVentanilla) {
			parametros.put("nombreUsuario", tramite.getUsuario().getNomNombre());
			parametros.put("primerApellidoUsuario", tramite.getUsuario().getNomPaterno());
			parametros.put("segundoApellidoUsuario", tramite.getUsuario().getNomMaterno());
			parametros.put("curpUsuario", tramite.getUsuario().getUsuario());
			if (tramite.getUsuario().getPassword() != null || !tramite.getUsuario().getPassword().isEmpty()) {
				parametros.put("matriculaUsuario", tramite.getUsuario().getPassword());
			} else {
				parametros.put("matriculaUsuario", "S/M");
			}
		}
		
		parametros.put("sexo", tramite.getPersona().getSexo().getDescripcion());
		parametros.put("nss", tramite.getPersona().getNss());
		if (aprobarTramite || isUsuarioVentanilla) {
			parametros.put("correoElectronico", tramite.getCorreoCapturado());
		}
		if (rechazarTramite) {
			parametros.put("motivos", tramite.getObservacion());
		}
		parametros.put("IMAGENES_DIR", new ClassPathResource("reportes/img/").getPath());

		
		ByteArrayOutputStream repo = manejadorReportes.ejecutaReporte(parametros, solicitud.getTramites(), plantilla);
		
		byte[] documento = repo.toByteArray();
		log.debug("documento:"+documento);
		if(documento != null && solicitud.getSecuenciaDeNotaria() != null){
			firmaDigitalBusinessRemote.guardarArchivoFirmado(solicitud.getSecuenciaDeNotaria(), "acuseReciboElectronico.pdf", documento);
		}

		return documento;
	}
	
	/*private BufferedImage getFotoAseguradoADIMSS(String strNSS) {
		BufferedImage imgFoto = null;
		
		try{
			WSConsultaAdimssService_Service service = new WSConsultaAdimssService_Service();
	    	WSConsultaAdimssService cliente = service.getWSConsultaAdimssServiceSOAP();
	    	byte[] foto = cliente.getFotografiaAsegurado(strNSS, CALIDAD_ASEGURADO_PENSIONADO);
	    	InputStream in = new ByteArrayInputStream(foto);
	    	imgFoto = ImageIO.read(in);
	    	if(imgFoto == null){
	    		try{
	    			imgFoto = this.getImgFotoDefault();
	    		}catch(Exception exc){
	    			log.error("ERROR al querer recupear la foto default del asegurado cuando es nulaen WS[" + strNSS + "]", exc);
	    		}
	    	}
	    	
		}catch(Exception e){
			log.error("ERROR al querer recupear la foto del asegurado [" + strNSS + "]", e);
			try{
			imgFoto = this.getImgFotoDefault();
			}catch(Exception ex){
				log.error("ERROR al querer recupear la foto del asegurado por default[" + strNSS + "]", e);
			}
		}
		
		return imgFoto;
		
	}*/
	private BufferedImage getImgFotoDefault(){
		BufferedImage imgFoto = null;
		
		try{
			
			InputStream stream =this.getClass().getResourceAsStream( "/reportes/img/fotoDefault.jpg" );
			if(stream == null)
				log.debug("stream nulo");
			
			InputStream stream2 =this.getClass().getResourceAsStream( "/fotoDefault.jpg" );
			if(stream2 == null)
				log.debug("stream2 nulo");
			
			
			
			log.debug("la ruta del archivo es [" +new ClassPathResource("reportes/img/fotoDefault.jpg").getPath()+ "]");
			imgFoto = ImageIO.read(this.getClass().getResourceAsStream( "/reportes/img/fotoDefault.jpg" ));
		}catch(Exception e){
			log.error("ERROR al querer leer la imagen default" ,e);
		}
		return imgFoto;
	}
	
	private  void setCalculoFechaFinVigenciaConstancia (CabezaGrupoFamiliar cabeza, 
			ComprobanteVigenciaDerechosDTO comprobante, List<Modalidad> modalidades ) {
		Date fechaHoy = org.apache.commons.lang3.time.DateUtils.truncate(new Date(), Calendar.DATE);
		log.debug("la fecha truncada es: [" +fechaHoy+"] y la fecha normal es "+ new Date());
		Date fechaInicioVigencia= org.apache.commons.lang3.time.DateUtils.truncate(cabeza.getFechaUltimoMovAfiliacion(), Calendar.DATE);
		Date fechaFinVigencia= org.apache.commons.lang3.time.DateUtils.truncate(cabeza.getFechaFinVigencia(), Calendar.DATE);
		comprobante.setFechaInicioVigencia(fechaInicioVigencia);		
		log.debug("la fecha truncada es: [" +fechaInicioVigencia+"] y la fecha normal es "+ cabeza.getFechaInicioVigencia());
		if(cabeza.getEstadoDerechohabiente().getIdEstadoDerechohabiente().longValue() == EstadoDerechohabienteEnum.VIGENTE.getId()) {
			if(modalidades != null){
				for(Modalidad moda: modalidades) {
					if(isModalidad1013141730313233353637384042434445(moda)) {
						comprobante.setFechaValidezConstancia(fechaHoy);
						break;
					}
					if(moda.getNumModalidad().equals("00")) {
						//Para modalidad 00 y estado vigente, no pone setFechaValidezConstancia para que lleve valor null
						//y ponga "---" en el campo Vigencia y en el campo CON DERECHO A SERVICIO MEDICO deberá¡ decir NO
						comprobante.setServicioMedico("NO");
						break;
					}
				}
			}
		}else if(cabeza.getEstadoDerechohabiente().getIdEstadoDerechohabiente().longValue() == EstadoDerechohabienteEnum.BAJA.getId()){
			if(cabeza.getPatronSujetoObligado() != null){
				if(cabeza.getPatronSujetoObligado().getModalidad().getNumModalidad().equals("00")){
					try {
						//Para modalidad 00 y estado baja, se pone setFechaValidezConstancia = "12/12/2223" para que 
						//ponga "------" en el campo Vigencia
						comprobante.setFechaValidezConstancia(new SimpleDateFormat("dd/MM/yyyy").parse("12/12/2223"));
					} catch(Exception exep){
					}
				}
			}
		}else if(cabeza.getEstadoDerechohabiente().getIdEstadoDerechohabiente().longValue() == EstadoDerechohabienteEnum.CON_DERECHO.getId()){
			comprobante.setFechaFinVigencia(fechaHoy);
		}else if(cabeza.getEstadoDerechohabiente().getIdEstadoDerechohabiente().longValue() == EstadoDerechohabienteEnum.CONSERVACION_DERECHOS.getId()){
			comprobante.setFechaFinVigencia(fechaHoy);
            comprobante.setFechaValidezConstancia(fechaFinVigencia);
        }
        //A solicitud del lider IMSS Frnando Castellanos, de acuerdo a las reglas de negocio RNGD05123, RNGD05193 y RNGD05194,
		//se pone Fecha de constancia con fecha de hoy sin importar el estado del derechohabiente que devuelve el WS.
        comprobante.setFechaFinVigencia(fechaHoy);
        try{
            if(comprobante.getFechaValidezConstancia() != null){
                String fechaS = DateUtils.dateToStringConFormato(comprobante.getFechaValidezConstancia(), "dd/MM/yyyy");
                if(fechaS.substring(6).equals("3000")){
                    log.error("EL WS WSConsInfoCabGpoFam para la CVE_ID_ASIGNACION_NSS : " + cabeza.getAsignacionNSS() + ", regresa fecha "  + fechaS + ". Se cambia a fecha de hoy: " + fechaHoy);
                    comprobante.setFechaValidezConstancia(fechaHoy);
                }

            }
		} catch(Exception e){
			e.printStackTrace();
		} 	
	}
	
	
	private  static boolean isModalidad1013143034353638424344 (Modalidad moda ) {
		boolean modalidadBase = false; 
		if(moda.getIdModalidad().longValue() == ModalidadEnum.DIEZ.getId() ||
				moda.getIdModalidad() == ModalidadEnum.TRECE.getId() ||
				moda.getIdModalidad() == ModalidadEnum.CATORCE.getId() ||
				moda.getIdModalidad() == ModalidadEnum.TREINTA.getId() ||
				moda.getIdModalidad() == ModalidadEnum.TREINTAYCUATRO.getId() ||
				moda.getIdModalidad() == ModalidadEnum.TREINTAYCINCO.getId() ||
				moda.getIdModalidad() == ModalidadEnum.TREINTAYSEIS.getId() ||
				moda.getIdModalidad() == ModalidadEnum.TREINTAYOCHO.getId() ||
				moda.getIdModalidad() == ModalidadEnum.CUARENTAYDOS.getId() ||
				moda.getIdModalidad() == ModalidadEnum.CUARENTAYTRES.getId() ||
				moda.getIdModalidad() == ModalidadEnum.CUARENTAYCUATRO.getId()){
			modalidadBase= true;
		}
		return modalidadBase;
	}
	
	private static boolean isModalidad101314173036384245 (Modalidad moda ) {
		boolean modalidadBase = false; 
		if(moda.getIdModalidad().longValue()== ModalidadEnum.DIEZ.getId() ||
				moda.getIdModalidad() == ModalidadEnum.TRECE.getId() ||
				moda.getIdModalidad() == ModalidadEnum.CATORCE.getId() ||
				moda.getIdModalidad() == ModalidadEnum.DIECISIETE.getId() ||
				moda.getIdModalidad() == ModalidadEnum.TREINTA.getId() ||
				moda.getIdModalidad() == ModalidadEnum.TREINTAYSEIS.getId() ||
				moda.getIdModalidad() == ModalidadEnum.TREINTAYOCHO.getId() ||
				moda.getIdModalidad() == ModalidadEnum.CUARENTAYDOS.getId() ||
				moda.getIdModalidad() == ModalidadEnum.CUERANTEYCINCO.getId()) {
			modalidadBase= true;
		}
		return modalidadBase;
	}

	private static boolean isModalidad1013141730313233353637384042434445 (Modalidad moda ) {
		boolean modalidadBase = false; 
		if( moda.getNumModalidad().equals("10") ||
			moda.getNumModalidad().equals("13") ||
			moda.getNumModalidad().equals("14") ||
			moda.getNumModalidad().equals("17") ||
			moda.getNumModalidad().equals("30") ||
			moda.getNumModalidad().equals("31") ||
			moda.getNumModalidad().equals("32") ||
			moda.getNumModalidad().equals("33") ||
			moda.getNumModalidad().equals("35") ||
			moda.getNumModalidad().equals("36") ||
			moda.getNumModalidad().equals("37") ||
			moda.getNumModalidad().equals("38") ||
			moda.getNumModalidad().equals("40") ||
			moda.getNumModalidad().equals("42") ||
			moda.getNumModalidad().equals("43") ||
			moda.getNumModalidad().equals("44") ||
			moda.getNumModalidad().equals("45")) {
			modalidadBase= true;
		}
		return modalidadBase;
	}


	private static boolean isModalidad101314173031(Modalidad moda ) {
		boolean modalidadBase = false; 
		if(moda.getIdModalidad().longValue() == ModalidadEnum.DIEZ.getId() ||
				moda.getIdModalidad().longValue() == ModalidadEnum.TRECE.getId() ||
				moda.getIdModalidad().longValue() == ModalidadEnum.CATORCE.getId() ||
				moda.getIdModalidad().longValue() == ModalidadEnum.DIECISIETE.getId() ||
				moda.getIdModalidad().longValue() == ModalidadEnum.TREINTA.getId() ||
				moda.getIdModalidad().longValue() == ModalidadEnum.TREINTAYUNO.getId())  {
			modalidadBase= true;
		}
		return modalidadBase;
	}
	
	private static boolean isModalidad1013141730354344 (Modalidad moda ) {
		boolean modalidadBase = false; 
		if(moda.getIdModalidad().longValue() == ModalidadEnum.DIEZ.getId() ||
				moda.getIdModalidad() == ModalidadEnum.TRECE.getId() ||
				moda.getIdModalidad() == ModalidadEnum.CATORCE.getId() ||
				moda.getIdModalidad() == ModalidadEnum.DIECISIETE.getId() ||
				moda.getIdModalidad() == ModalidadEnum.TREINTA.getId() ||
				moda.getIdModalidad() == ModalidadEnum.TREINTAYCINCO.getId() ||
				moda.getIdModalidad() == ModalidadEnum.CUARENTAYTRES.getId() ||
				moda.getIdModalidad() == ModalidadEnum.CUARENTAYCUATRO.getId()) {
			modalidadBase= true;
		}
		return modalidadBase;
	}
	
	private static boolean isModalidad0031323638404245(Modalidad moda ) {
		boolean modalidadBase = false; 
		if(	moda.getIdModalidad().longValue() == ModalidadEnum.CERO.getId() ||
				moda.getIdModalidad().longValue() == ModalidadEnum.TREINTAYUNO.getId() ||
				moda.getIdModalidad() == ModalidadEnum.TREINTAYDOS.getId() ||
				moda.getIdModalidad() == ModalidadEnum.TREINTAYSEIS.getId() ||
				moda.getIdModalidad() == ModalidadEnum.TREINTAYOCHO.getId() ||
				moda.getIdModalidad() == ModalidadEnum.CUARENTA.getId() ||
				moda.getIdModalidad() == ModalidadEnum.CUARENTAYDOS.getId() ||
				moda.getIdModalidad() == ModalidadEnum.CUERANTEYCINCO.getId()){
			modalidadBase= true;
		}
		return modalidadBase;
	}
	
	private static boolean isModalidad313235404344(Modalidad moda ) {
		boolean modalidadBase = false; 
		if(	moda.getIdModalidad().longValue() == ModalidadEnum.TREINTAYUNO.getId() ||
				moda.getIdModalidad() == ModalidadEnum.TREINTAYDOS.getId() ||
				moda.getIdModalidad() == ModalidadEnum.TREINTAYCINCO.getId() ||
				moda.getIdModalidad() == ModalidadEnum.CUARENTA.getId() ||
				moda.getIdModalidad() == ModalidadEnum.CUARENTAYTRES.getId() ||
				moda.getIdModalidad() == ModalidadEnum.CUARENTAYCUATRO.getId()){
			modalidadBase= true;
		}
		return modalidadBase;
	}
	
	private static boolean isModalidad3335434445(Modalidad moda ) {
		boolean modalidadBase = false; 
		if(moda.getIdModalidad().longValue() == ModalidadEnum.TREINTAYTRES.getId() ||
			moda.getIdModalidad().longValue() == ModalidadEnum.TREINTAYCINCO.getId() ||
			moda.getIdModalidad().longValue() == ModalidadEnum.CUARENTAYTRES.getId() ||
			moda.getIdModalidad().longValue() == ModalidadEnum.CUARENTAYCUATRO.getId() ||
			moda.getIdModalidad().longValue() == ModalidadEnum.CUERANTEYCINCO.getId()){
			modalidadBase= true;
		}
		return modalidadBase;
	}
	
	private static boolean isModalidad32(Modalidad moda ) {
		boolean modalidadBase = false; 
		if(moda.getIdModalidad().longValue() == ModalidadEnum.TREINTAYDOS.getId()){
			modalidadBase= true;
		}
		return modalidadBase;
	}
	
	private  static int numeroDiasEntreDosFechas(Date fechaInicio, Date fechaFin){
	     long startTime = fechaInicio.getTime();
	     long endTime = fechaFin.getTime();
	     long diffTime = endTime - startTime;
	     return (int)TimeUnit.DAYS.convert(diffTime, TimeUnit.MILLISECONDS);
	}
	
	
}
