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
 * The persistent class for the SSO_CATDEPTOMODULO database table.
 * 
 */
@Entity
@Table(name="SSO_CATDEPTOMODULO")
public class SsoCatdeptomodulo  extends AbstractModel {
	private static final long serialVersionUID = 1L;
	private Long cveSsodeptomodulo;
	private DicModulo dicModulo;
	private SsoCatdepartamento ssoCatdepartamento;

    public SsoCatdeptomodulo() {
    }


	@Id
	@Column(name="CVE_SSODEPTOMODULO", unique=true, nullable=false)
	public Long getCveSsodeptomodulo() {
		return this.cveSsodeptomodulo;
	}

	public void setCveSsodeptomodulo(Long cveSsodeptomodulo) {
		this.cveSsodeptomodulo = cveSsodeptomodulo;
	}

	//bi-directional many-to-one association to DicModulo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_MODULO")
	public DicModulo getDicModulo() {
		return this.dicModulo;
	}

	public void setDicModulo(DicModulo dicModulo) {
		this.dicModulo = dicModulo;
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