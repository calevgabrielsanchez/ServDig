package mx.imss.estrados.dto;

import java.io.Serializable;

public class TipodocumentoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6640543228073475456L;

	/**
	 * 
	 */
	

	public TipodocumentoDTO() {
	}

	public TipodocumentoDTO(Integer cveTipodocto, ProcesoDTO procesoDTO,
			String desTipodocumento, String desMarca) {
		super();
		this.cveTipodocto = cveTipodocto;
		this.procesoDTO = procesoDTO;
		this.desTipodocumento = desTipodocumento;
	}

	private Integer cveTipodocto;
	private ProcesoDTO procesoDTO;
	private String desTipodocumento;

	public Integer getCveTipodocto() {
		return cveTipodocto;
	}

	public void setCveTipodocto(Integer cveTipodocto) {
		this.cveTipodocto = cveTipodocto;
	}

	public ProcesoDTO getProcesoDTO() {
		return procesoDTO;
	}

	public void setProcesoDTO(ProcesoDTO procesoDTO) {
		this.procesoDTO = procesoDTO;
	}

	public String getDesTipodocumento() {
		return desTipodocumento;
	}

	public void setDesTipodocumento(String desTipodocumento) {
		this.desTipodocumento = desTipodocumento;
	}

}
