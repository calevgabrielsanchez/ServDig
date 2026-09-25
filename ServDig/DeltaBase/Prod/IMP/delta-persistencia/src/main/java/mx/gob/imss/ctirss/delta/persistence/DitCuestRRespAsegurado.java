package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_CUEST_R_RESP_ASEGURADO database table.
 * 
 */
@Entity
@Table(name="DIT_CUEST_R_RESP_ASEGURADO")
public class DitCuestRRespAsegurado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_CUEST_R_RESP_ASEGURADO", nullable=false, precision=22)
	private long cveIdCuestRRespAsegurado;

	@Column(name="DES_RESPUESTA_TEXTO_LIBRE", length=255)
	private String desRespuestaTextoLibre;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicCuestionarioRespuesta
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_CUESTIONARIO_RESPUESTA")
	private DicCuestionarioRespuesta dicCuestionarioRespuesta;

	//bi-directional many-to-one association to DitCuestRPregAsegurado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_CUEST_R_PREG_ASEGURADO")
	private DitCuestRPregAsegurado ditCuestRPregAsegurado;

    public DitCuestRRespAsegurado() {
    }

	public long getCveIdCuestRRespAsegurado() {
		return this.cveIdCuestRRespAsegurado;
	}

	public void setCveIdCuestRRespAsegurado(long cveIdCuestRRespAsegurado) {
		this.cveIdCuestRRespAsegurado = cveIdCuestRRespAsegurado;
	}

	public String getDesRespuestaTextoLibre() {
		return this.desRespuestaTextoLibre;
	}

	public void setDesRespuestaTextoLibre(String desRespuestaTextoLibre) {
		this.desRespuestaTextoLibre = desRespuestaTextoLibre;
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

	public DicCuestionarioRespuesta getDicCuestionarioRespuesta() {
		return this.dicCuestionarioRespuesta;
	}

	public void setDicCuestionarioRespuesta(DicCuestionarioRespuesta dicCuestionarioRespuesta) {
		this.dicCuestionarioRespuesta = dicCuestionarioRespuesta;
	}
	
	public DitCuestRPregAsegurado getDitCuestRPregAsegurado() {
		return this.ditCuestRPregAsegurado;
	}

	public void setDitCuestRPregAsegurado(DitCuestRPregAsegurado ditCuestRPregAsegurado) {
		this.ditCuestRPregAsegurado = ditCuestRPregAsegurado;
	}
	
}