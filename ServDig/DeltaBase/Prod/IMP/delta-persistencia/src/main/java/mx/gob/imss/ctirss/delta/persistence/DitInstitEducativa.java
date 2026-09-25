package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_INSTIT_EDUCATIVA database table.
 * 
 */
@Entity
@Table(name="DIT_INSTIT_EDUCATIVA")
public class DitInstitEducativa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_PATRON_SUJETO_OBLIGADO", nullable=false, precision=22)
	private long cveIdPatronSujetoObligado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NOM_INSTITUCION_NIVEL", length=255)
	private String nomInstitucionNivel;

	//bi-directional many-to-one association to DgDomicilioGeografico
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DOMICILIO_ID")
	private DgDomicilioGeografico dgDomicilioGeografico;

	//bi-directional many-to-one association to DicNivelEducativo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_NIVEL_EDUCATIVO")
	private DicNivelEducativo dicNivelEducativo;

	//bi-directional one-to-one association to DitPatronSujetoObligado
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO", nullable=false, insertable=false, updatable=false)
	private DitPatronSujetoObligado ditPatronSujetoObligado;

    public DitInstitEducativa() {
    }

	public long getCveIdPatronSujetoObligado() {
		return this.cveIdPatronSujetoObligado;
	}

	public void setCveIdPatronSujetoObligado(long cveIdPatronSujetoObligado) {
		this.cveIdPatronSujetoObligado = cveIdPatronSujetoObligado;
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

	public String getNomInstitucionNivel() {
		return this.nomInstitucionNivel;
	}

	public void setNomInstitucionNivel(String nomInstitucionNivel) {
		this.nomInstitucionNivel = nomInstitucionNivel;
	}

	public DgDomicilioGeografico getDgDomicilioGeografico() {
		return this.dgDomicilioGeografico;
	}

	public void setDgDomicilioGeografico(DgDomicilioGeografico dgDomicilioGeografico) {
		this.dgDomicilioGeografico = dgDomicilioGeografico;
	}
	
	public DicNivelEducativo getDicNivelEducativo() {
		return this.dicNivelEducativo;
	}

	public void setDicNivelEducativo(DicNivelEducativo dicNivelEducativo) {
		this.dicNivelEducativo = dicNivelEducativo;
	}
	
	public DitPatronSujetoObligado getDitPatronSujetoObligado() {
		return this.ditPatronSujetoObligado;
	}

	public void setDitPatronSujetoObligado(DitPatronSujetoObligado ditPatronSujetoObligado) {
		this.ditPatronSujetoObligado = ditPatronSujetoObligado;
	}
	
}