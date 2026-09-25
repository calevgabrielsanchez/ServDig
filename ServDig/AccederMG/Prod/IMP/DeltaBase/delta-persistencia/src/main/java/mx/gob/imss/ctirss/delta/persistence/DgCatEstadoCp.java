package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the DG_CAT_ESTADO_CP database table.
 * 
 */
@Entity
@Table(name="DG_CAT_ESTADO_CP")
public class DgCatEstadoCp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ENT", nullable=false, length=2)
	private String cveEnt;

	@Column(name="CP_FINAL", nullable=false, length=5)
	private String cpFinal;

	@Column(name="CP_INICIAL", nullable=false, length=5)
	private String cpInicial;

	//bi-directional one-to-one association to DgCatEstado
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ENT", nullable=false, insertable=false, updatable=false)
	private DgCatEstado dgCatEstado;

    public DgCatEstadoCp() {
    }

	public String getCveEnt() {
		return this.cveEnt;
	}

	public void setCveEnt(String cveEnt) {
		this.cveEnt = cveEnt;
	}

	public String getCpFinal() {
		return this.cpFinal;
	}

	public void setCpFinal(String cpFinal) {
		this.cpFinal = cpFinal;
	}

	public String getCpInicial() {
		return this.cpInicial;
	}

	public void setCpInicial(String cpInicial) {
		this.cpInicial = cpInicial;
	}

	public DgCatEstado getDgCatEstado() {
		return this.dgCatEstado;
	}

	public void setDgCatEstado(DgCatEstado dgCatEstado) {
		this.dgCatEstado = dgCatEstado;
	}
	
}