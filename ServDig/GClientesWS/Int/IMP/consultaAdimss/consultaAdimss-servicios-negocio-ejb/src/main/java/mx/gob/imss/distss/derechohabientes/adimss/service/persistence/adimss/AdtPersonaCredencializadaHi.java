package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ADT_PERSONA_CREDENCIALIZADA_HI database table.
 * 
 */
@Entity
@Table(name="ADT_PERSONA_CREDENCIALIZADA_HI")
@NamedQuery(name="AdtPersonaCredencializadaHi.findAll", query="SELECT a FROM AdtPersonaCredencializadaHi a")
public class AdtPersonaCredencializadaHi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long numhst;

	@Column(name="CVE_EDO_CIVIL")
	private BigDecimal cveEdoCivil;

	@Column(name="CVE_ENT_FEDERATIVA")
	private BigDecimal cveEntFederativa;

	@Column(name="CVE_MUNICIPIO")
	private BigDecimal cveMunicipio;

	@Column(name="CVE_NACIONALIDAD")
	private String cveNacionalidad;

	@Column(name="CVE_SEXO")
	private String cveSexo;

	@Column(name="CVE_SITUACION_PERSONA")
	private String cveSituacionPersona;

	@Column(name="CVE_TIPO_DOC_PROB_NAC")
	private BigDecimal cveTipoDocProbNac;

	@Column(name="DOM_CALLE")
	private String domCalle;

	@Column(name="DOM_COLONIA")
	private String domColonia;

	@Column(name="DOM_CP")
	private String domCp;

	@Column(name="DOM_NUM_EXT")
	private String domNumExt;

	@Column(name="DOM_NUM_INT")
	private String domNumInt;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_NACIMIENTO")
	private Date fecNacimiento;

	@Column(name="IND_NOMBRE_CANSE")
	private BigDecimal indNombreCanse;

	@Column(name="NOM_APELL_MAT")
	private String nomApellMat;

	@Column(name="NOM_APELL_PAT")
	private String nomApellPat;

	@Column(name="NOM_CALLE_LAT_DER")
	private String nomCalleLatDer;

	@Column(name="NOM_CALLE_LAT_IZQ")
	private String nomCalleLatIzq;

	@Column(name="NOM_CALLE_LAT_TRAS")
	private String nomCalleLatTras;

	@Column(name="NOM_CANSE")
	private String nomCanse;

	@Column(name="NOM_NOMBRE")
	private String nomNombre;

	@Column(name="NUM_TELEFONO")
	private String numTelefono;

	@Column(name="REF_CORREO_ELECTRONICO")
	private String refCorreoElectronico;

	@Column(name="REF_CURP")
	private String refCurp;

	@Temporal(TemporalType.DATE)
	@Column(name="TIM_RECEP_TRANSAC")
	private Date timRecepTransac;

	@Temporal(TemporalType.DATE)
	@Column(name="TIM_TRANS_TRANSAC")
	private Date timTransTransac;

	//bi-directional many-to-one association to AdtPersonaCredencializada
	@ManyToOne
	@JoinColumn(name="NUM_FOLIO_PERSONA")
	private AdtPersonaCredencializada adtPersonaCredencializada;

	public AdtPersonaCredencializadaHi() {
	}

	public long getNumhst() {
		return this.numhst;
	}

	public void setNumhst(long numhst) {
		this.numhst = numhst;
	}

	public BigDecimal getCveEdoCivil() {
		return this.cveEdoCivil;
	}

	public void setCveEdoCivil(BigDecimal cveEdoCivil) {
		this.cveEdoCivil = cveEdoCivil;
	}

	public BigDecimal getCveEntFederativa() {
		return this.cveEntFederativa;
	}

	public void setCveEntFederativa(BigDecimal cveEntFederativa) {
		this.cveEntFederativa = cveEntFederativa;
	}

	public BigDecimal getCveMunicipio() {
		return this.cveMunicipio;
	}

	public void setCveMunicipio(BigDecimal cveMunicipio) {
		this.cveMunicipio = cveMunicipio;
	}

	public String getCveNacionalidad() {
		return this.cveNacionalidad;
	}

	public void setCveNacionalidad(String cveNacionalidad) {
		this.cveNacionalidad = cveNacionalidad;
	}

	public String getCveSexo() {
		return this.cveSexo;
	}

	public void setCveSexo(String cveSexo) {
		this.cveSexo = cveSexo;
	}

	public String getCveSituacionPersona() {
		return this.cveSituacionPersona;
	}

	public void setCveSituacionPersona(String cveSituacionPersona) {
		this.cveSituacionPersona = cveSituacionPersona;
	}

	public BigDecimal getCveTipoDocProbNac() {
		return this.cveTipoDocProbNac;
	}

	public void setCveTipoDocProbNac(BigDecimal cveTipoDocProbNac) {
		this.cveTipoDocProbNac = cveTipoDocProbNac;
	}

	public String getDomCalle() {
		return this.domCalle;
	}

	public void setDomCalle(String domCalle) {
		this.domCalle = domCalle;
	}

	public String getDomColonia() {
		return this.domColonia;
	}

	public void setDomColonia(String domColonia) {
		this.domColonia = domColonia;
	}

	public String getDomCp() {
		return this.domCp;
	}

	public void setDomCp(String domCp) {
		this.domCp = domCp;
	}

	public String getDomNumExt() {
		return this.domNumExt;
	}

	public void setDomNumExt(String domNumExt) {
		this.domNumExt = domNumExt;
	}

	public String getDomNumInt() {
		return this.domNumInt;
	}

	public void setDomNumInt(String domNumInt) {
		this.domNumInt = domNumInt;
	}

	public Date getFecNacimiento() {
		return this.fecNacimiento;
	}

	public void setFecNacimiento(Date fecNacimiento) {
		this.fecNacimiento = fecNacimiento;
	}

	public BigDecimal getIndNombreCanse() {
		return this.indNombreCanse;
	}

	public void setIndNombreCanse(BigDecimal indNombreCanse) {
		this.indNombreCanse = indNombreCanse;
	}

	public String getNomApellMat() {
		return this.nomApellMat;
	}

	public void setNomApellMat(String nomApellMat) {
		this.nomApellMat = nomApellMat;
	}

	public String getNomApellPat() {
		return this.nomApellPat;
	}

	public void setNomApellPat(String nomApellPat) {
		this.nomApellPat = nomApellPat;
	}

	public String getNomCalleLatDer() {
		return this.nomCalleLatDer;
	}

	public void setNomCalleLatDer(String nomCalleLatDer) {
		this.nomCalleLatDer = nomCalleLatDer;
	}

	public String getNomCalleLatIzq() {
		return this.nomCalleLatIzq;
	}

	public void setNomCalleLatIzq(String nomCalleLatIzq) {
		this.nomCalleLatIzq = nomCalleLatIzq;
	}

	public String getNomCalleLatTras() {
		return this.nomCalleLatTras;
	}

	public void setNomCalleLatTras(String nomCalleLatTras) {
		this.nomCalleLatTras = nomCalleLatTras;
	}

	public String getNomCanse() {
		return this.nomCanse;
	}

	public void setNomCanse(String nomCanse) {
		this.nomCanse = nomCanse;
	}

	public String getNomNombre() {
		return this.nomNombre;
	}

	public void setNomNombre(String nomNombre) {
		this.nomNombre = nomNombre;
	}

	public String getNumTelefono() {
		return this.numTelefono;
	}

	public void setNumTelefono(String numTelefono) {
		this.numTelefono = numTelefono;
	}

	public String getRefCorreoElectronico() {
		return this.refCorreoElectronico;
	}

	public void setRefCorreoElectronico(String refCorreoElectronico) {
		this.refCorreoElectronico = refCorreoElectronico;
	}

	public String getRefCurp() {
		return this.refCurp;
	}

	public void setRefCurp(String refCurp) {
		this.refCurp = refCurp;
	}

	public Date getTimRecepTransac() {
		return this.timRecepTransac;
	}

	public void setTimRecepTransac(Date timRecepTransac) {
		this.timRecepTransac = timRecepTransac;
	}

	public Date getTimTransTransac() {
		return this.timTransTransac;
	}

	public void setTimTransTransac(Date timTransTransac) {
		this.timTransTransac = timTransTransac;
	}

	public AdtPersonaCredencializada getAdtPersonaCredencializada() {
		return this.adtPersonaCredencializada;
	}

	public void setAdtPersonaCredencializada(AdtPersonaCredencializada adtPersonaCredencializada) {
		this.adtPersonaCredencializada = adtPersonaCredencializada;
	}

}