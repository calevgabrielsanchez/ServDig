package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.arp;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.solicitud.MatriculaSolicitud;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.business.rep.legal.RepresentanteLegalServiceBusinessLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.business.socios.SocioServiceBusinessLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.AfiliacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.arp.ArpBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.personas.autorizadas.PersonasAutorizadasServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.arp.ArpUtility;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EscrituraConstitutiva;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.delta.tramite.service.entity.TramiteServiceEntityLocal;
import mx.gob.imss.ctirss.sapi.model.business.ReportesARP;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.CollectionUtils;

@Stateless(name = "arpBusiness", mappedName = "arpBusiness")
public class ArpBusiness extends AbstractServiceBusiness implements ArpBusinessRemote {

	@EJB
	SujetoObligadoServiceBusinessRemote sujetoObligadoService;	

	@EJB
	SolicitudBusinessRemote solicitudBusiness;

	@EJB
	DomicilioServiceBusinessRemote domicilioService;	

	@EJB
	RepresentanteLegalServiceBusinessLocal representanteLegalService;

	@EJB
	MediosContactoServiceBusinessRemote mediosContactoService;
	
	@EJB
	PersonasAutorizadasServiceRemote personasAutorizadasService;

	@EJB
	TramiteServiceEntityLocal tramiteServiceEntityLocal;
	
	@EJB
	FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;
	
	@EJB
	AfiliacionServiceBusinessRemote afiliacionService;
	
	@EJB
	SocioServiceBusinessLocal socioServiceBusiness;
	
	@EJB(mappedName = "consultaMatriculaServiceBusiness")
	private MatriculaSolicitud matriculaSolicitud;
	
	
	private transient final ArpUtility arpUtility = new ArpUtility();	
	
	private String nombreQuienTramita="";

    /**
     * Este metodo procesara los parametros del reporte PDF para generar el ARP
     * @param solicitud
     * @return
     */
	@Override
	public byte[] getArpPersona(String folioSolicitud)
			throws Exception {
		
		this.log.debug("**********Entre al ArpBusiness:getArpPersona folio solicitud: " + folioSolicitud);
		Map<String, String> cadenas = null;		
		ReportesARP arp = new ReportesARP();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    String tipo = null;
	    Date fechaPresentacionArp = new Date();
	    
//		arp = getDatosHarkcode(arp);

		//Obtiene sujeto obligado
		Solicitud solicitud = obtieneSolicitud(folioSolicitud);
		
		if(solicitud != null){
			//Obtener Fecha Presentacion ARP
			fechaPresentacionArp = (solicitud.getFechaConclusion() != null 
				? solicitud.getFechaConclusion() : (solicitud.getFechaActualizacion() != null 
					? solicitud.getFechaActualizacion(): new Date()));			
		}
		
		SujetoObligado sujetoObligado = obtieneSujetoObligado(solicitud.getTramites());
		
		if(sujetoObligado == null) {
			this.log.debug("********** sujetoObligado es NULL");
		} else {
			this.log.debug("**********Obteniendo datos del registro patronal " + sujetoObligado.getNumeroRegistroPatronal());
		}

		if(sujetoObligado.getTipoPersonaFiscal().getCodigo().equals(TipoPersonaFiscal.FISICA.getCodigo())){
			arp = arpUtility.getDatosPersonaFisica(sujetoObligado, domicilioService, representanteLegalService,
					mediosContactoService, personasAutorizadasService, fechaPresentacionArp);
			tipo = "F";
			
			cadenas = firmaDigitalBusinessRemote.getCadenaOriginalYSelloDigital(solicitud,sujetoObligado.getFisica(),sujetoObligado.getNumeroRegistroPatronal(),null);
		}else{
			
			if (CollectionUtils.isEmpty(sujetoObligado.getSocios())
					&& sujetoObligado.getMoral() != null
					&& sujetoObligado.getMoral().getIdPersona() != null) {
				this.log.debug("No se tienen socios en el sujeto obligado, se procede a consultarlos directamente a BDTU");
				
				Socio socio = new Socio();
				socio.setIdPersonaMoralPatron(sujetoObligado.getMoral().getIdPersona());
				
				List<Socio> socios = this.socioServiceBusiness
						.sociosPorIdPersonaMoralPatron(socio);
				
				if (CollectionUtils.isEmpty(socios)) {
					this.log.debug("No se encontraron socios para el sujeto obligado [cveIdSujetoObligado = "
							+ sujetoObligado.getCveIdSujetoObligado() + "]");
				} else {
					this.log.debug("Se encontraron "
							+ socios.size()
							+ " socios para el sujeto obligado [cveIdSujetoObligado = "
							+ sujetoObligado.getCveIdSujetoObligado() + "]");
					sujetoObligado.setSocios(socios);
				}
			}
			//consultamos el alta para tener todo el detalle
			if(sujetoObligado.getMoral().getEscrituraConstitutiva()!=null
					&& sujetoObligado.getMoral().getEscrituraConstitutiva().getCveEscrituraConstitutiva()!=null){
				EscrituraConstitutiva acta = sujetoObligadoService.obtenerEscrituraConstitutivaPorId(sujetoObligado.getMoral().getEscrituraConstitutiva().getCveEscrituraConstitutiva());
				sujetoObligado.getMoral().setEscrituraConstitutiva(acta);
			}
			arp = arpUtility.getDatosPersonaMoral(sujetoObligado, domicilioService, representanteLegalService,
					mediosContactoService, personasAutorizadasService, fechaPresentacionArp);
	        tipo  =  "RV3"; //TODO cuando se liberen los reportes se descomenta esta linea y se sube la otra
	        //tipo = "R";
	        
	        cadenas = firmaDigitalBusinessRemote.getCadenaOriginalYSelloDigital(solicitud,sujetoObligado.getMoral(),sujetoObligado.getNumeroRegistroPatronal(),null);
			
		}
		
		arp.setFolioNRP(folioSolicitud);
		arp.setCadenaOriginal(cadenas.get("cadenaOriginal"));
		arp.setFirmaDigital(cadenas.get("selloDigital"));
		arp.setNumNotariaDigital(cadenas.get("secuenciaNotaria"));
		arp.setSerieCertificado(cadenas.get("numeroSerie"));
		arp.setOrigenSolicitud(solicitud.getOrigenSolicitud().getIdTipoSolicitud().intValue());
		if(solicitud.getSolicitante()!=null)
			arp.setUsuario(solicitud.getSolicitante().getUsuario());
		/**
		 * Al guardar el xml como el nombre de subdelegacion tiene "" JAXB corta la cadena, para
		 * asegurar que el nombre va completo se consulta la subdelegacion nuevamente
		 */
		//TODO
		if(sujetoObligado.getSubdelegacion()!=null){
			Subdelegacion subdelegacion = afiliacionService.obtenerSubdelegacion(sujetoObligado.getSubdelegacion().getId());
			arp.setSubDelegacion(subdelegacion.getDescripcion());
		}
		
		insertarSolicitudFirmaDigitalArp(solicitud, cadenas);

		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
	        ArrayList<ReportesARP> beanData = new ArrayList<ReportesARP>();
			
			List<JasperPrint> arreglo = new ArrayList<JasperPrint>(); 			
			JRBeanCollectionDataSource dataSource = null;
			JasperPrint print = null;
			
			if(arp.getFecPresentacion()==null){
				System.err.println("Se asigna fecha en el ultimo paso");
				arp.setFecPresentacion(Calendar.getInstance().getTime());
			}
			
			beanData.add(arp);
			
			parameters.put("header", new ClassPathResource("comprobantes/headerPicture.png").getPath());
			parameters.put("PIE", new ClassPathResource("comprobantes/footer.png").getPath());
			
			List<String> comprobantes = new ArrayList<String>();
			comprobantes.add("afil01"+tipo+".jasper");
			if(!tipo.equals("F")) {
				comprobantes.add("afil02"+tipo+".jasper");
				comprobantes.add("afil03"+tipo+".jasper");
				comprobantes.add("afil04"+tipo+".jasper");
			}
			
			System.out.println("Subdel enviado a reporte: "+arp.getSubDelegacion());
			System.out.println("Calle centro de trabajo [: "+arp.getCalleCT()+"]");
			log.debug("Calle centro de trabajo [: "+arp.getCalleCT()+"]");
			
			for (String comprobante : comprobantes) {
				JasperReport report = (JasperReport) JRLoader.loadObject(new ClassPathResource("comprobantes/"+comprobante).getInputStream());
				dataSource = new JRBeanCollectionDataSource(beanData);
				print = JasperFillManager.fillReport(report,parameters,dataSource);
				arreglo.add(print);
			}
			
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,arreglo);			
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,byteArrayOutputStream);			
			exporter.exportReport();
			
		} catch (Exception e) {// Agregar las excepciones personalizadas
			e.printStackTrace();
		}
        
		this.log.debug("**********regresando byteArrayOutputStream");
		System.err.println("Subdel enviado a reporte: "+arp.getSubDelegacion());
		
		return byteArrayOutputStream.toByteArray();
	} 
	
	
	@Override
	public byte[] getArpPersona(String folioSolicitud, Tramite tramite)
			throws Exception {
		
		this.log.debug("**********Entre al ArpBusiness:getArpPersona folio solicitud: " + folioSolicitud);
		Map<String, String> cadenas = null;		
		ReportesARP arp = new ReportesARP();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    String tipo = null;

//		arp = getDatosHarkcode(arp);

		//Obtiene sujeto obligado
		Solicitud solicitud = obtieneSolicitud(folioSolicitud);
		
		
		SujetoObligado sujetoObligado = ((TramiteSujetoObligado)tramite).getSujetoObligado();
		
		if(sujetoObligado == null) {
			this.log.debug("********** sujetoObligado es NULL");
		} else {
			this.log.debug("**********Obteniendo datos del registro patronal " + sujetoObligado.getNumeroRegistroPatronal());
		}

		if(sujetoObligado.getTipoPersonaFiscal().getCodigo().equals(TipoPersonaFiscal.FISICA.getCodigo())){
			arp = arpUtility.getDatosPersonaFisica(sujetoObligado, domicilioService, representanteLegalService,
					mediosContactoService, personasAutorizadasService, solicitud.getFechaConclusion());
			tipo = "F";
			
			cadenas = firmaDigitalBusinessRemote.getCadenaOriginalYSelloDigital(solicitud,sujetoObligado.getFisica(),sujetoObligado.getNumeroRegistroPatronal(),null);
		}else{
			
			if (CollectionUtils.isEmpty(sujetoObligado.getSocios())
					&& sujetoObligado.getMoral() != null
					&& sujetoObligado.getMoral().getIdPersona() != null) {
				this.log.debug("No se tienen socios en el sujeto obligado, se procede a consultarlos directamente a BDTU");
				
				Socio socio = new Socio();
				socio.setIdPersonaMoralPatron(sujetoObligado.getMoral().getIdPersona());
				
				List<Socio> socios = this.socioServiceBusiness
						.sociosPorIdPersonaMoralPatron(socio);
				
				if (CollectionUtils.isEmpty(socios)) {
					this.log.debug("No se encontraron socios para el sujeto obligado [cveIdSujetoObligado = "
							+ sujetoObligado.getCveIdSujetoObligado() + "]");
				} else {
					this.log.debug("Se encontraron "
							+ socios.size()
							+ " socios para el sujeto obligado [cveIdSujetoObligado = "
							+ sujetoObligado.getCveIdSujetoObligado() + "]");
					sujetoObligado.setSocios(socios);
				}
			}
			//consultamos el alta para tener todo el detalle
			if(sujetoObligado.getMoral().getEscrituraConstitutiva()!=null
					&& sujetoObligado.getMoral().getEscrituraConstitutiva().getCveEscrituraConstitutiva()!=null){
				EscrituraConstitutiva acta = sujetoObligadoService.obtenerEscrituraConstitutivaPorId(sujetoObligado.getMoral().getEscrituraConstitutiva().getCveEscrituraConstitutiva());
				sujetoObligado.getMoral().setEscrituraConstitutiva(acta);
			}
			arp = arpUtility.getDatosPersonaMoral(sujetoObligado, domicilioService, representanteLegalService,
					mediosContactoService, personasAutorizadasService, solicitud.getFechaConclusion());
			tipo = "RV3"; //TODO cuando se liberen los reportes se descomenta esta linea y se sube la otra
	        //tipo = "R";
	        
	        cadenas = firmaDigitalBusinessRemote.getCadenaOriginalYSelloDigital(solicitud,sujetoObligado.getMoral(),sujetoObligado.getNumeroRegistroPatronal(),null);
			
		}
		
		arp.setFolioNRP(folioSolicitud);
		arp.setCadenaOriginal(cadenas.get("cadenaOriginal"));
		arp.setFirmaDigital(cadenas.get("selloDigital"));
		arp.setNumNotariaDigital(cadenas.get("secuenciaNotaria"));
		arp.setSerieCertificado(cadenas.get("numeroSerie"));
		arp.setOrigenSolicitud(solicitud.getOrigenSolicitud().getIdTipoSolicitud().intValue());
		if(solicitud.getSolicitante()!=null)
			arp.setUsuario(solicitud.getSolicitante().getUsuario());
		/**
		 * Al guardar el xml como el nombre de subdelegacion tiene "" JAXB corta la cadena, para
		 * asegurar que el nombre va completo se consulta la subdelegaci?n nuevamente
		 */
		//TODO
		if(sujetoObligado.getSubdelegacion()!=null){
			Subdelegacion subdelegacion = afiliacionService.obtenerSubdelegacion(sujetoObligado.getSubdelegacion().getId());
			arp.setSubDelegacion(subdelegacion.getDescripcion());
		}
		
		insertarSolicitudFirmaDigitalArp(solicitud, cadenas);

		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
	        ArrayList<ReportesARP> beanData = new ArrayList<ReportesARP>();
	        
	        parameters.put("header", new ClassPathResource("comprobantes/headerPicture.png").getPath());
			parameters.put("PIE", new ClassPathResource("comprobantes/footer.png").getPath());
			
			List<JasperPrint> arreglo = new ArrayList<JasperPrint>(); 			
			JRBeanCollectionDataSource dataSource = null;
			JasperPrint print = null;
			beanData.add(arp);
			
			List<String> comprobantes = new ArrayList<String>();
			comprobantes.add("afil01"+tipo+".jasper");
			if(!tipo.endsWith("F")) {
				comprobantes.add("afil02"+tipo+".jasper");
				comprobantes.add("afil03"+tipo+".jasper");
				comprobantes.add("afil04"+tipo+".jasper");
			}
			
			System.err.println("Subdel enviado a reporte: "+arp.getSubDelegacion());
			
			for (String comprobante : comprobantes) {
				JasperReport report = (JasperReport) JRLoader.loadObject(new ClassPathResource("comprobantes/"+comprobante).getInputStream());
				dataSource = new JRBeanCollectionDataSource(beanData);
				print = JasperFillManager.fillReport(report,parameters,dataSource);
				arreglo.add(print);
			}
			
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,arreglo);			
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,byteArrayOutputStream);			
			exporter.exportReport();
			
		} catch (Exception e) {// Agregar las excepciones personalizadas
			e.printStackTrace();
		}
        
		this.log.debug("**********regresando byteArrayOutputStream");
		System.err.println("Subdel enviado a reporte: "+arp.getSubDelegacion());
		
		return byteArrayOutputStream.toByteArray();
	} 

	
    @Override
	public Object getReporeteArpPersona(Solicitud solicitud) throws Exception {
		
    	Tramite tramite = solicitud.getTramites().get(0);
    	byte[] documento = null;
    	
    	documento = (byte[]) tramiteServiceEntityLocal.getDocumentoPorTipoIdTramite(tramite.getTramiteId(), DocumentoPorTipoEnum.ARP.getId());
    	
    	if(documento == null) {
	    	documento = this.getArpPersona(solicitud.getNoFolioSolicitud());
	    	detectarSecuenciaNotaria(solicitud);
	    	tramiteServiceEntityLocal.actualizarDocumentosTramite(tramite.getTramiteId(), DocumentoPorTipoEnum.ARP.getId(), documento);
	    	
	    	log.warn("secuencia de notaria: " + solicitud.getSecuenciaDeNotaria());
	    	
	    	if(solicitud.getSecuenciaDeNotaria() != null) {
				firmaDigitalBusinessRemote.guardarArchivoFirmado(solicitud.getSecuenciaDeNotaria(), "arp.pdf", documento);
			}
    	}
    	
		return documento;
	}
	
    @Override
	public Object getReporteArpPersona(Solicitud solicitud, Tramite tramite) throws Exception {
		
    	byte[] documento = null;
    	
    	documento = (byte[]) tramiteServiceEntityLocal.getDocumentoPorTipoIdTramite(tramite.getTramiteId(), DocumentoPorTipoEnum.ARP.getId());
    	
    	if(documento == null) {
	    	documento = this.getArpPersona(solicitud.getNoFolioSolicitud(), tramite);
	    	detectarSecuenciaNotaria(solicitud);
	    	tramiteServiceEntityLocal.actualizarDocumentosTramite(tramite.getTramiteId(), DocumentoPorTipoEnum.ARP.getId(), documento);
	    	
	    	log.warn("secuencia de notaria: " + solicitud.getSecuenciaDeNotaria());
	    	
	    	if(solicitud.getSecuenciaDeNotaria() != null) {
				firmaDigitalBusinessRemote.guardarArchivoFirmado(solicitud.getSecuenciaDeNotaria(), "arp.pdf", documento);
			}
    	}
    	
		return documento;
	}
    
	@Override
	public Object getReporteTipPersoa(Solicitud solicitud) throws Exception {
		Tramite tramite = solicitud.getTramites().get(0);
    	byte[] documento = null;
    	
    	documento = (byte[]) tramiteServiceEntityLocal.getDocumentoPorTipoIdTramite(tramite.getTramiteId(), DocumentoPorTipoEnum.TIP.getId());
    	
    	if(documento == null) {
	    	documento = this.getTipPersona(solicitud.getNoFolioSolicitud());
	    	detectarSecuenciaNotaria(solicitud);
	    	tramiteServiceEntityLocal.actualizarDocumentosTramite(tramite.getTramiteId(), DocumentoPorTipoEnum.TIP.getId(), documento);
	    	
	    	log.warn("secuencia de notaria: " + solicitud.getSecuenciaDeNotaria());
	    	
	    	if(solicitud.getSecuenciaDeNotaria() != null) {
				firmaDigitalBusinessRemote.guardarArchivoFirmado(solicitud.getSecuenciaDeNotaria(), "tip.pdf", documento);
			}
    	}
    	
		return documento;
	}		
	
	
	@Override
	public Object getReporteTipPersona(Solicitud solicitud, Tramite tramite) throws Exception {
    	byte[] documento = null;
    	
    	documento = (byte[]) tramiteServiceEntityLocal.getDocumentoPorTipoIdTramite(tramite.getTramiteId(), DocumentoPorTipoEnum.TIP.getId());
    	
    	if(documento == null) {
    		TramiteSujetoObligado tramiteSujetoObligado = (TramiteSujetoObligado)tramite;
      		nombreQuienTramita = tramiteSujetoObligado.getSujetoObligado().getClasificacion().getNombreQuienPresenta();
      		this.log.debug("NOMBRE QUIEN TRAMITA: " + nombreQuienTramita);

	    	documento = this.getTipPersona(solicitud.getNoFolioSolicitud(), tramite);
	    	detectarSecuenciaNotaria(solicitud);
	    	tramiteServiceEntityLocal.actualizarDocumentosTramite(tramite.getTramiteId(), DocumentoPorTipoEnum.TIP.getId(), documento);
	    	
	    	log.warn("secuencia de notaria: " + solicitud.getSecuenciaDeNotaria());
	    	
	    	if(solicitud.getSecuenciaDeNotaria() != null) {
				firmaDigitalBusinessRemote.guardarArchivoFirmado(solicitud.getSecuenciaDeNotaria(), "tip.pdf", documento);
			}
    	}
    	
		return documento;
	}
	
	
	/**
	 * Se agrega debido a que surgio la necesidad de tener dos tramites de alta en la misma solicitud
	 * por las modalidades 30 y 14
	 * @param folioSolicitud
	 * @param tramite
	 * @return
	 * @throws Exception
	 */
	@Override
	public byte[] getTipPersona(String folioSolicitud, Tramite tramite)
			throws Exception {
		
		this.log.debug("**********Entre al ArpBusiness:getTipPersona folio solicitud: " + folioSolicitud);
		ReportesARP arp = null;
		//mapa para guardar el sello digital y la cadena original
		Map<String, String> cadenas = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

//		arp = getDatosHarkcode(arp);

		//Obtiene sujeto obligado
		final Solicitud solicitud = obtieneSolicitud(folioSolicitud);
		final SujetoObligado sujetoObligado = ((TramiteSujetoObligado)tramite).getSujetoObligado();
		
		if(sujetoObligado == null){
			this.log.debug("********** sujetoObligado es NULL");
		}

        this.log.debug("**********Obteniendo datos del registro patronal");
                                
		//se obtiene el domicilio del centro de trabajo, RL, personas autorizadas
        // Si no se encuentra en el servicio el dato se toma del tramite de alta
		if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTIVIDAD_ECONOMICA.getCodigo()) ||
		   tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.DISPOSICION_DE_LEY.getCodigo()) ||
		   tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.INCORPORACION_DE_ACTIVIDADES.getCodigo()) ||
		   tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.COMPRA_DE_ACTIVOS.getCodigo()) ||
		   tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.COMODATO.getCodigo()) ||
		   tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ENAJENACION.getCodigo()) ||
		   tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ARRENDAMIENTO.getCodigo()) ||
		   tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.FIDEICOMISO_TRASLATIVO.getCodigo()) ||
		   tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ESCISION.getCodigo()) ||
		   tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.SUSTITUCION_PATRONAL.getCodigo()) ||
		   tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.FUSION.getCodigo()) ||
		   tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.REANUDACION_DE_ACTIVIDADES.getCodigo()) ||
		   tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.SUSTITUCION_PATRONAL_SUBCONTRATACION.getCodigo()) ||
		   tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO.getCodigo()) ||
		   tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo()) 
		){
			this.log.debug("********** Tramite de clasificacion: " + tramite.getTipoTramite().getIdTipoTramite()
					+ ", nrp: " + sujetoObligado.getNumeroRegistroPatronal());
			String curpSolicitante = null;
			if(solicitud.getSolicitante() != null && solicitud.getSolicitante().getUsuario() != null){
				curpSolicitante = solicitud.getSolicitante().getUsuario().trim();
			}
			arp = this.getARP(arp, sujetoObligado, curpSolicitante);			
		}else{
			arp = arpUtility.getDatosPersonaTIP(sujetoObligado, representanteLegalService, 
					personasAutorizadasService, domicilioService);
		}
		
		Persona personaCadena = sujetoObligado.getFisica() == null ? sujetoObligado.getMoral() : sujetoObligado.getFisica();
		cadenas = firmaDigitalBusinessRemote.getCadenaOriginalYSelloDigital(solicitud,personaCadena,sujetoObligado.getNumeroRegistroPatronal(),null);
		
		cadenas = firmaDigitalBusinessRemote.getCadenaOriginalYSelloDigital(
				solicitud,personaCadena,sujetoObligado.getNumeroRegistroPatronal(),null);
		
          String cadenaOriginalFormada = cadenas.get("cadenaOriginal");

          if (cadenaOriginalFormada != null) {
		    // Elimina Nombre o Razon Social cuando viene vacío, null o null null
		    cadenaOriginalFormada = cadenaOriginalFormada.replaceAll(
		            "\\|Nombre o Razon Social:\\s*(?:(?:null\\s*)+)?\\|", "|");

		    // Elimina CURP cuando viene vacío o null
		    cadenaOriginalFormada = cadenaOriginalFormada.replaceAll( "\\|CURP:\\s*(?:null)?\\s*\\|", "|");
		    cadenas.put("cadenaOriginal", cadenaOriginalFormada);
          }
		
		
		arp.setFolioNRP(folioSolicitud);
		arp.setCadenaOriginal(cadenas.get("cadenaOriginal"));
		arp.setFirmaDigital(cadenas.get("selloDigital"));
		arp.setNumNotarira(cadenas.get("secuenciaNotaria"));
		arp.setNumeroReferencia(cadenas.get("numeroSerie"));
		arp.setOrigenSolicitud(solicitud.getOrigenSolicitud().getIdTipoSolicitud().intValue());
		log.debug("MOSTRAR SOLICITANTE :: " + solicitud.getSolicitante());
		if(solicitud.getSolicitante()!=null) {
			arp.setUsuario(solicitud.getSolicitante().getUsuario());
			// Se agrega condicion para los tramites de ventanilla
   			if (this.nombreQuienTramita !=null) {
    			arp.setUsuario(this.matriculaSolicitud.consultaMatricula(arp.getUsuario()));
   			}
		}
		
		if(sujetoObligado.getSubdelegacion()!=null){
			Subdelegacion subdelegacion = afiliacionService.obtenerSubdelegacion(sujetoObligado.getSubdelegacion().getId());
			arp.setSubDelegacion(subdelegacion.getDescripcion());
			
		}
		

			// Se agrega condicion para los tramites de ventanilla
        if  (this.nombreQuienTramita !=null) {
            String cadenaOriginalVentanilla = cadenas.get("cadenaOriginal").replace("Invocante:portalimssdigital|", "Invocante:ventanilla|");
           		arp.setNombreCompletoRl(this.nombreQuienTramita);
            	arp.setCadenaOriginal(cadenaOriginalVentanilla);
        }
		
		insertarSolicitudFirmaDigitalArp(solicitud, cadenas);
				
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			ArrayList<ReportesARP> beanData = new ArrayList<ReportesARP>();
			String comprobante;			
			if (
					tramite.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.SUSTITUCION_PATRONAL_SUBCONTRATACION.getCodigo().intValue()
					|| tramite.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ARRENDAMIENTO.getCodigo().intValue()
					|| tramite.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.COMODATO.getCodigo().intValue()
					|| tramite.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.COMPRA_DE_ACTIVOS.getCodigo().intValue()
					|| tramite.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ENAJENACION.getCodigo().intValue()
					|| tramite.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ESCISION.getCodigo().intValue()
					|| tramite.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.FIDEICOMISO_TRASLATIVO.getCodigo().intValue()
					|| tramite.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.FUSION.getCodigo().intValue()
					|| tramite.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.SUSTITUCION_PATRONAL.getCodigo().intValue()
					|| tramite.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.DISPOSICION_DE_LEY.getCodigo().intValue()
					|| tramite.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ACTIVIDAD_ECONOMICA.getCodigo().intValue()
					|| tramite.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.INCORPORACION_DE_ACTIVIDADES.getCodigo().intValue() 
					|| tramite.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.REANUDACION_DE_ACTIVIDADES.getCodigo().intValue()					   
					|| tramite.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO.getCodigo().intValue()	
					// || tramite.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo().intValue()
			) {
				comprobante = "TIP_prima.jasper";
			} else {
				comprobante = "TIP.jasper";
			}
			beanData.add(arp);

			JasperReport report = (JasperReport) JRLoader.loadObject(new ClassPathResource("comprobantes/"+ comprobante).getInputStream());
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(beanData);
			JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource);

			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
			exporter.exportReport();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.log.debug("**********regresando byteArrayOutputStream");
		
		return byteArrayOutputStream.toByteArray();
	} 			
			
	private ReportesARP getARP(ReportesARP arp, SujetoObligado sujetoObligado, String curpSolicitante){
		this.log.debug("********** En getArp obteniendo datos para TIP");
		SujetoObligado soRp = new SujetoObligado();
		soRp.setNumeroRegistroPatronal(sujetoObligado.getNumeroRegistroPatronal());
		soRp.setTipoPersonaFiscal(sujetoObligado.getTipoPersonaFiscal());
		//busca detalle del rp
		this.log.debug("********** Obteniedo detalle para el patron: " + sujetoObligado.getNumeroRegistroPatronal());
		soRp = sujetoObligadoService.obtenerDetalleSujetoObligadoActividadEconomica(soRp);		
		if(soRp.getNumeroRegistroPatronal() != null && soRp.getModalidad().getNumModalidad() != null && soRp.getDigVerificador() != null){
			soRp.setNumeroRegistroPatronal(soRp.getNumeroRegistroPatronal()+soRp.getModalidad().getNumModalidad()+soRp.getDigVerificador());
		}
		
		//buscamos en el detalle del patron los datos para la TIP
		arp = arpUtility.getDatosPersonaTIP(soRp, representanteLegalService, 
				personasAutorizadasService, domicilioService);	
		
		//Asignamos en la prima el valor de la prima actual
		if(soRp.getClasificacion() != null && soRp.getClasificacion().getPrimaSRTActual() != null){
			this.log.debug("********** Asignamos prima correcta");
	        BigDecimal bd = soRp.getClasificacion().getPrimaSRTActual();
	        this.log.debug("********** Valor actual de la prima: " +bd);
	        // Truncar o completar a 5 decimales sin redondear
	        bd = bd.setScale(5, RoundingMode.DOWN);
	        // Formatear como cadena con 5 decimales fijos
	        DecimalFormat df = new DecimalFormat("0.00000");
	        String prim = df.format(bd.doubleValue());
	        this.log.debug("********** Valor de la prima con formato: " + prim);
			arp.setPrima(prim);
		}else{
			this.log.debug("********** No es posible asignar prima");
		}
		
		//Adecuamos domicilio sino fue encontrado
		if(arp.getDomicilio() != null && arp.getDomicilio().trim().startsWith("LOCALIDAD -------------------- C.P. ----- MUNICIPIO -------")){
			arp.setDomicilio(" ");
			if(soRp.getCntroTrabajo() != null && soRp.getCntroTrabajo().getDescripcion() != null){
				this.log.debug("********** Cambiamos la descripcion del domicilio");
				String patDomicilio = soRp.getCntroTrabajo().getDescripcion().trim();
				patDomicilio = patDomicilio.length()>=150? patDomicilio.substring(0,150) : patDomicilio; //limitamos el domicilio a 150 caracteres
				arp.setDomicilio(patDomicilio);
			}			
		}
		
		//Adecuamos lugar y fecha de expedicion sino fue encontrado
		if(arp.getLugarExpedicion() != null && arp.getLugarExpedicion().trim().startsWith("----------")){
			this.log.debug("********** Cambiamos fecha y lugar de expedicion");		
			//arp.get
			Date fechaAlta = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd");
			SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy");
			String lugarExpedicion = sdf.format(fechaAlta) + " DE ";
			lugarExpedicion += getNombreMesDate(fechaAlta).toUpperCase()+ " DEL ANIO " + sdf3.format(fechaAlta);
			arp.setLugarExpedicion(lugarExpedicion);
		}
		
		//sino viene el RL en el detalle del patron, lo buscamos por el id de persona o servicio de RL
		if(arp.getNombreCompletoRl()!= null && arp.getNombreCompletoRl().trim().startsWith("----------")){
			this.log.debug("********** No se encontro RL se buscara por CURP del solicitante");
			arp.setNombreCompletoRl(" ");						
			//buscar por la curp de la solicitud la persona en BD
			Fisica rl = null;
			if(curpSolicitante != null && curpSolicitante.trim().length() > 0){
				this.log.debug("********** Buscando datos del solicitante por CURP: " + curpSolicitante);
				rl = representanteLegalService.getPersonaByCurp(curpSolicitante); 
			}else{
				this.log.debug("********** La CURP del solicitante viene NULL o vacia");
			}
			if(rl != null){
				String nombreC = "";
				if(rl.getNombre() != null){
					nombreC = rl.getNombre(); 
				}
				if(rl.getPrimerApellido() != null){
					nombreC += " " + rl.getPrimerApellido(); 
				}
				if(rl.getSegundoApellido() != null){
					nombreC += " " + rl.getSegundoApellido(); 
				}				
				this.log.debug("********** RL encontrado por CURP: " + nombreC);
				arp.setNombreCompletoRl(nombreC);
			}
			//Si no se encontro el solicitante por CURP se busca por servicio de RL
			if(arp.getNombreCompletoRl() == null || arp.getNombreCompletoRl().trim().length() == 0){
				if(sujetoObligado.getTipoPersonaFiscal().getCodigo().equals(TipoPersonaFiscal.MORAL.getCodigo())){			
					TipoPersonaEnum tipoPersona = TipoPersonaEnum.MORAL;
					this.log.debug("********** Buscamos el RL por tipo de persona: IdPersona - " + soRp.getMoral().getIdPersona() + ", rfc - " + soRp.getMoral().getRfc());
					List<RepresentanteLegal> lRL = representanteLegalService
							.obtenerRepresentantesLegalesPorPersona(soRp.getMoral().getIdPersona(), tipoPersona);
					if(lRL == null || lRL.size() == 0){
						// Se busca por RFC de la persona moral
						this.log.debug("********** Buscamos el RL por RFC: " + soRp.getMoral().getRfc());
						lRL = representanteLegalService.obtenerRepresentantesPorRFCMoral(soRp.getMoral().getRfc());					
					}
					// si encontramos RL
					if(lRL != null && lRL.size() > 0){
						RepresentanteLegal representanteLegal = null;
						Fisica pRl = null;
						if(curpSolicitante != null && curpSolicitante.trim().length() > 0){
							//recorremos la lista y recuperamos el que coincida con la CURP del solicitante
							for (Iterator<RepresentanteLegal> iterator = lRL.iterator(); iterator.hasNext();) {
								representanteLegal = iterator.next();
								pRl = representanteLegal.getPersonaFisica();
								if(pRl.getCurp().trim().equals(curpSolicitante)){
									break;
								}								
							}
						}else{ //si la curp del solicitante viene vacia tomamos el primer resultado
							representanteLegal = lRL.get(0);
							pRl = representanteLegal.getPersonaFisica();
						}							
						if(pRl !=  null){
							StringBuffer rlQueEjecutaAlta=new StringBuffer();
							if(!StringUtils.isBlank(pRl.getNombre()))
								rlQueEjecutaAlta.append(pRl.getNombre());
							if(!StringUtils.isBlank(pRl.getPrimerApellido()))
								rlQueEjecutaAlta.append(" "+pRl.getPrimerApellido());
							if(!StringUtils.isBlank(pRl.getSegundoApellido()))
								rlQueEjecutaAlta.append(" "+pRl.getSegundoApellido());				
							arp.setNombreCompletoRl(rlQueEjecutaAlta.toString());						
							this.log.debug("********** Encontre el RL por servicio: " + arp.getNombreCompletoRl());
						}
					}else{
						this.log.debug("********** No se encontraron RL para el patron: " + sujetoObligado.getNumeroRegistroPatronal());
					}
				}				
			}
		}
		
		if(arp.getNombrePA1() != null &&  arp.getNombrePA1().startsWith("----------------------------")){
			arp.setNombrePA1(" ");
		}
		if(arp.getNombrePA2() != null &&  arp.getNombrePA2().startsWith("----------------------------")){
			arp.setNombrePA2(" ");
		}
		if(arp.getNombrePA3() != null &&  arp.getNombrePA3().startsWith("----------------------------")){
			arp.setNombrePA3(" ");
		}
		
		return arp;
	}	
	
    private String getNombreMesDate(Date date){
        Locale loc_mx = new Locale("es", "MX");
        SimpleDateFormat sf = new SimpleDateFormat("MMMMMMMMMM",loc_mx);
        return sf.format(date).toString();
    }
	
    /**
     * Este metodo procesara los parametros del reporte PDF para generar la TIP
     * @param solicitud
     * @return
     */
	@Override
	public byte[] getTipPersona(String folioSolicitud)
			throws Exception {
		
		this.log.debug("**********Entre al ArpBusiness:getTipPersona folio solicitud: " + folioSolicitud);
		ReportesARP arp = null;
		//mapa para guardar el sello digital y la cadena original
		Map<String, String> cadenas = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

//		arp = getDatosHarkcode(arp);

		//Obtiene sujeto obligado
		Solicitud solicitud = obtieneSolicitud(folioSolicitud);
		SujetoObligado sujetoObligado = obtieneSujetoObligado(solicitud.getTramites());
		
		if(sujetoObligado == null)
			this.log.debug("********** sujetoObligado es NULL");

        this.log.debug("**********Obteniendo datos del registro patronal");

		arp = arpUtility.getDatosPersonaTIP(sujetoObligado, representanteLegalService, 
			personasAutorizadasService, domicilioService);
		
		Persona personaCadena = sujetoObligado.getFisica() == null ? sujetoObligado.getMoral() : sujetoObligado.getFisica();
		cadenas = firmaDigitalBusinessRemote.getCadenaOriginalYSelloDigital(solicitud,personaCadena,sujetoObligado.getNumeroRegistroPatronal(),null);
		
		arp.setFolioNRP(folioSolicitud);
		arp.setCadenaOriginal(cadenas.get("cadenaOriginal"));
		arp.setFirmaDigital(cadenas.get("selloDigital"));
		arp.setNumNotarira(cadenas.get("secuenciaNotaria"));
		arp.setNumeroReferencia(cadenas.get("numeroSerie"));
		arp.setOrigenSolicitud(solicitud.getOrigenSolicitud().getIdTipoSolicitud().intValue());
		if(solicitud.getSolicitante()!=null)
			arp.setUsuario(solicitud.getSolicitante().getUsuario());
		if(sujetoObligado.getSubdelegacion()!=null){
			Subdelegacion subdelegacion = afiliacionService.obtenerSubdelegacion(sujetoObligado.getSubdelegacion().getId());
			arp.setSubDelegacion(subdelegacion.getDescripcion());
		}
		
		insertarSolicitudFirmaDigitalArp(solicitud, cadenas);

		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			ArrayList<ReportesARP> beanData = new ArrayList<ReportesARP>();

			String comprobante = "TIP.jasper";
			beanData.add(arp);

			JasperReport report = (JasperReport) JRLoader.loadObject(new ClassPathResource("comprobantes/"+ comprobante).getInputStream());
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(beanData);
			JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource);

			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
			exporter.exportReport();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.log.debug("**********regresando byteArrayOutputStream");
		
		return byteArrayOutputStream.toByteArray();
	} 	
	
    /**
     * Este metodo procesara los parametros del reporte PDF para generar el ARP
     * @param solicitud
     * @return
     */
	@Override
	public byte[] getArpPersonaMoralTest(String folioSolicitud)
			throws Exception {
		
		this.log.debug("**********Entre al ArpBusiness:getArpPersonaMoralTest folio solicitud: " + folioSolicitud);
				
		ReportesARP arp = new ReportesARP();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

//		arp = getDatosHarkcode(arp);

		//Obtiene sujeto obligado
		Solicitud solicitud = obtieneSolicitud(folioSolicitud);				
		SujetoObligado sujetoObligado = obtieneSujetoObligado(solicitud.getTramites());
		
		if(sujetoObligado != null){
			if(sujetoObligado.getClasificacion() != null && sujetoObligado.getClasificacion().getFecEfecto() != null)
				arp.setFecEfecto(sujetoObligado.getClasificacion().getFecEfecto());
		}else
			this.log.debug("********** sujetoObligado es NULL");

        this.log.debug("**********Obteniendo datos del registro patronal");

		arp = arpUtility.getDatosPersonaFisica(sujetoObligado, domicilioService, representanteLegalService,
				mediosContactoService, personasAutorizadasService, solicitud.getFechaSolicitud());
		        
		arp.setFolioNRP(folioSolicitud);
		arp.setCadenaOriginal(solicitud.getCadenaOriginal());
		arp.setFirmaDigital(solicitud.getSelloDigital());

		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
	        ArrayList<ReportesARP> beanData = new ArrayList<ReportesARP>();
			
			List<JasperPrint> arreglo = new ArrayList<JasperPrint>(); 			
			JRBeanCollectionDataSource dataSource = null;
			JasperPrint print = null;
			beanData.add(arp);
			
			parameters.put("header", new ClassPathResource("comprobantes/headerPicture.png").getPath());
			parameters.put("PIE", new ClassPathResource("comprobantes/footer.png").getPath());
			
			List<String> comprobantes = new ArrayList<String>();
			/*TODO cuando se liberen los reportes con gobmx se descomentesta parte y se comenta la otra*/
			comprobantes.add("afil01RV3.jasper"); 
			comprobantes.add("afil02RV3.jasper"); 
			comprobantes.add("afil03RV3.jasper"); 
			comprobantes.add("afil04RV3.jasper"); 
			/*comprobantes.add("afil01R.jasper");
			comprobantes.add("afil02R.jasper");
			comprobantes.add("afil03R.jasper");
			comprobantes.add("afil04R.jasper");*/
			
			for (String comprobante : comprobantes) {
				JasperReport report = (JasperReport) JRLoader.loadObject(new ClassPathResource("comprobantes/"+comprobante).getInputStream());
				dataSource = new JRBeanCollectionDataSource(beanData);
				print = JasperFillManager.fillReport(report,parameters,dataSource);
				arreglo.add(print);
			}
			
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,arreglo);			
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,byteArrayOutputStream);			
			exporter.exportReport();
			
		} catch (Exception e) {// Agregar las excepciones personalizadas
			e.printStackTrace();
		}
        
		this.log.debug("**********regresando byteArrayOutputStream");
		
		return byteArrayOutputStream.toByteArray();
	} 


    /**
     * Este metodo procesara los parametros del reporte PDF para generar el ARP
     * @param solicitud
     * @return
     */
	@Override
	public Map<String, Object> getArpModelPersona(String folioSolicitud)
			throws Exception {
		
		this.log.debug("**********Entre al ArpBusiness:getArpModelPersona folio solicitud: " + folioSolicitud);
				
	    Date  fechaIni= new Date();
	    Date  fechaFin = null;
		Map<String, Object> repModel = new HashMap<String, Object>();
		ReportesARP arp = new ReportesARP();

//		arp = getDatosHarkcode(arp);

		//Obtiene sujeto obligado
		Solicitud solicitud = obtieneSolicitud(folioSolicitud);				
		SujetoObligado sujetoObligado = obtieneSujetoObligado(solicitud.getTramites());
				
		if(sujetoObligado != null){
			if(sujetoObligado.getClasificacion() != null && sujetoObligado.getClasificacion().getFecEfecto() != null)
				arp.setFecEfecto(sujetoObligado.getClasificacion().getFecEfecto());
		}else
			this.log.debug("********** sujetoObligado es NULL");

        this.log.debug("**********Obteniendo datos del registro patronal");

		if(sujetoObligado.getTipoPersonaFiscal().getCodigo().equals(TipoPersonaFiscal.FISICA.getCodigo())){
			arp = arpUtility.getDatosPersonaFisica(sujetoObligado,domicilioService, representanteLegalService,
					mediosContactoService, personasAutorizadasService,solicitud.getFechaSolicitud());
			repModel.put("viewName", "pf");
		}else{
			
			if (CollectionUtils.isEmpty(sujetoObligado.getSocios())
					&& sujetoObligado.getMoral() != null
					&& sujetoObligado.getMoral().getIdPersona() != null) {
				this.log.debug("No se tienen socios en el sujeto obligado, se procede a consultarlos directamente a BDTU");
				
				Socio socio = new Socio();
				socio.setIdPersonaMoralPatron(sujetoObligado.getMoral().getIdPersona());
				
				List<Socio> socios = this.socioServiceBusiness
						.sociosPorIdPersonaMoralPatron(socio);
				
				if (CollectionUtils.isEmpty(socios)) {
					this.log.debug("No se encontraron socios para el sujeto obligado [cveIdSujetoObligado = "
							+ sujetoObligado.getCveIdSujetoObligado() + "]");
				} else {
					this.log.debug("Se encontraron "
							+ socios.size()
							+ " socios para el sujeto obligado [cveIdSujetoObligado = "
							+ sujetoObligado.getCveIdSujetoObligado() + "]");
					sujetoObligado.setSocios(socios);
				}
			}
			
			arp = arpUtility.getDatosPersonaMoral(sujetoObligado, domicilioService, representanteLegalService,
					mediosContactoService, personasAutorizadasService, solicitud.getFechaSolicitud());
	        repModel.put("viewName", "pm");
		}
		
	    fechaFin = new Date();
        
	    //calcula el tiempo de generacion del arp y folio
		arp.setFolioNRP(folioSolicitud);
	    arp.setTiempoAlta(arpUtility.getTiempoAlta(fechaIni, fechaFin));        
		arp.setCadenaOriginal(solicitud.getCadenaOriginal());
		arp.setFirmaDigital(solicitud.getSelloDigital());

	    //agregando el VO obtenido a una coleccion para que pueda ser enviado al jasper por spring mvc 
        ArrayList<ReportesARP> beanData = new ArrayList<ReportesARP>();
        beanData.add(arp);
        repModel.put("arp", beanData);
		
		this.log.debug("**********regresando repModel");
		
		return repModel;
	} 
	
    /**
     * Este metodo procesara los parametros del reporte PDF para generar la TIP
     * @param solicitud
     * @return
     */
	@Override
	public Map<String, Object> getTipModelPersona(String folioSolicitud)
			throws Exception {
		
		this.log.debug("**********Entre al ArpBusiness:getTipModelPersona folio solicitud: " + folioSolicitud);
				
	    Date  fechaIni= new Date();
	    Date  fechaFin = null;
		Map<String, Object> repModel = new HashMap<String, Object>();
		ReportesARP arp = null;

//		arp = arpUtility.getDatosHarkcode(arp);

		//Obtiene sujeto obligado
		Solicitud solicitud = obtieneSolicitud(folioSolicitud);				
		SujetoObligado sujetoObligado = obtieneSujetoObligado(solicitud.getTramites());
				
		if(sujetoObligado == null)
			this.log.debug("********** sujetoObligado es NULL");

        this.log.debug("**********Obteniendo datos del registro patronal");

		arp = arpUtility.getDatosPersonaTIP(sujetoObligado, representanteLegalService, 
			personasAutorizadasService, domicilioService);
		
	    fechaFin = new Date();	    
	    //calcula el tiempo de generacion del arp y folio
		arp.setFolioNRP(folioSolicitud);
	    arp.setTiempoAlta(arpUtility.getTiempoAlta(fechaIni, fechaFin));        
		arp.setCadenaOriginal(solicitud.getCadenaOriginal());
		arp.setFirmaDigital(solicitud.getSelloDigital());
	    
	    //agregando el VO obtenido a una coleccion para que pueda ser enviado al jasper por spring mvc 
        ArrayList<ReportesARP> beanData = new ArrayList<ReportesARP>();
        beanData.add(arp);
        repModel.put("arp", beanData);
		
		this.log.debug("**********regresando repModel");
		
		return repModel;
	} 
	
	private Solicitud obtieneSolicitud(String folioSolicitud){
		Solicitud solicitud = new Solicitud();		
		solicitud.setNoFolioSolicitud(folioSolicitud);
		try {
			solicitud = solicitudBusiness.consultarFolio(solicitud);
		} catch (SolicitudNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return solicitud;
	}

	private SujetoObligado obtieneSujetoObligado(List<Tramite> tramites){
		Tramite tramite = null;
		TramiteSujetoObligado tso = null;
		for (Iterator<Tramite> iterator = tramites.iterator(); iterator.hasNext();) {
			tramite = iterator.next();
			if(tramite instanceof TramiteSujetoObligado){
				tso = (TramiteSujetoObligado)tramite;
				if(tso.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ALTA_SRT.getCodigo().intValue() ||
						tso.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ALTA_SRT_PM.getCodigo().intValue()) {
					break;
				}else
					tso = null;
			}			
		}		
		if(tso.getSujetoObligado() == null)
			this.log.debug("********** sujetoObligado es NULLLLLLLLLLLLLLL");
		
		return tso.getSujetoObligado();
	}

	private void insertarSolicitudFirmaDigitalArp(Solicitud solicitud, Map<String, String> cadenas) {
		FirmaElectronica datosFirma = firmaDigitalBusinessRemote.getFirmaElectronica(solicitud);
		if (datosFirma == null) {
			FirmaElectronica firmaElectronica = new FirmaElectronica();
			firmaElectronica.setCadenaOriginal(cadenas.get("cadenaOriginal"));
			firmaElectronica.setReciboNotarial(cadenas.get("tramite"));
			firmaElectronica.setSecuenciaNotaria(cadenas.get("tramite"));
			firmaElectronica.setSerialCertificado(cadenas.get("numeroSerie"));
			firmaElectronica.setRecibo(cadenas.get("selloDigital"));
			firmaElectronica.setUrlAcuseFirma("");
			firmaElectronica.setIniciaVigenciaCertificado(new Date());
			firmaElectronica.setFinVigenciaCertificado(new Date());

			firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud,
					firmaElectronica);
		}
	}

	private void detectarSecuenciaNotaria(Solicitud solicitud) throws SolicitudNoEncontradaException {
		Solicitud solicitudTemp = solicitudBusiness.consultarFolioSinDatosTramite(solicitud);
    	FirmaElectronica datosFirma = firmaDigitalBusinessRemote.getFirmaElectronica(solicitudTemp);

    	if (datosFirma != null) {
    		solicitud.setSecuenciaDeNotaria(datosFirma.getSecuenciaNotaria());
    	}
	}
	
	@Override
	public byte[] visualizacionPreviaAltaPatron(TramiteSujetoObligado tramiteSujetoObligado) throws Exception {
		Map<String, String> cadenas = null;		
		ReportesARP arp = new ReportesARP();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    String tipo = null;
	    Date fechaPresentacionArp = new Date();
		
		SujetoObligado sujetoObligado = tramiteSujetoObligado.getSujetoObligado();
			
		if (CollectionUtils.isEmpty(sujetoObligado.getSocios())
				&& sujetoObligado.getMoral() != null
				&& sujetoObligado.getMoral().getIdPersona() != null) {
			this.log.debug("No se tienen socios en el sujeto obligado, se procede a consultarlos directamente a BDTU");
			
			Socio socio = new Socio();
			socio.setIdPersonaMoralPatron(sujetoObligado.getMoral().getIdPersona());
			
			List<Socio> socios = this.socioServiceBusiness
					.sociosPorIdPersonaMoralPatron(socio);
			
			if (CollectionUtils.isEmpty(socios)) {
				this.log.debug("No se encontraron socios para el sujeto obligado [cveIdSujetoObligado = "
						+ sujetoObligado.getCveIdSujetoObligado() + "]");
			} else {
				this.log.debug("Se encontraron "
						+ socios.size()
						+ " socios para el sujeto obligado [cveIdSujetoObligado = "
						+ sujetoObligado.getCveIdSujetoObligado() + "]");
				sujetoObligado.setSocios(socios);
			}
		}
		
		//consultamos el alta para tener todo el detalle
		if(sujetoObligado.getMoral().getEscrituraConstitutiva()!=null
				&& sujetoObligado.getMoral().getEscrituraConstitutiva().getCveEscrituraConstitutiva()!=null){
			EscrituraConstitutiva acta = sujetoObligadoService.obtenerEscrituraConstitutivaPorId(sujetoObligado.getMoral().getEscrituraConstitutiva().getCveEscrituraConstitutiva());
			sujetoObligado.getMoral().setEscrituraConstitutiva(acta);
		}
		arp = arpUtility.getDatosPersonaMoral(sujetoObligado, domicilioService, representanteLegalService,
				mediosContactoService, personasAutorizadasService, fechaPresentacionArp);
        tipo  =  "RV3"; //TODO cuando se liberen los reportes se descomenta esta linea y se sube la otra
        //tipo = "R";
		
        //TODO Checarcuando ya se tengan los datos de sesion
//		if(solicitud.getSolicitante()!=null)
//			arp.setUsuario(solicitud.getSolicitante().getUsuario());
        
		/**
		 * Al guardar el xml como el nombre de subdelegacion tiene "" JAXB corta la cadena, para
		 * asegurar que el nombre va completo se consulta la subdelegacion nuevamente
		 */
		//TODO
		if(sujetoObligado.getSubdelegacion()!=null){
			Subdelegacion subdelegacion = afiliacionService.obtenerSubdelegacion(sujetoObligado.getSubdelegacion().getId());
			arp.setSubDelegacion(subdelegacion.getDescripcion());
		}

		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
	        ArrayList<ReportesARP> beanData = new ArrayList<ReportesARP>();
			
			List<JasperPrint> arreglo = new ArrayList<JasperPrint>(); 			
			JRBeanCollectionDataSource dataSource = null;
			JasperPrint print = null;
			
			if(arp.getFecPresentacion()==null){
				System.err.println("Se asigna fecha en el ultimo paso");
				arp.setFecPresentacion(Calendar.getInstance().getTime());
			}
			
			beanData.add(arp);
			
			parameters.put("header", new ClassPathResource("comprobantes/headerPicture.png").getPath());
			parameters.put("PIE", new ClassPathResource("comprobantes/footer.png").getPath());
			
			List<String> comprobantes = new ArrayList<String>();
			comprobantes.add("afil01"+tipo+".jasper");
			if(!tipo.equals("F")) {
				comprobantes.add("afil02"+tipo+".jasper");
				comprobantes.add("afil03"+tipo+".jasper");
				comprobantes.add("afil04"+tipo+".jasper");
			}
			
			System.err.println("Subdel enviado a reporte: "+arp.getSubDelegacion());
			
			for (String comprobante : comprobantes) {
				JasperReport report = (JasperReport) JRLoader.loadObject(new ClassPathResource("comprobantes/"+comprobante).getInputStream());
				dataSource = new JRBeanCollectionDataSource(beanData);
				print = JasperFillManager.fillReport(report,parameters,dataSource);
				arreglo.add(print);
			}
			
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,arreglo);			
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,byteArrayOutputStream);			
			exporter.exportReport();
			
		} catch (Exception e) {// Agregar las excepciones personalizadas
			e.printStackTrace();
		}
        
		this.log.debug("**********regresando byteArrayOutputStream");
		System.err.println("Subdel enviado a reporte: "+arp.getSubDelegacion());
		
		return byteArrayOutputStream.toByteArray();
	}
}
