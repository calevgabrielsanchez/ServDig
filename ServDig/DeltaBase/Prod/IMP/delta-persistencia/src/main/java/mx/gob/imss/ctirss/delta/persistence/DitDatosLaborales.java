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
@Table(name="DIT_DATOS_LABORALES")
public class DitDatosLaborales implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "SEQ_DITDATOSLABORALES", sequenceName = "SEQ_DITDATOSLABORALES")
    @GeneratedValue(generator = "SEQ_DITDATOSLABORALES")
	@Column(name="CVE_ID_DATOS_LABORALES")
	private Long cveIdDatosLaborales;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_CORRECCION_DATOS_ASEG", referencedColumnName = "CVE_ID_CORRECCION_DATOS_ASEG", nullable = false)
	private DitCorreccionDatosAsegurado  ditCorreccionDatosAsegurado;
	
	@Column(name="NOM_PATRON")
	private String nombrePatron;
	
	@Column(name="CVE_ENT", length=50)
	private String cveEnt;

	@Column(name="REF_FEC_INSCRIPCION", length=30)
	private String refFecInscripcion;
	
	@Column(name="REF_FEC_BAJA", length=30)
	private String refFecBaja;
	
	@Column(name="REF_REGISTRO_PATRONAL", length=50)
	private String refRegistroPatronal;
	
	@Column(name="DES_DOMICILIO", length=500)
	private String desDomicilio;
	
	@Column(name="DES_ACTIVIDAD", length=250)
	private String desActividad;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
	 
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	public Long getCveIdDatosLaborales() {
		return cveIdDatosLaborales;
	}

	public void setCveIdDatosLaborales(Long cveIdDatosLaborales) {
		this.cveIdDatosLaborales = cveIdDatosLaborales;
	}

	public DitCorreccionDatosAsegurado getDitCorreccionDatosAsegurado() {
		return ditCorreccionDatosAsegurado;
	}

	public void setDitCorreccionDatosAsegurado(DitCorreccionDatosAsegurado ditCorreccionDatosAsegurado) {
		this.ditCorreccionDatosAsegurado = ditCorreccionDatosAsegurado;
	}

	public String getNombrePatron() {
		return nombrePatron;
	}

	public void setNombrePatron(String nombrePatron) {
		this.nombrePatron = nombrePatron;
	}

	public String getCveEnt() {
		return cveEnt;
	}

	public void setCveEnt(String cveEnt) {
		this.cveEnt = cveEnt;
	}

	public String getRefFecInscripcion() {
		return refFecInscripcion;
	}

	public void setRefFecInscripcion(String refFecInscripcion) {
		this.refFecInscripcion = refFecInscripcion;
	}

	public String getRefFecBaja() {
		return refFecBaja;
	}

	public void setRefFecBaja(String refFecBaja) {
		this.refFecBaja = refFecBaja;
	}

	public String getRefRegistroPatronal() {
		return refRegistroPatronal;
	}

	public void setRefRegistroPatronal(String refRegistroPatronal) {
		this.refRegistroPatronal = refRegistroPatronal;
	}

	public String getDesDomicilio() {
		return desDomicilio;
	}

	public void setDesDomicilio(String desDomicilio) {
		this.desDomicilio = desDomicilio;
	}

	public String getDesActividad() {
		return desActividad;
	}

	public void setDesActividad(String desActividad) {
		this.desActividad = desActividad;
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

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}
	
}
