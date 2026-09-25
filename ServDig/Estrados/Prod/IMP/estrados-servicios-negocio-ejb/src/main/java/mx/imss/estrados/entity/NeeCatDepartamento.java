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
@Table(name="NEE_CAT_DEPARTAMENTO")
public class NeeCatDepartamento implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5692891329272686469L;

	/**
	 * 
	 */
	

	public NeeCatDepartamento() {
	}
	
	@Id
	@Column(name="CVE_DEPTO")
	private Integer cveDepto;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_PROCESO")
	private NeeCatProceso neeCatProceso;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_AREANORMA")
	private NeeCatAreanormativa neeCatAreanormativa;
	
	@Column(name="DES_DEPARTAMENTO")
	private String desDepartamento;

	public Integer getCveDepto() {
		return cveDepto;
	}

	public void setCveDepto(Integer cveDepto) {
		this.cveDepto = cveDepto;
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

	public String getDesDepartamento() {
		return desDepartamento;
	}

	public void setDesDepartamento(String desDepartamento) {
		this.desDepartamento = desDepartamento;
	}

}
