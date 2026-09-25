package mx.gob.imss.ctirss.delta.gestion.beneficio.service.business;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.ReportesBeneficiosBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.utility.BeneficioServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.utility.BeneficiosConstants;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.GenerarCodigoQRServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.beneficio.RespuestaRifSat;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRiss;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.CollectionUtils;

@Stateless(name = "reportesBeneficiosBusiness", mappedName = "reportesBeneficiosBusiness")
public class ReportesBeneficiosBusiness extends AbstractServiceBusiness
implements ReportesBeneficiosBusinessRemote {

	@EJB
	private BeneficioServiceUtilityLocal beneficioServiceUtility;
	@EJB
	private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;
	@EJB
    private FirmaDigitalBusinessRemote firmaDigitalBusiness;
	@EJB
	private PersonaBusinessRemote personaBusiness;
	@EJB
	private GenerarCodigoQRServiceBusinessRemote generarCodigoQRServiceBusiness;
	
	public String generarCadenaOriginal(Solicitud solicitud, Persona persona){
		return beneficioServiceUtility.generarCadenaOriginal(solicitud, persona);
	}

	public byte[] generarReporteBeneficioRiss(Solicitud solicitud) {		
		Fisica personaTramite = null;
		RespuestaRifSat respuestaRifSat = null;
		TramiteRiss tramiteSO = beneficioServiceUtility.obtenerTramitePatron(solicitud);
		TramiteRiss tramitePF = beneficioServiceUtility.obtenerTramitePersonaFisica(solicitud);
		List<SujetoObligado> listaSujetosObligados = new ArrayList<SujetoObligado>();
		//Obtener la persona fisica (independiente o patron) y la respuesta SAT
		if(tramitePF!=null){
			respuestaRifSat=tramitePF.getRespuestaRifSat();			
			personaTramite = personaBusiness.getPersonaFisica(tramitePF.getFisica().getIdPersona());
			if (personaTramite != null && personaTramite.getRfc() == null) {
				personaTramite.setRfc(tramitePF.getRfcSolicitud());
			}
				
		}
		if(tramiteSO!=null){
			if (respuestaRifSat == null) {
				respuestaRifSat = tramiteSO.getRespuestaRifSat();
			}
			
			List<Long> listaSOclaves = tramiteSO.getListaCveIdSujetosObligados();
			if((!CollectionUtils.isEmpty(listaSOclaves))){
				for(Long idSujeto : listaSOclaves){
					SujetoObligado so = sujetoObligadoServiceBusiness
						.obtenerDetalleRegistroPatronalPorClaveTipoPersona(idSujeto, TipoPersonaFiscal.FISICA);
					listaSujetosObligados.add(so);
					if(personaTramite==null){
						personaTramite = personaBusiness.getPersonaFisica(so.getFisica().getIdPersona());
						if (personaTramite != null && personaTramite.getRfc() == null) {
							personaTramite.setRfc(tramiteSO.getRfcSolicitud());
						}
					}
				}
			}			
		}
		if(personaTramite!=null && respuestaRifSat!=null){
			byte[] documento=null;		
			OrigenSolicitudEnum origen = OrigenSolicitudEnum.getById(solicitud.getOrigenSolicitud().getIdTipoSolicitud());
			log.warn("Secuencia de notaria INICIAL: " + solicitud.getSecuenciaDeNotaria());
			String cadenaOriginal = generarCadenaOriginal(solicitud, personaTramite);
			FirmaElectronica firmaElectronica = firmaDigitalBusiness.convertirRespuestaFirmadoSimple(
				cadenaOriginal, firmaDigitalBusiness.getSelloDigital(cadenaOriginal, 
				solicitud.getSecuenciaDeNotaria(), personaTramite.getRfc()));
			log.warn("firmaElectronica: " + firmaElectronica.toString());			
			if(origen.equals(OrigenSolicitudEnum.VENTANILLA)){
				//Para Ventanilla almacenar la firma digital generada, por la Secuencia de Notaria generada
				firmaDigitalBusiness.insertarSolicitudFirmaDigital(solicitud, firmaElectronica);
			}
			documento = generaReporteBeneficioRiss(personaTramite, cadenaOriginal, 
				firmaElectronica, respuestaRifSat, (tramitePF!=null ? true : false), 
				(tramiteSO!=null ? true : false), solicitud.getFechaSolicitud(), listaSujetosObligados,
				solicitud);			
			if(solicitud.getSecuenciaDeNotaria() != null) {
				firmaDigitalBusiness.guardarArchivoFirmado(firmaElectronica.getSecuenciaNotaria(), 
					BeneficiosConstants.REPORTES_NOMBRE_BENEFICIO_RISS+solicitud.getNoFolioSolicitud()
						+BeneficiosConstants.REPORTES_EXTENCION_PDF, documento);
			}
			return documento;	
		}else{
			log.warn("No se genera COMPROBANTE DE ALTA DE BENEFICIO RISS al no cumplir con datos necesarios");
		}
		return null;
	}
	
	
	
	//Metodos privados	
	private byte[] generaReporteBeneficioRiss(Fisica persona, String cadenaOriginal, 
			FirmaElectronica firmaElectronica, RespuestaRifSat respuestaRifSat, boolean tramiteFisica, 
			boolean tramitePatron, Date fechaAltaBeneficio, List<SujetoObligado> listaSujetosObligados,
			Solicitud solicitud){	
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {			
			JREmptyDataSource emptyDS = new JREmptyDataSource();
			Map<String, Object> parameters = beneficioServiceUtility
				.generarParametrosReporteRiss(persona, firmaElectronica, respuestaRifSat, 
					tramiteFisica,tramitePatron, fechaAltaBeneficio, listaSujetosObligados,
					solicitud,generarCodigoQRServiceBusiness.generadorCodigoQrUrl(recuperarUrlImssDigital(), 
						BeneficiosConstants.IMAGEN_QR_ANCHO, BeneficiosConstants.IMAGEN_QR_ALTO));	
			JasperReport report = (JasperReport) JRLoader.loadObject( 
				new ClassPathResource(BeneficiosConstants.REPORTES_BENEFICIO_RISS_JASPER).getInputStream());
			JasperPrint print = JasperFillManager.fillReport(report, parameters, emptyDS);
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,byteArrayOutputStream);
			exporter.exportReport();			
		} catch (Exception e) {
			log.error(e);
		}
		return byteArrayOutputStream.toByteArray();
	}
	
	private String recuperarUrlImssDigital(){
		Properties prop = new Properties();
		String url="";
		try{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream(BeneficiosConstants.ARCHIVO_PROPERTIES);
			prop.load(input);			
			url = prop.getProperty(BeneficiosConstants.URL_IMSS_DIGITAL);
			if (url == null) {
				return "";
			}
		}catch (Exception e){
			log.error("error al cargar las propiedades " ,e);			
		}
		return url;
	}

}
