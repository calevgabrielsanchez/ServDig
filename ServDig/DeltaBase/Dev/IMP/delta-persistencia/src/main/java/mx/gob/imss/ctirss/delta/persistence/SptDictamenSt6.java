package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SPT_DICTAMEN_ST6 database table.
 * 
 */
@Entity
@Table(name="SPT_DICTAMEN_ST6")
@NamedQuery(name="SptDictamenSt6.findAll", query="SELECT s FROM SptDictamenSt6 s")
public class SptDictamenSt6 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTDICTAMENST6", sequenceName = "SEQ_SPTDICTAMENST6")
	@GeneratedValue(generator = "SEQ_SPTDICTAMENST6")	
	@Column(name="CVE_ID_DICTAMEN")
	private long cveIdDictamen;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO_INCAPACIDAD")
	private Date fecInicioIncapacidad;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="ID_PARENTESCO")
	private String idParentesco;

	@Column(name="NOM_APELLIDO_MATERNO")
	private String nomApellidoMaterno;

	@Column(name="NOM_APELLIDO_PATERNO")
	private String nomApellidoPaterno;

	@Column(name="NOM_NOMBRE")
	private String nomNombre;

	@Column(name="NUM_EDAD")
	private BigDecimal numEdad;

	//bi-directional many-to-one association to SptBenefPensDictSt6
	@OneToMany(mappedBy="sptDictamenSt6")
	private List<SptBenefPensDictSt6> sptBenefPensDictSt6s;

	//bi-directional many-to-one association to SptDiagnosticoSt6
	@OneToMany(mappedBy="sptDictamenSt6")
	private List<SptDiagnosticoSt6> sptDiagnosticoSt6s;

	//bi-directional one-to-one association to SptDictamen
	@OneToOne
	@JoinColumn(name="CVE_ID_DICTAMEN")
	private SptDictamen sptDictamen;

	public SptDictamenSt6() {
	}

	public long getCveIdDictamen() {
		return this.cveIdDictamen;
	}

	public void setCveIdDictamen(long cveIdDictamen) {
		this.cveIdDictamen = cveIdDictamen;
	}

	public Date getFecInicioIncapacidad() {
		return this.fecInicioIncapacidad;
	}

	public void setFecInicioIncapacidad(Date fecInicioIncapacidad) {
		this.fecInicioIncapacidad = fecInicioIncapacidad;
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

	public String getIdParentesco() {
		return this.idParentesco;
	}

	public void setIdParentesco(String idParentesco) {
		this.idParentesco = idParentesco;
	}

	public String getNomApellidoMaterno() {
		return this.nomApellidoMaterno;
	}

	public void setNomApellidoMaterno(String nomApellidoMaterno) {
		this.nomApellidoMaterno = nomApellidoMaterno;
	}

	public String getNomApellidoPaterno() {
		return this.nomApellidoPaterno;
	}

	public void setNomApellidoPaterno(String nomApellidoPaterno) {
		this.nomApellidoPaterno = nomApellidoPaterno;
	}

	public String getNomNombre() {
		return this.nomNombre;
	}

	public void setNomNombre(String nomNombre) {
		this.nomNombre = nomNombre;
	}

	public BigDecimal getNumEdad() {
		return this.numEdad;
	}

	public void setNumEdad(BigDecimal numEdad) {
		this.numEdad = numEdad;
	}

	public List<SptBenefPensDictSt6> getSptBenefPensDictSt6s() {
		return this.sptBenefPensDictSt6s;
	}

	public void setSptBenefPensDictSt6s(List<SptBenefPensDictSt6> sptBenefPensDictSt6s) {
		this.sptBenefPensDictSt6s = sptBenefPensDictSt6s;
	}

	public SptBenefPensDictSt6 addSptBenefPensDictSt6(SptBenefPensDictSt6 sptBenefPensDictSt6) {
		getSptBenefPensDictSt6s().add(sptBenefPensDictSt6);
		sptBenefPensDictSt6.setSptDictamenSt6(this);

		return sptBenefPensDictSt6;
	}

	public SptBenefPensDictSt6 removeSptBenefPensDictSt6(SptBenefPensDictSt6 sptBenefPensDictSt6) {
		getSptBenefPensDictSt6s().remove(sptBenefPensDictSt6);
		sptBenefPensDictSt6.setSptDictamenSt6(null);

		return sptBenefPensDictSt6;
	}

	public List<SptDiagnosticoSt6> getSptDiagnosticoSt6s() {
		return this.sptDiagnosticoSt6s;
	}

	public void setSptDiagnosticoSt6s(List<SptDiagnosticoSt6> sptDiagnosticoSt6s) {
		this.sptDiagnosticoSt6s = sptDiagnosticoSt6s;
	}

	public SptDiagnosticoSt6 addSptDiagnosticoSt6(SptDiagnosticoSt6 sptDiagnosticoSt6) {
		getSptDiagnosticoSt6s().add(sptDiagnosticoSt6);
		sptDiagnosticoSt6.setSptDictamenSt6(this);

		return sptDiagnosticoSt6;
	}

	public SptDiagnosticoSt6 removeSptDiagnosticoSt6(SptDiagnosticoSt6 sptDiagnosticoSt6) {
		getSptDiagnosticoSt6s().remove(sptDiagnosticoSt6);
		sptDiagnosticoSt6.setSptDictamenSt6(null);

		return sptDiagnosticoSt6;
	}

	public SptDictamen getSptDictamen() {
		return this.sptDictamen;
	}

	public void setSptDictamen(SptDictamen sptDictamen) {
		this.sptDictamen = sptDictamen;
	}

}