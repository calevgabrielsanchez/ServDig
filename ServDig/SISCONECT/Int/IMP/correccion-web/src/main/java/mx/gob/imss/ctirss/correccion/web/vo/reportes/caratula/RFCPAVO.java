package mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RFCPAVO {

	private String folioCorreccion;
	private String registroPatronal;
	private String nombre;
	private String fechaSolicitudAutorizada;
	private String diasHabilesTranscurridos;
	
	public RFCPAVO(){}
	
	public RFCPAVO(Object[] obj){
		
		int i=0;
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		setFolioCorreccion(obj[i]!=null ? String.valueOf(obj[i]) : " ");
		i++;
		setRegistroPatronal(obj[i]!=null ? String.valueOf(obj[i]) : " ");
		i++;
		setNombre(obj[i]!=null ? String.valueOf(obj[i]) : " ");
		i++;
		setFechaSolicitudAutorizada(obj[i]!=null ? formater.format((Date)obj[i]) : " ");
		i++;
		// idsubdelegacion, no se ocupa
		i++;		
		setDiasHabilesTranscurridos(obj[i]!=null ? ((BigDecimal)obj[i])+"" : " ");
		
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

	public String getFechaSolicitudAutorizada() {
		return fechaSolicitudAutorizada;
	}

	public void setFechaSolicitudAutorizada(String fechaSolicitudAutorizada) {
		this.fechaSolicitudAutorizada = fechaSolicitudAutorizada;
	}
	
	
}
