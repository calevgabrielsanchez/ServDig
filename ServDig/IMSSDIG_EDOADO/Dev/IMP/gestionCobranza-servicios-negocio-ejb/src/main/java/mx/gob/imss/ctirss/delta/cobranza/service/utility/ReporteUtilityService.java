package mx.gob.imss.ctirss.delta.cobranza.service.utility;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.cobranza.enums.TotalesCobranzaEnum;
import mx.gob.imss.ctirss.delta.cobranza.modelo.Patron;
import mx.gob.imss.ctirss.delta.cobranza.modelo.TipoCobro;
import mx.gob.imss.ctirss.delta.cobranza.modelo.TipoCobroRcv;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;
import net.sf.jasperreports.engine.util.JRLoader;

import org.springframework.core.io.ClassPathResource;

@Stateless(name = "reporteUtilityService", mappedName = "reporteUtilityService")
public class ReporteUtilityService extends AbstractServiceUtility implements
		ReporteUtilityServiceLocal {

	@SuppressWarnings("unchecked")
	public byte[] getReporteTipoCobro(Map<String, Object> datosReporte, Patron patron) {
		ByteArrayOutputStream reporte = null;
		Map<String, String> subReportes = new HashMap<String, String>();
		List<TipoCobro> creditos = (List<TipoCobro>) datosReporte.get("dataSource");
		
		HashMap<String, Object> parametrosReporte = new HashMap<String, Object>();
		
		this.llenarParametrosReportes(parametrosReporte, datosReporte, patron, "");
		
		subReportes.put("tipoCobroSubReport_1.jasper", "SUBREPORTE_TIPO_COBRO");
		reporte = ejecutaReporteSubreporte(parametrosReporte, creditos, "estadoCuentaTrabajoAdeudoTipoCobro_1.jasper", subReportes);
		
		return reporte.toByteArray();
	}
	
	
	@Override
	public byte[] getReporteTipoCobroRCV(Map<String, Object> datosReporte,
			Patron patron) {
		ByteArrayOutputStream reporte = null;
		Map<String, String> subReportes = new HashMap<String, String>();
		List<TipoCobroRcv> creditos = (List<TipoCobroRcv>) datosReporte.get("dataSource");
		
		HashMap<String, Object> parametrosReporte = new HashMap<String, Object>();
		
		this.llenarParametrosReportesRCV(parametrosReporte, datosReporte, patron, "");
		subReportes.put("tipoCobroSubReportRCV_1.jasper", "SUBREPORTE_TIPO_COBRO");
		reporte = ejecutaReporteSubreporte(parametrosReporte, creditos, "estadoCuentaTrabajoAdeudoTipoCobroRCV_1.jasper", subReportes);
		
		return reporte.toByteArray();
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	private  ByteArrayOutputStream ejecutaReporteSubreporte(Map<String, Object> parametros,List<? extends Serializable> lista, 
			String reporte,Map<String, String> plantillas) {
		
		JasperReport subreport=null;
		Iterator it = plantillas.entrySet().iterator();
		while (it.hasNext()) {
			
			Map.Entry<String,Object> e = (Map.Entry<String,Object>)it.next();
			//Key va a ser la plantilla, value va a ser el parametro
			try {
				subreport = (JasperReport) JRLoader.loadObject(new ClassPathResource("reportes/"+e.getKey()).getInputStream());
				parametros.put(e.getValue().toString(), subreport);
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
		}
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		try {
			JRBeanCollectionDataSource dataSource;
			dataSource = new JRBeanCollectionDataSource(lista);
			ClassPathResource c = new ClassPathResource("reportes/logo_tiny.jpg"); 
			parametros.put("logoImss", c.getPath());
			JasperReport report = (JasperReport) JRLoader.loadObject(new ClassPathResource("reportes/"+reporte).getInputStream());
			JasperPrint print = JasperFillManager.fillReport(report, parametros, dataSource);

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
	
	private Map<String, Object> llenarParametrosReportes(Map<String, Object> parametrosReporte,Map<String, Object> datosReporte, Patron patron, String contextPath) {
		
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
	
	private Map<String, Object> llenarParametrosReportesRCV(Map<String, Object> parametrosReporte, Map<String,Object> datosReporte, Patron patron, String contextPath) {
		
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
	
	private void fillReportHeader(Patron patron, Map<String, Object> parametrosReporte, String rutaFinal) {
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

}
