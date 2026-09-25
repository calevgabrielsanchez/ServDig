package mx.gob.imss.ctirss.delta.derechohabientes.service.utility;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import net.sf.jasperreports.engine.JasperPrint;

@Local
public interface ManejadorReportesLocal {
	
	public ByteArrayOutputStream ejecutaHolaMundo(String cveSolicitud);
	public ByteArrayOutputStream ejecutaReporte(Map parametros,List<? extends Serializable> lista, String nombre);
	ByteArrayOutputStream ejecutaReportePlantillas(Map parametros,List<? extends Serializable> lista, List<String> reporte);
	public JasperPrint imprimeReporte (Map parametros,List<? extends Serializable> lista, String reporte);
	public ByteArrayOutputStream ejecutaReporteCompilado(Map parametros,List<? extends Serializable> lista, String reporte);
	ByteArrayOutputStream ejecutaReporteSubreporte(Map parametros,List<? extends Serializable> lista, 
			String reporte,Map<String, String> plantillas);
	
	public ByteArrayOutputStream  concatPDF(List<ByteArrayOutputStream>  byteArrayOutputStream, boolean paginate );
	public JasperPrint getReporteCompilado(Map parametros,List<? extends Serializable> lista, String reporte);
	public ByteArrayOutputStream mergeReporteCompilado(List<JasperPrint> jasperPrints);
}
