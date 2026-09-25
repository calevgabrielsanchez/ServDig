package mx.gob.imss.ctirss.correccion.web.controller.reportes.caratula;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.gob.imss.common.utils.readAndExportXLS.process.ReadAndExportXLS;
import mx.gob.imss.ctirss.correccion.constantes.ArchivosXLSCaratula;

public abstract class AbstractReportesCaratula {
	
	@SuppressWarnings("rawtypes")
	protected List myList;
	protected String templateXLS;
	protected String outfileName;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected ReadAndExportXLS reXLS;
	public static Date fechaFinReporte;
	
	@SuppressWarnings("rawtypes")
	public List getMyList() {
		return myList;
	}
	@SuppressWarnings("rawtypes")
	public void setMyList(List myList) {
		this.myList = myList;
	}
	public String getTemplateXLS() {
		return templateXLS;
	}
	public void setTemplateXLS(String templateXLS) {
		this.templateXLS = templateXLS;
	}
	public String getOutfileName() {
		return outfileName;
	}
	public void setOutfileName(String outfileName) {
		this.outfileName = outfileName;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	public ReadAndExportXLS getReXLS() {
		return reXLS;
	}
	public void setReXLS(ReadAndExportXLS reXLS) {
		this.reXLS = reXLS;
	}
	
	
	
	
	public static Date getFechaFinReporte() {
		return fechaFinReporte;
	}
	public static void setFechaFinReporte(Date fechaFinReporte) {
		AbstractReportesCaratula.fechaFinReporte = fechaFinReporte;
	}
	public void setReXLS() {
		String contextPath = request.getSession().getServletContext().getRealPath(File.separator);
		if(!contextPath.endsWith(File.separator))contextPath+=File.separator;
		this.reXLS = new ReadAndExportXLS(contextPath+
				ArchivosXLSCaratula.RUTA + getTemplateXLS());
		
		System.out.println("RUTA PLANTILLAS = "+ reXLS);
	}
	
	protected String generaReporte(){
		
		if(reXLS.execute(response, request, getOutfileName())){
			return ArchivosXLSCaratula.MENSAJE_REPORTE_GENERADO;
		}else{
			return ArchivosXLSCaratula.MENSAJE_REPORTE_NO_ENCONTRADO;
		}
		
	}
	
	public String validaNull(String obj){
		
		if(obj==null) return "";
		else return obj;
	}
	
	public String getFechaActual(){
		
		SimpleDateFormat formater = new SimpleDateFormat("dd/MMM/yyyy", new Locale("es","MX"));	
		String fech=formater.format(fechaFinReporte);
		String val[]=fech.split("/");
		String fec=val[0]+"/"+convertirPrimeraMayuscula(val[1])+"/"+val[2];		
		return  fec;
	}
	
	public String convertirPrimeraMayuscula(String texto) {
		if (texto.length() > 0) {
			texto = texto.replace(texto.substring(0, 1), texto.substring(0, 1).toUpperCase());
		}
		return texto;
	}
	

}
