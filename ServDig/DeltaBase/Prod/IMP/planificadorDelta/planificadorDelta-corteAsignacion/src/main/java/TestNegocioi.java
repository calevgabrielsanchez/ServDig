import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio.ConsultaAltaPatronal;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio.ConsultaAltaRissAsegurado;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio.ConsultaAltaRissPatronal;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio.ConsultaAsignacion;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio.ConsultaCambioClinica;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio.ConsultaClasificacion;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.task.ReporteGlobalCorteTask;
import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.task.ReporteGlobalCorteTaskImpl;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestNegocioi {
	public static void main(String[] args) throws IOException {
		ReporteGlobalCorteTask reporteGlobalCorteTask = new ReporteGlobalCorteTaskImpl();
		String excelFileName = "/mtfs/Test.xlsx";
		XSSFWorkbook workbook = reporteGlobalCorteTask
				.crearLibroExcel(excelFileName);
		reporteGlobalCorteTask.crearTitulosTablasCortes(workbook);

		// Consulta Asegurados
		List<ConsultaAsignacion> listConsultaAsignacion = crearListaAsegurados();
		reporteGlobalCorteTask.colocarCorteBdtuAsignacion(workbook,
				listConsultaAsignacion);

		// Consulta Alta Patronal
		List<ConsultaAltaPatronal> listConsultaAltaPatronal = crearListaPatrones();
		reporteGlobalCorteTask.colocarCorteBdtuAltaPatronal(workbook,
				listConsultaAltaPatronal);

		// Consulta Clasificacion
		List<ConsultaClasificacion> listConsultaClasificacion = new ArrayList<ConsultaClasificacion>();
		reporteGlobalCorteTask.colocarCorteBdtuClasificacion(workbook,
				listConsultaClasificacion);

		// Consulta Alta Riss Asegurados
		List<ConsultaAltaRissAsegurado> listConsultaAltaRissAsegurado = new ArrayList<ConsultaAltaRissAsegurado>();
		reporteGlobalCorteTask.colocarCorteBdtuAltaRissAseg(workbook,
				listConsultaAltaRissAsegurado);

		// Consulta Alta Riss Patronal
		List<ConsultaAltaRissPatronal> listConsultaAltaRissPatronal = new ArrayList<ConsultaAltaRissPatronal>();
		reporteGlobalCorteTask.colocarCorteBdtuAltaRissPat(workbook,
				listConsultaAltaRissPatronal);

		// Consulta Cambio de Clinica
		List<ConsultaCambioClinica> listConsultaCambioClinica = new ArrayList<ConsultaCambioClinica>();
		reporteGlobalCorteTask.colocarCorteBdtuCambioClinica(workbook,
				listConsultaCambioClinica);

		reporteGlobalCorteTask.crearArchivoExcel(excelFileName, workbook);
	}

	private static List<ConsultaAsignacion> crearListaAsegurados() {
		List<ConsultaAsignacion> listConsultaAsignacion = new ArrayList<ConsultaAsignacion>();

		ConsultaAsignacion aseguradoUno = new ConsultaAsignacion();
		aseguradoUno.setFolioSolicitud("14163298345464876863");
		aseguradoUno.setCveIdPersonaAsig(141596761L);
		aseguradoUno.setNumNss("76149000689");
		aseguradoUno.setCveIdAsignacionNss(89348213L);
		aseguradoUno.setCurp("GOTM900213HCSDRN02");
		aseguradoUno.setFecSolicitud(new Date());
		aseguradoUno.setFecConclusionSol(new Date());
		aseguradoUno.setFecRegistroActualizadoAsig(new Date());
		listConsultaAsignacion.add(aseguradoUno);

		ConsultaAsignacion aseguradoDos = new ConsultaAsignacion();
		aseguradoDos.setFolioSolicitud("14163324187364878577");
		aseguradoDos.setCveIdPersonaAsig(141597868L);
		aseguradoDos.setNumNss("76139800015");
		aseguradoDos.setCveIdAsignacionNss(89348341L);
		aseguradoDos.setCurp("LEAC980913MBCDCR06");
		aseguradoDos.setFecSolicitud(new Date());
		aseguradoDos.setFecConclusionSol(new Date());
		aseguradoDos.setFecRegistroActualizadoAsig(new Date());
		listConsultaAsignacion.add(aseguradoDos);

		ConsultaAsignacion aseguradoTres = new ConsultaAsignacion();
		aseguradoTres.setFolioSolicitud("14163660047284894847");
		aseguradoTres.setCveIdPersonaAsig(141603832L);
		aseguradoTres.setNumNss("05149774779");
		aseguradoTres.setCveIdAsignacionNss(89347972L);
		aseguradoTres.setCurp("RAHF970917HSPMRR09");
		aseguradoTres.setFecSolicitud(new Date());
		aseguradoTres.setFecConclusionSol(new Date());
		aseguradoTres.setFecRegistroActualizadoAsig(new Date());
		listConsultaAsignacion.add(aseguradoTres);

		return listConsultaAsignacion;
	}

	private static List<ConsultaAltaPatronal> crearListaPatrones() {
		List<ConsultaAltaPatronal> listConsultaAltaPatronal = new ArrayList<ConsultaAltaPatronal>();

		ConsultaAltaPatronal patronUno = new ConsultaAltaPatronal();
		patronUno.setCveCiz(3);
		patronUno.setFolioSolicitud("14159280862994821127");
		patronUno.setCveIdPatronSujetoObligado(6899323L);
		patronUno.setRegPatron("R1271337");
		patronUno.setNumModalidad("10");
		patronUno.setDigVer("4");
		patronUno.setFecSolicitud(new Date());
		patronUno.setFecConclusionSol(new Date());
		patronUno.setFecRegistroAlta(new Date());
		patronUno.setFecRegistroActualizado(new Date());
		listConsultaAltaPatronal.add(patronUno);

		return listConsultaAltaPatronal;
	}
}
