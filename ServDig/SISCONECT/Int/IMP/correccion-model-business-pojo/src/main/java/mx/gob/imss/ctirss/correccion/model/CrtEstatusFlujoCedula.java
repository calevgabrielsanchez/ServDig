package mx.gob.imss.ctirss.correccion.model;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

import java.util.Set;


/**
 * The persistent class for the CRT_ESTATUS_FLUJO_CEDULAS database table.
 * 
 */
@Entity
@Table(name="CRT_ESTATUS_FLUJO_CEDULAS")
public class CrtEstatusFlujoCedula  extends AbstractModel {
	private static final Long serialVersionUID = 1L;
 
	
	@Id
	@Column(name="CVE_ESTATUS")
	private Long cveEstatus;

	@Column(name="TX_DESCRIPCION")
	private String txDescripcion;

	/**
	//bi-directional many-to-one association to CrtControlFlujoCedula
	@OneToMany(mappedBy="crtEstatusFlujoCedula")
	private Set<CrtControlFlujoCedula> crtControlFlujoCedulas;
*/
    public CrtEstatusFlujoCedula() {
    }

	public Long getCveEstatus() {
		return this.cveEstatus;
	}

	public void setCveEstatus(Long cveEstatus) {
		this.cveEstatus = cveEstatus;
	}

	public String getTxDescripcion() {
		return this.txDescripcion;
	}

	public void setTxDescripcion(String txDescripcion) {
		this.txDescripcion = txDescripcion;
	}

	/**
	public Set<CrtControlFlujoCedula> getCrtControlFlujoCedulas() {
		return this.crtControlFlujoCedulas;
	}

	public void setCrtControlFlujoCedulas(Set<CrtControlFlujoCedula> crtControlFlujoCedulas) {
		this.crtControlFlujoCedulas = crtControlFlujoCedulas;
	}**/
	
}