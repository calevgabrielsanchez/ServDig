package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the FDT_A3_PERSONA_SUJETO database table.
 * 
 */
@Entity
@Table(name="FDT_A3_PERSONA_SUJETO")
public class FdtA3PersonaSujeto implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtA3PersonaSujetoPK id;

	@Column(name="IM_REGULARIZADO", nullable=false, precision=12, scale=2)
	private BigDecimal imRegularizado;

	@Column(name="NU_PERSONAS_AFILIADAS", nullable=false, precision=6)
	private BigDecimal nuPersonasAfiliadas;

	@Column(name="TX_TP_SUJETO_ASEG", nullable=false, length=30)
	private String txTpSujetoAseg;

	//bi-directional many-to-one association to FdtRegistroPeriodo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false, insertable=false, updatable=false)
		})
	private FdtRegistroPeriodo fdtRegistroPeriodo;

    public FdtA3PersonaSujeto() {
    }

	public FdtA3PersonaSujetoPK getId() {
		return this.id;
	}

	public void setId(FdtA3PersonaSujetoPK id) {
		this.id = id;
	}
	
	public BigDecimal getImRegularizado() {
		return this.imRegularizado;
	}

	public void setImRegularizado(BigDecimal imRegularizado) {
		this.imRegularizado = imRegularizado;
	}

	public BigDecimal getNuPersonasAfiliadas() {
		return this.nuPersonasAfiliadas;
	}

	public void setNuPersonasAfiliadas(BigDecimal nuPersonasAfiliadas) {
		this.nuPersonasAfiliadas = nuPersonasAfiliadas;
	}

	public String getTxTpSujetoAseg() {
		return this.txTpSujetoAseg;
	}

	public void setTxTpSujetoAseg(String txTpSujetoAseg) {
		this.txTpSujetoAseg = txTpSujetoAseg;
	}

	public FdtRegistroPeriodo getFdtRegistroPeriodo() {
		return this.fdtRegistroPeriodo;
	}

	public void setFdtRegistroPeriodo(FdtRegistroPeriodo fdtRegistroPeriodo) {
		this.fdtRegistroPeriodo = fdtRegistroPeriodo;
	}
	
}