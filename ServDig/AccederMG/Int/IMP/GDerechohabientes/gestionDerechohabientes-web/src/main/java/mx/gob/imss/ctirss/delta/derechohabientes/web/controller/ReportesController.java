/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.solicitud.SolicitudesPendAutDataTable;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.reportes.ReportesRemote;

import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.ReporteRegistro;
import mx.gob.imss.ctirss.delta.model.util.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.support.RestGatewaySupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.ui.Model;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author ghdolores
 *
 */
@Controller
@RequestMapping("/reportes")
public class ReportesController  extends AbstractController{

    @Autowired
	ReportesRemote reportes;
	
    @RequestMapping(value="/reporteSav011/{idAsegurado}", method = RequestMethod.GET)
	public void getReporteSav011(@PathVariable("idAsegurado") Integer idAsegurado,HttpServletResponse response){
		
		
		
		try {
			byte[] res  = (byte[])reportes.getReporteSav011(idAsegurado);
			
			
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition","inline;filename = sav" );
			response.getOutputStream().write(res);
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
			/*ProductoSolicitud ps = new ProductoSolicitud();
			ps = gestionDocumental.buscaDocumentoToPrint(new BigInteger("553"));
			byte[] res = ps.getDocumento();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			baos.toByteArray();
			
			sresponse.setContentType(ps.getTipContenido());
			sresponse.setHeader("Content-Disposition","filename = " + ps.getNomArchivo());
			sresponse.getOutputStream().write(res);
			sresponse.getOutputStream().flush();
			sresponse.getOutputStream().close();
			*/
		}catch(Exception e) {
			//log.error("Error en el metodo init  previo : " + e);
			e.printStackTrace();
		}
		
		
	}

	@RequestMapping(value = "/registroConyugeConcubinario")
	public String registroConyugeConcubinario(@RequestParam("tramite") Long tipoTramite, @RequestParam("fechaInicio") String fechaInicio, @RequestParam("fechaFin") String fechaFin,  @RequestParam("nivelreporte") String nivelreporte,  @RequestParam("delegacion") String delegacion,  @RequestParam("subdelegacion") String subdelegacion,HttpSession session,
			HttpServletRequest request, HttpServletResponse response, Model model) {

		AsignacionNSS asignacionNSS = (AsignacionNSS) session.getAttribute("AsignacionNSS");
		Usuario usuario = (Usuario) session.getAttribute("usuarioreporte");
		session.setAttribute("fechaInicio", fechaInicio);
		session.setAttribute("fechaFin", fechaFin);
		model.addAttribute("nivelreporte", nivelreporte);
		model.addAttribute("delegacion", delegacion);
		model.addAttribute("subdelegacion", subdelegacion);
		log.debug("//JAS nivelreporte: " + nivelreporte);
		log.debug("//JAS delegacion: " + delegacion);
		log.debug("//JAS subdelegacion: " + subdelegacion);
		try {
			reportes.getReporteRegistroConyugeConcubinario(fechaInicio, fechaFin, usuario.getUsuarioFuncionario().getDelegacion().getId(), usuario.getCveIdSubdelegacion());

		} catch (Exception e) {
			response.addHeader("Set-Cookie", "fileDownloadError=true;Path=/");
			e.printStackTrace();
		}

		return Constants.URL_REP_REG_CONYUGE_CONCUBINARIO;
		
	}	

	@RequestMapping(value = "/muestraRegistroConyuge", method = RequestMethod.POST)
	public @ResponseBody DatosSalidaPaginador<ReporteRegistro> muestraRegistroConyuge(@RequestBody SolicitudesPendAutDataTable aoData, HttpSession session,HttpServletRequest request) {

		AsignacionNSS asignacionNSS = (AsignacionNSS) session.getAttribute("AsignacionNSS");
		Usuario usuario = (Usuario) session.getAttribute("usuarioreporte");
		String fechaInicio = (String) session.getAttribute("fechaInicio");
		String fechaFin = (String) session.getAttribute("fechaFin");
		DatosSalidaPaginador<ReporteRegistro> resultado = new DatosSalidaPaginador<ReporteRegistro>();
		List<ReporteRegistro> regs = null;
		@SuppressWarnings("rawtypes")
		DatosEntradaPaginador envio = new DatosEntradaPaginador();
		envio.parserArray(aoData.getAoData());

		try {
			resultado = reportes.getReporteRegistroConyugeConcubinario(fechaInicio, fechaFin,  usuario.getUsuarioFuncionario().getDelegacion().getId(), usuario.getCveIdSubdelegacion());
			resultado.setsEcho(envio.getsEcho());
		} catch (Exception e) {
			e.printStackTrace();
		}
        resultado.setsEcho(envio.getsEcho());
		session.setAttribute("DatosRepRegCon", resultado);
		return resultado;
	}	

    @RequestMapping(value="/reporteRegConyugeConcubinario", method = RequestMethod.POST)
	public void getReporteRegConyugeConcubinario(@RequestParam("idExcelTxt") Integer idExcelTxt,HttpSession session,
				HttpServletRequest request, HttpServletResponse response){
					
		try {
			DatosSalidaPaginador<ReporteRegistro> resultado = (DatosSalidaPaginador<ReporteRegistro>) session.getAttribute(("DatosRepRegCon"));
			String filename = "";
			String registro = "";
			byte[] registrotInBytes = null;
			if(idExcelTxt == 1) {
				response.setContentType("application/xls");
				filename = "reporteRegConyugeConcubinario.xls";
				Workbook workbook = new HSSFWorkbook();
				Sheet sheet = workbook.createSheet("Hoja de datos");
				Row row = null;
				Cell cell = null;
				int numeroRenglon = 0;

				row = sheet.createRow(numeroRenglon++);
				cell = row.createCell(0);
				cell.setCellValue("NSS");
				cell = row.createCell(1);
				cell.setCellValue("NOMBRE ASEGURADO");
				cell = row.createCell(2);
				cell.setCellValue("APELLIDO PATERNO ASEGURADO");
				cell = row.createCell(3);
				cell.setCellValue("APELLIDO MATERNO ASEGURADO");
				cell = row.createCell(4);
				cell.setCellValue("CURP ASEGURADO");
				cell = row.createCell(5);
				cell.setCellValue("SEXO ASEGURADO");
				cell = row.createCell(6);
				cell.setCellValue("DOMICILIO ASEGURADO MOMENTO TRAMITE");
				cell = row.createCell(7);
				cell.setCellValue("DOMICILIO ASEGURADO ACTUAL");
				cell = row.createCell(8);
				cell.setCellValue("NOMBRE BENEFICIARIO");
				cell = row.createCell(9);
				cell.setCellValue("APELLIDO PATERNO BENEFICIARIO");
				cell = row.createCell(10);
				cell.setCellValue("APELLIDO MATERNO BENEFICIARIO");
				cell = row.createCell(11);
				cell.setCellValue("CURP BENEFICIARIO");
				cell = row.createCell(12);
				cell.setCellValue("SEXO BENEFICIARIO");
				cell = row.createCell(13);
				cell.setCellValue("DOMICILIO BENEFICIARIO MOMENTO TRAMITE");
				cell = row.createCell(14);
				cell.setCellValue("DOMICILIO BENEFICIARIO ACTUAL");
				cell = row.createCell(15);
				cell.setCellValue("FECHA TRAMITE");
				cell = row.createCell(16);
				cell.setCellValue("ID TIPO TRAMITE");
				cell = row.createCell(17);
				cell.setCellValue("DELEGACION MOMENTO TRAMITE");
				cell = row.createCell(18);
				cell.setCellValue("DELEGACION ACTUAL");
				cell = row.createCell(19);
				cell.setCellValue("SUBDELEGACION MOMENTO TRAMITE");
				cell = row.createCell(20);
				cell.setCellValue("SUBDELEGACION ACTUAL");
				cell = row.createCell(21);
				cell.setCellValue("UMF MOMENTO TRAMITE");
				cell = row.createCell(22);
				cell.setCellValue("UMF ACTUAL");
				cell = row.createCell(23);
				cell.setCellValue("TIPO TRAMITE");
				cell = row.createCell(24);
				cell.setCellValue("VIGENCIA ASEGURADO MOMENTO TRAMITE");
				cell = row.createCell(25);
				cell.setCellValue("VIGENCIA BENEFICIARIO MOMENTO TRAMITE");
				cell = row.createCell(26);
				cell.setCellValue("IND CONYUGE MISMO SEXO");
				cell = row.createCell(27);
				cell.setCellValue("IND CONCUBINARIO MISMO SEXO");
				cell = row.createCell(28);
				cell.setCellValue("CUENTA USUARIO");
				cell = row.createCell(29);
				cell.setCellValue("ORIGEN TRAMITE");
				cell = row.createCell(30);
				cell.setCellValue("DOCUMENTOS PROBATORIOS");

				for (ReporteRegistro reg : resultado.getAaData()) {
					row = sheet.createRow(numeroRenglon++);
					cell = row.createCell(0);
					cell.setCellValue(reg.getNUM_NSS());
					cell = row.createCell(1);
					cell.setCellValue(reg.getNOMBRE_ASEGURADO());
					cell = row.createCell(2);
					cell.setCellValue(reg.getAPELLIDO_PATERNO_ASEGURADO());
					cell = row.createCell(3);
					cell.setCellValue(reg.getAPELLIDO_MATERNO_ASEGURADO());
					cell = row.createCell(4);
					cell.setCellValue(reg.getCURP_ASEGURADO());
					cell = row.createCell(5);
					cell.setCellValue(reg.getSEXO_ASEGURADO());
					cell = row.createCell(6);
					cell.setCellValue(reg.getDOMICILIO_ASEGURADO_MOMENTO());
					cell = row.createCell(7);
					cell.setCellValue(reg.getDOMICILIO_ASEGURADO_ACTUAL());
					cell = row.createCell(8);
					cell.setCellValue(reg.getNOMBRE_BENEFICIARIO());
					cell = row.createCell(9);
					cell.setCellValue(reg.getAPELLIDO_PATERNO_BENEFICIARIO());
					cell = row.createCell(10);
					cell.setCellValue(reg.getAPELLIDO_MATERNO_BENEFICIARIO());
					cell = row.createCell(11);
					cell.setCellValue(reg.getCURP_BENEFICIARIO());
					cell = row.createCell(12);
					cell.setCellValue(reg.getSEXO_BENEFICIARIO());
					cell = row.createCell(13);
					cell.setCellValue(reg.getDOMICILIO_BENEFICIARIO_MOMENTO());
					cell = row.createCell(14);
					cell.setCellValue(reg.getDOMICILIO_BENEFICIARIO_ACTUAL());
					cell = row.createCell(15);
					cell.setCellValue(reg.getFECHA_TRAMITE());
					cell = row.createCell(16);
					cell.setCellValue(reg.getID_TIPO_TRAMITE());
					cell = row.createCell(17);
					cell.setCellValue(reg.getDES_DEL_MOMENTO());
					cell = row.createCell(18);
					cell.setCellValue(reg.getDES_DEL_ACTUAL());
					cell = row.createCell(19);
					cell.setCellValue(reg.getDES_SUB_MOMENTO());
					cell = row.createCell(20);
					cell.setCellValue(reg.getDES_SUB_ACTUAL());
					cell = row.createCell(21);
					cell.setCellValue(reg.getDES_UMF_MOMENTO());
					cell = row.createCell(22);
					cell.setCellValue(reg.getDES_UMF_ACTUAL());
					cell = row.createCell(23);
					cell.setCellValue(reg.getTIPO_TRAMITE());
					cell = row.createCell(24);
					cell.setCellValue(reg.getVIGENCIA_ASEG_MOMENTO());
					cell = row.createCell(25);
					cell.setCellValue(reg.getVIGENCIA_BENEF_MOMENTO());
					cell = row.createCell(26);
					cell.setCellValue(reg.getIND_CONYUGE_MISMO_SEXO());
					cell = row.createCell(27);
					cell.setCellValue(reg.getIND_CONCUBINARIO_MISMO_SEXO());
					cell = row.createCell(28);
					cell.setCellValue(reg.getCUENTA_USUARIO());
					cell = row.createCell(29);
					cell.setCellValue(reg.getORIGEN_TRAMITE());
					cell = row.createCell(30);
					cell.setCellValue(reg.getDOCUMENTOS_PROBATORIOS());
				}
				workbook.write(response.getOutputStream());
			} else {
				response.setContentType("application/txt");
				filename = "reporteRegConyugeConcubinario.txt";

				registro = "NSS | NOMBRE ASEGURADO | APELLIDO PATERNO ASEGURADO | APELLIDO MATERNO ASEGURADO | CURP ASEGURADO | ";
				registro += "SEXO ASEGURADO | DOMICILIO ASEGURADO MOMENTO TRAMITE | DOMICILIO ASEGURADO ACTUAL | NOMBRE BENEFICIARIO | ";
				registro += "APELLIDO PATERNO BENEFICIARIO | APELLIDO MATERNO BENEFICIARIO | CURP BENEFICIARIO | SEXO BENEFICIARIO | ";
				registro += "DOMICILIO BENEFICIARIO MOMENTO TRAMITE | DOMICILIO BENEFICIARIO ACTUAL | FECHA TRAMITE | ID TIPO TRAMITE | ";
				registro += "CLAVE DELEGACION MOMENTO TRAMITE | CLAVE DELEGACION ACTUAL | DELEGACION MOMENTO TRAMITE | DELEGACION ACTUAL | ";
				registro += "CLAVE SUBDELEGACION MOMENTO TRAMITE | CLAVE SUBDELEGACION ACTUAL | SUBDELEGACION MOMENTO TRAMITE | ";
				registro += "SUBDELEGACION ACTUAL | CLAVE UMF MOMENTO TRAMITE | UMF MOMENTO TRAMITE | CLAVE UMF ACTUAL | UMF ACTUAL | ";
				registro += "TIPO TRAMITE | VIGENCIA ASEGURADO MOMENTO TRAMITE | VIGENCIA BENEFICIARIO MOMENTO TRAMITE | ";
				registro += "IND CONYUGE MISMO SEXO | IND CONCUBINARIO MISMO SEXO | CUENTA USUARIO | ORIGEN TRAMITE | DOCUMENTOS PROBATORIOS" + "\r\n";
				registrotInBytes = registro.getBytes();
				response.getOutputStream().write(registrotInBytes);
			
				for (ReporteRegistro reg : resultado.getAaData()) {
					registro = reg.getNUM_NSS() + "|" ;
					registro += reg.getNOMBRE_ASEGURADO() + "|";
					registro += reg.getAPELLIDO_PATERNO_ASEGURADO() + "|";
					registro += reg.getAPELLIDO_MATERNO_ASEGURADO() + "|";
					registro += reg.getCURP_ASEGURADO() + "|";
					registro += reg.getSEXO_ASEGURADO() + "|";
					registro += reg.getDOMICILIO_ASEGURADO_MOMENTO() + "|";
					registro += reg.getDOMICILIO_ASEGURADO_ACTUAL() + "|";
					registro += reg.getNOMBRE_BENEFICIARIO() + "|";
					registro += reg.getAPELLIDO_PATERNO_BENEFICIARIO() + "|";
					registro += reg.getAPELLIDO_MATERNO_BENEFICIARIO() + "|";
					registro += reg.getCURP_BENEFICIARIO() + "|";
					registro += reg.getSEXO_BENEFICIARIO() + "|";
					registro += reg.getDOMICILIO_BENEFICIARIO_MOMENTO() + "|";
					registro += reg.getDOMICILIO_BENEFICIARIO_ACTUAL() + "|";
					registro += reg.getFECHA_TRAMITE() + "|";
					registro += reg.getID_TIPO_TRAMITE() + "|";
					registro += reg.getCLAVE_DEL_MOMENTO() + "|";
					registro += reg.getCLAVE_DEL_ACTUAL() + "|";
					registro += reg.getDES_DEL_MOMENTO() + "|";
					registro += reg.getDES_DEL_ACTUAL() + "|";
					registro += reg.getCLAVE_SUB_MOMENTO() + "|";
					registro += reg.getCLAVE_SUB_ACTUAL() + "|";
					registro += reg.getDES_SUB_MOMENTO() + "|";
					registro += reg.getDES_SUB_ACTUAL() + "|";
					registro += reg.getCVE_UMF_MOMENTO() + "|";
					registro += reg.getDES_UMF_MOMENTO() + "|";
					registro += reg.getCVE_UMF_ACTUAL() + "|";
					registro += reg.getDES_UMF_ACTUAL() + "|";
					registro += reg.getTIPO_TRAMITE() + "|";
					registro += reg.getVIGENCIA_ASEG_MOMENTO() + "|";
					registro += reg.getVIGENCIA_BENEF_MOMENTO() + "|";
					registro += reg.getIND_CONYUGE_MISMO_SEXO() + "|";
					registro += reg.getIND_CONCUBINARIO_MISMO_SEXO() + "|";
					registro += reg.getCUENTA_USUARIO() + "|";
					registro += reg.getORIGEN_TRAMITE() + "|";
					registro += reg.getDOCUMENTOS_PROBATORIOS().replace('|', '-') + "\r\n";
					registrotInBytes = registro.getBytes();
					response.getOutputStream().write(registrotInBytes);
				}
			}
			response.setHeader("Content-Disposition","attachment;filename = " + filename );
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		}catch(Exception e) {
			//log.error("Error en el metodo init  previo : " + e);
			e.printStackTrace();
		}
		
		
	}

		
}
