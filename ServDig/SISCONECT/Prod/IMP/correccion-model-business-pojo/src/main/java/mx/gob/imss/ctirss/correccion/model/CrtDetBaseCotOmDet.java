package mx.gob.imss.ctirss.correccion.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCrtDetBaseCotOmDet;

@Entity
@Table(name="CRT_DETBASECOT_OM_DET")
public class CrtDetBaseCotOmDet extends AbstractCrtDetBaseCotOmDet {

	@Transient
	public String txRemuneracion;
	
	@Transient
	public String getTxRemuneracion() {
		return txRemuneracion;
	}
	@Transient
	public void setTxRemuneracion(String txRemuneracion) {
		this.txRemuneracion = txRemuneracion;
	}
	
	
}
