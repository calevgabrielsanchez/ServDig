package mx.gob.imss.ctirss.delta.cobranza.service.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the D_COP_ACTIVIDADES_ECONOMICAS database table.
 * 
 */
@Entity
@Table(name="D_COP_ACTIVIDADES_ECONOMICAS")
public class DCopActividadesEconomica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CLAVE_ACT_ECO")
	private BigDecimal clabeActEco;
	
	@Column(name="CLAVE_GPO_ACT")
	private BigDecimal claveGpoAct;

	@Column(name="DESC_ACT_ECO")
	private String descActEco;

    public DCopActividadesEconomica() {
    }

	public String getDescActEco() {
		return this.descActEco;
	}

	public void setDescActEco(String descActEco) {
		this.descActEco = descActEco;
	}

	public BigDecimal getClabeActEco() {
		return clabeActEco;
	}

	public void setClabeActEco(BigDecimal clabeActEco) {
		this.clabeActEco = clabeActEco;
	}

	public BigDecimal getClaveGpoAct() {
		return claveGpoAct;
	}

	public void setClaveGpoAct(BigDecimal claveGpoAct) {
		this.claveGpoAct = claveGpoAct;
	}
	
	
	
}