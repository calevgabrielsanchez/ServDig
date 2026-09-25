package mx.imss.estrados.dto;

import java.io.Serializable;

public class DepartamentoDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7518079941022963275L;

	/**
	 * 
	 */
	

	public DepartamentoDTO() {
	}
	
	public DepartamentoDTO(Integer cveDepto, ProcesoDTO procesoDTO,
			AreanormativaDTO areanormativaDTO, String desDepartamento) {
		super();
		this.cveDepto = cveDepto;
		this.procesoDTO = procesoDTO;
		this.areanormativaDTO = areanormativaDTO;
		this.desDepartamento = desDepartamento;
	}
	
	private Integer cveDepto;
	private ProcesoDTO procesoDTO;
	private AreanormativaDTO areanormativaDTO;
	private String desDepartamento;

	public Integer getCveDepto() {
		return cveDepto;
	}

	public void setCveDepto(Integer cveDepto) {
		this.cveDepto = cveDepto;
	}

	public ProcesoDTO getProcesoDTO() {
		return procesoDTO;
	}

	public void setProcesoDTO(ProcesoDTO procesoDTO) {
		this.procesoDTO = procesoDTO;
	}

	public AreanormativaDTO getAreanormativaDTO() {
		return areanormativaDTO;
	}

	public void setAreanormativaDTO(AreanormativaDTO areanormativaDTO) {
		this.areanormativaDTO = areanormativaDTO;
	}

	public String getDesDepartamento() {
		return desDepartamento;
	}

	public void setDesDepartamento(String desDepartamento) {
		this.desDepartamento = desDepartamento;
	}

}
