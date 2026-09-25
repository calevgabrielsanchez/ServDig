package mx.gob.imss.ctirss.delta.cobranza.service.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the D_COP_MOVIMIENTOS_PATRONALES database table.
 * 
 */
@Entity
@Table(name="D_COP_MOVIMIENTOS_PATRONALES")
public class DCopMovimientosPatronale implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CVE_MOV_PATRONAL")
	private String cveMovPatronal;
	
	@Column(name="DESC_MOV_PATRONAL")
	private String descMovPatronal;

    public String getCveMovPatronal() {
		return cveMovPatronal;
	}

	public void setCveMovPatronal(String cveMovPatronal) {
		this.cveMovPatronal = cveMovPatronal;
	}

	public DCopMovimientosPatronale() {
    }

	public String getDescMovPatronal() {
		return this.descMovPatronal;
	}

	public void setDescMovPatronal(String descMovPatronal) {
		this.descMovPatronal = descMovPatronal;
	}

}