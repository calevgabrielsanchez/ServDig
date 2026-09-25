package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_MUNICIPIO_IMSS_INEGI database table.
 * 
 */
@Entity
@Table(name="DIT_MUNICIPIO_IMSS_INEGI")
public class DitMunicipioImssInegi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_MUNICIPIO_IMSS_INEGI", nullable=false, precision=22)
	private long cveIdMunicipioImssInegi;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicMunicipioImss
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_MUNICIPIO_IMSS")
	private DicMunicipioImss dicMunicipioImss;

	//bi-directional many-to-one association to DgCatMunicipio
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_ENT", referencedColumnName="CVE_ENT"),
		@JoinColumn(name="CVE_MUN", referencedColumnName="CVE_MUN")
		})
	private DgCatMunicipio dgCatMunicipio;

    public DitMunicipioImssInegi() {
    }

	public long getCveIdMunicipioImssInegi() {
		return this.cveIdMunicipioImssInegi;
	}

	public void setCveIdMunicipioImssInegi(long cveIdMunicipioImssInegi) {
		this.cveIdMunicipioImssInegi = cveIdMunicipioImssInegi;
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

	public DicMunicipioImss getDicMunicipioImss() {
		return this.dicMunicipioImss;
	}

	public void setDicMunicipioImss(DicMunicipioImss dicMunicipioImss) {
		this.dicMunicipioImss = dicMunicipioImss;
	}
	
	public DgCatMunicipio getDgCatMunicipio() {
		return this.dgCatMunicipio;
	}

	public void setDgCatMunicipio(DgCatMunicipio dgCatMunicipio) {
		this.dgCatMunicipio = dgCatMunicipio;
	}
	
}