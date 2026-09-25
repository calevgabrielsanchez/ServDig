package mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes;

import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.externo.TurnoExternoDto;
import mx.gob.imss.ctirss.delta.model.externo.AbstractResponseExterno;

public class TurnoResponse extends AbstractResponseExterno  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TurnoExternoDto[] turnoExternoDto;
	
	public TurnoResponse() {
		super();
	}
	
	public TurnoResponse(TurnoExternoDto[] turnoExternoDto) {
		super();
		this.turnoExternoDto = turnoExternoDto;
	}
	
	public TurnoResponse (String codigo, String mensaje, TurnoExternoDto[] turnoExternoDto ){
		super(codigo,mensaje);
		this.turnoExternoDto = turnoExternoDto;
	}

	public TurnoExternoDto[] getTurnoExternoDto() {
		return turnoExternoDto;
	}

	public void setTurnoExternoDto(TurnoExternoDto[] turnoExternoDto) {
		this.turnoExternoDto = turnoExternoDto;
	}

	
	
	
	
}
