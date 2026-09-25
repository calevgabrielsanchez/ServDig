package mx.gob.imss.ctirss.idse.persistencia;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the IDC_ACCION_MOVIMIENTOS database table.
 * 
 */
@Entity
@Table(name="IDC_ACCION_MOVIMIENTOS")
public class IdcAccionMovimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ACCION_MOVIMIENTO")
	private long cveAccionMovimiento;

	@Column(name="NUM_ACCION_MOVIMIENTO")
	private BigDecimal numAccionMovimiento;

	@Column(name="REF_ACCION_MOVIMIENTO")
	private String refAccionMovimiento;

	//bi-directional many-to-one association to IdtHistoricoMovimiento
	@OneToMany(mappedBy="idcAccionMovimiento")
	private List<IdtHistoricoMovimiento> idtHistoricoMovimientos;

    public IdcAccionMovimiento() {
    }

	public long getCveAccionMovimiento() {
		return this.cveAccionMovimiento;
	}

	public void setCveAccionMovimiento(long cveAccionMovimiento) {
		this.cveAccionMovimiento = cveAccionMovimiento;
	}

	public BigDecimal getNumAccionMovimiento() {
		return this.numAccionMovimiento;
	}

	public void setNumAccionMovimiento(BigDecimal numAccionMovimiento) {
		this.numAccionMovimiento = numAccionMovimiento;
	}

	public String getRefAccionMovimiento() {
		return this.refAccionMovimiento;
	}

	public void setRefAccionMovimiento(String refAccionMovimiento) {
		this.refAccionMovimiento = refAccionMovimiento;
	}

	public List<IdtHistoricoMovimiento> getIdtHistoricoMovimientos() {
		return this.idtHistoricoMovimientos;
	}

	public void setIdtHistoricoMovimientos(List<IdtHistoricoMovimiento> idtHistoricoMovimientos) {
		this.idtHistoricoMovimientos = idtHistoricoMovimientos;
	}
	
}