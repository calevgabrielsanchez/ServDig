package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the SPT_CABECERA_SOLIC_PENSION database table.
 * 
 */
@Entity
@Table(name="SPT_CABECERA_SOLIC_PENSION")
@NamedQuery(name="SptCabeceraSolicPension.findAll", query="SELECT s FROM SptCabeceraSolicPension s")
public class SptCabeceraSolicPension implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id	
	@SequenceGenerator(name = "SEQ_SPTCABECERASOLICPENSION", sequenceName = "SEQ_SPTCABECERASOLICPENSION")
	@GeneratedValue(generator = "SEQ_SPTCABECERASOLICPENSION")
	@Column(name="CVE_ID_CABECERA_SOLIC_PENSION")
	private long cveIdCabeceraSolicPension;

	@Column(name="CVE_AFORE")
	private String cveAfore;

	@Column(name="CVE_DELEGACION")
	private String cveDelegacion;

	@Column(name="CVE_SUBDELEGACION")
	private String cveSubdelegacion;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_BAJA")
	private Date fecBaja;

//	@Temporal(TemporalType.DATE)
//	@Column(name="FEC_ELECCION_ASEGURADORA")
//	private Date fecEleccionAseguradora;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO_AJUSTE")
	private Date fecInicioAjuste;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INSCRIPCION_IMSS")
	private Date fecInscripcionImss;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REFORMA_LEY")
	private Date fecReformaLey;

	@Column(name="ID_ASEGURADORA")
	private String idAseguradora;

	//@Column(name="ID_ESTADO_CIVIL")
	//private String idEstadoCivil;

	@Column(name="ID_REFORMA_LEY")
	private String idReformaLey;

	//@Column(name="ID_TIPO_MOVIMIENTO")
	//private String idTipoMovimiento;

	@Column(name="IND_AUTORIZA_CP")
	private String indAutorizaCp;

	//bi-directional many-to-one association to DitAsignacionNss
	@ManyToOne
	@JoinColumn(name="CVE_ID_ASIGNACION_NSS")
	private DitAsignacionNss ditAsignacionNss;

	public SptCabeceraSolicPension() {
	}

	public long getCveIdCabeceraSolicPension() {
		return this.cveIdCabeceraSolicPension;
	}

	public void setCveIdCabeceraSolicPension(long cveIdCabeceraSolicPension) {
		this.cveIdCabeceraSolicPension = cveIdCabeceraSolicPension;
	}

	public String getCveAfore() {
		return this.cveAfore;
	}

	public void setCveAfore(String cveAfore) {
		this.cveAfore = cveAfore;
	}

	public String getCveDelegacion() {
		return this.cveDelegacion;
	}

	public void setCveDelegacion(String cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}

	public String getCveSubdelegacion() {
		return this.cveSubdelegacion;
	}

	public void setCveSubdelegacion(String cveSubdelegacion) {
		this.cveSubdelegacion = cveSubdelegacion;
	}

	public Date getFecBaja() {
		return this.fecBaja;
	}

	public void setFecBaja(Date fecBaja) {
		this.fecBaja = fecBaja;
	}
//
//	public Date getFecEleccionAseguradora() {
//		return this.fecEleccionAseguradora;
//	}
//
//	public void setFecEleccionAseguradora(Date fecEleccionAseguradora) {
//		this.fecEleccionAseguradora = fecEleccionAseguradora;
//	}

	public Date getFecInicioAjuste() {
		return this.fecInicioAjuste;
	}

	public void setFecInicioAjuste(Date fecInicioAjuste) {
		this.fecInicioAjuste = fecInicioAjuste;
	}

	public Date getFecInscripcionImss() {
		return this.fecInscripcionImss;
	}

	public void setFecInscripcionImss(Date fecInscripcionImss) {
		this.fecInscripcionImss = fecInscripcionImss;
	}

	public Date getFecReformaLey() {
		return this.fecReformaLey;
	}

	public void setFecReformaLey(Date fecReformaLey) {
		this.fecReformaLey = fecReformaLey;
	}

	public String getIdAseguradora() {
		return this.idAseguradora;
	}

	public void setIdAseguradora(String idAseguradora) {
		this.idAseguradora = idAseguradora;
	}

//	public String getIdEstadoCivil() {
//		return this.idEstadoCivil;
//	}
//
//	public void setIdEstadoCivil(String idEstadoCivil) {
//		this.idEstadoCivil = idEstadoCivil;
//	}

	public String getIdReformaLey() {
		return this.idReformaLey;
	}

	public void setIdReformaLey(String idReformaLey) {
		this.idReformaLey = idReformaLey;
	}

//	public String getIdTipoMovimiento() {
//		return this.idTipoMovimiento;
//	}
//
//	public void setIdTipoMovimiento(String idTipoMovimiento) {
//		this.idTipoMovimiento = idTipoMovimiento;
//	}

	public String getIndAutorizaCp() {
		return this.indAutorizaCp;
	}

	public void setIndAutorizaCp(String indAutorizaCp) {
		this.indAutorizaCp = indAutorizaCp;
	}

	public DitAsignacionNss getDitAsignacionNss() {
		return this.ditAsignacionNss;
	}

	public void setDitAsignacionNss(DitAsignacionNss ditAsignacionNss) {
		this.ditAsignacionNss = ditAsignacionNss;
	}

}