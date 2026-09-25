package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="DIT_CITA_SOLICITUD")
public class DitCitaSolicitud implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1576323843385422972L;
	
	@Id
	@SequenceGenerator(name = "SEQ_DITCITASOLICITUD", sequenceName = "SEQ_DITCITASOLICITUD")
    @GeneratedValue(generator = "SEQ_DITCITASOLICITUD")
	@Column(name="CVE_ID_CITA_SOLICITUD")
	private Long cveIdCitaSolicitud;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_CITA")
	private Date fecCita;
	
	@Column(name="REF_FOLIO_CITA", nullable=false, length=255)
	private String refFolioCita;
	
	@Column(name="NUM_CONTADOR_CAMBIO_CITA")
	private Integer numContadorCambioCita;
	
	@Column(name="IND_CITA_ACTIVA")
	private Boolean indCitaActiva;
	
	
    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

    //bi-directional many-to-one association to DitSolicitud
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_SOLICITUD")
	private DitSolicitud ditSolicitud;
    
  //bi-directional many-to-one association to DicTipoSolicitud
  	@ManyToOne(fetch=FetchType.LAZY)
  	@JoinColumn(name="CVE_ID_SUBDELEGACION")
  	private DicSubdelegacion dicSubdelegacion;

	public Long getCveIdCitaSolicitud() {
		return cveIdCitaSolicitud;
	}

	public void setCveIdCitaSolicitud(Long cveIdCitaSolicitud) {
		this.cveIdCitaSolicitud = cveIdCitaSolicitud;
	}

	public Date getFecCita() {
		return fecCita;
	}

	public void setFecCita(Date fecCita) {
		this.fecCita = fecCita;
	}

	public String getRefFolioCita() {
		return refFolioCita;
	}

	public void setRefFolioCita(String refFolioCita) {
		this.refFolioCita = refFolioCita;
	}

	public Integer getNumContadorCambioCita() {
		return numContadorCambioCita;
	}

	public void setNumContadorCambioCita(Integer numContadorCambioCita) {
		this.numContadorCambioCita = numContadorCambioCita;
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

	public DitSolicitud getDitSolicitud() {
		return ditSolicitud;
	}

	public void setDitSolicitud(DitSolicitud ditSolicitud) {
		this.ditSolicitud = ditSolicitud;
	}

	public DicSubdelegacion getDicSubdelegacion() {
		return dicSubdelegacion;
	}

	public void setDicSubdelegacion(DicSubdelegacion dicSubdelegacion) {
		this.dicSubdelegacion = dicSubdelegacion;
	}

	public Boolean getIndCitaActiva() {
		return indCitaActiva;
	}

	public void setIndCitaActiva(Boolean indCitaActiva) {
		this.indCitaActiva = indCitaActiva;
	}
	
	

}
