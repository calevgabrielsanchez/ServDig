package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the DIT_FOLIO_CERTIFICACION database table.
 * 
 */
@Entity
@Table(name = "DIT_FOLIO_CERTIFICACION")
public class DitFolioCertificacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DIT_FOLIO_CERTIFICACION_CVEIDFOLIOCERTIFICACION_GENERATOR", sequenceName = "SEQ_DITFOLIOCERTIFICACION")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_FOLIO_CERTIFICACION_CVEIDFOLIOCERTIFICACION_GENERATOR")
	@Column(name = "CVE_ID_FOLIO_CERTIFICACION")
	private Long cveIdFolioCertificacion;

	@Column(name = "NUM_DELEGACION")
	private String numDelegacion;

	@Column(name = "NUM_ANIO_REGISTRO")
	private BigDecimal numAnioRegistro;

	@Column(name = "NOM_SECUENCIA")
	private String nomSecuencia;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	public Long getCveIdFolioCertificacion() {
		return cveIdFolioCertificacion;
	}

	public void setCveIdFolioCertificacion(Long cveIdFolioCertificacion) {
		this.cveIdFolioCertificacion = cveIdFolioCertificacion;
	}

	public String getNumDelegacion() {
		return numDelegacion;
	}

	public void setNumDelegacion(String numDelegacion) {
		this.numDelegacion = numDelegacion;
	}

	public BigDecimal getNumAnioRegistro() {
		return numAnioRegistro;
	}

	public void setNumAnioRegistro(BigDecimal numAnioRegistro) {
		this.numAnioRegistro = numAnioRegistro;
	}

	public String getNomSecuencia() {
		return nomSecuencia;
	}

	public void setNomSecuencia(String nomSecuencia) {
		this.nomSecuencia = nomSecuencia;
	}

	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

}