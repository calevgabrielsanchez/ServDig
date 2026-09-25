package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the DIT_LLAVE_PATRON database table.
 * 
 */
@Entity
@Table(name = "DIT_LLAVE_PATRON")
public class DitLlavePatron implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "REF_BUSCA", unique = true, nullable = false, precision = 10)
	private String refBusca;

	// bi-directional many-to-one association to DitPatronGeneral
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_PATRON_GENERAL")
	private DitPatronGeneral ditPatronGeneral;

	// bi-directional many-to-one association to DitPatronSujetoObligado
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_PATRON_SUJETO_OBLIGADO")
	private DitPatronSujetoObligado ditPatronSujetoObligado;

	// bi-directional many-to-one association to DitPersona
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_PERSONA")
	private DitPersona ditPersona;

	// bi-directional many-to-one association to DitPersonaFisica
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_PERSONA_FISICA")
	private DitPersonaFisica ditPersonaFisica;

	// bi-directional many-to-one association to DitPersonaMoral
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_PERSONA_MORAL")
	private DitPersonaMoral ditPersonaMoral;

	// bi-directional many-to-one association to DicTipoPersona
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TIP_PERSONA")
	private DicTipoPersona dicTipoPersona;

	public DitLlavePatron() {
	}

	public DitPatronGeneral getDitPatronGeneral() {
		return ditPatronGeneral;
	}

	public void setDitPatronGeneral(DitPatronGeneral ditPatronGeneral) {
		this.ditPatronGeneral = ditPatronGeneral;
	}

	public DitPatronSujetoObligado getDitPatronSujetoObligado() {
		return ditPatronSujetoObligado;
	}

	public void setDitPatronSujetoObligado(
			DitPatronSujetoObligado ditPatronSujetoObligado) {
		this.ditPatronSujetoObligado = ditPatronSujetoObligado;
	}

	public DitPersona getDitPersona() {
		return ditPersona;
	}

	public void setDitPersona(DitPersona ditPersona) {
		this.ditPersona = ditPersona;
	}

	public DitPersonaFisica getDitPersonaFisica() {
		return ditPersonaFisica;
	}

	public void setDitPersonaFisica(DitPersonaFisica ditPersonaFisica) {
		this.ditPersonaFisica = ditPersonaFisica;
	}

	public DitPersonaMoral getDitPersonaMoral() {
		return ditPersonaMoral;
	}

	public void setDitPersonaMoral(DitPersonaMoral ditPersonaMoral) {
		this.ditPersonaMoral = ditPersonaMoral;
	}

	public String getRefBusca() {
		return this.refBusca;
	}

	public void setRefBusca(String refBusca) {
		this.refBusca = refBusca;
	}

	public DicTipoPersona getDicTipoPersona() {
		return dicTipoPersona;
	}

	public void setDicTipoPersona(DicTipoPersona dicTipoPersona) {
		this.dicTipoPersona = dicTipoPersona;
	}
}