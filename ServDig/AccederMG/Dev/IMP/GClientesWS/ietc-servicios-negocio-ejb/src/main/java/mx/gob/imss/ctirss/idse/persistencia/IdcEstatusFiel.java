package mx.gob.imss.ctirss.idse.persistencia;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the IDC_ESTATUS_FIEL database table.
 * 
 */
@Entity
@Table(name="IDC_ESTATUS_FIEL")
public class IdcEstatusFiel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ESTATUS_FIEL")
	private long cveEstatusFiel;

	@Column(name="NUM_ESTATUS_FIEL")
	private BigDecimal numEstatusFiel;

	@Column(name="REF_ESTATUS_FIEL")
	private String refEstatusFiel;

	//bi-directional many-to-one association to IdtDatosCertificado
	@OneToMany(mappedBy="idcEstatusFiel")
	private List<IdtDatosCertificado> idtDatosCertificados;

    public IdcEstatusFiel() {
    }

	public long getCveEstatusFiel() {
		return this.cveEstatusFiel;
	}

	public void setCveEstatusFiel(long cveEstatusFiel) {
		this.cveEstatusFiel = cveEstatusFiel;
	}

	public BigDecimal getNumEstatusFiel() {
		return this.numEstatusFiel;
	}

	public void setNumEstatusFiel(BigDecimal numEstatusFiel) {
		this.numEstatusFiel = numEstatusFiel;
	}

	public String getRefEstatusFiel() {
		return this.refEstatusFiel;
	}

	public void setRefEstatusFiel(String refEstatusFiel) {
		this.refEstatusFiel = refEstatusFiel;
	}

	public List<IdtDatosCertificado> getIdtDatosCertificados() {
		return this.idtDatosCertificados;
	}

	public void setIdtDatosCertificados(List<IdtDatosCertificado> idtDatosCertificados) {
		this.idtDatosCertificados = idtDatosCertificados;
	}
	
}