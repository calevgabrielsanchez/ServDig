package mx.imss.ctirss.denuncia.vo;

import java.io.Serializable;

public class DomicilioVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long domicilioId;
	private String codigo;
	private Long cveTipoDom;
	private String cveEnt;
	private String cveMun;
	private String cveLoc;
	private String cvePeriodo;
	private String nomVial;
	private Long numExtNum;
	private String numExtAlf;
	private String numExtAnt;
	private Long numIntNum;
	private String numIntAlf;
	private String cveAsen;
	private Integer cveViaPrin;
	private Long cveViaRef1;
	private Long cveViaRef2;
	private Long cveViaRef3;
	private String descripc;
	private String domGeog;
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
	public Long getCveTipoDom() {
		return cveTipoDom;
	}
	public void setCveTipoDom(Long cveTipoDom) {
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
	public String getCvePeriodo() {
		return cvePeriodo;
	}
	public void setCvePeriodo(String cvePeriodo) {
		this.cvePeriodo = cvePeriodo;
	}
	public String getNomVial() {
		return nomVial;
	}
	public void setNomVial(String nomVial) {
		this.nomVial = nomVial;
	}
	public Long getNumExtNum() {
		return numExtNum;
	}
	public void setNumExtNum(Long numExtNum) {
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
	public Long getNumIntNum() {
		return numIntNum;
	}
	public void setNumIntNum(Long numIntNum) {
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
	public String getDescripc() {
		return descripc;
	}
	public void setDescripc(String descripc) {
		this.descripc = descripc;
	}
	public String getDomGeog() {
		return domGeog;
	}
	public void setDomGeog(String domGeog) {
		this.domGeog = domGeog;
	}
	
	
	
}
