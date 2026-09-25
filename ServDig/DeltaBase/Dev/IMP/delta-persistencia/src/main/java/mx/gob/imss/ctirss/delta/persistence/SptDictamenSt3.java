package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SPT_DICTAMEN_ST3 database table.
 * 
 */
@Entity
@Table(name="SPT_DICTAMEN_ST3")
@NamedQuery(name="SptDictamenSt3.findAll", query="SELECT s FROM SptDictamenSt3 s")
public class SptDictamenSt3 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTDICTAMENST3", sequenceName = "SEQ_SPTDICTAMENST3")
	@GeneratedValue(generator = "SEQ_SPTDICTAMENST3")	
	@Column(name="CVE_ID_DICTAMEN")
	private long cveIdDictamen;

	private BigDecimal cveriesgotrabajo;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_ACCIDENTE")
	private Date fecAccidente;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_CERTIFICACION")
	private Date fecCertificacion;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_DEFUNCION")
	private Date fecDefuncion;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO_PENSION")
	private Date fecInicioPension;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REVALORACION")
	private Date fecRevaloracion;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_SOL_CERTIFICACION")
	private Date fecSolCertificacion;

	@Column(name="IND_DEFUNCION")
	private String indDefuncion;

	@Column(name="POR_INCAPACIDAD_ORG")
	private BigDecimal porIncapacidadOrg;

	//bi-directional many-to-one association to SptDiagnosticoSt3
	@OneToMany(mappedBy="sptDictamenSt3")
	private List<SptDiagnosticoSt3> sptDiagnosticoSt3s;

	//bi-directional one-to-one association to SptDictamen
	@OneToOne
	@JoinColumn(name="CVE_ID_DICTAMEN")
	private SptDictamen sptDictamen;

	public SptDictamenSt3() {
	}

	public long getCveIdDictamen() {
		return this.cveIdDictamen;
	}

	public void setCveIdDictamen(long cveIdDictamen) {
		this.cveIdDictamen = cveIdDictamen;
	}

	public BigDecimal getCveriesgotrabajo() {
		return this.cveriesgotrabajo;
	}

	public void setCveriesgotrabajo(BigDecimal cveriesgotrabajo) {
		this.cveriesgotrabajo = cveriesgotrabajo;
	}

	public Date getFecAccidente() {
		return this.fecAccidente;
	}

	public void setFecAccidente(Date fecAccidente) {
		this.fecAccidente = fecAccidente;
	}

	public Date getFecCertificacion() {
		return this.fecCertificacion;
	}

	public void setFecCertificacion(Date fecCertificacion) {
		this.fecCertificacion = fecCertificacion;
	}

	public Date getFecDefuncion() {
		return this.fecDefuncion;
	}

	public void setFecDefuncion(Date fecDefuncion) {
		this.fecDefuncion = fecDefuncion;
	}

	public Date getFecInicioPension() {
		return this.fecInicioPension;
	}

	public void setFecInicioPension(Date fecInicioPension) {
		this.fecInicioPension = fecInicioPension;
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

	public Date getFecRevaloracion() {
		return this.fecRevaloracion;
	}

	public void setFecRevaloracion(Date fecRevaloracion) {
		this.fecRevaloracion = fecRevaloracion;
	}

	public Date getFecSolCertificacion() {
		return this.fecSolCertificacion;
	}

	public void setFecSolCertificacion(Date fecSolCertificacion) {
		this.fecSolCertificacion = fecSolCertificacion;
	}

	public String getIndDefuncion() {
		return this.indDefuncion;
	}

	public void setIndDefuncion(String indDefuncion) {
		this.indDefuncion = indDefuncion;
	}

	public BigDecimal getPorIncapacidadOrg() {
		return this.porIncapacidadOrg;
	}

	public void setPorIncapacidadOrg(BigDecimal porIncapacidadOrg) {
		this.porIncapacidadOrg = porIncapacidadOrg;
	}

	public List<SptDiagnosticoSt3> getSptDiagnosticoSt3s() {
		return this.sptDiagnosticoSt3s;
	}

	public void setSptDiagnosticoSt3s(List<SptDiagnosticoSt3> sptDiagnosticoSt3s) {
		this.sptDiagnosticoSt3s = sptDiagnosticoSt3s;
	}

	public SptDiagnosticoSt3 addSptDiagnosticoSt3(SptDiagnosticoSt3 sptDiagnosticoSt3) {
		getSptDiagnosticoSt3s().add(sptDiagnosticoSt3);
		sptDiagnosticoSt3.setSptDictamenSt3(this);

		return sptDiagnosticoSt3;
	}

	public SptDiagnosticoSt3 removeSptDiagnosticoSt3(SptDiagnosticoSt3 sptDiagnosticoSt3) {
		getSptDiagnosticoSt3s().remove(sptDiagnosticoSt3);
		sptDiagnosticoSt3.setSptDictamenSt3(null);

		return sptDiagnosticoSt3;
	}

	public SptDictamen getSptDictamen() {
		return this.sptDictamen;
	}

	public void setSptDictamen(SptDictamen sptDictamen) {
		this.sptDictamen = sptDictamen;
	}

}