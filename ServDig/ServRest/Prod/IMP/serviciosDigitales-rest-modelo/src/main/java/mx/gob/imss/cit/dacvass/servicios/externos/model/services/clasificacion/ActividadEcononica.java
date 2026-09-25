package mx.gob.imss.cit.dacvass.servicios.externos.model.services.clasificacion;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ActividadEcononica implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = -3357351949409416202L;

	private String numDivision;
	private String numGrupo;
	private String numFraccion;
	
	
	public String getNumDivision() {
		return numDivision;
	}
	public void setNumDivision(String numDivision) {
		this.numDivision = numDivision;
	}
	public String getNumGrupo() {
		return numGrupo;
	}
	public void setNumGrupo(String numGrupo) {
		this.numGrupo = numGrupo;
	}
	public String getNumFraccion() {
		return numFraccion;
	}
	public void setNumFraccion(String numFraccion) {
		this.numFraccion = numFraccion;
	}
	
	

}
