package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "DIT_PERSONA_AUTORIZADA")
public class DitPersonaAutorizada implements Serializable {

	private static final long serialVersionUID = 2594764168644157194L;

	@Id
	@SequenceGenerator(name = "DIT_PERSONA_AUTORIZADA_CVEIDPERSONA_GENERATOR", sequenceName = "SEQ_DITPERSONAAUTORIZADA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_PERSONA_AUTORIZADA_CVEIDPERSONA_GENERATOR")
	@Column(name = "CVE_ID_PERSONA_AUTORIZADA")
	private long cveIdPersonaAutorizada;
	
	@ManyToOne
	@JoinColumn(name="CVE_ID_PERSONA_FISICA")
	private DitPersonaFisica ditPersonaFisica;
	
	@ManyToOne
	@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO")
	private DitPatronSujetoObligado ditPatronSujetoObligado;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
	
	public long getCveIdPersonaAutorizada() {
		return cveIdPersonaAutorizada;
	}

	public void setCveIdPersonaAutorizada(long cveIdPersonaAutorizada) {
		this.cveIdPersonaAutorizada = cveIdPersonaAutorizada;
	}

	public DitPersonaFisica getDitPersonaFisica() {
		return ditPersonaFisica;
	}
	
	public void setDitPersonaFisica(DitPersonaFisica ditPersonaFisica) {
		this.ditPersonaFisica = ditPersonaFisica;
	}
	
	public DitPatronSujetoObligado getDitPatronSujetoObligado() {
		return ditPatronSujetoObligado;
	}
	
	public void setDitPatronSujetoObligado(
			DitPatronSujetoObligado ditPatronSujetoObligado) {
		this.ditPatronSujetoObligado = ditPatronSujetoObligado;
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
		
}
