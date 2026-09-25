package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SPT_IDENTIFICADOR_DICTAMEN database table.
 * 
 */
@Entity
@Table(name="SPT_IDENTIFICADOR_DICTAMEN")
@NamedQuery(name="SptIdentificadorDictamen.findAll", query="SELECT s FROM SptIdentificadorDictamen s")
public class SptIdentificadorDictamen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTIDENTIFICADORDICTAMEN", sequenceName = "SEQ_SPTIDENTIFICADORDICTAMEN")
	@GeneratedValue(generator = "SEQ_SPTIDENTIFICADORDICTAMEN")
	@Column(name="CVE_ID_IDENTIFICADOR_DICTAMEN")
	private long cveIdIdentificadorDictamen;

	@Column(name="CVE_OFICIO_DICTAMEN")
	private String cveOficioDictamen;

	@Column(name="CVE_ORIGEN")
	private String cveOrigen;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_FOLIO_DICTAMEN")
	private BigDecimal numFolioDictamen;

	//bi-directional many-to-one association to SptDictamen
	@ManyToOne
	@JoinColumn(name="CVE_ID_DICTAMEN")
	private SptDictamen sptDictamen;

	public SptIdentificadorDictamen() {
	}

	public long getCveIdIdentificadorDictamen() {
		return this.cveIdIdentificadorDictamen;
	}

	public void setCveIdIdentificadorDictamen(long cveIdIdentificadorDictamen) {
		this.cveIdIdentificadorDictamen = cveIdIdentificadorDictamen;
	}

	public String getCveOficioDictamen() {
		return this.cveOficioDictamen;
	}

	public void setCveOficioDictamen(String cveOficioDictamen) {
		this.cveOficioDictamen = cveOficioDictamen;
	}

	public String getCveOrigen() {
		return this.cveOrigen;
	}

	public void setCveOrigen(String cveOrigen) {
		this.cveOrigen = cveOrigen;
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

	public BigDecimal getNumFolioDictamen() {
		return this.numFolioDictamen;
	}

	public void setNumFolioDictamen(BigDecimal numFolioDictamen) {
		this.numFolioDictamen = numFolioDictamen;
	}

	public SptDictamen getSptDictamen() {
		return this.sptDictamen;
	}

	public void setSptDictamen(SptDictamen sptDictamen) {
		this.sptDictamen = sptDictamen;
	}

}