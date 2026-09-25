package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the FDT_DICTAMENES_RECHAZADOS database table.
 * 
 */
@Entity
@Table(name="FDT_DICTAMENES_RECHAZADOS")
public class FdtDictamenesRechazado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_RECHAZO", nullable=false, precision=22)
	private long idRechazo;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_RECHAZO")
	private Date fhRechazo;

    @Lob()
	@Column(name="TX_CAUSAS_RECHAZO")
	private byte[] txCausasRechazo;

	//bi-directional many-to-one association to FdtAviso
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_AVISO", nullable=false)
	private FdtAviso fdtAviso;

    public FdtDictamenesRechazado() {
    }

	public long getIdRechazo() {
		return this.idRechazo;
	}

	public void setIdRechazo(long idRechazo) {
		this.idRechazo = idRechazo;
	}

	public Date getFhRechazo() {
		return this.fhRechazo;
	}

	public void setFhRechazo(Date fhRechazo) {
		this.fhRechazo = fhRechazo;
	}

	public byte[] getTxCausasRechazo() {
		return this.txCausasRechazo;
	}

	public void setTxCausasRechazo(byte[] txCausasRechazo) {
		this.txCausasRechazo = txCausasRechazo != null ? txCausasRechazo.clone() : null;
	}

	public FdtAviso getFdtAviso() {
		return this.fdtAviso;
	}

	public void setFdtAviso(FdtAviso fdtAviso) {
		this.fdtAviso = fdtAviso;
	}
	
}