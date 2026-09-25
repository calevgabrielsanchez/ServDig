package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_PROCESO database table.
 * 
 */
@Entity
@Table(name="DIT_PROCESO")
public class DitProceso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DIT_PROCESO_GENERATOR", sequenceName = "SEQ_DITPROCESO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_PROCESO_GENERATOR")
	@Column(name="CVE_ID_PROCESO", nullable=false, precision=22)
	private long cveIdProceso;

	@Column(name="DES_FINAL", length=500)
	private String desFinal;

	@Column(name="DES_INICIAL", length=500)
	private String desInicial;

	@Column(name="DES_INTERMEDIO", length=500)
	private String desIntermedio;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitPatronSujetoObligado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO")
	private DitPatronSujetoObligado ditPatronSujetoObligado;

    public DitProceso() {
    }

	public long getCveIdProceso() {
		return this.cveIdProceso;
	}

	public void setCveIdProceso(long cveIdProceso) {
		this.cveIdProceso = cveIdProceso;
	}

	public String getDesFinal() {
		return this.desFinal;
	}

	public void setDesFinal(String desFinal) {
		this.desFinal = desFinal;
	}

	public String getDesInicial() {
		return this.desInicial;
	}

	public void setDesInicial(String desInicial) {
		this.desInicial = desInicial;
	}

	public String getDesIntermedio() {
		return this.desIntermedio;
	}

	public void setDesIntermedio(String desIntermedio) {
		this.desIntermedio = desIntermedio;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public DitPatronSujetoObligado getDitPatronSujetoObligado() {
		return this.ditPatronSujetoObligado;
	}

	public void setDitPatronSujetoObligado(DitPatronSujetoObligado ditPatronSujetoObligado) {
		this.ditPatronSujetoObligado = ditPatronSujetoObligado;
	}
	
}