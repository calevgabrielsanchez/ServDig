package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the DIT_DATOS_PERSONA_SAT database table.
 * 
 */
@Entity
@Table(name="DIT_DATOS_PERSONA_SAT")
public class DitDatosPersonaSat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIT_DATOS_PERSONA_SAT_CVEIDDATOSSAT_GENERATOR", sequenceName="SEQ_DITDATOSPERSONASAT")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIT_DATOS_PERSONA_SAT_CVEIDDATOSSAT_GENERATOR")
	@Column(name="CVE_ID_DATOS_SAT")
	private long cveIdDatosSat;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_CONSTITUCION")
	private Date fecConstitucion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INICIO_OPERACIONES")
	private Date fecInicioOperaciones;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitPersonaFisica
    @ManyToOne
	@JoinColumn(name="CVE_ID_PERSONA_FISICA")
	private DitPersonaFisica ditPersonaFisica;

	//bi-directional many-to-one association to DitPersonaMoral
    @ManyToOne
	@JoinColumn(name="CVE_ID_PERSONA_MORAL")
	private DitPersonaMoral ditPersonaMoral;

    public DitDatosPersonaSat() {
    }

	public long getCveIdDatosSat() {
		return this.cveIdDatosSat;
	}

	public void setCveIdDatosSat(long cveIdDatosSat) {
		this.cveIdDatosSat = cveIdDatosSat;
	}

	public Date getFecConstitucion() {
		return this.fecConstitucion;
	}

	public void setFecConstitucion(Date fecConstitucion) {
		this.fecConstitucion = fecConstitucion;
	}

	public Date getFecInicioOperaciones() {
		return this.fecInicioOperaciones;
	}

	public void setFecInicioOperaciones(Date fecInicioOperaciones) {
		this.fecInicioOperaciones = fecInicioOperaciones;
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

	public DitPersonaFisica getDitPersonaFisica() {
		return this.ditPersonaFisica;
	}

	public void setDitPersonaFisica(DitPersonaFisica ditPersonaFisica) {
		this.ditPersonaFisica = ditPersonaFisica;
	}
	
	public DitPersonaMoral getDitPersonaMoral() {
		return this.ditPersonaMoral;
	}

	public void setDitPersonaMoral(DitPersonaMoral ditPersonaMoral) {
		this.ditPersonaMoral = ditPersonaMoral;
	}
	
}