package mx.gob.imss.ctirss.idse.persistencia;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the IDT_HISTORICO_MOVIMIENTOS database table.
 * 
 */
@Entity
@Table(name="IDT_HISTORICO_MOVIMIENTOS")
public class IdtHistoricoMovimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="IDT_HISTORICO_MOVIMIENTOS_CVEHISTORIAMOVIMIENTOS_GENERATOR", sequenceName="SEQ_MOVIMIENTO_HIST", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="IDT_HISTORICO_MOVIMIENTOS_CVEHISTORIAMOVIMIENTOS_GENERATOR")
	@Column(name="CVE_HISTORIA_MOVIMIENTOS")
	private Long cveHistoriaMovimientos;

	@Column(name="REF_USUARIO_AFECTA")
	private String refUsuarioAfecta;

	@Column(name="STP_FECHA_MOVIMIENTO")
	private Timestamp stpFechaMovimiento;

	//bi-directional many-to-one association to IdcAccionMovimiento
    @ManyToOne
	@JoinColumn(name="CVE_ACCION_MOVIMIENTO")
	private IdcAccionMovimiento idcAccionMovimiento;

	//bi-directional many-to-one association to IdtRegistrosPatronale
    @ManyToOne
	@JoinColumn(name="CVE_REGISTRO_PATRONAL")
	private IdtRegistrosPatronale idtRegistrosPatronale;

    public IdtHistoricoMovimiento() {
    }

	public Long getCveHistoriaMovimientos() {
		return this.cveHistoriaMovimientos;
	}

	public void setCveHistoriaMovimientos(Long cveHistoriaMovimientos) {
		this.cveHistoriaMovimientos = cveHistoriaMovimientos;
	}

	public String getRefUsuarioAfecta() {
		return this.refUsuarioAfecta;
	}

	public void setRefUsuarioAfecta(String refUsuarioAfecta) {
		this.refUsuarioAfecta = refUsuarioAfecta;
	}

	public Timestamp getStpFechaMovimiento() {
		return this.stpFechaMovimiento;
	}

	public void setStpFechaMovimiento(Timestamp stpFechaMovimiento) {
		this.stpFechaMovimiento = stpFechaMovimiento;
	}

	public IdcAccionMovimiento getIdcAccionMovimiento() {
		return this.idcAccionMovimiento;
	}

	public void setIdcAccionMovimiento(IdcAccionMovimiento idcAccionMovimiento) {
		this.idcAccionMovimiento = idcAccionMovimiento;
	}
	
	public IdtRegistrosPatronale getIdtRegistrosPatronale() {
		return this.idtRegistrosPatronale;
	}

	public void setIdtRegistrosPatronale(IdtRegistrosPatronale idtRegistrosPatronale) {
		this.idtRegistrosPatronale = idtRegistrosPatronale;
	}
	
}