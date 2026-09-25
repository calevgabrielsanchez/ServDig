package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the NASPATHS database table.
 * 
 */
@Entity
@Table(name="NASPATHS")
@NamedQuery(name="Naspath.findAll", query="SELECT n FROM Naspath n")
public class Naspath implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idnaspath;

	private String descnaspaths;

	public Naspath() {
	}

	public long getIdnaspath() {
		return this.idnaspath;
	}

	public void setIdnaspath(long idnaspath) {
		this.idnaspath = idnaspath;
	}

	public String getDescnaspaths() {
		return this.descnaspaths;
	}

	public void setDescnaspaths(String descnaspaths) {
		this.descnaspaths = descnaspaths;
	}

}