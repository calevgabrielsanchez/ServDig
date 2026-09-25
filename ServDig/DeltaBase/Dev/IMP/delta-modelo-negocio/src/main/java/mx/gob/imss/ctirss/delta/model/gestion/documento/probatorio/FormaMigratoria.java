package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import mx.gob.imss.ctirss.delta.model.domicilio.Pais;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.CalidadMigratoria;

@XmlRootElement
public class FormaMigratoria extends DocumentoProbatorio {


	private static final long serialVersionUID = 1L;
	private String numeroDoc;
	private Pais paisOrigen;
	private Date fechaVencimiento;
	private CalidadMigratoria calidadMigratoria;

	
	
	public String getNumeroDoc() {
		return numeroDoc;
	}

	public void setNumeroDoc(String numeroDoc) {
		this.numeroDoc = numeroDoc;
	}
	
	public Pais getPaisOrigen() {
		return paisOrigen;
	}

	public void setPaisOrigen(Pais paisOrigen) {
		this.paisOrigen = paisOrigen;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public CalidadMigratoria getCalidadMigratoria() {
		return calidadMigratoria;
	}

	public void setCalidadMigratoria(CalidadMigratoria calidadMigratoria) {
		this.calidadMigratoria = calidadMigratoria;
	}

	
}
