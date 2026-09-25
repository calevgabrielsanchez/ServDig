package mx.gob.imss.ctirss.delta.gestion.asegurado.service.utility;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.GenerarCodigoQRServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;

@Stateless(mappedName = "reporteHelper")
public class ReporteHelper extends AbstractServiceUtility implements
		ReporteHelperLocal {

	@EJB
	private DomicilioServiceBusinessRemote domicilioServiceBusiness;
	@EJB(mappedName = "generarCodigoQRServiceBusiness")
	private GenerarCodigoQRServiceBusinessRemote generarCodigoQRServiceBusinessRemote;
	
	private transient final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"dd/MM/yyyy", new Locale("es", "mx"));
	private transient final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(
			"kk:mm:ss", new Locale("es", "mx"));
	
	

	@Override
	public byte[] reporteSimpleConQRImgane(AsignacionNSS asignacionNSS) {
		//respuesta del reporte
		byte[] reporteRespuesta = null;
		List<AsignacionNSS> data = new ArrayList<AsignacionNSS>();
		data.add(asignacionNSS);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		try {
			Map<String, Object> parametros = getParametrosFormatoSimpleConQR(asignacionNSS);
			JasperReport report = JasperCompileManager.compileReport(new ClassPathResource("reportes/credencialNSS.jrxml").getInputStream());
			
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
			JasperPrint print = JasperFillManager.fillReport(report,parametros, dataSource);
			
			BufferedImage imagen = (BufferedImage) JasperPrintManager.printPageToImage(print, 0, 2.5f);
			ImageIO.write(imagen, "png", byteArrayOutputStream);
			
			reporteRespuesta = byteArrayOutputStream.toByteArray();
		} catch (JRException jre) {
			jre.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return reporteRespuesta;
	}

	@Override
	public byte[] reporteSimpleConQR(Solicitud solicitud) {
		//respuesta del reporte
		byte[] reporteRespuesta = null;
		final List<Tramite> tramites = new ArrayList<Tramite>();

		for (Tramite tramite : solicitud.getTramites()) {
			if (tramite instanceof TramiteAsegurado) {
				tramites.add(tramite);
			}
		}

		corrigeDatos(tramites);
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		try {
			Map<String, Object> parametros = getParametrosFormatoSimpleConQR(solicitud);
			JasperReport report = JasperCompileManager.compileReport(new ClassPathResource("reportes/ComprobanteAsignacionQR.jrxml").getInputStream());
			
			
			JasperReport subreport = (JasperReport) JRLoader.loadObject(new ClassPathResource("reportes/credencialNSS.jasper").getInputStream());
			parametros.put("SUBREPORTE_CREDENCIAL", subreport);
			
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(tramites);
			JasperPrint print = JasperFillManager.fillReport(report,parametros, dataSource);
			
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,byteArrayOutputStream);
			exporter.exportReport();
			reporteRespuesta = byteArrayOutputStream.toByteArray();
		} catch (JRException jre) {
			jre.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return reporteRespuesta;
	}

	@Override
	public ByteArrayOutputStream ejecutaReporte(Map parametros,List<? extends Serializable> lista, String reporte) {
		// TODO Auto-generated method stub
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		try {
			JRBeanCollectionDataSource dataSource;
			dataSource = new JRBeanCollectionDataSource(lista);
			
			JasperReport report = JasperCompileManager.compileReport(new ClassPathResource("reportes/"+reporte).getInputStream());
			
			JasperPrint print = JasperFillManager.fillReport(report,parametros,dataSource);

			
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					byteArrayOutputStream);
			
			
			exporter.exportReport();

			
			return byteArrayOutputStream;
		} catch (Exception e) {// Agregar las excepciones personalizadas
			log.error("trono al generar el reporte "+ reporte, e);
			e.getMessage();
		}
		
		return null;
	}
	
	@Override
	public byte[] reporte(final Solicitud solicitud) {
		final List<Tramite> tramites = new ArrayList<Tramite>();

		for (Tramite tramite : solicitud.getTramites()) {
			if (tramite instanceof TramiteAsegurado) {
				tramites.add(tramite);
			}
		}

		corrigeDatos(tramites);
		byte[] reporteRespuesta = null; // NOPMD
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			JasperReport report = JasperCompileManager
					.compileReport(new ClassPathResource(
							"reportes/ReporteComprobanteAsignacionV2.jrxml")
							//"reportes/ReporteComprobanteAsignacion.jrxml")
							.getInputStream());
			JasperPrint print;
			JRBeanCollectionDataSource beanDS = new JRBeanCollectionDataSource(
					tramites);
			print = JasperFillManager.fillReport(report,
					getParametersReporteInterno(solicitud), beanDS);
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					byteArrayOutputStream);
			exporter.exportReport();
			reporteRespuesta = byteArrayOutputStream.toByteArray();
		} catch (JRException jre) {
			jre.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return reporteRespuesta;
	}

	
	@Override
	public byte[] reporteLocalizacionNss(Solicitud solicitud) {
		final List<Tramite> tramites = new ArrayList<Tramite>();
		TramiteAsegurado tramiteAsegurado = null;
		for (Tramite tramite : solicitud.getTramites()) {
			if (tramite instanceof TramiteAsegurado) {
				tramites.add(tramite);
				tramiteAsegurado = (TramiteAsegurado) tramite;
			}
		}

		corrigeDatos(tramites);
		byte[] reporteRespuesta = null; // NOPMD
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			JasperReport report = JasperCompileManager
					.compileReport(new ClassPathResource(
							"reportes/ReporteComprobanteAsignacionV2.jrxml")
							//"reportes/ReporteComprobanteAsignacion.jrxml")
							.getInputStream());
			JasperPrint print;
			JRBeanCollectionDataSource beanDS = new JRBeanCollectionDataSource(
					tramites);
			print = JasperFillManager.fillReport(report,
					getModelRecuperado(tramiteAsegurado, solicitud), beanDS);
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					byteArrayOutputStream);
			exporter.exportReport();
			reporteRespuesta = byteArrayOutputStream.toByteArray();
		} catch (JRException jre) {
			jre.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return reporteRespuesta;
	}

	private Map<String, Object> getModelRecuperado(TramiteAsegurado tramite,Solicitud solicitud) {
		
		final Map<String, Object> model = getParametersReporteRecuperado(
				solicitud.getCadenaOriginal(), solicitud.getSelloDigital(), solicitud.getSecuenciaDeNotaria(), solicitud.getNumeroSerieCertificado(), solicitud);

		return model;
	}
	
	@Override
	public Map<String, Object> getModelRecuperado(TramiteAsegurado tramite,
			String cadenaOriginal, String selloDigital,
			String secuenciaNotaria, String serie,Solicitud solicitud) {
		List<Tramite> dataSource = new ArrayList<Tramite>();
		
		/*
		 * Debido a que las solicitudes de asignación pueden tener
		 * TramiteAsegurado y TramiteFisica, se filtra que sólo el
		 * TramiteAsegurado sea el que se utilice como dataSource del
		 * comprobante, para evitar que se genere en el comprobante una hoja por
		 * trámite.
		 */
		dataSource.add(tramite);

		corrigeDatos(dataSource);

		final Map<String, Object> model = getParametersReporteRecuperado(
				cadenaOriginal, selloDigital, secuenciaNotaria, serie, solicitud);
		model.put("dataSource", dataSource);

		return model;
	}

	@Override
	public Map<String, Object> getModel(Solicitud solicitud) {

		final List<Tramite> tramites = solicitud.getTramites();
		List<Tramite> dataSource = new ArrayList<Tramite>();
		
		/*
		 * Debido a que las solicitudes de asignación pueden tener
		 * TramiteAsegurado y TramiteFisica, se filtra que sólo el
		 * TramiteAsegurado sea el que se utilice como dataSource del
		 * comprobante, para evitar que se genere en el comprobante una hoja por
		 * trámite.
		 */
		for (Tramite tramite : tramites) {
			if (tramite instanceof TramiteAsegurado) {
				dataSource.add(tramite);
			}
		}

		corrigeDatos(dataSource);
		final Map<String, Object> model = getParametersReporteInterno(solicitud);
		model.put("dataSource", dataSource);

		return model;
	}

	@Override
	public Map<String, Object> getModelExterno(Solicitud solicitud) {
		final List<Tramite> tramites = solicitud.getTramites();
		List<Tramite> dataSource = new ArrayList<Tramite>();

		/*
		 * Debido a que las solicitudes de asignación pueden tener
		 * TramiteAsegurado y TramiteFisica, se filtra que sólo el
		 * TramiteAsegurado sea el que se utilice como dataSource del
		 * comprobante, para evitar que se genere en el comprobante una hoja por
		 * trámite.
		 */
		for (Tramite tramite : tramites) {
			if (tramite instanceof TramiteAsegurado) {
				dataSource.add(tramite);
			}
		}

		corrigeDatos(tramites);
		final Map<String, Object> model = getParametersReporteExterno(solicitud);
		model.put("dataSource", dataSource);

		return model;
	}

	private void corrigeDatos(final List<Tramite> tramites) {
		for (Tramite tramiteG : tramites) {
			if (tramiteG instanceof TramiteAsegurado) {
				final TramiteAsegurado tramite = (TramiteAsegurado) tramiteG;

				// ASIGNA DESCRIPCION DE SEXO
				if (tramite.getFisica() != null) {
					Fisica fisica = tramite.getFisica();

					if (fisica.getSexo() != null
							&& fisica.getSexo().getIdSexo() != null) {

						if (tramite.getFisica().getSexo().getIdSexo() == 1) {
							tramite.getFisica().getSexo()
									.setDescripcion("Hombre");
						} else {
							tramite.getFisica().getSexo()
									.setDescripcion("Mujer");
						}
					}

					/*
					 * Se va por el nombre de la entidad de nacimiento, sólo si
					 * no se trae
					 */
					if (fisica.getLugarNacimiento() != null
							&& StringUtils.isNotBlank(fisica
									.getLugarNacimiento().getClave())
							&& StringUtils.isBlank(fisica.getLugarNacimiento()
									.getNombre())) {

						EntidadFederativa estado = this.domicilioServiceBusiness
								.getEstado(fisica.getLugarNacimiento()
										.getClave());
						if (estado != null) {
							fisica.setLugarNacimiento(estado);
						}
					}
				}
			}
		}
	}
	
	@Override
	public byte[] getReporteVacio() {
		Solicitud solicitud = new Solicitud();
		solicitud.setNoFolioSolicitud("");
		List<Tramite> tramites = new ArrayList<Tramite>();
		TramiteAsegurado tramiteAsegurado = new TramiteAsegurado();
		tramites.add(tramiteAsegurado);
		
		byte[] reporteRespuesta = null; // NOPMD
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			JasperReport report = JasperCompileManager
					.compileReport(new ClassPathResource(
							"reportes/ReporteComprobanteAsignacionV2.jrxml")
							//"reportes/ReporteComprobanteAsignacion.jrxml")
							.getInputStream());
			JasperPrint print;
			JRBeanCollectionDataSource beanDS = new JRBeanCollectionDataSource(
					tramites);
			print = JasperFillManager.fillReport(report,
					getParametersReporteVacio(solicitud), beanDS);
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					byteArrayOutputStream);
			exporter.exportReport();
			reporteRespuesta = byteArrayOutputStream.toByteArray();
		} catch (JRException jre) {
			jre.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return reporteRespuesta;
	}
	
	/**
	 * 191807 200912 Este metodo ahora procesara los parametros de los reportes
	 * PDF internos, o sea por ventanilla
	 * 
	 * @param solicitud
	 * @return
	 */
	private Map<String, Object> getParametersReporteVacio(final Solicitud solicitud) {
		final Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("LOGO", new ClassPathResource("comprobantes/headerPicture.png").getPath());
		parameters.put("PIE", new ClassPathResource("comprobantes/footer.png").getPath());
		parameters.put("NOMBRE_SOLICITANTE","");
		parameters.put("ESTATUS_OPER", "En Proceso");
		parameters.put("JUSTIFICACION_LEGAL", "JUSTIFICACION LEGAL PENDIENTE");
		parameters.put("FORMATEADOR_REPORTE", new ReporteFormatter());
		parameters.put("CADENA_ORIGINAL", "");
		parameters.put("SELLO_DIGITAL", "");
		parameters.put("SECUENCIA_NOTARIA", "");
		parameters.put("NO_SERIE", "");
		parameters.put("IS_RECUPERADO", Boolean.FALSE);

		return parameters;
	}

	/**
	 * 191807 200912 Este metodo ahora procesara los parametros de los reportes
	 * PDF internos, o sea por ventanilla
	 * 
	 * @param solicitud
	 * @return
	 */
	private Map<String, Object> getParametersReporteInterno(
			final Solicitud solicitud) {
		final Map<String, Object> parameters = new HashMap<String, Object>();
		Locale locMEX = new Locale("es", "MX");
		//para la version 2 descomentar la linea
		SimpleDateFormat sdf = new SimpleDateFormat("dd '          ' MM '          ' yyyy",locMEX);
		//SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'del' yyyy 'a las' kk:mm",locMEX);
		
		
		try {
			InputStream image = new ClassPathResource(
					"reportes/imss_header.gif").getInputStream();
			parameters.put("IMSS_HEADER_PARAM", image);
		} catch (IOException e) {
			this.log.error(
					"Error al leer la imagen de logo IMSS para el reporte.", e);
		}
		
		parameters.put("LOGO", new ClassPathResource("comprobantes/headerPicture.png").getPath());
		parameters.put("PIE", new ClassPathResource("comprobantes/footer.png").getPath());
		parameters.put("IMSS_HEADER2_PARAM", "");
		final Date fechaImpresion = solicitud.getFechaSolicitud();
		parameters.put("FECHA", DATE_FORMAT.format(fechaImpresion));
		parameters.put("HORA", TIME_FORMAT.format(fechaImpresion));
		parameters.put("FOLIO_OPER", solicitud.getNoFolioSolicitud());
		parameters.put("NOMBRE_SOLICITANTE",solicitud.getSolicitante() == null ? "" : solicitud.getSolicitante());
		parameters.put("ESTATUS_OPER", "En Proceso");
		parameters.put("JUSTIFICACION_LEGAL", "JUSTIFICACION LEGAL PENDIENTE");
		parameters.put("FORMATEADOR_REPORTE", new ReporteFormatter());
		parameters.put("CADENA_ORIGINAL", solicitud.getCadenaOriginal());
		parameters.put("SELLO_DIGITAL", solicitud.getSelloDigital());
		parameters.put("SECUENCIA_NOTARIA", solicitud.getSecuenciaDeNotaria());
		parameters.put("NO_SERIE", solicitud.getNumeroSerieCertificado());
		parameters.put("IS_RECUPERADO", Boolean.FALSE);
		parameters.put("FECHA_FORMATEADA", sdf.format(fechaImpresion));

		return parameters;
	}
	
	private Map<String, Object> getParametrosFormatoSimpleConQR(
			final Solicitud solicitud) {
		final Map<String, Object> parameters = new HashMap<String, Object>();
		Locale locMEX = new Locale("es", "MX");
		SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy ",locMEX);
		
		String cadena = "";
		
		for(Tramite tramite:solicitud.getTramites()) {
			if(tramite instanceof TramiteAsegurado) {
				cadena = this.getCadenaQR(((TramiteAsegurado) tramite).getFisica());
				break;
			}
		}
		BufferedImage imagenCodeQR = generarCodigoQRServiceBusinessRemote.generadorCodigoQrCadena(cadena, 250, 250);
		
		parameters.put("imagenQR", imagenCodeQR);
		parameters.put("LOGO", new ClassPathResource("reportes/headerPicture.png").getPath());
		parameters.put("logoIMSS", new ClassPathResource("reportes/imssBlanco.png").getPath());
		parameters.put("imgRecorte", new ClassPathResource("reportes/imgRecortar.png").getPath());
		parameters.put("PIE", new ClassPathResource("reportes/footer.png").getPath());
		final Date fechaImpresion = solicitud.getFechaSolicitud();
		parameters.put("FECHA", DATE_FORMAT.format(fechaImpresion));
		parameters.put("HORA", TIME_FORMAT.format(fechaImpresion));
		parameters.put("FOLIO_OPER", solicitud.getNoFolioSolicitud());
		parameters.put("NOMBRE_SOLICITANTE",solicitud.getSolicitante() == null ? "" : solicitud.getSolicitante());
		parameters.put("ESTATUS_OPER", "En Proceso");
		parameters.put("JUSTIFICACION_LEGAL", "JUSTIFICACION LEGAL PENDIENTE");
		parameters.put("FORMATEADOR_REPORTE", new ReporteFormatter());
		parameters.put("CADENA_ORIGINAL", solicitud.getCadenaOriginal());
		parameters.put("SELLO_DIGITAL", solicitud.getSelloDigital());
		parameters.put("SECUENCIA_NOTARIA", solicitud.getSecuenciaDeNotaria());
		parameters.put("NO_SERIE", solicitud.getNumeroSerieCertificado());
		parameters.put("IS_RECUPERADO", Boolean.FALSE);
		parameters.put("FECHA_FORMATEADA", sdf.format(fechaImpresion));

		return parameters;
	}
	
	private Map<String, Object> getParametrosFormatoSimpleConQR(final AsignacionNSS nss) {
		final Map<String, Object> parameters = new HashMap<String, Object>();
		String cadena = this.getCadenaQR(nss);
		BufferedImage imagenCodeQR = generarCodigoQRServiceBusinessRemote.generadorCodigoQrCadena(cadena, 250, 250);
		
		parameters.put("imagenQR", imagenCodeQR);
		parameters.put("LOGO", new ClassPathResource("reportes/headerPicture.png").getPath());
		parameters.put("logoIMSS", new ClassPathResource("reportes/imssBlanco.png").getPath());
		parameters.put("imgRecorte", new ClassPathResource("reportes/imgRecortar.png").getPath());
		parameters.put("ESTATUS_OPER", "En Proceso");
		parameters.put("JUSTIFICACION_LEGAL", "JUSTIFICACION LEGAL PENDIENTE");

		return parameters;
	}
	private String getCadenaQR(Fisica fisica) {
		String cadena = "";
		
		cadena+="||N\u00FAmero de Seguridad Social: " + fisica.getNss();
		cadena+=" |Nombre: " + fisica.getNombreCompleto()+"";
		cadena+=" |CURP: "+ fisica.getCurp()+"||";
		
		return cadena;
	}


	/**
	 * 191807 200912 Este metodo ahora procesara los parametros de los reportes
	 * PDF internos, o sea por ventanilla
	 * 
	 * @param solicitud
	 * @return
	 */
	private Map<String, Object> getParametersReporteRecuperado(
			String cadenaOriginal, String selloDigital, String secuencia,
			String serie, Solicitud solicitud) {
		final Map<String, Object> parameters = new HashMap<String, Object>();
		Locale locMEX = new Locale("es", "MX");
		//SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'del' yyyy 'a las' kk:mm",locMEX);
		//TODO para la version 2 descomentar
		SimpleDateFormat sdf = new SimpleDateFormat("dd '          ' MM '          ' yyyy",locMEX);
		try {
			InputStream image = new ClassPathResource(
					"reportes/imss_header.gif").getInputStream();
			parameters.put("IMSS_HEADER_PARAM", image);
		} catch (IOException e) {
			this.log.error(
					"Error al leer la imagen de logo IMSS para el reporte.", e);
		}
		parameters.put("LOGO", new ClassPathResource("comprobantes/headerPicture.png").getPath());
		parameters.put("PIE", new ClassPathResource("comprobantes/footer.png").getPath());
		parameters.put("IMSS_HEADER2_PARAM", "");
		final Date fechaImpresion = solicitud.getFechaPresentacion();
		parameters.put("FECHA", DATE_FORMAT.format(fechaImpresion));
		parameters.put("HORA", TIME_FORMAT.format(fechaImpresion));
		parameters.put("ESTATUS_OPER", "En Proceso");
		parameters.put("JUSTIFICACION_LEGAL", "JUSTIFICACION LEGAL PENDIENTE");
		parameters.put("FORMATEADOR_REPORTE", new ReporteFormatter());
		parameters.put("CADENA_ORIGINAL", cadenaOriginal);
		parameters.put("SELLO_DIGITAL", selloDigital);
		parameters.put("SECUENCIA_NOTARIA", secuencia);
		parameters.put("NO_SERIE", serie);
		parameters.put("IS_RECUPERADO", Boolean.TRUE);
		parameters.put("FECHA", DATE_FORMAT.format(fechaImpresion));
		parameters.put("HORA", TIME_FORMAT.format(fechaImpresion));
		parameters.put("FOLIO_OPER", solicitud.getNoFolioSolicitud());
		parameters.put("FECHA_FORMATEADA", sdf.format(fechaImpresion));

		return parameters;
	}

	/**
	 * 191807 200912 Este metodo agrega los parametros para el reporte PDF de
	 * asegurados que accesaron de manera externa, o sea por internet
	 * 
	 * @param solicitud
	 * @return
	 */
	private Map<String, Object> getParametersReporteExterno(
			final Solicitud solicitud) {
		final Map<String, Object> parameters = new HashMap<String, Object>();
		final Date fechaImpresion = solicitud.getFechaSolicitud();
		parameters.put("FECHA", DATE_FORMAT.format(fechaImpresion));
		parameters.put("FOLIO_OPER", solicitud.getNoFolioSolicitud()); 
		parameters.put("CADENA_ORIGINAL", solicitud.getCadenaOriginal());
		parameters.put("SELLO_DIGITAL", solicitud.getSelloDigital());
		parameters.put("IS_RECUPERADO", Boolean.FALSE);
		return parameters;
	}
}
