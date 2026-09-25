package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SPT_CABEC_SOLIC_PENS_SOLIC database table.
 * 
 */
@Entity
@Table(name="SPT_CABEC_SOLIC_PENS_SOLIC")
@NamedQuery(name="SptCabecSolicPensSolic.findAll", query="SELECT s FROM SptCabecSolicPensSolic s")
public class SptCabecSolicPensSolic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTCABECSOLICPENSSOLIC", sequenceName = "SEQ_SPTCABECSOLICPENSSOLIC")
	@GeneratedValue(generator = "SEQ_SPTCABECSOLICPENSSOLIC")
	@Column(name="CVE_ID_CABEC_SOLIC_PENS_SOLIC")
	private long cveIdCabecSolicPensSolic;

	@Column(name="CVE_ID_CABECERA_SOLIC_PENSION")
	private BigDecimal cveIdCabeceraSolicPension;


	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitSolicitud
	@ManyToOne
	@JoinColumn(name="CVE_ID_SOLICITUD")
	private DitSolicitud ditSolicitud;

	public SptCabecSolicPensSolic() {
	}

	public long getCveIdCabecSolicPensSolic() {
		return this.cveIdCabecSolicPensSolic;
	}

	public void setCveIdCabecSolicPensSolic(long cveIdCabecSolicPensSolic) {
		this.cveIdCabecSolicPensSolic = cveIdCabecSolicPensSolic;
	}

	public BigDecimal getCveIdCabeceraSolicPension() {
		return this.cveIdCabeceraSolicPension;
	}

	public void setCveIdCabeceraSolicPension(BigDecimal cveIdCabeceraSolicPension) {
		this.cveIdCabeceraSolicPension = cveIdCabeceraSolicPension;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public DitSolicitud getDitSolicitud() {
		return this.ditSolicitud;
	}

	public void setDitSolicitud(DitSolicitud ditSolicitud) {
		this.ditSolicitud = ditSolicitud;
	}


}
