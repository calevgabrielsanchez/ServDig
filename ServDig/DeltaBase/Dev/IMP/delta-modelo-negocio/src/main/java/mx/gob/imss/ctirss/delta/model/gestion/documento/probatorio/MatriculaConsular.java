package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MatriculaConsular extends DocumentoProbatorio implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String numeroDoc;
	private String autoridadEmiteMat;
	private Date fechaVencimiento;
	private String calidadMigratoria;
	
	
	public String getNumeroDoc() {
		return numeroDoc;
	}
	
	public void setNumeroDoc(String numeroDoc) {
		this.numeroDoc = numeroDoc;
	}
	
	public String getAutoridadEmiteMat() {
		return autoridadEmiteMat;
	}
	
	public void setAutoridadEmiteMat(String autoridadEmiteMat) {
		this.autoridadEmiteMat = autoridadEmiteMat;
	}
	
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}
	
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	
	public String getCalidadMigratoria() {
		return calidadMigratoria;
	}
	
	public void setCalidadMigratoria(String calidadMigratoria) {
		this.calidadMigratoria = calidadMigratoria;
	}
	
}
