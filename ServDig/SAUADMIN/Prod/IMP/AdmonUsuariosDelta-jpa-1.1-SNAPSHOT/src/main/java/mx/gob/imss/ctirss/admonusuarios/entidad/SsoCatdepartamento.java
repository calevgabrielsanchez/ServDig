package mx.gob.imss.ctirss.admonusuarios.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.admonusuarios.abstractModel.AbstractModel;



/**
 * The persistent class for the SSO_CATDEPARTAMENTO database table.
 * 
 */
@Entity
@Table(name="SSO_CATDEPARTAMENTO")
public class SsoCatdepartamento  extends AbstractModel {
	private static final long serialVersionUID = 1L;
	private long cveSsodepto;
	private String desDepartamento;
	private String desLeyendaAcuse;
	private SsoCatareanormativa ssoCatareanormativa;
//	private SsoCatdepartamento ssoCatdepartamento;
	private SsoCatdepartamento ssoCatdepartamentoPadre;
	private SsoCatdepartamento ssoCatdepartamentoGeneral;

    public SsoCatdepartamento() {
    }


	@Id
	@Column(name="CVE_SSODEPTO", unique=true, nullable=false)
	public long getCveSsodepto() {
		return this.cveSsodepto;
	}

	public void setCveSsodepto(long cveSsodepto) {
		this.cveSsodepto = cveSsodepto;
	}


	@Column(name="DES_DEPARTAMENTO", length=100)
	public String getDesDepartamento() {
		return this.desDepartamento;
	}

	public void setDesDepartamento(String desDepartamento) {
		this.desDepartamento = desDepartamento;
	}

//	@Transient
	@Column(name="DES_LEYENDAACUSE", length=100)
	public String getDesLeyendaAcuse() {
		return this.desLeyendaAcuse;
	}

	public void setDesLeyendaAcuse(String desLeyendaAcuse) {
		this.desLeyendaAcuse = desLeyendaAcuse;
	}


	//bi-directional many-to-one association to SsoCatareanormativa
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_SSOAREANORMA")
	public SsoCatareanormativa getSsoCatareanormativa() {
		return this.ssoCatareanormativa;
	}

	public void setSsoCatareanormativa(SsoCatareanormativa ssoCatareanormativa) {
		this.ssoCatareanormativa = ssoCatareanormativa;
	}
	
//
//	//bi-directional many-to-one association to SsoCatdepartamento
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="CVE_DEPTO_PADRE")
//	public SsoCatdepartamento getSsoCatdepartamento() {
//		return this.ssoCatdepartamento;
//	}
//
//	public void setSsoCatdepartamento(SsoCatdepartamento ssoCatdepartamento) {
//		this.ssoCatdepartamento = ssoCatdepartamento;
//	}
	
	//uni-directional many-to-one association to SsoCatdepartamento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_DEPTO_PADRE")
	public SsoCatdepartamento getSsoCatdepartamentoPadre() {
		return this.ssoCatdepartamentoPadre;
	}

	public void setSsoCatdepartamentoPadre(SsoCatdepartamento ssoCatdepartamentoPadre) {
		this.ssoCatdepartamentoPadre = ssoCatdepartamentoPadre;
	}
	

	//bi-directional one-to-one association to SsoCatdepartamento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_DEPTO_GENERAL")
	public SsoCatdepartamento getSsoCatdepartamentoGeneral() {
		return this.ssoCatdepartamentoGeneral;
	}

	public void setSsoCatdepartamentoGeneral(SsoCatdepartamento ssoCatdepartamento2) {
		this.ssoCatdepartamentoGeneral = ssoCatdepartamento2;
	}

	
}