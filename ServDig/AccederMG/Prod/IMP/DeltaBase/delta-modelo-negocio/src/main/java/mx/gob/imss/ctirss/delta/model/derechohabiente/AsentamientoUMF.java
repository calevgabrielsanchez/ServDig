package mx.gob.imss.ctirss.delta.model.derechohabiente;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;

public class AsentamientoUMF  extends AbstractModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1875997612175893398L;

	private Asentamiento asentamiento;
	private UnidadMedicaFamiliar umf;

	public Asentamiento getAsentamiento() {
		return asentamiento;
	}

	public void setAsentamiento(Asentamiento asentamiento) {
		this.asentamiento = asentamiento;
	}

	public UnidadMedicaFamiliar getUmf() {
		return umf;
	}

	public void setUmf(UnidadMedicaFamiliar umf) {
		this.umf = umf;
	}

}
