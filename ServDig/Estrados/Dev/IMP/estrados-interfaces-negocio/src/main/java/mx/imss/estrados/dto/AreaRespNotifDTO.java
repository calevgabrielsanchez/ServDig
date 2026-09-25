package mx.imss.estrados.dto;

import java.io.Serializable;

public class AreaRespNotifDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7355938990614404732L;

	/**
	 * 
	 */
	

	public AreaRespNotifDTO() {
		this.procesoDTO = new ProcesoDTO();
	}

	public AreaRespNotifDTO(Integer cveAreaRespNotif, ProcesoDTO procesoDTO,
			AreanormativaDTO areanormativaDTO) {
		super();
		this.cveAreaRespNotif = cveAreaRespNotif;
		this.procesoDTO = procesoDTO;
		this.areanormativaDTO = areanormativaDTO;
	}

	private Integer cveAreaRespNotif;
	private ProcesoDTO procesoDTO;
	private AreanormativaDTO areanormativaDTO;

	public Integer getCveAreaRespNotif() {
		return cveAreaRespNotif;
	}

	public void setCveAreaRespNotif(Integer cveAreaRespNotif) {
		this.cveAreaRespNotif = cveAreaRespNotif;
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

}
