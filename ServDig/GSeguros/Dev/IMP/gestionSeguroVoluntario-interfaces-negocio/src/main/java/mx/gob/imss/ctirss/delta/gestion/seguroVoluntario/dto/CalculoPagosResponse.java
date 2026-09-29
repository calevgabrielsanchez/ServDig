package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CalculoPagosResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6193334012333384875L;
	
	private String codigo;
	private String descripcion;
	private CalculoDTO  vrDto;
	
	
	public CalculoPagosResponse() {
		
	}
	
	public CalculoPagosResponse(String codigo, String descripcion,
			CalculoDTO vrDto) {
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

	public CalculoDTO getVrDto() {
		return vrDto;
	}

	public void setVrDto(CalculoDTO vrDto) {
		this.vrDto = vrDto;
	}
	
	
}
