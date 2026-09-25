package mx.gob.imss.ctirss.idse.persistencia;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the IDC_ESTATUS_REG_PAT database table.
 * 
 */
@Entity
@Table(name="IDC_ESTATUS_REG_PAT")
public class IdcEstatusRegPat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ESTATUS_REG_PAT")
	private long cveEstatusRegPat;

	@Column(name="NUM_ESTATUS_REG_PAT")
	private BigDecimal numEstatusRegPat;

	@Column(name="REF_ESTATUS_REG_PAT")
	private String refEstatusRegPat;

	//bi-directional many-to-one association to IdtRegistrosPatronale
	@OneToMany(mappedBy="idcEstatusRegPat")
	private List<IdtRegistrosPatronale> idtRegistrosPatronales;

    public IdcEstatusRegPat() {
    }

	public long getCveEstatusRegPat() {
		return this.cveEstatusRegPat;
	}

	public void setCveEstatusRegPat(long cveEstatusRegPat) {
		this.cveEstatusRegPat = cveEstatusRegPat;
	}

	public BigDecimal getNumEstatusRegPat() {
		return this.numEstatusRegPat;
	}

	public void setNumEstatusRegPat(BigDecimal numEstatusRegPat) {
		this.numEstatusRegPat = numEstatusRegPat;
	}

	public String getRefEstatusRegPat() {
		return this.refEstatusRegPat;
	}

	public void setRefEstatusRegPat(String refEstatusRegPat) {
		this.refEstatusRegPat = refEstatusRegPat;
	}

	public List<IdtRegistrosPatronale> getIdtRegistrosPatronales() {
		return this.idtRegistrosPatronales;
	}

	public void setIdtRegistrosPatronales(List<IdtRegistrosPatronale> idtRegistrosPatronales) {
		this.idtRegistrosPatronales = idtRegistrosPatronales;
	}
	
}