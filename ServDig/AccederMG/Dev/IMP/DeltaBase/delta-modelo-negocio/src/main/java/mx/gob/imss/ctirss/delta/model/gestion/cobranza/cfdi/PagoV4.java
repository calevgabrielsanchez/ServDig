package mx.gob.imss.ctirss.delta.model.gestion.cobranza.cfdi;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PagoV4 extends Pago implements Serializable {

	private static final long serialVersionUID = 1L;
	private String codigoPostalReceptor;
	private String regimenFiscalReceptor;

	public String getCodigoPostalReceptor() {
		return codigoPostalReceptor;
	}

	public void setCodigoPostalReceptor(String codigoPostalReceptor) {
		this.codigoPostalReceptor = codigoPostalReceptor;
	}

	public String getRegimenFiscalReceptor() {
		return regimenFiscalReceptor;
	}

	public void setRegimenFiscalReceptor(String regimenFiscalReceptor) {
		this.regimenFiscalReceptor = regimenFiscalReceptor;
	}
}
