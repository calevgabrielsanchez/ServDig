package mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RFCPCVO {

	private String folioCorreccion;
	private String registroPatronal;
	private String nombre;
	private String fechaAutodeterminacion;
	private String diasHabilesTranscurridos;
	
	public RFCPCVO(){}
	
	public RFCPCVO(Object[] obj){

		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		int i=0;
		
		setFolioCorreccion(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setRegistroPatronal(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setNombre(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setFechaAutodeterminacion(obj[i++]!=null ? formater.format((Date)obj[i-1]) : " ");
		// id subdelegacion no se ocupa
		setDiasHabilesTranscurridos(obj[i++]!=null ? (BigDecimal)obj[i]+"" : " ");
		
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

	public String getFechaAutodeterminacion() {
		return fechaAutodeterminacion;
	}

	public void setFechaAutodeterminacion(String fechaAutodeterminacion) {
		this.fechaAutodeterminacion = fechaAutodeterminacion;
	}
	
	
}
