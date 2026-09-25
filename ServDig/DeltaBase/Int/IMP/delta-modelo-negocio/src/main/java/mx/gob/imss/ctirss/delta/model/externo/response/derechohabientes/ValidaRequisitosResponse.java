package mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes;

import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.TramiteDerechohabientesMovilDto;
import mx.gob.imss.ctirss.delta.model.externo.AbstractResponseExterno;

public class ValidaRequisitosResponse extends AbstractResponseExterno {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2173977906512672564L;
	private TramiteDerechohabientesMovilDto tramiteDerechohabienteMovilDto;

	
	public ValidaRequisitosResponse() {
		super();
	}
	
	public ValidaRequisitosResponse(TramiteDerechohabientesMovilDto tramiteDerechohabienteMovilDto) {
		super();
		this.tramiteDerechohabienteMovilDto = tramiteDerechohabienteMovilDto;
	}

	public ValidaRequisitosResponse(TramiteDerechohabientesMovilDto tramiteDerechohabienteMovilDto,String codigo, String mensaje) {
		super(codigo, mensaje);
		this.tramiteDerechohabienteMovilDto = tramiteDerechohabienteMovilDto;
	}

	public TramiteDerechohabientesMovilDto getTramiteDerechohabienteMovilDto() {
		return tramiteDerechohabienteMovilDto;
	}

	public void setTramiteDerechohabienteMovilDto(
			TramiteDerechohabientesMovilDto tramiteDerechohabienteMovilDto) {
		this.tramiteDerechohabienteMovilDto = tramiteDerechohabienteMovilDto;
	}
	
	

}
