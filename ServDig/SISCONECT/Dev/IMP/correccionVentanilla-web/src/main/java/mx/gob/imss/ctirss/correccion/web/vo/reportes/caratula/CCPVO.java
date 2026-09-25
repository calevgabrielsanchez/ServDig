package mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class CCPVO {

	private String folioCorreccion;
	private String registroPatronal;
	private String fecha;
	private String tipoCorreccion;
	
	
	public CCPVO(){}
	
	public CCPVO(Object[] obj){
		
		int i=0;
		
		setFolioCorreccion(obj[i++]!=null ? String.valueOf( obj[i-1]) : " ");
		setRegistroPatronal(obj[i++]!=null ?String.valueOf( obj[i-1]) : " ");
		setFecha(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setTipoCorreccion(obj[i++]!=null ? String.valueOf( obj[i-1]) : " ");
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


	public String getFecha() {
		return fecha;
	}


	public void setFecha(String fecha) {
		this.fecha = fecha;
	}


	public String getTipoCorreccion() {
		return tipoCorreccion;
	}


	public void setTipoCorreccion(String tipoCorreccion) {
		this.tipoCorreccion = tipoCorreccion;
	}
	
	
}
