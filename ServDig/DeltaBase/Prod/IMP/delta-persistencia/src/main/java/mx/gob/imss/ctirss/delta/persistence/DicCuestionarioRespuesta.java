package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_CUESTIONARIO_RESPUESTA database table.
 * 
 */
@Entity
@Table(name="DIC_CUESTIONARIO_RESPUESTA")
public class DicCuestionarioRespuesta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_CUESTIONARIO_RESPUESTA", nullable=false, precision=22)
	private long cveIdCuestionarioRespuesta;

	@Column(name="DES_RESPUESTA", length=100)
	private String desRespuesta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IND_RESPUESTA_CORRECTA", precision=22)
	private BigDecimal indRespuestaCorrecta;

	@Column(name="NUM_SECUENCIA", precision=22)
	private BigDecimal numSecuencia;

	//bi-directional many-to-one association to DicCuestionarioPregunta
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_CUESTIONARIO_PREGUNTA")
	private DicCuestionarioPregunta dicCuestionarioPregunta;

	//bi-directional many-to-one association to DitCuestRRespAsegurado
	@OneToMany(mappedBy="dicCuestionarioRespuesta")
	private List<DitCuestRRespAsegurado> ditCuestRRespAsegurados;

    public DicCuestionarioRespuesta() {
    }

	public long getCveIdCuestionarioRespuesta() {
		return this.cveIdCuestionarioRespuesta;
	}

	public void setCveIdCuestionarioRespuesta(long cveIdCuestionarioRespuesta) {
		this.cveIdCuestionarioRespuesta = cveIdCuestionarioRespuesta;
	}

	public String getDesRespuesta() {
		return this.desRespuesta;
	}

	public void setDesRespuesta(String desRespuesta) {
		this.desRespuesta = desRespuesta;
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

	public BigDecimal getIndRespuestaCorrecta() {
		return this.indRespuestaCorrecta;
	}

	public void setIndRespuestaCorrecta(BigDecimal indRespuestaCorrecta) {
		this.indRespuestaCorrecta = indRespuestaCorrecta;
	}

	public BigDecimal getNumSecuencia() {
		return this.numSecuencia;
	}

	public void setNumSecuencia(BigDecimal numSecuencia) {
		this.numSecuencia = numSecuencia;
	}

	public DicCuestionarioPregunta getDicCuestionarioPregunta() {
		return this.dicCuestionarioPregunta;
	}

	public void setDicCuestionarioPregunta(DicCuestionarioPregunta dicCuestionarioPregunta) {
		this.dicCuestionarioPregunta = dicCuestionarioPregunta;
	}
	
	public List<DitCuestRRespAsegurado> getDitCuestRRespAsegurados() {
		return this.ditCuestRRespAsegurados;
	}

	public void setDitCuestRRespAsegurados(List<DitCuestRRespAsegurado> ditCuestRRespAsegurados) {
		this.ditCuestRRespAsegurados = ditCuestRRespAsegurados;
	}
	
}