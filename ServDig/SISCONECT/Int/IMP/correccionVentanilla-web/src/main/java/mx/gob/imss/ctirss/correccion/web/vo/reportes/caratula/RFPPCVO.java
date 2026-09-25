package mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RFPPCVO {

	private String folioCorreccion;
	private String registroPatronal;
	private String nombre;
	private String fechaNotificaicon;
	private String diasHabilesTranscurridos;
	
	public RFPPCVO(){}
	

	public RFPPCVO(Object[] obj){
		
		int i=0;
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		
		setFolioCorreccion(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setRegistroPatronal(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setNombre(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setFechaNotificaicon(obj[i++]!=null ? formater.format((Date)obj[i-1]) : " ");
		setDiasHabilesTranscurridos(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		
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
	

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDiasHabilesTranscurridos() {
		return diasHabilesTranscurridos;
	}

	public void setDiasHabilesTranscurridos(String diasHabilesTranscurridos) {
		this.diasHabilesTranscurridos = diasHabilesTranscurridos;
	}

	public String getFechaNotificaicon() {
		return fechaNotificaicon;
	}

	public void setFechaNotificaicon(String fechaNotificaicon) {
		this.fechaNotificaicon = fechaNotificaicon;
	}
	
	
}
