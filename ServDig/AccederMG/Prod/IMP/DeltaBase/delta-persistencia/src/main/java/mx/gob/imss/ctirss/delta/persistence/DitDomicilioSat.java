package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the DIT_DOMICILIO_SAT database table.
 * 
 */
@Entity
@Table(name = "DIT_DOMICILIO_SAT")
public class DitDomicilioSat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIT_DOMICILIO_SAT_CVEIDDOMICILIO_GENERATOR", sequenceName="SEQ_DITDOMICILIOSAT", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIT_DOMICILIO_SAT_CVEIDDOMICILIO_GENERATOR")
	@Column(name = "CVE_ID_DOMICILIO")
	private long cveIdDomicilio;

	private String calle;

	private String codigo;

	private String colonia;

	@Column(name = "ENTIDAD_FEDERATIVA")
	private String entidadFederativa;

	@Column(name = "ENTRE_CALLE_1")
	private String entreCalle1;

	@Column(name = "ENTRE_CALLE_2")
	private String entreCalle2;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	private String inmueble;

	private String localidad;

	private String municipio;

	@Column(name = "NUM_EXTERIOR")
	private String numExterior;

	@Column(name = "NUM_INTERIOR")
	private String numInterior;

	private String referencia;

	private String vialidad;

	// bi-directional many-to-one association to DitPersonaFDomFiscal
	@OneToMany(mappedBy = "ditDomicilioSat")
	private List<DitPersonaFDomFiscal> ditPersonaFDomFiscales;

	// bi-directional many-to-one association to DitPersonaMDomFiscal
	@OneToMany(mappedBy = "ditDomicilioSat")
	private List<DitPersonaMDomFiscal> ditPersonaMDomFiscales;

	public DitDomicilioSat() {
	}

	public long getCveIdDomicilio() {
		return this.cveIdDomicilio;
	}

	public void setCveIdDomicilio(long cveIdDomicilio) {
		this.cveIdDomicilio = cveIdDomicilio;
	}

	public String getCalle() {
		return this.calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getColonia() {
		return this.colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getEntidadFederativa() {
		return this.entidadFederativa;
	}

	public void setEntidadFederativa(String entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}

	public String getEntreCalle1() {
		return this.entreCalle1;
	}

	public void setEntreCalle1(String entreCalle1) {
		this.entreCalle1 = entreCalle1;
	}

	public String getEntreCalle2() {
		return this.entreCalle2;
	}

	public void setEntreCalle2(String entreCalle2) {
		this.entreCalle2 = entreCalle2;
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

	public String getInmueble() {
		return this.inmueble;
	}

	public void setInmueble(String inmueble) {
		this.inmueble = inmueble;
	}

	public String getLocalidad() {
		return this.localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getMunicipio() {
		return this.municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getNumExterior() {
		return this.numExterior;
	}

	public void setNumExterior(String numExterior) {
		this.numExterior = numExterior;
	}

	public String getNumInterior() {
		return this.numInterior;
	}

	public void setNumInterior(String numInterior) {
		this.numInterior = numInterior;
	}

	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getVialidad() {
		return this.vialidad;
	}

	public void setVialidad(String vialidad) {
		this.vialidad = vialidad;
	}

	public List<DitPersonaFDomFiscal> getDitPersonafDomFiscals() {
		return this.ditPersonaFDomFiscales;
	}

	public void setDitPersonafDomFiscals(
			List<DitPersonaFDomFiscal> ditPersonaFDomFiscales) {
		this.ditPersonaFDomFiscales = ditPersonaFDomFiscales;
	}

	public List<DitPersonaMDomFiscal> getDitPersonamDomFiscals() {
		return this.ditPersonaMDomFiscales;
	}

	public void setDitPersonamDomFiscals(
			List<DitPersonaMDomFiscal> ditPersonaMDomFiscales) {
		this.ditPersonaMDomFiscales = ditPersonaMDomFiscales;
	}

}