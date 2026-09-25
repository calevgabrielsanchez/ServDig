package mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Parentesco  implements Serializable{
	
	private static final long serialVersionUID = 9207374109439917097L;	
	private Long idParentesco;
	private String descripcion;
	private String descripcionFemenina;
	private String descripcionMasculina;

	
	
	public Long getIdParentesco() {
		return idParentesco;
	}
	public void setIdParentesco(Long idParentesco) {
		this.idParentesco = idParentesco;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDescripcionFemenina() {
		return descripcionFemenina;
	}
	public void setDescripcionFemenina(String descripcionFemenina) {
		this.descripcionFemenina = descripcionFemenina;
	}
	public String getDescripcionMasculina() {
		return descripcionMasculina;
	}
	public void setDescripcionMasculina(String descripcionMasculina) {
		this.descripcionMasculina = descripcionMasculina;
	}

	
	
		



	
	

}
