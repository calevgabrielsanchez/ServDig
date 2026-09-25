package mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class CFCCEVO {

	private String folioCorreccion;
	private String registroPatronal;
	private String registroObra;
	private String fechaSolicitudAutorizada;
	private String folioPromocion;	
	private String observaciones;
	
	
	public CFCCEVO(){}
	
	public CFCCEVO(Object[] obj){

		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
				
		int i=0;
		
		setFolioCorreccion(obj[i++]!=null ? String.valueOf(obj[i-1]):" ");
		setRegistroPatronal(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setRegistroObra(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setFechaSolicitudAutorizada(obj[i++]!=null ? formater.format((Timestamp)obj[i-1]) : " ");
		setFolioPromocion(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setObservacines(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");

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
	
	public String getFolioPromocion() {
		return folioPromocion;
	}

	public void setFolioPromocion(String folioPromocion) {
		this.folioPromocion = folioPromocion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservacines(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getRegistroObra() {
		return registroObra;
	}

	public void setRegistroObra(String registroObra) {
		this.registroObra = registroObra;
	}
	

	public String getFechaSolicitudAutorizada() {
		return fechaSolicitudAutorizada;
	}

	public void setFechaSolicitudAutorizada(String fechaSolicitudAutorizada) {
		this.fechaSolicitudAutorizada = fechaSolicitudAutorizada;
	}


	
}
