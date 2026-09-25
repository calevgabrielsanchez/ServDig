package mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class CPVO {

	private String folioCorreccion;
	private String registroPatronal;
	private String fechaVencimiento;
	
	
	public CPVO(){}
	
	public CPVO(Object[] obj){

		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		
		int i=0;
		
		setFolioCorreccion(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setRegistroPatronal(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setFechaVencimiento(obj[i++]!=null ? formater.format((Timestamp)obj[i-1]) : " ");
	
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



	public String getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	
	
}
