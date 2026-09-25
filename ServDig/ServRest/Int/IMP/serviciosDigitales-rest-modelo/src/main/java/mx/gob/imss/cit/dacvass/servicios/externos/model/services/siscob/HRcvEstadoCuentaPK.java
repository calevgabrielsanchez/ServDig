package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siscob;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class HRcvEstadoCuentaPK implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5889907347022041238L;

	private String crPat;

	private String crMod;

	private long crPer;

	private String crCred;

	public HRcvEstadoCuentaPK() {
	}
	public String getCrPat() {
		return this.crPat;
	}
	public void setCrPat(String crPat) {
		this.crPat = crPat;
	}
	public String getCrMod() {
		return this.crMod;
	}
	public void setCrMod(String crMod) {
		this.crMod = crMod;
	}
	public long getCrPer() {
		return this.crPer;
	}
	public void setCrPer(long crPer) {
		this.crPer = crPer;
	}
	public String getCrCred() {
		return this.crCred;
	}
	public void setCrCred(String crCred) {
		this.crCred = crCred;
	}

}