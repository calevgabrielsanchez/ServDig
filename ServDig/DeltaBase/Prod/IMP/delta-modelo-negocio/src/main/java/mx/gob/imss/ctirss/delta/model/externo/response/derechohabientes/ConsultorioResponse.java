package mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes;

import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.externo.ConsultorioExternoDto;
import mx.gob.imss.ctirss.delta.model.externo.AbstractResponseExterno;

public class ConsultorioResponse extends AbstractResponseExterno  {

	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ConsultorioExternoDto consultorioExternoDto;
	public ConsultorioResponse() {
		super();
	}
	
	public ConsultorioResponse (String codigo, String mensaje, ConsultorioExternoDto consultorioExternoDto ){
		super(codigo,mensaje);
		this.consultorioExternoDto = consultorioExternoDto;
	}

	
	public ConsultorioResponse (ConsultorioExternoDto consultorioExternoDto ){
		super();
		this.consultorioExternoDto = consultorioExternoDto;
	}
	
	public ConsultorioExternoDto getTurnoConsultorioExternoDto() {
		return consultorioExternoDto;
	}
	public void setTurnoConsultorioExternoDto(
			ConsultorioExternoDto consultorioExternoDto) {
		this.consultorioExternoDto = consultorioExternoDto;
	}
	
	
	
	
	
}
