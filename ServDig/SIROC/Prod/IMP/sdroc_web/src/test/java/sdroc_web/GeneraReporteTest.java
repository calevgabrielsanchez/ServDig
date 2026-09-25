/**
 * 
 */
package sdroc_web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.imss.csdiss.sdroc.dto.InformacionObraDTO;
import mx.gob.imss.csdiss.sdroc.service.ConsultaObraService;
import mx.gob.imss.csdiss.sdroc.service.ConsultaObraServiceImpl;
import mx.gob.imss.csdiss.sdroc.service.RegistroObraService;
import mx.gob.imss.csdiss.sdroc.service.RegistroObraServiceImpl;
import mx.gob.imss.csdiss.sdroc.util.CargarParametrosReporte;
import mx.gob.imss.csdiss.sdroc.util.GeneraReporte;
import mx.gob.imss.csdiss.sdroc.util.ReporteEnum;

/**
 * @author daniel.hernandez
 * 
 */
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class GeneraReporteTest {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {


		try {
//			generaReporteCancelacion();
			generaReporteRegistroPatronal();
//			generaReporteGeneralObra();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void generaReporteCancelacion() {
		
		GeneraReporte reporteador = new GeneraReporte();
		CargarParametrosReporte paramReporte = new CargarParametrosReporte();
		InformacionObraDTO informacionRespuesta = new InformacionObraDTO();
		informacionRespuesta.setCveInformacionObra(new Long(1));
		informacionRespuesta.setCveRegistroObra(new String("1"));

		String rutaPlantilla = "C:\\Users\\daniel.hernandez\\Documents\\Registro Obras\\backup_reg_obra\\integracion\\daniel\\sdroc_web\\src\\main\\webapp\\static\\report\\plantilla";
		String nombreReporte = "\\reportCancelacion";
		String pathImg = "C:\\Users\\daniel.hernandez\\Documents\\Registro Obras\\backup_reg_obra\\integracion\\daniel\\sdroc_web\\src\\main\\webapp\\static\\images";
		String tipoRep = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy hh:mm:ss");
		String fechaFormato = sdf.format(new Date());
		
		HashMap<String, Object> param = paramReporte.cargarParametrosReporte(
				informacionRespuesta, null,
				ReporteEnum.REGISTRO_OBRA.getValor(), pathImg, null,fechaFormato);
	}

	private static void generaReporteRegistroPatronal() throws IOException {
		
		RegistroObraService registroObraService = new RegistroObraServiceImpl();
		GeneraReporte reporteador = new GeneraReporte();
		String rutaPlantilla = "C:\\workspace\\sdroc_web\\src\\main\\webapp\\static\\report\\plantilla";
		String nombreReporte = "\\reporteRegistroPatronal";
		String nombreSubReporte = "\\reporteRegistroPatronal_subreport1";
		String cveRegPatronal = "B4754624103";
		List<InformacionObraDTO> listaObrasRegistradas = (List<InformacionObraDTO>) registroObraService.consultaObrasRegistradasPorRegistroPatronal(cveRegPatronal);
		
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("razonSocial", "PRUEBAS");
		parametros.put("rfc", "PRUE010101BAS");
		parametros.put("registroPatronal", "REGISTRO PATRONAL PRUEBA");
		
		byte[] archivo = reporteador.generarReporteExcel(rutaPlantilla, nombreReporte, nombreSubReporte, parametros, listaObrasRegistradas);
		escribeArchivo("excelPruebaReporteRegistroPatronal.xls", archivo);
	}
	
	private static void generaReporteGeneralObra() throws IOException {

		ConsultaObraService consultaObraService = new ConsultaObraServiceImpl();
		GeneraReporte reporteador = new GeneraReporte();
		String rutaPlantilla = "C:\\workspace\\sdroc_web\\src\\main\\webapp\\static\\report\\plantilla";
		String nombreReporte = "\\reporteGeneralObra";
		String nombreSubReporte = "\\reporteGeneralObra_subreport";
		
		List<InformacionObraDTO> listaObrasRegistradas = (List<InformacionObraDTO>) consultaObraService.consultarInformacionObrasPorCveRfcyAnio("OERC8710217B9", "2016");
		
		//Seteamos los datos al
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("razonSocial", "PRUEBAS");
		parametros.put("rfc", "PRUE010101BAS");
		parametros.put("registroPatronal", "REGISTRO PATRONAL PRUEBA");
		
		byte[] archivo = reporteador.generarReporteExcel(rutaPlantilla, nombreReporte, nombreSubReporte, parametros, listaObrasRegistradas);
		escribeArchivo("excelPruebaReporteGeneralObra.xls", archivo);
	}
	
	private static void escribeArchivo(String nombreArchivo, byte[] contenidoArchivo) throws IOException {
		
        FileOutputStream fos = new FileOutputStream(nombreArchivo);
        fos.write(contenidoArchivo);
        fos.close();
	}

}
