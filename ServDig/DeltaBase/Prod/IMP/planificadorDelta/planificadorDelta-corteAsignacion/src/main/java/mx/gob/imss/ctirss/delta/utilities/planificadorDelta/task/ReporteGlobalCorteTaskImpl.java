package mx.gob.imss.ctirss.delta.utilities.planificadorDelta.task;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio.ConsultaAltaPatronal;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio.ConsultaAltaRissAsegurado;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio.ConsultaAltaRissPatronal;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio.ConsultaAsignacion;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio.ConsultaCambioClinica;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio.ConsultaClasificacion;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReporteGlobalCorteTaskImpl implements ReporteGlobalCorteTask {
	private SimpleDateFormat sdfh = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	@Override
	public XSSFWorkbook crearLibroExcel(String excelFileName) {
		XSSFWorkbook wb = new XSSFWorkbook();
		return wb;
	}

	@Override
	public void crearTitulosTablasCortes(XSSFWorkbook workbook) {
		// Asignacion de NSS
		List<String> lstTituloTablaAsignacion = new ArrayList<String>();
		lstTituloTablaAsignacion.add("REF_FOLIO_SOL");
		lstTituloTablaAsignacion.add("CVE_ID_PERSONA_ASIG");
		lstTituloTablaAsignacion.add("NUM_NSS");
		lstTituloTablaAsignacion.add("CVE_ID_ASIGNACION_NSS");
		lstTituloTablaAsignacion.add("CURP");
		lstTituloTablaAsignacion.add("FEC_SOLICITUD");
		lstTituloTablaAsignacion.add("FEC_CONCLUSION_SOL");
		lstTituloTablaAsignacion.add("FEC_REGISTRO_ALTA_ASIG");
		lstTituloTablaAsignacion.add("FEC_REGISTRO_BAJA_ASIG");
		lstTituloTablaAsignacion.add("FEC_REGISTRO_ACTUALIZADO_ASIG");
		lstTituloTablaAsignacion.add("LOCALIZADO");
		lstTituloTablaAsignacion.add("REPETIDO");

		List<String> lstTituloCompAsignacion = new ArrayList<String>();
		lstTituloCompAsignacion.add("NSS_ARCHIVO_LAYOUT");
		lstTituloCompAsignacion.add("FRECUENCIA_BDTU");
		lstTituloCompAsignacion.add("NO_EN_BDTU");
		lstTituloCompAsignacion.add("NO_EN_ARCHIVO");
		crearHojaCorte(workbook, "CorteAsignacion", lstTituloTablaAsignacion,
				lstTituloCompAsignacion);

		// Alta Patronal
		List<String> lstTituloTablaAltaPat = new ArrayList<String>();
		lstTituloTablaAltaPat.add("CVE_CIZ");
		lstTituloTablaAltaPat.add("REF_FOLIO");
		lstTituloTablaAltaPat.add("CVE_ID_PATRON_SUJETO_OBLIGADO");
		lstTituloTablaAltaPat.add("REG_PATRON");
		lstTituloTablaAltaPat.add("NUM_MODALIDAD");
		lstTituloTablaAltaPat.add("DIG_VER");
		lstTituloTablaAltaPat.add("FEC_SOLICITUD");
		lstTituloTablaAltaPat.add("FEC_CONCLUSION");
		lstTituloTablaAltaPat.add("FEC_REGISTRO_ALTA");
		lstTituloTablaAltaPat.add("FEC_REGISTRO_BAJA");
		lstTituloTablaAltaPat.add("FEC_REGISTRO_ACTUALIZADO");
		lstTituloTablaAltaPat.add("REGPAT_COMPLETO");
		lstTituloTablaAltaPat.add("LOCALIZADO");

		List<String> lstTituloCompAltaPat = new ArrayList<String>();
		lstTituloCompAltaPat.add("REG_PAT_ARCHIVO_LAYOUT");
		lstTituloCompAltaPat.add("FRECUENCIA_BDTU");
		crearHojaCorte(workbook, "CorteAltaPatronal", lstTituloTablaAltaPat,
				lstTituloCompAltaPat);

		// Movimientos de Clasificacion
		List<String> lstTituloTablaClasificacion = new ArrayList<String>();
		lstTituloTablaClasificacion.add("CVE_ID_TRAMITE");
		lstTituloTablaClasificacion.add("CVE_CIZ");
		lstTituloTablaClasificacion.add("REF_FOLIO");
		lstTituloTablaClasificacion.add("DES_TIPO_TRAMITE");
		lstTituloTablaClasificacion.add("CVE_ID_PATRON_SUJETO_OBLIGADO");
		lstTituloTablaClasificacion.add("REG_PATRON");
		lstTituloTablaClasificacion.add("NUM_MODALIDAD");
		lstTituloTablaClasificacion.add("DIG_VER");
		lstTituloTablaClasificacion.add("FEC_REGISTRO_ALTA");
		lstTituloTablaClasificacion.add("FEC_REGISTRO_BAJA");
		lstTituloTablaClasificacion.add("FEC_REGISTRO_ACTUALIZADO");
		lstTituloTablaClasificacion.add("FEC_PRESENTACION_TRAM");
		lstTituloTablaClasificacion.add("FEC_REGISTRO_ALTA_TRAM");
		lstTituloTablaClasificacion.add("FEC_REGISTRO_ACTUALIZADO_TRAM");
		lstTituloTablaClasificacion.add("FEC_EFECTO_TRAM");
		lstTituloTablaClasificacion.add("REGPAT_COMPLETO");
		lstTituloTablaClasificacion.add("LOCALIZADO");

		List<String> lstTituloCompClasificacion = new ArrayList<String>();
		lstTituloCompClasificacion.add("REG_PAT_ARCHIVO_LAYOUT");
		lstTituloCompClasificacion.add("FRECUENCIA_BDTU");
		crearHojaCorte(workbook, "CorteMovClasificacion",
				lstTituloTablaClasificacion, lstTituloCompClasificacion);

		// Alta al RISS (Asegurados)
		List<String> lstTituloTablaAltaRissAseg = new ArrayList<String>();
		lstTituloTablaAltaRissAseg.add("REF_FOLIO");
		lstTituloTablaAltaRissAseg.add("RFC");
		lstTituloTablaAltaRissAseg.add("CURP");
		lstTituloTablaAltaRissAseg.add("FEC_SOLICITUD");
		lstTituloTablaAltaRissAseg.add("FEC_CONCLUSION");
		lstTituloTablaAltaRissAseg.add("FEC_REGISTRO_ALTA_BEN");
		lstTituloTablaAltaRissAseg.add("FEC_REGISTRO_BAJA_BEN");
		lstTituloTablaAltaRissAseg.add("FEC_REGISTRO_ACTUALIZADO_BEN");
		lstTituloTablaAltaRissAseg.add("DES_TIPO_BENEFICIO");
		lstTituloTablaAltaRissAseg.add("DES_ESTADO_BENEFICIO");
		lstTituloTablaAltaRissAseg.add("FEC_INICIO_VIGENCIA");
		lstTituloTablaAltaRissAseg.add("FEC_FIN_VIGENCIA");
		lstTituloTablaAltaRissAseg.add("LOCALIZADO");

		List<String> lstTituloCompAltaRissAseg = new ArrayList<String>();
		lstTituloCompAltaRissAseg.add("RFC_ARCHIVO_LAYOUT");
		lstTituloCompAltaRissAseg.add("CURP_ARCHIVO_LAYOUT");
		lstTituloCompAltaRissAseg.add("FREC_RFC_BDTU");
		lstTituloCompAltaRissAseg.add("FREC_CURP_BDTU");
		lstTituloCompAltaRissAseg.add("FRECUENCIA_BDTU");
		crearHojaCorte(workbook, "CorteAltaRiss(Aseg)",
				lstTituloTablaAltaRissAseg, lstTituloCompAltaRissAseg);

		// Alta al RISS (Patronales)
		List<String> lstTituloTablaAltaRissPat = new ArrayList<String>();
		lstTituloTablaAltaRissPat.add("REF_FOLIO");
		lstTituloTablaAltaRissPat.add("RFC");
		lstTituloTablaAltaRissPat.add("CURP");
		lstTituloTablaAltaRissPat.add("CVE_ID_PATRON_SUJETO_OBLIGADO");
		lstTituloTablaAltaRissPat.add("REG_PATRON");
		lstTituloTablaAltaRissPat.add("NUM_MODALIDAD");
		lstTituloTablaAltaRissPat.add("DIG_VER");
		lstTituloTablaAltaRissPat.add("FEC_SOLICITUD");
		lstTituloTablaAltaRissPat.add("FEC_CONCLUSION");
		lstTituloTablaAltaRissPat.add("FEC_REGISTRO_ALTA_BEN");
		lstTituloTablaAltaRissPat.add("FEC_REGISTRO_BAJA_BEN");
		lstTituloTablaAltaRissPat.add("FEC_REGISTRO_ACTUALIZADO_BEN");
		lstTituloTablaAltaRissPat.add("DES_TIPO_BENEFICIO");
		lstTituloTablaAltaRissPat.add("DES_ESTADO_BENEFICIO");
		lstTituloTablaAltaRissPat.add("FEC_INICIO_VIGENCIA");
		lstTituloTablaAltaRissPat.add("FEC_FIN_VIGENCIA");
		lstTituloTablaAltaRissPat.add("REGPAT_COMPLETO");
		lstTituloTablaAltaRissPat.add("LOCALIZADO");

		List<String> lstTituloCompAltaRissPat = new ArrayList<String>();
		lstTituloCompAltaRissPat.add("REG_PAT_ARCHIVO_LAYOUT");
		lstTituloCompAltaRissPat.add("FRECUENCIA_BDTU");
		crearHojaCorte(workbook, "CorteAltaRiss(Pat)",
				lstTituloTablaAltaRissPat, lstTituloCompAltaRissPat);

		// Cambio de Clinica
		List<String> lstTituloTablaCambioClinica = new ArrayList<String>();
		lstTituloTablaCambioClinica.add("CVE_CIZ");
		lstTituloTablaCambioClinica.add("REF_FOLIO");
		lstTituloTablaCambioClinica.add("DES_TIPO_SOLICITUD");
		lstTituloTablaCambioClinica.add("CVE_ID_PERSONA");
		lstTituloTablaCambioClinica.add("NUM_NSS");
		lstTituloTablaCambioClinica.add("CVE_ID_ASIGNACION_NSS");
		lstTituloTablaCambioClinica.add("CURP");
		lstTituloTablaCambioClinica.add("FEC_SOLICITUD");
		lstTituloTablaCambioClinica.add("FEC_CONCLUSION");
		lstTituloTablaCambioClinica.add("LOCALIZADO");

		List<String> lstTituloCompCambioClinica = new ArrayList<String>();
		lstTituloCompCambioClinica.add("CURP_ARCHIVO_LAYOUT");
		lstTituloCompCambioClinica.add("FRECUENCIA_BDTU");
		crearHojaCorte(workbook, "CorteCambioClinica",
				lstTituloTablaCambioClinica, lstTituloCompCambioClinica);

		crearHojaCorteFinal(workbook);

	}

	private void crearHojaCorte(XSSFWorkbook workbook, String tituloHoja,
			List<String> lstTituloTablaConsulta,
			List<String> lstTituloTablaCamparacion) {
		// Se creao hoja de Trabajo
		XSSFSheet sheetCorte = workbook.createSheet(tituloHoja);

		// Encabezado
		XSSFCellStyle tituloCellStyle = definirEstiloCeldaTitulo(workbook, 0);
		XSSFRow headrow = sheetCorte.createRow(0);

		// Tabla de Consulta Bdtu
		int index = 0;
		for (String tituloTablaConsulta : lstTituloTablaConsulta) {
			XSSFCell cell = headrow.createCell(index);
			cell.setCellStyle(tituloCellStyle);
			cell.setCellValue(tituloTablaConsulta);

			index++;
		}

		// Tabla de Comparacion
		XSSFCellStyle tituloSecCellStyle = definirEstiloCeldaTitulo(workbook, 1);
		int indexComp = 1;
		for (String tituloTablaCamparacion : lstTituloTablaCamparacion) {
			XSSFCell cell = headrow.createCell(index + indexComp);
			cell.setCellStyle(tituloSecCellStyle);
			cell.setCellValue(tituloTablaCamparacion);

			indexComp++;
		}

		// Ajuste de Tamanio para las columnas
		for (int indexResize = 0; indexResize <= index + indexComp; indexResize++) {
			sheetCorte.autoSizeColumn(indexResize);
		}

	}

	private XSSFCellStyle definirEstiloCeldaTitulo(XSSFWorkbook workbook,
			int tipoTitulo) {
		XSSFCellStyle tituloCellStyle = workbook.createCellStyle();

		// Fondo
		if (tipoTitulo == 0) {
			tituloCellStyle.setFillForegroundColor(new XSSFColor(BLACK_COLOR));
		} else {
			tituloCellStyle.setFillForegroundColor(new XSSFColor(GREEN_COLOR));
		}
		tituloCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

		// Tipo de Letra
		XSSFFont tituloFont = workbook.createFont();
		tituloFont.setBold(true);
		tituloFont.setColor(new XSSFColor(WHITE_COLOR));
		tituloCellStyle.setFont(tituloFont);

		return tituloCellStyle;
	}

	@Override
	public void crearHojaCorteFinal(XSSFWorkbook workbook) {
		// Se creao hoja de Trabajo
		XSSFSheet sheetCorteFinal = workbook.createSheet("CorteFinal");
		sheetCorteFinal.setSelected(true);
		sheetCorteFinal.setTabColor(12);

		List<String> lstTituloTablaCorte = new ArrayList<String>();
		lstTituloTablaCorte.add("OPERACION");
		lstTituloTablaCorte.add("CIZ/ARCHIVO");
		lstTituloTablaCorte.add("FRECUENCIA");
		lstTituloTablaCorte.add("DIFERENCIA");

		// Encabezado
		XSSFCellStyle tituloCellStyle = definirEstiloCeldaTitulo(workbook, 0);
		XSSFRow headrow = sheetCorteFinal.createRow(0);

		// Tabla de Corte Final
		int index = 0;

		for (String tituloTablaCorte : lstTituloTablaCorte) {
			XSSFCell cell = headrow.createCell(index);
			cell.setCellStyle(tituloCellStyle);
			cell.setCellValue(tituloTablaCorte);

			index++;
		}

		for (int indexResize = 0; indexResize <= index; indexResize++) {
			sheetCorteFinal.autoSizeColumn(indexResize);
		}
	}

	@Override
	public void crearArchivoExcel(String excelFileName, XSSFWorkbook workbook)
			throws IOException {
		FileOutputStream fileOut = new FileOutputStream(excelFileName);

		// write this workbook to an Outputstream.
		workbook.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}

	@Override
	public void colocarCorteBdtuAsignacion(XSSFWorkbook workbook,
			List<ConsultaAsignacion> listConsultaAsignacion) {
		// Se obtiene hoja de Trabajo
		XSSFSheet sheetCorte = workbook.getSheet("CorteAsignacion");

		int index = 1;
		int indexCol = 0;
		for (ConsultaAsignacion consultaAsignacion : listConsultaAsignacion) {
			XSSFRow headrow = sheetCorte.createRow(index);

			indexCol = 0;
			XSSFCell cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaAsignacion.getFolioSolicitud());

			cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaAsignacion.getCveIdPersonaAsig());

			cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaAsignacion.getNumNss());

			cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaAsignacion.getCveIdAsignacionNss());

			cell = headrow.createCell(indexCol++);
			if (StringUtils.isNotBlank(consultaAsignacion.getCurp())) {
				cell.setCellValue(consultaAsignacion.getCurp());
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (consultaAsignacion.getFecSolicitud() != null) {
				cell.setCellValue(sdfh.format(consultaAsignacion
						.getFecSolicitud()));
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (consultaAsignacion.getFecConclusionSol() != null) {
				cell.setCellValue(sdfh.format(consultaAsignacion
						.getFecConclusionSol()));
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (consultaAsignacion.getFecRegistroAltaAsig() != null) {
				cell.setCellValue(sdfh.format(consultaAsignacion
						.getFecRegistroAltaAsig()));
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (consultaAsignacion.getFecRegistroBajaAsig() != null) {
				cell.setCellValue(sdfh.format(consultaAsignacion
						.getFecRegistroBajaAsig()));
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (consultaAsignacion.getFecRegistroActualizadoAsig() != null) {
				cell.setCellValue(sdfh.format(consultaAsignacion
						.getFecRegistroActualizadoAsig()));
			} else {
				cell.setCellValue("");
			}

			index++;
		}

		for (int indexResize = 0; indexResize <= indexCol; indexResize++) {
			sheetCorte.autoSizeColumn(indexResize);
		}
	}

	@Override
	public void colocarCorteBdtuAltaPatronal(XSSFWorkbook workbook,
			List<ConsultaAltaPatronal> listConsultaAltaPatronal) {
		// Se obtiene hoja de Trabajo
		XSSFSheet sheetCorte = workbook.getSheet("CorteAltaPatronal");

		int index = 1;
		int indexCol = 0;

		for (ConsultaAltaPatronal consultaAltaPatronal : listConsultaAltaPatronal) {
			XSSFRow headrow = sheetCorte.createRow(index);

			indexCol = 0;
			XSSFCell cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaAltaPatronal.getCveCiz());

			cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaAltaPatronal.getFolioSolicitud());

			cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaAltaPatronal
					.getCveIdPatronSujetoObligado());

			cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaAltaPatronal.getRegPatron());

			cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaAltaPatronal.getNumModalidad());

			cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaAltaPatronal.getDigVer());

			cell = headrow.createCell(indexCol++);
			if (consultaAltaPatronal.getFecSolicitud() != null) {
				cell.setCellValue(sdfh.format(consultaAltaPatronal
						.getFecSolicitud()));
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (consultaAltaPatronal.getFecConclusionSol() != null) {
				cell.setCellValue(sdfh.format(consultaAltaPatronal
						.getFecConclusionSol()));
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (consultaAltaPatronal.getFecRegistroAlta() != null) {
				cell.setCellValue(sdfh.format(consultaAltaPatronal
						.getFecRegistroAlta()));
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (consultaAltaPatronal.getFecRegistroBaja() != null) {
				cell.setCellValue(sdfh.format(consultaAltaPatronal
						.getFecRegistroBaja()));
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (consultaAltaPatronal.getFecRegistroActualizado() != null) {
				cell.setCellValue(sdfh.format(consultaAltaPatronal
						.getFecRegistroActualizado()));
			} else {
				cell.setCellValue("");
			}

			index++;
		}

		for (int indexResize = 0; indexResize <= indexCol; indexResize++) {
			sheetCorte.autoSizeColumn(indexResize);
		}
	}

	@Override
	public void colocarCorteBdtuClasificacion(XSSFWorkbook workbook,
			List<ConsultaClasificacion> listConsultaClasificacion) {
		// Se obtiene hoja de Trabajo
		XSSFSheet sheetCorte = workbook.getSheet("CorteMovClasificacion");

		int index = 1;
		int indexCol = 0;

		for (ConsultaClasificacion consultaClasificacion : listConsultaClasificacion) {
			XSSFRow headrow = sheetCorte.createRow(index);

			indexCol = 0;
			XSSFCell cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaClasificacion.getCveIdTramite());

			cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaClasificacion.getCveCiz());

			cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaClasificacion.getFolioSolicitud());

			cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaClasificacion.getDescripcionTramite());

			cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaClasificacion
					.getCveIdPatronSujetoObligado());

			cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaClasificacion.getRegPatron());

			cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaClasificacion.getNumModalidad());

			cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaClasificacion.getDigVer());

			cell = headrow.createCell(indexCol++);
			if (consultaClasificacion.getFecRegistroAlta() != null) {
				cell.setCellValue(sdfh.format(consultaClasificacion
						.getFecRegistroAlta()));
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (consultaClasificacion.getFecRegistroBaja() != null) {
				cell.setCellValue(sdfh.format(consultaClasificacion
						.getFecRegistroBaja()));
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (consultaClasificacion.getFecRegistroActualizado() != null) {
				cell.setCellValue(sdfh.format(consultaClasificacion
						.getFecRegistroActualizado()));
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (consultaClasificacion.getFecPresentacionTram() != null) {
				cell.setCellValue(sdfh.format(consultaClasificacion
						.getFecPresentacionTram()));
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (consultaClasificacion.getFecRegistroAltaTram() != null) {
				cell.setCellValue(sdfh.format(consultaClasificacion
						.getFecRegistroAltaTram()));
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (consultaClasificacion.getFecRegistroActualizadoTram() != null) {
				cell.setCellValue(sdfh.format(consultaClasificacion
						.getFecRegistroActualizadoTram()));
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (consultaClasificacion.getFecEfectoTram() != null) {
				cell.setCellValue(sdfh.format(consultaClasificacion
						.getFecEfectoTram()));
			} else {
				cell.setCellValue("");
			}

			index++;
		}

		for (int indexResize = 0; indexResize <= indexCol; indexResize++) {
			sheetCorte.autoSizeColumn(indexResize);
		}
	}

	@Override
	public void colocarCorteBdtuAltaRissAseg(XSSFWorkbook workbook,
			List<ConsultaAltaRissAsegurado> listConsultaAltaRissAsegurado) {
		// Se obtiene hoja de Trabajo
		XSSFSheet sheetCorte = workbook.getSheet("CorteAltaRiss(Aseg)");

		int index = 1;
		int indexCol = 0;

		for (ConsultaAltaRissAsegurado consultaAltaRissAsegurado : listConsultaAltaRissAsegurado) {
			XSSFRow headrow = sheetCorte.createRow(index);

			indexCol = 0;
			XSSFCell cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaAltaRissAsegurado.getFolioSolicitud());

			cell = headrow.createCell(indexCol++);
			if (StringUtils.isNotBlank(consultaAltaRissAsegurado.getRfc())) {
				cell.setCellValue(consultaAltaRissAsegurado.getRfc());
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (StringUtils.isNotBlank(consultaAltaRissAsegurado.getCurp())) {
				cell.setCellValue(consultaAltaRissAsegurado.getCurp());
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (consultaAltaRissAsegurado.getFecSolicitud() != null) {
				cell.setCellValue(sdfh.format(consultaAltaRissAsegurado
						.getFecSolicitud()));
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (consultaAltaRissAsegurado.getFecConclusionSol() != null) {
				cell.setCellValue(sdfh.format(consultaAltaRissAsegurado
						.getFecConclusionSol()));
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (consultaAltaRissAsegurado.getFecRegistroAltaBen() != null) {
				cell.setCellValue(sdfh.format(consultaAltaRissAsegurado
						.getFecRegistroAltaBen()));
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (consultaAltaRissAsegurado.getFecRegistroBajaBen() != null) {
				cell.setCellValue(sdfh.format(consultaAltaRissAsegurado
						.getFecRegistroBajaBen()));
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (consultaAltaRissAsegurado.getFecRegistroActualizadoBen() != null) {
				cell.setCellValue(sdfh.format(consultaAltaRissAsegurado
						.getFecRegistroActualizadoBen()));
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaAltaRissAsegurado.getDesTipoBeneficio());

			cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaAltaRissAsegurado.getDesEstadoBeneficio());

			cell = headrow.createCell(indexCol++);
			if (consultaAltaRissAsegurado.getFecInicioVigencia() != null) {
				cell.setCellValue(sdfh.format(consultaAltaRissAsegurado
						.getFecInicioVigencia()));
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (consultaAltaRissAsegurado.getFecFinVigencia() != null) {
				cell.setCellValue(sdfh.format(consultaAltaRissAsegurado
						.getFecFinVigencia()));
			} else {
				cell.setCellValue("");
			}

			index++;
		}

		for (int indexResize = 0; indexResize <= indexCol; indexResize++) {
			sheetCorte.autoSizeColumn(indexResize);
		}
	}

	@Override
	public void colocarCorteBdtuAltaRissPat(XSSFWorkbook workbook,
			List<ConsultaAltaRissPatronal> listConsultaAltaRissPatronal) {
		// Se obtiene hoja de Trabajo
		XSSFSheet sheetCorte = workbook.getSheet("CorteAltaRiss(Pat)");

		int index = 1;
		int indexCol = 0;

		for (ConsultaAltaRissPatronal consultaAltaRissPatronal : listConsultaAltaRissPatronal) {
			XSSFRow headrow = sheetCorte.createRow(index);

			indexCol = 0;
			XSSFCell cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaAltaRissPatronal.getFolioSolicitud());

			cell = headrow.createCell(indexCol++);
			if (StringUtils.isNotBlank(consultaAltaRissPatronal.getRfc())) {
				cell.setCellValue(consultaAltaRissPatronal.getRfc());
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (StringUtils.isNotBlank(consultaAltaRissPatronal.getCurp())) {
				cell.setCellValue(consultaAltaRissPatronal.getCurp());
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaAltaRissPatronal
					.getCveIdPatronSujetoObligado());

			cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaAltaRissPatronal.getRegPatron());

			cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaAltaRissPatronal.getNumModalidad());

			cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaAltaRissPatronal.getDigVer());

			cell = headrow.createCell(indexCol++);
			if (consultaAltaRissPatronal.getFecSolicitud() != null) {
				cell.setCellValue(sdfh.format(consultaAltaRissPatronal
						.getFecSolicitud()));
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (consultaAltaRissPatronal.getFecConclusionSol() != null) {
				cell.setCellValue(sdfh.format(consultaAltaRissPatronal
						.getFecConclusionSol()));
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (consultaAltaRissPatronal.getFecRegistroAltaBen() != null) {
				cell.setCellValue(sdfh.format(consultaAltaRissPatronal
						.getFecRegistroAltaBen()));
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (consultaAltaRissPatronal.getFecRegistroBajaBen() != null) {
				cell.setCellValue(sdfh.format(consultaAltaRissPatronal
						.getFecRegistroBajaBen()));
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (consultaAltaRissPatronal.getFecRegistroActualizadoBen() != null) {
				cell.setCellValue(sdfh.format(consultaAltaRissPatronal
						.getFecRegistroActualizadoBen()));
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaAltaRissPatronal.getDesTipoBeneficio());

			cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaAltaRissPatronal.getDesEstadoBeneficio());

			cell = headrow.createCell(indexCol++);
			if (consultaAltaRissPatronal.getFecInicioVigencia() != null) {
				cell.setCellValue(sdfh.format(consultaAltaRissPatronal
						.getFecInicioVigencia()));
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (consultaAltaRissPatronal.getFecFinVigencia() != null) {
				cell.setCellValue(sdfh.format(consultaAltaRissPatronal
						.getFecFinVigencia()));
			} else {
				cell.setCellValue("");
			}

			index++;
		}

		for (int indexResize = 0; indexResize <= indexCol; indexResize++) {
			sheetCorte.autoSizeColumn(indexResize);
		}
	}

	@Override
	public void colocarCorteBdtuCambioClinica(XSSFWorkbook workbook,
			List<ConsultaCambioClinica> listConsultaCambioClinica) {
		// Se obtiene hoja de Trabajo
		XSSFSheet sheetCorte = workbook.getSheet("CorteCambioClinica");

		int index = 1;
		int indexCol = 0;

		for (ConsultaCambioClinica consultaCambioClinica : listConsultaCambioClinica) {
			XSSFRow headrow = sheetCorte.createRow(index);

			indexCol = 0;
			XSSFCell cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaCambioClinica.getCveCiz());

			cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaCambioClinica.getFolioSolicitud());

			cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaCambioClinica.getDesTipoSolicitud());

			cell = headrow.createCell(indexCol++);
			cell.setCellValue(consultaCambioClinica.getCveIdPersonaAsig());

			cell = headrow.createCell(indexCol++);
			if (StringUtils.isNotBlank(consultaCambioClinica.getNumNss())) {
				cell.setCellValue(consultaCambioClinica.getNumNss());
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (consultaCambioClinica.getCveIdAsignacionNss() != null) {
				cell.setCellValue(consultaCambioClinica.getCveIdAsignacionNss());
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (StringUtils.isNotBlank(consultaCambioClinica.getCurp())) {
				cell.setCellValue(consultaCambioClinica.getCurp());
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (consultaCambioClinica.getFecSolicitud() != null) {
				cell.setCellValue(sdfh.format(consultaCambioClinica
						.getFecSolicitud()));
			} else {
				cell.setCellValue("");
			}

			cell = headrow.createCell(indexCol++);
			if (consultaCambioClinica.getFecConclusionSol() != null) {
				cell.setCellValue(sdfh.format(consultaCambioClinica
						.getFecConclusionSol()));
			} else {
				cell.setCellValue("");
			}

			index++;
		}

		for (int indexResize = 0; indexResize <= indexCol; indexResize++) {
			sheetCorte.autoSizeColumn(indexResize);
		}
	}
}
