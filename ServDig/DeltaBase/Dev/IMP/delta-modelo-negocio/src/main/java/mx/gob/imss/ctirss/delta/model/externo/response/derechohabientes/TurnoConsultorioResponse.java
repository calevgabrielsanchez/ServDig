package mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes;

import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.externo.TurnoConsultorioExternoDto;
import mx.gob.imss.ctirss.delta.model.externo.AbstractResponseExterno;

public class TurnoConsultorioResponse extends AbstractResponseExterno  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TurnoConsultorioExternoDto turnoConsultorioExternoDto;
	public TurnoConsultorioResponse() {
		super();
	}
	
	public TurnoConsultorioResponse(TurnoConsultorioExternoDto turnoConsultorioExternoDto) {
		super();
		this.turnoConsultorioExternoDto = turnoConsultorioExternoDto;
	}
	
	public TurnoConsultorioResponse (String codigo, String mensaje, TurnoConsultorioExternoDto turnoConsultorioExternoDto ){
		super(codigo,mensaje);
		this.turnoConsultorioExternoDto = turnoConsultorioExternoDto;
	}

	
	
	public TurnoConsultorioExternoDto getTurnoConsultorioExternoDto() {
		return turnoConsultorioExternoDto;
	}
	public void setTurnoConsultorioExternoDto(
			TurnoConsultorioExternoDto turnoConsultorioExternoDto) {
		this.turnoConsultorioExternoDto = turnoConsultorioExternoDto;
	}
	
	
}
