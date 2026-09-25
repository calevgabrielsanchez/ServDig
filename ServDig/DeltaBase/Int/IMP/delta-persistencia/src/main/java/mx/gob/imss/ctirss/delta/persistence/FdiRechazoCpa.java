package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the FDI_RECHAZO_CPA database table.
 * 
 */
@Entity
@Table(name="FDI_RECHAZO_CPA")
public class FdiRechazoCpa implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CV_CURP", nullable=false, length=18)
	private String cvCurp;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_RECHAZO", nullable=false)
	private Date fhRechazo;

	@Column(name="TIPO_RECHAZO", nullable=false, precision=22)
	private BigDecimal tipoRechazo;

	@Column(name="TX_CAUSAS_RECHAZO", length=1600)
	private String txCausasRechazo;

    public FdiRechazoCpa() {
    }

	public String getCvCurp() {
		return this.cvCurp;
	}

	public void setCvCurp(String cvCurp) {
		this.cvCurp = cvCurp;
	}

	public Date getFhRechazo() {
		return this.fhRechazo;
	}

	public void setFhRechazo(Date fhRechazo) {
		this.fhRechazo = fhRechazo;
	}

	public BigDecimal getTipoRechazo() {
		return this.tipoRechazo;
	}

	public void setTipoRechazo(BigDecimal tipoRechazo) {
		this.tipoRechazo = tipoRechazo;
	}

	public String getTxCausasRechazo() {
		return this.txCausasRechazo;
	}

	public void setTxCausasRechazo(String txCausasRechazo) {
		this.txCausasRechazo = txCausasRechazo;
	}

}