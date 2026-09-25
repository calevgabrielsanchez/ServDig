package mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto;

import java.util.List;

import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.DerechohabienteDTO;

public class PersonaGruposFamilares extends Persona {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<DerechohabienteDTO> listDerechohabienteDTO;
	public List<DerechohabienteDTO> getListDerechohabienteDTO() {
		return listDerechohabienteDTO;
	}
	public void setListDerechohabienteDTO(List<DerechohabienteDTO> listDerechohabienteDTO) {
		this.listDerechohabienteDTO = listDerechohabienteDTO;
	}

	
	

}
