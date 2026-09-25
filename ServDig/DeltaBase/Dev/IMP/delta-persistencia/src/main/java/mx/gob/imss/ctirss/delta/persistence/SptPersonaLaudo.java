package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the SPT_PERSONA_LAUDO database table.
 * 
 */
@Entity
@Table(name="SPT_PERSONA_LAUDO")
@NamedQuery(name="SptPersonaLaudo.findAll", query="SELECT s FROM SptPersonaLaudo s")
public class SptPersonaLaudo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTPERSONALAUDO", sequenceName = "SEQ_SPTPERSONALAUDO")
	@GeneratedValue(generator = "SEQ_SPTPERSONALAUDO")
	@Column(name="CVE_ID_PERSONA_LAUDO")
	private long cveIdPersonaLaudo;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="OTROS_DATOS_LAUDO_PENDIENTE")
	private String otrosDatosLaudoPendiente;

	//bi-directional many-to-one association to DitPersona
	@ManyToOne
	@JoinColumn(name="CVE_ID_PERSONA")
	private DitPersona ditPersona;

	public SptPersonaLaudo() {
	}

	public long getCveIdPersonaLaudo() {
		return this.cveIdPersonaLaudo;
	}

	public void setCveIdPersonaLaudo(long cveIdPersonaLaudo) {
		this.cveIdPersonaLaudo = cveIdPersonaLaudo;
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

	public String getOtrosDatosLaudoPendiente() {
		return this.otrosDatosLaudoPendiente;
	}

	public void setOtrosDatosLaudoPendiente(String otrosDatosLaudoPendiente) {
		this.otrosDatosLaudoPendiente = otrosDatosLaudoPendiente;
	}

	public DitPersona getDitPersona() {
		return this.ditPersona;
	}

	public void setDitPersona(DitPersona ditPersona) {
		this.ditPersona = ditPersona;
	}

}