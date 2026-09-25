package mx.gob.imss.ctirss.delta.model.derechohabiente;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;

public class UmfCodigoPostal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -982743824279590902L;
	
	private Long idUmfCodigoPostal;
	private UnidadMedicaFamiliar unidadMedicaFamiliar;
	private CodigoPostal codigoPostal;

	
	public Long getIdUmfCodigoPostal() {
		return idUmfCodigoPostal;
	}
	public void setIdUmfCodigoPostal(Long idUmfCodigoPostal) {
		this.idUmfCodigoPostal = idUmfCodigoPostal;
	}
	public UnidadMedicaFamiliar getUnidadMedicaFamiliar() {
		return unidadMedicaFamiliar;
	}
	public void setUnidadMedicaFamiliar(UnidadMedicaFamiliar unidadMedicaFamiliar) {
		this.unidadMedicaFamiliar = unidadMedicaFamiliar;
	}
	public CodigoPostal getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(CodigoPostal codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	
	

}
