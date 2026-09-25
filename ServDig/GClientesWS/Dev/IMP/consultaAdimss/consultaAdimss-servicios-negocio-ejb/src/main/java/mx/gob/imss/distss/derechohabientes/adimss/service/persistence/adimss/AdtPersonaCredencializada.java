package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ADT_PERSONA_CREDENCIALIZADA database table.
 * 
 */
@Entity
@Table(name="ADT_PERSONA_CREDENCIALIZADA")
@NamedQuery(name="AdtPersonaCredencializada.findAll", query="SELECT a FROM AdtPersonaCredencializada a")
public class AdtPersonaCredencializada implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NUM_FOLIO_PERSONA")
	private long numFolioPersona;

	@Column(name="CVE_LADA")
	private BigDecimal cveLada;

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

	@Column(name="IND_NOMBRE_CANASE")
	private BigDecimal indNombreCanase;

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

	@Column(name="NOM_CANASE")
	private String nomCanase;

	@Column(name="NOM_NOMBRE")
	private String nomNombre;

	@Column(name="NUM_TELEFONO")
	private String numTelefono;

	@Column(name="REF_CORREO_ELECTRONICO")
	private String refCorreoElectronico;

	@Column(name="REF_CURP")
	private String refCurp;

	//bi-directional one-to-one association to AdtActNacimiento
	@OneToOne(mappedBy="adtPersonaCredencializada")
	private AdtActNacimiento adtActNacimiento;

	//bi-directional one-to-one association to AdtCartaNatural
	@OneToOne(mappedBy="adtPersonaCredencializada")
	private AdtCartaNatural adtCartaNatural;

	//bi-directional one-to-one association to AdtCertNac
	@OneToOne(mappedBy="adtPersonaCredencializada")
	private AdtCertNac adtCertNac;

	//bi-directional many-to-one association to AdtCredencial
	@OneToMany(mappedBy="adtPersonaCredencializada")
	private List<AdtCredencial> adtCredencials;

	//bi-directional one-to-one association to AdtMenoresEdad
	@OneToOne(mappedBy="adtPersonaCredencializada")
	private AdtMenoresEdad adtMenoresEdad;

	//bi-directional many-to-one association to AdcDocNacionalidad
	@ManyToOne
	@JoinColumn(name="CVE_TIPO_DOC_PROB_NAC")
	private AdcDocNacionalidad adcDocNacionalidad;

	//bi-directional many-to-one association to AdcEdoCivil
	@ManyToOne
	@JoinColumn(name="CVE_EDO_CIVIL")
	private AdcEdoCivil adcEdoCivil;

	//bi-directional many-to-one association to AdcMunicipio
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="CVE_ENTIDAD_FEDERATIVA", referencedColumnName="CVE_ENT_FEDERATIVA"),
		@JoinColumn(name="CVE_MUNICIPIO", referencedColumnName="CVE_MUNICIPIO")
		})
	private AdcMunicipio adcMunicipio;

	//bi-directional many-to-one association to AdcNal
	@ManyToOne
	@JoinColumn(name="CVE_NACIONALIDAD")
	private AdcNal adcNal;

	//bi-directional many-to-one association to AdcSexo
	@ManyToOne
	@JoinColumn(name="CVE_SEXO")
	private AdcSexo adcSexo;

	//bi-directional many-to-one association to AdtCatSituacion
	@ManyToOne
	@JoinColumn(name="CVE_SITUACION_PERSONA")
	private AdtCatSituacion adtCatSituacion;

	//bi-directional many-to-one association to AdtPersonaCredencializadaHi
	@OneToMany(mappedBy="adtPersonaCredencializada")
	private List<AdtPersonaCredencializadaHi> adtPersonaCredencializadaHis;

	//bi-directional many-to-one association to AdtRegNalExtr
	@OneToMany(mappedBy="adtPersonaCredencializada")
	private List<AdtRegNalExtr> adtRegNalExtrs;

	public AdtPersonaCredencializada() {
	}

	public long getNumFolioPersona() {
		return this.numFolioPersona;
	}

	public void setNumFolioPersona(long numFolioPersona) {
		this.numFolioPersona = numFolioPersona;
	}

	public BigDecimal getCveLada() {
		return this.cveLada;
	}

	public void setCveLada(BigDecimal cveLada) {
		this.cveLada = cveLada;
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

	public BigDecimal getIndNombreCanase() {
		return this.indNombreCanase;
	}

	public void setIndNombreCanase(BigDecimal indNombreCanase) {
		this.indNombreCanase = indNombreCanase;
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

	public String getNomCanase() {
		return this.nomCanase;
	}

	public void setNomCanase(String nomCanase) {
		this.nomCanase = nomCanase;
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

	public AdtActNacimiento getAdtActNacimiento() {
		return this.adtActNacimiento;
	}

	public void setAdtActNacimiento(AdtActNacimiento adtActNacimiento) {
		this.adtActNacimiento = adtActNacimiento;
	}

	public AdtCartaNatural getAdtCartaNatural() {
		return this.adtCartaNatural;
	}

	public void setAdtCartaNatural(AdtCartaNatural adtCartaNatural) {
		this.adtCartaNatural = adtCartaNatural;
	}

	public AdtCertNac getAdtCertNac() {
		return this.adtCertNac;
	}

	public void setAdtCertNac(AdtCertNac adtCertNac) {
		this.adtCertNac = adtCertNac;
	}

	public List<AdtCredencial> getAdtCredencials() {
		return this.adtCredencials;
	}

	public void setAdtCredencials(List<AdtCredencial> adtCredencials) {
		this.adtCredencials = adtCredencials;
	}

	public AdtCredencial addAdtCredencial(AdtCredencial adtCredencial) {
		getAdtCredencials().add(adtCredencial);
		adtCredencial.setAdtPersonaCredencializada(this);

		return adtCredencial;
	}

	public AdtCredencial removeAdtCredencial(AdtCredencial adtCredencial) {
		getAdtCredencials().remove(adtCredencial);
		adtCredencial.setAdtPersonaCredencializada(null);

		return adtCredencial;
	}

	public AdtMenoresEdad getAdtMenoresEdad() {
		return this.adtMenoresEdad;
	}

	public void setAdtMenoresEdad(AdtMenoresEdad adtMenoresEdad) {
		this.adtMenoresEdad = adtMenoresEdad;
	}

	public AdcDocNacionalidad getAdcDocNacionalidad() {
		return this.adcDocNacionalidad;
	}

	public void setAdcDocNacionalidad(AdcDocNacionalidad adcDocNacionalidad) {
		this.adcDocNacionalidad = adcDocNacionalidad;
	}

	public AdcEdoCivil getAdcEdoCivil() {
		return this.adcEdoCivil;
	}

	public void setAdcEdoCivil(AdcEdoCivil adcEdoCivil) {
		this.adcEdoCivil = adcEdoCivil;
	}

	public AdcMunicipio getAdcMunicipio() {
		return this.adcMunicipio;
	}

	public void setAdcMunicipio(AdcMunicipio adcMunicipio) {
		this.adcMunicipio = adcMunicipio;
	}

	public AdcNal getAdcNal() {
		return this.adcNal;
	}

	public void setAdcNal(AdcNal adcNal) {
		this.adcNal = adcNal;
	}

	public AdcSexo getAdcSexo() {
		return this.adcSexo;
	}

	public void setAdcSexo(AdcSexo adcSexo) {
		this.adcSexo = adcSexo;
	}

	public AdtCatSituacion getAdtCatSituacion() {
		return this.adtCatSituacion;
	}

	public void setAdtCatSituacion(AdtCatSituacion adtCatSituacion) {
		this.adtCatSituacion = adtCatSituacion;
	}

	public List<AdtPersonaCredencializadaHi> getAdtPersonaCredencializadaHis() {
		return this.adtPersonaCredencializadaHis;
	}

	public void setAdtPersonaCredencializadaHis(List<AdtPersonaCredencializadaHi> adtPersonaCredencializadaHis) {
		this.adtPersonaCredencializadaHis = adtPersonaCredencializadaHis;
	}

	public AdtPersonaCredencializadaHi addAdtPersonaCredencializadaHi(AdtPersonaCredencializadaHi adtPersonaCredencializadaHi) {
		getAdtPersonaCredencializadaHis().add(adtPersonaCredencializadaHi);
		adtPersonaCredencializadaHi.setAdtPersonaCredencializada(this);

		return adtPersonaCredencializadaHi;
	}

	public AdtPersonaCredencializadaHi removeAdtPersonaCredencializadaHi(AdtPersonaCredencializadaHi adtPersonaCredencializadaHi) {
		getAdtPersonaCredencializadaHis().remove(adtPersonaCredencializadaHi);
		adtPersonaCredencializadaHi.setAdtPersonaCredencializada(null);

		return adtPersonaCredencializadaHi;
	}

	public List<AdtRegNalExtr> getAdtRegNalExtrs() {
		return this.adtRegNalExtrs;
	}

	public void setAdtRegNalExtrs(List<AdtRegNalExtr> adtRegNalExtrs) {
		this.adtRegNalExtrs = adtRegNalExtrs;
	}

	public AdtRegNalExtr addAdtRegNalExtr(AdtRegNalExtr adtRegNalExtr) {
		getAdtRegNalExtrs().add(adtRegNalExtr);
		adtRegNalExtr.setAdtPersonaCredencializada(this);

		return adtRegNalExtr;
	}

	public AdtRegNalExtr removeAdtRegNalExtr(AdtRegNalExtr adtRegNalExtr) {
		getAdtRegNalExtrs().remove(adtRegNalExtr);
		adtRegNalExtr.setAdtPersonaCredencializada(null);

		return adtRegNalExtr;
	}

}