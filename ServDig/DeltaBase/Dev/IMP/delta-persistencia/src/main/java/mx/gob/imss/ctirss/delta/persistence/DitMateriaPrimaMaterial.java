package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_MATERIA_PRIMA_MATERIAL database table.
 * 
 */
@Entity
@Table(name="DIT_MATERIA_PRIMA_MATERIAL")
public class DitMateriaPrimaMaterial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DIT_MATERIA_PRIMA_MATERIAL_GENERATOR", sequenceName = "SEQ_DITMATERIAPRIMAMATERIAL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_MATERIA_PRIMA_MATERIAL_GENERATOR")
	@Column(name="CVE_ID_MATERIA_PRIMA_MATERIAL", nullable=false, precision=22)
	private long cveIdMateriaPrimaMaterial;

	@Column(name="DES_MATERIA_PRIMA_MATERIAL", length=255)
	private String desMateriaPrimaMaterial;

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

    public DitMateriaPrimaMaterial() {
    }

	public long getCveIdMateriaPrimaMaterial() {
		return this.cveIdMateriaPrimaMaterial;
	}

	public void setCveIdMateriaPrimaMaterial(long cveIdMateriaPrimaMaterial) {
		this.cveIdMateriaPrimaMaterial = cveIdMateriaPrimaMaterial;
	}

	public String getDesMateriaPrimaMaterial() {
		return this.desMateriaPrimaMaterial;
	}

	public void setDesMateriaPrimaMaterial(String desMateriaPrimaMaterial) {
		this.desMateriaPrimaMaterial = desMateriaPrimaMaterial;
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