package mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class CSCVO {

	private String folioCorreccion;
	private String registroPatronal;
	private String fechaSolicitudPresentada;
	private String fechaSolicitudAceptada;
	private String fechaSolicitudRechazada;	
	private String motivoRechazo;
	
	public CSCVO(){}
	
	public CSCVO(Object[] obj){

		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		
		int i=0;
		
		setFolioCorreccion(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setRegistroPatronal(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setFechaSolicitudPresentada(obj[i++]!=null ? formater.format((Timestamp)obj[i-1]) : " ");
		setFechaSolicitudAceptada(obj[i++]!=null ? formater.format((Timestamp)obj[i-1]) : " ");
		setFechaSolicitudRechazada(obj[i++]!=null ? formater.format((Timestamp)obj[i-1]) : " ");
		setMotivoRechazo(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");

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

	public String getFechaSolicitudAceptada() {
		return fechaSolicitudAceptada;
	}

	public void setFechaSolicitudAceptada(String fechaSolicitudAceptada) {
		this.fechaSolicitudAceptada = fechaSolicitudAceptada;
	}

	public String getFechaSolicitudPresentada() {
		return fechaSolicitudPresentada;
	}

	public void setFechaSolicitudPresentada(String fechaSolicitudPresentada) {
		this.fechaSolicitudPresentada = fechaSolicitudPresentada;
	}

	public String getFechaSolicitudRechazada() {
		return fechaSolicitudRechazada;
	}

	public void setFechaSolicitudRechazada(String fechaSolicitudRechazada) {
		this.fechaSolicitudRechazada = fechaSolicitudRechazada;
	}

	public String getMotivoRechazo() {
		return motivoRechazo;
	}

	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}


	
}
