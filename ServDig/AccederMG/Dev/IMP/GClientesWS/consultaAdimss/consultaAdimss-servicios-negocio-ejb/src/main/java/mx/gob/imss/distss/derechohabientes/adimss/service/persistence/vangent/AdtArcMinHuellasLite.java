package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ADT_ARC_MIN_HUELLAS_LITE database table.
 * 
 */
@Entity
@Table(name="ADT_ARC_MIN_HUELLAS_LITE")
@NamedQuery(name="AdtArcMinHuellasLite.findAll", query="SELECT a FROM AdtArcMinHuellasLite a")
public class AdtArcMinHuellasLite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NUM_FOLIO_PERSONA")
	private long numFolioPersona;

	@Column(name="CVE_STATUS_HUELLA_1")
	private String cveStatusHuella1;

	@Column(name="CVE_STATUS_HUELLA_10")
	private String cveStatusHuella10;

	@Column(name="CVE_STATUS_HUELLA_2")
	private String cveStatusHuella2;

	@Column(name="CVE_STATUS_HUELLA_3")
	private String cveStatusHuella3;

	@Column(name="CVE_STATUS_HUELLA_4")
	private String cveStatusHuella4;

	@Column(name="CVE_STATUS_HUELLA_5")
	private String cveStatusHuella5;

	@Column(name="CVE_STATUS_HUELLA_6")
	private String cveStatusHuella6;

	@Column(name="CVE_STATUS_HUELLA_7")
	private String cveStatusHuella7;

	@Column(name="CVE_STATUS_HUELLA_8")
	private String cveStatusHuella8;

	@Column(name="CVE_STATUS_HUELLA_9")
	private String cveStatusHuella9;

	public AdtArcMinHuellasLite() {
	}

	public long getNumFolioPersona() {
		return this.numFolioPersona;
	}

	public void setNumFolioPersona(long numFolioPersona) {
		this.numFolioPersona = numFolioPersona;
	}

	public String getCveStatusHuella1() {
		return this.cveStatusHuella1;
	}

	public void setCveStatusHuella1(String cveStatusHuella1) {
		this.cveStatusHuella1 = cveStatusHuella1;
	}

	public String getCveStatusHuella10() {
		return this.cveStatusHuella10;
	}

	public void setCveStatusHuella10(String cveStatusHuella10) {
		this.cveStatusHuella10 = cveStatusHuella10;
	}

	public String getCveStatusHuella2() {
		return this.cveStatusHuella2;
	}

	public void setCveStatusHuella2(String cveStatusHuella2) {
		this.cveStatusHuella2 = cveStatusHuella2;
	}

	public String getCveStatusHuella3() {
		return this.cveStatusHuella3;
	}

	public void setCveStatusHuella3(String cveStatusHuella3) {
		this.cveStatusHuella3 = cveStatusHuella3;
	}

	public String getCveStatusHuella4() {
		return this.cveStatusHuella4;
	}

	public void setCveStatusHuella4(String cveStatusHuella4) {
		this.cveStatusHuella4 = cveStatusHuella4;
	}

	public String getCveStatusHuella5() {
		return this.cveStatusHuella5;
	}

	public void setCveStatusHuella5(String cveStatusHuella5) {
		this.cveStatusHuella5 = cveStatusHuella5;
	}

	public String getCveStatusHuella6() {
		return this.cveStatusHuella6;
	}

	public void setCveStatusHuella6(String cveStatusHuella6) {
		this.cveStatusHuella6 = cveStatusHuella6;
	}

	public String getCveStatusHuella7() {
		return this.cveStatusHuella7;
	}

	public void setCveStatusHuella7(String cveStatusHuella7) {
		this.cveStatusHuella7 = cveStatusHuella7;
	}

	public String getCveStatusHuella8() {
		return this.cveStatusHuella8;
	}

	public void setCveStatusHuella8(String cveStatusHuella8) {
		this.cveStatusHuella8 = cveStatusHuella8;
	}

	public String getCveStatusHuella9() {
		return this.cveStatusHuella9;
	}

	public void setCveStatusHuella9(String cveStatusHuella9) {
		this.cveStatusHuella9 = cveStatusHuella9;
	}

}