package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the DIT_DTS_EXTRA_PATRON database table.
 * 
 */
@Entity
@Table(name="DIT_DTS_EXTRA_PATRON")
public class DitDtsExtraPatron implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_PATRON_GENERAL")
	private long cveIdPatronGeneral;
	
    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INI_HUELGA")
	private Date fecIniHuelga;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
    
    @Temporal( TemporalType.DATE)
	@Column(name="FEC_MOVTO")
	private Date fecMovto;
    
	@Column(name="IND_ID_HUELGA")
	private String indIdHuelga;
	
	
	@Column(name="CVE_TIPO_MOVTO")
	private BigDecimal cveTipoMovto;

	
	@Column(name="CAN_TRAB_VIG_PER")
	private Integer canTrabVigPer;
	
	@Column(name="CAN_TRAB_VIG_EVE")
	private Integer canTrabVigEve;
	
	@Column(name="CAN_TRAB_VIG_CONS")
	private Integer canTrabVigCons;
	
	@Column(name="CAN_TRAB_MEX_EXTRAN")
	private Integer canTrabVigMexExtran;
	
	@Column(name="REF_SEC_NOTIF")
	private String refSecNotif;
	
	@Column(name="REF_ADIC_PENS")
	private String refAdicPens;
	
	public BigDecimal getCveTipoMovto() {
		return cveTipoMovto;
	}

	public void setCveTipoMovto(BigDecimal cveTipoMovto) {
		this.cveTipoMovto = cveTipoMovto;
	}

	//bi-directional one-to-one association to DitPatronGeneral
	@OneToOne
	@JoinColumn(name="CVE_ID_PATRON_GENERAL")
	private DitPatronGeneral ditPatronGeneral;

    public DitDtsExtraPatron() {
    }

	public long getCveIdPatronGeneral() {
		return this.cveIdPatronGeneral;
	}

	public void setCveIdPatronGeneral(long cveIdPatronGeneral) {
		this.cveIdPatronGeneral = cveIdPatronGeneral;
	}

	
	public Date getFecIniHuelga() {
		return this.fecIniHuelga;
	}

	public void setFecIniHuelga(Date fecIniHuelga) {
		this.fecIniHuelga = fecIniHuelga;
	}

	public Date getFecMovto() {
		return this.fecMovto;
	}

	public void setFecMovto(Date fecMovto) {
		this.fecMovto = fecMovto;
	}

	
	public String getIndIdHuelga() {
		return this.indIdHuelga;
	}

	public void setIndIdHuelga(String indIdHuelga) {
		this.indIdHuelga = indIdHuelga;
	}

	
	public DitPatronGeneral getDitPatronGeneral() {
		return this.ditPatronGeneral;
	}

	public void setDitPatronGeneral(DitPatronGeneral ditPatronGeneral) {
		this.ditPatronGeneral = ditPatronGeneral;
	}
	
	public Integer getCanTrabVigPer() {
		return canTrabVigPer;
	}

	public void setCanTrabVigPer(Integer canTrabVigPer) {
		this.canTrabVigPer = canTrabVigPer;
	}

	public Integer getCanTrabVigEve() {
		return canTrabVigEve;
	}

	public void setCanTrabVigEve(Integer canTrabVigEve) {
		this.canTrabVigEve = canTrabVigEve;
	}

	public Integer getCanTrabVigCons() {
		return canTrabVigCons;
	}

	public void setCanTrabVigCons(Integer canTrabVigCons) {
		this.canTrabVigCons = canTrabVigCons;
	}

	public Integer getCanTrabVigMexExtran() {
		return canTrabVigMexExtran;
	}

	public void setCanTrabVigMexExtran(Integer canTrabVigMexExtran) {
		this.canTrabVigMexExtran = canTrabVigMexExtran;
	}

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public String getRefSecNotif() {
		return refSecNotif;
	}

	public void setRefSecNotif(String refSecNotif) {
		this.refSecNotif = refSecNotif;
	}

	public String getRefAdicPens() {
		return refAdicPens;
	}

	public void setRefAdicPens(String refAdicPens) {
		this.refAdicPens = refAdicPens;
	}
	
}