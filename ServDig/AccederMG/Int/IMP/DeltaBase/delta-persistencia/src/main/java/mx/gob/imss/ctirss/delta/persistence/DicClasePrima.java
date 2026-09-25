package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_CLASE_PRIMA database table.
 * 
 */
@Entity
@Table(name="DIC_CLASE_PRIMA")
public class DicClasePrima implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_CLASE_PRIMA", nullable=false, precision=22)
	private long cveIdClasePrima;

	@Column(name="DES_CLASE", length=50)
	private String desClase;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FIN")
	private Date fecFin;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INI")
	private Date fecIni;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IND_PRIMA_MEDIA", precision=22)
	private BigDecimal indPrimaMedia;

	@Column(name="NUM_GRADO_RIESGO", precision=22)
	private BigDecimal numGradoRiesgo;

	@Column(name="NUM_PORCENTAJE", precision=18, scale=15)
	private BigDecimal numPorcentaje;
	
	//bi-directional many-to-one association to DitCuotasPatronSujetoOblig
	@OneToMany(mappedBy="dicClasePrima")
	private List<DitCuotasPatronSujetoOblig> ditCuotasPatronSujetoObligs;

	//bi-directional many-to-one association to DicClase
    @ManyToOne
	@JoinColumn(name="CVE_ID_CLASE")
	private DicClase dicClase;

	
    public DicClasePrima() {
    }

	public long getCveIdClasePrima() {
		return this.cveIdClasePrima;
	}

	public void setCveIdClasePrima(long cveIdClasePrima) {
		this.cveIdClasePrima = cveIdClasePrima;
	}

	public String getDesClase() {
		return this.desClase;
	}

	public void setDesClase(String desClase) {
		this.desClase = desClase;
	}

	public Date getFecFin() {
		return this.fecFin;
	}

	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
	}

	public Date getFecIni() {
		return this.fecIni;
	}

	public void setFecIni(Date fecIni) {
		this.fecIni = fecIni;
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

	public BigDecimal getIndPrimaMedia() {
		return this.indPrimaMedia;
	}

	public void setIndPrimaMedia(BigDecimal indPrimaMedia) {
		this.indPrimaMedia = indPrimaMedia;
	}

	public BigDecimal getNumGradoRiesgo() {
		return this.numGradoRiesgo;
	}

	public void setNumGradoRiesgo(BigDecimal numGradoRiesgo) {
		this.numGradoRiesgo = numGradoRiesgo;
	}

	public BigDecimal getNumPorcentaje() {
		return this.numPorcentaje;
	}

	public void setNumPorcentaje(BigDecimal numPorcentaje) {
		this.numPorcentaje = numPorcentaje;
	}

	public List<DitCuotasPatronSujetoOblig> getDitCuotasPatronSujetoObligs() {
		return this.ditCuotasPatronSujetoObligs;
	}

	public void setDitCuotasPatronSujetoObligs(List<DitCuotasPatronSujetoOblig> ditCuotasPatronSujetoObligs) {
		this.ditCuotasPatronSujetoObligs = ditCuotasPatronSujetoObligs;
	}

	public DicClase getDicClase() {
		return dicClase;
	}

	public void setDicClase(DicClase dicClase) {
		this.dicClase = dicClase;
	}
	
	
	
}