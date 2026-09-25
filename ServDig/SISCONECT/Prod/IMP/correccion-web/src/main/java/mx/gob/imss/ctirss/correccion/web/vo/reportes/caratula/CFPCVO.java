package mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class CFPCVO {

	private String folioCorreccion;
	private String registroPatronal;
	private String fechaOficioPromocion;
	private String fechaNotificacionOficio;
	private String fechaCancelada;
	private String motivoCancelacion;
	private String observaciones;
	
	public CFPCVO(){}
	
	public CFPCVO(Object[] obj){

		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		
		int i=0;
		
		setFolioCorreccion(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setRegistroPatronal(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setFechaOficioPromocion(obj[i++]!=null ? formater.format((Timestamp)obj[i-1]) : " ");
		setFechaNotificacionOficio(obj[i++]!=null ? formater.format((Timestamp)obj[i-1]) : " ");
		setFechaCancelada(obj[i++]!=null ? formater.format((Timestamp)obj[i-1]) : " ");
		setMotivoCancelacion(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setObservaciones(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		
	}


	public String getFolioCorreccion() {
		return folioCorreccion;
	}


	public void setFolioCorreccion(String folioCorreccion) {
		this.folioCorreccion = folioCorreccion;
	}


	public String getRegistroPatronal() {
		return registroPatronal;
	}


	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}

	public String getFechaOficioPromocion() {
		return fechaOficioPromocion;
	}

	public void setFechaOficioPromocion(String fechaOficioPromocion) {
		this.fechaOficioPromocion = fechaOficioPromocion;
	}

	public String getFechaNotificacionOficio() {
		return fechaNotificacionOficio;
	}

	public void setFechaNotificacionOficio(String fechaNotificacionOficio) {
		this.fechaNotificacionOficio = fechaNotificacionOficio;
	}

	public String getFechaCancelada() {
		return fechaCancelada;
	}

	public void setFechaCancelada(String fechaCancelada) {
		this.fechaCancelada = fechaCancelada;
	}

	public String getMotivoCancelacion() {
		return motivoCancelacion;
	}

	public void setMotivoCancelacion(String motivoCancelacion) {
		this.motivoCancelacion = motivoCancelacion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	
	
	
}
