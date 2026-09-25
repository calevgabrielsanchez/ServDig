package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ADT_ARC_MIN_HUELLAS_TMP database table.
 * 
 */
@Entity
@Table(name="ADT_ARC_MIN_HUELLAS_TMP")
@NamedQuery(name="AdtArcMinHuellasTmp.findAll", query="SELECT a FROM AdtArcMinHuellasTmp a")
public class AdtArcMinHuellasTmp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NUM_FOLIO_PERSONA")
	private long numFolioPersona;

	@Lob
	@Column(name="ARC_MIN_HUELLA1")
	private byte[] arcMinHuella1;

	@Lob
	@Column(name="ARC_MIN_HUELLA10")
	private byte[] arcMinHuella10;

	@Lob
	@Column(name="ARC_MIN_HUELLA2")
	private byte[] arcMinHuella2;

	@Lob
	@Column(name="ARC_MIN_HUELLA3")
	private byte[] arcMinHuella3;

	@Lob
	@Column(name="ARC_MIN_HUELLA4")
	private byte[] arcMinHuella4;

	@Lob
	@Column(name="ARC_MIN_HUELLA5")
	private byte[] arcMinHuella5;

	@Lob
	@Column(name="ARC_MIN_HUELLA6")
	private byte[] arcMinHuella6;

	@Lob
	@Column(name="ARC_MIN_HUELLA7")
	private byte[] arcMinHuella7;

	@Lob
	@Column(name="ARC_MIN_HUELLA8")
	private byte[] arcMinHuella8;

	@Lob
	@Column(name="ARC_MIN_HUELLA9")
	private byte[] arcMinHuella9;

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

	@Column(name="NUM_OID_MIN_HUELLA_ANSI_WSQ1")
	private String numOidMinHuellaAnsiWsq1;

	@Column(name="NUM_OID_MIN_HUELLA_ANSI_WSQ10")
	private String numOidMinHuellaAnsiWsq10;

	@Column(name="NUM_OID_MIN_HUELLA_ANSI_WSQ2")
	private String numOidMinHuellaAnsiWsq2;

	@Column(name="NUM_OID_MIN_HUELLA_ANSI_WSQ3")
	private String numOidMinHuellaAnsiWsq3;

	@Column(name="NUM_OID_MIN_HUELLA_ANSI_WSQ4")
	private String numOidMinHuellaAnsiWsq4;

	@Column(name="NUM_OID_MIN_HUELLA_ANSI_WSQ5")
	private String numOidMinHuellaAnsiWsq5;

	@Column(name="NUM_OID_MIN_HUELLA_ANSI_WSQ6")
	private String numOidMinHuellaAnsiWsq6;

	@Column(name="NUM_OID_MIN_HUELLA_ANSI_WSQ7")
	private String numOidMinHuellaAnsiWsq7;

	@Column(name="NUM_OID_MIN_HUELLA_ANSI_WSQ8")
	private String numOidMinHuellaAnsiWsq8;

	@Column(name="NUM_OID_MIN_HUELLA_ANSI_WSQ9")
	private String numOidMinHuellaAnsiWsq9;

	@Column(name="NUM_OID_MIN_HUELLA_PK1")
	private String numOidMinHuellaPk1;

	@Column(name="NUM_OID_MIN_HUELLA_PK10")
	private String numOidMinHuellaPk10;

	@Column(name="NUM_OID_MIN_HUELLA_PK2")
	private String numOidMinHuellaPk2;

	@Column(name="NUM_OID_MIN_HUELLA_PK3")
	private String numOidMinHuellaPk3;

	@Column(name="NUM_OID_MIN_HUELLA_PK4")
	private String numOidMinHuellaPk4;

	@Column(name="NUM_OID_MIN_HUELLA_PK5")
	private String numOidMinHuellaPk5;

	@Column(name="NUM_OID_MIN_HUELLA_PK6")
	private String numOidMinHuellaPk6;

	@Column(name="NUM_OID_MIN_HUELLA_PK7")
	private String numOidMinHuellaPk7;

	@Column(name="NUM_OID_MIN_HUELLA_PK8")
	private String numOidMinHuellaPk8;

	@Column(name="NUM_OID_MIN_HUELLA_PK9")
	private String numOidMinHuellaPk9;

	@Column(name="NUM_OID_MIN_HUELLA_WSQ1")
	private String numOidMinHuellaWsq1;

	@Column(name="NUM_OID_MIN_HUELLA_WSQ10")
	private String numOidMinHuellaWsq10;

	@Column(name="NUM_OID_MIN_HUELLA_WSQ2")
	private String numOidMinHuellaWsq2;

	@Column(name="NUM_OID_MIN_HUELLA_WSQ3")
	private String numOidMinHuellaWsq3;

	@Column(name="NUM_OID_MIN_HUELLA_WSQ4")
	private String numOidMinHuellaWsq4;

	@Column(name="NUM_OID_MIN_HUELLA_WSQ5")
	private String numOidMinHuellaWsq5;

	@Column(name="NUM_OID_MIN_HUELLA_WSQ6")
	private String numOidMinHuellaWsq6;

	@Column(name="NUM_OID_MIN_HUELLA_WSQ7")
	private String numOidMinHuellaWsq7;

	@Column(name="NUM_OID_MIN_HUELLA_WSQ8")
	private String numOidMinHuellaWsq8;

	@Column(name="NUM_OID_MIN_HUELLA_WSQ9")
	private String numOidMinHuellaWsq9;

	@Column(name="NUM_OID_SLAP1")
	private String numOidSlap1;

	@Column(name="NUM_OID_SLAP2")
	private String numOidSlap2;

	@Column(name="NUM_OID_SLAP3")
	private String numOidSlap3;

	@Column(name="NUM_OID_SLAP4")
	private String numOidSlap4;

	public AdtArcMinHuellasTmp() {
	}

	public long getNumFolioPersona() {
		return this.numFolioPersona;
	}

	public void setNumFolioPersona(long numFolioPersona) {
		this.numFolioPersona = numFolioPersona;
	}

	public byte[] getArcMinHuella1() {
		return this.arcMinHuella1;
	}

	public void setArcMinHuella1(byte[] arcMinHuella1) {
		this.arcMinHuella1 = arcMinHuella1;
	}

	public byte[] getArcMinHuella10() {
		return this.arcMinHuella10;
	}

	public void setArcMinHuella10(byte[] arcMinHuella10) {
		this.arcMinHuella10 = arcMinHuella10;
	}

	public byte[] getArcMinHuella2() {
		return this.arcMinHuella2;
	}

	public void setArcMinHuella2(byte[] arcMinHuella2) {
		this.arcMinHuella2 = arcMinHuella2;
	}

	public byte[] getArcMinHuella3() {
		return this.arcMinHuella3;
	}

	public void setArcMinHuella3(byte[] arcMinHuella3) {
		this.arcMinHuella3 = arcMinHuella3;
	}

	public byte[] getArcMinHuella4() {
		return this.arcMinHuella4;
	}

	public void setArcMinHuella4(byte[] arcMinHuella4) {
		this.arcMinHuella4 = arcMinHuella4;
	}

	public byte[] getArcMinHuella5() {
		return this.arcMinHuella5;
	}

	public void setArcMinHuella5(byte[] arcMinHuella5) {
		this.arcMinHuella5 = arcMinHuella5;
	}

	public byte[] getArcMinHuella6() {
		return this.arcMinHuella6;
	}

	public void setArcMinHuella6(byte[] arcMinHuella6) {
		this.arcMinHuella6 = arcMinHuella6;
	}

	public byte[] getArcMinHuella7() {
		return this.arcMinHuella7;
	}

	public void setArcMinHuella7(byte[] arcMinHuella7) {
		this.arcMinHuella7 = arcMinHuella7;
	}

	public byte[] getArcMinHuella8() {
		return this.arcMinHuella8;
	}

	public void setArcMinHuella8(byte[] arcMinHuella8) {
		this.arcMinHuella8 = arcMinHuella8;
	}

	public byte[] getArcMinHuella9() {
		return this.arcMinHuella9;
	}

	public void setArcMinHuella9(byte[] arcMinHuella9) {
		this.arcMinHuella9 = arcMinHuella9;
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

	public String getNumOidMinHuellaAnsiWsq1() {
		return this.numOidMinHuellaAnsiWsq1;
	}

	public void setNumOidMinHuellaAnsiWsq1(String numOidMinHuellaAnsiWsq1) {
		this.numOidMinHuellaAnsiWsq1 = numOidMinHuellaAnsiWsq1;
	}

	public String getNumOidMinHuellaAnsiWsq10() {
		return this.numOidMinHuellaAnsiWsq10;
	}

	public void setNumOidMinHuellaAnsiWsq10(String numOidMinHuellaAnsiWsq10) {
		this.numOidMinHuellaAnsiWsq10 = numOidMinHuellaAnsiWsq10;
	}

	public String getNumOidMinHuellaAnsiWsq2() {
		return this.numOidMinHuellaAnsiWsq2;
	}

	public void setNumOidMinHuellaAnsiWsq2(String numOidMinHuellaAnsiWsq2) {
		this.numOidMinHuellaAnsiWsq2 = numOidMinHuellaAnsiWsq2;
	}

	public String getNumOidMinHuellaAnsiWsq3() {
		return this.numOidMinHuellaAnsiWsq3;
	}

	public void setNumOidMinHuellaAnsiWsq3(String numOidMinHuellaAnsiWsq3) {
		this.numOidMinHuellaAnsiWsq3 = numOidMinHuellaAnsiWsq3;
	}

	public String getNumOidMinHuellaAnsiWsq4() {
		return this.numOidMinHuellaAnsiWsq4;
	}

	public void setNumOidMinHuellaAnsiWsq4(String numOidMinHuellaAnsiWsq4) {
		this.numOidMinHuellaAnsiWsq4 = numOidMinHuellaAnsiWsq4;
	}

	public String getNumOidMinHuellaAnsiWsq5() {
		return this.numOidMinHuellaAnsiWsq5;
	}

	public void setNumOidMinHuellaAnsiWsq5(String numOidMinHuellaAnsiWsq5) {
		this.numOidMinHuellaAnsiWsq5 = numOidMinHuellaAnsiWsq5;
	}

	public String getNumOidMinHuellaAnsiWsq6() {
		return this.numOidMinHuellaAnsiWsq6;
	}

	public void setNumOidMinHuellaAnsiWsq6(String numOidMinHuellaAnsiWsq6) {
		this.numOidMinHuellaAnsiWsq6 = numOidMinHuellaAnsiWsq6;
	}

	public String getNumOidMinHuellaAnsiWsq7() {
		return this.numOidMinHuellaAnsiWsq7;
	}

	public void setNumOidMinHuellaAnsiWsq7(String numOidMinHuellaAnsiWsq7) {
		this.numOidMinHuellaAnsiWsq7 = numOidMinHuellaAnsiWsq7;
	}

	public String getNumOidMinHuellaAnsiWsq8() {
		return this.numOidMinHuellaAnsiWsq8;
	}

	public void setNumOidMinHuellaAnsiWsq8(String numOidMinHuellaAnsiWsq8) {
		this.numOidMinHuellaAnsiWsq8 = numOidMinHuellaAnsiWsq8;
	}

	public String getNumOidMinHuellaAnsiWsq9() {
		return this.numOidMinHuellaAnsiWsq9;
	}

	public void setNumOidMinHuellaAnsiWsq9(String numOidMinHuellaAnsiWsq9) {
		this.numOidMinHuellaAnsiWsq9 = numOidMinHuellaAnsiWsq9;
	}

	public String getNumOidMinHuellaPk1() {
		return this.numOidMinHuellaPk1;
	}

	public void setNumOidMinHuellaPk1(String numOidMinHuellaPk1) {
		this.numOidMinHuellaPk1 = numOidMinHuellaPk1;
	}

	public String getNumOidMinHuellaPk10() {
		return this.numOidMinHuellaPk10;
	}

	public void setNumOidMinHuellaPk10(String numOidMinHuellaPk10) {
		this.numOidMinHuellaPk10 = numOidMinHuellaPk10;
	}

	public String getNumOidMinHuellaPk2() {
		return this.numOidMinHuellaPk2;
	}

	public void setNumOidMinHuellaPk2(String numOidMinHuellaPk2) {
		this.numOidMinHuellaPk2 = numOidMinHuellaPk2;
	}

	public String getNumOidMinHuellaPk3() {
		return this.numOidMinHuellaPk3;
	}

	public void setNumOidMinHuellaPk3(String numOidMinHuellaPk3) {
		this.numOidMinHuellaPk3 = numOidMinHuellaPk3;
	}

	public String getNumOidMinHuellaPk4() {
		return this.numOidMinHuellaPk4;
	}

	public void setNumOidMinHuellaPk4(String numOidMinHuellaPk4) {
		this.numOidMinHuellaPk4 = numOidMinHuellaPk4;
	}

	public String getNumOidMinHuellaPk5() {
		return this.numOidMinHuellaPk5;
	}

	public void setNumOidMinHuellaPk5(String numOidMinHuellaPk5) {
		this.numOidMinHuellaPk5 = numOidMinHuellaPk5;
	}

	public String getNumOidMinHuellaPk6() {
		return this.numOidMinHuellaPk6;
	}

	public void setNumOidMinHuellaPk6(String numOidMinHuellaPk6) {
		this.numOidMinHuellaPk6 = numOidMinHuellaPk6;
	}

	public String getNumOidMinHuellaPk7() {
		return this.numOidMinHuellaPk7;
	}

	public void setNumOidMinHuellaPk7(String numOidMinHuellaPk7) {
		this.numOidMinHuellaPk7 = numOidMinHuellaPk7;
	}

	public String getNumOidMinHuellaPk8() {
		return this.numOidMinHuellaPk8;
	}

	public void setNumOidMinHuellaPk8(String numOidMinHuellaPk8) {
		this.numOidMinHuellaPk8 = numOidMinHuellaPk8;
	}

	public String getNumOidMinHuellaPk9() {
		return this.numOidMinHuellaPk9;
	}

	public void setNumOidMinHuellaPk9(String numOidMinHuellaPk9) {
		this.numOidMinHuellaPk9 = numOidMinHuellaPk9;
	}

	public String getNumOidMinHuellaWsq1() {
		return this.numOidMinHuellaWsq1;
	}

	public void setNumOidMinHuellaWsq1(String numOidMinHuellaWsq1) {
		this.numOidMinHuellaWsq1 = numOidMinHuellaWsq1;
	}

	public String getNumOidMinHuellaWsq10() {
		return this.numOidMinHuellaWsq10;
	}

	public void setNumOidMinHuellaWsq10(String numOidMinHuellaWsq10) {
		this.numOidMinHuellaWsq10 = numOidMinHuellaWsq10;
	}

	public String getNumOidMinHuellaWsq2() {
		return this.numOidMinHuellaWsq2;
	}

	public void setNumOidMinHuellaWsq2(String numOidMinHuellaWsq2) {
		this.numOidMinHuellaWsq2 = numOidMinHuellaWsq2;
	}

	public String getNumOidMinHuellaWsq3() {
		return this.numOidMinHuellaWsq3;
	}

	public void setNumOidMinHuellaWsq3(String numOidMinHuellaWsq3) {
		this.numOidMinHuellaWsq3 = numOidMinHuellaWsq3;
	}

	public String getNumOidMinHuellaWsq4() {
		return this.numOidMinHuellaWsq4;
	}

	public void setNumOidMinHuellaWsq4(String numOidMinHuellaWsq4) {
		this.numOidMinHuellaWsq4 = numOidMinHuellaWsq4;
	}

	public String getNumOidMinHuellaWsq5() {
		return this.numOidMinHuellaWsq5;
	}

	public void setNumOidMinHuellaWsq5(String numOidMinHuellaWsq5) {
		this.numOidMinHuellaWsq5 = numOidMinHuellaWsq5;
	}

	public String getNumOidMinHuellaWsq6() {
		return this.numOidMinHuellaWsq6;
	}

	public void setNumOidMinHuellaWsq6(String numOidMinHuellaWsq6) {
		this.numOidMinHuellaWsq6 = numOidMinHuellaWsq6;
	}

	public String getNumOidMinHuellaWsq7() {
		return this.numOidMinHuellaWsq7;
	}

	public void setNumOidMinHuellaWsq7(String numOidMinHuellaWsq7) {
		this.numOidMinHuellaWsq7 = numOidMinHuellaWsq7;
	}

	public String getNumOidMinHuellaWsq8() {
		return this.numOidMinHuellaWsq8;
	}

	public void setNumOidMinHuellaWsq8(String numOidMinHuellaWsq8) {
		this.numOidMinHuellaWsq8 = numOidMinHuellaWsq8;
	}

	public String getNumOidMinHuellaWsq9() {
		return this.numOidMinHuellaWsq9;
	}

	public void setNumOidMinHuellaWsq9(String numOidMinHuellaWsq9) {
		this.numOidMinHuellaWsq9 = numOidMinHuellaWsq9;
	}

	public String getNumOidSlap1() {
		return this.numOidSlap1;
	}

	public void setNumOidSlap1(String numOidSlap1) {
		this.numOidSlap1 = numOidSlap1;
	}

	public String getNumOidSlap2() {
		return this.numOidSlap2;
	}

	public void setNumOidSlap2(String numOidSlap2) {
		this.numOidSlap2 = numOidSlap2;
	}

	public String getNumOidSlap3() {
		return this.numOidSlap3;
	}

	public void setNumOidSlap3(String numOidSlap3) {
		this.numOidSlap3 = numOidSlap3;
	}

	public String getNumOidSlap4() {
		return this.numOidSlap4;
	}

	public void setNumOidSlap4(String numOidSlap4) {
		this.numOidSlap4 = numOidSlap4;
	}

}