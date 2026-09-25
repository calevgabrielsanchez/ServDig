package mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes;

import mx.gob.imss.ctirss.delta.global.model.UnidadMedicaFamiliarTO;
import mx.gob.imss.ctirss.delta.model.externo.AbstractResponseExterno;

public class UmfResponse extends AbstractResponseExterno  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UnidadMedicaFamiliarTO[] unidadMedicaFamiliarTO;
	
	public UmfResponse() {
		super();
	}
	
	public UmfResponse (String codigo, String mensaje, UnidadMedicaFamiliarTO[] unidadMedicaFamiliarTO ){
		super(codigo,mensaje);
		this.unidadMedicaFamiliarTO = unidadMedicaFamiliarTO;
	}

	public UmfResponse (UnidadMedicaFamiliarTO[] unidadMedicaFamiliarTO ){
		super();
		this.unidadMedicaFamiliarTO = unidadMedicaFamiliarTO;
	}
	
	public UnidadMedicaFamiliarTO[] getUnidadMedicaFamiliarTO() {
		return unidadMedicaFamiliarTO;
	}

	public void setUnidadMedicaFamiliarTO(
			UnidadMedicaFamiliarTO[] unidadMedicaFamiliarTO) {
		this.unidadMedicaFamiliarTO = unidadMedicaFamiliarTO;
	}

	

	
	
	
	
}
