package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.util.DateUtils;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity.ReporteRissServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ReporteRissServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.utility.EmailServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.enums.ApartadoPersonaBeneficioEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParametroSistemaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.integracion.common.EmailDataWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.integracion.common.ReporteRissWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Stateless(name = "reporteRissServiceBusiness", mappedName = "reporteRissServiceBusiness")
public class ReporteRissServiceBusiness extends AbstractServiceBusiness
		implements ReporteRissServiceBusinessRemote {
	
	@EJB
	private ReporteRissServiceEntityLocal reporteRissServiceEntity;
	@EJB
	private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;
	@EJB
	private EmailServiceUtilityLocal emailServiceUtility;
	@EJB
	private ParametrosServiceBusinessLocal  parametrosServiceBusiness;
	
	private static final String SEPARADOR_TRAMA = "|";
	private static final String ENCABEZADO = "FOLIO SOLICITUD|RFC|APARTADO|NSS|NRP|OBSERVACIONES|FECHA|ORIGEN|ESTADO";
	private static final String ENCABEZADO_NSS = "NSS|FECHA ALTA|ESTADO BENEFICIO|MEDIO CONTACTO|TIPO";
	private static final String ENCABEZADO_NRP = "NRP|MEDIO CONTACTO|TIPO|DOMICILIO";
	private static final String RUTA_BASE = "/home/marco/Documentos/Reportes RISS/";
	
	@Override
	public void obtenerTxtMovimientosRiss() {
		
		Date fechaInicio = null;
		Date fechaFin = null;
		
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(DateUtils.dateToDateConFormato("01/07/2014", "dd/MM/yyyy"));
		fechaInicio = calendar.getTime();
		
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -1);
		fechaFin = calendar.getTime();
		
		this.log.debug("Fecha inicio calculada para txt RISS -> " + fechaInicio);
		this.log.debug("Fecha fin calculada para txt RISS -> " + fechaFin);
		
		List<ReporteRissWrapper> solicitudes = obtenerDatosReporte(fechaInicio, fechaFin);
		
		BufferedWriter bw = null;
		StringBuffer registro = new StringBuffer();
				
		try {
			File file = new File(RUTA_BASE + "REPORTE RISS.txt");
			 
			if (!file.exists()) {
				file.createNewFile();
			}
	
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			
			this.log.warn("Total de solicitudes en reporte -> " + solicitudes.size());
			
			bw.write(ENCABEZADO);
			bw.write("\r\n");
			
			for (ReporteRissWrapper solicitud : solicitudes) {
				
				registro.append(formatearCampo(solicitud.getFolio())).append(SEPARADOR_TRAMA);
				registro.append(formatearCampo(getRfc(solicitud))).append(SEPARADOR_TRAMA);
				registro.append(formatearCampo(solicitud.getApartado())).append(SEPARADOR_TRAMA);
				registro.append(formatearCampo(solicitud.getNss())).append(SEPARADOR_TRAMA);
				registro.append(formatearCampo(solicitud.getNrp())).append(SEPARADOR_TRAMA);
				registro.append(formatearCampo(getObservaciones(solicitud))).append(SEPARADOR_TRAMA);							
				registro.append(DateUtils.dateToStringConFormato(solicitud.getFecha(), "dd/MM/yyyy")).append(SEPARADOR_TRAMA);
				registro.append(formatearCampo(solicitud.getOrigen())).append(SEPARADOR_TRAMA);
				registro.append(formatearCampo(solicitud.getEstado()));
	
				bw.write(registro.toString());
				bw.write("\r\n");
				
				registro.delete(0, registro.length());
			}
		} catch(IOException e) {
			this.log.error(e);
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					this.log.error(e);
				}
			}
		}
	}
	
	@Override
	public void generarExcelMovimientosRiss(boolean isCorteDiaAnterior,
			boolean mostrarDatosNrp, boolean enviarCorreo) {
				
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		
		Date fechaInicio = null;
		Date fechaFin = null;
		
		if (isCorteDiaAnterior) {
			if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
				calendar.add(Calendar.DATE, -3);
			} else {
				calendar.add(Calendar.DATE, -1);
			}
		} else {
			// Fecha de salida a producción del RISS
			calendar.setTime(DateUtils.dateToDateConFormato("01/07/2014", "dd/MM/yyyy"));
		}
		
		fechaInicio = calendar.getTime();
				
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -1);
		fechaFin = calendar.getTime();
		
		this.log.debug("Fecha inicio calculada para reporte RISS -> " + fechaInicio);
		this.log.debug("Fecha fin calculada para reporte RISS -> " + fechaFin);
		
		List<ReporteRissWrapper> solicitudes = obtenerDatosReporte(fechaInicio, fechaFin);
		int rowNum = 0;
		
		try {
			// keep 100 rows in memory, exceeding rows will be flushed to disk
			SXSSFWorkbook wb = new SXSSFWorkbook(100); 
	        Sheet sh = wb.createSheet("Solicitudes RISS");
	        Row row = null;
	        Cell cell = null;
			
	        String[] encabezado = ENCABEZADO.split("\\|"); 
	        
	        row = sh.createRow(rowNum);
	        
	        CellStyle headerStyle = wb.createCellStyle();
	        Font headerFont = wb.createFont();
	        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
	        headerFont.setFontHeightInPoints((short)10);
	        headerFont.setFontName("Arial");
	        headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
	        headerStyle.setFont(headerFont);
	        
	        CellStyle bodyStyle = wb.createCellStyle();
	        Font bodyFont = wb.createFont();
	        bodyFont.setFontHeightInPoints((short)10);
	        bodyFont.setFontName("Arial");
	        bodyStyle.setFont(bodyFont);
	        
	        for (int i = 0; i < encabezado.length; i++) {
	        	cell = row.createCell(i);
	        	cell.setCellValue(encabezado[i]);
	        	cell.setCellStyle(headerStyle);
	        }
	               			
	        rowNum ++;
	        
			for (ReporteRissWrapper solicitud : solicitudes) {
				
				row = sh.createRow(rowNum);

				// FOLIO
				cell = row.createCell(0);
	        	cell.setCellValue(formatearCampo(solicitud.getFolio()));
	        	cell.setCellStyle(bodyStyle);
	        	
	        	// RFC
	        	cell = row.createCell(1);
	        	cell.setCellValue(getRfc(solicitud));
	        	cell.setCellStyle(bodyStyle);
	        			        	
	        	// APARTADO
	        	cell = row.createCell(2);
	        	cell.setCellValue(formatearCampo(solicitud.getApartado()));
	        	cell.setCellStyle(bodyStyle);
	        	
	        	// NSS
	        	cell = row.createCell(3);
				cell.setCellValue(StringUtils.isNotBlank(solicitud.getNss()) ? formatearCampo(solicitud
						.getNss()) : formatearCampo(solicitud.getNssXML()));
	        	cell.setCellStyle(bodyStyle);
	        	
	        	// NRP
	        	cell = row.createCell(4);
	        	cell.setCellValue(formatearCampo(solicitud.getNrp()));
	        	cell.setCellStyle(bodyStyle);
	        	
	        	// OBSERVACIONES
	        	cell = row.createCell(5);
	        	cell.setCellValue(getObservaciones(solicitud));
	        	cell.setCellStyle(bodyStyle);
	        	
	        	// FECHA
	        	cell = row.createCell(6);
	        	cell.setCellValue(DateUtils.dateToStringConFormato(solicitud.getFecha(), "dd/MM/yyyy"));
	        	cell.setCellStyle(bodyStyle);
	        	
	        	// ORIGEN
	        	cell = row.createCell(7);
	        	cell.setCellValue(formatearCampo(solicitud.getOrigen()));
	        	cell.setCellStyle(bodyStyle);
	        	
	        	// ESTADO
	        	cell = row.createCell(8);
	        	cell.setCellValue(formatearCampo(solicitud.getEstado()));
	        	cell.setCellStyle(bodyStyle);
	        	
	        	rowNum++;
				
			}
									
			if (isCorteDiaAnterior) {
				InputStream input = new FileInputStream(new File(RUTA_BASE + "RISS_" + DateUtils.dateToStringConFormato(fechaInicio, "yyyyMMdd") + ".xlsx"));
				XSSFWorkbook wbAnterior = new XSSFWorkbook(input);
				XSSFSheet shAnterior = wbAnterior.getSheetAt(0);
				XSSFRow rowAnterior = null;
				
				for(int i = 1; i <= shAnterior.getLastRowNum(); i++) {
					row = sh.createRow(rowNum);
					rowAnterior = shAnterior.getRow(i);
					
					for (int j = 0; j < rowAnterior.getLastCellNum(); j++) {
						cell = row.createCell(j);
			        	cell.setCellValue(rowAnterior.getCell(j).getStringCellValue());
			        	cell.setCellStyle(bodyStyle);
					}
					
					rowNum++;
				}
				
				input.close();
			}
			
//			sh.autoSizeColumn(0);
//			sh.autoSizeColumn(1);
//			sh.autoSizeColumn(2);
//			sh.autoSizeColumn(3);
//			sh.autoSizeColumn(4);
//			sh.autoSizeColumn(5);
//			sh.autoSizeColumn(6);
//			sh.autoSizeColumn(7);
//			sh.autoSizeColumn(8);
			
			rowNum = 0;
			
			sh = wb.createSheet("Medios - NSS");
			encabezado = ENCABEZADO_NSS.split("\\|");

			row = sh.createRow(rowNum);

			for (int i = 0; i < encabezado.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(encabezado[i]);
				cell.setCellStyle(headerStyle);
			}

			rowNum++;
			
			solicitudes = this.reporteRissServiceEntity.consultarMediosContactoNssRiss();
			
			for (ReporteRissWrapper solicitud : solicitudes) {
				row = sh.createRow(rowNum);

				// NSS
				cell = row.createCell(0);
	        	cell.setCellValue(formatearCampo(solicitud.getNss()));
	        	cell.setCellStyle(bodyStyle);
	        	
	        	// FECHA ALTA
	        	cell = row.createCell(1);
	        	cell.setCellValue(DateUtils.dateToStringConFormato(solicitud.getFecha(), "dd/MM/yyyy"));
	        	cell.setCellStyle(bodyStyle);
	        			        	
	        	// ESTADO BENEFICIO
	        	cell = row.createCell(2);
	        	cell.setCellValue(formatearCampo(solicitud.getEstado()));
	        	cell.setCellStyle(bodyStyle);
	        	
	        	// MEDIO CONTACTO
	        	cell = row.createCell(3);
	        	cell.setCellValue(formatearCampo(solicitud.getDescMedioContacto()));
	        	cell.setCellStyle(bodyStyle);
	        	
	        	// TIPO
	        	cell = row.createCell(4);
	        	cell.setCellValue(formatearCampo(solicitud.getTipoMedioContacto()));
	        	cell.setCellStyle(bodyStyle);
	        		        	
	        	rowNum++;
			}
			
			sh.autoSizeColumn(0);
			sh.autoSizeColumn(1);
			sh.autoSizeColumn(2);
			sh.autoSizeColumn(3);
			sh.autoSizeColumn(4);

			if (mostrarDatosNrp) {
				rowNum = 0;
				
				sh = wb.createSheet("Medios - NRP");
				encabezado = ENCABEZADO_NRP.split("\\|");
	
				row = sh.createRow(rowNum);
	
				for (int i = 0; i < encabezado.length; i++) {
					cell = row.createCell(i);
					cell.setCellValue(encabezado[i]);
					cell.setCellStyle(headerStyle);
				}
	
				rowNum++;
				
				solicitudes = this.reporteRissServiceEntity.consultarMediosDomicilioNrpRiss();
				
				Map<Long, CentroTrabajo> domNrp = new HashMap<Long, CentroTrabajo>();
				CentroTrabajo centroTrabajo = null;
				StringBuffer domicilioCompleto = null;
				
				for (ReporteRissWrapper solicitud : solicitudes) {
					if (!domNrp.containsKey(solicitud.getIdSujOblig().longValue())) {
						centroTrabajo = this.sujetoObligadoServiceBusiness
								.consultarDomicilioCentroTrabajo(solicitud
										.getIdSujOblig().longValue());
						
						domicilioCompleto = new StringBuffer();
						domicilioCompleto.append(centroTrabajo.getVialidadPrimaria().getNombre());
						if (centroTrabajo.getNumExterior1() != null){
							domicilioCompleto.append(" #").append(centroTrabajo.getNumExterior1());
						}
						domicilioCompleto.append(centroTrabajo.getNumExteriorAlf()!= null ? " " + centroTrabajo.getNumExteriorAlf() : "");
						
						if(centroTrabajo.getNumInterior() != null) {
							domicilioCompleto.append(", interior "+centroTrabajo.getNumInterior()+(centroTrabajo.getNumInteriorAlf()!=null ? " "+centroTrabajo.getNumInteriorAlf() : "" ));
						}
						
						domicilioCompleto.append(", COLONIA "+centroTrabajo.getAsentamiento().getNombre());
						domicilioCompleto.append(", ").append(centroTrabajo.getAsentamiento().getLocalidad().getMunicipio().getNombre());
						domicilioCompleto.append(", ").append(centroTrabajo.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre());
						domicilioCompleto.append(", CP "+centroTrabajo.getCodigoPostal().getCodigoPostal());
						
						centroTrabajo.setDescripcion(domicilioCompleto.toString());
						
						domNrp.put(solicitud.getIdSujOblig().longValue(), centroTrabajo);
					}
				}
							
				for (ReporteRissWrapper solicitud : solicitudes) {
					row = sh.createRow(rowNum);
	
					// NRP
					cell = row.createCell(0);
		        	cell.setCellValue(formatearCampo(solicitud.getNrp()));
		        	cell.setCellStyle(bodyStyle);
		        			        	
		        	// MEDIO CONTACTO
		        	cell = row.createCell(1);
		        	cell.setCellValue(formatearCampo(solicitud.getDescMedioContacto()));
		        	cell.setCellStyle(bodyStyle);
		        	
		        	// TIPO
		        	cell = row.createCell(2);
		        	cell.setCellValue(formatearCampo(solicitud.getTipoMedioContacto()));
		        	cell.setCellStyle(bodyStyle);
		        	
		        	// DOMICILIO
		        	cell = row.createCell(3);
		        	cell.setCellValue(formatearCampo(domNrp.get(solicitud.getIdSujOblig().longValue()).getDescripcion()));
		        	cell.setCellStyle(bodyStyle);
		        		       
		        	rowNum++;
				}
				
				sh.autoSizeColumn(0);
				sh.autoSizeColumn(1);
				sh.autoSizeColumn(2);
				sh.autoSizeColumn(3);
			}
			
			String fileName = "RISS_" + DateUtils.dateToStringConFormato(new Date(), "yyyyMMdd") + ".xlsx";
			String fullPathFileName = RUTA_BASE + fileName;
	        FileOutputStream out = new FileOutputStream(fullPathFileName);
	        wb.write(out);
	        out.close();

	        // dispose of temporary files backing this workbook on disk
	        wb.dispose();
	        
	        if (enviarCorreo) {
	        	
	        	List<String> llaves = new ArrayList<String>();
	        	llaves.add(ParametroSistemaEnum.CORREOS_RISS_TO.getCodigo());
	        	llaves.add(ParametroSistemaEnum.CORREOS_RISS_CC.getCodigo());
	        	llaves.add(ParametroSistemaEnum.CORREOS_RISS_BCC.getCodigo());
	        	
				Map<String, String> correosRiss = this.parametrosServiceBusiness
						.obtenerGrupoParametros(llaves);
	        	
	        	String enviarA = correosRiss.get(ParametroSistemaEnum.CORREOS_RISS_TO.getCodigo());
	        	String enviarCc = correosRiss.get(ParametroSistemaEnum.CORREOS_RISS_CC.getCodigo());;
	        	String enviarBcc = correosRiss.get(ParametroSistemaEnum.CORREOS_RISS_BCC.getCodigo());;
	        	
	        	this.log.debug("Correos a enviar correo -> " + enviarA);
	        	this.log.debug("Correos con copia a enviar correo -> " + enviarCc);
	        	this.log.debug("Correos con copia oculta a enviar correo -> " + enviarBcc);
	        	
	        	EmailDataWrapper emailData = new EmailDataWrapper();
	        	emailData.setToAddress(enviarA);
				if (StringUtils.isNotBlank(enviarCc)) {
	        		emailData.setCcAddress(enviarCc);
	        	}
				if (StringUtils.isNotBlank(enviarBcc)) {
	        		emailData.setBccAddress(enviarBcc);
	        	}
	        	emailData.setAsunto("Reporte RISS");
	        	
	        	StringBuffer mensaje = new StringBuffer();
	        	mensaje.append("<p>Buen d&iacute;a,<br><br>");
	        	mensaje.append("Adjunto se env&iacute;a la s&aacute;bana de datos para el reporte ");
	        	mensaje.append("de solicitudes de inscripci&oacute;n al beneficio RISS.<br>");
	        	mensaje.append("<br>Saludos.</p>");
	        	
	        	emailData.setMensaje(mensaje.toString());
	        	
	        	List<String> nombreArchivos = new ArrayList<String>();
	        	List<byte[]> archivos = new ArrayList<byte[]>();
	        	List<String> contentTypeArchivos = new ArrayList<String>();
	        	
	        	nombreArchivos.add(fileName);
	        	
	        	File file = new File(fullPathFileName);
	            FileInputStream fis = new FileInputStream(file);
	            ByteArrayOutputStream bos = new ByteArrayOutputStream();
	            
	            byte[] buf = new byte[1024];
	            try {
	                for (int readNum; (readNum = fis.read(buf)) != -1;) {
	                    bos.write(buf, 0, readNum);
	                }
	                
	                archivos.add(bos.toByteArray());
		        	
		        	contentTypeArchivos.add("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		        	
		        	emailData.setArchivosAdjuntos(archivos);
		        	emailData.setNombreArchivosAdjuntos(nombreArchivos);
		        	emailData.setContentTypeAchivosAdjuntos(contentTypeArchivos);
		        	
		        	this.emailServiceUtility.enviarCorreoAdjuntos(emailData);
	                
	            } catch (IOException ex) {
	                this.log.error(ex);
	            }	        	
	        }
		} catch(IOException e) {
			this.log.error(e);
		}
	}

	private List<ReporteRissWrapper> obtenerDatosReporte(Date fechaInicio,
			Date fechaFin) {
		
		boolean escribirRegistro = false;
		int countError = 0;
		List<ReporteRissWrapper> solicitudes = this.reporteRissServiceEntity
				.consultarSolicitudesRiss(fechaInicio, fechaFin);
		
		this.log.warn("Total de solicitudes consultadas -> " + solicitudes.size());
		
		Map<String, List<ReporteRissWrapper>> solicMap = new HashMap<String, List<ReporteRissWrapper>>();
		
		for(ReporteRissWrapper registro : solicitudes) {
			if (solicMap.get(registro.getFolio()) != null) {
				solicMap.get(registro.getFolio()).add(registro);
			} else {
				solicMap.put(registro.getFolio(), new ArrayList<ReporteRissWrapper>());
				solicMap.get(registro.getFolio()).add(registro);
			}
		}
		
		Iterator<ReporteRissWrapper> it = solicitudes.iterator();
		ReporteRissWrapper solicitud = null;
		
		while(it.hasNext()) {
			solicitud = it.next();
			
			/* 
			 * Se valida que se tenga el NSS directo del XML, si es así
			 * se checa que el NSS traido del cruce con ditAsignacionNSS
			 * sea el mismo que el del XML, de no ser así el registro
			 * no se escribe
			 */
			if (StringUtils.isNotBlank(solicitud.getNrp())) {
				escribirRegistro = true;
			} else {
				if (StringUtils.isNotBlank(solicitud.getNssXML())
						&& StringUtils.isNotBlank(solicitud.getNss())) {
					if (solicitud.getNssXML().compareToIgnoreCase(solicitud.getNss()) == 0) {
						escribirRegistro = true;
					}
				} else {
					escribirRegistro = true;
				}
			}
			
			if (!escribirRegistro) {
				this.log.warn("Registro no escrito -> " + solicitud.toString());
				it.remove();
				countError++;
			}
			
			if (StringUtils.isNotBlank(solicitud.getApartado())
					&& escribirRegistro) {
				/*
				 * Lista de trámites RISS de la misma solicitud, para validar
				 * que el apartado en el XML sea correcto
				 */
				List<ReporteRissWrapper> tramitesRiss = solicMap.get(solicitud.getFolio());
				boolean esApartadoA = false;
				boolean esApartadoB = false;
				boolean esApartadoC = false;
				
				for (ReporteRissWrapper tramite : tramitesRiss) {
					if (StringUtils.isNotBlank(tramite.getNss())
							|| StringUtils.isNotBlank(tramite.getNssXML())) {
						esApartadoA = true;
					} else if (StringUtils.isNotBlank(tramite.getNrp())) {
						esApartadoB = true;
					}
					
					if(tramite.getEsApartadoC()) {
						esApartadoC = true;
					}
				}
				
				String apartadoCalculado = null;
				String apartadoSolicitud = solicitud.getApartado().trim();
				
				if (esApartadoA && !esApartadoB) {
					apartadoCalculado = ApartadoPersonaBeneficioEnum.APARTADO_A.getClave();
				} else if (!esApartadoA && esApartadoB) {
					apartadoCalculado = ApartadoPersonaBeneficioEnum.APARTADO_B.getClave();
				} else {
					apartadoCalculado = ApartadoPersonaBeneficioEnum.APARTADO_AB.getClave();
				}
				
				if (esApartadoC) {
					apartadoCalculado += "-C";
				}
				
				if (!apartadoCalculado.equals(apartadoSolicitud)) {
					this.log.warn("Se cambia el apartado de la solicitud "
							+ solicitud.getFolio() + " de " + apartadoSolicitud
							+ " a " + apartadoCalculado);
					solicitud.setApartado(apartadoCalculado);
				}
			}
			
			escribirRegistro = false;
		}
		
		this.log.warn("Total de solicitudes en reporte -> " + solicitudes.size());
		this.log.warn("Total de solicitudes descartadas -> " + countError);
		
		return solicitudes;
	}
	
	private String getRfc(ReporteRissWrapper solicitud) {
		
		String rfc = null;
		
		if(StringUtils.isBlank(solicitud.getRfcPersona())) {
			if (StringUtils.isBlank(solicitud.getRfcPersonaFisica())) {
				rfc = formatearCampo(solicitud.getRfcXML());
			} else {
				rfc = formatearCampo(solicitud.getRfcPersonaFisica());
			}
		} else {
			rfc = formatearCampo(solicitud.getRfcPersona());
		}
		
		return rfc;
	}
	
	private String getObservaciones(ReporteRissWrapper solicitud) {
		
		String observaciones = null;
		
		if (solicitud.getObservaciones().contains("|")) {
			String[] aux = solicitud.getObservaciones().split("\\|");
			
			for (String tmp : aux) {
				if (tmp.contains("informa")) {
					observaciones = formatearCampo(tmp);
					break;
				} else if (tmp.contains("CodecHandler")) {
					observaciones = "Error inesperado";
					break;
				}
			}
		} else if (solicitud.getObservaciones().contains("CodecHandler")) {
			observaciones = "Error inesperado";
		} else {
			observaciones = formatearCampo(solicitud.getObservaciones());
		}
		
		return observaciones;
	}
	
	private String formatearCampo(String campo) {
		
		if (StringUtils.isBlank(campo)) {
			campo = "";
		} else {
			campo = campo.trim();
		}
		
		return campo;		
	}	
}
