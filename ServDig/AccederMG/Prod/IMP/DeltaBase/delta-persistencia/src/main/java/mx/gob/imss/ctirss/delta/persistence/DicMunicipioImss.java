package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_MUNICIPIO_IMSS database table.
 * 
 */
@Entity
@Table(name="DIC_MUNICIPIO_IMSS")
public class DicMunicipioImss implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_MUNICIPIO_IMSS", nullable=false, precision=22)
	private long cveIdMunicipioImss;

	@Column(name="CVE_MUNICIPIO", length=50)
	private String cveMunicipio;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INICIO_SERVICIO_CAMP")
	private Date fecInicioServicioCamp;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INICIO_SERVICIO_URB")
	private Date fecInicioServicioUrb;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
    
    @Temporal ( TemporalType.DATE)
    @Column(name = "FEC_BAJA_VIGENCIA")
    private Date fecBajaVigencia;

	@Column(name="NOM_MUNICIPIO_IMSS", length=50)
	private String nomMunicipioImss;

	//bi-directional many-to-one association to DicTipoAmbito
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_AMBITO")
	private DicTipoAmbito dicTipoAmbito;

	//bi-directional many-to-one association to DicAreaGeografica
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_AREA_GEOGRAFICA")
	private DicAreaGeografica dicAreaGeografica;

	//bi-directional many-to-one association to DgCatEstado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ENT")
	private DgCatEstado dgCatEstado;

	//bi-directional many-to-one association to DitMunicipioImssInegi
	@OneToMany(mappedBy="dicMunicipioImss")
	private List<DitMunicipioImssInegi> ditMunicipioImssInegis;

	//bi-directional many-to-one association to DitMunicipioSubdelegacion
	@OneToMany(mappedBy="dicMunicipioImss")
	private List<DitMunicipioSubdelegacion> ditMunicipioSubdelegacions;
	
	@Column(name="IND_CONVENIO")
	private Integer indConvenio;
	
	
    public DicMunicipioImss() {
    }

	public long getCveIdMunicipioImss() {
		return this.cveIdMunicipioImss;
	}

	public void setCveIdMunicipioImss(long cveIdMunicipioImss) {
		this.cveIdMunicipioImss = cveIdMunicipioImss;
	}

	public String getCveMunicipio() {
		return this.cveMunicipio;
	}

	public void setCveMunicipio(String cveMunicipio) {
		this.cveMunicipio = cveMunicipio;
	}

	public Date getFecInicioServicioCamp() {
		return this.fecInicioServicioCamp;
	}

	public void setFecInicioServicioCamp(Date fecInicioServicioCamp) {
		this.fecInicioServicioCamp = fecInicioServicioCamp;
	}

	public Date getFecInicioServicioUrb() {
		return this.fecInicioServicioUrb;
	}

	public void setFecInicioServicioUrb(Date fecInicioServicioUrb) {
		this.fecInicioServicioUrb = fecInicioServicioUrb;
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

	public String getNomMunicipioImss() {
		return this.nomMunicipioImss;
	}

	public void setNomMunicipioImss(String nomMunicipioImss) {
		this.nomMunicipioImss = nomMunicipioImss;
	}

	public DicTipoAmbito getDicTipoAmbito() {
		return this.dicTipoAmbito;
	}

	public void setDicTipoAmbito(DicTipoAmbito dicTipoAmbito) {
		this.dicTipoAmbito = dicTipoAmbito;
	}
	
	public DicAreaGeografica getDicAreaGeografica() {
		return this.dicAreaGeografica;
	}

	public void setDicAreaGeografica(DicAreaGeografica dicAreaGeografica) {
		this.dicAreaGeografica = dicAreaGeografica;
	}
	
	public DgCatEstado getDgCatEstado() {
		return this.dgCatEstado;
	}

	public void setDgCatEstado(DgCatEstado dgCatEstado) {
		this.dgCatEstado = dgCatEstado;
	}
	
	public List<DitMunicipioImssInegi> getDitMunicipioImssInegis() {
		return this.ditMunicipioImssInegis;
	}

	public void setDitMunicipioImssInegis(List<DitMunicipioImssInegi> ditMunicipioImssInegis) {
		this.ditMunicipioImssInegis = ditMunicipioImssInegis;
	}
	
	public List<DitMunicipioSubdelegacion> getDitMunicipioSubdelegacions() {
		return this.ditMunicipioSubdelegacions;
	}

	public void setDitMunicipioSubdelegacions(List<DitMunicipioSubdelegacion> ditMunicipioSubdelegacions) {
		this.ditMunicipioSubdelegacions = ditMunicipioSubdelegacions;
	}

	public Integer getIndConvenio() {
		return indConvenio;
	}

	public void setIndConvenio(Integer indConvenio) {
		this.indConvenio = indConvenio;
	}

	public Date getFecBajaVigencia() {
		return fecBajaVigencia;
	}

	public void setFecBajaVigencia(Date fecBajaVigencia) {
		this.fecBajaVigencia = fecBajaVigencia;
	}
	
}