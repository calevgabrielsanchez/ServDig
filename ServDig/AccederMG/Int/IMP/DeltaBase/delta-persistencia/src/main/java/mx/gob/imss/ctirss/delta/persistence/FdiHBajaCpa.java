package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the FDI_H_BAJA_CPA database table.
 * 
 */
@Entity
@Table(name="FDI_H_BAJA_CPA")
public class FdiHBajaCpa implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdiHBajaCpaPK id;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_BAJA", nullable=false)
	private Date fhBaja;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_REACTIVA")
	private Date fhReactiva;

	@Column(name="IN_BAJA", nullable=false, length=1)
	private String inBaja;

	@Column(name="TX_CAUSAS", length=1600)
	private String txCausas;

	//bi-directional many-to-one association to FdiCpa
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CV_CURP", nullable=false, insertable=false, updatable=false)
	private FdiCpa fdiCpa;

    public FdiHBajaCpa() {
    }

	public FdiHBajaCpaPK getId() {
		return this.id;
	}

	public void setId(FdiHBajaCpaPK id) {
		this.id = id;
	}
	
	public Date getFhBaja() {
		return this.fhBaja;
	}

	public void setFhBaja(Date fhBaja) {
		this.fhBaja = fhBaja;
	}

	public Date getFhReactiva() {
		return this.fhReactiva;
	}

	public void setFhReactiva(Date fhReactiva) {
		this.fhReactiva = fhReactiva;
	}

	public String getInBaja() {
		return this.inBaja;
	}

	public void setInBaja(String inBaja) {
		this.inBaja = inBaja;
	}

	public String getTxCausas() {
		return this.txCausas;
	}

	public void setTxCausas(String txCausas) {
		this.txCausas = txCausas;
	}

	public FdiCpa getFdiCpa() {
		return this.fdiCpa;
	}

	public void setFdiCpa(FdiCpa fdiCpa) {
		this.fdiCpa = fdiCpa;
	}
	
}