/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.reporte;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.reporte.ReporteEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.reporte.ManejadorReportesRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.dto.CartaTerminosDto;
import mx.gob.imss.ctirss.delta.model.enums.TipoDomicilioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EscrituraConstitutiva;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RegistroSindicato;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteMoral;
import mx.gob.imss.ctirss.delta.model.persona.hlda.HldaDetalleVO;
import mx.gob.imss.ctirss.delta.model.persona.hlda.HldaVO;
import mx.gob.imss.ctirss.delta.tramite.service.entity.TramiteServiceEntityLocal;
import mx.gob.imss.ctirss.gestionpersonas.servicios.hlda.interfaces.HldaClientServiceRemote;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;


/**
 * 
 * @author I
 */
@Stateless(name = "manejadorReportesBusiness", mappedName = "manejadorReportesBusiness")
public class ManejadorReportes implements ManejadorReportesRemote,
		ManejadorReportesLocal {

	public static final String sParrafoFinal1 = "EN ELECTR�NICO, EL CUAL INCORPORA FIRMA ELECTR�NICA, EN SUSTITUCI�N DE LA AUTOGRAFA Y CON EL MISMO VALOR PROBATORIO";
	public static final String sParrafoFinal2 = "DE MANERA PRESENCIAL";

	protected final Log log = LogFactory.getLog(getClass());
	
	private Connection conexion;

	// @Resource(name = "pathIMG")
	private String pathIMG = "reportes/";
	
	@EJB
	ReporteEntityLocal reporteEntity;
	
	@EJB
	SolicitudServiceBusinessRemote solicitudService;
	
	@EJB
	TramiteServiceEntityLocal tramiteServiceEntityLocal;
	
	@EJB
	SujetoObligadoServiceBusinessRemote sujetoService;
	
	@EJB
	HldaClientServiceRemote hldaService;
	
	@PostConstruct
	public void initialize() {
		try {
			conexion = reporteEntity.retrieveCMTConnection();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		}

	}

	@PreDestroy
	public void cleanup() {
		try {
			conexion.close();
			conexion = null;
		} catch (SQLException sqle) {// agregar excepciones personalizadas
			sqle.printStackTrace();
		}
	}

	@Override
	public ByteArrayOutputStream ejecutaCLEM04Delegacional(Long cveSolicitud,
			String cveAnalisis, String cveCLEM, String delegacion,
			String subDelegacion, String fraccion, String inciso,
			String psp15A, String psp19, String art20, String art26,
			String art28) {

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros
				.put("IMAGENES_DIR", new ClassPathResource(pathIMG).getPath());
		parametros.put("CVE_SOLICITUD", cveSolicitud);
		parametros.put("CVE_ANALISIS_CE", cveAnalisis);
		parametros.put("CVE_CLEM", cveCLEM);
		parametros.put("DELEGACION", delegacion);
		parametros.put("SUBDELEGACION", subDelegacion);
		parametros.put("FRACCION115", fraccion);
		parametros.put("INCISO115", inciso);

		parametros.put("PSP15A", psp15A);
		parametros.put("PSP19", psp19);
		parametros.put("ART20", art20);
		parametros.put("ART26", art26);
		parametros.put("ART28", art28);

		return ejecutaReporte(parametros, "Clem04Delegacional.jrxml", true);
	}

	@Override
	public ByteArrayOutputStream ejecutaCLEM04SubDelegacional(
			Long cveSolicitud, String cveAnalisis, String cveCLEM,
			String delegacion, String subDelegacion, String fraccion,
			String inciso, String psp15A, String psp19, String art20,
			String art26, String art28) {

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros
				.put("IMAGENES_DIR", new ClassPathResource(pathIMG).getPath());
		parametros.put("CVE_SOLICITUD", cveSolicitud);
		parametros.put("CVE_ANALISIS_CE", cveAnalisis);
		parametros.put("CVE_CLEM", cveCLEM);
		parametros.put("DELEGACION", delegacion);
		parametros.put("SUBDELEGACION", subDelegacion);
		parametros.put("FRACCION115", fraccion);
		parametros.put("INCISO115", inciso);

		parametros.put("PSP15A", psp15A);
		parametros.put("PSP19", psp19);
		parametros.put("ART20", art20);
		parametros.put("ART26", art26);
		parametros.put("ART28", art28);

		return ejecutaReporte(parametros, "Clem04Subdelegacional.jrxml", true);

	}

	// Ejecuta el reporte Jasper y regresa un ByteArrayOutputStream
	private ByteArrayOutputStream ejecutaReporte(
			Map<String, Object> parametros, String reporte,
			boolean pasarConexion) {

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {

			JasperReport report = JasperCompileManager
					.compileReport(new ClassPathResource("reportes/" + reporte)
							.getInputStream());
			JasperPrint print;
			if (pasarConexion) {
				print = JasperFillManager.fillReport(report, parametros,conexion);
			} else {
				JREmptyDataSource emptyDS = new JREmptyDataSource();
				print = JasperFillManager.fillReport(report, parametros, emptyDS);
			}

			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					byteArrayOutputStream);

			exporter.exportReport();

			return byteArrayOutputStream;

		} catch (Exception e) {// Agregar las excepciones personalizadas
			e.printStackTrace();
		}

		return null;
	}
	
	
	@Override
	public byte[] ejecutaAvisoDeModificacion(Map<String, Object> parametros, List<SujetoObligado> sujetos) {
		return ejecutaAvisoDeModificacion(parametros,sujetos,null);
	}

	@Override
	public byte[] ejecutaAvisoDeModificacion(Map<String, Object> parametros, List<SujetoObligado> sujetos, Solicitud solicitudRegis) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			
			log.debug(":::: Se va generar AMSRT");
			if(sujetos != null){
				log.debug("::::::::::::Total de registros patronales a sustituir/fusionar/patronAnterior: " + sujetos.size());
				for (Iterator<SujetoObligado> iterator = sujetos
						.iterator(); iterator.hasNext();) {
					SujetoObligado so = iterator.next();
					log.debug("::::: Registro a sustituir/fusionar/patronAnterior: " + so.getNumeroRegistroPatronal());
				}
			}else{
				log.debug(":::: La lista se patrones a sustituir viene NULl");
			}
			
			String folioSolicitud = (String)parametros.get("P_FOLIO_SOLCT");
			Solicitud solicitud = null;
			
			if(solicitudRegis != null && solicitudRegis.getTramites() !=null && !solicitudRegis.getTramites().isEmpty()) {
				solicitud = solicitudRegis;
			} else {
				solicitud = solicitudService.consultarSolicitudPorFolio(folioSolicitud);
			}
			
			log.debug("folio solicitud documento: "+folioSolicitud);
			parametros.put("IMAGENES_DIR", new ClassPathResource(pathIMG).getPath());
			parametros.put("SUBREPORT_DIR", new ClassPathResource(pathIMG).getPath());
			JasperReport report = JasperCompileManager.compileReport(new ClassPathResource("reportes/AvisoDeModificacion.jrxml").getInputStream());
			
			//Se compilan los subreportes
			JasperReport subReportDatosGeneral = JasperCompileManager.compileReport(new ClassPathResource("reportes/avisoDatosGenerales2.jrxml").getInputStream());
			JasperReport subCentroTRabajo = JasperCompileManager.compileReport(new ClassPathResource("reportes/avisoCentroTrabajo.jrxml").getInputStream());
			JasperReport subCentroTRabajoDifMun = JasperCompileManager.compileReport(new ClassPathResource("reportes/avisoCentroTrabajoDifMun.jrxml").getInputStream());
			JasperReport subClasificacion = JasperCompileManager.compileReport(new ClassPathResource("reportes/avisoClasificacion.jrxml").getInputStream());
			JasperReport subProductos = JasperCompileManager.compileReport(new ClassPathResource("reportes/avisoProductos.jrxml").getInputStream());
			JasperReport subMaterias = JasperCompileManager.compileReport(new ClassPathResource("reportes/SeccionVI-48.jrxml").getInputStream());
			JasperReport subMaquinaria = JasperCompileManager.compileReport(new ClassPathResource("reportes/SeccionVI-a.jrxml").getInputStream());
			JasperReport subTransporte = JasperCompileManager.compileReport(new ClassPathResource("reportes/SeccionVI-b.jrxml").getInputStream());
			JasperReport subProcesos = JasperCompileManager.compileReport(new ClassPathResource("reportes/avisoProcesos.jrxml").getInputStream());
			JasperReport subPersonal = JasperCompileManager.compileReport(new ClassPathResource("reportes/SeccionVI-d.jrxml").getInputStream());
			JasperReport subActividadesCom = JasperCompileManager.compileReport(new ClassPathResource("reportes/SeccionVI-e.jrxml").getInputStream());
			JasperReport subFusionSust = JasperCompileManager.compileReport(new ClassPathResource("reportes/SeccionSust.jrxml").getInputStream());
			JasperReport subBienes = JasperCompileManager.compileReport(new ClassPathResource("reportes/SeccionVIII.jrxml").getInputStream());
			JasperReport subFirmas = JasperCompileManager.compileReport(new ClassPathResource("reportes/SeccionIX.jrxml").getInputStream());
			JasperReport subFirmasIMSS = JasperCompileManager.compileReport(new ClassPathResource("reportes/SeccionIX-b.jrxml").getInputStream());
			
			log.debug("Voy a anadir los subreportes compilados");
			//Seccion para establecer los subreportes
			parametros.put("SUB_DATOS_GENERALES", subReportDatosGeneral);
			parametros.put("SUB_CENTRO", subCentroTRabajo);
			parametros.put("SUB_CENTRO_DOMANT", subCentroTRabajoDifMun);
			parametros.put("SUB_CLASIFICACION", subClasificacion);
			parametros.put("SUB_PRODUCTOS", subProductos);
			parametros.put("SUB_MATERIAS", subMaterias);
			parametros.put("SUB_MAQUINARIA", subMaquinaria);
			parametros.put("SUB_TRANSPORTE", subTransporte);
			parametros.put("SUB_PROCESOS", subProcesos);
			parametros.put("SUB_PERSONAL", subPersonal);
			parametros.put("SUB_ACTIVIDADES_COM", subActividadesCom);
			parametros.put("SUB_FUSION_SUS", subFusionSust);
			parametros.put("SUB_BIENES", subBienes);
			parametros.put("SUB_FIRMAS", subFirmas);
			parametros.put("SUB_FIRMA_IMSS", subFirmasIMSS);
			
			JasperPrint print;
			
			if(parametros.get("P_CVE_TRAMITE").equals("ACD")) {
				sujetos.get(0).getSujetosObligados().clear(); 
				
			}
			
			JRBeanCollectionDataSource beanDS = new JRBeanCollectionDataSource(sujetos);
			
			
			print = JasperFillManager.fillReport(report, parametros, beanDS);
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,byteArrayOutputStream);
			
			exporter.exportReport();
			
			//solicitud.setDocumentoComprobante(byteArrayOutputStream.toByteArray());
			tramiteServiceEntityLocal.actualizarDocumentosTramite(solicitud.getTramites().get(0).getTramiteId(), DocumentoPorTipoEnum.AVISO_DE_MODIFICACION.getId(), byteArrayOutputStream.toByteArray());
			
			//solicitudService.actualizarDocumentosDeSolicitud(solicitud);
		
			return byteArrayOutputStream.toByteArray();

		} catch (Exception e) {// Agregar las excepciones personalizadas
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public byte[] ejecutaAvisoDeModificacionMovPat(Map<String, Object> parametros, List<SujetoObligado> sujetos) {
		return ejecutaAvisoDeModificacionMovPat(parametros,sujetos,null);
	}

	@Override
	public byte[] ejecutaAvisoDeModificacionMovPat(Map<String, Object> parametros, List<SujetoObligado> sujetos, Solicitud solicitudRegis) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			
			log.debug(":::: Se va generar AMSRT");
			if(sujetos != null){
				log.debug("::::::::::::Total de registros patronales a sustituir: " + sujetos.size());
				for (Iterator<SujetoObligado> iterator = sujetos
						.iterator(); iterator.hasNext();) {
					SujetoObligado so = iterator.next();
					log.debug("::::: Registro a sustituir: " + so.getNumeroRegistroPatronal());
				}
			}else{
				log.debug(":::: La lista se patrones a sustituir viene NULl");
			}
			
			String folioSolicitud = (String)parametros.get("P_FOLIO_SOLCT");
			Solicitud solicitud = null;
			
			if(solicitudRegis != null && solicitudRegis.getTramites() !=null && !solicitudRegis.getTramites().isEmpty()) {
				solicitud = solicitudRegis;
			} else {
				solicitud = solicitudService.consultarSolicitudPorFolio(folioSolicitud);
			}
			
			log.debug("folio solicitud documento: "+folioSolicitud);
			parametros.put("IMAGENES_DIR", new ClassPathResource(pathIMG).getPath());
			parametros.put("SUBREPORT_DIR", new ClassPathResource(pathIMG).getPath());
			JasperReport report = JasperCompileManager.compileReport(new ClassPathResource("reportes/AvisoDeModificacion.jrxml").getInputStream());
			
			//Se compilan los subreportes
			JasperReport subReportDatosGeneral = JasperCompileManager.compileReport(new ClassPathResource("reportes/avisoDatosGenerales2.jrxml").getInputStream());
			JasperReport subCentroTRabajo = JasperCompileManager.compileReport(new ClassPathResource("reportes/avisoCentroTrabajo.jrxml").getInputStream());
			JasperReport subCentroTRabajoDifMun = JasperCompileManager.compileReport(new ClassPathResource("reportes/avisoCentroTrabajoDifMun.jrxml").getInputStream());
			JasperReport subClasificacion = JasperCompileManager.compileReport(new ClassPathResource("reportes/avisoClasificacionMP.jrxml").getInputStream());
			JasperReport subProductos = JasperCompileManager.compileReport(new ClassPathResource("reportes/avisoProductos.jrxml").getInputStream());
			JasperReport subMaterias = JasperCompileManager.compileReport(new ClassPathResource("reportes/SeccionVI-48.jrxml").getInputStream());
			JasperReport subMaquinaria = JasperCompileManager.compileReport(new ClassPathResource("reportes/SeccionVI-a.jrxml").getInputStream());
			JasperReport subTransporte = JasperCompileManager.compileReport(new ClassPathResource("reportes/SeccionVI-b.jrxml").getInputStream());
			JasperReport subProcesos = JasperCompileManager.compileReport(new ClassPathResource("reportes/avisoProcesos.jrxml").getInputStream());
			JasperReport subPersonal = JasperCompileManager.compileReport(new ClassPathResource("reportes/SeccionVI-d.jrxml").getInputStream());
			JasperReport subActividadesCom = JasperCompileManager.compileReport(new ClassPathResource("reportes/SeccionVI-e.jrxml").getInputStream());
			JasperReport subFusionSust = JasperCompileManager.compileReport(new ClassPathResource("reportes/SeccionSust.jrxml").getInputStream());
			JasperReport subBienes = JasperCompileManager.compileReport(new ClassPathResource("reportes/SeccionVIII.jrxml").getInputStream());
			JasperReport subFirmas = JasperCompileManager.compileReport(new ClassPathResource("reportes/SeccionIX.jrxml").getInputStream());
			
			
			log.debug("Voy a anadir los subreportes compilados");
			//Seccion para establecer los subreportes
			parametros.put("SUB_DATOS_GENERALES", subReportDatosGeneral);
			parametros.put("SUB_CENTRO", subCentroTRabajo);
			parametros.put("SUB_CENTRO_DOMANT", subCentroTRabajoDifMun);
			parametros.put("SUB_CLASIFICACION", subClasificacion);
			parametros.put("SUB_PRODUCTOS", subProductos);
			parametros.put("SUB_MATERIAS", subMaterias);
			parametros.put("SUB_MAQUINARIA", subMaquinaria);
			parametros.put("SUB_TRANSPORTE", subTransporte);
			parametros.put("SUB_PROCESOS", subProcesos);
			parametros.put("SUB_PERSONAL", subPersonal);
			parametros.put("SUB_ACTIVIDADES_COM", subActividadesCom);
			parametros.put("SUB_FUSION_SUS", subFusionSust);
			parametros.put("SUB_BIENES", subBienes);
			parametros.put("SUB_FIRMAS", subFirmas);
			
			JasperPrint print;
			
			if(parametros.get("P_CVE_TRAMITE").equals("ACD")) {
				sujetos.get(0).getSujetosObligados().clear(); 
				
			}
			
			JRBeanCollectionDataSource beanDS = new JRBeanCollectionDataSource(sujetos);
			
			
			print = JasperFillManager.fillReport(report, parametros, beanDS);
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,byteArrayOutputStream);
			
			exporter.exportReport();
			
			//solicitud.setDocumentoComprobante(byteArrayOutputStream.toByteArray());
			tramiteServiceEntityLocal.actualizarDocumentosTramite(solicitud.getTramites().get(0).getTramiteId(), DocumentoPorTipoEnum.AVISO_DE_MODIFICACION.getId(), byteArrayOutputStream.toByteArray());
			
			//solicitudService.actualizarDocumentosDeSolicitud(solicitud);
		
			return byteArrayOutputStream.toByteArray();

		} catch (Exception e) {// Agregar las excepciones personalizadas
			e.printStackTrace();
		}
		return null;
	}
	
	

	@Override
	public byte[] ejecutaCartaTerminosFielRepresentante(Solicitud solicitud) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		List<CartaTerminosDto> cartasTerminos = new ArrayList<CartaTerminosDto>();
		CartaTerminosDto carta = new CartaTerminosDto();
		
		carta.setCadenaOriginal(solicitud.getCadenaOriginal());
		carta.setSelloDigital(solicitud.getSelloDigital());
		carta.setFecha(solicitud.getFechaConclusion());
		carta.setSecuenciaNotaria(solicitud.getSecuenciaDeNotaria());
		carta.setNumeroSerie(solicitud.getNumeroSerieCertificado());
		
		
		if(solicitud.getCadenaOriginalRepresentado() != null) {
			carta.setSelloDigitalRepresentado(solicitud.getSelloDigitalRepresentado());
			carta.setCadenaOriginalRepresentado(solicitud.getCadenaOriginalRepresentado());
			carta.setSecuenciaNotariaRepresentado(solicitud.getSecuenciaDeNotariaRepresentado());
			carta.setNumeroSerieRepresentado(solicitud.getNumeroSerieCertificadoRepresentado());
		}
		
		cartasTerminos.add(carta);
		
		
		try {
			Map<String,Object> parametros = new HashMap<String, Object>();
			
			JasperReport report = JasperCompileManager
					.compileReport(new ClassPathResource("reportes/cartaTerminosRepresentante.jrxml")
							.getInputStream());
			JasperPrint print;
			JRBeanCollectionDataSource beanDS = new JRBeanCollectionDataSource(cartasTerminos);
			print = JasperFillManager.fillReport(report, parametros, beanDS);
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					byteArrayOutputStream);
			
			exporter.exportReport();
			
			
			tramiteServiceEntityLocal.actualizarDocumentosTramite(solicitud.getTramites().get(0).getTramiteId(),
					DocumentoPorTipoEnum.CARTA_TERMINOS_FIEL.getId(), byteArrayOutputStream.toByteArray());
		
			return byteArrayOutputStream.toByteArray();

		} catch (Exception e) {// Agregar las excepciones personalizadas
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public byte[] ejecutaCartaTerminosFiel(Solicitud solicitud) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		List<CartaTerminosDto> cartasTerminos = new ArrayList<CartaTerminosDto>();
		CartaTerminosDto carta = new CartaTerminosDto();
		
		carta.setCadenaOriginal(solicitud.getCadenaOriginal());
		carta.setSelloDigital(solicitud.getSelloDigital());
		carta.setSecuenciaNotaria(solicitud.getSecuenciaDeNotaria());
		carta.setNumeroSerie(solicitud.getNumeroSerieCertificado());
		carta.setFecha(solicitud.getFechaConclusion());
		
		cartasTerminos.add(carta);
		
		
		try {
			Map<String,Object> parametros = new HashMap<String, Object>();
			
			JasperReport report = JasperCompileManager
					.compileReport(new ClassPathResource("reportes/cartaTerminosCondicionesFiel.jrxml")
							.getInputStream());
			JasperPrint print;
			JRBeanCollectionDataSource beanDS = new JRBeanCollectionDataSource(cartasTerminos);
			print = JasperFillManager.fillReport(report, parametros, beanDS);
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					byteArrayOutputStream);
			
			exporter.exportReport();
			
			
			tramiteServiceEntityLocal.actualizarDocumentosTramite(solicitud.getTramites().get(0).getTramiteId(),
					DocumentoPorTipoEnum.CARTA_TERMINOS_FIEL.getId(), byteArrayOutputStream.toByteArray());
		
			return byteArrayOutputStream.toByteArray();

		} catch (Exception e) {// Agregar las excepciones personalizadas
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public byte[] ejecutaAcuse(Map<String, Object> parametros)
			throws GestionPatronalBusinessException {

		if (parametros != null) {
			
			String folioSolicitud = (String)parametros.get("P_FOLIO_SOLCT");
			Solicitud solicitud = solicitudService.consultarSolicitudPorFolio(folioSolicitud);
			parametros.put("IMAGENES_DIR",
					new ClassPathResource(pathIMG).getPath());
			ByteArrayOutputStream reporte = ejecutaReporte(parametros,
					"Acuse.jrxml", false);
			
			if (reporte != null) {
				solicitud.setDocumentoAcuse(reporte.toByteArray());
				solicitudService.actualizarDocumentosDeSolicitud(solicitud);
				this.log.debug(">>>>>*$$$$$ - ejecutaAcuse - Rreporte size " + reporte.size());
			} else {
				this.log.debug(">>>>>*$$$$$ - ejecutaAcuse - Reporte size no tiene ");
			}
			return reporte.toByteArray();
		} else {
			throw new GestionPatronalBusinessException(
					"Parametros Insuficientes");
		}
	}
	
	
	@Override
	public byte[] ejecutaAcuseVentanilla(Map<String, Object> parametros)
			throws GestionPatronalBusinessException {

		if (parametros != null) {
			
			String folioSolicitud = (String)parametros.get("P_FOLIO_SOLCT");
			Solicitud solicitud = solicitudService.consultarSolicitudPorFolio(folioSolicitud);
			parametros.put("IMAGENES_DIR", new ClassPathResource(pathIMG).getPath());
			parametros.put("SUBREPORT_DIR", new ClassPathResource(pathIMG).getPath());
			ByteArrayOutputStream reporte = ejecutaReporteVentanilla(parametros,
					"AcuseVentanilla2.jrxml", false);
			
			if (reporte != null) {
				solicitud.setDocumentoAcuse(reporte.toByteArray());
				solicitudService.actualizarDocumentosDeSolicitud(solicitud);
				this.log.debug(">>>>>*$$$$$ - ejecutaAcuse - Rreporte size " + reporte.size());
			} else {
				this.log.debug(">>>>>*$$$$$ - ejecutaAcuse - Reporte size no tiene ");
			}
			return reporte.toByteArray();
		} else {
			throw new GestionPatronalBusinessException(
					"Parametros Insuficientes");
		}
	}
	
	
	@Override
	public byte[] ejecutaAcuseVentanillaSustFusion(Map<String, Object> parametros)
			throws GestionPatronalBusinessException {

		if (parametros != null) {
			
			String folioSolicitud = (String)parametros.get("P_FOLIO_SOLCT");
			Solicitud solicitud = solicitudService.consultarSolicitudPorFolio(folioSolicitud);
			parametros.put("IMAGENES_DIR", new ClassPathResource(pathIMG).getPath());
			parametros.put("SUBREPORT_DIR", new ClassPathResource(pathIMG).getPath());
			ByteArrayOutputStream reporte = ejecutaReporteVentanilla(parametros,
					"AcuseVentanillaSF.jrxml", false);
			
			if (reporte != null) {
				solicitud.setDocumentoAcuse(reporte.toByteArray());
				solicitudService.actualizarDocumentosDeSolicitud(solicitud);
				this.log.debug(">>>>>*$$$$$ - ejecutaAcuse - Rreporte size " + reporte.size());
			} else {
				this.log.debug(">>>>>*$$$$$ - ejecutaAcuse - Reporte size no tiene ");
			}
			return reporte.toByteArray();
		} else {
			throw new GestionPatronalBusinessException(
					"Parametros Insuficientes");
		}
	}
	
	@Override
	public byte[] ejecutaAcuseCancelacion(Map<String, Object> parametros)
			throws GestionPatronalBusinessException {

		if (parametros != null) {
			
			String folioSolicitud = (String)parametros.get("P_FOLIO_SOLCT");
			Solicitud solicitud = solicitudService.consultarSolicitudPorFolio(folioSolicitud);
			parametros.put("IMAGENES_DIR",
					new ClassPathResource(pathIMG).getPath());
			ByteArrayOutputStream reporte = ejecutaReporte(parametros,
					"AcuseCancelacion.jrxml", false);
			
			if (reporte != null) {
				solicitud.setDocumentoAcuse(reporte.toByteArray());
				solicitudService.actualizarDocumentosDeSolicitud(solicitud);
				this.log.debug(">>>>>*$$$$$ - ejecutaAcuse - Rreporte size " + reporte.size());
			} else {
				this.log.debug(">>>>>*$$$$$ - ejecutaAcuse - Reporte size no tiene ");
			}
			return reporte.toByteArray();
		} else {
			throw new GestionPatronalBusinessException(
					"Parametros Insuficientes");
		}
	}
	
	// Ejecuta el reporte Jasper y regresa un ByteArrayOutputStream
	private ByteArrayOutputStream ejecutaReporteVentanilla(
			Map<String, Object> parametros, String reporte,
			boolean pasarConexion) {
		
		this.log.debug(">>>>>*$$$$$ - ejecutaReporteVentanilla " + reporte);

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			
			JasperReport report = JasperCompileManager
					.compileReport(new ClassPathResource("reportes/" + reporte).getInputStream());
			JasperPrint print;
			if (pasarConexion) {
				print = JasperFillManager.fillReport(report, parametros,conexion);
			} else {
				List<Documento> documentosReq = (List<Documento>) parametros.get("P_DOC_REQUERIDOS");
				JRBeanCollectionDataSource beanDS = null;
				if(documentosReq.isEmpty()) {
					JREmptyDataSource emptyDS = new JREmptyDataSource();
					parametros.put("beanDS", emptyDS);
				} else {
					beanDS = new JRBeanCollectionDataSource(documentosReq);
					parametros.put("beanDS", beanDS);
					
				}
				print = JasperFillManager.fillReport(report, parametros, beanDS);
			}

			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					byteArrayOutputStream);

			exporter.exportReport();

			return byteArrayOutputStream;

		} catch (Exception e) {// Agregar las excepciones personalizadas
			e.printStackTrace();
		}

		return null;
	}
	
	
	// Ejecuta el reporte Jasper y regresa un byte[]
	public byte[] ejecutaComprobanteCita(
				Map<String, Object> parametros, String reporte,
				boolean pasarConexion) throws GestionPatronalBusinessException {
			
			this.log.debug(">>>>>*$$$$$ - ejecutaComprobanteCita " + reporte);
			
			parametros.put("IMAGENES_DIR", new ClassPathResource(pathIMG).getPath());
			parametros.put("SUBREPORT_DIR", new ClassPathResource(pathIMG).getPath());

			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			try {
				
				JasperReport report = JasperCompileManager
						.compileReport(new ClassPathResource("reportes/" + reporte).getInputStream());
				JasperPrint print;
				if (pasarConexion) {
					print = JasperFillManager.fillReport(report, parametros,conexion);
				} else {
					List<Documento> documentosReq = (List<Documento>) parametros.get("P_DOC_REQUERIDOS");
					JRBeanCollectionDataSource beanDS = null;
					if(documentosReq.isEmpty()) {
						JREmptyDataSource emptyDS = new JREmptyDataSource();
						parametros.put("beanDS", emptyDS);
					} else {
						beanDS = new JRBeanCollectionDataSource(documentosReq);
						parametros.put("beanDS", beanDS);
						
					}
					print = JasperFillManager.fillReport(report, parametros, beanDS);
				}

				JRPdfExporter exporter = new JRPdfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
						byteArrayOutputStream);

				exporter.exportReport();

				return byteArrayOutputStream.toByteArray();

			} catch (Exception e) {// Agregar las excepciones personalizadas
				e.printStackTrace();
			}

			return null;
		}
			
	
	@Override
	public byte[] ejecutaAcuseModificacionDatosPatronales(Map<String, Object> mapData) {
		
		log.info("/**** SERVICIO PARA CONSTRUIR REPORTE DE ACUSE DE MODIFICACION PATRONAL ****/");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy  HH:mm");

		String flagFirmaDigital;
		String flagTipoSO;
		String flagDG = "0";
		String flagDC = "0";
		String flagSO = "0";
		String flagRL = "0";
		String flagCT = "0";
		String flagSOFi = "0";
		String flagSOMo = "0";
		String flagAC = "0";
		String flagRS = "0";
		
		String detFechaSolicitud = "";
		String detNoSolicitud = "";
		String detTituloPresenta = "";
		String detRFC = "";
		String detCadenaOriginal = "";
		String detCharAutenticidad = "";
		String detSerialCertificado = "";
		String detFinParrafo = "";
		
		StringBuffer detRazonSocial ;
		StringBuffer detNombrePresenta;
		
		String dgRFC = "";
		String dgCURP = "";
		String dgNombre = "";
		String dgPrimerApe = "";
		String dgSegundoApe = "";
		
//		StringBuffer dcDomicilioFiscal = new StringBuffer();
		StringBuffer dcMediosContacto = new StringBuffer();
		
		List<Socio> listSociosFisicos = new ArrayList<Socio>();
		List<Socio> listSociosMorales = new ArrayList<Socio>();
		
		List<RepresentanteLegal> listRepresentantes = new ArrayList<RepresentanteLegal>();
		
		String ctRegPatronal="";
		String ctDelControl="";
		String ctSubDelControl="";
		StringBuffer ctDatosContacto=new StringBuffer();
		
		String acNoEscritura ="";
		String acNoNotaria ="";
		String acEntidadFed ="";
		String acMunicipio ="";
		String acFechaExp ="";
		String acFolioMercantil ="";
		String acSeccion ="";
		String acPartida ="";
		String acVolumen ="";
		String acFoja ="";
		
		String rsNoReferencia ="";
		String rsAutoridad ="";
		String rsFechaDoc ="";
		
		String domCentrotrabajo="";
		
		Map<String, Object> parametros = new HashMap<String, Object>();		
		
		String titulo = (String) mapData.get("varTitulo"); 	
		Solicitud solicitud = (Solicitud)mapData.get("solicitudData");
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
		String fechaPresenta = sdf.format(Calendar.getInstance().getTime());
		
		detFechaSolicitud = fechaPresenta;//solicitud.getFechaSolicitudParse() != null ? solicitud.getFechaSolicitudParse() :"";
		detNoSolicitud = solicitud.getNoFolioSolicitud();
		
		if(solicitud.getSelloDigital()!= null && !solicitud.getSelloDigital().isEmpty()){
			detCadenaOriginal = solicitud.getCadenaOriginal();
			detCharAutenticidad = solicitud.getSecuenciaDeNotaria();
			detSerialCertificado = solicitud.getSelloDigital();
			detFinParrafo = sParrafoFinal1;
			flagFirmaDigital = "1";
		}else{
			detFinParrafo = sParrafoFinal2;
			flagFirmaDigital = "0";
		}
		
		
		if (solicitud.getSolicitante() != null){
			Usuario userSolicitante = solicitud.getSolicitante();
			if (userSolicitante.getPerfilUsuario() != null){
				detTituloPresenta = userSolicitante.getPerfilUsuario().getDescripcion() != null ? userSolicitante.getPerfilUsuario().getDescripcion() :"" ;
			} else {
				detTituloPresenta = " ";
			}
			detNombrePresenta = new StringBuffer("C.");
			detNombrePresenta.append(userSolicitante.getNomNombre() != null ? userSolicitante.getNomNombre() : "").append(" ");
			detNombrePresenta.append(userSolicitante.getNomPaterno() != null ? userSolicitante.getNomPaterno() : "").append(" ");
			detNombrePresenta.append(userSolicitante.getNomMaterno() != null ? userSolicitante.getNomMaterno() : "");
		} else {
			detTituloPresenta = " ";
			detNombrePresenta = new StringBuffer(" ");
			detNombrePresenta.append(" ");
			detNombrePresenta.append(" ");
			detNombrePresenta.append(" ");
		}
		
		
		
		SujetoObligado sujetoObligado = solicitud.getSujetoObligado();
		detRazonSocial = new StringBuffer();
		
		log.info("######$$$$$$>>>>>>>   Enum TIPO-Perona fISICA :"+TipoPersonaFiscal.FISICA);
		log.info("######$$$$$$>>>>>>>   SUJETO OBLIGADO :"+sujetoObligado);
		if(sujetoObligado != null)
			log.info("######$$$$$$>>>>>>>   SUJETO OBLIGADO - tipoPersonaFiscal:"+sujetoObligado.getTipoPersonaFiscal());
		
		
		if(solicitud.getTipoSolicitud().getIdTipoSolicitud().equals(TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO.getValor().longValue())){
			flagCT = "1";
			if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
				Fisica personaFisica = sujetoObligado.getFisica();
				detRazonSocial.append(personaFisica.getNombre().toUpperCase()).append(" ");
				detRazonSocial.append(personaFisica.getPrimerApellido().toUpperCase()).append(" ");
				detRazonSocial.append(personaFisica.getSegundoApellido().toUpperCase());
				
				detRFC = personaFisica.getRfc().toUpperCase();
				flagTipoSO = "f";
				
			}else{
				Moral personaMoral = sujetoObligado.getMoral();
				detRazonSocial.append(personaMoral.getRazonSocial().toUpperCase());
				
				detRFC = personaMoral.getRfc();
				flagTipoSO = "m";
			}
			
			CentroTrabajo domicilio = sujetoObligado.getCntroTrabajo();
			
			StringBuffer domicilioCompleto = new StringBuffer();
			domicilioCompleto.append(domicilio.getVialidadPrimaria().getNombre());
			domicilioCompleto.append(" #"+domicilio.getNumExterior1()+ (domicilio.getNumExteriorAlf()!=null ? " " + domicilio.getNumExteriorAlf() : "") );
			if(domicilio.getNumInterior()!=null)
				domicilioCompleto.append(", interior "+domicilio.getNumInterior()+(domicilio.getNumInteriorAlf()!=null ? " "+domicilio.getNumInteriorAlf() : "" ) );
			domicilioCompleto.append(", COLONIA "+domicilio.getAsentamiento().getNombre());
			domicilioCompleto.append(", CP "+domicilio.getCodigoPostal().getCodigoPostal());
			domicilio.setDescripcion(domicilioCompleto.toString());
			sujetoObligado.getCntroTrabajo().setDescripcion(domicilioCompleto.toString());
			
			ctRegPatronal=sujetoObligado.getNumeroRegistroPatronal()+sujetoObligado.getModalidad().getNumModalidad()+sujetoObligado.getDigVerificador();
			ctDelControl=solicitud.getSubdelegacion().getDelegacion().getDescripcion();
			ctSubDelControl=solicitud.getSubdelegacion().getDescripcion();
			domCentrotrabajo = domicilioCompleto.toString();
			
			for(MedioContacto medioContacto : sujetoObligado.getCntroTrabajo().getMediosContacto()){
				ctDatosContacto.append(stringMedioContacto(medioContacto));
			}
			
			
			
		}else if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
			log.info("######$$$$$$>>>>>>>   PATRON PERSONA FISICA   <<<<<<<<$$$$$$###### ");
			Fisica personaFisica = sujetoObligado.getFisica();
			detRazonSocial.append(personaFisica.getNombre().toUpperCase()).append(" ");
			detRazonSocial.append(personaFisica.getPrimerApellido().toUpperCase()).append(" ");
			detRazonSocial.append(personaFisica.getSegundoApellido().toUpperCase());
			
			detRFC = personaFisica.getRfc().toUpperCase();
			flagTipoSO = "f";
			
			
			List<Tramite> listTramites = solicitud.getTramites();
			log.info("######$$$$$$>>>>>>>   Num de TRAMITES listados :"+listTramites.size());
			for(int x=0; x<listTramites.size();x++){
				
				if(!(listTramites.get(x) instanceof TramiteFisica))
					continue;
				
				TramiteFisica tramiteFI=(TramiteFisica)listTramites.get(x);
				
				log.info("######$$$$$$>>>>>>>   Tramite Fisica   <<<<<<<<$$$$$$####### ");
				log.info("######$$$$$$>>>>>>>   Tipo Tramite :: "+ tramiteFI.getTipoTramite().getIdTipoTramite() );
			
				if(tramiteFI.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL.getCodigo())
						|| tramiteFI.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES.getCodigo())){
					
					log.info("######$$$$$$>>>>>>>   Tramite Fisica- DEN SOCIAL ");
					Fisica personaFisicaTramite = tramiteFI.getFisica();
					dgRFC = personaFisicaTramite.getRfc() != null ? personaFisicaTramite.getRfc() : "";
					dgCURP = personaFisicaTramite.getCurp() != null ? personaFisicaTramite.getCurp() : "";
					dgNombre = personaFisicaTramite.getNombre() != null ? personaFisicaTramite.getNombre() : "";
					dgPrimerApe = personaFisicaTramite.getPrimerApellido() != null ? personaFisicaTramite.getPrimerApellido() : "";
					dgSegundoApe = personaFisicaTramite.getSegundoApellido() != null ? personaFisicaTramite.getSegundoApellido() : "";
					
					flagDG = "1";
				} else if(tramiteFI.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO.getCodigo())){
					log.info("######$$$$$$>>>>>>>   Tramite Fisica - DAT CONTACTO ");
					Fisica personaFisicaTramite = tramiteFI.getFisica();
					List<MedioContacto> listMediosContactos = personaFisicaTramite.getMediosContacto();
					
					dcMediosContacto = new StringBuffer();
					for(MedioContacto medioContacto : listMediosContactos){
						dcMediosContacto.append(stringMedioContacto(medioContacto));
					}
					
					flagDC = "1";
					
				}  else if(tramiteFI.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_SOCIO.getCodigo())){
					log.info("######$$$$$$>>>>>>>   Tramite Fisica - ACTUALIZA SOCIOS ");
					Fisica personaFisicaTramite = tramiteFI.getFisica();
					List<Socio> listSocios = personaFisicaTramite.getSocios();
					log.info("######$$$$$$>>>>>>>   Lista Socios Tramite : "+listSocios.size());
					
					listSociosFisicos = new ArrayList<Socio>();
					listSociosMorales = new ArrayList<Socio>();
					
					for(Socio socioNoDef :listSocios){
						
						if(socioNoDef.getEsPersonaFisica()){
							listSociosFisicos.add(socioNoDef);
							flagSOFi = "1";
							log.info("######$$$$$$>>>>>>>   Agrega Socios Fisica  ");
						} else {
							listSociosMorales.add(socioNoDef);
							flagSOMo = "1";
							log.info("######$$$$$$>>>>>>>   Agrega Socios moral  ");
						}
					}
					
					flagSO = "1";
					
				} else if(tramiteFI.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo())){
					log.info("######$$$$$$>>>>>>>   Tramite Fisica - REP LEGAL ");
					Fisica personaFisicaTramite = tramiteFI.getFisica();
					listRepresentantes = personaFisicaTramite.getRepresentantesLegales();
					
					flagRL = "1";
					log.info("######$$$$$$>>>>>>>   Lista Socios Rep Legal : "+listRepresentantes.size());
				
				} 	
				
				
			}
			
			
			
		}else{
			log.info("######$$$$$$>>>>>>>   PATRON PERSONA MORAL   <<<<<<<<$$$$$$###### ");
			Moral personaMoral = sujetoObligado.getMoral();
			detRazonSocial.append(personaMoral.getRazonSocial().toUpperCase());
			
			detRFC = personaMoral.getRfc();
			flagTipoSO = "m";
			
			
			List<Tramite> listTramites = solicitud.getTramites();
			log.info("######$$$$$$>>>>>>>   Num de TRAMITES listados :"+listTramites.size());
			for(int x=0; x<listTramites.size();x++){
				
				if(listTramites.get(x) instanceof TramiteFisica)
					continue;
				
				TramiteMoral tramiteMO=null;
				if((listTramites.get(x) instanceof TramiteMoral)){
					tramiteMO=(TramiteMoral)listTramites.get(x);
					if (tramiteMO.getTipoTramite().getIdTipoTramite().equals(24))
							continue;
				}
				
//				TramiteMoral tramiteMO=(TramiteMoral)listTramites.get(x);
				
				log.info("######$$$$$$>>>>>>>   Tramite Moral   <<<<<<<<$$$$$$####### ");
				log.info("######$$$$$$>>>>>>>   Tipo Tramite :: "+ tramiteMO.getTipoTramite().getIdTipoTramite() );
				
				if(tramiteMO.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL.getCodigo())){
					log.info("######$$$$$$>>>>>>>   Tramite Moral- DENOMINACINO SOCIAL ");
					Moral personaMoralTramite = tramiteMO.getMoral();
					dgRFC = personaMoralTramite.getRfc();
					dgNombre = personaMoralTramite.getRazonSocial();
					
					flagDG = "1";
					
				} else if(tramiteMO.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO.getCodigo())){
					log.info("######$$$$$$>>>>>>>   Tramite Moral - DATOS CONTACTO ");
					Moral personaMoralTramite = tramiteMO.getMoral();
					List<MedioContacto> listMediosContactos = personaMoralTramite.getMediosContacto();
					
					dcMediosContacto = new StringBuffer();
					for(MedioContacto medioContacto : listMediosContactos){
						dcMediosContacto.append("-").append(stringMedioContacto(medioContacto)).append("\\n");
					}
					
					flagDC = "1";
					
				}  else if(tramiteMO.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_SOCIO.getCodigo())){
					log.info("######$$$$$$>>>>>>>   Tramite Moral - ACTUALIZA SOCIOS ");
					Moral personaMoralTramite = tramiteMO.getMoral();
					List<Socio> listSocios = personaMoralTramite.getSocios();
					log.info("######$$$$$$>>>>>>>   Lista Socios Tramite : "+listSocios.size());
					
					listSociosFisicos = new ArrayList<Socio>();
					listSociosMorales = new ArrayList<Socio>();
					
					for(Socio socioNoDef :listSocios){
						
						if(socioNoDef.getEsPersonaFisica()){
							listSociosFisicos.add(socioNoDef);
							flagSOFi = "1";
							log.info("######$$$$$$>>>>>>>   Agrega Socios Fisica  ");
						} else {
							listSociosMorales.add(socioNoDef);
							flagSOMo = "1";
							log.info("######$$$$$$>>>>>>>   Agraga Socios Moral  ");
						}
					}
					
					flagSO = "1";
					
				} else if(tramiteMO.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo())){
					log.info("######$$$$$$>>>>>>>   Tramite Moral - REP LEGAL ");
					Moral personaMoralTramite = tramiteMO.getMoral();
					listRepresentantes = personaMoralTramite.getRepresentantesLegales();
					
					flagRL = "1";
					log.info("######$$$$$$>>>>>>>   Lista Socios Rep Legal : "+listRepresentantes.size());
				
				} else if(tramiteMO.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA.getCodigo())){
					log.info("######$$$$$$>>>>>>>   Tramite Moral - ACTA CONSTITUTIVA ");
					Moral personaMoralTramite = tramiteMO.getMoral();
					EscrituraConstitutiva actaConst = personaMoralTramite.getEscrituraConstitutiva();
					Municipio lugarExp = actaConst.getLugarExpedicion();
					EntidadFederativa entidadFed = lugarExp.getEntidadFederativa();
							
					acNoEscritura = actaConst.getNumEscritura()!= null? actaConst.getNumEscritura().toString():"sin informaci�n";
					acNoNotaria = actaConst.getNumNotaria() != null ? actaConst.getNumNotaria() : "sin informaci�n";
					acEntidadFed = entidadFed.getNombre() != null ? entidadFed.getNombre() : "sin informacion";
					acMunicipio = lugarExp.getNombre() != null ? lugarExp.getNombre() : "sin informacion";
					acFechaExp = dateFormat.format(actaConst.getFechaExpedicion());
					
					
					if(actaConst.getFolioMercantil() != null && !actaConst.getFolioMercantil().trim().isEmpty())
						acFolioMercantil = actaConst.getFolioMercantil();
					else
						acFolioMercantil = "sin informaci�n" ;
					
					if(actaConst.getSeccion() != null && !actaConst.getSeccion().trim().isEmpty())
						acSeccion = actaConst.getSeccion();
					else
						acSeccion = "sin informaci�n" ;
					
					if(actaConst.getPartida() != null && !actaConst.getPartida().trim().isEmpty())
						acPartida = actaConst.getPartida();
					else
						acPartida = "sin informaci�n" ;
					
					if(actaConst.getVolumen() != null && !actaConst.getVolumen().trim().isEmpty())
						acVolumen = actaConst.getVolumen();
					else
						acVolumen = "sin informaci�n" ;
					
					if(actaConst.getFoja() != null && !actaConst.getFoja().trim().isEmpty())
						acFoja = actaConst.getFoja();
					else
						acFoja = "sin informaci�n" ;
					
					
					flagAC = "1";
				
				} 	else if(tramiteMO.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO.getCodigo())){
					log.info("######$$$$$$>>>>>>>   Tramite Moral - REGISTRO SINDICAL ");
					Moral personaMoralTramite = tramiteMO.getMoral();
					RegistroSindicato regSindicato = personaMoralTramite.getRegistroSindicato();
				
					rsNoReferencia = regSindicato.getNumReferenciadocRegistro().toString();
					rsAutoridad = regSindicato.getAutoridadLaboral();
					rsFechaDoc = dateFormat.format(regSindicato.getFechaRegistro());
					
					flagRS = "1";
				
				} 	  	
				
				
			}
		}
		
		parametros.put("p_det_titulo", titulo);
		
		parametros.put("p_flagDatosGen", flagDG);
		parametros.put("p_flagDatosContacto", flagDC);
		parametros.put("p_flagSocios", flagSO);
		parametros.put("p_flagRepLegal", flagRL);
		parametros.put("p_flagCentroTrabajo", flagCT);
		parametros.put("p_flagSociosFi", flagSOFi);
		parametros.put("p_flagSociosMo", flagSOMo);
		parametros.put("p_flagFirmaDig", flagFirmaDigital);
		parametros.put("p_flagTipoPersona", flagTipoSO);
		parametros.put("p_flagActaCons", flagAC);
		parametros.put("p_flagRegSindicato",flagRS);
		
		parametros.put("p_det_fechaHora", dateFormat.format(new Date()));
		parametros.put("p_det_noSolicitud", detNoSolicitud);
		parametros.put("p_det_presenta", detNombrePresenta.toString());
		parametros.put("p_det_tituloPresenta", detTituloPresenta);
		parametros.put("p_det_razonSocial", detRazonSocial.toString());
		parametros.put("p_det_finParrafo", detFinParrafo);
		parametros.put("p_det_rfc", detRFC);
		parametros.put("p_det_fechaPresenta", detFechaSolicitud);
		parametros.put("p_det_cadenaOriginal", detCadenaOriginal);
		parametros.put("p_det_charAutenticidad", detCharAutenticidad);
		parametros.put("p_det_serialCertificado", detSerialCertificado);
		
		parametros.put("p_dg_rfc", dgRFC);
		parametros.put("p_dg_curp", dgCURP);
		parametros.put("p_dg_nombre", dgNombre);
		parametros.put("p_dg_primerApe", dgPrimerApe);
		parametros.put("p_dg_segundoApe", dgSegundoApe);
		
		parametros.put("p_dc_datosContacto", dcMediosContacto.toString());
		
		parametros.put("p_listSociosFi", listSociosFisicos);
		parametros.put("p_listSociosMo", listSociosMorales);
		parametros.put("p_listRepLegal", listRepresentantes);
		
		parametros.put("p_ac_NoEscritura", acNoEscritura);
		parametros.put("p_ac_NoNotaria", acNoNotaria);
		parametros.put("p_ac_entidadFed", acEntidadFed);
		parametros.put("p_ac_municipio", acMunicipio );
		parametros.put("p_ac_fechaExp", acFechaExp);
		parametros.put("p_ac_folioMercantil", acFolioMercantil);
		parametros.put("p_ac_seccion", acSeccion);
		parametros.put("p_ac_partida", acPartida);
		parametros.put("p_ac_volumen", acVolumen);
		parametros.put("p_ac_foja", acFoja);
		
		parametros.put("p_rs_NoReferencia", rsNoReferencia);
		parametros.put("p_rs_autoridad", rsAutoridad);
		parametros.put("p_rs_fechaDoc", rsFechaDoc);
		
		parametros.put("p_ct_regPatronal",ctRegPatronal);
		parametros.put("p_ct_delControl",ctDelControl);
		parametros.put("p_ct_subDelControl",ctSubDelControl);
		parametros.put("p_centro_trabajo_descripcion",domCentrotrabajo);
		parametros.put("p_ct_datosContacto",ctDatosContacto.toString());
		
		parametros.put("IMAGENES_DIR", new ClassPathResource(pathIMG).getPath());
		parametros.put("SUBREPORT_DIR", new ClassPathResource(pathIMG).getPath());
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			JasperReport report = JasperCompileManager
					.compileReport(new ClassPathResource("reportes/AcuseModificacionDatosPatronales.jrxml").getInputStream());
			JasperPrint print;
			
//			JRBeanCollectionDataSource beanDS = new JRBeanCollectionDataSource(sujetoObligadoLista);
			JREmptyDataSource emptyDS = new JREmptyDataSource();
			print = JasperFillManager.fillReport(report, parametros, emptyDS);
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
			exporter.exportReport();
			
			if (byteArrayOutputStream!=null) {				
				if (titulo!=null && titulo.equals("ACUSE")) {
					solicitud.setDocumentoAcuse(byteArrayOutputStream.toByteArray());
				} else if (titulo!=null && titulo.equals("COMPROBANTE")) {
					solicitud.setDocumentoComprobante(byteArrayOutputStream.toByteArray());
				}							
				solicitudService.actualizarDocumentosDeSolicitud(solicitud);
			}
						
		} catch (JRException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return byteArrayOutputStream.toByteArray();
	}
	
	
	
	private String stringMedioContacto(MedioContacto medioContacto) {
		StringBuffer medioContactoRetorno = new StringBuffer();
		switch (medioContacto.getTipoMedioContacto().getIdTipoMedioContacto().intValue()) {
		case 1:
			medioContactoRetorno.append("CORREO ELECTR�NICO PERSONAL: ")
							.append(medioContacto.getDesFormaContacto())
							.append("; ");
			break;
		case 2:
			medioContactoRetorno.append("TEL�FONO FIJO CON LADA (10 D�GITOS): ")
							.append(medioContacto.getDesFormaContacto())
							.append("; ");
			break;
		case 3:
			medioContactoRetorno.append("TEL�FONO M�VIL: ")
							.append(medioContacto.getDesFormaContacto())
							.append("; ");
			break;
		case 4:
			medioContactoRetorno.append("FACEBOOK: ")
							.append(medioContacto.getDesFormaContacto())
							.append("; ");
			break;
		case 5:
			medioContactoRetorno.append("TWITTER: ")
							.append(medioContacto.getDesFormaContacto())
							.append("; ");
			break;
		default:
			break;
		}
		return medioContactoRetorno.toString();
	}
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public byte[] ejecutaAcuseModificacionDatosPatronalesPortal(Map<String, Object> mapData) {
		
		log.info("/**** SERVICIO PARA CONSTRUIR REPORTE DE ACUSE DE MODIFICACION PATRONAL ****/");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy  HH:mm");

		String flagFirmaDigital;
		String flagTipoSO="f";
		String flagDG = "0";
		String flagDC = "0";
		String flagSO = "0";
		String flagRL = "0";
		String flagCT = "0";
		String flagSOFi = "0";
		String flagSOMo = "0";
		String flagAC = "0";
		String flagRS = "0";
		
		String detFechaSolicitud = "";
		String detNoSolicitud = "";
		String detTituloPresenta = "";
		String detRFC = "";
		String detCadenaOriginal = "";
		String detCharAutenticidad = "";
		String detSerialCertificado = "";
		String detFinParrafo = "";
		
		StringBuffer detRazonSocial ;
		StringBuffer detNombrePresenta;
		
		String dgRFC = "";
		String dgCURP = "";
		String dgNombre = "";
		String dgPrimerApe = "";
		String dgSegundoApe = "";
		
//		StringBuffer dcDomicilioFiscal = new StringBuffer();
		StringBuffer dcMediosContacto = new StringBuffer();
		
		List<Socio> listSociosFisicos = new ArrayList<Socio>();
		List<Socio> listSociosMorales = new ArrayList<Socio>();
		
		List<RepresentanteLegal> listRepresentantes = new ArrayList<RepresentanteLegal>();
		
		String ctRegPatronal="";
		String ctDelControl="";
		String ctSubDelControl="";
		StringBuffer ctDatosContacto=new StringBuffer();
		
		String acNoEscritura ="";
		String acNoNotaria ="";
		String acEntidadFed ="";
		String acMunicipio ="";
		String acFechaExp ="";
		String acFolioMercantil ="";
		String acSeccion ="";
		String acPartida ="";
		String acVolumen ="";
		String acFoja ="";
		
		String rsNoReferencia ="";
		String rsAutoridad ="";
		String rsFechaDoc ="";
		
		String domCentrotrabajo="";
		
		Map<String, Object> parametros = new HashMap<String, Object>();		
		
		String titulo = (String) mapData.get("varTitulo"); 	
		Solicitud solicitud = (Solicitud)mapData.get("solicitudData");
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
		String fechaPresenta = sdf.format(Calendar.getInstance().getTime());
		
		detFechaSolicitud = fechaPresenta;//solicitud.getFechaSolicitudParse() != null ? solicitud.getFechaSolicitudParse() :"";
		detNoSolicitud = solicitud.getNoFolioSolicitud();
		
		if(solicitud.getSelloDigital()!= null && !solicitud.getSelloDigital().isEmpty()){
			detCadenaOriginal = solicitud.getCadenaOriginal();
			detCharAutenticidad = solicitud.getSecuenciaDeNotaria();
			detSerialCertificado = solicitud.getSelloDigital();
			detFinParrafo = sParrafoFinal1;
			flagFirmaDigital = "1";
		}else{
			detFinParrafo = sParrafoFinal2;
			flagFirmaDigital = "0";
		}
		
		
		if (solicitud.getSolicitante() != null){
			Usuario userSolicitante = solicitud.getSolicitante();
			if (userSolicitante.getPerfilUsuario() != null){
				detTituloPresenta = userSolicitante.getPerfilUsuario().getDescripcion() != null ? userSolicitante.getPerfilUsuario().getDescripcion() :"" ;
			} else {
				detTituloPresenta = " ";
			}
			detNombrePresenta = new StringBuffer("C.");
			detNombrePresenta.append(userSolicitante.getNomNombre() != null ? userSolicitante.getNomNombre() : "").append(" ");
			detNombrePresenta.append(userSolicitante.getNomPaterno() != null ? userSolicitante.getNomPaterno() : "").append(" ");
			detNombrePresenta.append(userSolicitante.getNomMaterno() != null ? userSolicitante.getNomMaterno() : "");
		} else {
			detTituloPresenta = " ";
			detNombrePresenta = new StringBuffer(" ");
			detNombrePresenta.append(" ");
			detNombrePresenta.append(" ");
			detNombrePresenta.append(" ");
		}
		
		
		
		SujetoObligado sujetoObligado = solicitud.getSujetoObligado();
		detRazonSocial = new StringBuffer();
		
		log.info("######$$$$$$>>>>>>>   Enum TIPO-Perona fISICA :"+TipoPersonaFiscal.FISICA);
		log.info("######$$$$$$>>>>>>>   SUJETO OBLIGADO :"+sujetoObligado);
		if(sujetoObligado != null)
			log.info("######$$$$$$>>>>>>>   SUJETO OBLIGADO - tipoPersonaFiscal:"+sujetoObligado.getTipoPersonaFiscal());
		
		
		if(solicitud.getTipoSolicitud().getIdTipoSolicitud().equals(TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO.getValor().longValue())){
			flagCT = "1";
			if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
				Fisica personaFisica = sujetoObligado.getFisica();
				detRazonSocial.append(personaFisica.getNombre().toUpperCase()).append(" ");
				detRazonSocial.append(personaFisica.getPrimerApellido().toUpperCase()).append(" ");
				detRazonSocial.append(personaFisica.getSegundoApellido().toUpperCase());
				
				detRFC = personaFisica.getRfc().toUpperCase();
				flagTipoSO = "f";
				
			}else{
				Moral personaMoral = sujetoObligado.getMoral();
				detRazonSocial.append(personaMoral.getRazonSocial().toUpperCase());
				
				detRFC = personaMoral.getRfc();
				flagTipoSO = "m";
			}
			
			CentroTrabajo domicilio = sujetoObligado.getCntroTrabajo();
			
			StringBuffer domicilioCompleto = new StringBuffer();
			domicilioCompleto.append(domicilio.getVialidadPrimaria().getNombre());
			domicilioCompleto.append(" #"+domicilio.getNumExterior1()+ (domicilio.getNumExteriorAlf()!=null ? " " + domicilio.getNumExteriorAlf() : "") );
			if(domicilio.getNumInterior()!=null)
				domicilioCompleto.append(", interior "+domicilio.getNumInterior()+(domicilio.getNumInteriorAlf()!=null ? " "+domicilio.getNumInteriorAlf() : "" ) );
			domicilioCompleto.append(", COLONIA "+domicilio.getAsentamiento().getNombre());
			domicilioCompleto.append(", CP "+domicilio.getCodigoPostal().getCodigoPostal());
			domicilio.setDescripcion(domicilioCompleto.toString());
			sujetoObligado.getCntroTrabajo().setDescripcion(domicilioCompleto.toString());
			
			ctRegPatronal=sujetoObligado.getNumeroRegistroPatronal()+sujetoObligado.getModalidad().getNumModalidad()+sujetoObligado.getDigVerificador();
			ctDelControl=solicitud.getSubdelegacion().getDelegacion().getDescripcion();
			ctSubDelControl=solicitud.getSubdelegacion().getDescripcion();
			domCentrotrabajo = domicilioCompleto.toString();
			
			for(MedioContacto medioContacto : sujetoObligado.getCntroTrabajo().getMediosContacto()){
				ctDatosContacto.append(stringMedioContacto(medioContacto));
			}
			
			
			
		}else if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
			log.info("Agregando datos fisica:::::");
			Map<String, Object> parametrosAgregados = agregarParametrosPersonaFisica(solicitud);
			
			flagDG = (String)parametrosAgregados.get("p_flagDatosGen");
			log.info("Flag Datos contacto particulares: "+(String)parametrosAgregados.get("p_flagDatosContacto"));
			flagDC = (String)parametrosAgregados.get("p_flagDatosContacto");
			flagSO = (String)parametrosAgregados.get("p_flagSocios");
			flagRL = (String)parametrosAgregados.get("p_flagRepLegal");
			flagTipoSO = (String)parametrosAgregados.get("p_flagTipoPersona");
			
			detRazonSocial.append((String)parametrosAgregados.get("p_det_razonSocial"));
			detRFC = (String)parametrosAgregados.get("p_det_rfc");
			
			dgRFC = (String)parametrosAgregados.get("p_dg_rfc");
			dgCURP = (String)parametrosAgregados.get("p_dg_curp");
			dgNombre = (String)parametrosAgregados.get("p_dg_nombre");
			dgPrimerApe = (String)parametrosAgregados.get("p_dg_primerApe");
			dgSegundoApe = (String)parametrosAgregados.get("p_dg_segundoApe");
			
			log.info("Medios contacto particulares: "+(String)parametrosAgregados.get("p_dc_datosContacto"));
			dcMediosContacto.append((String)parametrosAgregados.get("p_dc_datosContacto"));
			
		}else{
			
			Map<String, Object> parametrosAgregados = agregarParametrosPersonaMoral(solicitud);
			
			flagDG = (String)parametrosAgregados.get("p_flagDatosGen");
			flagDC = (String)parametrosAgregados.get("p_flagDatosContacto");
			flagSO = (String)parametrosAgregados.get("p_flagSocios");
			flagRL = (String)parametrosAgregados.get("p_flagRepLegal");
			flagSOFi = (String)parametrosAgregados.get("p_flagSociosFi");
			flagSOMo = (String)parametrosAgregados.get("p_flagSociosMo");
			flagTipoSO = (String)parametrosAgregados.get("p_flagTipoPersona");
			flagAC = (String)parametrosAgregados.get("p_flagActaCons");
			flagRS = (String)parametrosAgregados.get("p_flagRegSindicato");
			
			detRazonSocial.append((String)parametrosAgregados.get("p_det_razonSocial"));
			detRFC = (String)parametrosAgregados.get("p_det_rfc");
			
			dgRFC = (String)parametrosAgregados.get("p_dg_rfc");
			dgNombre = (String)parametrosAgregados.get("p_dg_nombre");
			
			String datosParticulares = (String)parametrosAgregados.get("p_dc_datosContacto");
			dcMediosContacto = new StringBuffer();
			dcMediosContacto.append(datosParticulares);
			
			listSociosFisicos = (List<Socio>)parametrosAgregados.get("p_listSociosFi");
			listSociosMorales = (List<Socio>)parametrosAgregados.get("p_listSociosMo");
			listRepresentantes = (List<RepresentanteLegal>)parametrosAgregados.get("p_listRepLegal");
			
			acNoEscritura = (String)parametrosAgregados.get("p_ac_NoEscritura");
			acNoNotaria = (String)parametrosAgregados.get("p_ac_NoNotaria");
			acEntidadFed = (String)parametrosAgregados.get("p_ac_entidadFed");
			acMunicipio = (String)parametrosAgregados.get("p_ac_municipio" );
			acFechaExp = (String)parametrosAgregados.get("p_ac_fechaExp");
			acFolioMercantil = (String)parametrosAgregados.get("p_ac_folioMercantil");
			acSeccion = (String)parametrosAgregados.get("p_ac_seccion");
			acPartida = (String)parametrosAgregados.get("p_ac_partida");
			acVolumen = (String)parametrosAgregados.get("p_ac_volumen");
			acFoja = (String)parametrosAgregados.get("p_ac_foja");
			
			rsNoReferencia = (String)parametrosAgregados.get("p_rs_NoReferencia");
			rsAutoridad = (String)parametrosAgregados.get("p_rs_autoridad");
			rsFechaDoc = (String)parametrosAgregados.get("p_rs_fechaDoc" );
		
			
			
		}
		
		
		
		parametros.put("p_det_titulo", titulo);
		
		parametros.put("p_flagDatosGen", flagDG);
		parametros.put("p_flagDatosContacto", flagDC);
		parametros.put("p_flagSocios", flagSO);
		parametros.put("p_flagRepLegal", flagRL);
		parametros.put("p_flagCentroTrabajo", flagCT);
		parametros.put("p_flagSociosFi", flagSOFi);
		parametros.put("p_flagSociosMo", flagSOMo);
		parametros.put("p_flagFirmaDig", flagFirmaDigital);
		parametros.put("p_flagTipoPersona", flagTipoSO);
		parametros.put("p_flagActaCons", flagAC);
		parametros.put("p_flagRegSindicato",flagRS);
		
		parametros.put("p_det_fechaHora", dateFormat.format(new Date()));
		parametros.put("p_det_noSolicitud", detNoSolicitud);
		parametros.put("p_det_presenta", detNombrePresenta.toString());
		parametros.put("p_det_tituloPresenta", detTituloPresenta);
		parametros.put("p_det_razonSocial", detRazonSocial.toString());
		parametros.put("p_det_finParrafo", detFinParrafo);
		parametros.put("p_det_rfc", detRFC);
		parametros.put("p_det_fechaPresenta", detFechaSolicitud);
		parametros.put("p_det_cadenaOriginal", detCadenaOriginal);
		parametros.put("p_det_charAutenticidad", detCharAutenticidad);
		parametros.put("p_det_serialCertificado", detSerialCertificado);
		
		parametros.put("p_dg_rfc", dgRFC);
		parametros.put("p_dg_curp", dgCURP);
		parametros.put("p_dg_nombre", dgNombre);
		parametros.put("p_dg_primerApe", dgPrimerApe);
		parametros.put("p_dg_segundoApe", dgSegundoApe);
		
		parametros.put("p_dc_datosContacto", dcMediosContacto.toString());
		
		parametros.put("p_listSociosFi", listSociosFisicos);
		parametros.put("p_listSociosMo", listSociosMorales);
		parametros.put("p_listRepLegal", listRepresentantes);
		
		parametros.put("p_ac_NoEscritura", acNoEscritura);
		parametros.put("p_ac_NoNotaria", acNoNotaria);
		parametros.put("p_ac_entidadFed", acEntidadFed);
		parametros.put("p_ac_municipio", acMunicipio );
		parametros.put("p_ac_fechaExp", acFechaExp);
		parametros.put("p_ac_folioMercantil", acFolioMercantil);
		parametros.put("p_ac_seccion", acSeccion);
		parametros.put("p_ac_partida", acPartida);
		parametros.put("p_ac_volumen", acVolumen);
		parametros.put("p_ac_foja", acFoja);
		
		parametros.put("p_rs_NoReferencia", rsNoReferencia);
		parametros.put("p_rs_autoridad", rsAutoridad);
		parametros.put("p_rs_fechaDoc", rsFechaDoc);
		
		parametros.put("p_ct_regPatronal",ctRegPatronal);
		parametros.put("p_ct_delControl",ctDelControl);
		parametros.put("p_ct_subDelControl",ctSubDelControl);
		parametros.put("p_centro_trabajo_descripcion",domCentrotrabajo);
		parametros.put("p_ct_datosContacto",ctDatosContacto.toString());
		
		parametros.put("IMAGENES_DIR", new ClassPathResource(pathIMG).getPath());
		parametros.put("SUBREPORT_DIR", new ClassPathResource(pathIMG).getPath());
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			JasperReport report = JasperCompileManager
					.compileReport(new ClassPathResource("reportes/AcuseModificacionDatosPatronales.jrxml").getInputStream());
			JasperPrint print;
			
//			JRBeanCollectionDataSource beanDS = new JRBeanCollectionDataSource(sujetoObligadoLista);
			JREmptyDataSource emptyDS = new JREmptyDataSource();
			print = JasperFillManager.fillReport(report, parametros, emptyDS);
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
			exporter.exportReport();
			
			if (byteArrayOutputStream!=null) {				
				/*if (titulo!=null && titulo.equals("ACUSE")) {
					solicitud.setDocumentoAcuse(byteArrayOutputStream.toByteArray());
				} else if (titulo!=null && titulo.equals("COMPROBANTE")) {
					solicitud.setDocumentoComprobante(byteArrayOutputStream.toByteArray());
				}	*/						
				tramiteServiceEntityLocal.actualizarDocumentosTramite(solicitud.getTramites().get(0).getTramiteId(), 
						DocumentoPorTipoEnum.AVISO_DE_MODIFICACION.getId(), byteArrayOutputStream.toByteArray());
			}
						
		} catch (JRException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return byteArrayOutputStream.toByteArray();
	}

	
	private Map<String, Object> agregarParametrosPersonaFisica(Solicitud solicitud){
		Map<String, Object> parametrosAgregados = new HashMap<String, Object>();
		
		Fisica personaFisicaTramite = null;
		
		StringBuffer detRazonSocial = new StringBuffer();
		StringBuffer dcMediosContacto = new StringBuffer();
		String detRFC = "";
		String dgRFC = "";
		String dgCURP = "";
		String dgNombre = "";
		String dgPrimerApe = "";
		String dgSegundoApe = "";
		
		String flagDG = "0";
		String flagTipoSO = "f";
		String flagDC = "0";
		String flagSO = "0";
		String flagRL = "0";
		String datosParticulares = "";
		log.info("######$$$$$$>>>>>>>   PATRON PERSONA FISICA   <<<<<<<<$$$$$$###### ");
		
		List<Tramite> listTramites = solicitud.getTramites();
		log.info("######$$$$$$>>>>>>>   Num de TRAMITES listados :"+listTramites.size());
		boolean cambioMedioParticulares=false;
		boolean cambioDomicilioParticular=false;
		for(int x=0; x<listTramites.size();x++){
			
			if(!(listTramites.get(x) instanceof TramiteFisica))
				continue;
			
			TramiteFisica tramiteFI=(TramiteFisica)listTramites.get(x);
			log.info("Tipo de tramite para reporte: "+tramiteFI.getTipoTramite().getIdTipoTramite());
			if(tramiteFI.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL.getCodigo())
				||	tramiteFI.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES.getCodigo())
					){
				log.info("Consultando persona fisica ICA:::::");
				//personaFisicaTramite = tramiteFI.getDatosICA().getPersonaFisicaIMSS();
				personaFisicaTramite = (Fisica)sujetoService.obtenerPersonaPorIdentificador(tramiteFI.getDatosICA().getPersonaFisicaIMSS().getIdPersona());
			}else if(tramiteFI.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO.getCodigo())
					||tramiteFI.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DOMICILIO_PARTICULAR.getCodigo())
					){
				log.info("Consultando persona fisica MDM:::::");
				//personaFisicaTramite = tramiteFI.getDatosMDM().getPersonaFisica();
				cambioMedioParticulares = tramiteFI.getDatosMDM().getIndCapturaMediosContactoParticular();
				cambioDomicilioParticular = tramiteFI.getDatosMDM().getIndCapturaDomicilioParticular();
				log.info("Cambiaron Medios Particulares:::::"+cambioMedioParticulares);
				personaFisicaTramite = (Fisica)sujetoService.obtenerPersonaPorIdentificador(tramiteFI.getDatosMDM().getPersonaFisica().getIdPersona());
				
				
			}
			
			log.info("Persona Fisica:::"+personaFisicaTramite);
			
			detRFC = personaFisicaTramite.getRfc()!=null ? personaFisicaTramite.getRfc().toUpperCase() : "Sin informaci�n";
			
			detRazonSocial.append(personaFisicaTramite.getNombre().toUpperCase()).append(" ");
			detRazonSocial.append(personaFisicaTramite.getPrimerApellido().toUpperCase()).append(" ");
			detRazonSocial.append(personaFisicaTramite.getSegundoApellido().toUpperCase());
			
			
			log.info("######$$$$$$>>>>>>>   Tramite Fisica   <<<<<<<<$$$$$$####### ");
			log.info("######$$$$$$>>>>>>>   Tipo Tramite :: "+ tramiteFI.getTipoTramite().getIdTipoTramite() );
		
			if(tramiteFI.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL.getCodigo())
					|| tramiteFI.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES.getCodigo())
					|| tramiteFI.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DOMICILIO_PARTICULAR.getCodigo())
					|| tramiteFI.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO.getCodigo())
					){
				
				log.info("######$$$$$$>>>>>>>   Tramite Fisica- ICA ");
				
				dgRFC = personaFisicaTramite.getRfc() != null ? personaFisicaTramite.getRfc() : "";
				dgCURP = personaFisicaTramite.getCurp() != null ? personaFisicaTramite.getCurp() : "";
				dgNombre = personaFisicaTramite.getNombre() != null ? personaFisicaTramite.getNombre() : "";
				dgPrimerApe = personaFisicaTramite.getPrimerApellido() != null ? personaFisicaTramite.getPrimerApellido() : "";
				dgSegundoApe = personaFisicaTramite.getSegundoApellido() != null ? personaFisicaTramite.getSegundoApellido() : "";
				
				flagDG = "1";
				
				log.error("Cambiaron los medios de contacto:::"+cambioMedioParticulares);
				String domicilioCompleto="";
				
				if(cambioMedioParticulares || cambioDomicilioParticular){
					log.error("Cambiaron los medios de contacto o domicilio:::");
					Domicilio domParticular = null;
					
					List<Domicilio> domiciliosPersona = personaFisicaTramite.getDomicilios();
					if(domiciliosPersona!=null)
						for(Domicilio domicilio:domiciliosPersona){
							log.error("Tipo del Domicilio: "+domicilio.getDicTipoDomicilio().getClave());
							log.error("Tipo domicilio particular: "+TipoDomicilioEnum.PARTICULAR.getId());
							if(domicilio.getDicTipoDomicilio().getClave().longValue()==TipoDomicilioEnum.PARTICULAR.getId()){
								log.error("Asignando domicilio particular");
								domParticular = domicilio;
							}
						}
					if(domParticular !=null)
						domicilioCompleto=obtenerDomicilioCompleto(domParticular);
					List<MedioContacto> listMediosContactos = personaFisicaTramite.getMediosContacto();
					if(listMediosContactos!=null){
						for(MedioContacto medioContacto : listMediosContactos){
							dcMediosContacto.append(stringMedioContacto(medioContacto));
						}
						flagDC = "1";
					}
					datosParticulares = domicilioCompleto+"; "+dcMediosContacto.toString();
				}
			}
		}
		parametrosAgregados.put("p_flagDatosGen", flagDG);
		parametrosAgregados.put("p_flagDatosContacto", flagDC);
		parametrosAgregados.put("p_flagSocios", flagSO);
		parametrosAgregados.put("p_flagRepLegal", flagRL);
		parametrosAgregados.put("p_flagTipoPersona", flagTipoSO);
		
		parametrosAgregados.put("p_det_razonSocial", detRazonSocial.toString());
		parametrosAgregados.put("p_det_rfc", detRFC);
		
		parametrosAgregados.put("p_dg_rfc", dgRFC);
		parametrosAgregados.put("p_dg_curp", dgCURP);
		parametrosAgregados.put("p_dg_nombre", dgNombre);
		parametrosAgregados.put("p_dg_primerApe", dgPrimerApe);
		parametrosAgregados.put("p_dg_segundoApe", dgSegundoApe);
		parametrosAgregados.put("p_dc_datosContacto",datosParticulares);
		
		return parametrosAgregados;
	}
	
	private Map<String, Object> agregarParametrosPersonaMoral(Solicitud solicitud){
		Map<String, Object> parametrosAgregados = new HashMap<String, Object>();
		
		String detRFC ="";
		StringBuffer detRazonSocial = new StringBuffer();
		String flagTipoSO="";
		String dgRFC="";
		String dgNombre="";
		String flagDG="0";
		String flagDC="0";
		StringBuffer dcMediosContacto = new StringBuffer();
		List<Socio> listSociosFisicos = new ArrayList<Socio>();
		List<Socio> listSociosMorales = new ArrayList<Socio>();
		List<RepresentanteLegal> listRepresentantes = null;
		String flagSO="0";
		String flagRL="0";
		String flagSOFi="0";
		String flagSOMo="0";
		String acNoEscritura = "";
		String acNoNotaria = "";
		String acEntidadFed = "";
		String acMunicipio = "";
		String acFechaExp = "";
		String acFolioMercantil = "" ;
		String acSeccion = "" ;
		String acPartida = "" ;
		String acVolumen = "" ;
		String acFoja = "" ;
		String flagAC = "0";
		
		String rsNoReferencia = "";
		String rsAutoridad = "";
		String rsFechaDoc = "";
		
		String flagRS = "0";
		String datosParticulares="";
		
		boolean cambioMedioParticulares = false;
		boolean cambioDomicilioParticular = false;
		
		
		
		log.info("######$$$$$$>>>>>>>   PATRON PERSONA MORAL   <<<<<<<<$$$$$$###### ");
		Moral personaMoral = null;
		detRazonSocial.append(personaMoral.getRazonSocial().toUpperCase());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy  HH:mm");

		detRFC = personaMoral.getRfc();
		flagTipoSO = "m";
		
		
		
		Moral personaMoralTramite = null;
		List<Tramite> listTramites = solicitud.getTramites();
		log.info("######$$$$$$>>>>>>>   Num de TRAMITES listados :"+listTramites.size());
		for(int x=0; x<listTramites.size();x++){
			
			if(listTramites.get(x) instanceof TramiteFisica)
				continue;
			
			TramiteMoral tramiteMO=null;
			if((listTramites.get(x) instanceof TramiteMoral)){
				tramiteMO=(TramiteMoral)listTramites.get(x);
				if (tramiteMO.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.MODIFICACION_DE_DERECHOHABIENTE.getCodigo()))
						continue;
			}
			
//			TramiteMoral tramiteMO=(TramiteMoral)listTramites.get(x);
			
			log.info("######$$$$$$>>>>>>>   Tramite Moral   <<<<<<<<$$$$$$####### ");
			log.info("######$$$$$$>>>>>>>   Tipo Tramite :: "+ tramiteMO.getTipoTramite().getIdTipoTramite() );
			
			if(tramiteMO.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL.getCodigo())
					||	tramiteMO.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES.getCodigo())
					){
				log.info("######$$$$$$>>>>>>>   Tramite Moral- DENOMINACINO SOCIAL ");
				
				
				log.info("Consultando persona moral ICA:::::");
				//personaFisicaTramite = tramiteFI.getDatosICA().getPersonaFisicaIMSS();
				personaMoralTramite = (Moral)sujetoService.obtenerPersonaMoralPorIdentificador(tramiteMO.getDatosICA().getPersonaMoralIMSS().getIdPersona());
				dgRFC = personaMoralTramite.getRfc();
				dgNombre = personaMoralTramite.getRazonSocial();
				flagDG = "1";

			}else if(tramiteMO.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO.getCodigo())
					||tramiteMO.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_DOMICILIO_PARTICULAR.getCodigo())
					){
				log.info("Consultando persona moral MDM:::::");
				//personaFisicaTramite = tramiteFI.getDatosMDM().getPersonaFisica();
				cambioMedioParticulares = tramiteMO.getDatosMDM().getIndCapturaMediosContactoParticular();
				cambioDomicilioParticular = tramiteMO.getDatosMDM().getIndCapturaDomicilioParticular();
				log.info("Cambiaron Medios Particulares:::::"+cambioMedioParticulares);
				personaMoralTramite = (Moral)sujetoService.obtenerPersonaMoralPorIdentificador(tramiteMO.getDatosMDM().getPersonaMoral().getIdPersona());
				String domicilioCompleto="";
				List<MedioContacto> listMediosContactos = personaMoralTramite.getMediosContacto();
				dcMediosContacto = new StringBuffer();
				for(MedioContacto medioContacto : listMediosContactos){
					dcMediosContacto.append("-").append(stringMedioContacto(medioContacto)).append("\\n");
				}
				
				if(cambioMedioParticulares || cambioDomicilioParticular){
					log.error("Cambiaron los medios de contacto o domicilio:::");
					Domicilio domParticular = null;
					
					List<Domicilio> domiciliosPersona = personaMoralTramite.getDomicilios();
					if(domiciliosPersona!=null)
						for(Domicilio domicilio:domiciliosPersona){
							log.error("Tipo del Domicilio: "+domicilio.getDicTipoDomicilio().getClave());
							log.error("Tipo domicilio particular: "+TipoDomicilioEnum.PARTICULAR.getId());
							if(domicilio.getDicTipoDomicilio().getClave().longValue()==TipoDomicilioEnum.PARTICULAR.getId()){
								log.error("Asignando domicilio particular");
								domParticular = domicilio;
							}
						}
					if(domParticular !=null)
						domicilioCompleto=obtenerDomicilioCompleto(domParticular);
					if(listMediosContactos!=null){
						for(MedioContacto medioContacto : listMediosContactos){
							dcMediosContacto.append(stringMedioContacto(medioContacto));
						}
						flagDC = "1";
					}
					datosParticulares = domicilioCompleto+"; "+dcMediosContacto.toString();
				}
				
				
				flagDC = "1";
				
			}else if(tramiteMO.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_SOCIO.getCodigo())){
				log.info("######$$$$$$>>>>>>>   Tramite Moral - ACTUALIZA SOCIOS ");
				personaMoralTramite = tramiteMO.getMoral();
				List<Socio> listSocios = personaMoralTramite.getSocios();
				log.info("######$$$$$$>>>>>>>   Lista Socios Tramite : "+listSocios.size());
				
				
				for(Socio socioNoDef :listSocios){
					
					if(socioNoDef.getEsPersonaFisica()){
						listSociosFisicos.add(socioNoDef);
						flagSOFi = "1";
						log.info("######$$$$$$>>>>>>>   Agrega Socios Fisica  ");
					} else {
						listSociosMorales.add(socioNoDef);
						flagSOMo = "1";
						log.info("######$$$$$$>>>>>>>   Agraga Socios Moral  ");
					}
				}
				
				flagSO = "1";
				
			} else if(tramiteMO.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo())){
				log.info("######$$$$$$>>>>>>>   Tramite Moral - REP LEGAL ");
				personaMoralTramite = tramiteMO.getMoral();
				listRepresentantes = personaMoralTramite.getRepresentantesLegales();
				
				flagRL = "1";
				log.info("######$$$$$$>>>>>>>   Lista Socios Rep Legal : "+listRepresentantes.size());
			
			} else if(tramiteMO.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA.getCodigo())){
				log.info("######$$$$$$>>>>>>>   Tramite Moral - ACTA CONSTITUTIVA ");
				personaMoralTramite = tramiteMO.getMoral();
				EscrituraConstitutiva actaConst = personaMoralTramite.getEscrituraConstitutiva();
				Municipio lugarExp = actaConst.getLugarExpedicion();
				EntidadFederativa entidadFed = lugarExp.getEntidadFederativa();
						
				acNoEscritura = actaConst.getNumEscritura()!= null? actaConst.getNumEscritura().toString():"sin informaci�n";
				acNoNotaria = actaConst.getNumNotaria() != null ? actaConst.getNumNotaria() : "sin informaci�n";
				acEntidadFed = entidadFed.getNombre() != null ? entidadFed.getNombre() : "sin informacion";
				acMunicipio = lugarExp.getNombre() != null ? lugarExp.getNombre() : "sin informacion";
				acFechaExp = dateFormat.format(actaConst.getFechaExpedicion());
				
				
				if(actaConst.getFolioMercantil() != null && !actaConst.getFolioMercantil().trim().isEmpty())
					acFolioMercantil = actaConst.getFolioMercantil();
				else
					acFolioMercantil = "sin informaci�n" ;
				
				if(actaConst.getSeccion() != null && !actaConst.getSeccion().trim().isEmpty())
					acSeccion = actaConst.getSeccion();
				else
					acSeccion = "sin informaci�n" ;
				
				if(actaConst.getPartida() != null && !actaConst.getPartida().trim().isEmpty())
					acPartida = actaConst.getPartida();
				else
					acPartida = "sin informaci�n" ;
				
				if(actaConst.getVolumen() != null && !actaConst.getVolumen().trim().isEmpty())
					acVolumen = actaConst.getVolumen();
				else
					acVolumen = "sin informaci�n" ;
				
				if(actaConst.getFoja() != null && !actaConst.getFoja().trim().isEmpty())
					acFoja = actaConst.getFoja();
				else
					acFoja = "sin informaci�n" ;
				
				
				flagAC = "1";
			
			} 	else if(tramiteMO.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO.getCodigo())){
				log.info("######$$$$$$>>>>>>>   Tramite Moral - REGISTRO SINDICAL ");
				personaMoralTramite = tramiteMO.getMoral();
				RegistroSindicato regSindicato = personaMoralTramite.getRegistroSindicato();
			
				rsNoReferencia = regSindicato.getNumReferenciadocRegistro().toString();
				rsAutoridad = regSindicato.getAutoridadLaboral();
				rsFechaDoc = dateFormat.format(regSindicato.getFechaRegistro());
				
				flagRS = "1";
			
			} 	  	
			
			
		}
		
		parametrosAgregados.put("p_flagDatosGen", flagDG);
		parametrosAgregados.put("p_flagDatosContacto", flagDC);
		parametrosAgregados.put("p_flagSocios", flagSO);
		parametrosAgregados.put("p_flagRepLegal", flagRL);
		parametrosAgregados.put("p_flagSociosFi", flagSOFi);
		parametrosAgregados.put("p_flagSociosMo", flagSOMo);
		parametrosAgregados.put("p_flagTipoPersona", flagTipoSO);
		parametrosAgregados.put("p_flagActaCons", flagAC);
		parametrosAgregados.put("p_flagRegSindicato",flagRS);
		
		parametrosAgregados.put("p_det_razonSocial", detRazonSocial.toString());
		parametrosAgregados.put("p_det_rfc", detRFC);
		
		parametrosAgregados.put("p_dg_rfc", dgRFC);
		parametrosAgregados.put("p_dg_nombre", dgNombre);
		
		parametrosAgregados.put("p_dc_datosContacto", datosParticulares);
		
		parametrosAgregados.put("p_listSociosFi", listSociosFisicos);
		parametrosAgregados.put("p_listSociosMo", listSociosMorales);
		parametrosAgregados.put("p_listRepLegal", listRepresentantes);
		
		parametrosAgregados.put("p_ac_NoEscritura", acNoEscritura);
		parametrosAgregados.put("p_ac_NoNotaria", acNoNotaria);
		parametrosAgregados.put("p_ac_entidadFed", acEntidadFed);
		parametrosAgregados.put("p_ac_municipio", acMunicipio );
		parametrosAgregados.put("p_ac_fechaExp", acFechaExp);
		parametrosAgregados.put("p_ac_folioMercantil", acFolioMercantil);
		parametrosAgregados.put("p_ac_seccion", acSeccion);
		parametrosAgregados.put("p_ac_partida", acPartida);
		parametrosAgregados.put("p_ac_volumen", acVolumen);
		parametrosAgregados.put("p_ac_foja", acFoja);
		
		parametrosAgregados.put("p_rs_NoReferencia", rsNoReferencia);
		parametrosAgregados.put("p_rs_autoridad", rsAutoridad);
		parametrosAgregados.put("p_rs_fechaDoc", rsFechaDoc);
		
		return parametrosAgregados;
	}
	
	
	private String obtenerDomicilioCompleto(Domicilio domicilio){
		log.debug("Obteniendo cadena de domicilio completo:::");
		StringBuffer domicilioCompleto = new StringBuffer();
		domicilioCompleto.append(domicilio.getVialidadPrimaria().getNombre());
		domicilioCompleto.append(" #"+domicilio.getNumExterior1()+ (domicilio.getNumExteriorAlf()!=null ? " " + domicilio.getNumExteriorAlf() : "") );
		if(domicilio.getNumInterior()!=null)
			domicilioCompleto.append(", interior "+domicilio.getNumInterior()+(domicilio.getNumInteriorAlf()!=null ? " "+domicilio.getNumInteriorAlf() : "" ) );
		domicilioCompleto.append(", COLONIA "+domicilio.getAsentamiento().getNombre());
		domicilioCompleto.append(", CP "+domicilio.getCodigoPostal().getCodigoPostal());
		log.debug("Domicilio completo:::"+domicilioCompleto.toString());
		return domicilioCompleto.toString();
	}
	
	@Override
	public byte[] generaReporteSemanasCotizadas(HldaVO hlda, FirmaElectronica datosFirma){
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		
		try{
			List<HldaDetalleVO> elementosPrimerColumna = new ArrayList<HldaDetalleVO>();
			List<HldaDetalleVO> elementosSegundaColumna = new ArrayList<HldaDetalleVO>();
			List<HldaDetalleVO> elementosTercerColumna = new ArrayList<HldaDetalleVO>();
			
			if(hlda.getDetalle().size()<3){
				if(hlda.getDetalle().size()>0)
					elementosPrimerColumna = hlda.getDetalle().subList(0, 1);
				if(hlda.getDetalle().size()>1)
					elementosPrimerColumna = hlda.getDetalle().subList(1, 2);				
			}else{
				Integer elementosPorColumna =	hlda.getDetalle().size() / 3;
				Integer numeroElementosPrimerColumna = elementosPorColumna;
				Integer numeroElementosUltimaColumna = hlda.getDetalle().size() - elementosPorColumna*2;
				if(numeroElementosPrimerColumna<numeroElementosUltimaColumna){
					numeroElementosPrimerColumna = numeroElementosUltimaColumna;
				}
				
				elementosPrimerColumna = hlda.getDetalle().subList(0, numeroElementosPrimerColumna);
				elementosSegundaColumna = hlda.getDetalle().subList(numeroElementosPrimerColumna, numeroElementosPrimerColumna+elementosPorColumna);
				elementosTercerColumna = hlda.getDetalle().subList(numeroElementosPrimerColumna+elementosPorColumna, hlda.getDetalle().size());
				
			}
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("fechaEmision", Calendar.getInstance().getTime());
			parameters.put("elementosColumnaUno", elementosPrimerColumna);
			parameters.put("elementosColumnaDos", elementosSegundaColumna);
			parameters.put("elementosColumnaTres", elementosTercerColumna);
			
			//Datos firma
			parameters.put("cadenaOriginal", datosFirma.getCadenaOriginal());
			parameters.put("secuenciaNotaria", datosFirma.getReciboNotarial());
			parameters.put("selloDigital", datosFirma.getRecibo());
			parameters.put("numeroSerie", datosFirma.getSerialCertificado());
			parameters.put("logo", "logo_imss.jpg");
			
			ArrayList<HldaVO> beanData = new ArrayList<HldaVO>();
			
			List<JasperPrint> arreglo = new ArrayList<JasperPrint>(); 			
			JRBeanCollectionDataSource dataSource = null;
			JasperPrint print = null;
			beanData.add(hlda);
			
			List<String> comprobantes = new ArrayList<String>();
			comprobantes.add("Semanas_Cotizadas.jasper");
			
			JasperReport report = (JasperReport) JRLoader.loadObject(new ClassPathResource("comprobantes/Semanas_Cotizadas.jasper").getInputStream());
			dataSource = new JRBeanCollectionDataSource(beanData);
			print = JasperFillManager.fillReport(report,parameters,dataSource);
			arreglo.add(print);
				
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,arreglo);			
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,byteArrayOutputStream);			
			exporter.exportReport();		
		
		
		} catch (Exception e) {// Agregar las excepciones personalizadas
			e.printStackTrace();
		}
		
		return byteArrayOutputStream.toByteArray();
	}
	
	
	public ByteArrayOutputStream concatenaPDFs(List<ByteArrayOutputStream>  byteArrayOutputStream, boolean paginate ) {
        Document document = new Document();
        OutputStream outputStream = new ByteArrayOutputStream();
        try {
            List<ByteArrayOutputStream> pdfs = byteArrayOutputStream;
            List<PdfReader> readers = new ArrayList<PdfReader>();
            int totalPages = 0;
            Iterator<ByteArrayOutputStream> iteratorPDFs = pdfs.iterator();
            
            while (iteratorPDFs.hasNext()) {
            	ByteArrayOutputStream pdf = iteratorPDFs.next();
                PdfReader pdfReader = new PdfReader(pdf.toByteArray());
                readers.add(pdfReader);
                totalPages += pdfReader.getNumberOfPages();
            }
            
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
                  document.open();
            PdfContentByte cb = writer.getDirectContent();
            PdfImportedPage page;
            int currentPageNumber = 0;
            int pageOfCurrentReaderPDF = 0;
            Iterator<PdfReader> iteratorPDFReader = readers.iterator();
 
            while (iteratorPDFReader.hasNext()) {
                PdfReader pdfReader = iteratorPDFReader.next();
 
                while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
 
                    Rectangle rectangle = pdfReader.getPageSizeWithRotation(1);
                    document.setPageSize(rectangle);
                    document.newPage();
 
                    pageOfCurrentReaderPDF++;
                    currentPageNumber++;
                    page = writer.getImportedPage(pdfReader,
                            pageOfCurrentReaderPDF);
                    switch (rectangle.getRotation()) {
                    case 0:
                        cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
                        break;
                    case 90:
                        cb.addTemplate(page, 0, -1f, 1f, 0, 0, pdfReader
                                .getPageSizeWithRotation(1).getHeight());
                        break;
                    case 180:
                        cb.addTemplate(page, -1f, 0, 0, -1f, 0, 0);
                        break;
                    case 270:
                        cb.addTemplate(page, 0, 1.0F, -1.0F, 0, pdfReader
                                .getPageSizeWithRotation(1).getWidth(), 0);
                        break;
                    default:
                        break;
                    }
                    if (paginate) {
                        cb.beginText();
                        cb.getPdfDocument().getPageSize();
                        cb.endText();
                    }
                }
                pageOfCurrentReaderPDF = 0;
            }
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (document.isOpen())
                document.close();
        }
        return (ByteArrayOutputStream)outputStream;
    }
	
}
	

