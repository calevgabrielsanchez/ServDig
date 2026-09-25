package mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TDVO {

	private String folioCorreccion;
	private String registroPatronal;
	private String proceso;
	private String trabajadoresRevisados;
	private String trabajadoresOmisos;
	private String trabajadoresSubDeclarados;
	private String trabajadoresRegularizados;
	
	public TDVO(){}
	
	public TDVO(Object[] obj){

		int i=0;
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		
		setFolioCorreccion(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setRegistroPatronal(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setProceso(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setTrabajadoresRevisados(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setTrabajadoresOmisos(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setTrabajadoresSubDeclarados(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setTrabajadoresRegularizados(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
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

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public String getTrabajadoresRevisados() {
		return trabajadoresRevisados;
	}

	public void setTrabajadoresRevisados(String trabajadoresRevisados) {
		this.trabajadoresRevisados = trabajadoresRevisados;
	}

	public String getTrabajadoresOmisos() {
		return trabajadoresOmisos;
	}

	public void setTrabajadoresOmisos(String trabajadoresOmisos) {
		this.trabajadoresOmisos = trabajadoresOmisos;
	}

	public String getTrabajadoresSubDeclarados() {
		return trabajadoresSubDeclarados;
	}

	public void setTrabajadoresSubDeclarados(String trabajadoresSubDeclarados) {
		this.trabajadoresSubDeclarados = trabajadoresSubDeclarados;
	}

	public String getTrabajadoresRegularizados() {
		return trabajadoresRegularizados;
	}

	public void setTrabajadoresRegularizados(String trabajadoresRegularizados) {
		this.trabajadoresRegularizados = trabajadoresRegularizados;
	}

	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}
	
	
}
