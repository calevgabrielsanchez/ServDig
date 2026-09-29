package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ValidaRetroactividadResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6193334012333384875L;
	
	private String codigo;
	private String descripcion;
	private ValidaRetroactividadDTO  vrDto;
	
	
	public ValidaRetroactividadResponse() {
		
	}
	
	public ValidaRetroactividadResponse(String codigo, String descripcion,
			ValidaRetroactividadDTO vrDto) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.vrDto = vrDto;
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public ValidaRetroactividadDTO getVrDto() {
		return vrDto;
	}

	public void setVrDto(ValidaRetroactividadDTO vrDto) {
		this.vrDto = vrDto;
	}
	
	
}
