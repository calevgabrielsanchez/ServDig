package mx.imss.estrados.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="NEE_CAT_AREA_RESP_NOTIF")
public class NeeCatAreaRespNotif implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7693703602348701355L;

	/**
	 * 
	 */
	

	public NeeCatAreaRespNotif() {
    }
	
	@Id
	@Column(name="CVE_AREA_RESP_NOTIF")
	private Integer cveAreaRespNotif;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_PROCESO")
	private NeeCatProceso neeCatProceso;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_AREANORMA")
	private NeeCatAreanormativa neeCatAreanormativa;

	@Column(name="CVE_SSODEPTO")
	private Integer cveSSODepto;
	
	public Integer getCveAreaRespNotif() {
		return cveAreaRespNotif;
	}

	public void setCveAreaRespNotif(Integer cveAreaRespNotif) {
		this.cveAreaRespNotif = cveAreaRespNotif;
	}

	public NeeCatProceso getNeeCatProceso() {
		return neeCatProceso;
	}

	public void setNeeCatProceso(NeeCatProceso neeCatProceso) {
		this.neeCatProceso = neeCatProceso;
	}

	public NeeCatAreanormativa getNeeCatAreanormativa() {
		return neeCatAreanormativa;
	}

	public void setNeeCatAreanormativa(NeeCatAreanormativa neeCatAreanormativa) {
		this.neeCatAreanormativa = neeCatAreanormativa;
	}

	public Integer getCveSSODepto() {
		return cveSSODepto;
	}

	public void setCveSSODepto(Integer cveSSODepto) {
		this.cveSSODepto = cveSSODepto;
	}

	
	
}
