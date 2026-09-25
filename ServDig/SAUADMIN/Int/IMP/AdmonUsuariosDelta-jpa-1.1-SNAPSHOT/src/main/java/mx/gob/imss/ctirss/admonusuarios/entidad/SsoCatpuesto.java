package mx.gob.imss.ctirss.admonusuarios.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mx.gob.imss.ctirss.admonusuarios.abstractModel.AbstractModel;


/**
 * The persistent class for the SSO_CATPUESTOS database table.
 * 
 */
@Entity
@Table(name="SSO_CATPUESTOS")
public class SsoCatpuesto  extends AbstractModel {
	private static final long serialVersionUID = 1L;
	private Long cveSsopuesto;
	private String desPuesto;
	private SsoCatdepartamento ssoCatdepartamento;
	

    public SsoCatpuesto() {
    }


	@Id
	@Column(name="CVE_SSOPUESTO", unique=true, nullable=true)
	public Long getCveSsopuesto() {
		return this.cveSsopuesto;
	}

	public void setCveSsopuesto(Long cveSsopuesto) {
		this.cveSsopuesto = cveSsopuesto;
	}


	@Column(name="DES_PUESTO", length=50)
	public String getDesPuesto() {
		return this.desPuesto;
	}

	public void setDesPuesto(String desPuesto) {
		this.desPuesto = desPuesto;
	}


	//bi-directional many-to-one association to SsoCatdepartamento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_SSODEPTO")
	public SsoCatdepartamento getSsoCatdepartamento() {
		return this.ssoCatdepartamento;
	}

	public void setSsoCatdepartamento(SsoCatdepartamento ssoCatdepartamento) {
		this.ssoCatdepartamento = ssoCatdepartamento;
	}

}