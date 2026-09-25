package mx.gob.imss.ctirss.delta.utilities.planificadorDelta.task;

import java.io.IOException;
import java.util.List;

import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio.ConsultaAltaPatronal;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio.ConsultaAltaRissAsegurado;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio.ConsultaAltaRissPatronal;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio.ConsultaAsignacion;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio.ConsultaCambioClinica;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio.ConsultaClasificacion;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface ReporteGlobalCorteTask {
	java.awt.Color BLACK_COLOR = new java.awt.Color(0, 0, 0);
	java.awt.Color WHITE_COLOR = new java.awt.Color(255, 255, 254);
	java.awt.Color GREEN_COLOR = new java.awt.Color(0, 128, 0);

	XSSFWorkbook crearLibroExcel(String excelFileName);

	void crearTitulosTablasCortes(XSSFWorkbook workbook);

	void crearHojaCorteFinal(XSSFWorkbook workbook);

	void crearArchivoExcel(String excelFileName, XSSFWorkbook woorkbook)
			throws IOException;

	void colocarCorteBdtuAsignacion(XSSFWorkbook workbook,
			List<ConsultaAsignacion> listConsultaAsignacion);

	void colocarCorteBdtuAltaPatronal(XSSFWorkbook workbook,
			List<ConsultaAltaPatronal> listConsultaAltaPatronal);

	void colocarCorteBdtuClasificacion(XSSFWorkbook workbook,
			List<ConsultaClasificacion> listConsultaClasificacion);

	void colocarCorteBdtuAltaRissAseg(XSSFWorkbook workbook,
			List<ConsultaAltaRissAsegurado> listConsultaAltaRissAsegurado);

	void colocarCorteBdtuAltaRissPat(XSSFWorkbook workbook,
			List<ConsultaAltaRissPatronal> listConsultaAltaRissPatronal);

	void colocarCorteBdtuCambioClinica(XSSFWorkbook workbook,
			List<ConsultaCambioClinica> listConsultaCambioClinica);
}
