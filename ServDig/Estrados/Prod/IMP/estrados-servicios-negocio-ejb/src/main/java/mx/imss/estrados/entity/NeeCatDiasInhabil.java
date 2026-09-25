package mx.imss.estrados.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="NEE_CAT_DIAS_INHABIL")
public class NeeCatDiasInhabil implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1837312526350519891L;

	/**
	 * 
	 */
	

	public NeeCatDiasInhabil() {
    }
	
	@Id
	@Column(name="CVE_DIA_INHABIL")
	private String cveDiaInhabil;
	
	@Temporal(TemporalType.DATE)
	@Column(name="FEC_FECHAINHABIL")
	private Date fecFechainhabil;

	@Column(name="DES_DIA_INHABIL")
	private String desDiaInhabil;

	public String getCveDiaInhabil() {
		return cveDiaInhabil;
	}

	public void setCveDiaInhabil(String cveDiaInhabil) {
		this.cveDiaInhabil = cveDiaInhabil;
	}

	public Date getFecFechainhabil() {
		return fecFechainhabil;
	}

	public void setFecFechainhabil(Date fecFechainhabil) {
		this.fecFechainhabil = fecFechainhabil;
	}

	public String getDesDiaInhabil() {
		return desDiaInhabil;
	}

	public void setDesDiaInhabil(String desDiaInhabil) {
		this.desDiaInhabil = desDiaInhabil;
	}

}
