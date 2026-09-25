package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the DIC_ESTATUS_ROL_OPERACION_CE database table.
 * 
 */
@Entity
@Table(name = "DIC_ESTATUS_ROL_OPERACION_CE")
public class DicEstatusRolOperacionCe implements Serializable {

	/** Serial version */
	private static final long serialVersionUID = 8131838599846498724L;

	@EmbeddedId
	private DicEstatusRolOperacionCePK id;

	@Column(name = "IND_USUARIO")
	private Integer indUsuario;

	public DicEstatusRolOperacionCePK getId() {
		return id;
	}

	public void setId(DicEstatusRolOperacionCePK id) {
		this.id = id;
	}

	/**
	 * @return the indUsuario
	 */
	public Integer getIndUsuario() {
		return indUsuario;
	}

	/**
	 * @param indUsuario
	 *            the indUsuario to set
	 */
	public void setIndUsuario(Integer indUsuario) {
		this.indUsuario = indUsuario;
	}

}
