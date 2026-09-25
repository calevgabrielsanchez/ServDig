package mx.gob.imss.ctirss.delta.cobranza.service.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the D_COP_FACTOR database table.
 * 
 */
@Entity
@Table(name="D_COP_FACTOR")
public class DCopFactor implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DCopFactorPK id;

	@Column(name="FAC_ACT")
	private BigDecimal facAct;

	@Column(name="FAC_INT")
	private BigDecimal facInt;

    @Temporal( TemporalType.DATE)
	@Column(name="FACREC_DOF")
	private Date facrecDof;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_VIGENCIA")
	private Date fechaVigencia;

    @Column(name="INPC")
	private BigDecimal inpc;

    @Temporal( TemporalType.DATE)
	@Column(name="INPC_REC")
	private Date inpcRec;

    @Column(name="RECARGOS")
	private BigDecimal recargos;

    public DCopFactor() {
    }

	public DCopFactorPK getId() {
		return this.id;
	}

	public void setId(DCopFactorPK id) {
		this.id = id;
	}
	
	public BigDecimal getFacAct() {
		return this.facAct;
	}

	public void setFacAct(BigDecimal facAct) {
		this.facAct = facAct;
	}

	public BigDecimal getFacInt() {
		return this.facInt;
	}

	public void setFacInt(BigDecimal facInt) {
		this.facInt = facInt;
	}

	public Date getFacrecDof() {
		return this.facrecDof;
	}

	public void setFacrecDof(Date facrecDof) {
		this.facrecDof = facrecDof;
	}

	public Date getFechaVigencia() {
		return this.fechaVigencia;
	}

	public void setFechaVigencia(Date fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}

	public BigDecimal getInpc() {
		return this.inpc;
	}

	public void setInpc(BigDecimal inpc) {
		this.inpc = inpc;
	}

	public Date getInpcRec() {
		return this.inpcRec;
	}

	public void setInpcRec(Date inpcRec) {
		this.inpcRec = inpcRec;
	}

	public BigDecimal getRecargos() {
		return this.recargos;
	}

	public void setRecargos(BigDecimal recargos) {
		this.recargos = recargos;
	}

}