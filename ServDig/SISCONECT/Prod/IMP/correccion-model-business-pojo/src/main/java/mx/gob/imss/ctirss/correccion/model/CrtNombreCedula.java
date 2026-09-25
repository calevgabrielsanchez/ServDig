package mx.gob.imss.ctirss.correccion.model;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

import java.util.Set;


/**
 * The persistent class for the CRT_NOMBRE_CEDULAS database table.
 * 
 */
@Entity
@Table(name="CRT_NOMBRE_CEDULAS")
public class CrtNombreCedula  extends AbstractModel {
	private static final Long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_CEDULA")
	private Long cveCedula;

	@Column(name="TX_NOMBRE")
	private String txNombre;

	/**
	//bi-directional many-to-one association to CrtControlFlujoCedula
	@OneToMany(mappedBy="crtNombreCedula")
	private Set<CrtControlFlujoCedula> crtControlFlujoCedulas;
*/
    public CrtNombreCedula() {
    }

	public Long getCveCedula() {
		return this.cveCedula;
	}

	public void setCveCedula(Long cveCedula) {
		this.cveCedula = cveCedula;
	}

	public String getTxNombre() {
		return this.txNombre;
	}

	public void setTxNombre(String txNombre) {
		this.txNombre = txNombre;
	}

	/**
	public Set<CrtControlFlujoCedula> getCrtControlFlujoCedulas() {
		return this.crtControlFlujoCedulas;
	}

	public void setCrtControlFlujoCedulas(Set<CrtControlFlujoCedula> crtControlFlujoCedulas) {
		this.crtControlFlujoCedulas = crtControlFlujoCedulas;
	}
	**/
	
}