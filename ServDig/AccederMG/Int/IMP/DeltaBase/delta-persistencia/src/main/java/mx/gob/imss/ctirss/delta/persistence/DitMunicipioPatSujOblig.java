package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DIT_MUNICIPIO_PAT_SUJ_OBLIG database table.
 * 
 */
@Entity
@Table(name="DIT_MUNICIPIO_PAT_SUJ_OBLIG")
public class DitMunicipioPatSujOblig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_PATRON_SUJETO_OBLIGADO")
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

	@Column(name="IND_MIGR_MUNIC")
	private BigDecimal indMigrMunic;

	//bi-directional many-to-one association to DicMunicipioImss
    @ManyToOne
	@JoinColumn(name="CVE_ID_MUNICIPIO_IMSS")
	private DicMunicipioImss dicMunicipioImss;

	//bi-directional one-to-one association to DitPatronSujetoObligado
	@OneToOne
	@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO")
	private DitPatronSujetoObligado ditPatronSujetoObligado;

    public DitMunicipioPatSujOblig() {
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

	public BigDecimal getIndMigrMunic() {
		return this.indMigrMunic;
	}

	public void setIndMigrMunic(BigDecimal indMigrMunic) {
		this.indMigrMunic = indMigrMunic;
	}

	public DicMunicipioImss getDicMunicipioImss() {
		return this.dicMunicipioImss;
	}

	public void setDicMunicipioImss(DicMunicipioImss dicMunicipioImss) {
		this.dicMunicipioImss = dicMunicipioImss;
	}
	
	public DitPatronSujetoObligado getDitPatronSujetoObligado() {
		return this.ditPatronSujetoObligado;
	}

	public void setDitPatronSujetoObligado(DitPatronSujetoObligado ditPatronSujetoObligado) {
		this.ditPatronSujetoObligado = ditPatronSujetoObligado;
	}
	
}