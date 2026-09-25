package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the DIT_ADJUNTOS_CLASIFICACION database table.
 * 
 */
@Entity
@Table(name="DIT_ADJUNTOS_CLASIFICACION")
public class DitAdjuntosClasificacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DIT_ADJUNTOS_CLASIFICACION_GENERATOR", sequenceName = "SEQ_DITADJUNTOSCLASIFICACION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_ADJUNTOS_CLASIFICACION_GENERATOR")
    @Column(name="CVE_ID_DATOS_ADJUNTOS")
	private long cveIdDatosAdjuntos;
	
	@Column(name="REF_FOLIO", nullable=false, length=255)
	private String refFolio;

	@Column(name="RUTA_ARCHIVO", nullable=false, length=150)
	private String rutaArchivo;

	@Column(name="NOMBRE_ARCHIVO", nullable=false, length=150)
	private String nombreArchivo;

	@Column(name="CVE_ID_TIPO_TRAMITE", nullable=false, precision=22)
	private Long cveIdTipoTramite;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA", nullable=false)
	private Date fecRegistroAlta;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;
    

	public long getCveIdDatosAdjuntos() {
		return cveIdDatosAdjuntos;
	}

	public void setCveIdDatosAdjuntos(long cveIdDatosAdjuntos) {
		this.cveIdDatosAdjuntos = cveIdDatosAdjuntos;
	}

	public String getRefFolio() {
		return refFolio;
	}

	public void setRefFolio(String refFolio) {
		this.refFolio = refFolio;
	}

	public String getRutaArchivo() {
		return rutaArchivo;
	}

	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public Long getCveIdTipoTramite() {
		return cveIdTipoTramite;
	}

	public void setCveIdTipoTramite(Long cveIdTipoTramite) {
		this.cveIdTipoTramite = cveIdTipoTramite;
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