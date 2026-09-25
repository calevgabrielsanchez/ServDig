package mx.gob.imss.ctirss.delta.cobranza.web.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.delta.cobranza.enums.TotalesCobranzaEnum;
import mx.gob.imss.ctirss.delta.cobranza.exception.EstadoAdeudoException;
import mx.gob.imss.ctirss.delta.cobranza.modelo.Patron;
import mx.gob.imss.ctirss.delta.cobranza.modelo.SituacionCobro;
import mx.gob.imss.ctirss.delta.cobranza.modelo.SituacionCobroRcv;
import mx.gob.imss.ctirss.delta.cobranza.modelo.TipoCobro;
import mx.gob.imss.ctirss.delta.cobranza.modelo.TipoCobroRcv;
import mx.gob.imss.ctirss.delta.cobranza.service.interfaces.PatronCobranzaServiceRemote;
import mx.gob.imss.ctirss.delta.cobranza.service.interfaces.ReportesCobranzaServiceRemote;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;
import net.sf.jasperreports.engine.util.JRLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/reportesCobranza/edoCuenta/trabajoAdeudo")
public class ReportesCobranzaController extends AbstractController {

	@Autowired
	ReportesCobranzaServiceRemote reportesCobranzaServiceRemote;
	@Autowired
	PatronCobranzaServiceRemote patronCobranzaServiceRemote;
	
	private static final String ERROR_INFO = "El patr&oacute;n no cuenta con informaci&oacute;n de estado de adeudo";
	private static final String SIN_ADEUDO = "El patr&oacute;n no tiene adeudo";

	@RequestMapping(value = "/tipoCobro/{nrp}")
	public String prepararTipoCobro(HttpServletRequest request,
			@PathVariable String nrp, HttpServletResponse response) {

		request.setAttribute("nrp", nrp);
		request.setAttribute("tipoReporte", 1);

		return "prepararEdoAdeudo";
	}

	@RequestMapping(value = "/tipoCobro", method = RequestMethod.POST)
	public String tipoCobroNew(HttpServletRequest request,
			@RequestParam String nrp, @RequestParam String token,
			HttpServletResponse response) {

		byte[] reporte = null;
		log.debug("Entro a crear el reporte de motivo");

		try {
			reporte = reportesCobranzaServiceRemote.getReporteTipoCobro(nrp);
		} catch (EstadoAdeudoException e) {
			e.printStackTrace();
			request.setAttribute("error", e.getSituacion());
			return "errorReporte";
		}

		try {

			response.reset();

			Cookie cookie = new Cookie("edoAdeudo1Cookie", token);
			response.addCookie(cookie);

			response.addHeader("Accept-Ranges", "bytes");
			response.addHeader("Cache-Control", "public");
			response.addHeader("Cache-Control", "must-revalidate");
			response.addHeader("Pragma", "public");
			response.setContentType("application/pdf");
			response.addHeader("expires", "0");
			response.addHeader("Content-disposition",
					"attachment;filename=\"motivoCobro" + nrp + ".pdf\"");
			response.setContentLength(reporte.length);
			response.getOutputStream().write(reporte);
			response.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "/tipoCobroRCV/{nrp}")
	public String prepararTipoCobroRcv(HttpServletRequest request,
			@PathVariable String nrp, HttpServletResponse response) {

		request.setAttribute("nrp", nrp);
		request.setAttribute("tipoReporte", 2);

		return "prepararEdoAdeudo";
	}

	@RequestMapping(value = "/tipoCobroRCV", method = RequestMethod.POST)
	public String tipoCobroRCVNew(HttpServletRequest request,
			@RequestParam String nrp, @RequestParam String token,
			HttpServletResponse response) {

		byte[] reporte = null;

		try {
			reporte = reportesCobranzaServiceRemote.getReporteTipoCobroRcv(nrp);
		} catch (EstadoAdeudoException e) {
			e.printStackTrace();
			request.setAttribute("error", e.getSituacion());
			return "errorReporte";
		}

		try {
			response.reset();

			Cookie cookie = new Cookie("edoAdeudo2Cookie", token);
			response.addCookie(cookie);

			response.addHeader("Accept-Ranges", "bytes");
			response.addHeader("Cache-Control", "public");
			response.addHeader("Cache-Control", "must-revalidate");
			response.addHeader("Pragma", "public");
			response.setContentType("application/pdf");
			response.addHeader("expires", "0");
			response.addHeader("Content-disposition",
					"attachment;filename=\"motivoCobroRCV" + nrp + ".pdf\"");
			response.setContentLength(reporte.length);
			response.getOutputStream().write(reporte);
			response.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/tipoCobroBack/{nrp}")
	public String tipoCobro(HttpServletRequest request,@PathVariable String nrp, HttpServletResponse response, ModelMap modelMap) {
		Map<String, Object> datosReporte = null;
		
		
		String rp = nrp.substring(0, 8);
		String modalidad = nrp.substring(8,10);
		
		System.out.println("rp: " + rp);
		System.out.println("modalidad: " + modalidad);
		
		Patron patron = new Patron();
		patron.setRegPatronal(rp);
		patron.setCveModalidad(modalidad);
		
		try {
			patron = patronCobranzaServiceRemote.getPatron(patron.getRegPatronal(), patron.getCveModalidad());
		}catch(Exception e) {
			request.setAttribute("error", ERROR_INFO);
			return "errorReporte";
		}
		
		if(patron == null) {
			request.setAttribute("error", ERROR_INFO);
			return "errorReporte";
		}
		
		List<TipoCobro> creditos = null;
		try {
			datosReporte = reportesCobranzaServiceRemote.getDatosReporteEstadoCuentaTrabajoAdeudoTipoCobro(patron);
		} catch(EstadoAdeudoException e){
			e.printStackTrace();
			request.setAttribute("error", e.getSituacion());
			return "errorReporte";
		} catch(Exception e){
			e.printStackTrace();
			request.setAttribute("error", ERROR_INFO);
			return "errorReporte";
		}
		HttpServletRequestWrapper srw = new HttpServletRequestWrapper(request);
		System.out.println("pacth de la aplicacion: " + srw.getRealPath(""));
		
		try {
			if(datosReporte != null) {
				creditos = (List<TipoCobro>) datosReporte.get("dataSource");
				
				if(!creditos.isEmpty()) {
					this.llenarParametrosReportes(modelMap, datosReporte, patron, srw.getRealPath(""));
					JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(creditos, false);
					this.setSubreport("SUBREPORTE_TIPO_COBRO", "tipoCobroSubReport_1.jasper",modelMap);
					modelMap.put("adeudoKey",dataSource);
					
					this.setDatosResponse(response);
				} else {
					request.setAttribute("error", SIN_ADEUDO);
					return "errorReporte";
				}
			} else {
				request.setAttribute("error", SIN_ADEUDO);
				return "errorReporte";
			}
		
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("error", ERROR_INFO);
			return "errorReporte";
		}
		
		return "reporteAdeudoTipoCobro";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/situacionCobro/{nrp}")
	public String situacionCobro(HttpServletRequest request, @PathVariable String nrp,HttpServletResponse response, ModelMap modelMap) {
		
		Map<String, Object> datosReporte = null;
		
		String rp = nrp.substring(0, 8);
		String modalidad = nrp.substring(8,10);
		
		Patron patron = new Patron();
		List<SituacionCobro> creditos = null;
		
		patron.setRegPatronal(rp);
		patron.setCveModalidad(modalidad);
		
		try {
			patron = patronCobranzaServiceRemote.getPatron(patron.getRegPatronal(), patron.getCveModalidad());
		}catch(Exception e) {
			request.setAttribute("error", ERROR_INFO);
			return "errorReporte";
		}
		
		if(patron == null) {
			request.setAttribute("error", ERROR_INFO);
			return "errorReporte";
		}
		
		try {
			datosReporte = reportesCobranzaServiceRemote.getDatosReporteEstadoCuentaTrabajoAdeudoSituacionCobro(patron);
		}catch(EstadoAdeudoException e){
			log.error(e);
			request.setAttribute("error", e.getSituacion());
			return "errorReporte";
		} catch(Exception e){
			e.printStackTrace();
			request.setAttribute("error", ERROR_INFO);
			return "errorReporte";
		}
		HttpServletRequestWrapper srw = new HttpServletRequestWrapper(request);
		System.out.println("pacth de la aplicacion: " + srw.getRealPath(""));
		
		
		try{
			if(datosReporte != null) {
				
				creditos = (List<SituacionCobro>) datosReporte.get("dataSource");
				if(!creditos.isEmpty()) {
					this.llenarParametrosReportes(modelMap, datosReporte, patron, srw.getRealPath(""));
					JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(creditos, false);
					this.setSubreport("SUBREPORTE_TIPO_COBRO", "tipoCobroSubReport_1.jasper",modelMap);
					modelMap.put("adeudoKey",dataSource);
					this.setDatosResponse(response);
				} else {
					request.setAttribute("error", SIN_ADEUDO);
					return "errorReporte";
				}
			} else {
				request.setAttribute("error", SIN_ADEUDO);
				return "errorReporte";
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", ERROR_INFO);
			return "errorReporte";
		}
		return "reporteAdeudoSituacionCobro";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/tipoCobroRCVBack/{nrp}")
	public String tipoCobroRCV(HttpServletRequest request, @PathVariable String nrp, HttpServletResponse response, ModelMap modelMap) {
		
		Map<String, Object> datosReporte = null;
		
		Patron patron = new Patron();
		List<TipoCobroRcv> creditos = null;
		
		String rp = nrp.substring(0, 8);
		String modalidad = nrp.substring(8,10);
		
		patron.setRegPatronal(rp);
		patron.setCveModalidad(modalidad);
		
		try {
			patron = patronCobranzaServiceRemote.getPatron(patron.getRegPatronal(), patron.getCveModalidad());
		}catch(Exception e) {
			request.setAttribute("error", ERROR_INFO);
			return "errorReporte";
		}
		
		if(patron == null) {
			request.setAttribute("error", ERROR_INFO);
			return "errorReporte";
		}
		
		try{
			datosReporte = reportesCobranzaServiceRemote.getDatosReporteEstadoCuentaTrabajoAdeudoTipoCobroRCV(patron);
		}catch(EstadoAdeudoException e){
			log.error(e);
			request.setAttribute("error", e.getSituacion());
			return "errorReporte";
		} catch(Exception e){
			e.printStackTrace();
			request.setAttribute("error", ERROR_INFO);
			return "errorReporte";
		}
		HttpServletRequestWrapper srw = new HttpServletRequestWrapper(request);
		System.out.println("pacth de la aplicacion: " + srw.getRealPath(""));
		
		try {
			if(datosReporte != null) {
				creditos = (List<TipoCobroRcv>) datosReporte.get("dataSource");
				
				if(!creditos.isEmpty()) {
					this.llenarParametrosReportesRCV(modelMap, datosReporte,patron, srw.getRealPath(""));
					JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(creditos, false);
					this.setSubreport("SUBREPORTE_TIPO_COBRO", "tipoCobroSubReportRCV_1.jasper",modelMap);
					modelMap.put("adeudoKey",dataSource);
					this.setDatosResponse(response);
				} else {
					request.setAttribute("error", SIN_ADEUDO);
					return "errorReporte";
				}
			} else {
				request.setAttribute("error", SIN_ADEUDO);
				return "errorReporte";
			}
			
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("error", ERROR_INFO);
			return "errorReporte";
		}
		
		return "reporteAdeudoTipoCobroRCV";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/situacionCobroRCV/{nrp}")
	public String situacionCobroRCV(HttpServletRequest request, @PathVariable String nrp,HttpServletResponse response, ModelMap modelMap) {
		
		Map<String, Object> datosReporte = null;
		
		Patron patron = new Patron();
		List<SituacionCobroRcv> creditos = null;
		
		String rp = nrp.substring(0, 8);
		String modalidad = nrp.substring(8,10);
		
		patron.setRegPatronal(rp);
		patron.setCveModalidad(modalidad);
		
		try {
			patron = patronCobranzaServiceRemote.getPatron(patron.getRegPatronal(), patron.getCveModalidad());
		}catch(Exception e) {
			request.setAttribute("error", ERROR_INFO);
			return "errorReporte";
		}
		
		if(patron == null) {
			request.setAttribute("error", ERROR_INFO);
			return "errorReporte";
		}
		
		try {
			datosReporte = reportesCobranzaServiceRemote.getDatosReporteEstadoCuentaTrabajoAdeudoSituacionCobroRCV(patron);
		} catch(EstadoAdeudoException e){
			log.error(e);
			request.setAttribute("error", e.getSituacion());
			return "errorReporte";
		} catch(Exception e){
			e.printStackTrace();
			request.setAttribute("error", ERROR_INFO);
			return "errorReporte";
		}
		HttpServletRequestWrapper srw = new HttpServletRequestWrapper(request);
		System.out.println("pacth de la aplicacion: " + srw.getRealPath(""));
		
		try {
			if(datosReporte != null) {
				
				creditos = (List<SituacionCobroRcv>) datosReporte.get("dataSource");
				
				if(!creditos.isEmpty()) {
					this.llenarParametrosReportesRCV(modelMap, datosReporte,patron, srw.getRealPath(""));
					JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(creditos, false);
					this.setSubreport("SUBREPORTE_TIPO_COBRO", "tipoCobroSubReportRCV_1.jasper",modelMap);
					modelMap.put("adeudoKey",dataSource);
					this.setDatosResponse(response);
				} else {
					request.setAttribute("error", SIN_ADEUDO);
					return "errorReporte";
				}
				
			} else {
				request.setAttribute("error", SIN_ADEUDO);
				return "errorReporte";
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("error", ERROR_INFO);
			return "errorReporte";
		}
		return "reporteAdeudoSituacionCobroRCV";
	}
	
	private void setDatosResponse(HttpServletResponse response) {
		response.setHeader("Content-type", "application/pdf");
        response.setHeader("Content-Disposition","attachment; filename=\"recibo.pdf\"");
	}
	
	private Map<String, Object> llenarParametrosReportes(ModelMap parametrosReporte,Map<String, Object> datosReporte, Patron patron, String contextPath) {
		
		DecimalFormat to2Dec = new DecimalFormat("###,###,##0.00");
		Double totalEnfermedadesMaternidad = 0.0;
		Double totalSalIV = 0.0;
		Double totalSalRt = 0.0;
		Double totalSalGuar = 0.0;
		Double totalSalTot = 0.0;
		Double totalAct = 0.0;
		Double totalInt = 0.0;
		Double porcentajeRecargos = 0.0; 
		Double sumaTotales = 0.0;
		
		totalEnfermedadesMaternidad = (Double) datosReporte.get("totalEnfermedadesMaternidad");
		totalSalIV = (Double) datosReporte.get("totalSalIV");
		totalSalRt = (Double) datosReporte.get("totalSalRt");
		totalSalGuar = (Double) datosReporte.get("totalSalGuar");
		totalSalTot = (Double) datosReporte.get("totalSalTot");
		totalAct = (Double) datosReporte.get("totalAct");
		totalInt = (Double) datosReporte.get("totalInt");
		porcentajeRecargos = (Double) datosReporte.get("porcentajeRecargos");
		sumaTotales = (Double) datosReporte.get(TotalesCobranzaEnum.SUMA_TOTALES.getKey());
        
        parametrosReporte.put("contextPath", contextPath);
        parametrosReporte.put("SALDOTOTPAT", "0.0");
        parametrosReporte.put("SALDOTOTPATACT", "00.00");
        parametrosReporte.put("SALDOTOTPATREC", "0.0");
        parametrosReporte.put("SALDOTOTPATTOT", "0.0");
        parametrosReporte.put("FECHA_CIERRE", datosReporte.get("FECHA_CIERRE"));
        
        this.fillReportHeader(patron, parametrosReporte, contextPath);
        
        parametrosReporte.put("totalEnfermedadesMaternidad", to2Dec.format(totalEnfermedadesMaternidad));
        parametrosReporte.put("totalSalIV", to2Dec.format(totalSalIV));
        parametrosReporte.put("totalSalRt", to2Dec.format(totalSalRt));
        parametrosReporte.put("totalSalGuar", to2Dec.format(totalSalGuar));
        parametrosReporte.put("totalSalTot", to2Dec.format(totalSalTot));
        parametrosReporte.put("totalAct", to2Dec.format(totalAct));
        parametrosReporte.put("totalInt", to2Dec.format(totalInt));
        parametrosReporte.put("porcentajeRecargos", to2Dec.format(porcentajeRecargos));
        parametrosReporte.put(TotalesCobranzaEnum.SUMA_TOTALES.getKey(), to2Dec.format(sumaTotales));
        parametrosReporte.put("cadenaOriginal", datosReporte.get("cadenaOriginal"));
        parametrosReporte.put("selloDigital", datosReporte.get("selloDigital"));
        parametrosReporte.put("secuenciaNotaria", datosReporte.get("secuenciaNotaria"));
        parametrosReporte.put("numeroSerie", datosReporte.get("numeroSerie"));
		
		return parametrosReporte;
	}

	public void fillReportHeader(Patron patron, ModelMap parametrosReporte, String rutaFinal) {
		DecimalFormat to5Dec = new DecimalFormat("#00.00000");
        
		parametrosReporte.put("TITULO", "ESTADO DE ADEUDO");
		parametrosReporte.put("FECHA_CONSULTA", fechaActual());//DATE
		parametrosReporte.put("FECHA_CERRADA", "yyyy-MM-dd");//DATE
		parametrosReporte.put("USUARIO", "");
		parametrosReporte.put("REGISTRO_PATRON", patron.getCvePatron()+"-"+ patron.getCveModalidad());
        if(!"1".equals(patron.getCveTipoPatron())){
        	parametrosReporte.put("DESC_TIP_PAT", patron.getDescTipoPatron());
        }else{
        	parametrosReporte.put("DESC_TIP_PAT", "");
        }
        
        
        System.out.println("la prima Rt es la siguiente sin formatear: " + patron.getPrimaRT());
        System.out.println("la prima Rt es la siguiente conviertiendola a double: " + new Double(patron.getPrimaRT()));
        System.out.println("la prima RT es la siguiente formateandola " + to5Dec.format(new Double(patron.getPrimaRT())));
        System.out.println("la prima RT es la siguiente con valueOf" + Double.valueOf(patron.getPrimaRT()));
        System.out.println("la prina RT es la siguinte formateandola y con valueof " + to5Dec.format(Double.valueOf(patron.getPrimaRT())));
        
        
        //datos del patron
        parametrosReporte.put("delegacionRes", patron.getDescDelegacion());
        parametrosReporte.put("subdelegacionRes", patron.getDescSubdelegacion());
        parametrosReporte.put("NOMBRE", patron.getRazonSocial());
        parametrosReporte.put("DESC_ACT", patron.getDescActEconomica());
        parametrosReporte.put("DESC_MOVPATRONAL", patron.getDescMovPatronal());
        parametrosReporte.put("DIRECCION", patron.getDomicilio());
        parametrosReporte.put("LOCALIDAD", patron.getLocalidad());
        parametrosReporte.put("CP", patron.getCp());
        parametrosReporte.put("RFC", patron.getRfc());
        parametrosReporte.put("RIESGO", to5Dec.format(new Double(patron.getPrimaRT())));
        parametrosReporte.put("GRUPO", "");
        parametrosReporte.put("DELEGACION", patron.getCveDelegacion());
        parametrosReporte.put("SUBDELEGACION", patron.getCveSubdelegacion());
        parametrosReporte.put("CLAVE_ACT", patron.getCveActEco());
        parametrosReporte.put("NUM_TRAB", patron.getNumTrabaja());
        parametrosReporte.put("SECTOR_NOT", patron.getCveSectorNotificacion());
        parametrosReporte.put("FECHA_MOV", patron.getFecMovto());//DATE
        parametrosReporte.put("CLAVE_ULT_MOV", patron.getCveMovtoPatronal());
        parametrosReporte.put("DESC_ULT_MOV", "");
        parametrosReporte.put("TIPO_APORT", patron.getTipoAportacion());
        parametrosReporte.put("SEC_NOT_ESP", patron.getCveSectorNotificacion());
        parametrosReporte.put("CURRENT_DIR", rutaFinal+"/");

        parametrosReporte.put("SUBREPORT_DIR", rutaFinal + "/WEB-INF/reporteTemplates/");
        parametrosReporte.put("imagenPath", rutaFinal+ "/reports/");
        
        JRFileVirtualizer virtualizer = new JRFileVirtualizer (100,rutaFinal+"/virtualizer");
        virtualizer.setReadOnly(false);
        parametrosReporte.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
        
	}
	
	private String fechaActual ()
	{
		SimpleDateFormat formateador = new SimpleDateFormat(
				   "dd/MM/yyyy", new Locale("ES"));
		Date fechaDate = new Date();
		String fecha = formateador.format(fechaDate);
		return fecha;
	}
	
	private void setSubreport(String nomParam,String reporte, ModelMap parametrosReporte) {
		
		JasperReport subreport = null;
		try {
			subreport = (JasperReport) JRLoader.loadObject(new ClassPathResource("reporteTemplates/"+reporte).getInputStream());
			parametrosReporte.put(nomParam, subreport);
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
	}
	
	private Map<String, Object> llenarParametrosReportesRCV(ModelMap parametrosReporte, Map<String,Object> datosReporte, Patron patron, String contextPath) {
		
		DecimalFormat to2Dec = new DecimalFormat("###,###,##0.00");
		Double totalCyV = 0.0;
		Double totalSalRet = 0.0;
		Double totalSalTot = 0.0;
		Double totalActua = 0.0;
		Double totalRecar = 0.0;
		Double sumaTotales = 0.0;
		
		totalCyV = (Double) datosReporte.get("totalCyV");
		totalSalRet = (Double) datosReporte.get("totalSalRet");
		totalSalTot = (Double) datosReporte.get("totalSalTot");
		totalActua = (Double) datosReporte.get("totalActua");
		totalRecar = (Double) datosReporte.get("totalRecar");
		sumaTotales = (Double) datosReporte.get(TotalesCobranzaEnum.SUMA_TOTALES.getKey());
		
		parametrosReporte.put("contextPath", contextPath);
	    parametrosReporte.put("SALDOTOTPAT", "0.0");
        parametrosReporte.put("SALDOTOTPATACT", "00.00");
        parametrosReporte.put("SALDOTOTPATREC", "0.0");
        parametrosReporte.put("SALDOTOTPATTOT", "0.0");
        parametrosReporte.put("FECHA_CIERRE", datosReporte.get("FECHA_CIERRE"));
        
        fillReportHeader(patron, parametrosReporte, contextPath);
        
        
        parametrosReporte.put("totalCyV", to2Dec.format(totalCyV));
        parametrosReporte.put("totalSalRet", to2Dec.format(totalSalRet));
        parametrosReporte.put("totalSalTot", to2Dec.format(totalSalTot));
        parametrosReporte.put("totalActua", to2Dec.format(totalActua));
        parametrosReporte.put("totalRecar", to2Dec.format(totalRecar));
        parametrosReporte.put(TotalesCobranzaEnum.SUMA_TOTALES.getKey(), to2Dec.format(sumaTotales));
        
        parametrosReporte.put("cadenaOriginal", datosReporte.get("cadenaOriginal"));
        parametrosReporte.put("selloDigital", datosReporte.get("selloDigital"));
        parametrosReporte.put("secuenciaNotaria", datosReporte.get("secuenciaNotaria"));
        parametrosReporte.put("numeroSerie", datosReporte.get("numeroSerie"));
		
		return parametrosReporte;
	}
}
