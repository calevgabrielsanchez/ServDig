package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity.SolicitudEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.AcuseTramitesVentanillaBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.utility.AcuseTramitesVentanillaUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.AcuseVentanilla;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.springframework.core.io.ClassPathResource;

@Stateless(name = "acuseTramitesVentanillaBusiness", mappedName = "acuseTramitesVentanillaBusiness")
public class AcuseTramitesVentanillaBusiness 
	extends AbstractServiceBusiness implements AcuseTramitesVentanillaBusinessRemote{

    @EJB
    private SolicitudEntityLocal solicitudEntity;
    @EJB
    private AcuseTramitesVentanillaUtilityLocal acuseTramitesVentanillaUtility;
	@EJB
    private FirmaDigitalBusinessRemote firmaDigitalBusiness;
	
	@Override
	public byte[] generarAcuseTramiteVentanilla(Solicitud solicitud, Tramite tramite, Long idTipoTramite) {
		OrigenSolicitudEnum origenSolicitud = OrigenSolicitudEnum
			.getById(solicitud.getOrigenSolicitud().getIdTipoSolicitud());		
		byte[] documento=null;
		
		if(origenSolicitud.equals(OrigenSolicitudEnum.VENTANILLA)){
			//Obtener acuse del tramite
			documento = (byte[])solicitudEntity
				.getDocumentoPorTipoIdTramite(tramite.getTramiteId(), DocumentoPorTipoEnum.ACUSE_TRAMITE.getId());
			
			if(documento == null) {
				//Preparar datos para generar reporte
				AcuseVentanilla acuseVentanilla = acuseTramitesVentanillaUtility
					.prepararDatosAcusePorTramite(solicitud, tramite, idTipoTramite);
				
				if(acuseVentanilla != null) {
					//Procesar firma
					String cadenaOriginal = acuseTramitesVentanillaUtility
						.generarCadenaOriginalIMSS(solicitud, acuseVentanilla);				
					FirmaElectronica firmaElectronica = firmaDigitalBusiness.convertirRespuestaFirmadoSimple(
						cadenaOriginal, firmaDigitalBusiness.getSelloDigital(cadenaOriginal, 
						solicitud.getSecuenciaDeNotaria(), acuseVentanilla.getRfc()));			
					firmaDigitalBusiness.insertarSolicitudFirmaDigital(solicitud, firmaElectronica);				
					acuseVentanilla = acuseTramitesVentanillaUtility
						.complementarDatosFirma(acuseVentanilla, firmaElectronica);
					//Generar documento Acuse Recibo Ventanilla
					documento = generaReporteAcuseVentanilla(acuseVentanilla);
					//Almacenar documento Notaria
					if(solicitud.getSecuenciaDeNotaria() != null) {
						firmaDigitalBusiness.guardarArchivoFirmado(firmaElectronica.getSecuenciaNotaria(), 
							"AcuseReciboVentanilla"+solicitud.getNoFolioSolicitud()+".pdf", documento);
					}
					//Asociar documento a tramite
					solicitudEntity.actualizarDocumentosTramite(tramite.getTramiteId(), 
						DocumentoPorTipoEnum.ACUSE_TRAMITE.getId(), documento);
				}
			}
		}
		return documento;
	}
	
	private byte[] generaReporteAcuseVentanilla(AcuseVentanilla acuseVentanilla){
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			JREmptyDataSource emptyDS = new JREmptyDataSource();
			Map<String, Object> parameters = acuseTramitesVentanillaUtility
				.generarParametrosReporte(acuseVentanilla);	
			JasperReport report = (JasperReport) JRLoader.loadObject(
				new ClassPathResource("reportes/acuseVentanilla/AcuseVentanilla.jasper").getInputStream());
			JasperPrint print = JasperFillManager.fillReport(report, parameters, emptyDS);
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,byteArrayOutputStream);
			exporter.exportReport();				
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return byteArrayOutputStream.toByteArray();
	}
	
	
}
