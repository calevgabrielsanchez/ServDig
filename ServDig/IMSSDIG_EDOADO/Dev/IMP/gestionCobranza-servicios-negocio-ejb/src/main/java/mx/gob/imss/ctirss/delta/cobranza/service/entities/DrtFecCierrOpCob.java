package mx.gob.imss.ctirss.delta.cobranza.service.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DRT_FEC_CIERRE_OP_COB")
public class DrtFecCierrOpCob implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3425417190269095442L;

	@Id
	@Column(name = "CVE_ID_FEC_CIERRE_OP_COB")
	private long cveIdFecCierreOpCob;
	
	@Column(name = "FEC_CIERRE_OP_COB")
	private Date fecCierreOpCob;
	
	@Column(name = "FEC_ALTA")
	private Date fechaAlta;

	public long getCveIdFecCierreOpCob() {
		return cveIdFecCierreOpCob;
	}

	public void setCveIdFecCierreOpCob(long cveIdFecCierreOpCob) {
		this.cveIdFecCierreOpCob = cveIdFecCierreOpCob;
	}

	public Date getFecCierreOpCob() {
		return fecCierreOpCob;
	}

	public void setFecCierreOpCob(Date fecCierreOpCob) {
		this.fecCierreOpCob = fecCierreOpCob;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

}
