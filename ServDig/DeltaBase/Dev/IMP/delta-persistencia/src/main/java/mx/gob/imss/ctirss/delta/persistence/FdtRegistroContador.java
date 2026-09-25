package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the FDT_REGISTRO_CONTADOR database table.
 * 
 */
@Entity
@Table(name="FDT_REGISTRO_CONTADOR")
public class FdtRegistroContador implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_NUM_SOLICTUD", nullable=false, precision=10)
	private long idNumSolictud;

	@Column(name="SELLO_DIGITAL", length=500)
	private String selloDigital;

	//bi-directional many-to-one association to FdiCpa
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CV_CURP")
	private FdiCpa fdiCpa;

    public FdtRegistroContador() {
    }

	public long getIdNumSolictud() {
		return this.idNumSolictud;
	}

	public void setIdNumSolictud(long idNumSolictud) {
		this.idNumSolictud = idNumSolictud;
	}

	public String getSelloDigital() {
		return this.selloDigital;
	}

	public void setSelloDigital(String selloDigital) {
		this.selloDigital = selloDigital;
	}

	public FdiCpa getFdiCpa() {
		return this.fdiCpa;
	}

	public void setFdiCpa(FdiCpa fdiCpa) {
		this.fdiCpa = fdiCpa;
	}
	
}