package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the SPT_RESOLUTIVOS_RESOLUCION database table.
 * 
 */
@Entity
@Table(name="SPT_RESOLUTIVOS_RESOLUCION")
@NamedQuery(name="SptResolutivosResolucion.findAll", query="SELECT s FROM SptResolutivosResolucion s")
public class SptResolutivosResolucion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTRESOLUTIVOSRESOLUCION", sequenceName = "SEQ_SPTRESOLUTIVOSRESOLUCION")
	@GeneratedValue(generator = "SEQ_SPTRESOLUTIVOSRESOLUCION")
	@Column(name="CVE_ID_RESOLUTIVO_RESOLUCION")
	private long cveIdResolutivoResolucion;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to SpcResolutivo
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="CVE_RESOLUTIVO", referencedColumnName="CVE_RESOLUTIVO"),
		@JoinColumn(name="CVE_TIPO_RESOLUCION", referencedColumnName="CVE_TIPO_RESOLUCION"),
		@JoinColumn(name="ID_REGIMEN", referencedColumnName="ID_REGIMEN")
		})
	private SpcResolutivo spcResolutivo;

	//bi-directional many-to-one association to SptResolucion
	@ManyToOne
	@JoinColumn(name="CVE_ID_RESOLUCION")
	private SptResolucion sptResolucion;

	public SptResolutivosResolucion() {
	}

	public long getCveIdResolutivoResolucion() {
		return this.cveIdResolutivoResolucion;
	}

	public void setCveIdResolutivoResolucion(long cveIdResolutivoResolucion) {
		this.cveIdResolutivoResolucion = cveIdResolutivoResolucion;
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

	public SpcResolutivo getSpcResolutivo() {
		return this.spcResolutivo;
	}

	public void setSpcResolutivo(SpcResolutivo spcResolutivo) {
		this.spcResolutivo = spcResolutivo;
	}

	public SptResolucion getSptResolucion() {
		return this.sptResolucion;
	}

	public void setSptResolucion(SptResolucion sptResolucion) {
		this.sptResolucion = sptResolucion;
	}

}