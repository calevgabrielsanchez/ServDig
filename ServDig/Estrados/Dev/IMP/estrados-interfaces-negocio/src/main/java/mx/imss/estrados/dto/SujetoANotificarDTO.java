package mx.imss.estrados.dto;

import java.io.Serializable;

public class SujetoANotificarDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1388438251018284384L;

	/**
	 * 
	 */


	public SujetoANotificarDTO() {
	}

	public SujetoANotificarDTO(Integer cveSujetoANotificar,
			SujetoANotificarDTO sujetoANotificarDTO, String desDirigidoA) {
		super();
		this.cveSujetoANotificar = cveSujetoANotificar;
		this.sujetoANotificarDTO = sujetoANotificarDTO;
		this.desDirigidoA = desDirigidoA;
	}

	private Integer cveSujetoANotificar;
	private SujetoANotificarDTO sujetoANotificarDTO;
	private String desDirigidoA;

	public Integer getCveSujetoANotificar() {
		return cveSujetoANotificar;
	}

	public void setCveSujetoANotificar(Integer cveSujetoANotificar) {
		this.cveSujetoANotificar = cveSujetoANotificar;
	}

	public SujetoANotificarDTO getSujetoANotificarDTO() {
		return sujetoANotificarDTO;
	}

	public void setSujetoANotificarDTO(SujetoANotificarDTO sujetoANotificarDTO) {
		this.sujetoANotificarDTO = sujetoANotificarDTO;
	}

	public String getDesDirigidoA() {
		return desDirigidoA;
	}

	public void setDesDirigidoA(String desDirigidoA) {
		this.desDirigidoA = desDirigidoA;
	}

}
