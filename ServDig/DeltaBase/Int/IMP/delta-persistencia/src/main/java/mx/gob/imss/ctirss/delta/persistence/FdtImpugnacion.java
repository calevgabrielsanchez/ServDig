package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the FDT_IMPUGNACION database table.
 * 
 */
@Entity
@Table(name="FDT_IMPUGNACION")
public class FdtImpugnacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtImpugnacionPK id;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_CIERRE_MI")
	private Date fhCierreMi;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_MODIFICA")
	private Date fhModifica;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_PRESENTA_MEDIO")
	private Date fhPresentaMedio;

	@Column(name="ID_PROMOVENTE", precision=22)
	private BigDecimal idPromovente;

    @Lob()
	@Column(name="TX_OBSERVACION")
	private byte[] txObservacion;

	@Column(name="TX_USER", length=12)
	private String txUser;

	//bi-directional many-to-one association to FdiSancion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SANCION", nullable=false, insertable=false, updatable=false)
	private FdiSancion fdiSancion;

	//bi-directional many-to-one association to FdcMediosImpugnacion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_IMPUGNACION", nullable=false, insertable=false, updatable=false)
	private FdcMediosImpugnacion fdcMediosImpugnacion;

	//bi-directional many-to-one association to FdcStatusMimpugna
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="STATUS_MEDIOIMP")
	private FdcStatusMimpugna fdcStatusMimpugna;

    public FdtImpugnacion() {
    }

	public FdtImpugnacionPK getId() {
		return this.id;
	}

	public void setId(FdtImpugnacionPK id) {
		this.id = id;
	}
	
	public Date getFhCierreMi() {
		return this.fhCierreMi;
	}

	public void setFhCierreMi(Date fhCierreMi) {
		this.fhCierreMi = fhCierreMi;
	}

	public Date getFhModifica() {
		return this.fhModifica;
	}

	public void setFhModifica(Date fhModifica) {
		this.fhModifica = fhModifica;
	}

	public Date getFhPresentaMedio() {
		return this.fhPresentaMedio;
	}

	public void setFhPresentaMedio(Date fhPresentaMedio) {
		this.fhPresentaMedio = fhPresentaMedio;
	}

	public BigDecimal getIdPromovente() {
		return this.idPromovente;
	}

	public void setIdPromovente(BigDecimal idPromovente) {
		this.idPromovente = idPromovente;
	}

	public byte[] getTxObservacion() {
		return this.txObservacion;
	}

	public void setTxObservacion(byte[] txObservacion) {
		this.txObservacion = txObservacion != null ? txObservacion.clone() : null;
	}

	public String getTxUser() {
		return this.txUser;
	}

	public void setTxUser(String txUser) {
		this.txUser = txUser;
	}

	public FdiSancion getFdiSancion() {
		return this.fdiSancion;
	}

	public void setFdiSancion(FdiSancion fdiSancion) {
		this.fdiSancion = fdiSancion;
	}
	
	public FdcMediosImpugnacion getFdcMediosImpugnacion() {
		return this.fdcMediosImpugnacion;
	}

	public void setFdcMediosImpugnacion(FdcMediosImpugnacion fdcMediosImpugnacion) {
		this.fdcMediosImpugnacion = fdcMediosImpugnacion;
	}
	
	public FdcStatusMimpugna getFdcStatusMimpugna() {
		return this.fdcStatusMimpugna;
	}

	public void setFdcStatusMimpugna(FdcStatusMimpugna fdcStatusMimpugna) {
		this.fdcStatusMimpugna = fdcStatusMimpugna;
	}
	
}