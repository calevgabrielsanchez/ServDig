package mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Sexo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5145809340618545668L;
	
	private Long idSexo;
	private String descripcion;
	private String genero;
	
	
	public Sexo(){
		
	}
	
	public Sexo(Long idSexo, String descripcion ) {
		this.idSexo =  idSexo;
		this.descripcion = descripcion;
	}
	
	public Long getIdSexo() {
		return idSexo;
	}

	public void setIdSexo(Long idSexo) {
		this.idSexo = idSexo;
	}

	
	public Sexo(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the genero
	 */
	public String getGenero() {
		return genero;
	}

	/**
	 * @param genero the genero to set
	 */
	public void setGenero(String genero) {
		this.genero = genero;
	}

	
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
