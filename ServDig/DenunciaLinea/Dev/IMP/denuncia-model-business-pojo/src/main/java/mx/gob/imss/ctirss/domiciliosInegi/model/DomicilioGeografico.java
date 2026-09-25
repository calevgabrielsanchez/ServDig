package mx.gob.imss.ctirss.domiciliosInegi.model;


import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.imss.ctirss.framework.annotations.OnSearchLlavePrimaria;
import mx.imss.ctirss.framework.base.model.AbstractModel;

import java.util.Date;


/**
 * The persistent class for the DG_DOMICILIO_GEOGRAFICO database table.
 * 
 */
@Entity
@Table(name="DG_DOMICILIO_GEOGRAFICO")
@OnSearchLlavePrimaria(atributos="domicilioId")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DomicilioGeografico extends AbstractModel {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1990932398247443692L;

	public DomicilioGeografico(){}
	@Id
	@Column(name = "DOMICILIO_ID", unique = true, nullable = false, precision = 10, scale = 0)
	@SequenceGenerator(name="CRS_DOMICILIO_ID_GENERATOR", sequenceName="SEQ_CVE_DOMICILIO_ID")
	@GeneratedValue(generator="CRS_DOMICILIO_ID_GENERATOR")
	private Long domicilioId;
	
	@Column(name = "CODIGO")
	private String codigo;
	
	@Column(name = "CVE_TIPO_DOM")
	private Integer cveTipoDom;
	
	@Column(name = "CVE_ENT")
	private String cveEnt;
	
	@Column(name = "CVE_MUN")
	private String cveMun;
	
	@Column(name = "CVE_LOC")
	private String cveLoc;
	
	@Column(name = "CVE_PERIODO")
	private Integer cvePeriodo;
	
	@Column(name = "NOMVIAL")
	private String nomVial;
	
	@Column(name = "NUMEXTNUM")
	private Integer numExtNum;
	
	@Column(name = "NUMEXTALF")
	private String numExtAlf;
	
	@Column(name = "NUMEXT_ANT")
	private String numExtAnt;
	
	@Column(name = "NUMINTNUM")
	private Integer numIntNum;
	
	@Column(name = "NUMINTALF")
	private String numIntAlf;
	
	@Column(name = "CVE_ASEN")
	private String cveAsen;
	
	@Column(name = "CVE_VIA_PRIN")
	private Integer cveViaPrin;
	
	@Column(name = "CVE_VIA_REF1")
	private Long cveViaRef1;
	
	@Column(name = "CVE_VIA_REF2")
	private Long cveViaRef2;
	
	@Column(name = "CVE_VIA_REF3")
	private Long cveViaRef3;
	
	@Column(name = "DESCRIPC")
	private String descripcion;
	
	@Column(name = "FECHA_HORA_ALTA")
	private Date fechaHoraAlta;
	
	@Column(name = "CVE_USUARIO")
	private String cveUsuario;
	
	public Long getDomicilioId() {
		return domicilioId;
	}
	public void setDomicilioId(Long domicilioId) {
		this.domicilioId = domicilioId;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public Integer getCveTipoDom() {
		return cveTipoDom;
	}
	public void setCveTipoDom(Integer cveTipoDom) {
		this.cveTipoDom = cveTipoDom;
	}
	public String getCveEnt() {
		return cveEnt;
	}
	public void setCveEnt(String cveEnt) {
		this.cveEnt = cveEnt;
	}
	public String getCveMun() {
		return cveMun;
	}
	public void setCveMun(String cveMun) {
		this.cveMun = cveMun;
	}
	public String getCveLoc() {
		return cveLoc;
	}
	public void setCveLoc(String cveLoc) {
		this.cveLoc = cveLoc;
	}
	public Integer getCvePeriodo() {
		return cvePeriodo;
	}
	public void setCvePeriodo(Integer cvePeriodo) {
		this.cvePeriodo = cvePeriodo;
	}
	public String getNomVial() {
		return nomVial;
	}
	public void setNomVial(String nomVial) {
		this.nomVial = nomVial;
	}
	public Integer getNumExtNum() {
		return numExtNum;
	}
	public void setNumExtNum(Integer numExtNum) {
		this.numExtNum = numExtNum;
	}
	public String getNumExtAlf() {
		return numExtAlf;
	}
	public void setNumExtAlf(String numExtAlf) {
		this.numExtAlf = numExtAlf;
	}
	public String getNumExtAnt() {
		return numExtAnt;
	}
	public void setNumExtAnt(String numExtAnt) {
		this.numExtAnt = numExtAnt;
	}
	public Integer getNumIntNum() {
		return numIntNum;
	}
	public void setNumIntNum(Integer numIntNum) {
		this.numIntNum = numIntNum;
	}
	public String getNumIntAlf() {
		return numIntAlf;
	}
	public void setNumIntAlf(String numIntAlf) {
		this.numIntAlf = numIntAlf;
	}
	public String getCveAsen() {
		return cveAsen;
	}
	public void setCveAsen(String cveAsen) {
		this.cveAsen = cveAsen;
	}
	public Integer getCveViaPrin() {
		return cveViaPrin;
	}
	public void setCveViaPrin(Integer cveViaPrin) {
		this.cveViaPrin = cveViaPrin;
	}
	
	public Long getCveViaRef1() {
		return cveViaRef1;
	}
	public void setCveViaRef1(Long cveViaRef1) {
		this.cveViaRef1 = cveViaRef1;
	}
	public Long getCveViaRef2() {
		return cveViaRef2;
	}
	public void setCveViaRef2(Long cveViaRef2) {
		this.cveViaRef2 = cveViaRef2;
	}
	public Long getCveViaRef3() {
		return cveViaRef3;
	}
	public void setCveViaRef3(Long cveViaRef3) {
		this.cveViaRef3 = cveViaRef3;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFechaHoraAlta() {
		return fechaHoraAlta;
	}
	public void setFechaHoraAlta(Date fechaHoraAlta) {
		this.fechaHoraAlta = fechaHoraAlta;
	}
	public String getCveUsuario() {
		return cveUsuario;
	}
	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}
	
	
	
	
	
	
		
}