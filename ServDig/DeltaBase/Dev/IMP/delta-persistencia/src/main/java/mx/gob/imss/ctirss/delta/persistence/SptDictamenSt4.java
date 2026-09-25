package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SPT_DICTAMEN_ST4 database table.
 * 
 */
@Entity
@Table(name="SPT_DICTAMEN_ST4")
@NamedQuery(name="SptDictamenSt4.findAll", query="SELECT s FROM SptDictamenSt4 s")
public class SptDictamenSt4 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTDICTAMENST4", sequenceName = "SEQ_SPTDICTAMENST4")
	@GeneratedValue(generator = "SEQ_SPTDICTAMENST4")	
	@Column(name="CVE_ID_DICTAMEN")
	private long cveIdDictamen;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_CERTIFICACION")
	private Date fecCertificacion;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_FIN_PEN_ANT_295_2000")
	private Date fecFinPenAnt2952000;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO_INVALIDEZ")
	private Date fecInicioInvalidez;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO_PEN_ANT_295_2000")
	private Date fecInicioPenAnt2952000;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO_SUBSIDIO")
	private Date fecInicioSubsidio;

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

	@Column(name="IND_ACUERDO_295_2000")
	private String indAcuerdo2952000;

	@Column(name="IND_ART_123")
	private String indArt123;

	@Column(name="IND_EXISTENCIA_INVALIDEZ")
	private String indExistenciaInvalidez;

	@Column(name="IND_MAYOR75")
	private String indMayor75;

	@Column(name="NUM_DIAS_INCAP_PREVIOS")
	private BigDecimal numDiasIncapPrevios;

	@Column(name="POR_ART_140")
	private BigDecimal porArt140;

	@Column(name="POR_VALUACION")
	private BigDecimal porValuacion;

	//bi-directional many-to-one association to SptDiagnosticoSt4
	@OneToMany(mappedBy="sptDictamenSt4")
	private List<SptDiagnosticoSt4> sptDiagnosticoSt4s;

	//bi-directional one-to-one association to SptDictamen
	@OneToOne
	@JoinColumn(name="CVE_ID_DICTAMEN")
	private SptDictamen sptDictamen;

	public SptDictamenSt4() {
	}

	public long getCveIdDictamen() {
		return this.cveIdDictamen;
	}

	public void setCveIdDictamen(long cveIdDictamen) {
		this.cveIdDictamen = cveIdDictamen;
	}

	public Date getFecCertificacion() {
		return this.fecCertificacion;
	}

	public void setFecCertificacion(Date fecCertificacion) {
		this.fecCertificacion = fecCertificacion;
	}

	public Date getFecFinPenAnt2952000() {
		return this.fecFinPenAnt2952000;
	}

	public void setFecFinPenAnt2952000(Date fecFinPenAnt2952000) {
		this.fecFinPenAnt2952000 = fecFinPenAnt2952000;
	}

	public Date getFecInicioInvalidez() {
		return this.fecInicioInvalidez;
	}

	public void setFecInicioInvalidez(Date fecInicioInvalidez) {
		this.fecInicioInvalidez = fecInicioInvalidez;
	}

	public Date getFecInicioPenAnt2952000() {
		return this.fecInicioPenAnt2952000;
	}

	public void setFecInicioPenAnt2952000(Date fecInicioPenAnt2952000) {
		this.fecInicioPenAnt2952000 = fecInicioPenAnt2952000;
	}

	public Date getFecInicioSubsidio() {
		return this.fecInicioSubsidio;
	}

	public void setFecInicioSubsidio(Date fecInicioSubsidio) {
		this.fecInicioSubsidio = fecInicioSubsidio;
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

	public String getIndAcuerdo2952000() {
		return this.indAcuerdo2952000;
	}

	public void setIndAcuerdo2952000(String indAcuerdo2952000) {
		this.indAcuerdo2952000 = indAcuerdo2952000;
	}

	public String getIndArt123() {
		return this.indArt123;
	}

	public void setIndArt123(String indArt123) {
		this.indArt123 = indArt123;
	}

	public String getIndExistenciaInvalidez() {
		return this.indExistenciaInvalidez;
	}

	public void setIndExistenciaInvalidez(String indExistenciaInvalidez) {
		this.indExistenciaInvalidez = indExistenciaInvalidez;
	}

	public String getIndMayor75() {
		return this.indMayor75;
	}

	public void setIndMayor75(String indMayor75) {
		this.indMayor75 = indMayor75;
	}

	public BigDecimal getNumDiasIncapPrevios() {
		return this.numDiasIncapPrevios;
	}

	public void setNumDiasIncapPrevios(BigDecimal numDiasIncapPrevios) {
		this.numDiasIncapPrevios = numDiasIncapPrevios;
	}

	public BigDecimal getPorArt140() {
		return this.porArt140;
	}

	public void setPorArt140(BigDecimal porArt140) {
		this.porArt140 = porArt140;
	}

	public BigDecimal getPorValuacion() {
		return this.porValuacion;
	}

	public void setPorValuacion(BigDecimal porValuacion) {
		this.porValuacion = porValuacion;
	}

	public List<SptDiagnosticoSt4> getSptDiagnosticoSt4s() {
		return this.sptDiagnosticoSt4s;
	}

	public void setSptDiagnosticoSt4s(List<SptDiagnosticoSt4> sptDiagnosticoSt4s) {
		this.sptDiagnosticoSt4s = sptDiagnosticoSt4s;
	}

	public SptDiagnosticoSt4 addSptDiagnosticoSt4(SptDiagnosticoSt4 sptDiagnosticoSt4) {
		getSptDiagnosticoSt4s().add(sptDiagnosticoSt4);
		sptDiagnosticoSt4.setSptDictamenSt4(this);

		return sptDiagnosticoSt4;
	}

	public SptDiagnosticoSt4 removeSptDiagnosticoSt4(SptDiagnosticoSt4 sptDiagnosticoSt4) {
		getSptDiagnosticoSt4s().remove(sptDiagnosticoSt4);
		sptDiagnosticoSt4.setSptDictamenSt4(null);

		return sptDiagnosticoSt4;
	}

	public SptDictamen getSptDictamen() {
		return this.sptDictamen;
	}

	public void setSptDictamen(SptDictamen sptDictamen) {
		this.sptDictamen = sptDictamen;
	}

}