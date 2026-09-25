package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

@XmlRootElement
public class Grupo extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1847211613575958504L;
	private Long id;
	private String descripcion;
	private String numGrupo;
	
	/**
	 * @author Hugo Armando Mart-nez Cham-nica
	 */
	private Division division;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the division
	 */
	public Division getDivision() {
		return division;
	}

	/**
	 * @param division
	 *            the division to set
	 */
	public void setDivision(Division division) {
		this.division = division;
	}

	public String getNumGrupo() {
		return numGrupo;
	}

	public void setNumGrupo(String numGrupo) {
		this.numGrupo = numGrupo;
	}

}
