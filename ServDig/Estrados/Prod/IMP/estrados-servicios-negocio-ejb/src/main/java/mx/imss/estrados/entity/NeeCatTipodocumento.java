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
@Table(name="NEE_CAT_TIPODOCUMENTO")
public class NeeCatTipodocumento implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2374544128369541289L;

	/**
	 * 
	 */
	

	public NeeCatTipodocumento() {
	}
	
	@Id
	@Column(name="CVE_TIPODOCTO")
	private Integer cveTipodocto;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_PROCESO")
	private NeeCatProceso neeCatProceso;
	
	@Column(name="DES_TIPODOCUMENTO")
	private String desTipodocumento;

	public Integer getCveTipodocto() {
		return cveTipodocto;
	}

	public void setCveTipodocto(Integer cveTipodocto) {
		this.cveTipodocto = cveTipodocto;
	}

	public NeeCatProceso getNeeCatProceso() {
		return neeCatProceso;
	}

	public void setNeeCatProceso(NeeCatProceso neeCatProceso) {
		this.neeCatProceso = neeCatProceso;
	}

	public String getDesTipodocumento() {
		return desTipodocumento;
	}

	public void setDesTipodocumento(String desTipodocumento) {
		this.desTipodocumento = desTipodocumento;
	}

}
