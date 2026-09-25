package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DIT_MOVTOS_AUSENTISMO database table.
 * 
 */
@Entity
@Table(name="DIT_MOVTOS_AUSENTISMO")
public class DitMovtosAusentismo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DitMovtosAusentismoPK id;

	@Column(name="CVE_NSS", length=50)
	private String cveNss;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FINAL")
	private Date fecFinal;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INICIO")
	private Date fecInicio;

	@Column(name="NUM_DIAS_AUSENTISMO", precision=1)
	private BigDecimal numDiasAusentismo;

	@Column(name="NUM_TRABAJADOR", precision=10)
	private BigDecimal numTrabajador;

	//bi-directional many-to-one association to DitPatronSujetoObligado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO", nullable=false, insertable=false, updatable=false)
	private DitPatronSujetoObligado ditPatronSujetoObligado;

	//bi-directional many-to-one association to DitAsegurado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_ASEGURADO", nullable=false, insertable=false, updatable=false)
	private DitAsegurado ditAsegurado;

    public DitMovtosAusentismo() {
    }

	public DitMovtosAusentismoPK getId() {
		return this.id;
	}

	public void setId(DitMovtosAusentismoPK id) {
		this.id = id;
	}
	
	public String getCveNss() {
		return this.cveNss;
	}

	public void setCveNss(String cveNss) {
		this.cveNss = cveNss;
	}

	public Date getFecFinal() {
		return this.fecFinal;
	}

	public void setFecFinal(Date fecFinal) {
		this.fecFinal = fecFinal;
	}

	public Date getFecInicio() {
		return this.fecInicio;
	}

	public void setFecInicio(Date fecInicio) {
		this.fecInicio = fecInicio;
	}

	public BigDecimal getNumDiasAusentismo() {
		return this.numDiasAusentismo;
	}

	public void setNumDiasAusentismo(BigDecimal numDiasAusentismo) {
		this.numDiasAusentismo = numDiasAusentismo;
	}

	public BigDecimal getNumTrabajador() {
		return this.numTrabajador;
	}

	public void setNumTrabajador(BigDecimal numTrabajador) {
		this.numTrabajador = numTrabajador;
	}

	public DitPatronSujetoObligado getDitPatronSujetoObligado() {
		return this.ditPatronSujetoObligado;
	}

	public void setDitPatronSujetoObligado(DitPatronSujetoObligado ditPatronSujetoObligado) {
		this.ditPatronSujetoObligado = ditPatronSujetoObligado;
	}
	
	public DitAsegurado getDitAsegurado() {
		return this.ditAsegurado;
	}

	public void setDitAsegurado(DitAsegurado ditAsegurado) {
		this.ditAsegurado = ditAsegurado;
	}
	
}